import os
import re
import json
import zipfile
import importlib.util
from datetime import datetime


VERSION = "7"


# =====================================
# Logger integration
# =====================================

try:

    from logger import logger


except Exception as error:


    print(
        "Logger import failed:",
        error
    )


    class DummyLogger:


        def info(self, text):
            print("[INFO]", text)


        def debug(self, text):
            print("[DEBUG]", text)


        def success(self, text):
            print("[SUCCESS]", text)


        def warning(self, text):
            print("[WARNING]", text)


        def error(self, text):
            print("[ERROR]", text)


        def exception(self, error):
            print("[EXCEPTION]", error)


        def start_timer(self, name):
            pass


        def stop_timer(self, name):
            return 0


        def finish(self):
            pass


        def section(self, text):
            print("=" * 80)
            print(text)
            print("=" * 80)
        

        def banner(self, text):
            print("=" * 80)
            print(text)
            print("=" * 80)


        def module_info(self, module, text):
            self.info(
                f"[{module}] {text}"
            )


        def module_debug(self, module, text):
            self.debug(
                f"[{module}] {text}"
            )


        def module_success(self, module, text):
            self.success(
                f"[{module}] {text}"
            )


        def module_error(self, module, text):
            self.error(
                f"[{module}] {text}"
            )


        def log_dependency(self, mod, dep):
            self.debug(
                f"Dependency: {mod} -> {dep}"
            )


        def log_mod_start(self, mod):
            self.info(
                f"Starting mod: {mod}"
            )


        def log_mod_finish(self, mod):
            self.success(
                f"Finished mod: {mod}"
            )


        def log_exception_context(
                self,
                module,
                stage,
                error
        ):
            self.error(
                f"{module} failed at {stage}: {error}"
            )



    logger = DummyLogger()





MODULE_NAME = "ModScanner"





# =====================================
# Logging compatibility
# =====================================

def log(text):

    logger.module_info(
        MODULE_NAME,
        text
    )





def log_debug(text):

    logger.module_debug(
        MODULE_NAME,
        text
    )





def log_success(text):

    logger.module_success(
        MODULE_NAME,
        text
    )





def log_error(text):

    logger.module_error(
        MODULE_NAME,
        text
    )







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

            "Mods folder does not exist: "

            +

            path

        )



    log(

        f"Mods folder selected: {path}"

    )



    return path







# =====================================
# JAR finder
# =====================================

def find_jars(folder):


    logger.start_timer(

        "find_jars"

    )



    jars = []



    for root, dirs, files in os.walk(folder):


        for file in files:


            if file.lower().endswith(".jar"):


                jars.append(

                    os.path.join(

                        root,

                        file

                    )

                )



    logger.stop_timer(

        "find_jars"

    )



    log(

        f"Found JAR files: {len(jars)}"

    )



    return jars







# =====================================
# Version selector
# =====================================

def find_latest_version(folder, prefix):


    versions = []



    if not os.path.isdir(folder):


        return None



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
# Safe module loader
# =====================================

def load_module(script, module_name):


    if not os.path.isfile(script):


        raise Exception(

            f"Module not found: {script}"

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



        log_success(

            f"Loaded module: {os.path.basename(script)}"

        )



        return module



    except Exception as error:


        logger.exception(

            error

        )


        raise Exception(

            f"Cannot load {script}: {error}"

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



    functions = {


        "class":

        "scan_mods",



        "resource":

        "scan_resources",



        "recipe":

        "scan_recipes"

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



        if not hasattr(

            module,

            functions[name]

        ):


            raise Exception(

                f"{os.path.basename(script)} missing {functions[name]}()"

            )



        scanners[name] = module



    log_success(

        "All scanners loaded"

    )



    return scanners
# =====================================
# mcmod.info reader
# =====================================

def read_mcmod_info(jar_path):


    filename = os.path.splitext(

        os.path.basename(jar_path)

    )[0]



    result = {


        "name":

        filename,



        "modid":

        filename,



        "version":

        "Unknown",



        "author":

        "Unknown"

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

                        rf'"{key}"\s*:\s*"([^"]+)"',

                        text

                    )



                    if match:


                        result[key] = match.group(1)



                log_debug(

                    f"mcmod.info found: {filename}"

                )


                break



    except Exception as error:


        logger.log_exception_context(

            MODULE_NAME,

            "read_mcmod_info",

            error

        )



    return result







# =====================================
# MANIFEST reader
# =====================================

def read_manifest(jar_path):


    result = {


        "dependencies":

        [],



        "forge_version":

        "Unknown"

    }



    try:



        with zipfile.ZipFile(

            jar_path,

            "r"

        ) as jar:



            for file in jar.namelist():



                if not file.upper().endswith(

                    "MANIFEST.MF"

                ):

                    continue



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



    except Exception as error:


        log_debug(

            f"Manifest read error: {error}"

        )



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



                blocks = re.findall(

                    r'"dependencies"\s*:\s*\[(.*?)\]',

                    text,

                    re.DOTALL

                )



                for block in blocks:



                    dependencies.extend(

                        re.findall(

                            r'"([^"]+)"',

                            block

                        )

                    )





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



        logger.log_exception_context(

            MODULE_NAME,

            "scan_dependencies",

            error

        )




    result = []



    for dependency in dependencies:



        dependency = dependency.strip()



        if not dependency:

            continue



        if dependency.lower() in (

            "forge",

            "minecraft"

        ):

            continue



        if dependency not in result:



            result.append(

                dependency

            )


            logger.log_dependency(

                os.path.basename(jar_path),

                dependency

            )



    return result







# =====================================
# Surface scan
# =====================================

def surface_scan(jars):


    logger.section(

        "Surface Scan"

    )


    logger.start_timer(

        "surface_scan"

    )



    result = {}



    total = len(jars)



    for index, jar in enumerate(

        jars,

        1

    ):



        filename = os.path.basename(

            jar

        )



        try:



            logger.log_mod_start(

                filename

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

                    (1024 * 1024),

                    2

                ),



                "forge":

                manifest["forge_version"],



                "dependencies":

                dependencies



            }



            log(

                f"Surface [{index}/{total}] {filename}"

            )



        except Exception as error:



            logger.log_exception_context(

                MODULE_NAME,

                f"surface_scan {filename}",

                error

            )



        finally:



            logger.log_mod_finish(

                filename

            )



    logger.stop_timer(

        "surface_scan"

    )



    log_success(

        "Surface scan complete"

    )



    return result
# =====================================
# Merge scanner results
# =====================================

def merge_scans(
        surface,
        class_data,
        resource_data,
        recipe_data
):


    logger.section(
        "Merging scanner results"
    )


    logger.start_timer(
        "merge_scans"
    )


    mods = []


    for filename, base in surface.items():


        try:


            logger.log_mod_start(
                filename
            )


            data = base.copy()



            # -------------------------
            # Class scanner
            # -------------------------

            if filename in class_data:


                logger.module_debug(
                    "ModScanner",
                    f"Merging class data: {filename}"
                )


                data.update(

                    class_data[filename]

                )


            else:


                logger.warning(
                    f"No class data for {filename}"
                )





            # -------------------------
            # Resource scanner
            # -------------------------

            if filename in resource_data:


                data["resources"] = (

                    resource_data[filename]

                )


            else:


                data["resources"] = {}

                logger.debug(
                    f"No resources found: {filename}"
                )





            # -------------------------
            # Recipe scanner
            # -------------------------

            if filename in recipe_data:


                data["recipe_data"] = (

                    recipe_data[filename]

                )


            else:


                data["recipe_data"] = {}

                logger.debug(
                    f"No recipe data found: {filename}"
                )






            # -------------------------
            # Compatibility defaults
            # -------------------------

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
                [],



                "minecraft_usage":
                {},



                "client_percent":
                0,



                "server_percent":
                0,



                "estimated_methods":
                0



            }



            for key, value in defaults.items():


                if key not in data:


                    data[key] = value






            mods.append(

                data

            )



            logger.log_mod_finish(
                filename
            )



        except Exception as error:


            logger.log_exception_context(

                MODULE_NAME,

                f"merge_scans {filename}",

                error

            )



    logger.stop_timer(
        "merge_scans"
    )


    log_success(
        f"Merged mods: {len(mods)}"
    )


    return mods







# =====================================
# Markdown helper
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
# Create modlist report
# =====================================

def create_modlist(mods):


    path = os.path.join(

        get_docs_path(),

        "modlist.md"

    )



    logger.section(

        "Creating modlist.md"

    )



    logger.start_timer(

        "create_modlist"

    )



    try:


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



                logger.debug(

                    f"Writing mod entry: {mod.get('name')}"

                )



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


                        f"Category: {', '.join(mod.get('type',['Unknown']))}",


                        f"Difficulty: {mod.get('difficulty')}",


                        f"Score: {mod.get('score')}/100"


                    ]

                )



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

                        f"Size: {resources.get('total_resource_size_mb',0)} MB"

                    ]

                )



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



                file.write(

                    "\n"

                )





        logger.log_file(

            path,

            "Created report"

        )



        log_success(

            "modlist.md created"

        )



    except Exception as error:


        logger.log_exception_context(

            MODULE_NAME,

            "create_modlist",

            error

        )



    finally:


        logger.stop_timer(

            "create_modlist"

        )
# =====================================
# Migration report
# =====================================

def create_migration_report(mods):


    path = os.path.join(

        get_docs_path(),

        "migration_report.md"

    )


    logger.section(

        "Creating migration_report.md"

    )


    logger.start_timer(

        "migration_report"

    )


    try:


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

                    "difficulty",

                    "Unknown"

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


                        f"Classes: {len(mod.get('important_classes',[]))}",


                        f"Estimated methods: {mod.get('estimated_methods',0)}"

                    ]

                )



                reasons = mod.get(

                    "reasons",

                    []

                )



                file.write(

                    "Problems:\n"

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

                            "No problems detected"

                        ]

                    )



        logger.log_file(

            path,

            "Migration report"

        )


        log_success(

            "migration_report.md created"

        )



    except Exception as error:


        logger.log_exception_context(

            MODULE_NAME,

            "create_migration_report",

            error

        )


    finally:


        logger.stop_timer(

            "migration_report"

        )









# =====================================
# Dependency report
# =====================================

def create_dependencies_report(mods):


    path = os.path.join(

        get_docs_path(),

        "dependencies.md"

    )


    logger.section(

        "Creating dependencies.md"

    )


    logger.start_timer(

        "dependencies_report"

    )



    try:


        with open(

            path,

            "w",

            encoding="utf-8"

        ) as file:



            file.write(

                "# Mod Dependencies\n\n"

            )



            for mod in mods:



                name = mod.get(

                    "name",

                    "Unknown"

                )



                file.write(

                    f"## {name}\n\n"

                )



                dependencies = mod.get(

                    "dependencies",

                    []

                )



                if dependencies:



                    write_block(

                        file,

                        dependencies

                    )


                else:


                    write_block(

                        file,

                        [

                            "No dependencies"

                        ]

                    )



        logger.log_file(

            path,

            "Dependencies report"

        )


        log_success(

            "dependencies.md created"

        )



    except Exception as error:


        logger.log_exception_context(

            MODULE_NAME,

            "create_dependencies_report",

            error

        )


    finally:


        logger.stop_timer(

            "dependencies_report"

        )









# =====================================
# Graph JSON
# =====================================

def create_graph_json(mods):


    path = os.path.join(

        get_docs_path(),

        "mod_graph.json"

    )


    logger.section(

        "Creating mod_graph.json"

    )


    logger.start_timer(

        "graph_json"

    )



    graph = []



    try:


        for mod in mods:



            graph.append(

                {


                    "name":

                    mod.get(

                        "name",

                        "Unknown"

                    ),



                    "modid":

                    mod.get(

                        "modid",

                        ""

                    ),



                    "score":

                    mod.get(

                        "score",

                        0

                    ),



                    "difficulty":

                    mod.get(

                        "difficulty",

                        "Unknown"

                    ),



                    "type":

                    mod.get(

                        "type",

                        []

                    ),



                    "size":

                    mod.get(

                        "size_mb",

                        0

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

                    ),



                    "content":

                    mod.get(

                        "content",

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



        logger.log_file(

            path,

            "Graph JSON"

        )


        log_success(

            "mod_graph.json created"

        )



    except Exception as error:


        logger.log_exception_context(

            MODULE_NAME,

            "create_graph_json",

            error

        )


    finally:


        logger.stop_timer(

            "graph_json"

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



    logger.module_info(

        MODULE_NAME,

        f"Selected graph generator: {os.path.basename(script)}"

    )



    try:


        module = load_module(

            script,

            "graph_generator"

        )



        if not hasattr(

            module,

            "main"

        ):


            raise Exception(

                "Graph generator missing main()"

            )



        logger.start_timer(

            "graph_generator"

        )



        module.main()



        logger.stop_timer(

            "graph_generator"

        )



        logger.success(

            "Graph generator finished"

        )



    except Exception as error:


        logger.log_exception_context(

            MODULE_NAME,

            "run_graph_generator",

            error

        )


        raise







# =====================================
# Scanner execution
# =====================================

def run_scanners(
        scanners,
        jars
):


    logger.section(

        "Running scanners"

    )


    results = {}



    try:


        logger.start_timer(

            "class_scanner"

        )


        logger.info(

            "Starting ClassScanner"

        )


        results["class"] = scanners["class"].scan_mods(

            jars

        )


        logger.stop_timer(

            "class_scanner"

        )


        logger.success(

            "ClassScanner finished"

        )





        logger.start_timer(

            "resource_scanner"

        )


        logger.info(

            "Starting ResourceScanner"

        )


        results["resource"] = scanners["resource"].scan_resources(

            jars

        )


        logger.stop_timer(

            "resource_scanner"

        )


        logger.success(

            "ResourceScanner finished"

        )





        logger.start_timer(

            "recipe_scanner"

        )


        logger.info(

            "Starting RecipeScanner"

        )


        results["recipe"] = scanners["recipe"].scan_recipes(

            jars

        )


        logger.stop_timer(

            "recipe_scanner"

        )


        logger.success(

            "RecipeScanner finished"

        )



    except Exception as error:


        logger.log_exception_context(

            MODULE_NAME,

            "run_scanners",

            error

        )


        raise



    return results







# =====================================
# Main process
# =====================================

def main():


    documents_saved = False



    try:



        logger.banner(

            "ModScanner v7 starting"

        )


        logger.log_system_info()



        scanners = load_scanners()



        mods_folder = ask_mod_folder()



        jars = find_jars(

            mods_folder

        )



        if not jars:


            raise Exception(

                "No JAR files found"

            )



        logger.info(

            f"JAR count: {len(jars)}"

        )





        # -----------------------------
        # Surface analysis
        # -----------------------------


        surface = surface_scan(

            jars

        )





        # -----------------------------
        # Other scanners
        # -----------------------------


        scan_results = run_scanners(

            scanners,

            jars

        )





        # -----------------------------
        # Merge
        # -----------------------------


        mods = merge_scans(

            surface,

            scan_results["class"],

            scan_results["resource"],

            scan_results["recipe"]

        )



        logger.info(

            f"Final mods analyzed: {len(mods)}"

        )





        # -----------------------------
        # Documentation
        # -----------------------------


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



        logger.success(

            "All documents generated"

        )





        # -----------------------------
        # Graph
        # -----------------------------


        answer = input(

            "\nOpen graph generator? [Y/N]\n> "

        )



        if answer.lower() == "y":


            run_graph_generator()



        else:


            logger.info(

                "Graph generator skipped"

            )





        logger.banner(

            "ModScanner finished successfully"

        )



    except Exception as error:



        logger.log_exception_context(

            MODULE_NAME,

            "main",

            error

        )



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



        logger.finish()



        input(

            "\nPress Enter to exit..."

        )







# =====================================
# Entry point
# =====================================

if __name__ == "__main__":


    main()
