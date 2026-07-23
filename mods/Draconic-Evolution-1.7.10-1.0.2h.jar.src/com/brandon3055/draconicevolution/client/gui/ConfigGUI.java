/*    */ package com.brandon3055.draconicevolution.client.gui;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*    */ import cpw.mods.fml.client.config.GuiConfig;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraftforge.common.config.ConfigElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConfigGUI
/*    */   extends GuiConfig
/*    */ {
/*    */   public ConfigGUI(GuiScreen parent) {
/* 15 */     super(parent, (new ConfigElement(ConfigHandler.config.getCategory("general"))).getChildElements(), "DraconicEvolution", false, false, GuiConfig.getAbridgedConfigPath(ConfigHandler.config.toString()));
/* 16 */     this.configElements.addAll((new ConfigElement(ConfigHandler.config.getCategory("spawner"))).getChildElements());
/* 17 */     this.configElements.addAll((new ConfigElement(ConfigHandler.config.getCategory("long range dislocator"))).getChildElements());
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\ConfigGUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */