# Forge 1.7.10 backport task log

This file is now the sequential work log for the manual items requested after the first mechanical pass.

## 2. Minecraft 1.12-only game APIs

Status: partially applied in code.

- Replaced `ItemStack.EMPTY` assignments with `null` and converted direct `stack.isEmpty()` checks to `stack == null || stack.stackSize <= 0` style checks where this could be done mechanically.
- Replaced GUI `TextFormatting` imports/usages with `EnumChatFormatting`.
- Replaced tile `ITickable` implementations in machine tile entities with `updateEntity()` methods.
- Removed `EntityEquipmentSlot` from `DAFeatures` construction sites and changed armor item construction to numeric armor slots.

Still manual / intentionally left for focused class-by-class conversion:
- Block classes that depend on 1.8+ `IBlockState`, `BlockPos`, block properties, rotations and mirrors still need full metadata rewrites.
- Tool interaction methods using `EnumHand`, raytrace `BlockPos`, and 1.12 world/player helpers still need manual 1.7.10 signatures.
- Sound calls that rely on `SoundCategory` still need conversion to the exact 1.7.10 sound helper used by the target dependency set.

## 3. Forge capabilities

Status: applied for `ChaosInBlood`.

- Replaced the `ChaosInBloodProvider` capability provider with an `IExtendedEntityProperties` implementation that saves and loads chaos values through NBT.
- Switched `DAEventHandler`, `ChaosContainer`, and `ChaoticArmor` chaos lookups from player capabilities to `ChaosInBloodProvider.get(player)`.
- Removed `CapabilityManager` registration from `CommonProxy.preInit`.
- Kept `ChaosInBloodStorage` as an inert compatibility placeholder while old references are phased out.

## 4. Client rendering and models

Status: scaffolded for 1.7.10.

- Added `DA1710RenderRegistration` to centralize item and block renderer registration.
- Registered the existing chaos crystal and stabilizer item renderers through `MinecraftForgeClient.registerItemRenderer`.
- Added `RenderBlock1710Stub` implementing `ISimpleBlockRenderingHandler` as the 1.7.10 replacement point for JSON blockstate rendering.
- Left a local `ItemModelMesher` hook with TODO notes because the requested mesher API is not native to Forge 1.7.10 and may only exist when the target pack provides a compatibility shim.

## 1. BrandonsCore feature system

Status: applied.

- Removed `@ModFeature`, `@ModFeatures`, and `IModFeatures` usage from `DAFeatures`.
- Kept direct item/block/tile registration in `DAFeatures.registerFor1710()`.
- Kept the disabled chaos infuser as a TODO comment because it was disabled in the source and still needs a manual block/tile port.

## 5. Energy API verification

Status: checked.

- Current source imports use `cofh.api.energy.IEnergyReceiver`, `IEnergyProvider`, and `IEnergyContainerItem`.
- No remaining `cofh.redstoneflux.api.IEnergyReceiver`, `IEnergyProvider`, or `IEnergyStorage` imports were found.

## 7. Language/resource naming

Status: checked and patched.

- Registration now uses colon-based unlocalized names such as `draconicadditions:chaos_heart` to match existing language keys.
- Added missing Russian language entries for `portable_wired_discharger` and `chaos_crystal_stable`.
