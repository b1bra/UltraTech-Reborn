/*     */ package com.brandon3055.draconicevolution.client.render.tile;
/*     */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.CustomSpawnerBaseLogic;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileCustomSpawner;
/*     */ import com.gamerforea.client.util.TessellatorDebuggerUtils;
/*     */ import com.gamerforea.draconicevolution.util.RenderUtils;
/*     */ import com.gamerforea.eventhelper.imc.client.ISpawnerEntity;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class RenderTileCustomSpawner extends TileEntitySpecialRenderer {
/*     */   public void renderTileEntityAt(TileCustomSpawner tile, double x, double y, double z, float p_147518_8_) {
/*  19 */     GL11.glPushMatrix();
/*  20 */     GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);
/*  21 */     renderTile(tile.getBaseLogic(), x, y, z, p_147518_8_);
/*  22 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void renderTile(CustomSpawnerBaseLogic tile, double x, double y, double z, float partialTick) {
/*  26 */     Tessellator tessellator = Tessellator.field_78398_a;
/*     */     
/*  28 */     Entity entity = tile.getEntityForRenderer();
/*     */     
/*  30 */     if (entity != null) {
/*  31 */       if (tile.powered) {
/*  32 */         partialTick = 0.0F;
/*     */       }
/*  34 */       GL11.glPushMatrix();
/*  35 */       entity.func_70029_a(tile.getSpawnerWorld());
/*  36 */       GL11.glTranslatef(0.0F, 0.4F, 0.0F);
/*     */ 
/*     */ 
/*     */       
/*  40 */       if (!(entity instanceof ISpawnerEntity) || ((ISpawnerEntity)entity).shouldRotateSpawner()) {
/*  41 */         GL11.glRotatef((float)(tile.renderRotation1 + (tile.renderRotation0 - tile.renderRotation1) * partialTick) * 10.0F, 0.0F, 1.0F, 0.0F);
/*     */       }
/*     */       
/*  44 */       GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
/*  45 */       GL11.glTranslatef(0.0F, -0.4F, 0.0F);
/*  46 */       GL11.glScalef(0.4375F, 0.4375F, 0.4375F);
/*  47 */       entity.func_70012_b(x, y, z, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */       
/*  51 */       RenderUtils.renderScaledEntity(entity, partialTick);
/*     */ 
/*     */       
/*  54 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/*  57 */     GL11.glPushMatrix();
/*     */     
/*  59 */     if (tile.spawnSpeed == 2) {
/*  60 */       ResourceHandler.bindResource("textures/items/wyvernCore.png");
/*  61 */     } else if (tile.spawnSpeed == 3) {
/*  62 */       ResourceHandler.bindResource("textures/items/awakenedCore.png");
/*     */     } else {
/*  64 */       ResourceHandler.bindResource("textures/items/draconicCore.png");
/*     */     } 
/*  66 */     tessellator.func_78370_a(255, 255, 255, 255);
/*  67 */     tessellator.func_78380_c(200);
/*     */     
/*  69 */     tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileCustomSpawner/renderTile(Lcom/brandon3055/draconicevolution/common/tileentities/CustomSpawnerBaseLogic;DDDF)V");
/*  70 */     tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
/*  71 */     GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
/*     */     
/*  73 */     tessellator.func_78374_a(0.0D, 0.99D, 0.0D, 0.0D, 0.0D);
/*  74 */     tessellator.func_78374_a(1.0D, 0.99D, 0.0D, 1.0D, 0.0D);
/*  75 */     tessellator.func_78374_a(1.0D, 0.99D, 1.0D, 1.0D, 1.0D);
/*  76 */     tessellator.func_78374_a(0.0D, 0.99D, 1.0D, 0.0D, 1.0D);
/*     */     
/*  78 */     tessellator.func_78374_a(0.0D, 0.99D, 0.0D, 0.0D, 0.0D);
/*  79 */     tessellator.func_78374_a(0.0D, 0.99D, 1.0D, 1.0D, 0.0D);
/*  80 */     tessellator.func_78374_a(1.0D, 0.99D, 1.0D, 1.0D, 1.0D);
/*  81 */     tessellator.func_78374_a(1.0D, 0.99D, 0.0D, 0.0D, 1.0D);
/*     */     
/*  83 */     tessellator.func_78374_a(0.0D, 0.01D, 0.0D, 0.0D, 0.0D);
/*  84 */     tessellator.func_78374_a(1.0D, 0.01D, 0.0D, 1.0D, 0.0D);
/*  85 */     tessellator.func_78374_a(1.0D, 0.01D, 1.0D, 1.0D, 1.0D);
/*  86 */     tessellator.func_78374_a(0.0D, 0.01D, 1.0D, 0.0D, 1.0D);
/*     */     
/*  88 */     tessellator.func_78374_a(0.0D, 0.01D, 0.0D, 0.0D, 0.0D);
/*  89 */     tessellator.func_78374_a(0.0D, 0.01D, 1.0D, 1.0D, 0.0D);
/*  90 */     tessellator.func_78374_a(1.0D, 0.01D, 1.0D, 1.0D, 1.0D);
/*  91 */     tessellator.func_78374_a(1.0D, 0.01D, 0.0D, 0.0D, 1.0D);
/*     */     
/*  93 */     tessellator.func_78381_a();
/*     */     
/*  95 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_147500_a(TileEntity tile, double x, double y, double z, float partialTicks) {
/* 100 */     renderTileEntityAt((TileCustomSpawner)tile, x, y, z, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\tile\RenderTileCustomSpawner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */