/*    */ package com.brandon3055.brandonscore.common.utills;
/*    */ 
/*    */ import cpw.mods.fml.common.FMLLog;
/*    */ import org.apache.logging.log4j.Level;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LogHelper
/*    */ {
/*    */   public static void log(Level logLevel, Object object) {
/* 13 */     FMLLog.log("Brandon's Core", logLevel, String.valueOf(object), new Object[0]);
/*    */   }
/*    */   
/*    */   public static void all(Object object) {
/* 17 */     log(Level.ALL, object);
/*    */   }
/*    */   
/*    */   public static void debug(Object object) {
/* 21 */     log(Level.DEBUG, object);
/*    */   }
/*    */   
/*    */   public static void error(Object object) {
/* 25 */     log(Level.ERROR, object);
/*    */   }
/*    */   
/*    */   public static void fatal(Object object) {
/* 29 */     log(Level.FATAL, object);
/*    */   }
/*    */   
/*    */   public static void info(Object object) {
/* 33 */     log(Level.INFO, object);
/*    */   }
/*    */   
/*    */   public static void off(Object object) {
/* 37 */     log(Level.OFF, object);
/*    */   }
/*    */   
/*    */   public static void trace(Object object) {
/* 41 */     log(Level.TRACE, object);
/*    */   }
/*    */   
/*    */   public static void warn(Object object) {
/* 45 */     log(Level.WARN, object);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\commo\\utills\LogHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */