/*     */ package com.brandon3055.draconicevolution.client.render.particle;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.client.handler.ClientEventHandler;
/*     */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*     */ import com.gamerforea.client.util.TessellatorDebuggerUtils;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParticleEnergyBeam
/*     */   extends EntityFX
/*     */ {
/*     */   private int flow;
/*  28 */   private double tX = 0.0D;
/*     */ 
/*     */ 
/*     */   
/*  32 */   private double tY = 0.0D;
/*     */ 
/*     */ 
/*     */   
/*  36 */   private double tZ = 0.0D;
/*     */   
/*     */   private boolean advanced;
/*     */   
/*     */   private boolean renderParticle = true;
/*     */   
/*  42 */   private float length = 0.0F;
/*  43 */   private float rotYaw = 0.0F;
/*  44 */   private float rotPitch = 0.0F;
/*  45 */   private float prevYaw = 0.0F;
/*  46 */   private float prevPitch = 0.0F;
/*     */ 
/*     */   
/*     */   private EntityPlayer player;
/*     */   
/*  51 */   private static ResourceLocation beamTextureBasic = new ResourceLocation("draconicevolution", "textures/models/EnergyBeamBlue.png");
/*  52 */   private static ResourceLocation beamTextureAdvanced = new ResourceLocation("draconicevolution", "textures/models/EnergyBeamRed.png");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticleEnergyBeam(World world, double x, double y, double z, double tX, double tY, double tZ, int maxAge, int flow, boolean advanced, int offsetMode) {
/*  59 */     super(world, x, y, z, 0.0D, 0.0D, 0.0D);
/*  60 */     this.field_70552_h = 1.0F;
/*  61 */     this.field_70553_i = 1.0F;
/*  62 */     this.field_70551_j = 1.0F;
/*  63 */     this.field_70145_X = true;
/*  64 */     this.field_70159_w = 0.0D;
/*  65 */     this.field_70181_x = 0.0D;
/*  66 */     this.field_70179_y = 0.0D;
/*  67 */     this.field_70547_e = maxAge;
/*  68 */     this.flow = flow;
/*  69 */     this.prevYaw = this.field_70177_z;
/*  70 */     this.prevPitch = this.rotPitch;
/*  71 */     func_70105_a(0.2F, 0.2F);
/*  72 */     this.advanced = advanced;
/*  73 */     this.tX = tX;
/*  74 */     this.tY = tY;
/*  75 */     this.tZ = tZ;
/*     */     
/*  77 */     if (offsetMode > 0) {
/*  78 */       double dist = Utills.getDistanceAtoB(x, z, tX, tZ);
/*  79 */       if (dist == 0.0D) dist = 0.1D; 
/*  80 */       double xDist = x - tX;
/*  81 */       double zDist = z - tZ;
/*  82 */       double xOff = xDist / dist;
/*  83 */       double zOff = zDist / dist;
/*  84 */       if (xOff == 0.0D && zOff == 0.0D) xOff = 1.0D;
/*     */       
/*  86 */       double offM = 0.4D;
/*     */       
/*  88 */       if (offsetMode == 2 || offsetMode == 3) {
/*  89 */         func_70107_b(this.field_70165_t - xOff * offM, this.field_70163_u, this.field_70161_v - zOff * offM);
/*     */       }
/*  91 */       if (offsetMode == 1 || offsetMode == 3) {
/*  92 */         this.tX = tX + xOff * offM;
/*  93 */         this.tY = tY;
/*  94 */         this.tZ = tZ + zOff * offM;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(int flow, boolean render) {
/* 101 */     this.renderParticle = render;
/* 102 */     for (this.flow = flow; this.field_70547_e - this.field_70546_d < 4; this.field_70547_e++);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 112 */     this.field_70169_q = this.field_70165_t;
/* 113 */     this.field_70167_r = this.field_70163_u;
/* 114 */     this.field_70166_s = this.field_70161_v;
/*     */     
/* 116 */     this.prevYaw = this.rotYaw;
/* 117 */     this.prevPitch = this.rotPitch;
/*     */     
/* 119 */     float xd = (float)(this.field_70165_t - this.tX);
/* 120 */     float yd = (float)(this.field_70163_u - this.tY);
/* 121 */     float zd = (float)(this.field_70161_v - this.tZ);
/* 122 */     this.length = MathHelper.func_76129_c(xd * xd + yd * yd + zd * zd);
/* 123 */     double var7 = MathHelper.func_76133_a((xd * xd + zd * zd));
/* 124 */     this.rotYaw = (float)(Math.atan2(xd, zd) * 180.0D / Math.PI);
/* 125 */     this.rotPitch = (float)(Math.atan2(yd, var7) * 180.0D / Math.PI);
/* 126 */     this.prevYaw = this.rotYaw;
/* 127 */     this.prevPitch = this.rotPitch;
/*     */ 
/*     */     
/* 130 */     if (this.field_70546_d++ >= this.field_70547_e) {
/* 131 */       func_70106_y();
/*     */     }
/*     */   }
/*     */   
/*     */   private EntityPlayer getPlayer() {
/* 136 */     if (this.player == null) {
/* 137 */       this.player = (EntityPlayer)(Minecraft.func_71410_x()).field_71439_g;
/*     */     }
/* 139 */     return this.player;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70539_a(Tessellator tessellator, float partialTick, float rotX, float rotXZ, float rotZ, float rotYZ, float rotXY) {
/* 144 */     if (!this.renderParticle)
/* 145 */       return;  tessellator.func_78381_a();
/* 146 */     GL11.glPushMatrix();
/*     */     
/* 148 */     float var9 = 1.0F;
/* 149 */     float slide = (getPlayer()).field_70173_aa;
/* 150 */     float size = this.flow / 100.0F * 2.0F;
/* 151 */     if (this.advanced) { (Minecraft.func_71410_x()).field_71446_o.func_110577_a(beamTextureAdvanced); }
/* 152 */     else { (Minecraft.func_71410_x()).field_71446_o.func_110577_a(beamTextureBasic); }
/* 153 */      GL11.glTexParameterf(3553, 10242, 10497.0F);
/* 154 */     GL11.glTexParameterf(3553, 10243, 10497.0F);
/* 155 */     GL11.glDisable(2884);
/* 156 */     float var11 = slide + partialTick;
/* 157 */     float var12 = -var11 * 0.2F - MathHelper.func_76141_d(-var11 * 0.1F);
/* 158 */     GL11.glBlendFunc(770, 1);
/* 159 */     GL11.glDepthMask(false);
/* 160 */     float xx = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * partialTick - field_70556_an);
/* 161 */     float yy = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * partialTick - field_70554_ao);
/* 162 */     float zz = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * partialTick - field_70555_ap);
/* 163 */     GL11.glTranslated(xx, yy, zz);
/* 164 */     float ry = (float)(this.prevYaw + (this.rotYaw - this.prevYaw) * partialTick);
/* 165 */     float rp = (float)(this.prevPitch + (this.rotPitch - this.prevPitch) * partialTick);
/* 166 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 167 */     GL11.glRotatef(180.0F + ry, 0.0F, 0.0F, -1.0F);
/* 168 */     GL11.glRotatef(rp, 1.0F, 0.0F, 0.0F);
/* 169 */     double var44 = -0.15D * size;
/* 170 */     double var17 = 0.15D * size;
/*     */     
/* 172 */     GL11.glTranslated(0.03D, 0.0D, 0.0D); int t;
/* 173 */     for (t = 0; t < 2; t++) {
/* 174 */       double var29 = (this.length * var9);
/* 175 */       double var31 = 0.0D;
/* 176 */       double var33 = 1.0D;
/* 177 */       double var35 = (-1.0F + var12 + t / 3.0F);
/* 178 */       double var37 = (this.length * var9) + var35;
/* 179 */       GL11.glRotatef(t * 90.0F, 0.0F, 1.0F, 0.0F);
/* 180 */       tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/ParticleEnergyBeam/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/* 181 */       tessellator.func_78380_c(200);
/* 182 */       tessellator.func_78369_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F);
/* 183 */       tessellator.func_78374_a(var44, var29, 0.0D, var33, var37);
/* 184 */       tessellator.func_78374_a(var44, 0.0D, 0.0D, var33, var35);
/* 185 */       tessellator.func_78374_a(var17, 0.0D, 0.0D, var31, var35);
/* 186 */       tessellator.func_78374_a(var17, var29, 0.0D, var31, var37);
/* 187 */       tessellator.func_78381_a();
/* 188 */       GL11.glRotatef(t * 90.0F, 0.0F, -1.0F, 0.0F);
/*     */     } 
/*     */     
/* 191 */     if (ClientEventHandler.playerHoldingWrench) {
/* 192 */       var44 = -0.15D;
/* 193 */       var17 = 0.15D;
/*     */ 
/*     */ 
/*     */       
/* 197 */       for (t = 0; t < 2; t++) {
/* 198 */         double var29 = (this.length * var9);
/* 199 */         double var31 = 0.0D;
/* 200 */         double var33 = 1.0D;
/* 201 */         double var35 = (-1.0F + var12 + t / 3.0F);
/* 202 */         double var37 = (this.length * var9) + var35;
/* 203 */         GL11.glRotatef(t * 90.0F, 0.0F, 1.0F, 0.0F);
/* 204 */         tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/ParticleEnergyBeam/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/* 205 */         tessellator.func_78380_c(200);
/* 206 */         tessellator.func_78369_a(0.0F, 1.0F, 0.0F, 1.0F);
/* 207 */         tessellator.func_78374_a(var44, var29, 0.0D, var33, var37);
/* 208 */         tessellator.func_78374_a(var44, 0.0D, 0.0D, var33, var35);
/* 209 */         tessellator.func_78374_a(var17, 0.0D, 0.0D, var31, var35);
/* 210 */         tessellator.func_78374_a(var17, var29, 0.0D, var31, var37);
/* 211 */         tessellator.func_78381_a();
/* 212 */         GL11.glRotatef(t * 90.0F, 0.0F, -1.0F, 0.0F);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 218 */     GL11.glBlendFunc(770, 771);
/* 219 */     GL11.glEnable(2884);
/*     */     
/* 221 */     GL11.glPopMatrix();
/*     */ 
/*     */     
/* 224 */     ResourceHandler.bindDefaultParticles();
/* 225 */     tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/ParticleEnergyBeam/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/*     */   }
/*     */   
/*     */   public int getFlow() {
/* 229 */     return this.flow;
/*     */   }
/*     */   
/*     */   public void setFlow(int flow) {
/* 233 */     this.flow = flow;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\particle\ParticleEnergyBeam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */