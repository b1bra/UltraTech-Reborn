# Remaining manual backport work

- No occurrences of the requested 1.12-only API names remain under `mods/src/main/java` after the mechanical pass.
- A full Forge 1.7.10 compile still needs to be run in a complete modding workspace; this checkout does not include a Gradle wrapper or build file at repository depth 3, so I could not validate against the actual Minecraft/Forge/Brandon's Core/Draconic Evolution APIs here.
- Several adjacent 1.8+/1.12 compatibility issues may still need manual cleanup during compile, especially `ActionResult`/`EnumActionResult`, `TextComponentString`/`TextComponentTranslation`, `ITooltipFlag`, `SoundEvents`/`SoundEvent`, `EnumParticleTypes`, `ItemStack#getCount`/`shrink`, blockstate/property setup, and other non-requested modern API symbols.
