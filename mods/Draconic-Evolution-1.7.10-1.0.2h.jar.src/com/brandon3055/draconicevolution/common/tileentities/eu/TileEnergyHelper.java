/*    */ package com.brandon3055.draconicevolution.common.tileentities.eu;
/*    */ 
/*    */ import cpw.mods.fml.common.eventhandler.Event;
/*    */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*    */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*    */ import ic2.api.energy.tile.IEnergyTile;
/*    */ import ic2.core.IC2;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ 
/*    */ public final class TileEnergyHelper {
/*    */   private final IEnergyTile tile;
/*    */   
/*    */   public TileEnergyHelper(IEnergyTile tile) {
/* 14 */     this.tile = tile;
/*    */   }
/*    */   private boolean addedToEnergyNet;
/*    */   public boolean isRegistered() {
/* 18 */     return this.addedToEnergyNet;
/*    */   }
/*    */   
/*    */   public void registerEnergyNet() {
/* 22 */     if (!this.addedToEnergyNet && IC2.platform.isSimulating()) {
/* 23 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent(this.tile));
/* 24 */       this.addedToEnergyNet = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void unregisterEnergyNet() {
/* 29 */     if (this.addedToEnergyNet && IC2.platform.isSimulating()) {
/* 30 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent(this.tile));
/* 31 */       this.addedToEnergyNet = false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\eu\TileEnergyHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */