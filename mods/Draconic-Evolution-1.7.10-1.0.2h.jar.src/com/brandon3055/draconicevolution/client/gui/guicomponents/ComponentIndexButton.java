/*    */ package com.brandon3055.draconicevolution.client.gui.guicomponents;
/*    */ 
/*    */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentScrollingBase;
/*    */ import com.brandon3055.brandonscore.client.gui.guicomponents.GUIScrollingBase;
/*    */ import com.brandon3055.brandonscore.common.utills.Utills;
/*    */ import com.brandon3055.draconicevolution.client.gui.componentguis.ManualPage;
/*    */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ComponentIndexButton
/*    */   extends ComponentScrollingBase
/*    */ {
/*    */   private ManualPage page;
/*    */   private ItemStack stack;
/*    */   
/*    */   public ComponentIndexButton(int x, int y, GUIScrollingBase gui, ManualPage page) {
/* 22 */     super(x, y, gui);
/* 23 */     this.page = page;
/* 24 */     this.stack = Utills.getStackFromName(page.name, page.meta);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleScrollInput(int direction) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 34 */     return 200;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 39 */     return 20;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderBackground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/* 44 */     if (isOnScreen()) {
/* 45 */       int sy = this.y - this.gui.scrollOffset;
/* 46 */       boolean mouseOver = isMouseOver(mouseX, mouseY);
/*    */       
/* 48 */       this.fontRendererObj.func_78276_b(this.page.getLocalizedName(), this.x + 19, sy, mouseOver ? 14483711 : 0);
/* 49 */       ResourceHandler.bindResource("textures/gui/Widgets.png");
/*    */ 
/*    */       
/* 52 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 53 */       if (mouseOver)
/* 54 */       { GL11.glColor4f(0.0F, 1.0F, 1.0F, 1.0F);
/* 55 */         func_73729_b(this.x - 2, sy - 2, 118, 0, 20, 20); }
/* 56 */       else { func_73729_b(this.x - 1, sy - 1, 138, 0, 18, 18); }
/*    */       
/* 58 */       if (this.stack != null && this.stack.func_77973_b() != null) { drawItemStack(this.stack, this.x, sy, ""); }
/* 59 */       else { drawItemStack(new ItemStack(Items.field_151122_aG), this.x, sy, ""); }
/*    */     
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isOnScreen() {
/* 65 */     int sy = this.y - this.gui.scrollOffset;
/* 66 */     return (sy > 1 && sy + getHeight() < this.gui.getYSize());
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderForground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {}
/*    */ 
/*    */   
/*    */   public ManualPage getPage() {
/* 74 */     return this.page;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\guicomponents\ComponentIndexButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */