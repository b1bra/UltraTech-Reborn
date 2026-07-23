/*     */ package com.brandon3055.draconicevolution.common.utills;
/*     */ 
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ public class EnergyStorage {
/*     */   protected long energy;
/*     */   protected long capacity;
/*     */   protected long maxTransfer;
/*     */   
/*     */   public EnergyStorage(long capacity) {
/*  11 */     this(capacity, capacity);
/*     */   }
/*     */   
/*     */   public EnergyStorage(long capacity, long maxTransfer) {
/*  15 */     this.capacity = capacity;
/*  16 */     this.maxTransfer = maxTransfer;
/*     */   }
/*     */   
/*     */   public EnergyStorage readFromNBT(NBTTagCompound nbt) {
/*  20 */     this.energy = nbt.func_74763_f("EnergyHelper");
/*     */     
/*  22 */     if (this.energy > this.capacity) {
/*  23 */       this.energy = this.capacity;
/*     */     }
/*  25 */     return this;
/*     */   }
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
/*  29 */     if (this.energy < 0L) {
/*  30 */       this.energy = 0L;
/*     */     }
/*  32 */     nbt.func_74772_a("EnergyHelper", this.energy);
/*  33 */     return nbt;
/*     */   }
/*     */   
/*     */   public void setCapacity(long capacity) {
/*  37 */     this.capacity = capacity;
/*     */     
/*  39 */     if (this.energy > capacity) {
/*  40 */       this.energy = capacity;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setMaxTransfer(long maxTransfer) {
/*  45 */     this.maxTransfer = maxTransfer;
/*     */   }
/*     */   
/*     */   public long getMaxTransfer() {
/*  49 */     return this.maxTransfer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnergyStored(long energy) {
/*  59 */     this.energy = energy;
/*     */     
/*  61 */     if (this.energy > this.capacity) {
/*  62 */       this.energy = this.capacity;
/*  63 */     } else if (this.energy < 0L) {
/*  64 */       this.energy = 0L;
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
/*     */   public void modifyEnergyStored(long energy) {
/*  76 */     this.energy += energy;
/*     */     
/*  78 */     if (this.energy > this.capacity) {
/*  79 */       this.energy = this.capacity;
/*  80 */     } else if (this.energy < 0L) {
/*  81 */       this.energy = 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   public long receiveEnergy(long maxReceive, boolean simulate) {
/*  86 */     long energyReceived = Math.min(this.capacity - this.energy, Math.min(this.maxTransfer, maxReceive));
/*  87 */     if (energyReceived <= 0L) {
/*  88 */       return 0L;
/*     */     }
/*     */     
/*  91 */     if (!simulate) {
/*  92 */       this.energy += energyReceived;
/*     */     }
/*     */     
/*  95 */     return energyReceived;
/*     */   }
/*     */   
/*     */   public long extractEnergy(long maxExtract, boolean simulate) {
/*  99 */     return extractEnergyUnchecked(Math.min(maxExtract, this.maxTransfer), simulate);
/*     */   }
/*     */   
/*     */   public long extractEnergyUnchecked(long maxExtract, boolean simulate) {
/* 103 */     long energyExtracted = Math.min(this.energy, maxExtract);
/* 104 */     if (energyExtracted <= 0L) {
/* 105 */       return 0L;
/*     */     }
/*     */     
/* 108 */     if (!simulate) {
/* 109 */       this.energy -= energyExtracted;
/*     */     }
/*     */     
/* 112 */     return energyExtracted;
/*     */   }
/*     */   
/*     */   public long getEnergyStored() {
/* 116 */     return this.energy;
/*     */   }
/*     */   
/*     */   public long getMaxEnergyStored() {
/* 120 */     return this.capacity;
/*     */   }
/*     */   
/*     */   public long getFreeSpace() {
/* 124 */     return (this.energy < this.capacity) ? (this.capacity - this.energy) : 0L;
/*     */   }
/*     */   
/*     */   public long getDemandedEnergy() {
/* 128 */     return Math.min(getFreeSpace(), this.maxTransfer);
/*     */   }
/*     */   
/*     */   public long getOfferedEnergy() {
/* 132 */     return Math.min(getFreeSpace(), this.maxTransfer);
/*     */   }
/*     */   
/*     */   public EnergyStorage readFromNBT(NBTTagCompound nbt, String tag) {
/* 136 */     this.energy = nbt.func_74763_f(tag);
/*     */     
/* 138 */     if (this.energy > this.capacity) {
/* 139 */       this.energy = this.capacity;
/*     */     }
/* 141 */     return this;
/*     */   }
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound nbt, String tag) {
/* 145 */     if (this.energy < 0L) {
/* 146 */       this.energy = 0L;
/*     */     }
/* 148 */     nbt.func_74772_a(tag, this.energy);
/* 149 */     return nbt;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\commo\\utills\EnergyStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */