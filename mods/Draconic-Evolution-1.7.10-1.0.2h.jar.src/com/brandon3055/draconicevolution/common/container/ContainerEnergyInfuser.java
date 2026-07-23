/*    */ package com.brandon3055.draconicevolution.common.container;
/*    */ 
/*    */ import com.brandon3055.brandonscore.common.utills.IC2Helper;
/*    */ import com.brandon3055.draconicevolution.common.inventory.SlotChargable;
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileEnergyInfuser;
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileObjectSync;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ContainerEnergyInfuser extends ContainerDataSync {
/*    */   private TileEnergyInfuser tile;
/* 17 */   private long lastTickEnergyStorage = -1L; private EntityPlayer player;
/*    */   
/*    */   public ContainerEnergyInfuser(InventoryPlayer invPlayer, TileEnergyInfuser tile) {
/* 20 */     this.tile = tile;
/* 21 */     this.player = invPlayer.field_70458_d;
/*    */     
/* 23 */     for (int x = 0; x < 9; x++) {
/* 24 */       func_75146_a(new Slot((IInventory)invPlayer, x, 8 + 18 * x, 116));
/*    */     }
/*    */     
/* 27 */     for (int y = 0; y < 3; y++) {
/* 28 */       for (int i = 0; i < 9; i++) {
/* 29 */         func_75146_a(new Slot((IInventory)invPlayer, i + y * 9 + 9, 8 + 18 * i, 58 + y * 18));
/*    */       }
/*    */     } 
/*    */     
/* 33 */     func_75146_a((Slot)new SlotChargable((IInventory)tile, 0, 80, 22));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer player) {
/* 38 */     return this.tile.func_70300_a(player);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_82846_b(EntityPlayer player, int i) {
/* 43 */     Slot slot = func_75139_a(i);
/*    */     
/* 45 */     if (slot != null && slot.func_75216_d()) {
/* 46 */       ItemStack stack = slot.func_75211_c();
/* 47 */       ItemStack result = stack.func_77946_l();
/*    */       
/* 49 */       if (i >= 36) {
/* 50 */         if (!func_75135_a(stack, 0, 36, false)) {
/* 51 */           return null;
/*    */         }
/* 53 */       } else if (stack.field_77994_a != 1 || !IC2Helper.isElectricItem(stack) || !func_75135_a(stack, 36, 37, false)) {
/* 54 */         return null;
/*    */       } 
/*    */       
/* 57 */       if (stack.field_77994_a == 0) {
/* 58 */         slot.func_75215_d(null);
/*    */       } else {
/* 60 */         slot.func_75218_e();
/*    */       } 
/*    */       
/* 63 */       slot.func_82870_a(player, stack);
/*    */       
/* 65 */       return result;
/*    */     } 
/*    */     
/* 68 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75142_b() {
/* 73 */     super.func_75142_b();
/* 74 */     if (this.tile.energy.getEnergyStored() != this.lastTickEnergyStorage) {
/* 75 */       this.lastTickEnergyStorage = sendObjectToClient((TileObjectSync)null, 0, this.tile.energy.getEnergyStored());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void receiveSyncData(int index, long value) {
/* 82 */     this.tile.energy.setEnergyStored(value);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\container\ContainerEnergyInfuser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */