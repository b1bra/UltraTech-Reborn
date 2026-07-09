import os
import re
import zipfile
import json


VERSION = "5.0"


MODULE_NAME = "ClassScanner"





# ==========================================================
# Logger integration
# ==========================================================

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


        def section(self, text):
            print("=" * 80)
            print(text)
            print("=" * 80)


    logger = DummyLogger()





# ==========================================================
# Logging helpers
# ==========================================================


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









# ==========================================================
# Migration Detection
# ==========================================================


MIGRATION_PATTERNS = {


    "CoreMod / ASM":
    {
        "patterns":
        [

            "IClassTransformer",

            "IFMLLoadingPlugin",

            "FMLCorePlugin",

            "org/objectweb/asm",

            "ClassTransformer"

        ],

        "score": 40

    },



    "Old Forge API":
    {

        "patterns":

        [

            "cpw/mods/fml",

            "net/minecraftforge"

        ],

        "score": 10

    },



    "Old Registry":
    {

        "patterns":

        [

            "GameRegistry",

            "EntityRegistry",

            "LanguageRegistry"

        ],

        "score": 10

    },



    "Old Rendering":
    {

        "patterns":

        [

            "RenderBlocks",

            "ISimpleBlockRenderingHandler",

            "TileEntitySpecialRenderer"

        ],

        "score": 15

    },



    "Old Network":
    {

        "patterns":

        [

            "Packet250CustomPayload",

            "SimpleNetworkWrapper",

            "NetworkRegistry"

        ],

        "score": 15

    }


}









# ==========================================================
# Result creation
# ==========================================================


def create_result(path):


    filename = os.path.basename(path) if path else ""



    return {


        "file":

        filename,



        "size_mb":

        round(

            os.path.getsize(path)
            /
            (1024 * 1024),

            2

        )
        if path and os.path.isfile(path)

        else 0,



        "files": 0,


        "class_files": 0,


        "packages": 0,



        "score": 0,


        "difficulty": "Unknown",



        "detections": [],



        "content":

        {

            "Blocks": 0,

            "Items": 0,

            "TileEntities": 0,

            "Entities": 0

        },



        "content_lists":

        {

            "Blocks": [],

            "Items": [],

            "TileEntities": [],

            "Entities": []

        },



        "content_sources":

        {

            "Blocks":

            {

                "class": 0,

                "lang": 0,

                "models": 0,

                "blockstates": 0

            },


            "Items":

            {

                "class": 0,

                "lang": 0,

                "models": 0

            }

        },



        "registration":

        {

            "Blocks": 0,

            "Items": 0,

            "TileEntities": 0,

            "Entities": 0,

            "WorldGenerators": 0

        },



        "recipes":

        {

            "Crafting": 0,

            "Smelting": 0,

            "Custom": 0

        },



        "gui": {},



        "network": {},



        "creative": 0,



        "important_classes": [],



        "estimated_methods": 0,



        "client_hits": 0,


        "server_hits": 0,



        "client_percent": 0,


        "server_percent": 0,



        "reasons": [],



        "lang_entries": 0,


        "models": 0,


        "blockstates": 0


    }
# ==========================================================
# Resource filters
# ==========================================================


INVALID_NAMES = {


    "name",

    "block",

    "item",

    "null",

    "unknown",

    "air",

    "empty",

    "missing",


}





def clean_name(name):


    if not name:

        return None



    name = name.strip()



    name = name.lower()



    if name in INVALID_NAMES:

        return None



    if len(name) < 3:

        return None



    if name.startswith(
        "tile."
    ):

        name = name[5:]



    if name.startswith(
        "item."
    ):

        name = name[5:]



    if name.endswith(
        ".name"
    ):

        name = name[:-5]



    name = name.replace(
        ".",
        "_"
    )



    if not re.match(
        r"^[a-z0-9_\-]+$",
        name
    ):

        return None



    return name







# ==========================================================
# LANG scanner
# ==========================================================


def scan_lang_file(
        data,
        result
):


    text = data.decode(
        "utf-8",
        errors="ignore"
    )


    entries = 0



    for line in text.splitlines():


        line = line.strip()



        if not line:

            continue



        if line.startswith(
            "#"
        ):

            continue



        if "=" not in line:

            continue



        key, value = line.split(
            "=",
            1
        )



        key = key.strip()



        entries += 1



        # --------------------------
        # Blocks
        # --------------------------


        if key.startswith(
            "tile."
        ):


            name = clean_name(
                key
            )



            if name:


                if name not in result["content_lists"]["Blocks"]:


                    result["content_lists"]["Blocks"].append(
                        name
                    )


                    result["content_sources"]["Blocks"]["lang"] += 1





        # --------------------------
        # Items
        # --------------------------


        elif key.startswith(
            "item."
        ):


            name = clean_name(
                key
            )



            if name:


                if name not in result["content_lists"]["Items"]:


                    result["content_lists"]["Items"].append(
                        name
                    )


                    result["content_sources"]["Items"]["lang"] += 1



    result["lang_entries"] += entries







# ==========================================================
# Blockstate scanner
# ==========================================================


def scan_blockstate_file(
        filename,
        result
):


    if not filename.endswith(
        ".json"
    ):

        return



    base = os.path.basename(
        filename
    )



    name = base.replace(
        ".json",
        ""
    )



    name = clean_name(
        name
    )



    if not name:

        return



    if name not in result["content_lists"]["Blocks"]:


        result["content_lists"]["Blocks"].append(
            name
        )


        result["content_sources"]["Blocks"]["blockstates"] += 1



    result["blockstates"] += 1







# ==========================================================
# Model scanner
# ==========================================================


def scan_model_file(
        filename,
        result
):


    if not filename.endswith(
        ".json"
    ):

        return



    if "/models/block/" in filename:


        name = os.path.basename(
            filename
        )



        name = name.replace(
            ".json",
            ""
        )



        name = clean_name(
            name
        )



        if name:


            if name not in result["content_lists"]["Blocks"]:


                result["content_lists"]["Blocks"].append(
                    name
                )


                result["content_sources"]["Blocks"]["models"] += 1



    elif "/models/item/" in filename:


        name = os.path.basename(
            filename
        )



        name = name.replace(
            ".json",
            ""
        )



        name = clean_name(
            name
        )



        if name:


            if name not in result["content_lists"]["Items"]:


                result["content_lists"]["Items"].append(
                    name
                )


                result["content_sources"]["Items"]["models"] += 1



    result["models"] += 1







# ==========================================================
# Resource scanner inside JAR
# ==========================================================


def scan_resources(
        jar,
        result
):


    log_debug(
        "Scanning resources"
    )



    try:


        for file in jar.namelist():



            lower = file.lower()



            # LANG


            if lower.endswith(
                ".lang"
            ):


                try:


                    scan_lang_file(

                        jar.read(file),

                        result

                    )


                except Exception:

                    continue





            # BLOCKSTATES


            elif "/blockstates/" in lower:


                scan_blockstate_file(

                    file,

                    result

                )





            # MODELS


            elif "/models/" in lower:


                scan_model_file(

                    file,

                    result

                )



    except Exception as error:


        log_error(
            f"Resource scan error: {error}"
        )







# ==========================================================
# Finalize content counts
# ==========================================================


def finalize_content(
        result
):


    result["content"]["Blocks"] = len(
        result["content_lists"]["Blocks"]
    )



    result["content"]["Items"] = len(
        result["content_lists"]["Items"]
    )



    result["content"]["TileEntities"] = len(
        result["content_lists"]["TileEntities"]
    )



    result["content"]["Entities"] = len(
        result["content_lists"]["Entities"]
    )



    log_debug(
        f"Detected blocks: {result['content']['Blocks']}"
    )


    log_debug(
        f"Detected items: {result['content']['Items']}"
    )
# ==========================================================
# Pattern helpers
# ==========================================================


def add_unique(
        array,
        value
):


    if not value:

        return



    if value not in array:


        array.append(
            value
        )







def count_patterns(
        text,
        patterns
):


    total = 0



    for pattern in patterns:


        total += text.count(
            pattern
        )



    return total







# ==========================================================
# Registration patterns
# ==========================================================


REGISTRATION_PATTERNS = {


    "Blocks":
    [

        "GameRegistry.registerBlock",

        "registerBlock("

    ],



    "Items":
    [

        "GameRegistry.registerItem",

        "registerItem("

    ],



    "TileEntities":
    [

        "GameRegistry.registerTileEntity",

        "registerTileEntity("

    ],



    "Entities":
    [

        "EntityRegistry.registerModEntity",

        "EntityRegistry.registerGlobalEntityID"

    ],



    "WorldGenerators":
    [

        "GameRegistry.registerWorldGenerator"

    ]

}







# ==========================================================
# Content class patterns
# ==========================================================


CONTENT_CLASS_PATTERNS = {


    "Blocks":
    [

        "extends Block",

        "Block;",

        "Block("

    ],



    "Items":
    [

        "extends Item",

        "Item;",

        "Item("

    ],



    "TileEntities":
    [

        "extends TileEntity",

        "TileEntity;"

    ],



    "Entities":
    [

        "extends Entity",

        "Entity;"

    ]

}







# ==========================================================
# Recipe patterns
# ==========================================================


RECIPE_PATTERNS = {


    "Crafting":
    [

        "GameRegistry.addRecipe",

        "ShapedRecipe",

        "ShapelessRecipe",

        "ShapedOreRecipe",

        "ShapelessOreRecipe"

    ],



    "Smelting":
    [

        "GameRegistry.addSmelting",

        "addSmelting("

    ],



    "Custom":
    [

        "IRecipe",

        "implements IRecipe"

    ]

}







# ==========================================================
# GUI patterns
# ==========================================================


GUI_PATTERNS = {


    "GUI":

    [

        "GuiScreen",

        "GuiContainer",

        "GuiButton"

    ],



    "Containers":

    [

        "Container",

        "Slot"

    ]

}







# ==========================================================
# Network patterns
# ==========================================================


NETWORK_PATTERNS = {


    "Packets":

    [

        "Packet",

        "PacketHandler"

    ],



    "Channels":

    [

        "SimpleNetworkWrapper",

        "registerMessage"

    ]

}







# ==========================================================
# Analyze one class
# ==========================================================


def analyze_class(
        text,
        result
):


    # ------------------------------------
    # Migration
    # ------------------------------------


    for name, data in MIGRATION_PATTERNS.items():


        found = False



        for pattern in data["patterns"]:


            if pattern in text:


                found = True

                break



        if found:


            add_unique(

                result["detections"],

                name

            )


            result["score"] += data["score"]





    # ------------------------------------
    # Registration
    # ------------------------------------


    for name, patterns in REGISTRATION_PATTERNS.items():


        amount = count_patterns(

            text,

            patterns

        )


        result["registration"][name] += amount





    # ------------------------------------
    # Content classes
    # ------------------------------------


    for name, patterns in CONTENT_CLASS_PATTERNS.items():


        amount = count_patterns(

            text,

            patterns

        )



        if amount:


            result["content_sources"].get(
                name,
                {}
            )



            if name in result["content_sources"]:


                if "class" in result["content_sources"][name]:


                    result["content_sources"][name]["class"] += amount



            else:


                if name == "Blocks":


                    result["content_sources"]["Blocks"]["class"] += amount



                elif name == "Items":


                    result["content_sources"]["Items"]["class"] += amount





    # ------------------------------------
    # Recipes
    # ------------------------------------


    for name, patterns in RECIPE_PATTERNS.items():


        result["recipes"][name] += count_patterns(

            text,

            patterns

        )





    # ------------------------------------
    # GUI
    # ------------------------------------


    for name, patterns in GUI_PATTERNS.items():


        amount = count_patterns(

            text,

            patterns

        )



        if amount:


            result["gui"][name] = (

                result["gui"].get(

                    name,

                    0

                )

                +

                amount

            )





    # ------------------------------------
    # Network
    # ------------------------------------


    for name, patterns in NETWORK_PATTERNS.items():


        amount = count_patterns(

            text,

            patterns

        )



        if amount:


            result["network"][name] = (

                result["network"].get(

                    name,

                    0

                )

                +

                amount

            )







    # ------------------------------------
    # Creative tabs
    # ------------------------------------


    result["creative"] += count_patterns(

        text,

        [

            "setCreativeTab",

            "CreativeTabs"

        ]

    )





    # ------------------------------------
    # Client/server
    # ------------------------------------


    result["client_hits"] += count_patterns(

        text,

        [

            "net/minecraft/client/",

            "Minecraft.getMinecraft"

        ]

    )



    result["server_hits"] += count_patterns(

        text,

        [

            "WorldServer",

            "MinecraftServer",

            "net/minecraft/world/"

        ]

    )





    # ------------------------------------
    # Methods
    # ------------------------------------


    result["estimated_methods"] += int(

        text.count("(")

        /

        5

    )





    # ------------------------------------
    # Class names
    # ------------------------------------


    classes = re.findall(

        r"(?:class|extends)\s+([A-Za-z0-9_]+)",

        text

    )



    for cls in classes:


        add_unique(

            result["important_classes"],

            cls

        )





    detect_named_content(
        text,
        result
    )







# ==========================================================
# Detect names from bytecode strings
# ==========================================================


def detect_named_content(
        text,
        result
):


    # Block registry names


    block_patterns = [

        r"registerBlock\([^,]+,\s*\"([A-Za-z0-9_\-]+)\"",

        r"Block\w*\(\"([A-Za-z0-9_\-]+)\""

    ]



    for pattern in block_patterns:


        for match in re.findall(

            pattern,

            text

        ):


            add_unique(

                result["content_lists"]["Blocks"],

                match

            )





    # Item registry names


    item_patterns = [

        r"registerItem\([^,]+,\s*\"([A-Za-z0-9_\-]+)\"",

        r"Item\w*\(\"([A-Za-z0-9_\-]+)\""

    ]



    for pattern in item_patterns:


        for match in re.findall(

            pattern,

            text

        ):


            add_unique(

                result["content_lists"]["Items"],

                match

            )
# ==========================================================
# Entity / TileEntity detection
# ==========================================================


def detect_special_content(
        text,
        result
):


    # TileEntity names


    tile_patterns = [

        r"class\s+([A-Za-z0-9_]+)\s+extends\s+TileEntity",

        r"([A-Za-z0-9_]+)Tile"

    ]



    for pattern in tile_patterns:


        matches = re.findall(

            pattern,

            text

        )



        for name in matches:


            if isinstance(
                name,
                tuple
            ):

                name = name[0]



            add_unique(

                result["content_lists"]["TileEntities"],

                name

            )





    # Entity names


    entity_patterns = [

        r"class\s+([A-Za-z0-9_]+)\s+extends\s+Entity",

        r"Entity([A-Za-z0-9_]+)"

    ]



    for pattern in entity_patterns:


        matches = re.findall(

            pattern,

            text

        )



        for name in matches:


            if isinstance(
                name,
                tuple
            ):

                name = name[0]



            add_unique(

                result["content_lists"]["Entities"],

                name

            )









# ==========================================================
# JAR Scanner
# ==========================================================


def scan_jar(
        path,
        index,
        total
):


    filename = os.path.basename(
        path
    )



    log(

        f"[{index}/{total}] Scanning {filename}"

    )



    result = create_result(
        path
    )



    packages = set()



    try:


        with zipfile.ZipFile(

            path,

            "r"

        ) as jar:



            files = jar.namelist()



            result["files"] = len(
                files
            )



            # ------------------------------------
            # Resources
            # ------------------------------------


            scan_resources(

                jar,

                result

            )





            # ------------------------------------
            # Classes
            # ------------------------------------


            for file in files:


                if not file.endswith(
                    ".class"
                ):


                    continue



                result["class_files"] += 1



                packages.add(

                    os.path.dirname(
                        file
                    )

                )



                try:


                    data = jar.read(
                        file
                    ).decode(

                        "latin1",

                        errors="ignore"

                    )



                    analyze_class(

                        data,

                        result

                    )



                    detect_special_content(

                        data,

                        result

                    )



                except Exception:


                    continue





        result["packages"] = len(
            packages
        )





    except Exception as error:


        log_error(

            f"{filename}: {error}"

        )



    finalize_content(
        result
    )



    return result







# ==========================================================
# Cleanup
# ==========================================================


def cleanup_result(
        result
):


    result["detections"] = list(

        set(

            result["detections"]

        )

    )



    result["important_classes"] = list(

        set(

            result["important_classes"]

        )

    )



    for key in result["content_lists"]:


        result["content_lists"][key] = list(

            set(

                result["content_lists"][key]

            )

        )



    # Percent client/server


    total = (

        result["client_hits"]

        +

        result["server_hits"]

    )



    if total:


        result["client_percent"] = round(

            result["client_hits"]

            /

            total

            *

            100,

            1

        )



        result["server_percent"] = round(

            result["server_hits"]

            /

            total

            *

            100,

            1

        )





    finalize_content(
        result
    )



    return result







# ==========================================================
# Difficulty calculation
# ==========================================================


def calculate_difficulty(
        result
):


    score = result["score"]



    # Classes


    if result["class_files"] > 1000:


        score += 20


        result["reasons"].append(

            "Large amount of classes"

        )





    # Blocks


    blocks = result["content"]["Blocks"]



    if blocks > 200:


        score += 15


        result["reasons"].append(

            "Large block system"

        )


    elif blocks > 50:


        score += 5





    # Items


    items = result["content"]["Items"]



    if items > 500:


        score += 15


        result["reasons"].append(

            "Large item system"

        )





    # Machines


    tiles = result["content"]["TileEntities"]



    if tiles > 50:


        score += 20


        result["reasons"].append(

            "Large machine system"

        )


    elif tiles > 10:


        score += 10





    # Recipes


    recipes = sum(

        result["recipes"].values()

    )



    if recipes > 500:


        score += 15


        result["reasons"].append(

            "Large recipe system"

        )





    # Networking


    packets = sum(

        result["network"].values()

    )



    if packets > 50:


        score += 15


        result["reasons"].append(

            "Heavy networking"

        )





    # Client


    if result["client_percent"] > 50:


        score += 10


        result["reasons"].append(

            "Heavy client code"

        )





    result["score"] = min(

        score,

        100

    )





    if result["score"] >= 70:


        result["difficulty"] = "Hard"



    elif result["score"] >= 35:


        result["difficulty"] = "Normal"



    else:


        result["difficulty"] = "Easy"
# ==========================================================
# Mod type detection
# ==========================================================


def detect_mod_type(
        result
):


    types = []



    technology_score = (

        result["content"]["TileEntities"]

        +

        result["content"]["Blocks"]

        +

        sum(
            result["recipes"].values()
        )

    )



    if technology_score > 50:


        types.append(
            "Technology"
        )





    if result["registration"]["WorldGenerators"] > 0:


        types.append(
            "World Generation"
        )





    if (

        result["content"]["Blocks"]

        >

        20

        or

        result["content"]["Items"]

        >

        50

    ):


        types.append(
            "Content Mod"
        )





    if result["network"]:


        types.append(
            "Network Based"
        )





    if not types:


        types.append(
            "Utility"
        )



    return types







# ==========================================================
# Finalize scanner output
# ==========================================================


def finalize_result(
        result
):


    cleanup_result(
        result
    )



    calculate_difficulty(
        result
    )



    result["type"] = detect_mod_type(
        result
    )



    # сортировка для красивого JSON


    result["content_lists"]["Blocks"].sort()

    result["content_lists"]["Items"].sort()

    result["content_lists"]["Entities"].sort()

    result["content_lists"]["TileEntities"].sort()



    result["reasons"] = list(

        set(

            result["reasons"]

        )

    )



    return result







# ==========================================================
# Main scanner API
# ==========================================================


def scan_mods(
        jars
):


    logger.section(

        f"ClassScanner v{VERSION}"

    )


    logger.start_timer(

        "class_scanner"

    )



    result = {}



    total = len(
        jars
    )



    log(

        f"Starting scan of {total} mods"

    )



    for index, jar in enumerate(

        jars,

        1

    ):



        try:


            scanned = scan_jar(

                jar,

                index,

                total

            )



            scanned = finalize_result(

                scanned

            )



            filename = os.path.basename(
                jar
            )



            result[filename] = scanned





            logger.module_success(

                MODULE_NAME,

                (

                    f"{filename}: "

                    f"{scanned['difficulty']} "

                    f"{scanned['score']}/100 "

                    f"Blocks={scanned['content']['Blocks']} "

                    f"Items={scanned['content']['Items']} "

                    f"Entities={scanned['content']['Entities']}"

                )

            )



        except Exception as error:


            logger.log_exception_context(

                MODULE_NAME,

                f"scan_mod {jar}",

                error

            )





    logger.stop_timer(

        "class_scanner"

    )



    logger.module_success(

        MODULE_NAME,

        "ClassScanner finished"

    )



    return result







# ==========================================================
# Standalone test
# ==========================================================


if __name__ == "__main__":



    folder = input(

        "Mods folder: "

    )



    jars = []



    for root, dirs, files in os.walk(

        folder

    ):


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





    data = scan_mods(

        jars

    )





    for mod, info in data.items():


        print()

        print(

            "=" * 60

        )


        print(
            mod
        )


        print(

            "Difficulty:",

            info["difficulty"]

        )


        print(

            "Score:",

            info["score"]

        )



        print(

            "Type:",

            info["type"]

        )



        print()

        print(

            "Blocks:",

            info["content"]["Blocks"]

        )



        print(

            "Items:",

            info["content"]["Items"]

        )



        print(

            "TileEntities:",

            info["content"]["TileEntities"]

        )



        print(

            "Entities:",

            info["content"]["Entities"]

        )



        print()

        print(

            "Recipes:",

            sum(

                info["recipes"].values()

            )

        )
