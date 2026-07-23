/*    */ package com.brandon3055.brandonscore.common.utills;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.api.IDraconicElectricItem;
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.api.item.IElectricItem;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public final class IC2Helper {
/*    */   public static long floorEu(double eu) {
/* 11 */     return Double.isFinite(eu) ? Math.round(Math.floor(eu)) : 0L;
/*    */   }
/*    */   
/*    */   public static long ceilEu(double eu) {
/* 15 */     return Double.isFinite(eu) ? Math.round(Math.ceil(eu)) : 0L;
/*    */   }
/*    */   
/*    */   public static boolean isElectricItem(ItemStack stack) {
/* 19 */     return (stack != null && stack.func_77973_b() instanceof IElectricItem);
/*    */   }
/*    */   
/*    */   public static double getChargePercent(ItemStack stack) {
/* 23 */     if (stack == null) {
/* 24 */       return 0.0D;
/*    */     }
/*    */     
/* 27 */     Item item = stack.func_77973_b();
/* 28 */     if (item instanceof IDraconicElectricItem) {
/* 29 */       return ((IDraconicElectricItem)item).getChargePercent(stack);
/*    */     }
/*    */     
/* 32 */     if (!(item instanceof IElectricItem)) {
/* 33 */       return 0.0D;
/*    */     }
/*    */     
/* 36 */     double maxCharge = ((IElectricItem)item).getMaxCharge(stack);
/* 37 */     if (maxCharge <= 0.0D || !Double.isFinite(maxCharge)) {
/* 38 */       return 0.0D;
/*    */     }
/*    */     
/* 41 */     double charge = ElectricItem.manager.getCharge(stack);
/* 42 */     if (charge <= 0.0D || !Double.isFinite(charge)) {
/* 43 */       return 0.0D;
/*    */     }
/*    */     
/* 46 */     return (charge < maxCharge) ? (charge / maxCharge) : 1.0D;
/*    */   }
/*    */   
/*    */   public static double getCharge(ItemStack stack) {
/* 50 */     if (stack == null) {
/* 51 */       return 0.0D;
/*    */     }
/*    */     
/* 54 */     Item item = stack.func_77973_b();
/* 55 */     if (item instanceof IDraconicElectricItem) {
/* 56 */       return ((IDraconicElectricItem)item).getCharge(stack);
/*    */     }
/*    */     
/* 59 */     return ElectricItem.manager.getCharge(stack);
/*    */   }
/*    */   
/*    */   public static double getMaxCharge(ItemStack stack) {
/* 63 */     if (stack == null) {
/* 64 */       return 0.0D;
/*    */     }
/*    */     
/* 67 */     Item item = stack.func_77973_b();
/* 68 */     return (item instanceof IElectricItem) ? ((IElectricItem)item).getMaxCharge(stack) : 0.0D;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\commo\\utills\IC2Helper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */