/*     */ package com.brandon3055.draconicevolution.common.blocks.machine;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.blocks.BlockDE;
/*     */ import com.brandon3055.draconicevolution.common.blocks.itemblocks.ItemBlockFrowGate;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.gates.TileFluidGate;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.gates.TileGate;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlowGate
/*     */   extends BlockDE
/*     */ {
/*     */   IIcon icon_input;
/*     */   IIcon icon_output;
/*  38 */   IIcon[] icon_fluid = new IIcon[4];
/*     */   
/*     */   public FlowGate() {
/*  41 */     func_149663_c("flowGate");
/*  42 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/*     */     
/*  44 */     ModBlocks.register(this, ItemBlockFrowGate.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTileEntity(int metadata) {
/*  49 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity createTileEntity(World world, int metadata) {
/*  54 */     return (TileEntity)new TileFluidGate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149666_a(Item item, CreativeTabs p_149666_2_, List<ItemStack> list) {
/*  59 */     list.add(new ItemStack(item));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister iconRegister) {
/*  65 */     this.field_149761_L = iconRegister.func_94245_a("draconicevolution:machine_side");
/*  66 */     this.icon_input = iconRegister.func_94245_a("draconicevolution:machine_io_i");
/*  67 */     this.icon_output = iconRegister.func_94245_a("draconicevolution:machine_io_o");
/*  68 */     for (int i = 0; i < 4; i++) {
/*  69 */       this.icon_fluid[i] = iconRegister.func_94245_a("draconicevolution:gates/fluidGate" + i);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149691_a(int side, int meta) {
/*  76 */     int f = meta % 6;
/*     */     
/*  78 */     if (side == f) return this.icon_output; 
/*  79 */     if (ForgeDirection.getOrientation(side).getOpposite().ordinal() == f) return this.icon_input;
/*     */     
/*  81 */     int t = 0;
/*  82 */     if (f == 0) { t = 1; }
/*  83 */     else if (f == 1) { t = 3; }
/*  84 */     else if (f == 2)
/*  85 */     { t = (side == 0) ? 3 : ((side == 1) ? 3 : ((side == 4) ? 2 : 0)); }
/*  86 */     else if (f == 3)
/*  87 */     { t = (side == 0) ? 1 : ((side == 1) ? 1 : ((side == 4) ? 0 : 2)); }
/*  88 */     else if (f == 4)
/*  89 */     { t = (side == 0) ? 2 : ((side == 1) ? 2 : ((side == 2) ? 0 : 2)); }
/*  90 */     else if (f == 5)
/*  91 */     { t = (side == 0) ? 0 : ((side == 1) ? 0 : ((side == 2) ? 2 : 0)); }
/*     */     
/*  93 */     return this.icon_fluid[t];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149689_a(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
/*  99 */     int d = determineOrientation(world, x, y, z, entity);
/* 100 */     TileGate gate = (TileGate)world.func_147438_o(x, y, z);
/* 101 */     gate.output = ForgeDirection.getOrientation(d);
/* 102 */     world.func_72921_c(x, y, z, d, 2);
/*     */   }
/*     */   
/*     */   public static int determineOrientation(World world, int x, int y, int z, EntityLivingBase entity) {
/* 106 */     if (MathHelper.func_76135_e((float)entity.field_70165_t - x) < 2.0F && MathHelper.func_76135_e((float)entity.field_70161_v - z) < 2.0F) {
/* 107 */       double d0 = entity.field_70163_u + 1.82D - entity.field_70129_M;
/*     */       
/* 109 */       if (d0 - y > 2.0D) {
/* 110 */         return 0;
/*     */       }
/*     */       
/* 113 */       if (y - d0 > 0.0D) {
/* 114 */         return 1;
/*     */       }
/*     */     } 
/*     */     
/* 118 */     int l = MathHelper.func_76128_c((entity.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3;
/* 119 */     return (l == 0) ? 3 : ((l == 1) ? 4 : ((l == 2) ? 2 : ((l == 3) ? 5 : 0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis) {
/* 124 */     int meta = worldObj.func_72805_g(x, y, z);
/* 125 */     ForgeDirection facing = ForgeDirection.getOrientation(meta % 6);
/*     */     
/* 127 */     if (facing == axis || facing == axis.getOpposite()) return false; 
/* 128 */     if (axis == ForgeDirection.UP || axis == ForgeDirection.DOWN) {
/* 129 */       if (facing == ForgeDirection.NORTH) {
/* 130 */         facing = ForgeDirection.EAST;
/* 131 */       } else if (facing == ForgeDirection.SOUTH) {
/* 132 */         facing = ForgeDirection.WEST;
/* 133 */       } else if (facing == ForgeDirection.EAST) {
/* 134 */         facing = ForgeDirection.SOUTH;
/* 135 */       } else if (facing == ForgeDirection.WEST) {
/* 136 */         facing = ForgeDirection.NORTH;
/*     */       } 
/* 138 */     } else if (axis == ForgeDirection.NORTH || axis == ForgeDirection.SOUTH) {
/* 139 */       if (facing == ForgeDirection.UP) {
/* 140 */         facing = ForgeDirection.WEST;
/* 141 */       } else if (facing == ForgeDirection.DOWN) {
/* 142 */         facing = ForgeDirection.EAST;
/* 143 */       } else if (facing == ForgeDirection.EAST) {
/* 144 */         facing = ForgeDirection.UP;
/* 145 */       } else if (facing == ForgeDirection.WEST) {
/* 146 */         facing = ForgeDirection.DOWN;
/*     */       } 
/* 148 */     } else if (axis == ForgeDirection.EAST || axis == ForgeDirection.WEST) {
/* 149 */       if (facing == ForgeDirection.UP) {
/* 150 */         facing = ForgeDirection.NORTH;
/* 151 */       } else if (facing == ForgeDirection.DOWN) {
/* 152 */         facing = ForgeDirection.SOUTH;
/* 153 */       } else if (facing == ForgeDirection.SOUTH) {
/* 154 */         facing = ForgeDirection.UP;
/* 155 */       } else if (facing == ForgeDirection.NORTH) {
/* 156 */         facing = ForgeDirection.DOWN;
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     ((TileGate)worldObj.func_147438_o(x, y, z)).output = facing;
/* 161 */     worldObj.func_72921_c(x, y, z, facing.ordinal(), 2);
/* 162 */     Utills.updateNeabourBlocks(worldObj, x, y, z);
/* 163 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
/* 168 */     if (world.field_72995_K && (player.func_70694_bm() == null || !player.func_70694_bm().func_77973_b().equals(ModItems.wrench)))
/* 169 */       player.openGui(DraconicEvolution.instance, 13, world, x, y, z); 
/* 170 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int x, int y, int z, Block block) {
/* 175 */     updateSignal((IBlockAccess)world, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
/* 180 */     updateSignal(world, x, y, z);
/*     */   }
/*     */   
/*     */   private void updateSignal(IBlockAccess world, int x, int y, int z) {
/* 184 */     TileGate gate = (world.func_147438_o(x, y, z) instanceof TileGate) ? (TileGate)world.func_147438_o(x, y, z) : null;
/* 185 */     if (gate != null && world instanceof World) {
/* 186 */       gate.signal = ((World)world).func_94572_D(x, y, z);
/* 187 */       ((World)world).func_147471_g(x, y, z);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
/* 193 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\machine\FlowGate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */