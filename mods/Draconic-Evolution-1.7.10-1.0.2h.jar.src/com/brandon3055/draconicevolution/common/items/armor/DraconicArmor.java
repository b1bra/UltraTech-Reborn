/*     */ package com.brandon3055.draconicevolution.common.items.armor;
/*     */ 
/*     */ import com.brandon3055.brandonscore.BrandonsCore;
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.client.model.ModelDraconicArmor;
/*     */ import com.brandon3055.draconicevolution.client.model.ModelDraconicArmorOld;
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
/*     */ import com.brandon3055.draconicevolution.integration.ModHelper;
/*     */ import cpw.mods.fml.common.Loader;
/*     */ import cpw.mods.fml.common.Optional.Interface;
/*     */ import cpw.mods.fml.common.Optional.InterfaceList;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import cpw.mods.fml.relauncher.ReflectionHelper;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ISpecialArmor;
/*     */ 
/*     */ @InterfaceList({@Interface(iface = "thaumcraft.api.IGoggles", modid = "Thaumcraft"), @Interface(iface = "thaumcraft.api.nodes.IRevealer", modid = "Thaumcraft")})
/*     */ public class DraconicArmor
/*     */   extends ItemElectricArmorBase
/*     */   implements ISpecialArmor, IConfigurableItem, IInventoryTool, IUpgradableItem {
/*     */   @SideOnly(Side.CLIENT)
/*     */   private IIcon helmIcon;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private IIcon chestIcon;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private IIcon leggsIcon;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private IIcon bootsIcon;
/*     */   @SideOnly(Side.CLIENT)
/*     */   public ModelBiped model;
/*     */   
/*     */   public DraconicArmor(ItemArmor.ArmorMaterial material, int armorType, String name) {
/*  66 */     super(material, 0, armorType);
/*  67 */     func_77655_b(name);
/*  68 */     func_77637_a(DraconicEvolution.tabToolsWeapons);
/*  69 */     if (ModItems.isEnabled((Item)this)) GameRegistry.registerItem((Item)this, name);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean func_77616_k(ItemStack p_77616_1_) {
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77658_a() {
/*  80 */     return String.format("item.%s%s", new Object[] { "draconicevolution:", super.func_77658_a().substring(super.func_77658_a().indexOf('.') + 1) });
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack itemStack) {
/*  85 */     return func_77658_a();
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister iconRegister) {
/*  91 */     this.helmIcon = iconRegister.func_94245_a("draconicevolution:draconic_helmet");
/*  92 */     this.chestIcon = iconRegister.func_94245_a("draconicevolution:draconic_chestplate");
/*  93 */     this.leggsIcon = iconRegister.func_94245_a("draconicevolution:draconic_leggings");
/*  94 */     this.bootsIcon = iconRegister.func_94245_a("draconicevolution:draconic_boots");
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
/* 100 */     if (stack.func_77973_b() == ModItems.draconicHelm) return this.helmIcon; 
/* 101 */     if (stack.func_77973_b() == ModItems.draconicChest) return this.chestIcon; 
/* 102 */     if (stack.func_77973_b() == ModItems.draconicLeggs) return this.leggsIcon; 
/* 103 */     return this.bootsIcon;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77650_f(ItemStack stack) {
/* 109 */     if (stack.func_77973_b() == ModItems.draconicHelm) return this.helmIcon; 
/* 110 */     if (stack.func_77973_b() == ModItems.draconicChest) return this.chestIcon; 
/* 111 */     if (stack.func_77973_b() == ModItems.draconicLeggs) return this.leggsIcon; 
/* 112 */     return this.bootsIcon;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
/* 118 */     if (!ConfigHandler.useOldArmorModel)
/* 119 */       return "draconicevolution:textures/models/armor/armorDraconic.png"; 
/* 120 */     if (stack.func_77973_b() == ModItems.draconicHelm || stack.func_77973_b() == ModItems.draconicChest || stack.func_77973_b() == ModItems.draconicBoots) {
/* 121 */       return "draconicevolution:textures/models/armor/draconic_layer_1.png";
/*     */     }
/* 123 */     return "draconicevolution:textures/models/armor/draconic_layer_2.png";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumRarity func_77613_e(ItemStack p_77613_1_) {
/* 129 */     return EnumRarity.epic;
/*     */   }
/*     */   
/*     */   protected float getProtectionShare() {
/* 133 */     switch (this.field_77881_a) {
/*     */       case 0:
/*     */       case 3:
/* 136 */         return 0.15F;
/*     */       case 1:
/* 138 */         return 0.4F;
/*     */       case 2:
/* 140 */         return 0.3F;
/*     */     } 
/* 142 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
/* 148 */     if (source.func_76363_c() || source.func_151517_h() || source.func_82725_o())
/* 149 */       return new ISpecialArmor.ArmorProperties(0, this.field_77879_b / 100.0D, 15); 
/* 150 */     return new ISpecialArmor.ArmorProperties(0, this.field_77879_b / 24.5D, 1000);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
/* 155 */     return (int)(getProtectionShare() * 20.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
/* 165 */     if (stack == null)
/* 166 */       return;  if (stack.func_77973_b() == ModItems.draconicHelm) {
/* 167 */       if (world.field_72995_K)
/* 168 */         return;  if (getCharge(stack) >= BalanceConfigHandler.draconicArmorEnergyToRemoveEffects && clearNegativeEffects((Entity)player)) {
/* 169 */         discharge(stack, BalanceConfigHandler.draconicArmorEnergyToRemoveEffects, false);
/*     */       }
/* 171 */       if (player.field_70170_p.func_72957_l((int)Math.floor(player.field_70165_t), (int)player.field_70163_u + 1, (int)Math.floor(player.field_70161_v)) < 5 && IConfigurableItem.ProfileHelper.getBoolean(stack, "ArmorNVActive", false))
/* 172 */       { player.func_70690_d(new PotionEffect(Potion.field_76439_r.field_76415_H, 419, 0, true)); }
/* 173 */       else if (IConfigurableItem.ProfileHelper.getBoolean(stack, "ArmorNVActive", false) && IConfigurableItem.ProfileHelper.getBoolean(stack, "ArmorNVLock", true))
/* 174 */       { player.func_70690_d(new PotionEffect(Potion.field_76439_r.field_76415_H, 419, 0, true)); }
/* 175 */       else if (player.func_82165_m(Potion.field_76439_r.field_76415_H)) { player.func_82170_o(Potion.field_76439_r.field_76415_H); }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
/* 184 */     InfoHelper.addEnergyAndLore(stack, list);
/* 185 */     ToolBase.holdCTRLForUpgrades(list, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean clearNegativeEffects(Entity par3Entity) {
/* 190 */     boolean flag = false;
/* 191 */     if (par3Entity.field_70173_aa % 20 == 0 && 
/* 192 */       par3Entity instanceof EntityPlayer) {
/* 193 */       EntityPlayer player = (EntityPlayer)par3Entity;
/*     */       
/* 195 */       Collection<PotionEffect> potions = player.func_70651_bq();
/*     */       
/* 197 */       if (player.func_70027_ad()) {
/* 198 */         player.func_70066_B();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 205 */       for (PotionEffect potion : potions) {
/* 206 */         int id = potion.func_76456_a();
/* 207 */         if (((Boolean)ReflectionHelper.getPrivateValue(Potion.class, Potion.field_76425_a[id], new String[] { "isBadEffect", "field_76418_K", "J" })).booleanValue()) {
/* 208 */           if ((potion.func_76456_a() != Potion.field_76419_f.field_76415_H || !ModHelper.isHoldingCleaver(player)) && (
/* 209 */             player.func_70694_bm() == null || (player.func_70694_bm().func_77973_b() != ModItems.wyvernBow && player.func_70694_bm().func_77973_b() != ModItems.draconicBow) || id != 2)) {
/* 210 */             player.func_82170_o(id);
/* 211 */             flag = true;
/*     */           } 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 218 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/* 224 */     int points = IUpgradableItem.EnumUpgrade.RF_CAPACITY.getUpgradePoints(stack);
/* 225 */     return (BalanceConfigHandler.draconicArmorBaseStorage + points * BalanceConfigHandler.draconicArmorStoragePerUpgrade);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/* 230 */     return BalanceConfigHandler.draconicArmorMaxTransfer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity createEntity(World world, Entity location, ItemStack itemstack) {
/* 236 */     return (Entity)new EntityPersistentItem(world, location, itemstack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCustomEntity(ItemStack stack) {
/* 241 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemConfigField> getFields(ItemStack stack, int slot) {
/* 246 */     List<ItemConfigField> list = new ArrayList<>();
/* 247 */     if (this.field_77881_a == 0) {
/* 248 */       list.add((new ItemConfigField(6, slot, "ArmorNVActive")).readFromItem(stack, Boolean.valueOf(false)));
/* 249 */       list.add((new ItemConfigField(6, slot, "ArmorNVLock")).readFromItem(stack, Boolean.valueOf(true)));
/* 250 */       if (Loader.isModLoaded("Thaumcraft"))
/* 251 */         list.add((new ItemConfigField(6, slot, "GogglesOfRevealing")).readFromItem(stack, Boolean.valueOf(true))); 
/* 252 */     } else if (this.field_77881_a == 1) {
/* 253 */       list.add((new ItemConfigField(4, slot, "VerticalAcceleration")).setMinMaxAndIncromente(Float.valueOf(0.0F), Float.valueOf(8.0F), Float.valueOf(0.1F)).readFromItem(stack, Float.valueOf(0.0F)).setModifier("PLUSPERCENT"));
/* 254 */       list.add((new ItemConfigField(4, slot, "ArmorFlightSpeedMult")).setMinMaxAndIncromente(Float.valueOf(0.0F), Float.valueOf(6.0F), Float.valueOf(0.1F)).readFromItem(stack, Float.valueOf(0.0F)).setModifier("PLUSPERCENT"));
/* 255 */       list.add((new ItemConfigField(6, slot, "EffectiveOnSprint")).readFromItem(stack, Boolean.valueOf(false)));
/* 256 */       list.add((new ItemConfigField(6, slot, "ArmorFlightLock")).readFromItem(stack, Boolean.valueOf(false)));
/* 257 */       list.add((new ItemConfigField(6, slot, "ArmorInertiaCancellation")).readFromItem(stack, Boolean.valueOf(false)));
/* 258 */     } else if (this.field_77881_a == 2) {
/* 259 */       list.add((new ItemConfigField(4, slot, "ArmorSpeedMult")).setMinMaxAndIncromente(Float.valueOf(0.0F), Float.valueOf(8.0F), Float.valueOf(0.1F)).readFromItem(stack, Float.valueOf(0.0F)).setModifier("PLUSPERCENT"));
/* 260 */       list.add((new ItemConfigField(6, slot, "ArmorSprintOnly")).readFromItem(stack, Boolean.valueOf(false)));
/* 261 */     } else if (this.field_77881_a == 3) {
/* 262 */       list.add((new ItemConfigField(4, slot, "ArmorJumpMult")).setMinMaxAndIncromente(Float.valueOf(0.0F), Float.valueOf(15.0F), Float.valueOf(0.1F)).readFromItem(stack, Float.valueOf(0.0F)).setModifier("PLUSPERCENT"));
/* 263 */       list.add((new ItemConfigField(6, slot, "ArmorSprintOnly")).readFromItem(stack, Boolean.valueOf(false)));
/* 264 */       list.add((new ItemConfigField(6, slot, "ArmorHillStep")).readFromItem(stack, Boolean.valueOf(true)));
/*     */     } 
/* 266 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInventoryName() {
/* 271 */     return StatCollector.func_74838_a("info.de.toolInventoryEnch.txt");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInventorySlots() {
/* 276 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnchantValid(Enchantment enchant) {
/* 281 */     return (enchant.field_77351_y == EnumEnchantmentType.armor || (this.field_77881_a == 0 && enchant.field_77351_y == EnumEnchantmentType.armor_head) || (this.field_77881_a == 1 && enchant.field_77351_y == EnumEnchantmentType.armor_torso) || (this.field_77881_a == 2 && enchant.field_77351_y == EnumEnchantmentType.armor_legs) || (this.field_77881_a == 3 && enchant.field_77351_y == EnumEnchantmentType.armor_feet));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
/* 302 */     if (ConfigHandler.useOldArmorModel) return super.getArmorModel(entityLiving, itemStack, armorSlot);
/*     */     
/* 304 */     if (!ConfigHandler.useOriginal3DArmorModel) {
/* 305 */       if (this.model == null) {
/* 306 */         if (this.field_77881_a == 0) { this.model = (ModelBiped)new ModelDraconicArmor(1.1F, true, false, false, false); }
/* 307 */         else if (this.field_77881_a == 1) { this.model = (ModelBiped)new ModelDraconicArmor(1.1F, false, true, false, false); }
/* 308 */         else if (this.field_77881_a == 2) { this.model = (ModelBiped)new ModelDraconicArmor(1.1F, false, false, true, false); }
/* 309 */         else { this.model = (ModelBiped)new ModelDraconicArmor(1.1F, false, false, false, true); }
/* 310 */          this.model.field_78116_c.field_78806_j = (this.field_77881_a == 0);
/* 311 */         this.model.field_78114_d.field_78806_j = (this.field_77881_a == 0);
/* 312 */         this.model.field_78115_e.field_78806_j = (this.field_77881_a == 1 || this.field_77881_a == 2);
/* 313 */         this.model.field_78113_g.field_78806_j = (this.field_77881_a == 1);
/* 314 */         this.model.field_78112_f.field_78806_j = (this.field_77881_a == 1);
/* 315 */         this.model.field_78124_i.field_78806_j = (this.field_77881_a == 2 || this.field_77881_a == 3);
/* 316 */         this.model.field_78123_h.field_78806_j = (this.field_77881_a == 2 || this.field_77881_a == 3);
/*     */       }
/*     */     
/* 319 */     } else if (this.model == null) {
/* 320 */       if (this.field_77881_a == 0) { this.model = (ModelBiped)new ModelDraconicArmorOld(1.1F, true, false, false, false, true); }
/* 321 */       else if (this.field_77881_a == 1)
/* 322 */       { this.model = (ModelBiped)new ModelDraconicArmorOld(1.1F, false, true, false, false, true); }
/* 323 */       else if (this.field_77881_a == 2)
/* 324 */       { this.model = (ModelBiped)new ModelDraconicArmorOld(1.1F, false, false, true, false, true); }
/* 325 */       else { this.model = (ModelBiped)new ModelDraconicArmorOld(1.1F, false, false, false, true, true); }
/* 326 */        this.model.field_78116_c.field_78806_j = (this.field_77881_a == 0);
/* 327 */       this.model.field_78114_d.field_78806_j = (this.field_77881_a == 0);
/* 328 */       this.model.field_78115_e.field_78806_j = (this.field_77881_a == 1 || this.field_77881_a == 2);
/* 329 */       this.model.field_78113_g.field_78806_j = (this.field_77881_a == 1);
/* 330 */       this.model.field_78112_f.field_78806_j = (this.field_77881_a == 1);
/* 331 */       this.model.field_78124_i.field_78806_j = (this.field_77881_a == 2 || this.field_77881_a == 3);
/* 332 */       this.model.field_78123_h.field_78806_j = (this.field_77881_a == 2 || this.field_77881_a == 3);
/*     */     } 
/*     */ 
/*     */     
/* 336 */     if (entityLiving == null) return this.model;
/*     */     
/* 338 */     this.model.field_78117_n = entityLiving.func_70093_af();
/* 339 */     this.model.field_78093_q = entityLiving.func_70115_ae();
/* 340 */     this.model.field_78091_s = entityLiving.func_70631_g_();
/* 341 */     this.model.field_78118_o = false;
/* 342 */     this.model.field_78120_m = (entityLiving.func_70694_bm() != null) ? 1 : 0;
/*     */     
/* 344 */     if (entityLiving instanceof EntityPlayer && (
/* 345 */       (EntityPlayer)entityLiving).func_71057_bx() > 0) {
/* 346 */       EnumAction enumaction = ((EntityPlayer)entityLiving).func_71011_bu().func_77975_n();
/* 347 */       if (enumaction == EnumAction.block) {
/* 348 */         this.model.field_78120_m = 3;
/* 349 */       } else if (enumaction == EnumAction.bow) {
/* 350 */         this.model.field_78118_o = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 356 */     return this.model;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IUpgradableItem.EnumUpgrade> getUpgrades(ItemStack itemstack) {
/* 361 */     return new ArrayList<IUpgradableItem.EnumUpgrade>()
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
/* 372 */     return BalanceConfigHandler.draconicArmorMaxUpgrades;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTier(ItemStack itemstack) {
/* 377 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUpgradeStats(ItemStack stack) {
/* 382 */     List<String> strings = new ArrayList<>();
/*     */     
/* 384 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.RFCapacity.txt") + ": " + InfoHelper.HITC() + Utills.formatNumber(getMaxCharge(stack)));
/* 385 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.ShieldCapacity.txt") + ": " + InfoHelper.HITC() + (int)getProtectionPoints(stack));
/* 386 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.ShieldRecovery.txt") + ": " + InfoHelper.HITC() + Utills.round(getRecoveryPoints(stack) * 0.2D, 10.0D) + " EPS");
/*     */     
/* 388 */     return strings;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex) {
/* 393 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.RF_CAPACITY.index) {
/* 394 */       return BalanceConfigHandler.draconicArmorMaxCapacityUpgradePoints;
/*     */     }
/* 396 */     return BalanceConfigHandler.draconicArmorMaxUpgradePoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex, ItemStack stack) {
/* 401 */     return getMaxUpgradePoints(upgradeIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseUpgradePoints(int upgradeIndex) {
/* 406 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.SHIELD_CAPACITY.index) {
/* 407 */       return (int)(getProtectionShare() * 25.0F) + ((this.field_77881_a == 2) ? 2 : 0);
/*     */     }
/* 409 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.SHIELD_RECOVERY.index) {
/* 410 */       return 5;
/*     */     }
/* 412 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getProtectionPoints(ItemStack stack) {
/* 419 */     return (IUpgradableItem.EnumUpgrade.SHIELD_CAPACITY.getUpgradePoints(stack) * 20);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRecoveryPoints(ItemStack stack) {
/* 424 */     return IUpgradableItem.EnumUpgrade.SHIELD_RECOVERY.getUpgradePoints(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSpeedModifier(ItemStack stack, EntityPlayer player) {
/* 429 */     if (IConfigurableItem.ProfileHelper.getBoolean(stack, "ArmorSprintOnly", false))
/* 430 */       return player.func_70051_ag() ? IConfigurableItem.ProfileHelper.getFloat(stack, "ArmorSpeedMult", 0.0F) : (IConfigurableItem.ProfileHelper.getFloat(stack, "ArmorSpeedMult", 0.0F) / 5.0F); 
/* 431 */     return IConfigurableItem.ProfileHelper.getFloat(stack, "ArmorSpeedMult", 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getJumpModifier(ItemStack stack, EntityPlayer player) {
/* 436 */     if (IConfigurableItem.ProfileHelper.getBoolean(stack, "ArmorSprintOnly", false))
/* 437 */       return (player.func_70051_ag() || BrandonsCore.proxy.isCtrlDown()) ? IConfigurableItem.ProfileHelper.getFloat(stack, "ArmorJumpMult", 0.0F) : (IConfigurableItem.ProfileHelper.getFloat(stack, "ArmorJumpMult", 0.0F) / 5.0F); 
/* 438 */     return IConfigurableItem.ProfileHelper.getFloat(stack, "ArmorJumpMult", 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasHillStep(ItemStack stack, EntityPlayer player) {
/* 443 */     if (IConfigurableItem.ProfileHelper.getBoolean(stack, "ArmorSprintOnly", false))
/* 444 */       return ((player.func_70051_ag() || BrandonsCore.proxy.isCtrlDown()) && IConfigurableItem.ProfileHelper.getBoolean(stack, "ArmorHillStep", true)); 
/* 445 */     return IConfigurableItem.ProfileHelper.getBoolean(stack, "ArmorHillStep", true);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFireResistance(ItemStack stack) {
/* 450 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] hasFlight(ItemStack stack) {
/* 455 */     return new boolean[] { true, IConfigurableItem.ProfileHelper.getBoolean(stack, "ArmorFlightLock", false), IConfigurableItem.ProfileHelper.getBoolean(stack, "ArmorInertiaCancellation", false) };
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFlightSpeedModifier(ItemStack stack, EntityPlayer player) {
/* 460 */     if (IConfigurableItem.ProfileHelper.getBoolean(stack, "EffectiveOnSprint", false))
/* 461 */       return BrandonsCore.proxy.isCtrlDown() ? IConfigurableItem.ProfileHelper.getFloat(stack, "ArmorFlightSpeedMult", 0.0F) : 0.0F; 
/* 462 */     return IConfigurableItem.ProfileHelper.getFloat(stack, "ArmorFlightSpeedMult", 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFlightVModifier(ItemStack stack, EntityPlayer player) {
/* 467 */     if (IConfigurableItem.ProfileHelper.getBoolean(stack, "EffectiveOnSprint", false))
/* 468 */       return BrandonsCore.proxy.isCtrlDown() ? IConfigurableItem.ProfileHelper.getFloat(stack, "VerticalAcceleration", 0.0F) : 0.0F; 
/* 469 */     return IConfigurableItem.ProfileHelper.getFloat(stack, "VerticalAcceleration", 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyPerProtectionPoint() {
/* 474 */     return BalanceConfigHandler.draconicArmorEnergyPerProtectionPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasProfiles() {
/* 481 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\armor\DraconicArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */