/*     */ package com.brandon3055.draconicevolution.common.items.weapons;
/*     */ import com.brandon3055.brandonscore.BrandonsCore;
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.client.render.IRenderTweak;
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
/*     */ import net.minecraft.entity.EntityLivingBase;
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
/*     */ public class WyvernSword extends ItemElectricSwordBase implements IInventoryTool, IRenderTweak, IUpgradableItem, IHudDisplayItem {
/*     */   public WyvernSword() {
/*  42 */     super(ModItems.WYVERN);
/*  43 */     func_77655_b("wyvernSword");
/*  44 */     func_77637_a(DraconicEvolution.tabToolsWeapons);
/*  45 */     if (ModItems.isEnabled((Item)this)) GameRegistry.registerItem((Item)this, "wyvernSword");
/*     */   
/*     */   }
/*     */   
/*     */   public boolean func_77616_k(ItemStack p_77616_1_) {
/*  50 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77658_a() {
/*  56 */     return String.format("item.%s%s", new Object[] { "draconicevolution:", super.func_77658_a().substring(super.func_77658_a().indexOf('.') + 1) });
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack itemStack) {
/*  61 */     return func_77658_a();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister iconRegister) {
/*  68 */     this.field_77791_bV = iconRegister.func_94245_a("draconicevolution:sword_wyvern");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
/*  73 */     ToolHandler.AOEAttack(player, entity, stack, IConfigurableItem.ProfileHelper.getInteger(stack, "WeaponAttackAOE", 0));
/*  74 */     ToolHandler.damageEntityBasedOnHealth(entity, player, 0.1F);
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean extraInformation) {
/*  81 */     if (InfoHelper.holdShiftForDetails(list)) {
/*  82 */       List<ItemConfigField> l = getFields(stack, 0);
/*  83 */       for (ItemConfigField f : l) list.add(f.getTooltipInfo()); 
/*  84 */       list.add(InfoHelper.ITC() + StatCollector.func_74838_a("info.de.sword.txt"));
/*  85 */       InfoHelper.addLore(stack, list);
/*     */     } 
/*  87 */     ToolBase.holdCTRLForUpgrades(list, stack);
/*  88 */     InfoHelper.addEnergyInfo(stack, list);
/*  89 */     list.add("");
/*  90 */     list.add(EnumChatFormatting.BLUE + "+" + ToolHandler.getBaseAttackDamage(stack) + " " + StatCollector.func_74838_a("info.de.attackDamage.txt"));
/*  91 */     list.add(EnumChatFormatting.BLUE + "+10% " + StatCollector.func_74838_a("info.de.bonusHealthDamage.txt"));
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/*  96 */     return EnumRarity.uncommon;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77644_a(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
/* 101 */     return super.func_77644_a(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/* 106 */     int points = IUpgradableItem.EnumUpgrade.RF_CAPACITY.getUpgradePoints(stack);
/* 107 */     return (BalanceConfigHandler.wyvernWeaponsBaseStorage + points * BalanceConfigHandler.wyvernWeaponsStoragePerUpgrade);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/* 112 */     return BalanceConfigHandler.wyvernWeaponsMaxTransfer;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCustomEntity(ItemStack stack) {
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity createEntity(World world, Entity location, ItemStack itemstack) {
/* 122 */     return (Entity)new EntityPersistentItem(world, location, itemstack);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInventoryName() {
/* 127 */     return StatCollector.func_74838_a("info.de.toolInventoryEnch.txt");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInventorySlots() {
/* 132 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnchantValid(Enchantment enchant) {
/* 137 */     return (enchant.field_77351_y == EnumEnchantmentType.weapon);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemConfigField> getFields(ItemStack stack, int slot) {
/* 142 */     List<ItemConfigField> list = new ArrayList<>();
/* 143 */     list.add((new ItemConfigField(2, slot, "WeaponAttackAOE")).setMinMaxAndIncromente(Integer.valueOf(0), Integer.valueOf(IUpgradableItem.EnumUpgrade.ATTACK_AOE.getUpgradePoints(stack)), Integer.valueOf(1)).readFromItem(stack, Integer.valueOf(1)).setModifier("AOE"));
/* 144 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap getAttributeModifiers(ItemStack stack) {
/* 149 */     Multimap map = super.getAttributeModifiers(stack);
/* 150 */     map.clear();
/* 151 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tweakRender(IItemRenderer.ItemRenderType type) {
/* 156 */     GL11.glTranslated(0.25D, 0.8D, 0.05D);
/* 157 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 158 */     GL11.glRotatef(140.0F, 0.0F, -1.0F, 0.0F);
/* 159 */     GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/* 160 */     GL11.glScaled(0.6D, 0.6D, 0.6D);
/*     */     
/* 162 */     if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/* 163 */       GL11.glScalef(11.8F, 11.8F, 11.8F);
/* 164 */       GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 165 */       GL11.glTranslated(-1.5D, 0.0D, -0.1D);
/* 166 */     } else if (type == IItemRenderer.ItemRenderType.ENTITY) {
/* 167 */       GL11.glRotatef(90.5F, 0.0F, 1.0F, 0.0F);
/* 168 */       GL11.glTranslated(0.2D, 0.0D, -0.8D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IUpgradableItem.EnumUpgrade> getUpgrades(ItemStack itemstack) {
/* 174 */     return new ArrayList<IUpgradableItem.EnumUpgrade>()
/*     */       {
/*     */       
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUpgradeCap(ItemStack itemstack) {
/* 183 */     return BalanceConfigHandler.wyvernWeaponsMaxUpgrades;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTier(ItemStack itemstack) {
/* 188 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex) {
/* 193 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.RF_CAPACITY.index) {
/* 194 */       return BalanceConfigHandler.wyvernWeaponsMaxCapacityUpgradePoints;
/*     */     }
/* 196 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ATTACK_AOE.index) {
/* 197 */       return 3;
/*     */     }
/* 199 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ARROW_DAMAGE.index) {
/* 200 */       return 8;
/*     */     }
/* 202 */     return BalanceConfigHandler.wyvernWeaponsMaxUpgradePoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex, ItemStack stack) {
/* 207 */     return getMaxUpgradePoints(upgradeIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseUpgradePoints(int upgradeIndex) {
/* 212 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ATTACK_AOE.index) {
/* 213 */       return 1;
/*     */     }
/* 215 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ARROW_DAMAGE.index) {
/* 216 */       return 0;
/*     */     }
/* 218 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUpgradeStats(ItemStack stack) {
/* 223 */     List<String> strings = new ArrayList<>();
/*     */     
/* 225 */     int attackaoe = 0;
/* 226 */     for (ItemConfigField field : getFields(stack, 0)) {
/* 227 */       if (field.name.equals("WeaponAttackAOE")) attackaoe = 1 + ((Integer)field.max).intValue() * 2; 
/*     */     } 
/* 229 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.RFCapacity.txt") + ": " + InfoHelper.HITC() + Utills.formatNumber(getMaxCharge(stack)));
/* 230 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("info.de.attackDamage.txt") + ": " + InfoHelper.HITC() + ToolHandler.getBaseAttackDamage(stack));
/* 231 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.max.txt") + " " + StatCollector.func_74838_a("gui.de.AttackAOE.txt") + ": " + InfoHelper.HITC() + attackaoe + "x" + attackaoe);
/*     */ 
/*     */     
/* 234 */     return strings;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getDisplayData(ItemStack stack) {
/* 239 */     List<String> list = new ArrayList<>();
/* 240 */     for (ItemConfigField field : getFields(stack, 0))
/* 241 */       list.add(field.getTooltipInfo()); 
/* 242 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack stack, World world, EntityPlayer player) {
/* 247 */     if (!world.field_72995_K && !BrandonsCore.proxy.isDedicatedServer()) {
/* 248 */       ToolBase.handleModeChange(stack, player, InfoHelper.isShiftKeyDown(), InfoHelper.isCtrlKeyDown());
/* 249 */     } else if (world.field_72995_K && BrandonsCore.proxy.getMCServer() == null) {
/* 250 */       ToolBase.handleModeChange(stack, player, InfoHelper.isShiftKeyDown(), InfoHelper.isCtrlKeyDown());
/* 251 */       DraconicEvolution.network.sendToServer((IMessage)new ToolModePacket(InfoHelper.isShiftKeyDown(), InfoHelper.isCtrlKeyDown()));
/*     */     } 
/* 253 */     return super.func_77659_a(stack, world, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasProfiles() {
/* 258 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyPerAttack() {
/* 263 */     return BalanceConfigHandler.wyvernWeaponsEnergyPerAttack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\weapons\WyvernSword.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */