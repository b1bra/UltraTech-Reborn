/*    */ package com.brandon3055.draconicevolution.common.blocks;
/*    */ 
/*    */ import com.brandon3055.brandonscore.common.utills.InventoryUtils;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BlockCustomDrop
/*    */   extends BlockContainerDE
/*    */ {
/*    */   public BlockCustomDrop(Material material) {
/* 20 */     super(material);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract boolean dropInventory();
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract boolean hasCustomDropps();
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract void getCustomTileEntityDrops(TileEntity paramTileEntity, List<ItemStack> paramList);
/*    */ 
/*    */ 
/*    */   
/*    */   private void getCustomDrops(TileEntity te, List<ItemStack> droppes) {
/* 39 */     if (te == null)
/* 40 */       return;  if (hasCustomDropps()) {
/* 41 */       getCustomTileEntityDrops(te, droppes);
/*    */     }
/* 43 */     if (dropInventory() && te instanceof IInventory) {
/* 44 */       droppes.addAll(InventoryUtils.getInventoryContents((IInventory)te));
/* 45 */       for (int i = 0; i < ((IInventory)te).func_70302_i_(); i++) {
/* 46 */         ((IInventory)te).func_70299_a(i, null);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
/* 52 */     if (willHarvest) {
/* 53 */       TileEntity te = world.func_147438_o(x, y, z);
/*    */       
/* 55 */       boolean result = super.removedByPlayer(world, player, x, y, z, willHarvest);
/*    */       
/* 57 */       if (result) {
/* 58 */         List<ItemStack> teDrops = Lists.newArrayList();
/* 59 */         getCustomDrops(te, teDrops);
/* 60 */         for (ItemStack drop : teDrops) {
/* 61 */           func_149642_a(world, x, y, z, drop);
/*    */         }
/*    */       } 
/* 64 */       return result;
/*    */     } 
/*    */     
/* 67 */     return super.removedByPlayer(world, player, x, y, z, willHarvest);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
/* 72 */     ArrayList<ItemStack> result = Lists.newArrayList();
/* 73 */     if (!hasCustomDropps()) result.addAll(super.getDrops(world, x, y, z, metadata, fortune)); 
/* 74 */     if (hasCustomDropps() || dropInventory()) {
/* 75 */       TileEntity te = world.func_147438_o(x, y, z);
/* 76 */       getCustomDrops(te, result);
/*    */     } 
/*    */     
/* 79 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\BlockCustomDrop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */