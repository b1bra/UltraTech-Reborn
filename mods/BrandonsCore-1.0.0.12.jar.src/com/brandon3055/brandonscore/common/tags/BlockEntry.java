package com.brandon3055.brandonscore.common.tags;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockEntry {
    private final Block block;
    private final int meta;

    public BlockEntry(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    public Block getBlock() { return block; }
    public int getPlacementMeta() { return meta; }
    public Item getItem() { return Item.func_150898_a(block); }
    public ItemStack toStack() { return new ItemStack(block, 1, meta); }
}
