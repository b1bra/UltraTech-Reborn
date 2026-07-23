/*    */ package com.brandon3055.brandonscore.client.utills;
/*    */ 
/*    */ import com.brandon3055.brandonscore.common.utills.LogHelper;
/*    */ import java.net.URI;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientUtills
/*    */ {
/*    */   public static void openLink(String url) {
/*    */     try {
/* 14 */       URI uri = new URI(url);
/* 15 */       Class<?> oclass = Class.forName("java.awt.Desktop");
/* 16 */       Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 17 */       oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { uri });
/* 18 */     } catch (Throwable throwable) {
/* 19 */       LogHelper.error("Couldn't open link");
/* 20 */       throwable.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\clien\\utills\ClientUtills.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */