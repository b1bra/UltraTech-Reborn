/*    */ package com.brandon3055.brandonscore.client.gui.guicomponents;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiTextField;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ComponentTextField
/*    */   extends ComponentBase
/*    */ {
/*    */   public GuiTextField textField;
/*    */   private int xSize;
/*    */   private int ySize;
/*    */   private GUIBase parent;
/* 15 */   private String label = "";
/* 16 */   private int labelColour = 0;
/*    */   
/*    */   public ComponentTextField(GUIBase parent, int x, int y, int xSize, int ySize) {
/* 19 */     super(x, y);
/* 20 */     this.xSize = xSize;
/* 21 */     this.ySize = ySize;
/* 22 */     this.parent = parent;
/* 23 */     this.textField = new GuiTextField(this.fontRendererObj, x, y, xSize, ySize);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 28 */     return this.xSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 33 */     return this.ySize;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderBackground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderForground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/* 43 */     func_73734_a(this.x - 1, this.y - 1, this.x + this.xSize - this.textField.field_146218_h + 1, this.y + this.ySize + 1, -6250336);
/* 44 */     this.textField.func_146194_f();
/* 45 */     func_73734_a(this.x, this.y, this.x + this.xSize - this.textField.field_146218_h, this.y + this.ySize, -16777216);
/* 46 */     this.fontRendererObj.func_78276_b(this.label, this.x + 1, this.y + 2, this.labelColour);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int x, int y, int button) {
/* 51 */     this.textField.func_146192_a(x, y, button);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void keyTyped(char par1, int par2) {
/* 56 */     this.textField.func_146201_a(par1, par2);
/* 57 */     if (this.textField.func_146206_l()) this.parent.componentCallBack(this); 
/*    */   }
/*    */   
/*    */   public boolean isFocused() {
/* 61 */     return this.textField.func_146206_l();
/*    */   }
/*    */   
/*    */   public ComponentTextField setLabel(String label, int labelColour) {
/* 65 */     this.label = label;
/* 66 */     this.labelColour = labelColour;
/* 67 */     int labelLength = this.fontRendererObj.func_78256_a(label);
/* 68 */     this.textField.field_146209_f = this.x + labelLength;
/* 69 */     this.textField.field_146218_h = this.xSize - labelLength;
/* 70 */     this.textField.func_146193_g(labelColour);
/* 71 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\client\gui\guicomponents\ComponentTextField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */