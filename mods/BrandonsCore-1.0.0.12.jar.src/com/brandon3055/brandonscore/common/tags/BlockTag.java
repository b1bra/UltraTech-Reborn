package com.brandon3055.brandonscore.common.tags;

import java.util.Optional;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockTag implements IItemMatcher {
    private final BlockEntry primary;

    public BlockTag(Block block) { this(block, 0); }
    public BlockTag(Block block, int meta) { this.primary = new BlockEntry(block, meta); }

    public static BlockTag get(String modid, String name) {
        if ("draconium".equals(name)) return new OreBlockTag("blockDraconium");
        if ("awakened_draconium".equals(name)) return new OreBlockTag("blockDraconiumAwakened");
        return new BlockTag((Block)null);
    }

    public Optional<BlockEntry> getPrimary() { return Optional.ofNullable(primary.getBlock() == null ? null : primary); }
    public BlockEntry requirePrimary() { if (primary.getBlock() == null) throw new IllegalStateException("Missing block tag"); return primary; }
    public Optional<ItemStack> makeStack() { return getPrimary().map(BlockEntry::toStack); }
    public ItemStack requireStack() { return requirePrimary().toStack(); }
    public boolean is(Block block) { return block == primary.getBlock(); }
    public boolean is(IBlockAccess world, int x, int y, int z) { return is(world.func_147439_a(x, y, z)); }
    public boolean is(ItemStack stack) { return stack != null && primary.getBlock() != null && stack.func_77973_b() == primary.getItem(); }
    public void set(World world, int x, int y, int z) { world.func_147465_d(x, y, z, requirePrimary().getBlock(), requirePrimary().getPlacementMeta(), 3); }

    private static class OreBlockTag extends BlockTag {
        private final String oreName;
        private OreBlockTag(String oreName) { super((Block)null); this.oreName = oreName; }
        private ItemStack oreStack() { return OreDictionary.getOres(oreName).isEmpty() ? null : OreDictionary.getOres(oreName).get(0); }
        private Block oreBlock() { ItemStack stack = oreStack(); return stack == null ? null : Block.func_149634_a(stack.func_77973_b()); }
        private int oreMeta() { ItemStack stack = oreStack(); return stack == null ? 0 : stack.func_77960_j(); }
        public Optional<BlockEntry> getPrimary() { Block block = oreBlock(); return Optional.ofNullable(block == null ? null : new BlockEntry(block, oreMeta())); }
        public BlockEntry requirePrimary() { Block block = oreBlock(); if (block == null) throw new IllegalStateException("Missing ore dictionary block " + oreName); return new BlockEntry(block, oreMeta()); }
        public boolean is(Block block) { return block != null && block == oreBlock(); }
        public boolean is(ItemStack stack) {
            if (stack == null) return false;
            int target = OreDictionary.getOreID(oreName);
            for (int id : OreDictionary.getOreIDs(stack)) if (id == target) return true;
            return false;
        }
    }
}
