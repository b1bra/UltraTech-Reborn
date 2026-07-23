/*     */ package com.brandon3055.draconicevolution.common.tileentities;
/*     */ import com.brandon3055.brandonscore.common.utills.IC2Helper;
/*     */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.eu.TileEnergyBase;
/*     */ import com.brandon3055.draconicevolution.common.utills.EnergyStorage;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import com.brandon3055.brandonscore.common.tags.OreDict;
/*     */ import com.brandon3055.brandonscore.common.tags.Tags;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemSword;
/*     */ import net.minecraft.item.ItemTool;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.util.FakePlayer;
/*     */ import net.minecraftforge.common.util.FakePlayerFactory;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class TileGrinder extends TileEnergyBase.Sink implements ISidedInventory {
/*  40 */   public int meta = -1;
/*     */   List<EntityLiving> killList;
/*     */   AxisAlignedBB killBox;
/*  43 */   int tick = 0;
/*     */   public double centreX;
/*  45 */   public double centreY = -1.0D;
/*     */   public double centreZ;
/*     */   private ItemStack[] items;
/*  48 */   public int burnTime = 1;
/*  49 */   public int burnTimeRemaining = 0;
/*     */   public boolean disabled = false;
/*     */   private boolean disabledCach = false;
/*     */   public boolean hasPower = false;
/*     */   public boolean hasPowerCach = false;
/*     */   private boolean readyNext = false;
/*  55 */   public EnergyStorage internalGenBuffer = new EnergyStorage(BalanceConfigHandler.grinderInternalEnergyBufferSize, BalanceConfigHandler.grinderMaxReceive);
/*  56 */   public EnergyStorage externalInputBuffer = new EnergyStorage(BalanceConfigHandler.grinderExternalEnergyBufferSize, BalanceConfigHandler.grinderMaxReceive);
/*  57 */   public int energyPerKill = BalanceConfigHandler.grinderEnergyPerKill;
/*     */   private ItemStack diamondSword;
/*     */   public static FakePlayer fakePlayer;
/*     */   
/*     */   public void updateVariables() {
/*  62 */     if (this.meta == -1)
/*  63 */       this.meta = this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d, this.field_145849_e); 
/*  64 */     if (this.centreY == -1.0D) {
/*  65 */       switch (this.meta) {
/*     */         case 0:
/*  67 */           this.centreX = this.field_145851_c + 0.5D;
/*  68 */           this.centreY = this.field_145848_d + 0.5D;
/*  69 */           this.centreZ = this.field_145849_e + 0.5D - 5.0D;
/*     */           break;
/*     */         case 1:
/*  72 */           this.centreX = this.field_145851_c + 0.5D + 5.0D;
/*  73 */           this.centreY = this.field_145848_d + 0.5D;
/*  74 */           this.centreZ = this.field_145849_e + 0.5D;
/*     */           break;
/*     */         case 2:
/*  77 */           this.centreX = this.field_145851_c + 0.5D;
/*  78 */           this.centreY = this.field_145848_d + 0.5D;
/*  79 */           this.centreZ = this.field_145849_e + 0.5D + 5.0D;
/*     */           break;
/*     */         case 3:
/*  82 */           this.centreX = this.field_145851_c + 0.5D - 5.0D;
/*  83 */           this.centreY = this.field_145848_d + 0.5D;
/*  84 */           this.centreZ = this.field_145849_e + 0.5D;
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public TileGrinder() {
/*  92 */     this.items = new ItemStack[1];
/*  93 */     this.diamondSword = new ItemStack(Items.field_151048_u);
/*  94 */     if (BalanceConfigHandler.grinderShouldUseLooting) {
/*  95 */       this.diamondSword.func_77966_a(Enchantment.field_77335_o, 3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 101 */     updateVariables();
/*     */     
/* 103 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/* 106 */     this.hasPower = (getActiveBuffer().getEnergyStored() >= this.energyPerKill);
/*     */     
/* 108 */     int burnSpeed = 2;
/* 109 */     int EPBT = 10;
/*     */     
/* 111 */     if (this.burnTimeRemaining > 0 && this.internalGenBuffer.getEnergyStored() < this.internalGenBuffer.getMaxEnergyStored()) {
/* 112 */       this.burnTimeRemaining -= burnSpeed;
/* 113 */       this.internalGenBuffer.setEnergyStored(this.internalGenBuffer.getEnergyStored() + Math.min((burnSpeed * EPBT), this.internalGenBuffer.getMaxEnergyStored() - this.internalGenBuffer.getEnergyStored()));
/* 114 */     } else if (this.burnTimeRemaining <= 0) {
/* 115 */       tryRefuel();
/*     */     } 
/* 117 */     if (this.readyNext && !this.disabled && getActiveBuffer().getEnergyStored() >= this.energyPerKill && 
/* 118 */       killNextEntity()) {
/* 119 */       getActiveBuffer().modifyEnergyStored(-this.energyPerKill);
/*     */     }
/*     */ 
/*     */     
/* 123 */     if (this.tick % 100 == 0)
/*     */     {
/* 125 */       this.readyNext = true;
/*     */     }
/* 127 */     detectAndSendChanges((this.tick % 500 == 0));
/* 128 */     this.tick++;
/*     */   }
/*     */   
/*     */   public EnergyStorage getActiveBuffer() {
/* 132 */     return isExternallyPowered() ? this.externalInputBuffer : this.internalGenBuffer;
/*     */   }
/*     */   
/*     */   public boolean isExternallyPowered() {
/* 136 */     return (this.externalInputBuffer.getEnergyStored() > this.energyPerKill);
/*     */   }
/*     */   
/*     */   public void tryRefuel() {
/* 140 */     if (this.burnTimeRemaining > 0 || this.internalGenBuffer.getEnergyStored() >= this.internalGenBuffer.getMaxEnergyStored())
/*     */       return; 
/* 142 */     if (this.items[0] != null && (this.items[0]).field_77994_a > 0) {
/* 143 */       int itemBurnTime = getItemBurnTime(this.items[0]);
/*     */       
/* 145 */       if (itemBurnTime > 0) {
/* 146 */         (this.items[0]).field_77994_a--;
/* 147 */         if ((this.items[0]).field_77994_a == 0) {
/* 148 */           this.items[0] = this.items[0].func_77973_b().getContainerItem(this.items[0]);
/*     */         }
/* 150 */         this.burnTime = itemBurnTime;
/* 151 */         this.burnTimeRemaining = itemBurnTime;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean killNextEntity() {
/* 158 */     if (this.field_145850_b.field_72995_K) {
/* 159 */       return false;
/*     */     }
/* 161 */     if (fakePlayer == null) {
/* 162 */       fakePlayer = FakePlayerFactory.get((WorldServer)this.field_145850_b, new GameProfile(UUID.fromString("5b5689b9-e43d-4282-a42a-dc916f3616b7"), "[Draconic-Evolution]"));
/*     */     }
/* 164 */     if (BalanceConfigHandler.grinderShouldUseLooting && (fakePlayer.func_70694_bm() == null || !ItemStack.func_77989_b(fakePlayer.func_70694_bm(), this.diamondSword))) {
/* 165 */       fakePlayer.func_70062_b(0, this.diamondSword);
/*     */     }
/* 167 */     this.killBox = AxisAlignedBB.func_72330_a(this.centreX - 4.5D, this.centreY - 4.5D, this.centreZ - 4.5D, this.centreX + 4.5D, this.centreY + 4.5D, this.centreZ + 4.5D);
/*     */     
/* 169 */     this.killList = this.field_145850_b.func_72872_a(EntityLiving.class, this.killBox);
/* 170 */     List<EntityXPOrb> xp = this.field_145850_b.func_72872_a(EntityXPOrb.class, this.killBox.func_72314_b(4.0D, 4.0D, 4.0D));
/* 171 */     for (EntityXPOrb orb : xp) {
/* 172 */       if (orb.field_70531_b < 5400) {
/* 173 */         orb.field_70531_b = 5400;
/*     */       }
/*     */     } 
/* 176 */     if (!this.killList.isEmpty()) {
/* 177 */       EntityLiving mob = this.killList.get(this.field_145850_b.field_73012_v.nextInt(this.killList.size()));
/* 178 */       if (mob.func_70089_S()) {
/* 179 */         mob.func_70097_a(DamageSource.func_76365_a((EntityPlayer)fakePlayer), 50000.0F);
/* 180 */         this.readyNext = true;
/* 181 */         return true;
/*     */       } 
/* 183 */       this.readyNext = true;
/* 184 */       return false;
/*     */     } 
/* 186 */     this.readyNext = false;
/* 187 */     return false;
/*     */   }
/*     */   
/*     */   public static int getItemBurnTime(ItemStack stack) {
/* 191 */     if (stack == null) {
/* 192 */       return 0;
/*     */     }
/* 194 */     Item item = stack.func_77973_b();
/*     */     
/* 196 */     if (item instanceof net.minecraft.item.ItemBlock && Block.func_149634_a(item) != Blocks.field_150350_a) {
/* 197 */       Block block = Block.func_149634_a(item);
/*     */       
/* 199 */       if (block.func_149688_o() == Material.field_151575_d) {
/* 200 */         return 300;
/*     */       }
/*     */     } 
/*     */     
/* 204 */     if (OreDict.SLAB_WOOD.is(stack)) {
/* 205 */       return 150;
/*     */     }
/*     */     
/* 208 */     if (Tags.Blocks.COAL_BLOCK.is(stack)) {
/* 209 */       return 16000;
/*     */     }
/*     */     
/* 212 */     if (item instanceof ItemTool && ((ItemTool)item).func_77861_e().equals("WOOD"))
/* 213 */       return 200; 
/* 214 */     if (item instanceof ItemSword && ((ItemSword)item).func_150932_j().equals("WOOD"))
/* 215 */       return 200; 
/* 216 */     if (item instanceof ItemHoe && ((ItemHoe)item).func_77842_f().equals("WOOD"))
/* 217 */       return 200; 
/* 218 */     if (Tags.Items.STICK.is(stack))
/* 219 */       return 100; 
/* 220 */     if (Tags.Items.COAL.is(stack))
/* 221 */       return 1600; 
/* 222 */     if (Tags.Items.LAVA_BUCKET.is(stack))
/* 223 */       return 20000; 
/* 224 */     if (OreDict.TREE_SAPLING.is(stack)) {
/* 225 */       return 100;
/*     */     }
/*     */ 
/*     */     
/* 229 */     if (Tags.Items.BLAZE_ROD.is(stack))
/*     */     {
/* 231 */       return 2400;
/*     */     }
/* 233 */     return GameRegistry.getFuelValue(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 239 */     return this.items.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/* 244 */     return this.items[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int count) {
/* 249 */     ItemStack itemstack = func_70301_a(i);
/*     */     
/* 251 */     if (itemstack != null) {
/* 252 */       if (itemstack.field_77994_a <= count) {
/* 253 */         func_70299_a(i, (ItemStack)null);
/*     */       } else {
/* 255 */         itemstack = itemstack.func_77979_a(count);
/* 256 */         if (itemstack.field_77994_a == 0) {
/* 257 */           func_70299_a(i, (ItemStack)null);
/*     */         }
/*     */       } 
/*     */     }
/* 261 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int i) {
/* 266 */     ItemStack item = func_70301_a(i);
/* 267 */     if (item != null)
/* 268 */       func_70299_a(i, (ItemStack)null); 
/* 269 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/* 274 */     this.items[i] = itemstack;
/* 275 */     if (itemstack != null && itemstack.field_77994_a > func_70297_j_()) {
/* 276 */       itemstack.field_77994_a = func_70297_j_();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 282 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 287 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 292 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 297 */     if (this.field_145850_b == null) {
/* 298 */       return true;
/*     */     }
/* 300 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) != this) {
/* 301 */       return false;
/*     */     }
/* 303 */     return (player.func_70092_e(this.field_145851_c + 0.5D, this.field_145848_d + 0.5D, this.field_145849_e + 0.4D) < 64.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70305_f() {}
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack stack) {
/* 316 */     return (getItemBurnTime(stack) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 321 */     return new int[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int var1, ItemStack var2, int var3) {
/* 326 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int var1, ItemStack var2, int var3) {
/* 331 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound compound) {
/* 336 */     NBTTagCompound[] tag = new NBTTagCompound[this.items.length];
/*     */     
/* 338 */     for (int i = 0; i < this.items.length; i++) {
/* 339 */       tag[i] = new NBTTagCompound();
/*     */       
/* 341 */       if (this.items[i] != null) {
/* 342 */         tag[i] = this.items[i].func_77955_b(tag[i]);
/*     */       }
/*     */       
/* 345 */       compound.func_74782_a("Item" + i, (NBTBase)tag[i]);
/*     */     } 
/*     */     
/* 348 */     compound.func_74757_a("Disabled", this.disabled);
/* 349 */     compound.func_74768_a("BurnTime", this.burnTime);
/* 350 */     compound.func_74768_a("BurnTimeRemaining", this.burnTimeRemaining);
/* 351 */     this.externalInputBuffer.writeToNBT(compound, "ExternalBuffer");
/* 352 */     this.internalGenBuffer.writeToNBT(compound, "InternalBuffer");
/*     */     
/* 354 */     super.func_145841_b(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound compound) {
/* 359 */     NBTTagCompound[] tag = new NBTTagCompound[this.items.length];
/*     */     
/* 361 */     for (int i = 0; i < this.items.length; i++) {
/* 362 */       tag[i] = compound.func_74775_l("Item" + i);
/* 363 */       this.items[i] = ItemStack.func_77949_a(tag[i]);
/*     */     } 
/*     */     
/* 366 */     this.disabled = compound.func_74767_n("Disabled");
/* 367 */     this.burnTime = compound.func_74762_e("BurnTime");
/* 368 */     this.burnTimeRemaining = compound.func_74762_e("BurnTimeRemaining");
/* 369 */     this.externalInputBuffer.readFromNBT(compound, "ExternalBuffer");
/* 370 */     this.internalGenBuffer.readFromNBT(compound, "InternalBuffer");
/*     */     
/* 372 */     super.func_145839_a(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void receiveObjectFromServer(int index, Object object) {
/* 378 */     if (index == 0 && this.disabled != ((Boolean)object).booleanValue()) {
/* 379 */       this.disabled = ((Boolean)object).booleanValue();
/* 380 */       this.field_145850_b.func_147458_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 381 */     } else if (this.hasPower != ((Boolean)object).booleanValue()) {
/* 382 */       this.hasPower = ((Boolean)object).booleanValue();
/* 383 */       this.field_145850_b.func_147458_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void detectAndSendChanges(boolean sendAnyway) {
/* 388 */     if (this.disabledCach != this.disabled || sendAnyway) {
/* 389 */       this.disabledCach = ((Boolean)sendObjectToClient((byte)6, 0, Boolean.valueOf(this.disabled))).booleanValue();
/*     */     }
/* 391 */     if (this.hasPowerCach != this.hasPower || sendAnyway) {
/* 392 */       this.hasPowerCach = ((Boolean)sendObjectToClient((byte)6, 1, Boolean.valueOf(this.hasPower))).booleanValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet func_145844_m() {
/* 398 */     NBTTagCompound tagCompound = new NBTTagCompound();
/* 399 */     func_145841_b(tagCompound);
/* 400 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, tagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/* 405 */     func_145839_a(pkt.func_148857_g());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 411 */     return this.externalInputBuffer.getDemandedEnergy();
/*     */   }
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection side, double amount, double voltage) {
/* 416 */     return amount - this.externalInputBuffer.receiveEnergy(IC2Helper.floorEu(amount), false);
/*     */   }
/*     */   
/*     */   public long getEnergyStored() {
/* 420 */     return this.externalInputBuffer.getEnergyStored();
/*     */   }
/*     */   
/*     */   public long getMaxEnergyStored() {
/* 424 */     return this.externalInputBuffer.getMaxEnergyStored();
/*     */   }
/*     */   
/*     */   public EnergyStorage getInternalBuffer() {
/* 428 */     return this.internalGenBuffer;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\TileGrinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */