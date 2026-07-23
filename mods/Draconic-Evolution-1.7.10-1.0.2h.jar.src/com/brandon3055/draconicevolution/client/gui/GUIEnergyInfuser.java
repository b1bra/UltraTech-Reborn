/*     */ package com.brandon3055.draconicevolution.client.gui;
/*     */ 
/*     */ import com.brandon3055.brandonscore.client.utills.GuiHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.IC2Helper;
/*     */ import com.brandon3055.draconicevolution.common.container.ContainerEnergyInfuser;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileEnergyInfuser;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GUIEnergyInfuser
/*     */   extends GuiContainer
/*     */ {
/*     */   public EntityPlayer player;
/*     */   private TileEnergyInfuser tile;
/*  29 */   private float rotation = 0.0F;
/*     */   
/*     */   public GUIEnergyInfuser(InventoryPlayer invPlayer, TileEnergyInfuser tile) {
/*  32 */     super((Container)new ContainerEnergyInfuser(invPlayer, tile));
/*     */     
/*  34 */     this.field_146999_f = 176;
/*  35 */     this.field_147000_g = 140;
/*     */     
/*  37 */     this.tile = tile;
/*  38 */     this.player = invPlayer.field_70458_d;
/*     */   }
/*     */   
/*  41 */   private static final ResourceLocation texture = new ResourceLocation("draconicevolution", "textures/gui/EnergyInfuser.png");
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/*  45 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  46 */     Minecraft.func_71410_x().func_110434_K().func_110577_a(texture);
/*  47 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*     */     
/*  49 */     ItemStack stack = this.tile.func_70301_a(0);
/*  50 */     if (stack == null) {
/*  51 */       func_73729_b(this.field_147003_i + 63, this.field_147009_r + 34, 36, this.field_147000_g, 18, 18);
/*     */     }
/*  53 */     double power = 1.0D - this.tile.energy.getEnergyStored() / this.tile.energy.getMaxEnergyStored();
/*  54 */     int powerScaled = (int)(power * 45.0D);
/*  55 */     func_73729_b(this.field_147003_i + 49, this.field_147009_r + 7 + powerScaled, this.field_146999_f, powerScaled, 8, 45 - powerScaled);
/*     */     
/*  57 */     if (this.tile.running && IC2Helper.isElectricItem(stack)) {
/*  58 */       double charge = 1.0D - IC2Helper.getChargePercent(stack);
/*  59 */       int scaledCharge = (int)(charge * 45.0D);
/*  60 */       func_73729_b(this.field_147003_i + 119, this.field_147009_r + 7 + scaledCharge, this.field_146999_f, scaledCharge, 8, 45 - scaledCharge);
/*     */     } 
/*     */     
/*  63 */     drawAnimatedParts();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int x, int y) {
/*  68 */     this.field_146289_q.func_78261_a("Energy Infuser", 49, -9, 65535);
/*     */   }
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
/*     */   public void func_73863_a(int x, int y, float p_73863_3_) {
/*  81 */     super.func_73863_a(x, y, p_73863_3_);
/*  82 */     drawEnergyBarHoverText(x - this.field_147003_i, y - this.field_147009_r);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  87 */     super.func_73866_w_();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton button) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73876_c() {
/* 101 */     super.func_73876_c();
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawEnergyBarHoverText(int x, int y) {
/* 106 */     if (GuiHelper.isInRect(48, 6, 9, 46, x, y)) {
/* 107 */       ArrayList<String> internal = new ArrayList<>();
/* 108 */       internal.add(StatCollector.func_74838_a("gui.de.internalStorage.txt"));
/* 109 */       internal.add("" + EnumChatFormatting.DARK_BLUE + this.tile.energy.getEnergyStored() + "/" + this.tile.energy.getMaxEnergyStored());
/* 110 */       GL11.glPushAttrib(64);
/* 111 */       drawHoveringText(internal, x + this.field_147003_i, y + this.field_147009_r, this.field_146289_q);
/* 112 */       GL11.glPopAttrib();
/*     */     } 
/*     */     
/* 115 */     ItemStack stack = this.tile.func_70301_a(0);
/*     */     
/* 117 */     if (GuiHelper.isInRect(118, 6, 10, 46, x, y) && this.tile.running && IC2Helper.isElectricItem(stack)) {
/* 118 */       ArrayList<String> internal = new ArrayList<>();
/* 119 */       internal.add(StatCollector.func_74838_a("gui.de.itemStorage.txt"));
/* 120 */       internal.add("" + EnumChatFormatting.DARK_BLUE + IC2Helper.floorEu(IC2Helper.getCharge(stack)) + "/" + IC2Helper.floorEu(IC2Helper.getMaxCharge(stack)));
/* 121 */       GL11.glPushAttrib(64);
/* 122 */       drawHoveringText(internal, x + this.field_147003_i, y + this.field_147009_r, this.field_146289_q);
/* 123 */       GL11.glPopAttrib();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawAnimatedParts() {
/* 128 */     if (!this.tile.running) func_73729_b(this.field_147003_i + 79, this.field_147009_r + 21, this.field_146999_f, 45, 18, 18); 
/* 129 */     if (this.tile.running) this.rotation += 0.2F; 
/* 130 */     GL11.glPushMatrix();
/* 131 */     GL11.glEnable(3042);
/* 132 */     GL11.glTranslatef((this.field_147003_i + 62), (this.field_147009_r + 4), 0.0F);
/*     */ 
/*     */     
/* 135 */     GL11.glTranslatef(26.0F, 26.0F, 0.0F);
/* 136 */     GL11.glRotatef(this.rotation, 0.0F, 0.0F, 1.0F);
/* 137 */     GL11.glTranslatef(-26.0F, -26.0F, 0.0F);
/* 138 */     func_73729_b(0, 0, this.field_146999_f, 63, 52, 52);
/*     */     
/* 140 */     if (this.tile.running && this.tile.transfer) {
/* 141 */       Random rand = (this.tile.func_145831_w()).field_73012_v;
/* 142 */       int boltL = rand.nextInt(4);
/* 143 */       int boltS = rand.nextInt(3);
/* 144 */       int boltT = rand.nextInt(10);
/* 145 */       func_73729_b(0, 0, this.field_146999_f, 115, 52, 52);
/*     */       
/* 147 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F + rand.nextFloat() / 2.0F);
/* 148 */       func_73729_b(0, 0, this.field_146999_f, 167, 52, 52);
/* 149 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 151 */       if (boltT == 5) {
/* 152 */         GL11.glTranslatef(26.0F, 26.0F, 0.0F);
/* 153 */         GL11.glRotatef((boltL * 90), 0.0F, 0.0F, 1.0F);
/* 154 */         GL11.glTranslatef(-26.0F, -26.0F, 0.0F);
/*     */         
/* 156 */         if (boltS == 0) {
/* 157 */           GL11.glTranslatef(-(this.field_147003_i + 62), -(this.field_147009_r + 4), 0.0F);
/* 158 */           GL11.glTranslatef((this.field_147003_i + 68), (this.field_147009_r + 23), 0.0F);
/* 159 */           func_73729_b(0, 0, 0, this.field_147000_g, 27, 15);
/*     */         } 
/* 161 */         if (boltS == 1) {
/* 162 */           GL11.glTranslatef(-(this.field_147003_i + 62), -(this.field_147009_r + 4), 0.0F);
/* 163 */           GL11.glTranslatef((this.field_147003_i + 68), (this.field_147009_r + 26), 0.0F);
/* 164 */           func_73729_b(0, 0, 0, 156, 25, 8);
/*     */         } 
/* 166 */         if (boltS == 2) {
/* 167 */           GL11.glTranslatef(-(this.field_147003_i + 62), -(this.field_147009_r + 4), 0.0F);
/* 168 */           GL11.glTranslatef((this.field_147003_i + 68), (this.field_147009_r + 27), 0.0F);
/* 169 */           func_73729_b(0, 0, 0, 165, 23, 7);
/*     */         } 
/* 171 */         if (boltS == 3) {
/* 172 */           GL11.glTranslatef(-(this.field_147003_i + 62), -(this.field_147009_r + 4), 0.0F);
/* 173 */           GL11.glTranslatef((this.field_147003_i + 68), (this.field_147009_r + 26), 0.0F);
/* 174 */           func_73729_b(0, 0, 0, 173, 26, 8);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 181 */     GL11.glDisable(3042);
/* 182 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\GUIEnergyInfuser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */