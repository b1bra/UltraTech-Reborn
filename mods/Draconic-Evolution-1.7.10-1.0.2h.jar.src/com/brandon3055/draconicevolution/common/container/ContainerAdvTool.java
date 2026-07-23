/*     */ package com.brandon3055.draconicevolution.common.container;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.common.inventory.InventoryTool;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileObjectSync;
/*     */ import com.brandon3055.draconicevolution.common.utills.IInventoryTool;
/*     */ import com.gamerforea.containerwarden.coremod.AsmHooks;
/*     */ import com.google.common.primitives.Ints;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerAdvTool
/*     */   extends ContainerDataSync
/*     */ {
/*     */   public InventoryTool inventoryTool;
/*     */   private EntityPlayer player;
/*  27 */   public int inventoryItemSlot = -1;
/*     */   private boolean slotsActive = false;
/*     */   
/*     */   public ContainerAdvTool(InventoryPlayer invPlayer, InventoryTool inventory) {
/*  31 */     this.inventoryTool = inventory;
/*  32 */     this.inventoryTool.setContainer(this);
/*  33 */     this.player = invPlayer.field_70458_d;
/*  34 */     setSlotsActive(false);
/*     */   }
/*     */   
/*     */   public void setSlotsActive(boolean active) {
/*  38 */     this.slotsActive = active;
/*  39 */     this.field_75153_a = new ArrayList();
/*  40 */     this.field_75151_b = new ArrayList();
/*     */     
/*  42 */     if (this.slotsActive) {
/*  43 */       for (int j = 0; j < 9; j++) {
/*  44 */         func_75146_a(new SlotPlayerInv((IInventory)this.player.field_71071_by, j, 8 + 18 * j, 103, this.inventoryItemSlot));
/*     */       }
/*     */       
/*  47 */       for (int i = 0; i < 3; i++) {
/*  48 */         for (int k = 0; k < 9; k++) {
/*  49 */           func_75146_a(new SlotPlayerInv((IInventory)this.player.field_71071_by, k + i * 9 + 9, 8 + 18 * k, 45 + i * 18, this.inventoryItemSlot));
/*     */         }
/*     */       } 
/*     */       
/*  53 */       for (int x = 0; x < this.inventoryTool.size; x++) {
/*  54 */         func_75146_a(new SlotOblitFilter((IInventory)this.inventoryTool, x, 8 + 18 * x, 19));
/*     */       }
/*     */       
/*  57 */       for (int y = 0; y < 5; y++) {
/*  58 */         func_75146_a(new SlotEnchantment((IInventory)this.inventoryTool, this.inventoryTool.size + y, 173, 19 + y * 21));
/*     */       }
/*     */     } else {
/*  61 */       for (int x = 0; x < 9; x++) {
/*  62 */         func_75146_a(new SlotPlayerInv((IInventory)this.player.field_71071_by, x, -992 + 18 * x, -897, this.inventoryItemSlot));
/*     */       }
/*     */       
/*  65 */       for (int y = 0; y < 3; y++) {
/*  66 */         for (int i = 0; i < 9; i++) {
/*  67 */           func_75146_a(new SlotPlayerInv((IInventory)this.player.field_71071_by, i + y * 9 + 9, -992 + 18 * i, -955 + y * 18, this.inventoryItemSlot));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     if (this.player.field_70170_p.field_72995_K) {
/*  73 */       sendObjectToServer((TileObjectSync)null, 1, active ? 1L : 0L);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setInventory(InventoryTool inventory) {
/*  78 */     this.inventoryTool = inventory;
/*     */   }
/*     */   
/*     */   public void setPlayer(EntityPlayer player) {
/*  82 */     this.player = player;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer player) {
/*  87 */     return true;
/*     */   }
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
/*     */   public ItemStack func_82846_b(EntityPlayer player, int i) {
/* 118 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveSyncData(int index, long value) {
/* 123 */     if (index == 0) {
/* 124 */       updateInventoryStack(Ints.saturatedCast(value));
/* 125 */     } else if (index == 1) {
/* 126 */       setSlotsActive((value == 1L));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateInventoryStack(int slot) {
/* 131 */     this.inventoryItemSlot = slot;
/* 132 */     if (this.player.field_70170_p.field_72995_K) {
/* 133 */       sendObjectToServer((TileObjectSync)null, 0, slot);
/*     */     }
/*     */     
/* 136 */     if (this.player.field_71071_by.func_70301_a(slot) != null && this.player.field_71071_by.func_70301_a(slot).func_77973_b() instanceof IInventoryTool)
/* 137 */       this.inventoryTool.setAndReadFromStack(this.player.field_71071_by.func_70301_a(slot), slot); 
/*     */   }
/*     */   
/*     */   public class SlotPlayerInv
/*     */     extends Slot {
/* 142 */     public int inventoryItemSlot = -1;
/*     */     
/*     */     public SlotPlayerInv(IInventory iInventory, int slot, int x, int y, int inventoryItemSlot) {
/* 145 */       super(iInventory, slot, x, y);
/* 146 */       this.inventoryItemSlot = inventoryItemSlot;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_82869_a(EntityPlayer player) {
/* 151 */       return (this.inventoryItemSlot != this.field_75222_d && super.func_82869_a(player));
/*     */     }
/*     */   }
/*     */   
/*     */   public class SlotOblitFilter extends Slot {
/*     */     public SlotOblitFilter(IInventory iInventory, int slot, int x, int y) {
/* 157 */       super(iInventory, slot, x, y);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_75214_a(ItemStack stack) {
/* 162 */       return AsmHooks.isItemValid((stack != null && stack.func_77973_b() instanceof net.minecraft.item.ItemBlock && super.func_75214_a(stack)), this, stack);
/*     */     }
/*     */   }
/*     */   
/*     */   public class SlotEnchantment
/*     */     extends Slot
/*     */   {
/*     */     InventoryTool field_75224_c;
/*     */     
/*     */     public SlotEnchantment(IInventory iInventory, int slot, int x, int y) {
/* 172 */       super(iInventory, slot, x, y);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_75214_a(ItemStack stack) {
/* 177 */       if (stack == null || stack.func_77973_b() != Items.field_151134_bR || !stack.func_77942_o()) return AsmHooks.isItemValid(false, this, stack); 
/* 178 */       NBTTagList list = stack.func_77978_p().func_150295_c("StoredEnchantments", 10);
/* 179 */       if (list.func_74745_c() != 1) return AsmHooks.isItemValid(false, this, stack); 
/* 180 */       Enchantment enchant = Enchantment.field_77331_b[list.func_150305_b(0).func_74762_e("id")];
/* 181 */       if (ContainerAdvTool.this.inventoryTool.inventoryItem == null || !(ContainerAdvTool.this.inventoryTool.inventoryItem.func_77973_b() instanceof IInventoryTool) || (!((IInventoryTool)ContainerAdvTool.this.inventoryTool.inventoryItem.func_77973_b()).isEnchantValid(enchant) && enchant.field_77351_y != EnumEnchantmentType.all))
/* 182 */         return AsmHooks.isItemValid(false, this, stack); 
/* 183 */       if (EnchantmentHelper.func_82781_a(ContainerAdvTool.this.inventoryTool.inventoryItem).containsKey(Integer.valueOf(list.func_150305_b(0).func_74762_e("id"))))
/* 184 */         return AsmHooks.isItemValid(false, this, stack); 
/* 185 */       return AsmHooks.isItemValid(super.func_75214_a(stack), this, stack);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\container\ContainerAdvTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */