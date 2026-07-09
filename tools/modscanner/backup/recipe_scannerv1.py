import os
import json
import zipfile
from collections import Counter


VERSION = "1"


def log(text):
    print(f"[RecipeScanner v{VERSION}] {text}")



# =====================================
# Recipe categories
# =====================================

RECIPE_TYPES = {

    "crafting_shaped":
    [
        "minecraft:crafting_shaped",
        "crafting_shaped"
    ],


    "crafting_shapeless":
    [
        "minecraft:crafting_shapeless",
        "crafting_shapeless"
    ],


    "smelting":
    [
        "minecraft:smelting",
        "smelting"
    ],


    "blasting":
    [
        "minecraft:blasting",
        "blasting"
    ],


    "smoking":
    [
        "minecraft:smoking",
        "smoking"
    ],


    "stonecutting":
    [
        "minecraft:stonecutting",
        "stonecutting"
    ],


    "special":
    [
        "special"
    ]

}




# =====================================
# Empty result
# =====================================

def create_result():

    return {

        "total_recipes": 0,

        "crafting_shaped": 0,

        "crafting_shapeless": 0,

        "smelting": 0,

        "blasting": 0,

        "smoking": 0,

        "stonecutting": 0,

        "special": 0,


        "json_files": 0,


        "ingredients": {},


        "outputs": {},


        "complex_recipes": [],


        "recipe_paths": []

    }





# =====================================
# Recipe type detection
# =====================================

def detect_recipe_type(data):


    if not isinstance(data, dict):

        return None



    recipe_type = data.get(
        "type",
        ""
    )



    recipe_type = recipe_type.lower()



    for category, names in RECIPE_TYPES.items():

        for name in names:


            if name in recipe_type:


                return category



    return None





# =====================================
# Ingredient collector
# =====================================

def collect_ingredients(data, result):


    text = str(
        data
    )



    words = []


    current = ""



    for char in text:


        if char.isalnum() or char in "_:-":

            current += char


        else:


            if len(current) > 3:

                words.append(
                    current
                )

            current = ""



    for word in words:


        if ":" in word:


            result["ingredients"][word] = (

                result["ingredients"].get(
                    word,
                    0
                )

                +

                1

            )





# =====================================
# Output collector
# =====================================

def collect_output(data, result):


    output = None



    if isinstance(data, dict):


        if "result" in data:


            output = data["result"]



        elif "output" in data:


            output = data["output"]



    if output:


        if isinstance(output, dict):

            item = output.get(
                "item"
            )

        else:

            item = output



        if item:


            result["outputs"][item] = (

                result["outputs"].get(
                    item,
                    0
                )

                +

                1

            )





# =====================================
# JSON recipe scanner
# =====================================

def scan_recipe_file(path, data, result):


    result["json_files"] += 1



    recipe_type = detect_recipe_type(
        data
    )



    if recipe_type:


        result[recipe_type] += 1



        result["total_recipes"] += 1



    collect_ingredients(
        data,
        result
    )


    collect_output(
        data,
        result
    )



    # Сложные рецепты

    ingredient_count = len(
        str(data)
    )



    if ingredient_count > 1000:


        result["complex_recipes"].append(

            path

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



                if (

                    "recipes/"
                    not in lower

                    or

                    not lower.endswith(
                        ".json"
                    )

                ):

                    continue



                try:


                    data = json.loads(

                        jar.read(
                            file
                        ).decode(
                            "utf-8",
                            errors="ignore"
                        )

                    )



                    result["recipe_paths"].append(
                        file
                    )



                    scan_recipe_file(

                        file,

                        data,

                        result

                    )



                except Exception:


                    continue





    except Exception as error:


        log(
            f"ERROR {filename}: {error}"
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



    result = scan_recipes(
        jars
    )



    for mod, data in result.items():


        print()

        print(
            mod
        )

        print(
            data
        )