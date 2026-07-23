/*     */ package com.brandon3055.draconicevolution.client.render.particle;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*     */ import com.gamerforea.client.util.TessellatorDebuggerUtils;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.model.AdvancedModelLoader;
/*     */ import net.minecraftforge.client.model.IModelCustom;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Particles
/*     */ {
/*     */   public static class EnergyBeamParticle
/*     */     extends EntityFX
/*     */   {
/*  25 */     private int direction = 0;
/*  26 */     private double masterX = 0.0D;
/*  27 */     private double masterZ = 0.0D;
/*  28 */     private float rotation = 0.0F;
/*     */     private boolean mirror = false;
/*  30 */     private double[] trailX = new double[15];
/*  31 */     private double[] trailY = new double[15];
/*  32 */     private double[] trailZ = new double[15];
/*     */     
/*     */     public EnergyBeamParticle(World world, double x, double y, double z, double x1, double z1, int direction, boolean mirror) {
/*  35 */       super(world, x, y, z, 0.0D, 0.0D, 0.0D);
/*  36 */       float speed = 0.04F;
/*  37 */       this.field_70159_w = 0.0D;
/*  38 */       this.field_70181_x = 0.0D;
/*  39 */       this.field_70179_y = 0.0D;
/*  40 */       this.masterX = x1;
/*  41 */       this.masterZ = z1;
/*  42 */       this.direction = direction;
/*  43 */       this.mirror = mirror;
/*     */       
/*  45 */       switch (direction) {
/*     */         case 0:
/*  47 */           this.field_70159_w = speed;
/*     */           break;
/*     */         case 1:
/*  50 */           this.field_70159_w = -speed;
/*     */           break;
/*     */         case 2:
/*  53 */           this.field_70179_y = speed;
/*     */           break;
/*     */         case 3:
/*  56 */           this.field_70179_y = -speed;
/*     */           break;
/*     */       } 
/*     */       
/*  60 */       this.field_94054_b = 0;
/*  61 */       this.field_94055_c = 0;
/*  62 */       this.field_70544_f = 1.0F;
/*  63 */       this.field_70145_X = true;
/*  64 */       this.field_70547_e = 300;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_70071_h_() {
/*  69 */       if (this.field_70546_d > this.field_70547_e || func_70092_e(this.masterX, this.field_70163_u, this.masterZ) < 0.02D)
/*  70 */         func_70106_y(); 
/*  71 */       if (func_70092_e(this.masterX, this.field_70163_u, this.masterZ) < 0.1D) {
/*  72 */         this.field_70159_w = (this.masterX - this.field_70165_t) * 0.10000000149011612D;
/*  73 */         this.field_70181_x = (Math.floor(this.field_70163_u) + 0.5D - this.field_70163_u) * 0.10000000149011612D;
/*  74 */         this.field_70179_y = (this.masterZ - this.field_70161_v) * 0.10000000149011612D;
/*  75 */         this.field_70169_q = this.field_70165_t;
/*  76 */         this.field_70167_r = this.field_70163_u;
/*  77 */         this.field_70166_s = this.field_70161_v;
/*  78 */         func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */         return;
/*     */       } 
/*  81 */       this.trailX[0] = this.field_70165_t;
/*  82 */       this.trailY[0] = this.field_70163_u;
/*  83 */       this.trailZ[0] = this.field_70161_v;
/*  84 */       for (int i = 14; i >= 0; i--) {
/*  85 */         if (i > 0) {
/*  86 */           this.trailX[i] = this.trailX[i - 1];
/*  87 */           this.trailY[i] = this.trailY[i - 1];
/*  88 */           this.trailZ[i] = this.trailZ[i - 1];
/*     */         } 
/*     */       } 
/*     */       
/*  92 */       float multiplier = 0.27F;
/*  93 */       double masterY = Math.floor(this.field_70163_u) + 0.5D;
/*  94 */       if (this.direction == 0 || this.direction == 1) {
/*  95 */         this.field_70161_v = this.masterZ + Math.sin(this.rotation) * multiplier;
/*     */       } else {
/*  97 */         this.field_70165_t = this.masterX + Math.sin(this.rotation) * multiplier;
/*     */       } 
/*  99 */       this.field_70163_u = masterY + Math.cos(this.rotation) * multiplier;
/* 100 */       if (this.mirror) {
/* 101 */         float modifier = 3.0F;
/* 102 */         if (this.direction == 0 || this.direction == 1) {
/* 103 */           this.field_70161_v = this.masterZ + Math.sin((this.rotation + modifier)) * multiplier;
/*     */         } else {
/* 105 */           this.field_70165_t = this.masterX + Math.sin((this.rotation + modifier)) * multiplier;
/*     */         } 
/* 107 */         this.field_70163_u = masterY + Math.cos((this.rotation + modifier)) * multiplier;
/*     */       } 
/* 109 */       func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*     */       
/* 111 */       this.field_70169_q = this.field_70165_t;
/* 112 */       this.field_70167_r = this.field_70163_u;
/* 113 */       this.field_70166_s = this.field_70161_v;
/* 114 */       if (this.direction == 0 || this.direction == 1) { func_70091_d(this.field_70159_w, 0.0D, 0.0D); }
/* 115 */       else { func_70091_d(0.0D, 0.0D, this.field_70179_y); }
/* 116 */        this.field_70546_d++;
/* 117 */       if (this.direction == 0 || this.direction == 3) { this.rotation = (float)(this.rotation - 0.15D); }
/* 118 */       else { this.rotation = (float)(this.rotation + 0.15D); }
/*     */     
/*     */     }
/*     */ 
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public void func_70539_a(Tessellator tesselator, float par2, float par3, float par4, float par5, float par6, float par7) {
/* 125 */       tesselator.func_78381_a();
/* 126 */       ResourceHandler.bindParticles();
/* 127 */       tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/Particles$EnergyBeamParticle/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/* 128 */       tesselator.func_78380_c(200);
/*     */ 
/*     */       
/* 131 */       float minU = 0.0F;
/* 132 */       float maxU = 0.1245F;
/* 133 */       float minV = 0.0F;
/* 134 */       float maxV = 0.1245F;
/* 135 */       float drawScale = 0.1F * this.field_70544_f;
/*     */       
/* 137 */       if (this.field_70550_a != null) {
/* 138 */         minU = this.field_70550_a.func_94209_e();
/* 139 */         maxU = this.field_70550_a.func_94212_f();
/* 140 */         minV = this.field_70550_a.func_94206_g();
/* 141 */         maxV = this.field_70550_a.func_94210_h();
/*     */       } 
/*     */       
/* 144 */       float drawX = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * par2 - field_70556_an);
/* 145 */       float drawY = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * par2 - field_70554_ao);
/* 146 */       float drawZ = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * par2 - field_70555_ap);
/*     */       
/* 148 */       tesselator.func_78369_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as);
/*     */       
/* 150 */       tesselator.func_78374_a((drawX - par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ - par5 * drawScale - par7 * drawScale), maxU, maxV);
/* 151 */       tesselator.func_78374_a((drawX - par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ - par5 * drawScale + par7 * drawScale), maxU, minV);
/* 152 */       tesselator.func_78374_a((drawX + par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ + par5 * drawScale + par7 * drawScale), minU, minV);
/* 153 */       tesselator.func_78374_a((drawX + par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ + par5 * drawScale - par7 * drawScale), minU, maxV);
/*     */       
/* 155 */       for (int i = 0; i <= 14; i++) {
/* 156 */         GL11.glPushMatrix();
/* 157 */         drawX = (float)(this.trailX[i] + 0.0D * par2 - field_70556_an);
/* 158 */         drawY = (float)(this.trailY[i] + 0.0D * par2 - field_70554_ao);
/* 159 */         drawZ = (float)(this.trailZ[i] + 0.0D * par2 - field_70555_ap);
/* 160 */         float scale = 0.1F * (1.0F - i / 14.0F);
/* 161 */         float scale2 = 1.0F - i / 14.0F;
/*     */         
/* 163 */         if (!this.mirror) {
/* 164 */           tesselator.func_78369_a(1.0F, scale2, scale2, scale2);
/*     */         } else {
/* 166 */           tesselator.func_78369_a(scale2, 1.0F, scale2, scale2);
/*     */         } 
/*     */         
/* 169 */         tesselator.func_78374_a((drawX - par3 * scale - par6 * scale), (drawY - par4 * scale), (drawZ - par5 * scale - par7 * scale), maxU, maxV);
/* 170 */         tesselator.func_78374_a((drawX - par3 * scale + par6 * scale), (drawY + par4 * scale), (drawZ - par5 * scale + par7 * scale), maxU, minV);
/* 171 */         tesselator.func_78374_a((drawX + par3 * scale + par6 * scale), (drawY + par4 * scale), (drawZ + par5 * scale + par7 * scale), minU, minV);
/* 172 */         tesselator.func_78374_a((drawX + par3 * scale - par6 * scale), (drawY - par4 * scale), (drawZ + par5 * scale - par7 * scale), minU, maxV);
/* 173 */         GL11.glPopMatrix();
/*     */       } 
/*     */       
/* 176 */       tesselator.func_78381_a();
/* 177 */       ResourceHandler.bindDefaultParticles();
/* 178 */       tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/Particles$EnergyBeamParticle/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class EnergyTransferParticle
/*     */     extends EntityFX
/*     */   {
/*     */     private double targetX;
/*     */     private double targetY;
/*     */     private double targetZ;
/*     */     private boolean passive;
/*     */     
/*     */     public EnergyTransferParticle(World world, double x, double y, double z, double tX, double tY, double tZ, boolean passive) {
/* 191 */       super(world, x, y, z, 0.0D, 0.0D, 0.0D);
/* 192 */       this.passive = passive;
/* 193 */       this.targetX = tX;
/* 194 */       this.targetY = tY;
/* 195 */       this.targetZ = tZ;
/* 196 */       this.field_70159_w = 0.0D;
/* 197 */       this.field_70181_x = 0.0D;
/* 198 */       this.field_70179_y = 0.0D;
/* 199 */       this.field_94054_b = 0;
/* 200 */       this.field_94055_c = 0;
/* 201 */       this.field_70544_f = world.field_73012_v.nextFloat() + 0.5F;
/* 202 */       this.field_70547_e = 100;
/* 203 */       this.field_70145_X = true;
/* 204 */       if (!passive) {
/* 205 */         this.field_70552_h = 0.0F;
/* 206 */         this.field_70553_i = 0.36862746F;
/* 207 */         this.field_70551_j = 0.98039216F;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_70071_h_() {
/* 213 */       if (this.field_70546_d > this.field_70547_e) func_70106_y(); 
/* 214 */       if (this.field_70546_d > this.field_70547_e || func_70092_e(this.targetX, this.targetY, this.targetZ) < 0.05D)
/* 215 */         func_70106_y(); 
/* 216 */       this.field_70546_d++;
/* 217 */       this.field_70159_w += (this.targetX - this.field_70165_t) * 0.0010000000474974513D;
/* 218 */       this.field_70181_x += (this.targetY - this.field_70163_u) * 0.0010000000474974513D;
/* 219 */       this.field_70179_y += (this.targetZ - this.field_70161_v) * 0.0010000000474974513D;
/* 220 */       this.field_70169_q = this.field_70165_t;
/* 221 */       this.field_70167_r = this.field_70163_u;
/* 222 */       this.field_70166_s = this.field_70161_v;
/* 223 */       func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public void func_70539_a(Tessellator tesselator, float par2, float par3, float par4, float par5, float par6, float par7) {
/* 230 */       tesselator.func_78381_a();
/* 231 */       ResourceHandler.bindParticles();
/* 232 */       tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/Particles$EnergyTransferParticle/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/* 233 */       tesselator.func_78380_c(200);
/*     */ 
/*     */       
/* 236 */       float minU = 0.0F;
/* 237 */       float maxU = 0.1245F;
/* 238 */       float minV = 0.0F;
/* 239 */       float maxV = 0.1245F;
/* 240 */       float drawScale = 0.1F * this.field_70544_f;
/*     */       
/* 242 */       if (this.field_70550_a != null) {
/* 243 */         minU = this.field_70550_a.func_94209_e();
/* 244 */         maxU = this.field_70550_a.func_94212_f();
/* 245 */         minV = this.field_70550_a.func_94206_g();
/* 246 */         maxV = this.field_70550_a.func_94210_h();
/*     */       } 
/*     */       
/* 249 */       float drawX = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * par2 - field_70556_an);
/* 250 */       float drawY = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * par2 - field_70554_ao);
/* 251 */       float drawZ = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * par2 - field_70555_ap);
/*     */       
/* 253 */       if (this.passive) {
/* 254 */         tesselator.func_78369_a((float)func_70092_e(this.targetX, this.targetY, this.targetZ) * 5.0F * this.field_70552_h, (float)func_70092_e(this.targetX, this.targetY, this.targetZ) * 5.0F * this.field_70553_i, this.field_70551_j, this.field_82339_as);
/*     */       } else {
/* 256 */         tesselator.func_78369_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, (float)func_70092_e(this.targetX, this.targetY, this.targetZ) * 5.0F);
/*     */       } 
/*     */ 
/*     */       
/* 260 */       tesselator.func_78374_a((drawX - par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ - par5 * drawScale - par7 * drawScale), maxU, maxV);
/* 261 */       tesselator.func_78374_a((drawX - par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ - par5 * drawScale + par7 * drawScale), maxU, minV);
/* 262 */       tesselator.func_78374_a((drawX + par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ + par5 * drawScale + par7 * drawScale), minU, minV);
/* 263 */       tesselator.func_78374_a((drawX + par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ + par5 * drawScale - par7 * drawScale), minU, maxV);
/*     */       
/* 265 */       tesselator.func_78381_a();
/* 266 */       ResourceHandler.bindDefaultParticles();
/* 267 */       tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/Particles$EnergyTransferParticle/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class AdvancedSeekerParticle
/*     */     extends EntityFX
/*     */   {
/*     */     public double targetX;
/*     */     public double targetY;
/*     */     public double targetZ;
/* 277 */     public double startDist = 0.0D;
/*     */     public int behaviour;
/* 279 */     public int timer = 0;
/*     */     
/*     */     public AdvancedSeekerParticle(World world, double x, double y, double z, double tX, double tY, double tZ, int type, int maxAge) {
/* 282 */       super(world, x, y, z, 0.0D, 0.0D, 0.0D);
/* 283 */       this.targetX = tX;
/* 284 */       this.targetY = tY;
/* 285 */       this.targetZ = tZ;
/* 286 */       this.field_70159_w = 0.0D;
/* 287 */       this.field_70181_x = 0.0D;
/* 288 */       this.field_70179_y = 0.0D;
/* 289 */       this.field_94054_b = 0;
/* 290 */       this.field_94055_c = 0;
/* 291 */       this.field_70544_f = world.field_73012_v.nextFloat() + 0.5F;
/* 292 */       this.field_70547_e = 100;
/* 293 */       this.field_70145_X = true;
/* 294 */       this.behaviour = type;
/* 295 */       this.startDist = func_70011_f(this.targetX, this.targetY, this.targetZ);
/* 296 */       this.field_70547_e = maxAge;
/*     */     }
/*     */     
/*     */     public AdvancedSeekerParticle(World world, double x, double y, double z, double tX, double tY, double tZ, int type, float red, float green, float blue, int maxAge) {
/* 300 */       this(world, x, y, z, tX, tY, tZ, type, maxAge);
/* 301 */       this.field_70552_h = red;
/* 302 */       this.field_70553_i = green;
/* 303 */       this.field_70551_j = blue;
/*     */     }
/*     */     
/*     */     public AdvancedSeekerParticle(World world, double x, double y, double z, double tX, double tY, double tZ, int type, float red, float green, float blue, int maxAge, int timer) {
/* 307 */       this(world, x, y, z, tX, tY, tZ, type, maxAge);
/* 308 */       this.field_70552_h = red;
/* 309 */       this.field_70553_i = green;
/* 310 */       this.field_70551_j = blue;
/* 311 */       this.timer = timer;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_70071_h_() {
/* 316 */       switch (this.behaviour) {
/*     */         case 1:
/* 318 */           behaviour1();
/*     */           break;
/*     */         case 2:
/* 321 */           behaviour2();
/*     */           break;
/*     */         case 3:
/* 324 */           behaviour3();
/*     */           break;
/*     */         case 4:
/* 327 */           behaviour4();
/*     */           break;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void behaviour1() {
/* 337 */       if (this.field_70546_d > this.field_70547_e) func_70106_y(); 
/* 338 */       if (this.field_70546_d + 10 >= this.field_70547_e)
/* 339 */         this.field_82339_as = (this.field_70547_e - this.field_70546_d) / 10.0F; 
/* 340 */       this.field_70546_d++;
/* 341 */       this.field_70169_q = this.field_70165_t;
/* 342 */       this.field_70167_r = this.field_70163_u;
/* 343 */       this.field_70166_s = this.field_70161_v;
/* 344 */       func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 345 */       this.timer++;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void behaviour2() {
/* 352 */       if (this.field_70546_d > this.field_70547_e) func_70106_y(); 
/* 353 */       this.field_70546_d++;
/*     */       
/* 355 */       if (this.field_70544_f > 0.0F) this.field_70544_f -= 0.02F;
/*     */       
/* 357 */       if (this.field_70546_d > this.field_70547_e - 10) {
/* 358 */         this.field_70544_f++;
/* 359 */         this.field_82339_as -= 0.1F;
/*     */       } 
/*     */       
/* 362 */       float motionMod = 0.001F * Math.max(1.0F - (this.field_70546_d / 50), 0.0F);
/* 363 */       this.field_70159_w += (this.targetX - this.field_70165_t) * motionMod;
/* 364 */       this.field_70181_x += (this.targetY - this.field_70163_u) * motionMod;
/* 365 */       this.field_70179_y += (this.targetZ - this.field_70161_v) * motionMod;
/*     */       
/* 367 */       float directMotMod = 0.05F;
/* 368 */       float directMotT = Math.max(1.0F - this.field_70546_d / 50.0F, 0.0F);
/* 369 */       directMotMod *= 1.0F - directMotT;
/* 370 */       this.field_70159_w = this.field_70159_w * directMotT + (this.targetX - this.field_70165_t) * directMotMod;
/* 371 */       this.field_70181_x = this.field_70181_x * directMotT + (this.targetY - this.field_70163_u) * directMotMod;
/* 372 */       this.field_70179_y = this.field_70179_y * directMotT + (this.targetZ - this.field_70161_v) * directMotMod;
/*     */       
/* 374 */       this.field_70169_q = this.field_70165_t;
/* 375 */       this.field_70167_r = this.field_70163_u;
/* 376 */       this.field_70166_s = this.field_70161_v;
/* 377 */       func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 378 */       this.timer++;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void behaviour3() {
/* 385 */       if (func_70092_e(this.targetX, this.targetY, this.targetZ) < 0.1D && this.field_70546_d < this.field_70547_e - 40)
/* 386 */         this.field_70546_d = this.field_70547_e - 40; 
/* 387 */       if (this.field_70546_d > this.field_70547_e) func_70106_y(); 
/* 388 */       this.field_70546_d++;
/*     */       
/* 390 */       if (this.field_70544_f > 0.0F) this.field_70544_f -= 0.02F;
/*     */       
/* 392 */       if ((this.field_70552_h == 1.0F || (this.field_70553_i == 1.0F && this.field_70551_j == 0.0F)) && this.field_70546_d > this.field_70547_e - 10) {
/* 393 */         int t = this.timer - 300;
/* 394 */         if (this.timer > 0)
/* 395 */           this.field_70544_f = t / (1.0F - this.field_70546_d / (this.field_70547_e - 10.0F) * 100.0F); 
/* 396 */       } else if (this.field_70551_j == 1.0F && this.field_70546_d > this.field_70547_e - 15) {
/* 397 */         int t = this.timer - 700;
/* 398 */         if (this.timer > 0) {
/* 399 */           this.field_70544_f = t / (1.0F - this.field_70546_d / (this.field_70547_e - 10.0F) * 100.0F);
/*     */         }
/*     */       } 
/* 402 */       float motionMod = 0.001F * Math.max(1.0F - (this.field_70546_d / 50), 0.0F);
/* 403 */       this.field_70159_w += (this.targetX - this.field_70165_t) * motionMod;
/* 404 */       this.field_70181_x += (this.targetY - this.field_70163_u) * motionMod;
/* 405 */       this.field_70179_y += (this.targetZ - this.field_70161_v) * motionMod;
/*     */       
/* 407 */       float directMotMod = 0.05F;
/* 408 */       float directMotT = Math.max(1.0F - this.field_70546_d / 50.0F, 0.0F);
/* 409 */       directMotMod *= 1.0F - directMotT;
/* 410 */       this.field_70159_w = this.field_70159_w * directMotT + (this.targetX - this.field_70165_t) * directMotMod;
/* 411 */       this.field_70181_x = this.field_70181_x * directMotT + (this.targetY - this.field_70163_u) * directMotMod;
/* 412 */       this.field_70179_y = this.field_70179_y * directMotT + (this.targetZ - this.field_70161_v) * directMotMod;
/*     */       
/* 414 */       if (this.field_70547_e - this.field_70546_d < 40 && this.timer > 2300 && (this.field_70552_h == 1.0F || (this.field_70553_i == 1.0F && this.field_70551_j == 1.0F))) {
/* 415 */         double yChange = (this.timer - 2300);
/*     */         
/* 417 */         if (this.field_70163_u < this.targetY + 60.0D) this.field_70181_x = yChange * 0.05000000074505806D;
/*     */       
/*     */       } 
/* 420 */       this.field_70169_q = this.field_70165_t;
/* 421 */       this.field_70167_r = this.field_70163_u;
/* 422 */       this.field_70166_s = this.field_70161_v;
/* 423 */       func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 424 */       this.timer++;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void behaviour4() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public void func_70539_a(Tessellator tesselator, float par2, float par3, float par4, float par5, float par6, float par7) {
/* 438 */       tesselator.func_78381_a();
/* 439 */       ResourceHandler.bindParticles();
/* 440 */       tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/Particles$AdvancedSeekerParticle/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/* 441 */       tesselator.func_78380_c(200);
/*     */ 
/*     */       
/* 444 */       int uIndex = 0;
/* 445 */       int vIndex = 0;
/*     */       
/* 447 */       float minU = uIndex * 0.125F;
/* 448 */       float maxU = (uIndex + 1) * 0.12F;
/* 449 */       float minV = vIndex * 0.125F;
/* 450 */       float maxV = (vIndex + 1) * 0.125F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 456 */       float drawScale = 0.1F * this.field_70544_f;
/*     */       
/* 458 */       if (this.field_70550_a != null) {
/* 459 */         minU = this.field_70550_a.func_94209_e();
/* 460 */         maxU = this.field_70550_a.func_94212_f();
/* 461 */         minV = this.field_70550_a.func_94206_g();
/* 462 */         maxV = this.field_70550_a.func_94210_h();
/*     */       } 
/*     */       
/* 465 */       float drawX = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * par2 - field_70556_an);
/* 466 */       float drawY = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * par2 - field_70554_ao);
/* 467 */       float drawZ = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * par2 - field_70555_ap);
/*     */       
/* 469 */       tesselator.func_78369_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as);
/*     */ 
/*     */ 
/*     */       
/* 473 */       tesselator.func_78374_a((drawX - par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ - par5 * drawScale - par7 * drawScale), maxU, maxV);
/* 474 */       tesselator.func_78374_a((drawX - par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ - par5 * drawScale + par7 * drawScale), maxU, minV);
/* 475 */       tesselator.func_78374_a((drawX + par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ + par5 * drawScale + par7 * drawScale), minU, minV);
/* 476 */       tesselator.func_78374_a((drawX + par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ + par5 * drawScale - par7 * drawScale), minU, maxV);
/*     */       
/* 478 */       tesselator.func_78381_a();
/* 479 */       ResourceHandler.bindDefaultParticles();
/* 480 */       tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/Particles$AdvancedSeekerParticle/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class TransceiverParticle
/*     */     extends EntityFX
/*     */   {
/*     */     public double targetX;
/*     */     public double targetY;
/*     */     public double targetZ;
/* 490 */     private int textureIndex = 0;
/*     */     
/*     */     public TransceiverParticle(World world, double x, double y, double z, double tx, double ty, double tz) {
/* 493 */       super(world, x, y, z, 0.0D, 0.0D, 0.0D);
/* 494 */       this.targetX = tx;
/* 495 */       this.targetY = ty;
/* 496 */       this.targetZ = tz;
/* 497 */       this.field_70159_w = 0.0D;
/* 498 */       this.field_70181_x = 0.0D;
/* 499 */       this.field_70179_y = 0.0D;
/* 500 */       this.field_70547_e = 1000;
/* 501 */       this.field_70145_X = true;
/* 502 */       this.field_70552_h = this.field_70553_i = this.field_70551_j = 1.0F;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_70071_h_() {
/* 508 */       if (this.field_70546_d > this.field_70547_e) func_70106_y(); 
/* 509 */       if (this.field_70546_d > this.field_70547_e || func_70092_e(this.targetX, this.targetY, this.targetZ) < 0.05D)
/* 510 */         func_70106_y(); 
/* 511 */       this.field_70546_d++;
/* 512 */       float speed = this.field_70546_d * 2.0E-5F;
/* 513 */       this.field_70159_w += (this.targetX - this.field_70165_t) * speed;
/* 514 */       this.field_70181_x += (this.targetY - this.field_70163_u) * speed;
/* 515 */       this.field_70179_y += (this.targetZ - this.field_70161_v) * speed;
/* 516 */       this.field_70169_q = this.field_70165_t;
/* 517 */       this.field_70167_r = this.field_70163_u;
/* 518 */       this.field_70166_s = this.field_70161_v;
/* 519 */       func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */       
/* 521 */       this.textureIndex = this.field_70170_p.field_73012_v.nextInt(5);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public void func_70539_a(Tessellator tesselator, float par2, float par3, float par4, float par5, float par6, float par7) {
/* 528 */       tesselator.func_78381_a();
/* 529 */       ResourceHandler.bindParticles();
/* 530 */       tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/Particles$TransceiverParticle/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/* 531 */       tesselator.func_78380_c(200);
/*     */       
/* 533 */       int uIndex = this.textureIndex;
/* 534 */       int vIndex = 1;
/*     */       
/* 536 */       float minU = uIndex * 0.125F;
/* 537 */       float maxU = (uIndex + 1) * 0.125F;
/* 538 */       float minV = vIndex * 0.125F;
/* 539 */       float maxV = (vIndex + 1) * 0.125F;
/*     */       
/* 541 */       float drawScale = 0.1F * this.field_70544_f;
/*     */       
/* 543 */       if (this.field_70550_a != null) {
/* 544 */         minU = this.field_70550_a.func_94209_e();
/* 545 */         maxU = this.field_70550_a.func_94212_f();
/* 546 */         minV = this.field_70550_a.func_94206_g();
/* 547 */         maxV = this.field_70550_a.func_94210_h();
/*     */       } 
/*     */       
/* 550 */       float drawX = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * par2 - field_70556_an);
/* 551 */       float drawY = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * par2 - field_70554_ao);
/* 552 */       float drawZ = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * par2 - field_70555_ap);
/*     */       
/* 554 */       tesselator.func_78369_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as);
/*     */ 
/*     */ 
/*     */       
/* 558 */       tesselator.func_78374_a((drawX - par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ - par5 * drawScale - par7 * drawScale), maxU, maxV);
/* 559 */       tesselator.func_78374_a((drawX - par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ - par5 * drawScale + par7 * drawScale), maxU, minV);
/* 560 */       tesselator.func_78374_a((drawX + par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ + par5 * drawScale + par7 * drawScale), minU, minV);
/* 561 */       tesselator.func_78374_a((drawX + par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ + par5 * drawScale - par7 * drawScale), minU, maxV);
/*     */       
/* 563 */       tesselator.func_78381_a();
/* 564 */       ResourceHandler.bindDefaultParticles();
/* 565 */       tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/Particles$TransceiverParticle/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PortalParticle
/*     */     extends EntityFX {
/*     */     public double targetX;
/*     */     public double targetY;
/*     */     public double targetZ;
/*     */     public double startX;
/*     */     public double startY;
/*     */     public double startZ;
/*     */     public float baseScale;
/*     */     
/*     */     public PortalParticle(World world, double x, double y, double z, double tx, double ty, double tz) {
/* 580 */       super(world, x, y, z, 0.0D, 0.0D, 0.0D);
/* 581 */       this.startX = x;
/* 582 */       this.startY = y;
/* 583 */       this.startZ = z;
/* 584 */       this.targetX = tx;
/* 585 */       this.targetY = ty;
/* 586 */       this.targetZ = tz;
/* 587 */       float speed = 0.12F + this.field_70146_Z.nextFloat() * 0.2F;
/* 588 */       this.field_70159_w = (this.targetX - this.startX) * speed;
/* 589 */       this.field_70181_x = (this.targetY - this.startY) * speed;
/* 590 */       this.field_70179_y = (this.targetZ - this.startZ) * speed;
/* 591 */       this.field_70547_e = 100;
/* 592 */       this.field_70145_X = true;
/* 593 */       this.field_70552_h = this.field_70553_i = this.field_70551_j = 1.0F;
/* 594 */       float baseSize = 0.05F + (float)RenderManager.field_78727_a.field_78734_h.func_70011_f(x, y, z) * 0.007F;
/* 595 */       if (RenderManager.field_78727_a.field_78734_h != null) {
/* 596 */         this.baseScale = baseSize + this.field_70146_Z.nextFloat() * baseSize * 2.0F;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_70071_h_() {
/* 602 */       if (this.field_70546_d >= this.field_70547_e || func_70092_e(this.targetX, this.targetY, this.targetZ) < 0.05D) {
/* 603 */         func_70106_y();
/*     */       }
/* 605 */       double d1 = Utills.getDistanceAtoB(this.startX, this.startY, this.startZ, this.targetX, this.targetY, this.targetZ);
/* 606 */       double d2 = Utills.getDistanceAtoB(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.targetX, this.targetY, this.targetZ);
/* 607 */       this.field_70544_f = (float)(d2 / d1) * this.baseScale;
/*     */       
/* 609 */       this.field_70546_d++;
/* 610 */       this.field_70169_q = this.field_70165_t;
/* 611 */       this.field_70167_r = this.field_70163_u;
/* 612 */       this.field_70166_s = this.field_70161_v;
/* 613 */       func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public void func_70539_a(Tessellator tesselator, float par2, float par3, float par4, float par5, float par6, float par7) {
/* 621 */       tesselator.func_78381_a();
/* 622 */       ResourceHandler.bindParticles();
/* 623 */       tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/Particles$PortalParticle/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/* 624 */       tesselator.func_78380_c(200);
/*     */       
/* 626 */       int uIndex = 6;
/* 627 */       int vIndex = 0;
/*     */       
/* 629 */       float minU = uIndex * 0.125F;
/* 630 */       float maxU = (uIndex + 1) * 0.125F;
/* 631 */       float minV = vIndex * 0.125F;
/* 632 */       float maxV = (vIndex + 1) * 0.125F;
/*     */       
/* 634 */       float drawScale = 0.1F * this.field_70544_f;
/*     */       
/* 636 */       if (this.field_70550_a != null) {
/* 637 */         minU = this.field_70550_a.func_94209_e();
/* 638 */         maxU = this.field_70550_a.func_94212_f();
/* 639 */         minV = this.field_70550_a.func_94206_g();
/* 640 */         maxV = this.field_70550_a.func_94210_h();
/*     */       } 
/*     */       
/* 643 */       float drawX = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * par2 - field_70556_an);
/* 644 */       float drawY = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * par2 - field_70554_ao);
/* 645 */       float drawZ = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * par2 - field_70555_ap);
/*     */       
/* 647 */       tesselator.func_78369_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as);
/*     */       
/* 649 */       tesselator.func_78374_a((drawX - par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ - par5 * drawScale - par7 * drawScale), maxU, maxV);
/* 650 */       tesselator.func_78374_a((drawX - par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ - par5 * drawScale + par7 * drawScale), maxU, minV);
/* 651 */       tesselator.func_78374_a((drawX + par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ + par5 * drawScale + par7 * drawScale), minU, minV);
/* 652 */       tesselator.func_78374_a((drawX + par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ + par5 * drawScale - par7 * drawScale), minU, maxV);
/*     */       
/* 654 */       tesselator.func_78381_a();
/* 655 */       ResourceHandler.bindDefaultParticles();
/* 656 */       tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/Particles$PortalParticle/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ArrowParticle
/*     */     extends EntityFX {
/*     */     private int particleColour;
/*     */     private float setScale;
/*     */     
/*     */     public ArrowParticle(World world, double x, double y, double z, int colour, float scale) {
/* 666 */       super(world, x, y, z);
/* 667 */       this.field_70547_e = 30;
/* 668 */       if (scale > 5.0F) this.field_70547_e = 10; 
/* 669 */       this.field_70145_X = true;
/* 670 */       this.particleColour = colour;
/* 671 */       this.field_70552_h = this.field_70553_i = this.field_70551_j = 1.0F;
/* 672 */       this.setScale = scale;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_70071_h_() {
/* 677 */       if (this.field_70546_d >= this.field_70547_e) func_70106_y();
/*     */       
/* 679 */       this.field_82339_as = 1.0F - (float)(this.field_70546_d / this.field_70547_e);
/* 680 */       this.field_70544_f = this.setScale * (1.0F - (float)(this.field_70546_d / this.field_70547_e));
/*     */       
/* 682 */       this.field_70546_d++;
/* 683 */       this.field_70169_q = this.field_70165_t;
/* 684 */       this.field_70167_r = this.field_70163_u;
/* 685 */       this.field_70166_s = this.field_70161_v;
/* 686 */       func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */     }
/*     */ 
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public void func_70539_a(Tessellator tesselator, float par2, float par3, float par4, float par5, float par6, float par7) {
/* 692 */       tesselator.func_78381_a();
/* 693 */       ResourceHandler.bindParticles();
/* 694 */       tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/Particles$ArrowParticle/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/* 695 */       tesselator.func_78380_c(200);
/*     */ 
/*     */       
/* 698 */       int uIndex = 3;
/* 699 */       int vIndex = 1;
/*     */       
/* 701 */       float minU = uIndex * 0.125F;
/* 702 */       float maxU = (uIndex + 1) * 0.125F;
/* 703 */       float minV = vIndex * 0.125F;
/* 704 */       float maxV = (vIndex + 1) * 0.125F;
/*     */       
/* 706 */       float drawScale = 0.1F * this.field_70544_f;
/*     */       
/* 708 */       if (this.field_70550_a != null) {
/* 709 */         minU = this.field_70550_a.func_94209_e();
/* 710 */         maxU = this.field_70550_a.func_94212_f();
/* 711 */         minV = this.field_70550_a.func_94206_g();
/* 712 */         maxV = this.field_70550_a.func_94210_h();
/*     */       } 
/*     */       
/* 715 */       float drawX = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * par2 - field_70556_an);
/* 716 */       float drawY = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * par2 - field_70554_ao);
/* 717 */       float drawZ = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * par2 - field_70555_ap);
/*     */ 
/*     */       
/* 720 */       tesselator.func_78384_a(this.particleColour, 255);
/*     */       
/* 722 */       tesselator.func_78374_a((drawX - par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ - par5 * drawScale - par7 * drawScale), maxU, maxV);
/* 723 */       tesselator.func_78374_a((drawX - par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ - par5 * drawScale + par7 * drawScale), maxU, minV);
/* 724 */       tesselator.func_78374_a((drawX + par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ + par5 * drawScale + par7 * drawScale), minU, minV);
/* 725 */       tesselator.func_78374_a((drawX + par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ + par5 * drawScale - par7 * drawScale), minU, maxV);
/*     */       
/* 727 */       tesselator.func_78381_a();
/* 728 */       ResourceHandler.bindDefaultParticles();
/* 729 */       tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/Particles$ArrowParticle/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ArrowShockParticle
/*     */     extends EntityFX {
/*     */     public static IModelCustom uvSphere;
/* 736 */     public double size = 0.0D;
/*     */     public double maxSize;
/*     */     
/*     */     public ArrowShockParticle(World world, double x, double y, double z, int maxSize) {
/* 740 */       super(world, x, y, z, 0.0D, 0.0D, 0.0D);
/* 741 */       if (uvSphere == null)
/* 742 */         uvSphere = AdvancedModelLoader.loadModel(new ResourceLocation("draconicevolution", "models/reactorCoreModel.obj")); 
/* 743 */       this.maxSize = maxSize / 100.0D;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_70071_h_() {
/* 749 */       this.field_70546_d++;
/* 750 */       this.size += 1.2D;
/* 751 */       if (this.size > this.maxSize * 1.2D) func_70106_y();
/*     */       
/* 753 */       this.field_70169_q = this.field_70165_t;
/* 754 */       this.field_70167_r = this.field_70163_u;
/* 755 */       this.field_70166_s = this.field_70161_v;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public void func_70539_a(Tessellator tessellator, float partialTick, float par3, float par4, float par5, float par6, float par7) {
/* 762 */       tessellator.func_78381_a();
/* 763 */       GL11.glPushMatrix();
/* 764 */       float xx = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * partialTick - field_70556_an);
/* 765 */       float yy = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * partialTick - field_70554_ao);
/* 766 */       float zz = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * partialTick - field_70555_ap);
/* 767 */       GL11.glTranslated(xx + 0.5D, yy + 0.5D, zz + 0.5D);
/*     */       
/* 769 */       GL11.glDisable(2884);
/* 770 */       GL11.glAlphaFunc(516, 0.0F);
/* 771 */       GL11.glEnable(3042);
/* 772 */       GL11.glBlendFunc(770, 771);
/* 773 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 200.0F, 200.0F);
/* 774 */       ResourceHandler.bindResource("textures/models/white.png");
/* 775 */       double s = this.size + partialTick;
/* 776 */       GL11.glScaled(s, s / 3.0D, s);
/*     */ 
/*     */ 
/*     */       
/* 780 */       GL11.glPushMatrix();
/* 781 */       float a = (float)Math.max(0.0D, 0.3D - this.size / this.maxSize);
/* 782 */       GL11.glScaled(5.0D, 5.0D, 5.0D);
/*     */       
/* 784 */       GL11.glPopMatrix();
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
/* 795 */       GL11.glPushMatrix();
/* 796 */       a = (float)Math.max(0.0D, 0.5D - this.size / this.maxSize * 0.5D);
/* 797 */       GL11.glColor4f(1.0F, 0.1F, 0.0F, a);
/* 798 */       if (a > 0.0F) uvSphere.renderAll(); 
/* 799 */       GL11.glPopMatrix();
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
/* 856 */       GL11.glEnable(2884);
/* 857 */       GL11.glPopMatrix();
/* 858 */       ResourceHandler.bindDefaultParticles();
/* 859 */       tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/Particles$ArrowShockParticle/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\particle\Particles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */