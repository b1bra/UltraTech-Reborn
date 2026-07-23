/*    */ package com.brandon3055.brandonscore.client.gui.guicomponents;
/*    */ 
/*    */ import net.minecraft.inventory.Container;
/*    */ import org.lwjglx.input.Mouse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class GUIScrollingBase
/*    */   extends GUIBase
/*    */ {
/*    */   public int scrollOffset;
/*    */   public int pageLength;
/*    */   public int scrollLimit;
/*    */   public int barPosition;
/*    */   
/*    */   public GUIScrollingBase(Container container, int xSize, int ySize) {
/* 18 */     super(container, xSize, ySize);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 42 */     this.clickedY = 0;
/* 43 */     this.scrollPressed = false;
/*    */   } public boolean disableScrollBar = true; protected int clickedY; protected boolean scrollPressed; public void func_146274_d() { super.func_146274_d(); int i = Mouse.getEventDWheel(); if (i != 0) { handleScrollInput((i > 0) ? -1 : 1); for (ComponentBase c : this.collection.getComponents()) { if (c instanceof ComponentScrollingBase && c.isEnabled())
/*    */           ((ComponentScrollingBase)c).handleScrollInput((i > 0) ? -1 : 1);  }
/*    */        }
/* 47 */      } protected void func_73864_a(int x, int y, int button) { super.func_73864_a(x, y, button);
/* 48 */     if (this.disableScrollBar)
/* 49 */       return;  this.clickedY = y;
/* 50 */     if (x - this.field_147003_i + 17 > 0 && x - this.field_147003_i + 17 < 17 && this.clickedY - this.field_147009_r - 20 > this.barPosition && this.clickedY - this.field_147009_r - 20 < this.barPosition + 38)
/* 51 */       this.scrollPressed = true;  }
/*    */    public abstract void handleScrollInput(int paramInt); protected void func_146976_a(float f, int mouseX, int mouseY) {
/*    */     super.func_146976_a(f, mouseX, mouseY);
/*    */   }
/*    */   protected void func_146286_b(int x, int y, int button) {
/* 56 */     super.func_146286_b(x, y, button);
/* 57 */     this.scrollPressed = false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146273_a(int x, int y, int button, long time) {
/* 62 */     super.func_146273_a(x, y, button, time);
/* 63 */     if (!this.scrollPressed || this.disableScrollBar)
/* 64 */       return;  this.barPosition += y - this.clickedY;
/* 65 */     if (this.barPosition < 0) { this.barPosition = 0; }
/* 66 */     else if (this.barPosition > 247) { this.barPosition = 247; }
/* 67 */      if (this.barPosition != 0 && this.barPosition != 247) this.clickedY = y; 
/* 68 */     barMoved(this.barPosition / 247.0D);
/*    */   }
/*    */   
/*    */   public abstract void barMoved(double paramDouble);
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\client\gui\guicomponents\GUIScrollingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */