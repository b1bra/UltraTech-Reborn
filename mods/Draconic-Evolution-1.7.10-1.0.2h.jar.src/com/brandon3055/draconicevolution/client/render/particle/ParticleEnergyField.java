/*     */ package com.brandon3055.draconicevolution.client.render.particle;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*     */ import com.gamerforea.client.util.TessellatorDebuggerUtils;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParticleEnergyField
/*     */   extends EntityFX
/*     */ {
/*     */   private int type;
/*     */   private boolean advanced;
/*     */   private boolean renderParticle = true;
/*     */   
/*     */   public ParticleEnergyField(World world, double x, double y, double z, int maxAge, int type, boolean advanced) {
/*  22 */     super(world, x, y, z, 0.0D, 0.0D, 0.0D);
/*  23 */     this.field_70552_h = 1.0F;
/*  24 */     this.field_70553_i = 1.0F;
/*  25 */     this.field_70551_j = 1.0F;
/*  26 */     this.field_70145_X = true;
/*  27 */     this.field_70159_w = 0.0D;
/*  28 */     this.field_70181_x = 0.0D;
/*  29 */     this.field_70179_y = 0.0D;
/*  30 */     this.field_70547_e = maxAge;
/*  31 */     this.type = type;
/*  32 */     this.advanced = advanced;
/*  33 */     func_70105_a(1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void update(boolean render) {
/*  37 */     for (this.renderParticle = render; this.field_70547_e - this.field_70546_d < 4; this.field_70547_e++);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  44 */     this.field_70169_q = this.field_70165_t;
/*  45 */     this.field_70167_r = this.field_70163_u;
/*  46 */     this.field_70166_s = this.field_70161_v;
/*     */ 
/*     */     
/*  49 */     if (this.field_70546_d++ >= this.field_70547_e) {
/*  50 */       func_70106_y();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70539_a(Tessellator tessellator, float partialTick, float rotX, float rotXZ, float rotZ, float rotYZ, float rotXY) {
/*  57 */     if (!this.renderParticle)
/*  58 */       return;  tessellator.func_78381_a();
/*  59 */     GL11.glPushMatrix();
/*     */     
/*  61 */     GL11.glDepthMask(false);
/*  62 */     ResourceHandler.bindParticles();
/*     */     
/*  64 */     float minU = 0.0F + 0.125F * (this.advanced ? 4 : 3);
/*  65 */     float maxU = 0.0F + 0.125F * (this.advanced ? 5 : 4);
/*  66 */     if (this.type == 2) {
/*  67 */       minU = 0.625F;
/*  68 */       maxU = 0.75F;
/*     */     } 
/*  70 */     float minV = 0.0F;
/*  71 */     float maxV = 0.123F;
/*  72 */     float drawScale = 0.2F;
/*     */     
/*  74 */     if (this.field_70550_a != null) {
/*  75 */       minU = this.field_70550_a.func_94209_e();
/*  76 */       maxU = this.field_70550_a.func_94212_f();
/*  77 */       minV = this.field_70550_a.func_94206_g();
/*  78 */       maxV = this.field_70550_a.func_94210_h();
/*     */     } 
/*     */     
/*  81 */     float drawX = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * partialTick - field_70556_an);
/*  82 */     float drawY = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * partialTick - field_70554_ao);
/*  83 */     float drawZ = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * partialTick - field_70555_ap);
/*     */ 
/*     */     
/*  86 */     if (this.type == 0 || this.type == 2) {
/*  87 */       tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/ParticleEnergyField/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/*  88 */       tessellator.func_78380_c(200);
/*  89 */       tessellator.func_78369_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F);
/*     */       
/*  91 */       double pCount = (Minecraft.func_71410_x()).field_71474_y.field_74347_j ? 35.0D : 15.0D;
/*  92 */       for (int i = 0; i < pCount; i++) {
/*  93 */         double rot = i / pCount * 6.282D + this.field_70546_d / 3.0D;
/*  94 */         double offset = 0.4D;
/*  95 */         double ox = Math.sin(rot) * offset;
/*  96 */         double oz = Math.cos(rot) * offset;
/*     */         
/*  98 */         float drawXX = drawX + (float)ox;
/*  99 */         float drawZZ = drawZ + (float)oz;
/*     */         
/* 101 */         tessellator.func_78374_a((drawXX - rotX * drawScale - rotYZ * drawScale), (drawY - rotXZ * drawScale), (drawZZ - rotZ * drawScale - rotXY * drawScale), maxU, maxV);
/* 102 */         tessellator.func_78374_a((drawXX - rotX * drawScale + rotYZ * drawScale), (drawY + rotXZ * drawScale), (drawZZ - rotZ * drawScale + rotXY * drawScale), maxU, minV);
/* 103 */         tessellator.func_78374_a((drawXX + rotX * drawScale + rotYZ * drawScale), (drawY + rotXZ * drawScale), (drawZZ + rotZ * drawScale + rotXY * drawScale), minU, minV);
/* 104 */         tessellator.func_78374_a((drawXX + rotX * drawScale - rotYZ * drawScale), (drawY - rotXZ * drawScale), (drawZZ + rotZ * drawScale - rotXY * drawScale), minU, maxV);
/*     */       } 
/*     */       
/* 107 */       tessellator.func_78381_a();
/* 108 */     } else if (this.type == 1) {
/*     */       
/* 110 */       tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/ParticleEnergyField/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/* 111 */       tessellator.func_78380_c(200);
/* 112 */       tessellator.func_78369_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F);
/*     */       
/* 114 */       tessellator.func_78374_a((drawX - rotX * drawScale - rotYZ * drawScale), (drawY - rotXZ * drawScale), (drawZ - rotZ * drawScale - rotXY * drawScale), maxU, maxV);
/* 115 */       tessellator.func_78374_a((drawX - rotX * drawScale + rotYZ * drawScale), (drawY + rotXZ * drawScale), (drawZ - rotZ * drawScale + rotXY * drawScale), maxU, minV);
/* 116 */       tessellator.func_78374_a((drawX + rotX * drawScale + rotYZ * drawScale), (drawY + rotXZ * drawScale), (drawZ + rotZ * drawScale + rotXY * drawScale), minU, minV);
/* 117 */       tessellator.func_78374_a((drawX + rotX * drawScale - rotYZ * drawScale), (drawY - rotXZ * drawScale), (drawZ + rotZ * drawScale - rotXY * drawScale), minU, maxV);
/*     */       
/* 119 */       tessellator.func_78381_a();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     GL11.glPopMatrix();
/*     */     
/* 127 */     ResourceHandler.bindDefaultParticles();
/* 128 */     tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/ParticleEnergyField/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\particle\ParticleEnergyField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */