/*    */ package com.brandon3055.draconicevolution.common.network;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.client.handler.ClientEventHandler;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MountUpdatePacket
/*    */   implements IMessage
/*    */ {
/*    */   public int entityID;
/*    */   
/*    */   public MountUpdatePacket() {}
/*    */   
/*    */   public MountUpdatePacket(int id) {
/* 21 */     this.entityID = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(ByteBuf buf) {
/* 26 */     buf.writeInt(this.entityID);
/*    */   }
/*    */ 
/*    */   
/*    */   public void fromBytes(ByteBuf buf) {
/* 31 */     this.entityID = buf.readInt();
/*    */   }
/*    */   
/*    */   public static class Handler
/*    */     implements IMessageHandler<MountUpdatePacket, IMessage>
/*    */   {
/*    */     public IMessage onMessage(MountUpdatePacket message, MessageContext ctx) {
/* 38 */       if (ctx.side.equals(Side.SERVER)) {
/* 39 */         if (message.entityID == -1) {
/* 40 */           (ctx.getServerHandler()).field_147369_b.func_70078_a(null);
/* 41 */           return null;
/* 42 */         }  if ((ctx.getServerHandler()).field_147369_b.field_70154_o != null) {
/* 43 */           return new MountUpdatePacket((ctx.getServerHandler()).field_147369_b.field_70154_o.func_145782_y());
/*    */         }
/* 45 */         return null;
/*    */       } 
/*    */       
/* 48 */       ClientEventHandler.tryRepositionPlayerOnMount(message.entityID);
/* 49 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\network\MountUpdatePacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */