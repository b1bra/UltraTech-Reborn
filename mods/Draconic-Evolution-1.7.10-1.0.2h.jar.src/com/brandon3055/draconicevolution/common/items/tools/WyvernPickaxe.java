/*     */ package com.brandon3055.draconicevolution.common.items.tools;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.client.render.IRenderTweak;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.items.ItemDE;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.baseclasses.MiningTool;
/*     */ import com.brandon3055.draconicevolution.common.utills.IInventoryTool;
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
/*     */ import java.util.List;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraftforge.client.IItemRenderer;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ public class WyvernPickaxe
/*     */   extends MiningTool
/*     */   implements IInventoryTool, IRenderTweak
/*     */ {
/*     */   public WyvernPickaxe() {
/*  24 */     super(ModItems.WYVERN);
/*  25 */     func_77655_b("wyvernPickaxe");
/*  26 */     setHarvestLevel("pickaxe", 10);
/*  27 */     this.energyPerOperation = BalanceConfigHandler.wyvernToolsEnergyPerAction;
/*  28 */     ModItems.register((ItemDE)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemConfigField> getFields(ItemStack stack, int slot) {
/*  33 */     List<ItemConfigField> list = super.getFields(stack, slot);
/*  34 */     list.add((new ItemConfigField(2, slot, "ToolDigAOE")).setMinMaxAndIncromente(Integer.valueOf(0), Integer.valueOf(IUpgradableItem.EnumUpgrade.DIG_AOE.getUpgradePoints(stack)), Integer.valueOf(1)).readFromItem(stack, Integer.valueOf(0)).setModifier("AOE"));
/*  35 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInventoryName() {
/*  40 */     return StatCollector.func_74838_a("info.de.toolInventoryEnch.txt");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInventorySlots() {
/*  45 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnchantValid(Enchantment enchant) {
/*  50 */     return (enchant.field_77351_y == EnumEnchantmentType.digger);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tweakRender(IItemRenderer.ItemRenderType type) {
/*  56 */     GL11.glTranslated(0.4D, 0.65D, 0.1D);
/*     */     
/*  58 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  59 */     GL11.glRotatef(140.0F, 0.0F, -1.0F, 0.0F);
/*  60 */     GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/*  61 */     GL11.glScaled(0.6D, 0.6D, 0.6D);
/*     */     
/*  63 */     if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/*  64 */       GL11.glScalef(13.8F, 13.8F, 13.8F);
/*  65 */       GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*  66 */       GL11.glTranslated(-1.2D, 0.0D, -0.35D);
/*  67 */     } else if (type == IItemRenderer.ItemRenderType.ENTITY) {
/*  68 */       GL11.glRotatef(90.5F, 0.0F, 1.0F, 0.0F);
/*  69 */       GL11.glTranslated(-0.1D, 0.0D, -0.9D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUpgradeCap(ItemStack itemstack) {
/*  75 */     return BalanceConfigHandler.wyvernToolsMaxUpgrades;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTier(ItemStack itemstack) {
/*  80 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUpgradeStats(ItemStack stack) {
/*  85 */     return super.getUpgradeStats(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/*  90 */     int points = IUpgradableItem.EnumUpgrade.RF_CAPACITY.getUpgradePoints(stack);
/*  91 */     return (BalanceConfigHandler.wyvernToolsBaseStorage + points * BalanceConfigHandler.wyvernToolsStoragePerUpgrade);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/*  96 */     return BalanceConfigHandler.wyvernToolsMaxTransfer;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex) {
/* 101 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.RF_CAPACITY.index) {
/* 102 */       return BalanceConfigHandler.wyvernToolsMaxCapacityUpgradePoints;
/*     */     }
/* 104 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_AOE.index) {
/* 105 */       return 2;
/*     */     }
/* 107 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_SPEED.index) {
/* 108 */       return 16;
/*     */     }
/* 110 */     return BalanceConfigHandler.wyvernToolsMaxUpgradePoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseUpgradePoints(int upgradeIndex) {
/* 115 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_AOE.index) {
/* 116 */       return 1;
/*     */     }
/* 118 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_SPEED.index) {
/* 119 */       return 4;
/*     */     }
/* 121 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IUpgradableItem.EnumUpgrade> getUpgrades(ItemStack itemstack) {
/* 126 */     List<IUpgradableItem.EnumUpgrade> list = super.getUpgrades(itemstack);
/* 127 */     list.remove(IUpgradableItem.EnumUpgrade.DIG_DEPTH);
/* 128 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\WyvernPickaxe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */