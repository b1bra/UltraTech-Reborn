# Inventory Tweaks

## Surface

```text
file: InventoryTweaks-1.7.10-Client.jar
path: C:\Users\Vasya\AppData\Roaming\.loliland\game-resources\clients\ultra_tech\main\mods\InventoryTweaks-1.7.10-Client.jar
name: Inventory Tweaks
modid: inventorytweaks
version: 1.59-dev-152-cf6e263
author: Unknown
size_mb: 0.17
forge: FMLCorePlugin: invtweaks.forge.asm.FMLPlugin
dependencies[0]: AccessTransformer
dependencies[1]: Forge
dependencies[2]: inventorytweaks
files: 88
class_files: 76
packages: 13
score: 100
difficulty: Hard
detections[0]: CoreMod / ASM
detections[1]: Old Forge API
detections[2]: Old Network
content.Blocks: 0
content.Items: 0
content.TileEntities: 0
content.Entities: 3
content_lists.Blocks: []
content_lists.Items: []
content_lists.TileEntities: []
content_lists.Entities[0]: ClientPlayerMP
content_lists.Entities[1]: Player
content_lists.Entities[2]: PlayerMP
content_sources.Blocks.class: 0
content_sources.Blocks.lang: 0
content_sources.Blocks.models: 0
content_sources.Blocks.blockstates: 0
content_sources.Items.class: 66
content_sources.Items.lang: 0
content_sources.Items.models: 0
registration.Blocks: 0
registration.Items: 0
registration.TileEntities: 0
registration.Entities: 0
registration.WorldGenerators: 0
recipes.Crafting: 0
recipes.Smelting: 0
recipes.Custom: 0
gui.Containers: 405
gui.GUI: 87
network.Packets: 37
creative: 0
important_classes: []
estimated_methods: 252
client_hits: 238
server_hits: 0
client_percent: 100.0
server_percent: 0.0
reasons[0]: Heavy client code
lang_entries: 250
models: 0
blockstates: 0
type[0]: Network Based
resources.textures: 0
resources.block_textures: 0
resources.item_textures: 0
resources.models: 0
resources.block_models: 0
resources.item_models: 0
resources.blockstates: 0
resources.languages: 3
resources.sounds: 0
resources.gui_resources: 0
resources.recipes: 0
resources.advancements: 0
resources.json_files: 0
resources.resource_files: 5
resources.total_resource_size_mb: 0.06
resources.important_paths[0]: assets/inventorytweaks/lang/ru_RU.lang
resources.important_paths[1]: assets/inventorytweaks/lang/uk_UA.lang
resources.important_paths[2]: assets/inventorytweaks/lang/en_US.lang
resources.large_files: []
resources.resource_complexity: 0
recipe_data.total_recipes: 0
recipe_data.json_recipes: 0
recipe_data.code_recipes: 0
recipe_data.crafting: 0
recipe_data.ore_recipes: 0
recipe_data.smelting: 0
recipe_data.special: 0
recipe_data.json_files: 0
recipe_data.class_files_scanned: 76
recipe_data.recipe_classes: []
No data
No data
recipe_data.recipe_paths: []
No data
No data
dependency_records[0].name: AccessTransformer
dependency_records[0].categories[0]: CoreMod
dependency_records[0].confidence: 90
dependency_records[0].sources[0]: META-INF/invtweaks_at.cfg
dependency_records[0].evidence[0]: access transformer file
dependency_records[0].evidence[1]: access transformer resource
dependency_records[1].name: Forge
dependency_records[1].categories[0]: CoreMod
dependency_records[1].categories[1]: Hidden
dependency_records[1].confidence: 92
dependency_records[1].sources[0]: com/gamerforea/invtweaks/InvTweaksHelper.class
dependency_records[1].sources[1]: com/gamerforea/invtweaks/api/IInfiniteContainer.class
dependency_records[1].sources[2]: invtweaks/InvTweaks.class
dependency_records[1].sources[3]: invtweaks/InvTweaksConfig.class
dependency_records[1].sources[4]: invtweaks/InvTweaksContainerManager.class
dependency_records[1].sources[5]: invtweaks/InvTweaksItemTree.class
dependency_records[1].sources[6]: invtweaks/InvTweaksItemTreeLoader.class
dependency_records[1].sources[7]: invtweaks/InvTweaksObfuscation.class
dependency_records[1].sources[8]: invtweaks/forge/ClientProxy.class
dependency_records[1].sources[9]: invtweaks/forge/CommonProxy.class
dependency_records[1].sources[10]: invtweaks/forge/ForgeClientTick.class
dependency_records[1].sources[11]: invtweaks/forge/InvTweaksMod.class
dependency_records[1].sources[12]: invtweaks/forge/ModGuiFactory.class
dependency_records[1].sources[13]: invtweaks/forge/asm/FMLPlugin.class
dependency_records[1].sources[14]: invtweaks/network/ITMessageToMessageCodec.class
dependency_records[1].sources[15]: invtweaks/network/handlers/ClickMessageHandler.class
dependency_records[1].sources[16]: invtweaks/network/handlers/SortingCompleteMessageHandler.class
dependency_records[1].evidence[0]: cpw/mods/fml/
dependency_records[1].evidence[1]: net/minecraftforge/, cpw/mods/fml/
dependency_records[2].name: inventorytweaks
dependency_records[2].categories[0]: Optional
dependency_records[2].confidence: 45
dependency_records[2].sources[0]: assets/inventorytweaks/DefaultConfig.dat
dependency_records[2].sources[1]: assets/inventorytweaks/ItemTree.xml
dependency_records[2].sources[2]: assets/inventorytweaks/lang/en_US.lang
dependency_records[2].sources[3]: assets/inventorytweaks/lang/ru_RU.lang
dependency_records[2].sources[4]: assets/inventorytweaks/lang/uk_UA.lang
dependency_records[2].evidence[0]: resource namespace
```


## Class Scanner

```text
file: InventoryTweaks-1.7.10-Client.jar
size_mb: 0.17
files: 88
class_files: 76
packages: 13
score: 100
difficulty: Hard
detections[0]: CoreMod / ASM
detections[1]: Old Forge API
detections[2]: Old Network
content.Blocks: 0
content.Items: 0
content.TileEntities: 0
content.Entities: 3
content_lists.Blocks: []
content_lists.Items: []
content_lists.TileEntities: []
content_lists.Entities[0]: ClientPlayerMP
content_lists.Entities[1]: Player
content_lists.Entities[2]: PlayerMP
content_sources.Blocks.class: 0
content_sources.Blocks.lang: 0
content_sources.Blocks.models: 0
content_sources.Blocks.blockstates: 0
content_sources.Items.class: 66
content_sources.Items.lang: 0
content_sources.Items.models: 0
registration.Blocks: 0
registration.Items: 0
registration.TileEntities: 0
registration.Entities: 0
registration.WorldGenerators: 0
recipes.Crafting: 0
recipes.Smelting: 0
recipes.Custom: 0
gui.Containers: 405
gui.GUI: 87
network.Packets: 37
creative: 0
important_classes: []
estimated_methods: 252
client_hits: 238
server_hits: 0
client_percent: 100.0
server_percent: 0.0
reasons[0]: Heavy client code
lang_entries: 250
models: 0
blockstates: 0
type[0]: Network Based
```


## Resource Scanner

```text
textures: 0
block_textures: 0
item_textures: 0
models: 0
block_models: 0
item_models: 0
blockstates: 0
languages: 3
sounds: 0
gui_resources: 0
recipes: 0
advancements: 0
json_files: 0
resource_files: 5
total_resource_size_mb: 0.06
important_paths[0]: assets/inventorytweaks/lang/ru_RU.lang
important_paths[1]: assets/inventorytweaks/lang/uk_UA.lang
important_paths[2]: assets/inventorytweaks/lang/en_US.lang
large_files: []
resource_complexity: 0
```


## Recipe Scanner

```text
total_recipes: 0
json_recipes: 0
code_recipes: 0
crafting: 0
ore_recipes: 0
smelting: 0
special: 0
json_files: 0
class_files_scanned: 76
recipe_classes: []
No data
No data
recipe_paths: []
```
