/*     */ package com.brandon3055.draconicevolution.common.blocks.machine;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.blocks.BlockCustomDrop;
/*     */ import com.brandon3055.draconicevolution.common.blocks.BlockDE;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileGrinder;
/*     */ import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class Grinder
/*     */   extends BlockCustomDrop
/*     */ {
/*     */   public IIcon icon_front;
/*     */   public IIcon icon_side;
/*     */   public IIcon icon_back;
/*     */   public IIcon icon_back_inactive;
/*     */   public IIcon icon_front_inactive;
/*  36 */   public IIcon[] icon_top = new IIcon[4];
/*     */   
/*     */   public Grinder() {
/*  39 */     super(Material.field_151573_f);
/*  40 */     func_149663_c("grinder");
/*  41 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/*  42 */     func_149672_a(field_149769_e);
/*  43 */     func_149752_b(2000.0F);
/*  44 */     ModBlocks.register((BlockDE)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister iconRegister) {
/*  51 */     this.icon_front = iconRegister.func_94245_a("draconicevolution:animated/grinder_front_active");
/*  52 */     this.icon_front_inactive = iconRegister.func_94245_a("draconicevolution:grinder_front");
/*  53 */     this.icon_side = iconRegister.func_94245_a("draconicevolution:machine_side");
/*  54 */     this.icon_back = iconRegister.func_94245_a("draconicevolution:animated/machine_fan");
/*  55 */     this.icon_back_inactive = iconRegister.func_94245_a("draconicevolution:machine_fan");
/*  56 */     for (int i = 0; i < 4; i++) {
/*  57 */       this.icon_top[i] = iconRegister.func_94245_a("draconicevolution:machine_top_" + i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149666_a(Item item, CreativeTabs tab, List<ItemStack> par3list) {
/*  70 */     par3list.add(new ItemStack(item, 1, 3));
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149692_a(int p_149692_1_) {
/*  75 */     return 3;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149673_e(IBlockAccess world, int x, int y, int z, int side) {
/*     */     IIcon back, front;
/*  81 */     TileGrinder tile = (TileGrinder)world.func_147438_o(x, y, z);
/*  82 */     int meta = world.func_72805_g(x, y, z);
/*     */ 
/*     */     
/*  85 */     if (meta > 3) {
/*  86 */       meta = 3;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     if (!tile.disabled && tile.hasPower) {
/*  93 */       back = this.icon_back;
/*  94 */       front = this.icon_front;
/*     */     } else {
/*  96 */       back = this.icon_back_inactive;
/*  97 */       front = this.icon_front_inactive;
/*     */     } 
/*     */     
/* 100 */     switch (side) {
/*     */       case 0:
/* 102 */         return this.icon_side;
/*     */       case 1:
/* 104 */         return this.icon_top[meta];
/*     */       case 2:
/* 106 */         if (meta == 0)
/* 107 */           return front; 
/* 108 */         if (meta == 2)
/* 109 */           return back; 
/* 110 */         return this.icon_side;
/*     */       case 3:
/* 112 */         if (meta == 2)
/* 113 */           return front; 
/* 114 */         if (meta == 0)
/* 115 */           return back; 
/* 116 */         return this.icon_side;
/*     */       case 4:
/* 118 */         if (meta == 3)
/* 119 */           return front; 
/* 120 */         if (meta == 1)
/* 121 */           return back; 
/* 122 */         return this.icon_side;
/*     */       case 5:
/* 124 */         if (meta == 1)
/* 125 */           return front; 
/* 126 */         if (meta == 3)
/* 127 */           return back; 
/* 128 */         return this.icon_side;
/*     */     } 
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149691_a(int side, int meta) {
/* 137 */     if (meta > 3) {
/* 138 */       meta = 3;
/*     */     }
/*     */     
/* 141 */     switch (side) {
/*     */       case 0:
/* 143 */         return this.icon_side;
/*     */       case 1:
/* 145 */         return this.icon_top[meta];
/*     */       case 2:
/* 147 */         if (meta == 0)
/* 148 */           return this.icon_front; 
/* 149 */         if (meta == 2)
/* 150 */           return this.icon_back; 
/* 151 */         return this.icon_side;
/*     */       case 3:
/* 153 */         if (meta == 2)
/* 154 */           return this.icon_front; 
/* 155 */         if (meta == 0)
/* 156 */           return this.icon_back; 
/* 157 */         return this.icon_side;
/*     */       case 4:
/* 159 */         if (meta == 3)
/* 160 */           return this.icon_front; 
/* 161 */         if (meta == 1)
/* 162 */           return this.icon_back; 
/* 163 */         return this.icon_side;
/*     */       case 5:
/* 165 */         if (meta == 1)
/* 166 */           return this.icon_front; 
/* 167 */         if (meta == 3)
/* 168 */           return this.icon_back; 
/* 169 */         return this.icon_side;
/*     */     } 
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World var1, int var2) {
/* 176 */     return (TileEntity)new TileGrinder();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int X, int Y, int Z, EntityPlayer player, int side, float prx, float pry, float prz) {
/* 181 */     TileEntity tile = world.func_147438_o(X, Y, Z);
/* 182 */     if (!(tile instanceof TileGrinder))
/* 183 */       return false; 
/* 184 */     if (!world.field_72995_K && !player.func_70093_af()) {
/* 185 */       FMLNetworkHandler.openGui(player, DraconicEvolution.instance, 2, world, X, Y, Z);
/* 186 */     } else if (player.func_70093_af()) {
/* 187 */       double x; for (x = ((TileGrinder)tile).centreX - 4.0D; x <= ((TileGrinder)tile).centreX + 4.0D; x++) {
/* 188 */         double y; for (y = ((TileGrinder)tile).centreY - 4.0D; y <= ((TileGrinder)tile).centreY + 4.0D; y++) {
/* 189 */           double z; for (z = ((TileGrinder)tile).centreZ - 4.0D; z <= ((TileGrinder)tile).centreZ + 4.0D; z++)
/* 190 */             world.func_72869_a("flame", x, y, z, 0.0D, 0.0D, 0.0D); 
/*     */         } 
/*     */       } 
/*     */     } 
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149689_a(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
/* 199 */     int l = MathHelper.func_76128_c((placer.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3;
/* 200 */     if (l == 0) {
/* 201 */       world.func_72921_c(x, y, z, 0, 2);
/*     */     }
/* 203 */     if (l == 1) {
/* 204 */       world.func_72921_c(x, y, z, 1, 2);
/*     */     }
/* 206 */     if (l == 2) {
/* 207 */       world.func_72921_c(x, y, z, 2, 2);
/*     */     }
/* 209 */     if (l == 3) {
/* 210 */       world.func_72921_c(x, y, z, 3, 2);
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_149695_a(World world, int x, int y, int z, Block block) {
/* 215 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 216 */     if (tile instanceof TileGrinder) {
/* 217 */       ((TileGrinder)tile).disabled = world.func_72864_z(x, y, z);
/* 218 */       world.func_147471_g(x, y, z);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean dropInventory() {
/* 224 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasCustomDropps() {
/* 229 */     return false;
/*     */   }
/*     */   
/*     */   protected void getCustomTileEntityDrops(TileEntity te, List<ItemStack> droppes) {}
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\machine\Grinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */