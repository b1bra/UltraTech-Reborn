# Manual tasks for the Forge 1.7.10 backport

The automated pass performed the mechanical Forge/FML namespace, registration, network, and RF API migrations requested for the 1.12.2 sources under `mods/src`. The following areas still require manual review or implementation before the mod can be considered a complete Forge 1.7.10 port.

## 1. BrandonsCore feature system

`DAFeatures.registerFor1710()` now performs direct `GameRegistry.registerItem`, `GameRegistry.registerBlock`, and `GameRegistry.registerTileEntity` calls during `preInit`, because Forge 1.7.10 does not use `RegistryEvent`.

Manual work:
- Verify whether the targeted 1.7.10 BrandonsCore build supports `@ModFeature`, `@ModFeatures`, `IModFeatures`, and `ModFeatureParser`.
- If it does not, remove those annotations/interfaces and replace remaining feature metadata with explicit 1.7.10 initialization code.
- Confirm that `ItemBlockBCore` exists in the targeted 1.7.10 BrandonsCore API and has a constructor compatible with `GameRegistry.registerBlock(block, ItemBlockBCore.class, name)`.

## 2. Minecraft 1.12-only game APIs

This pass did not fully convert Minecraft API changes that are outside the requested mechanical Forge/FML migration list.

Manual work:
- Replace 1.12 types such as `BlockPos`, `EnumFacing`, `EnumHand`, `IBlockState`, `EntityEquipmentSlot`, `ITickable`, `SoundCategory`, `TextFormatting`, and `ItemStack.EMPTY`/`isEmpty()` with their Minecraft 1.7.10 equivalents.
- Convert block state/property code to 1.7.10 metadata and `TileEntity` update patterns.
- Convert 1.12 item interaction methods such as `onItemUseFirst(..., EnumHand ...)` to 1.7.10 method signatures.

## 3. Forge capabilities

Forge 1.7.10 does not include the modern capability system used by `net.minecraftforge.common.capabilities`.

Manual work:
- Replace `ChaosInBlood` capability registration/storage/provider code with a 1.7.10-compatible storage approach, such as `IExtendedEntityProperties` or explicit NBT attached to players.
- Remove `CapabilityManager` usage from `CommonProxy.preInit` after the replacement is implemented.

## 4. Client rendering and models

No `ModelLoader.setCustomModelResourceLocation` calls were present in the current `mods/src` tree, so nothing was commented out automatically.

Manual work:
- Port JSON/blockstate item and block model registration to Forge 1.7.10 rendering.
- Implement `IItemRenderer`, `ISimpleBlockRenderingHandler`, `TileEntitySpecialRenderer`, or an equivalent 1.7.10 rendering path for each item/block that currently depends on 1.8+ model JSON behavior.

## 5. Energy API verification

The tile energy interfaces were changed to `cofh.api.energy.IEnergyReceiver` / `IEnergyProvider`, with side-aware methods using `ForgeDirection`.

Manual work:
- Verify that the selected CoFHLib/RedstoneFlux version for 1.7.10 exposes the exact `cofh.api.energy` package. Some modpacks ship RF interfaces under `cofh.redstoneflux.api`; if so, switch the imports to match the dependency actually used.
- Audit `TileEnergyInventoryBase` and `EnergyHelper` from the targeted 1.7.10 BrandonsCore version to ensure their energy method signatures accept `ForgeDirection`.

## 6. Networking registration

Packet channel creation and `registerMessage` calls were moved from `preInit` to `init`.

Manual work:
- Verify packet handler signatures against the Forge 1.7.10 SimpleImpl API and replace any server-player/context access patterns that still assume 1.12 internals.

## 7. Language/resource naming

The direct 1.7.10 registration helper assigns unlocalized names in the form `draconicadditions.<registry_name>`.

Manual work:
- Confirm that every key in `assets/draconicadditions/lang/*.lang` matches the final 1.7.10 unlocalized names.
- Convert 1.8+ resource files (`blockstates` and JSON models) into 1.7.10-compatible renderer/texture registration data.
