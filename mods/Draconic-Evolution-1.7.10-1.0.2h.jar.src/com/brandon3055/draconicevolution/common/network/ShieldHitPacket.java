/*    */ package com.brandon3055.draconicevolution.common.network;
/*    */ 
/*    */ import com.brandon3055.brandonscore.common.utills.DataUtills;
/*    */ import com.brandon3055.draconicevolution.client.handler.ClientEventHandler;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShieldHitPacket
/*    */   implements IMessage
/*    */ {
/*    */   public int playerID;
/*    */   public byte shieldPowerB;
/*    */   public float shieldPowerF;
/*    */   
/*    */   public ShieldHitPacket() {}
/*    */   
/*    */   public ShieldHitPacket(EntityPlayer playerHit, float shieldPower) {
/* 25 */     this.playerID = playerHit.func_145782_y();
/* 26 */     this.shieldPowerB = (byte)(int)(shieldPower * 127.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void fromBytes(ByteBuf bytes) {
/* 31 */     this.playerID = bytes.readInt();
/* 32 */     this.shieldPowerB = bytes.readByte();
/* 33 */     this.shieldPowerF = this.shieldPowerB / 127.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(ByteBuf bytes) {
/* 38 */     bytes.writeInt(this.playerID);
/* 39 */     bytes.writeByte(this.shieldPowerB);
/*    */   }
/*    */   
/*    */   public static class Handler
/*    */     implements IMessageHandler<ShieldHitPacket, IMessage>
/*    */   {
/*    */     public IMessage onMessage(ShieldHitPacket message, MessageContext ctx) {
/* 46 */       Entity entity = (Minecraft.func_71410_x()).field_71441_e.func_73045_a(message.playerID);
/* 47 */       if (entity instanceof EntityPlayer)
/* 48 */         ClientEventHandler.playerShieldStatus.put((EntityPlayer)entity, new DataUtills.XZPair(Float.valueOf(message.shieldPowerF), Integer.valueOf(ClientEventHandler.elapsedTicks))); 
/* 49 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\network\ShieldHitPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */