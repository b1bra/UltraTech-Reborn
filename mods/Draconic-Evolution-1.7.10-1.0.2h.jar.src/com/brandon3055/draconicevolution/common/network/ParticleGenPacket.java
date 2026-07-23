/*     */ package com.brandon3055.draconicevolution.common.network;
/*     */ import com.brandon3055.brandonscore.common.utills.InventoryUtils;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileParticleGenerator;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import com.brandon3055.brandonscore.common.tags.Tags;
/*     */ import com.brandon3055.brandonscore.common.tags.IItemMatcher;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ public class ParticleGenPacket implements IMessage {
/*  19 */   byte buttonId = 0;
/*  20 */   short value = 0;
/*  21 */   int tileX = 0;
/*  22 */   int tileY = 0;
/*  23 */   int tileZ = 0;
/*     */ 
/*     */   
/*     */   public ParticleGenPacket() {}
/*     */   
/*     */   public ParticleGenPacket(byte buttonId, short value, int x, int y, int z) {
/*  29 */     this.buttonId = buttonId;
/*  30 */     this.value = value;
/*  31 */     this.tileX = x;
/*  32 */     this.tileY = y;
/*  33 */     this.tileZ = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public void toBytes(ByteBuf bytes) {
/*  38 */     bytes.writeByte(this.buttonId);
/*  39 */     bytes.writeShort(this.value);
/*  40 */     bytes.writeInt(this.tileX);
/*  41 */     bytes.writeInt(this.tileY);
/*  42 */     bytes.writeInt(this.tileZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fromBytes(ByteBuf bytes) {
/*  47 */     this.buttonId = bytes.readByte();
/*  48 */     this.value = bytes.readShort();
/*  49 */     this.tileX = bytes.readInt();
/*  50 */     this.tileY = bytes.readInt();
/*  51 */     this.tileZ = bytes.readInt();
/*     */   }
/*     */   
/*     */   public static class Handler
/*     */     implements IMessageHandler<ParticleGenPacket, IMessage>
/*     */   {
/*     */     public IMessage onMessage(ParticleGenPacket message, MessageContext ctx) {
/*  58 */       TileEntity tile = (ctx.getServerHandler()).field_147369_b.field_70170_p.func_147438_o(message.tileX, message.tileY, message.tileZ);
/*  59 */       TileParticleGenerator gen = (tile instanceof TileParticleGenerator) ? (TileParticleGenerator)tile : null;
/*  60 */       if (gen != null) {
/*     */         
/*  62 */         switch (message.buttonId) {
/*     */           case 0:
/*  64 */             gen.red = message.value;
/*     */             break;
/*     */           case 1:
/*     */           case 7:
/*  68 */             gen.green = message.value;
/*     */             break;
/*     */           case 2:
/*     */           case 8:
/*  72 */             gen.blue = message.value;
/*     */             break;
/*     */           case 3:
/*     */           case 9:
/*  76 */             gen.motion_x = message.value / 1000.0F;
/*     */             break;
/*     */           case 4:
/*     */           case 10:
/*  80 */             gen.motion_y = message.value / 1000.0F;
/*     */             break;
/*     */           case 5:
/*     */           case 11:
/*  84 */             gen.motion_z = message.value / 1000.0F;
/*     */             break;
/*     */           case 6:
/*  87 */             gen.red = message.value;
/*     */             break;
/*     */           case 12:
/*  90 */             gen.random_red = message.value;
/*     */             break;
/*     */           case 13:
/*     */           case 19:
/*  94 */             gen.random_green = message.value;
/*     */             break;
/*     */           case 14:
/*     */           case 20:
/*  98 */             gen.random_blue = message.value;
/*     */             break;
/*     */           case 15:
/*     */           case 21:
/* 102 */             gen.random_motion_x = message.value / 1000.0F;
/*     */             break;
/*     */           case 16:
/*     */           case 22:
/* 106 */             gen.random_motion_y = message.value / 1000.0F;
/*     */             break;
/*     */           case 17:
/*     */           case 23:
/* 110 */             gen.random_motion_z = message.value / 1000.0F;
/*     */             break;
/*     */           case 18:
/* 113 */             gen.random_red = message.value;
/*     */             break;
/*     */           case 24:
/* 116 */             gen.life = message.value;
/*     */             break;
/*     */           case 25:
/* 119 */             gen.life = message.value;
/*     */             break;
/*     */           case 26:
/* 122 */             gen.random_life = message.value;
/*     */             break;
/*     */           case 27:
/* 125 */             gen.random_life = message.value;
/*     */             break;
/*     */           case 28:
/* 128 */             gen.scale = message.value / 100.0F;
/*     */             break;
/*     */           case 29:
/* 131 */             gen.scale = message.value / 100.0F;
/*     */             break;
/*     */           case 30:
/* 134 */             gen.random_scale = message.value / 100.0F;
/*     */             break;
/*     */           case 31:
/* 137 */             gen.random_scale = message.value / 100.0F;
/*     */             break;
/*     */           case 32:
/* 140 */             gen.page = message.value;
/*     */             break;
/*     */           case 33:
/* 143 */             gen.page = message.value;
/*     */             break;
/*     */           case 34:
/* 146 */             gen.spawn_x = message.value / 100.0F;
/*     */             break;
/*     */           case 35:
/* 149 */             gen.spawn_x = message.value / 100.0F;
/*     */             break;
/*     */           case 36:
/* 152 */             gen.random_spawn_x = message.value / 100.0F;
/*     */             break;
/*     */           case 37:
/* 155 */             gen.random_spawn_x = message.value / 100.0F;
/*     */             break;
/*     */           case 38:
/* 158 */             gen.spawn_y = message.value / 100.0F;
/*     */             break;
/*     */           case 39:
/* 161 */             gen.spawn_y = message.value / 100.0F;
/*     */             break;
/*     */           case 40:
/* 164 */             gen.random_spawn_y = message.value / 100.0F;
/*     */             break;
/*     */           case 41:
/* 167 */             gen.random_spawn_y = message.value / 100.0F;
/*     */             break;
/*     */           case 42:
/* 170 */             gen.spawn_z = message.value / 100.0F;
/*     */             break;
/*     */           case 43:
/* 173 */             gen.spawn_z = message.value / 100.0F;
/*     */             break;
/*     */           case 44:
/* 176 */             gen.random_spawn_z = message.value / 100.0F;
/*     */             break;
/*     */           case 45:
/* 179 */             gen.random_spawn_z = message.value / 100.0F;
/*     */             break;
/*     */           case 46:
/*     */           case 47:
/* 183 */             gen.spawn_rate = message.value;
/*     */             break;
/*     */           case 48:
/*     */           case 49:
/* 187 */             gen.fade = message.value;
/*     */             break;
/*     */           case 50:
/* 190 */             gen.collide = (message.value != 0);
/*     */             break;
/*     */           case 51:
/* 193 */             gen.selected_particle = message.value;
/*     */             break;
/*     */           case 52:
/* 196 */             gen.gravity = message.value / 1000.0F;
/*     */             break;
/*     */           case 53:
/* 199 */             gen.gravity = message.value / 1000.0F;
/*     */             break;
/*     */           case 54:
/* 202 */             gen.page = message.value;
/*     */             break;
/*     */           case 55:
/* 205 */             gen.page = message.value;
/*     */             break;
/*     */           case 58:
/* 208 */             LogHelper.info(Short.valueOf(message.value));
/* 209 */             gen.particles_enabled = (message.value == 1);
/*     */             break;
/*     */           
/*     */           case 100:
/* 213 */             gen.beam_red = message.value;
/*     */             break;
/*     */           case 101:
/* 216 */             gen.beam_green = message.value;
/*     */             break;
/*     */           case 102:
/* 219 */             gen.beam_blue = message.value;
/*     */             break;
/*     */           case 103:
/* 222 */             gen.beam_pitch = message.value / 100.0F;
/*     */             break;
/*     */           case 104:
/* 225 */             gen.beam_yaw = message.value / 100.0F;
/*     */             break;
/*     */           case 105:
/* 228 */             gen.beam_length = message.value / 100.0F;
/*     */             break;
/*     */           case 106:
/* 231 */             gen.beam_rotation = message.value / 100.0F;
/*     */             break;
/*     */           case 107:
/* 234 */             gen.beam_scale = message.value / 100.0F;
/*     */             break;
/*     */           case 108:
/* 237 */             gen.beam_red = message.value;
/*     */             break;
/*     */           case 109:
/* 240 */             gen.beam_green = message.value;
/*     */             break;
/*     */           case 110:
/* 243 */             gen.beam_blue = message.value;
/*     */             break;
/*     */           case 111:
/* 246 */             gen.beam_pitch = message.value / 100.0F;
/*     */             break;
/*     */           case 112:
/* 249 */             gen.beam_yaw = message.value / 100.0F;
/*     */             break;
/*     */           case 113:
/* 252 */             gen.beam_length = message.value / 100.0F;
/*     */             break;
/*     */           case 114:
/* 255 */             gen.beam_rotation = message.value / 100.0F;
/*     */             break;
/*     */           case 115:
/* 258 */             gen.beam_scale = message.value / 100.0F;
/*     */             break;
/*     */           case 116:
/* 261 */             gen.beam_enabled = (message.value == 1);
/*     */             break;
/*     */           case 117:
/* 264 */             gen.render_core = (message.value == 1);
/*     */             break;
/*     */         } 
/*     */         
/* 268 */         if (message.buttonId == Byte.MAX_VALUE) {
/* 269 */           if ((ctx.getServerHandler()).field_147369_b.field_71075_bZ.field_75098_d || InventoryUtils.hasItem((EntityPlayer)(ctx.getServerHandler()).field_147369_b, (IItemMatcher)Tags.Items.PAPER)) {
/* 270 */             giveNote(message, ctx);
/*     */           } else {
/* 272 */             (ctx.getServerHandler()).field_147369_b.func_146105_b((IChatComponent)new ChatComponentText("You need paper in your inventory to do that"));
/*     */           } 
/*     */         }
/* 275 */         (ctx.getServerHandler()).field_147369_b.field_70170_p.func_147471_g(message.tileX, message.tileY, message.tileZ);
/*     */       } 
/* 277 */       return null;
/*     */     }
/*     */     
/*     */     private void giveNote(ParticleGenPacket message, MessageContext ctx) {
/* 281 */       EntityPlayerMP entityPlayerMP = (ctx.getServerHandler()).field_147369_b;
/* 282 */       if (!((EntityPlayer)entityPlayerMP).field_71075_bZ.field_75098_d) InventoryUtils.consumeItem((EntityPlayer)entityPlayerMP, (IItemMatcher)Tags.Items.PAPER); 
/* 283 */       ItemStack stack = Tags.Items.PAPER.requireStack();
/* 284 */       stack.func_77982_d(new NBTTagCompound());
/* 285 */       TileEntity tile = (ctx.getServerHandler()).field_147369_b.field_70170_p.func_147438_o(message.tileX, message.tileY, message.tileZ);
/* 286 */       TileParticleGenerator gen = (tile instanceof TileParticleGenerator) ? (TileParticleGenerator)tile : null;
/* 287 */       if (gen != null) {
/* 288 */         gen.getBlockNBT(stack.func_77978_p());
/* 289 */         stack.func_151001_c("Saved Particle Gen Settings");
/* 290 */         ((EntityPlayer)entityPlayerMP).field_70170_p.func_72838_d((Entity)new EntityItem(((EntityPlayer)entityPlayerMP).field_70170_p, ((EntityPlayer)entityPlayerMP).field_70165_t, ((EntityPlayer)entityPlayerMP).field_70163_u, ((EntityPlayer)entityPlayerMP).field_70161_v, stack));
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\network\ParticleGenPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */