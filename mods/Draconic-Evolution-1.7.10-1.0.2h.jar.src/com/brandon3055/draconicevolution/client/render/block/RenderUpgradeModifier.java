/*    */ package com.brandon3055.draconicevolution.client.render.block;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileUpgradeModifier;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.client.IItemRenderer;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ public class RenderUpgradeModifier
/*    */   implements IItemRenderer {
/*    */   public RenderUpgradeModifier() {
/* 13 */     this.tile = new TileUpgradeModifier();
/* 14 */     this.tile.rotation = 0.0F;
/*    */   }
/*    */   private TileUpgradeModifier tile;
/*    */   
/*    */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/* 19 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/* 29 */     if (type == IItemRenderer.ItemRenderType.ENTITY) GL11.glTranslatef(-0.5F, 0.0F, -0.5F); 
/* 30 */     TileEntityRendererDispatcher.field_147556_a.func_147549_a((TileEntity)this.tile, 0.0D, 0.0D, 0.0D, 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\block\RenderUpgradeModifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */