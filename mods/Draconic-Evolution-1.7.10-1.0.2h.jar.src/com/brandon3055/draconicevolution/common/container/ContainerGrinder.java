/*    */ package com.brandon3055.draconicevolution.common.container;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.inventory.SlotItemValid;
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileGrinder;
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileObjectSync;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ContainerGrinder extends ContainerDataSync {
/* 15 */   private long energyCach = -1L; private TileGrinder tile;
/* 16 */   private long energy2Cach = -1L;
/* 17 */   private int burnCach = -1;
/* 18 */   private int burnRemainingCach = -1;
/*    */   
/*    */   public ContainerGrinder(InventoryPlayer invPlayer, TileGrinder tile) {
/* 21 */     this.tile = tile;
/*    */     
/* 23 */     for (int x = 0; x < 9; x++) {
/* 24 */       func_75146_a(new Slot((IInventory)invPlayer, x, 8 + 18 * x, 138));
/*    */     }
/*    */     
/* 27 */     for (int y = 0; y < 3; y++) {
/* 28 */       for (int i = 0; i < 9; i++) {
/* 29 */         func_75146_a(new Slot((IInventory)invPlayer, i + y * 9 + 9, 8 + 18 * i, 80 + y * 18));
/*    */       }
/*    */     } 
/* 32 */     if (!tile.isExternallyPowered()) { func_75146_a((Slot)new SlotItemValid((IInventory)tile, 0, 64, 35, true)); }
/* 33 */     else { func_75146_a((Slot)new SlotItemValid((IInventory)tile, 0, -10000, -10000, true)); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer player) {
/* 39 */     return this.tile.func_70300_a(player);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack func_82846_b(EntityPlayer player, int i) {
/* 45 */     Slot slot = func_75139_a(i);
/*    */     
/* 47 */     if (slot != null && slot.func_75216_d()) {
/* 48 */       ItemStack stack = slot.func_75211_c();
/* 49 */       ItemStack result = stack.func_77946_l();
/*    */       
/* 51 */       if (i >= 36) {
/* 52 */         if (!func_75135_a(stack, 0, 36, false)) {
/* 53 */           return null;
/*    */         }
/* 55 */       } else if (TileGrinder.getItemBurnTime(stack) == 0 || !func_75135_a(stack, 36, 36 + this.tile.func_70302_i_(), false)) {
/* 56 */         return null;
/*    */       } 
/*    */       
/* 59 */       if (stack.field_77994_a == 0) {
/* 60 */         slot.func_75215_d(null);
/*    */       } else {
/* 62 */         slot.func_75218_e();
/*    */       } 
/*    */       
/* 65 */       slot.func_82870_a(player, stack);
/*    */       
/* 67 */       return result;
/*    */     } 
/*    */     
/* 70 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75142_b() {
/* 75 */     super.func_75142_b();
/* 76 */     if (this.energyCach != this.tile.externalInputBuffer.getEnergyStored())
/* 77 */       this.energyCach = sendObjectToClient((TileObjectSync)null, 0, this.tile.externalInputBuffer.getEnergyStored()); 
/* 78 */     if (this.energy2Cach != this.tile.internalGenBuffer.getEnergyStored())
/* 79 */       this.energy2Cach = sendObjectToClient((TileObjectSync)null, 1, this.tile.internalGenBuffer.getEnergyStored()); 
/* 80 */     if (this.burnCach != this.tile.burnTime)
/* 81 */       this.burnCach = sendObjectToClient((TileObjectSync)null, 2, this.tile.burnTime); 
/* 82 */     if (this.burnRemainingCach != this.tile.burnTimeRemaining) {
/* 83 */       this.burnRemainingCach = sendObjectToClient((TileObjectSync)null, 3, this.tile.burnTimeRemaining);
/*    */     }
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void receiveSyncData(int index, long value) {
/* 89 */     if (index == 0) { this.tile.externalInputBuffer.setEnergyStored(value); }
/* 90 */     else if (index == 1) { this.tile.internalGenBuffer.setEnergyStored(value); }
/* 91 */     else if (index == 2) { this.tile.burnTime = (int)value; }
/* 92 */     else if (index == 3) { this.tile.burnTimeRemaining = (int)value; }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\container\ContainerGrinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */