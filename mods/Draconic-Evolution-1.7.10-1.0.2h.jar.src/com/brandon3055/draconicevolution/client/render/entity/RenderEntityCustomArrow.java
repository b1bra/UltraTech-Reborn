/*    */ package com.brandon3055.draconicevolution.client.render.entity;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*    */ import com.brandon3055.draconicevolution.common.entity.EntityCustomArrow;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.client.model.AdvancedModelLoader;
/*    */ import net.minecraftforge.client.model.IModelCustom;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderEntityCustomArrow
/*    */   extends Render
/*    */ {
/* 18 */   private IModelCustom arrow = AdvancedModelLoader.loadModel(ResourceHandler.getResource("models/tools/ArrowCommon.obj"));
/*    */   
/*    */   public void doRender(EntityCustomArrow entityArrow, double x, double y, double z, float f1, float f2) {
/* 21 */     func_110777_b((Entity)entityArrow);
/* 22 */     GL11.glPushMatrix();
/* 23 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/* 24 */     GL11.glRotatef(entityArrow.field_70126_B + (entityArrow.field_70177_z - entityArrow.field_70126_B) * f2 - 90.0F, 0.0F, 1.0F, 0.0F);
/* 25 */     GL11.glRotatef(entityArrow.field_70127_C + (entityArrow.field_70125_A - entityArrow.field_70127_C) * f2, 0.0F, 0.0F, 1.0F);
/*    */     
/* 27 */     float f10 = 0.3F;
/* 28 */     float f11 = entityArrow.field_70249_b - f2;
/*    */     
/* 30 */     if (f11 > 0.0F) {
/* 31 */       float f12 = -MathHelper.func_76126_a(f11 * 3.0F) * f11;
/* 32 */       GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
/*    */     } 
/*    */     
/* 35 */     GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
/* 36 */     GL11.glScalef(f10, f10, f10);
/* 37 */     GL11.glEnable(3042);
/* 38 */     GL11.glEnable(32826);
/* 39 */     GL11.glBlendFunc(770, 771);
/*    */ 
/*    */ 
/*    */     
/* 43 */     if (entityArrow.bowProperties != null && entityArrow.bowProperties.energyBolt) {
/* 44 */       this.arrow.renderAll();
/*    */       
/* 46 */       GL11.glTranslated(0.0D, -0.025D, 0.0D);
/* 47 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
/* 48 */       GL11.glScaled(1.05D, 1.05D, 1.05D);
/*    */       
/* 50 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
/* 51 */       GL11.glScaled(1.05D, 1.05D, 1.05D);
/* 52 */       this.arrow.renderAll();
/*    */     } else {
/* 54 */       this.arrow.renderAll();
/*    */     } 
/*    */     
/* 57 */     GL11.glDisable(32826);
/* 58 */     GL11.glDisable(3042);
/* 59 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityCustomArrow arrow) {
/* 68 */     return arrow.bowProperties.energyBolt ? ResourceHandler.getResource("textures/models/reactorCore.png") : ResourceHandler.getResource("textures/models/tools/ArrowCommon.png");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation func_110775_a(Entity p_110775_1_) {
/* 76 */     return getEntityTexture((EntityCustomArrow)p_110775_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_76986_a(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
/* 87 */     doRender((EntityCustomArrow)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\entity\RenderEntityCustomArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */