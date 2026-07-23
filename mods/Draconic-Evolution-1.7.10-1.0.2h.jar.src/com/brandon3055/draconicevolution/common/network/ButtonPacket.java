/*    */ package com.brandon3055.draconicevolution.common.network;
/*    */ 
/*    */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*    */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*    */ import com.brandon3055.draconicevolution.common.container.ContainerDissEnchanter;
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileDissEnchanter;
/*    */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ButtonPacket implements IMessage {
/*    */   public static final byte ID_DISSENCHANTER = 1;
/*    */   public static final byte ID_TOOLCONFIG = 7;
/*    */   public static final byte ID_TOOL_PROFILE_CHANGE = 9;
/* 20 */   byte buttonId = 0;
/*    */   
/*    */   boolean state = false;
/*    */   
/*    */   public ButtonPacket() {}
/*    */   
/*    */   public ButtonPacket(byte buttonId, boolean state) {
/* 27 */     this.buttonId = buttonId;
/* 28 */     this.state = state;
/*    */   }
/*    */ 
/*    */   
/*    */   public void fromBytes(ByteBuf bytes) {
/* 33 */     this.buttonId = bytes.readByte();
/* 34 */     this.state = bytes.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(ByteBuf bytes) {
/* 39 */     bytes.writeByte(this.buttonId);
/* 40 */     bytes.writeBoolean(this.state);
/*    */   }
/*    */   
/*    */   public static class Handler implements IMessageHandler<ButtonPacket, IMessage> {
/*    */     public IMessage onMessage(ButtonPacket message, MessageContext ctx) {
/*    */       Container container;
/*    */       ItemStack stack;
/* 47 */       switch (message.buttonId) {
/*    */         case 1:
/* 49 */           container = (ctx.getServerHandler()).field_147369_b.field_71070_bA;
/* 50 */           if (container instanceof ContainerDissEnchanter) {
/* 51 */             TileDissEnchanter tile = ((ContainerDissEnchanter)container).getTile();
/* 52 */             tile.buttonClick((EntityPlayer)(ctx.getServerHandler()).field_147369_b);
/*    */           } 
/*    */           break;
/*    */         case 7:
/* 56 */           (ctx.getServerHandler()).field_147369_b.openGui(DraconicEvolution.instance, 12, (ctx.getServerHandler()).field_147369_b.field_70170_p, (int)(ctx.getServerHandler()).field_147369_b.field_70165_t, (int)(ctx.getServerHandler()).field_147369_b.field_70163_u, (int)(ctx.getServerHandler()).field_147369_b.field_70161_v);
/*    */           break;
/*    */         case 9:
/* 59 */           stack = (ctx.getServerHandler()).field_147369_b.func_70694_bm();
/* 60 */           if (stack != null && stack.func_77973_b() instanceof IConfigurableItem && ((IConfigurableItem)stack.func_77973_b()).hasProfiles()) {
/* 61 */             int preset = ItemNBTHelper.getInteger(stack, "ConfigProfile", 0);
/* 62 */             if (++preset >= 5) preset = 0; 
/* 63 */             ItemNBTHelper.setInteger(stack, "ConfigProfile", preset);
/*    */           } 
/*    */           break;
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 70 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\network\ButtonPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */