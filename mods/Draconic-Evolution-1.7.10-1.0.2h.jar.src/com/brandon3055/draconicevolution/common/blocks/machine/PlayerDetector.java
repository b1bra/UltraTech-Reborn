/*     */ package com.brandon3055.draconicevolution.common.blocks.machine;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.blocks.BlockDE;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TilePlayerDetector;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class PlayerDetector
/*     */   extends BlockDE {
/*     */   IIcon side_inactive;
/*     */   IIcon side_active;
/*     */   IIcon top;
/*     */   IIcon bottom;
/*     */   
/*     */   public PlayerDetector() {
/*  27 */     func_149663_c("playerDetector");
/*  28 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/*  29 */     func_149672_a(field_149769_e);
/*  30 */     ModBlocks.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149651_a(IIconRegister iconRegister) {
/*  35 */     this.side_inactive = iconRegister.func_94245_a("draconicevolution:player_detector_side_inactive");
/*  36 */     this.side_active = iconRegister.func_94245_a("draconicevolution:player_detector_side_active");
/*  37 */     this.top = iconRegister.func_94245_a("draconicevolution:machine_top_0");
/*  38 */     this.bottom = iconRegister.func_94245_a("draconicevolution:machine_side");
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_149673_e(IBlockAccess world, int x, int y, int z, int side) {
/*     */     IIcon side_icon;
/*  44 */     TileEntity tile = world.func_147438_o(x, y, z);
/*  45 */     if (tile instanceof TilePlayerDetector && ((TilePlayerDetector)tile).output)
/*  46 */     { side_icon = this.side_active; }
/*  47 */     else { side_icon = this.side_inactive; }
/*     */     
/*  49 */     if (side == 0) return this.bottom; 
/*  50 */     if (side == 1) return this.top; 
/*  51 */     return side_icon;
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_149691_a(int side, int meta) {
/*  56 */     if (side == 0) return this.bottom; 
/*  57 */     if (side == 1) return this.top; 
/*  58 */     return this.side_active;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149747_d(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_) {
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTileEntity(int meta) {
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity createTileEntity(World world, int metadata) {
/*  78 */     return (TileEntity)new TilePlayerDetector();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
/*  83 */     return (side != 0 && side != 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149744_f() {
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149709_b(IBlockAccess world, int x, int y, int z, int meta) {
/*  93 */     TileEntity te = world.func_147438_o(x, y, z);
/*  94 */     TilePlayerDetector detector = (te instanceof TilePlayerDetector) ? (TilePlayerDetector)te : null;
/*  95 */     if (detector != null) return detector.output ? 15 : 0; 
/*  96 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149748_c(IBlockAccess world, int x, int y, int z, int meta) {
/* 101 */     TileEntity te = world.func_147438_o(x, y, z);
/* 102 */     TilePlayerDetector detector = (te instanceof TilePlayerDetector) ? (TilePlayerDetector)te : null;
/* 103 */     if (detector != null) return detector.output ? 15 : 0; 
/* 104 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
/* 109 */     TileEntity te = world.func_147438_o(x, y, z);
/* 110 */     TilePlayerDetector detector = (te instanceof TilePlayerDetector) ? (TilePlayerDetector)te : null;
/* 111 */     if (detector != null) {
/* 112 */       int range = detector.getRange();
/*     */       
/* 114 */       if (player.func_70093_af()) {
/* 115 */         range--;
/*     */       } else {
/* 117 */         range++;
/*     */       } 
/*     */       
/* 120 */       if (range > 10) range = 1; 
/* 121 */       if (range < 1) range = 10; 
/* 122 */       detector.setRange(range);
/*     */       
/* 124 */       if (world.field_72995_K)
/* 125 */         player.func_145747_a((new ChatComponentTranslation("msg.range.txt", new Object[0])).func_150257_a((IChatComponent)new ChatComponentText(" " + range))); 
/*     */     } 
/* 127 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149749_a(World world, int x, int y, int z, Block block, int meta) {
/* 132 */     super.func_149749_a(world, x, y, z, block, meta);
/*     */     
/* 134 */     world.func_147459_d(x - 1, y, z, world.func_147439_a(x, y, z));
/* 135 */     world.func_147459_d(x + 1, y, z, world.func_147439_a(x, y, z));
/* 136 */     world.func_147459_d(x, y - 1, z, world.func_147439_a(x, y, z));
/* 137 */     world.func_147459_d(x, y + 1, z, world.func_147439_a(x, y, z));
/* 138 */     world.func_147459_d(x, y, z - 1, world.func_147439_a(x, y, z));
/* 139 */     world.func_147459_d(x, y, z + 1, world.func_147439_a(x, y, z));
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\machine\PlayerDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */