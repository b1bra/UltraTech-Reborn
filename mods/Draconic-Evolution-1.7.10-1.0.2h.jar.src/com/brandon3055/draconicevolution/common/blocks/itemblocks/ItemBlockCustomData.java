/*    */ package com.brandon3055.draconicevolution.common.blocks.itemblocks;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ public class ItemBlockCustomData
/*    */   extends ItemBlock
/*    */ {
/*    */   public ItemBlockCustomData(Block block) {
/* 16 */     super(block);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_77648_a(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
/* 21 */     if (stack.field_77994_a <= 0) return false;
/*    */     
/* 23 */     Block block = world.func_147439_a(x, y, z);
/*    */     
/* 25 */     ForgeDirection sideDir = ForgeDirection.getOrientation(side);
/*    */     
/* 27 */     if (block == null || !block.isReplaceable((IBlockAccess)world, x, y, z)) {
/* 28 */       x += sideDir.offsetX;
/* 29 */       y += sideDir.offsetY;
/* 30 */       z += sideDir.offsetZ;
/*    */     } 
/*    */ 
/*    */     
/* 34 */     if (!player.func_82247_a(x, y, z, side, stack)) return false;
/*    */     
/* 36 */     Block ownBlock = this.field_150939_a;
/* 37 */     if (y == 255 && ownBlock.func_149688_o().func_76220_a()) return false;
/*    */     
/* 39 */     if (!world.func_147472_a(this.field_150939_a, x, y, z, false, side, (Entity)player, stack)) return false;
/*    */     
/* 41 */     int newMeta = func_77647_b(stack.func_77960_j());
/* 42 */     newMeta = ownBlock.func_149660_a(world, x, y, z, side, hitX, hitY, hitZ, newMeta);
/*    */     
/* 44 */     if (!placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, newMeta)) return false;
/*    */     
/* 46 */     world.func_72908_a(x + 0.5D, y + 0.5D, z + 0.5D, ownBlock.field_149762_H.func_150495_a(), (ownBlock.field_149762_H.func_150497_c() + 1.0F) / 2.0F, ownBlock.field_149762_H.func_150494_d() * 0.8F);
/* 47 */     stack.field_77994_a--;
/* 48 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\itemblocks\ItemBlockCustomData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */