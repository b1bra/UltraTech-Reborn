/*     */ package com.brandon3055.draconicevolution.common.container;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileUpgradeModifier;
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import com.gamerforea.containerwarden.coremod.AsmHooks;
/*     */ import com.gamerforea.draconicevolution.EventConfig;
/*     */ import com.google.common.primitives.Ints;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class ContainerUpgradeModifier extends ContainerDataSync {
/*  19 */   private static final Item[] CORES_INDEX = new Item[] { (Item)ModItems.draconicCore, (Item)ModItems.wyvernCore, (Item)ModItems.awakenedCore, (Item)ModItems.chaoticCore };
/*     */   private TileUpgradeModifier tile;
/*     */   private EntityPlayer player;
/*     */   private boolean slotsActive = true;
/*     */   
/*     */   public ContainerUpgradeModifier(InventoryPlayer invPlayer, TileUpgradeModifier tile) {
/*  25 */     this.tile = tile;
/*  26 */     this.player = invPlayer.field_70458_d;
/*     */     
/*  28 */     for (int x = 0; x < 9; x++) {
/*  29 */       func_75146_a(new Slot((IInventory)invPlayer, x, 8 + 18 * x, 167));
/*     */     }
/*     */     
/*  32 */     for (int y = 0; y < 3; y++) {
/*  33 */       for (int i = 0; i < 9; i++) {
/*  34 */         func_75146_a(new Slot((IInventory)invPlayer, i + y * 9 + 9, 8 + 18 * i, 111 + y * 18));
/*     */       }
/*     */     } 
/*     */     
/*  38 */     func_75146_a(new SlotUpgradable((IInventory)tile, 0, 112, 48));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_75142_b() {
/*  44 */     super.func_75142_b();
/*  45 */     for (int i = 0; i < this.field_75149_d.size(); i++) {
/*  46 */       ICrafting icrafting = this.field_75149_d.get(i);
/*  47 */       icrafting.func_71112_a(this, 0, 0);
/*     */     } 
/*  49 */     updateSlotState();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_75137_b(int index, int value) {
/*  54 */     super.func_75137_b(index, value);
/*  55 */     if (index == 0)
/*  56 */       updateSlotState(); 
/*     */   }
/*     */   
/*     */   private void updateSlotState() {
/*  60 */     if (this.tile.func_70301_a(0) != null && this.tile.func_70301_a(0)
/*  61 */       .func_77973_b() instanceof IUpgradableItem && this.slotsActive) {
/*  62 */       for (Object o : this.field_75151_b) {
/*  63 */         if (o instanceof Slot && !(o instanceof SlotUpgradable))
/*  64 */           ((Slot)o).field_75223_e += 1000; 
/*     */       } 
/*  66 */       this.slotsActive = false;
/*  67 */     } else if ((this.tile.func_70301_a(0) == null || 
/*  68 */       !(this.tile.func_70301_a(0).func_77973_b() instanceof IUpgradableItem)) && !this.slotsActive) {
/*  69 */       for (Object o : this.field_75151_b) {
/*  70 */         if (o instanceof Slot && !(o instanceof SlotUpgradable))
/*  71 */           ((Slot)o).field_75223_e -= 1000; 
/*     */       } 
/*  73 */       this.slotsActive = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer player) {
/*  79 */     return this.tile.func_70300_a(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_82846_b(EntityPlayer player, int i) {
/*  84 */     Slot slot = func_75139_a(i);
/*     */     
/*  86 */     if (slot != null && slot.func_75216_d()) {
/*  87 */       ItemStack stack = slot.func_75211_c();
/*  88 */       ItemStack result = stack.func_77946_l();
/*     */       
/*  90 */       if (i >= 36) {
/*  91 */         if (!func_75135_a(stack, 0, 36, false))
/*  92 */           return null; 
/*  93 */       } else if (!isStackValidForInventory(stack, 0) || !func_75135_a(stack, 36, 36 + this.tile.func_70302_i_(), false)) {
/*  94 */         return null;
/*     */       } 
/*  96 */       if (stack.field_77994_a == 0) {
/*  97 */         slot.func_75215_d(null);
/*     */       } else {
/*  99 */         slot.func_75218_e();
/*     */       } 
/* 101 */       slot.func_82870_a(player, stack);
/*     */       
/* 103 */       return result;
/*     */     } 
/*     */     
/* 106 */     return null;
/*     */   }
/*     */   
/*     */   private boolean isStackValidForInventory(ItemStack stack, int slot) {
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveSyncData(int index, long value) {
/* 115 */     IUpgradableItem.EnumUpgrade upgrade = IUpgradableItem.EnumUpgrade.getUpgradeByIndex(index);
/* 116 */     int coreTier = Ints.saturatedCast(value / 2L);
/* 117 */     boolean addCore = (value % 2L == 0L);
/* 118 */     ItemStack stack = this.tile.func_70301_a(0);
/* 119 */     if (upgrade == null || stack == null || !(stack.func_77973_b() instanceof IUpgradableItem) || coreTier < 0 || coreTier > 3 || (upgrade.getCoresApplied(stack)[coreTier] <= 0 && !addCore))
/*     */       return; 
/* 121 */     handleCoreTransaction(upgrade, coreTier, addCore, (IUpgradableItem)stack.func_77973_b(), stack);
/*     */   }
/*     */   
/*     */   private void handleCoreTransaction(IUpgradableItem.EnumUpgrade upgrade, int coreTier, boolean addCoreElseRemove, IUpgradableItem upgradableItem, ItemStack stack) {
/* 125 */     int coreSlots = upgradableItem.getUpgradeCap(stack);
/* 126 */     int totalCores = 0;
/* 127 */     int[] coresApplied = upgrade.getCoresApplied(stack);
/* 128 */     for (IUpgradableItem.EnumUpgrade u : upgradableItem.getUpgrades(stack)) {
/* 129 */       totalCores += u.getCoresApplied(stack)[coreTier];
/*     */     }
/*     */     
/* 132 */     if (addCoreElseRemove) {
/*     */       
/* 134 */       if (!EventConfig.enableDigSpeedUpgrade && upgrade == IUpgradableItem.EnumUpgrade.DIG_DEPTH) {
/*     */         return;
/*     */       }
/*     */       
/* 138 */       if (!this.player.field_71071_by.func_146028_b(CORES_INDEX[coreTier]) || totalCores >= coreSlots || upgrade.getUpgradePoints(stack) >= upgradableItem.getMaxUpgradePoints(upgrade.index, stack))
/*     */         return; 
/* 140 */       coresApplied[coreTier] = coresApplied[coreTier] + 1;
/* 141 */       this.player.field_71071_by.func_146026_a(CORES_INDEX[coreTier]);
/* 142 */       upgrade.setCoresApplied(stack, coresApplied);
/* 143 */       upgrade.onAppliedToItem(stack);
/*     */     } else {
/* 145 */       if (coresApplied[coreTier] <= 0)
/*     */         return; 
/* 147 */       coresApplied[coreTier] = coresApplied[coreTier] - 1;
/* 148 */       upgrade.setCoresApplied(stack, coresApplied);
/* 149 */       upgrade.onRemovedFromItem(stack);
/* 150 */       if (!this.player.field_71071_by.func_70441_a(new ItemStack(CORES_INDEX[coreTier]))) {
/* 151 */         EntityItem entityItem = new EntityItem(this.player.field_70170_p, this.player.field_70165_t, this.player.field_70163_u, this.player.field_70161_v, new ItemStack(CORES_INDEX[coreTier]));
/* 152 */         if (!this.player.field_70170_p.field_72995_K)
/* 153 */           this.player.field_70170_p.func_72838_d((Entity)entityItem); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class SlotUpgradable
/*     */     extends Slot {
/*     */     public SlotUpgradable(IInventory inventory1, int slot, int x, int y) {
/* 161 */       super(inventory1, slot, x, y);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean func_75214_a(ItemStack stack) {
/* 167 */       return AsmHooks.isItemValid(super.func_75214_a(stack), this, stack);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\container\ContainerUpgradeModifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */