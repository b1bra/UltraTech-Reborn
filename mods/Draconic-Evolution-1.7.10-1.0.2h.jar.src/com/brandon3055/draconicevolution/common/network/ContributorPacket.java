/*    */ package com.brandon3055.draconicevolution.common.network;
/*    */ 
/*    */ import cpw.mods.fml.common.network.ByteBufUtils;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ 
/*    */ public class ContributorPacket
/*    */   implements IMessage {
/*    */   public String contributor;
/*    */   public boolean wings;
/*    */   public boolean badge;
/*    */   
/*    */   public ContributorPacket() {}
/*    */   
/*    */   public ContributorPacket(String contributor, boolean wings, boolean badge) {
/* 18 */     this.contributor = contributor;
/* 19 */     this.wings = wings;
/* 20 */     this.badge = badge;
/*    */   }
/*    */ 
/*    */   
/*    */   public void fromBytes(ByteBuf bytes) {
/* 25 */     this.contributor = ByteBufUtils.readUTF8String(bytes);
/* 26 */     this.wings = bytes.readBoolean();
/* 27 */     this.badge = bytes.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(ByteBuf bytes) {
/* 32 */     ByteBufUtils.writeUTF8String(bytes, this.contributor);
/* 33 */     bytes.writeBoolean(this.wings);
/* 34 */     bytes.writeBoolean(this.badge);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Handler
/*    */     implements IMessageHandler<ContributorPacket, IMessage>
/*    */   {
/*    */     public IMessage onMessage(ContributorPacket message, MessageContext ctx) {
/* 66 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\network\ContributorPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */