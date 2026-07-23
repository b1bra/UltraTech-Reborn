/*    */ package com.brandon3055.draconicevolution;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.client.creativetab.DETab;
/*    */ import com.brandon3055.draconicevolution.common.CommonProxy;
/*    */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*    */ import com.gamerforea.draconicevolution.EventConfig;
/*    */ import com.gamerforea.eventhelper.config.ConfigUtils;
/*    */ import cpw.mods.fml.common.Mod;
/*    */ import cpw.mods.fml.common.Mod.EventHandler;
/*    */ import cpw.mods.fml.common.Mod.Instance;
/*    */ import cpw.mods.fml.common.SidedProxy;
/*    */ import cpw.mods.fml.common.event.FMLInitializationEvent;
/*    */ import cpw.mods.fml.common.event.FMLPostInitializationEvent;
/*    */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*    */ import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.enchantment.Enchantment;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mod(modid = "DraconicEvolution", name = "Draconic Evolution", version = "1.0.2h", guiFactory = "com.brandon3055.draconicevolution.client.gui.DEGUIFactory", dependencies = "after:NotEnoughItems;after:NotEnoughItems;after:ThermalExpansion;after:ThermalFoundation;required-after:BrandonsCore@[1.0.0.11,);")
/*    */ public class DraconicEvolution
/*    */ {
/*    */   @Instance("DraconicEvolution")
/*    */   public static DraconicEvolution instance;
/*    */   @SidedProxy(clientSide = "com.brandon3055.draconicevolution.client.ClientProxy", serverSide = "com.brandon3055.draconicevolution.common.CommonProxy")
/*    */   public static CommonProxy proxy;
/* 31 */   public static CreativeTabs tabToolsWeapons = (CreativeTabs)new DETab(CreativeTabs.getNextID(), "DraconicEvolution", "toolsAndWeapons", 0);
/* 32 */   public static CreativeTabs tabBlocksItems = (CreativeTabs)new DETab(CreativeTabs.getNextID(), "DraconicEvolution", "blocksAndItems", 1);
/*    */   
/*    */   public static final String networkChannelName = "DEvolutionNC";
/*    */   
/*    */   public static SimpleNetworkWrapper network;
/*    */   public static Enchantment reaperEnchant;
/*    */   
/*    */   public DraconicEvolution() {
/* 40 */     LogHelper.info("Hello Minecraft!!!");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public static void preInit(FMLPreInitializationEvent event) {
/* 45 */     ConfigUtils.readConfig(EventConfig.class);
/* 46 */     proxy.preInit(event);
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void init(FMLInitializationEvent event) {
/* 52 */     proxy.init(event);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void postInit(FMLPostInitializationEvent event) {
/* 57 */     proxy.postInit(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\DraconicEvolution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */