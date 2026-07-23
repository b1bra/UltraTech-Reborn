package com.brandon3055.brandonscore.common.tags;

import java.util.Optional;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemTag implements IItemMatcher {
    private final ItemEntry primary;

    public ItemTag(Item item) { this(item, 0); }
    public ItemTag(Item item, int meta) { this.primary = new ItemEntry(item, meta); }

    public static ItemTag get(String modid, String name) {
        if ("awakened_draconium_nugget".equals(name)) return new OreItemTag("nuggetDraconiumAwakened");
        if ("awakened_draconium_ingot".equals(name)) return new OreItemTag("ingotDraconiumAwakened");
        if ("chaos_shard".equals(name)) return new OreItemTag("chaosShard");
        return new ItemTag((Item)null);
    }

    public Optional<ItemEntry> getPrimary() { return Optional.ofNullable(primary.getItem() == null ? null : primary); }
    public ItemEntry requirePrimary() { if (primary.getItem() == null) throw new IllegalStateException("Missing item tag"); return primary; }
    public Optional<ItemStack> makeStack() { return getPrimary().map(ItemEntry::toStack); }
    public ItemStack requireStack() { return requirePrimary().toStack(); }
    public boolean is(ItemStack stack) { return stack != null && primary.getItem() != null && stack.func_77973_b() == primary.getItem() && (primary.getMeta() == 32767 || stack.func_77960_j() == primary.getMeta()); }
    public Optional<ItemStack> get() { return makeStack(); }

    private static class OreItemTag extends ItemTag {
        private final String oreName;
        private OreItemTag(String oreName) { super((Item)null); this.oreName = oreName; }
        private ItemStack oreStack() { return OreDictionary.getOres(oreName).isEmpty() ? null : OreDictionary.getOres(oreName).get(0); }
        public Optional<ItemEntry> getPrimary() { ItemStack stack = oreStack(); return Optional.ofNullable(stack == null ? null : new ItemEntry(stack.func_77973_b(), stack.func_77960_j())); }
        public ItemEntry requirePrimary() { ItemStack stack = oreStack(); if (stack == null) throw new IllegalStateException("Missing ore dictionary item " + oreName); return new ItemEntry(stack.func_77973_b(), stack.func_77960_j()); }
        public boolean is(ItemStack stack) {
            if (stack == null) return false;
            int target = OreDictionary.getOreID(oreName);
            for (int id : OreDictionary.getOreIDs(stack)) if (id == target) return true;
            return false;
        }
    }
}
