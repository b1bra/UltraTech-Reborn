import os
import json
import zipfile
from datetime import datetime


VERSION = "0.3"


def log(text):
    print(f"[ModScanner] {text}")


def get_mods_folder():

    print()
    print("==============================")
    print(" Minecraft Mod Scanner")
    print("==============================")
    print()

    path = input(
        "Enter mods folder path:\n> "
    )

    path = path.strip().strip('"')

    print()
    log(f"Received path:")
    log(repr(path))
    print()

    return path



def find_jars(folder):

    if not os.path.exists(folder):

        log("ERROR: Folder does not exist")
        return []


    if not os.path.isdir(folder):

        log("ERROR: Path is not a folder")
        return []


    jars = []


    for file in os.listdir(folder):

        if file.lower().endswith(".jar"):

            jars.append(
                os.path.join(folder, file)
            )


    return jars





def analyze_jar(path):

    filename = os.path.basename(path)


    result = {

        "file": filename,

        "name": "Unknown",

        "modid": "Unknown",

        "version": "Unknown",

        "author": "Unknown",

        "description": "",

        "dependencies": [],

        "coremod": False,

        "mcmod_found": False,

        "status": "Unknown"

    }



    try:

        with zipfile.ZipFile(
            path,
            "r"
        ) as jar:


            files = jar.namelist()



            # CoreMod check

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





            # mcmod.info

            if "mcmod.info" in files:


                result["mcmod_found"] = True


                raw = jar.read(
                    "mcmod.info"
                ).decode(
                    "utf-8",
                    errors="ignore"
                )


                data = json.loads(raw)


                if isinstance(data, list):

                    mod = data[0]


                elif isinstance(data, dict):

                    mods = data.get(
                        "modList",
                        []
                    )

                    if mods:

                        mod = mods[0]

                    else:

                        mod = data


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


                author = mod.get(
                    "authorList",
                    "Unknown"
                )


                if isinstance(author, list):

                    author = ", ".join(author)


                result["author"] = author


                result["description"] = mod.get(
                    "description",
                    ""
                )


                result["dependencies"] = mod.get(
                    "requiredMods",
                    []
                )



            else:

                result["status"] = (
                    "No mcmod.info"
                )



            if result["status"] == "Unknown":

                result["status"] = "Analyzed"



    except Exception as e:

        result["status"] = (
            f"Error: {e}"
        )


    return result






def save_modlist(mods, file):

    with open(
        file,
        "w",
        encoding="utf-8"
    ) as f:


        f.write(
            "# Mod List\n\n"
        )


        f.write(
            f"Generated: {datetime.now()}\n\n"
        )


        for mod in mods:


            f.write(
                f"## {mod['name']}\n\n"
            )


            f.write(
                f"File:\n{mod['file']}\n\n"
            )


            f.write(
                f"Mod ID:\n{mod['modid']}\n\n"
            )


            f.write(
                f"Version:\n{mod['version']}\n\n"
            )


            f.write(
                f"Author:\n{mod['author']}\n\n"
            )


            f.write(
                f"CoreMod:\n{mod['coremod']}\n\n"
            )


            f.write(
                f"Status:\n{mod['status']}\n\n"
            )


            f.write("---\n\n")







def save_dependencies(mods, file):

    with open(
        file,
        "w",
        encoding="utf-8"
    ) as f:


        f.write(
            "# Dependencies Report\n\n"
        )


        for mod in mods:


            f.write(
                f"## {mod['name']}\n\n"
            )


            if mod["dependencies"]:


                for dep in mod["dependencies"]:

                    f.write(
                        f"- {dep}\n"
                    )

            else:

                f.write(
                    "No dependencies detected\n"
                )


            f.write("\n")







def save_migration(mods, file):

    with open(
        file,
        "w",
        encoding="utf-8"
    ) as f:


        f.write(
            "# Migration Report\n\n"
        )


        for mod in mods:


            f.write(
                f"## {mod['name']}\n\n"
            )


            if mod["coremod"]:

                f.write(
                    "WARNING: CoreMod detected\n\n"
                )


            if not mod["mcmod_found"]:

                f.write(
                    "WARNING: mcmod.info missing\n\n"
                )


            f.write(
                f"Status: {mod['status']}\n\n"
            )






def save_json(mods, file):

    with open(
        file,
        "w",
        encoding="utf-8"
    ) as f:


        json.dump(
            mods,
            f,
            indent=4,
            ensure_ascii=False
        )







def main():

    log(
        f"Starting ModScanner v{VERSION}"
    )


    folder = get_mods_folder()


    jars = find_jars(folder)


    if not jars:

        log(
            "No JAR files found. Stopping."
        )

        input(
            "Press Enter to close..."
        )

        return



    log(
        f"Found JAR files: {len(jars)}"
    )


    mods = []



    for i, jar in enumerate(
        jars,
        1
    ):


        name = os.path.basename(jar)


        log(
            f"[{i}/{len(jars)}] {name}"
        )


        info = analyze_jar(
            jar
        )


        mods.append(
            info
        )


        log(
            f" -> {info['name']}"
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
            "modlist1.md"
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
    log("Finished")
    log(f"Mods analyzed: {len(mods)}")
    log(f"Reports: {docs}")
    log("==============================")


    input(
        "Press Enter to close..."
    )





if __name__ == "__main__":
    main()