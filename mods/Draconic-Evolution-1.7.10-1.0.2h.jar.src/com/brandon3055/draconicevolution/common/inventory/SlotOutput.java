/*    */ package com.brandon3055.draconicevolution.common.inventory;
/*    */ 
/*    */ import com.gamerforea.containerwarden.coremod.AsmHooks;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class SlotOutput
/*    */   extends Slot
/*    */ {
/*    */   public SlotOutput(IInventory inventory, int id, int x, int y) {
/* 12 */     super(inventory, id, x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 17 */     return AsmHooks.isItemValid(false, this, stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_75219_a() {
/* 22 */     return 64;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\inventory\SlotOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */