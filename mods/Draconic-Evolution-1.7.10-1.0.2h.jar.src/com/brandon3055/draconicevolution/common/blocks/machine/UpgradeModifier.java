/*    */ package com.brandon3055.draconicevolution.common.blocks.machine;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*    */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*    */ import com.brandon3055.draconicevolution.common.blocks.BlockCustomDrop;
/*    */ import com.brandon3055.draconicevolution.common.blocks.BlockDE;
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileUpgradeModifier;
/*    */ import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UpgradeModifier
/*    */   extends BlockCustomDrop
/*    */ {
/*    */   public UpgradeModifier() {
/* 27 */     super(Material.field_151573_f);
/* 28 */     func_149663_c("upgradeModifier");
/* 29 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/* 30 */     func_149672_a(field_149769_e);
/* 31 */     func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
/* 32 */     ModBlocks.register((BlockDE)this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_149651_a(IIconRegister iconRegister) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World world, int meta) {
/* 43 */     return (TileEntity)new TileUpgradeModifier();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float prx, float pry, float prz) {
/* 48 */     if (!world.field_72995_K) {
/* 49 */       FMLNetworkHandler.openGui(player, DraconicEvolution.instance, 15, world, x, y, z);
/*    */     }
/* 51 */     world.func_147471_g(x, y, z);
/* 52 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int func_149645_b() {
/* 58 */     return -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149662_c() {
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149686_d() {
/* 68 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean dropInventory() {
/* 73 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean hasCustomDropps() {
/* 78 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void getCustomTileEntityDrops(TileEntity te, List<ItemStack> droppes) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_149646_a(IBlockAccess access, int x, int y, int z, int side) {
/* 88 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/* 93 */     return (side == ForgeDirection.DOWN);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\machine\UpgradeModifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */