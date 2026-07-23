/*     */ package com.brandon3055.draconicevolution.common.blocks.multiblock;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.blocks.BlockDE;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.TileEnergyPylon;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import com.brandon3055.brandonscore.common.tags.Tags;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnergyPylon
/*     */   extends BlockDE
/*     */ {
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon icon_active_face;
/*     */   public IIcon icon_input;
/*     */   public IIcon icon_output;
/*     */   
/*     */   public EnergyPylon() {
/*  31 */     super(Material.field_151573_f);
/*  32 */     func_149711_c(10.0F);
/*  33 */     func_149752_b(20.0F);
/*  34 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/*  35 */     func_149663_c("energyPylon");
/*  36 */     ModBlocks.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTileEntity(int metadata) {
/*  41 */     return (metadata == 1 || metadata == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity createTileEntity(World world, int metadata) {
/*  46 */     if (metadata == 1 || metadata == 2) return (TileEntity)new TileEnergyPylon(); 
/*  47 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister iconRegister) {
/*  53 */     this.icon_input = iconRegister.func_94245_a("draconicevolution:energy_pylon_input");
/*  54 */     this.icon_output = iconRegister.func_94245_a("draconicevolution:energy_pylon_output");
/*  55 */     this.icon_active_face = iconRegister.func_94245_a("draconicevolution:energy_pylon_active_face");
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149691_a(int side, int meta) {
/*  61 */     if (meta == 1 && side == 1) return this.icon_active_face; 
/*  62 */     if (meta == 2 && side == 0) return this.icon_active_face; 
/*  63 */     return this.icon_input;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149673_e(IBlockAccess world, int x, int y, int z, int side) {
/*  69 */     int meta = world.func_72805_g(x, y, z);
/*  70 */     if (meta == 1 && side == 1) return this.icon_active_face; 
/*  71 */     if (meta == 2 && side == 0) return this.icon_active_face; 
/*  72 */     TileEnergyPylon thisTile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TileEnergyPylon) ? (TileEnergyPylon)world.func_147438_o(x, y, z) : null;
/*  73 */     if (thisTile == null) return this.icon_input; 
/*  74 */     return !thisTile.reciveEnergy ? this.icon_output : this.icon_input;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int x, int y, int z, Block p_149695_5_) {
/*  79 */     int meta = world.func_72805_g(x, y, z);
/*  80 */     if (meta == 0) {
/*  81 */       if (Tags.Blocks.GLASS.is((IBlockAccess)world, x, y + 1, z)) {
/*  82 */         world.func_72921_c(x, y, z, 1, 2);
/*  83 */         world.func_147465_d(x, y + 1, z, (Block)ModBlocks.invisibleMultiblock, 2, 2);
/*  84 */       } else if (Tags.Blocks.GLASS.is((IBlockAccess)world, x, y - 1, z)) {
/*  85 */         world.func_72921_c(x, y, z, 2, 2);
/*  86 */         world.func_147465_d(x, y - 1, z, (Block)ModBlocks.invisibleMultiblock, 2, 2);
/*     */       } 
/*     */     } else {
/*  89 */       TileEnergyPylon tileEnergyPylon = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TileEnergyPylon) ? (TileEnergyPylon)world.func_147438_o(x, y, z) : null;
/*  90 */       if (tileEnergyPylon == null || (meta == 1 && !isGlass(world, x, y + 1, z)) || (meta == 2 && !isGlass(world, x, y - 1, z))) {
/*  91 */         world.func_72921_c(x, y, z, 0, 2);
/*     */       }
/*     */     } 
/*  94 */     TileEnergyPylon thisTile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TileEnergyPylon) ? (TileEnergyPylon)world.func_147438_o(x, y, z) : null;
/*  95 */     if (thisTile != null) {
/*  96 */       thisTile.onActivated();
/*     */     }
/*  98 */     if (world.func_72805_g(x, y, z) == 0 && world.func_147438_o(x, y, z) != null)
/*  99 */       world.func_147475_p(x, y, z); 
/*     */   }
/*     */   
/*     */   private boolean isGlass(World world, int x, int y, int z) {
/* 103 */     return (world.func_147439_a(x, y, z) == ModBlocks.invisibleMultiblock && world.func_72805_g(x, y, z) == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
/* 108 */     int meta = world.func_72805_g(x, y, z);
/* 109 */     if (meta == 0) return false; 
/* 110 */     TileEnergyPylon thisTile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TileEnergyPylon) ? (TileEnergyPylon)world.func_147438_o(x, y, z) : null;
/* 111 */     if (thisTile != null) {
/* 112 */       if (!player.func_70093_af()) { thisTile.onActivated(); }
/* 113 */       else { thisTile.nextCore(); }
/* 114 */        return true;
/*     */     } 
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149714_e(World world, int x, int y, int z, int p_149714_5_) {
/* 121 */     int meta = world.func_72805_g(x, y, z);
/* 122 */     if (meta == 0) {
/* 123 */       if (Tags.Blocks.GLASS.is((IBlockAccess)world, x, y + 1, z)) {
/* 124 */         world.func_72921_c(x, y, z, 1, 2);
/* 125 */         world.func_147465_d(x, y + 1, z, (Block)ModBlocks.invisibleMultiblock, 2, 2);
/* 126 */       } else if (Tags.Blocks.GLASS.is((IBlockAccess)world, x, y - 1, z)) {
/* 127 */         world.func_72921_c(x, y, z, 2, 2);
/* 128 */         world.func_147465_d(x, y - 1, z, (Block)ModBlocks.invisibleMultiblock, 2, 2);
/*     */       } 
/*     */     } else {
/* 131 */       TileEnergyPylon tileEnergyPylon = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TileEnergyPylon) ? (TileEnergyPylon)world.func_147438_o(x, y, z) : null;
/* 132 */       if (tileEnergyPylon == null || (meta == 1 && !isGlass(world, x, y + 1, z)) || (meta == 2 && !isGlass(world, x, y - 1, z))) {
/* 133 */         world.func_72921_c(x, y, z, 0, 2);
/*     */       }
/*     */     } 
/* 136 */     TileEnergyPylon thisTile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TileEnergyPylon) ? (TileEnergyPylon)world.func_147438_o(x, y, z) : null;
/* 137 */     if (thisTile != null) {
/* 138 */       thisTile.onActivated();
/*     */     }
/* 140 */     if (world.func_72805_g(x, y, z) == 0 && world.func_147438_o(x, y, z) != null) {
/* 141 */       world.func_147475_p(x, y, z);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean func_149740_M() {
/* 146 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149736_g(World world, int x, int y, int z, int meta) {
/* 151 */     TileEnergyPylon tile = (world.func_147438_o(x, y, z) instanceof TileEnergyPylon) ? (TileEnergyPylon)world.func_147438_o(x, y, z) : null;
/* 152 */     if (tile != null) return (int)(tile.getEnergyStored() / tile.getMaxEnergyStored() * 15.0D); 
/* 153 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\multiblock\EnergyPylon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */