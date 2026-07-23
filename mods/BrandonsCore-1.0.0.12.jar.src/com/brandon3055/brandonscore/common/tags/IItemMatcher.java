package com.brandon3055.brandonscore.common.tags;

import net.minecraft.item.ItemStack;

public interface IItemMatcher {
    boolean is(ItemStack stack);
}
