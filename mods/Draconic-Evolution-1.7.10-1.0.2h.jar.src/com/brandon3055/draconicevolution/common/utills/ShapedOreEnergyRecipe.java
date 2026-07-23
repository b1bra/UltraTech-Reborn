/*    */ package com.brandon3055.draconicevolution.common.utills;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.inventory.InventoryCrafting;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.oredict.ShapedOreRecipe;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShapedOreEnergyRecipe
/*    */   extends ShapedOreRecipe
/*    */ {
/*    */   public ShapedOreEnergyRecipe(Block result, Object... recipe) {
/* 17 */     this(new ItemStack(result), recipe);
/*    */   }
/*    */   
/*    */   public ShapedOreEnergyRecipe(Item result, Object... recipe) {
/* 21 */     this(new ItemStack(result), recipe);
/*    */   }
/*    */   
/*    */   public ShapedOreEnergyRecipe(ItemStack result, Object... recipe) {
/* 25 */     super(result, recipe);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_77572_b(InventoryCrafting matrix) {
/* 30 */     ItemStack result = super.func_77572_b(matrix);
/* 31 */     if (result == null || !(result.func_77973_b() instanceof ic2.api.item.IElectricItem)) {
/* 32 */       return result;
/*    */     }
/*    */     
/* 35 */     double energy = 0.0D;
/*    */     
/* 37 */     for (int i = 0; i < matrix.func_70302_i_(); i++) {
/* 38 */       ItemStack stack = matrix.func_70301_a(i);
/* 39 */       if (stack != null && stack.func_77973_b() instanceof ic2.api.item.IElectricItem) {
/* 40 */         energy += ElectricItem.manager.getCharge(stack);
/*    */       }
/*    */     } 
/*    */     
/* 44 */     if (energy > 0.0D) {
/* 45 */       ElectricItem.manager.charge(result, energy, 2147483647, true, false);
/*    */     }
/*    */     
/* 48 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\commo\\utills\ShapedOreEnergyRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */