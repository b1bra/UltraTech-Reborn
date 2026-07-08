import os
import zipfile
import re


VERSION = "3"


def log(text):
    print(f"[ClassScanner v{VERSION}] {text}")



# =====================================
# Migration score patterns
# =====================================

PATTERNS = {

    "CoreMod / ASM":
    {
        "words":
        [
            "IClassTransformer",
            "IFMLLoadingPlugin",
            "FMLCorePlugin",
            "ClassTransformer",
            "ASM"
        ],
        "score": 40
    },


    "Old Forge":
    {
        "words":
        [
            "cpw/mods/fml",
            "net/minecraftforge"
        ],
        "score": 15
    },


    "Old Registry":
    {
        "words":
        [
            "GameRegistry",
            "EntityRegistry",
            "LanguageRegistry"
        ],
        "score": 10
    },


    "Old Network":
    {
        "words":
        [
            "Packet250CustomPayload",
            "SimpleNetworkWrapper",
            "NetworkRegistry"
        ],
        "score": 15
    },


    "Old Rendering":
    {
        "words":
        [
            "ISimpleBlockRenderingHandler",
            "TileEntitySpecialRenderer",
            "RenderBlocks"
        ],
        "score": 15
    }

}




# =====================================
# API detection
# =====================================

API_PATTERNS = {

    "Forge":
    [
        "net/minecraftforge",
        "cpw/mods/fml"
    ],


    "Events":
    [
        "SubscribeEvent",
        "EventBus",
        "Event"
    ],


    "Registry":
    [
        "GameRegistry",
        "EntityRegistry",
        "OreDictionary"
    ],


    "Network":
    [
        "Packet",
        "SimpleNetworkWrapper",
        "NetworkRegistry"
    ],


    "Rendering":
    [
        "Render",
        "Model",
        "Texture"
    ],


    "World Generation":
    [
        "WorldGenerator",
        "generate",
        "Biome"
    ]

}
# =====================================
# Content detection
# =====================================

CONTENT_PATTERNS = {

    "Blocks":
    [
        "net/minecraft/block/",
        "Block;",
        "extends Block"
    ],


    "Items":
    [
        "net/minecraft/item/",
        "Item;",
        "extends Item"
    ],


    "TileEntities":
    [
        "net/minecraft/tileentity/",
        "TileEntity;",
        "extends TileEntity"
    ],


    "Entities":
    [
        "net/minecraft/entity/",
        "Entity;",
        "extends Entity"
    ]

}




# =====================================
# Registration detection
# =====================================

REGISTRATION_PATTERNS = {

    "Registered Blocks":
    [
        "registerBlock",
        "GameRegistry.registerBlock"
    ],


    "Registered Items":
    [
        "registerItem",
        "GameRegistry.registerItem"
    ],


    "Registered TileEntities":
    [
        "registerTileEntity",
        "GameRegistry.registerTileEntity"
    ],


    "World Generators":
    [
        "registerWorldGenerator",
        "GameRegistry.registerWorldGenerator"
    ],


    "Registered Entities":
    [
        "registerEntity",
        "EntityRegistry.registerEntity"
    ]

}





# =====================================
# Recipe detection
# =====================================

RECIPE_PATTERNS = {

    "Crafting":
    [
        "addRecipe",
        "ShapedRecipe",
        "ShapedOreRecipe",
        "ShapelessRecipe",
        "ShapelessOreRecipe"
    ],


    "Smelting":
    [
        "addSmelting",
        "FurnaceRecipes"
    ],


    "Custom Recipes":
    [
        "IRecipe",
        "Recipe"
    ]

}





# =====================================
# GUI detection
# =====================================

GUI_PATTERNS = {

    "Screens":
    [
        "GuiScreen",
        "GuiContainer",
        "GuiButton"
    ],


    "Containers":
    [
        "Container",
        "Slot",
        "IContainerListener"
    ],


    "GuiHandler":
    [
        "GuiHandler",
        "IGuiHandler"
    ]

}





# =====================================
# Network detection
# =====================================

NETWORK_PATTERNS = {

    "Packets":
    [
        "Packet",
        "PacketHandler",
        "Message"
    ],


    "Channels":
    [
        "newSimpleChannel",
        "registerMessage",
        "channel"
    ]

}





# =====================================
# Minecraft class usage
# =====================================

MINECRAFT_PATTERNS = {

    "Blocks":
    [
        "net/minecraft/block/"
    ],


    "Items":
    [
        "net/minecraft/item/"
    ],


    "World":
    [
        "net/minecraft/world/"
    ],


    "Client":
    [
        "net/minecraft/client/"
    ],


    "Entities":
    [
        "net/minecraft/entity/"
    ],


    "TileEntities":
    [
        "net/minecraft/tileentity/"
    ]

}
# =====================================
# Utility counter
# =====================================

def count_patterns(text, patterns):

    count = 0


    for pattern in patterns:


        count += len(
            re.findall(
                re.escape(pattern),
                text
            )
        )


    return count





# =====================================
# Empty analysis structure
# =====================================

def create_analysis():

    return {

        "detections": [],

        "score": 0,


        "api_usage": {},


        "content": {},


        "registration": {},


        "recipes": {},


        "gui": {},


        "network": {},


        "minecraft_usage": {},


        "client_hits": 0,


        "server_hits": 0,


        "estimated_methods": 0,


        "important_classes": [],


        "reasons": []

    }





# =====================================
# Class text analysis
# =====================================

def analyze_class(text):


    result = create_analysis()



    # -----------------------------
    # Migration score
    # -----------------------------


    for name, data in PATTERNS.items():


        for word in data["words"]:


            if word in text:


                result["detections"].append(
                    name
                )


                result["score"] += data["score"]


                break





    # -----------------------------
    # API usage
    # -----------------------------


    for name, patterns in API_PATTERNS.items():


        amount = count_patterns(
            text,
            patterns
        )


        if amount:


            result["api_usage"][name] = amount






    # -----------------------------
    # Content
    # -----------------------------


    for name, patterns in CONTENT_PATTERNS.items():


        amount = count_patterns(
            text,
            patterns
        )


        result["content"][name] = amount






    # -----------------------------
    # Registration
    # -----------------------------


    for name, patterns in REGISTRATION_PATTERNS.items():


        amount = count_patterns(
            text,
            patterns
        )


        result["registration"][name] = amount






    # -----------------------------
    # Recipes
    # -----------------------------


    for name, patterns in RECIPE_PATTERNS.items():


        amount = count_patterns(
            text,
            patterns
        )


        result["recipes"][name] = amount






    # -----------------------------
    # GUI
    # -----------------------------


    for name, patterns in GUI_PATTERNS.items():


        amount = count_patterns(
            text,
            patterns
        )


        result["gui"][name] = amount






    # -----------------------------
    # Network
    # -----------------------------


    for name, patterns in NETWORK_PATTERNS.items():


        amount = count_patterns(
            text,
            patterns
        )


        result["network"][name] = amount






    # -----------------------------
    # Minecraft usage
    # -----------------------------


    for name, patterns in MINECRAFT_PATTERNS.items():


        amount = count_patterns(
            text,
            patterns
        )


        result["minecraft_usage"][name] = amount



        if name == "Client":

            result["client_hits"] += amount


        else:

            result["server_hits"] += amount






    # -----------------------------
    # Methods estimation
    # -----------------------------


    result["estimated_methods"] = int(

        text.count("(")
        /
        5

    )





    # -----------------------------
    # Important classes
    # -----------------------------


    class_match = re.findall(

        r"(?:class|extends)\s+([A-Za-z0-9_]+)",

        text

    )


    for cls in class_match:


        if cls not in result["important_classes"]:

            result["important_classes"].append(
                cls
            )



    return result
# =====================================
# JAR scanner
# =====================================

def scan_jar(path, index, total):


    filename = os.path.basename(
        path
    )


    log(
        f"[{index}/{total}] {filename}"
    )



    result = {


        "size_mb": round(
            os.path.getsize(path)
            /
            (1024 * 1024),
            2
        ),


        "files": 0,


        "class_files": 0,


        "packages": 0,


        "score": 0,


        "difficulty": "Unknown",


        "detections": [],


        "api_usage": {},


        "content": {},


        "registration": {},


        "recipes": {},


        "gui": {},


        "network": {},


        "minecraft_usage": {},


        "client_percent": 0,


        "server_percent": 0,


        "estimated_methods": 0,


        "important_classes": [],


        "reasons": []

    }





    packages = set()


    client = 0

    server = 0



    try:


        with zipfile.ZipFile(
            path,
            "r"
        ) as jar:



            files = jar.namelist()



            result["files"] = len(
                files
            )



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



                    result["score"] += (
                        scan["score"]
                    )



                    result["detections"].extend(
                        scan["detections"]
                    )



                    # Merge dictionaries


                    for key, value in scan["api_usage"].items():

                        result["api_usage"][key] = (

                            result["api_usage"].get(
                                key,
                                0
                            )
                            +
                            value

                        )



                    for key, value in scan["content"].items():

                        result["content"][key] = (

                            result["content"].get(
                                key,
                                0
                            )
                            +
                            value

                        )



                    for key, value in scan["registration"].items():

                        result["registration"][key] = (

                            result["registration"].get(
                                key,
                                0
                            )
                            +
                            value

                        )



                    for key, value in scan["recipes"].items():

                        result["recipes"][key] = (

                            result["recipes"].get(
                                key,
                                0
                            )
                            +
                            value

                        )



                    for key, value in scan["gui"].items():

                        result["gui"][key] = (

                            result["gui"].get(
                                key,
                                0
                            )
                            +
                            value

                        )



                    for key, value in scan["network"].items():

                        result["network"][key] = (

                            result["network"].get(
                                key,
                                0
                            )
                            +
                            value

                        )



                    for key, value in scan["minecraft_usage"].items():

                        result["minecraft_usage"][key] = (

                            result["minecraft_usage"].get(
                                key,
                                0
                            )
                            +
                            value

                        )



                    client += scan["client_hits"]


                    server += scan["server_hits"]



                    result["estimated_methods"] += (

                        scan["estimated_methods"]

                    )



                    result["important_classes"].extend(

                        scan["important_classes"]

                    )



                except Exception:


                    pass






        result["packages"] = len(
            packages
        )



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



        side = client + server



        if side:


            result["client_percent"] = round(

                client / side * 100,

                1

            )


            result["server_percent"] = round(

                server / side * 100,

                1

            )



        result["estimated_methods"] = int(

            result["estimated_methods"]

        )



        calculate_difficulty(
            result
        )



    except Exception as error:


        log(
            f"ERROR {filename}: {error}"
        )



    log(

        f"{filename}: {result['difficulty']} ({result['score']})"

    )



    return result
# =====================================
# Difficulty calculation
# =====================================

def calculate_difficulty(result):


    score = result["score"]



    # Размер проекта

    if result["class_files"] if "class_files" in result else 0:

        if result["class_files"] > 1000:

            score += 20



    # TileEntities

    tiles = result["content"].get(
        "TileEntities",
        0
    )


    if tiles > 50:

        score += 20

        result["reasons"].append(
            "Large amount of TileEntities"
        )


    elif tiles > 20:

        score += 10

        result["reasons"].append(
            "Many TileEntities"
        )





    # Recipes

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

        result["reasons"].append(
            "Many recipes"
        )





    # GUI

    gui_amount = sum(
        result["gui"].values()
    )


    if gui_amount > 100:

        score += 10

        result["reasons"].append(
            "Large GUI system"
        )





    # Network

    packets = sum(
        result["network"].values()
    )


    if packets > 50:

        score += 15

        result["reasons"].append(
            "Heavy network usage"
        )





    # Rendering

    if result["client_percent"] > 40:

        score += 10

        result["reasons"].append(
            "Heavy client rendering"
        )





    # ASM

    if "CoreMod / ASM" in result["detections"]:

        result["reasons"].append(
            "ASM/CoreMod detected"
        )





    # Final score

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






# =====================================
# Mod type detection
# =====================================

def detect_mod_type(result):


    types = []



    # Technology

    tech_score = (

        result["content"].get(
            "TileEntities",
            0
        )

        +

        sum(
            result["recipes"].values()
        )

        +

        result["network"].get(
            "Packets",
            0
        )

    )



    if tech_score > 50:

        types.append(
            "Technology"
        )





    # World generation

    if (

        result["api_usage"].get(
            "World Generation",
            0
        )

        >

        0

    ):

        types.append(
            "World Generation"
        )





    # Magic

    magic_words = [

        "Aspect",

        "Research",

        "Thaumcraft",

        "Magic"

    ]



    # Placeholder for future API scans

    if any(

        word in str(
            result
        )

        for word in magic_words

    ):

        types.append(
            "Magic"
        )





    if not types:

        types.append(
            "Utility"
        )



    return types







# =====================================
# External API
# =====================================

def scan_mods(jars):


    log(
        "Starting extended class analysis..."
    )



    result = {}


    total = len(
        jars
    )



    for index, jar in enumerate(
        jars,
        1
    ):


        scanned = scan_jar(
            jar,
            index,
            total
        )



        scanned["type"] = detect_mod_type(
            scanned
        )



        result[
            os.path.basename(jar)
        ] = scanned





    log(
        "Class analysis complete"
    )



    return result
# =====================================
# Cleanup
# =====================================

def cleanup_result(result):


    result["detections"] = list(
        set(
            result["detections"]
        )
    )



    result["reasons"] = list(
        set(
            result["reasons"]
        )
    )



    result["important_classes"] = list(
        set(
            result["important_classes"]
        )
    )



    return result






# =====================================
# Fixed JAR scanner wrapper
# =====================================

def scan_jar(path, index, total):


    filename = os.path.basename(
        path
    )


    log(
        f"[{index}/{total}] {filename}"
    )



    result = {


        "file": filename,


        "size_mb": round(
            os.path.getsize(path)
            /
            (1024 * 1024),
            2
        ),


        "files": 0,


        "class_files": 0,


        "packages": 0,


        "score": 0,


        "difficulty": "Unknown",


        "type": [],


        "detections": [],


        "api_usage": {},


        "content": {},


        "registration": {},


        "recipes": {},


        "gui": {},


        "network": {},


        "minecraft_usage": {},


        "client_percent": 0,


        "server_percent": 0,


        "estimated_methods": 0,


        "important_classes": [],


        "reasons": []

    }





    packages = set()



    client = 0

    server = 0





    try:


        with zipfile.ZipFile(
            path,
            "r"
        ) as jar:



            files = jar.namelist()



            result["files"] = len(
                files
            )



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


                    text = jar.read(
                        file
                    ).decode(
                        "latin1",
                        errors="ignore"
                    )



                    scan = analyze_class(
                        text
                    )



                    result["score"] += scan["score"]



                    result["detections"].extend(
                        scan["detections"]
                    )



                    for key in [

                        "api_usage",

                        "content",

                        "registration",

                        "recipes",

                        "gui",

                        "network",

                        "minecraft_usage"

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



                    client += scan["client_hits"]

                    server += scan["server_hits"]



                    result["estimated_methods"] += (

                        scan["estimated_methods"]

                    )



                    result["important_classes"].extend(

                        scan["important_classes"]

                    )



                except Exception:

                    continue





        result["packages"] = len(
            packages
        )



        total_side = client + server



        if total_side:


            result["client_percent"] = round(

                client / total_side * 100,

                1

            )


            result["server_percent"] = round(

                server / total_side * 100,

                1

            )



        result["estimated_methods"] = int(

            result["estimated_methods"]

        )



        result = cleanup_result(
            result
        )



        calculate_difficulty(
            result
        )



        result["type"] = detect_mod_type(
            result
        )



    except Exception as error:


        log(
            f"ERROR {filename}: {error}"
        )



    log(

        f"{filename}: {result['difficulty']} {result['score']}/100"

    )



    return result






# =====================================
# Main API
# =====================================

def scan_mods(jars):


    log(
        "Starting ClassScanner v3..."
    )


    result = {}



    total = len(
        jars
    )



    for index, jar in enumerate(
        jars,
        1
    ):


        scanned = scan_jar(
            jar,
            index,
            total
        )



        result[
            os.path.basename(jar)
        ] = scanned



    log(
        "ClassScanner v3 finished"
    )



    return result