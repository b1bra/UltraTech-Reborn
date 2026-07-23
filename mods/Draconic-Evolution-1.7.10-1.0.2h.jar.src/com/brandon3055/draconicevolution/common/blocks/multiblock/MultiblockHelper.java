/*     */ package com.brandon3055.draconicevolution.common.blocks.multiblock;
/*     */ 
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ChunkCoordinates;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiblockHelper
/*     */ {
/*     */   public static class TileLocation
/*     */     extends ChunkCoordinates
/*     */   {
/*     */     public boolean initialized = false;
/*     */     
/*     */     public TileLocation() {}
/*     */     
/*     */     public TileLocation(int x, int y, int z) {
/*  22 */       this.field_71574_a = x;
/*  23 */       this.field_71572_b = y;
/*  24 */       this.field_71573_c = z;
/*  25 */       this.initialized = true;
/*     */     }
/*     */     
/*     */     public int getXCoord() {
/*  29 */       return this.field_71574_a;
/*     */     }
/*     */     
/*     */     public int getYCoord() {
/*  33 */       return this.field_71572_b;
/*     */     }
/*     */     
/*     */     public int getZCoord() {
/*  37 */       return this.field_71573_c;
/*     */     }
/*     */     
/*     */     public boolean isThisLocation(int x, int y, int z) {
/*  41 */       return (x == this.field_71574_a && y == this.field_71572_b && z == this.field_71573_c);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_71571_b(int x, int y, int z) {
/*  46 */       this.initialized = true;
/*  47 */       super.func_71571_b(x, y, z);
/*     */     }
/*     */     
/*     */     public void setXCoord(int x) {
/*  51 */       this.field_71574_a = x;
/*  52 */       this.initialized = true;
/*     */     }
/*     */     
/*     */     public void setYCoord(int y) {
/*  56 */       this.field_71572_b = y;
/*  57 */       this.initialized = true;
/*     */     }
/*     */     
/*     */     public void setZCoord(int z) {
/*  61 */       this.field_71573_c = z;
/*  62 */       this.initialized = true;
/*     */     }
/*     */     
/*     */     public TileEntity getTileEntity(World world) {
/*  66 */       return world.func_147438_o(this.field_71574_a, this.field_71572_b, this.field_71573_c);
/*     */     }
/*     */     
/*     */     public void writeToNBT(NBTTagCompound compound, String key) {
/*  70 */       compound.func_74768_a("X_" + key, this.field_71574_a);
/*  71 */       compound.func_74768_a("Y_" + key, this.field_71572_b);
/*  72 */       compound.func_74768_a("Z_" + key, this.field_71573_c);
/*  73 */       compound.func_74757_a("Init_" + key, this.initialized);
/*     */     }
/*     */     
/*     */     public void readFromNBT(NBTTagCompound compound, String key) {
/*  77 */       this.field_71574_a = compound.func_74762_e("X_" + key);
/*  78 */       this.field_71572_b = compound.func_74762_e("Y_" + key);
/*  79 */       this.field_71573_c = compound.func_74762_e("Z_" + key);
/*  80 */       this.initialized = compound.func_74767_n("Z_" + key);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class TileOffset
/*     */   {
/*     */     public int offsetX;
/*     */     public int offsetY;
/*     */     public int offsetZ;
/*     */     
/*     */     public TileOffset() {}
/*     */     
/*     */     public TileOffset(int x, int y, int z, int offsetX, int offsetY, int offsetZ) {
/*  93 */       this.offsetX = x - offsetX;
/*  94 */       this.offsetY = y - offsetY;
/*  95 */       this.offsetZ = z - offsetZ;
/*     */     }
/*     */     
/*     */     public TileOffset(TileEntity tile1, TileEntity offsetTile) {
/*  99 */       this.offsetX = tile1.field_145851_c - offsetTile.field_145851_c;
/* 100 */       this.offsetY = tile1.field_145848_d - offsetTile.field_145848_d;
/* 101 */       this.offsetZ = tile1.field_145849_e - offsetTile.field_145849_e;
/*     */     }
/*     */     
/*     */     public int getXCoord(TileEntity tileEntity) {
/* 105 */       return tileEntity.field_145851_c - this.offsetX;
/*     */     }
/*     */     
/*     */     public int getYCoord(TileEntity tileEntity) {
/* 109 */       return tileEntity.field_145848_d - this.offsetY;
/*     */     }
/*     */     
/*     */     public int getZCoord(TileEntity tileEntity) {
/* 113 */       return tileEntity.field_145849_e - this.offsetZ;
/*     */     }
/*     */     
/*     */     public int getXCoord(int xCoord) {
/* 117 */       return xCoord - this.offsetX;
/*     */     }
/*     */     
/*     */     public int getYCoord(int yCoord) {
/* 121 */       return yCoord - this.offsetY;
/*     */     }
/*     */     
/*     */     public int getZCoord(int zCoord) {
/* 125 */       return zCoord - this.offsetZ;
/*     */     }
/*     */     
/*     */     public TileEntity getTileEntity(TileEntity tileEntity) {
/* 129 */       return tileEntity.func_145831_w().func_147438_o(getXCoord(tileEntity), getYCoord(tileEntity), getZCoord(tileEntity));
/*     */     }
/*     */     
/*     */     public void writeToNBT(NBTTagCompound compound, String key) {
/* 133 */       compound.func_74768_a("X_" + key, this.offsetX);
/* 134 */       compound.func_74768_a("Y_" + key, this.offsetY);
/* 135 */       compound.func_74768_a("Z_" + key, this.offsetZ);
/*     */     }
/*     */     
/*     */     public void readFromNBT(NBTTagCompound compound, String key) {
/* 139 */       this.offsetX = compound.func_74762_e("X_" + key);
/* 140 */       this.offsetY = compound.func_74762_e("Y_" + key);
/* 141 */       this.offsetZ = compound.func_74762_e("Z_" + key);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\multiblock\MultiblockHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */