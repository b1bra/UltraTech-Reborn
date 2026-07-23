/*     */ package com.brandon3055.draconicevolution.common.entity;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.event.entity.living.EnderTeleportEvent;
/*     */ 
/*     */ public class EntityEnderArrow extends EntityDraconicArrow {
/*  21 */   private int field_145791_d = -1;
/*  22 */   private int field_145792_e = -1;
/*  23 */   private int field_145789_f = -1;
/*     */   private Block field_145790_g;
/*     */   private int inData;
/*     */   private boolean inGround;
/*     */   private int ticksInGround;
/*     */   private int ticksInAir;
/*  29 */   private double damage = 2.0D;
/*     */   
/*     */   private int knockbackStrength;
/*     */   
/*     */   public boolean ignorSpeed = false;
/*     */   
/*     */   public boolean explosive = false;
/*     */   
/*     */   public EntityEnderArrow(World p_i1753_1_) {
/*  38 */     super(p_i1753_1_);
/*     */   }
/*     */   
/*     */   public EntityEnderArrow(World p_i1754_1_, double p_i1754_2_, double p_i1754_4_, double p_i1754_6_) {
/*  42 */     super(p_i1754_1_, p_i1754_2_, p_i1754_4_, p_i1754_6_);
/*     */   }
/*     */   
/*     */   public EntityEnderArrow(World p_i1755_1_, EntityLivingBase p_i1755_2_, EntityLivingBase p_i1755_3_, float p_i1755_4_, float p_i1755_5_) {
/*  46 */     super(p_i1755_1_, p_i1755_2_, p_i1755_3_, p_i1755_4_, p_i1755_5_);
/*     */   }
/*     */   
/*     */   public EntityEnderArrow(World par1World, EntityLivingBase par2EntityLivingBase, float par3) {
/*  50 */     super(par1World, par2EntityLivingBase, par3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  55 */     super.func_70088_a();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70186_c(double par1, double par3, double par5, float par7, float par8) {
/*  61 */     float f2 = MathHelper.func_76133_a(par1 * par1 + par3 * par3 + par5 * par5);
/*  62 */     par1 /= f2;
/*  63 */     par3 /= f2;
/*  64 */     par5 /= f2;
/*  65 */     par1 += this.field_70146_Z.nextGaussian() * (this.field_70146_Z.nextBoolean() ? -1 : true) * 0.007499999832361937D * par8;
/*  66 */     par3 += this.field_70146_Z.nextGaussian() * (this.field_70146_Z.nextBoolean() ? -1 : true) * 0.007499999832361937D * par8;
/*  67 */     par5 += this.field_70146_Z.nextGaussian() * (this.field_70146_Z.nextBoolean() ? -1 : true) * 0.007499999832361937D * par8;
/*  68 */     par1 *= par7;
/*  69 */     par3 *= par7;
/*  70 */     par5 *= par7;
/*  71 */     this.field_70159_w = par1;
/*  72 */     this.field_70181_x = par3;
/*  73 */     this.field_70179_y = par5;
/*  74 */     float f3 = MathHelper.func_76133_a(par1 * par1 + par5 * par5);
/*  75 */     this.field_70126_B = this.field_70177_z = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
/*  76 */     this.field_70127_C = this.field_70125_A = (float)(Math.atan2(par3, f3) * 180.0D / Math.PI);
/*  77 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70056_a(double par1, double par3, double par5, float par7, float par8, int par9) {
/*  83 */     func_70107_b(par1, par3, par5);
/*  84 */     func_70101_b(par7, par8);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70016_h(double par1, double par3, double par5) {
/*  90 */     this.field_70159_w = par1;
/*  91 */     this.field_70181_x = par3;
/*  92 */     this.field_70179_y = par5;
/*     */     
/*  94 */     if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
/*  95 */       float f = MathHelper.func_76133_a(par1 * par1 + par5 * par5);
/*  96 */       this.field_70126_B = this.field_70177_z = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
/*  97 */       this.field_70127_C = this.field_70125_A = (float)(Math.atan2(par3, f) * 180.0D / Math.PI);
/*  98 */       this.field_70127_C = this.field_70125_A;
/*  99 */       this.field_70126_B = this.field_70177_z;
/* 100 */       func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
/* 101 */       this.ticksInGround = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 108 */     for (int i = 0; i < 10; i++) {
/* 109 */       this.field_70170_p.func_72869_a("portal", this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O - 0.25D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D, -this.field_70146_Z.nextDouble(), (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D);
/*     */     }
/* 111 */     func_70030_z();
/* 112 */     if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
/* 113 */       float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 114 */       this.field_70126_B = this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / Math.PI);
/* 115 */       this.field_70127_C = this.field_70125_A = (float)(Math.atan2(this.field_70181_x, f) * 180.0D / Math.PI);
/*     */     } 
/*     */     
/* 118 */     Block block = this.field_70170_p.func_147439_a(this.field_145791_d, this.field_145792_e, this.field_145789_f);
/*     */     
/* 120 */     if (block.func_149688_o() != Material.field_151579_a) {
/* 121 */       onImpact((Entity)null);
/* 122 */       block.func_149719_a((IBlockAccess)this.field_70170_p, this.field_145791_d, this.field_145792_e, this.field_145789_f);
/* 123 */       AxisAlignedBB axisalignedbb = block.func_149668_a(this.field_70170_p, this.field_145791_d, this.field_145792_e, this.field_145789_f);
/*     */       
/* 125 */       if (axisalignedbb != null && axisalignedbb.func_72318_a(Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v))) {
/* 126 */         this.inGround = true;
/*     */       }
/*     */     } 
/*     */     
/* 130 */     if (this.field_70249_b > 0) {
/* 131 */       this.field_70249_b--;
/*     */     }
/*     */     
/* 134 */     if (this.inGround) {
/* 135 */       func_70106_y();
/*     */     } else {
/* 137 */       this.ticksInAir++;
/* 138 */       Vec3 vec31 = Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 139 */       Vec3 vec3 = Vec3.func_72443_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/* 140 */       MovingObjectPosition movingobjectposition = this.field_70170_p.func_147447_a(vec31, vec3, false, true, false);
/* 141 */       vec31 = Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 142 */       vec3 = Vec3.func_72443_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*     */       
/* 144 */       if (movingobjectposition != null) {
/* 145 */         vec3 = Vec3.func_72443_a(movingobjectposition.field_72307_f.field_72450_a, movingobjectposition.field_72307_f.field_72448_b, movingobjectposition.field_72307_f.field_72449_c);
/*     */       }
/*     */       
/* 148 */       Entity entity = null;
/* 149 */       List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, this.field_70121_D.func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_72314_b(1.0D, 1.0D, 1.0D));
/* 150 */       double d0 = 0.0D;
/*     */ 
/*     */ 
/*     */       
/* 154 */       for (int j = 0; j < list.size(); j++) {
/* 155 */         Entity entity1 = list.get(j);
/*     */         
/* 157 */         if (entity1.func_70067_L() && (entity1 != this.field_70250_c || this.ticksInAir >= 5)) {
/* 158 */           float f = 0.3F;
/* 159 */           AxisAlignedBB axisalignedbb1 = entity1.field_70121_D.func_72314_b(f, f, f);
/* 160 */           MovingObjectPosition movingobjectposition1 = axisalignedbb1.func_72327_a(vec31, vec3);
/*     */           
/* 162 */           if (movingobjectposition1 != null) {
/* 163 */             double d1 = vec31.func_72438_d(movingobjectposition1.field_72307_f);
/*     */             
/* 165 */             if (d1 < d0 || d0 == 0.0D) {
/* 166 */               entity = entity1;
/* 167 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 173 */       if (entity == this.field_70250_c) entity = null;
/*     */       
/* 175 */       if (entity != null) {
/* 176 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */ 
/*     */       
/* 180 */       if (movingobjectposition != null && movingobjectposition.field_72308_g instanceof EntityPlayer) {
/* 181 */         EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.field_72308_g;
/*     */         
/* 183 */         if (entityplayer.field_71075_bZ.field_75102_a || (this.field_70250_c instanceof EntityPlayer && !((EntityPlayer)this.field_70250_c).func_96122_a(entityplayer))) {
/* 184 */           movingobjectposition = null;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 191 */       if (movingobjectposition != null)
/*     */       {
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
/* 266 */         if (movingobjectposition.field_72308_g == null) {
/* 267 */           this.field_145791_d = movingobjectposition.field_72311_b;
/* 268 */           this.field_145792_e = movingobjectposition.field_72312_c;
/* 269 */           this.field_145789_f = movingobjectposition.field_72309_d;
/* 270 */           this.field_145790_g = block;
/* 271 */           this.inData = this.field_70170_p.func_72805_g(this.field_145791_d, this.field_145792_e, this.field_145789_f);
/* 272 */           this.field_70159_w = (float)(movingobjectposition.field_72307_f.field_72450_a - this.field_70165_t);
/* 273 */           this.field_70181_x = (float)(movingobjectposition.field_72307_f.field_72448_b - this.field_70163_u);
/* 274 */           this.field_70179_y = (float)(movingobjectposition.field_72307_f.field_72449_c - this.field_70161_v);
/* 275 */           float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
/* 276 */           this.field_70165_t -= this.field_70159_w / f * 0.05000000074505806D;
/* 277 */           this.field_70163_u -= this.field_70181_x / f * 0.05000000074505806D;
/* 278 */           this.field_70161_v -= this.field_70179_y / f * 0.05000000074505806D;
/* 279 */           func_85030_a("random.bowhit", 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
/* 280 */           this.inGround = true;
/* 281 */           this.field_70249_b = 7;
/* 282 */           func_70243_d(false);
/*     */           
/* 284 */           if (this.field_145790_g.func_149688_o() != Material.field_151579_a) {
/* 285 */             this.field_145790_g.func_149670_a(this.field_70170_p, this.field_145791_d, this.field_145792_e, this.field_145789_f, (Entity)this);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 290 */       this.field_70165_t += this.field_70159_w;
/* 291 */       this.field_70163_u += this.field_70181_x;
/* 292 */       this.field_70161_v += this.field_70179_y;
/* 293 */       float f2 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 294 */       this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / Math.PI);
/*     */       
/* 296 */       for (this.field_70125_A = (float)(Math.atan2(this.field_70181_x, f2) * 180.0D / Math.PI); this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F);
/*     */ 
/*     */       
/* 299 */       while (this.field_70125_A - this.field_70127_C >= 180.0F) {
/* 300 */         this.field_70127_C += 360.0F;
/*     */       }
/*     */       
/* 303 */       while (this.field_70177_z - this.field_70126_B < -180.0F) {
/* 304 */         this.field_70126_B -= 360.0F;
/*     */       }
/*     */       
/* 307 */       while (this.field_70177_z - this.field_70126_B >= 180.0F) {
/* 308 */         this.field_70126_B += 360.0F;
/*     */       }
/*     */       
/* 311 */       this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2F;
/* 312 */       this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
/* 313 */       float f3 = 0.99F;
/* 314 */       float f1 = 0.05F;
/*     */       
/* 316 */       if (func_70090_H()) {
/* 317 */         for (int l = 0; l < 4; l++) {
/* 318 */           float f4 = 0.25F;
/* 319 */           this.field_70170_p.func_72869_a("bubble", this.field_70165_t - this.field_70159_w * f4, this.field_70163_u - this.field_70181_x * f4, this.field_70161_v - this.field_70179_y * f4, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */         } 
/*     */         
/* 322 */         f3 = 0.8F;
/*     */       } 
/*     */       
/* 325 */       if (func_70026_G()) {
/* 326 */         func_70066_B();
/*     */       }
/*     */       
/* 329 */       this.field_70159_w *= f3;
/* 330 */       this.field_70181_x *= f3;
/* 331 */       this.field_70179_y *= f3;
/* 332 */       this.field_70181_x -= f1;
/* 333 */       func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 334 */       func_145775_I();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/* 340 */     par1NBTTagCompound.func_74777_a("xTile", (short)this.field_145791_d);
/* 341 */     par1NBTTagCompound.func_74777_a("yTile", (short)this.field_145792_e);
/* 342 */     par1NBTTagCompound.func_74777_a("zTile", (short)this.field_145789_f);
/* 343 */     par1NBTTagCompound.func_74777_a("life", (short)this.ticksInGround);
/* 344 */     par1NBTTagCompound.func_74774_a("inTile", (byte)Block.func_149682_b(this.field_145790_g));
/* 345 */     par1NBTTagCompound.func_74774_a("inData", (byte)this.inData);
/* 346 */     par1NBTTagCompound.func_74774_a("shake", (byte)this.field_70249_b);
/* 347 */     par1NBTTagCompound.func_74774_a("inGround", (byte)(this.inGround ? 1 : 0));
/* 348 */     par1NBTTagCompound.func_74774_a("pickup", (byte)this.field_70251_a);
/* 349 */     par1NBTTagCompound.func_74780_a("damage", this.damage);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/* 355 */     this.field_145791_d = par1NBTTagCompound.func_74765_d("xTile");
/* 356 */     this.field_145792_e = par1NBTTagCompound.func_74765_d("yTile");
/* 357 */     this.field_145789_f = par1NBTTagCompound.func_74765_d("zTile");
/* 358 */     this.ticksInGround = par1NBTTagCompound.func_74765_d("life");
/* 359 */     this.field_145790_g = Block.func_149729_e(par1NBTTagCompound.func_74771_c("inTile") & 0xFF);
/* 360 */     this.inData = par1NBTTagCompound.func_74771_c("inData") & 0xFF;
/* 361 */     this.field_70249_b = par1NBTTagCompound.func_74771_c("shake") & 0xFF;
/* 362 */     this.inGround = (par1NBTTagCompound.func_74771_c("inGround") == 1);
/*     */ 
/*     */     
/* 365 */     if (par1NBTTagCompound.func_150297_b("damage", 99)) {
/* 366 */       this.damage = par1NBTTagCompound.func_74769_h("damage");
/*     */     }
/*     */     
/* 369 */     if (par1NBTTagCompound.func_150297_b("pickup", 99)) {
/* 370 */       this.field_70251_a = par1NBTTagCompound.func_74771_c("pickup");
/* 371 */     } else if (par1NBTTagCompound.func_150297_b("player", 99)) {
/* 372 */       this.field_70251_a = par1NBTTagCompound.func_74767_n("player") ? 1 : 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70100_b_(EntityPlayer par1EntityPlayer) {}
/*     */ 
/*     */   
/*     */   protected void onImpact(Entity entityHit) {
/* 381 */     if (this.field_70250_c == null)
/* 382 */       return;  double startX = this.field_70250_c.field_70165_t;
/* 383 */     double startY = this.field_70250_c.field_70163_u;
/* 384 */     double startZ = this.field_70250_c.field_70161_v;
/*     */     
/* 386 */     for (int i = 0; i < 32; i++) {
/* 387 */       this.field_70170_p.func_72869_a("portal", this.field_70165_t, this.field_70163_u + this.field_70146_Z.nextDouble() * 2.0D, this.field_70161_v, this.field_70146_Z.nextGaussian(), 0.0D, this.field_70146_Z.nextGaussian());
/*     */     }
/*     */     
/* 390 */     if (!(this.field_70250_c instanceof EntityPlayer) || this.field_70250_c.field_70128_L)
/* 391 */       return;  if (entityHit != null) {
/* 392 */       entityHit.func_70097_a(DamageSource.func_76356_a((Entity)this, this.field_70250_c), 0.0F);
/*     */     }
/*     */     
/* 395 */     if (!this.field_70170_p.field_72995_K && 
/* 396 */       this.field_70250_c != null && this.field_70250_c instanceof EntityPlayerMP) {
/* 397 */       EntityPlayerMP entityplayermp = (EntityPlayerMP)this.field_70250_c;
/*     */       
/* 399 */       if (entityplayermp.field_71135_a.func_147362_b().func_150724_d() && entityplayermp.field_70170_p == this.field_70170_p) {
/* 400 */         int x = (int)Math.floor(this.field_70165_t);
/* 401 */         int y = (int)Math.floor(this.field_70163_u);
/* 402 */         int z = (int)Math.floor(this.field_70161_v);
/*     */         
/* 404 */         double travelDist = Utills.getDistanceAtoB(startX, startY, startZ, x, y, z);
/* 405 */         float travelDmg = (float)(travelDist / 5.0D);
/* 406 */         EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase)entityplayermp, x + 0.5D, y + 0.5D, z + 0.5D, travelDmg);
/* 407 */         if (!MinecraftForge.EVENT_BUS.post((Event)event)) {
/* 408 */           if (this.field_70250_c.func_70115_ae()) {
/* 409 */             this.field_70250_c.func_70078_a(null);
/*     */           }
/* 411 */           ((EntityPlayer)this.field_70250_c).func_70634_a(event.targetX, event.targetY, event.targetZ);
/* 412 */           this.field_70250_c.field_70143_R = 0.0F;
/* 413 */           this.field_70250_c.func_70097_a(DamageSource.field_76379_h, (float)(travelDist / 5.0D));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 418 */     func_70106_y();
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\entity\EntityEnderArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */