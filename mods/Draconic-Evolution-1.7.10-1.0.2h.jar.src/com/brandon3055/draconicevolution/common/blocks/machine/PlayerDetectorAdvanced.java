/*     */ package com.brandon3055.draconicevolution.common.blocks.machine;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.blocks.BlockCustomDrop;
/*     */ import com.brandon3055.draconicevolution.common.blocks.BlockDE;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TilePlayerDetectorAdvanced;
/*     */ import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ public class PlayerDetectorAdvanced
/*     */   extends BlockCustomDrop
/*     */ {
/*     */   IIcon side_inactive;
/*     */   IIcon side_active;
/*     */   IIcon top;
/*     */   IIcon bottom;
/*     */   
/*     */   public PlayerDetectorAdvanced() {
/*  34 */     super(Material.field_151573_f);
/*  35 */     func_149663_c("playerDetectorAdvanced");
/*  36 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/*  37 */     func_149672_a(field_149769_e);
/*  38 */     ModBlocks.register((BlockDE)this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister iconRegister) {
/*  44 */     this.side_inactive = iconRegister.func_94245_a("draconicevolution:advanced_player_detector_side_inactive");
/*  45 */     this.side_active = iconRegister.func_94245_a("draconicevolution:advanced_player_detector_side_active");
/*  46 */     this.top = iconRegister.func_94245_a("draconicevolution:machine_top_0");
/*  47 */     this.bottom = iconRegister.func_94245_a("draconicevolution:machine_side");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149747_d(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_) {
/*  52 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/*  57 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149673_e(IBlockAccess world, int x, int y, int z, int side) {
/*  65 */     TileEntity tile = world.func_147438_o(x, y, z);
/*  66 */     TilePlayerDetectorAdvanced detector = (tile instanceof TilePlayerDetectorAdvanced) ? (TilePlayerDetectorAdvanced)tile : null;
/*  67 */     if (detector != null && detector.func_70301_a(0) != null) {
/*  68 */       ItemStack stack = detector.func_70301_a(0);
/*  69 */       Block block = Block.func_149634_a(stack.func_77973_b());
/*  70 */       if (block != null && block.func_149686_d()) return block.func_149691_a(side, stack.func_77960_j()); 
/*     */     } else {
/*  72 */       IIcon side_icon; if (detector != null && detector.output) { side_icon = this.side_active; }
/*  73 */       else { side_icon = this.side_inactive; }
/*     */       
/*  75 */       if (side == 0) return this.bottom; 
/*  76 */       if (side == 1) return this.top; 
/*  77 */       return side_icon;
/*     */     } 
/*     */     
/*  80 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149691_a(int side, int meta) {
/*  86 */     if (side == 0) return this.bottom; 
/*  87 */     if (side == 1) return this.top; 
/*  88 */     return this.side_active;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World var1, int var2) {
/*  93 */     return (TileEntity)new TilePlayerDetectorAdvanced();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
/*  98 */     if (!world.field_72995_K) {
/*  99 */       FMLNetworkHandler.openGui(player, DraconicEvolution.instance, 6, world, x, y, z);
/*     */     }
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
/* 106 */     return (side != 0 && side != 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149744_f() {
/* 111 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149709_b(IBlockAccess world, int x, int y, int z, int meta) {
/* 116 */     TileEntity te = world.func_147438_o(x, y, z);
/* 117 */     TilePlayerDetectorAdvanced detector = (te instanceof TilePlayerDetectorAdvanced) ? (TilePlayerDetectorAdvanced)te : null;
/* 118 */     if (detector != null) { if (!detector.outputInverted) return detector.output ? 15 : 0; 
/* 119 */       return detector.output ? 0 : 15; }
/* 120 */      return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149748_c(IBlockAccess world, int x, int y, int z, int meta) {
/* 125 */     TileEntity te = world.func_147438_o(x, y, z);
/* 126 */     TilePlayerDetectorAdvanced detector = (te instanceof TilePlayerDetectorAdvanced) ? (TilePlayerDetectorAdvanced)te : null;
/* 127 */     if (detector != null) { if (!detector.outputInverted) return detector.output ? 15 : 0; 
/* 128 */       return detector.output ? 0 : 15; }
/* 129 */      return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean dropInventory() {
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasCustomDropps() {
/* 139 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void getCustomTileEntityDrops(TileEntity te, List<ItemStack> droppes) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
/* 149 */     TileEntity te = world.func_147438_o(x, y, z);
/* 150 */     TilePlayerDetectorAdvanced detector = (te instanceof TilePlayerDetectorAdvanced) ? (TilePlayerDetectorAdvanced)te : null;
/* 151 */     if (detector != null && detector.func_70301_a(0) != null) {
/* 152 */       return detector.func_70301_a(0);
/*     */     }
/* 154 */     return super.getPickBlock(target, world, x, y, z);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\machine\PlayerDetectorAdvanced.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */