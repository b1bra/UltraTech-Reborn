import os
import json
import zipfile
import re
from datetime import datetime


VERSION = "0.5"


def log(text):
    print(f"[ModScanner] {text}")


def get_folder():
    print("==============================")
    print(" Minecraft Mod Scanner")
    print("==============================")
    print()

    path = input(
        "Enter mods folder path:\n> "
    )

    return path.strip().strip('"')



def find_jars(folder):

    if not os.path.exists(folder):
        log("ERROR: Folder does not exist")
        return []

    return sorted([
        os.path.join(folder, file)
        for file in os.listdir(folder)
        if file.lower().endswith(".jar")
    ])




def unique(items):

    result = []

    for item in items:

        if item not in result:
            result.append(item)

    return result




def scan_dependencies(text):

    dependencies = []

    patterns = [

        r"required-after:([^;]+)",
        r"required-before:([^;]+)",
        r"after:([^;]+)",
        r"before:([^;]+)"

    ]


    for pattern in patterns:

        found = re.findall(
            pattern,
            text
        )

        dependencies.extend(found)


    return [
        x.strip()
        for x in unique(dependencies)
    ]




def detect_category(data):

    name = (
        data["name"]
        +
        " "
        +
        data["file"]
    ).lower()


    if "api" in name:
        return "API"

    if "core" in name:
        return "Core"

    if "lib" in name:
        return "Library"

    if data["coremod"]:
        return "CoreMod"

    return "Mod"




def analyze_jar(path):

    result = {

        "file": os.path.basename(path),

        "name": "Unknown",

        "modid": "Unknown",

        "version": "Unknown",

        "author": "Unknown",

        "description": "",

        "dependencies": [],

        "coremod": False,

        "core_plugin": "",

        "category": "Unknown",

        "mcmod_found": False,

        "migration_status": "Unknown",

        "migration_notes": []

    }



    try:

        with zipfile.ZipFile(path, "r") as jar:

            files = jar.namelist()



            # Manifest

            if "META-INF/MANIFEST.MF" in files:


                manifest = jar.read(
                    "META-INF/MANIFEST.MF"
                ).decode(
                    "utf-8",
                    errors="ignore"
                )


                if (
                    "FMLCorePlugin" in manifest
                    or "CorePlugin" in manifest
                ):

                    result["coremod"] = True

                    result["migration_notes"].append(
                        "CoreMod detected. Requires manual migration."
                    )


                result["core_plugin"] = manifest


                result["dependencies"].extend(
                    scan_dependencies(
                        manifest
                    )
                )



            # mcmod.info

            if "mcmod.info" in files:


                result["mcmod_found"] = True


                raw = jar.read(
                    "mcmod.info"
                ).decode(
                    "utf-8",
                    errors="ignore"
                )


                info = json.loads(raw)



                if isinstance(info, list):

                    mod = info[0]


                elif isinstance(info, dict):

                    mods = info.get(
                        "modList",
                        []
                    )

                    mod = (
                        mods[0]
                        if mods
                        else info
                    )

                else:

                    mod = {}



                result["name"] = mod.get(
                    "name",
                    "Unknown"
                )

                result["modid"] = mod.get(
                    "modid",
                    "Unknown"
                )

                result["version"] = mod.get(
                    "version",
                    "Unknown"
                )

                result["description"] = mod.get(
                    "description",
                    ""
                )


                author = mod.get(
                    "authorList",
                    "Unknown"
                )


                if isinstance(author, list):
                    author = ", ".join(author)


                result["author"] = author


                required = mod.get(
                    "requiredMods",
                    []
                )


                if isinstance(required, list):
                    result["dependencies"].extend(required)



            else:

                result["migration_notes"].append(
                    "mcmod.info not found."
                )



    except Exception as error:

        result["migration_notes"].append(
            f"Analysis error: {error}"
        )



    result["dependencies"] = unique(
        result["dependencies"]
    )


    result["category"] = detect_category(
        result
    )



    if result["coremod"]:

        result["migration_status"] = (
            "Hard"
        )

    elif not result["mcmod_found"]:

        result["migration_status"] = (
            "Needs investigation"
        )

    else:

        result["migration_status"] = (
            "Normal"
        )



    return result






def save_modlist(mods, path):

    with open(
        path,
        "w",
        encoding="utf-8"
    ) as file:


        file.write("# Mod List\n\n")

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
                f"Category: {mod['category']}\n"
            )

            file.write(
                f"CoreMod: {mod['coremod']}\n"
            )

            file.write(
                f"Status: {mod['migration_status']}\n"
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


            file.write("\n---\n\n")






def save_dependencies(mods, path):

    with open(
        path,
        "w",
        encoding="utf-8"
    ) as file:


        file.write(
            "# Dependencies\n\n"
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


            file.write("\n")






def save_migration(mods, path):

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
                f"## {mod['name']}\n"
            )

            file.write(
                f"Status: {mod['migration_status']}\n\n"
            )


            for note in mod["migration_notes"]:

                file.write(
                    f"- {note}\n"
                )


            file.write("\n")






def save_json(mods, path):

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






def main():

    log(
        f"Starting ModScanner v{VERSION}"
    )


    folder = get_folder()


    jars = find_jars(
        folder
    )


    if not jars:

        log(
            "No jars found"
        )

        input(
            "Press Enter..."
        )

        return



    mods = []


    for i, jar in enumerate(
        jars,
        1
    ):

        log(
            f"[{i}/{len(jars)}] "
            f"{os.path.basename(jar)}"
        )


        mod = analyze_jar(
            jar
        )


        mods.append(
            mod
        )


        log(
            " -> "
            +
            mod["name"]
        )



    mods.sort(
        key=lambda x: x["name"].lower()
    )



    root = os.path.dirname(
        os.path.dirname(
            os.path.dirname(
                os.path.abspath(__file__)
            )
        )
    )


    docs = os.path.join(
        root,
        "docs"
    )


    os.makedirs(
        docs,
        exist_ok=True
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


    save_migration(
        mods,
        os.path.join(
            docs,
            "migration_report.md"
        )
    )


    save_json(
        mods,
        os.path.join(
            docs,
            "mod_graph.json"
        )
    )



    print()

    log("==============================")
    log("Analysis complete")
    log(f"Mods: {len(mods)}")
    log(f"Output: {docs}")
    log("==============================")


    input(
        "Press Enter to close..."
    )



if __name__ == "__main__":
    main()