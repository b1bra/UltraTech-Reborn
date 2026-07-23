/*     */ package com.brandon3055.draconicevolution.client.render.block;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.lib.References;
/*     */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*     */ import com.brandon3055.brandonscore.common.tags.Tags;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderPortal
/*     */   implements ISimpleBlockRenderingHandler
/*     */ {
/*     */   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}
/*     */   
/*     */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
/*  24 */     Tessellator tess = Tessellator.field_78398_a;
/*     */     
/*  26 */     int meta = world.func_72805_g(x, y, z);
/*     */     
/*  28 */     IIcon c = Tags.Blocks.OBSIDIAN.requirePrimary().getBlock().func_149691_a(0, 0);
/*  29 */     float u = c.func_94209_e();
/*  30 */     float v = c.func_94206_g();
/*  31 */     float U = c.func_94212_f();
/*  32 */     float V = c.func_94210_h();
/*     */     
/*  34 */     tess.func_78370_a(0, 0, 0, 256);
/*  35 */     switch (meta) {
/*     */       case 1:
/*  37 */         tess.func_78374_a(x - 0.01D, y - 0.01D, z + 0.75D, u, v);
/*  38 */         tess.func_78374_a(x - 0.01D, y + 1.01D, z + 0.75D, u, V);
/*  39 */         tess.func_78374_a(x + 1.01D, y + 1.01D, z + 0.75D, U, V);
/*  40 */         tess.func_78374_a(x + 1.01D, y - 0.01D, z + 0.75D, U, v);
/*     */         
/*  42 */         tess.func_78374_a(x + 1.01D, y - 0.01D, z + 0.25D, U, v);
/*  43 */         tess.func_78374_a(x + 1.01D, y + 1.01D, z + 0.25D, U, V);
/*  44 */         tess.func_78374_a(x - 0.01D, y + 1.01D, z + 0.25D, u, V);
/*  45 */         tess.func_78374_a(x - 0.01D, y - 0.01D, z + 0.25D, u, v);
/*     */         
/*  47 */         if (isFrame(world, x + 1, y, z)) {
/*  48 */           tess.func_78374_a(x + 0.99D, y - 0.01D, z + 0.25D, u, v);
/*  49 */           tess.func_78374_a(x + 0.99D, y - 0.01D, z + 0.75D, u, V);
/*  50 */           tess.func_78374_a(x + 0.99D, y + 1.01D, z + 0.75D, U, V);
/*  51 */           tess.func_78374_a(x + 0.99D, y + 1.01D, z + 0.25D, U, v);
/*     */         } 
/*  53 */         if (isFrame(world, x - 1, y, z)) {
/*  54 */           tess.func_78374_a(x + 0.01D, y + 1.01D, z + 0.25D, U, v);
/*  55 */           tess.func_78374_a(x + 0.01D, y + 1.01D, z + 0.75D, U, V);
/*  56 */           tess.func_78374_a(x + 0.01D, y - 0.01D, z + 0.75D, u, V);
/*  57 */           tess.func_78374_a(x + 0.01D, y - 0.01D, z + 0.25D, u, v);
/*     */         } 
/*  59 */         if (isFrame(world, x, y - 1, z)) {
/*  60 */           tess.func_78374_a(x - 0.01D, y + 0.01D, z + 0.25D, u, v);
/*  61 */           tess.func_78374_a(x - 0.01D, y + 0.01D, z + 0.75D, u, V);
/*  62 */           tess.func_78374_a(x + 1.01D, y + 0.01D, z + 0.75D, U, V);
/*  63 */           tess.func_78374_a(x + 1.01D, y + 0.01D, z + 0.25D, U, v);
/*     */         } 
/*  65 */         if (isFrame(world, x, y + 1, z)) {
/*  66 */           tess.func_78374_a(x + 1.01D, y + 0.99D, z + 0.25D, U, v);
/*  67 */           tess.func_78374_a(x + 1.01D, y + 0.99D, z + 0.75D, U, V);
/*  68 */           tess.func_78374_a(x - 0.01D, y + 0.99D, z + 0.75D, u, V);
/*  69 */           tess.func_78374_a(x - 0.01D, y + 0.99D, z + 0.25D, u, v);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 2:
/*  74 */         tess.func_78374_a(x + 0.75D, y - 0.01D, z - 0.01D, u, v);
/*  75 */         tess.func_78374_a(x + 0.75D, y - 0.01D, z + 1.01D, u, V);
/*  76 */         tess.func_78374_a(x + 0.75D, y + 1.01D, z + 1.01D, U, V);
/*  77 */         tess.func_78374_a(x + 0.75D, y + 1.01D, z - 0.01D, U, v);
/*     */         
/*  79 */         tess.func_78374_a(x + 0.25D, y + 1.01D, z - 0.01D, U, v);
/*  80 */         tess.func_78374_a(x + 0.25D, y + 1.01D, z + 1.01D, U, V);
/*  81 */         tess.func_78374_a(x + 0.25D, y - 0.01D, z + 1.01D, u, V);
/*  82 */         tess.func_78374_a(x + 0.25D, y - 0.01D, z - 0.01D, u, v);
/*     */         
/*  84 */         if (isFrame(world, x, y, z + 1)) {
/*  85 */           tess.func_78374_a(x + 0.25D, y - 0.01D, z + 0.99D, u, v);
/*  86 */           tess.func_78374_a(x + 0.25D, y + 1.01D, z + 0.99D, U, V);
/*  87 */           tess.func_78374_a(x + 0.75D, y + 1.01D, z + 0.99D, U, V);
/*  88 */           tess.func_78374_a(x + 0.75D, y - 0.01D, z + 0.99D, u, v);
/*     */         } 
/*  90 */         if (isFrame(world, x, y, z - 1)) {
/*  91 */           tess.func_78374_a(x + 0.75D, y - 0.01D, z + 0.01D, u, v);
/*  92 */           tess.func_78374_a(x + 0.75D, y + 1.01D, z + 0.01D, U, V);
/*  93 */           tess.func_78374_a(x + 0.25D, y + 1.01D, z + 0.01D, U, V);
/*  94 */           tess.func_78374_a(x + 0.25D, y - 0.01D, z + 0.01D, u, v);
/*     */         } 
/*  96 */         if (isFrame(world, x, y - 1, z)) {
/*  97 */           tess.func_78374_a(x + 0.25D, y + 0.01D, z - 0.01D, u, v);
/*  98 */           tess.func_78374_a(x + 0.25D, y + 0.01D, z + 1.01D, u, V);
/*  99 */           tess.func_78374_a(x + 0.75D, y + 0.01D, z + 1.01D, U, V);
/* 100 */           tess.func_78374_a(x + 0.75D, y + 0.01D, z - 0.01D, U, v);
/*     */         } 
/* 102 */         if (isFrame(world, x, y + 1, z)) {
/* 103 */           tess.func_78374_a(x + 0.75D, y + 0.99D, z - 0.01D, U, v);
/* 104 */           tess.func_78374_a(x + 0.75D, y + 0.99D, z + 1.01D, U, V);
/* 105 */           tess.func_78374_a(x + 0.25D, y + 0.99D, z + 1.01D, u, V);
/* 106 */           tess.func_78374_a(x + 0.25D, y + 0.99D, z - 0.01D, u, v);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 3:
/* 111 */         tess.func_78374_a(x - 0.01D, y + 0.25D, z - 0.01D, u, v);
/* 112 */         tess.func_78374_a(x - 0.01D, y + 0.25D, z + 1.01D, u, V);
/* 113 */         tess.func_78374_a(x + 1.01D, y + 0.25D, z + 1.01D, U, V);
/* 114 */         tess.func_78374_a(x + 1.01D, y + 0.25D, z - 0.01D, U, v);
/*     */         
/* 116 */         tess.func_78374_a(x + 1.01D, y + 0.75D, z - 0.01D, U, v);
/* 117 */         tess.func_78374_a(x + 1.01D, y + 0.75D, z + 1.01D, U, V);
/* 118 */         tess.func_78374_a(x - 0.01D, y + 0.75D, z + 1.01D, u, V);
/* 119 */         tess.func_78374_a(x - 0.01D, y + 0.75D, z - 0.01D, u, v);
/*     */         
/* 121 */         if (isFrame(world, x, y, z + 1)) {
/* 122 */           tess.func_78374_a(x - 0.01D, y + 0.25D, z + 0.99D, u, v);
/* 123 */           tess.func_78374_a(x - 0.01D, y + 0.75D, z + 0.99D, u, V);
/* 124 */           tess.func_78374_a(x + 1.01D, y + 0.75D, z + 0.99D, U, V);
/* 125 */           tess.func_78374_a(x + 1.01D, y + 0.25D, z + 0.99D, U, v);
/*     */         } 
/* 127 */         if (isFrame(world, x, y, z - 1)) {
/* 128 */           tess.func_78374_a(x + 1.01D, y + 0.25D, z + 0.01D, U, v);
/* 129 */           tess.func_78374_a(x + 1.01D, y + 0.75D, z + 0.01D, U, V);
/* 130 */           tess.func_78374_a(x - 0.01D, y + 0.75D, z + 0.01D, u, V);
/* 131 */           tess.func_78374_a(x - 0.01D, y + 0.25D, z + 0.01D, u, v);
/*     */         } 
/* 133 */         if (isFrame(world, x + 1, y, z)) {
/* 134 */           tess.func_78374_a(x + 0.99D, y + 0.25D, z - 0.01D, u, v);
/* 135 */           tess.func_78374_a(x + 0.99D, y + 0.25D, z + 1.01D, u, V);
/* 136 */           tess.func_78374_a(x + 0.99D, y + 0.75D, z + 1.01D, U, V);
/* 137 */           tess.func_78374_a(x + 0.99D, y + 0.75D, z - 0.01D, U, v);
/*     */         } 
/* 139 */         if (isFrame(world, x - 1, y, z)) {
/* 140 */           tess.func_78374_a(x + 0.01D, y + 0.75D, z - 0.01D, U, v);
/* 141 */           tess.func_78374_a(x + 0.01D, y + 0.75D, z + 1.01D, U, V);
/* 142 */           tess.func_78374_a(x + 0.01D, y + 0.25D, z + 1.01D, u, V);
/* 143 */           tess.func_78374_a(x + 0.01D, y + 0.25D, z - 0.01D, u, v);
/*     */         } 
/*     */         break;
/*     */     } 
/* 147 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender3DInInventory(int modelId) {
/* 152 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderId() {
/* 157 */     return References.idPortal;
/*     */   }
/*     */   
/*     */   private boolean isFrame(IBlockAccess access, int x, int y, int z) {
/* 161 */     Block block = access.func_147439_a(x, y, z);
/* 162 */     return (block == ModBlocks.infusedObsidian || block == ModBlocks.dislocatorReceptacle);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\block\RenderPortal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */