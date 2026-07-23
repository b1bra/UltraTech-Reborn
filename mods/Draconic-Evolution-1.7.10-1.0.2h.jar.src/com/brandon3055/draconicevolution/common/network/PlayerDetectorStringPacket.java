/*    */ package com.brandon3055.draconicevolution.common.network;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.container.ContainerPlayerDetector;
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TilePlayerDetectorAdvanced;
/*    */ import cpw.mods.fml.common.network.ByteBufUtils;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ 
/*    */ public class PlayerDetectorStringPacket
/*    */   implements IMessage {
/* 13 */   private int index = 0;
/* 14 */   private String name = "";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerDetectorStringPacket(byte index, String name) {
/* 20 */     this.index = index;
/* 21 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(ByteBuf bytes) {
/* 26 */     bytes.writeByte(this.index);
/* 27 */     ByteBufUtils.writeUTF8String(bytes, this.name);
/*    */   }
/*    */ 
/*    */   
/*    */   public void fromBytes(ByteBuf bytes) {
/* 32 */     this.index = bytes.readByte();
/* 33 */     this.name = ByteBufUtils.readUTF8String(bytes);
/*    */   }
/*    */   
/*    */   public PlayerDetectorStringPacket() {}
/*    */   
/*    */   public static class Handler implements IMessageHandler<PlayerDetectorStringPacket, IMessage> {
/*    */     public IMessage onMessage(PlayerDetectorStringPacket message, MessageContext ctx) {
/* 40 */       ContainerPlayerDetector container = ((ctx.getServerHandler()).field_147369_b.field_71070_bA instanceof ContainerPlayerDetector) ? (ContainerPlayerDetector)(ctx.getServerHandler()).field_147369_b.field_71070_bA : null;
/* 41 */       TilePlayerDetectorAdvanced tile = (container != null) ? container.getTileDetector() : null;
/*    */       
/* 43 */       if (tile != null) {
/* 44 */         tile.names[message.index] = message.name;
/* 45 */         (ctx.getServerHandler()).field_147369_b.field_70170_p.func_147471_g(tile.field_145851_c, tile.field_145848_d, tile.field_145849_e);
/*    */       } 
/* 47 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\network\PlayerDetectorStringPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */