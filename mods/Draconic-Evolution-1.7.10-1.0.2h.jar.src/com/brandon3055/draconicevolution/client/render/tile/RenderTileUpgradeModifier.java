/*     */ package com.brandon3055.draconicevolution.client.render.tile;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileUpgradeModifier;
/*     */ import com.gamerforea.client.util.TessellatorDebuggerUtils;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ public class RenderTileUpgradeModifier
/*     */   extends TileEntitySpecialRenderer {
/*  18 */   private static float pxl = 0.00390625F;
/*     */ 
/*     */   
/*     */   public void func_147500_a(TileEntity tileEntity, double x, double y, double z, float f) {
/*  22 */     GL11.glPushMatrix();
/*     */     
/*  24 */     GL11.glTranslated(x, y, z);
/*  25 */     TileUpgradeModifier tile = (TileUpgradeModifier)tileEntity;
/*  26 */     renderBlock(tile, f);
/*     */     
/*  28 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void renderBlock(TileUpgradeModifier tile, float pt) {
/*  32 */     Tessellator tess = Tessellator.field_78398_a;
/*  33 */     tess.func_78370_a(255, 255, 255, 255);
/*     */     
/*  35 */     ResourceHandler.bindResource("textures/models/upgradeModifierGear.png");
/*  36 */     GL11.glPushMatrix();
/*  37 */     GL11.glEnable(3008);
/*     */     
/*  39 */     GL11.glScaled(0.8D, 0.8D, 0.8D);
/*  40 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  41 */     GL11.glTranslatef(-0.375F, -0.375F, -0.47F);
/*  42 */     GL11.glTranslated(1.0D, 1.0D, 0.0D);
/*  43 */     GL11.glRotatef(tile.rotation + pt * tile.rotationSpeed, 0.0F, 0.0F, 1.0F);
/*  44 */     GL11.glTranslated(-1.0D, -1.0D, 0.0D);
/*  45 */     render2DWithThicness(tess, 1.0F, 0.0F, 0.0F, 1.0F, 128, 128, 0.0625F);
/*     */     
/*  47 */     GL11.glPopMatrix();
/*     */     
/*  49 */     GL11.glColor4f(0.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  51 */     GL11.glPushMatrix();
/*     */     
/*  53 */     GL11.glScaled(0.4D, 0.4D, 0.4D);
/*  54 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  55 */     GL11.glTranslatef(0.25F, 0.25F, -0.945F);
/*  56 */     GL11.glTranslated(1.0D, 1.0D, 0.0D);
/*  57 */     GL11.glRotatef(-tile.rotation + pt * -tile.rotationSpeed, 0.0F, 0.0F, 1.0F);
/*  58 */     GL11.glTranslated(-1.0D, -1.0D, 0.0D);
/*  59 */     render2DWithThicness(tess, 1.0F, 0.0F, 0.0F, 1.0F, 128, 128, 0.0625F);
/*  60 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  62 */     GL11.glPopMatrix();
/*     */ 
/*     */     
/*  65 */     drawBase(tess);
/*  66 */     renderChargingItem(tile, pt);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawBase(Tessellator tess) {
/*  71 */     ResourceHandler.bindResource("textures/models/EnergyInfuserTextureSheet.png");
/*  72 */     GL11.glPushMatrix();
/*  73 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileUpgradeModifier/drawBase(Lnet/minecraft/client/renderer/Tessellator;)V");
/*  74 */     tess.func_78375_b(0.0F, 0.0F, 1.0F);
/*     */     
/*  76 */     double srcXMin = 0.0D;
/*  77 */     double srcYMin = 0.0D;
/*  78 */     double srcXMax = 64.0D * pxl;
/*  79 */     double srcYMax = 64.0D * pxl;
/*     */     
/*  81 */     tess.func_78374_a(0.0D, 5.0E-4D, 0.0D, srcXMin, srcYMin);
/*  82 */     tess.func_78374_a(1.0D, 5.0E-4D, 0.0D, srcXMax, srcYMin);
/*  83 */     tess.func_78374_a(1.0D, 5.0E-4D, 1.0D, srcXMax, srcYMax);
/*  84 */     tess.func_78374_a(0.0D, 5.0E-4D, 1.0D, srcXMin, srcYMax);
/*     */ 
/*     */     
/*  87 */     srcXMin = 128.0D * pxl;
/*  88 */     srcYMin = 0.0D;
/*  89 */     srcXMax = 192.0D * pxl;
/*  90 */     srcYMax = 64.0D * pxl;
/*     */     
/*  92 */     tess.func_78374_a(0.0D, 0.3745D, 0.0D, srcXMin, srcYMin);
/*  93 */     tess.func_78374_a(0.0D, 0.3745D, 1.0D, srcXMax, srcYMin);
/*  94 */     tess.func_78374_a(1.0D, 0.3745D, 1.0D, srcXMax, srcYMax);
/*  95 */     tess.func_78374_a(1.0D, 0.3745D, 0.0D, srcXMin, srcYMax);
/*     */     
/*  97 */     srcXMin = 64.0D * pxl;
/*  98 */     srcYMin = 0.0D;
/*  99 */     srcXMax = 128.0D * pxl;
/* 100 */     srcYMax = 24.0D * pxl;
/*     */     
/* 102 */     tess.func_78374_a(1.0D, 0.0D, 0.0D, srcXMin, srcYMin);
/* 103 */     tess.func_78374_a(0.0D, 0.0D, 0.0D, srcXMax, srcYMin);
/* 104 */     tess.func_78374_a(0.0D, 0.375D, 0.0D, srcXMax, srcYMax);
/* 105 */     tess.func_78374_a(1.0D, 0.375D, 0.0D, srcXMin, srcYMax);
/*     */     
/* 107 */     tess.func_78374_a(1.0D, 0.0D, 1.0D, srcXMin, srcYMin);
/* 108 */     tess.func_78374_a(1.0D, 0.375D, 1.0D, srcXMin, srcYMax);
/* 109 */     tess.func_78374_a(0.0D, 0.375D, 1.0D, srcXMax, srcYMax);
/* 110 */     tess.func_78374_a(0.0D, 0.0D, 1.0D, srcXMax, srcYMin);
/*     */     
/* 112 */     tess.func_78374_a(0.0D, 0.375D, 1.0D, srcXMin, srcYMin);
/* 113 */     tess.func_78374_a(0.0D, 0.375D, 0.0D, srcXMax, srcYMin);
/* 114 */     tess.func_78374_a(0.0D, 0.0D, 0.0D, srcXMax, srcYMax);
/* 115 */     tess.func_78374_a(0.0D, 0.0D, 1.0D, srcXMin, srcYMax);
/*     */     
/* 117 */     tess.func_78374_a(1.0D, 0.375D, 1.0D, srcXMin, srcYMin);
/* 118 */     tess.func_78374_a(1.0D, 0.0D, 1.0D, srcXMin, srcYMax);
/* 119 */     tess.func_78374_a(1.0D, 0.0D, 0.0D, srcXMax, srcYMax);
/* 120 */     tess.func_78374_a(1.0D, 0.375D, 0.0D, srcXMax, srcYMin);
/*     */     
/* 122 */     tess.func_78381_a();
/* 123 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void render2DWithThicness(Tessellator tess, float maxU, float minV, float minU, float maxV, int width, int height, float thickness) {
/* 128 */     double pix = 0.015625D;
/* 129 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileUpgradeModifier/render2DWithThicness(Lnet/minecraft/client/renderer/Tessellator;FFFFIIF)V");
/* 130 */     tess.func_78375_b(0.0F, 0.0F, 1.0F);
/* 131 */     tess.func_78374_a(0.0D, 0.0D, 0.0D, maxU, maxV);
/* 132 */     tess.func_78374_a(width * pix, 0.0D, 0.0D, minU, maxV);
/* 133 */     tess.func_78374_a(width * pix, height * pix, 0.0D, minU, minV);
/* 134 */     tess.func_78374_a(0.0D, height * pix, 0.0D, maxU, minV);
/* 135 */     tess.func_78381_a();
/* 136 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileUpgradeModifier/render2DWithThicness(Lnet/minecraft/client/renderer/Tessellator;FFFFIIF)V");
/* 137 */     tess.func_78375_b(0.0F, 0.0F, -1.0F);
/* 138 */     tess.func_78374_a(0.0D, height * pix, (0.0F - thickness), maxU, minV);
/* 139 */     tess.func_78374_a(width * pix, height * pix, (0.0F - thickness), minU, minV);
/* 140 */     tess.func_78374_a(width * pix, 0.0D, (0.0F - thickness), minU, maxV);
/* 141 */     tess.func_78374_a(0.0D, 0.0D, (0.0F - thickness), maxU, maxV);
/* 142 */     tess.func_78381_a();
/* 143 */     float f5 = 0.5F * (maxU - minU) / width;
/* 144 */     float f6 = 0.5F * (maxV - minV) / height;
/* 145 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileUpgradeModifier/render2DWithThicness(Lnet/minecraft/client/renderer/Tessellator;FFFFIIF)V");
/* 146 */     tess.func_78375_b(-1.0F, 0.0F, 0.0F);
/*     */ 
/*     */     
/*     */     int k;
/*     */ 
/*     */     
/* 152 */     for (k = 0; k < width; k++) {
/* 153 */       double d = k * pix;
/* 154 */       float f7 = k / width;
/* 155 */       float f8 = maxU + (minU - maxU) * f7 - f5;
/* 156 */       tess.func_78374_a(d, 0.0D, (0.0F - thickness), f8, maxV);
/* 157 */       tess.func_78374_a(d, 0.0D, 0.0D, f8, maxV);
/* 158 */       tess.func_78374_a(d, height * pix, 0.0D, f8, minV);
/* 159 */       tess.func_78374_a(d, height * pix, (0.0F - thickness), f8, minV);
/*     */     } 
/*     */     
/* 162 */     tess.func_78381_a();
/* 163 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileUpgradeModifier/render2DWithThicness(Lnet/minecraft/client/renderer/Tessellator;FFFFIIF)V");
/* 164 */     tess.func_78375_b(1.0F, 0.0F, 0.0F);
/*     */     
/* 166 */     for (k = 0; k < width; k++) {
/* 167 */       double d = (k + 1) * pix;
/* 168 */       float f7 = k / width;
/* 169 */       float f8 = maxU + (minU - maxU) * f7 - f5;
/* 170 */       tess.func_78374_a(d, height * pix, (0.0F - thickness), f8, minV);
/* 171 */       tess.func_78374_a(d, height * pix, 0.0D, f8, minV);
/* 172 */       tess.func_78374_a(d, 0.0D, 0.0D, f8, maxV);
/* 173 */       tess.func_78374_a(d, 0.0D, (0.0F - thickness), f8, maxV);
/*     */     } 
/*     */     
/* 176 */     tess.func_78381_a();
/* 177 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileUpgradeModifier/render2DWithThicness(Lnet/minecraft/client/renderer/Tessellator;FFFFIIF)V");
/* 178 */     tess.func_78375_b(0.0F, 1.0F, 0.0F);
/*     */     
/* 180 */     for (k = 0; k < height; k++) {
/* 181 */       double d = (k + 1) * pix;
/* 182 */       float f7 = k / height;
/* 183 */       float f8 = maxV + (minV - maxV) * f7 - f6;
/* 184 */       tess.func_78374_a(0.0D, d, 0.0D, maxU, f8);
/* 185 */       tess.func_78374_a(width * pix, d, 0.0D, minU, f8);
/* 186 */       tess.func_78374_a(width * pix, d, (0.0F - thickness), minU, f8);
/* 187 */       tess.func_78374_a(0.0D, d, (0.0F - thickness), maxU, f8);
/*     */     } 
/*     */     
/* 190 */     tess.func_78381_a();
/* 191 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileUpgradeModifier/render2DWithThicness(Lnet/minecraft/client/renderer/Tessellator;FFFFIIF)V");
/* 192 */     tess.func_78375_b(0.0F, -1.0F, 0.0F);
/*     */     
/* 194 */     for (k = 0; k < height; k++) {
/* 195 */       double d = k * pix;
/* 196 */       float f7 = k / height;
/* 197 */       float f8 = maxV + (minV - maxV) * f7 - f6;
/* 198 */       tess.func_78374_a(width * pix, d, 0.0D, minU, f8);
/* 199 */       tess.func_78374_a(0.0D, d, 0.0D, maxU, f8);
/* 200 */       tess.func_78374_a(0.0D, d, (0.0F - thickness), maxU, f8);
/* 201 */       tess.func_78374_a(width * pix, d, (0.0F - thickness), minU, f8);
/*     */     } 
/*     */     
/* 204 */     tess.func_78381_a();
/*     */   }
/*     */   
/*     */   public void renderChargingItem(TileUpgradeModifier tile, float pt) {
/* 208 */     if (tile.func_70301_a(0) != null) {
/* 209 */       GL11.glPushMatrix();
/*     */       
/* 211 */       ItemStack stack = tile.func_70301_a(0);
/* 212 */       EntityItem itemEntity = new EntityItem(tile.func_145831_w(), 0.0D, 0.0D, 0.0D, tile.func_70301_a(0));
/* 213 */       itemEntity.field_70290_d = 0.0F;
/*     */       
/* 215 */       GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 216 */       GL11.glScalef(1.0F, 1.0F, 1.0F);
/*     */       
/* 218 */       GL11.glRotatef((tile.rotation + pt * tile.rotationSpeed) * 0.2F, 0.0F, -1.0F, 0.0F);
/* 219 */       if (stack.func_77973_b() instanceof net.minecraft.item.ItemBlock) {
/* 220 */         GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 221 */         GL11.glTranslatef(0.0F, 0.045F, 0.0F);
/*     */       } 
/*     */       
/* 224 */       RenderItem.field_82407_g = true;
/* 225 */       RenderManager.field_78727_a.func_147940_a((Entity)itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
/* 226 */       RenderItem.field_82407_g = false;
/*     */       
/* 228 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\tile\RenderTileUpgradeModifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */