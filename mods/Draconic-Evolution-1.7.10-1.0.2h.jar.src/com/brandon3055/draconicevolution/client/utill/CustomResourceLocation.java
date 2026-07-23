/*    */ package com.brandon3055.draconicevolution.client.utill;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import javax.imageio.ImageIO;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.resources.IResource;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomResourceLocation
/*    */   extends ResourceLocation
/*    */ {
/*    */   private final int width;
/*    */   private final int height;
/*    */   
/*    */   public CustomResourceLocation(String texturePath) throws IOException {
/* 23 */     super("draconicevolution", "textures/gui/manualimages/" + texturePath);
/*    */     
/* 25 */     ResourceLocation location = new ResourceLocation("draconicevolution", "textures/gui/manualimages/" + texturePath);
/* 26 */     IResource resource = Minecraft.func_71410_x().func_110442_L().func_110536_a(location);
/*    */     
/* 28 */     try (InputStream in = resource.func_110527_b()) {
/* 29 */       BufferedImage image = ImageIO.read(in);
/* 30 */       if (image == null) {
/* 31 */         throw new IOException(location + " is not an image");
/*    */       }
/*    */       
/* 34 */       this.width = image.getWidth();
/* 35 */       this.height = image.getHeight();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void bind() {
/* 40 */     ResourceHandler.bindTexture(this);
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 44 */     return this.width;
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 48 */     return this.height;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\clien\\utill\CustomResourceLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */