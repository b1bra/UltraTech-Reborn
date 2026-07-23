/*    */ package com.brandon3055.brandonscore.client.gui.guicomponents;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ComponentScrollingBase
/*    */   extends ComponentBase
/*    */ {
/*    */   protected GUIScrollingBase gui;
/*    */   
/*    */   public ComponentScrollingBase(int x, int y, GUIScrollingBase gui) {
/* 11 */     super(x, y);
/* 12 */     this.gui = gui;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void handleScrollInput(int paramInt);
/*    */   
/*    */   public boolean isMouseOver(int mouseX, int mouseY) {
/* 19 */     return super.isMouseOver(mouseX, mouseY + this.gui.scrollOffset);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\client\gui\guicomponents\ComponentScrollingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */