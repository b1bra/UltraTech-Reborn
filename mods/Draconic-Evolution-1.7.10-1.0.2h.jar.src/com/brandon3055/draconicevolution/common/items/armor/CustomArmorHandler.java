/*     */ package com.brandon3055.draconicevolution.common.items.armor;
/*     */ import com.brandon3055.brandonscore.BrandonsCore;
/*     */ import com.brandon3055.brandonscore.common.utills.IC2Helper;
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.network.ShieldHitPacket;
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import com.brandon3055.draconicevolution.integration.ModHelper;
/*     */ import cpw.mods.fml.common.gameevent.TickEvent;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.WeakHashMap;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.ChatStyle;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraftforge.event.entity.living.LivingAttackEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingDeathEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingHurtEvent;
/*     */ 
/*     */ public class CustomArmorHandler {
/*  34 */   public static final UUID WALK_SPEED_UUID = UUID.fromString("0ea6ce8e-d2e8-11e5-ab30-625662870761");
/*  35 */   private static final DamageSource ADMIN_KILL = (new DamageSource("administrative.kill")).func_76359_i()
/*  36 */     .func_76348_h()
/*  37 */     .func_151518_m();
/*  38 */   public static Map<EntityPlayer, Boolean> playersWithFlight = new WeakHashMap<>();
/*  39 */   public static List<String> playersWithUphillStep = new ArrayList<>();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onPlayerHurt(LivingHurtEvent event) {}
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onPlayerAttacked(LivingAttackEvent event) {
/*  82 */     if (event.isCanceled()) {
/*     */       return;
/*     */     }
/*  85 */     EntityPlayer player = (EntityPlayer)event.entityLiving;
/*  86 */     ArmorSummery summery = (new ArmorSummery()).getSummery(player);
/*     */     
/*  88 */     float hitAmount = ModHelper.applyModDamageAdjustments(summery, event);
/*     */     
/*  90 */     if (applyArmorDamageBlocking(event, summery))
/*     */       return; 
/*  92 */     if (summery == null || summery.protectionPoints <= 0.0F || event.source == ADMIN_KILL)
/*     */       return; 
/*  94 */     event.setCanceled(true);
/*     */     
/*  96 */     if (hitAmount == Float.MAX_VALUE && !event.source.field_76373_n.equals(ADMIN_KILL.field_76373_n)) {
/*  97 */       player.func_70097_a(ADMIN_KILL, Float.MAX_VALUE);
/*     */       return;
/*     */     } 
/* 100 */     if (player.field_70172_ad > player.field_70771_an / 2.0F) {
/*     */       return;
/*     */     }
/* 103 */     float newEntropy = Math.min(summery.entropy + 1.0F + hitAmount / 20.0F, 100.0F);
/*     */ 
/*     */     
/* 106 */     float totalAbsorbed = 0.0F;
/* 107 */     int remainingPoints = 0;
/* 108 */     for (int i = 0; i < summery.allocation.length; i++) {
/* 109 */       if (summery.allocation[i] != 0.0F) {
/*     */         
/* 111 */         ItemStack armorPeace = summery.armorStacks[i];
/*     */         
/* 113 */         float dmgShear = summery.allocation[i] / summery.protectionPoints;
/* 114 */         float dmg = dmgShear * hitAmount;
/*     */         
/* 116 */         float absorbed = Math.min(dmg, summery.allocation[i]);
/* 117 */         totalAbsorbed += absorbed;
/* 118 */         summery.allocation[i] = summery.allocation[i] - absorbed;
/* 119 */         remainingPoints = (int)(remainingPoints + summery.allocation[i]);
/* 120 */         ItemNBTHelper.setFloat(armorPeace, "ProtectionPoints", summery.allocation[i]);
/* 121 */         ItemNBTHelper.setFloat(armorPeace, "ShieldEntropy", newEntropy);
/*     */       } 
/*     */     } 
/* 124 */     if (summery.protectionPoints > 0.0F) {
/* 125 */       DraconicEvolution.network.sendToAllAround((IMessage)new ShieldHitPacket(player, summery.protectionPoints / summery.maxProtectionPoints), new NetworkRegistry.TargetPoint(player.field_71093_bK, player.field_70165_t, player.field_70163_u, player.field_70161_v, 64.0D));
/* 126 */       player.field_70170_p.func_72908_a(player.field_70165_t + 0.5D, player.field_70163_u + 0.5D, player.field_70161_v + 0.5D, "draconicevolution:shieldStrike", 0.9F, player.field_70170_p.field_73012_v.nextFloat() * 0.1F + 1.055F);
/*     */     } 
/*     */     
/* 129 */     if (remainingPoints > 0) {
/* 130 */       player.field_70172_ad = 20;
/* 131 */     } else if (hitAmount - totalAbsorbed > 0.0F) {
/* 132 */       player.func_70097_a(event.source, hitAmount - totalAbsorbed);
/*     */     } 
/*     */   }
/*     */   public static void onPlayerDeath(LivingDeathEvent event) {
/* 136 */     if (event.isCanceled()) {
/*     */       return;
/*     */     }
/* 139 */     EntityPlayer player = (EntityPlayer)event.entityLiving;
/* 140 */     ArmorSummery summery = (new ArmorSummery()).getSummery(player);
/*     */     
/* 142 */     if (summery == null || event.source == ADMIN_KILL) {
/*     */       return;
/*     */     }
/* 145 */     if (summery.protectionPoints > 500.0F) {
/* 146 */       event.setCanceled(true);
/* 147 */       event.entityLiving.func_70606_j(10.0F);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 152 */     if (!summery.hasDraconic) {
/*     */       return;
/*     */     }
/* 155 */     long[] charge = new long[summery.armorStacks.length];
/* 156 */     long totalCharge = 0L;
/*     */     int i;
/* 158 */     for (i = 0; i < summery.armorStacks.length; i++) {
/* 159 */       ItemStack stack = summery.armorStacks[i];
/*     */       
/* 161 */       if (stack != null) {
/* 162 */         charge[i] = IC2Helper.floorEu(IC2Helper.getCharge(stack));
/* 163 */         totalCharge += charge[i];
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     if (totalCharge < BalanceConfigHandler.draconicArmorBaseStorage) {
/*     */       return;
/*     */     }
/* 170 */     for (i = 0; i < summery.armorStacks.length; i++) {
/* 171 */       ItemStack stack = summery.armorStacks[i];
/*     */       
/* 173 */       if (stack != null) {
/* 174 */         ElectricItem.manager.discharge(stack, charge[i] / totalCharge * BalanceConfigHandler.draconicArmorBaseStorage, 2147483647, true, false, false);
/*     */       }
/*     */     } 
/*     */     
/* 178 */     player.func_146105_b((new ChatComponentTranslation("msg.de.shieldDepleted.txt", new Object[0])).func_150255_a((new ChatStyle()).func_150238_a(EnumChatFormatting.DARK_RED)));
/* 179 */     event.setCanceled(true);
/* 180 */     player.func_70606_j(1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
/* 185 */     EntityPlayer player = event.player;
/* 186 */     ArmorSummery summery = (new ArmorSummery()).getSummery(player);
/*     */     
/* 188 */     tickShield(summery, player);
/* 189 */     tickArmorEffects(summery, player);
/*     */   }
/*     */   
/*     */   public static void tickShield(ArmorSummery summery, EntityPlayer player) {
/* 193 */     if (summery == null || ((summery.maxProtectionPoints - summery.protectionPoints) < 0.01D && summery.entropy == 0.0F) || player.field_70170_p.field_72995_K) {
/*     */       return;
/*     */     }
/* 196 */     float totalPointsToAdd = Math.min(summery.maxProtectionPoints - summery.protectionPoints, summery.maxProtectionPoints / 60.0F);
/* 197 */     totalPointsToAdd *= 1.0F - summery.entropy / 100.0F;
/* 198 */     totalPointsToAdd = Math.min(totalPointsToAdd, (float)(summery.totalEnergyStored / (summery.hasDraconic ? BalanceConfigHandler.draconicArmorEnergyPerProtectionPoint : BalanceConfigHandler.wyvernArmorEnergyPerProtectionPoint)));
/* 199 */     if (totalPointsToAdd < 0.0F) {
/* 200 */       totalPointsToAdd = 0.0F;
/*     */     }
/* 202 */     summery.entropy -= summery.meanRecoveryPoints * 0.01F;
/* 203 */     if (summery.entropy < 0.0F) {
/* 204 */       summery.entropy = 0.0F;
/*     */     }
/* 206 */     for (int i = 0; i < summery.armorStacks.length; i++) {
/* 207 */       ItemStack stack = summery.armorStacks[i];
/* 208 */       if (stack != null && summery.totalEnergyStored > 0L) {
/*     */         
/* 210 */         float maxForPeace = ((ICustomArmor)stack.func_77973_b()).getProtectionPoints(stack);
/* 211 */         int energyAmount = ((ICustomArmor)summery.armorStacks[i].func_77973_b()).getEnergyPerProtectionPoint();
/* 212 */         ElectricItem.manager.discharge(stack, summery.energyAllocation[i] / summery.totalEnergyStored * (totalPointsToAdd * energyAmount), 2147483647, true, false, false);
/* 213 */         float pointsForPeace = summery.pointsDown[i] / Math.max(1.0F, summery.maxProtectionPoints - summery.protectionPoints) * totalPointsToAdd;
/* 214 */         summery.allocation[i] = summery.allocation[i] + pointsForPeace;
/* 215 */         if (summery.allocation[i] > maxForPeace || maxForPeace - summery.allocation[i] < 0.1F)
/* 216 */           summery.allocation[i] = maxForPeace; 
/* 217 */         ItemNBTHelper.setFloat(stack, "ProtectionPoints", summery.allocation[i]);
/* 218 */         if (player.field_70172_ad <= 0) {
/* 219 */           ItemNBTHelper.setFloat(stack, "ShieldEntropy", summery.entropy);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void tickArmorEffects(ArmorSummery summery, EntityPlayer player) {
/* 226 */     if (ConfigHandler.enableFlight) {
/* 227 */       if (summery != null && summery.flight[0]) {
/* 228 */         playersWithFlight.put(player, Boolean.valueOf(true));
/* 229 */         player.field_71075_bZ.field_75101_c = true;
/* 230 */         if (summery.flight[1]) {
/* 231 */           player.field_71075_bZ.field_75100_b = true;
/*     */         }
/*     */         
/* 234 */         if (player.field_71075_bZ.field_75100_b) {
/* 235 */           player.field_70143_R = 0.0F;
/*     */         }
/*     */         
/* 238 */         if (player.field_70170_p.field_72995_K) {
/* 239 */           setPlayerFlySpeed(player, 0.05F + 0.05F * summery.flightSpeedModifier);
/*     */         }
/* 241 */         if (!player.field_70122_E && player.field_71075_bZ.field_75100_b && player.field_70181_x != 0.0D && summery.flightVModifier > 0.0F) {
/*     */ 
/*     */ 
/*     */           
/* 245 */           if (BrandonsCore.proxy.isSpaceDown() && !BrandonsCore.proxy.isShiftDown()) {
/* 246 */             player.field_70181_x = (0.225F * summery.flightVModifier);
/*     */           }
/* 248 */           if (BrandonsCore.proxy.isShiftDown() && !BrandonsCore.proxy.isSpaceDown()) {
/* 249 */             player.field_70181_x = (-0.225F * summery.flightVModifier);
/*     */           }
/*     */         } 
/* 252 */         if (summery.flight[2] && player.field_70701_bs == 0.0F && player.field_70702_br == 0.0F && player.field_71075_bZ.field_75100_b) {
/* 253 */           player.field_70159_w *= 0.5D;
/* 254 */           player.field_70179_y *= 0.5D;
/*     */         } 
/*     */       } else {
/*     */         
/* 258 */         playersWithFlight.putIfAbsent(player, Boolean.valueOf(false));
/*     */         
/* 260 */         if (((Boolean)playersWithFlight.get(player)).booleanValue() && !player.field_70170_p.field_72995_K) {
/* 261 */           playersWithFlight.put(player, Boolean.valueOf(false));
/*     */           
/* 263 */           if (!player.field_71075_bZ.field_75098_d) {
/* 264 */             player.field_71075_bZ.field_75101_c = false;
/* 265 */             player.field_71075_bZ.field_75100_b = false;
/* 266 */             player.func_71016_p();
/*     */           } 
/*     */         } 
/*     */         
/* 270 */         if (player.field_70170_p.field_72995_K && ((Boolean)playersWithFlight.get(player)).booleanValue()) {
/* 271 */           playersWithFlight.put(player, Boolean.valueOf(false));
/* 272 */           if (!player.field_71075_bZ.field_75098_d) {
/* 273 */             player.field_71075_bZ.field_75101_c = false;
/* 274 */             player.field_71075_bZ.field_75100_b = false;
/*     */           } 
/* 276 */           setPlayerFlySpeed(player, 0.05F);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 283 */     IAttribute speedAttr = SharedMonsterAttributes.field_111263_d;
/* 284 */     if (summery != null && summery.speedModifier > 0.0F) {
/* 285 */       double value = summery.speedModifier;
/* 286 */       if (player.func_110148_a(speedAttr).func_111127_a(WALK_SPEED_UUID) == null) {
/* 287 */         player.func_110148_a(speedAttr)
/* 288 */           .func_111121_a(new AttributeModifier(WALK_SPEED_UUID, speedAttr.func_111108_a(), value, 1));
/* 289 */       } else if (player.func_110148_a(speedAttr).func_111127_a(WALK_SPEED_UUID).func_111164_d() != value) {
/* 290 */         player.func_110148_a(speedAttr)
/* 291 */           .func_111124_b(player.func_110148_a(speedAttr).func_111127_a(WALK_SPEED_UUID));
/* 292 */         player.func_110148_a(speedAttr)
/* 293 */           .func_111121_a(new AttributeModifier(WALK_SPEED_UUID, speedAttr.func_111108_a(), value, 1));
/*     */       } 
/*     */       
/* 296 */       if (!player.field_70122_E && player.field_70154_o == null)
/* 297 */         player.field_70747_aH = 0.02F + 0.02F * summery.speedModifier; 
/* 298 */     } else if (player.func_110148_a(speedAttr).func_111127_a(WALK_SPEED_UUID) != null) {
/* 299 */       player.func_110148_a(speedAttr)
/* 300 */         .func_111124_b(player.func_110148_a(speedAttr).func_111127_a(WALK_SPEED_UUID));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 305 */     if (summery != null && player.field_70170_p.field_72995_K) {
/* 306 */       boolean highStepListed = (playersWithUphillStep.contains(player.getDisplayName()) && player.field_70138_W >= 1.0F);
/* 307 */       boolean hasHighStep = summery.hasHillStep;
/*     */       
/* 309 */       if (hasHighStep && !highStepListed) {
/* 310 */         playersWithUphillStep.add(player.getDisplayName());
/* 311 */         player.field_70138_W = 1.0F;
/*     */       } 
/*     */       
/* 314 */       if (!hasHighStep && highStepListed) {
/* 315 */         playersWithUphillStep.remove(player.getDisplayName());
/* 316 */         player.field_70138_W = 0.5F;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void setPlayerFlySpeed(EntityPlayer player, float speed) {
/* 323 */     player.field_71075_bZ.func_75092_a(speed);
/*     */   }
/*     */   
/*     */   private static boolean applyArmorDamageBlocking(LivingAttackEvent event, ArmorSummery summery) {
/* 327 */     if (summery == null) {
/* 328 */       return false;
/*     */     }
/* 330 */     if (event.source.func_76347_k() && summery.fireResistance >= 1.0F) {
/* 331 */       event.setCanceled(true);
/* 332 */       event.entityLiving.func_70066_B();
/* 333 */       return true;
/*     */     } 
/*     */     
/* 336 */     if (event.source.field_76373_n.equals("fall") && summery.jumpModifier > 0.0F) {
/* 337 */       if (event.ammount < summery.jumpModifier * 5.0F)
/* 338 */         event.setCanceled(true); 
/* 339 */       return true;
/*     */     } 
/*     */     
/* 342 */     if ((event.source.field_76373_n.equals("inWall") || event.source.field_76373_n.equals("drown")) && summery.armorStacks[3] != null) {
/* 343 */       if (event.ammount <= 2.0F)
/* 344 */         event.setCanceled(true); 
/* 345 */       return true;
/*     */     } 
/*     */     
/* 348 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ArmorSummery
/*     */   {
/* 356 */     public float maxProtectionPoints = 0.0F;
/*     */ 
/*     */ 
/*     */     
/* 360 */     public float protectionPoints = 0.0F;
/*     */ 
/*     */ 
/*     */     
/* 364 */     public int peaces = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     public float[] allocation;
/*     */ 
/*     */ 
/*     */     
/*     */     public float[] pointsDown;
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemStack[] armorStacks;
/*     */ 
/*     */ 
/*     */     
/* 380 */     public float entropy = 0.0F;
/*     */ 
/*     */ 
/*     */     
/* 384 */     public int meanRecoveryPoints = 0;
/*     */ 
/*     */ 
/*     */     
/* 388 */     public long totalEnergyStored = 0L;
/*     */ 
/*     */ 
/*     */     
/* 392 */     public long maxTotalEnergyStorage = 0L;
/*     */ 
/*     */     
/*     */     public long[] energyAllocation;
/*     */ 
/*     */     
/* 398 */     public boolean[] flight = new boolean[] { false, false, false };
/* 399 */     public float flightVModifier = 0.0F;
/* 400 */     public float speedModifier = 0.0F;
/* 401 */     public float jumpModifier = 0.0F;
/* 402 */     public float fireResistance = 0.0F;
/* 403 */     public float flightSpeedModifier = 0.0F;
/*     */     public boolean hasHillStep = false;
/*     */     public boolean hasDraconic = false;
/*     */     
/*     */     public ArmorSummery getSummery(EntityPlayer player) {
/* 408 */       ItemStack[] armorSlots = player.field_71071_by.field_70460_b;
/* 409 */       float totalEntropy = 0.0F;
/* 410 */       int totalRecoveryPoints = 0;
/*     */       
/* 412 */       this.allocation = new float[armorSlots.length];
/* 413 */       this.armorStacks = new ItemStack[armorSlots.length];
/* 414 */       this.pointsDown = new float[armorSlots.length];
/* 415 */       this.energyAllocation = new long[armorSlots.length];
/*     */       
/* 417 */       for (int i = 0; i < armorSlots.length; i++) {
/* 418 */         ItemStack stack = armorSlots[i];
/* 419 */         if (stack != null && stack.func_77973_b() instanceof ICustomArmor) {
/*     */           
/* 421 */           ICustomArmor armor = (ICustomArmor)stack.func_77973_b();
/* 422 */           this.peaces++;
/* 423 */           this.allocation[i] = ItemNBTHelper.getFloat(stack, "ProtectionPoints", 0.0F);
/* 424 */           this.protectionPoints += this.allocation[i];
/* 425 */           totalEntropy += ItemNBTHelper.getFloat(stack, "ShieldEntropy", 0.0F);
/* 426 */           this.armorStacks[i] = stack;
/* 427 */           totalRecoveryPoints += IUpgradableItem.EnumUpgrade.SHIELD_RECOVERY.getUpgradePoints(stack);
/* 428 */           float maxPoints = armor.getProtectionPoints(stack);
/* 429 */           this.pointsDown[i] = maxPoints - this.allocation[i];
/* 430 */           this.maxProtectionPoints += maxPoints;
/* 431 */           this.energyAllocation[i] = IC2Helper.floorEu(armor.getCharge(stack));
/* 432 */           this.totalEnergyStored += this.energyAllocation[i];
/* 433 */           this.maxTotalEnergyStorage += IC2Helper.floorEu(armor.getMaxCharge(stack));
/* 434 */           if (stack.func_77973_b() instanceof DraconicArmor) {
/* 435 */             this.hasDraconic = true;
/*     */           }
/* 437 */           this.fireResistance += armor.getFireResistance(stack);
/*     */           
/* 439 */           switch (i) {
/*     */             case 2:
/* 441 */               this.flight = armor.hasFlight(stack);
/* 442 */               if (this.flight[0]) {
/* 443 */                 this.flightVModifier = armor.getFlightVModifier(stack, player);
/* 444 */                 this.flightSpeedModifier = armor.getFlightSpeedModifier(stack, player);
/*     */               } 
/*     */               break;
/*     */             case 1:
/* 448 */               this.speedModifier = armor.getSpeedModifier(stack, player);
/*     */               break;
/*     */             case 0:
/* 451 */               this.hasHillStep = armor.hasHillStep(stack, player);
/* 452 */               this.jumpModifier = armor.getJumpModifier(stack, player);
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 457 */       if (this.peaces == 0) {
/* 458 */         return null;
/*     */       }
/* 460 */       this.entropy = totalEntropy / this.peaces;
/* 461 */       this.meanRecoveryPoints = totalRecoveryPoints / this.peaces;
/*     */       
/* 463 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\armor\CustomArmorHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */