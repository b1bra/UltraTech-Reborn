/*    */ package com.brandon3055.draconicevolution.client.gui;
/*    */ 
/*    */ import cpw.mods.fml.client.IModGuiFactory;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DEGUIFactory
/*    */   implements IModGuiFactory
/*    */ {
/*    */   public void initialize(Minecraft minecraftInstance) {}
/*    */   
/*    */   public Class<? extends GuiScreen> mainConfigGuiClass() {
/* 20 */     return (Class)ConfigGUI.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
/* 25 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public IModGuiFactory.RuntimeOptionGuiHandler getHandlerFor(IModGuiFactory.RuntimeOptionCategoryElement element) {
/* 30 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\DEGUIFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */