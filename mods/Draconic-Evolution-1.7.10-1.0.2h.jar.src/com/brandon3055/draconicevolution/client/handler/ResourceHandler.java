/*    */ package com.brandon3055.draconicevolution.client.handler;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.client.gui.componentguis.GUIManual;
/*    */ import com.brandon3055.draconicevolution.client.utill.CustomResourceLocation;
/*    */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*    */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResourceHandler
/*    */ {
/* 21 */   private static final ResourceLocation defaultParticles = new ResourceLocation("minecraft", "textures/particle/particles.png");
/* 22 */   private static final ResourceLocation particles = new ResourceLocation("draconicevolution", "textures/particle/particles.png");
/* 23 */   private static final Map<String, Optional<CustomResourceLocation>> downloadedImages = new HashMap<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public static void init(FMLPreInitializationEvent event) {
/* 28 */     GUIManual.loadPages();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static CustomResourceLocation getManualImage(String fileName) {
/* 33 */     Optional<CustomResourceLocation> cached = downloadedImages.get(fileName);
/* 34 */     if (cached != null) {
/* 35 */       return cached.orElse(null);
/*    */     }
/*    */     
/* 38 */     CustomResourceLocation location = null;
/*    */     
/*    */     try {
/* 41 */       location = new CustomResourceLocation(fileName);
/* 42 */     } catch (IOException e) {
/* 43 */       LogHelper.error("Failed to read " + fileName);
/* 44 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 47 */     downloadedImages.put(fileName, Optional.ofNullable(location));
/* 48 */     return location;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void bindTexture(ResourceLocation texture) {
/* 55 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(texture);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void bindDefaultParticles() {
/* 62 */     bindTexture(defaultParticles);
/*    */   }
/*    */   
/*    */   public static void bindParticles() {
/* 66 */     bindTexture(particles);
/*    */   }
/*    */   
/*    */   public static ResourceLocation getResource(String rs) {
/* 70 */     return new ResourceLocation("draconicevolution", rs);
/*    */   }
/*    */   
/*    */   public static ResourceLocation getResourceMinecraft(String rs) {
/* 74 */     return new ResourceLocation("minecraft", rs);
/*    */   }
/*    */   
/*    */   public static ResourceLocation getResourceWOP(String rs) {
/* 78 */     return new ResourceLocation(rs);
/*    */   }
/*    */   
/*    */   public static void bindResource(String rs) {
/* 82 */     bindTexture(getResource(rs));
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\handler\ResourceHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */