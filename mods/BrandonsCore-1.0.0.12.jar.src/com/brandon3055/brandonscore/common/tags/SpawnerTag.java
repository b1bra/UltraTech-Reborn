package com.brandon3055.brandonscore.common.tags;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.IBlockAccess;

public class SpawnerTag {
    public SpawnerLogic getLogic(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.func_147438_o(x, y, z);
        return tile instanceof TileEntityMobSpawner ? new SpawnerLogic(((TileEntityMobSpawner)tile).func_145881_a()) : null;
    }
}
