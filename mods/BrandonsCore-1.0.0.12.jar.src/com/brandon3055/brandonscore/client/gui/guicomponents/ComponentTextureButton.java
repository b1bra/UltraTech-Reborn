/*    */ package com.brandon3055.brandonscore.client.gui.guicomponents;
/*    */ 
/*    */ import com.gamerforea.client.util.TessellatorDebuggerUtils;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class ComponentTextureButton
/*    */   extends ComponentButton
/*    */ {
/*    */   public int textureXPos;
/*    */   public int textureYPos;
/*    */   private ResourceLocation texture;
/*    */   private boolean forceFullRender = false;
/*    */   
/*    */   public ComponentTextureButton(int x, int y, int textureXPos, int textureYPos, int xSize, int ySize, int id, GUIBase gui, String displayString, String hoverText, ResourceLocation texture) {
/* 20 */     super(x, y, xSize, ySize, id, gui, displayString, hoverText);
/* 21 */     this.textureXPos = textureXPos;
/* 22 */     this.textureYPos = textureYPos;
/* 23 */     this.texture = texture;
/*    */   }
/*    */   
/*    */   public ComponentTextureButton forceFullRender() {
/* 27 */     this.forceFullRender = true;
/* 28 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderForground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/* 34 */     GL11.glPushMatrix();
/* 35 */     minecraft.func_110434_K().func_110577_a(this.texture);
/* 36 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 38 */     if (!this.forceFullRender) {
/* 39 */       int k = isMouseOver(mouseX, mouseY) ? 2 : 1;
/* 40 */       GL11.glEnable(3042);
/* 41 */       OpenGlHelper.func_148821_a(770, 771, 1, 0);
/* 42 */       GL11.glBlendFunc(770, 771);
/* 43 */       func_73729_b(this.x, this.y, this.textureXPos, this.textureYPos + k * this.ySize, this.xSize, this.ySize);
/* 44 */       int l = 14737632;
/*    */       
/* 46 */       if (this.packedFGColour != 0) {
/* 47 */         l = this.packedFGColour;
/* 48 */       } else if (!this.enabled) {
/* 49 */         l = 10526880;
/* 50 */       } else if (isMouseOver(mouseX, mouseY)) {
/* 51 */         l = 16777120;
/*    */       } 
/* 53 */       func_73732_a(this.fontRendererObj, this.displayString, this.x + this.xSize / 2, this.y + (this.ySize - 8) / 2, l);
/*    */     } else {
/* 55 */       int xPos = this.x;
/* 56 */       int yPos = this.y;
/* 57 */       int xSize = this.xSize;
/* 58 */       int ySize = this.ySize;
/* 59 */       Tessellator tessellator = Tessellator.field_78398_a;
/*    */       
/* 61 */       if (isMouseOver(mouseX, mouseY)) {
/* 62 */         GL11.glDisable(3008);
/* 63 */         tessellator.func_78384_a(0, 255);
/* 64 */         tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/brandonscore/client/gui/guicomponents/ComponentTextureButton/renderForground(Lnet/minecraft/client/Minecraft;IIII)V");
/* 65 */         tessellator.func_78377_a(xPos, (yPos + ySize), this.field_73735_i);
/* 66 */         tessellator.func_78377_a((xPos + xSize), (yPos + ySize), this.field_73735_i);
/* 67 */         tessellator.func_78377_a((xPos + xSize), yPos, this.field_73735_i);
/* 68 */         tessellator.func_78377_a(xPos, yPos, this.field_73735_i);
/* 69 */         tessellator.func_78381_a();
/* 70 */         GL11.glEnable(3008);
/*    */       } 
/*    */       
/* 73 */       tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/brandonscore/client/gui/guicomponents/ComponentTextureButton/renderForground(Lnet/minecraft/client/Minecraft;IIII)V");
/* 74 */       tessellator.func_78374_a(xPos, (yPos + ySize), this.field_73735_i, 0.0D, 1.0D);
/* 75 */       tessellator.func_78374_a((xPos + xSize), (yPos + ySize), this.field_73735_i, 1.0D, 1.0D);
/* 76 */       tessellator.func_78374_a((xPos + xSize), yPos, this.field_73735_i, 1.0D, 0.0D);
/* 77 */       tessellator.func_78374_a(xPos, yPos, this.field_73735_i, 0.0D, 0.0D);
/* 78 */       tessellator.func_78381_a();
/*    */     } 
/* 80 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\client\gui\guicomponents\ComponentTextureButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */