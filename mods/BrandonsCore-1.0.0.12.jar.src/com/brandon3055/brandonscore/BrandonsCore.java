/*    */ package com.brandon3055.brandonscore;
/*    */ 
/*    */ import com.brandon3055.brandonscore.common.CommonProxy;
/*    */ import com.brandon3055.brandonscore.common.handlers.FileHandler;
/*    */ import com.brandon3055.brandonscore.common.handlers.ProcessHandler;
/*    */ import com.brandon3055.brandonscore.common.utills.LogHelper;
/*    */ import cpw.mods.fml.common.Mod;
/*    */ import cpw.mods.fml.common.Mod.EventHandler;
/*    */ import cpw.mods.fml.common.Mod.Instance;
/*    */ import cpw.mods.fml.common.SidedProxy;
/*    */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*    */ import cpw.mods.fml.common.event.FMLServerStartingEvent;
/*    */ import cpw.mods.fml.common.network.NetworkCheckHandler;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ @Mod(modid = "BrandonsCore", version = "1.0.0.12", name = "Brandon's Core")
/*    */ public class BrandonsCore
/*    */ {
/*    */   public static final String MODNAME = "Brandon's Core";
/*    */   public static final String MODID = "BrandonsCore";
/*    */   public static final String VERSION = "1.0.0.12";
/*    */   @Instance("BrandonsCore")
/*    */   public static BrandonsCore instance;
/*    */   @SidedProxy(clientSide = "com.brandon3055.brandonscore.client.ClientProxy", serverSide = "com.brandon3055.brandonscore.common.CommonProxy")
/*    */   public static CommonProxy proxy;
/*    */   
/*    */   @NetworkCheckHandler
/*    */   public boolean networkCheck(Map<String, String> map, Side side) {
/* 31 */     return true;
/*    */   }
/*    */   
/*    */   public BrandonsCore() {
/* 35 */     LogHelper.info("Hello Minecraft!!!");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void serverStart(FMLServerStartingEvent event) {}
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void preInit(FMLPreInitializationEvent event) {
/* 46 */     FileHandler.init(event);
/* 47 */     ProcessHandler.init();
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\BrandonsCore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */