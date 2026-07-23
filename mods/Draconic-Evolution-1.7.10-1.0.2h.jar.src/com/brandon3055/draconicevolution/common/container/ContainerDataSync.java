/*    */ package com.brandon3055.draconicevolution.common.container;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*    */ import com.brandon3055.draconicevolution.common.network.TileObjectPacket;
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileObjectSync;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.inventory.Container;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ContainerDataSync
/*    */   extends Container
/*    */ {
/*    */   public int sendObjectToClient(TileObjectSync tile, int index, int value) {
/* 20 */     sendObjectToClient(tile, index, value);
/* 21 */     return value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long sendObjectToClient(TileObjectSync tile, int index, long value) {
/* 30 */     for (Object p : this.field_75149_d) {
/* 31 */       DraconicEvolution.network.sendTo((IMessage)new TileObjectPacket(tile, (byte)3, index, Long.valueOf(value)), (EntityPlayerMP)p);
/*    */     }
/* 33 */     return value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public long sendObjectToServer(TileObjectSync tile, int index, long value) {
/* 43 */     DraconicEvolution.network.sendToServer((IMessage)new TileObjectPacket(tile, (byte)3, index, Long.valueOf(value)));
/* 44 */     return value;
/*    */   }
/*    */   
/*    */   public abstract void receiveSyncData(int paramInt, long paramLong);
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\container\ContainerDataSync.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */