/*    */ package com.brandon3055.brandonscore.client.gui.guicomponents;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ComponentSlotBackground
/*    */   extends ComponentBase
/*    */ {
/* 11 */   private static final ResourceLocation widgets = new ResourceLocation("brandonscore:textures/gui/Widgets.png");
/*    */   
/*    */   public ComponentSlotBackground(int x, int y) {
/* 14 */     super(x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 19 */     return 18;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 24 */     return 18;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderBackground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/* 29 */     minecraft.field_71446_o.func_110577_a(widgets);
/* 30 */     func_73729_b(this.x, this.y, 119, 1, getWidth(), getHeight());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderForground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {}
/*    */ 
/*    */   
/*    */   public static ComponentCollection addInventorySlots(ComponentCollection c, int xOffset, int yOffset, int lockedSlot) {
/* 39 */     for (int x = 0; x < 9; x++) {
/* 40 */       if (x != lockedSlot)
/* 41 */         c.addComponent(new ComponentSlotBackground(xOffset + x * 18, yOffset + 58)).setGroup("INVENTORY"); 
/*    */     } 
/* 43 */     for (int y = 0; y < 3; y++) {
/* 44 */       for (int i = 0; i < 9; i++) {
/* 45 */         if (i + y * 9 + 9 != lockedSlot)
/* 46 */           c.addComponent(new ComponentSlotBackground(xOffset + i * 18, yOffset + y * 18)).setGroup("INVENTORY"); 
/*    */       } 
/*    */     } 
/* 49 */     return c;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\client\gui\guicomponents\ComponentSlotBackground.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */