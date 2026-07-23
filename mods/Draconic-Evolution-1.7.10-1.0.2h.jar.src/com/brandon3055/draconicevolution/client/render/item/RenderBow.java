/*     */ package com.brandon3055.draconicevolution.client.render.item;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.ItemRenderer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.client.ForgeHooksClient;
/*     */ import net.minecraftforge.client.IItemRenderer;
/*     */ import net.minecraftforge.client.MinecraftForgeClient;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderBow
/*     */   implements IItemRenderer
/*     */ {
/*  29 */   private Minecraft mc = Minecraft.func_71410_x();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/*  37 */     return (type == IItemRenderer.ItemRenderType.EQUIPPED);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/*  47 */     EntityLivingBase entity = (EntityLivingBase)data[1];
/*     */     
/*  49 */     GL11.glPushMatrix();
/*  50 */     if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
/*  51 */       renderItem(entity, item, 0);
/*     */     } else {
/*  53 */       GL11.glPushMatrix();
/*     */ 
/*     */       
/*  56 */       float f2 = 2.2307692F;
/*  57 */       GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
/*  58 */       GL11.glRotatef(110.0F, 1.0F, 0.0F, 0.0F);
/*  59 */       GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
/*  60 */       GL11.glScalef(f2, f2, f2);
/*  61 */       GL11.glTranslatef(0.15F, -0.1875F, 0.1875F);
/*     */ 
/*     */ 
/*     */       
/*  65 */       float f3 = 0.625F;
/*  66 */       GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
/*  67 */       GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
/*  68 */       GL11.glScalef(f3, -f3, f3);
/*  69 */       GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
/*  70 */       GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/*     */       
/*  72 */       renderItem(entity, item, 0);
/*  73 */       GL11.glPopMatrix();
/*     */     } 
/*  75 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderItem(EntityLivingBase par1EntityLivingBase, ItemStack par2ItemStack, int par3) {
/*  80 */     RenderBlocks renderBlocksIr = new RenderBlocks();
/*     */     
/*  82 */     IItemRenderer.ItemRenderType type = IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
/*     */     
/*  84 */     GL11.glPushMatrix();
/*  85 */     TextureManager texturemanager = this.mc.func_110434_K();
/*  86 */     Item item = par2ItemStack.func_77973_b();
/*  87 */     Block block = Block.func_149634_a(item);
/*     */     
/*  89 */     IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(par2ItemStack, type);
/*  90 */     if (customRenderer != null) {
/*  91 */       texturemanager.func_110577_a(texturemanager.func_130087_a(par2ItemStack.func_94608_d()));
/*  92 */       ForgeHooksClient.renderEquippedItem(type, customRenderer, renderBlocksIr, par1EntityLivingBase, par2ItemStack);
/*  93 */     } else if (par2ItemStack.func_94608_d() == 0 && item instanceof net.minecraft.item.ItemBlock && RenderBlocks.func_147739_a(block.func_149645_b())) {
/*  94 */       texturemanager.func_110577_a(texturemanager.func_130087_a(0));
/*     */       
/*  96 */       if (par2ItemStack != null && par2ItemStack.func_77973_b() instanceof net.minecraft.item.ItemCloth) {
/*  97 */         GL11.glEnable(3042);
/*  98 */         GL11.glDepthMask(false);
/*  99 */         OpenGlHelper.func_148821_a(770, 771, 1, 0);
/* 100 */         renderBlocksIr.func_147800_a(block, par2ItemStack.func_77960_j(), 1.0F);
/* 101 */         GL11.glDepthMask(true);
/* 102 */         GL11.glDisable(3042);
/*     */       } else {
/* 104 */         renderBlocksIr.func_147800_a(block, par2ItemStack.func_77960_j(), 1.0F);
/*     */       } 
/*     */     } else {
/* 107 */       IIcon iicon = par1EntityLivingBase.func_70620_b(par2ItemStack, par3);
/*     */       
/* 109 */       if (iicon == null) {
/* 110 */         GL11.glPopMatrix();
/*     */         
/*     */         return;
/*     */       } 
/* 114 */       texturemanager.func_110577_a(texturemanager.func_130087_a(par2ItemStack.func_94608_d()));
/*     */       
/* 116 */       Tessellator tessellator = Tessellator.field_78398_a;
/* 117 */       float f = iicon.func_94209_e();
/* 118 */       float f1 = iicon.func_94212_f();
/* 119 */       float f2 = iicon.func_94206_g();
/* 120 */       float f3 = iicon.func_94210_h();
/* 121 */       float f4 = 0.0F;
/* 122 */       float f5 = 0.3F;
/* 123 */       GL11.glEnable(32826);
/* 124 */       GL11.glTranslatef(-f4, -f5, 0.0F);
/* 125 */       float f6 = 1.5F;
/* 126 */       GL11.glScalef(f6, f6, f6);
/* 127 */       GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
/* 128 */       GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
/* 129 */       GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
/* 130 */       ItemRenderer.func_78439_a(tessellator, f1, f2, f, f3, iicon.func_94211_a(), iicon.func_94216_b(), 0.0625F);
/*     */       
/* 132 */       if (par2ItemStack.hasEffect(par3)) {
/* 133 */         ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/* 134 */         GL11.glDepthFunc(514);
/* 135 */         GL11.glDisable(2896);
/* 136 */         texturemanager.func_110577_a(RES_ITEM_GLINT);
/* 137 */         GL11.glEnable(3042);
/* 138 */         OpenGlHelper.func_148821_a(768, 1, 1, 0);
/* 139 */         float f7 = 0.76F;
/* 140 */         GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
/* 141 */         GL11.glMatrixMode(5890);
/* 142 */         GL11.glPushMatrix();
/* 143 */         float f8 = 0.125F;
/* 144 */         GL11.glScalef(f8, f8, f8);
/* 145 */         float f9 = (float)(Minecraft.func_71386_F() % 3000L) / 3000.0F * 8.0F;
/* 146 */         GL11.glTranslatef(f9, 0.0F, 0.0F);
/* 147 */         GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
/* 148 */         ItemRenderer.func_78439_a(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
/* 149 */         GL11.glPopMatrix();
/* 150 */         GL11.glPushMatrix();
/* 151 */         GL11.glScalef(f8, f8, f8);
/* 152 */         f9 = (float)(Minecraft.func_71386_F() % 4873L) / 4873.0F * 8.0F;
/* 153 */         GL11.glTranslatef(-f9, 0.0F, 0.0F);
/* 154 */         GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
/* 155 */         ItemRenderer.func_78439_a(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
/* 156 */         GL11.glPopMatrix();
/* 157 */         GL11.glMatrixMode(5888);
/* 158 */         GL11.glDisable(3042);
/* 159 */         GL11.glEnable(2896);
/* 160 */         GL11.glDepthFunc(515);
/*     */       } 
/*     */       
/* 163 */       GL11.glDisable(32826);
/* 164 */       texturemanager.func_110577_a(texturemanager.func_130087_a(par2ItemStack.func_94608_d()));
/* 165 */       TextureUtil.func_147945_b();
/*     */     } 
/*     */     
/* 168 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\item\RenderBow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */