/*     */ package com.brandon3055.draconicevolution.common.tileentities.gates;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileObjectSync;
/*     */ import com.brandon3055.draconicevolution.integration.computers.IDEPeripheral;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TileGate
/*     */   extends TileObjectSync
/*     */   implements IDEPeripheral
/*     */ {
/*  18 */   public ForgeDirection output = ForgeDirection.DOWN;
/*  19 */   public int flowRSLow = 0;
/*  20 */   public int flowRSHigh = 1000;
/*  21 */   public int signal = -1;
/*     */   public boolean flowOverridden = false;
/*  23 */   protected int flowOverride = 0;
/*     */   
/*     */   public abstract String getFlowSetting(int paramInt);
/*     */   
/*     */   public abstract void incrementFlow(int paramInt1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt2);
/*     */   
/*     */   public abstract String getToolTip(int paramInt, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   public int getActualFlow() {
/*  32 */     if (this.flowOverridden) return this.flowOverride; 
/*  33 */     if (this.signal == -1)
/*  34 */       this.signal = this.field_145850_b.func_94572_D(this.field_145851_c, this.field_145848_d, this.field_145849_e); 
/*  35 */     return this.flowRSLow + (int)(this.signal / 15.0D * (this.flowRSHigh - this.flowRSLow));
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveObjectFromClient(int index, Object object) {
/*  40 */     if (index == 0) { this.flowRSLow = ((Integer)object).intValue(); }
/*  41 */     else if (index == 1) { this.flowRSHigh = ((Integer)object).intValue(); }
/*  42 */      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveObjectFromServer(int index, Object object) {
/*  47 */     if (index == 2) {
/*  48 */       this.flowOverridden = ((Boolean)object).booleanValue();
/*  49 */     } else if (index == 3) {
/*  50 */       this.flowOverride = ((Integer)object).intValue();
/*  51 */     } else if (index == 4) {
/*  52 */       this.flowRSHigh = ((Integer)object).intValue();
/*  53 */     } else if (index == 5) {
/*  54 */       this.flowRSLow = ((Integer)object).intValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet func_145844_m() {
/*  60 */     NBTTagCompound compound = new NBTTagCompound();
/*  61 */     func_145841_b(compound);
/*  62 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/*  67 */     func_145839_a(pkt.func_148857_g());
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound compound) {
/*  72 */     super.func_145841_b(compound);
/*  73 */     compound.func_74768_a("Output", this.output.ordinal());
/*  74 */     compound.func_74768_a("FlowRSLow", this.flowRSLow);
/*  75 */     compound.func_74768_a("FlowRSHigh", this.flowRSHigh);
/*  76 */     compound.func_74768_a("Signal", this.signal);
/*  77 */     compound.func_74768_a("FlowOverride", this.flowOverride);
/*  78 */     compound.func_74757_a("FlowOverridden", this.flowOverridden);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound compound) {
/*  83 */     super.func_145839_a(compound);
/*  84 */     this.output = ForgeDirection.getOrientation(compound.func_74762_e("Output"));
/*  85 */     this.flowRSLow = compound.func_74762_e("FlowRSLow");
/*  86 */     this.flowRSHigh = compound.func_74762_e("FlowRSHigh");
/*  87 */     this.signal = compound.func_74762_e("Signal");
/*  88 */     this.flowOverride = compound.func_74762_e("FlowOverride");
/*  89 */     this.flowOverridden = compound.func_74767_n("FlowOverridden");
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getMethodNames() {
/*  94 */     return new String[] { "getFlow", "setOverrideEnabled", "getOverrideEnabled", "setFlowOverride", "setSignalHighFlow", "getSignalHighFlow", "setSignalLowFlow", "getSignalLowFlow" };
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] callMethod(String method, Object... args) {
/*  99 */     switch (method) {
/*     */       case "getFlow":
/* 101 */         return new Object[] { Integer.valueOf(getActualFlow()) };
/*     */       case "setOverrideEnabled":
/* 103 */         if (args.length == 0 || !(args[0] instanceof Boolean))
/* 104 */           throw new IllegalArgumentException("Expected Boolean got " + ((args.length == 0) ? "nil" : args[0].getClass().getSimpleName())); 
/* 105 */         this.flowOverridden = ((Boolean)args[0]).booleanValue();
/* 106 */         if (!this.field_145850_b.field_72995_K) sendObjectToClient((byte)6, 2, Boolean.valueOf(this.flowOverridden)); 
/*     */         break;
/*     */       case "getOverrideEnabled":
/* 109 */         return new Object[] { Boolean.valueOf(this.flowOverridden) };
/*     */       case "setFlowOverride":
/* 111 */         if (args.length == 0 || !(args[0] instanceof Number))
/* 112 */           throw new IllegalArgumentException("Expected Number got " + ((args.length == 0) ? "nil" : args[0].getClass().getSimpleName())); 
/* 113 */         this.flowOverride = Utills.toInt(((Double)args[0]).doubleValue());
/* 114 */         if (!this.field_145850_b.field_72995_K) sendObjectToClient((byte)2, 3, Integer.valueOf(this.flowOverride)); 
/*     */         break;
/*     */       case "setSignalHighFlow":
/* 117 */         if (args.length == 0 || !(args[0] instanceof Number))
/* 118 */           throw new IllegalArgumentException("Expected Number got " + ((args.length == 0) ? "nil" : args[0].getClass().getSimpleName())); 
/* 119 */         this.flowRSHigh = Utills.toInt(((Double)args[0]).doubleValue());
/* 120 */         if (!this.field_145850_b.field_72995_K) sendObjectToClient((byte)2, 4, Integer.valueOf(this.flowRSHigh)); 
/*     */         break;
/*     */       case "getSignalHighFlow":
/* 123 */         return new Object[] { Integer.valueOf(this.flowRSHigh) };
/*     */       case "setSignalLowFlow":
/* 125 */         if (args.length == 0 || !(args[0] instanceof Number))
/* 126 */           throw new IllegalArgumentException("Expected Number got " + ((args.length == 0) ? "nil" : args[0].getClass().getSimpleName())); 
/* 127 */         this.flowRSLow = Utills.toInt(((Double)args[0]).doubleValue());
/* 128 */         if (!this.field_145850_b.field_72995_K) sendObjectToClient((byte)2, 5, Integer.valueOf(this.flowRSLow)); 
/*     */         break;
/*     */       case "getSignalLowFlow":
/* 131 */         return new Object[] { Integer.valueOf(this.flowRSLow) };
/*     */     } 
/*     */     
/* 134 */     return new Object[0];
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\gates\TileGate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */