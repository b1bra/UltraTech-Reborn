/*    */ package com.brandon3055.draconicevolution.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ public class CustomGuiButton
/*    */   extends GuiButton {
/*    */   public CustomGuiButton(int id, int left, int top, int width, int hight, String text) {
/* 12 */     super(id, left, top, width, hight, text);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_146112_a(Minecraft minecraft, int p_146112_2_, int p_146112_3_) {
/* 17 */     if (this.field_146125_m) {
/* 18 */       FontRenderer fontrenderer = minecraft.field_71466_p;
/* 19 */       minecraft.func_110434_K().func_110577_a(field_146122_a);
/* 20 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 21 */       this.field_146123_n = (p_146112_2_ >= this.field_146128_h && p_146112_3_ >= this.field_146129_i && p_146112_2_ < this.field_146128_h + this.field_146120_f && p_146112_3_ < this.field_146129_i + this.field_146121_g);
/* 22 */       int k = func_146114_a(this.field_146123_n);
/* 23 */       GL11.glEnable(3042);
/* 24 */       OpenGlHelper.func_148821_a(770, 771, 1, 0);
/* 25 */       GL11.glBlendFunc(770, 771);
/* 26 */       func_73729_b(this.field_146128_h, this.field_146129_i, 0, 46 + k * 20, this.field_146120_f / 2, this.field_146121_g);
/* 27 */       func_73729_b(this.field_146128_h + this.field_146120_f / 2, this.field_146129_i, 200 - this.field_146120_f / 2, 46 + k * 20, this.field_146120_f / 2, this.field_146121_g);
/* 28 */       func_146119_b(minecraft, p_146112_2_, p_146112_3_);
/* 29 */       int l = 14737632;
/*    */       
/* 31 */       if (this.packedFGColour != 0) {
/* 32 */         l = this.packedFGColour;
/* 33 */       } else if (!this.field_146124_l) {
/* 34 */         l = 10526880;
/* 35 */       } else if (this.field_146123_n) {
/* 36 */         l = 16777120;
/*    */       } 
/*    */       
/* 39 */       func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, l);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\CustomGuiButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */