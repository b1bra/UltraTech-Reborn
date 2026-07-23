/*     */ package com.brandon3055.draconicevolution.client.render.tile;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.TileEnergyStorageCore;
/*     */ import cpw.mods.fml.client.FMLClientHandler;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.client.model.AdvancedModelLoader;
/*     */ import net.minecraftforge.client.model.IModelCustom;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderTileEnergyStorageCore
/*     */   extends TileEntitySpecialRenderer
/*     */ {
/*  20 */   private static final ResourceLocation iner_model_texture = new ResourceLocation("draconicevolution", "textures/models/power_sphere_layer_1.png");
/*  21 */   private static final ResourceLocation outer_model_texture = new ResourceLocation("draconicevolution", "textures/models/power_sphere_layer_2.png");
/*     */ 
/*     */ 
/*     */   
/*  25 */   private IModelCustom iner_model = AdvancedModelLoader.loadModel(new ResourceLocation("draconicevolution", "models/power_sphere_layer_1.obj"));
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_147500_a(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {
/*  30 */     if (!(tile instanceof TileEnergyStorageCore))
/*  31 */       return;  TileEnergyStorageCore core = (TileEnergyStorageCore)tile;
/*  32 */     if (!core.isOnline())
/*  33 */       return;  float scale = 0.0F;
/*  34 */     float rotation = core.modelRotation + timeSinceLastTick / 2.0F;
/*     */     
/*  36 */     switch (core.getTier()) {
/*     */       case 0:
/*  38 */         scale = 0.7F;
/*     */         break;
/*     */       case 1:
/*  41 */         scale = 1.2F;
/*     */         break;
/*     */       case 2:
/*  44 */         scale = 1.7F;
/*     */         break;
/*     */       case 3:
/*  47 */         scale = 2.5F;
/*     */         break;
/*     */       case 4:
/*  50 */         scale = 3.5F;
/*     */         break;
/*     */       case 5:
/*  53 */         scale = 4.5F;
/*     */         break;
/*     */       case 6:
/*  56 */         scale = 5.5F;
/*     */         break;
/*     */     } 
/*     */     
/*  60 */     GL11.glPushMatrix();
/*  61 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  62 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 150.0F, 150.0F);
/*  63 */     GL11.glDisable(2896);
/*  64 */     GL11.glDisable(2884);
/*  65 */     GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
/*  66 */     FMLClientHandler.instance().getClient().func_110434_K().func_110577_a(iner_model_texture);
/*     */     
/*  68 */     double colour = ((TileEnergyStorageCore)tile).getEnergyStored() / ((TileEnergyStorageCore)tile).getMaxEnergyStored();
/*  69 */     float brightness = (float)Math.abs(Math.sin(((float)Minecraft.func_71386_F() / 3000.0F)) * 100.0D);
/*     */     
/*  71 */     colour = 1.0D - colour;
/*  72 */     GL11.glScalef(scale, scale, scale);
/*  73 */     GL11.glPushMatrix();
/*  74 */     GL11.glRotatef(rotation, 0.0F, 1.0F, 0.5F);
/*  75 */     GL11.glColor4d(1.0D, colour * 0.30000001192092896D, colour * 0.699999988079071D, 1.0D);
/*  76 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 80.0F + brightness, 80.0F + brightness);
/*  77 */     this.iner_model.renderAll();
/*  78 */     GL11.glPopMatrix();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     GL11.glScalef(1.1F, 1.1F, 1.1F);
/*  95 */     GL11.glDepthMask(false);
/*  96 */     FMLClientHandler.instance().getClient().func_110434_K().func_110577_a(outer_model_texture);
/*  97 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 200.0F, 200.0F);
/*  98 */     GL11.glEnable(3042);
/*  99 */     GL11.glBlendFunc(770, 771);
/* 100 */     GL11.glRotatef(rotation * 0.5F, 0.0F, -1.0F, -0.5F);
/* 101 */     GL11.glColor4f(0.5F, 2.0F, 2.0F, 0.7F);
/* 102 */     this.iner_model.renderAll();
/* 103 */     GL11.glDisable(3042);
/* 104 */     GL11.glDepthMask(true);
/* 105 */     GL11.glEnable(2896);
/*     */     
/* 107 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\tile\RenderTileEnergyStorageCore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */