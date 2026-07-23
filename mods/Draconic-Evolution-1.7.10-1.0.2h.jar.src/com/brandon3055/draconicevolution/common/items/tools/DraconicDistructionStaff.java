/*     */ package com.brandon3055.draconicevolution.common.items.tools;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.draconicevolution.client.render.IRenderTweak;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.items.ItemDE;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.baseclasses.MiningTool;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.baseclasses.ToolHandler;
/*     */ import com.brandon3055.draconicevolution.common.items.weapons.IEnergyContainerWeaponItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.IInventoryTool;
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
/*     */ import com.gamerforea.draconicevolution.EventConfig;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.IItemRenderer;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class DraconicDistructionStaff
/*     */   extends MiningTool
/*     */   implements IInventoryTool, IRenderTweak, IEnergyContainerWeaponItem
/*     */ {
/*     */   public DraconicDistructionStaff() {
/*  37 */     super(ModItems.CHAOTIC);
/*  38 */     func_77655_b("draconicDistructionStaff");
/*  39 */     setHarvestLevel("pickaxe", 10);
/*  40 */     setHarvestLevel("shovel", 10);
/*  41 */     setHarvestLevel("axe", 10);
/*  42 */     this.energyPerOperation = BalanceConfigHandler.draconicToolsEnergyPerAction;
/*  43 */     ModItems.register((ItemDE)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_150893_a(ItemStack stack, Block block) {
/*  48 */     return getEfficiency(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemConfigField> getFields(ItemStack stack, int slot) {
/*  53 */     List<ItemConfigField> list = super.getFields(stack, slot);
/*  54 */     list.add((new ItemConfigField(2, slot, "ToolDigAOE")).setMinMaxAndIncromente(Integer.valueOf(0), Integer.valueOf(IUpgradableItem.EnumUpgrade.DIG_AOE.getUpgradePoints(stack)), Integer.valueOf(1))
/*  55 */         .readFromItem(stack, Integer.valueOf(0))
/*  56 */         .setModifier("AOE"));
/*  57 */     list.add((new ItemConfigField(2, slot, "ToolDigDepth")).setMinMaxAndIncromente(Integer.valueOf(1), Integer.valueOf(IUpgradableItem.EnumUpgrade.DIG_DEPTH.getUpgradePoints(stack)), Integer.valueOf(1))
/*  58 */         .readFromItem(stack, Integer.valueOf(1)));
/*  59 */     list.add((new ItemConfigField(2, slot, "WeaponAttackAOE")).setMinMaxAndIncromente(Integer.valueOf(0), Integer.valueOf(IUpgradableItem.EnumUpgrade.ATTACK_AOE.getUpgradePoints(stack)), Integer.valueOf(1))
/*  60 */         .readFromItem(stack, Integer.valueOf(1))
/*  61 */         .setModifier("AOE"));
/*  62 */     list.add((new ItemConfigField(6, slot, "ToolVoidJunk")).readFromItem(stack, Boolean.valueOf(false)));
/*  63 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInventoryName() {
/*  68 */     return StatCollector.func_74838_a("info.de.toolInventoryOblit.txt");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInventorySlots() {
/*  73 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnchantValid(Enchantment enchant) {
/*  78 */     return (enchant.field_77351_y == EnumEnchantmentType.digger || enchant.field_77351_y == EnumEnchantmentType.weapon);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
/*  83 */     entity.field_70172_ad = 0;
/*  84 */     ToolHandler.damageEntityBasedOnHealth(entity, player, 0.3F);
/*  85 */     ToolHandler.AOEAttack(player, entity, stack, IConfigurableItem.ProfileHelper.getInteger(stack, "WeaponAttackAOE", 0));
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack stack, World world, EntityPlayer player) {
/*  91 */     return super.func_77659_a(stack, world, player);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean extended) {
/*  98 */     super.func_77624_a(stack, player, list, extended);
/*     */     
/* 100 */     list.add("");
/* 101 */     list.add(EnumChatFormatting.BLUE + "+" + ToolHandler.getBaseAttackDamage(stack) + " " + StatCollector.func_74838_a("info.de.attackDamage.txt"));
/* 102 */     list.add(EnumChatFormatting.BLUE + "+30% " + StatCollector.func_74838_a("info.de.bonusHealthDamage.txt"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tweakRender(IItemRenderer.ItemRenderType type) {
/* 107 */     GL11.glTranslated(0.77D, 0.19D, -0.15D);
/* 108 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 109 */     GL11.glRotatef(-35.0F, 0.0F, -1.0F, 0.0F);
/* 110 */     GL11.glScaled(0.7D, 0.7D, 0.7D);
/*     */     
/* 112 */     if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/* 113 */       GL11.glScalef(6.0F, 6.0F, 6.0F);
/* 114 */       GL11.glRotatef(145.0F, 0.0F, 1.0F, 0.0F);
/* 115 */       GL11.glTranslated(-1.7D, 0.0D, 1.8D);
/* 116 */     } else if (type == IItemRenderer.ItemRenderType.ENTITY) {
/* 117 */       GL11.glRotatef(-34.5F, 0.0F, 1.0F, 0.0F);
/* 118 */       GL11.glTranslated(-1.1D, 0.0D, -0.2D);
/* 119 */     } else if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
/* 120 */       GL11.glTranslated(0.0D, 0.4D, 0.0D);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getUpgradeCap(ItemStack itemstack) {
/* 125 */     return BalanceConfigHandler.draconicStaffMaxUpgrades;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTier(ItemStack itemstack) {
/* 130 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex) {
/* 135 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.RF_CAPACITY.index)
/* 136 */       return BalanceConfigHandler.draconicStaffMaxCapacityUpgradePoints; 
/* 137 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_AOE.index) {
/* 138 */       int max = 5;
/*     */ 
/*     */       
/* 141 */       max = Math.min(max, EventConfig.staffMaxRange);
/*     */ 
/*     */       
/* 144 */       return max;
/*     */     } 
/* 146 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_DEPTH.index)
/* 147 */       return 11; 
/* 148 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ATTACK_AOE.index)
/* 149 */       return 13; 
/* 150 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ATTACK_DAMAGE.index)
/* 151 */       return 64; 
/* 152 */     return BalanceConfigHandler.draconicStaffMaxUpgradePoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseUpgradePoints(int upgradeIndex) {
/* 157 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_AOE.index)
/* 158 */       return 3; 
/* 159 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_DEPTH.index)
/* 160 */       return 7; 
/* 161 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ATTACK_AOE.index)
/* 162 */       return 3; 
/* 163 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ATTACK_DAMAGE.index)
/* 164 */       return 0; 
/* 165 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/* 170 */     int points = IUpgradableItem.EnumUpgrade.RF_CAPACITY.getUpgradePoints(stack);
/* 171 */     return (BalanceConfigHandler.draconicToolsBaseStorage * 2 + BalanceConfigHandler.draconicWeaponsBaseStorage + points * (BalanceConfigHandler.draconicToolsStoragePerUpgrade + BalanceConfigHandler.draconicWeaponsStoragePerUpgrade));
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/* 176 */     return (BalanceConfigHandler.draconicToolsMaxTransfer * 2 + BalanceConfigHandler.draconicWeaponsMaxTransfer);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IUpgradableItem.EnumUpgrade> getUpgrades(ItemStack itemstack) {
/* 181 */     List<IUpgradableItem.EnumUpgrade> list = super.getUpgrades(itemstack);
/* 182 */     list.add(IUpgradableItem.EnumUpgrade.ATTACK_AOE);
/* 183 */     list.add(IUpgradableItem.EnumUpgrade.ATTACK_DAMAGE);
/* 184 */     list.remove(IUpgradableItem.EnumUpgrade.DIG_SPEED);
/*     */     
/* 186 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUpgradeStats(ItemStack stack) {
/* 191 */     List<String> list = super.getUpgradeStats(stack);
/* 192 */     list.add(InfoHelper.ITC() + StatCollector.func_74838_a("info.de.attackDamage.txt") + ": " + InfoHelper.HITC() + ToolHandler.getBaseAttackDamage(stack));
/* 193 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyPerAttack() {
/* 198 */     return BalanceConfigHandler.draconicWeaponsEnergyPerAttack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\DraconicDistructionStaff.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */