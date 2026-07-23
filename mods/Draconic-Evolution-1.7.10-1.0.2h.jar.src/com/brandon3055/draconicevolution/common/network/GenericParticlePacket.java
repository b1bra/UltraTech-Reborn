/*    */ package com.brandon3055.draconicevolution.common.network;
/*    */ 
/*    */ import com.brandon3055.brandonscore.BrandonsCore;
/*    */ import com.brandon3055.draconicevolution.client.handler.ParticleHandler;
/*    */ import com.brandon3055.draconicevolution.client.render.particle.Particles;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.client.particle.EntityFX;
/*    */ 
/*    */ public class GenericParticlePacket implements IMessage {
/*    */   public static final byte ARROW_SHOCK_WAVE = 4;
/* 16 */   private byte particleId = 0;
/*    */   
/*    */   private double posX;
/*    */   
/*    */   private double posY;
/*    */   
/*    */   private double posZ;
/*    */   private int additionalData;
/*    */   
/*    */   public GenericParticlePacket(byte particleId, double posX, double posY, double posZ) {
/* 26 */     this.particleId = particleId;
/* 27 */     this.posX = posX;
/* 28 */     this.posY = posY;
/* 29 */     this.posZ = posZ;
/*    */   }
/*    */   
/*    */   public GenericParticlePacket(byte particleId, double posX, double posY, double posZ, int aditionalData) {
/* 33 */     this.particleId = particleId;
/* 34 */     this.posX = posX;
/* 35 */     this.posY = posY;
/* 36 */     this.posZ = posZ;
/* 37 */     this.additionalData = aditionalData;
/*    */   }
/*    */ 
/*    */   
/*    */   public void fromBytes(ByteBuf bytes) {
/* 42 */     this.particleId = bytes.readByte();
/* 43 */     this.posX = bytes.readFloat();
/* 44 */     this.posY = bytes.readFloat();
/* 45 */     this.posZ = bytes.readFloat();
/*    */     
/* 47 */     if (this.particleId == 4) {
/* 48 */       this.additionalData = bytes.readInt();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(ByteBuf bytes) {
/* 54 */     bytes.writeByte(this.particleId);
/* 55 */     bytes.writeFloat((float)this.posX);
/* 56 */     bytes.writeFloat((float)this.posY);
/* 57 */     bytes.writeFloat((float)this.posZ);
/*    */     
/* 59 */     if (this.particleId == 4)
/* 60 */       bytes.writeInt(this.additionalData); 
/*    */   }
/*    */   
/*    */   public GenericParticlePacket() {}
/*    */   
/*    */   public static class Handler
/*    */     implements IMessageHandler<GenericParticlePacket, IMessage> {
/*    */     @SideOnly(Side.CLIENT)
/*    */     public IMessage onMessage(GenericParticlePacket message, MessageContext ctx) {
/* 69 */       if (message.particleId == 4) {
/* 70 */         ParticleHandler.spawnCustomParticle((EntityFX)new Particles.ArrowShockParticle(BrandonsCore.proxy.getClientWorld(), message.posX, message.posY, message.posZ, message.additionalData), 256.0D);
/* 71 */         for (int i = 0; i < 100; i++) {
/* 72 */           Particles.ArrowParticle particle = new Particles.ArrowParticle(BrandonsCore.proxy.getClientWorld(), message.posX - 0.25D + (BrandonsCore.proxy.getClientWorld()).field_73012_v.nextDouble() * 0.5D, message.posY + (BrandonsCore.proxy.getClientWorld()).field_73012_v.nextDouble() * 0.5D, message.posZ - 0.25D + (BrandonsCore.proxy.getClientWorld()).field_73012_v.nextDouble() * 0.5D, 16736256, 0.2F + (BrandonsCore.proxy.getClientWorld()).field_73012_v.nextFloat() * 10.0F);
/*    */           
/* 74 */           double mm = 2.0D;
/* 75 */           particle.field_70159_w = ((BrandonsCore.proxy.getClientWorld()).field_73012_v.nextDouble() - 0.5D) * mm;
/* 76 */           particle.field_70181_x = ((BrandonsCore.proxy.getClientWorld()).field_73012_v.nextDouble() - 0.5D) * mm;
/* 77 */           particle.field_70179_y = ((BrandonsCore.proxy.getClientWorld()).field_73012_v.nextDouble() - 0.5D) * mm;
/* 78 */           ParticleHandler.spawnCustomParticle((EntityFX)particle, 64.0D);
/*    */         } 
/*    */       } 
/* 81 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\network\GenericParticlePacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */