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
/*     */ public class WyvernShovel
/*     */   extends MiningTool
/*     */   implements IInventoryTool, IRenderTweak
/*     */ {
/*     */   public WyvernShovel() {
/*  25 */     super(ModItems.WYVERN);
/*  26 */     setHarvestLevel("shovel", 10);
/*  27 */     func_77655_b("wyvernShovel");
/*  28 */     this.energyPerOperation = BalanceConfigHandler.wyvernToolsEnergyPerAction;
/*  29 */     ModItems.register((ItemDE)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemConfigField> getFields(ItemStack stack, int slot) {
/*  34 */     List<ItemConfigField> list = super.getFields(stack, slot);
/*  35 */     list.add((new ItemConfigField(2, slot, "ToolDigAOE")).setMinMaxAndIncromente(Integer.valueOf(0), Integer.valueOf(IUpgradableItem.EnumUpgrade.DIG_AOE.getUpgradePoints(stack)), Integer.valueOf(1)).readFromItem(stack, Integer.valueOf(0)).setModifier("AOE"));
/*  36 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInventoryName() {
/*  41 */     return StatCollector.func_74838_a("info.de.toolInventoryEnch.txt");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInventorySlots() {
/*  46 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnchantValid(Enchantment enchant) {
/*  51 */     return (enchant.field_77351_y == EnumEnchantmentType.digger);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tweakRender(IItemRenderer.ItemRenderType type) {
/*  56 */     GL11.glTranslated(0.23D, 0.85D, -0.1D);
/*  57 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  58 */     GL11.glRotatef(140.0F, 0.0F, -1.0F, 0.0F);
/*  59 */     GL11.glScaled(0.6D, 0.6D, 0.6D);
/*  60 */     if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
/*  61 */       GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/*  62 */       GL11.glTranslated(0.0D, -0.4D, 0.0D);
/*  63 */     } else if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/*  64 */       GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/*  65 */       GL11.glScalef(12.5F, 12.5F, 12.5F);
/*  66 */       GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*  67 */       GL11.glTranslated(-1.35D, 0.0D, -0.05D);
/*  68 */     } else if (type == IItemRenderer.ItemRenderType.ENTITY) {
/*  69 */       GL11.glRotatef(-90.5F, 0.0F, 1.0F, 0.0F);
/*  70 */       GL11.glTranslated(-0.3D, 0.0D, -0.6D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUpgradeCap(ItemStack itemstack) {
/*  76 */     return BalanceConfigHandler.wyvernToolsMaxUpgrades;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTier(ItemStack itemstack) {
/*  81 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUpgradeStats(ItemStack stack) {
/*  86 */     return super.getUpgradeStats(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/*  91 */     int points = IUpgradableItem.EnumUpgrade.RF_CAPACITY.getUpgradePoints(stack);
/*  92 */     return (BalanceConfigHandler.wyvernToolsBaseStorage + points * BalanceConfigHandler.wyvernToolsStoragePerUpgrade);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/*  97 */     return BalanceConfigHandler.wyvernToolsMaxTransfer;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex) {
/* 102 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.RF_CAPACITY.index) {
/* 103 */       return BalanceConfigHandler.wyvernToolsMaxCapacityUpgradePoints;
/*     */     }
/* 105 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_AOE.index) {
/* 106 */       return 2;
/*     */     }
/* 108 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_SPEED.index) {
/* 109 */       return 16;
/*     */     }
/* 111 */     return BalanceConfigHandler.wyvernToolsMaxUpgradePoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseUpgradePoints(int upgradeIndex) {
/* 116 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_AOE.index) {
/* 117 */       return 1;
/*     */     }
/* 119 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_SPEED.index) {
/* 120 */       return 4;
/*     */     }
/* 122 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IUpgradableItem.EnumUpgrade> getUpgrades(ItemStack itemstack) {
/* 127 */     List<IUpgradableItem.EnumUpgrade> list = super.getUpgrades(itemstack);
/* 128 */     list.remove(IUpgradableItem.EnumUpgrade.DIG_DEPTH);
/* 129 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\WyvernShovel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */