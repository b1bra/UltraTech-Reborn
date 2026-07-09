import os
import zipfile
import re
import traceback


VERSION = "5.0"


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


        def start_timer(self, name):
            pass


        def stop_timer(self, name):
            return 0


        def section(self, text):
            print("=" * 80)
            print(text)
            print("=" * 80)


        def log_class(self, name):
            self.debug(
                f"Class detected: {name}"
            )


        def log_resource(self, name, count):
            self.debug(
                f"Resource {name}: {count}"
            )


        def module_info(self, name, text):
            self.info(
                f"[{name}] {text}"
            )


        def module_debug(self, name, text):
            self.debug(
                f"[{name}] {text}"
            )


        def module_success(self, name, text):
            self.success(
                f"[{name}] {text}"
            )


        def module_error(self, name, text):
            self.error(
                f"[{name}] {text}"
            )


    logger = DummyLogger()



MODULE_NAME = "ClassScanner"





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
# Forge 1.7.10 migration patterns
# ==========================================================


MIGRATION_PATTERNS = {


    "CoreMod / ASM":
    {

        "patterns":

        [

            "IClassTransformer",

            "IFMLLoadingPlugin",

            "FMLCorePlugin",

            "FMLCorePluginContainsFMLMod",

            "org.objectweb.asm",

            "ClassTransformer",

            "transform("

        ],


        "score": 40

    },




    "Old Forge API":
    {

        "patterns":

        [

            "cpw/mods/fml",

            "net/minecraftforge",

            "MinecraftForge",

            "FMLPreInitializationEvent",

            "FMLInitializationEvent",

            "FMLPostInitializationEvent"

        ],


        "score": 10

    },




    "Old Registry":
    {

        "patterns":

        [

            "GameRegistry",

            "EntityRegistry",

            "LanguageRegistry",

            "OreDictionary",

            "VillagerRegistry"

        ],


        "score": 10

    },




    "Old Rendering":
    {

        "patterns":

        [

            "RenderBlocks",

            "ISimpleBlockRenderingHandler",

            "TileEntitySpecialRenderer",

            "RenderLiving",

            "RenderItem"

        ],


        "score": 15

    },




    "Old Network":
    {

        "patterns":

        [

            "Packet250CustomPayload",

            "SimpleNetworkWrapper",

            "NetworkRegistry",

            "IMessage",

            "IMessageHandler"

        ],


        "score": 15

    },




    "Old Event System":
    {

        "patterns":

        [

            "SubscribeEvent",

            "EventBus",

            "ForgeSubscribe"

        ],


        "score": 10

    }

}





# ==========================================================
# Registration patterns
# ==========================================================


REGISTRATION_PATTERNS = {


    "Blocks":

    [

        "GameRegistry.registerBlock",

        "registerBlock",

        "BlockRegistry",

        "new Block",

        "Block("

    ],




    "Items":

    [

        "GameRegistry.registerItem",

        "registerItem",

        "ItemRegistry",

        "new Item",

        "Item("

    ],




    "TileEntities":

    [

        "GameRegistry.registerTileEntity",

        "registerTileEntity",

        "TileEntity"

    ],




    "Entities":

    [

        "EntityRegistry.registerModEntity",

        "EntityRegistry.registerGlobalEntityID",

        "registerModEntity",

        "EntityList"

    ],




    "WorldGenerators":

    [

        "GameRegistry.registerWorldGenerator",

        "IWorldGenerator"

    ]

}






# ==========================================================
# Content detection
# ==========================================================


CONTENT_PATTERNS = {


    "Blocks":

    [

        "extends Block",

        "Block;",

        "Block "

    ],




    "Items":

    [

        "extends Item",

        "Item;",

        "Item "

    ],




    "TileEntities":

    [

        "extends TileEntity",

        "TileEntity;"

    ],




    "Entities":

    [

        "extends Entity",

        "EntityLiving",

        "EntityMob",

        "EntityAnimal"

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

        "ShapedOreRecipe",

        "ShapelessRecipe",

        "ShapelessOreRecipe"

    ],




    "Smelting":

    [

        "GameRegistry.addSmelting",

        "addSmelting"

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

        "GuiButton",

        "GuiTextField",

        "drawScreen"

    ],




    "Containers":

    [

        "Container",

        "Slot",

        "InventoryPlayer",

        "transferStackInSlot"

    ]

}






# ==========================================================
# Network patterns
# ==========================================================


NETWORK_PATTERNS = {


    "Packets":

    [

        "Packet",

        "IMessage",

        "Message",

        "PacketHandler"

    ],




    "Channels":

    [

        "SimpleNetworkWrapper",

        "NetworkRegistry",

        "registerMessage",

        "newSimpleChannel"

    ]

}






# ==========================================================
# Creative tabs
# ==========================================================


CREATIVE_PATTERNS = {


    "CreativeTab":

    [

        "setCreativeTab",

        "CreativeTabs",

        "new CreativeTabs"

    ]

}






# ==========================================================
# Registry advanced patterns
# ==========================================================


ADVANCED_REGISTRY_PATTERNS = {


    "Blocks":

    [

        "GameRegistry.register",

        "registerBlock",

        "setBlockName",

        "setUnlocalizedName",

        "setHardness",

        "setResistance"

    ],




    "Items":

    [

        "registerItem",

        "setItemName",

        "setUnlocalizedName",

        "setTextureName",

        "setMaxStackSize"

    ],




    "Entities":

    [

        "EntityRegistry",

        "EntityList",

        "registerGlobalEntityID",

        "registerModEntity"

    ],




    "Machines":

    [

        "TileEntity",

        "IInventory",

        "ISidedInventory",

        "ICrafting"

    ]

}







# ==========================================================
# Minecraft usage patterns
# ==========================================================


MINECRAFT_USAGE_PATTERNS = {


    "World":

    [

        "net/minecraft/world",

        "World",

        "WorldServer"

    ],




    "Player":

    [

        "EntityPlayer",

        "EntityPlayerMP",

        "EntityPlayerSP"

    ],




    "Inventory":

    [

        "IInventory",

        "InventoryPlayer",

        "Container"

    ],




    "NBT":

    [

        "NBTTagCompound",

        "NBTTagList",

        "NBTBase"

    ],




    "Network":

    [

        "Packet",

        "IMessage",

        "ByteBuf"

    ]

}







# ==========================================================
# Class importance detection
# ==========================================================


IMPORTANT_CLASS_PATTERNS = {


    "Main Mod Class":

    [

        "Mod(",

        "FMLPreInitializationEvent",

        "FMLInitializationEvent"

    ],




    "Proxy":

    [

        "CommonProxy",

        "ClientProxy",

        "ServerProxy"

    ],




    "Handler":

    [

        "Handler",

        "EventHandler",

        "PacketHandler"

    ],




    "Registry":

    [

        "Registry",

        "Register",

        "Init"

    ]

}






# ==========================================================
# Utility
# ==========================================================


def count_patterns(text, patterns):


    total = 0


    for pattern in patterns:


        total += text.count(
            pattern
        )


    return total






def find_classes(text):


    result = []


    patterns = [

        r"class\s+([A-Za-z0-9_$]+)",

        r"extends\s+([A-Za-z0-9_$]+)",

        r"implements\s+([A-Za-z0-9_$,\s]+)"

    ]



    for pattern in patterns:


        found = re.findall(

            pattern,

            text

        )


        for item in found:


            if "," in item:


                result.extend(

                    item.split(",")

                )

            else:


                result.append(

                    item

                )


    return result






# ==========================================================
# Result structure
# ==========================================================


def create_result(path):


    filename = os.path.basename(
        path
    )



    return {


        "file":

        filename,



        "size_mb":

        round(

            os.path.getsize(path)
            /
            (1024 * 1024),

            2

        ),



        "files": 0,

        "class_files": 0,

        "packages": 0,



        "score": 0,


        "difficulty":

        "Unknown",




        "detections":

        [],




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





        "advanced_registry":

        {


            "Blocks": 0,

            "Items": 0,

            "Entities": 0,

            "Machines": 0

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





        "minecraft_usage":

        {},





        "important_classes":

        [],





        "estimated_methods":

        0,





        "client_hits":

        0,



        "server_hits":

        0,




        "client_percent":

        0,



        "server_percent":

        0,




        "reasons":

        []

    }
# ==========================================================
# Class analyzer
# ==========================================================


def analyze_class(text):


    result = {


        "score": 0,


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



        "advanced_registry":

        {

            "Blocks": 0,

            "Items": 0,

            "Entities": 0,

            "Machines": 0

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



        "minecraft_usage": {},



        "important_classes": [],



        "estimated_methods": 0,



        "client_hits": 0,



        "server_hits": 0

    }






    # ======================================================
    # Migration score
    # ======================================================


    for name, data in MIGRATION_PATTERNS.items():


        found = False


        for pattern in data["patterns"]:


            if pattern in text:


                found = True

                break



        if found:


            result["detections"].append(

                name

            )


            result["score"] += data["score"]






    # ======================================================
    # Content detection
    # ======================================================


    for name, patterns in CONTENT_PATTERNS.items():


        amount = count_patterns(

            text,

            patterns

        )


        result["content"][name] += amount






    # ======================================================
    # Registration detection
    # ======================================================


    for name, patterns in REGISTRATION_PATTERNS.items():


        amount = count_patterns(

            text,

            patterns

        )


        result["registration"][name] += amount






    # ======================================================
    # Advanced registration
    # ======================================================


    for name, patterns in ADVANCED_REGISTRY_PATTERNS.items():


        amount = count_patterns(

            text,

            patterns

        )


        result["advanced_registry"][name] += amount






    # ======================================================
    # Recipes
    # ======================================================


    for name, patterns in RECIPE_PATTERNS.items():


        amount = count_patterns(

            text,

            patterns

        )


        result["recipes"][name] += amount






    # ======================================================
    # GUI
    # ======================================================


    for name, patterns in GUI_PATTERNS.items():


        amount = count_patterns(

            text,

            patterns

        )


        if amount:


            result["gui"][name] = amount






    # ======================================================
    # Network
    # ======================================================


    for name, patterns in NETWORK_PATTERNS.items():


        amount = count_patterns(

            text,

            patterns

        )


        if amount:


            result["network"][name] = amount






    # ======================================================
    # Creative tabs
    # ======================================================


    result["creative"] = count_patterns(

        text,

        CREATIVE_PATTERNS["CreativeTab"]

    )






    # ======================================================
    # Minecraft usage
    # ======================================================


    for name, patterns in MINECRAFT_USAGE_PATTERNS.items():


        amount = count_patterns(

            text,

            patterns

        )


        if amount:


            result["minecraft_usage"][name] = amount






    # ======================================================
    # Important classes
    # ======================================================


    classes = find_classes(

        text

    )


    result["important_classes"].extend(

        classes

    )



    for name, patterns in IMPORTANT_CLASS_PATTERNS.items():


        for pattern in patterns:


            if pattern in text:


                result["important_classes"].append(

                    name

                )


                break






    # ======================================================
    # Client / Server estimation
    # ======================================================


    client_patterns = [


        "net/minecraft/client",


        "Minecraft.getMinecraft",


        "GuiScreen",


        "Render",


        "ModelBase"


    ]



    server_patterns = [


        "WorldServer",


        "MinecraftServer",


        "DedicatedServer",


        "TileEntity",


        "IWorldGenerator"


    ]



    result["client_hits"] = count_patterns(

        text,

        client_patterns

    )



    result["server_hits"] = count_patterns(

        text,

        server_patterns

    )






    # ======================================================
    # Method estimation
    # ======================================================


    result["estimated_methods"] = int(

        text.count("(")

        /

        5

    )







    return result
# ==========================================================
# Merge analyzer data
# ==========================================================


def merge_dict(target, source):


    for key, value in source.items():


        if isinstance(value, dict):


            if key not in target:


                target[key] = {}



            for sub_key, sub_value in value.items():


                target[key][sub_key] = (

                    target[key].get(

                        sub_key,

                        0

                    )

                    +

                    sub_value

                )



        elif isinstance(value, int):


            target[key] = (

                target.get(

                    key,

                    0

                )

                +

                value

            )






# ==========================================================
# JAR Scanner
# ==========================================================


def scan_jar(path, index, total):


    filename = os.path.basename(

        path

    )



    logger.section(

        f"Scanning {filename}"

    )



    logger.start_timer(

        f"jar_{filename}"

    )



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



            result["files"] = len(

                files

            )



            logger.log_file = getattr(

                logger,

                "log_file",

                lambda *x: None

            )





            for file in files:



                if not file.endswith(

                    ".class"

                ):


                    continue




                result["class_files"] += 1



                package = os.path.dirname(

                    file

                )



                if package:


                    packages.add(

                        package

                    )





                try:



                    data = jar.read(

                        file

                    )



                    text = data.decode(

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





                    for section in [

                        "content",

                        "registration",

                        "advanced_registry",

                        "recipes"

                    ]:



                        for name, value in scan[section].items():



                            result[section][name] += value






                    result["creative"] += (

                        scan["creative"]

                    )





                    result["estimated_methods"] += (

                        scan["estimated_methods"]

                    )





                    result["important_classes"].extend(

                        scan["important_classes"]

                    )





                    result["client_hits"] += (

                        scan["client_hits"]

                    )



                    result["server_hits"] += (

                        scan["server_hits"]

                    )






                    for section in [

                        "gui",

                        "network",

                        "minecraft_usage"

                    ]:



                        for name, value in scan[section].items():



                            result[section][name] = (

                                result[section].get(

                                    name,

                                    0

                                )

                                +

                                value

                            )





                    if scan["important_classes"]:


                        for cls in scan["important_classes"]:


                            try:


                                logger.log_class(

                                    cls

                                )


                            except Exception:


                                pass





                except Exception as error:


                    log_debug(

                        f"Class read error {file}: {error}"

                    )



                    continue






        result["packages"] = len(

            packages

        )





    except Exception as error:



        logger.log_exception_context(

            MODULE_NAME,

            "scan_jar",

            error

        )



    finally:



        logger.stop_timer(

            f"jar_{filename}"

        )



        logger.success(

            f"Finished {filename}"

        )





    return result
# ==========================================================
# Difficulty calculation
# ==========================================================


def calculate_difficulty(result):


    score = result["score"]



    # Classes amount

    if result["class_files"] > 1000:


        score += 20


        result["reasons"].append(

            "Large amount of classes"

        )





    # Blocks

    blocks = (

        result["registration"]["Blocks"]

        +

        result["content"]["Blocks"]

    )



    if blocks > 200:


        score += 15


        result["reasons"].append(

            "Many blocks"

        )


    elif blocks > 50:


        score += 5






    # Items

    items = (

        result["registration"]["Items"]

        +

        result["content"]["Items"]

    )



    if items > 500:


        score += 15


        result["reasons"].append(

            "Large item system"

        )







    # Machines

    machines = (

        result["registration"]["TileEntities"]

        +

        result["advanced_registry"]["Machines"]

    )



    if machines > 50:


        score += 20


        result["reasons"].append(

            "Large machine system"

        )


    elif machines > 10:


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


    elif recipes > 100:


        score += 5






    # Network

    network = sum(

        result["network"].values()

    )



    if network > 50:


        score += 15


        result["reasons"].append(

            "Heavy networking"

        )






    # Client side

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





    if (

        result["content"]["Blocks"]

        +

        result["content"]["Items"]

    ) > 50:


        types.append(

            "Content Mod"

        )





    if result["network"]:


        types.append(

            "Network Heavy"

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





    return result







# ==========================================================
# Main scanner API
# ==========================================================


def scan_mods(jars):


    logger.banner(

        "ClassScanner v5"

    )



    logger.start_timer(

        "class_scan"

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





            logger.success(

                f"{scanned['file']} | "

                f"{scanned['difficulty']} | "

                f"{scanned['score']}/100"

            )




        except Exception as error:



            logger.log_exception_context(

                MODULE_NAME,

                "scan_mods",

                error

            )





    logger.stop_timer(

        "class_scan"

    )



    logger.success(

        "ClassScanner v5 finished"

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


            if file.endswith(".jar"):


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

            "Score:",

            info["score"]

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

            "Entities:",

            info["registration"]["Entities"]

        )


        print(

            "Recipes:",

            sum(

                info["recipes"].values()

            )

        )