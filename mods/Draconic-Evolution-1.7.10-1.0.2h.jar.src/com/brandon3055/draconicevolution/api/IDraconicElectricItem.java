/*    */ package com.brandon3055.draconicevolution.api;
/*    */ 
/*    */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.api.item.ICustomDamageItem;
/*    */ import ic2.api.item.IElectricItem;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public interface IDraconicElectricItem
/*    */   extends IElectricItem, ICustomDamageItem
/*    */ {
/*    */   default int getCustomDamage(ItemStack stack) {
/* 17 */     return stack.func_77960_j();
/*    */   }
/*    */ 
/*    */   
/*    */   default int getMaxCustomDamage(ItemStack stack) {
/* 22 */     return stack.func_77958_k();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   default void setCustomDamage(ItemStack stack, int damage) {}
/*    */ 
/*    */   
/*    */   default boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase src) {
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   default Item getEmptyItem(ItemStack stack) {
/* 36 */     return (Item)this;
/*    */   }
/*    */ 
/*    */   
/*    */   default Item getChargedItem(ItemStack stack) {
/* 41 */     return (Item)this;
/*    */   }
/*    */ 
/*    */   
/*    */   default int getTier(ItemStack itemStack) {
/* 46 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   default boolean canProvideEnergy(ItemStack stack) {
/* 51 */     return false;
/*    */   }
/*    */   
/*    */   default double getChargePercent(ItemStack stack) {
/* 55 */     double charge = getCharge(stack);
/* 56 */     if (charge <= 0.0D || !Double.isFinite(charge)) {
/* 57 */       return 0.0D;
/*    */     }
/*    */     
/* 60 */     double maxCharge = getMaxCharge(stack);
/* 61 */     return (charge < maxCharge) ? (charge / maxCharge) : 1.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default double getCharge(ItemStack stack) {
/* 68 */     NBTTagCompound nbt = (stack != null) ? stack.func_77978_p() : null;
/* 69 */     return (nbt != null) ? nbt.func_74769_h("charge") : 0.0D;
/*    */   }
/*    */   
/*    */   default void setCharge(ItemStack stack, double charge) {
/* 73 */     if (stack != null) {
/* 74 */       ItemNBTHelper.setDouble(stack, "charge", charge);
/*    */     }
/*    */   }
/*    */   
/*    */   default ItemStack makeChargedStack() {
/* 79 */     return makeChargedStack(0);
/*    */   }
/*    */   
/*    */   default ItemStack makeChargedStack(int meta) {
/* 83 */     ItemStack stack = new ItemStack((Item)this, 1, meta);
/* 84 */     ElectricItem.manager.charge(stack, Double.POSITIVE_INFINITY, 2147483647, true, false);
/* 85 */     return stack;
/*    */   }
/*    */   
/*    */   default double discharge(ItemStack stack, double amount, boolean simulate) {
/* 89 */     return ElectricItem.manager.discharge(stack, amount, 2147483647, true, false, simulate);
/*    */   }
/*    */   
/*    */   default boolean useEnergy(ItemStack stack, double amount, EntityPlayer player) {
/* 93 */     return ElectricItem.manager.use(stack, amount, (EntityLivingBase)player);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\api\IDraconicElectricItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */