/*    */ package com.brandon3055.draconicevolution.common.inventory;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class GenericInventory
/*    */   implements IInventory
/*    */ {
/*    */   public abstract ItemStack[] getStorage();
/*    */   
/*    */   public int func_70302_i_() {
/* 16 */     return (getStorage()).length;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_70301_a(int slot) {
/* 21 */     return (slot < (getStorage()).length) ? getStorage()[slot] : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_70298_a(int i, int count) {
/* 26 */     ItemStack itemstack = func_70301_a(i);
/*    */     
/* 28 */     if (itemstack != null) {
/* 29 */       if (itemstack.field_77994_a <= count) {
/* 30 */         func_70299_a(i, null);
/*    */       } else {
/* 32 */         itemstack = itemstack.func_77979_a(count);
/* 33 */         if (itemstack.field_77994_a == 0) {
/* 34 */           func_70299_a(i, null);
/*    */         }
/*    */       } 
/*    */     }
/* 38 */     return itemstack;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_70304_b(int i) {
/* 43 */     ItemStack item = func_70301_a(i);
/* 44 */     if (item != null) func_70299_a(i, null); 
/* 45 */     return item;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_70299_a(int i, ItemStack itemstack) {
/* 50 */     getStorage()[i] = itemstack;
/* 51 */     if (itemstack != null && itemstack.field_77994_a > func_70297_j_()) {
/* 52 */       itemstack.field_77994_a = func_70297_j_();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_145825_b() {
/* 58 */     return "";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_145818_k_() {
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_70297_j_() {
/* 68 */     return 64;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_70300_a(EntityPlayer player) {
/* 73 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70295_k_() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70305_f() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70296_d() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 91 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\inventory\GenericInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */