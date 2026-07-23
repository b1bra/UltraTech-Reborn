/*    */ package com.brandon3055.draconicevolution.client.render.block;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.lib.References;
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileTeleporterStand;
/*    */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.RenderBlocks;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderTeleporterStand
/*    */   implements ISimpleBlockRenderingHandler
/*    */ {
/*    */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
/* 19 */     GL11.glPushMatrix();
/* 20 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 21 */     TileEntityRendererDispatcher.field_147556_a.func_147549_a((TileEntity)new TileTeleporterStand(), 0.0D, 0.0D, 0.0D, 0.0F);
/* 22 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldRender3DInInventory(int modelId) {
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRenderId() {
/* 37 */     return References.idTeleporterStand;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\block\RenderTeleporterStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */