import os
import re
import json
import zipfile
import subprocess
import importlib.util
from datetime import datetime


VERSION = "1"


# =====================================
# Logging
# =====================================

def log(message):
    print(f"[ModScanner v{VERSION}] {message}")



# =====================================
# Paths
# =====================================

def get_project_root():

    current = os.path.dirname(
        os.path.abspath(__file__)
    )

    return os.path.dirname(
        os.path.dirname(current)
    )



def get_docs_folder():

    root = get_project_root()

    docs = os.path.join(
        root,
        "docs"
    )

    os.makedirs(
        docs,
        exist_ok=True
    )

    return docs



# =====================================
# User input
# =====================================

def ask_mod_folder():

    print("==============================")
    print(" UltraTech ModScanner")
    print("==============================")
    print()

    folder = input(
        "Enter mods folder path:\n> "
    )

    return folder.strip().strip('"')



# =====================================
# Find JAR files
# =====================================

def find_jars(folder):

    if not os.path.exists(folder):

        raise Exception(
            "Mods folder does not exist"
        )


    jars = []


    for file in os.listdir(folder):

        if file.lower().endswith(".jar"):

            jars.append(
                os.path.join(
                    folder,
                    file
                )
            )


    return sorted(jars)



# =====================================
# Versioned script finder
# =====================================

def find_latest_script(folder, prefix):

    """
    Example:

    class_scannerv1.py
    class_scannerv2.py
    class_scannerv5.py

    Returns:

    class_scannerv5.py
    """

    scripts = []


    pattern = re.compile(
        "^" + re.escape(prefix) + r"v(\d+)\.py$"
    )


    for file in os.listdir(folder):

        match = pattern.match(
            file
        )


        if match:

            version = int(
                match.group(1)
            )

            scripts.append(
                (
                    version,
                    file
                )
            )



    if not scripts:

        return None



    scripts.sort(
        key=lambda x: x[0],
        reverse=True
    )


    return os.path.join(
        folder,
        scripts[0][1]
    )



# =====================================
# External script execution
# =====================================

def run_python_script(path, args=None):

    if not os.path.exists(path):

        raise Exception(
            f"Script not found: {path}"
        )


    command = [
        "py",
        path
    ]


    if args:

        command.extend(
            args
        )


    log(
        f"Starting {os.path.basename(path)}"
    )


    try:

        result = subprocess.run(
            command,
            capture_output=True,
            text=True
        )


    except Exception as error:

        raise Exception(
            f"Cannot start script: {error}"
        )



    if result.stdout:

        print(
            result.stdout
        )


    if result.returncode != 0:

        raise Exception(
            result.stderr
            or
            "External script failed"
        )


    log(
        f"{os.path.basename(path)} finished"
    )



# =====================================
# Helpers
# =====================================

def unique(items):

    result = []

    for item in items:

        if item not in result:

            result.append(
                item
            )

    return result



def safe_json_load(data):

    try:

        return json.loads(
            data
        )

    except:

        return {}



# =====================================
# Surface scan preparation
# =====================================

def create_mod_template():

    return {

        "file": "",

        "name": "Unknown",

        "modid": "Unknown",

        "version": "Unknown",

        "author": "Unknown",

        "description": "",

        "dependencies": [],

        "coremod": False,

        "core_plugin": "",

        "mcmod_found": False,

        "size_mb": 0,

        "files": 0,

        "class_files": 0,

        "packages": 0,

        "difficulty": "Unknown",

        "score": 0,

        "detections": [],

        "migration_status": "Unknown",

        "migration_notes": []

    }
# =====================================
# Surface JAR analysis
# =====================================

def scan_manifest(jar, mod):

    if "META-INF/MANIFEST.MF" not in jar.namelist():

        return


    try:

        manifest = jar.read(
            "META-INF/MANIFEST.MF"
        ).decode(
            "utf-8",
            errors="ignore"
        )


        mod["core_plugin"] = manifest


        if (
            "FMLCorePlugin" in manifest
            or
            "CorePlugin" in manifest
        ):

            mod["coremod"] = True

            mod["migration_notes"].append(
                "CoreMod detected. Manual migration required."
            )


        dependency_patterns = [

            r"required-after:([^;]+)",
            r"required-before:([^;]+)",
            r"after:([^;]+)",
            r"before:([^;]+)"

        ]


        for pattern in dependency_patterns:

            found = re.findall(
                pattern,
                manifest
            )


            for item in found:

                mod["dependencies"].append(
                    item.strip()
                )



    except Exception as error:

        mod["migration_notes"].append(
            f"Manifest error: {error}"
        )




def scan_mcmod(jar, mod):

    if "mcmod.info" not in jar.namelist():

        mod["migration_notes"].append(
            "mcmod.info not found."
        )

        return



    try:

        mod["mcmod_found"] = True


        data = jar.read(
            "mcmod.info"
        ).decode(
            "utf-8",
            errors="ignore"
        )


        info = safe_json_load(
            data
        )


        if isinstance(info, list):

            info = info[0]


        elif isinstance(info, dict):

            if "modList" in info:

                info = info["modList"][0]



        if not isinstance(info, dict):

            return



        mod["name"] = info.get(
            "name",
            "Unknown"
        )


        mod["modid"] = info.get(
            "modid",
            "Unknown"
        )


        mod["version"] = info.get(
            "version",
            "Unknown"
        )


        mod["description"] = info.get(
            "description",
            ""
        )


        author = info.get(
            "authorList",
            "Unknown"
        )


        if isinstance(author, list):

            author = ", ".join(
                author
            )


        mod["author"] = author



        required = info.get(
            "requiredMods",
            []
        )


        if isinstance(required, list):

            mod["dependencies"].extend(
                required
            )



    except Exception as error:

        mod["migration_notes"].append(
            f"mcmod.info error: {error}"
        )





def surface_scan_jar(path, index, total):

    name = os.path.basename(
        path
    )


    log(
        f"[Surface {index}/{total}] {name}"
    )


    mod = create_mod_template()


    mod["file"] = name


    try:

        mod["size_mb"] = round(
            os.path.getsize(path)
            /
            (1024 * 1024),
            2
        )


        with zipfile.ZipFile(
            path,
            "r"
        ) as jar:


            mod["files"] = len(
                jar.namelist()
            )


            mod["class_files"] = len(
                [
                    x
                    for x in jar.namelist()
                    if x.endswith(".class")
                ]
            )


            scan_manifest(
                jar,
                mod
            )


            scan_mcmod(
                jar,
                mod
            )



    except Exception as error:

        mod["migration_notes"].append(
            f"JAR read error: {error}"
        )



    mod["dependencies"] = unique(
        mod["dependencies"]
    )


    return mod






def surface_scan(jars):

    log(
        "Starting surface scan..."
    )


    result = []


    total = len(
        jars
    )


    for index, jar in enumerate(
        jars,
        1
    ):


        result.append(
            surface_scan_jar(
                jar,
                index,
                total
            )
        )



    log(
        "Surface scan completed"
    )


    return result





# =====================================
# Class scanner integration
# =====================================

def run_class_scanner(mod_folder):

    tools = os.path.dirname(
        os.path.abspath(__file__)
    )


    scanner = find_latest_script(
        tools,
        "class_scanner"
    )


    if scanner is None:

        raise Exception(
            "No class_scanner version found"
        )


    log(
        f"Selected class scanner: {os.path.basename(scanner)}"
    )


    module_name = (
        "class_scanner_module"
    )


    spec = importlib.util.spec_from_file_location(
        module_name,
        scanner
    )


    if spec is None:

        raise Exception(
            "Cannot load class scanner"
        )


    module = importlib.util.module_from_spec(
        spec
    )


    try:

        spec.loader.exec_module(
            module
        )


    except Exception as error:

        raise Exception(
            f"Class scanner loading error: {error}"
        )


    if not hasattr(
        module,
        "scan_mods"
    ):

        raise Exception(
            "class_scanner has no scan_mods function"
        )


    jars = find_jars(
        mod_folder
    )


    return module.scan_mods(
        jars
    )
# =====================================
# Merge scanner results
# =====================================

def merge_results(surface, classes):

    log(
        "Combining scan results..."
    )


    for mod in surface:

        file = mod["file"]


        class_data = classes.get(
            file,
            {}
        )


        if class_data:


            mod["size_mb"] = class_data.get(
                "size_mb",
                mod["size_mb"]
            )


            mod["files"] = class_data.get(
                "files",
                mod["files"]
            )


            mod["class_files"] = class_data.get(
                "class_files",
                mod["class_files"]
            )


            mod["packages"] = class_data.get(
                "packages",
                0
            )


            mod["score"] = class_data.get(
                "score",
                0
            )


            mod["detections"] = class_data.get(
                "detections",
                []
            )


            difficulty = class_data.get(
                "difficulty",
                "Unknown"
            )


            mod["difficulty"] = difficulty



            if difficulty == "Hard":

                mod["migration_status"] = (
                    "Requires rewrite"
                )


            elif difficulty == "Normal":

                mod["migration_status"] = (
                    "Needs adaptation"
                )


            elif difficulty == "Easy":

                mod["migration_status"] = (
                    "Simple migration"
                )



        else:

            mod["migration_notes"].append(
                "Class scanner data not found."
            )



        if mod["coremod"]:

            mod["difficulty"] = "Hard"

            mod["migration_status"] = (
                "Requires rewrite"
            )


            mod["score"] = max(
                mod["score"],
                80
            )


    return surface






# =====================================
# Documentation generation
# =====================================

def save_modlist(mods, path):

    with open(
        path,
        "w",
        encoding="utf-8"
    ) as file:


        file.write(
            "# UltraTech Mod List\n\n"
        )


        file.write(
            f"Generated: {datetime.now()}\n\n"
        )


        file.write(
            f"Total mods: {len(mods)}\n\n"
        )



        for mod in mods:


            file.write(
                f"## {mod['name']}\n"
            )


            file.write(
                f"File: {mod['file']}\n"
            )


            file.write(
                f"Mod ID: {mod['modid']}\n"
            )


            file.write(
                f"Version: {mod['version']}\n"
            )


            file.write(
                f"Author: {mod['author']}\n"
            )


            file.write(
                f"Size: {mod['size_mb']} MB\n"
            )


            file.write(
                f"Files: {mod['files']}\n"
            )


            file.write(
                f"Class files: {mod['class_files']}\n"
            )


            file.write(
                f"Packages: {mod['packages']}\n"
            )


            file.write(
                f"Difficulty: {mod['difficulty']}\n"
            )


            file.write(
                f"Migration score: {mod['score']}/100\n"
            )


            file.write(
                f"Migration status: {mod['migration_status']}\n"
            )



            if mod["dependencies"]:

                file.write(
                    "Dependencies: "
                    +
                    ", ".join(
                        mod["dependencies"]
                    )
                    +
                    "\n"
                )


            if mod["detections"]:

                file.write(
                    "Detected systems:\n"
                )


                for item in mod["detections"]:

                    file.write(
                        f"- {item}\n"
                    )



            if mod["migration_notes"]:

                file.write(
                    "Notes:\n"
                )


                for note in mod["migration_notes"]:

                    file.write(
                        f"- {note}\n"
                    )



            file.write(
                "\n---\n\n"
            )







def save_dependencies(mods, path):

    with open(
        path,
        "w",
        encoding="utf-8"
    ) as file:


        file.write(
            "# Dependency Report\n\n"
        )



        for mod in mods:


            file.write(
                f"## {mod['name']}\n"
            )


            if mod["dependencies"]:


                for dep in mod["dependencies"]:

                    file.write(
                        f"- {dep}\n"
                    )

            else:

                file.write(
                    "No dependencies detected\n"
                )


            file.write(
                "\n"
            )






def save_migration_report(mods, path):

    with open(
        path,
        "w",
        encoding="utf-8"
    ) as file:


        file.write(
            "# Migration Report\n\n"
        )


        for mod in mods:


            file.write(
                f"## {mod['name']}\n\n"
            )


            file.write(
                f"Difficulty: {mod['difficulty']}\n"
            )


            file.write(
                f"Score: {mod['score']}/100\n"
            )


            file.write(
                f"Status: {mod['migration_status']}\n\n"
            )



            if mod["detections"]:

                file.write(
                    "Detected:\n"
                )


                for item in mod["detections"]:

                    file.write(
                        f"- {item}\n"
                    )


            if mod["migration_notes"]:

                file.write(
                    "\nNotes:\n"
                )


                for note in mod["migration_notes"]:

                    file.write(
                        f"- {note}\n"
                    )


            file.write(
                "\n---\n\n"
            )






def save_graph_data(mods, path):

    with open(
        path,
        "w",
        encoding="utf-8"
    ) as file:


        json.dump(
            mods,
            file,
            indent=4,
            ensure_ascii=False
        )
# =====================================
# Graph generator integration
# =====================================

def run_graph_generator():

    tools = os.path.dirname(
        os.path.abspath(__file__)
    )


    generator = find_latest_script(
        tools,
        "graph_generator"
    )


    if generator is None:

        raise Exception(
            "No graph_generator version found"
        )


    log(
        f"Selected graph generator: {os.path.basename(generator)}"
    )


    try:

        run_python_script(
            generator
        )


    except Exception as error:

        raise Exception(
            f"Graph generator error: {error}"
        )





# =====================================
# Main
# =====================================

def main():

    try:


        mods_folder = ask_mod_folder()



        log(
            "Searching mods..."
        )


        jars = find_jars(
            mods_folder
        )


        if not jars:

            raise Exception(
                "No jar files found"
            )



        log(
            f"Found JAR files: {len(jars)}"
        )



        # -----------------------------
        # Surface analysis
        # -----------------------------

        surface = surface_scan(
            jars
        )



        # -----------------------------
        # Class scanner
        # -----------------------------

        class_results = run_class_scanner(
            mods_folder
        )



        # -----------------------------
        # Merge
        # -----------------------------

        mods = merge_results(
            surface,
            class_results
        )



        # -----------------------------
        # Save documents
        # -----------------------------

        docs = get_docs_folder()


        log(
            "Saving documents..."
        )


        save_modlist(
            mods,
            os.path.join(
                docs,
                "modlist.md"
            )
        )


        save_dependencies(
            mods,
            os.path.join(
                docs,
                "dependencies.md"
            )
        )


        save_migration_report(
            mods,
            os.path.join(
                docs,
                "migration_report.md"
            )
        )


        save_graph_data(
            mods,
            os.path.join(
                docs,
                "mod_graph.json"
            )
        )



        log(
            "Documents successfully saved."
        )



        # -----------------------------
        # Graph question
        # -----------------------------

        answer = input(
            "\nOpen graph generator? [Y/N]\n> "
        )


        if answer.lower() == "y":


            run_graph_generator()



        else:

            log(
                "Graph generation skipped."
            )



        print()

        print(
            "=============================="
        )

        print(
            " All tasks completed."
        )

        print(
            "=============================="
        )


        input(
            "Press Enter to exit..."
        )



    except Exception as error:


        print()

        print(
            "=============================="
        )

        print(
            " ModScanner ERROR"
        )

        print(
            "=============================="
        )


        print(
            error
        )


        print()


        print(
            "No documents were saved."
        )


        input(
            "Press Enter to exit..."
        )






if __name__ == "__main__":

    main()