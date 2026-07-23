/*    */ package com.brandon3055.draconicevolution.common.network;
/*    */ 
/*    */ import com.brandon3055.brandonscore.BrandonsCore;
/*    */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*    */ import com.brandon3055.draconicevolution.common.handler.MinecraftForgeEventHandler;
/*    */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ 
/*    */ 
/*    */ public class SpeedRequestPacket
/*    */   implements IMessage
/*    */ {
/* 17 */   double speed = 0.0D;
/*    */ 
/*    */   
/*    */   public SpeedRequestPacket() {}
/*    */   
/*    */   public SpeedRequestPacket(double speed) {
/* 23 */     this.speed = speed;
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(ByteBuf bytes) {
/* 28 */     bytes.writeDouble(this.speed);
/*    */   }
/*    */ 
/*    */   
/*    */   public void fromBytes(ByteBuf bytes) {
/* 33 */     this.speed = bytes.readDouble();
/*    */   }
/*    */   
/*    */   public static class Handler
/*    */     implements IMessageHandler<SpeedRequestPacket, IMessage>
/*    */   {
/*    */     public IMessage onMessage(SpeedRequestPacket message, MessageContext ctx) {
/* 40 */       if (ctx.side == Side.SERVER) {
/* 41 */         if (ConfigHandler.speedLimitDimList.contains(Integer.valueOf((ctx.getServerHandler()).field_147369_b.field_71093_bK)) || (BrandonsCore.proxy.isOp((ctx.getServerHandler()).field_147369_b.func_70005_c_()) && !ConfigHandler.speedLimitops))
/* 42 */           return new SpeedRequestPacket(20.0D); 
/* 43 */         return new SpeedRequestPacket(ConfigHandler.maxPlayerSpeed);
/*    */       } 
/* 45 */       MinecraftForgeEventHandler.maxSpeed = message.speed;
/* 46 */       MinecraftForgeEventHandler.ticksSinceRequest = 0;
/* 47 */       MinecraftForgeEventHandler.speedNeedsUpdating = false;
/* 48 */       LogHelper.info("Server speed is set to " + message.speed);
/*    */       
/* 50 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\network\SpeedRequestPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */