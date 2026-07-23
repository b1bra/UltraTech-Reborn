/*    */ package com.brandon3055.draconicevolution.integration.nei;
/*    */ 
/*    */ import codechicken.nei.VisiblityData;
/*    */ import codechicken.nei.api.INEIGuiHandler;
/*    */ import codechicken.nei.api.TaggedInventoryArea;
/*    */ import com.brandon3055.draconicevolution.client.gui.GUIUpgradeModifier;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DENEIGuiHandler
/*    */   implements INEIGuiHandler
/*    */ {
/*    */   public VisiblityData modifyVisiblity(GuiContainer gui, VisiblityData currentVisibility) {
/* 22 */     if (gui instanceof GUIUpgradeModifier && ((GUIUpgradeModifier)gui).inUse) { currentVisibility.showNEI = false; }
/* 23 */     else if (gui instanceof com.brandon3055.draconicevolution.client.gui.componentguis.GUIToolConfig || gui instanceof com.brandon3055.draconicevolution.client.gui.componentguis.GUIManual) { currentVisibility.showNEI = false; }
/* 24 */      return currentVisibility;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<Integer> getItemSpawnSlots(GuiContainer gui, ItemStack item) {
/* 29 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<TaggedInventoryArea> getInventoryAreas(GuiContainer gui) {
/* 34 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean handleDragNDrop(GuiContainer gui, int mousex, int mousey, ItemStack draggedStack, int button) {
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h) {
/* 44 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\integration\nei\DENEIGuiHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */