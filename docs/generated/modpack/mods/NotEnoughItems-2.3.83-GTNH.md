# NotEnoughItems

## Surface

```text
file: NotEnoughItems-2.3.83-GTNH.jar
path: C:\Users\Vasya\AppData\Roaming\.loliland\game-resources\clients\ultra_tech\main\mods\NotEnoughItems-2.3.83-GTNH.jar
name: NotEnoughItems
modid: NotEnoughItems
version: 2.3.83-GTNH
author: Unknown
size_mb: 0.7
forge: FMLCorePlugin: codechicken.nei.asm.NEICorePlugin
dependencies[0]: CodeChickenCore
files: 346
class_files: 325
packages: 16
score: 100
difficulty: Hard
detections[0]: Old Rendering
detections[1]: Old Registry
detections[2]: CoreMod / ASM
detections[3]: Old Forge API
content.Blocks: 0
content.Items: 0
content.TileEntities: 3
content.Entities: 16
content_lists.Blocks: []
content_lists.Items: []
content_lists.TileEntities[0]: drawBackground
content_lists.TileEntities[1]: w
content_lists.TileEntities[2]: y
content_lists.Entities[0]: ClientPlayerMP
content_lists.Entities[1]: Egg
content_lists.Entities[2]: EggInfo
content_lists.Entities[3]: Furnace
content_lists.Entities[4]: IronGolem
content_lists.Entities[5]: Item
content_lists.Entities[6]: List
content_lists.Entities[7]: Living
content_lists.Entities[8]: LivingBase
content_lists.Entities[9]: MobSpawner
content_lists.Entities[10]: Pig
content_lists.Entities[11]: Player
content_lists.Entities[12]: PlayerMP
content_lists.Entities[13]: PlayerSP
content_lists.Entities[14]: References
content_lists.Entities[15]: Snowman
content_sources.Blocks.class: 37
content_sources.Blocks.lang: 0
content_sources.Blocks.models: 0
content_sources.Blocks.blockstates: 0
content_sources.Items.class: 159
content_sources.Items.lang: 0
content_sources.Items.models: 0
registration.Blocks: 0
registration.Items: 0
registration.TileEntities: 0
registration.Entities: 0
registration.WorldGenerators: 0
recipes.Crafting: 72
recipes.Smelting: 0
recipes.Custom: 128
gui.Containers: 1174
gui.GUI: 545
network.Packets: 65
creative: 12
important_classes[0]: for
estimated_methods: 1383
client_hits: 780
server_hits: 137
client_percent: 85.1
server_percent: 14.9
reasons[0]: Heavy client code
reasons[1]: Heavy networking
lang_entries: 527
models: 0
blockstates: 0
type[0]: Technology
type[1]: Network Based
resources.textures: 14
resources.block_textures: 0
resources.item_textures: 0
resources.models: 0
resources.block_models: 0
resources.item_models: 0
resources.blockstates: 0
resources.languages: 2
resources.sounds: 0
resources.gui_resources: 3
resources.recipes: 0
resources.advancements: 0
resources.json_files: 0
resources.resource_files: 15
resources.total_resource_size_mb: 0.26
resources.important_paths[0]: assets/nei/lang/ru_RU.lang
resources.important_paths[1]: assets/nei/lang/en_US.lang
resources.important_paths[2]: assets/nei/textures/nei_sprites.png
resources.important_paths[3]: assets/nei/textures/gui/recipebg.png
resources.important_paths[4]: assets/nei/textures/gui/inv.png
resources.important_paths[5]: assets/nei/textures/gui/potion.png
resources.important_paths[6]: assets/nei/textures/slot.png
resources.important_paths[7]: assets/nei/textures/catalyst_tab.png
resources.important_paths[8]: assets/nei/textures/nei_tabbed_sprites.png
resources.large_files: []
resources.resource_complexity: 1
recipe_data.total_recipes: 13
recipe_data.json_recipes: 0
recipe_data.code_recipes: 13
recipe_data.crafting: 9
recipe_data.ore_recipes: 2
recipe_data.smelting: 2
recipe_data.special: 0
recipe_data.json_files: 0
recipe_data.class_files_scanned: 325
recipe_data.recipe_classes[0]: codechicken/nei/api/API.class
recipe_data.recipe_classes[1]: codechicken/nei/recipe/RecipeInfo.class
recipe_data.recipe_classes[2]: codechicken/nei/recipe/FireworkRecipeHandler$CachedFireworkRecipe.class
recipe_data.recipe_classes[3]: codechicken/nei/recipe/FireworkRecipeHandler.class
recipe_data.recipe_classes[4]: codechicken/nei/recipe/RecipeCatalysts.class
recipe_data.recipe_classes[5]: codechicken/nei/recipe/ShapelessRecipeHandler$CachedShapelessRecipe.class
recipe_data.recipe_classes[6]: codechicken/nei/recipe/ShapedRecipeHandler$CachedShapedRecipe.class
recipe_data.recipe_classes[7]: codechicken/nei/recipe/ShapelessRecipeHandler.class
recipe_data.recipe_classes[8]: codechicken/nei/recipe/ShapedRecipeHandler.class
recipe_data.recipe_classes[9]: codechicken/nei/recipe/FurnaceRecipeHandler.class
recipe_data.recipe_classes[10]: codechicken/nei/recipe/FuelRecipeHandler.class
No data
No data
recipe_data.recipe_paths: []
No data
No data
```


## Class Scanner

```text
file: NotEnoughItems-2.3.83-GTNH.jar
size_mb: 0.7
files: 346
class_files: 325
packages: 16
score: 100
difficulty: Hard
detections[0]: Old Rendering
detections[1]: Old Registry
detections[2]: CoreMod / ASM
detections[3]: Old Forge API
content.Blocks: 0
content.Items: 0
content.TileEntities: 3
content.Entities: 16
content_lists.Blocks: []
content_lists.Items: []
content_lists.TileEntities[0]: drawBackground
content_lists.TileEntities[1]: w
content_lists.TileEntities[2]: y
content_lists.Entities[0]: ClientPlayerMP
content_lists.Entities[1]: Egg
content_lists.Entities[2]: EggInfo
content_lists.Entities[3]: Furnace
content_lists.Entities[4]: IronGolem
content_lists.Entities[5]: Item
content_lists.Entities[6]: List
content_lists.Entities[7]: Living
content_lists.Entities[8]: LivingBase
content_lists.Entities[9]: MobSpawner
content_lists.Entities[10]: Pig
content_lists.Entities[11]: Player
content_lists.Entities[12]: PlayerMP
content_lists.Entities[13]: PlayerSP
content_lists.Entities[14]: References
content_lists.Entities[15]: Snowman
content_sources.Blocks.class: 37
content_sources.Blocks.lang: 0
content_sources.Blocks.models: 0
content_sources.Blocks.blockstates: 0
content_sources.Items.class: 159
content_sources.Items.lang: 0
content_sources.Items.models: 0
registration.Blocks: 0
registration.Items: 0
registration.TileEntities: 0
registration.Entities: 0
registration.WorldGenerators: 0
recipes.Crafting: 72
recipes.Smelting: 0
recipes.Custom: 128
gui.Containers: 1174
gui.GUI: 545
network.Packets: 65
creative: 12
important_classes[0]: for
estimated_methods: 1383
client_hits: 780
server_hits: 137
client_percent: 85.1
server_percent: 14.9
reasons[0]: Heavy client code
reasons[1]: Heavy networking
lang_entries: 527
models: 0
blockstates: 0
type[0]: Technology
type[1]: Network Based
```


## Resource Scanner

```text
textures: 14
block_textures: 0
item_textures: 0
models: 0
block_models: 0
item_models: 0
blockstates: 0
languages: 2
sounds: 0
gui_resources: 3
recipes: 0
advancements: 0
json_files: 0
resource_files: 15
total_resource_size_mb: 0.26
important_paths[0]: assets/nei/lang/ru_RU.lang
important_paths[1]: assets/nei/lang/en_US.lang
important_paths[2]: assets/nei/textures/nei_sprites.png
important_paths[3]: assets/nei/textures/gui/recipebg.png
important_paths[4]: assets/nei/textures/gui/inv.png
important_paths[5]: assets/nei/textures/gui/potion.png
important_paths[6]: assets/nei/textures/slot.png
important_paths[7]: assets/nei/textures/catalyst_tab.png
important_paths[8]: assets/nei/textures/nei_tabbed_sprites.png
large_files: []
resource_complexity: 1
```


## Recipe Scanner

```text
total_recipes: 13
json_recipes: 0
code_recipes: 13
crafting: 9
ore_recipes: 2
smelting: 2
special: 0
json_files: 0
class_files_scanned: 325
recipe_classes[0]: codechicken/nei/api/API.class
recipe_classes[1]: codechicken/nei/recipe/RecipeInfo.class
recipe_classes[2]: codechicken/nei/recipe/FireworkRecipeHandler$CachedFireworkRecipe.class
recipe_classes[3]: codechicken/nei/recipe/FireworkRecipeHandler.class
recipe_classes[4]: codechicken/nei/recipe/RecipeCatalysts.class
recipe_classes[5]: codechicken/nei/recipe/ShapelessRecipeHandler$CachedShapelessRecipe.class
recipe_classes[6]: codechicken/nei/recipe/ShapedRecipeHandler$CachedShapedRecipe.class
recipe_classes[7]: codechicken/nei/recipe/ShapelessRecipeHandler.class
recipe_classes[8]: codechicken/nei/recipe/ShapedRecipeHandler.class
recipe_classes[9]: codechicken/nei/recipe/FurnaceRecipeHandler.class
recipe_classes[10]: codechicken/nei/recipe/FuelRecipeHandler.class
No data
No data
recipe_paths: []
```
