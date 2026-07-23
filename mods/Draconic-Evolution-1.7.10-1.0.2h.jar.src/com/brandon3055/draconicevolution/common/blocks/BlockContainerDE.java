/*    */ package com.brandon3055.draconicevolution.common.blocks;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.ITileEntityProvider;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class BlockContainerDE
/*    */   extends BlockDE
/*    */   implements ITileEntityProvider
/*    */ {
/*    */   public BlockContainerDE(Material material) {
/* 15 */     super(material);
/* 16 */     this.field_149758_A = true;
/*    */   }
/*    */   
/*    */   public BlockContainerDE() {
/* 20 */     super(Material.field_151576_e);
/* 21 */     this.field_149758_A = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
/* 26 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_149726_b(World world, int x, int y, int z) {
/* 31 */     super.func_149726_b(world, x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_149749_a(World world, int x, int y, int z, Block block, int meta) {
/* 36 */     super.func_149749_a(world, x, y, z, block, meta);
/* 37 */     world.func_147475_p(x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149696_a(World world, int x, int y, int z, int p_149696_5_, int p_149696_6_) {
/* 42 */     super.func_149696_a(world, x, y, z, p_149696_5_, p_149696_6_);
/* 43 */     TileEntity tileentity = world.func_147438_o(x, y, z);
/* 44 */     return (tileentity != null && tileentity.func_145842_c(p_149696_5_, p_149696_6_));
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\BlockContainerDE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */