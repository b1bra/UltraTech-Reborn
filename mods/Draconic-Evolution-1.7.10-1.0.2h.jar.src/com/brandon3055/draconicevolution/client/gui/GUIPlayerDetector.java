/*     */ package com.brandon3055.draconicevolution.client.gui;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.container.ContainerPlayerDetector;
/*     */ import com.brandon3055.draconicevolution.common.network.PlayerDetectorButtonPacket;
/*     */ import com.brandon3055.draconicevolution.common.network.PlayerDetectorStringPacket;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TilePlayerDetectorAdvanced;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GUIPlayerDetector
/*     */   extends GuiContainer {
/*     */   public EntityPlayer player;
/*     */   private TilePlayerDetectorAdvanced detector;
/*     */   public boolean showInvSlots = true;
/*     */   private boolean editMode = false;
/*  30 */   private int range = 0;
/*  31 */   private int maxRange = 20;
/*     */   private boolean whitelist = false;
/*     */   private boolean initScedualed = false;
/*  34 */   private int initTick = 0;
/*  35 */   private String[] names = new String[42];
/*     */   private GuiTextField selectedNameText;
/*  37 */   private int selected = -1;
/*     */   private boolean outputInverted = false;
/*     */   
/*     */   public GUIPlayerDetector(InventoryPlayer invPlayer, TilePlayerDetectorAdvanced detector) {
/*  41 */     super(detector.getGuiContainer(invPlayer));
/*  42 */     this.field_147002_h = (Container)new ContainerPlayerDetector(invPlayer, detector, this);
/*     */     
/*  44 */     Arrays.fill((Object[])this.names, "");
/*     */     
/*  46 */     this.field_146999_f = 176;
/*  47 */     this.field_147000_g = 198;
/*     */     
/*  49 */     this.detector = detector;
/*  50 */     this.player = invPlayer.field_70458_d;
/*  51 */     syncWithServer();
/*     */   }
/*     */   
/*  54 */   private static final ResourceLocation texture = new ResourceLocation("draconicevolution", "textures/gui/PlayerDetector.png");
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/*  58 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  60 */     Minecraft.func_71410_x().func_110434_K().func_110577_a(texture);
/*  61 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*  62 */     if (this.editMode) {
/*  63 */       func_73729_b(this.field_147003_i + 3, this.field_147009_r + this.field_147000_g / 2, 3, 3, this.field_146999_f - 6, this.field_147000_g / 2 - 3);
/*  64 */       drawNameChart(x, y);
/*     */     } 
/*     */     
/*  67 */     if (this.showInvSlots) func_73729_b(this.field_147003_i + 142, this.field_147009_r + 19, this.field_146999_f, 0, 23, 41);
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int x, int y) {
/*  73 */     drawGuiText(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int x, int y, float p_73863_3_) {
/*  80 */     super.func_73863_a(x, y, p_73863_3_);
/*  81 */     ArrayList<String> lines = new ArrayList<>();
/*  82 */     lines.add("Camouflage");
/*  83 */     if (x - this.field_147003_i > 142 && x - this.field_147003_i < 160 && y - this.field_147009_r > 19 && y - this.field_147009_r < 37 && this.showInvSlots) {
/*  84 */       drawHoveringText(lines, x, y, this.field_146289_q);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  90 */     super.func_73866_w_();
/*  91 */     this.field_146292_n.clear();
/*  92 */     if (!this.editMode) {
/*  93 */       String wt = this.whitelist ? "White List" : "Black List";
/*  94 */       int centre = this.field_146294_l / 2;
/*  95 */       this.field_146292_n.add(new GuiButton(0, centre - 20 - 20, this.field_147009_r + 20, 20, 20, "+"));
/*  96 */       this.field_146292_n.add(new GuiButton(1, centre + 20, this.field_147009_r + 20, 20, 20, "-"));
/*  97 */       this.field_146292_n.add(new GuiButton(3, centre - 40, this.field_147009_r + 45, 60, 20, wt));
/*  98 */       this.field_146292_n.add(new GuiButton(4, centre + 20, this.field_147009_r + 45, 20, 20, "!"));
/*  99 */       this.field_146292_n.add(new GuiButton(6, centre - 40, this.field_147009_r + 70, 80, 20, "Invert Output"));
/*     */     } else {
/* 101 */       this.field_146292_n.add(new GuiButton(5, this.field_147003_i - 40, this.field_147009_r + this.field_147000_g - 20, 40, 20, "Back"));
/*     */     } 
/*     */     
/* 104 */     this.selectedNameText = new GuiTextField(this.field_146289_q, 4, -12, 168, 12);
/* 105 */     this.selectedNameText.func_146193_g(-1);
/* 106 */     this.selectedNameText.func_146204_h(-1);
/* 107 */     this.selectedNameText.func_146185_a(true);
/* 108 */     this.selectedNameText.func_146203_f(40);
/* 109 */     this.selectedNameText.func_146189_e(this.editMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton button) {
/*     */     byte val;
/*     */     byte val2;
/* 119 */     switch (button.field_146127_k) {
/*     */       case 0:
/* 121 */         this.range = (this.range < this.maxRange) ? (this.range + 1) : this.maxRange;
/* 122 */         DraconicEvolution.network.sendToServer((IMessage)new PlayerDetectorButtonPacket((byte)0, (short)(byte)this.range));
/*     */         break;
/*     */       case 1:
/* 125 */         this.range = (this.range > 1) ? (this.range - 1) : 1;
/* 126 */         DraconicEvolution.network.sendToServer((IMessage)new PlayerDetectorButtonPacket((byte)0, (short)(byte)this.range));
/*     */         break;
/*     */       case 3:
/* 129 */         this.initScedualed = true;
/* 130 */         this.editMode = true;
/* 131 */         this.showInvSlots = false;
/* 132 */         ((ContainerPlayerDetector)this.field_147002_h).updateContainerSlots();
/*     */         break;
/*     */       case 4:
/* 135 */         this.whitelist = !this.whitelist;
/* 136 */         this.initScedualed = true;
/* 137 */         val = (byte)(this.whitelist ? 1 : 0);
/* 138 */         DraconicEvolution.network.sendToServer((IMessage)new PlayerDetectorButtonPacket((byte)1, (short)val));
/*     */         break;
/*     */       case 5:
/* 141 */         this.editMode = false;
/* 142 */         this.showInvSlots = true;
/* 143 */         ((ContainerPlayerDetector)this.field_147002_h).updateContainerSlots();
/* 144 */         this.initScedualed = true;
/*     */         break;
/*     */       case 6:
/* 147 */         this.outputInverted = !this.outputInverted;
/* 148 */         this.initScedualed = true;
/* 149 */         val2 = (byte)(this.outputInverted ? 1 : 0);
/* 150 */         DraconicEvolution.network.sendToServer((IMessage)new PlayerDetectorButtonPacket((byte)2, (short)val2));
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char par1, int par2) {
/* 160 */     if (!this.selectedNameText.func_146201_a(par1, par2))
/* 161 */       if (par2 == 28)
/* 162 */       { if (this.selectedNameText.func_146206_l()) {
/* 163 */           this.names[this.selected] = this.selectedNameText.func_146179_b();
/* 164 */           this.selectedNameText.func_146180_a("");
/* 165 */           this.selectedNameText.func_146195_b(false);
/* 166 */           DraconicEvolution.network.sendToServer((IMessage)new PlayerDetectorStringPacket((byte)this.selected, this.names[this.selected]));
/* 167 */           this.selected = -1;
/*     */         }  }
/* 169 */       else { super.func_73869_a(par1, par2); }
/*     */        
/*     */   }
/*     */   
/*     */   public void func_73876_c() {
/* 174 */     if (this.initScedualed) this.initTick++; 
/* 175 */     if (this.initTick > 1) {
/* 176 */       this.initTick = 0;
/* 177 */       this.initScedualed = false;
/* 178 */       func_73866_w_();
/*     */     } 
/* 180 */     super.func_73876_c();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int x, int y, int par3) {
/* 185 */     super.func_73864_a(x, y, par3);
/*     */     
/* 187 */     if (this.editMode) selectName(x - this.field_147003_i, y - this.field_147009_r);
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawGuiText(int rawX, int rawY) {
/* 193 */     if (!this.editMode)
/* 194 */     { func_73732_a(this.field_146289_q, "Advanced Player Detector", this.field_146999_f / 2, 5, 65535);
/*     */       
/* 196 */       this.field_146289_q.func_85187_a("Range:", 73, 21, 0, false);
/* 197 */       this.field_146289_q.func_85187_a("Output Inverted: " + this.outputInverted, 33, 97, 0, false);
/* 198 */       if (this.range < 10) { this.field_146289_q.func_85187_a("" + this.range, 85, 31, 0, false); }
/* 199 */       else { this.field_146289_q.func_85187_a("" + this.range, 82, 31, 0, false); }
/*     */        }
/* 201 */     else { if (this.selected != -1) {
/* 202 */         func_73732_a(this.field_146289_q, "Press Enter to save", this.field_146999_f / 2, -22, 16711680);
/*     */       }
/* 204 */       for (int i = 0; i < 21; i++) {
/* 205 */         for (int j = 0; j < 2; j++) {
/* 206 */           if (i + j * 21 != this.selected) {
/* 207 */             String s = this.names[i + j * 21];
/* 208 */             if (s.length() > 13) s = s.substring(0, 13) + "..."; 
/* 209 */             this.field_146289_q.func_78276_b(s, 5 + j * 84, 6 + i * 9, 9961472);
/*     */           } 
/*     */         } 
/*     */       }  }
/*     */ 
/*     */     
/* 215 */     this.selectedNameText.func_146194_f();
/*     */   }
/*     */   
/*     */   private void drawNameChart(int rawX, int rawY) {
/* 219 */     int x = rawX - this.field_147003_i;
/* 220 */     int y = rawY - this.field_147009_r;
/*     */     int i;
/* 222 */     for (i = 0; i < 21; i++) {
/* 223 */       func_73729_b(this.field_147003_i + 4, this.field_147009_r + 4 + i * 9, 0, this.field_147000_g, 186, 10);
/*     */     }
/*     */     
/* 226 */     for (i = 0; i < 21; i++) {
/* 227 */       for (int j = 0; j < 2; j++) {
/* 228 */         if ((x > 4 + j * 84 && x < this.field_146999_f / 2 - 1 + j * 82 && y > 4 + i * 9 && y < 13 + i * 9) || i + j * 21 == this.selected)
/* 229 */           func_73729_b(this.field_147003_i + 5 + j * 84, this.field_147009_r + 5 + i * 9, 0, this.field_147000_g + 10, 82, 8); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void selectName(int x, int y) {
/* 235 */     if (this.initScedualed)
/*     */       return; 
/* 237 */     for (int i = 0; i < 21; i++) {
/* 238 */       for (int j = 0; j < 2; j++) {
/* 239 */         if (x > 4 + j * 84 && x < this.field_146999_f / 2 - 1 + j * 82 && y > 4 + i * 9 && y < 13 + i * 9) {
/* 240 */           this.selected = i + j * 21;
/* 241 */           this.selectedNameText.func_146180_a(this.names[i + j * 21]);
/* 242 */           this.selectedNameText.func_146195_b(true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void syncWithServer() {
/* 249 */     this.whitelist = this.detector.whiteList;
/* 250 */     for (int i = 0; i < this.detector.names.length; i++) {
/* 251 */       if (this.detector.names[i] != null) this.names[i] = this.detector.names[i]; 
/*     */     } 
/* 253 */     this.range = this.detector.range;
/* 254 */     this.outputInverted = this.detector.outputInverted;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\GUIPlayerDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */