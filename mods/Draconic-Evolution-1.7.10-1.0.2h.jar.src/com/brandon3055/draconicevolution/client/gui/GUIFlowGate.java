/*     */ package com.brandon3055.draconicevolution.client.gui;
/*     */ 
/*     */ import com.brandon3055.brandonscore.client.utills.GuiHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.gates.TileGate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import org.lwjglx.input.Keyboard;
/*     */ import org.lwjglx.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GUIFlowGate
/*     */   extends GuiScreen
/*     */ {
/*     */   public TileGate tile;
/*     */   
/*     */   public GUIFlowGate(TileGate gate) {
/*  24 */     this.tile = gate;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  29 */     int guiLeft = this.field_146294_l / 2 - 98;
/*  30 */     int guiTop = this.field_146295_m / 2 - 44;
/*  31 */     this.field_146292_n.clear();
/*  32 */     if (this.tile.flowOverridden)
/*  33 */       return;  this.field_146292_n.add(new GuiTextureButton(0, guiLeft + 20, guiTop + 20, 0, 108, 18, 18, ""));
/*  34 */     this.field_146292_n.add(new GuiTextureButton(1, guiLeft + 159, guiTop + 20, 0, 54, 18, 18, ""));
/*     */     
/*  36 */     this.field_146292_n.add(new GuiTextureButton(2, guiLeft + 20, guiTop + 50, 0, 108, 18, 18, ""));
/*  37 */     this.field_146292_n.add(new GuiTextureButton(3, guiLeft + 159, guiTop + 50, 0, 54, 18, 18, ""));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73863_a(int x, int y, float pt) {
/*  42 */     int guiLeft = this.field_146294_l / 2 - 98;
/*  43 */     int guiCrt = this.field_146294_l / 2;
/*  44 */     int guiTop = this.field_146295_m / 2 - 44;
/*     */     
/*  46 */     func_146276_q_();
/*  47 */     ResourceHandler.bindResource("textures/gui/ToolConfig.png");
/*  48 */     func_73729_b(guiLeft, guiTop, 0, 0, 197, 88);
/*  49 */     this.field_146289_q.func_78276_b(StatCollector.func_74838_a("gui.de.flowGate.name"), guiCrt - this.field_146289_q.func_78256_a(StatCollector.func_74838_a("gui.de.flowGate.name")) / 2, guiTop + 5, 2894892);
/*     */     
/*  51 */     if (!this.tile.flowOverridden) {
/*  52 */       String flowRSLow = this.tile.getFlowSetting(0);
/*  53 */       String flowRSHeigh = this.tile.getFlowSetting(1);
/*  54 */       this.field_146289_q.func_78276_b(StatCollector.func_74838_a("gui.de.flowGateRSHigh.name"), guiCrt - this.field_146289_q.func_78256_a(StatCollector.func_74838_a("gui.de.flowGateRSHigh.name")) / 2, guiTop + 20, 16711680);
/*  55 */       this.field_146289_q.func_78276_b(flowRSHeigh, guiCrt - this.field_146289_q.func_78256_a(flowRSHeigh) / 2, guiTop + 31, 2894892);
/*  56 */       this.field_146289_q.func_78276_b(StatCollector.func_74838_a("gui.de.flowGateRSLow.name"), guiCrt - this.field_146289_q.func_78256_a(StatCollector.func_74838_a("gui.de.flowGateRSLow.name")) / 2, guiTop + 50, 6684672);
/*  57 */       this.field_146289_q.func_78276_b(flowRSLow, guiCrt - this.field_146289_q.func_78256_a(flowRSLow) / 2, guiTop + 61, 2894892);
/*     */     } else {
/*  59 */       func_73732_a(this.field_146289_q, StatCollector.func_74838_a("gui.de.flowGateOverridden.txt"), this.field_146294_l / 2, this.field_146295_m / 2 - 30, 65280);
/*     */     } 
/*  61 */     String flow = StatCollector.func_74838_a("gui.de.flowGateCurrentFlow.name") + ": " + Utills.addCommas(this.tile.getActualFlow());
/*  62 */     this.field_146289_q.func_78276_b(flow, guiCrt - this.field_146289_q.func_78256_a(flow) / 2, guiTop + 76, 2894892);
/*     */     
/*  64 */     if (!this.tile.flowOverridden) {
/*  65 */       this.field_146289_q.func_78279_b(StatCollector.func_74838_a("gui.de.flowGatePartialSignal.name"), guiLeft + 5, guiTop + 90, 197, 16777215);
/*  66 */       this.field_146289_q.func_78279_b(StatCollector.func_74838_a("gui.de.ctrlAndShift.name"), guiLeft + 200, guiTop + 5, 100, 16777215);
/*     */       
/*  68 */       ResourceHandler.bindResource("textures/gui/Widgets.png");
/*  69 */       super.func_73863_a(x, y, pt);
/*     */       
/*  71 */       List<String> hoverText = new ArrayList<>();
/*  72 */       boolean shift = (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
/*  73 */       boolean ctrl = (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157));
/*     */       
/*  75 */       if (GuiHelper.isInRect(guiLeft + 20, guiTop + 20, 18, 18, x, y)) {
/*  76 */         hoverText.add(StatCollector.func_74838_a("gui.de.decrement.name") + " " + this.tile.getToolTip(1, shift, ctrl));
/*     */       }
/*  78 */       if (GuiHelper.isInRect(guiLeft + 159, guiTop + 20, 18, 18, x, y)) {
/*  79 */         hoverText.add(StatCollector.func_74838_a("gui.de.increment.name") + " " + this.tile.getToolTip(1, shift, ctrl));
/*     */       }
/*     */       
/*  82 */       if (GuiHelper.isInRect(guiLeft + 20, guiTop + 50, 18, 18, x, y)) {
/*  83 */         hoverText.add(StatCollector.func_74838_a("gui.de.decrement.name") + " " + this.tile.getToolTip(0, shift, ctrl));
/*     */       }
/*  85 */       if (GuiHelper.isInRect(guiLeft + 159, guiTop + 50, 18, 18, x, y)) {
/*  86 */         hoverText.add(StatCollector.func_74838_a("gui.de.increment.name") + " " + this.tile.getToolTip(0, shift, ctrl));
/*     */       }
/*     */       
/*  89 */       if (!hoverText.isEmpty()) {
/*  90 */         hoverText.add(EnumChatFormatting.GRAY + StatCollector.func_74838_a("gui.de.pressOrScroll.name"));
/*  91 */         drawHoveringText(hoverText, x - ((x < this.field_146294_l / 2) ? (this.field_146289_q.func_78256_a(StatCollector.func_74838_a("gui.de.decrement.name") + " " + this.tile.getToolTip(1, shift, ctrl)) + 25) : 0), y, this.field_146289_q);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int x, int y, int button) {
/*  98 */     int guiLeft = this.field_146294_l / 2 - 98;
/*  99 */     int guiTop = this.field_146295_m / 2 - 44;
/* 100 */     boolean shift = (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
/* 101 */     boolean ctrl = (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157));
/*     */     
/* 103 */     if (GuiHelper.isInRect(guiLeft + 20, guiTop + 20, 18, 18, x, y)) {
/* 104 */       this.tile.incrementFlow(1, ctrl, shift, false, button);
/*     */     }
/* 106 */     if (GuiHelper.isInRect(guiLeft + 159, guiTop + 20, 18, 18, x, y)) {
/* 107 */       this.tile.incrementFlow(1, ctrl, shift, true, button);
/*     */     }
/*     */     
/* 110 */     if (GuiHelper.isInRect(guiLeft + 20, guiTop + 50, 18, 18, x, y)) {
/* 111 */       this.tile.incrementFlow(0, ctrl, shift, false, button);
/*     */     }
/* 113 */     if (GuiHelper.isInRect(guiLeft + 159, guiTop + 50, 18, 18, x, y)) {
/* 114 */       this.tile.incrementFlow(0, ctrl, shift, true, button);
/*     */     }
/*     */     
/* 117 */     super.func_73864_a(x, y, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146274_d() {
/* 122 */     int guiLeft = this.field_146294_l / 2 - 98;
/* 123 */     int guiTop = this.field_146295_m / 2 - 44;
/* 124 */     int x = Mouse.getEventX() * this.field_146294_l / this.field_146297_k.field_71443_c;
/* 125 */     int y = this.field_146295_m - Mouse.getEventY() * this.field_146295_m / this.field_146297_k.field_71440_d - 1;
/* 126 */     boolean shift = (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
/* 127 */     boolean ctrl = (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157));
/* 128 */     int i = Mouse.getEventDWheel();
/*     */     
/* 130 */     if (i != 0 && GuiHelper.isInRect(guiLeft + 20, guiTop + 20, 18, 18, x, y)) {
/* 131 */       this.tile.incrementFlow(1, ctrl, shift, (i < 0), 0);
/*     */     }
/* 133 */     if (i != 0 && GuiHelper.isInRect(guiLeft + 159, guiTop + 20, 18, 18, x, y)) {
/* 134 */       this.tile.incrementFlow(1, ctrl, shift, (i > 0), 0);
/*     */     }
/*     */     
/* 137 */     if (i != 0 && GuiHelper.isInRect(guiLeft + 20, guiTop + 50, 18, 18, x, y)) {
/* 138 */       this.tile.incrementFlow(0, ctrl, shift, (i < 0), 0);
/*     */     }
/* 140 */     if (i != 0 && GuiHelper.isInRect(guiLeft + 159, guiTop + 50, 18, 18, x, y)) {
/* 141 */       this.tile.incrementFlow(0, ctrl, shift, (i > 0), 0);
/*     */     }
/*     */     
/* 144 */     super.func_146274_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char p_73869_1_, int key) {
/* 154 */     if (key == 1 || key == this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i()) {
/* 155 */       this.field_146297_k.func_147108_a(null);
/* 156 */       this.field_146297_k.func_71381_h();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\GUIFlowGate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */