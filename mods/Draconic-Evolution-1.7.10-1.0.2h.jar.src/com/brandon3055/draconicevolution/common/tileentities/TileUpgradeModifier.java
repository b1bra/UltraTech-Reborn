/*     */ package com.brandon3055.draconicevolution.common.tileentities;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ public class TileUpgradeModifier
/*     */   extends TileEntity
/*     */   implements ISidedInventory {
/*  16 */   ItemStack[] items = new ItemStack[1];
/*     */   
/*  18 */   public float rotation = 0.0F;
/*  19 */   public float rotationSpeed = 0.0F;
/*  20 */   private float targetSpeed = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  26 */     if (this.items[0] != null) { this.targetSpeed = 5.0F; }
/*  27 */     else { this.targetSpeed = 0.0F; }
/*     */     
/*  29 */     if (this.rotationSpeed < this.targetSpeed) { this.rotationSpeed += 0.05F; }
/*  30 */     else if (this.rotationSpeed > this.targetSpeed) { this.rotationSpeed -= 0.05F; }
/*  31 */      if (this.targetSpeed == 0.0F && this.rotationSpeed < 0.0F) this.rotationSpeed = 0.0F; 
/*  32 */     this.rotation += this.rotationSpeed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet func_145844_m() {
/*  40 */     NBTTagCompound tagCompound = new NBTTagCompound();
/*  41 */     func_145841_b(tagCompound);
/*  42 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, tagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/*  47 */     func_145839_a(pkt.func_148857_g());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  54 */     return this.items.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/*  59 */     return this.items[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int count) {
/*  64 */     ItemStack itemstack = func_70301_a(i);
/*     */     
/*  66 */     if (itemstack != null) {
/*  67 */       if (itemstack.field_77994_a <= count) {
/*  68 */         func_70299_a(i, (ItemStack)null);
/*     */       } else {
/*  70 */         itemstack = itemstack.func_77979_a(count);
/*  71 */         if (itemstack.field_77994_a == 0) {
/*  72 */           func_70299_a(i, (ItemStack)null);
/*     */         }
/*     */       } 
/*     */     }
/*  76 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int i) {
/*  81 */     ItemStack item = func_70301_a(i);
/*  82 */     if (item != null) func_70299_a(i, (ItemStack)null); 
/*  83 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/*  88 */     this.items[i] = itemstack;
/*  89 */     if (itemstack != null && itemstack.field_77994_a > func_70297_j_()) {
/*  90 */       itemstack.field_77994_a = func_70297_j_();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  96 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 106 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 111 */     if (this.field_145850_b == null) {
/* 112 */       return true;
/*     */     }
/* 114 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) != this) {
/* 115 */       return false;
/*     */     }
/* 117 */     return (player.func_70092_e(this.field_145851_c + 0.5D, this.field_145848_d + 0.5D, this.field_145849_e + 0.4D) < 64.0D);
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
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 135 */     return new int[] { 0 };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int slot, ItemStack item, int side) {
/* 140 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int slot, ItemStack item, int side) {
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound compound) {
/* 152 */     NBTTagCompound[] tag = new NBTTagCompound[this.items.length];
/*     */     
/* 154 */     for (int i = 0; i < this.items.length; i++) {
/* 155 */       tag[i] = new NBTTagCompound();
/*     */       
/* 157 */       if (this.items[i] != null) {
/* 158 */         tag[i] = this.items[i].func_77955_b(tag[i]);
/*     */       }
/*     */       
/* 161 */       compound.func_74782_a("Item" + i, (NBTBase)tag[i]);
/*     */     } 
/* 163 */     super.func_145841_b(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound compound) {
/* 168 */     NBTTagCompound[] tag = new NBTTagCompound[this.items.length];
/*     */     
/* 170 */     for (int i = 0; i < this.items.length; i++) {
/* 171 */       tag[i] = compound.func_74775_l("Item" + i);
/* 172 */       this.items[i] = ItemStack.func_77949_a(tag[i]);
/*     */     } 
/* 174 */     super.func_145839_a(compound);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\TileUpgradeModifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */