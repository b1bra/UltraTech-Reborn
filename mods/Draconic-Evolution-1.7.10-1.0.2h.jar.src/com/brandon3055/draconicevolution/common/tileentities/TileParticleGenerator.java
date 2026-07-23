/*     */ package com.brandon3055.draconicevolution.common.tileentities;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.client.handler.ParticleHandler;
/*     */ import com.brandon3055.draconicevolution.client.render.particle.ParticleCustom;
/*     */ import com.brandon3055.draconicevolution.client.render.particle.Particles;
/*     */ import com.brandon3055.draconicevolution.common.blocks.multiblock.MultiblockHelper;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.TileEnergyStorageCore;
/*     */ import com.brandon3055.draconicevolution.integration.computers.IDEPeripheral;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ 
/*     */ public class TileParticleGenerator
/*     */   extends TileEntity implements IDEPeripheral {
/*     */   public boolean particles_enabled = true;
/*  25 */   public int red = 0;
/*  26 */   public int green = 0;
/*  27 */   public int blue = 0;
/*  28 */   public int random_red = 0;
/*  29 */   public int random_green = 0;
/*  30 */   public int random_blue = 0;
/*  31 */   public float motion_x = 0.0F;
/*  32 */   public float motion_y = 0.0F;
/*  33 */   public float motion_z = 0.0F;
/*  34 */   public float random_motion_x = 0.0F;
/*  35 */   public float random_motion_y = 0.0F;
/*  36 */   public float random_motion_z = 0.0F;
/*  37 */   public float scale = 1.0F;
/*  38 */   public float random_scale = 0.0F;
/*  39 */   public int life = 100;
/*  40 */   public int random_life = 0;
/*  41 */   public float spawn_x = 0.0F;
/*  42 */   public float spawn_y = 0.0F;
/*  43 */   public float spawn_z = 0.0F;
/*  44 */   public float random_spawn_x = 0.0F;
/*  45 */   public float random_spawn_y = 0.0F;
/*  46 */   public float random_spawn_z = 0.0F;
/*  47 */   public int page = 1;
/*  48 */   public int fade = 0;
/*  49 */   public int spawn_rate = 1;
/*     */   public boolean collide = false;
/*  51 */   public int selected_particle = 1;
/*  52 */   public int selected_max = 3;
/*  53 */   public float gravity = 0.0F;
/*     */   public boolean active = true;
/*     */   public boolean signal = false;
/*     */   public boolean inverted = false;
/*  57 */   MultiblockHelper.TileLocation master = new MultiblockHelper.TileLocation();
/*  58 */   public float rotation = 0.0F;
/*     */   
/*     */   public boolean stabalizerMode = false;
/*     */   
/*     */   public boolean beam_enabled = false;
/*     */   
/*     */   public boolean render_core = false;
/*  65 */   public int beam_red = 0;
/*  66 */   public int beam_green = 0;
/*  67 */   public int beam_blue = 0;
/*  68 */   public float beam_scale = 1.0F;
/*  69 */   public float beam_pitch = 0.0F;
/*  70 */   public float beam_yaw = 0.0F;
/*  71 */   public float beam_length = 0.0F;
/*  72 */   public float beam_rotation = 0.0F;
/*     */   
/*  74 */   private int tick = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_145845_h() {
/*  85 */     if (!this.field_145850_b.field_72995_K)
/*  86 */       return;  this.rotation += 0.5F;
/*  87 */     if (this.stabalizerMode)
/*     */     {
/*  89 */       spawnStabilizerParticle();
/*     */     }
/*  91 */     if (this.stabalizerMode)
/*     */       return; 
/*  93 */     if (this.signal && !this.inverted) { this.active = true; }
/*  94 */     else { this.active = (!this.signal && this.inverted); }
/*     */     
/*  96 */     if (this.tick >= this.spawn_rate && this.active && this.particles_enabled) {
/*  97 */       this.tick = 0;
/*     */       
/*  99 */       Random rand = this.field_145850_b.field_73012_v;
/*     */       
/* 101 */       float MX = this.motion_x + this.random_motion_x * rand.nextFloat();
/* 102 */       float MY = this.motion_y + this.random_motion_y * rand.nextFloat();
/* 103 */       float MZ = this.motion_z + this.random_motion_z * rand.nextFloat();
/* 104 */       float SCALE = this.scale + this.random_scale * rand.nextFloat();
/* 105 */       double spawnX = (this.field_145851_c + this.spawn_x + this.random_spawn_x * rand.nextFloat());
/* 106 */       double spawnY = (this.field_145848_d + this.spawn_y + this.random_spawn_y * rand.nextFloat());
/* 107 */       double spawnZ = (this.field_145849_e + this.spawn_z + this.random_spawn_z * rand.nextFloat());
/*     */       
/* 109 */       ParticleCustom particle = new ParticleCustom(this.field_145850_b, spawnX + 0.5D, spawnY + 0.5D, spawnZ + 0.5D, MX, MY, MZ, SCALE, this.collide, this.selected_particle);
/* 110 */       particle.red = this.red + rand.nextInt(this.random_red + 1);
/* 111 */       particle.green = this.green + rand.nextInt(this.random_green + 1);
/* 112 */       particle.blue = this.blue + rand.nextInt(this.random_blue + 1);
/* 113 */       particle.maxAge = this.life + rand.nextInt(this.random_life + 1);
/* 114 */       particle.fadeTime = this.fade;
/* 115 */       particle.fadeLength = this.fade;
/* 116 */       particle.gravity = this.gravity;
/*     */       
/* 118 */       ParticleHandler.spawnCustomParticle((EntityFX)particle, 256.0D);
/*     */     } else {
/* 120 */       this.tick++;
/*     */     } 
/*     */   }
/*     */   @SideOnly(Side.CLIENT)
/*     */   private void spawnStabilizerParticle() {
/* 125 */     if (getMaster() == null || this.field_145850_b.func_82737_E() % 20L != 1L)
/*     */       return; 
/* 127 */     double x = this.field_145851_c + 0.5D;
/* 128 */     double y = this.field_145848_d + 0.5D;
/* 129 */     double z = this.field_145849_e + 0.5D;
/* 130 */     int direction = 0;
/*     */     
/* 132 */     if ((getMaster()).field_145851_c > this.field_145851_c) { direction = 0; }
/* 133 */     else if ((getMaster()).field_145851_c < this.field_145851_c) { direction = 1; }
/* 134 */     else if ((getMaster()).field_145849_e > this.field_145849_e) { direction = 2; }
/* 135 */     else if ((getMaster()).field_145849_e < this.field_145849_e) { direction = 3; }
/*     */     
/* 137 */     Particles.EnergyBeamParticle particle = new Particles.EnergyBeamParticle(this.field_145850_b, x, y, z, (getMaster()).field_145851_c + 0.5D, (getMaster()).field_145849_e + 0.5D, direction, false);
/* 138 */     Particles.EnergyBeamParticle particle2 = new Particles.EnergyBeamParticle(this.field_145850_b, x, y, z, (getMaster()).field_145851_c + 0.5D, (getMaster()).field_145849_e + 0.5D, direction, true);
/* 139 */     ParticleHandler.spawnCustomParticle((EntityFX)particle, 60.0D);
/* 140 */     ParticleHandler.spawnCustomParticle((EntityFX)particle2, 60.0D);
/*     */   }
/*     */   
/*     */   public void toggleInverted() {
/* 144 */     this.inverted = !this.inverted;
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet func_145844_m() {
/* 149 */     NBTTagCompound tagCompound = new NBTTagCompound();
/* 150 */     func_145841_b(tagCompound);
/* 151 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, tagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/* 156 */     func_145839_a(pkt.func_148857_g());
/* 157 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound compound) {
/* 162 */     this.master.writeToNBT(compound, "Key");
/* 163 */     compound.func_74757_a("StabalizerMode", this.stabalizerMode);
/* 164 */     getBlockNBT(compound);
/*     */     
/* 166 */     super.func_145841_b(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound compound) {
/* 171 */     this.master.readFromNBT(compound, "Key");
/* 172 */     this.stabalizerMode = compound.func_74767_n("StabalizerMode");
/* 173 */     setBlockNBT(compound);
/* 174 */     super.func_145839_a(compound);
/*     */   }
/*     */   
/*     */   public TileEnergyStorageCore getMaster() {
/* 178 */     if (this.master == null) return null; 
/* 179 */     return (this.field_145850_b.func_147438_o(this.master.getXCoord(), this.master.getYCoord(), this.master.getZCoord()) != null && this.field_145850_b.func_147438_o(this.master.getXCoord(), this.master.getYCoord(), this.master.getZCoord()) instanceof TileEnergyStorageCore) ? (TileEnergyStorageCore)this.field_145850_b.func_147438_o(this.master.getXCoord(), this.master.getYCoord(), this.master.getZCoord()) : null;
/*     */   }
/*     */   
/*     */   public void setMaster(MultiblockHelper.TileLocation master) {
/* 183 */     this.master = master;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getRenderBoundingBox() {
/* 188 */     return INFINITE_EXTENT_AABB;
/*     */   }
/*     */   
/*     */   public void getBlockNBT(NBTTagCompound compound) {
/* 192 */     compound.func_74768_a("Red", this.red);
/* 193 */     compound.func_74768_a("Green", this.green);
/* 194 */     compound.func_74768_a("Blue", this.blue);
/* 195 */     compound.func_74768_a("RandomRed", this.random_red);
/* 196 */     compound.func_74768_a("RandomGreen", this.random_green);
/* 197 */     compound.func_74768_a("RandomBlue", this.random_blue);
/* 198 */     compound.func_74776_a("MotionX", this.motion_x);
/* 199 */     compound.func_74776_a("MotionY", this.motion_y);
/* 200 */     compound.func_74776_a("MotionZ", this.motion_z);
/* 201 */     compound.func_74776_a("RandomMotionX", this.random_motion_x);
/* 202 */     compound.func_74776_a("RandomMotionY", this.random_motion_y);
/* 203 */     compound.func_74776_a("RandomMotionZ", this.random_motion_z);
/* 204 */     compound.func_74776_a("Scale", this.scale);
/* 205 */     compound.func_74776_a("RandomScale", this.random_scale);
/* 206 */     compound.func_74768_a("Life", this.life);
/* 207 */     compound.func_74768_a("RandomLife", this.random_life);
/* 208 */     compound.func_74776_a("SpawnX", this.spawn_x);
/* 209 */     compound.func_74776_a("SpawnY", this.spawn_y);
/* 210 */     compound.func_74776_a("SpawnZ", this.spawn_z);
/* 211 */     compound.func_74776_a("RandomSpawnX", this.random_spawn_x);
/* 212 */     compound.func_74776_a("RandomSpawnY", this.random_spawn_y);
/* 213 */     compound.func_74776_a("RandomSpawnZ", this.random_spawn_z);
/* 214 */     compound.func_74768_a("Page", this.page);
/* 215 */     compound.func_74768_a("SpawnRate", this.spawn_rate);
/* 216 */     compound.func_74757_a("CanCollide", this.collide);
/* 217 */     compound.func_74768_a("Fade", this.fade);
/* 218 */     compound.func_74768_a("SelectedParticle", this.selected_particle);
/* 219 */     compound.func_74776_a("Gravity", this.gravity);
/* 220 */     compound.func_74757_a("Active", this.active);
/* 221 */     compound.func_74757_a("Signal", this.signal);
/* 222 */     compound.func_74757_a("Inverted", this.inverted);
/* 223 */     compound.func_74757_a("particles_enabled", this.particles_enabled);
/*     */     
/* 225 */     compound.func_74757_a("beam_enabled", this.beam_enabled);
/* 226 */     compound.func_74757_a("render_core", this.render_core);
/* 227 */     compound.func_74768_a("beam_red", this.beam_red);
/* 228 */     compound.func_74768_a("beam_green", this.beam_green);
/* 229 */     compound.func_74768_a("beam_blue", this.beam_blue);
/* 230 */     compound.func_74776_a("beam_scale", this.beam_scale);
/* 231 */     compound.func_74776_a("beam_pitch", this.beam_pitch);
/* 232 */     compound.func_74776_a("beam_yaw", this.beam_yaw);
/* 233 */     compound.func_74776_a("beam_length", this.beam_length);
/* 234 */     compound.func_74776_a("beam_rotation", this.beam_rotation);
/*     */   }
/*     */   
/*     */   public void setBlockNBT(NBTTagCompound compound) {
/* 238 */     this.red = compound.func_74762_e("Red");
/* 239 */     this.green = compound.func_74762_e("Green");
/* 240 */     this.blue = compound.func_74762_e("Blue");
/* 241 */     this.random_red = compound.func_74762_e("RandomRed");
/* 242 */     this.random_green = compound.func_74762_e("RandomGreen");
/* 243 */     this.random_blue = compound.func_74762_e("RandomBlue");
/* 244 */     this.motion_x = compound.func_74760_g("MotionX");
/* 245 */     this.motion_y = compound.func_74760_g("MotionY");
/* 246 */     this.motion_z = compound.func_74760_g("MotionZ");
/* 247 */     this.random_motion_x = compound.func_74760_g("RandomMotionX");
/* 248 */     this.random_motion_y = compound.func_74760_g("RandomMotionY");
/* 249 */     this.random_motion_z = compound.func_74760_g("RandomMotionZ");
/* 250 */     this.scale = compound.func_74760_g("Scale");
/* 251 */     this.random_scale = compound.func_74760_g("RandomScale");
/* 252 */     this.life = compound.func_74762_e("Life");
/* 253 */     this.random_life = compound.func_74762_e("RandomLife");
/* 254 */     this.spawn_x = compound.func_74760_g("SpawnX");
/* 255 */     this.spawn_y = compound.func_74760_g("SpawnY");
/* 256 */     this.spawn_z = compound.func_74760_g("SpawnZ");
/* 257 */     this.random_spawn_x = compound.func_74760_g("RandomSpawnX");
/* 258 */     this.random_spawn_y = compound.func_74760_g("RandomSpawnY");
/* 259 */     this.random_spawn_z = compound.func_74760_g("RandomSpawnZ");
/* 260 */     this.page = compound.func_74762_e("Page");
/* 261 */     this.spawn_rate = compound.func_74762_e("SpawnRate");
/* 262 */     this.collide = compound.func_74767_n("CanCollide");
/* 263 */     this.fade = compound.func_74762_e("Fade");
/* 264 */     this.selected_particle = compound.func_74762_e("SelectedParticle");
/* 265 */     this.gravity = compound.func_74760_g("Gravity");
/* 266 */     this.active = compound.func_74767_n("Active");
/* 267 */     this.signal = compound.func_74767_n("Signal");
/* 268 */     this.inverted = compound.func_74767_n("Inverted");
/* 269 */     this.particles_enabled = compound.func_74767_n("particles_enabled");
/*     */     
/* 271 */     this.beam_enabled = compound.func_74767_n("beam_enabled");
/* 272 */     this.render_core = compound.func_74767_n("render_core");
/* 273 */     this.beam_red = compound.func_74762_e("beam_red");
/* 274 */     this.beam_green = compound.func_74762_e("beam_green");
/* 275 */     this.beam_blue = compound.func_74762_e("beam_blue");
/* 276 */     this.beam_scale = compound.func_74760_g("beam_scale");
/* 277 */     this.beam_pitch = compound.func_74760_g("beam_pitch");
/* 278 */     this.beam_yaw = compound.func_74760_g("beam_yaw");
/* 279 */     this.beam_length = compound.func_74760_g("beam_length");
/* 280 */     this.beam_rotation = compound.func_74760_g("beam_rotation");
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public double func_145833_n() {
/* 286 */     return 655360.0D;
/*     */   }
/*     */   
/*     */   public static double limit(double value, double min, double max) {
/* 290 */     return Math.max(min, Math.min(value, max));
/*     */   }
/*     */   
/*     */   public static int limit(int value, int min, int max) {
/* 294 */     return Math.max(min, Math.min(value, max));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 299 */     return "particle_generator";
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getMethodNames() {
/* 304 */     return new String[] { "setGeneratorProperty", "getGeneratorState", "resetGeneratorState" };
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] callMethod(String method, Object... args) {
/* 309 */     if (method.startsWith("setGeneratorProperty")) {
/* 310 */       if (args.length != 2) return new Object[] { Boolean.valueOf(false) }; 
/* 311 */       if (!(args[0] instanceof String)) return new Object[] { Boolean.valueOf(false) };
/*     */ 
/*     */       
/* 314 */       if (args[0].equals("particles_enabled") && args[1] instanceof Boolean) {
/* 315 */         this.particles_enabled = ((Boolean)args[1]).booleanValue();
/* 316 */       } else if (args[0].equals("red") && args[1] instanceof Double) {
/* 317 */         this.red = limit(((Double)args[1]).intValue(), 0, 255);
/* 318 */       } else if (args[0].equals("green") && args[1] instanceof Double) {
/* 319 */         this.green = limit(((Double)args[1]).intValue(), 0, 255);
/* 320 */       } else if (args[0].equals("blue") && args[1] instanceof Double) {
/* 321 */         this.blue = limit(((Double)args[1]).intValue(), 0, 255);
/* 322 */       } else if (args[0].equals("random_red") && args[1] instanceof Double) {
/* 323 */         this.random_red = limit(((Double)args[1]).intValue(), 0, 255);
/* 324 */       } else if (args[0].equals("random_green") && args[1] instanceof Double) {
/* 325 */         this.random_green = limit(((Double)args[1]).intValue(), 0, 255);
/* 326 */       } else if (args[0].equals("random_blue") && args[1] instanceof Double) {
/* 327 */         this.random_blue = limit(((Double)args[1]).intValue(), 0, 255);
/* 328 */       } else if (args[0].equals("motion_x") && args[1] instanceof Double) {
/* 329 */         this.motion_x = (float)limit(((Double)args[1]).doubleValue(), -5.0D, 5.0D);
/* 330 */       } else if (args[0].equals("motion_y") && args[1] instanceof Double) {
/* 331 */         this.motion_y = (float)limit(((Double)args[1]).doubleValue(), -5.0D, 5.0D);
/* 332 */       } else if (args[0].equals("motion_z") && args[1] instanceof Double) {
/* 333 */         this.motion_z = (float)limit(((Double)args[1]).doubleValue(), -5.0D, 5.0D);
/* 334 */       } else if (args[0].equals("random_motion_x") && args[1] instanceof Double) {
/* 335 */         this.random_motion_x = (float)limit(((Double)args[1]).doubleValue(), -5.0D, 5.0D);
/* 336 */       } else if (args[0].equals("random_motion_y") && args[1] instanceof Double) {
/* 337 */         this.random_motion_y = (float)limit(((Double)args[1]).doubleValue(), -5.0D, 5.0D);
/* 338 */       } else if (args[0].equals("random_motion_z") && args[1] instanceof Double) {
/* 339 */         this.random_motion_z = (float)limit(((Double)args[1]).doubleValue(), -5.0D, 5.0D);
/* 340 */       } else if (args[0].equals("scale") && args[1] instanceof Double) {
/* 341 */         this.scale = (float)limit(((Double)args[1]).doubleValue(), 0.009999999776482582D, 50.0D);
/* 342 */       } else if (args[0].equals("random_scale") && args[1] instanceof Double) {
/* 343 */         this.random_scale = (float)limit(((Double)args[1]).doubleValue(), 0.009999999776482582D, 50.0D);
/* 344 */       } else if (args[0].equals("life") && args[1] instanceof Double) {
/* 345 */         this.life = limit(((Double)args[1]).intValue(), 0, 1000);
/* 346 */       } else if (args[0].equals("random_life") && args[1] instanceof Double) {
/* 347 */         this.random_life = limit(((Double)args[1]).intValue(), 0, 1000);
/* 348 */       } else if (args[0].equals("spawn_x") && args[1] instanceof Double) {
/* 349 */         this.spawn_x = (float)limit(((Double)args[1]).doubleValue(), -50.0D, 50.0D);
/* 350 */       } else if (args[0].equals("spawn_y") && args[1] instanceof Double) {
/* 351 */         this.spawn_y = (float)limit(((Double)args[1]).doubleValue(), -50.0D, 50.0D);
/* 352 */       } else if (args[0].equals("spawn_z") && args[1] instanceof Double) {
/* 353 */         this.spawn_z = (float)limit(((Double)args[1]).doubleValue(), -50.0D, 50.0D);
/* 354 */       } else if (args[0].equals("random_spawn_x") && args[1] instanceof Double) {
/* 355 */         this.random_spawn_x = (float)limit(((Double)args[1]).doubleValue(), -50.0D, 50.0D);
/* 356 */       } else if (args[0].equals("random_spawn_y") && args[1] instanceof Double) {
/* 357 */         this.random_spawn_y = (float)limit(((Double)args[1]).doubleValue(), -50.0D, 50.0D);
/* 358 */       } else if (args[0].equals("random_spawn_z") && args[1] instanceof Double) {
/* 359 */         this.random_spawn_z = (float)limit(((Double)args[1]).doubleValue(), -50.0D, 50.0D);
/* 360 */       } else if (args[0].equals("fade") && args[1] instanceof Double) {
/* 361 */         this.fade = limit(((Double)args[1]).intValue(), 0, 100);
/* 362 */       } else if (args[0].equals("spawn_rate") && args[1] instanceof Double) {
/* 363 */         this.spawn_rate = limit(((Double)args[1]).intValue(), 1, 200);
/* 364 */       } else if (args[0].equals("collide") && args[1] instanceof Double) {
/* 365 */         this.collide = ((Boolean)args[1]).booleanValue();
/* 366 */       } else if (args[0].equals("selected_particle") && args[1] instanceof Double) {
/* 367 */         this.selected_particle = limit(((Double)args[1]).intValue(), 1, this.selected_max);
/* 368 */       } else if (args[0].equals("gravity") && args[1] instanceof Double) {
/* 369 */         this.gravity = (float)limit(((Double)args[1]).doubleValue(), -5.0D, 5.0D);
/*     */       
/*     */       }
/* 372 */       else if (args[0].equals("beam_enabled") && args[1] instanceof Boolean) {
/* 373 */         this.beam_enabled = ((Boolean)args[1]).booleanValue();
/* 374 */       } else if (args[0].equals("render_core") && args[1] instanceof Boolean) {
/* 375 */         this.render_core = ((Boolean)args[1]).booleanValue();
/* 376 */       } else if (args[0].equals("beam_red") && args[1] instanceof Double) {
/* 377 */         this.beam_red = limit(((Double)args[1]).intValue(), 0, 255);
/* 378 */       } else if (args[0].equals("beam_green") && args[1] instanceof Double) {
/* 379 */         this.beam_green = limit(((Double)args[1]).intValue(), 0, 255);
/* 380 */       } else if (args[0].equals("beam_blue") && args[1] instanceof Double) {
/* 381 */         this.beam_blue = limit(((Double)args[1]).intValue(), 0, 255);
/* 382 */       } else if (args[0].equals("beam_scale") && args[1] instanceof Double) {
/* 383 */         this.beam_scale = (float)limit(((Double)args[1]).doubleValue(), -0.0D, 5.0D);
/* 384 */       } else if (args[0].equals("beam_pitch") && args[1] instanceof Double) {
/* 385 */         this.beam_pitch = (float)limit(((Double)args[1]).doubleValue(), -180.0D, 180.0D);
/* 386 */       } else if (args[0].equals("beam_yaw") && args[1] instanceof Double) {
/* 387 */         this.beam_yaw = (float)limit(((Double)args[1]).doubleValue(), -180.0D, 180.0D);
/* 388 */       } else if (args[0].equals("beam_length") && args[1] instanceof Double) {
/* 389 */         this.beam_length = (float)limit(((Double)args[1]).doubleValue(), -0.0D, 320.0D);
/* 390 */       } else if (args[0].equals("beam_rotation") && args[1] instanceof Double) {
/* 391 */         this.beam_rotation = (float)limit(((Double)args[1]).doubleValue(), -1.0D, 1.0D);
/*     */       } else {
/* 393 */         return new Object[] { Boolean.valueOf(false) };
/*     */       } 
/*     */       
/* 396 */       this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 397 */       return new Object[] { Boolean.valueOf(true) };
/* 398 */     }  if (method.startsWith("getGeneratorState")) {
/* 399 */       Map<Object, Object> map = new HashMap<>();
/*     */ 
/*     */       
/* 402 */       map.put("particles_enabled", Boolean.valueOf(this.particles_enabled));
/* 403 */       map.put("red", Integer.valueOf(this.red));
/* 404 */       map.put("green", Integer.valueOf(this.green));
/* 405 */       map.put("blue", Integer.valueOf(this.blue));
/* 406 */       map.put("random_red", Integer.valueOf(this.random_red));
/* 407 */       map.put("random_green", Integer.valueOf(this.random_green));
/* 408 */       map.put("random_blue", Integer.valueOf(this.random_blue));
/* 409 */       map.put("motion_x", Float.valueOf(this.motion_x));
/* 410 */       map.put("motion_y", Float.valueOf(this.motion_y));
/* 411 */       map.put("motion_z", Float.valueOf(this.motion_z));
/* 412 */       map.put("random_motion_x", Float.valueOf(this.random_motion_x));
/* 413 */       map.put("random_motion_y", Float.valueOf(this.random_motion_y));
/* 414 */       map.put("random_motion_z", Float.valueOf(this.random_motion_z));
/* 415 */       map.put("scale", Float.valueOf(this.scale));
/* 416 */       map.put("random_scale", Float.valueOf(this.random_scale));
/* 417 */       map.put("life", Integer.valueOf(this.life));
/* 418 */       map.put("random_life", Integer.valueOf(this.random_life));
/* 419 */       map.put("spawn_x", Float.valueOf(this.spawn_x));
/* 420 */       map.put("spawn_y", Float.valueOf(this.spawn_y));
/* 421 */       map.put("spawn_z", Float.valueOf(this.spawn_z));
/* 422 */       map.put("random_spawn_x", Float.valueOf(this.random_spawn_x));
/* 423 */       map.put("random_spawn_y", Float.valueOf(this.random_spawn_y));
/* 424 */       map.put("random_spawn_z", Float.valueOf(this.random_spawn_z));
/* 425 */       map.put("fade", Integer.valueOf(this.fade));
/* 426 */       map.put("spawn_rate", Integer.valueOf(this.spawn_rate));
/* 427 */       map.put("collide", Boolean.valueOf(this.collide));
/* 428 */       map.put("selected_particle", Integer.valueOf(this.selected_particle));
/* 429 */       map.put("gravity", Float.valueOf(this.gravity));
/*     */ 
/*     */       
/* 432 */       map.put("beam_enabled", Boolean.valueOf(this.beam_enabled));
/* 433 */       map.put("render_core", Boolean.valueOf(this.render_core));
/* 434 */       map.put("beam_red", Integer.valueOf(this.beam_red));
/* 435 */       map.put("beam_green", Integer.valueOf(this.beam_green));
/* 436 */       map.put("beam_blue", Integer.valueOf(this.beam_blue));
/* 437 */       map.put("beam_scale", Float.valueOf(this.beam_scale));
/* 438 */       map.put("beam_pitch", Float.valueOf(this.beam_pitch));
/* 439 */       map.put("beam_yaw", Float.valueOf(this.beam_yaw));
/* 440 */       map.put("beam_length", Float.valueOf(this.beam_length));
/* 441 */       map.put("beam_rotation", Float.valueOf(this.beam_rotation));
/*     */       
/* 443 */       return new Object[] { map };
/* 444 */     }  if (method.startsWith("resetGeneratorState")) {
/* 445 */       this.particles_enabled = true;
/* 446 */       this.red = 0;
/* 447 */       this.green = 0;
/* 448 */       this.blue = 0;
/* 449 */       this.random_red = 0;
/* 450 */       this.random_green = 0;
/* 451 */       this.random_blue = 0;
/* 452 */       this.motion_x = 0.0F;
/* 453 */       this.motion_y = 0.0F;
/* 454 */       this.motion_z = 0.0F;
/* 455 */       this.random_motion_x = 0.0F;
/* 456 */       this.random_motion_y = 0.0F;
/* 457 */       this.random_motion_z = 0.0F;
/* 458 */       this.scale = 1.0F;
/* 459 */       this.random_scale = 0.0F;
/* 460 */       this.life = 100;
/* 461 */       this.random_life = 0;
/* 462 */       this.spawn_x = 0.0F;
/* 463 */       this.spawn_y = 0.0F;
/* 464 */       this.spawn_z = 0.0F;
/* 465 */       this.random_spawn_x = 0.0F;
/* 466 */       this.random_spawn_y = 0.0F;
/* 467 */       this.random_spawn_z = 0.0F;
/* 468 */       this.page = 1;
/* 469 */       this.fade = 0;
/* 470 */       this.spawn_rate = 1;
/* 471 */       this.collide = false;
/* 472 */       this.selected_particle = 1;
/* 473 */       this.gravity = 0.0F;
/*     */ 
/*     */       
/* 476 */       this.beam_enabled = false;
/* 477 */       this.render_core = false;
/*     */       
/* 479 */       this.beam_red = 0;
/* 480 */       this.beam_green = 0;
/* 481 */       this.beam_blue = 0;
/* 482 */       this.beam_scale = 1.0F;
/* 483 */       this.beam_pitch = 0.0F;
/* 484 */       this.beam_yaw = 0.0F;
/* 485 */       this.beam_length = 0.0F;
/* 486 */       this.beam_rotation = 0.0F;
/*     */       
/* 488 */       this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 489 */       return new Object[] { Boolean.valueOf(true) };
/*     */     } 
/*     */     
/* 492 */     return new Object[] { Integer.valueOf(0) };
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\TileParticleGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */