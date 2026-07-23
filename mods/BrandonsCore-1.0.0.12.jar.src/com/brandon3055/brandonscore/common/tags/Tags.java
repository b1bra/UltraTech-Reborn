package com.brandon3055.brandonscore.common.tags;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public final class Tags {
    public static final class Blocks {
        public static final BlockTag OBSIDIAN = new BlockTag(net.minecraft.init.Blocks.field_150343_Z);
        public static final BlockTag DIRT = new BlockTag(net.minecraft.init.Blocks.field_150346_d);
        public static final BlockTag GRASS = new BlockTag(net.minecraft.init.Blocks.field_150349_c);
        public static final BlockTag FARMLAND = new BlockTag(net.minecraft.init.Blocks.field_150458_ak);
        public static final BlockTag COAL_BLOCK = new BlockTag(net.minecraft.init.Blocks.field_150475_bE);
        public static final BlockTag BOOKSHELF = new BlockTag(net.minecraft.init.Blocks.field_150342_X);
        public static final BlockTag GLASS = new BlockTag(net.minecraft.init.Blocks.field_150359_w);
        public static final BlockTag REDSTONE_BLOCK = new BlockTag(net.minecraft.init.Blocks.field_150451_bX);
        public static final BlockTag STONE_SLAB = new BlockTag(net.minecraft.init.Blocks.field_150333_U);
        public static final BlockTag SAND = new BlockTag(net.minecraft.init.Blocks.field_150354_m);
    }
    public static final class Items {
        public static final ItemTag STICK = new ItemTag(net.minecraft.init.Items.field_151055_y);
        public static final ItemTag ENDER_EYE = new ItemTag(net.minecraft.init.Items.field_151061_bv);
        public static final ItemTag ENDER_PEARL = new ItemTag(net.minecraft.init.Items.field_151079_bi);
        public static final ItemTag PAPER = new ItemTag(net.minecraft.init.Items.field_151121_aF);
        public static final ItemTag NETHER_STAR = new ItemTag(net.minecraft.init.Items.field_151156_bN);
        public static final ItemTag EMPOWERED_GOLDEN_APPLE = new ItemTag(net.minecraft.init.Items.field_151153_ao, 1);
        public static final ItemTag COAL = new ItemTag(net.minecraft.init.Items.field_151044_h, 32767);
        public static final ItemTag LAVA_BUCKET = new ItemTag(net.minecraft.init.Items.field_151129_at);
        public static final ItemTag BLAZE_ROD = new ItemTag(net.minecraft.init.Items.field_151073_bk);
        public static final ItemTag BLAZE_POWDER = new ItemTag(net.minecraft.init.Items.field_151065_br);
        public static final ItemTag WITHER_SKELETON_SKULL = new ItemTag(net.minecraft.init.Items.field_151144_bL, 1);
        public static final ItemTag ROTTEN_FLESH = new ItemTag(net.minecraft.init.Items.field_151078_bh);
        public static final ItemTag PORKCHOP = new ItemTag(net.minecraft.init.Items.field_151076_bf);
        public static final ItemTag COOKED_PORKCHOP = new ItemTag(net.minecraft.init.Items.field_151157_am);
    }
    public static final class Spawners {
        public static final SpawnerTag MOB_SPAWNER = new SpawnerTag();
    }
}
