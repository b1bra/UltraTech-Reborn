/*     */ package com.brandon3055.draconicevolution.common.blocks.multiblock;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.blocks.BlockCustomDrop;
/*     */ import com.brandon3055.draconicevolution.common.blocks.BlockDE;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.TeleporterMKI;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.TileDislocatorReceptacle;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.ITileEntityProvider;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class DislocatorReceptacle
/*     */   extends BlockCustomDrop
/*     */   implements ITileEntityProvider
/*     */ {
/*     */   IIcon textureInactive;
/*     */   
/*     */   public DislocatorReceptacle() {
/*  31 */     super(Material.field_151576_e);
/*  32 */     func_149711_c(50.0F);
/*  33 */     func_149752_b(2000.0F);
/*  34 */     func_149663_c("dislocatorReceptacle");
/*  35 */     setHarvestLevel("pickaxe", 3);
/*  36 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/*     */     
/*  38 */     ModBlocks.register((BlockDE)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149651_a(IIconRegister iconRegister) {
/*  43 */     this.textureInactive = iconRegister.func_94245_a("draconicevolution:animated/dislocatorReceptacle_inactive");
/*  44 */     this.field_149761_L = iconRegister.func_94245_a("draconicevolution:animated/dislocatorReceptacle_active");
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_149673_e(IBlockAccess access, int x, int y, int z, int side) {
/*  49 */     TileDislocatorReceptacle tile = (access.func_147438_o(x, y, z) instanceof TileDislocatorReceptacle) ? (TileDislocatorReceptacle)access.func_147438_o(x, y, z) : null;
/*  50 */     if (tile != null) return tile.isActive ? this.field_149761_L : this.textureInactive; 
/*  51 */     return this.field_149761_L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149674_a(World world, int x, int y, int z, Random random) {
/*  56 */     TileDislocatorReceptacle tile = (TileDislocatorReceptacle)world.func_147438_o(x, y, z);
/*  57 */     if (tile != null) tile.updateState();
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_149691_a(int p_149691_1_, int meta) {
/*  63 */     return this.field_149761_L;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTileEntity(int metadata) {
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World world, int i) {
/*  78 */     return (TileEntity)new TileDislocatorReceptacle();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
/*  83 */     if (world.field_72995_K) return true; 
/*  84 */     TileDislocatorReceptacle tile = (TileDislocatorReceptacle)world.func_147438_o(x, y, z);
/*  85 */     if (tile == null) return false;
/*     */     
/*  87 */     if (tile.func_70301_a(0) != null) {
/*  88 */       if (player.func_70694_bm() == null) {
/*  89 */         player.field_71071_by.func_70299_a(player.field_71071_by.field_70461_c, tile.func_70301_a(0));
/*  90 */         tile.func_70299_a(0, null);
/*     */       } else {
/*  92 */         world.func_72838_d((Entity)new EntityItem(world, player.field_70165_t, player.field_70163_u, player.field_70161_v, tile.func_70301_a(0)));
/*  93 */         tile.func_70299_a(0, null);
/*     */       } 
/*  95 */       world.func_147471_g(x, y, z);
/*  96 */       world.func_147444_c(x, y, z, (Block)this);
/*     */     } else {
/*     */       
/*  99 */       ItemStack stack = player.func_70694_bm();
/* 100 */       if (stack != null && stack.func_77973_b() instanceof TeleporterMKI && ((TeleporterMKI)stack.func_77973_b()).getLocation(stack) != null) {
/* 101 */         tile.func_70299_a(0, player.func_70694_bm());
/* 102 */         player.func_71028_bD();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149740_M() {
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149736_g(World world, int x, int y, int z, int p_149736_5_) {
/* 118 */     TileDislocatorReceptacle tile = (TileDislocatorReceptacle)world.func_147438_o(x, y, z);
/* 119 */     return (tile == null) ? 0 : ((tile.func_70301_a(0) != null) ? 15 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean dropInventory() {
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasCustomDropps() {
/* 129 */     return false;
/*     */   }
/*     */   
/*     */   protected void getCustomTileEntityDrops(TileEntity te, List<ItemStack> droppes) {}
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\multiblock\DislocatorReceptacle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */