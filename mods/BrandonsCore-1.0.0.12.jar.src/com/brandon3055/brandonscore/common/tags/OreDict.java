package com.brandon3055.brandonscore.common.tags;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class OreDict {
    public static final OreMatcher SLAB_WOOD = new OreMatcher("slabWood");
    public static final OreMatcher TREE_SAPLING = new OreMatcher("treeSapling");

    public static class OreMatcher implements IItemMatcher {
        private final String name;
        public OreMatcher(String name) { this.name = name; }
        public boolean is(ItemStack stack) {
            if (stack == null) return false;
            int target = OreDictionary.getOreID(name);
            for (int id : OreDictionary.getOreIDs(stack)) if (id == target) return true;
            return false;
        }
    }
}
