/*     */ package com.brandon3055.draconicevolution.client.render.tile;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TilePlacedItem;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderTilePlacedItem
/*     */   extends TileEntitySpecialRenderer
/*     */ {
/*     */   public void func_147500_a(TileEntity te, double x, double y, double z, float timeSinceLastTick) {
/*  28 */     if (!(te instanceof TilePlacedItem))
/*  29 */       return;  TilePlacedItem tile = (TilePlacedItem)te;
/*  30 */     if (tile.getStack() == null)
/*  31 */       return;  GL11.glPushMatrix();
/*  32 */     GL11.glPushAttrib(8192);
/*  33 */     GL11.glTranslated(x, y, z);
/*  34 */     renderItem(tile);
/*  35 */     GL11.glPopAttrib();
/*  36 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void renderItem(TilePlacedItem tile) {
/*  40 */     ItemStack stack = tile.getStack();
/*  41 */     WorldClient worldClient = (Minecraft.func_71410_x()).field_71441_e;
/*  42 */     EntityItem itemEntity = new EntityItem(tile.func_145831_w(), 0.0D, 0.0D, 0.0D, stack);
/*  43 */     int meta = worldClient.func_72805_g(tile.field_145851_c, tile.field_145848_d, tile.field_145849_e);
/*     */     
/*  45 */     itemEntity.field_70290_d = 0.0F;
/*  46 */     boolean is3D = stack.func_77973_b().func_77662_d();
/*  47 */     boolean isBlock = stack.func_77973_b() instanceof net.minecraft.item.ItemBlock;
/*     */     
/*  49 */     if (isBlock) {
/*  50 */       GL11.glTranslatef(0.5F, 0.25F, 0.5F);
/*  51 */       GL11.glScalef(1.5F, 1.5F, 1.5F);
/*  52 */       metaAdjustBlock(meta);
/*  53 */       GL11.glRotatef(tile.rotation, 0.0F, 1.0F, 0.0F);
/*  54 */     } else if (is3D || stack.func_77973_b() instanceof net.minecraft.item.ItemArmor || stack.func_77973_b() instanceof net.minecraft.item.ItemBow) {
/*  55 */       GL11.glScalef(2.0F, 2.0F, 2.0F);
/*  56 */       GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);
/*  57 */       GL11.glTranslatef(0.25F, -0.45F, 0.02F);
/*  58 */       metaAdjustItemTool(meta);
/*  59 */       GL11.glTranslatef(0.0F, 0.21F, 0.0F);
/*  60 */       GL11.glRotatef(tile.rotation, 0.0F, 0.0F, 1.0F);
/*  61 */       GL11.glTranslatef(0.0F, -0.21F, 0.0F);
/*  62 */     } else if (stack.func_77973_b() instanceof com.brandon3055.draconicevolution.common.items.MobSoul) {
/*     */       
/*  64 */       GL11.glTranslatef(0.5F, 0.3F, 0.5F);
/*     */     } else {
/*  66 */       GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);
/*  67 */       GL11.glTranslatef(0.5F, -0.65F, 0.02F);
/*  68 */       metaAdjustItem(meta);
/*  69 */       GL11.glTranslatef(0.0F, 0.18F, 0.0F);
/*  70 */       GL11.glRotatef(tile.rotation, 0.0F, 0.0F, 1.0F);
/*  71 */       GL11.glTranslatef(0.0F, -0.18F, 0.0F);
/*     */     } 
/*     */     
/*  74 */     GL11.glEnable(3042);
/*  75 */     GL11.glBlendFunc(770, 771);
/*  76 */     RenderItem.field_82407_g = true;
/*  77 */     RenderManager.field_78727_a.func_147940_a((Entity)itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
/*  78 */     RenderItem.field_82407_g = false;
/*  79 */     GL11.glDisable(3042);
/*     */   }
/*     */   
/*     */   private void metaAdjustItemTool(int meta) {
/*  83 */     switch (meta) {
/*     */       case 0:
/*  85 */         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*  86 */         GL11.glTranslatef(0.0F, 0.0F, -0.46F);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/*  91 */         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  92 */         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*  93 */         GL11.glTranslatef(0.0F, 0.02F, -0.03F);
/*     */         break;
/*     */       case 3:
/*  96 */         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  97 */         GL11.glTranslatef(0.0F, 0.02F, -0.43F);
/*     */         break;
/*     */       case 4:
/* 100 */         GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
/* 101 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);
/* 102 */         GL11.glTranslatef(-0.205F, 0.02F, -0.23F);
/*     */         break;
/*     */       case 5:
/* 105 */         GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
/* 106 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);
/* 107 */         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 108 */         GL11.glTranslatef(0.223F, 0.0F, -0.23F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void metaAdjustItem(int meta) {
/* 114 */     switch (meta) {
/*     */       case 0:
/* 116 */         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 117 */         GL11.glTranslatef(0.0F, 0.0F, -0.96F);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 122 */         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 123 */         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 124 */         GL11.glTranslatef(0.0F, 0.32F, -0.33F);
/*     */         break;
/*     */       case 3:
/* 127 */         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 128 */         GL11.glTranslatef(0.0F, 0.3F, -0.63F);
/*     */         break;
/*     */       case 4:
/* 131 */         GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
/* 132 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);
/* 133 */         GL11.glTranslatef(-0.17F, 0.302F, -0.475F);
/*     */         break;
/*     */       case 5:
/* 136 */         GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
/* 137 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);
/* 138 */         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 139 */         GL11.glTranslatef(0.15F, 0.3F, -0.475F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void metaAdjustBlock(int meta) {
/* 145 */     switch (meta) {
/*     */       case 0:
/* 147 */         GL11.glTranslatef(0.0F, 0.51F, 0.0F);
/* 148 */         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/*     */         break;
/*     */       case 1:
/* 151 */         GL11.glTranslatef(0.0F, -0.17F, 0.0F);
/*     */         break;
/*     */       case 2:
/* 154 */         GL11.glTranslatef(-0.0F, 0.17F, 0.34F);
/* 155 */         GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);
/*     */         break;
/*     */       case 3:
/* 158 */         GL11.glTranslatef(0.0F, 0.17F, -0.34F);
/* 159 */         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*     */         break;
/*     */       case 4:
/* 162 */         GL11.glTranslatef(0.34F, 0.17F, 0.0F);
/* 163 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/*     */         break;
/*     */       case 5:
/* 166 */         GL11.glTranslatef(-0.34F, 0.17F, 0.0F);
/* 167 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\tile\RenderTilePlacedItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */