/*     */ package com.brandon3055.draconicevolution.client.gui;
/*     */ 
/*     */ import com.brandon3055.brandonscore.client.utills.GuiHelper;
/*     */ import com.brandon3055.draconicevolution.common.container.ContainerGrinder;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileGrinder;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GUIGrinder
/*     */   extends GuiContainer
/*     */ {
/*     */   public EntityPlayer player;
/*     */   private TileGrinder tile;
/*     */   private int guiUpdateTick;
/*     */   
/*     */   public GUIGrinder(InventoryPlayer invPlayer, TileGrinder tile) {
/*  28 */     super((Container)new ContainerGrinder(invPlayer, tile));
/*     */     
/*  30 */     this.field_146999_f = 176;
/*  31 */     this.field_147000_g = 162;
/*     */     
/*  33 */     this.tile = tile;
/*  34 */     this.player = invPlayer.field_70458_d;
/*     */   }
/*     */   
/*  37 */   private static final ResourceLocation texture = new ResourceLocation("draconicevolution", "textures/gui/Grinder.png");
/*     */   
/*     */   protected void func_146976_a(float f, int X, int Y) {
/*     */     double power;
/*  41 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  43 */     Minecraft.func_71410_x().func_110434_K().func_110577_a(texture);
/*  44 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*  45 */     if (!this.tile.isExternallyPowered())
/*  46 */       func_73729_b(this.field_147003_i + 63, this.field_147009_r + 34, 0, this.field_147000_g, 18, 18); 
/*  47 */     func_73729_b(this.field_147003_i + 97, this.field_147009_r + 34, 18, this.field_147000_g, 18, 18);
/*  48 */     if (this.tile.func_70301_a(0) == null && !this.tile.isExternallyPowered()) {
/*  49 */       func_73729_b(this.field_147003_i + 63, this.field_147009_r + 34, 36, this.field_147000_g, 18, 18);
/*     */     }
/*     */     
/*  52 */     if (this.tile.isExternallyPowered()) {
/*  53 */       power = 1.0D - this.tile.getEnergyStored() / this.tile.getMaxEnergyStored();
/*     */     } else {
/*  55 */       power = 1.0D - this.tile.getInternalBuffer().getEnergyStored() / this.tile.getInternalBuffer().getMaxEnergyStored();
/*     */     } 
/*     */     
/*  58 */     int powerScaled = (int)(power * 40.0D);
/*  59 */     float fuel = this.tile.burnTimeRemaining / this.tile.burnTime * -1.0F + 1.0F;
/*     */     
/*  61 */     func_73729_b(this.field_147003_i + 83, this.field_147009_r + 11 + powerScaled, this.field_146999_f, powerScaled, 12, 40 - powerScaled);
/*  62 */     func_73729_b(this.field_147003_i + 100, this.field_147009_r + 37 + (int)(fuel * 13.0F), this.field_146999_f, 40 + (int)(fuel * 13.0F), 18, 18 - (int)(fuel * 13.0F));
/*  63 */     if (this.tile.isExternallyPowered()) {
/*  64 */       func_73729_b(this.field_147003_i + 100, this.field_147009_r + 37, this.field_146999_f, 66, 13, 13);
/*     */     }
/*  66 */     this.field_146289_q.func_78261_a("Grinder", this.field_147003_i + 71, this.field_147009_r, 65535);
/*     */     
/*  68 */     int x = X - this.field_147003_i;
/*  69 */     int y = Y - this.field_147009_r;
/*  70 */     if (GuiHelper.isInRect(83, 10, 12, 40, x, y)) {
/*  71 */       ArrayList<String> internal = new ArrayList<>();
/*  72 */       internal.add("Energy Buffer");
/*  73 */       if (this.tile.isExternallyPowered()) {
/*  74 */         internal.add("" + EnumChatFormatting.DARK_BLUE + this.tile.getEnergyStored() + "/" + this.tile.getMaxEnergyStored());
/*     */       } else {
/*  76 */         internal.add("" + EnumChatFormatting.DARK_BLUE + this.tile.getInternalBuffer().getEnergyStored() + "/" + this.tile.getInternalBuffer().getMaxEnergyStored());
/*     */       } 
/*  78 */       drawHoveringText(internal, x + this.field_147003_i, y + this.field_147009_r, this.field_146289_q);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int p_146979_1_, int p_146979_2_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  92 */     super.func_73866_w_();
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
/* 106 */     this.guiUpdateTick++;
/* 107 */     if (this.guiUpdateTick >= 10) {
/* 108 */       func_73866_w_();
/* 109 */       this.guiUpdateTick = 0;
/*     */     } 
/* 111 */     super.func_73876_c();
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\GUIGrinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */