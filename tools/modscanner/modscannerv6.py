import os
import re
import json
import zipfile
import importlib.util
from datetime import datetime


VERSION = "3"


def log(text):
    print(f"[ModScanner v{VERSION}] {text}")



# =====================================
# Paths
# =====================================

def get_project_root():

    return os.path.abspath(
        os.path.join(
            os.path.dirname(__file__),
            "../.."
        )
    )



def get_docs_path():

    path = os.path.join(
        get_project_root(),
        "docs",
        "generated"
    )

    os.makedirs(
        path,
        exist_ok=True
    )

    return path





# =====================================
# Input
# =====================================

def ask_mod_folder():

    print()

    print(
        "Enter mods folder path:"
    )

    path = input(
        "> "
    ).strip()



    path = path.replace(
        '"',
        ""
    )



    if not os.path.isdir(path):

        raise Exception(
            "Mods folder does not exist"
        )



    return path






# =====================================
# JAR finder
# =====================================

def find_jars(folder):

    jars = []



    for root, dirs, files in os.walk(folder):


        for file in files:


            if file.lower().endswith(
                ".jar"
            ):


                jars.append(

                    os.path.join(
                        root,
                        file
                    )

                )



    return jars
# =====================================
# Version selector
# =====================================

def find_latest_version(folder, prefix):


    versions = []



    for file in os.listdir(folder):


        if not file.startswith(prefix):

            continue



        if not file.endswith(".py"):

            continue



        match = re.search(
            r"v(\d+)",
            file
        )



        if match:


            version = int(
                match.group(1)
            )


        else:


            version = 0



        versions.append(

            (
                version,
                file
            )

        )



    if not versions:


        return None



    versions.sort(
        reverse=True
    )



    return os.path.join(

        folder,

        versions[0][1]

    )






# =====================================
# Safe python module loader
# =====================================

def load_module(script, module_name):


    if not os.path.isfile(script):


        raise Exception(

            f"{script} not found"

        )



    try:


        spec = importlib.util.spec_from_file_location(

            module_name,

            script

        )



        if spec is None:


            raise Exception(

                "Cannot create module spec"

            )



        module = importlib.util.module_from_spec(
            spec
        )



        spec.loader.exec_module(
            module
        )



        return module



    except Exception as error:


        raise Exception(

            f"Cannot load {os.path.basename(script)}: {error}"

        )






# =====================================
# External scanners loader
# =====================================

def load_scanners():


    folder = os.path.dirname(
        os.path.abspath(__file__)
    )



    scanners = {}



    required = {


        "class":
        "class_scanner",


        "resource":
        "resource_scanner",


        "recipe":
        "recipe_scanner"

    }



    for name, prefix in required.items():



        script = find_latest_version(

            folder,

            prefix

        )



        if script is None:


            raise Exception(

                f"{prefix} not found"

            )



        log(

            f"Selected {name} scanner: {os.path.basename(script)}"

        )



        module = load_module(

            script,

            name + "_scanner"

        )



        function_name = {


            "class":
            "scan_mods",


            "resource":
            "scan_resources",


            "recipe":
            "scan_recipes"

        }[name]



        if not hasattr(

            module,

            function_name

        ):



            raise Exception(

                f"{os.path.basename(script)} missing {function_name}()"

            )



        scanners[name] = module



    return scanners
# =====================================
# mcmod.info reader
# =====================================

def read_mcmod_info(jar_path):

    filename = os.path.splitext(
        os.path.basename(jar_path)
    )[0]


    result = {

        "name": filename,

        "modid": filename,

        "version": "Unknown",

        "author": "Unknown"

    }



    try:

        with zipfile.ZipFile(
            jar_path,
            "r"
        ) as jar:



            for file in jar.namelist():


                if not file.endswith(
                    "mcmod.info"
                ):

                    continue



                text = jar.read(
                    file
                ).decode(
                    "utf-8",
                    errors="ignore"
                )



                for key in result.keys():


                    match = re.search(

                        f'"{key}"\\s*:\\s*"([^"]+)"',

                        text

                    )



                    if match:

                        result[key] = match.group(1)



                break



    except Exception as error:

        log(
            f"mcmod.info error {jar_path}: {error}"
        )



    return result





# =====================================
# MANIFEST reader
# =====================================

def read_manifest(jar_path):


    result = {


        "dependencies": [],


        "forge_version":
        "Unknown"

    }



    try:


        with zipfile.ZipFile(
            jar_path,
            "r"
        ) as jar:



            for file in jar.namelist():


                if file.upper().endswith(
                    "MANIFEST.MF"
                ):


                    text = jar.read(
                        file
                    ).decode(
                        "utf-8",
                        errors="ignore"
                    )



                    for line in text.splitlines():


                        lower = line.lower()



                        if (
                            "forge" in lower
                            or
                            "fml" in lower
                        ):


                            result["forge_version"] = line.strip()



                    break



    except Exception:


        pass



    return result






# =====================================
# Dependency scanner
# =====================================

def scan_dependencies(jar_path):

    dependencies = []


    try:

        with zipfile.ZipFile(
            jar_path,
            "r"
        ) as jar:


            for file in jar.namelist():


                if not file.endswith(
                    "mcmod.info"
                ):

                    continue



                text = jar.read(
                    file
                ).decode(
                    "utf-8",
                    errors="ignore"
                )



                # -------------------------
                # JSON dependencies
                # -------------------------

                matches = re.findall(

                    r'"dependencies"\s*:\s*\[(.*?)\]',

                    text,

                    re.DOTALL

                )



                for block in matches:


                    values = re.findall(

                        r'"([^"]+)"',

                        block

                    )


                    dependencies.extend(
                        values
                    )





                # -------------------------
                # Forge dependency syntax
                # -------------------------

                patterns = [


                    r'required-after:([A-Za-z0-9_\-]+)',


                    r'required-before:([A-Za-z0-9_\-]+)',


                    r'before:([A-Za-z0-9_\-]+)',


                    r'after:([A-Za-z0-9_\-]+)'

                ]



                for pattern in patterns:


                    dependencies.extend(

                        re.findall(

                            pattern,

                            text

                        )

                    )



                break





    except Exception as error:


        log(
            f"Dependency error {jar_path}: {error}"
        )




    # очистка

    result = []


    for dep in dependencies:


        dep = dep.strip()



        if (

            dep

            and

            dep.lower() not in [

                "forge",

                "minecraft"

            ]

            and

            dep not in result

        ):


            result.append(
                dep
            )



    return result

# =====================================
# Surface scan
# =====================================

def surface_scan(jars):


    log(
        "Starting surface scan..."
    )



    result = {}



    total = len(
        jars
    )



    for index, jar in enumerate(
        jars,
        1
    ):



        filename = os.path.basename(
            jar
        )



        log(

            f"[Surface {index}/{total}] {filename}"

        )



        info = read_mcmod_info(
            jar
        )



        manifest = read_manifest(
            jar
        )



        dependencies = scan_dependencies(
            jar
        )



        result[filename] = {


            "file":
            filename,


            "path":
            jar,


            "name":
            info["name"],


            "modid":
            info["modid"],


            "version":
            info["version"],


            "author":
            info["author"],



            "size_mb":

            round(

                os.path.getsize(jar)
                /
                (1024*1024),

                2

            ),



            "forge":

            manifest["forge_version"],



            "dependencies":

            dependencies

        }



    log(
        "Surface scan complete"
    )



    return result






# =====================================
# Merge all scanners
# =====================================

def merge_scans(

    surface,

    class_data,

    resource_data,

    recipe_data

):


    mods = []



    for filename, mod in surface.items():



        data = mod.copy()



        # Class scanner

        if filename in class_data:


            data.update(

                class_data[filename]

            )



        # Resource scanner

        if filename in resource_data:


            data["resources"] = (

                resource_data[filename]

            )



        else:


            data["resources"] = {}





        # Recipe scanner

        if filename in recipe_data:


            data["recipe_data"] = (

                recipe_data[filename]

            )



        else:


            data["recipe_data"] = {}





        # Defaults


        defaults = {


            "difficulty":
            "Unknown",


            "score":
            0,


            "type":
            [],


            "reasons":
            [],


            "content":
            {},


            "registration":
            {},


            "gui":
            {},


            "network":
            {},


            "api_usage":
            {},


            "important_classes":
            []

        }



        for key, value in defaults.items():


            if key not in data:


                data[key] = value



        mods.append(
            data
        )



    return mods
# =====================================
# Markdown block writer
# =====================================

def write_block(file, lines):


    file.write(
        "```text\n"
    )



    for line in lines:


        file.write(
            str(line)
            +
            "\n"
        )



    file.write(
        "```\n\n"
    )







# =====================================
# modlist generator
# =====================================

def create_modlist(mods):


    path = os.path.join(

        get_docs_path(),

        "modlist.md"

    )



    log(
        "Creating modlist.md..."
    )



    with open(

        path,

        "w",

        encoding="utf-8"

    ) as file:



        file.write(

            "# UltraTech-Reborn Mod Analysis\n\n"

        )



        file.write(

            f"Generated: {datetime.now()}\n\n"

        )



        file.write(

            f"Total mods: {len(mods)}\n\n"

        )





        for mod in mods:



            # -------------------------
            # Header
            # -------------------------


            file.write(

                f"## {mod.get('name','Unknown')}\n\n"

            )



            write_block(

                file,

                [

                    f"File: {mod.get('file')}",


                    f"Mod ID: {mod.get('modid')}",


                    f"Version: {mod.get('version')}",


                    f"Author: {mod.get('author')}",


                    f"Size: {mod.get('size_mb')} MB",


                    f"Category: {', '.join(mod.get('type', ['Unknown']))}",


                    f"Migration difficulty: {mod.get('difficulty')}",


                    f"Migration score: {mod.get('score')}/100",


                    "Status: Not analyzed",


                    "Notes: Detected automatically."

                ]

            )





            # -------------------------
            # Dependencies
            # -------------------------


            file.write(
                "Dependencies:\n"
            )



            deps = mod.get(
                "dependencies",
                []
            )



            if deps:


                write_block(

                    file,

                    deps

                )


            else:


                write_block(

                    file,

                    [

                        "No dependencies detected"

                    ]

                )





            # -------------------------
            # Content
            # -------------------------


            file.write(
                "Content:\n"
            )



            content = mod.get(
                "content",
                {}
            )



            write_block(

                file,

                [

                    f"Blocks: {content.get('Blocks',0)}",


                    f"Items: {content.get('Items',0)}",


                    f"TileEntities: {content.get('TileEntities',0)}",


                    f"Entities: {content.get('Entities',0)}"

                ]

            )





            # -------------------------
            # Resources
            # -------------------------


            file.write(
                "Resources:\n"
            )



            resources = mod.get(
                "resources",
                {}
            )



            write_block(

                file,

                [

                    f"Textures: {resources.get('textures',0)}",


                    f"Models: {resources.get('models',0)}",


                    f"Blockstates: {resources.get('blockstates',0)}",


                    f"Languages: {resources.get('languages',0)}",


                    f"Sounds: {resources.get('sounds',0)}",


                    f"JSON files: {resources.get('json_files',0)}",


                    f"Resource size: {resources.get('total_resource_size_mb',0)} MB"

                ]

            )
            # -------------------------
            # Registration
            # -------------------------

            file.write(
                "Registration:\n"
            )


            registration = mod.get(
                "registration",
                {}
            )


            write_block(
                file,
                [

                    f"Registered Blocks: {registration.get('Registered Blocks',0)}",

                    f"Registered Items: {registration.get('Registered Items',0)}",

                    f"Registered TileEntities: {registration.get('Registered TileEntities',0)}",

                    f"World Generators: {registration.get('World Generators',0)}",

                    f"Registered Entities: {registration.get('Registered Entities',0)}"

                ]
            )





            # -------------------------
            # Recipes
            # -------------------------

            file.write(
                "Recipes:\n"
            )


            recipes = mod.get(
                "recipe_data",
                {}
            )


            write_block(
                file,
                [

                    f"Total recipes: {recipes.get('total_recipes',0)}",

                    f"Crafting shaped: {recipes.get('crafting_shaped',0)}",

                    f"Crafting shapeless: {recipes.get('crafting_shapeless',0)}",

                    f"Smelting: {recipes.get('smelting',0)}",

                    f"Blasting: {recipes.get('blasting',0)}",

                    f"Smoking: {recipes.get('smoking',0)}",

                    f"Stonecutting: {recipes.get('stonecutting',0)}"

                ]
            )





            # -------------------------
            # GUI
            # -------------------------

            file.write(
                "GUI:\n"
            )


            gui = mod.get(
                "gui",
                {}
            )


            write_block(
                file,
                [

                    f"Screens: {gui.get('Screens',0)}",

                    f"Containers: {gui.get('Containers',0)}",

                    f"GuiHandler: {gui.get('GuiHandler',0)}"

                ]
            )





            # -------------------------
            # Network
            # -------------------------

            file.write(
                "Network:\n"
            )


            network = mod.get(
                "network",
                {}
            )


            write_block(
                file,
                [

                    f"Packets: {network.get('Packets',0)}",

                    f"Channels: {network.get('Channels',0)}"

                ]
            )





            # -------------------------
            # API usage
            # -------------------------

            file.write(
                "API usage:\n"
            )


            api = mod.get(
                "api_usage",
                {}
            )


            if api:


                write_block(

                    file,

                    [

                        f"{key}: {value}"

                        for key,value in api.items()

                    ]

                )


            else:


                write_block(

                    file,

                    [

                        "No API usage detected"

                    ]

                )





            # -------------------------
            # Important classes
            # -------------------------

            file.write(
                "Important classes:\n"
            )


            classes = mod.get(
                "important_classes",
                []
            )



            if classes:


                write_block(

                    file,

                    classes[:100]

                )


            else:


                write_block(

                    file,

                    [

                        "No important classes detected"

                    ]

                )
            # -------------------------
            # Migration problems
            # -------------------------

            file.write(
                "Migration problems:\n"
            )


            reasons = mod.get(
                "reasons",
                []
            )


            if reasons:


                write_block(

                    file,

                    reasons

                )


            else:


                write_block(

                    file,

                    [

                        "No critical problems detected"

                    ]

                )



            file.write(
                "\n"
            )



    log(
        "modlist.md created"
    )





# =====================================
# Migration report
# =====================================

def create_migration_report(mods):


    path = os.path.join(

        get_docs_path(),

        "migration_report.md"

    )


    log(
        "Creating migration_report.md..."
    )



    with open(

        path,

        "w",

        encoding="utf-8"

    ) as file:



        file.write(

            "# Migration Report\n\n"

        )



        difficult = 0

        normal = 0

        easy = 0



        for mod in mods:



            difficulty = mod.get(
                "difficulty"
            )



            if difficulty == "Hard":


                difficult += 1



            elif difficulty == "Normal":


                normal += 1



            else:


                easy += 1





        write_block(

            file,

            [

                f"Total mods: {len(mods)}",

                f"Hard: {difficult}",

                f"Normal: {normal}",

                f"Easy: {easy}"

            ]

        )





        for mod in mods:



            file.write(

                f"## {mod.get('name','Unknown')}\n\n"

            )



            write_block(

                file,

                [

                    f"Difficulty: {mod.get('difficulty')}",


                    f"Migration score: {mod.get('score')}/100",


                    f"File size: {mod.get('size_mb')} MB",


                    f"Dependencies: {len(mod.get('dependencies',[]))}",


                    f"Important classes: {len(mod.get('important_classes',[]))}"

                ]

            )







# =====================================
# Dependency report
# =====================================

def create_dependencies_report(mods):


    path = os.path.join(

        get_docs_path(),

        "dependencies.md"

    )



    log(
        "Creating dependencies.md..."
    )



    with open(

        path,

        "w",

        encoding="utf-8"

    ) as file:



        file.write(

            "# Mod Dependencies\n\n"

        )



        for mod in mods:



            file.write(

                f"## {mod.get('name')}\n\n"

            )



            deps = mod.get(
                "dependencies",
                []
            )



            if deps:


                write_block(

                    file,

                    deps

                )


            else:


                write_block(

                    file,

                    [

                        "No dependencies"

                    ]

                )





# =====================================
# Graph JSON
# =====================================

def create_graph_json(mods):


    path = os.path.join(

        get_docs_path(),

        "mod_graph.json"

    )


    log(
        "Creating mod_graph.json..."
    )


    graph = []



    for mod in mods:



        graph.append(

            {


                "name":

                mod.get(
                    "name"
                ),



                "modid":

                mod.get(
                    "modid"
                ),



                "score":

                mod.get(
                    "score"
                ),



                "difficulty":

                mod.get(
                    "difficulty"
                ),



                "size":

                mod.get(
                    "size_mb"
                ),



                "dependencies":

                mod.get(
                    "dependencies",
                    []
                ),



                "resources":

                mod.get(
                    "resources",
                    {}
                ),



                "recipes":

                mod.get(
                    "recipe_data",
                    {}
                )

            }

        )



    with open(

        path,

        "w",

        encoding="utf-8"

    ) as file:



        json.dump(

            graph,

            file,

            indent=4,

            ensure_ascii=False

        )
# =====================================
# Graph generator launcher
# =====================================

def run_graph_generator():


    folder = os.path.dirname(
        os.path.abspath(__file__)
    )



    script = find_latest_version(

        folder,

        "graph_generator"

    )



    if script is None:


        raise Exception(

            "Graph generator not found"

        )



    log(

        f"Selected graph generator: {os.path.basename(script)}"

    )



    module = load_module(

        script,

        "graph_generator"

    )



    if not hasattr(

        module,

        "main"

    ):


        raise Exception(

            "Graph generator does not contain main()"

        )



    log(
        "Starting graph generator..."
    )



    module.main()



    log(
        "Graph generator finished"
    )







# =====================================
# Main process
# =====================================

def main():


    documents_saved = False



    try:



        log(
            "ModScanner starting..."
        )



        scanners = load_scanners()



        mods_folder = ask_mod_folder()



        jars = find_jars(

            mods_folder

        )



        if not jars:


            raise Exception(

                "No JAR files found"

            )



        log(

            f"Found JAR files: {len(jars)}"

        )





        # -----------------------------
        # Surface
        # -----------------------------


        surface = surface_scan(

            jars

        )





        # -----------------------------
        # Class scanner
        # -----------------------------


        log(
            "Starting class scanner..."
        )


        class_result = scanners["class"].scan_mods(

            jars

        )



        log(
            "Class scanner complete"
        )





        # -----------------------------
        # Resource scanner
        # -----------------------------


        log(
            "Starting resource scanner..."
        )


        resource_result = scanners["resource"].scan_resources(

            jars

        )



        log(
            "Resource scanner complete"
        )





        # -----------------------------
        # Recipe scanner
        # -----------------------------


        log(
            "Starting recipe scanner..."
        )


        recipe_result = scanners["recipe"].scan_recipes(

            jars

        )



        log(
            "Recipe scanner complete"
        )





        # -----------------------------
        # Merge
        # -----------------------------


        mods = merge_scans(

            surface,

            class_result,

            resource_result,

            recipe_result

        )



        log(
            "Creating documents..."
        )



        create_modlist(

            mods

        )



        create_migration_report(

            mods

        )



        create_dependencies_report(

            mods

        )



        create_graph_json(

            mods

        )



        documents_saved = True



        log(
            "Documents created successfully"
        )





        # -----------------------------
        # Graph question
        # -----------------------------


        answer = input(

            "\nOpen graph generator? [Y/N]\n> "

        )



        if answer.lower() == "y":


            try:


                run_graph_generator()



            except Exception as error:


                raise Exception(

                    f"Graph generator error: {error}"

                )



        else:


            log(
                "Graph generation skipped"
            )






        print()

        print(
            "================================"
        )

        print(
            " ModScanner finished"
        )

        print(
            "================================"
        )



    except Exception as error:



        print()

        print(
            "================================"
        )

        print(
            " ModScanner ERROR"
        )

        print(
            "================================"
        )



        print(
            error
        )



        if not documents_saved:


            print(
                "No documents were saved."
            )



    finally:


        input(

            "\nPress Enter to exit..."

        )







if __name__ == "__main__":


    main()