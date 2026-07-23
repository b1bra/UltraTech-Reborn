/*     */ package com.brandon3055.draconicevolution.common.container;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.draconicevolution.common.inventory.SlotOutput;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileDissEnchanter;
/*     */ import com.gamerforea.containerwarden.coremod.AsmHooks;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class ContainerDissEnchanter extends Container {
/*     */   private TileDissEnchanter tile;
/*     */   private EntityPlayer player;
/*     */   private ItemStack cachIn0;
/*     */   private ItemStack cachIn1;
/*     */   private ItemStack cachIn2;
/*     */   private boolean nullCheck0 = false;
/*     */   private boolean nullCheck1 = false;
/*     */   private boolean nullCheck2 = false;
/*     */   
/*     */   public ContainerDissEnchanter(InventoryPlayer invPlayer, TileDissEnchanter tile) {
/*  27 */     this.tile = tile;
/*  28 */     this.player = invPlayer.field_70458_d;
/*     */     
/*  30 */     for (int x = 0; x < 9; x++) {
/*  31 */       func_75146_a(new Slot((IInventory)invPlayer, x, 8 + 18 * x, 118));
/*     */     }
/*     */     
/*  34 */     for (int y = 0; y < 3; y++) {
/*  35 */       for (int i = 0; i < 9; i++) {
/*  36 */         func_75146_a(new Slot((IInventory)invPlayer, i + y * 9 + 9, 8 + 18 * i, 60 + y * 18));
/*     */       }
/*     */     } 
/*     */     
/*  40 */     func_75146_a(new SlotEnchantedItem((IInventory)tile, 0, 27, 23));
/*  41 */     func_75146_a(new SlotBook((IInventory)tile, 1, 76, 23));
/*  42 */     func_75146_a((Slot)new SlotOutput((IInventory)tile, 2, 134, 23));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer player) {
/*  47 */     return this.tile.func_70300_a(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_82846_b(EntityPlayer player, int i) {
/*  52 */     Slot slot = func_75139_a(i);
/*     */     
/*  54 */     if (slot != null && slot.func_75216_d()) {
/*  55 */       ItemStack stack = slot.func_75211_c();
/*  56 */       ItemStack result = stack.func_77946_l();
/*     */       
/*  58 */       if (i >= 36) {
/*  59 */         if (!func_75135_a(stack, 0, 36, false)) {
/*  60 */           return null;
/*     */         }
/*  62 */       } else if ((!isStackValidForInventory(stack, 0) || !func_75135_a(stack, 36, 37, false)) && (!isStackValidForInventory(stack, 1) || !func_75135_a(stack, 37, 38, false))) {
/*  63 */         return null;
/*     */       } 
/*     */       
/*  66 */       if (stack.field_77994_a == 0) {
/*  67 */         slot.func_75215_d(null);
/*     */       } else {
/*  69 */         slot.func_75218_e();
/*     */       } 
/*     */       
/*  72 */       slot.func_82870_a(player, stack);
/*     */       
/*  74 */       return result;
/*     */     } 
/*     */     
/*  77 */     return null;
/*     */   }
/*     */   
/*     */   private boolean isStackValidForInventory(ItemStack stack, int slot) {
/*  81 */     if (slot == 0 && !EnchantmentHelper.func_82781_a(stack).isEmpty()) return true; 
/*  82 */     return (slot == 1 && stack.func_77973_b().equals(Items.field_151122_aG));
/*     */   }
/*     */   
/*     */   public class SlotBook extends Slot {
/*     */     public SlotBook(IInventory inventory, int id, int x, int y) {
/*  87 */       super(inventory, id, x, y);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_75214_a(ItemStack stack) {
/*  92 */       return AsmHooks.isItemValid(stack.func_77973_b().equals(Items.field_151122_aG), this, stack);
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_75219_a() {
/*  97 */       return 64;
/*     */     }
/*     */   }
/*     */   
/*     */   public class SlotEnchantedItem extends Slot {
/*     */     public SlotEnchantedItem(IInventory inventory, int id, int x, int y) {
/* 103 */       super(inventory, id, x, y);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_75214_a(ItemStack stack) {
/* 108 */       return AsmHooks.isItemValid((!EnchantmentHelper.func_82781_a(stack).isEmpty() || ItemNBTHelper.getInteger(stack, "RepairCost", 0) > 0), this, stack);
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_75219_a() {
/* 113 */       return 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_75142_b() {
/* 119 */     super.func_75142_b();
/* 120 */     ItemStack stack0 = this.tile.func_70301_a(0);
/* 121 */     ItemStack stack1 = this.tile.func_70301_a(1);
/* 122 */     ItemStack stack2 = this.tile.func_70301_a(2);
/* 123 */     if (((stack0 == null)) != this.nullCheck0) {
/* 124 */       this.tile.onInventoryChanged();
/* 125 */       this.nullCheck0 = (stack0 == null);
/*     */     } 
/* 127 */     if (((stack1 == null)) != this.nullCheck1) {
/* 128 */       this.tile.onInventoryChanged();
/* 129 */       this.nullCheck1 = (stack1 == null);
/*     */     } 
/* 131 */     if (((stack2 == null)) != this.nullCheck2) {
/* 132 */       this.tile.onInventoryChanged();
/* 133 */       this.nullCheck2 = (stack2 == null);
/*     */     } 
/*     */     
/* 136 */     if (stack0 != null && !ItemStack.func_77989_b(stack0, this.cachIn0)) {
/* 137 */       this.cachIn0 = stack0.func_77946_l();
/* 138 */       this.tile.onInventoryChanged();
/*     */     } 
/* 140 */     if (stack1 != null && !ItemStack.func_77989_b(stack1, this.cachIn1)) {
/* 141 */       this.cachIn1 = stack1.func_77946_l();
/* 142 */       this.tile.onInventoryChanged();
/*     */     } 
/* 144 */     if (stack2 != null && !ItemStack.func_77989_b(stack2, this.cachIn2)) {
/* 145 */       this.cachIn2 = stack2.func_77946_l();
/* 146 */       this.tile.onInventoryChanged();
/*     */     } 
/*     */   }
/*     */   
/*     */   public TileDissEnchanter getTile() {
/* 151 */     return this.tile;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\container\ContainerDissEnchanter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */