/*     */ package com.brandon3055.draconicevolution.common.tileentities.multiblocktiles;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.Teleporter;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.TeleporterMKI;
/*     */ import com.brandon3055.draconicevolution.common.utills.PortalHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ public class TileDislocatorReceptacle
/*     */   extends TileEntity
/*     */   implements IInventory
/*     */ {
/*     */   private ItemStack dislocator;
/*  21 */   public PortalHelper.PortalStructure structure = null;
/*     */   public boolean isActive = false;
/*     */   public boolean updating = false;
/*  24 */   public int coolDown = 0;
/*  25 */   public int ticksTillStart = -1;
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  29 */     if (this.field_145850_b.field_72995_K)
/*  30 */       return;  if (this.coolDown > 0) this.coolDown--; 
/*  31 */     if (this.ticksTillStart > -1) this.ticksTillStart--; 
/*  32 */     if (this.ticksTillStart == 0)
/*  33 */       this.field_145850_b.func_147464_a(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e), 1); 
/*     */   }
/*     */   
/*     */   public void validateActivePortal() {
/*  37 */     if (this.updating)
/*  38 */       return;  if (this.structure == null) {
/*  39 */       this.isActive = false;
/*     */       return;
/*     */     } 
/*  42 */     this.isActive = (this.structure.checkFrameIsValid(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e) && this.structure.scanPortal(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, false, true));
/*  43 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */   }
/*     */   
/*     */   public void updateState() {
/*  47 */     if (this.structure == null || !this.isActive)
/*  48 */       this.structure = PortalHelper.getValidStructure(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e); 
/*  49 */     if (this.structure == null) {
/*  50 */       this.isActive = false;
/*  51 */       this.field_145850_b.func_147444_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145854_h);
/*  52 */       this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */       
/*     */       return;
/*     */     } 
/*  56 */     if (this.isActive) {
/*  57 */       if (func_70301_a(0) == null || !this.structure.checkFrameIsValid(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e))
/*  58 */         this.isActive = false; 
/*     */     } else {
/*  60 */       boolean frameValid = this.structure.checkFrameIsValid(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*  61 */       boolean portalEmpty = this.structure.scanPortal(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, false, false);
/*     */       
/*  63 */       if (getLocation() != null && frameValid && portalEmpty) {
/*  64 */         this.isActive = true;
/*  65 */         this.structure.scanPortal(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, true, false);
/*     */       } 
/*     */     } 
/*  68 */     this.field_145850_b.func_147444_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145854_h);
/*  69 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */   }
/*     */   
/*     */   public Teleporter.TeleportLocation getLocation() {
/*  73 */     if (func_70301_a(0) != null && func_70301_a(0).func_77973_b() instanceof TeleporterMKI)
/*  74 */       return ((TeleporterMKI)func_70301_a(0).func_77973_b()).getLocation(func_70301_a(0)); 
/*  75 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet func_145844_m() {
/*  80 */     NBTTagCompound tagCompound = new NBTTagCompound();
/*  81 */     tagCompound.func_74757_a("IsActive", this.isActive);
/*  82 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, tagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/*  87 */     this.isActive = pkt.func_148857_g().func_74767_n("IsActive");
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  92 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/*  97 */     return (i == 0) ? this.dislocator : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int count) {
/* 102 */     ItemStack itemstack = func_70301_a(i);
/*     */     
/* 104 */     if (itemstack != null) {
/* 105 */       if (itemstack.field_77994_a <= count) {
/* 106 */         func_70299_a(i, (ItemStack)null);
/*     */       } else {
/* 108 */         itemstack = itemstack.func_77979_a(count);
/* 109 */         if (itemstack.field_77994_a == 0) {
/* 110 */           func_70299_a(i, (ItemStack)null);
/*     */         }
/*     */       } 
/*     */     }
/* 114 */     if (this.isActive) { updateState(); }
/* 115 */     else { this.ticksTillStart = 1; }
/* 116 */      return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int i) {
/* 121 */     ItemStack item = func_70301_a(i);
/* 122 */     if (item != null) func_70299_a(i, (ItemStack)null); 
/* 123 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/* 128 */     if (i != 0)
/* 129 */       return;  this.dislocator = itemstack;
/* 130 */     if (itemstack != null && itemstack.field_77994_a > func_70297_j_()) {
/* 131 */       itemstack.field_77994_a = func_70297_j_();
/*     */     }
/* 133 */     if (this.isActive) { updateState(); }
/* 134 */     else { this.ticksTillStart = 1; }
/*     */   
/*     */   }
/*     */   
/*     */   public String func_145825_b() {
/* 139 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 149 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 154 */     if (this.field_145850_b == null) {
/* 155 */       return true;
/*     */     }
/* 157 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) != this) {
/* 158 */       return false;
/*     */     }
/* 160 */     return (player.func_70092_e(this.field_145851_c + 0.5D, this.field_145848_d + 0.5D, this.field_145849_e + 0.5D) < 64.0D);
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
/* 173 */     return (itemstack != null && itemstack.func_77973_b() instanceof TeleporterMKI && ((TeleporterMKI)itemstack.func_77973_b()).getLocation(itemstack) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound compound) {
/* 178 */     compound.func_74757_a("IsActive", this.isActive);
/*     */     
/* 180 */     if (this.dislocator != null) {
/* 181 */       NBTTagCompound stack = new NBTTagCompound();
/* 182 */       this.dislocator.func_77955_b(stack);
/* 183 */       compound.func_74782_a("Dislocator", (NBTBase)stack);
/*     */     } 
/*     */     
/* 186 */     if (this.structure != null) {
/* 187 */       compound.func_74757_a("HasStructure", true);
/* 188 */       this.structure.writeToNBT(compound);
/*     */     } 
/*     */     
/* 191 */     super.func_145841_b(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound compound) {
/* 196 */     this.isActive = compound.func_74767_n("IsActive");
/*     */     
/* 198 */     if (compound.func_74764_b("Dislocator")) {
/* 199 */       this.dislocator = ItemStack.func_77949_a(compound.func_74775_l("Dislocator"));
/*     */     }
/* 201 */     if (compound.func_74764_b("HasStructure")) {
/* 202 */       this.structure = new PortalHelper.PortalStructure();
/* 203 */       this.structure.readFromNBT(compound);
/*     */     } 
/* 205 */     super.func_145839_a(compound);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\multiblocktiles\TileDislocatorReceptacle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */