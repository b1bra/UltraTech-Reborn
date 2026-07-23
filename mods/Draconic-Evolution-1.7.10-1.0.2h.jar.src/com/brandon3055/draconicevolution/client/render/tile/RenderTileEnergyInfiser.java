/*     */ package com.brandon3055.draconicevolution.client.render.tile;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileEnergyInfuser;
/*     */ import com.gamerforea.client.util.TessellatorDebuggerUtils;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ public class RenderTileEnergyInfiser
/*     */   extends TileEntitySpecialRenderer {
/*  18 */   private final ResourceLocation texture = new ResourceLocation("draconicevolution", "textures/models/EnergyInfuserTextureSheet.png");
/*     */   
/*  20 */   private static float pxl = 0.00390625F;
/*     */ 
/*     */   
/*     */   public void func_147500_a(TileEntity tileEntity, double x, double y, double z, float f) {
/*  24 */     GL11.glPushMatrix();
/*     */     
/*  26 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/*  27 */     TileEnergyInfuser tile = (TileEnergyInfuser)tileEntity;
/*  28 */     renderBlock(tile);
/*     */     
/*  30 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void renderBlock(TileEnergyInfuser tile) {
/*  34 */     Tessellator tessellator = Tessellator.field_78398_a;
/*  35 */     func_147499_a(this.texture);
/*     */     
/*  37 */     tessellator.func_78370_a(255, 255, 255, 255);
/*     */     
/*  39 */     GL11.glEnable(3008);
/*  40 */     GL11.glAlphaFunc(516, 0.1F);
/*  41 */     drawBase(tessellator);
/*  42 */     drawWings(tessellator, tile);
/*  43 */     renderChargingItem(tile);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawBase(Tessellator tess) {
/*  48 */     GL11.glPushMatrix();
/*  49 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileEnergyInfiser/drawBase(Lnet/minecraft/client/renderer/Tessellator;)V");
/*  50 */     tess.func_78375_b(0.0F, 0.0F, 1.0F);
/*     */     
/*  52 */     double srcXMin = 0.0D;
/*  53 */     double srcYMin = 0.0D;
/*  54 */     double srcXMax = 64.0D * pxl;
/*  55 */     double srcYMax = 64.0D * pxl;
/*     */     
/*  57 */     tess.func_78374_a(0.0D, 5.0E-4D, 0.0D, srcXMin, srcYMin);
/*  58 */     tess.func_78374_a(1.0D, 5.0E-4D, 0.0D, srcXMax, srcYMin);
/*  59 */     tess.func_78374_a(1.0D, 5.0E-4D, 1.0D, srcXMax, srcYMax);
/*  60 */     tess.func_78374_a(0.0D, 5.0E-4D, 1.0D, srcXMin, srcYMax);
/*     */ 
/*     */     
/*  63 */     srcXMin = 128.0D * pxl;
/*  64 */     srcYMin = 0.0D;
/*  65 */     srcXMax = 192.0D * pxl;
/*  66 */     srcYMax = 64.0D * pxl;
/*     */     
/*  68 */     tess.func_78374_a(0.0D, 0.3745D, 0.0D, srcXMin, srcYMin);
/*  69 */     tess.func_78374_a(0.0D, 0.3745D, 1.0D, srcXMax, srcYMin);
/*  70 */     tess.func_78374_a(1.0D, 0.3745D, 1.0D, srcXMax, srcYMax);
/*  71 */     tess.func_78374_a(1.0D, 0.3745D, 0.0D, srcXMin, srcYMax);
/*     */     
/*  73 */     srcXMin = 64.0D * pxl;
/*  74 */     srcYMin = 0.0D;
/*  75 */     srcXMax = 128.0D * pxl;
/*  76 */     srcYMax = 24.0D * pxl;
/*     */     
/*  78 */     tess.func_78374_a(1.0D, 0.0D, 0.0D, srcXMin, srcYMin);
/*  79 */     tess.func_78374_a(0.0D, 0.0D, 0.0D, srcXMax, srcYMin);
/*  80 */     tess.func_78374_a(0.0D, 0.375D, 0.0D, srcXMax, srcYMax);
/*  81 */     tess.func_78374_a(1.0D, 0.375D, 0.0D, srcXMin, srcYMax);
/*     */     
/*  83 */     tess.func_78374_a(1.0D, 0.0D, 1.0D, srcXMin, srcYMin);
/*  84 */     tess.func_78374_a(1.0D, 0.375D, 1.0D, srcXMin, srcYMax);
/*  85 */     tess.func_78374_a(0.0D, 0.375D, 1.0D, srcXMax, srcYMax);
/*  86 */     tess.func_78374_a(0.0D, 0.0D, 1.0D, srcXMax, srcYMin);
/*     */     
/*  88 */     tess.func_78374_a(0.0D, 0.375D, 1.0D, srcXMin, srcYMin);
/*  89 */     tess.func_78374_a(0.0D, 0.375D, 0.0D, srcXMax, srcYMin);
/*  90 */     tess.func_78374_a(0.0D, 0.0D, 0.0D, srcXMax, srcYMax);
/*  91 */     tess.func_78374_a(0.0D, 0.0D, 1.0D, srcXMin, srcYMax);
/*     */     
/*  93 */     tess.func_78374_a(1.0D, 0.375D, 1.0D, srcXMin, srcYMin);
/*  94 */     tess.func_78374_a(1.0D, 0.0D, 1.0D, srcXMin, srcYMax);
/*  95 */     tess.func_78374_a(1.0D, 0.0D, 0.0D, srcXMax, srcYMax);
/*  96 */     tess.func_78374_a(1.0D, 0.375D, 0.0D, srcXMax, srcYMin);
/*     */     
/*  98 */     tess.func_78381_a();
/*  99 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawWings(Tessellator tess, TileEnergyInfuser tile) {
/* 104 */     GL11.glPushMatrix();
/* 105 */     float srcXMin = 0.0F;
/* 106 */     float srcYMin = 64.0F * pxl;
/* 107 */     float srcXMax = 92.0F * pxl;
/* 108 */     float srcYMax = 115.0F * pxl;
/*     */     
/* 110 */     GL11.glTranslatef(-0.64F, 0.365F, 0.51F);
/* 111 */     GL11.glScalef(0.7F, 0.7F, 0.7F);
/* 112 */     float xTrans = 0.025F;
/*     */     
/* 114 */     GL11.glTranslatef(1.62F, 0.0F, -xTrans);
/* 115 */     GL11.glRotatef(tile.rotation, 0.0F, 1.0F, 0.0F);
/* 116 */     GL11.glTranslatef(-1.62F, 0.0F, xTrans);
/* 117 */     render2DWithThicness(tess, srcXMax, srcYMin, srcXMin, srcYMax, 92, 51, 0.0625F);
/*     */     
/* 119 */     GL11.glTranslatef(1.62F, 0.0F, -xTrans);
/* 120 */     GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 121 */     GL11.glTranslatef(-1.62F, 0.0F, xTrans);
/* 122 */     render2DWithThicness(tess, srcXMax, srcYMin, srcXMin, srcYMax, 92, 51, 0.0625F);
/*     */     
/* 124 */     GL11.glTranslatef(1.62F, 0.0F, -xTrans);
/* 125 */     GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 126 */     GL11.glTranslatef(-1.62F, 0.0F, xTrans);
/* 127 */     render2DWithThicness(tess, srcXMax, srcYMin, srcXMin, srcYMax, 92, 51, 0.0625F);
/*     */     
/* 129 */     GL11.glTranslatef(1.62F, 0.0F, -xTrans);
/* 130 */     GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 131 */     GL11.glTranslatef(-1.62F, 0.0F, xTrans);
/* 132 */     render2DWithThicness(tess, srcXMax, srcYMin, srcXMin, srcYMax, 92, 51, 0.0625F);
/*     */     
/* 134 */     srcXMin = 64.0F * pxl;
/* 135 */     srcYMin = 24.0F * pxl;
/* 136 */     srcXMax = 96.0F * pxl;
/* 137 */     srcYMax = 56.0F * pxl;
/* 138 */     GL11.glTranslatef(1.26F, 0.0F, 0.31F);
/* 139 */     GL11.glTranslatef(0.0F, 0.0F, 0.0F);
/* 140 */     GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 141 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 142 */     GL11.glTranslatef(-0.0F, 0.0F, 0.0F);
/* 143 */     GL11.glScalef(1.4F, 1.4F, 1.4F);
/* 144 */     render2DWithThicness(tess, srcXMax, srcYMin, srcXMin, srcYMax, 32, 32, 0.0625F);
/*     */     
/* 146 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void render2DWithThicness(Tessellator tess, float maxU, float minV, float minU, float maxV, int width, int height, float thickness) {
/* 150 */     double pix = 0.015625D;
/* 151 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileEnergyInfiser/render2DWithThicness(Lnet/minecraft/client/renderer/Tessellator;FFFFIIF)V");
/* 152 */     tess.func_78375_b(0.0F, 0.0F, 1.0F);
/* 153 */     tess.func_78374_a(0.0D, 0.0D, 0.0D, maxU, maxV);
/* 154 */     tess.func_78374_a(width * pix, 0.0D, 0.0D, minU, maxV);
/* 155 */     tess.func_78374_a(width * pix, height * pix, 0.0D, minU, minV);
/* 156 */     tess.func_78374_a(0.0D, height * pix, 0.0D, maxU, minV);
/* 157 */     tess.func_78381_a();
/* 158 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileEnergyInfiser/render2DWithThicness(Lnet/minecraft/client/renderer/Tessellator;FFFFIIF)V");
/* 159 */     tess.func_78375_b(0.0F, 0.0F, -1.0F);
/* 160 */     tess.func_78374_a(0.0D, height * pix, (0.0F - thickness), maxU, minV);
/* 161 */     tess.func_78374_a(width * pix, height * pix, (0.0F - thickness), minU, minV);
/* 162 */     tess.func_78374_a(width * pix, 0.0D, (0.0F - thickness), minU, maxV);
/* 163 */     tess.func_78374_a(0.0D, 0.0D, (0.0F - thickness), maxU, maxV);
/* 164 */     tess.func_78381_a();
/* 165 */     float f5 = 0.5F * (maxU - minU) / width;
/* 166 */     float f6 = 0.5F * (maxV - minV) / height;
/* 167 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileEnergyInfiser/render2DWithThicness(Lnet/minecraft/client/renderer/Tessellator;FFFFIIF)V");
/* 168 */     tess.func_78375_b(-1.0F, 0.0F, 0.0F);
/*     */ 
/*     */     
/*     */     int k;
/*     */ 
/*     */     
/* 174 */     for (k = 0; k < width; k++) {
/* 175 */       double d = k * pix;
/* 176 */       float f7 = k / width;
/* 177 */       float f8 = maxU + (minU - maxU) * f7 - f5;
/* 178 */       tess.func_78374_a(d, 0.0D, (0.0F - thickness), f8, maxV);
/* 179 */       tess.func_78374_a(d, 0.0D, 0.0D, f8, maxV);
/* 180 */       tess.func_78374_a(d, height * pix, 0.0D, f8, minV);
/* 181 */       tess.func_78374_a(d, height * pix, (0.0F - thickness), f8, minV);
/*     */     } 
/*     */     
/* 184 */     tess.func_78381_a();
/* 185 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileEnergyInfiser/render2DWithThicness(Lnet/minecraft/client/renderer/Tessellator;FFFFIIF)V");
/* 186 */     tess.func_78375_b(1.0F, 0.0F, 0.0F);
/*     */     
/* 188 */     for (k = 0; k < width; k++) {
/* 189 */       double d = (k + 1) * pix;
/* 190 */       float f7 = k / width;
/* 191 */       float f8 = maxU + (minU - maxU) * f7 - f5;
/* 192 */       tess.func_78374_a(d, height * pix, (0.0F - thickness), f8, minV);
/* 193 */       tess.func_78374_a(d, height * pix, 0.0D, f8, minV);
/* 194 */       tess.func_78374_a(d, 0.0D, 0.0D, f8, maxV);
/* 195 */       tess.func_78374_a(d, 0.0D, (0.0F - thickness), f8, maxV);
/*     */     } 
/*     */     
/* 198 */     tess.func_78381_a();
/* 199 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileEnergyInfiser/render2DWithThicness(Lnet/minecraft/client/renderer/Tessellator;FFFFIIF)V");
/* 200 */     tess.func_78375_b(0.0F, 1.0F, 0.0F);
/*     */     
/* 202 */     for (k = 0; k < height; k++) {
/* 203 */       double d = (k + 1) * pix;
/* 204 */       float f7 = k / height;
/* 205 */       float f8 = maxV + (minV - maxV) * f7 - f6;
/* 206 */       tess.func_78374_a(0.0D, d, 0.0D, maxU, f8);
/* 207 */       tess.func_78374_a(width * pix, d, 0.0D, minU, f8);
/* 208 */       tess.func_78374_a(width * pix, d, (0.0F - thickness), minU, f8);
/* 209 */       tess.func_78374_a(0.0D, d, (0.0F - thickness), maxU, f8);
/*     */     } 
/*     */     
/* 212 */     tess.func_78381_a();
/* 213 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileEnergyInfiser/render2DWithThicness(Lnet/minecraft/client/renderer/Tessellator;FFFFIIF)V");
/* 214 */     tess.func_78375_b(0.0F, -1.0F, 0.0F);
/*     */     
/* 216 */     for (k = 0; k < height; k++) {
/* 217 */       double d = k * pix;
/* 218 */       float f7 = k / height;
/* 219 */       float f8 = maxV + (minV - maxV) * f7 - f6;
/* 220 */       tess.func_78374_a(width * pix, d, 0.0D, minU, f8);
/* 221 */       tess.func_78374_a(0.0D, d, 0.0D, maxU, f8);
/* 222 */       tess.func_78374_a(0.0D, d, (0.0F - thickness), maxU, f8);
/* 223 */       tess.func_78374_a(width * pix, d, (0.0F - thickness), minU, f8);
/*     */     } 
/*     */     
/* 226 */     tess.func_78381_a();
/*     */   }
/*     */   
/*     */   public void renderChargingItem(TileEnergyInfuser tile) {
/* 230 */     if (tile.func_70301_a(0) != null) {
/* 231 */       GL11.glPushMatrix();
/*     */       
/* 233 */       ItemStack stack = tile.func_70301_a(0);
/* 234 */       EntityItem itemEntity = new EntityItem(tile.func_145831_w(), 0.0D, 0.0D, 0.0D, tile.func_70301_a(0));
/* 235 */       itemEntity.field_70290_d = 0.0F;
/*     */       
/* 237 */       GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 238 */       GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 239 */       if (tile.func_145831_w().func_72912_H().func_82574_x().func_82766_b("doDaylightCycle"))
/* 240 */       { GL11.glRotatef((float)tile.func_145831_w().func_72820_D(), 0.0F, -1.0F, 0.0F); }
/* 241 */       else { GL11.glRotatef(tile.rotation, 0.0F, -1.0F, 0.0F); }
/* 242 */        if (stack.func_77973_b() instanceof net.minecraft.item.ItemBlock) {
/* 243 */         GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 244 */         GL11.glTranslatef(0.0F, 0.045F, 0.0F);
/*     */       } 
/*     */       
/* 247 */       RenderItem.field_82407_g = true;
/* 248 */       RenderManager.field_78727_a.func_147940_a((Entity)itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
/* 249 */       RenderItem.field_82407_g = false;
/*     */       
/* 251 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\tile\RenderTileEnergyInfiser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */