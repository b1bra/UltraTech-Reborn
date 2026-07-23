/*    */ package com.brandon3055.draconicevolution.client.gui.componentguis;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.apache.commons.io.FilenameUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ManualPage
/*    */ {
/*    */   public String name;
/*    */   public String nameL;
/*    */   public int meta;
/*    */   public String[] imageURLs;
/*    */   public String[] content;
/* 17 */   public int scrollOffset = 0;
/*    */   
/*    */   public ManualPage(String name, String[] imageURLs, String[] content) {
/* 20 */     this.name = name;
/* 21 */     this.imageURLs = imageURLs;
/* 22 */     this.content = content;
/*    */   }
/*    */   
/*    */   public ManualPage(String name, String[] imageURLs, String[] content, String nameL, int meta) {
/* 26 */     this(name, imageURLs, content);
/* 27 */     this.nameL = nameL;
/* 28 */     this.meta = meta;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getImageResourceName(String url) {
/* 34 */     LogHelper.info(url);
/* 35 */     return FilenameUtils.getName(url);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLocalizedName() {
/* 47 */     if (this.nameL != null) return this.nameL; 
/* 48 */     return (this.name.contains("item.") || this.name.contains("tile.")) ? StatCollector.func_74838_a(this.name + ".name") : (this.name.contains("info.") ? this.name.substring(this.name.indexOf("info.") + 5) : "Invalid Name Data");
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\componentguis\ManualPage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */