/*    */ package com.brandon3055.draconicevolution.client.render.item;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*    */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*    */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*    */ import net.minecraft.item.ItemArmor;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.client.IItemRenderer;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderArmor
/*    */   implements IItemRenderer
/*    */ {
/*    */   private ItemArmor armor;
/*    */   
/*    */   public RenderArmor() {}
/*    */   
/*    */   public RenderArmor(ItemArmor armor) {
/* 24 */     this.armor = armor;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack stack, Object... data) {
/* 39 */     if (ConfigHandler.useOldArmorModel) {
/* 40 */       LogHelper.error("You must restart the game for armor model change to effect the armor items!!!");
/*    */       
/*    */       return;
/*    */     } 
/* 44 */     GL11.glPushMatrix();
/* 45 */     ResourceHandler.bindResource(this.armor.getArmorTexture(stack, null, 0, null).replace("draconicevolution:", ""));
/*    */     
/* 47 */     if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.EQUIPPED) {
/* 48 */       GL11.glTranslated(0.5D, 0.5D, 0.5D);
/* 49 */       GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
/*    */     } 
/* 51 */     GL11.glTranslated(0.0D, (this.armor.field_77881_a == 0) ? -0.25D : ((this.armor.field_77881_a == 1) ? 0.42D : ((this.armor.field_77881_a == 2) ? 1.05D : 1.5D)), 0.0D);
/* 52 */     GL11.glRotated(180.0D, -1.0D, 0.0D, 1.0D);
/* 53 */     this.armor.getArmorModel(null, stack, 0).func_78088_a(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
/*    */     
/* 55 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\item\RenderArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */