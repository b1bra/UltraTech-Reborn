/*     */ package com.brandon3055.draconicevolution.common.entity;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.EntityDragonPart;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityDraconicArrow extends EntityArrow {
/*  27 */   private int blockX = -1;
/*  28 */   private int blockY = -1;
/*  29 */   private int blockZ = -1;
/*     */   private Block blockHit;
/*     */   private int inData;
/*     */   private boolean inGround;
/*     */   private int ticksInGround;
/*     */   private int ticksInAir;
/*  35 */   private double damage = 2.0D;
/*     */   
/*     */   private int knockbackStrength;
/*     */   
/*     */   public boolean ignorSpeed = false;
/*     */   
/*     */   public boolean explosive = false;
/*     */   
/*     */   public EntityDraconicArrow(World p_i1753_1_) {
/*  44 */     super(p_i1753_1_);
/*     */   }
/*     */   
/*     */   public EntityDraconicArrow(World p_i1754_1_, double p_i1754_2_, double p_i1754_4_, double p_i1754_6_) {
/*  48 */     super(p_i1754_1_, p_i1754_2_, p_i1754_4_, p_i1754_6_);
/*     */   }
/*     */   
/*     */   public EntityDraconicArrow(World p_i1755_1_, EntityLivingBase p_i1755_2_, EntityLivingBase p_i1755_3_, float p_i1755_4_, float p_i1755_5_) {
/*  52 */     super(p_i1755_1_, p_i1755_2_, p_i1755_3_, p_i1755_4_, p_i1755_5_);
/*     */   }
/*     */   
/*     */   public EntityDraconicArrow(World par1World, EntityLivingBase par2EntityLivingBase, float par3) {
/*  56 */     super(par1World, par2EntityLivingBase, par3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  61 */     super.func_70088_a();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70186_c(double par1, double par3, double par5, float par7, float par8) {
/*  67 */     float f2 = MathHelper.func_76133_a(par1 * par1 + par3 * par3 + par5 * par5);
/*  68 */     par1 /= f2;
/*  69 */     par3 /= f2;
/*  70 */     par5 /= f2;
/*  71 */     par1 += this.field_70146_Z.nextGaussian() * (this.field_70146_Z.nextBoolean() ? -1 : true) * 0.007499999832361937D * par8;
/*  72 */     par3 += this.field_70146_Z.nextGaussian() * (this.field_70146_Z.nextBoolean() ? -1 : true) * 0.007499999832361937D * par8;
/*  73 */     par5 += this.field_70146_Z.nextGaussian() * (this.field_70146_Z.nextBoolean() ? -1 : true) * 0.007499999832361937D * par8;
/*  74 */     par1 *= par7;
/*  75 */     par3 *= par7;
/*  76 */     par5 *= par7;
/*  77 */     this.field_70159_w = par1;
/*  78 */     this.field_70181_x = par3;
/*  79 */     this.field_70179_y = par5;
/*  80 */     float f3 = MathHelper.func_76133_a(par1 * par1 + par5 * par5);
/*  81 */     this.field_70126_B = this.field_70177_z = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
/*  82 */     this.field_70127_C = this.field_70125_A = (float)(Math.atan2(par3, f3) * 180.0D / Math.PI);
/*  83 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70056_a(double par1, double par3, double par5, float par7, float par8, int par9) {
/*  89 */     func_70107_b(par1, par3, par5);
/*  90 */     func_70101_b(par7, par8);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70016_h(double par1, double par3, double par5) {
/*  96 */     this.field_70159_w = par1;
/*  97 */     this.field_70181_x = par3;
/*  98 */     this.field_70179_y = par5;
/*     */     
/* 100 */     if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
/* 101 */       float f = MathHelper.func_76133_a(par1 * par1 + par5 * par5);
/* 102 */       this.field_70126_B = this.field_70177_z = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
/* 103 */       this.field_70127_C = this.field_70125_A = (float)(Math.atan2(par3, f) * 180.0D / Math.PI);
/* 104 */       this.field_70127_C = this.field_70125_A;
/* 105 */       this.field_70126_B = this.field_70177_z;
/* 106 */       func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
/* 107 */       this.ticksInGround = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 115 */     func_70030_z();
/* 116 */     if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
/* 117 */       float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 118 */       this.field_70126_B = this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / Math.PI);
/* 119 */       this.field_70127_C = this.field_70125_A = (float)(Math.atan2(this.field_70181_x, f) * 180.0D / Math.PI);
/*     */     } 
/*     */     
/* 122 */     Block block = this.field_70170_p.func_147439_a(this.blockX, this.blockY, this.blockZ);
/*     */     
/* 124 */     if (block.func_149688_o() != Material.field_151579_a) {
/* 125 */       if (this.explosive) {
/* 126 */         this.field_70170_p.func_72876_a(this.field_70250_c, this.field_70169_q, this.field_70167_r, this.field_70166_s, 4.0F, ConfigHandler.bowBlockDamage);
/* 127 */         func_70106_y();
/*     */         return;
/*     */       } 
/* 130 */       block.func_149719_a((IBlockAccess)this.field_70170_p, this.blockX, this.blockY, this.blockZ);
/* 131 */       AxisAlignedBB axisalignedbb = block.func_149668_a(this.field_70170_p, this.blockX, this.blockY, this.blockZ);
/*     */       
/* 133 */       if (axisalignedbb != null && axisalignedbb.func_72318_a(Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v))) {
/* 134 */         this.inGround = true;
/*     */       }
/*     */     } 
/*     */     
/* 138 */     if (this.field_70249_b > 0) {
/* 139 */       this.field_70249_b--;
/*     */     }
/*     */     
/* 142 */     if (this.inGround) {
/* 143 */       int j = this.field_70170_p.func_72805_g(this.blockX, this.blockY, this.blockZ);
/*     */       
/* 145 */       if (block == this.blockHit && j == this.inData) {
/* 146 */         this.ticksInGround++;
/*     */         
/* 148 */         if (this.ticksInGround == 1200) {
/* 149 */           func_70106_y();
/*     */         }
/*     */       } else {
/* 152 */         this.inGround = false;
/* 153 */         this.field_70159_w *= (this.field_70146_Z.nextFloat() * 0.2F);
/* 154 */         this.field_70181_x *= (this.field_70146_Z.nextFloat() * 0.2F);
/* 155 */         this.field_70179_y *= (this.field_70146_Z.nextFloat() * 0.2F);
/* 156 */         this.ticksInGround = 0;
/* 157 */         this.ticksInAir = 0;
/*     */       } 
/*     */     } else {
/* 160 */       this.ticksInAir++;
/* 161 */       Vec3 vec31 = Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 162 */       Vec3 vec3 = Vec3.func_72443_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/* 163 */       MovingObjectPosition movingobjectposition = this.field_70170_p.func_147447_a(vec31, vec3, false, true, false);
/* 164 */       vec31 = Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 165 */       vec3 = Vec3.func_72443_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*     */       
/* 167 */       if (movingobjectposition != null) {
/* 168 */         vec3 = Vec3.func_72443_a(movingobjectposition.field_72307_f.field_72450_a, movingobjectposition.field_72307_f.field_72448_b, movingobjectposition.field_72307_f.field_72449_c);
/*     */       }
/*     */ 
/*     */       
/* 172 */       Entity entity = null;
/* 173 */       List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, this.field_70121_D.func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_72314_b(1.0D, 1.0D, 1.0D));
/* 174 */       double d0 = 0.0D;
/*     */       
/*     */       int i;
/*     */       
/* 178 */       for (i = 0; i < list.size(); i++) {
/* 179 */         Entity entity1 = list.get(i);
/*     */         
/* 181 */         if (entity1.func_70067_L() && (entity1 != this.field_70250_c || this.ticksInAir >= 5)) {
/* 182 */           float f = 0.3F;
/* 183 */           AxisAlignedBB axisalignedbb1 = entity1.field_70121_D.func_72314_b(f, f, f);
/* 184 */           MovingObjectPosition movingobjectposition1 = axisalignedbb1.func_72327_a(vec31, vec3);
/*     */           
/* 186 */           if (movingobjectposition1 != null) {
/* 187 */             double d1 = vec31.func_72438_d(movingobjectposition1.field_72307_f);
/*     */             
/* 189 */             if (d1 < d0 || d0 == 0.0D) {
/* 190 */               entity = entity1;
/* 191 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 197 */       if (entity != null) {
/* 198 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 201 */       if (movingobjectposition != null && movingobjectposition.field_72308_g instanceof EntityPlayer) {
/* 202 */         EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.field_72308_g;
/*     */         
/* 204 */         if (entityplayer.field_71075_bZ.field_75102_a || (this.field_70250_c instanceof EntityPlayer && !((EntityPlayer)this.field_70250_c).func_96122_a(entityplayer))) {
/* 205 */           movingobjectposition = null;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 212 */       if (movingobjectposition != null) {
/* 213 */         if (movingobjectposition.field_72308_g != null) {
/* 214 */           int k; if (this.explosive) {
/*     */ 
/*     */             
/* 217 */             this.field_70170_p.func_72876_a(this.field_70250_c, movingobjectposition.field_72308_g.field_70165_t, movingobjectposition.field_72308_g.field_70163_u, movingobjectposition.field_72308_g.field_70161_v, 4.0F, ConfigHandler.bowBlockDamage);
/* 218 */             func_70106_y();
/*     */           } 
/*     */           
/* 221 */           if (!this.ignorSpeed) {
/* 222 */             float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
/* 223 */             k = MathHelper.func_76143_f(f * this.damage);
/*     */           } else {
/*     */             
/* 226 */             k = (int)this.damage;
/*     */           } 
/* 228 */           if (func_70241_g()) {
/* 229 */             k += this.field_70146_Z.nextInt(k / 2 + 2);
/*     */           }
/*     */           
/* 232 */           DamageSource damagesource = null;
/*     */           
/* 234 */           if (this.field_70250_c == null) {
/* 235 */             damagesource = DamageSource.func_76353_a(this, (Entity)this);
/*     */           } else {
/* 237 */             damagesource = DamageSource.func_76353_a(this, this.field_70250_c);
/*     */           } 
/* 239 */           if (movingobjectposition.field_72308_g instanceof net.minecraft.entity.monster.EntityEnderman) damagesource = DamageSource.field_76376_m;
/*     */           
/* 241 */           if (func_70027_ad() && !(movingobjectposition.field_72308_g instanceof net.minecraft.entity.monster.EntityEnderman)) {
/* 242 */             movingobjectposition.field_72308_g.func_70015_d(5);
/*     */           }
/*     */           
/* 245 */           movingobjectposition.field_72308_g.field_70172_ad = 0;
/* 246 */           if (movingobjectposition.field_72308_g instanceof EntityDragonPart && ((EntityDragonPart)movingobjectposition.field_72308_g).field_70259_a instanceof EntityDragon) {
/* 247 */             ((EntityDragon)((EntityDragonPart)movingobjectposition.field_72308_g).field_70259_a).field_70172_ad = 0;
/*     */           }
/*     */           
/* 250 */           if (movingobjectposition.field_72308_g.func_70097_a(damagesource, k)) {
/* 251 */             if (movingobjectposition.field_72308_g instanceof EntityLivingBase) {
/* 252 */               EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.field_72308_g;
/*     */               
/* 254 */               if (!this.field_70170_p.field_72995_K) {
/* 255 */                 entitylivingbase.func_85034_r(entitylivingbase.func_85035_bI() + 1);
/*     */               }
/*     */               
/* 258 */               if (this.knockbackStrength > 0) {
/* 259 */                 float f4 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*     */                 
/* 261 */                 if (f4 > 0.0F) {
/* 262 */                   movingobjectposition.field_72308_g.func_70024_g(this.field_70159_w * this.knockbackStrength * 0.6000000238418579D / f4, 0.1D, this.field_70179_y * this.knockbackStrength * 0.6000000238418579D / f4);
/*     */                 }
/*     */               } 
/*     */               
/* 266 */               if (this.field_70250_c != null && this.field_70250_c instanceof EntityLivingBase) {
/* 267 */                 EnchantmentHelper.func_151384_a(entitylivingbase, this.field_70250_c);
/* 268 */                 EnchantmentHelper.func_151385_b((EntityLivingBase)this.field_70250_c, (Entity)entitylivingbase);
/*     */               } 
/*     */               
/* 271 */               if (this.field_70250_c != null && movingobjectposition.field_72308_g != this.field_70250_c && movingobjectposition.field_72308_g instanceof EntityPlayer && this.field_70250_c instanceof EntityPlayerMP) {
/* 272 */                 ((EntityPlayerMP)this.field_70250_c).field_71135_a.func_147359_a((Packet)new S2BPacketChangeGameState(6, 0.0F));
/*     */               }
/*     */             } 
/*     */             
/* 276 */             func_85030_a("random.bowhit", 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
/*     */ 
/*     */ 
/*     */             
/* 280 */             func_70106_y();
/*     */           } else {
/*     */             
/* 283 */             this.field_70159_w *= -0.10000000149011612D;
/* 284 */             this.field_70181_x *= -0.10000000149011612D;
/* 285 */             this.field_70179_y *= -0.10000000149011612D;
/* 286 */             this.field_70177_z += 180.0F;
/* 287 */             this.field_70126_B += 180.0F;
/* 288 */             this.ticksInAir = 0;
/*     */           } 
/*     */         } else {
/* 291 */           this.blockX = movingobjectposition.field_72311_b;
/* 292 */           this.blockY = movingobjectposition.field_72312_c;
/* 293 */           this.blockZ = movingobjectposition.field_72309_d;
/* 294 */           block = this.field_70170_p.func_147439_a(this.blockX, this.blockY, this.blockZ);
/* 295 */           this.blockHit = block;
/* 296 */           this.inData = this.field_70170_p.func_72805_g(this.blockX, this.blockY, this.blockZ);
/* 297 */           this.field_70159_w = (float)(movingobjectposition.field_72307_f.field_72450_a - this.field_70165_t);
/* 298 */           this.field_70181_x = (float)(movingobjectposition.field_72307_f.field_72448_b - this.field_70163_u);
/* 299 */           this.field_70179_y = (float)(movingobjectposition.field_72307_f.field_72449_c - this.field_70161_v);
/* 300 */           float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
/* 301 */           this.field_70165_t -= this.field_70159_w / f * 0.05000000074505806D;
/* 302 */           this.field_70163_u -= this.field_70181_x / f * 0.05000000074505806D;
/* 303 */           this.field_70161_v -= this.field_70179_y / f * 0.05000000074505806D;
/* 304 */           func_85030_a("random.bowhit", 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
/* 305 */           this.inGround = true;
/* 306 */           this.field_70249_b = 7;
/*     */           
/* 308 */           if (this.blockHit.func_149688_o() != Material.field_151579_a) {
/* 309 */             this.blockHit.func_149670_a(this.field_70170_p, this.blockX, this.blockY, this.blockZ, (Entity)this);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 314 */       if (func_70241_g()) {
/* 315 */         for (i = 0; i < 4; i++) {
/* 316 */           this.field_70170_p.func_72869_a("crit", this.field_70165_t + this.field_70159_w * i / 4.0D, this.field_70163_u + this.field_70181_x * i / 4.0D, this.field_70161_v + this.field_70179_y * i / 4.0D, -this.field_70159_w, -this.field_70181_x + 0.2D, -this.field_70179_y);
/*     */         }
/*     */       }
/*     */       
/* 320 */       this.field_70165_t += this.field_70159_w;
/* 321 */       this.field_70163_u += this.field_70181_x;
/* 322 */       this.field_70161_v += this.field_70179_y;
/* 323 */       float f2 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 324 */       this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / Math.PI);
/*     */       
/* 326 */       for (this.field_70125_A = (float)(Math.atan2(this.field_70181_x, f2) * 180.0D / Math.PI); this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F);
/*     */ 
/*     */       
/* 329 */       while (this.field_70125_A - this.field_70127_C >= 180.0F) {
/* 330 */         this.field_70127_C += 360.0F;
/*     */       }
/*     */       
/* 333 */       while (this.field_70177_z - this.field_70126_B < -180.0F) {
/* 334 */         this.field_70126_B -= 360.0F;
/*     */       }
/*     */       
/* 337 */       while (this.field_70177_z - this.field_70126_B >= 180.0F) {
/* 338 */         this.field_70126_B += 360.0F;
/*     */       }
/*     */       
/* 341 */       this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2F;
/* 342 */       this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
/* 343 */       float f3 = 0.99F;
/* 344 */       float f1 = 0.05F;
/*     */       
/* 346 */       if (func_70090_H()) {
/* 347 */         for (int l = 0; l < 4; l++) {
/* 348 */           float f4 = 0.25F;
/* 349 */           this.field_70170_p.func_72869_a("bubble", this.field_70165_t - this.field_70159_w * f4, this.field_70163_u - this.field_70181_x * f4, this.field_70161_v - this.field_70179_y * f4, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */         } 
/*     */         
/* 352 */         f3 = 0.8F;
/*     */       } 
/*     */       
/* 355 */       if (func_70026_G()) {
/* 356 */         func_70066_B();
/*     */       }
/*     */       
/* 359 */       this.field_70159_w *= f3;
/* 360 */       this.field_70181_x *= f3;
/* 361 */       this.field_70179_y *= f3;
/* 362 */       this.field_70181_x -= f1;
/* 363 */       func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 364 */       func_145775_I();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/* 370 */     par1NBTTagCompound.func_74777_a("xTile", (short)this.blockX);
/* 371 */     par1NBTTagCompound.func_74777_a("yTile", (short)this.blockY);
/* 372 */     par1NBTTagCompound.func_74777_a("zTile", (short)this.blockZ);
/* 373 */     par1NBTTagCompound.func_74777_a("life", (short)this.ticksInGround);
/* 374 */     par1NBTTagCompound.func_74774_a("inTile", (byte)Block.func_149682_b(this.blockHit));
/* 375 */     par1NBTTagCompound.func_74774_a("inData", (byte)this.inData);
/* 376 */     par1NBTTagCompound.func_74774_a("shake", (byte)this.field_70249_b);
/* 377 */     par1NBTTagCompound.func_74774_a("inGround", (byte)(this.inGround ? 1 : 0));
/* 378 */     par1NBTTagCompound.func_74774_a("pickup", (byte)this.field_70251_a);
/* 379 */     par1NBTTagCompound.func_74780_a("damage", this.damage);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/* 385 */     this.blockX = par1NBTTagCompound.func_74765_d("xTile");
/* 386 */     this.blockY = par1NBTTagCompound.func_74765_d("yTile");
/* 387 */     this.blockZ = par1NBTTagCompound.func_74765_d("zTile");
/* 388 */     this.ticksInGround = par1NBTTagCompound.func_74765_d("life");
/* 389 */     this.blockHit = Block.func_149729_e(par1NBTTagCompound.func_74771_c("inTile") & 0xFF);
/* 390 */     this.inData = par1NBTTagCompound.func_74771_c("inData") & 0xFF;
/* 391 */     this.field_70249_b = par1NBTTagCompound.func_74771_c("shake") & 0xFF;
/* 392 */     this.inGround = (par1NBTTagCompound.func_74771_c("inGround") == 1);
/*     */ 
/*     */     
/* 395 */     if (par1NBTTagCompound.func_150297_b("damage", 99)) {
/* 396 */       this.damage = par1NBTTagCompound.func_74769_h("damage");
/*     */     }
/*     */     
/* 399 */     if (par1NBTTagCompound.func_150297_b("pickup", 99)) {
/* 400 */       this.field_70251_a = par1NBTTagCompound.func_74771_c("pickup");
/* 401 */     } else if (par1NBTTagCompound.func_150297_b("player", 99)) {
/* 402 */       this.field_70251_a = par1NBTTagCompound.func_74767_n("player") ? 1 : 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70100_b_(EntityPlayer par1EntityPlayer) {
/* 408 */     if (!this.field_70170_p.field_72995_K && this.inGround && this.field_70249_b <= 0) {
/*     */       
/* 410 */       boolean flag = (this.field_70251_a == 1 || (this.field_70251_a == 2 && par1EntityPlayer.field_71075_bZ.field_75098_d));
/*     */       
/* 412 */       if (this.field_70251_a == 1 && !par1EntityPlayer.field_71071_by.func_70441_a(new ItemStack(Items.field_151032_g, 1))) {
/* 413 */         flag = false;
/*     */       }
/*     */       
/* 416 */       if (flag) {
/* 417 */         func_85030_a("random.pop", 0.2F, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 418 */         par1EntityPlayer.func_71001_a((Entity)this, 1);
/* 419 */         func_70106_y();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_70041_e_() {
/* 426 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float func_70053_R() {
/* 432 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70239_b(double par1) {
/* 437 */     this.damage = par1;
/*     */   }
/*     */ 
/*     */   
/*     */   public double func_70242_d() {
/* 442 */     return this.damage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70240_a(int par1) {
/* 447 */     this.knockbackStrength = par1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70075_an() {
/* 452 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70243_d(boolean par1) {
/* 457 */     byte b0 = this.field_70180_af.func_75683_a(16);
/*     */     
/* 459 */     if (par1) {
/* 460 */       this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     } else {
/* 462 */       this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70241_g() {
/* 468 */     byte b0 = this.field_70180_af.func_75683_a(16);
/* 469 */     return ((b0 & 0x1) != 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\entity\EntityDraconicArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */