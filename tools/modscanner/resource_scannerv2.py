import os
import zipfile


VERSION = "2"


try:
    from logger import get_logger

    logger = get_logger()

except Exception:

    class FallbackLogger:

        def info(self, text):
            print(text)

        def debug(self, text):
            print(text)

        def success(self, text):
            print(text)

        def warning(self, text):
            print(text)

        def error(self, text):
            print(text)

    logger = FallbackLogger()



def log(text):

    logger.info(
        f"[ResourceScanner v{VERSION}] {text}"
    )



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
        ".lang"
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

        "block_textures": 0,

        "item_textures": 0,


        "models": 0,

        "block_models": 0,

        "item_models": 0,


        "blockstates": 0,


        "languages": 0,


        "sounds": 0,


        "gui_resources": 0,


        "recipes": 0,


        "advancements": 0,


        "json_files": 0,


        "resource_files": 0,


        "total_resource_size_mb": 0,


        "important_paths": [],


        "large_files": [],


        "resource_complexity": 0

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
# Extra resource classification
# =====================================

def analyze_special_resource(path, result):

    lower = path.lower()



    # Textures

    if "textures/" in lower:

        if lower.endswith(
            (".png", ".jpg", ".jpeg")
        ):

            result["textures"] += 1



            if "textures/blocks/" in lower:

                result["block_textures"] += 1



            elif "textures/items/" in lower:

                result["item_textures"] += 1





    # Models

    if "models/" in lower:

        result["models"] += 1


        if "models/block/" in lower:

            result["block_models"] += 1



        elif "models/item/" in lower:

            result["item_models"] += 1





    # GUI

    if (

        "gui/" in lower

        or

        "textures/gui/" in lower

    ):

        result["gui_resources"] += 1






# =====================================
# JAR scanner
# =====================================

def scan_jar(path, index=0, total=0):


    filename = os.path.basename(
        path
    )


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



                analyze_special_resource(
                    name,
                    result
                )



                if (

                    category

                    and

                    len(
                        result["important_paths"]
                    )

                    <

                    100

                ):


                    result["important_paths"].append(
                        name
                    )



                if info.file_size > (

                    1024 * 1024

                ):


                    result["large_files"].append(

                        {

                            "file":
                            name,


                            "size_mb":
                            round(

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



        result["resource_complexity"] = calculate_complexity(
            result
        )



    except Exception as error:


        logger.error(
            f"[ResourceScanner v{VERSION}] ERROR {filename}: {error}"
        )



    log(

        f"{filename}: {result['resource_files']} resources"

    )


    return result




# =====================================
# Complexity calculation
# =====================================

def calculate_complexity(result):


    score = 0



    score += result["textures"] / 20


    score += result["models"] / 10


    score += result["blockstates"] / 5


    score += result["languages"] / 20


    score += result["sounds"] / 10


    score += result["gui_resources"] / 5



    if result["large_files"]:

        score += len(
            result["large_files"]
        )



    return min(
        int(score),
        100
    )





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



    for mod, info in data.items():

        print()

        print(
            mod
        )

        print(
            info
        )