/*     */ package com.brandon3055.draconicevolution.common.tileentities;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ 
/*     */ public class TileDissEnchanter
/*     */   extends TileEntity
/*     */   implements ISidedInventory {
/*  25 */   ItemStack[] items = new ItemStack[3];
/*     */   public boolean isValidRecipe = false;
/*  27 */   public int dissenchantCost = 0;
/*  28 */   public int timer = 0;
/*  29 */   public float bookPower = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  35 */     this.timer++;
/*  36 */     if (this.field_145850_b.field_72995_K && this.isValidRecipe && this.field_145850_b.field_73012_v.nextFloat() > 0.5F) {
/*  37 */       this.field_145850_b.func_72869_a("enchantmenttable", this.field_145851_c + 0.3D + this.field_145850_b.field_73012_v.nextDouble() * 0.4D, this.field_145848_d + 0.7D + this.field_145850_b.field_73012_v.nextDouble() * 0.5D, this.field_145849_e + 0.3D + this.field_145850_b.field_73012_v.nextDouble() * 0.4D, 0.0D, 0.3D, 0.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onInventoryChanged() {
/*  42 */     boolean flag = true;
/*  43 */     if (this.items[2] != null || this.items[1] == null || this.items[0] == null) {
/*  44 */       flag = false;
/*  45 */       this.dissenchantCost = 0;
/*     */     }
/*  47 */     else if (this.items[1].func_77973_b() != Items.field_151122_aG || EnchantmentHelper.func_82781_a(this.items[0]).isEmpty()) {
/*  48 */       flag = false;
/*     */     } 
/*  50 */     if (this.items[0] != null) this.dissenchantCost = ItemNBTHelper.getInteger(this.items[0], "RepairCost", 0);
/*     */ 
/*     */     
/*  53 */     this.isValidRecipe = flag;
/*     */     
/*  55 */     if (!this.field_145850_b.field_72995_K) {
/*     */       
/*  57 */       this.bookPower = 0.0F;
/*     */       
/*  59 */       for (int j = -1; j <= 1; j++) {
/*  60 */         for (int k = -1; k <= 1; k++) {
/*  61 */           if ((j != 0 || k != 0) && this.field_145850_b.func_147437_c(this.field_145851_c + k, this.field_145848_d, this.field_145849_e + j) && this.field_145850_b.func_147437_c(this.field_145851_c + k, this.field_145848_d + 1, this.field_145849_e + j)) {
/*  62 */             this.bookPower += ForgeHooks.getEnchantPower(this.field_145850_b, this.field_145851_c + k * 2, this.field_145848_d, this.field_145849_e + j * 2);
/*  63 */             this.bookPower += ForgeHooks.getEnchantPower(this.field_145850_b, this.field_145851_c + k * 2, this.field_145848_d + 1, this.field_145849_e + j * 2);
/*     */             
/*  65 */             if (k != 0 && j != 0) {
/*  66 */               this.bookPower += ForgeHooks.getEnchantPower(this.field_145850_b, this.field_145851_c + k * 2, this.field_145848_d, this.field_145849_e + j);
/*  67 */               this.bookPower += ForgeHooks.getEnchantPower(this.field_145850_b, this.field_145851_c + k * 2, this.field_145848_d + 1, this.field_145849_e + j);
/*  68 */               this.bookPower += ForgeHooks.getEnchantPower(this.field_145850_b, this.field_145851_c + k, this.field_145848_d, this.field_145849_e + j * 2);
/*  69 */               this.bookPower += ForgeHooks.getEnchantPower(this.field_145850_b, this.field_145851_c + k, this.field_145848_d + 1, this.field_145849_e + j * 2);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  75 */     this.bookPower *= 2.0F;
/*  76 */     if (this.bookPower > 40.0F) this.bookPower = 40.0F; 
/*  77 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */   }
/*     */ 
/*     */   
/*     */   public void buttonClick(EntityPlayer player) {
/*  82 */     if (!this.isValidRecipe)
/*  83 */       return;  if (player.field_71068_ca < this.dissenchantCost && !player.field_71075_bZ.field_75098_d)
/*  84 */       return;  ItemStack input = this.items[0];
/*  85 */     ItemStack enchantedBook = new ItemStack((Item)Items.field_151134_bR);
/*  86 */     Map enchants = EnchantmentHelper.func_82781_a(input);
/*  87 */     Object id = null;
/*  88 */     Object level = null;
/*  89 */     String tagName = "ench";
/*  90 */     if (input.func_77973_b() == Items.field_151134_bR) tagName = "StoredEnchantments"; 
/*  91 */     Iterator i = enchants.keySet().iterator();
/*  92 */     if (i.hasNext()) id = i.next(); 
/*  93 */     if (id == null)
/*  94 */       return;  level = enchants.get(id);
/*  95 */     if (level == null)
/*  96 */       return;  Map<Object, Object> enchant = new HashMap<>();
/*  97 */     enchant.put(id, level);
/*  98 */     EnchantmentHelper.func_82782_a(enchant, enchantedBook);
/*  99 */     func_70299_a(2, enchantedBook);
/* 100 */     NBTTagCompound compound = input.func_77978_p();
/* 101 */     NBTTagList list = new NBTTagList();
/* 102 */     if (compound.func_74781_a(tagName) instanceof NBTTagList) list = (NBTTagList)compound.func_74781_a(tagName); 
/* 103 */     if (list == null)
/* 104 */       return;  for (int j = 0; j < list.func_74745_c(); j++) {
/* 105 */       if (list.func_150305_b(j).func_74765_d("id") == id.hashCode()) {
/* 106 */         list.func_74744_a(j);
/*     */       }
/*     */     } 
/*     */     
/* 110 */     compound.func_82580_o(tagName);
/* 111 */     if (list.func_74745_c() > 0) input.func_77983_a(tagName, (NBTBase)list); 
/* 112 */     if (input.func_77973_b() == Items.field_151134_bR && list.func_74745_c() == 0) func_70299_a(0, (ItemStack)null); 
/* 113 */     if (!player.field_71075_bZ.field_75098_d) player.func_82242_a(-this.dissenchantCost); 
/* 114 */     if (this.items[0] != null && ItemNBTHelper.getInteger(this.items[0], "RepairCost", 0) > 0)
/* 115 */       ItemNBTHelper.setInteger(this.items[0], "RepairCost", ItemNBTHelper.getInteger(this.items[0], "RepairCost", 0) - Math.min(2, ItemNBTHelper.getInteger(this.items[0], "RepairCost", 0))); 
/* 116 */     if (!player.field_71075_bZ.field_75098_d) func_70298_a(1, 1); 
/* 117 */     int maxDamage = (this.items[0] != null) ? this.items[0].func_77958_k() : 0;
/* 118 */     float damageF = (40.0F - this.bookPower) / 100.0F;
/* 119 */     int damage = (int)(damageF * maxDamage);
/* 120 */     int damageResult = (this.items[0] != null) ? (this.items[0].func_77960_j() + damage) : 0;
/*     */     
/* 122 */     if (!player.field_71075_bZ.field_75098_d && damageResult > maxDamage && maxDamage > 0) {
/* 123 */       func_70299_a(0, (ItemStack)null);
/* 124 */     } else if (!player.field_71075_bZ.field_75098_d && maxDamage > 0 && this.items[0] != null) {
/* 125 */       this.items[0].func_77964_b(damageResult);
/*     */     } 
/* 127 */     onInventoryChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet func_145844_m() {
/* 135 */     NBTTagCompound tagCompound = new NBTTagCompound();
/* 136 */     func_145841_b(tagCompound);
/* 137 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, tagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/* 142 */     func_145839_a(pkt.func_148857_g());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 149 */     return this.items.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/* 154 */     return this.items[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int count) {
/* 159 */     ItemStack itemstack = func_70301_a(i);
/*     */     
/* 161 */     if (itemstack != null) {
/* 162 */       if (itemstack.field_77994_a <= count) {
/* 163 */         func_70299_a(i, (ItemStack)null);
/*     */       } else {
/* 165 */         itemstack = itemstack.func_77979_a(count);
/* 166 */         if (itemstack.field_77994_a == 0) {
/* 167 */           func_70299_a(i, (ItemStack)null);
/*     */         }
/*     */       } 
/*     */     }
/* 171 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int i) {
/* 176 */     ItemStack item = func_70301_a(i);
/* 177 */     if (item != null) func_70299_a(i, (ItemStack)null); 
/* 178 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/* 183 */     this.items[i] = itemstack;
/* 184 */     if (itemstack != null && itemstack.field_77994_a > func_70297_j_()) {
/* 185 */       itemstack.field_77994_a = func_70297_j_();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 191 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 196 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 201 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 206 */     if (this.field_145850_b == null) {
/* 207 */       return true;
/*     */     }
/* 209 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) != this) {
/* 210 */       return false;
/*     */     }
/* 212 */     return (player.func_70092_e(this.field_145851_c + 0.5D, this.field_145848_d + 0.5D, this.field_145849_e + 0.4D) < 64.0D);
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
/* 225 */     return (i == 1 && itemstack.func_77973_b().equals(Items.field_151122_aG));
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 230 */     return new int[] { 1 };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int slot, ItemStack item, int side) {
/* 235 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int slot, ItemStack item, int side) {
/* 240 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound compound) {
/* 247 */     NBTTagCompound[] tag = new NBTTagCompound[this.items.length];
/*     */     
/* 249 */     for (int i = 0; i < this.items.length; i++) {
/* 250 */       tag[i] = new NBTTagCompound();
/*     */       
/* 252 */       if (this.items[i] != null) {
/* 253 */         tag[i] = this.items[i].func_77955_b(tag[i]);
/*     */       }
/*     */       
/* 256 */       compound.func_74782_a("Item" + i, (NBTBase)tag[i]);
/*     */     } 
/* 258 */     compound.func_74757_a("IsValid", this.isValidRecipe);
/* 259 */     compound.func_74768_a("Cost", this.dissenchantCost);
/* 260 */     compound.func_74776_a("ServivalChance", this.bookPower);
/* 261 */     super.func_145841_b(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound compound) {
/* 266 */     NBTTagCompound[] tag = new NBTTagCompound[this.items.length];
/*     */     
/* 268 */     for (int i = 0; i < this.items.length; i++) {
/* 269 */       tag[i] = compound.func_74775_l("Item" + i);
/* 270 */       this.items[i] = ItemStack.func_77949_a(tag[i]);
/*     */     } 
/* 272 */     this.isValidRecipe = compound.func_74767_n("IsValid");
/* 273 */     this.dissenchantCost = compound.func_74762_e("Cost");
/* 274 */     this.bookPower = compound.func_74760_g("ServivalChance");
/* 275 */     super.func_145839_a(compound);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\TileDissEnchanter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */