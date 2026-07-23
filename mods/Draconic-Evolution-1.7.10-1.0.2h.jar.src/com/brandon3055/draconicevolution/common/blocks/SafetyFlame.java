/*    */ package com.brandon3055.draconicevolution.common.blocks;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockFire;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SafetyFlame
/*    */   extends BlockFire
/*    */ {
/*    */   public SafetyFlame() {
/* 17 */     func_149663_c("safetyFlame");
/* 18 */     func_149658_d("fire");
/* 19 */     func_149715_a(1.0F);
/* 20 */     ModBlocks.registerOther((Block)this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_149739_a() {
/* 25 */     return String.format("tile.%s%s", new Object[] { "draconicevolution:", getUnwrappedUnlocalizedName(super.func_149739_a()) });
/*    */   }
/*    */   
/*    */   public String getUnwrappedUnlocalizedName(String unlocalizedName) {
/* 29 */     return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_149674_a(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
/* 39 */     super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_149726_b(World world, int x, int y, int z) {
/* 44 */     super.func_149726_b(world, x, y, z);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\SafetyFlame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */