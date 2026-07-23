/*     */ package com.brandon3055.draconicevolution.common.blocks;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TilePotentiometer;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ public class Potentiometer
/*     */   extends BlockDE
/*     */ {
/*  25 */   IIcon[] icons = new IIcon[16];
/*     */   
/*     */   public Potentiometer() {
/*  28 */     super(Material.field_151594_q);
/*  29 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/*  30 */     func_149711_c(0.3F);
/*  31 */     func_149752_b(0.1F);
/*  32 */     func_149715_a(0.3F);
/*  33 */     func_149663_c("potentiometer");
/*  34 */     ModBlocks.register(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/*  40 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149668_a(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
/*  45 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTileEntity(int metadata) {
/*  50 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity createTileEntity(World world, int metadata) {
/*  55 */     return (TileEntity)new TilePotentiometer();
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister iconRegister) {
/*  61 */     for (int i = 0; i < 16; i++) {
/*  62 */       this.icons[i] = iconRegister.func_94245_a("draconicevolution:potentiometer/potentiometer_" + i);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_149673_e(IBlockAccess world, int x, int y, int z, int meta) {
/*  68 */     TileEntity tile = world.func_147438_o(x, y, z);
/*  69 */     if (tile instanceof TilePotentiometer) return this.icons[((TilePotentiometer)tile).power]; 
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_149691_a(int side, int meta) {
/*  75 */     return this.icons[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149742_c(World world, int x, int y, int z) {
/*  80 */     return (world.isSideSolid(x, y + 1, z, ForgeDirection.UP) || world.isSideSolid(x, y - 1, z, ForgeDirection.DOWN) || world.isSideSolid(x - 1, y, z, ForgeDirection.EAST) || world.isSideSolid(x + 1, y, z, ForgeDirection.WEST) || world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH) || world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149719_a(IBlockAccess world, int x, int y, int z) {
/*  85 */     int l = world.func_72805_g(x, y, z);
/*  86 */     setBounds(l);
/*     */   }
/*     */   
/*     */   private void setBounds(int meta) {
/*  90 */     float f = 0.206F;
/*  91 */     float f1 = 0.796F;
/*  92 */     float f2 = 0.296F;
/*  93 */     float f3 = 0.125F;
/*     */     
/*  95 */     if (meta == 6) {
/*  96 */       func_149676_a(0.5F - f2, 0.0F, 0.5F - f2, 0.5F + f2, f3, 0.5F + f2);
/*     */     }
/*  98 */     if (meta == 1) {
/*  99 */       func_149676_a(0.0F, f, 0.5F - f2, f3, f1, 0.5F + f2);
/* 100 */     } else if (meta == 2) {
/* 101 */       func_149676_a(1.0F - f3, f, 0.5F - f2, 1.0F, f1, 0.5F + f2);
/* 102 */     } else if (meta == 3) {
/* 103 */       func_149676_a(0.5F - f2, f, 0.0F, 0.5F + f2, f1, f3);
/* 104 */     } else if (meta == 4) {
/* 105 */       func_149676_a(0.5F - f2, f, 1.0F - f3, 0.5F + f2, f1, 1.0F);
/*     */     } 
/* 107 */     if (meta == 5) {
/* 108 */       func_149676_a(0.5F - f2, 1.0F - f3, 0.5F - f2, 0.5F + f2, 1.0F, 0.5F + f2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149683_g() {
/* 114 */     float f = 0.206F;
/* 115 */     float f1 = 0.796F;
/* 116 */     float f2 = 0.296F;
/* 117 */     float f3 = 0.125F;
/* 118 */     func_149676_a(0.5F - f2, f, 0.3F, 0.5F + f2, f1, f3 + 0.3F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149660_a(World world, int x, int y, int z, int side, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
/* 123 */     int j1 = world.func_72805_g(x, y, z);
/* 124 */     int k1 = j1 & 0x8;
/* 125 */     j1 &= 0x7;
/*     */     
/* 127 */     ForgeDirection dir = ForgeDirection.getOrientation(side);
/*     */     
/* 129 */     if (dir == ForgeDirection.NORTH && world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH)) {
/* 130 */       j1 = 4;
/* 131 */     } else if (dir == ForgeDirection.SOUTH && world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH)) {
/* 132 */       j1 = 3;
/* 133 */     } else if (dir == ForgeDirection.WEST && world.isSideSolid(x + 1, y, z, ForgeDirection.WEST)) {
/* 134 */       j1 = 2;
/* 135 */     } else if (dir == ForgeDirection.EAST && world.isSideSolid(x - 1, y, z, ForgeDirection.EAST)) {
/* 136 */       j1 = 1;
/* 137 */     } else if (dir == ForgeDirection.UP && world.isSideSolid(x, y - 1, z, ForgeDirection.UP)) {
/* 138 */       j1 = 6;
/* 139 */     } else if (dir == ForgeDirection.DOWN && world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN)) {
/* 140 */       j1 = 5;
/*     */     } else {
/* 142 */       j1 = fundSolidSide(world, x, y, z);
/*     */     } 
/*     */     
/* 145 */     return j1 + k1;
/*     */   }
/*     */   
/*     */   private int fundSolidSide(World world, int x, int y, int z) {
/* 149 */     if (world.isSideSolid(x, y - 1, z, ForgeDirection.UP)) return 6; 
/* 150 */     if (world.isSideSolid(x - 1, y, z, ForgeDirection.EAST)) return 1; 
/* 151 */     if (world.isSideSolid(x + 1, y, z, ForgeDirection.WEST)) return 2; 
/* 152 */     if (world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH)) return 3; 
/* 153 */     if (world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH)) return 4; 
/* 154 */     if (world.isSideSolid(x, y + 1, z, ForgeDirection.UP)) return 5; 
/* 155 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int x, int y, int z, Block block) {
/* 160 */     if (isLocationStillValid(world, x, y, z)) {
/* 161 */       int l = world.func_72805_g(x, y, z) & 0x7;
/* 162 */       boolean flag = (!world.isSideSolid(x, y - 1, z, ForgeDirection.UP) && l == 6);
/*     */       
/* 164 */       if (!world.isSideSolid(x - 1, y, z, ForgeDirection.EAST) && l == 1) {
/* 165 */         flag = true;
/*     */       }
/*     */       
/* 168 */       if (!world.isSideSolid(x + 1, y, z, ForgeDirection.WEST) && l == 2) {
/* 169 */         flag = true;
/*     */       }
/*     */       
/* 172 */       if (!world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH) && l == 3) {
/* 173 */         flag = true;
/*     */       }
/*     */       
/* 176 */       if (!world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH) && l == 4) {
/* 177 */         flag = true;
/*     */       }
/*     */       
/* 180 */       if (!world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN) && l == 5) {
/* 181 */         flag = true;
/*     */       }
/*     */       
/* 184 */       if (flag) {
/* 185 */         func_149697_b(world, x, y, z, world.func_72805_g(x, y, z), 0);
/* 186 */         world.func_147468_f(x, y, z);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isLocationStillValid(World world, int x, int y, int z) {
/* 192 */     if (!func_149742_c(world, x, y, z)) {
/* 193 */       func_149697_b(world, x, y, z, world.func_72805_g(x, y, z), 0);
/* 194 */       world.func_147468_f(x, y, z);
/* 195 */       return false;
/*     */     } 
/* 197 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149744_f() {
/* 203 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/* 208 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149749_a(World world, int x, int y, int z, Block block, int meta) {
/* 213 */     if (meta > 0) {
/* 214 */       updateBlocks(world, x, y, z, meta);
/*     */     }
/*     */     
/* 217 */     super.func_149749_a(world, x, y, z, block, meta);
/*     */   }
/*     */   
/*     */   private void updateBlocks(World world, int x, int y, int z, int meta) {
/* 221 */     world.func_147459_d(x, y, z, this);
/*     */     
/* 223 */     if (meta == 1) {
/* 224 */       world.func_147459_d(x - 1, y, z, this);
/* 225 */     } else if (meta == 2) {
/* 226 */       world.func_147459_d(x + 1, y, z, this);
/* 227 */     } else if (meta == 3) {
/* 228 */       world.func_147459_d(x, y, z - 1, this);
/* 229 */     } else if (meta == 4) {
/* 230 */       world.func_147459_d(x, y, z + 1, this);
/* 231 */     } else if (meta == 5) {
/* 232 */       world.func_147459_d(x, y + 1, z, this);
/* 233 */     } else if (meta == 6) {
/* 234 */       world.func_147459_d(x, y - 1, z, this);
/*     */     } else {
/* 236 */       world.func_147459_d(x, y - 1, z, this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
/* 243 */     TilePotentiometer tile = (TilePotentiometer)world.func_147438_o(x, y, z);
/*     */     
/* 245 */     if (tile instanceof TilePotentiometer)
/* 246 */     { if (!player.func_70093_af()) { tile.increasePower(); }
/* 247 */       else { tile.decreasePower(); }
/*     */       
/* 249 */       if (world.field_72995_K)
/* 250 */         player.func_145747_a((IChatComponent)new ChatComponentText(String.valueOf(tile.power))); 
/* 251 */       world.func_147458_c(x, y, z, x, y, z); }
/* 252 */     else { System.out.println("Invalid tile"); }
/* 253 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149709_b(IBlockAccess world, int x, int y, int z, int side) {
/* 258 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 259 */     if (tile instanceof TilePotentiometer) return ((TilePotentiometer)tile).power; 
/* 260 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149748_c(IBlockAccess world, int x, int y, int z, int side) {
/* 265 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 266 */     if (tile instanceof TilePotentiometer) return ((TilePotentiometer)tile).power; 
/* 267 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\Potentiometer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */