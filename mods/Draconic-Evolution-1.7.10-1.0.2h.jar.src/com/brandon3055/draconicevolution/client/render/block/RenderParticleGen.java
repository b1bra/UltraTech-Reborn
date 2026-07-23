/*    */ package com.brandon3055.draconicevolution.client.render.block;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileParticleGenerator;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.client.IItemRenderer;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class RenderParticleGen
/*    */   implements IItemRenderer
/*    */ {
/* 15 */   private TileEntity tile = (TileEntity)new TileParticleGenerator();
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/* 20 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/* 30 */     GL11.glPushMatrix();
/* 31 */     GL11.glPushAttrib(8192);
/* 32 */     if (type == IItemRenderer.ItemRenderType.ENTITY) GL11.glTranslatef(-0.5F, 0.0F, -0.5F); 
/* 33 */     TileEntityRendererDispatcher.field_147556_a.func_147549_a(this.tile, 0.0D, 0.0D, 0.0D, 0.0F);
/* 34 */     RenderHelper.func_74518_a();
/* 35 */     GL11.glPopAttrib();
/* 36 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\block\RenderParticleGen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */