# Remaining manual backport work

- This pass removes the requested 1.12-only symbols/imports from `mods/src/main/java` and fixes the listed BrandonsCore/Minecraft 1.7.10 package moves.
- A real Forge 1.7.10 build still must be run with the project dependencies on the classpath. This checkout has no Gradle wrapper/build file, so local `javac` can only confirm source syntax before failing on missing Minecraft/Forge/BrandonsCore/Draconic Evolution classes.
- `ChaosCrystalStable` has been temporarily detached from Draconic Evolution's 1.12 `ChaosCrystal`/`TileChaosCrystal` classes. Reconnect its TODOs to the correct Draconic Evolution 1.7.10 chaos crystal classes once those package names are available.
- Manual networking/render synchronization is still needed for the primitive tile fields that replaced BrandonsCore's data-manager `Managed*` values.
