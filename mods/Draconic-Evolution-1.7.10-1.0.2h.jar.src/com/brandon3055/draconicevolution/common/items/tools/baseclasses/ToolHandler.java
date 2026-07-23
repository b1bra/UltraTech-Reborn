/*     */ package com.brandon3055.draconicevolution.common.items.tools.baseclasses;
/*     */
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.items.weapons.IEnergyContainerWeaponItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*     */ import java.util.List;
/*     */
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemSword;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
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
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class ToolHandler
/*     */ {
/*     */   public static void damageEntityBasedOnHealth(Entity entity, EntityPlayer player, float dmgMult) {
/* 138 */     ItemStack stack = player.func_71045_bC();
/* 139 */     if (stack == null || !(stack.func_77973_b() instanceof IEnergyContainerWeaponItem)) {
/* 140 */       LogHelper.error("[ToolHandler.java:147] WTF? I don't get it... Player " + player.func_70005_c_() + " whacked something with a DE weapon but that they are not holding? Ok someone is messing with my shit...");
/*     */
/*     */       return;
/*     */     }
/* 144 */     IEnergyContainerWeaponItem item = (IEnergyContainerWeaponItem)stack.func_77973_b();
/* 145 */     float baseAttack = getDamageAgainstEntity(stack, entity);
/*     */
/* 147 */     if (entity instanceof EntityLivingBase) {
/* 148 */       float entHealth = ((EntityLivingBase)entity).func_110143_aJ();
/* 149 */       baseAttack += entHealth * dmgMult;
/*     */     }
/*     */
/* 152 */     if (entity instanceof net.minecraft.entity.boss.EntityDragonPart) {
/* 153 */       List<EntityDragon> list = player.field_70170_p.func_72872_a(EntityDragon.class, entity.field_70121_D.func_72314_b(10.0D, 10.0D, 10.0D));
/* 154 */       if (!list.isEmpty() && list.get(0) instanceof EntityDragon) {
/* 155 */         EntityDragon dragon = list.get(0);
/* 156 */         float entHealth = dragon.func_110143_aJ();
/* 157 */         baseAttack += entHealth * dmgMult;
/*     */       }
/*     */     }
/*     */
/* 161 */     double eu = (baseAttack * item.getEnergyPerAttack());
/*     */
/* 163 */     if (eu > item.getCharge(stack)) {
/* 164 */       baseAttack = (float)(item.getCharge(stack) / item.getEnergyPerAttack());
/* 165 */       eu = item.getCharge(stack);
/*     */     }
/*     */
/* 168 */     if (baseAttack <= 0.0F) baseAttack = 1.0F;
/*     */
/* 170 */     entity.func_70097_a(DamageSource.func_76365_a(player), baseAttack);
/* 171 */     if (EnchantmentHelper.func_77506_a(Enchantment.field_77334_n.field_77352_x, stack) > 0) {
/* 172 */       entity.func_70015_d(EnchantmentHelper.func_77506_a(Enchantment.field_77334_n.field_77352_x, stack) * 15);
/*     */     }
/* 174 */     if (!player.field_71075_bZ.field_75098_d) {
/* 175 */       item.useEnergy(stack, eu, player);
/*     */     }
/* 177 */     if (entity instanceof EntityLivingBase) {
/* 178 */       EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
/* 179 */       double d1 = player.field_70165_t - entityLivingBase.field_70165_t;
/*     */
/*     */       double d0;
/* 182 */       for (d0 = player.field_70161_v - entityLivingBase.field_70161_v; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
/* 183 */         d1 = (Math.random() - Math.random()) * 0.01D;
/*     */       }
/* 185 */       entityLivingBase.field_70739_aP = (float)(Math.atan2(d0, d1) * 180.0D / Math.PI) - entityLivingBase.field_70177_z;
/*     */
/* 187 */       if (entityLivingBase.field_70170_p.field_73012_v.nextDouble() >= entityLivingBase.func_110148_a(SharedMonsterAttributes.field_111266_c).func_111126_e()) {
/* 188 */         entityLivingBase.field_70160_al = true;
/* 189 */         float f1 = MathHelper.func_76133_a(d1 * d1 + d0 * d0);
/* 190 */         float f2 = 0.1F + EnchantmentHelper.func_77507_b((EntityLivingBase)player, entityLivingBase) * 0.4F;
/* 191 */         entityLivingBase.field_70159_w /= 2.0D;
/* 192 */         entityLivingBase.field_70181_x /= 2.0D;
/* 193 */         entityLivingBase.field_70179_y /= 2.0D;
/* 194 */         entityLivingBase.field_70159_w -= d1 / f1 * f2;
/* 195 */         entityLivingBase.field_70181_x += f2;
/* 196 */         entityLivingBase.field_70179_y -= d0 / f1 * f2;
/*     */
/* 198 */         if (entityLivingBase.field_70181_x > 0.4000000059604645D) {
/* 199 */           entityLivingBase.field_70181_x = 0.4000000059604645D;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */
/*     */   public static void AOEAttack(EntityPlayer player, Entity entity, ItemStack stack, int range) {
/* 206 */     if (range == 0) {
/*     */       return;
/*     */     }
/* 209 */     World world = player.field_70170_p;
/* 210 */     AxisAlignedBB box = AxisAlignedBB.func_72330_a(entity.field_70165_t - range, entity.field_70163_u - range, entity.field_70161_v - range, entity.field_70165_t + range, entity.field_70163_u + range, entity.field_70161_v + range).func_72314_b(1.0D, 1.0D, 1.0D);
/*     */
/* 212 */     List<EntityLivingBase> list = world.func_72872_a(EntityLivingBase.class, box);
/*     */
/* 214 */     IEnergyContainerWeaponItem item = (IEnergyContainerWeaponItem)stack.func_77973_b();
/*     */
/* 216 */     for (EntityLivingBase target : list) {
/* 217 */       if (target == player || target == entity) {
/*     */         continue;
/*     */       }
/* 220 */       double charge = item.getCharge(stack);
/* 221 */       if (charge < item.getEnergyPerAttack()) {
/*     */         break;
/*     */       }
/* 224 */       float dmg = getDamageAgainstEntity(stack, (Entity)target);
/* 225 */       double eu = (dmg * item.getEnergyPerAttack());
/*     */
/* 227 */       if (eu > charge) {
/* 228 */         dmg = (float)(charge / item.getEnergyPerAttack());
/* 229 */         eu = charge;
/*     */       }
/*     */
/* 232 */       target.func_70097_a(DamageSource.func_76365_a(player), dmg);
/* 233 */       item.useEnergy(stack, eu, player);
/* 234 */       if (EnchantmentHelper.func_77506_a(Enchantment.field_77334_n.field_77352_x, stack) > 0) {
/* 235 */         target.func_70015_d(EnchantmentHelper.func_77506_a(Enchantment.field_77334_n.field_77352_x, stack) * 15);
/*     */       }
/* 237 */       double d1 = player.field_70165_t - target.field_70165_t;
/*     */
/*     */       double d0;
/* 240 */       for (d0 = player.field_70161_v - target.field_70161_v; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
/* 241 */         d1 = (Math.random() - Math.random()) * 0.01D;
/*     */       }
/*     */
/* 244 */       if (target.field_70170_p.field_73012_v.nextDouble() >= target.func_110148_a(SharedMonsterAttributes.field_111266_c).func_111126_e()) {
/* 245 */         target.field_70160_al = true;
/* 246 */         float f1 = MathHelper.func_76133_a(d1 * d1 + d0 * d0);
/* 247 */         float f2 = 0.1F + EnchantmentHelper.func_77507_b((EntityLivingBase)player, target) * 0.4F;
/* 248 */         target.field_70159_w /= 2.0D;
/* 249 */         target.field_70181_x /= 2.0D;
/* 250 */         target.field_70179_y /= 2.0D;
/* 251 */         target.field_70159_w -= d1 / f1 * f2;
/* 252 */         target.field_70181_x += f2;
/* 253 */         target.field_70179_y -= d0 / f1 * f2;
/*     */
/* 255 */         if (target.field_70181_x > 0.4000000059604645D) {
/* 256 */           target.field_70181_x = 0.4000000059604645D;
/*     */         }
/*     */       }
/* 259 */       target.field_70724_aR = 0;
/*     */     }
/*     */   }
/*     */
/*     */   public static MovingObjectPosition raytraceFromEntity(World world, Entity player, double range) {
/* 264 */     float f = 1.0F;
/* 265 */     float f1 = player.field_70127_C + (player.field_70125_A - player.field_70127_C) * f;
/* 266 */     float f2 = player.field_70126_B + (player.field_70177_z - player.field_70126_B) * f;
/* 267 */     double d0 = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * f;
/* 268 */     double d1 = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * f;
/* 269 */     if (!world.field_72995_K && player instanceof EntityPlayer) d1 += 1.62D;
/* 270 */     double d2 = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * f;
/* 271 */     Vec3 vec3 = Vec3.func_72443_a(d0, d1, d2);
/* 272 */     float f3 = MathHelper.func_76134_b(-f2 * 0.017453292F - 3.1415927F);
/* 273 */     float f4 = MathHelper.func_76126_a(-f2 * 0.017453292F - 3.1415927F);
/* 274 */     float f5 = -MathHelper.func_76134_b(-f1 * 0.017453292F);
/* 275 */     float f6 = MathHelper.func_76126_a(-f1 * 0.017453292F);
/* 276 */     float f7 = f4 * f5;
/* 277 */     float f8 = f3 * f5;
/* 278 */     double d3 = range;
/* 279 */     if (player instanceof EntityPlayerMP && range < 10.0D) {
/* 280 */       d3 = ((EntityPlayerMP)player).field_71134_c.getBlockReachDistance();
/*     */     }
/* 282 */     Vec3 vec31 = vec3.func_72441_c(f7 * d3, f6 * d3, f8 * d3);
/* 283 */     return world.func_72933_a(vec3, vec31);
/*     */   }
/*     */
/*     */   public static void updateGhostBlocks(EntityPlayer player, World world) {
/* 287 */     if (world.field_72995_K)
/* 288 */       return;  int xPos = (int)player.field_70165_t;
/* 289 */     int yPos = (int)player.field_70163_u;
/* 290 */     int zPos = (int)player.field_70161_v;
/*     */
/* 292 */     for (int x = xPos - 6; x < xPos + 6; x++) {
/* 293 */       for (int y = yPos - 6; y < yPos + 6; y++) {
/* 294 */         for (int z = zPos - 6; z < zPos + 6; z++) {
/* 295 */           ((EntityPlayerMP)player).field_71135_a.func_147359_a((Packet)new S23PacketBlockChange(x, y, z, world));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */
/*     */
/*     */   public static float getBaseAttackDamage(ItemStack stack) {
/* 303 */     float dmg = 1.0F;
/* 304 */     if (stack == null) return 1.0F;
/*     */
/* 306 */     float sharpMod = EnchantmentHelper.func_77506_a(Enchantment.field_77338_j.field_77352_x, stack) * 4.0F;
/*     */
/* 308 */     if (stack.func_77973_b() == ModItems.draconicDestructionStaff)
/* 309 */     { dmg = ModItems.CHAOTIC.func_78000_c() + sharpMod; }
/* 310 */     else if (stack.func_77973_b() instanceof ItemSword) { dmg = ((ItemSword)stack.func_77973_b()).func_150931_i() + sharpMod; }
/*     */
/* 312 */     dmg += (IUpgradableItem.EnumUpgrade.ATTACK_DAMAGE.getUpgradePoints(stack) * 5);
/*     */
/* 314 */     return dmg;
/*     */   }
/*     */
/*     */   public static float getDamageAgainstEntity(ItemStack stack, Entity entity) {
/* 318 */     float baseAttack = getBaseAttackDamage(stack);
/* 319 */     float smiteMod = EnchantmentHelper.func_77506_a(Enchantment.field_77339_k.field_77352_x, stack) * 6.0F;
/* 320 */     float athropodsMod = EnchantmentHelper.func_77506_a(Enchantment.field_77336_l.field_77352_x, stack) * 6.0F;
/*     */
/* 322 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).func_70662_br()) baseAttack += smiteMod;
/* 323 */     if (entity instanceof net.minecraft.entity.monster.EntitySpider) baseAttack += athropodsMod;
/*     */
/* 325 */     return baseAttack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\baseclasses\ToolHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
