/*     */ package com.brandon3055.draconicevolution.common.network;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.DataUtills;
/*     */ import com.brandon3055.draconicevolution.common.container.ContainerDataSync;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileObjectSync;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
/*     */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileObjectPacket
/*     */   implements IMessage
/*     */ {
/*     */   int x;
/*     */   int y;
/*     */   int z;
/*     */   short index;
/*  24 */   short dataType = -1;
/*     */   
/*     */   Object object;
/*     */   
/*     */   boolean isContainerPacket;
/*     */ 
/*     */   
/*     */   public TileObjectPacket() {}
/*     */ 
/*     */   
/*     */   public TileObjectPacket(TileObjectSync tile, byte dataType, int index, Object object) {
/*  35 */     this.isContainerPacket = (tile == null);
/*  36 */     if (!this.isContainerPacket) {
/*  37 */       this.x = tile.field_145851_c;
/*  38 */       this.y = tile.field_145848_d;
/*  39 */       this.z = tile.field_145849_e;
/*     */     } 
/*  41 */     this.dataType = (short)dataType;
/*  42 */     this.object = object;
/*  43 */     this.index = (short)index;
/*     */   }
/*     */ 
/*     */   
/*     */   public void toBytes(ByteBuf bytes) {
/*  48 */     bytes.writeBoolean(this.isContainerPacket);
/*     */     
/*  50 */     if (!this.isContainerPacket) {
/*  51 */       bytes.writeInt(this.x);
/*  52 */       bytes.writeInt(this.y);
/*  53 */       bytes.writeInt(this.z);
/*     */     } 
/*     */     
/*  56 */     bytes.writeByte(this.dataType);
/*  57 */     bytes.writeShort(this.index);
/*     */     
/*  59 */     DataUtills.instance.writeObjectToBytes(bytes, this.dataType, this.object);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fromBytes(ByteBuf bytes) {
/*  65 */     this.isContainerPacket = bytes.readBoolean();
/*     */     
/*  67 */     if (!this.isContainerPacket) {
/*  68 */       this.x = bytes.readInt();
/*  69 */       this.y = bytes.readInt();
/*  70 */       this.z = bytes.readInt();
/*     */     } 
/*     */     
/*  73 */     this.dataType = (short)bytes.readByte();
/*  74 */     this.index = bytes.readShort();
/*     */     
/*  76 */     this.object = DataUtills.instance.readObjectFromBytes(bytes, this.dataType);
/*     */   }
/*     */   
/*     */   public static class Handler
/*     */     implements IMessageHandler<TileObjectPacket, IMessage>
/*     */   {
/*     */     public IMessage onMessage(TileObjectPacket message, MessageContext ctx) {
/*  83 */       if (ctx.side == Side.CLIENT) {
/*  84 */         if (message.isContainerPacket) {
/*  85 */           ContainerDataSync container = ((Minecraft.func_71410_x()).field_71439_g.field_71070_bA instanceof ContainerDataSync) ? (ContainerDataSync)(Minecraft.func_71410_x()).field_71439_g.field_71070_bA : null;
/*  86 */           if (container != null) {
/*  87 */             container.receiveSyncData(message.index, ((Long)message.object).longValue());
/*     */           }
/*     */         } else {
/*  90 */           TileEntity tile = (Minecraft.func_71410_x()).field_71441_e.func_147438_o(message.x, message.y, message.z);
/*  91 */           if (tile instanceof TileObjectSync) {
/*  92 */             ((TileObjectSync)tile).receiveObjectFromServer(message.index, message.object);
/*     */           }
/*     */         } 
/*     */       } else {
/*  96 */         EntityPlayerMP player = (ctx.getServerHandler()).field_147369_b;
/*  97 */         if (message.isContainerPacket) {
/*  98 */           ContainerDataSync container = (player.field_71070_bA instanceof ContainerDataSync) ? (ContainerDataSync)player.field_71070_bA : null;
/*  99 */           if (container != null) {
/* 100 */             container.receiveSyncData(message.index, ((Long)message.object).longValue());
/*     */           }
/*     */         } else {
/* 103 */           TileEntity tile = player.field_70170_p.func_147438_o(message.x, message.y, message.z);
/* 104 */           if (tile instanceof TileObjectSync) {
/* 105 */             ((TileObjectSync)tile).receiveObjectFromClient(message.index, message.object);
/*     */           }
/*     */         } 
/*     */       } 
/* 109 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\network\TileObjectPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */