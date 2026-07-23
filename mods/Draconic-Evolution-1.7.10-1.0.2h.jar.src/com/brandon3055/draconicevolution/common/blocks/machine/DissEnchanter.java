/*     */ package com.brandon3055.draconicevolution.common.blocks.machine;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.blocks.BlockCustomDrop;
/*     */ import com.brandon3055.draconicevolution.common.blocks.BlockDE;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileDissEnchanter;
/*     */ import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.loliland.mctags.api.Tags;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DissEnchanter
/*     */   extends BlockCustomDrop
/*     */ {
/*     */   IIcon top;
/*     */   IIcon bottom;
/*     */   
/*     */   public DissEnchanter() {
/*  33 */     super(Material.field_151573_f);
/*  34 */     func_149663_c("dissEnchanter");
/*  35 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/*  36 */     func_149672_a(field_149769_e);
/*  37 */     func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
/*  38 */     ModBlocks.register((BlockDE)this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister iconRegister) {
/*  44 */     this.field_149761_L = iconRegister.func_94245_a("draconicevolution:dissEnchanter_side");
/*  45 */     this.top = iconRegister.func_94245_a("draconicevolution:dissEnchanter_top");
/*  46 */     this.bottom = iconRegister.func_94245_a("draconicevolution:dissEnchanter_bottom");
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149691_a(int side, int meta) {
/*  52 */     return (side == 0) ? this.bottom : ((side == 1) ? this.top : this.field_149761_L);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World world, int meta) {
/*  57 */     return (TileEntity)new TileDissEnchanter();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float prx, float pry, float prz) {
/*  62 */     if (!world.field_72995_K) {
/*  63 */       FMLNetworkHandler.openGui(player, DraconicEvolution.instance, 10, world, x, y, z);
/*     */     }
/*  65 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_149645_b() {
/*  71 */     return super.func_149645_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean dropInventory() {
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasCustomDropps() {
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void getCustomTileEntityDrops(TileEntity te, List<ItemStack> droppes) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149734_b(World world, int x, int y, int z, Random rand) {
/* 102 */     super.func_149734_b(world, x, y, z, rand);
/*     */     
/* 104 */     for (int x1 = x - 2; x1 <= x + 2; x1++) {
/* 105 */       for (int z1 = z - 2; z1 <= z + 2; z1++) {
/* 106 */         if (x1 > x - 2 && x1 < x + 2 && z1 == z - 1) {
/* 107 */           z1 = z + 2;
/*     */         }
/*     */         
/* 110 */         if (rand.nextInt(16) == 0)
/* 111 */           for (int y1 = y; y1 <= y + 1; y1++) {
/* 112 */             if (Tags.Blocks.BOOKSHELF.is((IBlockAccess)world, x1, y1, z1)) {
/* 113 */               if (!world.func_147437_c((x1 - x) / 2 + x, y1, (z1 - z) / 2 + z)) {
/*     */                 break;
/*     */               }
/*     */ 
/*     */               
/* 118 */               world.func_72869_a("enchantmenttable", x1 + 0.4D + rand.nextFloat() * 0.2D, y1 + 0.8D, z1 + 0.4D + rand.nextFloat() * 0.2D, ((x - x1) + rand.nextFloat()) - 0.5D, ((y - y1) + rand.nextFloat()), ((z - z1) + rand.nextFloat()) - 0.5D);
/*     */             } 
/*     */           }  
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\machine\DissEnchanter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */