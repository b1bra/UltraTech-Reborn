/*     */ package com.brandon3055.draconicevolution.common.inventory;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.draconicevolution.common.container.ContainerAdvTool;
/*     */ import com.brandon3055.draconicevolution.common.utills.IInventoryTool;
/*     */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.StringUtils;
/*     */ 
/*     */ public class InventoryTool
/*     */   implements IInventory
/*     */ {
/*     */   public int size;
/*     */   public ItemStack inventoryItem;
/*     */   private ItemStack[] inventoryStacks;
/*     */   private EntityPlayer player;
/*     */   private ContainerAdvTool container;
/*  25 */   private int slot = -1;
/*     */   
/*     */   public InventoryTool(EntityPlayer player, ItemStack stack) {
/*  28 */     this.inventoryItem = stack;
/*  29 */     this.player = player;
/*  30 */     if (stack != null && stack.func_77973_b() instanceof IInventoryTool) {
/*  31 */       this.size = ((IInventoryTool)stack.func_77973_b()).getInventorySlots();
/*  32 */       readFromNBT(ItemNBTHelper.getCompound(stack));
/*     */     } 
/*  34 */     this.inventoryStacks = new ItemStack[this.size + 5];
/*     */   }
/*     */   
/*     */   public void setAndReadFromStack(ItemStack stack, int slot) {
/*  38 */     this.slot = slot;
/*  39 */     this.inventoryItem = stack;
/*  40 */     this.size = ((IInventoryTool)stack.func_77973_b()).getInventorySlots();
/*  41 */     this.inventoryStacks = new ItemStack[this.size + 5];
/*  42 */     readFromNBT(ItemNBTHelper.getCompound(this.inventoryItem));
/*     */   }
/*     */   
/*     */   public void setContainer(ContainerAdvTool container) {
/*  46 */     this.container = container;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  51 */     return this.inventoryStacks.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/*  56 */     return this.inventoryStacks[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int count) {
/*  61 */     ItemStack itemstack = func_70301_a(i);
/*     */     
/*  63 */     if (itemstack != null) {
/*  64 */       if (itemstack.field_77994_a <= count) {
/*  65 */         func_70299_a(i, null);
/*     */       } else {
/*  67 */         itemstack = itemstack.func_77979_a(count);
/*  68 */         if (itemstack.field_77994_a == 0) {
/*  69 */           func_70299_a(i, null);
/*     */         }
/*     */       } 
/*     */     }
/*  73 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int i) {
/*  78 */     ItemStack item = func_70301_a(i);
/*  79 */     if (item != null) func_70299_a(i, null); 
/*  80 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/*  85 */     this.inventoryStacks[i] = itemstack;
/*  86 */     if (itemstack != null && itemstack.field_77994_a > func_70297_j_()) {
/*  87 */       itemstack.field_77994_a = func_70297_j_();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  93 */     return (this.inventoryItem != null && this.inventoryItem.func_77973_b() instanceof IInventoryTool) ? (!StringUtils.func_151246_b(((IInventoryTool)this.inventoryItem.func_77973_b()).getInventoryName()) ? ((IInventoryTool)this.inventoryItem.func_77973_b()).getInventoryName() : "") : "";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 103 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/* 109 */     for (int i = 0; i < func_70302_i_(); i++) {
/* 110 */       if (func_70301_a(i) != null && (func_70301_a(i)).field_77994_a == 0) {
/* 111 */         this.inventoryStacks[i] = null;
/*     */       }
/*     */     } 
/*     */     
/* 115 */     if (getItem() != null) {
/* 116 */       writeToNBT(ItemNBTHelper.getCompound(getItem()));
/* 117 */       readFromNBT(ItemNBTHelper.getCompound(getItem()));
/*     */     } else {
/* 119 */       LogHelper.error("[InventoryItem] storage item == null This is not a good thing...");
/*     */     } 
/*     */ 
/*     */     
/* 123 */     this.container.func_75142_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 128 */     return true;
/*     */   }
/*     */   
/*     */   private ItemStack getItem() {
/* 132 */     if (this.slot != -1 && this.player.field_71071_by.func_70301_a(this.slot) != null && this.inventoryItem != null && this.player.field_71071_by.func_70301_a(this.slot).func_77973_b() == this.inventoryItem.func_77973_b()) {
/* 133 */       return this.player.field_71071_by.func_70301_a(this.slot);
/*     */     }
/* 135 */     LogHelper.error("Error getting inventory item [InventoryTool#getItem() - " + this.slot + "]");
/* 136 */     return null;
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
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 150 */     return true;
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 154 */     NBTTagCompound[] tag = new NBTTagCompound[this.size];
/* 155 */     NBTTagList enchList = new NBTTagList();
/*     */     
/* 157 */     for (int i = 0; i < func_70302_i_(); i++) {
/* 158 */       if (i < this.size) {
/* 159 */         tag[i] = new NBTTagCompound();
/*     */         
/* 161 */         if (this.inventoryStacks[i] != null) {
/* 162 */           tag[i] = this.inventoryStacks[i].func_77955_b(tag[i]);
/*     */         }
/*     */         
/* 165 */         compound.func_74782_a("Item" + i, (NBTBase)tag[i]);
/* 166 */       } else if (this.inventoryStacks[i] != null) {
/* 167 */         if (this.inventoryStacks[i].func_77978_p() == null) { this.inventoryStacks[i] = null; }
/*     */         else
/* 169 */         { enchList.func_74742_a((NBTBase)this.inventoryStacks[i].func_77978_p().func_150295_c("StoredEnchantments", 10).func_150305_b(0)); }
/*     */       
/*     */       } 
/* 172 */       compound.func_74782_a("ench", (NBTBase)enchList);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     if (compound.func_74764_b("ench") && compound.func_150295_c("ench", 10).func_74745_c() == 0) compound.func_82580_o("ench"); 
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 188 */     NBTTagCompound[] tag = new NBTTagCompound[this.size];
/* 189 */     NBTTagList enchList = null;
/* 190 */     if (compound.func_74764_b("ench")) enchList = compound.func_150295_c("ench", 10);
/*     */     
/* 192 */     for (int i = 0; i < func_70302_i_(); i++) {
/* 193 */       this.inventoryStacks[i] = null;
/* 194 */       if (i < this.size) {
/* 195 */         tag[i] = compound.func_74775_l("Item" + i);
/* 196 */         this.inventoryStacks[i] = ItemStack.func_77949_a(tag[i]);
/* 197 */       } else if (enchList != null && enchList.func_74745_c() > i - this.size) {
/* 198 */         this.inventoryStacks[i] = new ItemStack((Item)Items.field_151134_bR);
/* 199 */         this.inventoryStacks[i].func_77982_d(new NBTTagCompound());
/* 200 */         NBTTagList list = new NBTTagList();
/* 201 */         list.func_74742_a((NBTBase)enchList.func_150305_b(i - this.size));
/* 202 */         this.inventoryStacks[i].func_77978_p().func_74782_a("StoredEnchantments", (NBTBase)list);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     if (compound.func_74764_b("ench") && compound.func_150295_c("ench", 10).func_74745_c() == 0) compound.func_82580_o("ench"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\inventory\InventoryTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */