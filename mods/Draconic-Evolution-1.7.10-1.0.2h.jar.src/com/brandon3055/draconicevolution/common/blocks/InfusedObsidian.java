/*    */ package com.brandon3055.draconicevolution.common.blocks;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*    */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraft.world.Explosion;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InfusedObsidian
/*    */   extends BlockDE
/*    */ {
/*    */   public InfusedObsidian() {
/* 29 */     super(Material.field_151576_e);
/* 30 */     func_149711_c(100.0F);
/* 31 */     func_149752_b(4000.0F);
/* 32 */     func_149663_c("infusedObsidian");
/* 33 */     setHarvestLevel("pickaxe", 4);
/* 34 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/*    */     
/* 36 */     ModBlocks.register(this, InfusedObsidianItemBlock.class);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_149651_a(IIconRegister iconRegister) {
/* 42 */     this.field_149761_L = iconRegister.func_94245_a("draconicevolution:animated/infusedObsidian");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
/* 47 */     return entity instanceof EntityPlayer;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {}
/*    */ 
/*    */   
/*    */   public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
/* 56 */     return super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149659_a(Explosion p_149659_1_) {
/* 61 */     return false;
/*    */   }
/*    */   
/*    */   public static class InfusedObsidianItemBlock
/*    */     extends ItemBlock {
/*    */     public InfusedObsidianItemBlock(Block p_i45328_1_) {
/* 67 */       super(p_i45328_1_);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     @SideOnly(Side.CLIENT)
/*    */     public void func_77624_a(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
/* 74 */       list.add(EnumChatFormatting.GRAY + StatCollector.func_74838_a("info.infusedObsidian.txt"));
/* 75 */       super.func_77624_a(p_77624_1_, p_77624_2_, list, p_77624_4_);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\InfusedObsidian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */