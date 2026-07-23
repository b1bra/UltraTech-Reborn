/*     */ package com.brandon3055.draconicevolution.common.items.weapons;
/*     */ import com.brandon3055.brandonscore.common.utills.IC2Helper;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.entity.EntityCustomArrow;
/*     */ import com.brandon3055.draconicevolution.common.entity.EntityEnderArrow;
/*     */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import java.util.Random;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.player.ArrowLooseEvent;
/*     */ import net.minecraftforge.event.entity.player.ArrowNockEvent;
/*     */ 
/*     */ public class BowHandler {
/*     */   public static ItemStack onBowRightClick(Item bow, ItemStack stack, World world, EntityPlayer player) {
/*  27 */     BowProperties properties = new BowProperties(stack, player);
/*  28 */     if (properties.canFire()) {
/*  29 */       ArrowNockEvent event = new ArrowNockEvent(player, stack);
/*  30 */       MinecraftForge.EVENT_BUS.post((Event)event);
/*  31 */       if (event.isCanceled()) {
/*  32 */         return event.result;
/*     */       }
/*     */       
/*  35 */       player.func_71008_a(stack, bow.func_77626_a(stack));
/*     */     } 
/*     */     
/*  38 */     return stack;
/*     */   }
/*     */   
/*     */   public static void onBowUsingTick(ItemStack stack, EntityPlayer player, int count) {
/*  42 */     BowProperties properties = new BowProperties(stack, player);
/*  43 */     int j = 72000 - count;
/*  44 */     if (properties.autoFire && j >= properties.getDrawTicks()) player.func_71034_by(); 
/*     */   }
/*     */   
/*     */   public static void onPlayerStoppedUsingBow(ItemStack stack, World world, EntityPlayer player, int count) {
/*  48 */     BowProperties properties = new BowProperties(stack, player);
/*  49 */     if (!properties.canFire() || !IC2Helper.isElectricItem(stack))
/*     */       return; 
/*  51 */     int j = 72000 - count;
/*  52 */     ArrowLooseEvent event = new ArrowLooseEvent(player, stack, j);
/*  53 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*     */     
/*  55 */     if (event.isCanceled()) {
/*     */       return;
/*     */     }
/*     */     
/*  59 */     j = event.charge;
/*     */     
/*  61 */     float drawArrowSpeedModifier = Math.min(j / properties.getDrawTicks(), 1.0F);
/*     */     
/*  63 */     if (drawArrowSpeedModifier < 0.1D) {
/*     */       return;
/*     */     }
/*     */     
/*  67 */     float velocity = properties.arrowSpeed * drawArrowSpeedModifier * 2.0F;
/*     */     
/*  69 */     EntityCustomArrow customArrow = new EntityCustomArrow(world, (EntityLivingBase)player, velocity);
/*  70 */     customArrow.bowProperties = properties;
/*     */     
/*  72 */     if (drawArrowSpeedModifier == 1.0F) {
/*  73 */       customArrow.func_70243_d(true);
/*     */     }
/*     */     
/*  76 */     if (properties.consumeArrowAndEnergy()) {
/*  77 */       customArrow.field_70251_a = 1;
/*     */     } else {
/*  79 */       customArrow.field_70251_a = 2;
/*     */     } 
/*     */     
/*  82 */     if (!world.field_72995_K) world.func_72838_d((Entity)customArrow);
/*     */     
/*  84 */     world.func_72956_a((Entity)player, "random.bow", 1.0F, 1.0F / (world.field_73012_v.nextFloat() * 0.4F + 1.2F) + (drawArrowSpeedModifier + velocity / 40.0F) * 0.5F);
/*     */   }
/*     */   
/*     */   public static void enderShot(ItemStack stack, World world, EntityPlayer player, int count, Random itemRand, float pullSpeedModifier, float speedModifier, float soundPitchModifier, int minRelease) {
/*  88 */     int j = 72000 - count;
/*  89 */     ArrowLooseEvent event = new ArrowLooseEvent(player, stack, j);
/*  90 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  91 */     if (event.isCanceled()) {
/*     */       return;
/*     */     }
/*  94 */     j = event.charge;
/*     */     
/*  96 */     if (player.field_71071_by.func_146028_b((Item)ModItems.enderArrow)) {
/*  97 */       float f = j / pullSpeedModifier;
/*  98 */       f = (f * f + f * 2.0F) / 3.0F;
/*     */       
/* 100 */       if (j < minRelease || f < 0.1D)
/*     */         return; 
/* 102 */       if (f > 1.0F) f = 1.0F;
/*     */       
/* 104 */       f *= speedModifier;
/*     */       
/* 106 */       EntityEnderArrow entityArrow = new EntityEnderArrow(world, (EntityLivingBase)player, f * 2.0F);
/*     */ 
/*     */       
/* 109 */       stack.func_77972_a(1, (EntityLivingBase)player);
/* 110 */       world.func_72956_a((Entity)player, "random.bow", 1.0F, soundPitchModifier * (1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.3F));
/*     */       
/* 112 */       if (player.field_71071_by.func_146028_b((Item)ModItems.enderArrow)) {
/* 113 */         player.field_71071_by.func_146026_a((Item)ModItems.enderArrow);
/*     */       }
/* 115 */       if (!world.field_72995_K) {
/* 116 */         world.func_72838_d((Entity)entityArrow);
/* 117 */         player.func_70078_a((Entity)entityArrow);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static class BowProperties
/*     */   {
/*     */     public ItemStack bow;
/*     */     
/*     */     public EntityPlayer player;
/* 128 */     public float arrowDamage = 0.0F;
/* 129 */     public float arrowSpeed = 0.0F;
/* 130 */     public float explosionPower = 0.0F;
/* 131 */     public float shockWavePower = 0.0F;
/* 132 */     public float zoomModifier = 0.0F;
/* 133 */     private int drawTimeReduction = 0;
/*     */     
/*     */     public boolean autoFire = false;
/*     */     public boolean energyBolt = false;
/* 137 */     public String cantFireMessage = null;
/*     */     
/*     */     public BowProperties() {
/* 140 */       this.bow = new ItemStack(ModItems.wyvernBow);
/* 141 */       this.player = null;
/*     */     }
/*     */     
/*     */     public BowProperties(ItemStack bow, EntityPlayer player) {
/* 145 */       this.bow = bow;
/* 146 */       this.player = player;
/* 147 */       updateValues();
/*     */     }
/*     */     
/*     */     public int calculateEnergyCost() {
/* 151 */       updateValues();
/* 152 */       double rfCost = (this.bow.func_77973_b() instanceof IEnergyContainerWeaponItem) ? ((IEnergyContainerWeaponItem)this.bow.func_77973_b()).getEnergyPerAttack() : 80.0D;
/*     */       
/* 154 */       rfCost *= (1.0F + this.arrowDamage);
/* 155 */       rfCost *= ((1.0F + this.arrowSpeed) * (1.0F + this.arrowSpeed) * (1.0F + this.arrowSpeed));
/* 156 */       rfCost *= (1.0F + this.explosionPower * 20.0F);
/* 157 */       rfCost *= (1.0F + this.shockWavePower * 10.0F);
/* 158 */       if (this.energyBolt) rfCost *= BalanceConfigHandler.draconicFireEnergyCostMultiptier;
/*     */       
/* 160 */       return (int)rfCost;
/*     */     }
/*     */     
/*     */     public boolean canFire() {
/* 164 */       updateValues();
/*     */       
/* 166 */       if (this.player == null) return false; 
/* 167 */       if (!(this.bow.func_77973_b() instanceof IEnergyContainerWeaponItem)) {
/* 168 */         this.cantFireMessage = "[Error] This bow is not a valid energy container (This is a bug, Please report on the Draconic Evolution github)";
/* 169 */         return false;
/* 170 */       }  if (!this.energyBolt && this.shockWavePower > 0.0F) {
/* 171 */         this.cantFireMessage = "msg.de.shockWaveForEnergyBoltsOnly.txt";
/* 172 */         return false;
/* 173 */       }  if (this.energyBolt && this.explosionPower > 0.0F) {
/* 174 */         this.cantFireMessage = "msg.de.explosiveNotForEnergyBolts.txt";
/* 175 */         return false;
/* 176 */       }  if (calculateEnergyCost() > ((IEnergyContainerWeaponItem)this.bow.func_77973_b()).getCharge(this.bow) && !this.player.field_71075_bZ.field_75098_d) {
/* 177 */         this.cantFireMessage = "msg.de.insufficientPowerToFire.txt";
/* 178 */         return false;
/* 179 */       }  if (!this.energyBolt && !this.player.field_71071_by.func_146028_b(Items.field_151032_g) && EnchantmentHelper.func_77506_a(Enchantment.field_77342_w.field_77352_x, this.bow) == 0 && !this.player.field_71075_bZ.field_75098_d) {
/* 180 */         this.cantFireMessage = "msg.de.outOfArrows.txt";
/* 181 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 185 */       this.cantFireMessage = null;
/* 186 */       return true;
/*     */     }
/*     */     
/*     */     private void updateValues() {
/* 190 */       this.arrowDamage = IConfigurableItem.ProfileHelper.getFloat(this.bow, "BowArrowDamage", IUpgradableItem.EnumUpgrade.ARROW_DAMAGE.getUpgradePoints(this.bow));
/* 191 */       this.arrowSpeed = 1.0F + IConfigurableItem.ProfileHelper.getFloat(this.bow, "BowArrowSpeedModifier", 0.0F);
/* 192 */       this.explosionPower = IConfigurableItem.ProfileHelper.getFloat(this.bow, "BowExplosionPower", 0.0F);
/* 193 */       this.shockWavePower = IConfigurableItem.ProfileHelper.getFloat(this.bow, "BowShockWavePower", 0.0F);
/* 194 */       this.drawTimeReduction = IUpgradableItem.EnumUpgrade.DRAW_SPEED.getUpgradePoints(this.bow);
/* 195 */       this.zoomModifier = IConfigurableItem.ProfileHelper.getFloat(this.bow, "BowZoomModifier", 0.0F);
/* 196 */       this.autoFire = IConfigurableItem.ProfileHelper.getBoolean(this.bow, "BowAutoFire", false);
/* 197 */       this.energyBolt = IConfigurableItem.ProfileHelper.getBoolean(this.bow, "BowEnergyBolt", false);
/*     */     }
/*     */     
/*     */     public int getDrawTicks() {
/* 201 */       return Math.max(62 - this.drawTimeReduction * 10, 1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean consumeArrowAndEnergy() {
/* 210 */       if (!this.player.field_71075_bZ.field_75098_d) {
/* 211 */         ((IEnergyContainerWeaponItem)this.bow.func_77973_b()).useEnergy(this.bow, calculateEnergyCost(), this.player);
/*     */       }
/* 213 */       if (!this.energyBolt && EnchantmentHelper.func_77506_a(Enchantment.field_77342_w.field_77352_x, this.bow) == 0 && !this.player.field_71075_bZ.field_75098_d) {
/* 214 */         this.player.field_71071_by.func_146026_a(Items.field_151032_g);
/* 215 */         return true;
/*     */       } 
/*     */       
/* 218 */       return false;
/*     */     }
/*     */     
/*     */     public void writeToNBT(NBTTagCompound compound) {
/* 222 */       compound.func_74776_a("ArrowDamage", this.arrowDamage);
/* 223 */       compound.func_74776_a("ArrowExplosive", this.explosionPower);
/* 224 */       compound.func_74776_a("ArrowShock", this.shockWavePower);
/* 225 */       compound.func_74757_a("ArrowEnergy", this.energyBolt);
/*     */     }
/*     */     
/*     */     public void readFromNBT(NBTTagCompound compound) {
/* 229 */       this.arrowDamage = compound.func_74760_g("ArrowDamage");
/* 230 */       this.explosionPower = compound.func_74760_g("ArrowExplosive");
/* 231 */       this.shockWavePower = compound.func_74760_g("ArrowShock");
/* 232 */       this.energyBolt = compound.func_74767_n("ArrowEnergy");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\weapons\BowHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */