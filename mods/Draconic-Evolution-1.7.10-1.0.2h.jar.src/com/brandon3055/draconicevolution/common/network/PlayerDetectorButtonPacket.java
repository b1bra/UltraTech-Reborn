/*    */ package com.brandon3055.draconicevolution.common.network;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.container.ContainerPlayerDetector;
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TilePlayerDetectorAdvanced;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ 
/*    */ public class PlayerDetectorButtonPacket
/*    */   implements IMessage {
/* 12 */   private short index = 0;
/* 13 */   private short value = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerDetectorButtonPacket(byte index, short value) {
/* 19 */     this.index = (short)index;
/* 20 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(ByteBuf bytes) {
/* 25 */     bytes.writeByte(this.index);
/* 26 */     bytes.writeByte(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void fromBytes(ByteBuf bytes) {
/* 31 */     this.index = (short)bytes.readByte();
/* 32 */     this.value = (short)bytes.readByte();
/*    */   }
/*    */   
/*    */   public PlayerDetectorButtonPacket() {}
/*    */   
/*    */   public static class Handler implements IMessageHandler<PlayerDetectorButtonPacket, IMessage> {
/*    */     public IMessage onMessage(PlayerDetectorButtonPacket message, MessageContext ctx) {
/* 39 */       ContainerPlayerDetector container = ((ctx.getServerHandler()).field_147369_b.field_71070_bA instanceof ContainerPlayerDetector) ? (ContainerPlayerDetector)(ctx.getServerHandler()).field_147369_b.field_71070_bA : null;
/* 40 */       TilePlayerDetectorAdvanced tile = (container != null) ? container.getTileDetector() : null;
/*    */       
/* 42 */       if (tile != null) {
/* 43 */         switch (message.index) {
/*    */           case 0:
/* 45 */             tile.range = message.value;
/*    */             break;
/*    */           case 1:
/* 48 */             tile.whiteList = (message.value == 1);
/*    */             break;
/*    */           case 2:
/* 51 */             tile.outputInverted = (message.value == 1);
/*    */             break;
/*    */         } 
/* 54 */         (ctx.getServerHandler()).field_147369_b.field_70170_p.func_147471_g(tile.field_145851_c, tile.field_145848_d, tile.field_145849_e);
/*    */       } 
/* 56 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\network\PlayerDetectorButtonPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */