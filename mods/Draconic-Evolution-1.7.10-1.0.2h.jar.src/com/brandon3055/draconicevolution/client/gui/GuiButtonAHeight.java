/*    */ package com.brandon3055.draconicevolution.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiButtonAHeight
/*    */   extends GuiButton
/*    */ {
/*    */   public GuiButtonAHeight(int id, int xPos, int yPos, int width, int hight, String displayString) {
/* 15 */     super(id, xPos, yPos, width, hight, displayString);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_146112_a(Minecraft minecraft, int mouseX, int mouseY) {
/* 20 */     if (this.field_146125_m) {
/* 21 */       FontRenderer fontrenderer = minecraft.field_71466_p;
/* 22 */       minecraft.func_110434_K().func_110577_a(field_146122_a);
/* 23 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 24 */       this.field_146123_n = (mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g);
/* 25 */       int k = func_146114_a(this.field_146123_n);
/* 26 */       GL11.glEnable(3042);
/* 27 */       OpenGlHelper.func_148821_a(770, 771, 1, 0);
/* 28 */       GL11.glBlendFunc(770, 771);
/* 29 */       func_73729_b(this.field_146128_h, this.field_146129_i, 0, 46 + k * 20, this.field_146120_f % 2 + this.field_146120_f / 2, this.field_146121_g);
/* 30 */       func_73729_b(this.field_146120_f % 2 + this.field_146128_h + this.field_146120_f / 2, this.field_146129_i, 200 - this.field_146120_f / 2, 46 + k * 20, this.field_146120_f / 2, this.field_146121_g);
/* 31 */       if (this.field_146121_g < 20) {
/*    */         
/* 33 */         func_73729_b(this.field_146128_h, this.field_146129_i + 3, 0, 46 + k * 20 + 20 - this.field_146121_g + 3, this.field_146120_f % 2 + this.field_146120_f / 2, this.field_146121_g - 3);
/* 34 */         func_73729_b(this.field_146120_f % 2 + this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + 3, 200 - this.field_146120_f / 2, 46 + k * 20 + 20 - this.field_146121_g + 3, this.field_146120_f / 2, this.field_146121_g - 3);
/*    */       } 
/* 36 */       func_146119_b(minecraft, mouseX, mouseY);
/* 37 */       int l = 14737632;
/*    */       
/* 39 */       if (this.packedFGColour != 0) {
/* 40 */         l = this.packedFGColour;
/* 41 */       } else if (!this.field_146124_l) {
/* 42 */         l = 10526880;
/* 43 */       } else if (this.field_146123_n) {
/* 44 */         l = 16777120;
/*    */       } 
/* 46 */       func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, l);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\GuiButtonAHeight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */