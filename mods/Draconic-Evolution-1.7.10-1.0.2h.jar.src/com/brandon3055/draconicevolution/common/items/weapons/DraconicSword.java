/*     */ package com.brandon3055.draconicevolution.common.items.weapons;
/*     */ import com.brandon3055.brandonscore.BrandonsCore;
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.entity.EntityPersistentItem;
/*     */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.baseclasses.ToolBase;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.baseclasses.ToolHandler;
/*     */ import com.brandon3055.draconicevolution.common.items.weapons.baseclasses.ItemElectricSwordBase;
/*     */ import com.brandon3055.draconicevolution.common.network.ToolModePacket;
/*     */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.IHudDisplayItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.IInventoryTool;
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
/*     */ import com.google.common.collect.Multimap;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.IItemRenderer;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ public class DraconicSword extends ItemElectricSwordBase implements IInventoryTool, IRenderTweak, IUpgradableItem, IHudDisplayItem {
/*     */   public DraconicSword() {
/*  40 */     super(ModItems.AWAKENED);
/*  41 */     func_77655_b("draconicSword");
/*  42 */     func_77637_a(DraconicEvolution.tabToolsWeapons);
/*  43 */     if (ModItems.isEnabled((Item)this)) GameRegistry.registerItem((Item)this, "draconicSword");
/*     */   
/*     */   }
/*     */   
/*     */   public boolean func_77616_k(ItemStack p_77616_1_) {
/*  48 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77658_a() {
/*  54 */     return String.format("item.%s%s", new Object[] { "draconicevolution:", super.func_77658_a().substring(super.func_77658_a().indexOf('.') + 1) });
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack itemStack) {
/*  59 */     return func_77658_a();
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister iconRegister) {
/*  65 */     this.field_77791_bV = iconRegister.func_94245_a("draconicevolution:draconic_sword");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
/*  70 */     entity.field_70172_ad = 0;
/*  71 */     ToolHandler.AOEAttack(player, entity, stack, IConfigurableItem.ProfileHelper.getInteger(stack, "WeaponAttackAOE", 0));
/*  72 */     ToolHandler.damageEntityBasedOnHealth(entity, player, 0.2F);
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean extraInformation) {
/*  79 */     if (InfoHelper.holdShiftForDetails(list)) {
/*  80 */       List<ItemConfigField> l = getFields(stack, 0);
/*  81 */       for (ItemConfigField f : l) list.add(f.getTooltipInfo()); 
/*  82 */       list.add(InfoHelper.ITC() + StatCollector.func_74838_a("info.de.sword.txt"));
/*  83 */       InfoHelper.addLore(stack, list);
/*     */     } 
/*  85 */     ToolBase.holdCTRLForUpgrades(list, stack);
/*  86 */     InfoHelper.addEnergyInfo(stack, list);
/*  87 */     list.add("");
/*  88 */     list.add(EnumChatFormatting.BLUE + "+" + ToolHandler.getBaseAttackDamage(stack) + " " + StatCollector.func_74838_a("info.de.attackDamage.txt"));
/*  89 */     list.add(EnumChatFormatting.BLUE + "+20% " + StatCollector.func_74838_a("info.de.bonusHealthDamage.txt"));
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/*  94 */     return EnumRarity.rare;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/*  99 */     int points = IUpgradableItem.EnumUpgrade.RF_CAPACITY.getUpgradePoints(stack);
/* 100 */     return (BalanceConfigHandler.draconicWeaponsBaseStorage + points * BalanceConfigHandler.draconicWeaponsStoragePerUpgrade);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/* 105 */     return BalanceConfigHandler.draconicWeaponsMaxTransfer;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCustomEntity(ItemStack stack) {
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity createEntity(World world, Entity location, ItemStack itemstack) {
/* 115 */     return (Entity)new EntityPersistentItem(world, location, itemstack);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInventoryName() {
/* 120 */     return StatCollector.func_74838_a("info.de.toolInventoryEnch.txt");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInventorySlots() {
/* 125 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnchantValid(Enchantment enchant) {
/* 130 */     return (enchant.field_77351_y == EnumEnchantmentType.weapon);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemConfigField> getFields(ItemStack stack, int slot) {
/* 135 */     List<ItemConfigField> list = new ArrayList<>();
/* 136 */     list.add((new ItemConfigField(2, slot, "WeaponAttackAOE")).setMinMaxAndIncromente(Integer.valueOf(0), Integer.valueOf(IUpgradableItem.EnumUpgrade.ATTACK_AOE.getUpgradePoints(stack)), Integer.valueOf(1)).readFromItem(stack, Integer.valueOf(1)).setModifier("AOE"));
/* 137 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap getAttributeModifiers(ItemStack stack) {
/* 142 */     Multimap map = super.getAttributeModifiers(stack);
/* 143 */     map.clear();
/* 144 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tweakRender(IItemRenderer.ItemRenderType type) {
/* 150 */     GL11.glTranslated(-0.01D, 1.11D, -0.15D);
/* 151 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 152 */     GL11.glRotatef(140.0F, 0.0F, -1.0F, 0.0F);
/* 153 */     GL11.glScaled(0.7D, 0.7D, 0.7D);
/*     */     
/* 155 */     if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
/* 156 */       GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/* 157 */       GL11.glTranslated(0.0D, -0.4D, 0.0D);
/* 158 */     } else if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/* 159 */       GL11.glScalef(8.0F, 8.0F, 8.0F);
/* 160 */       GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 161 */       GL11.glTranslated(1.9D, 0.0D, 0.0D);
/* 162 */     } else if (type == IItemRenderer.ItemRenderType.ENTITY) {
/* 163 */       GL11.glRotatef(-90.5F, 0.0F, 1.0F, 0.0F);
/* 164 */       GL11.glTranslated(-0.8D, 0.0D, 0.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IUpgradableItem.EnumUpgrade> getUpgrades(ItemStack itemstack) {
/* 170 */     return new ArrayList<IUpgradableItem.EnumUpgrade>()
/*     */       {
/*     */       
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUpgradeCap(ItemStack itemstack) {
/* 179 */     return BalanceConfigHandler.draconicWeaponsMaxUpgrades;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTier(ItemStack itemstack) {
/* 184 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex) {
/* 189 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.RF_CAPACITY.index) {
/* 190 */       return BalanceConfigHandler.draconicWeaponsMaxCapacityUpgradePoints;
/*     */     }
/* 192 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ATTACK_AOE.index) {
/* 193 */       return 5;
/*     */     }
/* 195 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ATTACK_DAMAGE.index) {
/* 196 */       return 16;
/*     */     }
/* 198 */     return BalanceConfigHandler.draconicWeaponsMaxUpgradePoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex, ItemStack stack) {
/* 203 */     return getMaxUpgradePoints(upgradeIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseUpgradePoints(int upgradeIndex) {
/* 208 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ATTACK_AOE.index) {
/* 209 */       return 2;
/*     */     }
/* 211 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ARROW_DAMAGE.index) {
/* 212 */       return 0;
/*     */     }
/* 214 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUpgradeStats(ItemStack stack) {
/* 219 */     List<String> strings = new ArrayList<>();
/*     */     
/* 221 */     int attackaoe = 0;
/* 222 */     for (ItemConfigField field : getFields(stack, 0)) {
/* 223 */       if (field.name.equals("WeaponAttackAOE")) attackaoe = 1 + ((Integer)field.max).intValue() * 2; 
/*     */     } 
/* 225 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.RFCapacity.txt") + ": " + InfoHelper.HITC() + Utills.formatNumber(getMaxCharge(stack)));
/* 226 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("info.de.attackDamage.txt") + ": " + InfoHelper.HITC() + ToolHandler.getBaseAttackDamage(stack));
/* 227 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.max.txt") + " " + StatCollector.func_74838_a("gui.de.AttackAOE.txt") + ": " + InfoHelper.HITC() + attackaoe + "x" + attackaoe);
/*     */ 
/*     */     
/* 230 */     return strings;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getDisplayData(ItemStack stack) {
/* 235 */     List<String> list = new ArrayList<>();
/* 236 */     for (ItemConfigField field : getFields(stack, 0))
/* 237 */       list.add(field.getTooltipInfo()); 
/* 238 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack stack, World world, EntityPlayer player) {
/* 243 */     if (!world.field_72995_K && !BrandonsCore.proxy.isDedicatedServer()) {
/* 244 */       ToolBase.handleModeChange(stack, player, InfoHelper.isShiftKeyDown(), InfoHelper.isCtrlKeyDown());
/* 245 */     } else if (world.field_72995_K && BrandonsCore.proxy.getMCServer() == null) {
/* 246 */       ToolBase.handleModeChange(stack, player, InfoHelper.isShiftKeyDown(), InfoHelper.isCtrlKeyDown());
/* 247 */       DraconicEvolution.network.sendToServer((IMessage)new ToolModePacket(InfoHelper.isShiftKeyDown(), InfoHelper.isCtrlKeyDown()));
/*     */     } 
/* 249 */     return super.func_77659_a(stack, world, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasProfiles() {
/* 254 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyPerAttack() {
/* 259 */     return BalanceConfigHandler.draconicWeaponsEnergyPerAttack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\weapons\DraconicSword.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */