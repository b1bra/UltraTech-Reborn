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
/*     */ import com.gamerforea.draconicevolution.EventConfig;
/*     */ import java.util.List;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraftforge.client.IItemRenderer;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ public class DraconicPickaxe
/*     */   extends MiningTool
/*     */   implements IInventoryTool, IRenderTweak
/*     */ {
/*     */   public DraconicPickaxe() {
/*  25 */     super(ModItems.AWAKENED);
/*  26 */     setHarvestLevel("pickaxe", 10);
/*  27 */     func_77655_b("draconicPickaxe");
/*  28 */     this.energyPerOperation = BalanceConfigHandler.draconicToolsEnergyPerAction;
/*  29 */     ModItems.register((ItemDE)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemConfigField> getFields(ItemStack stack, int slot) {
/*  34 */     List<ItemConfigField> list = super.getFields(stack, slot);
/*  35 */     list.add((new ItemConfigField(2, slot, "ToolDigAOE")).setMinMaxAndIncromente(Integer.valueOf(0), Integer.valueOf(IUpgradableItem.EnumUpgrade.DIG_AOE.getUpgradePoints(stack)), Integer.valueOf(1))
/*  36 */         .readFromItem(stack, Integer.valueOf(0))
/*  37 */         .setModifier("AOE"));
/*  38 */     list.add((new ItemConfigField(2, slot, "ToolDigDepth")).setMinMaxAndIncromente(Integer.valueOf(1), Integer.valueOf(IUpgradableItem.EnumUpgrade.DIG_DEPTH.getUpgradePoints(stack)), Integer.valueOf(1))
/*  39 */         .readFromItem(stack, Integer.valueOf(1)));
/*  40 */     list.add((new ItemConfigField(6, slot, "ToolVoidJunk")).readFromItem(stack, Boolean.valueOf(false)));
/*  41 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInventoryName() {
/*  46 */     return StatCollector.func_74838_a("info.de.toolInventoryOblit.txt");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInventorySlots() {
/*  51 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnchantValid(Enchantment enchant) {
/*  56 */     return (enchant.field_77351_y == EnumEnchantmentType.digger);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tweakRender(IItemRenderer.ItemRenderType type) {
/*  61 */     GL11.glTranslated(0.34D, 0.69D, 0.1D);
/*  62 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  63 */     GL11.glRotatef(140.0F, 0.0F, -1.0F, 0.0F);
/*  64 */     GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/*  65 */     GL11.glScaled(0.7D, 0.7D, 0.7D);
/*     */     
/*  67 */     if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/*  68 */       GL11.glScalef(11.8F, 11.8F, 11.8F);
/*  69 */       GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*  70 */       GL11.glTranslated(-1.2D, 0.0D, -0.35D);
/*  71 */     } else if (type == IItemRenderer.ItemRenderType.ENTITY) {
/*  72 */       GL11.glRotatef(90.5F, 0.0F, 1.0F, 0.0F);
/*  73 */       GL11.glTranslated(0.0D, 0.0D, -0.9D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUpgradeCap(ItemStack itemstack) {
/*  79 */     return BalanceConfigHandler.draconicToolsMaxUpgrades;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTier(ItemStack itemstack) {
/*  84 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUpgradeStats(ItemStack stack) {
/*  89 */     return super.getUpgradeStats(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/*  94 */     int points = IUpgradableItem.EnumUpgrade.RF_CAPACITY.getUpgradePoints(stack);
/*  95 */     return (BalanceConfigHandler.draconicToolsBaseStorage + points * BalanceConfigHandler.draconicToolsStoragePerUpgrade);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/* 100 */     return BalanceConfigHandler.draconicToolsMaxTransfer;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex) {
/* 105 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.RF_CAPACITY.index)
/* 106 */       return BalanceConfigHandler.draconicToolsMaxCapacityUpgradePoints; 
/* 107 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_AOE.index) {
/* 108 */       int max = 4;
/*     */ 
/*     */       
/* 111 */       max = Math.min(max, EventConfig.pickaxeMaxRange);
/*     */ 
/*     */       
/* 114 */       return max;
/*     */     } 
/* 116 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_DEPTH.index)
/* 117 */       return 5; 
/* 118 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_SPEED.index)
/* 119 */       return 32; 
/* 120 */     return BalanceConfigHandler.draconicToolsMaxUpgradePoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseUpgradePoints(int upgradeIndex) {
/* 125 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_AOE.index)
/* 126 */       return 2; 
/* 127 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_DEPTH.index)
/* 128 */       return 1; 
/* 129 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_SPEED.index)
/* 130 */       return 5; 
/* 131 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\DraconicPickaxe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */