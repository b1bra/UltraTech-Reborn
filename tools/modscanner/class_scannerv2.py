import os
import zipfile
import re


VERSION = "2"



def log(text):
    print(f"[ClassScanner v{VERSION}] {text}")



# =====================================
# Patterns
# =====================================

PATTERNS = {


    "CoreMod / ASM": {

        "words": [
            "IClassTransformer",
            "IFMLLoadingPlugin",
            "FMLCorePlugin",
            "CorePlugin"
        ],

        "score": 40

    },


    "Old Forge": {

        "words": [
            "cpw.mods.fml",
            "net.minecraftforge"
        ],

        "score": 15

    },


    "Registry System": {

        "words": [
            "GameRegistry",
            "LanguageRegistry",
            "EntityRegistry"
        ],

        "score": 10

    },


    "Network System": {

        "words": [
            "NetworkRegistry",
            "Packet250CustomPayload"
        ],

        "score": 15

    }

}





API_PATTERNS = {


    "Forge":

    [
        "net.minecraftforge",
        "cpw.mods.fml"
    ],


    "Events":

    [
        "SubscribeEvent",
        "Event"
    ],


    "Registry":

    [
        "GameRegistry",
        "OreDictionary"
    ],


    "Network":

    [
        "SimpleNetworkWrapper",
        "Packet"
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
        "generate"
    ]

}




CONTENT_PATTERNS = {


    "Blocks":

    [
        "net.minecraft.block.Block"
    ],


    "Items":

    [
        "net.minecraft.item.Item"
    ],


    "TileEntities":

    [
        "net.minecraft.tileentity.TileEntity"
    ],


    "Entities":

    [
        "net.minecraft.entity.Entity"
    ]

}




MINECRAFT_PATTERNS = {


    "Blocks":

    [
        "net.minecraft.block"
    ],


    "Items":

    [
        "net.minecraft.item"
    ],


    "World":

    [
        "net.minecraft.world"
    ],


    "Client":

    [
        "net.minecraft.client"
    ],


    "Entities":

    [
        "net.minecraft.entity"
    ],


    "TileEntities":

    [
        "net.minecraft.tileentity"
    ]

}




# =====================================
# Analysis
# =====================================

def scan_text(text):


    result = {


        "detections": [],

        "score": 0,

        "api": {},

        "content": {},

        "minecraft": {},

        "client_hits": 0,

        "server_hits": 0,

        "methods_estimate": 0

    }



    for category, data in PATTERNS.items():


        for word in data["words"]:


            if word in text:


                result["detections"].append(
                    category + ": " + word
                )


                result["score"] += data["score"]




    for category, words in API_PATTERNS.items():


        count = 0


        for word in words:


            count += text.count(
                word
            )


        if count:

            result["api"][category] = count




    for category, words in CONTENT_PATTERNS.items():


        count = 0


        for word in words:

            count += text.count(
                word
            )


        result["content"][category] = count




    for category, words in MINECRAFT_PATTERNS.items():


        count = 0


        for word in words:

            count += text.count(
                word
            )


        result["minecraft"][category] = count



        if category == "Client":

            result["client_hits"] += count


        else:

            result["server_hits"] += count




    # Примерная оценка методов

    result["methods_estimate"] += (
        text.count("(")
        /
        5
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
            (1024*1024),
            2
        ),


        "files": 0,

        "class_files": 0,

        "packages": 0,


        "detections": [],


        "score": 0,


        "difficulty": "Unknown",


        "api_usage": {},


        "content": {},


        "minecraft_usage": {},


        "client_percent": 0,


        "server_percent": 0,


        "estimated_methods": 0,


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


                    scan = scan_text(
                        data
                    )



                    result["score"] += scan["score"]



                    result["detections"].extend(
                        scan["detections"]
                    )



                    for key,value in scan["api"].items():

                        result["api_usage"][key] = (
                            result["api_usage"].get(
                                key,
                                0
                            )
                            +
                            value
                        )



                    for key,value in scan["content"].items():

                        result["content"][key] = (
                            result["content"].get(
                                key,
                                0
                            )
                            +
                            value
                        )



                    for key,value in scan["minecraft"].items():

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
                        scan["methods_estimate"]
                    )



                except:

                    pass





        result["packages"] = len(
            packages
        )



        result["detections"] = list(
            set(
                result["detections"]
            )
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



        # Difficulty

        if result["score"] >= 70:

            result["difficulty"] = "Hard"


        elif result["score"] >= 30:

            result["difficulty"] = "Normal"


        else:

            result["difficulty"] = "Easy"




        # Reasons

        if result["score"] >= 70:

            result["reasons"].append(
                "Large amount of legacy code"
            )


        if "CoreMod / ASM" in str(result["detections"]):

            result["reasons"].append(
                "ASM/CoreMod usage"
            )


        if result["content"].get(
            "TileEntities",
            0
        ) > 20:

            result["reasons"].append(
                "Many TileEntities"
            )



        if result["client_percent"] > 40:

            result["reasons"].append(
                "Heavy client rendering"
            )



    except Exception as error:


        log(
            f"ERROR {filename}: {error}"
        )



    log(
        f" -> {result['difficulty']} ({result['score']}/100)"
    )


    return result





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


        result[
            os.path.basename(jar)
        ] = scan_jar(
            jar,
            index,
            total
        )



    log(
        "Extended class analysis complete"
    )


    return result