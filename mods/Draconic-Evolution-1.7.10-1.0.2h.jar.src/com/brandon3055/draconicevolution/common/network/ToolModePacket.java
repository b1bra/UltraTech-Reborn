/*    */ package com.brandon3055.draconicevolution.common.network;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.items.tools.baseclasses.ToolBase;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ToolModePacket
/*    */   implements IMessage
/*    */ {
/*    */   public boolean shift;
/*    */   public boolean ctrl;
/*    */   
/*    */   public ToolModePacket() {}
/*    */   
/*    */   public ToolModePacket(boolean shift, boolean ctrl) {
/* 21 */     this.shift = shift;
/* 22 */     this.ctrl = ctrl;
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(ByteBuf buf) {
/* 27 */     buf.writeBoolean(this.shift);
/* 28 */     buf.writeBoolean(this.ctrl);
/*    */   }
/*    */ 
/*    */   
/*    */   public void fromBytes(ByteBuf buf) {
/* 33 */     this.shift = buf.readBoolean();
/* 34 */     this.ctrl = buf.readBoolean();
/*    */   }
/*    */   
/*    */   public static class Handler
/*    */     implements IMessageHandler<ToolModePacket, IMessage> {
/*    */     public IMessage onMessage(ToolModePacket message, MessageContext ctx) {
/* 40 */       ToolBase.handleModeChange((ctx.getServerHandler()).field_147369_b.func_70694_bm(), (EntityPlayer)(ctx.getServerHandler()).field_147369_b, message.shift, message.ctrl);
/* 41 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\network\ToolModePacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */