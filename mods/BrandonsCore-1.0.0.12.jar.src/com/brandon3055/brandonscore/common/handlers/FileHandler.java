/*    */ package com.brandon3055.brandonscore.common.handlers;
/*    */ 
/*    */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileHandler
/*    */ {
/*    */   public static File configFolder;
/*    */   public static File mcDirectory;
/*    */   
/*    */   public static void init(FMLPreInitializationEvent event) {
/* 15 */     configFolder = event.getModConfigurationDirectory();
/* 16 */     mcDirectory = configFolder.getParentFile();
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\common\handlers\FileHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */