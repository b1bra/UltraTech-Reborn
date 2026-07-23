/*     */ package com.brandon3055.draconicevolution.common.tileentities;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.IC2Helper;
/*     */ import com.brandon3055.draconicevolution.client.handler.ParticleHandler;
/*     */ import com.brandon3055.draconicevolution.client.render.particle.ParticleEnergy;
/*     */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.eu.TileEnergyBase;
/*     */ import com.brandon3055.draconicevolution.common.utills.EnergyStorage;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class TileEnergyInfuser
/*     */   extends TileEnergyBase.Sink
/*     */   implements ISidedInventory
/*     */ {
/*  28 */   ItemStack[] items = new ItemStack[1];
/*  29 */   public EnergyStorage energy = new EnergyStorage(BalanceConfigHandler.energyInfuserStorage, BalanceConfigHandler.energyInfuserMaxTransfer);
/*     */   public boolean running = false;
/*     */   public boolean runningCach = false;
/*  32 */   private int tick = 0;
/*  33 */   public float rotation = 0.0F;
/*     */ 
/*     */   
/*     */   public boolean transfer = false;
/*     */ 
/*     */   
/*     */   public boolean transferCach = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  44 */     if (this.field_145850_b.field_72995_K) {
/*  45 */       if (this.running) {
/*  46 */         this.rotation += 0.5F;
/*  47 */         if (this.rotation > 360.0F) {
/*  48 */           this.rotation = 0.0F;
/*     */         }
/*  50 */         spawnParticles();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  55 */     registerEnergyNet();
/*     */     
/*  57 */     if (this.tick % 100 == 0) tryStartOrStop(); 
/*  58 */     if (this.tick % 400 == 0) detectAndSendChanges(true);
/*     */     
/*  60 */     if (this.running && tryStartOrStop())
/*  61 */     { long charged = IC2Helper.ceilEu(ElectricItem.manager.charge(this.items[0], this.energy.getEnergyStored(), 2147483647, true, false));
/*  62 */       setTransfer((this.energy.extractEnergy(charged, false) > 0L)); }
/*  63 */     else { setTransfer(false); }
/*     */     
/*  65 */     detectAndSendChanges(false);
/*  66 */     this.tick++;
/*     */   }
/*     */   
/*     */   private boolean tryStartOrStop() {
/*  70 */     ItemStack stack = this.items[0];
/*     */     
/*  72 */     if (stack != null && stack.field_77994_a == 1 && IC2Helper.isElectricItem(stack)) {
/*  73 */       this.running = (IC2Helper.getCharge(stack) < IC2Helper.getMaxCharge(stack));
/*     */     } else {
/*  75 */       this.running = false;
/*     */     } 
/*     */     
/*  78 */     return this.running;
/*     */   }
/*     */   
/*     */   private void setTransfer(boolean t) {
/*  82 */     this.transfer = t;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private void spawnParticles() {
/*  87 */     if (this.field_145850_b.field_72995_K && this.running && this.transfer) {
/*  88 */       Random rand = this.field_145850_b.field_73012_v;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  93 */       float y = 0.6F;
/*     */       
/*  95 */       double yRand = (rand.nextFloat() - 0.5D) / 2.0D;
/*  96 */       double radRand = (1.0F - rand.nextFloat() / 2.0F);
/*  97 */       double rotationF = (this.rotation / 57.0F);
/*  98 */       ParticleEnergy particle = new ParticleEnergy(this.field_145850_b, this.field_145851_c + 0.5D + radRand * Math.sin(rotationF), (this.field_145848_d + y) + yRand, this.field_145849_e + 0.5D + radRand * Math.cos(rotationF), this.field_145851_c + 0.5D, this.field_145848_d + 0.7D, this.field_145849_e + 0.5D, 1);
/*  99 */       ParticleHandler.spawnCustomParticle((EntityFX)particle);
/*     */       
/* 101 */       yRand = (rand.nextFloat() - 0.5D) / 2.0D;
/* 102 */       radRand = (1.0F - rand.nextFloat() / 2.0F);
/* 103 */       rotationF = ((this.rotation + 90.0F) / 57.0F);
/* 104 */       particle = new ParticleEnergy(this.field_145850_b, this.field_145851_c + 0.5D + radRand * Math.sin(rotationF), (this.field_145848_d + y) + yRand, this.field_145849_e + 0.5D + radRand * Math.cos(rotationF), this.field_145851_c + 0.5D, this.field_145848_d + 0.7D, this.field_145849_e + 0.5D, 1);
/* 105 */       ParticleHandler.spawnCustomParticle((EntityFX)particle);
/*     */       
/* 107 */       yRand = (rand.nextFloat() - 0.5D) / 2.0D;
/* 108 */       radRand = (1.0F - rand.nextFloat() / 2.0F);
/* 109 */       rotationF = ((this.rotation + 180.0F) / 57.0F);
/* 110 */       particle = new ParticleEnergy(this.field_145850_b, this.field_145851_c + 0.5D + radRand * Math.sin(rotationF), (this.field_145848_d + y) + yRand, this.field_145849_e + 0.5D + radRand * Math.cos(rotationF), this.field_145851_c + 0.5D, this.field_145848_d + 0.7D, this.field_145849_e + 0.5D, 1);
/* 111 */       ParticleHandler.spawnCustomParticle((EntityFX)particle);
/*     */       
/* 113 */       yRand = (rand.nextFloat() - 0.5D) / 2.0D;
/* 114 */       radRand = (1.0F - rand.nextFloat() / 2.0F);
/* 115 */       rotationF = ((this.rotation + 270.0F) / 57.0F);
/* 116 */       particle = new ParticleEnergy(this.field_145850_b, this.field_145851_c + 0.5D + radRand * Math.sin(rotationF), (this.field_145848_d + y) + yRand, this.field_145849_e + 0.5D + radRand * Math.cos(rotationF), this.field_145851_c + 0.5D, this.field_145848_d + 0.7D, this.field_145849_e + 0.5D, 1);
/* 117 */       ParticleHandler.spawnCustomParticle((EntityFX)particle);
/*     */       
/* 119 */       y = 0.79F;
/* 120 */       radRand = 0.35D;
/* 121 */       rotationF = (this.rotation / 57.0F);
/* 122 */       particle = new ParticleEnergy(this.field_145850_b, this.field_145851_c + 0.5D + radRand * Math.sin(rotationF), (this.field_145848_d + y), this.field_145849_e + 0.5D + radRand * Math.cos(rotationF), this.field_145851_c + 0.5D, this.field_145848_d + 0.7D, this.field_145849_e + 0.5D, 0);
/* 123 */       ParticleHandler.spawnCustomParticle((EntityFX)particle);
/*     */       
/* 125 */       rotationF = ((this.rotation + 90.0F) / 57.0F);
/* 126 */       particle = new ParticleEnergy(this.field_145850_b, this.field_145851_c + 0.5D + radRand * Math.sin(rotationF), (this.field_145848_d + y), this.field_145849_e + 0.5D + radRand * Math.cos(rotationF), this.field_145851_c + 0.5D, this.field_145848_d + 0.7D, this.field_145849_e + 0.5D, 0);
/* 127 */       ParticleHandler.spawnCustomParticle((EntityFX)particle);
/*     */       
/* 129 */       rotationF = ((this.rotation + 180.0F) / 57.0F);
/* 130 */       particle = new ParticleEnergy(this.field_145850_b, this.field_145851_c + 0.5D + radRand * Math.sin(rotationF), (this.field_145848_d + y), this.field_145849_e + 0.5D + radRand * Math.cos(rotationF), this.field_145851_c + 0.5D, this.field_145848_d + 0.7D, this.field_145849_e + 0.5D, 0);
/* 131 */       ParticleHandler.spawnCustomParticle((EntityFX)particle);
/*     */       
/* 133 */       rotationF = ((this.rotation + 270.0F) / 57.0F);
/* 134 */       particle = new ParticleEnergy(this.field_145850_b, this.field_145851_c + 0.5D + radRand * Math.sin(rotationF), (this.field_145848_d + y), this.field_145849_e + 0.5D + radRand * Math.cos(rotationF), this.field_145851_c + 0.5D, this.field_145848_d + 0.7D, this.field_145849_e + 0.5D, 0);
/* 135 */       ParticleHandler.spawnCustomParticle((EntityFX)particle);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 142 */     return this.energy.getDemandedEnergy();
/*     */   }
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection side, double amount, double voltage) {
/* 147 */     return amount - this.energy.receiveEnergy(IC2Helper.floorEu(amount), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet func_145844_m() {
/* 154 */     NBTTagCompound tagCompound = new NBTTagCompound();
/* 155 */     func_145841_b(tagCompound);
/* 156 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, tagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/* 161 */     func_145839_a(pkt.func_148857_g());
/*     */   }
/*     */   
/*     */   public void detectAndSendChanges(boolean sendAnyway) {
/* 165 */     if (this.runningCach != this.running || sendAnyway) {
/* 166 */       this.runningCach = ((Boolean)sendObjectToClient((byte)6, 0, Boolean.valueOf(this.running))).booleanValue();
/*     */     }
/* 168 */     if (this.transferCach != this.transfer || sendAnyway) {
/* 169 */       this.transferCach = ((Boolean)sendObjectToClient((byte)6, 1, Boolean.valueOf(this.transfer))).booleanValue();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void receiveObjectFromServer(int index, Object object) {
/* 177 */     if (index == 0) this.running = ((Boolean)object).booleanValue(); 
/* 178 */     if (index == 1) this.transfer = ((Boolean)object).booleanValue();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 185 */     return this.items.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/* 190 */     return this.items[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int count) {
/* 195 */     ItemStack itemstack = func_70301_a(i);
/*     */     
/* 197 */     if (itemstack != null) {
/* 198 */       if (itemstack.field_77994_a <= count) {
/* 199 */         func_70299_a(i, (ItemStack)null);
/*     */       } else {
/* 201 */         itemstack = itemstack.func_77979_a(count);
/* 202 */         if (itemstack.field_77994_a == 0) {
/* 203 */           func_70299_a(i, (ItemStack)null);
/*     */         }
/*     */       } 
/*     */     }
/* 207 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int i) {
/* 212 */     ItemStack item = func_70301_a(i);
/* 213 */     if (item != null) func_70299_a(i, (ItemStack)null); 
/* 214 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/* 219 */     this.items[i] = itemstack;
/* 220 */     if (itemstack != null && itemstack.field_77994_a > func_70297_j_()) {
/* 221 */       itemstack.field_77994_a = func_70297_j_();
/*     */     }
/* 223 */     tryStartOrStop();
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 228 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 233 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 238 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 243 */     if (this.field_145850_b == null) {
/* 244 */       return true;
/*     */     }
/* 246 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) != this) {
/* 247 */       return false;
/*     */     }
/* 249 */     return (player.func_70092_e(this.field_145851_c + 0.5D, this.field_145848_d + 0.5D, this.field_145849_e + 0.4D) < 64.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {
/* 254 */     System.out.println("open");
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70305_f() {
/* 259 */     System.out.println("close");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int slot, ItemStack stack) {
/* 264 */     return IC2Helper.isElectricItem(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 269 */     return new int[] { 0 };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int slot, ItemStack item, int side) {
/* 274 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int slot, ItemStack stack, int side) {
/* 279 */     if (!IC2Helper.isElectricItem(stack)) return true;
/*     */     
/* 281 */     return (IC2Helper.getCharge(stack) >= IC2Helper.getMaxCharge(stack));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound compound) {
/* 288 */     NBTTagCompound[] tag = new NBTTagCompound[this.items.length];
/*     */     
/* 290 */     for (int i = 0; i < this.items.length; i++) {
/* 291 */       tag[i] = new NBTTagCompound();
/*     */       
/* 293 */       if (this.items[i] != null) {
/* 294 */         tag[i] = this.items[i].func_77955_b(tag[i]);
/*     */       }
/*     */       
/* 297 */       compound.func_74782_a("Item" + i, (NBTBase)tag[i]);
/*     */     } 
/* 299 */     compound.func_74757_a("Running", this.running);
/* 300 */     compound.func_74757_a("Transfer", this.transfer);
/* 301 */     this.energy.writeToNBT(compound);
/*     */     
/* 303 */     super.func_145841_b(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound compound) {
/* 308 */     NBTTagCompound[] tag = new NBTTagCompound[this.items.length];
/*     */     
/* 310 */     for (int i = 0; i < this.items.length; i++) {
/* 311 */       tag[i] = compound.func_74775_l("Item" + i);
/* 312 */       this.items[i] = ItemStack.func_77949_a(tag[i]);
/*     */     } 
/* 314 */     this.running = compound.func_74767_n("Running");
/* 315 */     this.transfer = compound.func_74767_n("Transfer");
/* 316 */     this.energy.readFromNBT(compound);
/*     */     
/* 318 */     super.func_145839_a(compound);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\TileEnergyInfuser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */