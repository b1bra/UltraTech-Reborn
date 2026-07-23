/*     */ package com.brandon3055.draconicevolution.common.tileentities;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class TileCustomSpawner extends TileEntity {
/*     */   public boolean isSetToSpawn = false;
/*     */   public EntityPlayer owner;
/*     */   
/*  16 */   private final CustomSpawnerBaseLogic spawnerBaseLogic = new CustomSpawnerBaseLogic()
/*     */     {
/*     */       public void blockEvent(int par1) {
/*  19 */         TileCustomSpawner.this.field_145850_b.func_147452_c(TileCustomSpawner.this.field_145851_c, TileCustomSpawner.this.field_145848_d, TileCustomSpawner.this.field_145849_e, Blocks.field_150474_ac, par1, 0);
/*     */       }
/*     */ 
/*     */       
/*     */       public World getSpawnerWorld() {
/*  24 */         return TileCustomSpawner.this.field_145850_b;
/*     */       }
/*     */ 
/*     */       
/*     */       public int getSpawnerX() {
/*  29 */         return TileCustomSpawner.this.field_145851_c;
/*     */       }
/*     */ 
/*     */       
/*     */       public int getSpawnerY() {
/*  34 */         return TileCustomSpawner.this.field_145848_d;
/*     */       }
/*     */ 
/*     */       
/*     */       public int getSpawnerZ() {
/*  39 */         return TileCustomSpawner.this.field_145849_e;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  45 */     if (this.isSetToSpawn) {
/*  46 */       this.spawnerBaseLogic.updateSpawner();
/*     */     }
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
/*     */   public void func_145841_b(NBTTagCompound tagCompound) {
/*  92 */     super.func_145841_b(tagCompound);
/*  93 */     this.spawnerBaseLogic.writeToNBT(tagCompound);
/*  94 */     tagCompound.func_74757_a("Running", this.isSetToSpawn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound tagCompound) {
/*  99 */     super.func_145839_a(tagCompound);
/* 100 */     this.spawnerBaseLogic.readFromNBT(tagCompound);
/* 101 */     this.isSetToSpawn = tagCompound.func_74767_n("Running");
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet func_145844_m() {
/* 106 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 107 */     func_145841_b(nbttagcompound);
/* 108 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, nbttagcompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/* 113 */     func_145839_a(pkt.func_148857_g());
/*     */   }
/*     */   
/*     */   public CustomSpawnerBaseLogic getBaseLogic() {
/* 117 */     return this.spawnerBaseLogic;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\TileCustomSpawner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */