/*    */ package com.brandon3055.draconicevolution.common.utills;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
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
/* 14 */     if (ConfigHandler.disableLog) {
/*    */       return;
/*    */     }
/* 17 */     FMLLog.log("Draconic Evolution", logLevel, String.valueOf(object), new Object[0]);
/*    */   }
/*    */   
/*    */   public static void all(Object object) {
/* 21 */     log(Level.ALL, object);
/*    */   }
/*    */   
/*    */   public static void debug(Object object) {
/* 25 */     log(Level.DEBUG, object);
/*    */   }
/*    */   
/*    */   public static void error(Object object) {
/* 29 */     log(Level.ERROR, object);
/*    */   }
/*    */   
/*    */   public static void fatal(Object object) {
/* 33 */     log(Level.FATAL, object);
/*    */   }
/*    */   
/*    */   public static void info(Object object) {
/* 37 */     log(Level.INFO, object);
/*    */   }
/*    */   
/*    */   public static void off(Object object) {
/* 41 */     log(Level.OFF, object);
/*    */   }
/*    */   
/*    */   public static void trace(Object object) {
/* 45 */     log(Level.TRACE, object);
/*    */   }
/*    */   
/*    */   public static void warn(Object object) {
/* 49 */     log(Level.WARN, object);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\commo\\utills\LogHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */