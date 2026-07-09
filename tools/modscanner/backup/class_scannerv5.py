import os
import zipfile
import re


VERSION = "4.0"


def log(text):
    print(f"[ClassScanner v{VERSION}] {text}")



# ==========================================================
# Forge 1.7.10 Detection Patterns
# ==========================================================


MIGRATION_PATTERNS = {


    "CoreMod / ASM":
    {
        "patterns":
        [
            "IClassTransformer",
            "IFMLLoadingPlugin",
            "FMLCorePlugin",
            "org.objectweb.asm",
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
# Registration
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
# Recipes 1.7.10
# ==========================================================


RECIPE_PATTERNS = {


    "Crafting":
    [
        "GameRegistry.addRecipe",
        "ShapedRecipe",
        "ShapedOreRecipe",
        "ShapelessRecipe",
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
# Content
# ==========================================================


CONTENT_PATTERNS = {


    "Blocks":
    [
        "extends Block",
        "Block;"
    ],



    "Items":
    [
        "extends Item",
        "Item;"
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
# Other systems
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







CREATIVE_PATTERNS = {


    "CreativeTab":
    [
        "setCreativeTab",
        "CreativeTabs"
    ]

}
# ==========================================================
# Utility
# ==========================================================


def count_patterns(text, patterns):

    count = 0


    for pattern in patterns:

        count += text.count(pattern)


    return count






# ==========================================================
# Result structure
# ==========================================================


def create_result(path):


    return {


        "file":
        os.path.basename(path),


        "size_mb":
        round(
            os.path.getsize(path) /
            (1024 * 1024),
            2
        ),



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



        "gui":
        {},


        "network":
        {},



        "creative":
        0,



        "api_usage":
        {},



        "minecraft_usage":
        {},



        "important_classes": [],



        "estimated_methods": 0,



        "client_hits": 0,

        "server_hits": 0,



        "client_percent": 0,

        "server_percent": 0,



        "reasons": []

    }








# ==========================================================
# Class analyzer
# ==========================================================


def analyze_class(text):


    result = create_result(
        ""
    )



    # ------------------------------
    # Migration score
    # ------------------------------


    for name, data in MIGRATION_PATTERNS.items():


        if any(
            pattern in text
            for pattern in data["patterns"]
        ):


            result["detections"].append(
                name
            )


            result["score"] += data["score"]







    # ------------------------------
    # Content
    # ------------------------------


    for name, patterns in CONTENT_PATTERNS.items():


        amount = count_patterns(
            text,
            patterns
        )


        result["content"][name] += amount







    # ------------------------------
    # Registration
    # ------------------------------


    for name, patterns in REGISTRATION_PATTERNS.items():


        amount = count_patterns(
            text,
            patterns
        )


        result["registration"][name] += amount







    # ------------------------------
    # Recipes
    # ------------------------------


    for name, patterns in RECIPE_PATTERNS.items():


        amount = count_patterns(
            text,
            patterns
        )


        result["recipes"][name] += amount







    # ------------------------------
    # GUI
    # ------------------------------


    for name, patterns in GUI_PATTERNS.items():


        amount = count_patterns(
            text,
            patterns
        )


        if amount:

            result["gui"][name] = amount







    # ------------------------------
    # Network
    # ------------------------------


    for name, patterns in NETWORK_PATTERNS.items():


        amount = count_patterns(
            text,
            patterns
        )


        if amount:

            result["network"][name] = amount







    # ------------------------------
    # Creative tabs
    # ------------------------------


    result["creative"] = count_patterns(
        text,
        CREATIVE_PATTERNS["CreativeTab"]
    )







    # ------------------------------
    # Client/server estimation
    # ------------------------------


    client = count_patterns(
        text,
        [
            "net/minecraft/client/",
            "Minecraft.getMinecraft"
        ]
    )


    server = count_patterns(
        text,
        [
            "net/minecraft/world/",
            "WorldServer",
            "MinecraftServer"
        ]
    )


    result["client_hits"] = client

    result["server_hits"] = server







    # ------------------------------
    # Methods
    # ------------------------------


    result["estimated_methods"] = int(
        text.count("(") / 5
    )







    # ------------------------------
    # Classes
    # ------------------------------


    classes = re.findall(
        r"(?:class|extends)\s+([A-Za-z0-9_]+)",
        text
    )


    result["important_classes"].extend(
        classes
    )



    return result







# ==========================================================
# Merge analyzer data
# ==========================================================


def merge_dict(target, source):


    for key, value in source.items():


        if isinstance(value, dict):


            for k, v in value.items():

                target[key][k] = (
                    target[key].get(k, 0)
                    + v
                )


        elif isinstance(value, int):

            target[key] += value







# ==========================================================
# JAR Scanner
# ==========================================================


def scan_jar(path, index, total):


    filename = os.path.basename(path)


    log(
        f"[{index}/{total}] {filename}"
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


            result["files"] = len(files)



            for file in files:


                if not file.endswith(
                    ".class"
                ):

                    continue



                result["class_files"] += 1



                packages.add(
                    os.path.dirname(file)
                )



                try:


                    data = jar.read(
                        file
                    ).decode(
                        "latin1",
                        errors="ignore"
                    )



                    scan = analyze_class(
                        data
                    )



                    result["score"] += scan["score"]


                    result["detections"].extend(
                        scan["detections"]
                    )


                    for key in [
                        "content",
                        "registration",
                        "recipes"
                    ]:


                        for name,value in scan[key].items():


                            result[key][name] += value



                    result["creative"] += scan["creative"]



                    result["estimated_methods"] += scan["estimated_methods"]



                    result["important_classes"].extend(
                        scan["important_classes"]
                    )



                    result["client_hits"] += scan["client_hits"]

                    result["server_hits"] += scan["server_hits"]



                    for key in [
                        "gui",
                        "network"
                    ]:


                        for name,value in scan[key].items():


                            result[key][name] = (

                                result[key].get(
                                    name,
                                    0
                                )
                                +
                                value

                            )


                except Exception:

                    continue



        result["packages"] = len(packages)



    except Exception as error:


        log(
            f"ERROR {filename}: {error}"
        )



    return result
# ==========================================================
# Difficulty calculation
# ==========================================================


def calculate_difficulty(result):


    score = result["score"]



    # Размер мода

    if result["class_files"] > 1000:

        score += 20

        result["reasons"].append(
            "Large amount of classes"
        )



    # Блоки

    blocks = result["registration"]["Blocks"]


    if blocks > 200:

        score += 15

        result["reasons"].append(
            "Many registered blocks"
        )



    elif blocks > 50:

        score += 5





    # TileEntities / машины

    tiles = result["registration"]["TileEntities"]


    if tiles > 50:

        score += 20

        result["reasons"].append(
            "Large machine system"
        )


    elif tiles > 10:

        score += 10





    # Рецепты

    recipes = sum(
        result["recipes"].values()
    )


    if recipes > 500:

        score += 15

        result["reasons"].append(
            "Large recipe system"
        )


    elif recipes > 100:

        score += 5





    # Сеть

    packets = sum(
        result["network"].values()
    )


    if packets > 50:

        score += 15

        result["reasons"].append(
            "Heavy networking"
        )





    # Клиентская часть

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


def detect_mod_type(result):


    types = []



    tech_score = (

        result["registration"]["TileEntities"]

        +

        result["registration"]["Blocks"]

        +

        result["recipes"]["Crafting"]

    )



    if tech_score > 50:

        types.append(
            "Technology"
        )



    if result["registration"]["WorldGenerators"] > 0:

        types.append(
            "World Generation"
        )



    if result["creative"] > 0:

        types.append(
            "Content Mod"
        )



    if not types:

        types.append(
            "Utility"
        )



    return types









# ==========================================================
# Cleanup
# ==========================================================


def cleanup_result(result):


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



    result["reasons"] = list(
        set(
            result["reasons"]
        )
    )



    total_side = (

        result["client_hits"]

        +

        result["server_hits"]

    )



    if total_side:


        result["client_percent"] = round(

            result["client_hits"]
            /
            total_side
            *
            100,

            1

        )



        result["server_percent"] = round(

            result["server_hits"]
            /
            total_side
            *
            100,

            1

        )



    return result







# ==========================================================
# Main scanner API
# ==========================================================


def scan_mods(jars):


    log(
        "Starting ClassScanner v4..."
    )



    result = {}



    total = len(jars)



    for index, jar in enumerate(
        jars,
        1
    ):


        scanned = scan_jar(

            jar,

            index,

            total

        )



        scanned = cleanup_result(
            scanned
        )



        calculate_difficulty(
            scanned
        )



        scanned["type"] = detect_mod_type(
            scanned
        )



        result[
            os.path.basename(jar)
        ] = scanned



        log(

            f"{scanned['file']}: "
            f"{scanned['difficulty']} "
            f"{scanned['score']}/100"

        )



    log(
        "ClassScanner v4 finished"
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



    for root, dirs, files in os.walk(folder):


        for file in files:


            if file.endswith(
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
            mod
        )

        print(
            "Difficulty:",
            info["difficulty"]
        )

        print(
            "Blocks:",
            info["registration"]["Blocks"]
        )

        print(
            "Items:",
            info["registration"]["Items"]
        )

        print(
            "Recipes:",
            sum(
                info["recipes"].values()
            )
        )