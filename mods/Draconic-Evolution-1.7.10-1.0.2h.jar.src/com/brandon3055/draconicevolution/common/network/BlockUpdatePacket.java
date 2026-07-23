/*    */ package com.brandon3055.draconicevolution.common.network;
/*    */ 
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*    */ 
/*    */ public class BlockUpdatePacket
/*    */   implements IMessage {
/*    */   int x;
/*    */   int y;
/*    */   
/*    */   public BlockUpdatePacket(int x, int y, int z) {
/* 16 */     this.x = x;
/* 17 */     this.y = y;
/* 18 */     this.z = z;
/*    */   }
/*    */   int z;
/*    */   public BlockUpdatePacket() {}
/*    */   public void fromBytes(ByteBuf bytes) {
/* 23 */     this.x = bytes.readInt();
/* 24 */     this.y = bytes.readInt();
/* 25 */     this.z = bytes.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(ByteBuf bytes) {
/* 30 */     bytes.writeInt(this.x);
/* 31 */     bytes.writeInt(this.y);
/* 32 */     bytes.writeInt(this.z);
/*    */   }
/*    */   
/*    */   public static class Handler
/*    */     implements IMessageHandler<BlockUpdatePacket, IMessage>
/*    */   {
/*    */     public IMessage onMessage(BlockUpdatePacket message, MessageContext ctx) {
/* 39 */       (ctx.getServerHandler()).field_147369_b.field_71135_a.func_147359_a((Packet)new S23PacketBlockChange(message.x, message.y, message.z, (ctx.getServerHandler()).field_147369_b.field_70170_p));
/* 40 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\network\BlockUpdatePacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */