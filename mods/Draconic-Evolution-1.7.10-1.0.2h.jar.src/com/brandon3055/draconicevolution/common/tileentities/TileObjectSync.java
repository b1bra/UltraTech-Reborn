/*    */ package com.brandon3055.draconicevolution.common.tileentities;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*    */ import com.brandon3055.draconicevolution.common.network.TileObjectPacket;
/*    */ import cpw.mods.fml.common.network.NetworkRegistry;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TileObjectSync
/*    */   extends TileEntity
/*    */ {
/*    */   public Object sendObjectToClient(byte dataType, int index, Object object) {
/* 17 */     return sendObjectToClient(dataType, index, object, new NetworkRegistry.TargetPoint(this.field_145850_b.field_73011_w.field_76574_g, this.field_145851_c, this.field_145848_d, this.field_145849_e, 64.0D));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object sendObjectToClient(byte dataType, int index, Object object, NetworkRegistry.TargetPoint point) {
/* 24 */     DraconicEvolution.network.sendToAllAround((IMessage)new TileObjectPacket(this, dataType, index, object), point);
/* 25 */     return object;
/*    */   }
/*    */   
/*    */   public Object sendObjectToServer(byte dataType, int index, Object object) {
/* 29 */     DraconicEvolution.network.sendToServer((IMessage)new TileObjectPacket(this, dataType, index, object));
/* 30 */     return object;
/*    */   }
/*    */   
/*    */   public void receiveObjectFromClient(int index, Object object) {}
/*    */   
/*    */   public void receiveObjectFromServer(int index, Object object) {}
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\TileObjectSync.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */