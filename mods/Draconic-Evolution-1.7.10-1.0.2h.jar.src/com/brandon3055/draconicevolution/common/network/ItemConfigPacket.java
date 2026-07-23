/*    */ package com.brandon3055.draconicevolution.common.network;
/*    */ 
/*    */ import com.brandon3055.brandonscore.common.utills.DataUtills;
/*    */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*    */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*    */ import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
/*    */ import cpw.mods.fml.common.network.ByteBufUtils;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*    */ import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemConfigPacket
/*    */   implements IMessage
/*    */ {
/*    */   public byte datatype;
/*    */   public int slot;
/*    */   public Object value;
/*    */   public String name;
/*    */   public boolean renameProfile = false;
/*    */   
/*    */   public ItemConfigPacket() {}
/*    */   
/*    */   public ItemConfigPacket(ItemConfigField field) {
/* 29 */     this.datatype = (byte)field.datatype;
/* 30 */     this.slot = field.slot;
/* 31 */     this.value = field.value;
/* 32 */     this.name = field.name;
/*    */   }
/*    */   
/*    */   public ItemConfigPacket(int slot, String name) {
/* 36 */     this.datatype = 6;
/* 37 */     this.slot = slot;
/* 38 */     this.value = Boolean.valueOf(false);
/* 39 */     this.name = name;
/* 40 */     this.renameProfile = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void fromBytes(ByteBuf bytes) {
/* 45 */     this.datatype = bytes.readByte();
/* 46 */     this.slot = bytes.readInt();
/* 47 */     this.name = ByteBufUtils.readUTF8String(bytes);
/* 48 */     this.value = DataUtills.instance.readObjectFromBytes(bytes, this.datatype);
/* 49 */     this.renameProfile = bytes.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(ByteBuf bytes) {
/* 54 */     bytes.writeByte(this.datatype);
/* 55 */     bytes.writeInt(this.slot);
/* 56 */     ByteBufUtils.writeUTF8String(bytes, this.name);
/* 57 */     DataUtills.instance.writeObjectToBytes(bytes, this.datatype, this.value);
/* 58 */     bytes.writeBoolean(this.renameProfile);
/*    */   }
/*    */   
/*    */   public static class Handler
/*    */     implements IMessageHandler<ItemConfigPacket, IMessage>
/*    */   {
/*    */     public IMessage onMessage(ItemConfigPacket message, MessageContext ctx) {
/* 65 */       EntityPlayerMP entityPlayerMP = (ctx.getServerHandler()).field_147369_b;
/*    */       
/* 67 */       if (message.slot >= ((EntityPlayer)entityPlayerMP).field_71071_by.func_70302_i_() || message.slot < 0) return null;
/*    */       
/* 69 */       ItemStack stack = ((EntityPlayer)entityPlayerMP).field_71071_by.func_70301_a(message.slot);
/*    */       
/* 71 */       if (stack != null && stack.func_77973_b() instanceof IConfigurableItem) {
/*    */         
/* 73 */         if (message.renameProfile) {
/* 74 */           ItemNBTHelper.setString(stack, "ProfileName" + ItemNBTHelper.getInteger(stack, "ConfigProfile", 0), message.name);
/* 75 */           return null;
/*    */         } 
/*    */         
/* 78 */         IConfigurableItem item = (IConfigurableItem)stack.func_77973_b();
/* 79 */         List<ItemConfigField> fields = item.getFields(stack, message.slot);
/*    */         
/* 81 */         for (ItemConfigField field : fields) {
/* 82 */           if (field.name.equals(message.name) && message.datatype == field.datatype) {
/* 83 */             ItemConfigField newValue = new ItemConfigField(message.datatype, message.value, message.slot, message.name);
/*    */             
/* 85 */             if (newValue.castToDouble() <= field.castMaxToDouble() && newValue.castToDouble() >= field.castMinToDouble()) {
/* 86 */               DataUtills.writeObjectToCompound(IConfigurableItem.ProfileHelper.getProfileCompound(stack), message.value, message.datatype, message.name);
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/* 91 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\network\ItemConfigPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */