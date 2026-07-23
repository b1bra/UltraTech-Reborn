/*    */ package com.brandon3055.draconicevolution.common.inventory;
/*    */ 
/*    */ import com.brandon3055.brandonscore.common.utills.IC2Helper;
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileEnergyInfuser;
/*    */ import com.gamerforea.containerwarden.coremod.AsmHooks;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class SlotChargable
/*    */   extends Slot {
/*    */   public SlotChargable(IInventory inventory, int id, int x, int y) {
/* 13 */     super(inventory, id, x, y);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 19 */     return AsmHooks.isItemValid((super.func_75214_a(stack) && IC2Helper.isElectricItem(stack)), this, stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_75219_a() {
/* 24 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_111238_b() {
/* 30 */     return !((TileEnergyInfuser)this.field_75224_c).running;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\inventory\SlotChargable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */