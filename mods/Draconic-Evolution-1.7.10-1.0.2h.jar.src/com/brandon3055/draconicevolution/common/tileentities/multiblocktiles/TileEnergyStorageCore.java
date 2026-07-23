/*      */ package com.brandon3055.draconicevolution.common.tileentities.multiblocktiles;
/*      */ 
/*      */ import com.brandon3055.brandonscore.common.utills.IC2Helper;
/*      */ import com.brandon3055.draconicevolution.api.DraconicEvolutionTags;
/*      */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*      */ import com.brandon3055.draconicevolution.common.blocks.multiblock.MultiblockHelper;
/*      */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*      */ import com.brandon3055.draconicevolution.common.tileentities.TileObjectSync;
/*      */ import com.brandon3055.draconicevolution.common.tileentities.TileParticleGenerator;
/*      */ import com.brandon3055.draconicevolution.common.tileentities.eu.TileEnergyHelper;
/*      */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*      */ import com.gamerforea.mpp.common.util.FixedTileEntityCache;
/*      */ import com.google.common.collect.Lists;
/*      */ import cpw.mods.fml.common.network.NetworkRegistry;
/*      */ import ic2.api.energy.tile.IEnergySink;
/*      */ import ic2.api.energy.tile.IEnergySource;
/*      */ import ic2.api.energy.tile.IEnergyTile;
/*      */ import ic2.api.energy.tile.IMetaDelegate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.NetworkManager;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraftforge.common.util.ForgeDirection;
/*      */ 
/*      */ public class TileEnergyStorageCore
/*      */   extends TileObjectSync
/*      */   implements IEnergySource, IEnergySink, IMetaDelegate
/*      */ {
/*   38 */   private final TileEnergyHelper helper = new TileEnergyHelper((IEnergyTile)this);
/*   39 */   private final ArrayList<PylonLink> linkedPylons = new ArrayList<>();
/*   40 */   private List<TileEntity> energyTiles = Lists.newArrayList((Object[])new TileEntity[] { (TileEntity)this });
/*      */   
/*   42 */   protected MultiblockHelper.TileLocation[] stabilizers = new MultiblockHelper.TileLocation[4];
/*   43 */   protected int tier = 0;
/*      */   protected boolean online = false;
/*   45 */   public float modelRotation = 0.0F;
/*   46 */   private long energy = 0L;
/*   47 */   private long capacity = 0L;
/*   48 */   private long lastTickCapacity = 0L;
/*      */   
/*      */   public TileEnergyStorageCore() {
/*   51 */     for (int i = 0; i < this.stabilizers.length; i++) {
/*   52 */       this.stabilizers[i] = new MultiblockHelper.TileLocation();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_145845_h() {
/*   59 */     if (!this.online) {
/*   60 */       unregisterEnergyNet();
/*      */       
/*      */       return;
/*      */     } 
/*   64 */     if (this.field_145850_b.field_72995_K) {
/*   65 */       this.modelRotation += 0.5F;
/*      */     } else {
/*   67 */       validatePylons();
/*   68 */       registerEnergyNet();
/*   69 */       detectAndRendChanges();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_145843_s() {
/*   75 */     setAddedToChunk(false); unlinkAndUnregisterEnergyNet();
/*   76 */     super.func_145843_s();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onChunkUnload() {
/*   81 */     setAddedToChunk(false); unlinkAndUnregisterEnergyNet();
/*   82 */     super.onChunkUnload();
/*      */   }
/*      */   
/*      */   private void registerEnergyNet() {
/*   86 */     if (this.helper.isRegistered() || this.linkedPylons.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/*   90 */     ArrayList<TileEntity> energyTiles = new ArrayList<>(1 + this.linkedPylons.size());
/*   91 */     energyTiles.add(this);
/*      */     
/*   93 */     for (Iterator<PylonLink> iterator = this.linkedPylons.iterator(); iterator.hasNext(); ) {
/*   94 */       PylonLink link = iterator.next();
/*   95 */       TileEnergyPylon pylon = link.getPylon();
/*      */       
/*   97 */       if (pylon != null && link.isValid()) {
/*   98 */         energyTiles.add(pylon); continue;
/*      */       } 
/*  100 */       link.unlink();
/*  101 */       iterator.remove();
/*      */     } 
/*      */ 
/*      */     
/*  105 */     if (energyTiles.size() > 1) {
/*  106 */       this.energyTiles = energyTiles;
/*  107 */       this.helper.registerEnergyNet();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void unregisterEnergyNet() {
/*  112 */     this.helper.unregisterEnergyNet();
/*  113 */     if (this.energyTiles.size() > 1) {
/*  114 */       this.energyTiles = Lists.newArrayList((Object[])new TileEntity[] { (TileEntity)this });
/*      */     }
/*      */   }
/*      */   
/*      */   private void unlinkAndUnregisterEnergyNet() {
/*  119 */     this.linkedPylons.forEach(rec$ -> ((PylonLink)rec$).unlink());
/*  120 */     this.linkedPylons.clear();
/*  121 */     unregisterEnergyNet();
/*      */   }
/*      */   
/*      */   public PylonLink linkPylon(TileEnergyPylon pylon) {
/*  125 */     PylonLink result = null;
/*  126 */     boolean dirty = false;
/*      */     
/*  128 */     for (Iterator<PylonLink> iterator = this.linkedPylons.iterator(); iterator.hasNext(); ) {
/*  129 */       PylonLink link = iterator.next();
/*  130 */       TileEnergyPylon linkedPylon = link.getPylon();
/*      */       
/*  132 */       if (linkedPylon == pylon) {
/*  133 */         result = link; continue;
/*  134 */       }  if (linkedPylon == null) {
/*  135 */         link.unlink();
/*  136 */         iterator.remove();
/*  137 */         dirty = true;
/*      */       } 
/*      */     } 
/*      */     
/*  141 */     if (result == null) {
/*  142 */       result = new PylonLink(this, pylon);
/*  143 */       this.linkedPylons.add(result);
/*  144 */       dirty = true;
/*      */     } 
/*      */     
/*  147 */     if (dirty) {
/*  148 */       unregisterEnergyNet();
/*      */     }
/*      */     
/*  151 */     return result;
/*      */   }
/*      */   
/*      */   private void validatePylons() {
/*  155 */     boolean dirty = false;
/*      */     
/*  157 */     for (Iterator<PylonLink> iterator = this.linkedPylons.iterator(); iterator.hasNext(); ) {
/*  158 */       PylonLink link = iterator.next();
/*      */       
/*  160 */       if (!link.isValid()) {
/*  161 */         link.unlink();
/*  162 */         iterator.remove();
/*  163 */         dirty = true;
/*      */       } 
/*      */     } 
/*      */     
/*  167 */     if (dirty) {
/*  168 */       unregisterEnergyNet();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOfferedEnergy() {
/*  175 */     return this.energy;
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawEnergy(double amount) {
/*  180 */     long energyExtracted = Math.min(this.energy, IC2Helper.ceilEu(amount));
/*  181 */     if (energyExtracted > 0L) {
/*  182 */       this.energy -= energyExtracted;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSourceTier() {
/*  188 */     return Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
/*  196 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDemandedEnergy() {
/*  202 */     return (this.energy < this.capacity) ? (this.capacity - this.energy) : 0.0D;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSinkTier() {
/*  207 */     return Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */   
/*      */   public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
/*  212 */     long energyReceived = Math.min(this.capacity - this.energy, IC2Helper.floorEu(amount));
/*  213 */     if (energyReceived <= 0L) {
/*  214 */       return 0.0D;
/*      */     }
/*      */     
/*  217 */     this.energy += energyReceived;
/*  218 */     return amount - energyReceived;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/*  226 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public List<TileEntity> getSubTiles() {
/*  232 */     return this.energyTiles;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean tryActivate() {
/*  240 */     if (!findStabalyzers()) return false; 
/*  241 */     if (!setTier(false)) return false; 
/*  242 */     if (!testOrActivateStructureIfValid(false, false)) return false; 
/*  243 */     this.online = true;
/*  244 */     if (!testOrActivateStructureIfValid(false, true)) {
/*  245 */       this.online = false;
/*  246 */       deactivateStabilizers();
/*  247 */       return false;
/*      */     } 
/*  249 */     activateStabilizers();
/*  250 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*  251 */     return true;
/*      */   }
/*      */   
/*      */   public boolean creativeActivate() {
/*  255 */     if (!findStabalyzers()) return false; 
/*  256 */     if (!setTier(false)) return false; 
/*  257 */     if (!testOrActivateStructureIfValid(true, false)) return false; 
/*  258 */     this.online = true;
/*  259 */     if (!testOrActivateStructureIfValid(false, true)) {
/*  260 */       this.online = false;
/*  261 */       deactivateStabilizers();
/*  262 */       return false;
/*      */     } 
/*  264 */     activateStabilizers();
/*  265 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*  266 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isStructureStillValid(boolean update) {
/*  270 */     if (!checkStabilizers()) this.online = false; 
/*  271 */     if (!testOrActivateStructureIfValid(false, false)) this.online = false; 
/*  272 */     if (!areStabilizersActive()) this.online = false; 
/*  273 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*  274 */     if (!this.online) deactivateStabilizers(); 
/*  275 */     if (update && !this.online) reIntegrate();
/*      */     
/*  277 */     return this.online;
/*      */   }
/*      */   
/*      */   private void reIntegrate() {
/*  281 */     for (int x = this.field_145851_c - 1; x <= this.field_145851_c + 1; x++) {
/*  282 */       for (int y = this.field_145848_d - 1; y <= this.field_145848_d + 1; y++) {
/*  283 */         for (int z = this.field_145849_e - 1; z <= this.field_145849_e + 1; z++) {
/*  284 */           if (this.field_145850_b.func_147439_a(x, y, z) == ModBlocks.invisibleMultiblock) {
/*  285 */             if (this.field_145850_b.func_72805_g(x, y, z) == 0) {
/*  286 */               DraconicEvolutionTags.Blocks.DRACONIUM.set(this.field_145850_b, x, y, z);
/*  287 */             } else if (this.field_145850_b.func_72805_g(x, y, z) == 1) {
/*  288 */               this.field_145850_b.func_147465_d(x, y, z, BalanceConfigHandler.energyStorageStructureBlock, BalanceConfigHandler.energyStorageStructureBlockMetadata, 3);
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean findStabalyzers() {
/*  297 */     boolean flag = true; int x;
/*  298 */     for (x = this.field_145851_c; x <= this.field_145851_c + 11; x++) {
/*  299 */       if (this.field_145850_b.func_147439_a(x, this.field_145848_d, this.field_145849_e) == ModBlocks.particleGenerator) {
/*  300 */         if (this.field_145850_b.func_72805_g(x, this.field_145848_d, this.field_145849_e) == 1) {
/*  301 */           flag = false;
/*      */           break;
/*      */         } 
/*  304 */         this.stabilizers[0] = new MultiblockHelper.TileLocation(x, this.field_145848_d, this.field_145849_e); break;
/*      */       } 
/*  306 */       if (x == this.field_145851_c + 11) {
/*  307 */         flag = false;
/*      */       }
/*      */     } 
/*      */     
/*  311 */     for (x = this.field_145851_c; x >= this.field_145851_c - 11; x--) {
/*  312 */       if (this.field_145850_b.func_147439_a(x, this.field_145848_d, this.field_145849_e) == ModBlocks.particleGenerator) {
/*  313 */         if (this.field_145850_b.func_72805_g(x, this.field_145848_d, this.field_145849_e) == 1) {
/*  314 */           flag = false;
/*      */           break;
/*      */         } 
/*  317 */         this.stabilizers[1] = new MultiblockHelper.TileLocation(x, this.field_145848_d, this.field_145849_e); break;
/*      */       } 
/*  319 */       if (x == this.field_145851_c - 11)
/*  320 */         flag = false; 
/*      */     } 
/*      */     int z;
/*  323 */     for (z = this.field_145849_e; z <= this.field_145849_e + 11; z++) {
/*  324 */       if (this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, z) == ModBlocks.particleGenerator) {
/*  325 */         if (this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d, z) == 1) {
/*  326 */           flag = false;
/*      */           break;
/*      */         } 
/*  329 */         this.stabilizers[2] = new MultiblockHelper.TileLocation(this.field_145851_c, this.field_145848_d, z); break;
/*      */       } 
/*  331 */       if (z == this.field_145849_e + 11) {
/*  332 */         flag = false;
/*      */       }
/*      */     } 
/*  335 */     for (z = this.field_145849_e; z >= this.field_145849_e - 11; z--) {
/*  336 */       if (this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, z) == ModBlocks.particleGenerator) {
/*  337 */         if (this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d, z) == 1) {
/*  338 */           flag = false;
/*      */           break;
/*      */         } 
/*  341 */         this.stabilizers[3] = new MultiblockHelper.TileLocation(this.field_145851_c, this.field_145848_d, z); break;
/*      */       } 
/*  343 */       if (z == this.field_145849_e - 11) {
/*  344 */         flag = false;
/*      */       }
/*      */     } 
/*  347 */     return flag;
/*      */   }
/*      */   
/*      */   private boolean setTier(boolean force) {
/*  351 */     if (force) return true; 
/*  352 */     int xPos = 0;
/*  353 */     int xNeg = 0;
/*  354 */     int yPos = 0;
/*  355 */     int yNeg = 0;
/*  356 */     int zPos = 0;
/*  357 */     int zNeg = 0;
/*  358 */     int range = 5;
/*      */     int x;
/*  360 */     for (x = 0; x <= range; x++) {
/*  361 */       if (testForOrActivateDraconium(this.field_145851_c + x, this.field_145848_d, this.field_145849_e, false, false)) {
/*  362 */         xPos = x;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  367 */     for (x = 0; x <= range; x++) {
/*  368 */       if (testForOrActivateDraconium(this.field_145851_c - x, this.field_145848_d, this.field_145849_e, false, false)) {
/*  369 */         xNeg = x;
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     int y;
/*  374 */     for (y = 0; y <= range; y++) {
/*  375 */       if (testForOrActivateDraconium(this.field_145851_c, this.field_145848_d + y, this.field_145849_e, false, false)) {
/*  376 */         yPos = y;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  381 */     for (y = 0; y <= range; y++) {
/*  382 */       if (testForOrActivateDraconium(this.field_145851_c, this.field_145848_d - y, this.field_145849_e, false, false)) {
/*  383 */         yNeg = y;
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     int z;
/*  388 */     for (z = 0; z <= range; z++) {
/*  389 */       if (testForOrActivateDraconium(this.field_145851_c, this.field_145848_d, this.field_145849_e + z, false, false)) {
/*  390 */         zPos = z;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  395 */     for (z = 0; z <= range; z++) {
/*  396 */       if (testForOrActivateDraconium(this.field_145851_c, this.field_145848_d, this.field_145849_e - z, false, false)) {
/*  397 */         zNeg = z;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  402 */     if (zNeg != zPos || zNeg != yNeg || zNeg != yPos || zNeg != xNeg || zNeg != xPos) return false;
/*      */     
/*  404 */     this.tier = xPos;
/*  405 */     if (this.tier > 1) this.tier++; 
/*  406 */     if (this.tier == 1 && 
/*  407 */       testForOrActivateDraconium(this.field_145851_c + 1, this.field_145848_d + 1, this.field_145849_e, false, false)) {
/*  408 */       this.tier = 2;
/*      */     }
/*  410 */     return true;
/*      */   }
/*      */   
/*      */   private boolean testOrActivateStructureIfValid(boolean setBlocks, boolean activate) {
/*  414 */     switch (this.tier) {
/*      */       case 0:
/*  416 */         if (!testOrActivateRect(1, 1, 1, StructureBlock.AIR, setBlocks, activate)) return false; 
/*      */         break;
/*      */       case 1:
/*  419 */         if (!testForOrActivateDraconium(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e, setBlocks, activate) || !testForOrActivateDraconium(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e, setBlocks, activate) || !testForOrActivateDraconium(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e, setBlocks, activate) || !testForOrActivateDraconium(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, setBlocks, activate) || !testForOrActivateDraconium(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1, setBlocks, activate) || !testForOrActivateDraconium(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1, setBlocks, activate))
/*  420 */           return false; 
/*  421 */         if (!isReplacable(this.field_145851_c + 1, this.field_145848_d + 1, this.field_145849_e, setBlocks) || !isReplacable(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e + 1, setBlocks) || !isReplacable(this.field_145851_c - 1, this.field_145848_d + 1, this.field_145849_e, setBlocks) || !isReplacable(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e - 1, setBlocks) || !isReplacable(this.field_145851_c + 1, this.field_145848_d - 1, this.field_145849_e, setBlocks) || !isReplacable(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e + 1, setBlocks) || !isReplacable(this.field_145851_c - 1, this.field_145848_d - 1, this.field_145849_e, setBlocks) || !isReplacable(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e - 1, setBlocks) || !isReplacable(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e + 1, setBlocks) || !isReplacable(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e - 1, setBlocks) || !isReplacable(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e - 1, setBlocks) || !isReplacable(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e + 1, setBlocks))
/*  422 */           return false; 
/*  423 */         if (!isReplacable(this.field_145851_c + 1, this.field_145848_d + 1, this.field_145849_e + 1, setBlocks) || !isReplacable(this.field_145851_c - 1, this.field_145848_d + 1, this.field_145849_e - 1, setBlocks) || !isReplacable(this.field_145851_c + 1, this.field_145848_d + 1, this.field_145849_e - 1, setBlocks) || !isReplacable(this.field_145851_c - 1, this.field_145848_d + 1, this.field_145849_e + 1, setBlocks) || !isReplacable(this.field_145851_c + 1, this.field_145848_d - 1, this.field_145849_e + 1, setBlocks) || !isReplacable(this.field_145851_c - 1, this.field_145848_d - 1, this.field_145849_e - 1, setBlocks) || !isReplacable(this.field_145851_c + 1, this.field_145848_d - 1, this.field_145849_e - 1, setBlocks) || !isReplacable(this.field_145851_c - 1, this.field_145848_d - 1, this.field_145849_e + 1, setBlocks))
/*  424 */           return false; 
/*      */         break;
/*      */       case 2:
/*  427 */         if (!testOrActivateRect(1, 1, 1, StructureBlock.DRACONIUM, setBlocks, activate)) return false; 
/*      */         break;
/*      */       case 3:
/*  430 */         if (!testOrActivateSides(1, StructureBlock.DRACONIUM, setBlocks, activate)) return false; 
/*  431 */         if (!testOrActivateRect(1, 1, 1, StructureBlock.REDSTONE, setBlocks, activate)) return false; 
/*      */         break;
/*      */       case 4:
/*  434 */         if (!testOrActivateSides(2, StructureBlock.DRACONIUM, setBlocks, activate)) return false; 
/*  435 */         if (!testOrActivateRect(2, 1, 1, StructureBlock.REDSTONE, setBlocks, activate)) return false; 
/*  436 */         if (!testOrActivateRect(1, 2, 1, StructureBlock.REDSTONE, setBlocks, activate)) return false; 
/*  437 */         if (!testOrActivateRect(1, 1, 2, StructureBlock.REDSTONE, setBlocks, activate)) return false; 
/*  438 */         if (!testOrActivateRings(2, 2, StructureBlock.DRACONIUM, setBlocks, activate)) return false; 
/*      */         break;
/*      */       case 5:
/*  441 */         if (!testOrActivateSides(3, StructureBlock.DRACONIUM, setBlocks, activate)) return false; 
/*  442 */         if (!testOrActivateSides(2, StructureBlock.REDSTONE, setBlocks, activate)) return false; 
/*  443 */         if (!testOrActivateRect(2, 2, 2, StructureBlock.REDSTONE, setBlocks, activate)) return false; 
/*  444 */         if (!testOrActivateRings(2, 3, StructureBlock.DRACONIUM, setBlocks, activate)) return false; 
/*      */         break;
/*      */       case 6:
/*  447 */         if (!testOrActivateSides(4, StructureBlock.DRACONIUM, setBlocks, activate)) return false; 
/*  448 */         if (!testOrActivateSides(3, StructureBlock.REDSTONE, setBlocks, activate)) return false; 
/*  449 */         if (!testOrActivateRect(3, 2, 2, StructureBlock.REDSTONE, setBlocks, activate)) return false; 
/*  450 */         if (!testOrActivateRect(2, 3, 2, StructureBlock.REDSTONE, setBlocks, activate)) return false; 
/*  451 */         if (!testOrActivateRect(2, 2, 3, StructureBlock.REDSTONE, setBlocks, activate)) return false; 
/*  452 */         if (!testOrActivateRings(2, 4, StructureBlock.DRACONIUM, setBlocks, activate)) return false; 
/*  453 */         if (!testOrActivateRings(3, 3, StructureBlock.DRACONIUM, setBlocks, activate)) return false; 
/*      */         break;
/*      */     } 
/*  456 */     return true;
/*      */   }
/*      */   
/*      */   private boolean testOrActivateRect(int xDim, int yDim, int zDim, StructureBlock block, boolean set, boolean activate) {
/*  460 */     for (int x = this.field_145851_c - xDim; x <= this.field_145851_c + xDim; x++) {
/*  461 */       for (int y = this.field_145848_d - yDim; y <= this.field_145848_d + yDim; y++) {
/*  462 */         for (int z = this.field_145849_e - zDim; z <= this.field_145849_e + zDim; z++) {
/*      */           
/*  464 */           if (block == StructureBlock.AIR) {
/*  465 */             if ((x != this.field_145851_c || y != this.field_145848_d || z != this.field_145849_e) && !isReplacable(x, y, z, set))
/*  466 */               return false; 
/*  467 */           } else if (block == StructureBlock.REDSTONE) {
/*  468 */             if ((x != this.field_145851_c || y != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateRedstone(x, y, z, set, activate))
/*  469 */               return false; 
/*  470 */           } else if (block == StructureBlock.DRACONIUM) {
/*  471 */             if ((x != this.field_145851_c || y != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateDraconium(x, y, z, set, activate))
/*  472 */               return false; 
/*      */           } else {
/*  474 */             LogHelper.error("Invalid String In Multiblock Structure Code!!!");
/*  475 */             return false;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  480 */     return true;
/*      */   }
/*      */   private boolean testOrActivateRings(int size, int dist, StructureBlock block, boolean set, boolean activate) {
/*      */     int i;
/*  484 */     for (i = this.field_145848_d - size; i <= this.field_145848_d + size; i++) {
/*  485 */       for (int z = this.field_145849_e - size; z <= this.field_145849_e + size; z++) {
/*      */         
/*  487 */         if (i == this.field_145848_d - size || i == this.field_145848_d + size || z == this.field_145849_e - size || z == this.field_145849_e + size) {
/*  488 */           if (block == StructureBlock.AIR) {
/*  489 */             if ((this.field_145851_c + dist != this.field_145851_c || i != this.field_145848_d || z != this.field_145849_e) && !isReplacable(this.field_145851_c + dist, i, z, set))
/*  490 */               return false; 
/*  491 */           } else if (block == StructureBlock.REDSTONE) {
/*  492 */             if ((this.field_145851_c + dist != this.field_145851_c || i != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateRedstone(this.field_145851_c + dist, i, z, set, activate))
/*  493 */               return false; 
/*  494 */           } else if (block == StructureBlock.DRACONIUM) {
/*  495 */             if ((this.field_145851_c + dist != this.field_145851_c || i != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateDraconium(this.field_145851_c + dist, i, z, set, activate))
/*  496 */               return false; 
/*      */           } else {
/*  498 */             LogHelper.error("Invalid String In Multiblock Structure Code!!!");
/*  499 */             return false;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*  504 */     for (i = this.field_145848_d - size; i <= this.field_145848_d + size; i++) {
/*  505 */       for (int z = this.field_145849_e - size; z <= this.field_145849_e + size; z++) {
/*      */         
/*  507 */         if (i == this.field_145848_d - size || i == this.field_145848_d + size || z == this.field_145849_e - size || z == this.field_145849_e + size) {
/*  508 */           if (block == StructureBlock.AIR) {
/*  509 */             if ((this.field_145851_c - dist != this.field_145851_c || i != this.field_145848_d || z != this.field_145849_e) && !isReplacable(this.field_145851_c - dist, i, z, set))
/*  510 */               return false; 
/*  511 */           } else if (block == StructureBlock.REDSTONE) {
/*  512 */             if ((this.field_145851_c - dist != this.field_145851_c || i != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateRedstone(this.field_145851_c - dist, i, z, set, activate))
/*  513 */               return false; 
/*  514 */           } else if (block == StructureBlock.DRACONIUM) {
/*  515 */             if ((this.field_145851_c - dist != this.field_145851_c || i != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateDraconium(this.field_145851_c - dist, i, z, set, activate))
/*  516 */               return false; 
/*      */           } else {
/*  518 */             LogHelper.error("Invalid String In Multiblock Structure Code!!!");
/*  519 */             return false;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     int x;
/*  525 */     for (x = this.field_145851_c - size; x <= this.field_145851_c + size; x++) {
/*  526 */       for (int z = this.field_145849_e - size; z <= this.field_145849_e + size; z++) {
/*      */         
/*  528 */         if (x == this.field_145851_c - size || x == this.field_145851_c + size || z == this.field_145849_e - size || z == this.field_145849_e + size) {
/*  529 */           if (block == StructureBlock.AIR) {
/*  530 */             if ((x != this.field_145851_c || this.field_145848_d + dist != this.field_145848_d || z != this.field_145849_e) && !isReplacable(x, this.field_145848_d + dist, z, set))
/*  531 */               return false; 
/*  532 */           } else if (block == StructureBlock.REDSTONE) {
/*  533 */             if ((x != this.field_145851_c || this.field_145848_d + dist != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateRedstone(x, this.field_145848_d + dist, z, set, activate))
/*  534 */               return false; 
/*  535 */           } else if (block == StructureBlock.DRACONIUM) {
/*  536 */             if ((x != this.field_145851_c || this.field_145848_d + dist != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateDraconium(x, this.field_145848_d + dist, z, set, activate))
/*  537 */               return false; 
/*      */           } else {
/*  539 */             LogHelper.error("Invalid String In Multiblock Structure Code!!!");
/*  540 */             return false;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*  545 */     for (x = this.field_145851_c - size; x <= this.field_145851_c + size; x++) {
/*  546 */       for (int z = this.field_145849_e - size; z <= this.field_145849_e + size; z++) {
/*      */         
/*  548 */         if (x == this.field_145851_c - size || x == this.field_145851_c + size || z == this.field_145849_e - size || z == this.field_145849_e + size) {
/*  549 */           if (block == StructureBlock.AIR) {
/*  550 */             if ((x != this.field_145851_c || this.field_145848_d - dist != this.field_145848_d || z != this.field_145849_e) && !isReplacable(x, this.field_145848_d - dist, z, set))
/*  551 */               return false; 
/*  552 */           } else if (block == StructureBlock.REDSTONE) {
/*  553 */             if ((x != this.field_145851_c || this.field_145848_d - dist != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateRedstone(x, this.field_145848_d - dist, z, set, activate))
/*  554 */               return false; 
/*  555 */           } else if (block == StructureBlock.DRACONIUM) {
/*  556 */             if ((x != this.field_145851_c || this.field_145848_d - dist != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateDraconium(x, this.field_145848_d - dist, z, set, activate))
/*  557 */               return false; 
/*      */           } else {
/*  559 */             LogHelper.error("Invalid String In Multiblock Structure Code!!!");
/*  560 */             return false;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     int y;
/*  566 */     for (y = this.field_145848_d - size; y <= this.field_145848_d + size; y++) {
/*  567 */       for (int j = this.field_145851_c - size; j <= this.field_145851_c + size; j++) {
/*      */         
/*  569 */         if (y == this.field_145848_d - size || y == this.field_145848_d + size || j == this.field_145851_c - size || j == this.field_145851_c + size) {
/*  570 */           if (block == StructureBlock.AIR) {
/*  571 */             if ((j != this.field_145851_c || y != this.field_145848_d || this.field_145849_e + dist != this.field_145849_e) && !isReplacable(j, y, this.field_145849_e + dist, set))
/*  572 */               return false; 
/*  573 */           } else if (block == StructureBlock.REDSTONE) {
/*  574 */             if ((j != this.field_145851_c || y != this.field_145848_d || this.field_145849_e + dist != this.field_145849_e) && !testForOrActivateRedstone(j, y, this.field_145849_e + dist, set, activate))
/*  575 */               return false; 
/*  576 */           } else if (block == StructureBlock.DRACONIUM) {
/*  577 */             if ((j != this.field_145851_c || y != this.field_145848_d || this.field_145849_e + dist != this.field_145849_e) && !testForOrActivateDraconium(j, y, this.field_145849_e + dist, set, activate))
/*  578 */               return false; 
/*      */           } else {
/*  580 */             LogHelper.error("Invalid String In Multiblock Structure Code!!!");
/*  581 */             return false;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*  586 */     for (y = this.field_145848_d - size; y <= this.field_145848_d + size; y++) {
/*  587 */       for (int j = this.field_145851_c - size; j <= this.field_145851_c + size; j++) {
/*      */         
/*  589 */         if (y == this.field_145848_d - size || y == this.field_145848_d + size || j == this.field_145851_c - size || j == this.field_145851_c + size) {
/*  590 */           if (block == StructureBlock.AIR) {
/*  591 */             if ((j != this.field_145851_c || y != this.field_145848_d || this.field_145849_e - dist != this.field_145849_e) && !isReplacable(j, y, this.field_145849_e - dist, set))
/*  592 */               return false; 
/*  593 */           } else if (block == StructureBlock.REDSTONE) {
/*  594 */             if ((j != this.field_145851_c || y != this.field_145848_d || this.field_145849_e - dist != this.field_145849_e) && !testForOrActivateRedstone(j, y, this.field_145849_e - dist, set, activate))
/*  595 */               return false; 
/*  596 */           } else if (block == StructureBlock.DRACONIUM) {
/*  597 */             if ((j != this.field_145851_c || y != this.field_145848_d || this.field_145849_e - dist != this.field_145849_e) && !testForOrActivateDraconium(j, y, this.field_145849_e - dist, set, activate))
/*  598 */               return false; 
/*      */           } else {
/*  600 */             LogHelper.error("Invalid String In Multiblock Structure Code!!!");
/*  601 */             return false;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*  606 */     return true;
/*      */   }
/*      */   
/*      */   private boolean testOrActivateSides(int dist, StructureBlock block, boolean set, boolean activate) {
/*  610 */     dist++; int i;
/*  611 */     for (i = this.field_145848_d - 1; i <= this.field_145848_d + 1; i++) {
/*  612 */       for (int z = this.field_145849_e - 1; z <= this.field_145849_e + 1; z++) {
/*      */         
/*  614 */         if (block == StructureBlock.AIR) {
/*  615 */           if ((this.field_145851_c + dist != this.field_145851_c || i != this.field_145848_d || z != this.field_145849_e) && !isReplacable(this.field_145851_c + dist, i, z, set))
/*  616 */             return false; 
/*  617 */         } else if (block == StructureBlock.REDSTONE) {
/*  618 */           if ((this.field_145851_c + dist != this.field_145851_c || i != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateRedstone(this.field_145851_c + dist, i, z, set, activate))
/*  619 */             return false; 
/*  620 */         } else if (block == StructureBlock.DRACONIUM) {
/*  621 */           if ((this.field_145851_c + dist != this.field_145851_c || i != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateDraconium(this.field_145851_c + dist, i, z, set, activate))
/*  622 */             return false; 
/*      */         } else {
/*  624 */           LogHelper.error("Invalid String In Multiblock Structure Code!!!");
/*  625 */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/*  629 */     for (i = this.field_145848_d - 1; i <= this.field_145848_d + 1; i++) {
/*  630 */       for (int z = this.field_145849_e - 1; z <= this.field_145849_e + 1; z++) {
/*      */         
/*  632 */         if (block == StructureBlock.AIR) {
/*  633 */           if ((this.field_145851_c - dist != this.field_145851_c || i != this.field_145848_d || z != this.field_145849_e) && !isReplacable(this.field_145851_c - dist, i, z, set))
/*  634 */             return false; 
/*  635 */         } else if (block == StructureBlock.REDSTONE) {
/*  636 */           if ((this.field_145851_c - dist != this.field_145851_c || i != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateRedstone(this.field_145851_c - dist, i, z, set, activate))
/*  637 */             return false; 
/*  638 */         } else if (block == StructureBlock.DRACONIUM) {
/*  639 */           if ((this.field_145851_c - dist != this.field_145851_c || i != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateDraconium(this.field_145851_c - dist, i, z, set, activate))
/*  640 */             return false; 
/*      */         } else {
/*  642 */           LogHelper.error("Invalid String In Multiblock Structure Code!!!");
/*  643 */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     int x;
/*  648 */     for (x = this.field_145851_c - 1; x <= this.field_145851_c + 1; x++) {
/*  649 */       for (int z = this.field_145849_e - 1; z <= this.field_145849_e + 1; z++) {
/*      */         
/*  651 */         if (block == StructureBlock.AIR) {
/*  652 */           if ((x != this.field_145851_c || this.field_145848_d + dist != this.field_145848_d || z != this.field_145849_e) && !isReplacable(x, this.field_145848_d + dist, z, set))
/*  653 */             return false; 
/*  654 */         } else if (block == StructureBlock.REDSTONE) {
/*  655 */           if ((x != this.field_145851_c || this.field_145848_d + dist != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateRedstone(x, this.field_145848_d + dist, z, set, activate))
/*  656 */             return false; 
/*  657 */         } else if (block == StructureBlock.DRACONIUM) {
/*  658 */           if ((x != this.field_145851_c || this.field_145848_d + dist != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateDraconium(x, this.field_145848_d + dist, z, set, activate))
/*  659 */             return false; 
/*      */         } else {
/*  661 */           LogHelper.error("Invalid String In Multiblock Structure Code!!!");
/*  662 */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/*  666 */     for (x = this.field_145851_c - 1; x <= this.field_145851_c + 1; x++) {
/*  667 */       for (int z = this.field_145849_e - 1; z <= this.field_145849_e + 1; z++) {
/*      */         
/*  669 */         if (block == StructureBlock.AIR) {
/*  670 */           if ((x != this.field_145851_c || this.field_145848_d - dist != this.field_145848_d || z != this.field_145849_e) && !isReplacable(x, this.field_145848_d - dist, z, set))
/*  671 */             return false; 
/*  672 */         } else if (block == StructureBlock.REDSTONE) {
/*  673 */           if ((x != this.field_145851_c || this.field_145848_d - dist != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateRedstone(x, this.field_145848_d - dist, z, set, activate))
/*  674 */             return false; 
/*  675 */         } else if (block == StructureBlock.DRACONIUM) {
/*  676 */           if ((x != this.field_145851_c || this.field_145848_d - dist != this.field_145848_d || z != this.field_145849_e) && !testForOrActivateDraconium(x, this.field_145848_d - dist, z, set, activate))
/*  677 */             return false; 
/*      */         } else {
/*  679 */           LogHelper.error("Invalid String In Multiblock Structure Code!!!");
/*  680 */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     int y;
/*  685 */     for (y = this.field_145848_d - 1; y <= this.field_145848_d + 1; y++) {
/*  686 */       for (int j = this.field_145851_c - 1; j <= this.field_145851_c + 1; j++) {
/*      */         
/*  688 */         if (block == StructureBlock.AIR) {
/*  689 */           if ((j != this.field_145851_c || y != this.field_145848_d || this.field_145849_e + dist != this.field_145849_e) && !isReplacable(j, y, this.field_145849_e + dist, set))
/*  690 */             return false; 
/*  691 */         } else if (block == StructureBlock.REDSTONE) {
/*  692 */           if ((j != this.field_145851_c || y != this.field_145848_d || this.field_145849_e + dist != this.field_145849_e) && !testForOrActivateRedstone(j, y, this.field_145849_e + dist, set, activate))
/*  693 */             return false; 
/*  694 */         } else if (block == StructureBlock.DRACONIUM) {
/*  695 */           if ((j != this.field_145851_c || y != this.field_145848_d || this.field_145849_e + dist != this.field_145849_e) && !testForOrActivateDraconium(j, y, this.field_145849_e + dist, set, activate))
/*  696 */             return false; 
/*      */         } else {
/*  698 */           LogHelper.error("Invalid String In Multiblock Structure Code!!!");
/*  699 */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/*  703 */     for (y = this.field_145848_d - 1; y <= this.field_145848_d + 1; y++) {
/*  704 */       for (int j = this.field_145851_c - 1; j <= this.field_145851_c + 1; j++) {
/*      */         
/*  706 */         if (block == StructureBlock.AIR) {
/*  707 */           if ((j != this.field_145851_c || y != this.field_145848_d || this.field_145849_e - dist != this.field_145849_e) && !isReplacable(j, y, this.field_145849_e - dist, set))
/*  708 */             return false; 
/*  709 */         } else if (block == StructureBlock.REDSTONE) {
/*  710 */           if ((j != this.field_145851_c || y != this.field_145848_d || this.field_145849_e - dist != this.field_145849_e) && !testForOrActivateRedstone(j, y, this.field_145849_e - dist, set, activate))
/*  711 */             return false; 
/*  712 */         } else if (block == StructureBlock.DRACONIUM) {
/*  713 */           if ((j != this.field_145851_c || y != this.field_145848_d || this.field_145849_e - dist != this.field_145849_e) && !testForOrActivateDraconium(j, y, this.field_145849_e - dist, set, activate))
/*  714 */             return false; 
/*      */         } else {
/*  716 */           LogHelper.error("Invalid String In Multiblock Structure Code!!!");
/*  717 */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  722 */     return true;
/*      */   }
/*      */   
/*      */   private boolean testForOrActivateDraconium(int x, int y, int z, boolean set, boolean activate) {
/*  726 */     if (!activate) {
/*  727 */       if (set) {
/*  728 */         DraconicEvolutionTags.Blocks.DRACONIUM.set(this.field_145850_b, x, y, z);
/*  729 */         return true;
/*      */       } 
/*  731 */       return (DraconicEvolutionTags.Blocks.DRACONIUM.is((IBlockAccess)this.field_145850_b, x, y, z) || (this.field_145850_b.func_147439_a(x, y, z) == ModBlocks.invisibleMultiblock && this.field_145850_b.func_72805_g(x, y, z) == 0));
/*      */     } 
/*      */     
/*  734 */     return activateDraconium(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean testForOrActivateRedstone(int x, int y, int z, boolean set, boolean activate) {
/*  739 */     if (!activate) {
/*  740 */       if (set) {
/*  741 */         this.field_145850_b.func_147465_d(x, y, z, BalanceConfigHandler.energyStorageStructureBlock, BalanceConfigHandler.energyStorageStructureBlockMetadata, 3);
/*  742 */         return true;
/*      */       } 
/*  744 */       return ((this.field_145850_b.func_147439_a(x, y, z) == BalanceConfigHandler.energyStorageStructureBlock && this.field_145850_b.func_72805_g(x, y, z) == BalanceConfigHandler.energyStorageStructureBlockMetadata) || (this.field_145850_b.func_147439_a(x, y, z) == ModBlocks.invisibleMultiblock && this.field_145850_b.func_72805_g(x, y, z) == 1));
/*      */     } 
/*      */     
/*  747 */     return activateRedstone(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean activateDraconium(int x, int y, int z) {
/*  752 */     if (testForOrActivateDraconium(x, y, z, false, false)) {
/*  753 */       this.field_145850_b.func_147465_d(x, y, z, (Block)ModBlocks.invisibleMultiblock, 0, 2);
/*  754 */       TileInvisibleMultiblock tile = (this.field_145850_b.func_147438_o(x, y, z) != null && this.field_145850_b.func_147438_o(x, y, z) instanceof TileInvisibleMultiblock) ? (TileInvisibleMultiblock)this.field_145850_b.func_147438_o(x, y, z) : null;
/*  755 */       if (tile != null) {
/*  756 */         tile.master = new MultiblockHelper.TileLocation(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*      */       }
/*  758 */       return true;
/*      */     } 
/*  760 */     LogHelper.error("Failed to activate structure (activateDraconium)");
/*  761 */     return false;
/*      */   }
/*      */   
/*      */   private boolean activateRedstone(int x, int y, int z) {
/*  765 */     if (testForOrActivateRedstone(x, y, z, false, false)) {
/*  766 */       this.field_145850_b.func_147465_d(x, y, z, (Block)ModBlocks.invisibleMultiblock, 1, 2);
/*  767 */       TileInvisibleMultiblock tile = (this.field_145850_b.func_147438_o(x, y, z) != null && this.field_145850_b.func_147438_o(x, y, z) instanceof TileInvisibleMultiblock) ? (TileInvisibleMultiblock)this.field_145850_b.func_147438_o(x, y, z) : null;
/*  768 */       if (tile != null) {
/*  769 */         tile.master = new MultiblockHelper.TileLocation(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*      */       }
/*  771 */       return true;
/*      */     } 
/*  773 */     LogHelper.error("Failed to activate structure (activateRedstone)");
/*  774 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isReplacable(int x, int y, int z, boolean set) {
/*  778 */     if (set) {
/*  779 */       this.field_145850_b.func_147449_b(x, y, z, Blocks.field_150350_a);
/*  780 */       return true;
/*      */     } 
/*  782 */     return (this.field_145850_b.func_147439_a(x, y, z).isReplaceable((IBlockAccess)this.field_145850_b, x, y, z) || this.field_145850_b.func_147437_c(x, y, z));
/*      */   }
/*      */   
/*      */   public boolean isOnline() {
/*  786 */     return this.online;
/*      */   }
/*      */   
/*      */   private void activateStabilizers() {
/*  790 */     for (int i = 0; i < this.stabilizers.length; i++) {
/*  791 */       if (this.stabilizers[i] == null) {
/*  792 */         LogHelper.error("activateStabilizers stabalizers[" + i + "] == null!!!");
/*      */         return;
/*      */       } 
/*  795 */       TileParticleGenerator tile = (this.field_145850_b.func_147438_o(this.stabilizers[i].getXCoord(), this.stabilizers[i].getYCoord(), this.stabilizers[i].getZCoord()) != null && this.field_145850_b.func_147438_o(this.stabilizers[i].getXCoord(), this.stabilizers[i].getYCoord(), this.stabilizers[i].getZCoord()) instanceof TileParticleGenerator) ? (TileParticleGenerator)this.field_145850_b.func_147438_o(this.stabilizers[i].getXCoord(), this.stabilizers[i].getYCoord(), this.stabilizers[i].getZCoord()) : null;
/*  796 */       if (tile == null) {
/*  797 */         LogHelper.error("Missing Tile Entity (Particle Generator)");
/*      */         return;
/*      */       } 
/*  800 */       tile.stabalizerMode = true;
/*  801 */       tile.setMaster(new MultiblockHelper.TileLocation(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*  802 */       this.field_145850_b.func_72921_c(this.stabilizers[i].getXCoord(), this.stabilizers[i].getYCoord(), this.stabilizers[i].getZCoord(), 1, 2);
/*      */     } 
/*  804 */     initializeCapacity();
/*      */   }
/*      */   
/*      */   private void initializeCapacity() {
/*  808 */     long capacity = 0L;
/*  809 */     switch (this.tier) {
/*      */       case 0:
/*  811 */         capacity = BalanceConfigHandler.energyStorageTier1Storage;
/*      */         break;
/*      */       case 1:
/*  814 */         capacity = BalanceConfigHandler.energyStorageTier2Storage;
/*      */         break;
/*      */       case 2:
/*  817 */         capacity = BalanceConfigHandler.energyStorageTier3Storage;
/*      */         break;
/*      */       case 3:
/*  820 */         capacity = BalanceConfigHandler.energyStorageTier4Storage;
/*      */         break;
/*      */       case 4:
/*  823 */         capacity = BalanceConfigHandler.energyStorageTier5Storage;
/*      */         break;
/*      */       case 5:
/*  826 */         capacity = BalanceConfigHandler.energyStorageTier6Storage;
/*      */         break;
/*      */       case 6:
/*  829 */         capacity = BalanceConfigHandler.energyStorageTier7Storage;
/*      */         break;
/*      */     } 
/*  832 */     this.capacity = capacity;
/*  833 */     if (this.energy > capacity) this.energy = capacity; 
/*      */   }
/*      */   
/*      */   public void deactivateStabilizers() {
/*  837 */     for (int i = 0; i < this.stabilizers.length; i++) {
/*  838 */       if (this.stabilizers[i] == null) {
/*  839 */         LogHelper.error("activateStabilizers stabalizers[" + i + "] == null!!!");
/*      */       } else {
/*  841 */         TileParticleGenerator tile = (this.field_145850_b.func_147438_o(this.stabilizers[i].getXCoord(), this.stabilizers[i].getYCoord(), this.stabilizers[i].getZCoord()) != null && this.field_145850_b.func_147438_o(this.stabilizers[i].getXCoord(), this.stabilizers[i].getYCoord(), this.stabilizers[i].getZCoord()) instanceof TileParticleGenerator) ? (TileParticleGenerator)this.field_145850_b.func_147438_o(this.stabilizers[i].getXCoord(), this.stabilizers[i].getYCoord(), this.stabilizers[i].getZCoord()) : null;
/*  842 */         if (tile != null) {
/*      */ 
/*      */           
/*  845 */           tile.stabalizerMode = false;
/*  846 */           this.field_145850_b.func_72921_c(this.stabilizers[i].getXCoord(), this.stabilizers[i].getYCoord(), this.stabilizers[i].getZCoord(), 0, 2);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean areStabilizersActive() {
/*  853 */     for (int i = 0; i < this.stabilizers.length; i++) {
/*  854 */       if (this.stabilizers[i] == null) {
/*  855 */         LogHelper.error("activateStabilizers stabalizers[" + i + "] == null!!!");
/*  856 */         return false;
/*      */       } 
/*  858 */       TileParticleGenerator tile = (this.field_145850_b.func_147438_o(this.stabilizers[i].getXCoord(), this.stabilizers[i].getYCoord(), this.stabilizers[i].getZCoord()) != null && this.field_145850_b.func_147438_o(this.stabilizers[i].getXCoord(), this.stabilizers[i].getYCoord(), this.stabilizers[i].getZCoord()) instanceof TileParticleGenerator) ? (TileParticleGenerator)this.field_145850_b.func_147438_o(this.stabilizers[i].getXCoord(), this.stabilizers[i].getYCoord(), this.stabilizers[i].getZCoord()) : null;
/*  859 */       if (tile == null)
/*      */       {
/*  861 */         return false;
/*      */       }
/*  863 */       if (!tile.stabalizerMode || this.field_145850_b.func_72805_g(this.stabilizers[i].getXCoord(), this.stabilizers[i].getYCoord(), this.stabilizers[i].getZCoord()) != 1)
/*  864 */         return false; 
/*      */     } 
/*  866 */     return true;
/*      */   }
/*      */   
/*      */   private boolean checkStabilizers() {
/*  870 */     for (MultiblockHelper.TileLocation stabilizer : this.stabilizers) {
/*  871 */       if (stabilizer == null) return false; 
/*  872 */       TileParticleGenerator gen = (this.field_145850_b.func_147438_o(stabilizer.getXCoord(), stabilizer.getYCoord(), stabilizer.getZCoord()) != null && this.field_145850_b.func_147438_o(stabilizer.getXCoord(), stabilizer.getYCoord(), stabilizer.getZCoord()) instanceof TileParticleGenerator) ? (TileParticleGenerator)this.field_145850_b.func_147438_o(stabilizer.getXCoord(), stabilizer.getYCoord(), stabilizer.getZCoord()) : null;
/*  873 */       if (gen == null || !gen.stabalizerMode) return false; 
/*  874 */       if ((gen.getMaster()).field_145851_c != this.field_145851_c || (gen.getMaster()).field_145848_d != this.field_145848_d || (gen.getMaster()).field_145849_e != this.field_145849_e)
/*  875 */         return false; 
/*      */     } 
/*  877 */     return true;
/*      */   }
/*      */   
/*      */   public int getTier() {
/*  881 */     return this.tier;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_145841_b(NBTTagCompound compound) {
/*  891 */     super.func_145841_b(compound);
/*  892 */     compound.func_74757_a("Online", this.online);
/*  893 */     compound.func_74777_a("Tier", (short)this.tier);
/*  894 */     compound.func_74772_a("EnergyL", this.energy);
/*  895 */     for (int i = 0; i < this.stabilizers.length; i++) {
/*  896 */       if (this.stabilizers[i] != null) this.stabilizers[i].writeToNBT(compound, String.valueOf(i));
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public void func_145839_a(NBTTagCompound compound) {
/*  902 */     this.online = compound.func_74767_n("Online");
/*  903 */     this.tier = compound.func_74765_d("Tier");
/*  904 */     this.energy = compound.func_74763_f("EnergyL");
/*  905 */     if (compound.func_74764_b("Energy")) this.energy = (long)compound.func_74769_h("Energy"); 
/*  906 */     for (int i = 0; i < this.stabilizers.length; i++) {
/*  907 */       if (this.stabilizers[i] != null) this.stabilizers[i].readFromNBT(compound, String.valueOf(i)); 
/*      */     } 
/*  909 */     initializeCapacity();
/*  910 */     super.func_145839_a(compound);
/*      */   }
/*      */ 
/*      */   
/*      */   public Packet func_145844_m() {
/*  915 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  916 */     func_145841_b(nbttagcompound);
/*  917 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, nbttagcompound);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/*  922 */     func_145839_a(pkt.func_148857_g());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getEnergyStored() {
/*  928 */     return this.energy;
/*      */   }
/*      */   
/*      */   public long getMaxEnergyStored() {
/*  932 */     return this.capacity;
/*      */   }
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getRenderBoundingBox() {
/*  937 */     return INFINITE_EXTENT_AABB;
/*      */   }
/*      */ 
/*      */   
/*      */   public double func_145833_n() {
/*  942 */     return 40960.0D;
/*      */   }
/*      */   
/*      */   private void detectAndRendChanges() {
/*  946 */     if (this.lastTickCapacity != this.energy) {
/*  947 */       this.lastTickCapacity = ((Long)sendObjectToClient((byte)3, 0, Long.valueOf(this.energy), new NetworkRegistry.TargetPoint(this.field_145850_b.field_73011_w.field_76574_g, this.field_145851_c, this.field_145848_d, this.field_145849_e, 20.0D))).longValue();
/*      */     }
/*      */   }
/*      */   
/*      */   public void receiveObjectFromServer(int index, Object object) {
/*  952 */     this.energy = ((Long)object).longValue();
/*      */   }
/*      */   
/*      */   private enum StructureBlock {
/*  956 */     AIR,
/*  957 */     DRACONIUM,
/*  958 */     REDSTONE;
/*      */   }
/*      */   
/*      */   public static final class PylonLink {
/*      */     private final FixedTileEntityCache coreRef;
/*      */     private final FixedTileEntityCache pylonRef;
/*      */     
/*      */     private PylonLink(TileEnergyStorageCore core, TileEnergyPylon pylon) {
/*  966 */       this.coreRef = new FixedTileEntityCache((TileEntity)core);
/*  967 */       this.pylonRef = new FixedTileEntityCache((TileEntity)pylon);
/*      */     }
/*      */     
/*      */     public boolean isLinkedTo(TileEnergyStorageCore core) {
/*  971 */       return (this.coreRef.get() == core);
/*      */     }
/*      */     
/*      */     public void unlinkAndUnregister() {
/*  975 */       TileEnergyStorageCore core = getCore();
/*      */       
/*  977 */       unlink();
/*      */       
/*  979 */       if (core != null && core.linkedPylons.remove(this)) {
/*  980 */         core.unregisterEnergyNet();
/*      */       }
/*      */     }
/*      */     
/*      */     private void unlink() {
/*  985 */       this.coreRef.reset();
/*  986 */       this.pylonRef.reset();
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     private TileEnergyStorageCore getCore() {
/*  991 */       return (TileEnergyStorageCore)this.coreRef.get();
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     private TileEnergyPylon getPylon() {
/*  996 */       return (TileEnergyPylon)this.pylonRef.get();
/*      */     }
/*      */     
/*      */     public boolean isValid() {
/* 1000 */       TileEnergyStorageCore core = getCore();
/* 1001 */       if (core == null) {
/* 1002 */         return false;
/*      */       }
/*      */       
/* 1005 */       TileEnergyPylon pylon = getPylon();
/* 1006 */       if (pylon == null || !pylon.active) {
/* 1007 */         return false;
/*      */       }
/*      */       
/* 1010 */       MultiblockHelper.TileLocation masterLocation = pylon.getMasterLocation();
/* 1011 */       return (masterLocation != null && masterLocation.isThisLocation(core.field_145851_c, core.field_145848_d, core.field_145849_e));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\multiblocktiles\TileEnergyStorageCore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */