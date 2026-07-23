/*     */ package com.brandon3055.draconicevolution.common.tileentities.multiblocktiles;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.api.IExtendedRFStorage;
/*     */ import com.brandon3055.draconicevolution.client.handler.ParticleHandler;
/*     */ import com.brandon3055.draconicevolution.client.render.particle.Particles;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.blocks.multiblock.MultiblockHelper;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileObjectSync;
/*     */ import com.brandon3055.draconicevolution.integration.computers.IDEPeripheral;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.energy.tile.IEnergyAcceptor;
/*     */ import ic2.api.energy.tile.IEnergyEmitter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ public class TileEnergyPylon
/*     */   extends TileObjectSync
/*     */   implements IEnergyEmitter, IEnergyAcceptor, IExtendedRFStorage, IDEPeripheral
/*     */ {
/*     */   public boolean active = false;
/*     */   public boolean lastTickActive = false;
/*     */   public boolean reciveEnergy = false;
/*     */   public boolean lastTickReciveEnergy = false;
/*  37 */   public float modelRotation = 0.0F;
/*  38 */   public float modelScale = 0.0F;
/*  39 */   private List<MultiblockHelper.TileLocation> coreLocatios = new ArrayList<>();
/*  40 */   private int selectedCore = 0;
/*  41 */   private byte particleRate = 0;
/*  42 */   private byte lastTickParticleRate = 0;
/*  43 */   private int lastCheckCompOverride = 0;
/*  44 */   private int tick = 0;
/*     */   
/*     */   @Nullable
/*     */   private TileEnergyStorageCore.PylonLink coreLink;
/*     */   
/*     */   public void setReciveEnergy(boolean reciveEnergy) {
/*  50 */     if (this.reciveEnergy == reciveEnergy) {
/*     */       return;
/*     */     }
/*     */     
/*  54 */     this.reciveEnergy = reciveEnergy;
/*     */     
/*  56 */     if (!this.field_145850_b.field_72995_K) {
/*  57 */       unlinkAndUnregister();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  63 */     if (this.field_145850_b.field_72995_K) {
/*  64 */       if (this.active) {
/*  65 */         this.modelRotation += 1.5F;
/*  66 */         this.modelScale += !this.reciveEnergy ? -0.01F : 0.01F;
/*  67 */         if (this.modelScale < 0.0F && !this.reciveEnergy) this.modelScale = 10000.0F; 
/*  68 */         if (this.modelScale < 0.0F && this.reciveEnergy) this.modelScale = 0.0F; 
/*  69 */         spawnParticles();
/*     */       } else {
/*  71 */         this.modelScale = 0.5F;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  77 */     updateLink();
/*     */     
/*  79 */     this.tick++;
/*  80 */     if (this.tick % 20 == 0) {
/*  81 */       int cOut = (int)(getEnergyStored() / getMaxEnergyStored() * 15.0D);
/*  82 */       if (cOut != this.lastCheckCompOverride) {
/*  83 */         this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*  84 */         this.field_145850_b.func_147459_d(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*  85 */         this.field_145850_b.func_147459_d(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*  86 */         this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*  87 */         this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*  88 */         this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*  89 */         this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*  90 */         this.lastCheckCompOverride = cOut;
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     detectAndSendChanges();
/*  95 */     if (this.particleRate > 0) this.particleRate = (byte)(this.particleRate - 1); 
/*     */   }
/*     */   
/*     */   private void updateLink() {
/*  99 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 103 */     if (!this.active || (this.coreLink != null && !this.coreLink.isValid())) {
/* 104 */       unlinkAndUnregister();
/*     */     }
/*     */     
/* 107 */     if (this.active && this.coreLink == null) {
/* 108 */       TileEnergyStorageCore core = getMaster();
/* 109 */       if (core != null) {
/* 110 */         this.coreLink = core.linkPylon(this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 117 */     setAddedToChunk(false); unlinkAndUnregister();
/* 118 */     super.func_145843_s();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onChunkUnload() {
/* 123 */     setAddedToChunk(false); unlinkAndUnregister();
/* 124 */     super.onChunkUnload();
/*     */   }
/*     */   
/*     */   private void unlinkAndUnregister() {
/* 128 */     TileEnergyStorageCore.PylonLink link = this.coreLink;
/*     */     
/* 130 */     if (link != null) {
/* 131 */       this.coreLink = null;
/* 132 */       link.unlinkAndUnregister();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onActivated() {
/* 137 */     if (!this.active) {
/* 138 */       this.active = isValidStructure();
/*     */     }
/* 140 */     findCores();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MultiblockHelper.TileLocation getMasterLocation() {
/* 145 */     if (this.coreLocatios.isEmpty()) return null; 
/* 146 */     if (this.selectedCore >= this.coreLocatios.size()) this.selectedCore = this.coreLocatios.size() - 1; 
/* 147 */     return this.coreLocatios.get(this.selectedCore);
/*     */   }
/*     */   
/*     */   private TileEnergyStorageCore getMaster() {
/* 151 */     MultiblockHelper.TileLocation core = getMasterLocation();
/* 152 */     if (core == null) {
/* 153 */       return null;
/*     */     }
/* 155 */     TileEntity tile = this.field_145850_b.func_147438_o(core.getXCoord(), core.getYCoord(), core.getZCoord());
/* 156 */     return (tile instanceof TileEnergyStorageCore) ? (TileEnergyStorageCore)tile : null;
/*     */   }
/*     */   
/*     */   private void findCores() {
/* 160 */     int yMod = (this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d, this.field_145849_e) == 1) ? 15 : -15;
/* 161 */     int range = 15;
/* 162 */     List<MultiblockHelper.TileLocation> locations = new ArrayList<>();
/* 163 */     for (int x = this.field_145851_c - range; x <= this.field_145851_c + range; x++) {
/* 164 */       for (int y = this.field_145848_d + yMod - range; y <= this.field_145848_d + yMod + range; y++) {
/* 165 */         for (int z = this.field_145849_e - range; z <= this.field_145849_e + range; z++) {
/* 166 */           if (this.field_145850_b.func_147439_a(x, y, z) == ModBlocks.energyStorageCore) {
/* 167 */             locations.add(new MultiblockHelper.TileLocation(x, y, z));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 173 */     if (!locations.equals(this.coreLocatios)) {
/* 174 */       this.coreLocatios.clear();
/* 175 */       this.coreLocatios.addAll(locations);
/* 176 */       this.selectedCore = (this.selectedCore >= this.coreLocatios.size()) ? 0 : this.selectedCore;
/* 177 */       this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 178 */       updateLink();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void nextCore() {
/* 183 */     findCores();
/* 184 */     this.selectedCore++;
/* 185 */     if (this.selectedCore >= this.coreLocatios.size()) this.selectedCore = 0; 
/* 186 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 187 */     updateLink();
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private void spawnParticles() {
/* 192 */     Random rand = this.field_145850_b.field_73012_v;
/* 193 */     if (getMaster() == null || !getMaster().isOnline())
/*     */       return; 
/* 195 */     int x = (getMaster()).field_145851_c;
/* 196 */     int y = (getMaster()).field_145848_d;
/* 197 */     int z = (getMaster()).field_145849_e;
/* 198 */     int cYCoord = (this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d, this.field_145849_e) == 1) ? (this.field_145848_d + 1) : (this.field_145848_d - 1);
/*     */     
/* 200 */     float disMod = (getMaster().getTier() == 0) ? 0.5F : ((getMaster().getTier() == 1) ? 1.0F : ((getMaster().getTier() == 2) ? 1.0F : ((getMaster().getTier() == 3) ? 2.0F : ((getMaster().getTier() == 4) ? 2.0F : ((getMaster().getTier() == 5) ? 3.0F : 4.0F)))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     if (this.particleRate > 20) this.particleRate = 20; 
/* 208 */     if (!this.reciveEnergy) {
/* 209 */       double spawnX = x + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 210 */       double spawnY = y + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 211 */       double spawnZ = z + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 212 */       double targetX = this.field_145851_c + 0.5D;
/* 213 */       double targetY = cYCoord + 0.5D;
/* 214 */       double targetZ = this.field_145849_e + 0.5D;
/* 215 */       if (rand.nextFloat() < 0.05F) {
/* 216 */         Particles.EnergyTransferParticle passiveParticle = new Particles.EnergyTransferParticle(this.field_145850_b, spawnX, spawnY, spawnZ, targetX, targetY, targetZ, true);
/* 217 */         ParticleHandler.spawnCustomParticle((EntityFX)passiveParticle, 35.0D);
/*     */       } 
/* 219 */       if (this.particleRate > 0) {
/* 220 */         if (this.particleRate > 10) {
/* 221 */           for (int i = 0; i <= this.particleRate / 10; i++) {
/* 222 */             spawnX = x + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 223 */             spawnY = y + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 224 */             spawnZ = z + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 225 */             Particles.EnergyTransferParticle passiveParticle = new Particles.EnergyTransferParticle(this.field_145850_b, spawnX, spawnY, spawnZ, targetX, targetY, targetZ, false);
/* 226 */             ParticleHandler.spawnCustomParticle((EntityFX)passiveParticle, 35.0D);
/*     */           } 
/* 228 */         } else if (rand.nextInt(Math.max(1, 10 - this.particleRate)) == 0) {
/* 229 */           spawnX = x + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 230 */           spawnY = y + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 231 */           spawnZ = z + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 232 */           Particles.EnergyTransferParticle passiveParticle = new Particles.EnergyTransferParticle(this.field_145850_b, spawnX, spawnY, spawnZ, targetX, targetY, targetZ, false);
/* 233 */           ParticleHandler.spawnCustomParticle((EntityFX)passiveParticle, 35.0D);
/*     */         } 
/*     */       }
/*     */     } else {
/*     */       
/* 238 */       double targetX = x + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 239 */       double targetY = y + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 240 */       double targetZ = z + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 241 */       double spawnX = this.field_145851_c + 0.5D;
/* 242 */       double spawnY = cYCoord + 0.5D;
/* 243 */       double spawnZ = this.field_145849_e + 0.5D;
/* 244 */       if (rand.nextFloat() < 0.05F) {
/* 245 */         Particles.EnergyTransferParticle passiveParticle = new Particles.EnergyTransferParticle(this.field_145850_b, spawnX, spawnY, spawnZ, targetX, targetY, targetZ, true);
/* 246 */         ParticleHandler.spawnCustomParticle((EntityFX)passiveParticle, 35.0D);
/*     */       } 
/*     */       
/* 249 */       if (this.particleRate > 0) {
/* 250 */         if (this.particleRate > 10) {
/* 251 */           for (int i = 0; i <= this.particleRate / 10; i++) {
/* 252 */             targetX = x + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 253 */             targetY = y + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 254 */             targetZ = z + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 255 */             Particles.EnergyTransferParticle passiveParticle = new Particles.EnergyTransferParticle(this.field_145850_b, spawnX, spawnY, spawnZ, targetX, targetY, targetZ, false);
/* 256 */             ParticleHandler.spawnCustomParticle((EntityFX)passiveParticle, 35.0D);
/*     */           } 
/* 258 */         } else if (rand.nextInt(Math.max(1, 10 - this.particleRate)) == 0) {
/* 259 */           targetX = x + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 260 */           targetY = y + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 261 */           targetZ = z + 0.5D - disMod + (rand.nextFloat() * disMod * 2.0F);
/* 262 */           Particles.EnergyTransferParticle passiveParticle = new Particles.EnergyTransferParticle(this.field_145850_b, spawnX, spawnY, spawnZ, targetX, targetY, targetZ, false);
/* 263 */           ParticleHandler.spawnCustomParticle((EntityFX)passiveParticle, 35.0D);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isValidStructure() {
/* 270 */     return ((isGlass(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e) || isGlass(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e)) && (!isGlass(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e) || !isGlass(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e)));
/*     */   }
/*     */   
/*     */   private boolean isGlass(int x, int y, int z) {
/* 274 */     return (this.field_145850_b.func_147439_a(x, y, z) == ModBlocks.invisibleMultiblock && this.field_145850_b.func_72805_g(x, y, z) == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound compound) {
/* 279 */     super.func_145839_a(compound);
/* 280 */     this.active = compound.func_74767_n("Active");
/* 281 */     this.reciveEnergy = compound.func_74767_n("Input");
/* 282 */     int i = compound.func_74762_e("Cores");
/* 283 */     List<MultiblockHelper.TileLocation> list = new ArrayList<>();
/* 284 */     for (int j = 0; j < i; j++) {
/* 285 */       MultiblockHelper.TileLocation l = new MultiblockHelper.TileLocation();
/* 286 */       l.readFromNBT(compound, "Core" + j);
/* 287 */       list.add(l);
/*     */     } 
/* 289 */     this.coreLocatios = list;
/* 290 */     this.selectedCore = compound.func_74762_e("SelectedCore");
/* 291 */     this.particleRate = compound.func_74771_c("ParticleRate");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound compound) {
/* 297 */     super.func_145841_b(compound);
/* 298 */     compound.func_74757_a("Active", this.active);
/* 299 */     compound.func_74757_a("Input", this.reciveEnergy);
/* 300 */     int i = this.coreLocatios.size();
/* 301 */     compound.func_74768_a("Cores", i);
/* 302 */     for (int j = 0; j < i; j++) {
/* 303 */       ((MultiblockHelper.TileLocation)this.coreLocatios.get(j)).writeToNBT(compound, "Core" + j);
/*     */     }
/* 305 */     compound.func_74768_a("SelectedCore", this.selectedCore);
/* 306 */     compound.func_74774_a("ParticleRate", this.particleRate);
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet func_145844_m() {
/* 311 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 312 */     func_145841_b(nbttagcompound);
/* 313 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, nbttagcompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/* 318 */     func_145839_a(pkt.func_148857_g());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
/* 326 */     return !this.reciveEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/* 334 */     return this.reciveEnergy;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getRenderBoundingBox() {
/* 339 */     return INFINITE_EXTENT_AABB;
/*     */   }
/*     */   
/*     */   private void detectAndSendChanges() {
/* 343 */     if (this.lastTickActive != this.active)
/* 344 */       this.lastTickActive = ((Boolean)sendObjectToClient((byte)6, 0, Boolean.valueOf(this.active), new NetworkRegistry.TargetPoint(this.field_145850_b.field_73011_w.field_76574_g, this.field_145851_c, this.field_145848_d, this.field_145849_e, 256.0D))).booleanValue(); 
/* 345 */     if (this.lastTickReciveEnergy != this.reciveEnergy)
/* 346 */       this.lastTickReciveEnergy = ((Boolean)sendObjectToClient((byte)6, 1, Boolean.valueOf(this.reciveEnergy), new NetworkRegistry.TargetPoint(this.field_145850_b.field_73011_w.field_76574_g, this.field_145851_c, this.field_145848_d, this.field_145849_e, 256.0D))).booleanValue(); 
/* 347 */     if (this.lastTickParticleRate != this.particleRate) {
/* 348 */       this.lastTickParticleRate = ((Byte)sendObjectToClient((byte)0, 2, Byte.valueOf(this.particleRate))).byteValue();
/*     */     }
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void receiveObjectFromServer(int index, Object object) {
/* 354 */     switch (index) {
/*     */       case 0:
/* 356 */         this.active = ((Boolean)object).booleanValue();
/*     */         break;
/*     */       case 1:
/* 359 */         this.reciveEnergy = ((Boolean)object).booleanValue();
/*     */         break;
/*     */       case 2:
/* 362 */         this.particleRate = ((Byte)object).byteValue();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double getEnergyStored() {
/* 369 */     return (getMaster() != null) ? getMaster().getEnergyStored() : 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxEnergyStored() {
/* 374 */     return (getMaster() != null) ? getMaster().getMaxEnergyStored() : 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getExtendedStorage() {
/* 379 */     return (getMaster() != null) ? getMaster().getEnergyStored() : 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getExtendedCapacity() {
/* 384 */     return (getMaster() != null) ? getMaster().getMaxEnergyStored() : 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 389 */     return "draconic_rf_storage";
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getMethodNames() {
/* 394 */     return new String[] { "getEnergyStored", "getMaxEnergyStored" };
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] callMethod(String method, Object... args) {
/* 399 */     if (method.equals("getEnergyStored")) return new Object[] { Long.valueOf(getExtendedStorage()) }; 
/* 400 */     if (method.equals("getMaxEnergyStored")) return new Object[] { Long.valueOf(getExtendedCapacity()) }; 
/* 401 */     return new Object[0];
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\multiblocktiles\TileEnergyPylon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */