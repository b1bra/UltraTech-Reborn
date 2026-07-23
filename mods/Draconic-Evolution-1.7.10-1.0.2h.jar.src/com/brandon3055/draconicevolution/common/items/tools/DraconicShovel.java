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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DraconicShovel
/*     */   extends MiningTool
/*     */   implements IInventoryTool, IRenderTweak
/*     */ {
/*     */   public DraconicShovel() {
/*  27 */     super(ModItems.AWAKENED);
/*  28 */     setHarvestLevel("shovel", 10);
/*  29 */     func_77655_b("draconicShovel");
/*  30 */     this.energyPerOperation = BalanceConfigHandler.draconicToolsEnergyPerAction;
/*  31 */     ModItems.register((ItemDE)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemConfigField> getFields(ItemStack stack, int slot) {
/*  36 */     List<ItemConfigField> list = super.getFields(stack, slot);
/*  37 */     list.add((new ItemConfigField(2, slot, "ToolDigAOE")).setMinMaxAndIncromente(Integer.valueOf(0), Integer.valueOf(IUpgradableItem.EnumUpgrade.DIG_AOE.getUpgradePoints(stack)), Integer.valueOf(1)).readFromItem(stack, Integer.valueOf(0)).setModifier("AOE"));
/*  38 */     list.add((new ItemConfigField(2, slot, "ToolDigDepth")).setMinMaxAndIncromente(Integer.valueOf(1), Integer.valueOf(IUpgradableItem.EnumUpgrade.DIG_DEPTH.getUpgradePoints(stack)), Integer.valueOf(1)).readFromItem(stack, Integer.valueOf(1)));
/*  39 */     list.add((new ItemConfigField(6, slot, "ToolVoidJunk")).readFromItem(stack, Boolean.valueOf(false)));
/*  40 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInventoryName() {
/*  45 */     return StatCollector.func_74838_a("info.de.toolInventoryOblit.txt");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInventorySlots() {
/*  50 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnchantValid(Enchantment enchant) {
/*  55 */     return (enchant.field_77351_y == EnumEnchantmentType.digger);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tweakRender(IItemRenderer.ItemRenderType type) {
/*  60 */     GL11.glTranslated(0.15D, 0.9D, -0.12D);
/*  61 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  62 */     GL11.glRotatef(140.0F, 0.0F, -1.0F, 0.0F);
/*  63 */     GL11.glScaled(0.7D, 0.7D, 0.7D);
/*  64 */     if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
/*  65 */       GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/*  66 */       GL11.glTranslated(0.0D, -0.4D, 0.0D);
/*  67 */     } else if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/*  68 */       GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/*  69 */       GL11.glScalef(10.0F, 10.0F, 10.0F);
/*  70 */       GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*  71 */       GL11.glTranslated(-1.45D, 0.0D, -0.15D);
/*  72 */     } else if (type == IItemRenderer.ItemRenderType.ENTITY) {
/*  73 */       GL11.glRotatef(-90.5F, 0.0F, 1.0F, 0.0F);
/*  74 */       GL11.glTranslated(-0.38D, 0.0D, -0.6D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUpgradeCap(ItemStack itemstack) {
/*  80 */     return BalanceConfigHandler.draconicToolsMaxUpgrades;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTier(ItemStack itemstack) {
/*  85 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUpgradeStats(ItemStack stack) {
/*  90 */     return super.getUpgradeStats(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/*  95 */     int points = IUpgradableItem.EnumUpgrade.RF_CAPACITY.getUpgradePoints(stack);
/*  96 */     return (BalanceConfigHandler.draconicToolsBaseStorage + points * BalanceConfigHandler.draconicToolsStoragePerUpgrade);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/* 101 */     return BalanceConfigHandler.draconicToolsMaxTransfer;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex) {
/* 106 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.RF_CAPACITY.index) {
/* 107 */       return BalanceConfigHandler.draconicToolsMaxCapacityUpgradePoints;
/*     */     }
/* 109 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_AOE.index) {
/* 110 */       return 4;
/*     */     }
/* 112 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_DEPTH.index) {
/* 113 */       return 5;
/*     */     }
/* 115 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_SPEED.index) {
/* 116 */       return 32;
/*     */     }
/* 118 */     return BalanceConfigHandler.draconicToolsMaxUpgradePoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseUpgradePoints(int upgradeIndex) {
/* 123 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_AOE.index) {
/* 124 */       return 2;
/*     */     }
/* 126 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_DEPTH.index) {
/* 127 */       return 1;
/*     */     }
/* 129 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_SPEED.index) {
/* 130 */       return 5;
/*     */     }
/* 132 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\DraconicShovel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */