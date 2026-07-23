/*     */ package com.brandon3055.draconicevolution.client.render.item;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*     */ import com.brandon3055.draconicevolution.common.items.weapons.BowHandler;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.client.IItemRenderer;
/*     */ import net.minecraftforge.client.model.AdvancedModelLoader;
/*     */ import net.minecraftforge.client.model.IModelCustom;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class RenderBowModel
/*     */   implements IItemRenderer
/*     */ {
/*     */   private boolean draconic;
/*  17 */   private IModelCustom[] wyvernModels = new IModelCustom[4];
/*  18 */   private IModelCustom[] draconicModels = new IModelCustom[4];
/*     */   private IModelCustom arrow;
/*     */   
/*     */   public RenderBowModel(boolean draconic) {
/*  22 */     this.draconic = draconic;
/*     */     int i;
/*  24 */     for (i = 0; i < 4; i++)
/*  25 */       this.wyvernModels[i] = AdvancedModelLoader.loadModel(ResourceHandler.getResource("models/tools/WyvernBow0" + i + ".obj")); 
/*  26 */     for (i = 0; i < 4; i++)
/*  27 */       this.draconicModels[i] = AdvancedModelLoader.loadModel(ResourceHandler.getResource("models/tools/DraconicBow0" + i + ".obj")); 
/*  28 */     this.arrow = AdvancedModelLoader.loadModel(ResourceHandler.getResource("models/tools/ArrowCommon.obj"));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/*  33 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/*  38 */     return false;
/*     */   }
/*     */   
/*     */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/*     */     IModelCustom activeModel;
/*  43 */     GL11.glPushMatrix();
/*     */ 
/*     */     
/*  46 */     float j = 0.0F;
/*  47 */     int selection = 0;
/*  48 */     BowHandler.BowProperties properties = null;
/*     */     
/*  50 */     if (data.length >= 2 && data[1] instanceof EntityPlayer) {
/*  51 */       EntityPlayer player = (EntityPlayer)data[1];
/*  52 */       j = player.func_71057_bx();
/*  53 */       if (j > 0.0F) {
/*  54 */         properties = new BowHandler.BowProperties(item, player);
/*  55 */         if (j > properties.getDrawTicks()) j = properties.getDrawTicks(); 
/*  56 */         j /= properties.getDrawTicks();
/*  57 */         int j2 = (int)(j * 3.0F);
/*     */         
/*  59 */         if (j2 < 0) { j2 = 0; }
/*  60 */         else if (j2 > 3) { j2 = 3; }
/*     */         
/*  62 */         selection = j2;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     if (this.draconic) {
/*  70 */       activeModel = this.draconicModels[selection];
/*  71 */       ResourceHandler.bindResource("textures/models/tools/DraconicBow0" + selection + ".png");
/*     */     } else {
/*  73 */       activeModel = this.draconicModels[selection];
/*  74 */       ResourceHandler.bindResource("textures/models/tools/WyvernBow0" + selection + ".png");
/*     */     } 
/*     */ 
/*     */     
/*  78 */     if (activeModel != null) doRender(activeModel, type, (j > 0.0F) ? selection : -1, properties);
/*     */     
/*  80 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void doRender(IModelCustom modelCustom, IItemRenderer.ItemRenderType type, int drawState, BowHandler.BowProperties properties) {
/*  85 */     if (type == IItemRenderer.ItemRenderType.EQUIPPED) {
/*  86 */       GL11.glScaled(0.8D, 0.8D, 0.8D);
/*  87 */       GL11.glTranslated(0.7D, 0.0D, 0.2D);
/*  88 */       GL11.glRotatef(87.0F, 0.0F, 0.0F, 1.0F);
/*  89 */       GL11.glRotatef(190.0F, 1.0F, 0.0F, 0.0F);
/*  90 */     } else if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
/*  91 */       GL11.glScaled(0.8D, 0.8D, 0.8D);
/*  92 */       GL11.glTranslated(0.7D, 0.7D, 0.2D);
/*  93 */       GL11.glRotatef(130.0F, 0.0F, 0.0F, 1.0F);
/*  94 */       GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/*  95 */     } else if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/*  96 */       GL11.glScalef(6.0F, 6.0F, 6.0F);
/*  97 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  98 */       GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
/*  99 */       GL11.glTranslated(0.0D, 0.0D, 1.5D);
/* 100 */     } else if (type == IItemRenderer.ItemRenderType.ENTITY) {
/* 101 */       GL11.glScaled(0.8D, 0.8D, 0.8D);
/* 102 */       GL11.glTranslated(0.25D, 0.7D, 0.2D);
/* 103 */       GL11.glRotatef(130.0F, 0.0F, 0.0F, 1.0F);
/* 104 */       GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/*     */     } 
/*     */ 
/*     */     
/* 108 */     modelCustom.renderAll();
/*     */     
/* 110 */     if (drawState != -1) {
/* 111 */       GL11.glTranslated(0.3D, 0.151D, -0.2D + ((drawState == 1) ? 0.0D : ((drawState == 2) ? 0.55D : ((drawState == 3) ? 1.0D : -0.7D))));
/* 112 */       GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/*     */       
/* 114 */       if (properties != null && properties.energyBolt) {
/* 115 */         ResourceHandler.bindResource("textures/models/reactorCore.png");
/* 116 */         this.arrow.renderAll();
/*     */         
/* 118 */         GL11.glTranslated(0.0D, -0.025D, 0.0D);
/* 119 */         GL11.glEnable(3042);
/* 120 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
/* 121 */         GL11.glScaled(1.05D, 1.05D, 1.05D);
/*     */         
/* 123 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
/* 124 */         GL11.glScaled(1.05D, 1.05D, 1.05D);
/* 125 */         this.arrow.renderAll();
/*     */       } else {
/* 127 */         ResourceHandler.bindResource("textures/models/tools/ArrowCommon.png");
/* 128 */         this.arrow.renderAll();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\item\RenderBowModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */