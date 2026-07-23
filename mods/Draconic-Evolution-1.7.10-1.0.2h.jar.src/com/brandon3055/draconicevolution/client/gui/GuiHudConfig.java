/*     */ package com.brandon3055.draconicevolution.client.gui;
/*     */ 
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.GUIBase;
/*     */ import com.brandon3055.brandonscore.client.utills.GuiHelper;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiHudConfig
/*     */   extends GuiScreen
/*     */ {
/*     */   private GUIBase parent;
/*     */   private GuiButton buttonHudFade;
/*     */   private GuiButton buttonArmorFade;
/*     */   private GuiButton buttonArmorNumeric;
/*     */   private boolean draggingArmor = false;
/*     */   private boolean draggingHud = false;
/*  26 */   private int dragOffsetX = 0;
/*  27 */   private int dragOffsetY = 0;
/*     */   
/*     */   public GuiHudConfig(GUIBase parent) {
/*  30 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  36 */     super.func_73866_w_();
/*  37 */     this.field_146292_n.clear();
/*     */     
/*  39 */     int x = this.field_146294_l / 2;
/*  40 */     int y = this.field_146295_m / 2 - 30;
/*     */     
/*  42 */     this.field_146292_n.add(new GuiButtonAHeight(0, x - 35, y + 95, 70, 15, StatCollector.func_74838_a("gui.back")));
/*     */     
/*  44 */     this.field_146292_n.add(new GuiButtonAHeight(1, x + 2, y - 37, 80, 15, StatCollector.func_74838_a("button.de.scaleUp.txt")));
/*  45 */     this.field_146292_n.add(new GuiButtonAHeight(2, x - 81, y - 37, 80, 15, StatCollector.func_74838_a("button.de.scaleDown.txt")));
/*  46 */     this.field_146292_n.add(this.buttonHudFade = new GuiButtonAHeight(3, x - 81, y - 20, 163, 15, ""));
/*  47 */     this.field_146292_n.add(new GuiButtonAHeight(9, x - 81, y - 3, 163, 15, StatCollector.func_74838_a("button.de.toggleHidden.txt")));
/*     */     
/*  49 */     this.field_146292_n.add(new GuiButtonAHeight(4, x + 2, y + 25, 80, 15, StatCollector.func_74838_a("button.de.scaleUp.txt")));
/*  50 */     this.field_146292_n.add(new GuiButtonAHeight(5, x - 81, y + 25, 80, 15, StatCollector.func_74838_a("button.de.scaleDown.txt")));
/*  51 */     this.field_146292_n.add(this.buttonArmorFade = new GuiButtonAHeight(6, x - 81, y + 42, 163, 15, ""));
/*  52 */     this.field_146292_n.add(this.buttonArmorNumeric = new GuiButtonAHeight(7, x + 2, y + 59, 80, 15, ""));
/*  53 */     this.field_146292_n.add(new GuiButtonAHeight(8, x - 81, y + 59, 80, 15, StatCollector.func_74838_a("button.de.rotate.txt")));
/*  54 */     this.field_146292_n.add(new GuiButtonAHeight(10, x - 81, y + 76, 163, 15, StatCollector.func_74838_a("button.de.toggleHidden.txt")));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73876_c() {
/*  59 */     this.buttonHudFade.field_146126_j = StatCollector.func_74838_a("button.de.fadeMode" + ConfigHandler.hudSettings[6] + ".txt");
/*  60 */     this.buttonArmorFade.field_146126_j = StatCollector.func_74838_a("button.de.fadeMode" + ConfigHandler.hudSettings[7] + ".txt");
/*  61 */     this.buttonArmorNumeric.field_146126_j = StatCollector.func_74838_a("button.de.numeric.txt") + " " + ((ConfigHandler.hudSettings[9] == 0) ? StatCollector.func_74838_a("gui.de.off.txt") : StatCollector.func_74838_a("gui.de.on.txt"));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton button) {
/*  66 */     if (button.field_146127_k == 0) {
/*  67 */       Minecraft.func_71410_x().func_147108_a((GuiScreen)this.parent); return;
/*     */     } 
/*  69 */     if (button.field_146127_k == 1 && ConfigHandler.hudSettings[4] < 300) {
/*  70 */       ConfigHandler.hudSettings[4] = ConfigHandler.hudSettings[4] + 10;
/*  71 */     } else if (button.field_146127_k == 2 && ConfigHandler.hudSettings[4] > 30) {
/*  72 */       ConfigHandler.hudSettings[4] = ConfigHandler.hudSettings[4] - 10;
/*  73 */     } else if (button.field_146127_k == 3) {
/*  74 */       if (ConfigHandler.hudSettings[6] < 4) { ConfigHandler.hudSettings[6] = ConfigHandler.hudSettings[6] + 1; }
/*  75 */       else { ConfigHandler.hudSettings[6] = 0; } 
/*  76 */     } else if (button.field_146127_k == 4 && ConfigHandler.hudSettings[5] < 300) {
/*  77 */       ConfigHandler.hudSettings[5] = ConfigHandler.hudSettings[5] + 10;
/*  78 */     } else if (button.field_146127_k == 5 && ConfigHandler.hudSettings[5] > 30) {
/*  79 */       ConfigHandler.hudSettings[5] = ConfigHandler.hudSettings[5] - 10;
/*  80 */     } else if (button.field_146127_k == 6) {
/*  81 */       if (ConfigHandler.hudSettings[7] < 4) { ConfigHandler.hudSettings[7] = ConfigHandler.hudSettings[7] + 1; }
/*  82 */       else { ConfigHandler.hudSettings[7] = 0; } 
/*  83 */     } else if (button.field_146127_k == 7) {
/*  84 */       if (ConfigHandler.hudSettings[9] == 0) { ConfigHandler.hudSettings[9] = 1; }
/*  85 */       else { ConfigHandler.hudSettings[9] = 0; } 
/*  86 */     } else if (button.field_146127_k == 8) {
/*  87 */       if (ConfigHandler.hudSettings[8] == 0) { ConfigHandler.hudSettings[8] = 1; }
/*  88 */       else { ConfigHandler.hudSettings[8] = 0; } 
/*  89 */     } else if (button.field_146127_k == 9) {
/*  90 */       if (ConfigHandler.hudSettings[10] == 0) { ConfigHandler.hudSettings[10] = 1; }
/*  91 */       else { ConfigHandler.hudSettings[10] = 0; } 
/*  92 */     } else if (button.field_146127_k == 10) {
/*  93 */       if (ConfigHandler.hudSettings[11] == 0) { ConfigHandler.hudSettings[11] = 1; }
/*  94 */       else { ConfigHandler.hudSettings[11] = 0; }
/*     */     
/*     */     } 
/*  97 */     ConfigHandler.config.get("Gui Stuff", "HUD Settings", new int[] { 7, 874, 100, 100, 100, 100, 0, 0, 0, 0, 1, 1 }, "Used to store the position of the armor ant tool HUD's. This should not be modified", -2147483648, 2147483647, true, 12).set(ConfigHandler.hudSettings);
/*  98 */     ConfigHandler.config.save();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73863_a(int x, int y, float partial) {
/* 103 */     int[] pos = ConfigHandler.hudSettings;
/* 104 */     int hudX = (int)(pos[0] / 1000.0D * this.field_146294_l);
/* 105 */     int hudY = (int)(pos[1] / 1000.0D * this.field_146295_m);
/* 106 */     int armorX = (int)(pos[2] / 1000.0D * this.field_146294_l);
/* 107 */     int armorY = (int)(pos[3] / 1000.0D * this.field_146295_m);
/*     */     
/* 109 */     func_73732_a(this.field_146289_q, StatCollector.func_74838_a("gui.de.configureGuiElements.txt"), this.field_146294_l / 2, this.field_146295_m / 2 - 90, 65535);
/* 110 */     func_73732_a(this.field_146289_q, StatCollector.func_74838_a("gui.de.hudDisplaySettings.txt"), this.field_146294_l / 2, this.field_146295_m / 2 - 77, 16777215);
/* 111 */     func_73732_a(this.field_146289_q, StatCollector.func_74838_a("gui.de.shieldDisplaySettings.txt"), this.field_146294_l / 2, this.field_146295_m / 2 - 15, 16777215);
/* 112 */     func_73732_a(this.field_146289_q, StatCollector.func_74838_a("gui.de.clickAndDragPurpleBoxes.txt"), this.field_146294_l / 2, this.field_146295_m / 2 + 85, 16777215);
/*     */     
/* 114 */     func_73732_a(this.field_146289_q, ConfigHandler.hudSettings[4] + "%", this.field_146294_l / 2 + 97, this.field_146295_m / 2 - 63, 16777215);
/* 115 */     func_73732_a(this.field_146289_q, ConfigHandler.hudSettings[5] + "%", this.field_146294_l / 2 + 97, this.field_146295_m / 2 - 1, 16777215);
/* 116 */     super.func_73863_a(x, y, partial);
/*     */     
/* 118 */     GL11.glDisable(32826);
/* 119 */     RenderHelper.func_74518_a();
/* 120 */     GL11.glDisable(2896);
/* 121 */     GL11.glDisable(2929);
/*     */     
/* 123 */     GuiHelper.drawGradientRect(hudX - 19, hudY - 19, hudX + 20, hudY + 20, 553582847, 553582847, 1.0F, 1.0D);
/* 124 */     GuiHelper.drawGradientRect(hudX - 4, hudY, hudX + 5, hudY + 1, -1, -1, 1.0F, 1.0D);
/* 125 */     GuiHelper.drawGradientRect(hudX, hudY - 4, hudX + 1, hudY + 5, -1, -1, 1.0F, 1.0D);
/* 126 */     GuiHelper.drawGradientRect(hudX - 19, hudY - 19, hudX + 20, hudY - 18, -1, -1, 1.0F, 1.0D);
/* 127 */     GuiHelper.drawGradientRect(hudX - 19, hudY + 19, hudX + 20, hudY + 20, -1, -1, 1.0F, 1.0D);
/* 128 */     GuiHelper.drawGradientRect(hudX - 19, hudY - 19, hudX - 18, hudY + 20, -1, -1, 1.0F, 1.0D);
/* 129 */     GuiHelper.drawGradientRect(hudX + 19, hudY - 19, hudX + 20, hudY + 20, -1, -1, 1.0F, 1.0D);
/*     */     
/* 131 */     GuiHelper.drawGradientRect(armorX - 19, armorY - 19, armorX + 20, armorY + 20, 553582847, 553582847, 1.0F, 1.0D);
/* 132 */     GuiHelper.drawGradientRect(armorX - 4, armorY, armorX + 5, armorY + 1, -1, -1, 1.0F, 1.0D);
/* 133 */     GuiHelper.drawGradientRect(armorX, armorY - 4, armorX + 1, armorY + 5, -1, -1, 1.0F, 1.0D);
/* 134 */     GuiHelper.drawGradientRect(armorX - 19, armorY - 19, armorX + 20, armorY - 18, -1, -1, 1.0F, 1.0D);
/* 135 */     GuiHelper.drawGradientRect(armorX - 19, armorY + 19, armorX + 20, armorY + 20, -1, -1, 1.0F, 1.0D);
/* 136 */     GuiHelper.drawGradientRect(armorX - 19, armorY - 19, armorX - 18, armorY + 20, -1, -1, 1.0F, 1.0D);
/* 137 */     GuiHelper.drawGradientRect(armorX + 19, armorY - 19, armorX + 20, armorY + 20, -1, -1, 1.0F, 1.0D);
/*     */     
/* 139 */     GL11.glEnable(2896);
/* 140 */     GL11.glEnable(2929);
/* 141 */     RenderHelper.func_74519_b();
/* 142 */     GL11.glEnable(32826);
/*     */     
/* 144 */     if (GuiHelper.isInRect(armorX - 19, armorY - 19, 39, 39, x, y) || GuiHelper.isInRect(hudX - 19, hudY - 19, 39, 39, x, y)) {
/* 145 */       drawHoveringText(new ArrayList<String>() {  }, x, y, this.field_146289_q);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int x, int y, int button) {
/* 154 */     int[] pos = ConfigHandler.hudSettings;
/* 155 */     int hudX = (int)(pos[0] / 1000.0D * this.field_146294_l);
/* 156 */     int hudY = (int)(pos[1] / 1000.0D * this.field_146295_m);
/* 157 */     int armorX = (int)(pos[2] / 1000.0D * this.field_146294_l);
/* 158 */     int armorY = (int)(pos[3] / 1000.0D * this.field_146295_m);
/*     */     
/* 160 */     if (GuiHelper.isInRect(hudX - 19, hudY - 19, 39, 39, x, y)) {
/* 161 */       this.draggingHud = true;
/* 162 */       this.dragOffsetX = hudX - x;
/* 163 */       this.dragOffsetY = hudY - y; return;
/*     */     } 
/* 165 */     if (GuiHelper.isInRect(armorX - 19, armorY - 19, 39, 39, x, y)) {
/* 166 */       this.draggingArmor = true;
/* 167 */       this.dragOffsetX = armorX - x;
/* 168 */       this.dragOffsetY = armorY - y;
/*     */       
/*     */       return;
/*     */     } 
/* 172 */     super.func_73864_a(x, y, button);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146286_b(int x, int y, int action) {
/* 177 */     super.func_146286_b(x, y, action);
/* 178 */     this.draggingHud = this.draggingArmor = false;
/* 179 */     ConfigHandler.config.get("Gui Stuff", "HUD Settings", new int[] { 7, 874, 100, 100, 100, 100, 0, 0, 0, 0, 1, 1 }, "Used to store the position of the armor ant tool HUD's. This should not be modified", -2147483648, 2147483647, true, 12).set(ConfigHandler.hudSettings);
/* 180 */     ConfigHandler.config.save();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146273_a(int x, int y, int action, long time) {
/* 185 */     super.func_146273_a(x, y, action, time);
/* 186 */     int x1 = (int)((x + this.dragOffsetX) / this.field_146294_l * 1000.0F);
/* 187 */     int y1 = (int)((y + this.dragOffsetY) / this.field_146295_m * 1000.0F);
/*     */     
/* 189 */     if (this.draggingHud) {
/* 190 */       ConfigHandler.hudSettings[0] = x1;
/* 191 */       ConfigHandler.hudSettings[1] = y1;
/* 192 */     } else if (this.draggingArmor) {
/* 193 */       ConfigHandler.hudSettings[2] = x1;
/* 194 */       ConfigHandler.hudSettings[3] = y1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/* 200 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char keyChar, int keyInt) {
/* 205 */     if (keyInt == 1)
/* 206 */       Minecraft.func_71410_x().func_147108_a((GuiScreen)this.parent); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\GuiHudConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */