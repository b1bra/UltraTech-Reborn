# DecorativeAPI

## Surface

```text
file: DecorativeAPI-1.1.1-client.jar
path: C:\Users\Vasya\AppData\Roaming\.loliland\game-resources\clients\ultra_tech\main\mods\DecorativeAPI-1.1.1-client.jar
name: DecorativeAPI
modid: decorativeapi
version: 1.1.1
author: Unknown
size_mb: 0.05
forge: FMLAT: decorativeapi_at.cfg
dependencies[0]: AccessTransformer
dependencies[1]: decorativeapi
dependencies[2]: Forge
files: 33
class_files: 26
packages: 9
score: 100
difficulty: Hard
detections[0]: Old Rendering
detections[1]: Old Forge API
detections[2]: Old Registry
content.Blocks: 0
content.Items: 0
content.TileEntities: 0
content.Entities: 3
content_lists.Blocks: []
content_lists.Items: []
content_lists.TileEntities: []
content_lists.Entities[0]: ClientPlayerMP
content_lists.Entities[1]: LivingBase
content_lists.Entities[2]: Player
content_sources.Blocks.class: 145
content_sources.Blocks.lang: 0
content_sources.Blocks.models: 0
content_sources.Blocks.blockstates: 0
content_sources.Items.class: 6
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
gui.Containers: 6
No data
creative: 7
important_classes[0]: and
estimated_methods: 90
client_hits: 44
server_hits: 53
client_percent: 45.4
server_percent: 54.6
reasons: []
lang_entries: 0
models: 0
blockstates: 0
type[0]: Utility
resources.textures: 2
resources.block_textures: 1
resources.item_textures: 0
resources.models: 0
resources.block_models: 0
resources.item_models: 0
resources.blockstates: 0
resources.languages: 2
resources.sounds: 0
resources.gui_resources: 0
resources.recipes: 0
resources.advancements: 0
resources.json_files: 0
resources.resource_files: 3
resources.total_resource_size_mb: 0.0
resources.important_paths[0]: assets/decorativeapi/lang/en_US.lang
resources.important_paths[1]: assets/decorativeapi/lang/ru_RU.lang
resources.important_paths[2]: assets/decorativeapi/textures/blocks/debug_brick.png
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
recipe_data.class_files_scanned: 26
recipe_data.recipe_classes: []
No data
No data
recipe_data.recipe_paths: []
No data
No data
dependency_records[0].name: AccessTransformer
dependency_records[0].categories[0]: CoreMod
dependency_records[0].confidence: 90
dependency_records[0].sources[0]: META-INF/decorativeapi_at.cfg
dependency_records[0].evidence[0]: access transformer file
dependency_records[0].evidence[1]: access transformer resource
dependency_records[1].name: decorativeapi
dependency_records[1].categories[0]: Optional
dependency_records[1].confidence: 45
dependency_records[1].sources[0]: assets/decorativeapi/lang/en_US.lang
dependency_records[1].sources[1]: assets/decorativeapi/lang/ru_RU.lang
dependency_records[1].sources[2]: assets/decorativeapi/textures/blocks/debug_brick.png
dependency_records[1].evidence[0]: resource namespace
dependency_records[2].name: Forge
dependency_records[2].categories[0]: Hidden
dependency_records[2].confidence: 78
dependency_records[2].sources[0]: com/aizistral/decorations/DecorationsMod.class
dependency_records[2].sources[1]: com/aizistral/decorations/blocks/DecorativeFenceBlock.class
dependency_records[2].sources[2]: com/aizistral/decorations/blocks/DecorativeGateBlock.class
dependency_records[2].sources[3]: com/aizistral/decorations/blocks/DecorativeSlabBlock.class
dependency_records[2].sources[4]: com/aizistral/decorations/blocks/DecorativeStairsBlock.class
dependency_records[2].sources[5]: com/aizistral/decorations/blocks/DecorativeWallBlock.class
dependency_records[2].sources[6]: com/aizistral/decorations/core/BlockGroupImpl.class
dependency_records[2].sources[7]: com/aizistral/decorations/core/DecorativeUtils.class
dependency_records[2].sources[8]: com/aizistral/decorations/items/DecorativeItemBlock.class
dependency_records[2].sources[9]: com/aizistral/decorations/proxy/ClientProxy.class
dependency_records[2].sources[10]: com/aizistral/decorations/renderer/DecorativeGateBlockRenderer.class
dependency_records[2].sources[11]: com/aizistral/decorations/renderer/DecorativeSlabBlockRenderer$1.class
dependency_records[2].sources[12]: com/aizistral/decorations/renderer/DecorativeSlabBlockRenderer.class
dependency_records[2].sources[13]: com/aizistral/decorations/renderer/SlabPlacementPreviewRenderer.class
dependency_records[2].evidence[0]: cpw/mods/fml/
dependency_records[2].evidence[1]: net/minecraftforge/
dependency_records[2].evidence[2]: net/minecraftforge/, cpw/mods/fml/
```


## Class Scanner

```text
file: DecorativeAPI-1.1.1-client.jar
size_mb: 0.05
files: 33
class_files: 26
packages: 9
score: 100
difficulty: Hard
detections[0]: Old Rendering
detections[1]: Old Forge API
detections[2]: Old Registry
content.Blocks: 0
content.Items: 0
content.TileEntities: 0
content.Entities: 3
content_lists.Blocks: []
content_lists.Items: []
content_lists.TileEntities: []
content_lists.Entities[0]: ClientPlayerMP
content_lists.Entities[1]: LivingBase
content_lists.Entities[2]: Player
content_sources.Blocks.class: 145
content_sources.Blocks.lang: 0
content_sources.Blocks.models: 0
content_sources.Blocks.blockstates: 0
content_sources.Items.class: 6
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
gui.Containers: 6
No data
creative: 7
important_classes[0]: and
estimated_methods: 90
client_hits: 44
server_hits: 53
client_percent: 45.4
server_percent: 54.6
reasons: []
lang_entries: 0
models: 0
blockstates: 0
type[0]: Utility
```


## Resource Scanner

```text
textures: 2
block_textures: 1
item_textures: 0
models: 0
block_models: 0
item_models: 0
blockstates: 0
languages: 2
sounds: 0
gui_resources: 0
recipes: 0
advancements: 0
json_files: 0
resource_files: 3
total_resource_size_mb: 0.0
important_paths[0]: assets/decorativeapi/lang/en_US.lang
important_paths[1]: assets/decorativeapi/lang/ru_RU.lang
important_paths[2]: assets/decorativeapi/textures/blocks/debug_brick.png
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
class_files_scanned: 26
recipe_classes: []
No data
No data
recipe_paths: []
```
