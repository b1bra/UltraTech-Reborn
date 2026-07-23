/*     */ package com.brandon3055.brandonscore.client.utills;
/*     */ 
/*     */ import com.gamerforea.client.util.TessellatorDebuggerUtils;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiHelper
/*     */ {
/*     */   public static final double PXL128 = 0.0078125D;
/*     */   public static final double PXL256 = 0.00390625D;
/*     */   
/*     */   public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
/*  19 */     return (mouseX >= x && mouseX <= x + xSize && mouseY >= y && mouseY <= y + ySize);
/*     */   }
/*     */   
/*     */   public static void drawTexturedRect(int x, int y, int u, int v, int width, int height) {
/*  23 */     drawTexturedRect(x, y, width, height, u, v, width, height, 0.0D, 0.00390625D);
/*     */   }
/*     */   
/*     */   public static void drawTexturedRect(double x, double y, double width, double height, int u, int v, int uSize, int vSize, double zLevel, double pxl) {
/*  27 */     Tessellator tessellator = Tessellator.field_78398_a;
/*  28 */     tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/brandonscore/client/utills/GuiHelper/drawTexturedRect(DDDDIIIIDD)V");
/*  29 */     tessellator.func_78374_a(x, y + height, zLevel, u * pxl, (v + vSize) * pxl);
/*  30 */     tessellator.func_78374_a(x + width, y + height, zLevel, (u + uSize) * pxl, (v + vSize) * pxl);
/*  31 */     tessellator.func_78374_a(x + width, y, zLevel, (u + uSize) * pxl, v * pxl);
/*  32 */     tessellator.func_78374_a(x, y, zLevel, u * pxl, v * pxl);
/*  33 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   public static void drawHoveringText(List<String> list, int x, int y, FontRenderer font, float fade, double scale, int guiWidth, int guiHeight) {
/*  37 */     if (!list.isEmpty()) {
/*  38 */       GL11.glPushMatrix();
/*  39 */       GL11.glDisable(32826);
/*  40 */       GL11.glDisable(2896);
/*  41 */       GL11.glDisable(2929);
/*  42 */       GL11.glScaled(scale, scale, 1.0D);
/*  43 */       x = (int)(x / scale);
/*  44 */       y = (int)(y / scale);
/*     */       
/*  46 */       int k = 0;
/*     */       
/*  48 */       for (String s : list) {
/*  49 */         int l = font.func_78256_a(s);
/*  50 */         if (l > k) {
/*  51 */           k = l;
/*     */         }
/*     */       } 
/*     */       
/*  55 */       int adjX = x + 12;
/*  56 */       int adjY = y - 12;
/*  57 */       int i1 = 8;
/*     */       
/*  59 */       if (list.size() > 1) {
/*  60 */         i1 += 2 + (list.size() - 1) * 10;
/*     */       }
/*     */       
/*  63 */       if (adjX + k > (int)(guiWidth / scale)) {
/*  64 */         adjX -= 28 + k;
/*     */       }
/*     */       
/*  67 */       if (adjY + i1 + 6 > (int)(guiHeight / scale)) {
/*  68 */         adjY = (int)(guiHeight / scale) - i1 - 6;
/*     */       }
/*     */       
/*  71 */       int j1 = -267386864;
/*  72 */       drawGradientRect(adjX - 3, adjY - 4, adjX + k + 3, adjY - 3, j1, j1, fade, scale);
/*  73 */       drawGradientRect(adjX - 3, adjY + i1 + 3, adjX + k + 3, adjY + i1 + 4, j1, j1, fade, scale);
/*  74 */       drawGradientRect(adjX - 3, adjY - 3, adjX + k + 3, adjY + i1 + 3, j1, j1, fade, scale);
/*  75 */       drawGradientRect(adjX - 4, adjY - 3, adjX - 3, adjY + i1 + 3, j1, j1, fade, scale);
/*  76 */       drawGradientRect(adjX + k + 3, adjY - 3, adjX + k + 4, adjY + i1 + 3, j1, j1, fade, scale);
/*  77 */       int k1 = 1347420415;
/*  78 */       int l1 = (k1 & 0xFEFEFE) >> 1 | k1 & 0xFF000000;
/*  79 */       drawGradientRect(adjX - 3, adjY - 3 + 1, adjX - 3 + 1, adjY + i1 + 3 - 1, k1, l1, fade, scale);
/*  80 */       drawGradientRect(adjX + k + 2, adjY - 3 + 1, adjX + k + 3, adjY + i1 + 3 - 1, k1, l1, fade, scale);
/*  81 */       drawGradientRect(adjX - 3, adjY - 3, adjX + k + 3, adjY - 3 + 1, k1, k1, fade, scale);
/*  82 */       drawGradientRect(adjX - 3, adjY + i1 + 2, adjX + k + 3, adjY + i1 + 3, l1, l1, fade, scale);
/*     */       
/*  84 */       for (int i2 = 0; i2 < list.size(); i2++) {
/*  85 */         String s1 = list.get(i2);
/*     */         
/*  87 */         GL11.glEnable(3042);
/*  88 */         GL11.glDisable(3008);
/*  89 */         OpenGlHelper.func_148821_a(770, 771, 1, 0);
/*  90 */         font.func_78261_a(s1, adjX, adjY, (int)(fade * 240.0F) + 16 << 24 | 0xFFFFFF);
/*     */         
/*  92 */         adjY += 10;
/*     */       } 
/*     */       
/*  95 */       GL11.glEnable(2896);
/*  96 */       GL11.glEnable(2929);
/*  97 */       GL11.glEnable(32826);
/*  98 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void drawGradientRect(int x1, int y1, int x2, int y2, int colour1, int colour2, float fade, double scale) {
/* 103 */     float f = (colour1 >> 24 & 0xFF) / 255.0F * fade;
/* 104 */     float f1 = (colour1 >> 16 & 0xFF) / 255.0F;
/* 105 */     float f2 = (colour1 >> 8 & 0xFF) / 255.0F;
/* 106 */     float f3 = (colour1 & 0xFF) / 255.0F;
/* 107 */     float f4 = (colour2 >> 24 & 0xFF) / 255.0F * fade;
/* 108 */     float f5 = (colour2 >> 16 & 0xFF) / 255.0F;
/* 109 */     float f6 = (colour2 >> 8 & 0xFF) / 255.0F;
/* 110 */     float f7 = (colour2 & 0xFF) / 255.0F;
/* 111 */     GL11.glDisable(3553);
/* 112 */     GL11.glEnable(3042);
/* 113 */     GL11.glDisable(3008);
/* 114 */     OpenGlHelper.func_148821_a(770, 771, 1, 0);
/* 115 */     GL11.glShadeModel(7425);
/* 116 */     Tessellator tessellator = Tessellator.field_78398_a;
/* 117 */     tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/brandonscore/client/utills/GuiHelper/drawGradientRect(IIIIIIFD)V");
/* 118 */     tessellator.func_78369_a(f1, f2, f3, f);
/* 119 */     tessellator.func_78377_a(x2, y1, 300.0D);
/* 120 */     tessellator.func_78377_a(x1, y1, 300.0D);
/* 121 */     tessellator.func_78369_a(f5, f6, f7, f4);
/* 122 */     tessellator.func_78377_a(x1, y2, 300.0D);
/* 123 */     tessellator.func_78377_a(x2, y2, 300.0D);
/* 124 */     tessellator.func_78381_a();
/* 125 */     GL11.glShadeModel(7424);
/* 126 */     GL11.glDisable(3042);
/* 127 */     GL11.glEnable(3008);
/* 128 */     GL11.glEnable(3553);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\clien\\utills\GuiHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */