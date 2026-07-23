/*     */ package com.brandon3055.draconicevolution.common.blocks;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TilePlacedItem;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlacedItem
/*     */   extends BlockDE
/*     */ {
/*     */   public PlacedItem() {
/*  27 */     super(Material.field_151594_q);
/*  28 */     func_149711_c(5.0F);
/*  29 */     func_149752_b(20.0F);
/*  30 */     func_149663_c("placedItem");
/*  31 */     ModBlocks.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149651_a(IIconRegister iconRegister) {
/*  36 */     this.field_149761_L = iconRegister.func_94245_a("glass");
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149719_a(IBlockAccess world, int x, int y, int z) {
/*  41 */     switch (world.func_72805_g(x, y, z)) {
/*     */       case 0:
/*  43 */         func_149676_a(0.0F, 0.8F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         return;
/*     */       case 1:
/*  46 */         func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 1.0F);
/*     */         return;
/*     */       case 2:
/*  49 */         func_149676_a(0.0F, 0.0F, 0.8F, 1.0F, 1.0F, 1.0F);
/*     */         return;
/*     */       case 3:
/*  52 */         func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.2F);
/*     */         return;
/*     */       case 4:
/*  55 */         func_149676_a(0.8F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         return;
/*     */       case 5:
/*  58 */         func_149676_a(0.0F, 0.0F, 0.0F, 0.2F, 1.0F, 1.0F);
/*     */         return;
/*     */     } 
/*  61 */     super.func_149719_a(world, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149668_a(World world, int x, int y, int z) {
/*  77 */     TilePlacedItem tile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TilePlacedItem) ? (TilePlacedItem)world.func_147438_o(x, y, z) : null;
/*  78 */     int meta = world.func_72805_g(x, y, z);
/*  79 */     if (tile != null && tile.getStack() != null) {
/*  80 */       if (tile.getStack().func_77973_b() instanceof net.minecraft.item.ItemBlock) {
/*  81 */         switch (meta) {
/*     */           case 0:
/*  83 */             return AxisAlignedBB.func_72330_a(x + 0.25D, y + 0.5D, z + 0.25D, x + 0.75D, y + 1.0D, z + 0.75D);
/*     */           case 1:
/*  85 */             return AxisAlignedBB.func_72330_a(x + 0.25D, y + 0.0D, z + 0.25D, x + 0.75D, y + 0.5D, z + 0.75D);
/*     */           case 2:
/*  87 */             return AxisAlignedBB.func_72330_a(x + 0.25D, y + 0.25D, z + 0.5D, x + 0.75D, y + 0.75D, z + 1.0D);
/*     */           case 3:
/*  89 */             return AxisAlignedBB.func_72330_a(x + 0.25D, y + 0.25D, z + 0.0D, x + 0.75D, y + 0.75D, z + 0.5D);
/*     */           case 4:
/*  91 */             return AxisAlignedBB.func_72330_a(x + 0.5D, y + 0.25D, z + 0.25D, x + 1.0D, y + 0.75D, z + 0.75D);
/*     */           case 5:
/*  93 */             return AxisAlignedBB.func_72330_a(x + 0.0D, y + 0.25D, z + 0.25D, x + 0.5D, y + 0.75D, z + 0.75D);
/*     */         } 
/*     */       } else {
/*  96 */         switch (meta) {
/*     */           case 0:
/*  98 */             return AxisAlignedBB.func_72330_a(x + 0.25D, y + 0.9D, z + 0.25D, x + 0.75D, y + 1.0D, z + 0.75D);
/*     */           case 1:
/* 100 */             return AxisAlignedBB.func_72330_a(x + 0.25D, y + 0.0D, z + 0.25D, x + 0.75D, y + 0.1D, z + 0.75D);
/*     */           case 2:
/* 102 */             return AxisAlignedBB.func_72330_a(x + 0.25D, y + 0.25D, z + 0.9D, x + 0.75D, y + 0.75D, z + 1.0D);
/*     */           case 3:
/* 104 */             return AxisAlignedBB.func_72330_a(x + 0.25D, y + 0.25D, z + 0.0D, x + 0.75D, y + 0.75D, z + 0.1D);
/*     */           case 4:
/* 106 */             return AxisAlignedBB.func_72330_a(x + 0.9D, y + 0.25D, z + 0.25D, x + 1.0D, y + 0.75D, z + 0.75D);
/*     */           case 5:
/* 108 */             return AxisAlignedBB.func_72330_a(x + 0.0D, y + 0.25D, z + 0.25D, x + 0.1D, y + 0.75D, z + 0.75D);
/*     */         } 
/*     */       } 
/*     */     }
/* 112 */     return super.func_149668_a(world, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/* 117 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTileEntity(int metadata) {
/* 122 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity createTileEntity(World world, int metadata) {
/* 127 */     return (TileEntity)new TilePlacedItem();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149749_a(World world, int x, int y, int z, Block block, int meta) {
/* 132 */     TileEntity te = world.func_147438_o(x, y, z);
/* 133 */     if (te instanceof TilePlacedItem && ((TilePlacedItem)te).getStack() != null) {
/* 134 */       TilePlacedItem tile = (TilePlacedItem)te;
/*     */       
/* 136 */       float spawnX = x + world.field_73012_v.nextFloat();
/* 137 */       float spawnY = y + world.field_73012_v.nextFloat();
/* 138 */       float spawnZ = z + world.field_73012_v.nextFloat();
/*     */       
/* 140 */       EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, tile.getStack());
/* 141 */       tile.setStack(null);
/*     */       
/* 143 */       float multiplier = 0.05F;
/*     */       
/* 145 */       droppedItem.field_70159_w = ((-0.5F + world.field_73012_v.nextFloat()) * multiplier);
/* 146 */       droppedItem.field_70181_x = ((4.0F + world.field_73012_v.nextFloat()) * multiplier);
/* 147 */       droppedItem.field_70179_y = ((-0.5F + world.field_73012_v.nextFloat()) * multiplier);
/*     */       
/* 149 */       world.func_72838_d((Entity)droppedItem);
/*     */     } 
/* 151 */     super.func_149749_a(world, x, y, z, block, meta);
/*     */   }
/*     */ 
/*     */   
/*     */   public Item func_149650_a(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
/* 156 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
/* 161 */     if (player.func_70093_af()) {
/* 162 */       TilePlacedItem tile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TilePlacedItem) ? (TilePlacedItem)world.func_147438_o(x, y, z) : null;
/* 163 */       if (tile == null) {
/* 164 */         world.func_147468_f(x, y, z);
/*     */       }
/* 166 */       tile.rotation += 5.625F;
/*     */     } else {
/* 168 */       if (!world.field_72995_K) func_149749_a(world, x, y, z, this, world.func_72805_g(x, y, z)); 
/* 169 */       world.func_147468_f(x, y, z);
/*     */     } 
/* 171 */     world.func_147471_g(x, y, z);
/* 172 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149699_a(World world, int x, int y, int z, EntityPlayer player) {
/* 177 */     if (player.func_70093_af()) {
/* 178 */       TilePlacedItem tile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TilePlacedItem) ? (TilePlacedItem)world.func_147438_o(x, y, z) : null;
/* 179 */       if (tile == null) {
/* 180 */         world.func_147468_f(x, y, z);
/*     */       }
/* 182 */       tile.rotation += 22.5F;
/*     */     } else {
/* 184 */       TilePlacedItem tile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TilePlacedItem) ? (TilePlacedItem)world.func_147438_o(x, y, z) : null;
/* 185 */       if (tile == null) {
/* 186 */         world.func_147468_f(x, y, z);
/*     */       }
/* 188 */       tile.rotation -= 22.5F;
/*     */     } 
/* 190 */     world.func_147471_g(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLightValue(IBlockAccess world, int x, int y, int z) {
/* 195 */     TileEntity te = world.func_147438_o(x, y, z);
/* 196 */     if (te instanceof TilePlacedItem && ((TilePlacedItem)te).getStack() != null) {
/* 197 */       TilePlacedItem tile = (TilePlacedItem)te;
/* 198 */       if (tile.getStack() != null && tile.getStack().func_77973_b() instanceof net.minecraft.item.ItemBlock)
/* 199 */         return Block.func_149634_a(tile.getStack().func_77973_b()).func_149750_m(); 
/*     */     } 
/* 201 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
/* 206 */     TileEntity te = world.func_147438_o(x, y, z);
/* 207 */     if (te instanceof TilePlacedItem && ((TilePlacedItem)te).getStack() != null) {
/* 208 */       TilePlacedItem tile = (TilePlacedItem)te;
/* 209 */       return tile.getStack();
/*     */     } 
/* 211 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\PlacedItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */