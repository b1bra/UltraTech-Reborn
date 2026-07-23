/*     */ package com.brandon3055.draconicevolution.common.blocks.multiblock;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.blocks.BlockDE;
/*     */ import com.brandon3055.draconicevolution.common.lib.References;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.TileDislocatorReceptacle;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.TilePortalBlock;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.ITileEntityProvider;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Portal
/*     */   extends BlockDE
/*     */   implements ITileEntityProvider
/*     */ {
/*     */   public Portal() {
/*  29 */     super(Material.field_151567_E);
/*  30 */     func_149722_s();
/*  31 */     func_149663_c("portal");
/*     */     
/*  33 */     ModBlocks.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister iconRegister) {
/*  39 */     this.field_149761_L = iconRegister.func_94245_a("draconicevolution:transparency");
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/*  44 */     return References.idPortal;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149633_g(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_) {
/*  49 */     return AxisAlignedBB.func_72330_a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World world, int i) {
/*  64 */     return (TileEntity)new TilePortalBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149668_a(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int x, int y, int z, Block block) {
/*  75 */     if (world.field_72995_K)
/*  76 */       return;  if (getMaster(world, x, y, z) == null) {
/*  77 */       world.func_147468_f(x, y, z);
/*     */       
/*     */       return;
/*     */     } 
/*  81 */     if ((getMaster(world, x, y, z)).isActive) getMaster(world, x, y, z).validateActivePortal(); 
/*  82 */     if (!(getMaster(world, x, y, z)).isActive && !(getMaster(world, x, y, z)).updating) {
/*  83 */       world.func_147468_f(x, y, z);
/*     */       return;
/*     */     } 
/*  86 */     updateMetadata(world, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149670_a(World world, int x, int y, int z, Entity entity) {
/*  91 */     if (world.field_72995_K)
/*  92 */       return;  TileDislocatorReceptacle tile = getMaster(world, x, y, z);
/*  93 */     if (tile != null && tile.isActive && tile.getLocation() != null)
/*  94 */     { if (tile.coolDown > 0)
/*  95 */         return;  tile.coolDown = 1;
/*  96 */       tile.getLocation().sendEntityToCoords(entity); }
/*  97 */     else if (tile != null) { tile.validateActivePortal(); }
/*  98 */     else { world.func_147468_f(x, y, z); }
/*     */   
/*     */   }
/*     */   private TileDislocatorReceptacle getMaster(World world, int x, int y, int z) {
/* 102 */     return (world.func_147438_o(x, y, z) instanceof TilePortalBlock) ? ((TilePortalBlock)world.func_147438_o(x, y, z)).getMaster() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item func_149650_a(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
/* 107 */     return null;
/*     */   }
/*     */   
/*     */   private boolean isPortalOrFrame(IBlockAccess access, int x, int y, int z) {
/* 111 */     Block block = access.func_147439_a(x, y, z);
/* 112 */     return (block == ModBlocks.portal || block == ModBlocks.infusedObsidian || block == ModBlocks.dislocatorReceptacle);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149726_b(World world, int x, int y, int z) {
/* 117 */     updateMetadata(world, x, y, z);
/*     */   }
/*     */   
/*     */   private void updateMetadata(World world, int x, int y, int z) {
/* 121 */     if (world.field_72995_K || world.func_72805_g(x, y, z) != 0)
/* 122 */       return;  int meta = 0;
/*     */     
/* 124 */     if (isPortalOrFrame((IBlockAccess)world, x, y + 1, z) && isPortalOrFrame((IBlockAccess)world, x, y - 1, z) && isPortalOrFrame((IBlockAccess)world, x + 1, y, z) && isPortalOrFrame((IBlockAccess)world, x - 1, y, z)) {
/* 125 */       meta = 1;
/* 126 */     } else if (isPortalOrFrame((IBlockAccess)world, x, y + 1, z) && isPortalOrFrame((IBlockAccess)world, x, y - 1, z) && isPortalOrFrame((IBlockAccess)world, x, y, z + 1) && isPortalOrFrame((IBlockAccess)world, x, y, z - 1)) {
/* 127 */       meta = 2;
/* 128 */     } else if (isPortalOrFrame((IBlockAccess)world, x + 1, y, z) && isPortalOrFrame((IBlockAccess)world, x - 1, y, z) && isPortalOrFrame((IBlockAccess)world, x, y, z + 1) && isPortalOrFrame((IBlockAccess)world, x, y, z - 1)) {
/* 129 */       meta = 3;
/*     */     } 
/* 131 */     world.func_72921_c(x, y, z, meta, 2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\multiblock\Portal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */