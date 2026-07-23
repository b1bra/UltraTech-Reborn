/*    */ package com.brandon3055.draconicevolution.client.render.tile;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.TileEnergyPylon;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.client.model.AdvancedModelLoader;
/*    */ import net.minecraftforge.client.model.IModelCustom;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderTileEnergyPylon
/*    */   extends TileEntitySpecialRenderer
/*    */ {
/* 18 */   private static final ResourceLocation model_texture = new ResourceLocation("draconicevolution", "textures/models/pylon_sphere_texture.png");
/*    */ 
/*    */ 
/*    */   
/* 22 */   private IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("draconicevolution", "models/pylon_sphere.obj"));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_147500_a(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {
/* 28 */     if (!(tile instanceof TileEnergyPylon))
/* 29 */       return;  TileEnergyPylon pylon = (TileEnergyPylon)tile;
/* 30 */     if (!pylon.active)
/* 31 */       return;  float scale = pylon.modelScale + (timeSinceLastTick *= !pylon.reciveEnergy ? -0.01F : 0.01F);
/* 32 */     float rotation = pylon.modelRotation + timeSinceLastTick / 2.0F;
/*    */     
/* 34 */     GL11.glPushMatrix();
/* 35 */     GL11.glPushAttrib(1048575);
/*    */     
/* 37 */     GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
/* 38 */     if (pylon.func_145831_w().func_72805_g(pylon.field_145851_c, pylon.field_145848_d, pylon.field_145849_e) == 1) {
/* 39 */       GL11.glTranslated(0.0D, 1.0D, 0.0D);
/*    */     } else {
/* 41 */       GL11.glTranslated(0.0D, -1.0D, 0.0D);
/*    */     } 
/*    */     
/* 44 */     GL11.glAlphaFunc(516, 0.0F);
/*    */     
/* 46 */     func_147499_a(model_texture);
/* 47 */     GL11.glTexParameterf(3553, 10242, 10497.0F);
/* 48 */     GL11.glTexParameterf(3553, 10243, 10497.0F);
/* 49 */     GL11.glDisable(2896);
/* 50 */     GL11.glDisable(2884);
/*    */     
/* 52 */     GL11.glEnable(3042);
/* 53 */     OpenGlHelper.func_148821_a(770, 771, 1, 0);
/* 54 */     GL11.glDepthMask(false);
/* 55 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 200.0F, 200.0F);
/*    */     
/* 57 */     GL11.glPushMatrix();
/* 58 */     float scale1 = scale % 1.0F;
/* 59 */     GL11.glScalef(scale1, scale1, scale1);
/* 60 */     GL11.glRotatef(rotation * 0.5F, 0.0F, -1.0F, -0.5F);
/* 61 */     GL11.glColor4d(1.0D, 1.0D, 1.0D, (1.0F - scale1));
/* 62 */     this.model.renderAll();
/* 63 */     GL11.glPopMatrix();
/*    */     
/* 65 */     GL11.glPushMatrix();
/* 66 */     float scale2 = (scale + 0.25F) % 1.0F;
/* 67 */     GL11.glScalef(scale2, scale2, scale2);
/* 68 */     GL11.glRotatef(rotation * 0.5F, 0.0F, -1.0F, -0.5F);
/* 69 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F - scale2);
/* 70 */     this.model.renderAll();
/* 71 */     GL11.glPopMatrix();
/*    */     
/* 73 */     GL11.glPushMatrix();
/* 74 */     float scale3 = (scale + 0.5F) % 1.0F;
/* 75 */     GL11.glScalef(scale3, scale3, scale3);
/* 76 */     GL11.glRotatef(rotation * 0.5F, 0.0F, -1.0F, -0.5F);
/* 77 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F - scale3);
/* 78 */     this.model.renderAll();
/* 79 */     GL11.glPopMatrix();
/*    */     
/* 81 */     GL11.glPushMatrix();
/* 82 */     float scale4 = (scale + 0.75F) % 1.0F;
/* 83 */     GL11.glScalef(scale4, scale4, scale4);
/* 84 */     GL11.glRotatef(rotation * 0.5F, 0.0F, -1.0F, -0.5F);
/* 85 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F - scale4);
/* 86 */     this.model.renderAll();
/* 87 */     GL11.glPopMatrix();
/*    */     
/* 89 */     GL11.glEnable(2896);
/* 90 */     GL11.glEnable(3553);
/* 91 */     GL11.glAlphaFunc(516, 0.1F);
/* 92 */     GL11.glDisable(3042);
/* 93 */     GL11.glDepthMask(true);
/* 94 */     GL11.glPopAttrib();
/* 95 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\tile\RenderTileEnergyPylon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */