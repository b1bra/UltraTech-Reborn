/*     */ package com.brandon3055.draconicevolution.common.tileentities;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ public class TileTeleporterStand
/*     */   extends TileEntity
/*     */   implements IInventory {
/*  16 */   ItemStack[] items = new ItemStack[1];
/*  17 */   public int rotation = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet func_145844_m() {
/*  30 */     NBTTagCompound tagCompound = new NBTTagCompound();
/*  31 */     func_145841_b(tagCompound);
/*  32 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, tagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/*  37 */     func_145839_a(pkt.func_148857_g());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  44 */     return this.items.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/*  49 */     return this.items[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int count) {
/*  54 */     ItemStack itemstack = func_70301_a(i);
/*     */     
/*  56 */     if (itemstack != null) {
/*  57 */       if (itemstack.field_77994_a <= count) {
/*  58 */         func_70299_a(i, (ItemStack)null);
/*     */       } else {
/*  60 */         itemstack = itemstack.func_77979_a(count);
/*  61 */         if (itemstack.field_77994_a == 0) {
/*  62 */           func_70299_a(i, (ItemStack)null);
/*     */         }
/*     */       } 
/*     */     }
/*  66 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int i) {
/*  71 */     ItemStack item = func_70301_a(i);
/*  72 */     if (item != null) func_70299_a(i, (ItemStack)null); 
/*  73 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/*  78 */     this.items[i] = itemstack;
/*  79 */     if (itemstack != null && itemstack.field_77994_a > func_70297_j_()) {
/*  80 */       itemstack.field_77994_a = func_70297_j_();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  86 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/*  96 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 101 */     return (player.func_70092_e(this.field_145851_c + 0.5D, this.field_145848_d + 0.5D, this.field_145849_e + 0.4D) < 64.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70305_f() {}
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound compound) {
/* 121 */     NBTTagCompound[] tag = new NBTTagCompound[this.items.length];
/*     */     
/* 123 */     for (int i = 0; i < this.items.length; i++) {
/* 124 */       tag[i] = new NBTTagCompound();
/*     */       
/* 126 */       if (this.items[i] != null) {
/* 127 */         tag[i] = this.items[i].func_77955_b(tag[i]);
/*     */       }
/*     */       
/* 130 */       compound.func_74782_a("Item" + i, (NBTBase)tag[i]);
/*     */     } 
/* 132 */     super.func_145841_b(compound);
/*     */     
/* 134 */     compound.func_74768_a("Rotation", this.rotation);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound compound) {
/* 139 */     NBTTagCompound[] tag = new NBTTagCompound[this.items.length];
/*     */     
/* 141 */     for (int i = 0; i < this.items.length; i++) {
/* 142 */       tag[i] = compound.func_74775_l("Item" + i);
/* 143 */       this.items[i] = ItemStack.func_77949_a(tag[i]);
/*     */     } 
/* 145 */     super.func_145839_a(compound);
/*     */     
/* 147 */     this.rotation = compound.func_74762_e("Rotation");
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\TileTeleporterStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */