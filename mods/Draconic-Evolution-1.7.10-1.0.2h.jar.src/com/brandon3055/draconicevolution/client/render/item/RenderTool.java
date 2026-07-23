/*    */ package com.brandon3055.draconicevolution.client.render.item;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*    */ import com.brandon3055.draconicevolution.client.render.IRenderTweak;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.client.IItemRenderer;
/*    */ import net.minecraftforge.client.model.AdvancedModelLoader;
/*    */ import net.minecraftforge.client.model.IModelCustom;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderTool
/*    */   implements IItemRenderer
/*    */ {
/*    */   private IModelCustom toolModel;
/*    */   private String toolTexture;
/*    */   private IRenderTweak tool;
/*    */   
/*    */   public RenderTool(String model, String texture, IRenderTweak tool) {
/* 22 */     this.tool = tool;
/* 23 */     this.toolModel = AdvancedModelLoader.loadModel(ResourceHandler.getResource(model));
/* 24 */     this.toolTexture = texture;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/* 34 */     return (type == IItemRenderer.ItemRenderType.ENTITY && helper == IItemRenderer.ItemRendererHelper.ENTITY_ROTATION);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/* 39 */     GL11.glPushMatrix();
/* 40 */     ResourceHandler.bindResource(this.toolTexture);
/*    */     
/* 42 */     this.tool.tweakRender(type);
/* 43 */     this.toolModel.renderAll();
/*    */     
/* 45 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\item\RenderTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */