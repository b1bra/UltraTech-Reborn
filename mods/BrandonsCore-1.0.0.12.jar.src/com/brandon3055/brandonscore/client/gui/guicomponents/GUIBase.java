/*     */ package com.brandon3055.brandonscore.client.gui.guicomponents;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.inventory.Container;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GUIBase
/*     */   extends GuiContainer
/*     */ {
/*     */   protected ComponentCollection collection;
/*     */   protected boolean buttonPressed = false;
/*     */   
/*     */   public GUIBase(Container container, int xSize, int ySize) {
/*  19 */     super(container);
/*  20 */     this.field_146999_f = xSize;
/*  21 */     this.field_147000_g = ySize;
/*  22 */     this.field_146297_k = Minecraft.func_71410_x();
/*  23 */     this.collection = assembleComponents();
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract ComponentCollection assembleComponents();
/*     */ 
/*     */   
/*     */   protected void addDependentComponents() {}
/*     */   
/*     */   protected void func_73864_a(int x, int y, int button) {
/*  33 */     super.func_73864_a(x, y, button);
/*  34 */     if (this.collection.isMouseOver(x - this.field_147003_i, y - this.field_147009_r) || this.collection.openBoarders()) {
/*  35 */       this.collection.mouseClicked(x - this.field_147003_i, y - this.field_147009_r, button);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146286_b(int x, int y, int button) {
/*  42 */     super.func_146286_b(x, y, button);
/*  43 */     if (this.collection.isMouseOver(x - this.field_147003_i, y - this.field_147009_r) || this.collection.openBoarders());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146273_a(int x, int y, int button, long time) {
/*  50 */     super.func_146273_a(x, y, button, time);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char par1, int par2) {
/*  56 */     super.func_73869_a(par1, par2);
/*  57 */     this.collection.keyTyped(par1, par2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void preRender(float mouseX, float mouseY) {}
/*     */ 
/*     */   
/*     */   public void postRender(int mouseX, int mouseY) {}
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int mouseX, int mouseY) {
/*  68 */     prepareRenderState();
/*  69 */     preRender(mouseX, mouseY);
/*  70 */     GL11.glPushMatrix();
/*  71 */     GL11.glTranslated(this.field_147003_i, this.field_147009_r, 0.0D);
/*  72 */     this.collection.renderBackground(this.field_146297_k, 0, 0, mouseX - this.field_147003_i, mouseY - this.field_147009_r);
/*  73 */     GL11.glPopMatrix();
/*  74 */     postRender(mouseX, mouseY);
/*  75 */     restoreRenderState();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int mouseX, int mouseY) {
/*  81 */     GL11.glPushMatrix();
/*  82 */     this.collection.renderForground(this.field_146297_k, 0, 0, mouseX - this.field_147003_i, mouseY - this.field_147009_r);
/*  83 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float par3) {
/*  89 */     super.func_73863_a(mouseX, mouseY, par3);
/*  90 */     prepareRenderState();
/*  91 */     GL11.glPushMatrix();
/*  92 */     this.collection.renderFinal(this.field_146297_k, this.field_147003_i, this.field_147009_r, mouseX - this.field_147003_i, mouseY - this.field_147009_r);
/*  93 */     GL11.glPopMatrix();
/*  94 */     restoreRenderState();
/*     */   }
/*     */   
/*     */   protected void prepareRenderState() {
/*  98 */     GL11.glDisable(32826);
/*  99 */     RenderHelper.func_74518_a();
/* 100 */     GL11.glDisable(2896);
/* 101 */     GL11.glDisable(2929);
/*     */   }
/*     */   
/*     */   protected void restoreRenderState() {
/* 105 */     GL11.glEnable(32826);
/* 106 */     GL11.glEnable(2896);
/* 107 */     GL11.glEnable(2929);
/* 108 */     RenderHelper.func_74519_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146280_a(Minecraft mc, int width, int height) {
/* 113 */     super.func_146280_a(mc, width, height);
/* 114 */     this.collection.setWorldAndResolution(mc, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73876_c() {
/* 120 */     super.func_73876_c();
/* 121 */     this.collection.removeScheduled();
/* 122 */     this.collection.updateScreen();
/* 123 */     this.buttonPressed = false;
/*     */   }
/*     */   
/*     */   public void buttonClicked(int id, int button) {
/* 127 */     this.buttonPressed = true;
/*     */   }
/*     */   
/*     */   public int getXSize() {
/* 131 */     return this.field_146999_f;
/*     */   }
/*     */   
/*     */   public int getYSize() {
/* 135 */     return this.field_147000_g;
/*     */   }
/*     */   
/*     */   public void componentCallBack(ComponentBase component) {}
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\client\gui\guicomponents\GUIBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */