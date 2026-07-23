/*     */ package com.brandon3055.brandonscore.client.gui.guicomponents;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ public abstract class ComponentBase
/*     */   extends Gui
/*     */ {
/*  17 */   protected static RenderItem itemRender = new RenderItem();
/*     */   protected int x;
/*     */   protected int y;
/*     */   protected boolean enabled = true;
/*     */   public static Minecraft mc;
/*     */   public FontRenderer fontRendererObj;
/*  23 */   private String group = "";
/*     */ 
/*     */   
/*     */   public int width;
/*     */ 
/*     */   
/*     */   public int height;
/*     */   
/*     */   public String name;
/*     */ 
/*     */   
/*     */   public ComponentBase(int x, int y) {
/*  35 */     this.x = x;
/*  36 */     this.y = y;
/*  37 */     mc = Minecraft.func_71410_x();
/*  38 */     this.fontRendererObj = mc.field_71466_p;
/*     */   }
/*     */   
/*     */   public String getGroup() {
/*  42 */     return this.group;
/*     */   }
/*     */   
/*     */   public ComponentBase setGroup(String group) {
/*  46 */     this.group = group;
/*  47 */     return this;
/*     */   }
/*     */   
/*     */   public ComponentBase setName(String name) {
/*  51 */     this.name = name;
/*  52 */     return this;
/*     */   }
/*     */   
/*     */   public void setX(int x) {
/*  56 */     this.x = x;
/*     */   }
/*     */   
/*     */   public void setY(int y) {
/*  60 */     this.y = y;
/*     */   }
/*     */   
/*     */   public int getX() {
/*  64 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/*  68 */     return this.y;
/*     */   }
/*     */   
/*     */   public abstract int getWidth();
/*     */   
/*     */   public abstract int getHeight();
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/*  76 */     this.enabled = enabled;
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/*  80 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public boolean isMouseOver(int mouseX, int mouseY) {
/*  84 */     return (mouseX >= this.x && mouseX < this.x + getWidth() && mouseY >= this.y && mouseY < this.y + getHeight());
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void renderBackground(Minecraft paramMinecraft, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   public abstract void renderForground(Minecraft paramMinecraft, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   public void renderFinal(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {}
/*     */   
/*     */   protected void drawHoveringText(List<String> list, int x, int y, FontRenderer font) {
/*  95 */     if (!list.isEmpty()) {
/*  96 */       GL11.glDisable(32826);
/*     */       
/*  98 */       GL11.glDisable(2896);
/*  99 */       GL11.glDisable(2929);
/* 100 */       int k = 0;
/*     */       
/* 102 */       for (String s : list) {
/* 103 */         int l = font.func_78256_a(s);
/* 104 */         if (l > k) {
/* 105 */           k = l;
/*     */         }
/*     */       } 
/*     */       
/* 109 */       int adjX = x + 12;
/* 110 */       int adjY = y - 12;
/* 111 */       int i1 = 8;
/*     */       
/* 113 */       if (list.size() > 1) {
/* 114 */         i1 += 2 + (list.size() - 1) * 10;
/*     */       }
/*     */       
/* 117 */       if (adjX + k > this.width) {
/* 118 */         adjX -= 28 + k;
/*     */       }
/*     */       
/* 121 */       if (adjY + i1 + 6 > this.height) {
/* 122 */         adjY = this.height - i1 - 6;
/*     */       }
/*     */       
/* 125 */       this.field_73735_i = 300.0F;
/* 126 */       itemRender.field_77023_b = 300.0F;
/* 127 */       int j1 = -267386864;
/* 128 */       func_73733_a(adjX - 3, adjY - 4, adjX + k + 3, adjY - 3, j1, j1);
/* 129 */       func_73733_a(adjX - 3, adjY + i1 + 3, adjX + k + 3, adjY + i1 + 4, j1, j1);
/* 130 */       func_73733_a(adjX - 3, adjY - 3, adjX + k + 3, adjY + i1 + 3, j1, j1);
/* 131 */       func_73733_a(adjX - 4, adjY - 3, adjX - 3, adjY + i1 + 3, j1, j1);
/* 132 */       func_73733_a(adjX + k + 3, adjY - 3, adjX + k + 4, adjY + i1 + 3, j1, j1);
/* 133 */       int k1 = 1347420415;
/* 134 */       int l1 = (k1 & 0xFEFEFE) >> 1 | k1 & 0xFF000000;
/* 135 */       func_73733_a(adjX - 3, adjY - 3 + 1, adjX - 3 + 1, adjY + i1 + 3 - 1, k1, l1);
/* 136 */       func_73733_a(adjX + k + 2, adjY - 3 + 1, adjX + k + 3, adjY + i1 + 3 - 1, k1, l1);
/* 137 */       func_73733_a(adjX - 3, adjY - 3, adjX + k + 3, adjY - 3 + 1, k1, k1);
/* 138 */       func_73733_a(adjX - 3, adjY + i1 + 2, adjX + k + 3, adjY + i1 + 3, l1, l1);
/*     */       
/* 140 */       for (int i2 = 0; i2 < list.size(); i2++) {
/* 141 */         String s1 = list.get(i2);
/* 142 */         font.func_78261_a(s1, adjX, adjY, -1);
/*     */         
/* 144 */         if (i2 == 0) {
/* 145 */           adjY += 2;
/*     */         }
/*     */         
/* 148 */         adjY += 10;
/*     */       } 
/*     */       
/* 151 */       this.field_73735_i = 0.0F;
/* 152 */       itemRender.field_77023_b = 0.0F;
/* 153 */       GL11.glEnable(2896);
/* 154 */       GL11.glEnable(2929);
/*     */       
/* 156 */       GL11.glEnable(32826);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawItemStack(ItemStack stack, int x, int y, String count) {
/* 161 */     if (stack == null)
/* 162 */       return;  GL11.glPushMatrix();
/* 163 */     GL11.glPushAttrib(1048575);
/* 164 */     GL11.glDisable(2896);
/* 165 */     GL11.glEnable(2929);
/*     */ 
/*     */     
/* 168 */     GL11.glTranslatef(0.0F, 0.0F, 32.0F);
/* 169 */     this.field_73735_i = 200.0F;
/* 170 */     itemRender.field_77023_b = 200.0F;
/*     */     
/* 172 */     FontRenderer font = stack.func_77973_b().getFontRenderer(stack);
/* 173 */     if (font == null) font = this.fontRendererObj; 
/* 174 */     itemRender.func_82406_b(font, mc.func_110434_K(), stack, x, y);
/*     */     
/* 176 */     if (!count.equals("null"))
/* 177 */       itemRender.func_94148_a(font, mc.func_110434_K(), stack, x, y, count); 
/* 178 */     this.field_73735_i = 0.0F;
/* 179 */     itemRender.field_77023_b = 0.0F;
/*     */ 
/*     */ 
/*     */     
/* 183 */     GL11.glPopAttrib();
/* 184 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   protected void renderToolTip(ItemStack stack, int x, int y) {
/* 188 */     List<String> list = stack.func_82840_a((EntityPlayer)mc.field_71439_g, mc.field_71474_y.field_82882_x);
/*     */     
/* 190 */     for (int k = 0; k < list.size(); k++) {
/* 191 */       if (k == 0) {
/* 192 */         list.set(k, (stack.func_77953_t()).field_77937_e + (String)list.get(k));
/*     */       } else {
/* 194 */         list.set(k, EnumChatFormatting.GRAY + (String)list.get(k));
/*     */       } 
/*     */     } 
/*     */     
/* 198 */     FontRenderer font = stack.func_77973_b().getFontRenderer(stack);
/* 199 */     drawHoveringText(list, x, y, (font == null) ? this.fontRendererObj : font);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(int x, int y, int button) {}
/*     */ 
/*     */   
/*     */   public void setWorldAndResolution(Minecraft mc, int width, int height) {
/* 208 */     ComponentBase.mc = mc;
/* 209 */     this.fontRendererObj = mc.field_71466_p;
/* 210 */     this.width = width;
/* 211 */     this.height = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String ttl(String unlocalizedName) {
/* 218 */     return StatCollector.func_74838_a(unlocalizedName);
/*     */   }
/*     */   
/*     */   public void updateScreen() {}
/*     */   
/*     */   protected void keyTyped(char par1, int par2) {}
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\client\gui\guicomponents\ComponentBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */