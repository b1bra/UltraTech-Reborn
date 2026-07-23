/*     */ package com.brandon3055.brandonscore.client.gui.guicomponents;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class ComponentButton
/*     */   extends ComponentBase
/*     */ {
/*  17 */   private static final ResourceLocation widgets = new ResourceLocation("brandonscore:textures/gui/Widgets.png");
/*  18 */   protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
/*     */   
/*     */   public int buttonId;
/*     */   
/*     */   public GUIBase gui;
/*     */   public int xSize;
/*     */   public int ySize;
/*     */   public int packedFGColour;
/*     */   public String displayString;
/*     */   public String hoverText;
/*     */   
/*     */   public ComponentButton(int x, int y, int xSize, int ySize, int id, GUIBase gui, String displayString) {
/*  30 */     super(x, y);
/*  31 */     this.xSize = xSize;
/*  32 */     this.ySize = ySize;
/*  33 */     this.buttonId = id;
/*  34 */     this.gui = gui;
/*  35 */     this.displayString = displayString;
/*     */   }
/*     */   
/*     */   public ComponentButton(int x, int y, int xSize, int ySize, int id, GUIBase gui, String displayString, String hoverText) {
/*  39 */     this(x, y, xSize, ySize, id, gui, displayString);
/*  40 */     this.hoverText = hoverText;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  45 */     return this.xSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  50 */     return this.ySize;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderBackground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderForground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/*  60 */     GL11.glPushMatrix();
/*  61 */     GL11.glPushAttrib(1048575);
/*     */     
/*  63 */     minecraft.func_110434_K().func_110577_a(buttonTextures);
/*  64 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  65 */     int k = isMouseOver(mouseX, mouseY) ? 2 : 1;
/*  66 */     GL11.glEnable(3042);
/*  67 */     OpenGlHelper.func_148821_a(770, 771, 1, 0);
/*  68 */     GL11.glBlendFunc(770, 771);
/*  69 */     func_73729_b(this.x, this.y, 0, 46 + k * 20, this.xSize / 2, this.ySize);
/*  70 */     func_73729_b(this.x + this.xSize / 2, this.y, 200 - this.xSize / 2, 46 + k * 20, this.xSize / 2, this.ySize);
/*  71 */     if (this.ySize < 20) {
/*     */       
/*  73 */       func_73729_b(this.x, this.y + 3, 0, 46 + k * 20 + 20 - this.ySize + 3, this.xSize / 2, this.ySize - 3);
/*  74 */       func_73729_b(this.x + this.xSize / 2, this.y + 3, 200 - this.xSize / 2, 46 + k * 20 + 20 - this.ySize + 3, this.xSize / 2, this.ySize - 3);
/*     */     } 
/*     */     
/*  77 */     int l = 14737632;
/*     */     
/*  79 */     if (this.packedFGColour != 0) {
/*  80 */       l = this.packedFGColour;
/*  81 */     } else if (!this.enabled) {
/*  82 */       l = 10526880;
/*  83 */     } else if (isMouseOver(mouseX, mouseY)) {
/*  84 */       l = 16777120;
/*     */     } 
/*  86 */     func_73732_a(this.fontRendererObj, this.displayString, this.x + this.xSize / 2, this.y + (this.ySize - 8) / 2, l);
/*  87 */     GL11.glPopAttrib();
/*  88 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderFinal(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/*  93 */     if (isMouseOver(mouseX, mouseY) && !StringUtils.func_151246_b(this.hoverText)) {
/*  94 */       List<String> list = new ArrayList<>();
/*  95 */       list.add(this.hoverText);
/*  96 */       drawHoveringText(list, mouseX + offsetX, mouseY + offsetY + 10, this.fontRendererObj);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int x, int y, int button) {
/* 102 */     Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
/* 103 */     this.gui.buttonClicked(this.buttonId, button);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\client\gui\guicomponents\ComponentButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */