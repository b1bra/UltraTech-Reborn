/*     */ package com.brandon3055.draconicevolution.common.blocks;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.Teleporter;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.TeleporterMKI;
/*     */ import com.brandon3055.draconicevolution.common.lib.References;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileTeleporterStand;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TeleporterStand
/*     */   extends BlockCustomDrop
/*     */ {
/*     */   public TeleporterStand() {
/*  31 */     super(Material.field_151576_e);
/*  32 */     func_149663_c("teleporterStand");
/*  33 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/*  34 */     func_149672_a(field_149769_e);
/*  35 */     func_149711_c(1.5F);
/*  36 */     func_149752_b(10.0F);
/*  37 */     func_149676_a(0.35F, 0.0F, 0.35F, 0.65F, 0.8F, 0.65F);
/*  38 */     ModBlocks.register(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister iconRegister) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World world, int meta) {
/*  49 */     return (TileEntity)new TileTeleporterStand();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float prx, float pry, float prz) {
/*  54 */     TileTeleporterStand tile = (world.func_147438_o(x, y, z) instanceof TileTeleporterStand) ? (TileTeleporterStand)world.func_147438_o(x, y, z) : null;
/*  55 */     if (tile == null) return false; 
/*  56 */     if (tile.func_70301_a(0) == null && player.func_70694_bm() != null && player.func_70694_bm().func_77973_b() instanceof TeleporterMKI) {
/*  57 */       ItemStack stack = player.func_70694_bm();
/*  58 */       tile.func_70299_a(0, stack.func_77946_l());
/*  59 */       player.field_71071_by.func_70299_a(player.field_71071_by.field_70461_c, null);
/*  60 */       world.func_147471_g(x, y, z);
/*  61 */       return true;
/*     */     } 
/*     */     
/*  64 */     if (tile.func_70301_a(0) != null && player.func_70093_af()) {
/*  65 */       EntityItem item = new EntityItem(world, x + 0.5D, y + 0.9D, z + 0.5D, tile.func_70301_a(0).func_77946_l());
/*  66 */       item.field_70159_w = 0.0D;
/*  67 */       item.field_70181_x = 0.0D;
/*  68 */       item.field_70179_y = 0.0D;
/*  69 */       item.field_145804_b = 0;
/*  70 */       tile.func_70299_a(0, null);
/*  71 */       if (!world.field_72995_K) world.func_72838_d((Entity)item); 
/*  72 */       return true;
/*     */     } 
/*     */     
/*  75 */     if (tile.func_70301_a(0) != null && !player.func_70093_af() && tile.func_70301_a(0).func_77973_b() instanceof TeleporterMKI) {
/*  76 */       Teleporter.TeleportLocation l = ((TeleporterMKI)tile.func_70301_a(0).func_77973_b()).getLocation(tile.func_70301_a(0));
/*  77 */       if (l != null) l.sendEntityToCoords((Entity)player); 
/*  78 */       return true;
/*     */     } 
/*     */     
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_149645_b() {
/*  87 */     return References.idTeleporterStand;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149691_a(int par1, int par2) {
/*  93 */     return Block.func_149684_b("stone").func_149691_a(par1, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean dropInventory() {
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasCustomDropps() {
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void getCustomTileEntityDrops(TileEntity te, List<ItemStack> droppes) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149689_a(World world, int x, int y, int z, EntityLivingBase entity, ItemStack p_149689_6_) {
/* 123 */     super.func_149689_a(world, x, y, z, entity, p_149689_6_);
/* 124 */     TileTeleporterStand tile = (world.func_147438_o(x, y, z) instanceof TileTeleporterStand) ? (TileTeleporterStand)world.func_147438_o(x, y, z) : null;
/* 125 */     if (tile == null)
/* 126 */       return;  tile.rotation = (int)entity.field_70759_as;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
/* 131 */     return super.getPickBlock(target, world, x, y, z, player);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\TeleporterStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */