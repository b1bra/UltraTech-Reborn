/*    */ package com.brandon3055.draconicevolution.common.items;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemDE
/*    */   extends Item {
/*    */   public String getUnwrappedUnlocalizedName(String unlocalizedName) {
/* 12 */     return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77658_a() {
/* 17 */     return String.format("item.%s%s", new Object[] { "draconicevolution:", getUnwrappedUnlocalizedName(super.func_77658_a()) });
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack itemStack) {
/* 22 */     return func_77658_a();
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister iconRegister) {
/* 28 */     this.field_77791_bV = iconRegister.func_94245_a("draconicevolution:" + getUnwrappedUnlocalizedName(super.func_77658_a()));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasCustomEntity(ItemStack stack) {
/* 33 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\ItemDE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */