/*     */ package com.brandon3055.draconicevolution.common.network;
/*     */ 
/*     */ import com.brandon3055.brandonscore.BrandonsCore;
/*     */ import com.brandon3055.brandonscore.common.utills.InventoryUtils;
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Teleporter;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import cpw.mods.fml.common.network.ByteBufUtils;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
/*     */ import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.loliland.mctags.api.Tags;
/*     */ import net.loliland.mctags.api.generic.IItemMatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ 
/*     */ 
/*     */ public class TeleporterPacket
/*     */   implements IMessage
/*     */ {
/*     */   public static final int ADDDESTINATION = 0;
/*     */   public static final int REMOVEDESTINATION = 1;
/*     */   public static final int UPDATENAME = 2;
/*     */   public static final int UPDATELOCK = 3;
/*     */   public static final int CHANGESELECTION = 4;
/*  32 */   private int data = 0; public static final int UPDATEOFFSET = 5; public static final int ADDFUEL = 6; public static final int UPDATEDESTINATION = 7; public static final int TELEPORT = 8; public static final int SCROLL = 9; public static final int MOVELOCATION = 10;
/*     */   private boolean dataB;
/*  34 */   private byte function = -1;
/*     */ 
/*     */   
/*     */   private Teleporter.TeleportLocation location;
/*     */ 
/*     */   
/*     */   public TeleporterPacket(int function, int data, boolean b) {
/*  41 */     this.data = data;
/*  42 */     this.function = (byte)function;
/*  43 */     this.dataB = b;
/*     */   }
/*     */   
/*     */   public TeleporterPacket(Teleporter.TeleportLocation location, int function) {
/*  47 */     this.location = location;
/*  48 */     this.function = (byte)function;
/*     */   }
/*     */   
/*     */   public TeleporterPacket(Teleporter.TeleportLocation location, int function, int data) {
/*  52 */     this.data = data;
/*  53 */     this.location = location;
/*  54 */     this.function = (byte)function;
/*     */   }
/*     */ 
/*     */   
/*     */   public void toBytes(ByteBuf bytes) {
/*  59 */     bytes.writeByte(this.function);
/*  60 */     if (this.function == 0 || this.function == 7) {
/*  61 */       bytes.writeDouble(this.location.getXCoord());
/*  62 */       bytes.writeDouble(this.location.getYCoord());
/*  63 */       bytes.writeDouble(this.location.getZCoord());
/*  64 */       bytes.writeInt(this.location.getDimension());
/*  65 */       bytes.writeFloat(this.location.getPitch());
/*  66 */       bytes.writeFloat(this.location.getYaw());
/*  67 */       ByteBufUtils.writeUTF8String(bytes, this.location.getName());
/*     */       
/*  69 */       if (this.function == 7) bytes.writeInt(this.data);
/*     */     
/*     */     } 
/*  72 */     if (this.function == 3 || this.function == 10) {
/*  73 */       bytes.writeInt(this.data);
/*  74 */       bytes.writeBoolean(this.dataB);
/*     */     } 
/*     */     
/*  77 */     if (this.function == 5 || this.function == 4 || this.function == 1 || this.function == 6 || this.function == 9) {
/*  78 */       bytes.writeInt(this.data);
/*     */     }
/*     */     
/*  81 */     if (this.function == 2) {
/*  82 */       ByteBufUtils.writeUTF8String(bytes, this.location.getName());
/*  83 */       bytes.writeInt(this.data);
/*     */     } 
/*     */     
/*  86 */     if (this.function == 8) {
/*  87 */       bytes.writeInt(this.data);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void fromBytes(ByteBuf bytes) {
/*  93 */     this.function = bytes.readByte();
/*  94 */     if (this.function == 0 || this.function == 7) {
/*  95 */       this.location = new Teleporter.TeleportLocation();
/*  96 */       this.location.setXCoord(bytes.readDouble());
/*  97 */       this.location.setYCoord(bytes.readDouble());
/*  98 */       this.location.setZCoord(bytes.readDouble());
/*  99 */       this.location.setDimension(bytes.readInt());
/* 100 */       this.location.setPitch(bytes.readFloat());
/* 101 */       this.location.setYaw(bytes.readFloat());
/* 102 */       this.location.setName(ByteBufUtils.readUTF8String(bytes));
/*     */       
/* 104 */       if (this.function == 7) this.data = bytes.readInt();
/*     */     
/*     */     } 
/* 107 */     if (this.function == 3 || this.function == 10) {
/* 108 */       this.data = bytes.readInt();
/* 109 */       this.dataB = bytes.readBoolean();
/*     */     } 
/*     */     
/* 112 */     if (this.function == 5 || this.function == 4 || this.function == 1 || this.function == 6 || this.function == 9) {
/* 113 */       this.data = bytes.readInt();
/*     */     }
/*     */     
/* 116 */     if (this.function == 2) {
/* 117 */       this.location = new Teleporter.TeleportLocation();
/* 118 */       this.location.setName(ByteBufUtils.readUTF8String(bytes));
/* 119 */       this.data = bytes.readInt();
/*     */     } 
/*     */     
/* 122 */     if (this.function == 8)
/* 123 */       this.data = bytes.readInt(); 
/*     */   }
/*     */   
/*     */   public TeleporterPacket() {}
/*     */   
/*     */   public static class Handler
/*     */     implements IMessageHandler<TeleporterPacket, IMessage> {
/*     */     public IMessage onMessage(TeleporterPacket message, MessageContext ctx) {
/* 131 */       ItemStack teleporter = (ctx.getServerHandler()).field_147369_b.func_70694_bm();
/* 132 */       if (teleporter == null || teleporter.func_77973_b() != ModItems.teleporterMKII) return null;
/*     */       
/* 134 */       NBTTagCompound compound = teleporter.func_77978_p();
/* 135 */       if (compound == null) compound = new NBTTagCompound(); 
/* 136 */       NBTTagList list = (NBTTagList)compound.func_74781_a("Locations");
/* 137 */       if (list == null) list = new NBTTagList();
/*     */       
/* 139 */       if (message.function == 0) {
/* 140 */         NBTTagCompound tag = new NBTTagCompound();
/* 141 */         message.location.setDimentionName((BrandonsCore.proxy.getMCServer().func_71218_a(message.location.getDimension())).field_73011_w.func_80007_l());
/* 142 */         message.location.writeToNBT(tag);
/* 143 */         list.func_74742_a((NBTBase)tag);
/* 144 */         compound.func_74782_a("Locations", (NBTBase)list);
/* 145 */         teleporter.func_77982_d(compound);
/*     */       } 
/*     */       
/* 148 */       if (message.function == 9) {
/* 149 */         int selected = ItemNBTHelper.getShort(teleporter, "Selection", (short)0);
/* 150 */         int selectionOffset = ItemNBTHelper.getInteger(teleporter, "SelectionOffset", 0);
/* 151 */         int maxSelect = Math.min(list.func_74745_c() - 1, 11);
/* 152 */         int maxOffset = Math.max(list.func_74745_c() - 12, 0);
/*     */         
/* 154 */         if (message.data > 0 && selected < maxSelect) {
/* 155 */           ItemNBTHelper.setShort(teleporter, "Selection", (short)(selected + 1));
/* 156 */           return null;
/*     */         } 
/* 158 */         if (message.data > 0 && selectionOffset < maxOffset) {
/* 159 */           ItemNBTHelper.setInteger(teleporter, "SelectionOffset", selectionOffset + 1);
/* 160 */           return null;
/*     */         } 
/* 162 */         if (message.data < 0 && selected > 0) {
/* 163 */           ItemNBTHelper.setShort(teleporter, "Selection", (short)(selected - 1));
/* 164 */           return null;
/*     */         } 
/* 166 */         if (message.data < 0 && selectionOffset > 0) {
/* 167 */           ItemNBTHelper.setInteger(teleporter, "SelectionOffset", selectionOffset - 1);
/* 168 */           return null;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 173 */       if (message.function == 7) {
/* 174 */         NBTTagCompound tag = list.func_150305_b(message.data);
/* 175 */         message.location.setDimentionName((BrandonsCore.proxy.getMCServer().func_71218_a(message.location.getDimension())).field_73011_w.func_80007_l());
/* 176 */         message.location.writeToNBT(tag);
/* 177 */         list.func_150304_a(message.data, (NBTBase)tag);
/* 178 */         compound.func_74782_a("Locations", (NBTBase)list);
/* 179 */         teleporter.func_77982_d(compound);
/*     */       } 
/*     */       
/* 182 */       if (message.function == 3) {
/* 183 */         list.func_150305_b(message.data).func_74757_a("WP", message.dataB);
/* 184 */         compound.func_74782_a("Locations", (NBTBase)list);
/* 185 */         teleporter.func_77982_d(compound);
/*     */       } 
/*     */       
/* 188 */       if (message.function == 1) {
/* 189 */         list.func_74744_a(message.data);
/* 190 */         compound.func_74782_a("Locations", (NBTBase)list);
/* 191 */         teleporter.func_77982_d(compound);
/*     */       } 
/*     */       
/* 194 */       if (message.function == 2) {
/* 195 */         list.func_150305_b(message.data).func_74778_a("Name", message.location.getName());
/* 196 */         compound.func_74782_a("Locations", (NBTBase)list);
/* 197 */         teleporter.func_77982_d(compound);
/*     */       } 
/*     */       
/* 200 */       if (message.function == 8) {
/* 201 */         int fuel = ItemNBTHelper.getInteger(teleporter, "Fuel", 0);
/* 202 */         if (!(ctx.getServerHandler()).field_147369_b.field_71075_bZ.field_75098_d)
/* 203 */           ItemNBTHelper.setInteger(teleporter, "Fuel", fuel - 1); 
/* 204 */         Teleporter.TeleportLocation destination = new Teleporter.TeleportLocation();
/* 205 */         destination.readFromNBT(list.func_150305_b(message.data));
/* 206 */         destination.sendEntityToCoords((Entity)(ctx.getServerHandler()).field_147369_b);
/*     */       } 
/*     */       
/* 209 */       if (message.function == 10) {
/* 210 */         int selected = ItemNBTHelper.getShort(teleporter, "Selection", (short)0);
/* 211 */         int selectionOffset = ItemNBTHelper.getInteger(teleporter, "SelectionOffset", 0);
/* 212 */         int maxSelect = Math.min(list.func_74745_c() - 1, 11);
/* 213 */         int maxOffset = Math.max(list.func_74745_c() - 12, 0);
/*     */         
/* 215 */         if (message.dataB) {
/*     */           
/* 217 */           if (selected > 0) {
/* 218 */             NBTTagCompound temp = list.func_150305_b(selected + selectionOffset);
/* 219 */             list.func_150304_a(selected + selectionOffset, (NBTBase)list.func_150305_b(selected + selectionOffset - 1));
/* 220 */             list.func_150304_a(selected + selectionOffset - 1, (NBTBase)temp);
/* 221 */             compound.func_74782_a("Locations", (NBTBase)list);
/* 222 */             teleporter.func_77982_d(compound);
/* 223 */             ItemNBTHelper.setShort(teleporter, "Selection", (short)(ItemNBTHelper.getShort(teleporter, "Selection", (short)0) - 1));
/*     */           }
/*     */         
/*     */         }
/* 227 */         else if (selected < maxSelect) {
/* 228 */           NBTTagCompound temp = list.func_150305_b(selected + selectionOffset);
/* 229 */           list.func_150304_a(selected + selectionOffset, (NBTBase)list.func_150305_b(selected + selectionOffset + 1));
/* 230 */           list.func_150304_a(selected + selectionOffset + 1, (NBTBase)temp);
/* 231 */           compound.func_74782_a("Locations", (NBTBase)list);
/* 232 */           teleporter.func_77982_d(compound);
/* 233 */           ItemNBTHelper.setShort(teleporter, "Selection", (short)(ItemNBTHelper.getShort(teleporter, "Selection", (short)0) + 1));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 238 */       if (message.function == 6) {
/* 239 */         int fuel = ItemNBTHelper.getInteger(teleporter, "Fuel", 0);
/* 240 */         int count = 0;
/* 241 */         for (int i = 0; i < message.data && 
/* 242 */           InventoryUtils.consumeItem((EntityPlayer)(ctx.getServerHandler()).field_147369_b, (IItemMatcher)Tags.Items.ENDER_PEARL); i++) {
/* 243 */           count++;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 248 */         ItemNBTHelper.setInteger(teleporter, "Fuel", fuel + ConfigHandler.teleporterUsesPerPearl * count);
/*     */       } 
/*     */       
/* 251 */       if (message.function == 4) {
/* 252 */         ItemNBTHelper.setShort(teleporter, "Selection", (short)message.data);
/*     */       }
/*     */       
/* 255 */       if (message.function == 5) {
/* 256 */         ItemNBTHelper.setInteger(teleporter, "SelectionOffset", message.data);
/*     */       }
/* 258 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\network\TeleporterPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */