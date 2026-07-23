/*    */ package com.brandon3055.draconicevolution.common.blocks.itemblocks;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class LRDItemBlock
/*    */   extends ItemBlock
/*    */ {
/*    */   public LRDItemBlock(Block block) {
/* 12 */     super(block);
/* 13 */     func_77627_a(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_77647_b(int par1) {
/* 18 */     return par1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack stack) {
/* 23 */     return super.func_77667_c(stack) + stack.func_77960_j();
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\itemblocks\LRDItemBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */