import zipfile
import os


VERSION = "1"


def log(text):
    print(f"[ClassScanner v{VERSION}] {text}")



# Вес проблем
PATTERNS = {

    "CoreMod / ASM": [
        "IClassTransformer",
        "IFMLLoadingPlugin",
        "FMLCorePlugin",
        "CorePlugin"
    ],

    "Old Forge API": [
        "cpw.mods.fml",
        "net.minecraftforge"
    ],

    "Old Registry System": [
        "GameRegistry",
        "LanguageRegistry",
        "EntityRegistry"
    ],

    "Old Network System": [
        "NetworkRegistry",
        "Packet250CustomPayload"
    ]

}



def analyze_class_content(data):

    result = []

    score = 0


    for category, words in PATTERNS.items():

        for word in words:

            if word in data:

                result.append(
                    category + ": " + word
                )


                if category == "CoreMod / ASM":
                    score += 40

                elif category == "Old Forge API":
                    score += 15

                elif category == "Old Registry System":
                    score += 10

                elif category == "Old Network System":
                    score += 15



    return result, score





def scan_jar(path, index, total):

    name = os.path.basename(path)


    log(
        f"[{index}/{total}] Analyzing {name}"
    )


    result = {

        "size_mb": 0,

        "files": 0,

        "class_files": 0,

        "packages": 0,

        "detections": [],

        "score": 0,

        "difficulty": "Unknown"

    }



    try:

        result["size_mb"] = round(
            os.path.getsize(path)
            /
            (1024 * 1024),
            2
        )


        packages = set()


        with zipfile.ZipFile(path, "r") as jar:


            files = jar.namelist()


            result["files"] = len(files)



            for file in files:


                if file.endswith(".class"):


                    result["class_files"] += 1


                    package = os.path.dirname(
                        file
                    )


                    if package:

                        packages.add(
                            package
                        )


                    try:

                        content = jar.read(
                            file
                        ).decode(
                            "latin1",
                            errors="ignore"
                        )


                        detected, score = analyze_class_content(
                            content
                        )


                        result["detections"].extend(
                            detected
                        )


                        result["score"] += score



                    except Exception:

                        pass



        result["packages"] = len(
            packages
        )



        result["detections"] = list(
            set(
                result["detections"]
            )
        )


        if result["score"] >= 70:

            result["difficulty"] = "Hard"


        elif result["score"] >= 30:

            result["difficulty"] = "Normal"


        else:

            result["difficulty"] = "Easy"



    except Exception as error:

        log(
            f"ERROR {name}: {error}"
        )



    log(
        f" -> {result['difficulty']} "
        f"({result['score']}/100)"
    )


    return result





def scan_mods(jars):

    log(
        "Starting class analysis..."
    )


    results = {}


    total = len(jars)


    for index, jar in enumerate(
        jars,
        1
    ):


        results[
            os.path.basename(jar)
        ] = scan_jar(
            jar,
            index,
            total
        )


    log(
        "Class analysis finished"
    )


    return results