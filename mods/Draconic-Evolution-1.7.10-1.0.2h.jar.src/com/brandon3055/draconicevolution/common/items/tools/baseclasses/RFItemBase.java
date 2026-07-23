/*    */ package com.brandon3055.draconicevolution.common.items.tools.baseclasses;
/*    */ 
/*    */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*    */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*    */ import com.brandon3055.brandonscore.common.utills.Utills;
/*    */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*    */ import com.brandon3055.draconicevolution.api.IDraconicElectricItem;
/*    */ import com.brandon3055.draconicevolution.common.entity.EntityPersistentItem;
/*    */ import com.brandon3055.draconicevolution.common.items.ItemDE;
/*    */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*    */ import com.brandon3055.draconicevolution.common.utills.IHudDisplayItem;
/*    */ import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public abstract class RFItemBase
/*    */   extends ItemDE
/*    */   implements IDraconicElectricItem, IConfigurableItem, IHudDisplayItem
/*    */ {
/*    */   public RFItemBase() {
/* 31 */     func_77625_d(1);
/* 32 */     func_77637_a(DraconicEvolution.tabToolsWeapons);
/* 33 */     func_77627_a(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasCustomEntity(ItemStack stack) {
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity createEntity(World world, Entity location, ItemStack itemstack) {
/* 43 */     return (Entity)new EntityPersistentItem(world, location, itemstack);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean showDurabilityBar(ItemStack stack) {
/* 50 */     return (getCharge(stack) < getMaxCharge(stack));
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDurabilityForDisplay(ItemStack stack) {
/* 55 */     return 1.0D - getChargePercent(stack);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_150895_a(Item item, CreativeTabs tab, List<ItemStack> list) {
/* 62 */     ItemStack emptyStack = new ItemStack(item);
/* 63 */     list.add(emptyStack);
/*    */     
/* 65 */     if (getMaxCharge(emptyStack) > 0.0D) {
/* 66 */       list.add(makeChargedStack());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<ItemConfigField> getFields(ItemStack stack, int slot) {
/* 73 */     return new ArrayList<>();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getDisplayData(ItemStack stack) {
/* 78 */     List<String> list = new ArrayList<>();
/* 79 */     if (hasProfiles()) {
/* 80 */       int preset = ItemNBTHelper.getInteger(stack, "ConfigProfile", 0);
/* 81 */       list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.func_74838_a("info.de.capacitorMode.txt") + ": " + ItemNBTHelper.getString(stack, "ProfileName" + preset, "Profile " + preset));
/*    */     } 
/*    */     
/* 84 */     for (ItemConfigField field : getFields(stack, 0)) {
/* 85 */       list.add(field.getTooltipInfo());
/*    */     }
/*    */     
/* 88 */     if (getMaxCharge(stack) > 0.0D) {
/* 89 */       list.add(InfoHelper.ITC() + StatCollector.func_74838_a("info.de.charge.txt") + ": " + InfoHelper.HITC() + Utills.formatNumber(getCharge(stack)) + " / " + Utills.formatNumber(getMaxCharge(stack)));
/*    */     }
/*    */     
/* 92 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasProfiles() {
/* 97 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\baseclasses\RFItemBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */