/*     */ package com.brandon3055.draconicevolution.common.container;
/*     */ import com.brandon3055.draconicevolution.client.gui.GUIPlayerDetector;
/*     */ import com.brandon3055.draconicevolution.common.inventory.SlotOpaqueBlock;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TilePlayerDetectorAdvanced;
/*     */ import com.gamerforea.client.core.duck.INonTrivialContainer;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class ContainerPlayerDetector extends Container implements INonTrivialContainer {
/*     */   private TilePlayerDetectorAdvanced tileDetector;
/*  15 */   private GUIPlayerDetector gui = null;
/*     */   
/*     */   public ContainerPlayerDetector(InventoryPlayer invPlayer, TilePlayerDetectorAdvanced tileDetector) {
/*  18 */     this.tileDetector = tileDetector;
/*     */     
/*  20 */     bindPlayerInventory(invPlayer);
/*  21 */     addContainerSlots(tileDetector);
/*  22 */     updateContainerSlots();
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerPlayerDetector(InventoryPlayer invPlayer, TilePlayerDetectorAdvanced tileDetector, GUIPlayerDetector gui) {
/*  27 */     this.tileDetector = tileDetector;
/*  28 */     this.gui = gui;
/*     */     
/*  30 */     bindPlayerInventory(invPlayer);
/*  31 */     addContainerSlots(tileDetector);
/*  32 */     updateContainerSlots();
/*     */   }
/*     */ 
/*     */   
/*     */   private void bindPlayerInventory(InventoryPlayer invPlayer) {
/*  37 */     for (int x = 0; x < 9; x++) {
/*  38 */       func_75146_a(new Slot((IInventory)invPlayer, x, 8 + 18 * x, 174));
/*     */     }
/*     */     
/*  41 */     for (int y = 0; y < 3; y++) {
/*  42 */       for (int i = 0; i < 9; i++) {
/*  43 */         func_75146_a(new Slot((IInventory)invPlayer, i + y * 9 + 9, 8 + 18 * i, 116 + y * 18));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addContainerSlots(TilePlayerDetectorAdvanced tileDetector) {
/*  49 */     func_75146_a((Slot)new SlotOpaqueBlock((IInventory)tileDetector, 0, 145, 15));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateContainerSlots() {
/*  54 */     if (this.gui == null) {
/*     */       return;
/*     */     }
/*     */     
/*  58 */     for (Slot sl : this.field_75151_b) {
/*  59 */       if (sl instanceof SlotOpaqueBlock) {
/*  60 */         if (this.gui.showInvSlots) {
/*  61 */           sl.field_75223_e = 143;
/*  62 */           sl.field_75221_f = 20; continue;
/*     */         } 
/*  64 */         sl.field_75223_e = -1000;
/*  65 */         sl.field_75221_f = -1000;
/*     */         continue;
/*     */       } 
/*  68 */       if (this.gui.showInvSlots) {
/*  69 */         if (sl.field_75222_d < 9) {
/*  70 */           sl.field_75223_e = 8 + 18 * sl.field_75222_d;
/*  71 */           sl.field_75221_f = 174; continue;
/*  72 */         }  if (sl.field_75222_d < 18) {
/*  73 */           sl.field_75223_e = 8 + 18 * (sl.field_75222_d - 9);
/*  74 */           sl.field_75221_f = 116; continue;
/*  75 */         }  if (sl.field_75222_d < 27) {
/*  76 */           sl.field_75223_e = 8 + 18 * (sl.field_75222_d - 18);
/*  77 */           sl.field_75221_f = 134; continue;
/*  78 */         }  if (sl.field_75222_d < 36) {
/*  79 */           sl.field_75223_e = 8 + 18 * (sl.field_75222_d - 27);
/*  80 */           sl.field_75221_f = 152;
/*     */         } 
/*     */         continue;
/*     */       } 
/*  84 */       sl.field_75223_e = -1000;
/*  85 */       sl.field_75221_f = -1000;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer player) {
/*  93 */     return this.tileDetector.func_70300_a(player);
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
/*     */   public ItemStack func_82846_b(EntityPlayer player, int i) {
/* 126 */     return null;
/*     */   }
/*     */   
/*     */   public TilePlayerDetectorAdvanced getTileDetector() {
/* 130 */     return this.tileDetector;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_75144_a(int slot, int button, int par3, EntityPlayer par4EntityPlayer) {
/* 135 */     return super.func_75144_a(slot, button, par3, par4EntityPlayer);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\container\ContainerPlayerDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */