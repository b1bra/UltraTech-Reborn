/*     */ package com.brandon3055.brandonscore.common.utills;
/*     */ 
/*     */ import com.brandon3055.brandonscore.BrandonsCore;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S07PacketRespawn;
/*     */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*     */ import net.minecraft.network.play.server.S1FPacketSetExperience;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class Teleporter {
/*     */   public static class TeleportLocation {
/*     */     protected double xCoord;
/*     */     protected double yCoord;
/*     */     protected double zCoord;
/*     */     protected int dimension;
/*     */     protected float pitch;
/*     */     protected float yaw;
/*     */     protected String name;
/*  27 */     protected String dimentionName = "";
/*     */     
/*     */     protected boolean writeProtected = false;
/*     */ 
/*     */     
/*     */     public TeleportLocation() {}
/*     */     
/*     */     public TeleportLocation(double x, double y, double z, int dimension) {
/*  35 */       this.xCoord = x;
/*  36 */       this.yCoord = y;
/*  37 */       this.zCoord = z;
/*  38 */       this.dimension = dimension;
/*  39 */       this.pitch = 0.0F;
/*  40 */       this.yaw = 0.0F;
/*     */     }
/*     */     
/*     */     public TeleportLocation(double x, double y, double z, int dimension, float pitch, float yaw) {
/*  44 */       this.xCoord = x;
/*  45 */       this.yCoord = y;
/*  46 */       this.zCoord = z;
/*  47 */       this.dimension = dimension;
/*  48 */       this.pitch = pitch;
/*  49 */       this.yaw = yaw;
/*     */     }
/*     */     
/*     */     public TeleportLocation(double x, double y, double z, int dimension, float pitch, float yaw, String name) {
/*  53 */       this.xCoord = x;
/*  54 */       this.yCoord = y;
/*  55 */       this.zCoord = z;
/*  56 */       this.dimension = dimension;
/*  57 */       this.pitch = pitch;
/*  58 */       this.yaw = yaw;
/*  59 */       this.name = name;
/*     */     }
/*     */     
/*     */     public double getXCoord() {
/*  63 */       return this.xCoord;
/*     */     }
/*     */     
/*     */     public double getYCoord() {
/*  67 */       return this.yCoord;
/*     */     }
/*     */     
/*     */     public double getZCoord() {
/*  71 */       return this.zCoord;
/*     */     }
/*     */     
/*     */     public int getDimension() {
/*  75 */       return this.dimension;
/*     */     }
/*     */     
/*     */     public String getDimensionName() {
/*  79 */       return this.dimentionName;
/*     */     }
/*     */     
/*     */     public float getPitch() {
/*  83 */       return this.pitch;
/*     */     }
/*     */     
/*     */     public float getYaw() {
/*  87 */       return this.yaw;
/*     */     }
/*     */     
/*     */     public String getName() {
/*  91 */       return this.name;
/*     */     }
/*     */     
/*     */     public boolean getWriteProtected() {
/*  95 */       return this.writeProtected;
/*     */     }
/*     */     
/*     */     public void setXCoord(double x) {
/*  99 */       this.xCoord = x;
/*     */     }
/*     */     
/*     */     public void setYCoord(double y) {
/* 103 */       this.yCoord = y;
/*     */     }
/*     */     
/*     */     public void setZCoord(double z) {
/* 107 */       this.zCoord = z;
/*     */     }
/*     */     
/*     */     public void setDimension(int d) {
/* 111 */       this.dimension = d;
/*     */     }
/*     */     
/*     */     public void setPitch(float p) {
/* 115 */       this.pitch = p;
/*     */     }
/*     */     
/*     */     public void setYaw(float y) {
/* 119 */       this.yaw = y;
/*     */     }
/*     */     
/*     */     public void setName(String s) {
/* 123 */       this.name = s;
/*     */     }
/*     */     
/*     */     public void setWriteProtected(boolean b) {
/* 127 */       this.writeProtected = b;
/*     */     }
/*     */     
/*     */     public void writeToNBT(NBTTagCompound compound) {
/* 131 */       compound.func_74780_a("X", this.xCoord);
/* 132 */       compound.func_74780_a("Y", this.yCoord);
/* 133 */       compound.func_74780_a("Z", this.zCoord);
/* 134 */       compound.func_74768_a("Dimension", this.dimension);
/* 135 */       compound.func_74776_a("Pitch", this.pitch);
/* 136 */       compound.func_74776_a("Yaw", this.yaw);
/* 137 */       compound.func_74778_a("Name", this.name);
/* 138 */       compound.func_74778_a("DimentionName", this.dimentionName);
/* 139 */       compound.func_74757_a("WP", this.writeProtected);
/*     */     }
/*     */     
/*     */     public void readFromNBT(NBTTagCompound compound) {
/* 143 */       this.xCoord = compound.func_74769_h("X");
/* 144 */       this.yCoord = compound.func_74769_h("Y");
/* 145 */       this.zCoord = compound.func_74769_h("Z");
/* 146 */       this.dimension = compound.func_74762_e("Dimension");
/* 147 */       this.pitch = compound.func_74760_g("Pitch");
/* 148 */       this.yaw = compound.func_74760_g("Yaw");
/* 149 */       this.name = compound.func_74779_i("Name");
/* 150 */       this.dimentionName = compound.func_74779_i("DimentionName");
/* 151 */       this.writeProtected = compound.func_74767_n("WP");
/*     */     }
/*     */     
/*     */     public void sendEntityToCoords(Entity entity) {
/* 155 */       entity.field_70170_p.func_72908_a(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, "draconicevolution:portal", 0.1F, entity.field_70170_p.field_73012_v.nextFloat() * 0.1F + 0.9F);
/*     */       
/* 157 */       Teleporter.teleportEntity(entity, this);
/*     */       
/* 159 */       entity.field_70170_p.func_72908_a(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, "draconicevolution:portal", 0.1F, entity.field_70170_p.field_73012_v.nextFloat() * 0.1F + 0.9F);
/*     */     }
/*     */     
/*     */     public void setDimentionName(String dimentionName) {
/* 163 */       this.dimentionName = dimentionName;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Entity teleportEntity(Entity entity, TeleportLocation destination) {
/* 170 */     if (entity == null || entity.field_70170_p.field_72995_K) return entity;
/*     */     
/* 172 */     World startWorld = entity.field_70170_p;
/* 173 */     WorldServer destinationWorld = BrandonsCore.proxy.getMCServer().func_71218_a(destination.dimension);
/*     */     
/* 175 */     if (destinationWorld == null) {
/* 176 */       LogHelper.error("Destination world dose not exist!");
/* 177 */       return entity;
/*     */     } 
/*     */     
/* 180 */     Entity mount = entity.field_70154_o;
/* 181 */     if (entity.field_70154_o != null) {
/* 182 */       entity.func_70078_a(null);
/* 183 */       mount = teleportEntity(mount, destination);
/*     */     } 
/*     */     
/* 186 */     boolean interDimensional = (startWorld.field_73011_w.field_76574_g != destinationWorld.field_73011_w.field_76574_g);
/*     */     
/* 188 */     startWorld.func_72866_a(entity, false);
/*     */     
/* 190 */     if (entity instanceof EntityPlayerMP && interDimensional) {
/* 191 */       EntityPlayerMP player = (EntityPlayerMP)entity;
/* 192 */       player.func_71053_j();
/* 193 */       player.field_71093_bK = destination.dimension;
/* 194 */       player.field_71135_a.func_147359_a((Packet)new S07PacketRespawn(player.field_71093_bK, player.field_70170_p.field_73013_u, destinationWorld.func_72912_H().func_76067_t(), player.field_71134_c.func_73081_b()));
/* 195 */       ((WorldServer)startWorld).func_73040_p().func_72695_c(player);
/*     */       
/* 197 */       startWorld.field_73010_i.remove(player);
/* 198 */       startWorld.func_72854_c();
/* 199 */       int i = entity.field_70176_ah;
/* 200 */       int j = entity.field_70164_aj;
/* 201 */       if (entity.field_70175_ag && startWorld.func_72863_F().func_73149_a(i, j)) {
/* 202 */         startWorld.func_72964_e(i, j).func_76622_b(entity);
/* 203 */         (startWorld.func_72964_e(i, j)).field_76643_l = true;
/*     */       } 
/* 205 */       startWorld.field_72996_f.remove(entity);
/* 206 */       startWorld.func_72847_b(entity);
/*     */     } 
/*     */     
/* 209 */     entity.func_70012_b(destination.xCoord, destination.yCoord, destination.zCoord, destination.yaw, destination.pitch);
/*     */     
/* 211 */     destinationWorld.field_73059_b.func_73158_c((int)destination.xCoord >> 4, (int)destination.zCoord >> 4);
/*     */     
/* 213 */     destinationWorld.field_72984_F.func_76320_a("placing");
/* 214 */     if (interDimensional) {
/* 215 */       if (!(entity instanceof EntityPlayer)) {
/* 216 */         NBTTagCompound entityNBT = new NBTTagCompound();
/* 217 */         entity.field_70128_L = false;
/* 218 */         entityNBT.func_74778_a("id", EntityList.func_75621_b(entity));
/* 219 */         entity.func_70109_d(entityNBT);
/* 220 */         entity.field_70128_L = true;
/* 221 */         entity = EntityList.func_75615_a(entityNBT, (World)destinationWorld);
/* 222 */         if (entity == null) {
/* 223 */           LogHelper.error("Failed to teleport entity to new location");
/* 224 */           return null;
/*     */         } 
/* 226 */         entity.field_71093_bK = destinationWorld.field_73011_w.field_76574_g;
/*     */       } 
/* 228 */       destinationWorld.func_72838_d(entity);
/* 229 */       entity.func_70029_a((World)destinationWorld);
/*     */     } 
/* 231 */     entity.func_70012_b(destination.xCoord, destination.yCoord, destination.zCoord, destination.yaw, entity.field_70125_A);
/*     */     
/* 233 */     destinationWorld.func_72866_a(entity, false);
/* 234 */     entity.func_70012_b(destination.xCoord, destination.yCoord, destination.zCoord, destination.yaw, entity.field_70125_A);
/*     */     
/* 236 */     if (entity instanceof EntityPlayerMP) {
/* 237 */       EntityPlayerMP player = (EntityPlayerMP)entity;
/* 238 */       if (interDimensional) {
/* 239 */         player.field_71133_b.func_71203_ab().func_72375_a(player, destinationWorld);
/*     */       }
/* 241 */       player.field_71135_a.func_147364_a(destination.xCoord, destination.yCoord, destination.zCoord, player.field_70177_z, player.field_70125_A);
/*     */     } 
/*     */     
/* 244 */     destinationWorld.func_72866_a(entity, false);
/*     */     
/* 246 */     if (entity instanceof EntityPlayerMP && interDimensional) {
/* 247 */       EntityPlayerMP player = (EntityPlayerMP)entity;
/* 248 */       player.field_71134_c.func_73080_a(destinationWorld);
/* 249 */       player.field_71133_b.func_71203_ab().func_72354_b(player, destinationWorld);
/* 250 */       player.field_71133_b.func_71203_ab().func_72385_f(player);
/*     */       
/* 252 */       for (PotionEffect potionEffect : player.func_70651_bq()) {
/* 253 */         player.field_71135_a.func_147359_a((Packet)new S1DPacketEntityEffect(player.func_145782_y(), potionEffect));
/*     */       }
/*     */       
/* 256 */       player.field_71135_a.func_147359_a((Packet)new S1FPacketSetExperience(player.field_71106_cc, player.field_71067_cb, player.field_71068_ca));
/* 257 */       FMLCommonHandler.instance().firePlayerChangedDimensionEvent((EntityPlayer)player, startWorld.field_73011_w.field_76574_g, destinationWorld.field_73011_w.field_76574_g);
/*     */     } 
/* 259 */     entity.func_70012_b(destination.xCoord, destination.yCoord, destination.zCoord, destination.yaw, entity.field_70125_A);
/*     */     
/* 261 */     if (mount != null) {
/* 262 */       entity.func_70078_a(mount);
/* 263 */       if (entity instanceof EntityPlayerMP) {
/* 264 */         destinationWorld.func_72866_a(entity, true);
/*     */       }
/*     */     } 
/* 267 */     destinationWorld.field_72984_F.func_76319_b();
/* 268 */     entity.field_70143_R = 0.0F;
/* 269 */     return entity;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\commo\\utills\Teleporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */