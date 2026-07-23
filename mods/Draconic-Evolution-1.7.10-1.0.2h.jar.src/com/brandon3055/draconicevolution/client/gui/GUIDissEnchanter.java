/*    */ package com.brandon3055.draconicevolution.client.gui;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*    */ import com.brandon3055.draconicevolution.common.container.ContainerDissEnchanter;
/*    */ import com.brandon3055.draconicevolution.common.network.ButtonPacket;
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileDissEnchanter;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GUIDissEnchanter extends GuiContainer {
/*    */   public EntityPlayer player;
/*    */   private TileDissEnchanter tile;
/*    */   private boolean cachRecipeValid = false;
/* 25 */   private int cachCost = 0;
/*    */   
/*    */   public GUIDissEnchanter(InventoryPlayer invPlayer, TileDissEnchanter tile) {
/* 28 */     super((Container)new ContainerDissEnchanter(invPlayer, tile));
/*    */     
/* 30 */     this.field_146999_f = 176;
/* 31 */     this.field_147000_g = 142;
/*    */     
/* 33 */     this.tile = tile;
/* 34 */     this.player = invPlayer.field_70458_d;
/*    */   }
/*    */   
/* 37 */   private static final ResourceLocation texture = new ResourceLocation("draconicevolution", "textures/gui/DissEnchanter.png");
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 41 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 42 */     Minecraft.func_71410_x().func_110434_K().func_110577_a(texture);
/* 43 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int x, int y) {
/* 49 */     func_73732_a(this.field_146289_q, StatCollector.func_74838_a("tile.draconicevolution:dissEnchanter.name"), 88, 4, 65535);
/*    */     
/* 51 */     this.field_146289_q.func_78276_b("Item", 5, 40, 255);
/* 52 */     this.field_146289_q.func_78276_b("Damage: " + (40.0F - this.tile.bookPower) + "%", 5, 49, 255);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_73866_w_() {
/* 58 */     super.func_73866_w_();
/* 59 */     this.field_146292_n.clear();
/* 60 */     int posX = (this.field_146294_l - this.field_146999_f) / 2;
/* 61 */     int posY = (this.field_146295_m - this.field_147000_g) / 2;
/* 62 */     this.field_146292_n.add(new GuiButtonAHeight(0, posX + 108, posY + 45, 60, 12, ""));
/* 63 */     updateButtonState();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146284_a(GuiButton button) {
/* 68 */     if (button.field_146127_k == 0) DraconicEvolution.network.sendToServer((IMessage)new ButtonPacket((byte)1, true));
/*    */   
/*    */   }
/*    */   
/*    */   public void func_73876_c() {
/* 73 */     super.func_73876_c();
/* 74 */     if (this.cachRecipeValid != this.tile.isValidRecipe) {
/* 75 */       this.cachRecipeValid = this.tile.isValidRecipe;
/* 76 */       updateButtonState();
/*    */     } 
/* 78 */     if (this.cachCost != this.tile.dissenchantCost) {
/* 79 */       this.cachCost = this.tile.dissenchantCost;
/* 80 */       updateButtonState();
/*    */     } 
/*    */   }
/*    */   
/*    */   private void updateButtonState() {
/* 85 */     boolean flag = this.cachRecipeValid;
/* 86 */     if (flag && this.player.field_71068_ca < this.tile.dissenchantCost && !this.player.field_71075_bZ.field_75098_d)
/* 87 */       flag = false; 
/* 88 */     ((GuiButtonAHeight)this.field_146292_n.get(0)).field_146124_l = flag;
/* 89 */     ((GuiButtonAHeight)this.field_146292_n.get(0)).packedFGColour = this.cachRecipeValid ? 0 : 14614528;
/* 90 */     ((GuiButtonAHeight)this.field_146292_n.get(0)).field_146126_j = "Cost: " + this.tile.dissenchantCost;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\GUIDissEnchanter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */