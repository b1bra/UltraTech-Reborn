/*     */ package com.brandon3055.draconicevolution.client.render.particle;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*     */ import com.gamerforea.client.util.TessellatorDebuggerUtils;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class ParticleEnergy
/*     */   extends EntityFX
/*     */ {
/*     */   public double originalX;
/*     */   public double originalY;
/*     */   public double originalZ;
/*     */   public double targetX;
/*     */   
/*     */   public ParticleEnergy(World world, double x, double y, double z, double tX, double tY, double tZ, int particle, boolean expand) {
/*  19 */     this(world, x, y, z, tX, tY, tZ, particle);
/*  20 */     this.expand = expand;
/*  21 */     this.field_70552_h = 0.7254902F;
/*  22 */     this.field_70553_i = 0.0F;
/*  23 */     this.field_70551_j = 0.77254903F;
/*  24 */     this.field_70544_f = 0.0F;
/*     */   }
/*     */   public double targetY; public double targetZ; public int particle; public boolean expand = true;
/*     */   public ParticleEnergy(World world, double x, double y, double z, double tX, double tY, double tZ, int particle) {
/*  28 */     super(world, x, y, z, 0.0D, 0.0D, 0.0D);
/*  29 */     this.field_70159_w = 0.0D;
/*  30 */     this.field_70181_x = 0.0D;
/*  31 */     this.field_70179_y = 0.0D;
/*  32 */     this.originalX = x;
/*  33 */     this.originalY = y;
/*  34 */     this.originalZ = z;
/*  35 */     this.targetX = tX;
/*  36 */     this.targetY = tY;
/*  37 */     this.targetZ = tZ;
/*  38 */     this.particle = particle;
/*     */     
/*  40 */     this.field_94054_b = 0;
/*  41 */     this.field_94055_c = 0;
/*     */     
/*  43 */     this.field_70552_h = (particle == 1) ? 0.0F : 1.0F;
/*  44 */     this.field_70553_i = (particle == 1) ? 1.0F : 0.2F;
/*  45 */     this.field_70551_j = (particle == 1) ? 1.0F : 0.0F;
/*     */ 
/*     */     
/*  48 */     this.field_70544_f = 0.5F;
/*  49 */     this.field_70547_e = 80;
/*  50 */     this.field_70145_X = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  56 */     this.field_70169_q = this.field_70165_t;
/*  57 */     this.field_70167_r = this.field_70163_u;
/*  58 */     this.field_70166_s = this.field_70161_v;
/*     */     
/*  60 */     if (!this.expand && this.field_70544_f < 0.7D) {
/*  61 */       this.field_70544_f = (float)(this.field_70544_f + 0.05D);
/*     */     }
/*     */     
/*  64 */     this.field_82339_as = (1.0F - this.field_70546_d / this.field_70547_e) * 0.5F;
/*     */     
/*  66 */     if (this.field_70546_d++ >= this.field_70547_e) {
/*  67 */       func_70106_y();
/*     */     }
/*  69 */     func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*  70 */     float speedMultiplyer = 5.01E-4F;
/*  71 */     float ySpeedMultiplyer = 0.1006F;
/*  72 */     if (this.expand) {
/*  73 */       this.field_70159_w += (this.targetX - this.field_70165_t) * speedMultiplyer;
/*  74 */       this.field_70181_x += (this.targetY - this.field_70163_u) * speedMultiplyer;
/*  75 */       this.field_70179_y += (this.targetZ - this.field_70161_v) * speedMultiplyer;
/*     */     } else {
/*  77 */       this.field_70159_w = (this.targetX - this.field_70165_t) * speedMultiplyer;
/*  78 */       this.field_70181_x = (this.targetY - this.field_70163_u) * ySpeedMultiplyer;
/*  79 */       this.field_70179_y = (this.targetZ - this.field_70161_v) * speedMultiplyer;
/*     */     } 
/*  81 */     if (this.expand && Math.abs(this.targetX - this.field_70165_t) < 0.01D && Math.abs(this.targetY - this.field_70163_u) < 0.01D && Math.abs(this.targetZ - this.field_70161_v) < 0.01D) {
/*  82 */       this.field_70159_w = 0.0D;
/*  83 */       this.field_70181_x = 0.0D;
/*  84 */       this.field_70179_y = 0.0D;
/*  85 */       this.field_70552_h = 0.0F;
/*  86 */       this.field_70553_i = 1.0F;
/*  87 */       this.field_70551_j = 1.0F;
/*  88 */       this.field_70544_f = 2.0F;
/*  89 */       this.field_82339_as = 1.0F - this.field_70546_d / this.field_70547_e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70539_a(Tessellator tesselator, float par2, float par3, float par4, float par5, float par6, float par7) {
/*  98 */     tesselator.func_78381_a();
/*  99 */     ResourceHandler.bindParticles();
/* 100 */     tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/ParticleEnergy/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/* 101 */     tesselator.func_78380_c(200);
/*     */ 
/*     */     
/* 104 */     float minU = 0.0F;
/* 105 */     float maxU = 0.1245F;
/* 106 */     float minV = 0.0F;
/* 107 */     float maxV = 0.1245F;
/* 108 */     float drawScale = 0.1F * this.field_70544_f;
/*     */     
/* 110 */     if (this.field_70550_a != null) {
/* 111 */       minU = this.field_70550_a.func_94209_e();
/* 112 */       maxU = this.field_70550_a.func_94212_f();
/* 113 */       minV = this.field_70550_a.func_94206_g();
/* 114 */       maxV = this.field_70550_a.func_94210_h();
/*     */     } 
/*     */     
/* 117 */     float drawX = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * par2 - field_70556_an);
/* 118 */     float drawY = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * par2 - field_70554_ao);
/* 119 */     float drawZ = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * par2 - field_70555_ap);
/*     */     
/* 121 */     tesselator.func_78369_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as);
/*     */ 
/*     */     
/* 124 */     tesselator.func_78374_a((drawX - par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ - par5 * drawScale - par7 * drawScale), maxU, maxV);
/* 125 */     tesselator.func_78374_a((drawX - par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ - par5 * drawScale + par7 * drawScale), maxU, minV);
/* 126 */     tesselator.func_78374_a((drawX + par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ + par5 * drawScale + par7 * drawScale), minU, minV);
/* 127 */     tesselator.func_78374_a((drawX + par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ + par5 * drawScale - par7 * drawScale), minU, maxV);
/*     */     
/* 129 */     tesselator.func_78381_a();
/* 130 */     ResourceHandler.bindDefaultParticles();
/* 131 */     tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/ParticleEnergy/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\particle\ParticleEnergy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */