/*     */ package com.brandon3055.draconicevolution.common.items.weapons;
/*     */ 
/*     */ import com.brandon3055.brandonscore.BrandonsCore;
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.entity.EntityPersistentItem;
/*     */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.baseclasses.ToolBase;
/*     */ import com.brandon3055.draconicevolution.common.items.weapons.baseclasses.ItemElectricBowBase;
/*     */ import com.brandon3055.draconicevolution.common.utills.IHudDisplayItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.IInventoryTool;
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WyvernBow
/*     */   extends ItemElectricBowBase
/*     */   implements IInventoryTool, IUpgradableItem, IHudDisplayItem
/*     */ {
/*  39 */   public static final String[] bowPullIconNameArray = new String[] { "pulling_0", "pulling_1", "pulling_2" };
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private IIcon[] iconArray;
/*     */   
/*     */   public WyvernBow() {
/*  45 */     this.field_77777_bU = 1;
/*  46 */     func_77656_e(-1);
/*  47 */     func_77637_a(DraconicEvolution.tabToolsWeapons);
/*  48 */     func_77655_b("wyvernBow");
/*  49 */     if (ModItems.isEnabled((Item)this)) GameRegistry.registerItem((Item)this, "wyvernBow");
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77616_k(ItemStack p_77616_1_) {
/*  55 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77658_a() {
/*  61 */     return String.format("item.%s%s", new Object[] { "draconicevolution:", super.func_77658_a().substring(super.func_77658_a().indexOf('.') + 1) });
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack itemStack) {
/*  66 */     return func_77658_a();
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister iconRegister) {
/*  72 */     this.field_77791_bV = iconRegister.func_94245_a("draconicevolution:wyvern_bow_standby");
/*  73 */     this.iconArray = new IIcon[bowPullIconNameArray.length];
/*     */     
/*  75 */     for (int i = 0; i < this.iconArray.length; i++) {
/*  76 */       this.iconArray[i] = iconRegister.func_94245_a("draconicevolution:wyvern_bow_" + bowPullIconNameArray[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
/*  83 */     float j = stack.func_77988_m() - useRemaining;
/*  84 */     if (usingItem == null) {
/*  85 */       return this.field_77791_bV;
/*     */     }
/*     */     
/*  88 */     BowHandler.BowProperties properties = new BowHandler.BowProperties(stack, player);
/*     */     
/*  90 */     if (j > properties.getDrawTicks()) j = properties.getDrawTicks();
/*     */     
/*  92 */     j /= properties.getDrawTicks();
/*  93 */     int j2 = (int)(j * 2.0F);
/*     */     
/*  95 */     if (j2 < 0) { j2 = 0; }
/*  96 */     else if (j2 > 2) { j2 = 2; }
/*     */ 
/*     */     
/*  99 */     return func_94599_c(j2);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_94599_c(int par1) {
/* 105 */     return this.iconArray[par1];
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
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean extraInformation) {
/* 122 */     boolean show = InfoHelper.holdShiftForDetails(list);
/* 123 */     if (show) {
/* 124 */       int preset = ItemNBTHelper.getInteger(stack, "ConfigProfile", 0);
/* 125 */       list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.func_74838_a("info.de.capacitorMode.txt") + ": " + ItemNBTHelper.getString(stack, "ProfileName" + preset, "Profile " + preset));
/* 126 */       List<ItemConfigField> l = getFields(stack, 0);
/* 127 */       for (ItemConfigField f : l) list.add(f.getTooltipInfo()); 
/*     */     } 
/* 129 */     ToolBase.holdCTRLForUpgrades(list, stack);
/* 130 */     InfoHelper.addEnergyInfo(stack, list);
/* 131 */     if (show && !ConfigHandler.disableLore) InfoHelper.addLore(stack, list, true);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean func_77614_k() {
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack stack, World world, EntityPlayer player) {
/* 143 */     return BowHandler.onBowRightClick((Item)this, stack, world, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
/* 148 */     BowHandler.onBowUsingTick(stack, player, count);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77615_a(ItemStack stack, World world, EntityPlayer player, int count) {
/* 153 */     BowHandler.onPlayerStoppedUsingBow(stack, world, player, count);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInventoryName() {
/* 159 */     return StatCollector.func_74838_a("info.de.toolInventoryEnch.txt");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInventorySlots() {
/* 164 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnchantValid(Enchantment enchant) {
/* 169 */     return (enchant.field_77351_y == EnumEnchantmentType.bow || enchant.field_77352_x == DraconicEvolution.reaperEnchant.field_77352_x);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemConfigField> getFields(ItemStack stack, int slot) {
/* 174 */     List<ItemConfigField> list = new ArrayList<>();
/*     */     
/* 176 */     list.add((new ItemConfigField(4, slot, "BowArrowDamage")).setMinMaxAndIncromente(Float.valueOf(getBaseUpgradePoints(IUpgradableItem.EnumUpgrade.ARROW_DAMAGE.index)), Float.valueOf(IUpgradableItem.EnumUpgrade.ARROW_DAMAGE.getUpgradePoints(stack)), Float.valueOf(0.1F)).readFromItem(stack, Float.valueOf(IUpgradableItem.EnumUpgrade.ARROW_DAMAGE.getUpgradePoints(stack))));
/* 177 */     list.add((new ItemConfigField(4, slot, "BowArrowSpeedModifier")).setMinMaxAndIncromente(Float.valueOf(0.0F), Float.valueOf(IUpgradableItem.EnumUpgrade.ARROW_SPEED.getUpgradePoints(stack)), Float.valueOf(0.01F)).readFromItem(stack, Float.valueOf(0.0F)).setModifier("PLUSPERCENT"));
/* 178 */     list.add((new ItemConfigField(6, slot, "BowAutoFire")).readFromItem(stack, Boolean.valueOf(false)));
/* 179 */     list.add((new ItemConfigField(4, slot, "BowExplosionPower")).setMinMaxAndIncromente(Float.valueOf(0.0F), Float.valueOf(4.0F), Float.valueOf(0.1F)).readFromItem(stack, Float.valueOf(0.0F)));
/*     */ 
/*     */     
/* 182 */     list.add((new ItemConfigField(4, slot, "BowZoomModifier")).setMinMaxAndIncromente(Float.valueOf(0.0F), Float.valueOf(3.0F), Float.valueOf(0.01F)).readFromItem(stack, Float.valueOf(0.0F)).setModifier("PLUSPERCENT"));
/*     */     
/* 184 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasProfiles() {
/* 189 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IUpgradableItem.EnumUpgrade> getUpgrades(ItemStack itemstack) {
/* 194 */     return new ArrayList<IUpgradableItem.EnumUpgrade>()
/*     */       {
/*     */       
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUpgradeCap(ItemStack itemstack) {
/* 204 */     return BalanceConfigHandler.wyvernBowMaxUpgrades;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTier(ItemStack itemstack) {
/* 209 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex) {
/* 214 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.RF_CAPACITY.index) {
/* 215 */       return BalanceConfigHandler.wyvernBowMaxCapacityUpgradePoints;
/*     */     }
/* 217 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DRAW_SPEED.index) {
/* 218 */       return 5;
/*     */     }
/* 220 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ARROW_SPEED.index) {
/* 221 */       return 10;
/*     */     }
/* 223 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ARROW_DAMAGE.index) {
/* 224 */       return 10;
/*     */     }
/* 226 */     return BalanceConfigHandler.wyvernBowMaxUpgradePoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex, ItemStack stack) {
/* 231 */     return getMaxUpgradePoints(upgradeIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseUpgradePoints(int upgradeIndex) {
/* 236 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DRAW_SPEED.index) {
/* 237 */       return 3;
/*     */     }
/* 239 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ARROW_SPEED.index) {
/* 240 */       return 1;
/*     */     }
/* 242 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.ARROW_DAMAGE.index) {
/* 243 */       return 2;
/*     */     }
/* 245 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUpgradeStats(ItemStack stack) {
/* 250 */     BowHandler.BowProperties properties = new BowHandler.BowProperties(stack, null);
/* 251 */     List<String> list = new ArrayList<>();
/* 252 */     list.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.RFCapacity.txt") + ": " + InfoHelper.HITC() + Utills.formatNumber(getMaxCharge(stack)));
/* 253 */     list.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.max.txt") + " " + StatCollector.func_74838_a("gui.de.ArrowSpeed.txt") + ": " + InfoHelper.HITC() + "+" + (IUpgradableItem.EnumUpgrade.ARROW_SPEED.getUpgradePoints(stack) * 100) + "%");
/* 254 */     list.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.ArrowDamage.txt") + ": " + InfoHelper.HITC() + properties.arrowDamage + "");
/* 255 */     list.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.DrawSpeed.txt") + ": " + InfoHelper.HITC() + (properties.getDrawTicks() / 20.0D) + "s");
/*     */     
/* 257 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/* 262 */     int points = IUpgradableItem.EnumUpgrade.RF_CAPACITY.getUpgradePoints(stack);
/* 263 */     return (BalanceConfigHandler.wyvernWeaponsBaseStorage + points * BalanceConfigHandler.wyvernWeaponsStoragePerUpgrade);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/* 268 */     return BalanceConfigHandler.wyvernWeaponsMaxTransfer;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getDisplayData(ItemStack stack) {
/* 273 */     List<String> list = new ArrayList<>();
/*     */     
/* 275 */     if (BrandonsCore.proxy.getClientPlayer() != null && BrandonsCore.proxy.getClientPlayer().func_71011_bu() != null) {
/* 276 */       EntityPlayer player = BrandonsCore.proxy.getClientPlayer();
/* 277 */       BowHandler.BowProperties properties = new BowHandler.BowProperties(stack, player);
/* 278 */       int power = (int)Math.min(player.func_71057_bx() / properties.getDrawTicks() * 100.0F, 100.0F);
/* 279 */       list.add(InfoHelper.ITC() + StatCollector.func_74838_a("info.de.power.txt") + ": " + InfoHelper.HITC() + power + "%");
/*     */     } else {
/* 281 */       int preset = ItemNBTHelper.getInteger(stack, "ConfigProfile", 0);
/* 282 */       list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.func_74838_a("info.de.capacitorMode.txt") + ": " + ItemNBTHelper.getString(stack, "ProfileName" + preset, "Profile " + preset));
/*     */       
/* 284 */       for (ItemConfigField field : getFields(stack, 0)) {
/* 285 */         if ((field.datatype == 4 && ((Float)field.value).floatValue() > 0.0F) || (field.datatype == 6 && ((Boolean)field.value).booleanValue())) {
/* 286 */           list.add(field.getTooltipInfo());
/*     */         }
/*     */       } 
/* 289 */       list.add(InfoHelper.ITC() + StatCollector.func_74838_a("info.de.charge.txt") + ": " + InfoHelper.HITC() + Utills.formatNumber(getCharge(stack)) + " / " + Utills.formatNumber(getMaxCharge(stack)));
/*     */       
/* 291 */       if (BrandonsCore.proxy.getClientPlayer() != null) {
/* 292 */         BowHandler.BowProperties properties = new BowHandler.BowProperties(stack, BrandonsCore.proxy.getClientPlayer());
/* 293 */         list.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.rfPerShot.txt") + ": " + InfoHelper.HITC() + Utills.addCommas(properties.calculateEnergyCost()));
/* 294 */         if (!properties.canFire() && properties.cantFireMessage != null) {
/* 295 */           list.add(EnumChatFormatting.DARK_RED + StatCollector.func_74838_a(properties.cantFireMessage));
/*     */         }
/*     */       } 
/*     */     } 
/* 299 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyPerAttack() {
/* 304 */     return BalanceConfigHandler.wyvernBowEnergyPerShot;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\weapons\WyvernBow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */