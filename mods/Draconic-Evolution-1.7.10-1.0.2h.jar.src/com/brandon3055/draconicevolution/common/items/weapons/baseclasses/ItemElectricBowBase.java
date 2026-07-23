/*    */ package com.brandon3055.draconicevolution.common.items.weapons.baseclasses;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.items.weapons.IEnergyContainerWeaponItem;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemBow;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public abstract class ItemElectricBowBase
/*    */   extends ItemBow
/*    */   implements IEnergyContainerWeaponItem {
/*    */   public boolean showDurabilityBar(ItemStack stack) {
/* 16 */     return (getCharge(stack) < getMaxCharge(stack));
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDurabilityForDisplay(ItemStack stack) {
/* 21 */     return 1.0D - getChargePercent(stack);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_150895_a(Item item, CreativeTabs tab, List<ItemStack> list) {
/* 28 */     list.add(new ItemStack((Item)this));
/* 29 */     list.add(makeChargedStack());
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\weapons\baseclasses\ItemElectricBowBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */