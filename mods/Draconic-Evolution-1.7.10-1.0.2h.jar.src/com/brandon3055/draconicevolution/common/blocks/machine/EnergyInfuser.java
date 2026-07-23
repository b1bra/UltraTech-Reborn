/*    */ package com.brandon3055.draconicevolution.common.blocks.machine;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*    */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*    */ import com.brandon3055.draconicevolution.common.blocks.BlockCustomDrop;
/*    */ import com.brandon3055.draconicevolution.common.blocks.BlockDE;
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileEnergyInfuser;
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
/*    */ 
/*    */ 
/*    */ public class EnergyInfuser
/*    */   extends BlockCustomDrop
/*    */ {
/*    */   public EnergyInfuser() {
/* 29 */     super(Material.field_151573_f);
/* 30 */     func_149663_c("energyInfuser");
/* 31 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/* 32 */     func_149672_a(field_149769_e);
/* 33 */     func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
/* 34 */     ModBlocks.register((BlockDE)this);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_149651_a(IIconRegister iconRegister) {
/* 40 */     this.field_149761_L = iconRegister.func_94245_a("draconicevolution:machine_side");
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World world, int meta) {
/* 45 */     return (TileEntity)new TileEnergyInfuser();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float prx, float pry, float prz) {
/* 50 */     if (!world.field_72995_K) {
/* 51 */       FMLNetworkHandler.openGui(player, DraconicEvolution.instance, 7, world, x, y, z);
/*    */     }
/* 53 */     world.func_147471_g(x, y, z);
/* 54 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int func_149645_b() {
/* 60 */     return -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149662_c() {
/* 65 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149686_d() {
/* 70 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean dropInventory() {
/* 75 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean hasCustomDropps() {
/* 80 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void getCustomTileEntityDrops(TileEntity te, List<ItemStack> droppes) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_149646_a(IBlockAccess access, int x, int y, int z, int side) {
/* 90 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/* 95 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\machine\EnergyInfuser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */