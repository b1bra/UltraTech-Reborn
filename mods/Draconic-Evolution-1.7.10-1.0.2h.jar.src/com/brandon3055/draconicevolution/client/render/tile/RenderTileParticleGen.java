/*     */ package com.brandon3055.draconicevolution.client.render.tile;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileParticleGenerator;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.TileEnergyStorageCore;
/*     */ import com.gamerforea.client.util.TessellatorDebuggerUtils;
/*     */ import cpw.mods.fml.client.FMLClientHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.client.model.AdvancedModelLoader;
/*     */ import net.minecraftforge.client.model.IModelCustom;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class RenderTileParticleGen
/*     */   extends TileEntitySpecialRenderer
/*     */ {
/*  23 */   private final ResourceLocation texture = new ResourceLocation("draconicevolution", "textures/models/ParticleGenTextureSheet.png");
/*  24 */   private final ResourceLocation beamTexture = new ResourceLocation("draconicevolution", "textures/models/stabilizer_beam.png");
/*  25 */   private static final ResourceLocation modelTexture = new ResourceLocation("draconicevolution", "textures/models/stabilizer_sphere.png");
/*     */   
/*     */   private IModelCustom stabilizerSphereModel;
/*  28 */   private float pxl = 0.015625F;
/*     */   
/*     */   public RenderTileParticleGen() {
/*  31 */     this.stabilizerSphereModel = AdvancedModelLoader.loadModel(new ResourceLocation("draconicevolution", "models/stabilizer_sphere.obj"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_147500_a(TileEntity tileEntity, double x, double y, double z, float f) {
/*  36 */     GL11.glPushMatrix();
/*     */     
/*  38 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/*  39 */     TileParticleGenerator tileEntityGen = (TileParticleGenerator)tileEntity;
/*  40 */     renderBlock(tileEntityGen, f);
/*     */ 
/*     */     
/*  43 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void renderBlock(TileParticleGenerator tl, float f3) {
/*  47 */     Tessellator tessellator = Tessellator.field_78398_a;
/*     */     
/*  49 */     boolean inverted = tl.inverted;
/*  50 */     boolean stabilizerMode = tl.stabalizerMode;
/*     */     
/*  52 */     GL11.glPushMatrix();
/*     */ 
/*     */     
/*  55 */     func_147499_a(this.texture);
/*     */     
/*  57 */     tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileParticleGen/renderBlock(Lcom/brandon3055/draconicevolution/common/tileentities/TileParticleGenerator;F)V");
/*  58 */     tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
/*  59 */     tessellator.func_78370_a(255, 255, 255, 255);
/*     */ 
/*     */     
/*  62 */     float f = 0.4F;
/*  63 */     drawCornerCube(tessellator, f, f, f, 1.0F - f, inverted, stabilizerMode);
/*  64 */     drawCornerCube(tessellator, f, -f, f, 1.0F - f, inverted, stabilizerMode);
/*  65 */     drawCornerCube(tessellator, -f, f, -f, 1.0F - f, inverted, stabilizerMode);
/*  66 */     drawCornerCube(tessellator, -f, -f, -f, 1.0F - f, inverted, stabilizerMode);
/*  67 */     drawCornerCube(tessellator, -f, f, f, 1.0F - f, inverted, stabilizerMode);
/*  68 */     drawCornerCube(tessellator, f, f, -f, 1.0F - f, inverted, stabilizerMode);
/*  69 */     drawCornerCube(tessellator, f, -f, -f, 1.0F - f, inverted, stabilizerMode);
/*  70 */     drawCornerCube(tessellator, -f, -f, f, 1.0F - f, inverted, stabilizerMode);
/*     */ 
/*     */ 
/*     */     
/*  74 */     f = 0.45F;
/*  75 */     float f2 = 0.4F;
/*  76 */     drawBeamX(tessellator, 0.0F, f2, f2, 1.0F - f);
/*  77 */     drawBeamX(tessellator, 0.0F, -f2, f2, 1.0F - f);
/*  78 */     drawBeamX(tessellator, 0.0F, f2, -f2, 1.0F - f);
/*  79 */     drawBeamX(tessellator, 0.0F, -f2, -f2, 1.0F - f);
/*     */     
/*  81 */     drawBeamY(tessellator, f2, 0.0F, f2, 1.0F - f);
/*  82 */     drawBeamY(tessellator, -f2, 0.0F, f2, 1.0F - f);
/*  83 */     drawBeamY(tessellator, f2, 0.0F, -f2, 1.0F - f);
/*  84 */     drawBeamY(tessellator, -f2, 0.0F, -f2, 1.0F - f);
/*     */     
/*  86 */     drawBeamZ(tessellator, f2, f2, 0.0F, 1.0F - f);
/*  87 */     drawBeamZ(tessellator, -f2, f2, 0.0F, 1.0F - f);
/*  88 */     drawBeamZ(tessellator, f2, -f2, 0.0F, 1.0F - f);
/*  89 */     drawBeamZ(tessellator, -f2, -f2, 0.0F, 1.0F - f);
/*     */     
/*  91 */     tessellator.func_78381_a();
/*     */     
/*  93 */     GL11.glPopMatrix();
/*     */     
/*  95 */     if (stabilizerMode) {
/*  96 */       drawEnergyBeam(tessellator, tl, f3);
/*     */     }
/*     */     
/*  99 */     if (tl.beam_enabled) preRenderBeam(tessellator, tl, f3);
/*     */   
/*     */   }
/*     */   
/*     */   private void drawEnergyBeam(Tessellator tess, TileParticleGenerator gen, float f) {
/* 104 */     TileEnergyStorageCore master = gen.getMaster();
/* 105 */     if (master == null)
/* 106 */       return;  float length = 0.0F;
/*     */     
/* 108 */     GL11.glPushMatrix();
/*     */     
/* 110 */     if (master.field_145851_c > gen.field_145851_c) {
/* 111 */       GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
/* 112 */       GL11.glTranslated(-1.0D, 0.5D, 0.0D);
/* 113 */       length = (master.field_145851_c - gen.field_145851_c) - 0.2F;
/* 114 */     } else if (master.field_145851_c < gen.field_145851_c) {
/* 115 */       GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/* 116 */       GL11.glTranslated(0.0D, -0.5D, 0.0D);
/* 117 */       length = (gen.field_145851_c - master.field_145851_c) - 0.2F;
/* 118 */     } else if (master.field_145849_e > gen.field_145849_e) {
/* 119 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 120 */       GL11.glTranslated(0.0D, 0.5D, -1.0D);
/* 121 */       length = (master.field_145849_e - gen.field_145849_e) - 0.2F;
/* 122 */     } else if (master.field_145849_e < gen.field_145849_e) {
/* 123 */       GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/* 124 */       GL11.glTranslated(0.0D, -0.5D, 0.0D);
/* 125 */       length = (gen.field_145849_e - master.field_145849_e) - 0.2F;
/*     */     } 
/*     */     
/* 128 */     renderStabilizerSphere(gen);
/* 129 */     renderEnergyBeam(tess, gen, length, f);
/*     */     
/* 131 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void renderStabilizerSphere(TileParticleGenerator tile) {
/* 135 */     GL11.glPushMatrix();
/*     */     
/* 137 */     GL11.glColor4f(0.0F, 2.0F, 0.0F, 1.0F);
/* 138 */     GL11.glTranslated(0.5D, 0.0D, 0.5D);
/* 139 */     GL11.glScalef(0.4F, 0.4F, 0.4F);
/* 140 */     if (!tile.stabalizerMode) {
/* 141 */       float red = tile.beam_red / 255.0F;
/* 142 */       float green = tile.beam_green / 255.0F;
/* 143 */       float blue = tile.beam_blue / 255.0F;
/* 144 */       GL11.glColor4f(red, green, blue, 1.0F);
/* 145 */       GL11.glScalef(tile.beam_scale, tile.beam_scale, tile.beam_scale);
/*     */     } 
/*     */     
/* 148 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 200.0F, 200.0F);
/* 149 */     GL11.glDisable(2896);
/* 150 */     GL11.glDisable(2884);
/*     */     
/* 152 */     FMLClientHandler.instance().getClient().func_110434_K().func_110577_a(modelTexture);
/*     */     
/* 154 */     GL11.glRotatef(tile.rotation, 0.0F, 1.0F, 0.0F);
/* 155 */     this.stabilizerSphereModel.renderAll();
/*     */     
/* 157 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 200.0F, 200.0F);
/* 158 */     GL11.glRotatef(tile.rotation * 2.0F, 0.0F, -1.0F, 0.0F);
/* 159 */     GL11.glDisable(2896);
/* 160 */     GL11.glDepthMask(false);
/* 161 */     GL11.glColor4f(0.0F, 1.0F, 1.0F, 0.5F);
/* 162 */     if (!tile.stabalizerMode) {
/* 163 */       float red = tile.beam_red / 255.0F;
/* 164 */       float green = tile.beam_green / 255.0F;
/* 165 */       float blue = tile.beam_blue / 255.0F;
/* 166 */       GL11.glColor4f(red, green, blue, 0.5F);
/*     */     } 
/* 168 */     GL11.glEnable(3042);
/* 169 */     OpenGlHelper.func_148821_a(770, 771, 1, 0);
/* 170 */     GL11.glScalef(1.3F, 1.3F, 1.3F);
/* 171 */     this.stabilizerSphereModel.renderAll();
/* 172 */     GL11.glDisable(3042);
/* 173 */     GL11.glDepthMask(true);
/*     */     
/* 175 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void renderEnergyBeam(Tessellator tess, TileParticleGenerator tile, float length, float f) {
/* 179 */     int x = 0;
/* 180 */     int y = 0;
/* 181 */     int z = 0;
/*     */     
/* 183 */     GL11.glPushMatrix();
/* 184 */     GL11.glAlphaFunc(516, 0.1F);
/*     */     
/* 186 */     func_147499_a(this.beamTexture);
/* 187 */     GL11.glTexParameterf(3553, 10242, 10497.0F);
/* 188 */     GL11.glTexParameterf(3553, 10243, 10497.0F);
/* 189 */     GL11.glDisable(2896);
/* 190 */     GL11.glDisable(2884);
/* 191 */     GL11.glDisable(3042);
/* 192 */     GL11.glDepthMask(true);
/* 193 */     OpenGlHelper.func_148821_a(770, 1, 1, 0);
/*     */ 
/*     */     
/* 196 */     float time = tile.rotation + f;
/* 197 */     float upMot = -time * 0.2F - MathHelper.func_76141_d(-time * 0.1F);
/* 198 */     byte scaleMult = 1;
/* 199 */     double rotation = time * 0.025D * (1.0D - (scaleMult & 0x1) * 2.5D);
/*     */ 
/*     */     
/* 202 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileParticleGen/renderEnergyBeam(Lnet/minecraft/client/renderer/Tessellator;Lcom/brandon3055/draconicevolution/common/tileentities/TileParticleGenerator;FF)V");
/* 203 */     tess.func_78370_a(255, 255, 255, 32);
/*     */     
/* 205 */     double scale = scaleMult * 0.2D;
/* 206 */     double d7 = 0.5D + Math.cos(rotation + 2.356194490192345D) * scale;
/* 207 */     double d9 = 0.5D + Math.sin(rotation + 2.356194490192345D) * scale;
/* 208 */     double d11 = 0.5D + Math.cos(rotation + 0.7853981633974483D) * scale;
/* 209 */     double d13 = 0.5D + Math.sin(rotation + 0.7853981633974483D) * scale;
/* 210 */     double d15 = 0.5D + Math.cos(rotation + 3.9269908169872414D) * scale;
/* 211 */     double d17 = 0.5D + Math.sin(rotation + 3.9269908169872414D) * scale;
/* 212 */     double d19 = 0.5D + Math.cos(rotation + 5.497787143782138D) * scale;
/* 213 */     double d21 = 0.5D + Math.sin(rotation + 5.497787143782138D) * scale;
/* 214 */     double height = length;
/* 215 */     double texXMin = 0.0D;
/* 216 */     double texXMax = 1.0D;
/* 217 */     double d28 = (-1.0F + upMot);
/* 218 */     double texHeight = length * 0.5D / scale + d28;
/*     */     
/* 220 */     tess.func_78374_a(x + d7, y + height, z + d9, texXMax, texHeight);
/* 221 */     tess.func_78374_a(x + d7, y, z + d9, texXMax, d28);
/* 222 */     tess.func_78374_a(x + d11, y, z + d13, texXMin, d28);
/* 223 */     tess.func_78374_a(x + d11, y + height, z + d13, texXMin, texHeight);
/*     */     
/* 225 */     tess.func_78374_a(x + d19, y + height, z + d21, texXMax, texHeight);
/* 226 */     tess.func_78374_a(x + d19, y, z + d21, texXMax, d28);
/* 227 */     tess.func_78374_a(x + d15, y, z + d17, texXMin, d28);
/* 228 */     tess.func_78374_a(x + d15, y + height, z + d17, texXMin, texHeight);
/*     */     
/* 230 */     tess.func_78374_a(x + d11, y + height, z + d13, texXMax, texHeight);
/* 231 */     tess.func_78374_a(x + d11, y, z + d13, texXMax, d28);
/* 232 */     tess.func_78374_a(x + d19, y, z + d21, texXMin, d28);
/* 233 */     tess.func_78374_a(x + d19, y + height, z + d21, texXMin, texHeight);
/*     */     
/* 235 */     tess.func_78374_a(x + d15, y + height, z + d17, texXMax, texHeight);
/* 236 */     tess.func_78374_a(x + d15, y, z + d17, texXMax, d28);
/* 237 */     tess.func_78374_a(x + d7, y, z + d9, texXMin, d28);
/* 238 */     tess.func_78374_a(x + d7, y + height, z + d9, texXMin, texHeight);
/*     */     
/* 240 */     rotation += 0.7699999809265137D;
/* 241 */     d7 = 0.5D + Math.cos(rotation + 2.356194490192345D) * scale;
/* 242 */     d9 = 0.5D + Math.sin(rotation + 2.356194490192345D) * scale;
/* 243 */     d11 = 0.5D + Math.cos(rotation + 0.7853981633974483D) * scale;
/* 244 */     d13 = 0.5D + Math.sin(rotation + 0.7853981633974483D) * scale;
/* 245 */     d15 = 0.5D + Math.cos(rotation + 3.9269908169872414D) * scale;
/* 246 */     d17 = 0.5D + Math.sin(rotation + 3.9269908169872414D) * scale;
/* 247 */     d19 = 0.5D + Math.cos(rotation + 5.497787143782138D) * scale;
/* 248 */     d21 = 0.5D + Math.sin(rotation + 5.497787143782138D) * scale;
/*     */     
/* 250 */     d28 = (-1.0F + upMot * 1.0F);
/* 251 */     texHeight = length * 0.5D / scale + d28;
/*     */     
/* 253 */     tess.func_78369_a(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 255 */     tess.func_78374_a(x + d7, y + height, z + d9, texXMax, texHeight);
/* 256 */     tess.func_78374_a(x + d7, y, z + d9, texXMax, d28);
/* 257 */     tess.func_78374_a(x + d11, y, z + d13, texXMin, d28);
/* 258 */     tess.func_78374_a(x + d11, y + height, z + d13, texXMin, texHeight);
/*     */     
/* 260 */     tess.func_78374_a(x + d19, y + height, z + d21, texXMax, texHeight);
/* 261 */     tess.func_78374_a(x + d19, y, z + d21, texXMax, d28);
/* 262 */     tess.func_78374_a(x + d15, y, z + d17, texXMin, d28);
/* 263 */     tess.func_78374_a(x + d15, y + height, z + d17, texXMin, texHeight);
/*     */     
/* 265 */     tess.func_78374_a(x + d11, y + height, z + d13, texXMax, texHeight);
/* 266 */     tess.func_78374_a(x + d11, y, z + d13, texXMax, d28);
/* 267 */     tess.func_78374_a(x + d19, y, z + d21, texXMin, d28);
/* 268 */     tess.func_78374_a(x + d19, y + height, z + d21, texXMin, texHeight);
/*     */     
/* 270 */     tess.func_78374_a(x + d15, y + height, z + d17, texXMax, texHeight);
/* 271 */     tess.func_78374_a(x + d15, y, z + d17, texXMax, d28);
/* 272 */     tess.func_78374_a(x + d7, y, z + d9, texXMin, d28);
/* 273 */     tess.func_78374_a(x + d7, y + height, z + d9, texXMin, texHeight);
/*     */     
/* 275 */     tess.func_78381_a();
/* 276 */     GL11.glPushMatrix();
/*     */     
/* 278 */     GL11.glTranslated(0.0D, 0.4D, 0.0D);
/* 279 */     length -= 0.5F;
/*     */     
/* 281 */     GL11.glEnable(3042);
/* 282 */     OpenGlHelper.func_148821_a(770, 771, 1, 0);
/* 283 */     GL11.glDepthMask(false);
/* 284 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileParticleGen/renderEnergyBeam(Lnet/minecraft/client/renderer/Tessellator;Lcom/brandon3055/draconicevolution/common/tileentities/TileParticleGenerator;FF)V");
/* 285 */     tess.func_78370_a(255, 255, 255, 32);
/* 286 */     double d30 = 0.2D;
/* 287 */     double d4 = 0.2D;
/* 288 */     double d6 = 0.8D;
/* 289 */     double d8 = 0.2D;
/* 290 */     double d10 = 0.2D;
/* 291 */     double d12 = 0.8D;
/* 292 */     double d14 = 0.8D;
/* 293 */     double d16 = 0.8D;
/* 294 */     double d18 = length;
/* 295 */     double d20 = 0.0D;
/* 296 */     double d22 = 1.0D;
/* 297 */     double d24 = (-1.0F + upMot);
/* 298 */     double d26 = length + d24;
/* 299 */     tess.func_78374_a(x + d30, y + d18, z + d4, d22, d26);
/* 300 */     tess.func_78374_a(x + d30, y, z + d4, d22, d24);
/* 301 */     tess.func_78374_a(x + d6, y, z + d8, d20, d24);
/* 302 */     tess.func_78374_a(x + d6, y + d18, z + d8, d20, d26);
/* 303 */     tess.func_78374_a(x + d14, y + d18, z + d16, d22, d26);
/* 304 */     tess.func_78374_a(x + d14, y, z + d16, d22, d24);
/* 305 */     tess.func_78374_a(x + d10, y, z + d12, d20, d24);
/* 306 */     tess.func_78374_a(x + d10, y + d18, z + d12, d20, d26);
/* 307 */     tess.func_78374_a(x + d6, y + d18, z + d8, d22, d26);
/* 308 */     tess.func_78374_a(x + d6, y, z + d8, d22, d24);
/* 309 */     tess.func_78374_a(x + d14, y, z + d16, d20, d24);
/* 310 */     tess.func_78374_a(x + d14, y + d18, z + d16, d20, d26);
/* 311 */     tess.func_78374_a(x + d10, y + d18, z + d12, d22, d26);
/* 312 */     tess.func_78374_a(x + d10, y, z + d12, d22, d24);
/* 313 */     tess.func_78374_a(x + d30, y, z + d4, d20, d24);
/* 314 */     tess.func_78374_a(x + d30, y + d18, z + d4, d20, d26);
/* 315 */     tess.func_78381_a();
/* 316 */     GL11.glPopMatrix();
/*     */     
/* 318 */     GL11.glEnable(2896);
/* 319 */     GL11.glEnable(3553);
/* 320 */     GL11.glDepthMask(true);
/*     */     
/* 322 */     GL11.glEnable(2896);
/* 323 */     GL11.glEnable(3553);
/* 324 */     GL11.glDisable(3042);
/* 325 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawCornerCube(Tessellator tess, float x, float y, float z, float FP, boolean inverted, boolean stabalizerMode) {
/* 330 */     float srcXMin = inverted ? (38.0F * this.pxl) : (32.0F * this.pxl);
/* 331 */     float srcYMin = 0.0F;
/* 332 */     float srcXMax = inverted ? (44.0F * this.pxl) : (38.0F * this.pxl);
/* 333 */     float srcYMax = 6.0F * this.pxl;
/*     */     
/* 335 */     float FN = 1.0F - FP;
/*     */     
/* 337 */     if (stabalizerMode) {
/* 338 */       srcXMin = 44.0F * this.pxl;
/* 339 */       srcXMax = 50.0F * this.pxl;
/*     */     } 
/*     */ 
/*     */     
/* 343 */     tess.func_78374_a((FP + x), (FN + y), (FN + z), srcXMin, srcYMin);
/* 344 */     tess.func_78374_a((FP + x), (FP + y), (FN + z), srcXMax, srcYMin);
/* 345 */     tess.func_78374_a((FP + x), (FP + y), (FP + z), srcXMax, srcYMax);
/* 346 */     tess.func_78374_a((FP + x), (FN + y), (FP + z), srcXMin, srcYMax);
/*     */ 
/*     */     
/* 349 */     tess.func_78374_a((FN + x), (FN + y), (FP + z), srcXMin, srcYMin);
/* 350 */     tess.func_78374_a((FN + x), (FP + y), (FP + z), srcXMax, srcYMin);
/* 351 */     tess.func_78374_a((FN + x), (FP + y), (FN + z), srcXMax, srcYMax);
/* 352 */     tess.func_78374_a((FN + x), (FN + y), (FN + z), srcXMin, srcYMax);
/*     */ 
/*     */     
/* 355 */     tess.func_78374_a((FN + x), (FP + y), (FN + z), srcXMin, srcYMin);
/* 356 */     tess.func_78374_a((FN + x), (FP + y), (FP + z), srcXMax, srcYMin);
/* 357 */     tess.func_78374_a((FP + x), (FP + y), (FP + z), srcXMax, srcYMax);
/* 358 */     tess.func_78374_a((FP + x), (FP + y), (FN + z), srcXMin, srcYMax);
/*     */ 
/*     */     
/* 361 */     tess.func_78374_a((FN + x), (FN + y), (FN + z), srcXMin, srcYMin);
/* 362 */     tess.func_78374_a((FP + x), (FN + y), (FN + z), srcXMax, srcYMin);
/* 363 */     tess.func_78374_a((FP + x), (FN + y), (FP + z), srcXMax, srcYMax);
/* 364 */     tess.func_78374_a((FN + x), (FN + y), (FP + z), srcXMin, srcYMax);
/*     */ 
/*     */     
/* 367 */     tess.func_78374_a((FP + x), (FN + y), (FP + z), srcXMin, srcYMin);
/* 368 */     tess.func_78374_a((FP + x), (FP + y), (FP + z), srcXMax, srcYMin);
/* 369 */     tess.func_78374_a((FN + x), (FP + y), (FP + z), srcXMax, srcYMax);
/* 370 */     tess.func_78374_a((FN + x), (FN + y), (FP + z), srcXMin, srcYMax);
/*     */ 
/*     */     
/* 373 */     tess.func_78374_a((FN + x), (FN + y), (FN + z), srcXMin, srcYMin);
/* 374 */     tess.func_78374_a((FN + x), (FP + y), (FN + z), srcXMax, srcYMin);
/* 375 */     tess.func_78374_a((FP + x), (FP + y), (FN + z), srcXMax, srcYMax);
/* 376 */     tess.func_78374_a((FP + x), (FN + y), (FN + z), srcXMin, srcYMax);
/*     */   }
/*     */   
/*     */   private void drawBeamX(Tessellator tess, float x, float y, float z, float FP) {
/* 380 */     float srcXMin = 0.0F;
/* 381 */     float srcYMin = 0.0F;
/* 382 */     float srcXMax = 32.0F * this.pxl;
/* 383 */     float srcYMax = 4.0F * this.pxl;
/* 384 */     float FN = 1.0F - FP;
/*     */     
/* 386 */     float XX = 0.9F;
/* 387 */     float XM = 0.1F;
/*     */ 
/*     */     
/* 390 */     tess.func_78374_a(XM, (FP + y), (FN + z), srcXMin, srcYMax);
/* 391 */     tess.func_78374_a(XM, (FP + y), (FP + z), srcXMin, srcYMin);
/* 392 */     tess.func_78374_a(XX, (FP + y), (FP + z), srcXMax, srcYMin);
/* 393 */     tess.func_78374_a(XX, (FP + y), (FN + z), srcXMax, srcYMax);
/*     */ 
/*     */     
/* 396 */     tess.func_78374_a(XM, (FN + y), (FN + z), srcXMin, srcYMin);
/* 397 */     tess.func_78374_a(XX, (FN + y), (FN + z), srcXMax, srcYMin);
/* 398 */     tess.func_78374_a(XX, (FN + y), (FP + z), srcXMax, srcYMax);
/* 399 */     tess.func_78374_a(XM, (FN + y), (FP + z), srcXMin, srcYMax);
/*     */ 
/*     */     
/* 402 */     tess.func_78374_a(XX, (FN + y), (FP + z), srcXMin, srcYMax);
/* 403 */     tess.func_78374_a(XX, (FP + y), (FP + z), srcXMin, srcYMin);
/* 404 */     tess.func_78374_a(XM, (FP + y), (FP + z), srcXMax, srcYMin);
/* 405 */     tess.func_78374_a(XM, (FN + y), (FP + z), srcXMax, srcYMax);
/*     */ 
/*     */     
/* 408 */     tess.func_78374_a(XM, (FN + y), (FN + z), srcXMin, srcYMax);
/* 409 */     tess.func_78374_a(XM, (FP + y), (FN + z), srcXMin, srcYMin);
/* 410 */     tess.func_78374_a(XX, (FP + y), (FN + z), srcXMax, srcYMin);
/* 411 */     tess.func_78374_a(XX, (FN + y), (FN + z), srcXMax, srcYMax);
/*     */   }
/*     */   
/*     */   private void drawBeamY(Tessellator tess, float x, float y, float z, float FP) {
/* 415 */     float srcXMin = 0.0F;
/* 416 */     float srcYMin = 0.0F;
/* 417 */     float srcXMax = 32.0F * this.pxl;
/* 418 */     float srcYMax = 4.0F * this.pxl;
/* 419 */     float FN = 1.0F - FP;
/*     */     
/* 421 */     float XX = 0.9F;
/* 422 */     float XM = 0.1F;
/*     */ 
/*     */     
/* 425 */     tess.func_78374_a((FP + x), XM, (FN + z), srcXMin, srcYMin);
/* 426 */     tess.func_78374_a((FP + x), XX, (FN + z), srcXMax, srcYMin);
/* 427 */     tess.func_78374_a((FP + x), XX, (FP + z), srcXMax, srcYMax);
/* 428 */     tess.func_78374_a((FP + x), XM, (FP + z), srcXMin, srcYMax);
/*     */ 
/*     */     
/* 431 */     tess.func_78374_a((FN + x), XM, (FP + z), srcXMin, srcYMin);
/* 432 */     tess.func_78374_a((FN + x), XX, (FP + z), srcXMax, srcYMin);
/* 433 */     tess.func_78374_a((FN + x), XX, (FN + z), srcXMax, srcYMax);
/* 434 */     tess.func_78374_a((FN + x), XM, (FN + z), srcXMin, srcYMax);
/*     */ 
/*     */     
/* 437 */     tess.func_78374_a((FP + x), XM, (FP + z), srcXMin, srcYMin);
/* 438 */     tess.func_78374_a((FP + x), XX, (FP + z), srcXMax, srcYMin);
/* 439 */     tess.func_78374_a((FN + x), XX, (FP + z), srcXMax, srcYMax);
/* 440 */     tess.func_78374_a((FN + x), XM, (FP + z), srcXMin, srcYMax);
/*     */ 
/*     */     
/* 443 */     tess.func_78374_a((FN + x), XM, (FN + z), srcXMin, srcYMin);
/* 444 */     tess.func_78374_a((FN + x), XX, (FN + z), srcXMax, srcYMin);
/* 445 */     tess.func_78374_a((FP + x), XX, (FN + z), srcXMax, srcYMax);
/* 446 */     tess.func_78374_a((FP + x), XM, (FN + z), srcXMin, srcYMax);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawBeamZ(Tessellator tess, float x, float y, float z, float FP) {
/* 451 */     float srcXMin = 0.0F;
/* 452 */     float srcYMin = 0.0F;
/* 453 */     float srcXMax = 32.0F * this.pxl;
/* 454 */     float srcYMax = 4.0F * this.pxl;
/* 455 */     float FN = 1.0F - FP;
/*     */     
/* 457 */     float XX = 0.9F;
/* 458 */     float XM = 0.1F;
/*     */ 
/*     */     
/* 461 */     tess.func_78374_a((FP + x), (FN + y), XM, srcXMin, srcYMax);
/* 462 */     tess.func_78374_a((FP + x), (FP + y), XM, srcXMin, srcYMin);
/* 463 */     tess.func_78374_a((FP + x), (FP + y), XX, srcXMax, srcYMin);
/* 464 */     tess.func_78374_a((FP + x), (FN + y), XX, srcXMax, srcYMax);
/*     */ 
/*     */     
/* 467 */     tess.func_78374_a((FN + x), (FN + y), XX, srcXMin, srcYMax);
/* 468 */     tess.func_78374_a((FN + x), (FP + y), XX, srcXMin, srcYMin);
/* 469 */     tess.func_78374_a((FN + x), (FP + y), XM, srcXMax, srcYMin);
/* 470 */     tess.func_78374_a((FN + x), (FN + y), XM, srcXMax, srcYMax);
/*     */ 
/*     */     
/* 473 */     tess.func_78374_a((FN + x), (FP + y), XM, srcXMin, srcYMin);
/* 474 */     tess.func_78374_a((FN + x), (FP + y), XX, srcXMax, srcYMin);
/* 475 */     tess.func_78374_a((FP + x), (FP + y), XX, srcXMax, srcYMax);
/* 476 */     tess.func_78374_a((FP + x), (FP + y), XM, srcXMin, srcYMax);
/*     */ 
/*     */     
/* 479 */     tess.func_78374_a((FN + x), (FN + y), XM, srcXMin, srcYMax);
/* 480 */     tess.func_78374_a((FP + x), (FN + y), XM, srcXMin, srcYMin);
/* 481 */     tess.func_78374_a((FP + x), (FN + y), XX, srcXMax, srcYMin);
/* 482 */     tess.func_78374_a((FN + x), (FN + y), XX, srcXMax, srcYMax);
/*     */   }
/*     */ 
/*     */   
/*     */   private void preRenderBeam(Tessellator tess, TileParticleGenerator gen, float f) {
/* 487 */     GL11.glPushMatrix();
/*     */     
/* 489 */     GL11.glTranslated(0.0D, 0.5D, 0.5D);
/* 490 */     GL11.glRotatef(90.0F + gen.beam_pitch, 1.0F, 0.0F, 0.0F);
/* 491 */     GL11.glTranslated(0.0D, 0.0D, -0.5D);
/*     */     
/* 493 */     GL11.glTranslated(0.5D, 0.0D, 0.0D);
/* 494 */     GL11.glRotatef(gen.beam_yaw, 0.0F, 0.0F, 1.0F);
/* 495 */     GL11.glTranslated(-0.5D, 0.0D, 0.0D);
/*     */     
/* 497 */     renderBeam(tess, gen, f);
/* 498 */     GL11.glPopMatrix();
/*     */     
/* 500 */     if (gen.render_core) {
/* 501 */       GL11.glPushMatrix();
/* 502 */       GL11.glTranslated(0.0D, 0.5D, 0.0D);
/* 503 */       renderStabilizerSphere(gen);
/* 504 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderBeam(Tessellator tess, TileParticleGenerator tile, float f) {
/* 509 */     int x = 0;
/* 510 */     int y = 0;
/* 511 */     int z = 0;
/* 512 */     double length = tile.beam_length;
/* 513 */     float red = tile.beam_red / 255.0F;
/* 514 */     float green = tile.beam_green / 255.0F;
/* 515 */     float blue = tile.beam_blue / 255.0F;
/*     */     
/* 517 */     GL11.glPushMatrix();
/* 518 */     GL11.glAlphaFunc(516, 0.1F);
/*     */     
/* 520 */     func_147499_a(this.beamTexture);
/* 521 */     GL11.glTexParameterf(3553, 10242, 10497.0F);
/* 522 */     GL11.glTexParameterf(3553, 10243, 10497.0F);
/* 523 */     GL11.glDisable(2896);
/* 524 */     GL11.glDisable(2884);
/* 525 */     GL11.glDisable(3042);
/* 526 */     GL11.glDepthMask(true);
/* 527 */     OpenGlHelper.func_148821_a(770, 1, 1, 0);
/*     */ 
/*     */     
/* 530 */     float time = tile.rotation + f;
/* 531 */     float upMot = -time * 0.2F - MathHelper.func_76141_d(-time * 0.1F);
/* 532 */     double rotation = (tile.beam_rotation * (tile.rotation + f * 0.5F));
/*     */ 
/*     */     
/* 535 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileParticleGen/renderBeam(Lnet/minecraft/client/renderer/Tessellator;Lcom/brandon3055/draconicevolution/common/tileentities/TileParticleGenerator;F)V");
/* 536 */     tess.func_78380_c(200);
/* 537 */     tess.func_78370_a(tile.beam_red, tile.beam_green, tile.beam_blue, 32);
/*     */     
/* 539 */     double scale = tile.beam_scale * 0.2D;
/* 540 */     double d7 = 0.5D + Math.cos(rotation + 2.356194490192345D) * scale;
/* 541 */     double d9 = 0.5D + Math.sin(rotation + 2.356194490192345D) * scale;
/* 542 */     double d11 = 0.5D + Math.cos(rotation + 0.7853981633974483D) * scale;
/* 543 */     double d13 = 0.5D + Math.sin(rotation + 0.7853981633974483D) * scale;
/* 544 */     double d15 = 0.5D + Math.cos(rotation + 3.9269908169872414D) * scale;
/* 545 */     double d17 = 0.5D + Math.sin(rotation + 3.9269908169872414D) * scale;
/* 546 */     double d19 = 0.5D + Math.cos(rotation + 5.497787143782138D) * scale;
/* 547 */     double d21 = 0.5D + Math.sin(rotation + 5.497787143782138D) * scale;
/* 548 */     double texXMin = 0.0D;
/* 549 */     double texXMax = 1.0D;
/* 550 */     double d28 = (-1.0F + upMot);
/* 551 */     double texHeight = length * 0.5D / scale + d28;
/*     */     
/* 553 */     tess.func_78374_a(x + d7, y + length, z + d9, texXMax, texHeight);
/* 554 */     tess.func_78374_a(x + d7, y, z + d9, texXMax, d28);
/* 555 */     tess.func_78374_a(x + d11, y, z + d13, texXMin, d28);
/* 556 */     tess.func_78374_a(x + d11, y + length, z + d13, texXMin, texHeight);
/*     */     
/* 558 */     tess.func_78374_a(x + d19, y + length, z + d21, texXMax, texHeight);
/* 559 */     tess.func_78374_a(x + d19, y, z + d21, texXMax, d28);
/* 560 */     tess.func_78374_a(x + d15, y, z + d17, texXMin, d28);
/* 561 */     tess.func_78374_a(x + d15, y + length, z + d17, texXMin, texHeight);
/*     */     
/* 563 */     tess.func_78374_a(x + d11, y + length, z + d13, texXMax, texHeight);
/* 564 */     tess.func_78374_a(x + d11, y, z + d13, texXMax, d28);
/* 565 */     tess.func_78374_a(x + d19, y, z + d21, texXMin, d28);
/* 566 */     tess.func_78374_a(x + d19, y + length, z + d21, texXMin, texHeight);
/*     */     
/* 568 */     tess.func_78374_a(x + d15, y + length, z + d17, texXMax, texHeight);
/* 569 */     tess.func_78374_a(x + d15, y, z + d17, texXMax, d28);
/* 570 */     tess.func_78374_a(x + d7, y, z + d9, texXMin, d28);
/* 571 */     tess.func_78374_a(x + d7, y + length, z + d9, texXMin, texHeight);
/*     */     
/* 573 */     rotation += 0.7699999809265137D;
/* 574 */     d7 = 0.5D + Math.cos(rotation + 2.356194490192345D) * scale;
/* 575 */     d9 = 0.5D + Math.sin(rotation + 2.356194490192345D) * scale;
/* 576 */     d11 = 0.5D + Math.cos(rotation + 0.7853981633974483D) * scale;
/* 577 */     d13 = 0.5D + Math.sin(rotation + 0.7853981633974483D) * scale;
/* 578 */     d15 = 0.5D + Math.cos(rotation + 3.9269908169872414D) * scale;
/* 579 */     d17 = 0.5D + Math.sin(rotation + 3.9269908169872414D) * scale;
/* 580 */     d19 = 0.5D + Math.cos(rotation + 5.497787143782138D) * scale;
/* 581 */     d21 = 0.5D + Math.sin(rotation + 5.497787143782138D) * scale;
/*     */     
/* 583 */     d28 = (-1.0F + upMot * 1.0F);
/* 584 */     texHeight = length * 0.5D / scale + d28;
/*     */     
/* 586 */     tess.func_78369_a(red, green, blue, 1.0F);
/*     */     
/* 588 */     tess.func_78374_a(x + d7, y + length, z + d9, texXMax, texHeight);
/* 589 */     tess.func_78374_a(x + d7, y, z + d9, texXMax, d28);
/* 590 */     tess.func_78374_a(x + d11, y, z + d13, texXMin, d28);
/* 591 */     tess.func_78374_a(x + d11, y + length, z + d13, texXMin, texHeight);
/*     */     
/* 593 */     tess.func_78374_a(x + d19, y + length, z + d21, texXMax, texHeight);
/* 594 */     tess.func_78374_a(x + d19, y, z + d21, texXMax, d28);
/* 595 */     tess.func_78374_a(x + d15, y, z + d17, texXMin, d28);
/* 596 */     tess.func_78374_a(x + d15, y + length, z + d17, texXMin, texHeight);
/*     */     
/* 598 */     tess.func_78374_a(x + d11, y + length, z + d13, texXMax, texHeight);
/* 599 */     tess.func_78374_a(x + d11, y, z + d13, texXMax, d28);
/* 600 */     tess.func_78374_a(x + d19, y, z + d21, texXMin, d28);
/* 601 */     tess.func_78374_a(x + d19, y + length, z + d21, texXMin, texHeight);
/*     */     
/* 603 */     tess.func_78374_a(x + d15, y + length, z + d17, texXMax, texHeight);
/* 604 */     tess.func_78374_a(x + d15, y, z + d17, texXMax, d28);
/* 605 */     tess.func_78374_a(x + d7, y, z + d9, texXMin, d28);
/* 606 */     tess.func_78374_a(x + d7, y + length, z + d9, texXMin, texHeight);
/*     */     
/* 608 */     tess.func_78381_a();
/* 609 */     GL11.glPushMatrix();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 614 */     GL11.glEnable(3042);
/* 615 */     OpenGlHelper.func_148821_a(770, 771, 1, 0);
/* 616 */     GL11.glDepthMask(false);
/* 617 */     GL11.glTranslated(0.5D, 0.0D, 0.5D);
/* 618 */     GL11.glScalef(tile.beam_scale, 1.0F, tile.beam_scale);
/* 619 */     GL11.glTranslated(-0.5D, 0.0D, -0.5D);
/* 620 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileParticleGen/renderBeam(Lnet/minecraft/client/renderer/Tessellator;Lcom/brandon3055/draconicevolution/common/tileentities/TileParticleGenerator;F)V");
/* 621 */     tess.func_78370_a(tile.beam_red, tile.beam_green, tile.beam_blue, 32);
/* 622 */     double d30 = 0.2D;
/* 623 */     double d4 = 0.2D;
/* 624 */     double d6 = 0.8D;
/* 625 */     double d8 = 0.2D;
/* 626 */     double d10 = 0.2D;
/* 627 */     double d12 = 0.8D;
/* 628 */     double d14 = 0.8D;
/* 629 */     double d16 = 0.8D;
/* 630 */     double d20 = 0.0D;
/* 631 */     double d22 = 1.0D;
/* 632 */     double d24 = (-1.0F + upMot);
/* 633 */     double d26 = length + d24;
/* 634 */     tess.func_78374_a(x + d30, y + length, z + d4, d22, d26);
/* 635 */     tess.func_78374_a(x + d30, y, z + d4, d22, d24);
/* 636 */     tess.func_78374_a(x + d6, y, z + d8, d20, d24);
/* 637 */     tess.func_78374_a(x + d6, y + length, z + d8, d20, d26);
/* 638 */     tess.func_78374_a(x + d14, y + length, z + d16, d22, d26);
/* 639 */     tess.func_78374_a(x + d14, y, z + d16, d22, d24);
/* 640 */     tess.func_78374_a(x + d10, y, z + d12, d20, d24);
/* 641 */     tess.func_78374_a(x + d10, y + length, z + d12, d20, d26);
/* 642 */     tess.func_78374_a(x + d6, y + length, z + d8, d22, d26);
/* 643 */     tess.func_78374_a(x + d6, y, z + d8, d22, d24);
/* 644 */     tess.func_78374_a(x + d14, y, z + d16, d20, d24);
/* 645 */     tess.func_78374_a(x + d14, y + length, z + d16, d20, d26);
/* 646 */     tess.func_78374_a(x + d10, y + length, z + d12, d22, d26);
/* 647 */     tess.func_78374_a(x + d10, y, z + d12, d22, d24);
/* 648 */     tess.func_78374_a(x + d30, y, z + d4, d20, d24);
/* 649 */     tess.func_78374_a(x + d30, y + length, z + d4, d20, d26);
/* 650 */     tess.func_78381_a();
/* 651 */     GL11.glPopMatrix();
/*     */     
/* 653 */     GL11.glEnable(2896);
/* 654 */     GL11.glEnable(3553);
/* 655 */     GL11.glDepthMask(true);
/*     */ 
/*     */ 
/*     */     
/* 659 */     GL11.glDisable(3042);
/* 660 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\tile\RenderTileParticleGen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */