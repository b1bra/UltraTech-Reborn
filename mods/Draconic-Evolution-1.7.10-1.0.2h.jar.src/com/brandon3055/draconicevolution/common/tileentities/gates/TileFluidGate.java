/*     */ package com.brandon3055.draconicevolution.common.tileentities.gates;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTankInfo;
/*     */ import net.minecraftforge.fluids.IFluidHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileFluidGate
/*     */   extends TileGate
/*     */   implements IFluidHandler
/*     */ {
/*     */   private boolean executing;
/*     */   
/*     */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/*  20 */     if (this.executing || resource == null || resource.amount <= 0) {
/*  21 */       return 0;
/*     */     }
/*  23 */     this.executing = true;
/*     */     
/*     */     try {
/*  26 */       IFluidHandler target = getOutputTarget();
/*  27 */       if (target == null) {
/*  28 */         return 0;
/*     */       }
/*  30 */       int transfer = Math.min(getActualFlow(), target.fill(from, resource, false));
/*  31 */       if (transfer <= 0) {
/*  32 */         return 0;
/*     */       }
/*  34 */       if (transfer < resource.amount) {
/*  35 */         FluidStack newStack = resource.copy();
/*  36 */         newStack.amount = transfer;
/*  37 */         resource.amount -= transfer;
/*  38 */         return target.fill(from, newStack, doFill);
/*     */       } 
/*     */       
/*  41 */       return target.fill(from, resource, doFill);
/*     */     } finally {
/*  43 */       this.executing = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/*  49 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
/*  54 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/*  59 */     if (this.executing) {
/*  60 */       return false;
/*     */     }
/*  62 */     this.executing = true;
/*     */     
/*     */     try {
/*  65 */       IFluidHandler target = getOutputTarget();
/*  66 */       return (target != null && target.canFill(from, fluid));
/*     */     } finally {
/*  68 */       this.executing = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
/*  79 */     return new FluidTankInfo[0];
/*     */   }
/*     */   
/*     */   private IFluidHandler getOutputTarget() {
/*  83 */     ForgeDirection side = this.output;
/*  84 */     if (side == null || side == ForgeDirection.UNKNOWN) {
/*  85 */       return null;
/*     */     }
/*  87 */     TileEntity tile = this.field_145850_b.func_147438_o(this.field_145851_c + side.offsetX, this.field_145848_d + side.offsetY, this.field_145849_e + side.offsetZ);
/*  88 */     return (tile instanceof IFluidHandler) ? (IFluidHandler)tile : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFlowSetting(int selector) {
/*  93 */     return (selector == 0) ? (Utills.addCommas(this.flowRSLow) + " MB/t") : (Utills.addCommas(this.flowRSHigh) + " MB/t");
/*     */   }
/*     */ 
/*     */   
/*     */   public void incrementFlow(int selector, boolean ctrl, boolean shift, boolean add, int button) {
/*  98 */     int amount = (button == 0) ? (shift ? (ctrl ? 1000 : 100) : (ctrl ? 50 : 5)) : (shift ? (ctrl ? 100 : 50) : (ctrl ? 10 : 1));
/*  99 */     if (selector == 0) {
/* 100 */       this.flowRSLow += add ? amount : -amount;
/* 101 */       if (this.flowRSLow < 0) this.flowRSLow = 0; 
/* 102 */       if (this.field_145850_b.field_72995_K) sendObjectToServer((byte)2, 0, Integer.valueOf(this.flowRSLow)); 
/*     */     } else {
/* 104 */       this.flowRSHigh += add ? amount : -amount;
/* 105 */       if (this.flowRSHigh < 0) this.flowRSHigh = 0; 
/* 106 */       if (this.field_145850_b.field_72995_K) sendObjectToServer((byte)2, 1, Integer.valueOf(this.flowRSHigh));
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getToolTip(int selector, boolean shift, boolean ctrl) {
/* 112 */     int b1 = shift ? (ctrl ? 1000 : 100) : (ctrl ? 50 : 5);
/* 113 */     int b2 = shift ? (ctrl ? 100 : 50) : (ctrl ? 10 : 1);
/* 114 */     return b1 + "/" + b2 + " MB/t";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 119 */     return "fluid_gate";
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\gates\TileFluidGate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */