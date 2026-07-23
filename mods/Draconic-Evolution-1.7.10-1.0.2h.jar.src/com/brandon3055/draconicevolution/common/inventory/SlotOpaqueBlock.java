/*    */ package com.brandon3055.draconicevolution.common.inventory;
/*    */ 
/*    */ import com.gamerforea.containerwarden.coremod.AsmHooks;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class SlotOpaqueBlock
/*    */   extends Slot {
/*    */   public SlotOpaqueBlock(IInventory inventory, int id, int x, int y) {
/* 12 */     super(inventory, id, x, y);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 18 */     if (super.func_75214_a(stack)) {
/* 19 */       Block block = Block.func_149634_a(stack.func_77973_b());
/* 20 */       return AsmHooks.isItemValid((block.func_149662_c() && block.func_149686_d()), this, stack);
/*    */     } 
/* 22 */     return AsmHooks.isItemValid(false, this, stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_75219_a() {
/* 27 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\inventory\SlotOpaqueBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */