/*    */ package com.brandon3055.draconicevolution.common.inventory;
/*    */ import com.gamerforea.containerwarden.coremod.AsmHooks;
/*    */ import cpw.mods.fml.common.registry.GameRegistry;
/*    */ import net.loliland.mctags.api.OreDict;
/*    */ import net.loliland.mctags.api.Tags;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemHoe;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.ItemSword;
/*    */ import net.minecraft.item.ItemTool;
/*    */ 
/*    */ public class SlotItemValid extends Slot {
/*    */   private Item item;
/*    */   
/*    */   public SlotItemValid(IInventory inventory, int id, int x, int y, Item validItem) {
/* 19 */     super(inventory, id, x, y);
/*    */     
/* 21 */     this.item = validItem;
/*    */   }
/*    */   private boolean fuel = false;
/*    */   public SlotItemValid(IInventory inventory, int id, int x, int y, boolean fuel) {
/* 25 */     super(inventory, id, x, y);
/*    */     
/* 27 */     this.fuel = fuel;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 32 */     if (this.fuel) {
/* 33 */       return AsmHooks.isItemValid((getItemBurnTime(stack) > 0), this, stack);
/*    */     }
/* 35 */     return AsmHooks.isItemValid(stack.func_77969_a(new ItemStack(this.item)), this, stack);
/*    */   }
/*    */   
/*    */   public static int getItemBurnTime(ItemStack stack) {
/* 39 */     if (stack == null) {
/* 40 */       return 0;
/*    */     }
/* 42 */     Item item = stack.func_77973_b();
/*    */     
/* 44 */     if (item instanceof net.minecraft.item.ItemBlock && Block.func_149634_a(item) != Blocks.field_150350_a) {
/* 45 */       Block block = Block.func_149634_a(item);
/*    */       
/* 47 */       if (block.func_149688_o() == Material.field_151575_d) {
/* 48 */         return 300;
/*    */       }
/*    */     } 
/*    */     
/* 52 */     if (OreDict.SLAB_WOOD.is(stack)) {
/* 53 */       return 150;
/*    */     }
/*    */     
/* 56 */     if (Tags.Blocks.COAL_BLOCK.is(stack)) {
/* 57 */       return 16000;
/*    */     }
/*    */     
/* 60 */     if (item instanceof ItemTool && ((ItemTool)item).func_77861_e().equals("WOOD"))
/* 61 */       return 200; 
/* 62 */     if (item instanceof ItemSword && ((ItemSword)item).func_150932_j().equals("WOOD"))
/* 63 */       return 200; 
/* 64 */     if (item instanceof ItemHoe && ((ItemHoe)item).func_77842_f().equals("WOOD"))
/* 65 */       return 200; 
/* 66 */     if (Tags.Items.STICK.is(stack))
/* 67 */       return 100; 
/* 68 */     if (Tags.Items.COAL.is(stack))
/* 69 */       return 1600; 
/* 70 */     if (Tags.Items.LAVA_BUCKET.is(stack))
/* 71 */       return 20000; 
/* 72 */     if (OreDict.TREE_SAPLING.is(stack)) {
/* 73 */       return 100;
/*    */     }
/*    */ 
/*    */     
/* 77 */     if (Tags.Items.BLAZE_ROD.is(stack))
/*    */     {
/* 79 */       return 2400;
/*    */     }
/* 81 */     return GameRegistry.getFuelValue(stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\inventory\SlotItemValid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */