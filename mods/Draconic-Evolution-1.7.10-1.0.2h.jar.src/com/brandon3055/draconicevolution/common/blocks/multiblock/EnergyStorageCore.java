/*     */ package com.brandon3055.draconicevolution.common.blocks.multiblock;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.blocks.BlockDE;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.TileEnergyStorageCore;
/*     */ import com.brandon3055.draconicevolution.common.utills.IHudDisplayBlock;
/*     */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnergyStorageCore
/*     */   extends BlockDE
/*     */   implements IHudDisplayBlock
/*     */ {
/*     */   public EnergyStorageCore() {
/*  36 */     super(Material.field_151573_f);
/*  37 */     func_149711_c(10.0F);
/*  38 */     func_149752_b(20.0F);
/*  39 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/*  40 */     func_149663_c("energyStorageCore");
/*  41 */     ModBlocks.register(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister iconRegister) {
/*  48 */     this.field_149761_L = iconRegister.func_94245_a("draconicevolution:energy_storage_core");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  53 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTileEntity(int metadata) {
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity createTileEntity(World world, int metadata) {
/*  68 */     return (TileEntity)new TileEnergyStorageCore();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
/*  73 */     TileEnergyStorageCore tile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TileEnergyStorageCore) ? (TileEnergyStorageCore)world.func_147438_o(x, y, z) : null;
/*  74 */     if (tile == null) {
/*  75 */       LogHelper.error("Missing Tile Entity (EnergyStorageCore)");
/*  76 */       return false;
/*     */     } 
/*     */     
/*  79 */     if (!world.field_72995_K) {
/*  80 */       player.func_146105_b((IChatComponent)new ChatComponentText("Tier:" + (tile.getTier() + 1)));
/*  81 */       String BN = String.valueOf(tile.getEnergyStored());
/*  82 */       player.func_146105_b((IChatComponent)new ChatComponentText(StatCollector.func_74838_a("info.de.charge.txt") + ": " + Utills.formatNumber(tile.getEnergyStored()) + " / " + Utills.formatNumber(tile.getMaxEnergyStored()) + " [" + BN + " EU]"));
/*     */     } 
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_149646_a(IBlockAccess world, int x, int y, int z, int side) {
/*  90 */     TileEnergyStorageCore tile = (world.func_147438_o(x - (ForgeDirection.getOrientation(side)).offsetX, y - (ForgeDirection.getOrientation(side)).offsetY, z - (ForgeDirection.getOrientation(side)).offsetZ) != null && world.func_147438_o(x - (ForgeDirection.getOrientation(side)).offsetX, y - (ForgeDirection.getOrientation(side)).offsetY, z - (ForgeDirection.getOrientation(side)).offsetZ) instanceof TileEnergyStorageCore) ? (TileEnergyStorageCore)world.func_147438_o(x - (ForgeDirection.getOrientation(side)).offsetX, y - (ForgeDirection.getOrientation(side)).offsetY, z - (ForgeDirection.getOrientation(side)).offsetZ) : null;
/*     */     
/*  92 */     if (tile == null) {
/*  93 */       LogHelper.error("Missing Tile Entity (EnergyStorageCore)(shouldSideBeRendered)");
/*  94 */       return true;
/*     */     } 
/*  96 */     if (tile.isOnline()) return false; 
/*  97 */     return super.func_149646_a(world, x, y, z, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int x, int y, int z, Block p_149695_5_) {
/* 102 */     TileEnergyStorageCore thisTile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TileEnergyStorageCore) ? (TileEnergyStorageCore)world.func_147438_o(x, y, z) : null;
/* 103 */     if (thisTile != null && thisTile.isOnline() && thisTile.getTier() == 0) {
/* 104 */       thisTile.isStructureStillValid(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149749_a(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
/* 110 */     TileEnergyStorageCore thisTile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TileEnergyStorageCore) ? (TileEnergyStorageCore)world.func_147438_o(x, y, z) : null;
/* 111 */     if (thisTile != null && thisTile.isOnline() && thisTile.getTier() == 0) {
/* 112 */       thisTile.deactivateStabilizers();
/*     */     }
/* 114 */     super.func_149749_a(world, x, y, z, p_149749_5_, p_149749_6_);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149633_g(World world, int x, int y, int z) {
/* 119 */     TileEnergyStorageCore thisTile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TileEnergyStorageCore) ? (TileEnergyStorageCore)world.func_147438_o(x, y, z) : null;
/* 120 */     if (thisTile != null && thisTile.isOnline()) {
/* 121 */       return AxisAlignedBB.func_72330_a(thisTile.field_145851_c + 0.5D, thisTile.field_145848_d + 0.5D, thisTile.field_145849_e + 0.5D, thisTile.field_145851_c + 0.5D, thisTile.field_145848_d + 0.5D, thisTile.field_145849_e + 0.5D);
/*     */     }
/* 123 */     return super.func_149633_g(world, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getDisplayData(World world, int x, int y, int z) {
/* 128 */     List<String> list = new ArrayList<>();
/*     */     
/* 130 */     TileEnergyStorageCore tile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TileEnergyStorageCore) ? (TileEnergyStorageCore)world.func_147438_o(x, y, z) : null;
/* 131 */     if (tile == null) {
/* 132 */       LogHelper.error("Missing Tile Entity (EnergyStorageCore getDisplayData)");
/* 133 */       return list;
/*     */     } 
/*     */     
/* 136 */     list.add(InfoHelper.HITC() + func_149732_F());
/* 137 */     list.add("Tier: " + InfoHelper.ITC() + (tile.getTier() + 1));
/* 138 */     String BN = String.valueOf(tile.getEnergyStored());
/* 139 */     list.add(StatCollector.func_74838_a("info.de.charge.txt") + ": " + InfoHelper.ITC() + Utills.formatNumber(tile.getEnergyStored()) + " / " + Utills.formatNumber(tile.getMaxEnergyStored()) + " [" + BN + " EU]");
/*     */     
/* 141 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\multiblock\EnergyStorageCore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */