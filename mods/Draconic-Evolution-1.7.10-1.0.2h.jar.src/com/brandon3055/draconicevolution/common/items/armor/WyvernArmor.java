/*     */ package com.brandon3055.draconicevolution.common.items.armor;
/*     */ 
/*     */ import com.brandon3055.brandonscore.BrandonsCore;
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.client.model.ModelDraconicArmorOld;
/*     */ import com.brandon3055.draconicevolution.client.model.ModelWyvernArmor;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.entity.EntityPersistentItem;
/*     */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.items.armor.baseclasses.ItemElectricArmorBase;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.baseclasses.ToolBase;
/*     */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.IInventoryTool;
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ISpecialArmor;
/*     */ 
/*     */ public class WyvernArmor
/*     */   extends ItemElectricArmorBase implements ISpecialArmor, IConfigurableItem, IInventoryTool, IUpgradableItem {
/*     */   @SideOnly(Side.CLIENT)
/*     */   private IIcon helmIcon;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private IIcon chestIcon;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private IIcon leggsIcon;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private IIcon bootsIcon;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private ModelBiped model;
/*     */   
/*     */   public WyvernArmor(ItemArmor.ArmorMaterial material, int armorType, String name) {
/*  56 */     super(material, 0, armorType);
/*  57 */     func_77655_b(name);
/*  58 */     func_77637_a(DraconicEvolution.tabToolsWeapons);
/*  59 */     if (ModItems.isEnabled((Item)this)) GameRegistry.registerItem((Item)this, name);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean func_77616_k(ItemStack p_77616_1_) {
/*  64 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77658_a() {
/*  70 */     return String.format("item.%s%s", new Object[] { "draconicevolution:", super.func_77658_a().substring(super.func_77658_a().indexOf('.') + 1) });
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack itemStack) {
/*  75 */     return func_77658_a();
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister iconRegister) {
/*  81 */     this.helmIcon = iconRegister.func_94245_a("draconicevolution:wyvern_helmet");
/*  82 */     this.chestIcon = iconRegister.func_94245_a("draconicevolution:wyvern_chestplate");
/*  83 */     this.leggsIcon = iconRegister.func_94245_a("draconicevolution:wyvern_leggings");
/*  84 */     this.bootsIcon = iconRegister.func_94245_a("draconicevolution:wyvern_boots");
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
/*  90 */     if (stack.func_77973_b() == ModItems.wyvernHelm) return this.helmIcon; 
/*  91 */     if (stack.func_77973_b() == ModItems.wyvernChest) return this.chestIcon; 
/*  92 */     if (stack.func_77973_b() == ModItems.wyvernLeggs) return this.leggsIcon; 
/*  93 */     return this.bootsIcon;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77650_f(ItemStack stack) {
/*  99 */     if (stack.func_77973_b() == ModItems.wyvernHelm) return this.helmIcon; 
/* 100 */     if (stack.func_77973_b() == ModItems.wyvernChest) return this.chestIcon; 
/* 101 */     if (stack.func_77973_b() == ModItems.wyvernLeggs) return this.leggsIcon; 
/* 102 */     return this.bootsIcon;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
/* 108 */     if (!ConfigHandler.useOldArmorModel)
/* 109 */       return "draconicevolution:textures/models/armor/armorWyvern.png"; 
/* 110 */     if (stack.func_77973_b() == ModItems.wyvernHelm || stack.func_77973_b() == ModItems.wyvernChest || stack.func_77973_b() == ModItems.wyvernBoots) {
/* 111 */       return "draconicevolution:textures/models/armor/wyvern_layer_1.png";
/*     */     }
/* 113 */     return "draconicevolution:textures/models/armor/wyvern_layer_2.png";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumRarity func_77613_e(ItemStack p_77613_1_) {
/* 119 */     return EnumRarity.uncommon;
/*     */   }
/*     */   
/*     */   protected float getProtectionShare() {
/* 123 */     switch (this.field_77881_a) {
/*     */       case 0:
/*     */       case 3:
/* 126 */         return 0.15F;
/*     */       case 1:
/* 128 */         return 0.4F;
/*     */       case 2:
/* 130 */         return 0.3F;
/*     */     } 
/* 132 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
/* 138 */     if (source.func_76363_c() || source.func_151517_h() || source.func_82725_o())
/* 139 */       return new ISpecialArmor.ArmorProperties(0, this.field_77879_b / 100.0D, 15); 
/* 140 */     return new ISpecialArmor.ArmorProperties(0, this.field_77879_b / 25.0D, 1000);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
/* 145 */     return (int)(getProtectionShare() * 20.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
/* 161 */     InfoHelper.addEnergyAndLore(stack, list);
/* 162 */     ToolBase.holdCTRLForUpgrades(list, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCustomEntity(ItemStack stack) {
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity createEntity(World world, Entity location, ItemStack itemstack) {
/* 172 */     return (Entity)new EntityPersistentItem(world, location, itemstack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/* 178 */     int points = IUpgradableItem.EnumUpgrade.RF_CAPACITY.getUpgradePoints(stack);
/* 179 */     return (BalanceConfigHandler.wyvernArmorBaseStorage + points * BalanceConfigHandler.wyvernArmorStoragePerUpgrade);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/* 184 */     return BalanceConfigHandler.wyvernArmorMaxTransfer;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemConfigField> getFields(ItemStack stack, int slot) {
/* 189 */     List<ItemConfigField> list = new ArrayList<>();
/* 190 */     if (this.field_77881_a == 2) {
/* 191 */       list.add((new ItemConfigField(4, slot, "ArmorSpeedMult")).setMinMaxAndIncromente(Float.valueOf(0.0F), Float.valueOf(5.0F), Float.valueOf(0.1F)).readFromItem(stack, Float.valueOf(0.0F)).setModifier("PLUSPERCENT"));
/* 192 */       list.add((new ItemConfigField(6, slot, "ArmorSprintOnly")).readFromItem(stack, Boolean.valueOf(false)));
/* 193 */     } else if (this.field_77881_a == 3) {
/* 194 */       list.add((new ItemConfigField(4, slot, "ArmorJumpMult")).setMinMaxAndIncromente(Float.valueOf(0.0F), Float.valueOf(5.0F), Float.valueOf(0.1F)).readFromItem(stack, Float.valueOf(0.0F)).setModifier("PLUSPERCENT"));
/* 195 */       list.add((new ItemConfigField(6, slot, "ArmorSprintOnly")).readFromItem(stack, Boolean.valueOf(false)));
/*     */     } 
/* 197 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInventoryName() {
/* 202 */     return StatCollector.func_74838_a("info.de.toolInventoryEnch.txt");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInventorySlots() {
/* 207 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnchantValid(Enchantment enchant) {
/* 212 */     return (enchant.field_77351_y == EnumEnchantmentType.armor || (this.field_77881_a == 0 && enchant.field_77351_y == EnumEnchantmentType.armor_head) || (this.field_77881_a == 1 && enchant.field_77351_y == EnumEnchantmentType.armor_torso) || (this.field_77881_a == 2 && enchant.field_77351_y == EnumEnchantmentType.armor_legs) || (this.field_77881_a == 3 && enchant.field_77351_y == EnumEnchantmentType.armor_feet));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
/* 221 */     if (ConfigHandler.useOldArmorModel) return super.getArmorModel(entityLiving, itemStack, armorSlot);
/*     */     
/* 223 */     if (!ConfigHandler.useOriginal3DArmorModel) {
/* 224 */       if (this.model == null) {
/* 225 */         if (this.field_77881_a == 0) { this.model = (ModelBiped)new ModelWyvernArmor(1.0F, true, false, false, false); }
/* 226 */         else if (this.field_77881_a == 1) { this.model = (ModelBiped)new ModelWyvernArmor(1.0F, false, true, false, false); }
/* 227 */         else if (this.field_77881_a == 2) { this.model = (ModelBiped)new ModelWyvernArmor(1.0F, false, false, true, false); }
/* 228 */         else { this.model = (ModelBiped)new ModelWyvernArmor(1.0F, false, false, false, true); }
/* 229 */          this.model.field_78116_c.field_78806_j = (this.field_77881_a == 0);
/* 230 */         this.model.field_78114_d.field_78806_j = (this.field_77881_a == 0);
/* 231 */         this.model.field_78115_e.field_78806_j = (this.field_77881_a == 1 || this.field_77881_a == 2);
/* 232 */         this.model.field_78113_g.field_78806_j = (this.field_77881_a == 1);
/* 233 */         this.model.field_78112_f.field_78806_j = (this.field_77881_a == 1);
/* 234 */         this.model.field_78124_i.field_78806_j = (this.field_77881_a == 2 || this.field_77881_a == 3);
/* 235 */         this.model.field_78123_h.field_78806_j = (this.field_77881_a == 2 || this.field_77881_a == 3);
/*     */       }
/*     */     
/* 238 */     } else if (this.model == null) {
/* 239 */       if (this.field_77881_a == 0) { this.model = (ModelBiped)new ModelDraconicArmorOld(1.0F, true, false, false, false, false); }
/* 240 */       else if (this.field_77881_a == 1)
/* 241 */       { this.model = (ModelBiped)new ModelDraconicArmorOld(1.0F, false, true, false, false, false); }
/* 242 */       else if (this.field_77881_a == 2)
/* 243 */       { this.model = (ModelBiped)new ModelDraconicArmorOld(1.0F, false, false, true, false, false); }
/* 244 */       else { this.model = (ModelBiped)new ModelDraconicArmorOld(1.0F, false, false, false, true, false); }
/*     */       
/* 246 */       this.model.field_78116_c.field_78806_j = (this.field_77881_a == 0);
/* 247 */       this.model.field_78114_d.field_78806_j = (this.field_77881_a == 0);
/* 248 */       this.model.field_78115_e.field_78806_j = (this.field_77881_a == 1 || this.field_77881_a == 2);
/* 249 */       this.model.field_78113_g.field_78806_j = (this.field_77881_a == 1);
/* 250 */       this.model.field_78112_f.field_78806_j = (this.field_77881_a == 1);
/* 251 */       this.model.field_78124_i.field_78806_j = (this.field_77881_a == 2 || this.field_77881_a == 3);
/* 252 */       this.model.field_78123_h.field_78806_j = (this.field_77881_a == 2 || this.field_77881_a == 3);
/*     */     } 
/*     */ 
/*     */     
/* 256 */     if (entityLiving == null) return this.model;
/*     */     
/* 258 */     this.model.field_78117_n = entityLiving.func_70093_af();
/* 259 */     this.model.field_78093_q = entityLiving.func_70115_ae();
/* 260 */     this.model.field_78091_s = entityLiving.func_70631_g_();
/* 261 */     this.model.field_78118_o = false;
/* 262 */     this.model.field_78120_m = (entityLiving.func_70694_bm() != null) ? 1 : 0;
/*     */     
/* 264 */     if (entityLiving instanceof EntityPlayer && (
/* 265 */       (EntityPlayer)entityLiving).func_71057_bx() > 0) {
/* 266 */       EnumAction enumaction = ((EntityPlayer)entityLiving).func_71011_bu().func_77975_n();
/* 267 */       if (enumaction == EnumAction.block) {
/* 268 */         this.model.field_78120_m = 3;
/* 269 */       } else if (enumaction == EnumAction.bow) {
/* 270 */         this.model.field_78118_o = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 276 */     return this.model;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IUpgradableItem.EnumUpgrade> getUpgrades(ItemStack itemstack) {
/* 281 */     return new ArrayList<IUpgradableItem.EnumUpgrade>()
/*     */       {
/*     */       
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUpgradeCap(ItemStack itemstack) {
/* 292 */     return BalanceConfigHandler.wyvernArmorMaxUpgrades;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTier(ItemStack itemstack) {
/* 297 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUpgradeStats(ItemStack stack) {
/* 302 */     List<String> strings = new ArrayList<>();
/*     */     
/* 304 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.RFCapacity.txt") + ": " + InfoHelper.HITC() + Utills.formatNumber(getMaxCharge(stack)));
/* 305 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.ShieldCapacity.txt") + ": " + InfoHelper.HITC() + (int)getProtectionPoints(stack));
/* 306 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.ShieldRecovery.txt") + ": " + InfoHelper.HITC() + Utills.round(getRecoveryPoints(stack) * 0.2D, 10.0D) + " EPS");
/*     */     
/* 308 */     return strings;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex) {
/* 313 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.RF_CAPACITY.index) {
/* 314 */       return BalanceConfigHandler.wyvernArmorMaxCapacityUpgradePoints;
/*     */     }
/* 316 */     return BalanceConfigHandler.wyvernArmorMaxUpgradePoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex, ItemStack stack) {
/* 321 */     return getMaxUpgradePoints(upgradeIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseUpgradePoints(int upgradeIndex) {
/* 326 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.SHIELD_CAPACITY.index) {
/* 327 */       return (int)(getProtectionShare() * 10.0F) + ((this.field_77881_a == 2) ? 1 : 0);
/*     */     }
/* 329 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.SHIELD_RECOVERY.index) {
/* 330 */       return 5;
/*     */     }
/* 332 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getProtectionPoints(ItemStack stack) {
/* 338 */     return IUpgradableItem.EnumUpgrade.SHIELD_CAPACITY.getUpgradePoints(stack) * 20.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRecoveryPoints(ItemStack stack) {
/* 343 */     return IUpgradableItem.EnumUpgrade.SHIELD_RECOVERY.getUpgradePoints(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSpeedModifier(ItemStack stack, EntityPlayer player) {
/* 348 */     if (IConfigurableItem.ProfileHelper.getBoolean(stack, "ArmorSprintOnly", false))
/* 349 */       return player.func_70051_ag() ? IConfigurableItem.ProfileHelper.getFloat(stack, "ArmorSpeedMult", 0.0F) : (IConfigurableItem.ProfileHelper.getFloat(stack, "ArmorSpeedMult", 0.0F) / 5.0F); 
/* 350 */     return IConfigurableItem.ProfileHelper.getFloat(stack, "ArmorSpeedMult", 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getJumpModifier(ItemStack stack, EntityPlayer player) {
/* 355 */     if (IConfigurableItem.ProfileHelper.getBoolean(stack, "ArmorSprintOnly", false))
/* 356 */       return (player.func_70051_ag() || BrandonsCore.proxy.isCtrlDown()) ? IConfigurableItem.ProfileHelper.getFloat(stack, "ArmorJumpMult", 0.0F) : (IConfigurableItem.ProfileHelper.getFloat(stack, "ArmorJumpMult", 0.0F) / 5.0F); 
/* 357 */     return IConfigurableItem.ProfileHelper.getFloat(stack, "ArmorJumpMult", 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasHillStep(ItemStack stack, EntityPlayer player) {
/* 362 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFireResistance(ItemStack stack) {
/* 367 */     return getProtectionShare();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] hasFlight(ItemStack stack) {
/* 372 */     return new boolean[] { false, false, false };
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFlightSpeedModifier(ItemStack stack, EntityPlayer player) {
/* 377 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFlightVModifier(ItemStack stack, EntityPlayer player) {
/* 382 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyPerProtectionPoint() {
/* 387 */     return BalanceConfigHandler.wyvernArmorEnergyPerProtectionPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasProfiles() {
/* 394 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\armor\WyvernArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */