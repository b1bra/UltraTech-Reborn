import os
import json
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

        def exception(self, error):
            print(error)

    logger = FallbackLogger()



def log(text):

    logger.info(
        f"[RecipeScanner v{VERSION}] {text}"
    )





# =====================================
# Recipe patterns for 1.7.10
# =====================================

CODE_RECIPE_PATTERNS = {


    "crafting":

    [

        "GameRegistry.addRecipe",

        "GameRegistry.addShapedRecipe",

        "GameRegistry.addShapelessRecipe",

        "addRecipe",

        "ShapedRecipe",

        "ShapelessRecipe"

    ],



    "ore_recipes":

    [

        "ShapedOreRecipe",

        "ShapelessOreRecipe"

    ],



    "smelting":

    [

        "GameRegistry.addSmelting",

        "addSmelting",

        "FurnaceRecipes"

    ]

}






# =====================================
# JSON recipe patterns
# =====================================

JSON_RECIPE_TYPES = {


    "crafting_shaped":

    [

        "crafting_shaped"

    ],


    "crafting_shapeless":

    [

        "crafting_shapeless"

    ],


    "smelting":

    [

        "smelting"

    ]

}





# =====================================
# Result
# =====================================

def create_result():


    return {


        "total_recipes": 0,


        "json_recipes": 0,


        "code_recipes": 0,


        "crafting": 0,


        "ore_recipes": 0,


        "smelting": 0,


        "special": 0,



        "json_files": 0,


        "class_files_scanned": 0,


        "recipe_classes": [],



        "ingredients": {},


        "outputs": {},


        "recipe_paths": []

    }





# =====================================
# JSON detection
# =====================================

def detect_json_recipe(data):


    if not isinstance(
        data,
        dict
    ):

        return None



    recipe_type = str(

        data.get(
            "type",
            ""

        )

    ).lower()



    for name, values in JSON_RECIPE_TYPES.items():


        for value in values:


            if value in recipe_type:

                return name



    return None





# =====================================
# JSON scanner
# =====================================

def scan_recipe_file(path, data, result):


    result["json_files"] += 1



    recipe_type = detect_json_recipe(
        data
    )



    if recipe_type:


        result["total_recipes"] += 1

        result["json_recipes"] += 1



        if recipe_type in result:

            result[recipe_type] += 1



    result["recipe_paths"].append(
        path
    )






# =====================================
# Class scanner
# =====================================

def analyze_class(text, filename, result):


    found = False



    for category, patterns in CODE_RECIPE_PATTERNS.items():


        for pattern in patterns:


            if pattern in text:


                found = True


                result["code_recipes"] += 1


                result["total_recipes"] += 1



                if category in result:

                    result[category] += 1



                break




    if found:


        result["recipe_classes"].append(
            filename
        )






# =====================================
# JAR scanner
# =====================================

def scan_jar(path, index=0, total=0):


    filename = os.path.basename(
        path
    )


    log(

        f"[{index}/{total}] Scanning recipes: {filename}"

    )



    result = create_result()



    try:


        with zipfile.ZipFile(
            path,
            "r"
        ) as jar:



            for file in jar.namelist():



                lower = file.lower()



                # JSON recipes


                if (

                    "recipes/" in lower

                    and

                    lower.endswith(".json")

                ):


                    try:


                        data = json.loads(

                            jar.read(
                                file
                            ).decode(

                                "utf-8",

                                errors="ignore"

                            )

                        )



                        scan_recipe_file(

                            file,

                            data,

                            result

                        )



                    except Exception:

                        continue





                # Java classes


                elif lower.endswith(
                    ".class"
                ):



                    try:


                        text = jar.read(
                            file
                        ).decode(

                            "latin1",

                            errors="ignore"

                        )



                        result["class_files_scanned"] += 1



                        analyze_class(

                            text,

                            file,

                            result

                        )



                    except Exception:


                        continue





    except Exception as error:


        logger.error(

            f"[RecipeScanner v{VERSION}] ERROR {filename}: {error}"

        )





    result["recipe_classes"] = list(

        set(
            result["recipe_classes"]
        )

    )



    log(

        f"{filename}: {result['total_recipes']} recipes"

    )


    return result






# =====================================
# Multiple mods
# =====================================

def scan_recipes(jars):


    log(
        "Starting recipe analysis..."
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
        "Recipe analysis complete"
    )


    return result





# =====================================
# Test
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



    data = scan_recipes(
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