/*     */ package com.brandon3055.draconicevolution.common.tileentities;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.common.container.ContainerPlayerDetector;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ 
/*     */ public class TilePlayerDetectorAdvanced extends TileEntity implements IInventory {
/*  19 */   public String[] names = new String[42];
/*     */   private ItemStack[] items;
/*     */   public boolean whiteList = false;
/*  22 */   public int range = 10;
/*  23 */   private int tick = 0;
/*  24 */   private int scanRate = 5;
/*     */   public boolean output = false;
/*     */   public boolean outputInverted = false;
/*     */   private List<EntityPlayer> EntityList;
/*     */   
/*     */   public TilePlayerDetectorAdvanced() {
/*  30 */     for (int i = 0; i < this.names.length; i++) {
/*  31 */       if (this.names[i] == null) this.names[i] = ""; 
/*     */     } 
/*  33 */     this.items = new ItemStack[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  38 */     if (this.field_145850_b.field_72995_K)
/*     */       return; 
/*  40 */     if (this.tick >= this.scanRate)
/*     */     
/*  42 */     { this.tick = 0;
/*     */       
/*  44 */       if (shouldEmit())
/*  45 */       { if (!this.output) setOutput(true);
/*     */          }
/*  47 */       else if (this.output) { setOutput(false); }
/*     */        }
/*  49 */     else { this.tick++; }
/*     */   
/*     */   }
/*     */   public boolean shouldEmit() {
/*  53 */     findEntitys();
/*     */     
/*  55 */     boolean b = false;
/*  56 */     for (EntityPlayer ent : this.EntityList) {
/*  57 */       String name = ent.func_70005_c_();
/*  58 */       if (this.whiteList) {
/*  59 */         if (isPlayerListed(name)) return true;  continue;
/*     */       } 
/*  61 */       if (!isPlayerListed(name)) return true;
/*     */     
/*     */     } 
/*     */     
/*  65 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   private void findEntitys() {
/*  70 */     double x1 = this.field_145851_c + 0.5D - this.range;
/*  71 */     double y1 = this.field_145848_d + 0.5D - this.range;
/*  72 */     double z1 = this.field_145849_e + 0.5D - this.range;
/*  73 */     double x2 = this.field_145851_c + 0.5D + this.range;
/*  74 */     double y2 = this.field_145848_d + 0.5D + this.range;
/*  75 */     double z2 = this.field_145849_e + 0.5D + this.range;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     this.EntityList = this.field_145850_b.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a(x1, y1, z1, x2, y2, z2));
/*     */   }
/*     */ 
/*     */   
/*     */   private void setOutput(boolean out) {
/*  86 */     this.output = out;
/*  87 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*  88 */     updateBlocks();
/*     */   }
/*     */   
/*     */   public void updateBlocks() {
/*  92 */     this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*  93 */     this.field_145850_b.func_147459_d(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*  94 */     this.field_145850_b.func_147459_d(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*  95 */     this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*  96 */     this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*  97 */     this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*  98 */     this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*     */   }
/*     */   
/*     */   public boolean isPlayerListed(String name) {
/* 102 */     if (name == null) return false;
/*     */     
/* 104 */     for (String s : this.names) {
/* 105 */       if (s.equals(name)) return true; 
/*     */     } 
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 113 */     return this.items.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/* 118 */     return this.items[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int count) {
/* 123 */     ItemStack itemstack = func_70301_a(i);
/*     */     
/* 125 */     if (itemstack != null) {
/* 126 */       if (itemstack.field_77994_a <= count) {
/* 127 */         func_70299_a(i, (ItemStack)null);
/*     */       } else {
/* 129 */         itemstack = itemstack.func_77979_a(count);
/* 130 */         if (itemstack.field_77994_a == 0) {
/* 131 */           func_70299_a(i, (ItemStack)null);
/*     */         }
/*     */       } 
/*     */     }
/* 135 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int i) {
/* 140 */     ItemStack item = func_70301_a(i);
/* 141 */     if (item != null) func_70299_a(i, (ItemStack)null); 
/* 142 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/* 147 */     this.items[i] = itemstack;
/* 148 */     if (itemstack != null && itemstack.field_77994_a > func_70297_j_()) {
/* 149 */       itemstack.field_77994_a = func_70297_j_();
/*     */     }
/* 151 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 156 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 166 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 171 */     if (this.field_145850_b == null) {
/* 172 */       return true;
/*     */     }
/* 174 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) != this) {
/* 175 */       return false;
/*     */     }
/* 177 */     return (player.func_70092_e(this.field_145851_c + 0.5D, this.field_145848_d + 0.5D, this.field_145849_e + 0.4D) < 64.0D);
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
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 190 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet func_145844_m() {
/* 197 */     NBTTagCompound tagCompound = new NBTTagCompound();
/* 198 */     func_145841_b(tagCompound);
/* 199 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, tagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/* 204 */     func_145839_a(pkt.func_148857_g());
/* 205 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */   }
/*     */   
/*     */   public Container getGuiContainer(InventoryPlayer inventoryplayer) {
/* 209 */     return (Container)new ContainerPlayerDetector(inventoryplayer, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound compound) {
/* 215 */     NBTTagCompound[] tag = new NBTTagCompound[this.items.length];
/*     */     int i;
/* 217 */     for (i = 0; i < this.items.length; i++) {
/* 218 */       tag[i] = new NBTTagCompound();
/*     */       
/* 220 */       if (this.items[i] != null) {
/* 221 */         tag[i] = this.items[i].func_77955_b(tag[i]);
/*     */       }
/*     */       
/* 224 */       compound.func_74782_a("Item" + i, (NBTBase)tag[i]);
/*     */     } 
/*     */     
/* 227 */     for (i = 0; i < this.names.length; i++) {
/* 228 */       String name = (this.names[i] != null) ? this.names[i] : "";
/* 229 */       compound.func_74778_a("Name_" + i, name);
/*     */     } 
/*     */     
/* 232 */     compound.func_74757_a("WhiteList", this.whiteList);
/* 233 */     compound.func_74757_a("Output", this.output);
/* 234 */     compound.func_74768_a("Range", this.range);
/* 235 */     compound.func_74757_a("OutputInverted", this.outputInverted);
/*     */     
/* 237 */     super.func_145841_b(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound compound) {
/* 242 */     NBTTagCompound[] tag = new NBTTagCompound[this.items.length];
/*     */     int i;
/* 244 */     for (i = 0; i < this.items.length; i++) {
/* 245 */       tag[i] = compound.func_74775_l("Item" + i);
/* 246 */       this.items[i] = ItemStack.func_77949_a(tag[i]);
/*     */     } 
/*     */     
/* 249 */     for (i = 0; i < this.names.length; i++) {
/* 250 */       this.names[i] = compound.func_74779_i("Name_" + i);
/*     */     }
/* 252 */     this.whiteList = compound.func_74767_n("WhiteList");
/* 253 */     this.range = compound.func_74762_e("Range");
/* 254 */     this.output = compound.func_74767_n("Output");
/* 255 */     this.outputInverted = compound.func_74767_n("OutputInverted");
/*     */     
/* 257 */     super.func_145839_a(compound);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\TilePlayerDetectorAdvanced.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */