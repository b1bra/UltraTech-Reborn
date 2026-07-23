/*    */ package com.brandon3055.draconicevolution.client.render.tile;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileDissEnchanter;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.entity.RenderItem;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderTileDissEnchanter
/*    */   extends TileEntitySpecialRenderer
/*    */ {
/*    */   public void func_147500_a(TileEntity tileEntity, double x, double y, double z, float f) {
/* 25 */     GL11.glPushMatrix();
/*    */     
/* 27 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/* 28 */     TileDissEnchanter tile = (TileDissEnchanter)tileEntity;
/* 29 */     renderBlock(tile, tileEntity.func_145831_w(), tileEntity.field_145851_c, tileEntity.field_145848_d, tileEntity.field_145849_e, f);
/*    */     
/* 31 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   public void renderBlock(TileDissEnchanter tile, World world, int x, int y, int z, float f) {
/* 35 */     Tessellator tessellator = Tessellator.field_78398_a;
/*    */ 
/*    */     
/* 38 */     tessellator.func_78370_a(255, 255, 255, 255);
/* 39 */     tessellator.func_78380_c(200);
/* 40 */     int l = world.func_72802_i(x, y, z, 0);
/* 41 */     int l1 = l % 65536;
/* 42 */     int l2 = l / 65536;
/* 43 */     tessellator.func_78386_a(1.0F, 1.0F, 1.0F);
/* 44 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, l1, l2);
/*    */     
/* 46 */     GL11.glPushMatrix();
/* 47 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 48 */     GL11.glTranslated(0.0D, 1.2D, -0.27D);
/* 49 */     GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
/* 50 */     renderItem(tile, 1, f, false);
/* 51 */     GL11.glPopMatrix();
/* 52 */     if (Minecraft.func_71375_t()) {
/* 53 */       GL11.glPushMatrix();
/* 54 */       GL11.glTranslated(0.0D, 0.4D, 0.0D);
/* 55 */       renderItem(tile, 0, f, true);
/* 56 */       GL11.glPopMatrix();
/*    */     } else {
/* 58 */       GL11.glPushMatrix();
/* 59 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 60 */       GL11.glTranslated(0.0D, 1.2D, -0.37D);
/* 61 */       GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
/* 62 */       renderItem(tile, 0, f, false);
/* 63 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void renderItem(TileDissEnchanter tile, int i, float f, boolean rotate) {
/* 68 */     if (tile.func_70301_a(i) != null) {
/* 69 */       GL11.glPushMatrix();
/*    */       
/* 71 */       ItemStack stack = tile.func_70301_a(i).func_77946_l();
/* 72 */       stack.field_77994_a = 1;
/* 73 */       EntityItem itemEntity = new EntityItem(tile.func_145831_w(), 0.0D, 0.0D, 0.0D, stack);
/* 74 */       itemEntity.field_70290_d = 0.0F;
/*    */       
/* 76 */       GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 77 */       GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 78 */       if (rotate) GL11.glRotatef(tile.timer + f, 0.0F, -1.0F, 0.0F); 
/* 79 */       if (stack.func_77973_b() instanceof net.minecraft.item.ItemBlock) {
/* 80 */         GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 81 */         GL11.glTranslatef(0.0F, 0.045F, 0.0F);
/*    */       } 
/*    */       
/* 84 */       RenderItem.field_82407_g = true;
/* 85 */       RenderManager.field_78727_a.func_147940_a((Entity)itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
/* 86 */       RenderItem.field_82407_g = false;
/*    */       
/* 88 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\tile\RenderTileDissEnchanter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */