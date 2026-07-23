/*    */ package com.brandon3055.draconicevolution.common.tileentities.eu;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileObjectSync;
/*    */ import ic2.api.energy.tile.IEnergySink;
/*    */ import ic2.api.energy.tile.IEnergyTile;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ public abstract class TileEnergyBase extends TileObjectSync implements IEnergyTile {
/* 10 */   private final TileEnergyHelper helper = new TileEnergyHelper(this);
/*    */   
/*    */   protected final void registerEnergyNet() {
/* 13 */     this.helper.registerEnergyNet();
/*    */   }
/*    */   
/*    */   protected final void unregisterEnergyNet() {
/* 17 */     this.helper.unregisterEnergyNet();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145845_h() {
/* 22 */     registerEnergyNet();
/* 23 */     super.func_145845_h();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145843_s() {
/* 28 */     setAddedToChunk(false); unregisterEnergyNet();
/* 29 */     super.func_145843_s();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onChunkUnload() {
/* 34 */     setAddedToChunk(false); unregisterEnergyNet();
/* 35 */     super.onChunkUnload();
/*    */   }
/*    */   
/*    */   public static abstract class Sink
/*    */     extends TileEnergyBase implements IEnergySink {
/*    */     public int getSinkTier() {
/* 41 */       return Integer.MAX_VALUE;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection side) {
/* 46 */       return true;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\eu\TileEnergyBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */