/*    */ package com.brandon3055.draconicevolution.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class GuiTextureButton
/*    */   extends GuiButton
/*    */ {
/*    */   public int textureXPos;
/*    */   public int textureYPos;
/*    */   
/*    */   public GuiTextureButton(int id, int xPos, int yPos, int textureXPos, int textureYPos, int xSise, int ySise, String text) {
/* 17 */     super(id, xPos, yPos, xSise, ySise, text);
/* 18 */     this.textureXPos = textureXPos;
/* 19 */     this.textureYPos = textureYPos;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_146112_a(Minecraft mc, int x, int y) {
/* 24 */     if (this.field_146125_m) {
/* 25 */       FontRenderer fontrenderer = mc.field_71466_p;
/* 26 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 27 */       this.field_146123_n = (x >= this.field_146128_h && y >= this.field_146129_i && x < this.field_146128_h + this.field_146120_f && y < this.field_146129_i + this.field_146121_g);
/* 28 */       int k = func_146114_a(this.field_146123_n);
/* 29 */       GL11.glEnable(3042);
/* 30 */       OpenGlHelper.func_148821_a(770, 771, 1, 0);
/* 31 */       GL11.glBlendFunc(770, 771);
/* 32 */       func_73729_b(this.field_146128_h, this.field_146129_i, this.textureXPos, this.textureYPos + k * this.field_146121_g, this.field_146120_f, this.field_146121_g);
/*    */       
/* 34 */       func_146119_b(mc, x, y);
/* 35 */       int l = 14737632;
/*    */       
/* 37 */       if (this.packedFGColour != 0) {
/* 38 */         l = this.packedFGColour;
/* 39 */       } else if (!this.field_146124_l) {
/* 40 */         l = 10526880;
/* 41 */       } else if (this.field_146123_n) {
/* 42 */         l = 16777120;
/*    */       } 
/*    */       
/* 45 */       func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, l);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\GuiTextureButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */