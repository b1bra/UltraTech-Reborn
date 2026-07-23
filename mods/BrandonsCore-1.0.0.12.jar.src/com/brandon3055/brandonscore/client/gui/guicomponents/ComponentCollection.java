/*     */ package com.brandon3055.brandonscore.client.gui.guicomponents;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComponentCollection
/*     */   extends ComponentBase
/*     */ {
/*  13 */   protected List<ComponentBase> components = new ArrayList<>();
/*     */   protected int xSize;
/*     */   protected int ySize;
/*  16 */   protected List<String> deadGroups = new ArrayList<>();
/*     */   
/*     */   protected GUIBase gui;
/*     */   private boolean openBoarders = false;
/*     */   
/*     */   public ComponentCollection(int x, int y, int xSize, int ySize, GUIBase gui) {
/*  22 */     super(x, y);
/*  23 */     this.xSize = xSize;
/*  24 */     this.ySize = ySize;
/*  25 */     this.gui = gui;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  30 */     return this.xSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  35 */     return this.ySize;
/*     */   }
/*     */ 
/*     */   
/*     */   public ComponentBase addComponent(ComponentBase component) {
/*  40 */     this.components.add(component);
/*  41 */     component.setWorldAndResolution(Minecraft.func_71410_x(), this.width, this.height);
/*  42 */     return component;
/*     */   }
/*     */   
/*     */   private boolean isComponentEnabled(ComponentBase component) {
/*  46 */     return (component != null && component.isEnabled());
/*     */   }
/*     */   
/*     */   private boolean isComponentCapturingMouse(ComponentBase component, int mouseX, int mouseY) {
/*  50 */     return (isComponentEnabled(component) && component.isMouseOver(mouseX, mouseY));
/*     */   }
/*     */ 
/*     */   
/*     */   public final void renderBackground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/*  55 */     for (ComponentBase component : this.components) {
/*  56 */       if (isComponentEnabled(component)) {
/*  57 */         component.renderBackground(minecraft, offsetX + this.x, offsetY + this.y, mouseX - this.x, mouseY - this.y);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void renderForground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/*  63 */     for (ComponentBase component : this.components) {
/*  64 */       if (isComponentEnabled(component)) {
/*  65 */         component.renderForground(minecraft, offsetX + this.x, offsetY + this.y, mouseX - this.x, mouseY - this.y);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void renderFinal(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/*  71 */     for (ComponentBase component : this.components) {
/*  72 */       if (isComponentEnabled(component)) {
/*  73 */         component.renderFinal(minecraft, offsetX + this.x, offsetY + this.y, mouseX - this.x, mouseY - this.y);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseClicked(int x, int y, int button) {
/*  79 */     for (ComponentBase component : this.components) {
/*  80 */       if (!this.gui.buttonPressed && component.isEnabled() && component.isMouseOver(x, y)) {
/*  81 */         component.mouseClicked(x, y, button);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyTyped(char par1, int par2) {
/*  89 */     for (ComponentBase component : this.components) {
/*  90 */       if (component.isEnabled()) {
/*  91 */         component.keyTyped(par1, par2);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<ComponentBase> getComponents() {
/*  97 */     return this.components;
/*     */   }
/*     */   
/*     */   public ComponentBase getComponent(String name) {
/* 101 */     for (ComponentBase component : this.components) {
/* 102 */       if (component.name != null && component.name.equals(name)) return component; 
/*     */     } 
/* 104 */     return null;
/*     */   }
/*     */   
/*     */   public void setGroupEnabled(String group, boolean enabled) {
/* 108 */     for (ComponentBase component : this.components) {
/* 109 */       if (component.getGroup().equals(group)) {
/* 110 */         component.setEnabled(enabled);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOnlyGroupEnabled(String group) {
/* 117 */     for (ComponentBase component : this.components) {
/* 118 */       if (component.getGroup().equals(group)) {
/* 119 */         component.setEnabled(this.enabled); continue;
/*     */       } 
/* 121 */       component.setEnabled(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setComponentEnabled(String name, boolean enabled) {
/* 128 */     for (ComponentBase component : this.components) {
/* 129 */       if (component.name != null && component.name.equals(name)) {
/* 130 */         component.setEnabled(enabled);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeGroup(String group) {
/* 136 */     List<ComponentBase> list = new ArrayList<>();
/* 137 */     for (ComponentBase component : this.components) {
/* 138 */       if (component.getGroup().equals(group)) list.add(component); 
/*     */     } 
/* 140 */     this.components.removeAll(list);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorldAndResolution(Minecraft mc, int width, int height) {
/* 145 */     super.setWorldAndResolution(mc, width, height);
/* 146 */     for (ComponentBase component : this.components) {
/* 147 */       component.setWorldAndResolution(mc, width, height);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeScheduled() {
/* 152 */     if (!this.deadGroups.isEmpty()) {
/* 153 */       for (String s : this.deadGroups) removeGroup(s); 
/* 154 */       this.deadGroups.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void schedulRemoval(String group) {
/* 159 */     this.deadGroups.add(group);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 164 */     for (ComponentBase component : this.components) {
/* 165 */       component.updateScreen();
/*     */     }
/*     */   }
/*     */   
/*     */   public ComponentCollection setOpenBoarders() {
/* 170 */     this.openBoarders = true;
/* 171 */     return this;
/*     */   }
/*     */   
/*     */   public boolean openBoarders() {
/* 175 */     return this.openBoarders;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\client\gui\guicomponents\ComponentCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */