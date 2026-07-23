/*    */ package com.brandon3055.draconicevolution.common.tileentities.multiblocktiles;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.api.DraconicEvolutionTags;
/*    */ import com.brandon3055.draconicevolution.common.blocks.multiblock.MultiblockHelper;
/*    */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*    */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ 
/*    */ public class TileInvisibleMultiblock
/*    */   extends TileEntity
/*    */ {
/* 17 */   public MultiblockHelper.TileLocation master = new MultiblockHelper.TileLocation();
/*    */ 
/*    */   
/*    */   public boolean canUpdate() {
/* 21 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isMasterOnline() {
/* 25 */     TileEnergyStorageCore tile = (this.field_145850_b.func_147438_o(this.master.getXCoord(), this.master.getYCoord(), this.master.getZCoord()) != null && this.field_145850_b.func_147438_o(this.master.getXCoord(), this.master.getYCoord(), this.master.getZCoord()) instanceof TileEnergyStorageCore) ? (TileEnergyStorageCore)this.field_145850_b.func_147438_o(this.master.getXCoord(), this.master.getYCoord(), this.master.getZCoord()) : null;
/* 26 */     if (tile == null) {
/* 27 */       return false;
/*    */     }
/* 29 */     return tile.online;
/*    */   }
/*    */   
/*    */   public TileEnergyStorageCore getMaster() {
/* 33 */     if (this.master == null) return null; 
/* 34 */     return (this.field_145850_b.func_147438_o(this.master.getXCoord(), this.master.getYCoord(), this.master.getZCoord()) != null && this.field_145850_b.func_147438_o(this.master.getXCoord(), this.master.getYCoord(), this.master.getZCoord()) instanceof TileEnergyStorageCore) ? (TileEnergyStorageCore)this.field_145850_b.func_147438_o(this.master.getXCoord(), this.master.getYCoord(), this.master.getZCoord()) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145841_b(NBTTagCompound compound) {
/* 39 */     super.func_145841_b(compound);
/*    */     
/* 41 */     this.master.writeToNBT(compound, "Key");
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145839_a(NBTTagCompound compound) {
/* 46 */     super.func_145839_a(compound);
/*    */     
/* 48 */     this.master.readFromNBT(compound, "Key");
/*    */   }
/*    */ 
/*    */   
/*    */   public Packet func_145844_m() {
/* 53 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 54 */     func_145841_b(nbttagcompound);
/* 55 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, nbttagcompound);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/* 60 */     func_145839_a(pkt.func_148857_g());
/*    */   }
/*    */   
/*    */   public void isStructureStillValid() {
/* 64 */     if (getMaster() == null) {
/* 65 */       LogHelper.error("{Tile} Master = null reverting!");
/* 66 */       revert();
/*    */       return;
/*    */     } 
/* 69 */     if (!getMaster().isOnline()) revert(); 
/*    */   }
/*    */   
/*    */   private void revert() {
/* 73 */     int meta = this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 74 */     if (meta == 0) {
/* 75 */       DraconicEvolutionTags.Blocks.DRACONIUM.set(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 76 */     } else if (meta == 1) {
/* 77 */       this.field_145850_b.func_147465_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, BalanceConfigHandler.energyStorageStructureBlock, BalanceConfigHandler.energyStorageStructureBlockMetadata, 3);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\multiblocktiles\TileInvisibleMultiblock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */