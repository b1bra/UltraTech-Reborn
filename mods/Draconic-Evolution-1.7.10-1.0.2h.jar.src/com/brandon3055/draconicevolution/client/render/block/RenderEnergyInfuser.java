/*    */ package com.brandon3055.draconicevolution.client.render.block;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileEnergyInfuser;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.client.IItemRenderer;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ public class RenderEnergyInfuser
/*    */   implements IItemRenderer {
/*    */   public RenderEnergyInfuser() {
/* 13 */     this.tile = new TileEnergyInfuser();
/* 14 */     this.tile.rotation = 0.0F;
/* 15 */     this.tile.running = false;
/*    */   }
/*    */   private TileEnergyInfuser tile;
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
/* 30 */     if (type == IItemRenderer.ItemRenderType.ENTITY) GL11.glTranslatef(-0.5F, 0.0F, -0.5F); 
/* 31 */     TileEntityRendererDispatcher.field_147556_a.func_147549_a((TileEntity)this.tile, 0.0D, 0.0D, 0.0D, 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\block\RenderEnergyInfuser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */