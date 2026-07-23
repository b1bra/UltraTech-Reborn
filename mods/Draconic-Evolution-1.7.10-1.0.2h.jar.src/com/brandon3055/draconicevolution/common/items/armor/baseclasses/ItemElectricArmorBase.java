/*    */ package com.brandon3055.draconicevolution.common.items.armor.baseclasses;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.items.armor.ICustomArmor;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemArmor;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public abstract class ItemElectricArmorBase
/*    */   extends ItemArmor implements ICustomArmor {
/*    */   public ItemElectricArmorBase(ItemArmor.ArmorMaterial material, int renderIndex, int armorType) {
/* 15 */     super(material, renderIndex, armorType);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDurabilityForDisplay(ItemStack stack) {
/* 20 */     return 1.0D - getChargePercent(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean showDurabilityBar(ItemStack stack) {
/* 25 */     return (getCharge(stack) < getMaxCharge(stack));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_150895_a(Item item, CreativeTabs tab, List<ItemStack> list) {
/* 32 */     list.add(new ItemStack((Item)this));
/* 33 */     list.add(makeChargedStack());
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\armor\baseclasses\ItemElectricArmorBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */