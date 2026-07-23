package com.brandon3055.brandonscore.common.tags;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemEntry {
    private final Item item;
    private final int meta;

    public ItemEntry(Item item, int meta) {
        this.item = item;
        this.meta = meta;
    }

    public Item getItem() { return item; }
    public int getMeta() { return meta; }
    public ItemStack toStack() { return new ItemStack(item, 1, meta); }
}
