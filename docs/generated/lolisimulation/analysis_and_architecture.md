# LoliSimulation Analysis and Architecture

## Evidence used

The existing generated reports show an UltraTech/LoliLand client with 93 mod jars and 255.37 MB of mod artifacts, of which 83 were already classified as hard migrations and most were marked network based. The launcher report describes the `.loliland` tree as a complete launcher/client installation with 3892 files, 1027 directories, 1557 MB, 296 archives, 303 configuration files, 992 library/dependency entries, and 2209 URL occurrences. Save inspection found a Forge 1.7.10 world with 113 FML mod entries.

## Local LFS limitation

A direct pass over `tools/lolimods/.loliland` found many jar files represented as Git LFS pointer files rather than local zip archives. The LoliSimulation design therefore relies on the repository's generated scanner reports plus live filesystem/configuration inspection, and records missing binary contents as unavailable evidence instead of inventing decompiled APIs.

## Server-side reconstruction map

Observed server-adjacent subsystems are:

* Network synchronization: widespread packet/channel use reported by the dependency and class scanners.
* Player data: save files, launcher identity caches, quest mods, economy-like configs, and custom Loli modules imply per-player persisted state.
* Permissions/realm limiting: `RealmBlockLimiter`, `ContainerWarden`, `LoliUtility`, and related configs imply authorization gates.
* Recipe/runtime scripting: CraftTweaker/ModTweaker and generated recipe reports imply server-provided progression rules.
* World/dimension services: `LoliDimensions`, `WorldGenEngine`, Galacticraft, RTG, and saved dimension metadata imply world registration and sync.
* Diagnostics/logging: launcher and previous reports show that compatibility work needs persistent startup evidence.

## Architecture implemented

`tools/mods/LoliSimulation` is a Forge 1.7.10 source project. It provides a modular service registry, durable local property stores for player data, economy, permissions, and statistics, a packet ledger for future packet bridge adapters, diagnostics under `config/LoliSimulation/diagnostics`, and an event-driven compatibility layer that discovers loaded LoliLand-family mods without modifying their jars.

## Assumptions explicitly not implemented

No proprietary remote database schema, packet payload contract, economy formula, or permission rule was guessed because local jar bytecode is unavailable through LFS pointers in this checkout. The project is intentionally structured so later decompilation evidence can replace generic stores with exact adapters.
