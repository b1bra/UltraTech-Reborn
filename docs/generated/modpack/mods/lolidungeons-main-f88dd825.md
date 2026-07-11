# LoliDungeons

## Surface

```text
file: lolidungeons-main-f88dd825.jar
path: C:\Users\Vasya\AppData\Roaming\.loliland\game-resources\clients\ultra_tech\main\mods\lolidungeons-main-f88dd825.jar
name: LoliDungeons
modid: lolidungeons
version: 0.1.0
author: Unknown
size_mb: 0.13
forge: FMLCorePlugin: net.loliland.lolidungeons.asm.CoremodPlugin
dependencies[0]: AccessTransformer
dependencies[1]: Forge
dependencies[2]: LaunchWrapper
dependencies[3]: lolidungeons
files: 89
class_files: 75
packages: 24
score: 100
difficulty: Hard
detections[0]: CoreMod / ASM
detections[1]: Old Forge API
detections[2]: Old Registry
content.Blocks: 1
content.Items: 0
content.TileEntities: 4
content.Entities: 14
content_lists.Blocks[0]: dungeonspawner
content_lists.Items: []
content_lists.TileEntities[0]: S35PacketUpdate
content_lists.TileEntities[1]: createNew
content_lists.TileEntities[2]: get
content_lists.TileEntities[3]: register
content_lists.Entities[0]: ByUUID
content_lists.Entities[1]: ClientPlayerMP
content_lists.Entities[2]: Constructing
content_lists.Entities[3]: Construction
content_lists.Entities[4]: DungeonHandler
content_lists.Entities[5]: DungeonSpawner
content_lists.Entities[6]: Event
content_lists.Entities[7]: List
content_lists.Entities[8]: Living
content_lists.Entities[9]: LivingData
content_lists.Entities[10]: Player
content_lists.Entities[11]: PlayerMP
content_lists.Entities[12]: Properties
content_lists.Entities[13]: Zombie
content_sources.Blocks.class: 14
content_sources.Blocks.lang: 1
content_sources.Blocks.models: 0
content_sources.Blocks.blockstates: 0
content_sources.Items.class: 5
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
gui.GUI: 14
gui.Containers: 3
network.Packets: 75
network.Channels: 1
creative: 3
important_classes: []
estimated_methods: 197
client_hits: 63
server_hits: 49
client_percent: 56.2
server_percent: 43.8
reasons[0]: Heavy networking
reasons[1]: Heavy client code
lang_entries: 9
models: 0
blockstates: 0
type[0]: Network Based
resources.textures: 5
resources.block_textures: 1
resources.item_textures: 1
resources.models: 0
resources.block_models: 0
resources.item_models: 0
resources.blockstates: 0
resources.languages: 1
resources.sounds: 0
resources.gui_resources: 0
resources.recipes: 0
resources.advancements: 0
resources.json_files: 1
resources.resource_files: 5
resources.total_resource_size_mb: 0.0
resources.important_paths[0]: assets/lolidungeons/textures/blocks/dungeon_spawner.png.mcmeta
resources.important_paths[1]: assets/lolidungeons/textures/blocks/dungeon_spawner.png
resources.important_paths[2]: assets/lolidungeons/textures/items/dungeon_spawner.png
resources.important_paths[3]: assets/lolidungeons/lang/en_US.lang
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
recipe_data.class_files_scanned: 75
recipe_data.recipe_classes: []
No data
No data
recipe_data.recipe_paths: []
No data
No data
dependency_records[0].name: AccessTransformer
dependency_records[0].categories[0]: CoreMod
dependency_records[0].confidence: 90
dependency_records[0].sources[0]: META-INF/lolidungeons_at.cfg
dependency_records[0].evidence[0]: access transformer file
dependency_records[0].evidence[1]: access transformer resource
dependency_records[1].name: Forge
dependency_records[1].categories[0]: CoreMod
dependency_records[1].categories[1]: Hidden
dependency_records[1].confidence: 92
dependency_records[1].sources[0]: net/loliland/lolidungeons/EventListener.class
dependency_records[1].sources[1]: net/loliland/lolidungeons/LoliDungeons$configDir$2.class
dependency_records[1].sources[2]: net/loliland/lolidungeons/LoliDungeons$lolimodLoaded$2.class
dependency_records[1].sources[3]: net/loliland/lolidungeons/LoliDungeons$mswLoaded$2.class
dependency_records[1].sources[4]: net/loliland/lolidungeons/LoliDungeons$preInit$$inlined$load$default$1.class
dependency_records[1].sources[5]: net/loliland/lolidungeons/LoliDungeons$preInit$$inlined$load$default$2.class
dependency_records[1].sources[6]: net/loliland/lolidungeons/LoliDungeons.class
dependency_records[1].sources[7]: net/loliland/lolidungeons/api/event/DungeonEvent$JoinPlayer.class
dependency_records[1].sources[8]: net/loliland/lolidungeons/api/event/DungeonEvent$LeavePlayer.class
dependency_records[1].sources[9]: net/loliland/lolidungeons/api/event/DungeonEvent$Setup.class
dependency_records[1].sources[10]: net/loliland/lolidungeons/api/event/DungeonEvent.class
dependency_records[1].sources[11]: net/loliland/lolidungeons/asm/CoremodPlugin.class
dependency_records[1].sources[12]: net/loliland/lolidungeons/block/LoliDungeonsBlocks.class
dependency_records[1].sources[13]: net/loliland/lolidungeons/block/spawner/BlockDungeonSpawner.class
dependency_records[1].sources[14]: net/loliland/lolidungeons/client/gui/block/GuiDungeonSpawner.class
dependency_records[1].sources[15]: net/loliland/lolidungeons/client/renderer/dimension/DummyCloudRenderer.class
dependency_records[1].sources[16]: net/loliland/lolidungeons/client/renderer/texture/TextureDungeonSpawner.class
dependency_records[1].sources[17]: net/loliland/lolidungeons/entity/LoliDungeonsPlayer$Companion.class
dependency_records[1].sources[18]: net/loliland/lolidungeons/entity/LoliDungeonsPlayer.class
dependency_records[1].sources[19]: net/loliland/lolidungeons/impl/msw/RealmDungeonUtils.class
dependency_records[1].sources[20]: net/loliland/lolidungeons/network/LoliDungeonsNetwork.class
dependency_records[1].sources[21]: net/loliland/lolidungeons/network/packet/PacketDungeonSpawnerSettings$Companion.class
dependency_records[1].sources[22]: net/loliland/lolidungeons/network/packet/PacketDungeonSpawnerSettings$Handler.class
dependency_records[1].sources[23]: net/loliland/lolidungeons/network/packet/PacketDungeonSpawnerSettings.class
dependency_records[1].sources[24]: net/loliland/lolidungeons/network/packet/PacketGuiDungeonSpawner$Handler.class
dependency_records[1].sources[25]: net/loliland/lolidungeons/network/packet/PacketGuiDungeonSpawner.class
dependency_records[1].sources[26]: net/loliland/lolidungeons/util/UltramineHelper.class
dependency_records[1].sources[27]: net/loliland/lolidungeons/world/WorldProviderDungeonRealm.class
dependency_records[1].evidence[0]: cpw/mods/fml/
dependency_records[1].evidence[1]: net/minecraftforge/
dependency_records[1].evidence[2]: net/minecraftforge/, cpw/mods/fml/
dependency_records[2].name: LaunchWrapper
dependency_records[2].categories[0]: CoreMod
dependency_records[2].confidence: 92
dependency_records[2].sources[0]: net/loliland/lolidungeons/client/util/OptiFineHelper.class
dependency_records[2].evidence[0]: net/minecraft/launchwrapper/, LaunchClassLoader
dependency_records[3].name: lolidungeons
dependency_records[3].categories[0]: Optional
dependency_records[3].confidence: 45
dependency_records[3].sources[0]: assets/lolidungeons/lang/en_US.lang
dependency_records[3].sources[1]: assets/lolidungeons/sounds.json
dependency_records[3].sources[2]: assets/lolidungeons/textures/blocks/dungeon_spawner.png
dependency_records[3].sources[3]: assets/lolidungeons/textures/blocks/dungeon_spawner.png.mcmeta
dependency_records[3].sources[4]: assets/lolidungeons/textures/items/dungeon_spawner.png
dependency_records[3].evidence[0]: resource namespace
```


## Class Scanner

```text
file: lolidungeons-main-f88dd825.jar
size_mb: 0.13
files: 89
class_files: 75
packages: 24
score: 100
difficulty: Hard
detections[0]: CoreMod / ASM
detections[1]: Old Forge API
detections[2]: Old Registry
content.Blocks: 1
content.Items: 0
content.TileEntities: 4
content.Entities: 14
content_lists.Blocks[0]: dungeonspawner
content_lists.Items: []
content_lists.TileEntities[0]: S35PacketUpdate
content_lists.TileEntities[1]: createNew
content_lists.TileEntities[2]: get
content_lists.TileEntities[3]: register
content_lists.Entities[0]: ByUUID
content_lists.Entities[1]: ClientPlayerMP
content_lists.Entities[2]: Constructing
content_lists.Entities[3]: Construction
content_lists.Entities[4]: DungeonHandler
content_lists.Entities[5]: DungeonSpawner
content_lists.Entities[6]: Event
content_lists.Entities[7]: List
content_lists.Entities[8]: Living
content_lists.Entities[9]: LivingData
content_lists.Entities[10]: Player
content_lists.Entities[11]: PlayerMP
content_lists.Entities[12]: Properties
content_lists.Entities[13]: Zombie
content_sources.Blocks.class: 14
content_sources.Blocks.lang: 1
content_sources.Blocks.models: 0
content_sources.Blocks.blockstates: 0
content_sources.Items.class: 5
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
gui.GUI: 14
gui.Containers: 3
network.Packets: 75
network.Channels: 1
creative: 3
important_classes: []
estimated_methods: 197
client_hits: 63
server_hits: 49
client_percent: 56.2
server_percent: 43.8
reasons[0]: Heavy networking
reasons[1]: Heavy client code
lang_entries: 9
models: 0
blockstates: 0
type[0]: Network Based
```


## Resource Scanner

```text
textures: 5
block_textures: 1
item_textures: 1
models: 0
block_models: 0
item_models: 0
blockstates: 0
languages: 1
sounds: 0
gui_resources: 0
recipes: 0
advancements: 0
json_files: 1
resource_files: 5
total_resource_size_mb: 0.0
important_paths[0]: assets/lolidungeons/textures/blocks/dungeon_spawner.png.mcmeta
important_paths[1]: assets/lolidungeons/textures/blocks/dungeon_spawner.png
important_paths[2]: assets/lolidungeons/textures/items/dungeon_spawner.png
important_paths[3]: assets/lolidungeons/lang/en_US.lang
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
class_files_scanned: 75
recipe_classes: []
No data
No data
recipe_paths: []
```
