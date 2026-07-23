/*    */ package com.brandon3055.brandonscore.client.gui.guicomponents;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class ComponentItemRenderer
/*    */   extends ComponentBase
/*    */ {
/* 12 */   private static final ResourceLocation texture = new ResourceLocation("brandonscore:textures/gui/Widgets.png");
/*    */   
/*    */   ItemStack stack;
/*    */   
/*    */   public ComponentItemRenderer(int x, int y, ItemStack stack) {
/* 17 */     super(x, y);
/* 18 */     this.stack = stack;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 23 */     return 20;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 28 */     return 20;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderBackground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderForground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/* 38 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 39 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(texture);
/* 40 */     func_73729_b(this.x, this.y, 118, 0, getWidth(), getHeight());
/* 41 */     drawItemStack(this.stack, this.x + 2, this.y + 2, "null");
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderFinal(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/* 46 */     if (isMouseOver(mouseX, mouseY))
/* 47 */       renderToolTip(this.stack, mouseX + offsetX, mouseY + offsetY); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\client\gui\guicomponents\ComponentItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */