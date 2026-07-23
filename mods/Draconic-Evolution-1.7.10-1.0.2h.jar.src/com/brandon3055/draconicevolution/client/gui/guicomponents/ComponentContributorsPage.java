/*     */ package com.brandon3055.draconicevolution.client.gui.guicomponents;
/*     */ 
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentScrollingBase;
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.GUIScrollingBase;
/*     */ import com.brandon3055.brandonscore.client.utills.ClientUtills;
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiYesNoCallback;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ public class ComponentContributorsPage
/*     */   extends ComponentScrollingBase
/*     */   implements GuiYesNoCallback {
/*  15 */   public int scrollOffset = 0;
/*     */   
/*     */   public int pageLength;
/*     */   
/*     */   public ComponentContributorsPage(int x, int y, GUIScrollingBase gui) {
/*  20 */     super(x, y, gui);
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
/* 155 */     this.lastClickURL = "";
/*     */     this.pageLength = 55;
/*     */     this.scrollLimit = this.pageLength - 325;
/*     */   }
/*     */ 
/*     */   
/*     */   public int scrollLimit;
/*     */   private String lastClickURL;
/*     */   
/*     */   public void handleScrollInput(int direction) {
/*     */     this.scrollOffset += direction * (InfoHelper.isShiftKeyDown() ? 30 : 10);
/*     */     if (this.scrollOffset < 0) {
/*     */       this.scrollOffset = 0;
/*     */     }
/*     */     if (this.scrollOffset > this.pageLength - getHeight()) {
/*     */       this.scrollOffset = this.pageLength - getHeight();
/*     */     }
/*     */     if (this.pageLength <= getHeight()) {
/*     */       this.scrollOffset = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public int getWidth() {
/*     */     return this.gui.getXSize();
/*     */   }
/*     */   
/*     */   public int getHeight() {
/*     */     return this.gui.getYSize();
/*     */   }
/*     */   
/*     */   public void func_73878_a(boolean confirmed, int button) {
/* 186 */     mc.func_147108_a((GuiScreen)this.gui);
/* 187 */     if (confirmed)
/* 188 */       ClientUtills.openLink(this.lastClickURL); 
/*     */   }
/*     */   
/*     */   public void renderBackground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/*     */     int yOffset = -this.scrollOffset;
/*     */     yOffset += 20;
/*     */     if (yOffset > 0)
/*     */       func_73732_a(this.fontRendererObj, StatCollector.func_74838_a("info.de.manual.contributors.txt"), getWidth() / 2, yOffset, 65535); 
/*     */   }
/*     */   
/*     */   public void renderFinal(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/*     */     this.fontRendererObj.func_78276_b("[Error] Failed to download contributors list", offsetX + 20, offsetY + 40, 16711680);
/*     */   }
/*     */   
/*     */   public void renderForground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {}
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\guicomponents\ComponentContributorsPage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */