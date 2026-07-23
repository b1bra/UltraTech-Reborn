/*    */ package com.brandon3055.brandonscore.common.handlers;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.BufferedOutputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.Enumeration;
/*    */ import java.util.zip.ZipEntry;
/*    */ import java.util.zip.ZipFile;
/*    */ 
/*    */ public class ZipHelper {
/*    */   public static void unzip(File zipFile, File destination) throws IOException {
/* 14 */     ZipFile zip = new ZipFile(zipFile);
/*    */     
/* 16 */     destination.mkdir();
/* 17 */     Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();
/*    */     
/* 19 */     while (zipFileEntries.hasMoreElements()) {
/* 20 */       ZipEntry entry = zipFileEntries.nextElement();
/* 21 */       String currentEntry = entry.getName();
/* 22 */       File destFile = new File(destination, currentEntry);
/* 23 */       File destinationParent = destFile.getParentFile();
/* 24 */       destinationParent.mkdirs();
/*    */       
/* 26 */       if (!entry.isDirectory()) {
/* 27 */         BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
/*    */         
/* 29 */         byte[] data = new byte[2048];
/* 30 */         FileOutputStream fos = new FileOutputStream(destFile);
/* 31 */         BufferedOutputStream dest = new BufferedOutputStream(fos, 2048); int currentByte;
/* 32 */         while ((currentByte = is.read(data, 0, 2048)) != -1) {
/* 33 */           dest.write(data, 0, currentByte);
/*    */         }
/* 35 */         dest.flush();
/* 36 */         dest.close();
/* 37 */         is.close();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\common\handlers\ZipHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */