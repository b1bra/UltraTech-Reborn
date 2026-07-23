/*    */ package com.brandon3055.draconicevolution.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class GuiButtonTextOnly
/*    */   extends GuiButton
/*    */ {
/*    */   public String LINKED_PAGE;
/*    */   public int textColour;
/*    */   
/*    */   public GuiButtonTextOnly(int id, int xPos, int yPos, int width, int hight, String displayString, String linkedPage, int colour) {
/* 18 */     super(id, xPos, yPos, width, hight, displayString);
/* 19 */     this.LINKED_PAGE = linkedPage;
/* 20 */     this.textColour = colour;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_146112_a(Minecraft minecraft, int mouseX, int mouseY) {
/* 25 */     if (this.field_146125_m) {
/* 26 */       FontRenderer fontrenderer = minecraft.field_71466_p;
/* 27 */       minecraft.func_110434_K().func_110577_a(field_146122_a);
/* 28 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 29 */       this.field_146123_n = (mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g);
/* 30 */       GL11.glEnable(3042);
/* 31 */       OpenGlHelper.func_148821_a(770, 771, 1, 0);
/* 32 */       GL11.glBlendFunc(770, 771);
/*    */       
/* 34 */       func_146119_b(minecraft, mouseX, mouseY);
/*    */       
/* 36 */       String trimmedDisplayString = this.field_146126_j;
/* 37 */       if (fontrenderer.func_78256_a(this.field_146126_j) > this.field_146120_f + 30 && !this.field_146123_n) {
/* 38 */         int energencyBreak = 0;
/* 39 */         while (fontrenderer.func_78256_a(trimmedDisplayString) * 0.7D > (this.field_146120_f - 5)) {
/* 40 */           trimmedDisplayString = trimmedDisplayString.substring(0, trimmedDisplayString.length() - 1);
/* 41 */           energencyBreak++;
/* 42 */           if (energencyBreak > 100)
/*    */             break; 
/* 44 */         }  trimmedDisplayString = trimmedDisplayString + "...";
/*    */       } 
/*    */       
/* 47 */       if (this.field_146123_n) {
/* 48 */         trimmedDisplayString = EnumChatFormatting.BOLD + "" + EnumChatFormatting.ITALIC + trimmedDisplayString;
/* 49 */         GL11.glPushMatrix();
/* 50 */         GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
/* 51 */         func_73729_b(this.field_146128_h + (int)(this.field_146128_h * 0.01D), this.field_146129_i + (int)(this.field_146129_i * 0.01D), 0, 46, (int)(fontrenderer.func_78256_a(trimmedDisplayString) * 0.72D) + 2, 8);
/* 52 */         GL11.glPopMatrix();
/*    */       } 
/* 54 */       GL11.glPushMatrix();
/* 55 */       GL11.glScalef(0.7F, 0.7F, 1.0F);
/* 56 */       fontrenderer.func_78276_b(trimmedDisplayString, (int)(this.field_146128_h * 1.45D), (int)((this.field_146129_i + (this.field_146121_g - 8) / 2) * 1.45D), this.textColour);
/* 57 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean getIsHovering() {
/* 62 */     return this.field_146123_n;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\GuiButtonTextOnly.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */