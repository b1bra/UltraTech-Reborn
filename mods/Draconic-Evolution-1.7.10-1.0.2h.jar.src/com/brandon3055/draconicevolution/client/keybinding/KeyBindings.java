/*    */ package com.brandon3055.draconicevolution.client.keybinding;
/*    */ 
/*    */ import cpw.mods.fml.client.registry.ClientRegistry;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class KeyBindings
/*    */ {
/*    */   public static KeyBinding placeItem;
/*    */   public static KeyBinding toolConfig;
/*    */   public static KeyBinding toolProfileChange;
/*    */   public static KeyBinding toggleFlight;
/*    */   
/*    */   public static void init() {
/* 21 */     placeItem = new KeyBinding("key.placeItem", 25, "Draconic Evolution");
/* 22 */     toolConfig = new KeyBinding("key.toolConfig", 46, "Draconic Evolution");
/* 23 */     toolProfileChange = new KeyBinding("key.toolProfileChange", 43, "Draconic Evolution");
/* 24 */     toggleFlight = new KeyBinding("key.toggleFlight", 0, "Draconic Evolution");
/* 25 */     ClientRegistry.registerKeyBinding(placeItem);
/* 26 */     ClientRegistry.registerKeyBinding(toolConfig);
/* 27 */     ClientRegistry.registerKeyBinding(toolProfileChange);
/* 28 */     ClientRegistry.registerKeyBinding(toggleFlight);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\keybinding\KeyBindings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */