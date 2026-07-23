/*    */ package com.brandon3055.brandonscore.client.gui.guicomponents;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class ComponentTexturedRect
/*    */   extends ComponentBase
/*    */ {
/*    */   private int width;
/*    */   private int height;
/* 13 */   private int texX = 0;
/* 14 */   private int texY = 0;
/*    */   private final ResourceLocation texture;
/*    */   private boolean transparent = false;
/*    */   
/*    */   public ComponentTexturedRect(int x, int y, int width, int height, ResourceLocation texture) {
/* 19 */     super(x, y);
/* 20 */     this.width = width;
/* 21 */     this.height = height;
/* 22 */     this.texture = texture;
/*    */   }
/*    */   
/*    */   public ComponentTexturedRect(int x, int y, int texX, int texY, int width, int height, ResourceLocation texture, boolean transparent) {
/* 26 */     super(x, y);
/* 27 */     this.width = width;
/* 28 */     this.height = height;
/* 29 */     this.texture = texture;
/* 30 */     this.texX = texX;
/* 31 */     this.texY = texY;
/* 32 */     this.transparent = transparent;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 37 */     return this.width;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 42 */     return this.height;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderBackground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/* 47 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(this.texture);
/* 48 */     if (this.transparent) {
/* 49 */       GL11.glPushMatrix();
/* 50 */       GL11.glEnable(3042);
/*    */     } 
/*    */ 
/*    */     
/* 54 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 55 */     func_73729_b(this.x, this.y, this.texX, this.texY, this.width, this.height);
/* 56 */     if (this.transparent) {
/*    */       
/* 58 */       GL11.glDisable(3042);
/* 59 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void renderForground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {}
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\client\gui\guicomponents\ComponentTexturedRect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */