package com.brandon3055.brandonscore.common.tags;

import net.minecraft.tileentity.MobSpawnerBaseLogic;

public class SpawnerLogic {
    private final MobSpawnerBaseLogic logic;
    public SpawnerLogic(MobSpawnerBaseLogic logic) { this.logic = logic; }
    public String getEntityNameToSpawn() { return logic.func_98276_e(); }
}
