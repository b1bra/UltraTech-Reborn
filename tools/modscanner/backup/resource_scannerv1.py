import os
import zipfile


VERSION = "1"


def log(text):
    print(f"[ResourceScanner v{VERSION}] {text}")



# =====================================
# Resource categories
# =====================================

RESOURCE_PATTERNS = {

    "textures":
    [
        ".png",
        ".jpg",
        ".jpeg"
    ],


    "models":
    [
        "models/",
        "/model/"
    ],


    "blockstates":
    [
        "blockstates/"
    ],


    "languages":
    [
        "lang/",
        ".lang",
        ".json"
    ],


    "sounds":
    [
        "sounds/",
        ".ogg"
    ],


    "recipes":
    [
        "recipes/"
    ],


    "advancements":
    [
        "advancements/"
    ]

}




# =====================================
# Result structure
# =====================================

def create_result():

    return {

        "textures": 0,

        "models": 0,

        "blockstates": 0,

        "languages": 0,

        "sounds": 0,

        "recipes": 0,

        "advancements": 0,


        "json_files": 0,


        "resource_files": 0,


        "total_resource_size_mb": 0,


        "important_paths": [],


        "large_files": []

    }





# =====================================
# Category detector
# =====================================

def detect_category(path):


    path = path.lower()



    if "textures/" in path:

        return "textures"



    if "blockstates/" in path:

        return "blockstates"



    if "models/" in path:

        return "models"



    if "lang/" in path or path.endswith(".lang"):

        return "languages"



    if "sounds/" in path or path.endswith(".ogg"):

        return "sounds"



    if "recipes/" in path:

        return "recipes"



    if "advancements/" in path:

        return "advancements"



    return None





# =====================================
# JAR resource scan
# =====================================

def scan_jar(path, index=0, total=0):


    filename = os.path.basename(path)



    log(
        f"[{index}/{total}] Scanning resources: {filename}"
    )



    result = create_result()



    try:


        with zipfile.ZipFile(
            path,
            "r"
        ) as jar:



            files = jar.infolist()



            for info in files:



                name = info.filename



                if not name.startswith(
                    "assets/"
                ):

                    continue



                result["resource_files"] += 1



                result["total_resource_size_mb"] += (

                    info.file_size
                    /
                    (1024 * 1024)

                )



                category = detect_category(
                    name
                )



                if category:


                    result[category] += 1



                if name.endswith(
                    ".json"
                ):


                    result["json_files"] += 1



                # Save important paths

                if category and len(
                    result["important_paths"]
                ) < 100:


                    result["important_paths"].append(
                        name
                    )



                # Large resources

                if info.file_size > 1024 * 1024:


                    result["large_files"].append(

                        {
                            "file": name,
                            "size_mb": round(
                                info.file_size /
                                (1024*1024),
                                2
                            )
                        }

                    )



        result["total_resource_size_mb"] = round(

            result["total_resource_size_mb"],

            2

        )



    except Exception as error:


        log(
            f"ERROR {filename}: {error}"
        )



    log(

        f"{filename}: "
        f"{result['resource_files']} resources"

    )



    return result





# =====================================
# Multiple mods scan
# =====================================

def scan_resources(jars):


    log(
        "Starting resource analysis..."
    )



    result = {}



    total = len(
        jars
    )



    for index, jar in enumerate(
        jars,
        1
    ):


        result[
            os.path.basename(jar)
        ] = scan_jar(

            jar,

            index,

            total

        )



    log(
        "Resource analysis complete"
    )



    return result





# =====================================
# Standalone test
# =====================================

if __name__ == "__main__":


    folder = input(
        "Enter mods folder: "
    )



    jars = []



    for root, dirs, files in os.walk(folder):


        for file in files:


            if file.endswith(".jar"):


                jars.append(

                    os.path.join(
                        root,
                        file
                    )

                )



    data = scan_resources(
        jars
    )



    print()

    for mod, info in data.items():

        print(
            mod
        )

        print(
            info
        )