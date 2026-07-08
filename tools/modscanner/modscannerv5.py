import os
import re
import json
import zipfile
import importlib.util
from datetime import datetime


VERSION = "2"



# =====================================
# Logging
# =====================================

def log(message):

    print(
        f"[ModScanner v{VERSION}] {message}"
    )



# =====================================
# Project paths
# =====================================

def get_project_root():

    current = os.path.dirname(
        os.path.abspath(__file__)
    )

    return os.path.dirname(
        os.path.dirname(current)
    )



def get_docs_folder():

    docs = os.path.join(
        get_project_root(),
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
    print(" UltraTech ModScanner v2")
    print("==============================")
    print()


    path = input(
        "Enter mods folder path:\n> "
    )


    return path.strip().strip('"')





# =====================================
# JAR searching
# =====================================

def find_jars(folder):

    if not os.path.exists(folder):

        raise Exception(
            "Mods folder does not exist"
        )


    jars = []


    for file in os.listdir(folder):

        if file.lower().endswith(
            ".jar"
        ):

            jars.append(
                os.path.join(
                    folder,
                    file
                )
            )



    return sorted(
        jars
    )





# =====================================
# Versioned file search
# =====================================

def find_latest_script(folder, prefix):

    """
    Finds:

    prefixv1.py
    prefixv2.py
    prefixv10.py

    Returns newest version.
    """

    files = []


    pattern = re.compile(
        "^"
        +
        re.escape(prefix)
        +
        r"v(\d+)\.py$"
    )



    for file in os.listdir(folder):


        match = pattern.match(
            file
        )


        if match:


            version = int(
                match.group(1)
            )


            files.append(
                (
                    version,
                    file
                )
            )



    if not files:

        return None



    files.sort(
        key=lambda x: x[0],
        reverse=True
    )



    return os.path.join(
        folder,
        files[0][1]
    )





# =====================================
# Helpers
# =====================================

def unique(values):

    result = []


    for value in values:

        if value not in result:

            result.append(
                value
            )


    return result




def safe_json_load(text):

    try:

        return json.loads(
            text
        )

    except:

        return {}





# =====================================
# Default mod structure
# =====================================

def create_mod():

    return {


        "file": "",


        "name": "Unknown",


        "modid": "Unknown",


        "version": "Unknown",


        "author": "Unknown",


        "description": "",


        "dependencies": [],



        "size_mb": 0,


        "files": 0,


        "class_files": 0,


        "packages": 0,



        "coremod": False,



        "difficulty": "Unknown",


        "score": 0,


        "migration_status": "Unknown",



        "detections": [],


        "reasons": [],


        "migration_notes": [],



        "api_usage": {},


        "content": {},


        "minecraft_usage": {},



        "client_percent": 0,


        "server_percent": 0,


        "estimated_methods": 0

    }
# =====================================
# Manifest analysis
# =====================================

def scan_manifest(jar, mod):

    if "META-INF/MANIFEST.MF" not in jar.namelist():

        return



    try:

        data = jar.read(
            "META-INF/MANIFEST.MF"
        ).decode(
            "utf-8",
            errors="ignore"
        )



        if (
            "FMLCorePlugin" in data
            or
            "IFMLLoadingPlugin" in data
        ):

            mod["coremod"] = True


            mod["detections"].append(
                "CoreMod / ASM detected"
            )


            mod["migration_notes"].append(
                "ASM/CoreMod requires manual migration"
            )



        dependency_patterns = [

            r"required-after:([^;]+)",

            r"required-before:([^;]+)",

            r"after:([^;]+)"

        ]



        for pattern in dependency_patterns:


            found = re.findall(
                pattern,
                data
            )


            for item in found:

                mod["dependencies"].append(
                    item.strip()
                )



    except Exception as error:


        mod["migration_notes"].append(
            f"Manifest error: {error}"
        )







# =====================================
# mcmod.info analysis
# =====================================

def scan_mcmod(jar, mod):

    if "mcmod.info" not in jar.namelist():

        mod["migration_notes"].append(
            "mcmod.info not found"
        )

        return



    try:


        raw = jar.read(
            "mcmod.info"
        ).decode(
            "utf-8",
            errors="ignore"
        )



        info = safe_json_load(
            raw
        )



        if isinstance(info, list):

            info = info[0]



        if (
            isinstance(info, dict)
            and
            "modList" in info
        ):

            info = info["modList"][0]



        if not isinstance(
            info,
            dict
        ):

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



        if isinstance(
            author,
            list
        ):

            author = ", ".join(
                author
            )



        mod["author"] = author



        required = info.get(
            "requiredMods",
            []
        )



        if isinstance(
            required,
            list
        ):

            mod["dependencies"].extend(
                required
            )



    except Exception as error:


        mod["migration_notes"].append(
            f"mcmod.info error: {error}"
        )







# =====================================
# Surface JAR scanner
# =====================================

def scan_jar(path, index, total):


    filename = os.path.basename(
        path
    )


    log(
        f"[Surface {index}/{total}] {filename}"
    )



    mod = create_mod()


    mod["file"] = filename



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



            files = jar.namelist()



            mod["files"] = len(
                files
            )



            mod["class_files"] = len(
                [
                    x
                    for x in files
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
            scan_jar(
                jar,
                index,
                total
            )
        )



    log(
        "Surface scan complete"
    )



    return result
# =====================================
# Class scanner integration
# =====================================

def run_class_scanner(jars):

    tools = os.path.dirname(
        os.path.abspath(__file__)
    )


    scanner = find_latest_script(
        tools,
        "class_scanner"
    )



    if scanner is None:

        raise Exception(
            "class_scanner not found"
        )



    log(
        f"Selected class scanner: {os.path.basename(scanner)}"
    )



    spec = importlib.util.spec_from_file_location(
        "class_scanner_module",
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
            f"class_scanner loading error: {error}"
        )



    if not hasattr(
        module,
        "scan_mods"
    ):

        raise Exception(
            "class_scanner has no scan_mods()"
        )



    return module.scan_mods(
        jars
    )







# =====================================
# Result merge
# =====================================

def merge_results(
        surface,
        class_data
):


    log(
        "Merging analysis results..."
    )



    for mod in surface:



        data = class_data.get(
            mod["file"],
            {}
        )



        if not data:


            mod["migration_notes"].append(
                "No class scanner data"
            )


            continue





        # Size data


        mod["packages"] = data.get(
            "packages",
            0
        )


        mod["score"] = data.get(
            "score",
            0
        )



        mod["difficulty"] = data.get(
            "difficulty",
            "Unknown"
        )



        mod["detections"].extend(
            data.get(
                "detections",
                []
            )
        )



        mod["reasons"].extend(
            data.get(
                "reasons",
                []
            )
        )



        # New analysis fields


        mod["api_usage"] = data.get(
            "api_usage",
            {}
        )


        mod["content"] = data.get(
            "content",
            {}
        )


        mod["minecraft_usage"] = data.get(
            "minecraft_usage",
            {}
        )


        mod["client_percent"] = data.get(
            "client_percent",
            0
        )


        mod["server_percent"] = data.get(
            "server_percent",
            0
        )


        mod["estimated_methods"] = data.get(
            "estimated_methods",
            0
        )





        # Migration status


        if mod["difficulty"] == "Hard":


            mod["migration_status"] = (
                "Requires rewrite"
            )



        elif mod["difficulty"] == "Normal":


            mod["migration_status"] = (
                "Needs adaptation"
            )



        elif mod["difficulty"] == "Easy":


            mod["migration_status"] = (
                "Simple migration"
            )



        else:


            mod["migration_status"] = (
                "Unknown"
            )





        mod["detections"] = unique(
            mod["detections"]
        )


        mod["reasons"] = unique(
            mod["reasons"]
        )



    return surface







# =====================================
# Markdown block formatter
# =====================================

def write_text_block(file, lines):


    file.write(
        "```text\n"
    )


    for line in lines:

        file.write(
            line
            +
            "\n"
        )


    file.write(
        "```\n\n"
    )







# =====================================
# Mod formatting
# =====================================

def create_mod_block(mod):


    lines = []



    lines.append(
        f"File: {mod['file']}"
    )


    lines.append(
        f"Mod ID: {mod['modid']}"
    )


    lines.append(
        f"Version: {mod['version']}"
    )


    lines.append(
        f"Author: {mod['author']}"
    )


    lines.append(
        f"Size: {mod['size_mb']} MB"
    )


    lines.append(
        f"Files: {mod['files']}"
    )


    lines.append(
        f"Class files: {mod['class_files']}"
    )


    lines.append(
        f"Packages: {mod['packages']}"
    )


    lines.append(
        f"Difficulty: {mod['difficulty']}"
    )


    lines.append(
        f"Migration score: {mod['score']}/100"
    )


    lines.append(
        f"Migration status: {mod['migration_status']}"
    )



    lines.append(
        ""
    )



    lines.append(
        "API Usage:"
    )



    if mod["api_usage"]:


        for key,value in mod["api_usage"].items():

            lines.append(
                f"{key}: {value}"
            )

    else:

        lines.append(
            "None detected"
        )



    lines.append(
        ""
    )



    lines.append(
        "Content:"
    )



    for key,value in mod["content"].items():

        lines.append(
            f"{key}: {value}"
        )



    lines.append(
        ""
    )


    lines.append(
        "Side:"
    )


    lines.append(
        f"Client: {mod['client_percent']}%"
    )


    lines.append(
        f"Server: {mod['server_percent']}%"
    )



    lines.append(
        ""
    )



    lines.append(
        f"Estimated methods: {mod['estimated_methods']}"
    )



    return lines
# =====================================
# Documentation generation
# =====================================

def save_modlist(mods, path):

    log(
        "Creating modlist.md..."
    )


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
                f"## {mod['name']}\n\n"
            )


            write_text_block(
                file,
                create_mod_block(mod)
            )



            if mod["dependencies"]:


                file.write(
                    "Dependencies:\n"
                )


                write_text_block(
                    file,
                    mod["dependencies"]
                )



            if mod["detections"]:


                file.write(
                    "Detections:\n"
                )


                write_text_block(
                    file,
                    mod["detections"]
                )



            if mod["reasons"]:


                file.write(
                    "Migration problems:\n"
                )


                write_text_block(
                    file,
                    mod["reasons"]
                )



            if mod["migration_notes"]:


                file.write(
                    "Notes:\n"
                )


                write_text_block(
                    file,
                    mod["migration_notes"]
                )





def save_dependencies(mods, path):


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
                f"## {mod['name']}\n\n"
            )



            if mod["dependencies"]:


                write_text_block(
                    file,
                    mod["dependencies"]
                )

            else:


                write_text_block(
                    file,
                    [
                        "No dependencies detected"
                    ]
                )






def save_migration_report(mods, path):


    log(
        "Creating migration_report.md..."
    )


    with open(
        path,
        "w",
        encoding="utf-8"
    ) as file:



        file.write(
            "# UltraTech Migration Report\n\n"
        )



        hard = 0

        normal = 0

        easy = 0



        for mod in mods:


            if mod["difficulty"] == "Hard":

                hard += 1


            elif mod["difficulty"] == "Normal":

                normal += 1


            elif mod["difficulty"] == "Easy":

                easy += 1





        file.write(
            "Migration summary:\n\n"
        )



        write_text_block(
            file,
            [
                f"Total mods: {len(mods)}",
                f"Hard: {hard}",
                f"Normal: {normal}",
                f"Easy: {easy}"
            ]
        )



        for mod in mods:


            file.write(
                f"## {mod['name']}\n\n"
            )


            write_text_block(
                file,
                [
                    f"Difficulty: {mod['difficulty']}",
                    f"Score: {mod['score']}/100",
                    f"Status: {mod['migration_status']}",
                    f"Estimated methods: {mod['estimated_methods']}"
                ]
            )



            if mod["reasons"]:


                file.write(
                    "Problems:\n"
                )


                write_text_block(
                    file,
                    mod["reasons"]
                )





def save_graph_data(mods, path):


    log(
        "Creating mod_graph.json..."
    )


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
# Graph generator
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
            "graph_generator not found"
        )



    log(
        f"Selected graph generator: {os.path.basename(generator)}"
    )



    spec = importlib.util.spec_from_file_location(
        "graph_generator_module",
        generator
    )



    if spec is None:

        raise Exception(
            "Cannot load graph generator"
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
            f"Graph generator loading error: {error}"
        )



    if not hasattr(
        module,
        "main"
    ):


        raise Exception(
            "graph_generator has no main()"
        )



    module.main()
# =====================================
# Main process
# =====================================

def main():


    documents_saved = False



    try:


        mods_folder = ask_mod_folder()



        log(
            "Searching JAR files..."
        )



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
        # Surface scan
        # -----------------------------


        surface = surface_scan(
            jars
        )



        # -----------------------------
        # Class scanner
        # -----------------------------


        class_results = run_class_scanner(
            jars
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



        documents_saved = True



        log(
            "Documents successfully created"
        )



        # -----------------------------
        # Graph
        # -----------------------------


        answer = input(
            "\nOpen graph generator? [Y/N]\n> "
        )



        if answer.lower() == "y":



            try:


                run_graph_generator()



                log(
                    "Graph generator completed"
                )



            except Exception as error:



                print()

                log(
                    f"Graph generator error: {error}"
                )



                print(
                    "Documents are still available."
                )



        else:


            log(
                "Graph generation skipped"
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



        if documents_saved:

            print()

            print(
                "Documents were already saved."
            )

        else:

            print()

            print(
                "No documents were saved."
            )



    finally:


        input(
            "Press Enter to exit..."
        )





# =====================================
# Start
# =====================================

if __name__ == "__main__":

    main()