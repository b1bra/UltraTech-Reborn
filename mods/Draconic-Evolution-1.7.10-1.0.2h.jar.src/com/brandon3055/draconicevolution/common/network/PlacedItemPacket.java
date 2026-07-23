/*    */ package com.brandon3055.draconicevolution.common.network;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TilePlacedItem;
/*    */ import cpw.mods.fml.common.eventhandler.Event;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.common.util.BlockSnapshot;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ import net.minecraftforge.event.world.BlockEvent;
/*    */ 
/*    */ public class PlacedItemPacket implements IMessage {
/* 21 */   byte side = 0;
/* 22 */   int blockX = 0;
/* 23 */   int blockY = 0;
/* 24 */   int blockZ = 0;
/*    */ 
/*    */   
/*    */   public PlacedItemPacket() {}
/*    */   
/*    */   public PlacedItemPacket(byte side, int x, int y, int z) {
/* 30 */     this.side = side;
/* 31 */     this.blockX = x;
/* 32 */     this.blockY = y;
/* 33 */     this.blockZ = z;
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(ByteBuf bytes) {
/* 38 */     bytes.writeByte(this.side);
/* 39 */     bytes.writeInt(this.blockX);
/* 40 */     bytes.writeInt(this.blockY);
/* 41 */     bytes.writeInt(this.blockZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void fromBytes(ByteBuf bytes) {
/* 46 */     this.side = bytes.readByte();
/* 47 */     this.blockX = bytes.readInt();
/* 48 */     this.blockY = bytes.readInt();
/* 49 */     this.blockZ = bytes.readInt();
/*    */   }
/*    */   
/*    */   public static class Handler
/*    */     implements IMessageHandler<PlacedItemPacket, IMessage>
/*    */   {
/*    */     public IMessage onMessage(PlacedItemPacket message, MessageContext ctx) {
/* 56 */       ForgeDirection dir = ForgeDirection.getOrientation(message.side);
/* 57 */       int x = message.blockX + dir.offsetX;
/* 58 */       int y = message.blockY + dir.offsetY;
/* 59 */       int z = message.blockZ + dir.offsetZ;
/* 60 */       World world = (ctx.getServerHandler()).field_147369_b.field_70170_p;
/* 61 */       EntityPlayerMP entityPlayerMP = (ctx.getServerHandler()).field_147369_b;
/*    */       
/* 63 */       if (!world.func_147437_c(x, y, z) || entityPlayerMP.func_70694_bm() == null || !ModBlocks.isEnabled((Block)ModBlocks.placedItem)) {
/* 64 */         return null;
/*    */       }
/* 66 */       BlockEvent.PlaceEvent event = new BlockEvent.PlaceEvent(new BlockSnapshot(world, x, y, z, (Block)ModBlocks.placedItem, 0), world.func_147439_a(message.blockX, message.blockY, message.blockZ), (EntityPlayer)entityPlayerMP);
/* 67 */       MinecraftForge.EVENT_BUS.post((Event)event);
/*    */       
/* 69 */       if (event.isCanceled()) {
/* 70 */         return null;
/*    */       }
/*    */       
/* 73 */       ItemStack stack = entityPlayerMP.func_70694_bm();
/*    */       
/* 75 */       world.func_147465_d(x, y, z, (Block)ModBlocks.placedItem, message.side, 2);
/* 76 */       TilePlacedItem tile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TilePlacedItem) ? (TilePlacedItem)world.func_147438_o(x, y, z) : null;
/*    */       
/* 78 */       if (tile == null) {
/* 79 */         world.func_147468_f(x, y, z);
/* 80 */         return null;
/*    */       } 
/*    */       
/* 83 */       tile.setStack(stack.func_77946_l());
/* 84 */       entityPlayerMP.func_71028_bD();
/* 85 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\network\PlacedItemPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */