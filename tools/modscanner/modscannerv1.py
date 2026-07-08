import os
import json
import zipfile
from datetime import datetime


def log(message):
    print(f"[ModScanner] {message}")


def find_mods(mods_path):
    mods = []

    log(f"Checking folder: {mods_path}")

    if not os.path.exists(mods_path):
        log("ERROR: Folder not found")
        return mods

    files = os.listdir(mods_path)

    log(f"Files found: {len(files)}")

    for file in files:
        if file.lower().endswith(".jar"):
            mods.append(os.path.join(mods_path, file))

    return mods


def read_mcmod_info(jar_path):
    filename = os.path.basename(jar_path)

    log(f"Analyzing: {filename}")

    try:
        with zipfile.ZipFile(jar_path, "r") as jar:

            if "mcmod.info" not in jar.namelist():
                log("  -> mcmod.info not found")
                return None

            log("  -> mcmod.info found")

            data = jar.read("mcmod.info").decode("utf-8")

            return json.loads(data)

    except Exception as e:
        log(f"  -> ERROR: {e}")
        return None


def parse_mod_info(data):

    result = []

    if isinstance(data, list):
        mods = data

    elif isinstance(data, dict):
        mods = data.get("modList", [])

    else:
        mods = []


    for mod in mods:

        author = mod.get("authorList", "Unknown")

        if isinstance(author, list):
            author = ", ".join(author)


        result.append({

            "name": mod.get("name", "Unknown"),

            "modid": mod.get("modid", "Unknown"),

            "version": mod.get(
                "version",
                "Unknown"
            ),

            "author": author,

            "description": mod.get(
                "description",
                ""
            ),

            "category": "Unknown",

            "status": "Not analyzed",

            "notes": "Detected from mcmod.info."

        })


    return result



def create_report(mods, output_path):

    os.makedirs(
        os.path.dirname(output_path),
        exist_ok=True
    )


    log(f"Creating report: {output_path}")


    with open(
        output_path,
        "w",
        encoding="utf-8"
    ) as file:


        file.write("# Mod List\n\n")

        file.write(
            f"Generated: {datetime.now()}\n\n"
        )

        file.write(
            f"Total mods detected: {len(mods)}\n\n"
        )


        for mod in mods:

            file.write(
                f"## {mod['name']}\n\n"
            )

            file.write(
                f"Mod ID:\n{mod['modid']}\n\n"
            )

            file.write(
                f"Version:\n{mod['version']}\n\n"
            )

            file.write(
                f"Author:\n{mod['author']}\n\n"
            )

            file.write(
                f"Category:\n{mod['category']}\n\n"
            )

            file.write(
                f"Status:\n{mod['status']}\n\n"
            )

            file.write(
                f"Notes:\n{mod['notes']}\n\n"
            )


            if mod["description"]:

                file.write(
                    f"Description:\n{mod['description']}\n\n"
                )


            file.write("---\n\n")



def main():

    log("Starting ModScanner v0.1")

    print()
    print("================================")
    print(" Minecraft Mod Scanner")
    print("================================")
    print()


    mods_path = input(
        "Enter path to mods folder:\n> "
    ).strip('" ')


    if not mods_path:

        log("No path entered. Exiting.")

        input(
            "Press Enter to close..."
        )

        return



    jars = find_mods(mods_path)


    log(
        f"JAR files detected: {len(jars)}"
    )


    all_mods = []


    for index, jar in enumerate(
        jars,
        start=1
    ):

        log(
            f"[{index}/{len(jars)}]"
        )


        info = read_mcmod_info(jar)


        if info:

            mods = parse_mod_info(info)

            all_mods.extend(mods)


            for mod in mods:

                log(
                    f"  Found mod: "
                    f"{mod['name']} "
                    f"({mod['modid']})"
                )


        else:

            log(
                "  No mod information found"
            )



    project_root = os.path.dirname(
        os.path.dirname(
            os.path.dirname(
                os.path.abspath(__file__)
            )
        )
    )


    output = os.path.join(
        project_root,
        "docs",
        "modlist1.md"
    )


    create_report(
        all_mods,
        output
    )


    print()

    log("===============================")

    log(
        "Scan finished"
    )

    log(
        f"Mods detected: {len(all_mods)}"
    )

    log(
        f"Report saved: {output}"
    )

    log("===============================")


    input(
        "\nPress Enter to close..."
    )



if __name__ == "__main__":
    main()