/*     */ package com.gamerforea.draconicevolution.network;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.DataUtills;
/*     */ import com.brandon3055.draconicevolution.common.container.ContainerDataSync;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileObjectSync;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
/*     */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ public final class TileMultiObjectPacket
/*     */   implements IMessage
/*     */ {
/*     */   int x;
/*     */   int y;
/*     */   int z;
/*     */   boolean isContainerPacket;
/*     */   List<Record> records;
/*     */   
/*     */   public TileMultiObjectPacket() {}
/*     */   
/*     */   public TileMultiObjectPacket(TileObjectSync tile, List<Record> records) {
/*  28 */     this.isContainerPacket = (tile == null);
/*     */     
/*  30 */     if (!this.isContainerPacket) {
/*  31 */       this.x = tile.field_145851_c;
/*  32 */       this.y = tile.field_145848_d;
/*  33 */       this.z = tile.field_145849_e;
/*     */     } 
/*     */     
/*  36 */     this.records = records;
/*     */   }
/*     */ 
/*     */   
/*     */   public void toBytes(ByteBuf buf) {
/*  41 */     buf.writeBoolean(this.isContainerPacket);
/*     */     
/*  43 */     if (!this.isContainerPacket) {
/*  44 */       buf.writeInt(this.x);
/*  45 */       buf.writeInt(this.y);
/*  46 */       buf.writeInt(this.z);
/*     */     } 
/*     */     
/*  49 */     buf.writeByte(this.records.size());
/*     */     
/*  51 */     for (Record record : this.records) {
/*  52 */       buf.writeShort(record.index);
/*  53 */       buf.writeByte(record.dataType);
/*  54 */       DataUtills.instance.writeObjectToBytes(buf, record.dataType, record.object);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fromBytes(ByteBuf buf) {
/*  60 */     this.isContainerPacket = buf.readBoolean();
/*     */     
/*  62 */     if (!this.isContainerPacket) {
/*  63 */       this.x = buf.readInt();
/*  64 */       this.y = buf.readInt();
/*  65 */       this.z = buf.readInt();
/*     */     } 
/*     */     
/*  68 */     int size = buf.readUnsignedByte();
/*  69 */     this.records = new ArrayList<>(size);
/*     */     
/*  71 */     for (int i = 0; i < size; i++) {
/*  72 */       short index = buf.readShort();
/*  73 */       byte dataType = buf.readByte();
/*  74 */       Object object = DataUtills.instance.readObjectFromBytes(buf, dataType);
/*  75 */       this.records.add(new Record(index, (short)dataType, object));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static final class Handler
/*     */     implements IMessageHandler<TileMultiObjectPacket, IMessage> {
/*     */     public IMessage onMessage(TileMultiObjectPacket message, MessageContext ctx) {
/*  82 */       if (message.isContainerPacket) {
/*  83 */         Container c = (Minecraft.func_71410_x()).field_71439_g.field_71070_bA;
/*  84 */         ContainerDataSync container = (c instanceof ContainerDataSync) ? (ContainerDataSync)c : null;
/*  85 */         if (container == null) {
/*  86 */           return null;
/*     */         }
/*  88 */         for (TileMultiObjectPacket.Record record : message.records) {
/*  89 */           container.receiveSyncData(record.index, ((Long)record.object).longValue());
/*     */         }
/*     */       } else {
/*  92 */         TileEntity tile = (Minecraft.func_71410_x()).field_71441_e.func_147438_o(message.x, message.y, message.z);
/*  93 */         if (!(tile instanceof TileObjectSync)) {
/*  94 */           return null;
/*     */         }
/*  96 */         TileObjectSync sync = (TileObjectSync)tile;
/*     */         
/*  98 */         for (TileMultiObjectPacket.Record record : message.records) {
/*  99 */           sync.receiveObjectFromServer(record.index, record.object);
/*     */         }
/*     */       } 
/*     */       
/* 103 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Record {
/*     */     short index;
/*     */     short dataType;
/*     */     Object object;
/*     */     
/*     */     public Record(short index, short dataType, Object object) {
/* 113 */       this.index = index;
/* 114 */       this.dataType = dataType;
/* 115 */       this.object = object;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\gamerforea\draconicevolution\network\TileMultiObjectPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */