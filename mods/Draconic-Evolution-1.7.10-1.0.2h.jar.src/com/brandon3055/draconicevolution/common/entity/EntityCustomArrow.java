/*     */ package com.brandon3055.draconicevolution.common.entity;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.client.handler.ParticleHandler;
/*     */ import com.brandon3055.draconicevolution.client.render.particle.Particles;
/*     */ import com.brandon3055.draconicevolution.common.items.weapons.BowHandler;
/*     */ import com.brandon3055.draconicevolution.common.network.GenericParticlePacket;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.boss.EntityDragonPart;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntityDamageSourceIndirect;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityCustomArrow extends EntityArrow {
/*  33 */   private int blockX = -1;
/*  34 */   private int blockY = -1;
/*  35 */   private int blockZ = -1;
/*     */   
/*     */   private Block blockHit;
/*     */   
/*     */   private int inData;
/*     */   private boolean inGround;
/*     */   private int ticksInGround;
/*     */   private int ticksInAir;
/*     */   public boolean ignorSpeed = false;
/*     */   public boolean explosive = false;
/*  45 */   public int field_70256_ap = 0;
/*     */   
/*  47 */   public BowHandler.BowProperties bowProperties = new BowHandler.BowProperties();
/*     */   
/*     */   public EntityCustomArrow(World p_i1753_1_) {
/*  50 */     super(p_i1753_1_);
/*  51 */     this.field_70155_l = 40.0D;
/*     */   }
/*     */   
/*     */   public EntityCustomArrow(World p_i1754_1_, double p_i1754_2_, double p_i1754_4_, double p_i1754_6_) {
/*  55 */     super(p_i1754_1_, p_i1754_2_, p_i1754_4_, p_i1754_6_);
/*  56 */     this.field_70155_l = 40.0D;
/*     */   }
/*     */   
/*     */   public EntityCustomArrow(World p_i1755_1_, EntityLivingBase p_i1755_2_, EntityLivingBase p_i1755_3_, float p_i1755_4_, float p_i1755_5_) {
/*  60 */     super(p_i1755_1_, p_i1755_2_, p_i1755_3_, p_i1755_4_, p_i1755_5_);
/*  61 */     this.field_70155_l = 40.0D;
/*     */   }
/*     */   
/*     */   public EntityCustomArrow(World par1World, EntityLivingBase par2EntityLivingBase, float velocity) {
/*  65 */     super(par1World, par2EntityLivingBase, velocity);
/*  66 */     this.field_70155_l = 40.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  71 */     super.func_70088_a();
/*  72 */     this.field_70180_af.func_75682_a(17, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70186_c(double par1, double par3, double par5, float par7, float par8) {
/*  78 */     float f2 = MathHelper.func_76133_a(par1 * par1 + par3 * par3 + par5 * par5);
/*  79 */     par1 /= f2;
/*  80 */     par3 /= f2;
/*  81 */     par5 /= f2;
/*  82 */     par1 += this.field_70146_Z.nextGaussian() * (this.field_70146_Z.nextBoolean() ? -1 : true) * 7.499999832361937E-4D * par8;
/*  83 */     par3 += this.field_70146_Z.nextGaussian() * (this.field_70146_Z.nextBoolean() ? -1 : true) * 7.499999832361937E-4D * par8;
/*  84 */     par5 += this.field_70146_Z.nextGaussian() * (this.field_70146_Z.nextBoolean() ? -1 : true) * 7.499999832361937E-4D * par8;
/*  85 */     par1 *= par7;
/*  86 */     par3 *= par7;
/*  87 */     par5 *= par7;
/*  88 */     this.field_70159_w = par1;
/*  89 */     this.field_70181_x = par3;
/*  90 */     this.field_70179_y = par5;
/*  91 */     float f3 = MathHelper.func_76133_a(par1 * par1 + par5 * par5);
/*  92 */     this.field_70126_B = this.field_70177_z = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
/*  93 */     this.field_70127_C = this.field_70125_A = (float)(Math.atan2(par3, f3) * 180.0D / Math.PI);
/*  94 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 101 */     func_70030_z();
/* 102 */     if (this.field_70170_p.field_72995_K)
/* 103 */     { this.bowProperties.energyBolt = (this.field_70180_af.func_75683_a(17) == 1); }
/* 104 */     else { this.field_70180_af.func_75692_b(17, Byte.valueOf((byte)(this.bowProperties.energyBolt ? 1 : 0))); }
/*     */ 
/*     */     
/* 107 */     if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
/* 108 */       float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 109 */       this.field_70126_B = this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / Math.PI);
/* 110 */       this.field_70127_C = this.field_70125_A = (float)(Math.atan2(this.field_70181_x, f) * 180.0D / Math.PI);
/*     */     } 
/*     */     
/* 113 */     if (this.field_70249_b > 0) {
/* 114 */       this.field_70249_b--;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 119 */     Block block = this.field_70170_p.func_147439_a(this.blockX, this.blockY, this.blockZ);
/*     */     
/* 121 */     if (block.func_149688_o() != Material.field_151579_a) {
/* 122 */       onHitAnything();
/* 123 */       block.func_149719_a((IBlockAccess)this.field_70170_p, this.blockX, this.blockY, this.blockZ);
/* 124 */       AxisAlignedBB axisalignedbb = block.func_149668_a(this.field_70170_p, this.blockX, this.blockY, this.blockZ);
/*     */       
/* 126 */       if (axisalignedbb != null && axisalignedbb.func_72318_a(Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v))) {
/* 127 */         this.inGround = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 132 */     if (this.inGround) {
/* 133 */       int j = this.field_70170_p.func_72805_g(this.blockX, this.blockY, this.blockZ);
/*     */       
/* 135 */       if (block == this.blockHit && j == this.inData) {
/* 136 */         this.ticksInGround++;
/*     */         
/* 138 */         if (this.ticksInGround == 1200 || this.bowProperties.energyBolt) {
/* 139 */           func_70106_y();
/*     */         }
/*     */       } else {
/* 142 */         this.inGround = false;
/* 143 */         this.field_70159_w *= (this.field_70146_Z.nextFloat() * 0.2F);
/* 144 */         this.field_70181_x *= (this.field_70146_Z.nextFloat() * 0.2F);
/* 145 */         this.field_70179_y *= (this.field_70146_Z.nextFloat() * 0.2F);
/* 146 */         this.ticksInGround = 0;
/* 147 */         this.ticksInAir = 0;
/*     */       } 
/*     */     } else {
/*     */       
/* 151 */       this.ticksInAir++;
/* 152 */       Vec3 vec31 = Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 153 */       Vec3 vec3 = Vec3.func_72443_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/* 154 */       MovingObjectPosition movingobjectposition = this.field_70170_p.func_147447_a(vec31, vec3, false, true, false);
/* 155 */       vec31 = Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 156 */       vec3 = Vec3.func_72443_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*     */       
/* 158 */       if (movingobjectposition != null) {
/* 159 */         vec3 = Vec3.func_72443_a(movingobjectposition.field_72307_f.field_72450_a, movingobjectposition.field_72307_f.field_72448_b, movingobjectposition.field_72307_f.field_72449_c);
/*     */       }
/*     */       
/* 162 */       Entity entity = null;
/* 163 */       List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, this.field_70121_D.func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_72314_b(1.0D, 1.0D, 1.0D));
/* 164 */       double d0 = 0.0D;
/*     */       
/*     */       int i;
/*     */       
/* 168 */       for (i = 0; i < list.size(); i++) {
/* 169 */         Entity entity1 = list.get(i);
/*     */         
/* 171 */         if (entity1.func_70067_L() && (entity1 != this.field_70250_c || this.ticksInAir >= 5)) {
/* 172 */           float f = 0.3F;
/* 173 */           AxisAlignedBB axisalignedbb1 = entity1.field_70121_D.func_72314_b(f, f, f);
/* 174 */           MovingObjectPosition movingobjectposition1 = axisalignedbb1.func_72327_a(vec31, vec3);
/*     */           
/* 176 */           if (movingobjectposition1 != null) {
/* 177 */             double d1 = vec31.func_72438_d(movingobjectposition1.field_72307_f);
/*     */             
/* 179 */             if (d1 < d0 || d0 == 0.0D) {
/* 180 */               entity = entity1;
/* 181 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 187 */       if (entity != null) {
/* 188 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 191 */       if (movingobjectposition != null && movingobjectposition.field_72308_g instanceof EntityPlayer) {
/* 192 */         EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.field_72308_g;
/*     */         
/* 194 */         if (entityplayer.field_71075_bZ.field_75102_a || (this.field_70250_c instanceof EntityPlayer && !((EntityPlayer)this.field_70250_c).func_96122_a(entityplayer))) {
/* 195 */           movingobjectposition = null;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 204 */       if (movingobjectposition != null) {
/* 205 */         if (movingobjectposition.field_72308_g != null) {
/* 206 */           onHitAnything();
/* 207 */           if (this.field_70128_L) {
/*     */             return;
/*     */           }
/* 210 */           float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
/* 211 */           int actualDamage = MathHelper.func_76143_f((f * this.bowProperties.arrowDamage));
/*     */           
/* 213 */           if (this.bowProperties.energyBolt) actualDamage = (int)(actualDamage * 1.1F);
/*     */           
/* 215 */           actualDamage = Math.max(actualDamage, 1);
/*     */           
/* 217 */           if (func_70241_g()) {
/* 218 */             actualDamage += this.field_70146_Z.nextInt(actualDamage / 2 + 2);
/*     */           }
/*     */           
/* 221 */           if (func_70027_ad() && !(movingobjectposition.field_72308_g instanceof net.minecraft.entity.monster.EntityEnderman)) {
/* 222 */             movingobjectposition.field_72308_g.func_70015_d(5);
/*     */           }
/*     */           
/* 225 */           if (this.bowProperties.energyBolt) movingobjectposition.field_72308_g.field_70172_ad = 0; 
/* 226 */           if (movingobjectposition.field_72308_g instanceof EntityDragonPart && ((EntityDragonPart)movingobjectposition.field_72308_g).field_70259_a instanceof EntityDragon && this.bowProperties.energyBolt) {
/* 227 */             ((EntityDragon)((EntityDragonPart)movingobjectposition.field_72308_g).field_70259_a).field_70172_ad = 0;
/*     */           }
/*     */           
/* 230 */           if (movingobjectposition.field_72308_g.func_70097_a(getDamageSource(), actualDamage)) {
/* 231 */             if (movingobjectposition.field_72308_g instanceof EntityLivingBase) {
/* 232 */               EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.field_72308_g;
/*     */               
/* 234 */               if (!this.field_70170_p.field_72995_K) {
/* 235 */                 entitylivingbase.func_85034_r(entitylivingbase.func_85035_bI() + 1);
/*     */               }
/*     */               
/* 238 */               if (this.field_70256_ap > 0) {
/* 239 */                 float f4 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*     */                 
/* 241 */                 if (f4 > 0.0F) {
/* 242 */                   movingobjectposition.field_72308_g.func_70024_g(this.field_70159_w * this.field_70256_ap * 0.6000000238418579D / f4, 0.1D, this.field_70179_y * this.field_70256_ap * 0.6000000238418579D / f4);
/*     */                 }
/*     */               } 
/*     */               
/* 246 */               if (this.field_70250_c != null && this.field_70250_c instanceof EntityLivingBase) {
/* 247 */                 EnchantmentHelper.func_151384_a(entitylivingbase, this.field_70250_c);
/* 248 */                 EnchantmentHelper.func_151385_b((EntityLivingBase)this.field_70250_c, (Entity)entitylivingbase);
/*     */               } 
/*     */               
/* 251 */               if (this.field_70250_c != null && movingobjectposition.field_72308_g != this.field_70250_c && movingobjectposition.field_72308_g instanceof EntityPlayer && this.field_70250_c instanceof EntityPlayerMP) {
/* 252 */                 ((EntityPlayerMP)this.field_70250_c).field_71135_a.func_147359_a((Packet)new S2BPacketChangeGameState(6, 0.0F));
/*     */               }
/*     */             } 
/*     */             
/* 256 */             func_85030_a("random.bowhit", 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
/* 257 */             func_70106_y();
/*     */           
/*     */           }
/* 260 */           else if (!this.field_70170_p.field_72995_K || this.ticksInAir >= 5) {
/* 261 */             this.field_70159_w *= -0.10000000149011612D;
/* 262 */             this.field_70181_x *= -0.10000000149011612D;
/* 263 */             this.field_70179_y *= -0.10000000149011612D;
/* 264 */             this.field_70177_z += 180.0F;
/* 265 */             this.field_70126_B += 180.0F;
/* 266 */             this.ticksInAir = 0;
/*     */           } 
/*     */         } else {
/*     */           
/* 270 */           this.blockX = movingobjectposition.field_72311_b;
/* 271 */           this.blockY = movingobjectposition.field_72312_c;
/* 272 */           this.blockZ = movingobjectposition.field_72309_d;
/* 273 */           block = this.field_70170_p.func_147439_a(this.blockX, this.blockY, this.blockZ);
/* 274 */           this.blockHit = block;
/* 275 */           this.inData = this.field_70170_p.func_72805_g(this.blockX, this.blockY, this.blockZ);
/* 276 */           this.field_70159_w = (float)(movingobjectposition.field_72307_f.field_72450_a - this.field_70165_t);
/* 277 */           this.field_70181_x = (float)(movingobjectposition.field_72307_f.field_72448_b - this.field_70163_u);
/* 278 */           this.field_70179_y = (float)(movingobjectposition.field_72307_f.field_72449_c - this.field_70161_v);
/* 279 */           float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
/* 280 */           this.field_70165_t -= this.field_70159_w / f * 0.05000000074505806D;
/* 281 */           this.field_70163_u -= this.field_70181_x / f * 0.05000000074505806D;
/* 282 */           this.field_70161_v -= this.field_70179_y / f * 0.05000000074505806D;
/* 283 */           func_85030_a("random.bowhit", 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
/* 284 */           this.inGround = true;
/* 285 */           this.field_70249_b = 7;
/*     */           
/* 287 */           if (this.blockHit.func_149688_o() != Material.field_151579_a) {
/* 288 */             this.blockHit.func_149670_a(this.field_70170_p, this.blockX, this.blockY, this.blockZ, (Entity)this);
/*     */           }
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 295 */       if ((func_70241_g() || this.bowProperties.energyBolt) && this.field_70170_p.field_72995_K) {
/* 296 */         for (i = 0; i < 4; i++) {
/* 297 */           if (this.bowProperties.energyBolt) {
/* 298 */             spawnArrowParticles();
/*     */           } else {
/* 300 */             this.field_70170_p.func_72869_a("crit", this.field_70165_t + this.field_70159_w * i / 4.0D, this.field_70163_u + this.field_70181_x * i / 4.0D, this.field_70161_v + this.field_70179_y * i / 4.0D, -this.field_70159_w, -this.field_70181_x + 0.2D, -this.field_70179_y);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 305 */       this.field_70165_t += this.field_70159_w;
/* 306 */       this.field_70163_u += this.field_70181_x;
/* 307 */       this.field_70161_v += this.field_70179_y;
/* 308 */       float velocity = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 309 */       this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / Math.PI);
/*     */       
/* 311 */       for (this.field_70125_A = (float)(Math.atan2(this.field_70181_x, velocity) * 180.0D / Math.PI); this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F);
/*     */ 
/*     */       
/* 314 */       while (this.field_70125_A - this.field_70127_C >= 180.0F) {
/* 315 */         this.field_70127_C += 360.0F;
/*     */       }
/*     */       
/* 318 */       while (this.field_70177_z - this.field_70126_B < -180.0F) {
/* 319 */         this.field_70126_B -= 360.0F;
/*     */       }
/*     */       
/* 322 */       while (this.field_70177_z - this.field_70126_B >= 180.0F) {
/* 323 */         this.field_70126_B += 360.0F;
/*     */       }
/*     */       
/* 326 */       this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2F;
/* 327 */       this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
/* 328 */       float f3 = 0.99F;
/* 329 */       float f1 = this.bowProperties.energyBolt ? 0.025F : 0.05F;
/*     */       
/* 331 */       if (func_70090_H()) {
/* 332 */         for (int l = 0; l < 4; l++) {
/* 333 */           float f4 = 0.25F;
/* 334 */           this.field_70170_p.func_72869_a("bubble", this.field_70165_t - this.field_70159_w * f4, this.field_70163_u - this.field_70181_x * f4, this.field_70161_v - this.field_70179_y * f4, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */         } 
/*     */         
/* 337 */         f3 = 0.8F;
/*     */       } 
/*     */       
/* 340 */       if (func_70026_G()) {
/* 341 */         func_70066_B();
/*     */       }
/*     */       
/* 344 */       this.field_70159_w *= f3;
/* 345 */       this.field_70181_x *= f3;
/* 346 */       this.field_70179_y *= f3;
/* 347 */       this.field_70181_x -= f1;
/* 348 */       func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 349 */       func_145775_I();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private void spawnArrowParticles() {
/* 356 */     Particles.ArrowParticle particle = new Particles.ArrowParticle(this.field_70170_p, this.field_70165_t - 0.25D + this.field_70146_Z.nextDouble() * 0.5D, this.field_70163_u + this.field_70146_Z.nextDouble() * 0.5D, this.field_70161_v - 0.25D + this.field_70146_Z.nextDouble() * 0.5D, 16736256, 0.2F + this.field_70146_Z.nextFloat() * 0.5F);
/* 357 */     double mm = 0.2D;
/* 358 */     particle.field_70159_w = (this.field_70146_Z.nextDouble() - 0.5D) * mm;
/* 359 */     particle.field_70181_x = (this.field_70146_Z.nextDouble() - 0.5D) * mm;
/* 360 */     particle.field_70179_y = (this.field_70146_Z.nextDouble() - 0.5D) * mm;
/* 361 */     ParticleHandler.spawnCustomParticle((EntityFX)particle, 64.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70014_b(NBTTagCompound compound) {
/* 366 */     compound.func_74777_a("xTile", (short)this.blockX);
/* 367 */     compound.func_74777_a("yTile", (short)this.blockY);
/* 368 */     compound.func_74777_a("zTile", (short)this.blockZ);
/* 369 */     compound.func_74777_a("life", (short)this.ticksInGround);
/* 370 */     compound.func_74774_a("inTile", (byte)Block.func_149682_b(this.blockHit));
/* 371 */     compound.func_74774_a("inData", (byte)this.inData);
/* 372 */     compound.func_74774_a("shake", (byte)this.field_70249_b);
/* 373 */     compound.func_74774_a("inGround", (byte)(this.inGround ? 1 : 0));
/* 374 */     compound.func_74774_a("pickup", (byte)this.field_70251_a);
/* 375 */     if (this.bowProperties != null) this.bowProperties.writeToNBT(compound);
/*     */   
/*     */   }
/*     */   
/*     */   public void func_70037_a(NBTTagCompound compound) {
/* 380 */     this.blockX = compound.func_74765_d("xTile");
/* 381 */     this.blockY = compound.func_74765_d("yTile");
/* 382 */     this.blockZ = compound.func_74765_d("zTile");
/* 383 */     this.ticksInGround = compound.func_74765_d("life");
/* 384 */     this.blockHit = Block.func_149729_e(compound.func_74771_c("inTile") & 0xFF);
/* 385 */     this.inData = compound.func_74771_c("inData") & 0xFF;
/* 386 */     this.field_70249_b = compound.func_74771_c("shake") & 0xFF;
/* 387 */     this.inGround = (compound.func_74771_c("inGround") == 1);
/*     */     
/* 389 */     if (compound.func_150297_b("pickup", 99)) {
/* 390 */       this.field_70251_a = compound.func_74771_c("pickup");
/* 391 */     } else if (compound.func_150297_b("player", 99)) {
/* 392 */       this.field_70251_a = compound.func_74767_n("player") ? 1 : 0;
/*     */     } 
/*     */     
/* 395 */     if (this.bowProperties != null) this.bowProperties.readFromNBT(compound);
/*     */   
/*     */   }
/*     */   
/*     */   public void func_70100_b_(EntityPlayer par1EntityPlayer) {
/* 400 */     if (!this.field_70170_p.field_72995_K && this.inGround && this.field_70249_b <= 0) {
/*     */       
/* 402 */       boolean flag = (this.field_70251_a == 1 || (this.field_70251_a == 2 && par1EntityPlayer.field_71075_bZ.field_75098_d));
/*     */       
/* 404 */       if (this.field_70251_a == 1 && !par1EntityPlayer.field_71071_by.func_70441_a(new ItemStack(Items.field_151032_g, 1))) {
/* 405 */         flag = false;
/*     */       }
/*     */       
/* 408 */       if (flag) {
/* 409 */         func_85030_a("random.pop", 0.2F, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 410 */         par1EntityPlayer.func_71001_a((Entity)this, 1);
/* 411 */         func_70106_y();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onHitEntityLiving(EntityLivingBase entityLivingBase) {}
/*     */   
/*     */   public void onHitAnything() {
/* 420 */     if (this.bowProperties.explosionPower > 0.0F && !this.field_70170_p.field_72995_K) {
/* 421 */       this.field_70170_p.func_72876_a((Entity)this, this.field_70169_q, this.field_70167_r, this.field_70166_s, this.bowProperties.explosionPower, ConfigHandler.bowBlockDamage);
/* 422 */       func_70106_y();
/*     */     } 
/* 424 */     if (this.bowProperties.shockWavePower > 0.0F && !this.field_70170_p.field_72995_K) {
/* 425 */       DraconicEvolution.network.sendToAllAround((IMessage)new GenericParticlePacket((byte)4, this.field_70165_t, this.field_70163_u, this.field_70161_v, (int)(this.bowProperties.shockWavePower * 100.0F)), new NetworkRegistry.TargetPoint(this.field_71093_bK, this.field_70165_t, this.field_70163_u, this.field_70161_v, 256.0D));
/* 426 */       this.field_70170_p.func_72908_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, "random.explode", 4.0F, (1.0F + (this.field_70170_p.field_73012_v.nextFloat() - this.field_70170_p.field_73012_v.nextFloat()) * 0.2F) * 0.7F);
/*     */       
/* 428 */       double range = this.bowProperties.shockWavePower + 5.0D;
/* 429 */       List<Entity> list = this.field_70170_p.func_72872_a(Entity.class, this.field_70121_D.func_72314_b(range, range, range));
/*     */       
/* 431 */       float damage = 40.0F * this.bowProperties.shockWavePower;
/*     */       
/* 433 */       for (Entity e : list) {
/* 434 */         if (e instanceof EntityLivingBase) {
/* 435 */           EntityDragonPart entityDragonPart; Entity entity = e;
/* 436 */           float distanceModifier = 1.0F - entity.func_70032_d((Entity)this) / this.bowProperties.shockWavePower;
/*     */           
/* 438 */           if (e instanceof EntityDragon) {
/* 439 */             entityDragonPart = ((EntityDragon)entity).field_70987_i;
/* 440 */             distanceModifier = 1.0F - entityDragonPart.func_70032_d((Entity)this) / this.bowProperties.shockWavePower * 4.0F;
/*     */           } 
/*     */           
/* 443 */           if (distanceModifier > 0.0F) {
/* 444 */             entityDragonPart.func_70097_a(getDamageSource(), distanceModifier * damage);
/*     */           }
/*     */         } 
/*     */       } 
/* 448 */       func_70106_y();
/*     */     } 
/*     */   }
/*     */   
/*     */   private DamageSource getDamageSource() {
/* 453 */     if (this.bowProperties.energyBolt)
/* 454 */       return (new EntityDamageSourceIndirect("customArrowEnergy", (Entity)this, (this.field_70250_c != null) ? this.field_70250_c : (Entity)this)).func_76349_b().func_151518_m(); 
/* 455 */     return DamageSource.func_76353_a(this, (this.field_70250_c != null) ? this.field_70250_c : (Entity)this);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\entity\EntityCustomArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */