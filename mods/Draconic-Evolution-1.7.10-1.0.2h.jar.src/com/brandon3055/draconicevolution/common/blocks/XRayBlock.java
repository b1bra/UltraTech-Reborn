/*     */ package com.brandon3055.draconicevolution.common.blocks;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.client.handler.ParticleHandler;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class XRayBlock
/*     */   extends BlockDE
/*     */ {
/*     */   public XRayBlock() {
/*  24 */     func_149663_c("xRayBlock");
/*  25 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/*  26 */     func_149676_a(0.4F, 0.4F, 0.4F, 0.6F, 0.6F, 0.6F);
/*  27 */     func_149711_c(10.0F);
/*  28 */     setHarvestLevel("pickaxe", 4);
/*  29 */     ModBlocks.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/*  34 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
/*  39 */     return super.isReplaceable(world, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/*  44 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149668_a(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
/*  49 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149734_b(World world, int x, int y, int z, Random rand) {
/*  55 */     double d0 = (x + 0.5F);
/*  56 */     double d1 = (y + 0.48F);
/*  57 */     double d2 = (z + 0.5F);
/*     */     
/*  59 */     float mX1 = (rand.nextFloat() - 0.5F) * 0.005F;
/*  60 */     float mY1 = rand.nextFloat() * 0.01F;
/*  61 */     float mZ1 = (rand.nextFloat() - 0.5F) * 0.005F;
/*  62 */     ParticleHandler.spawnParticle("distortionParticle", d0, d1, d2, mX1, mY1, mZ1, 1.0F);
/*  63 */     for (int i = 0; i < 3; i++) {
/*  64 */       float mX = (rand.nextFloat() - 0.5F) * 0.005F;
/*  65 */       float mY = 0.01F + rand.nextFloat() * 0.005F;
/*  66 */       float mZ = (rand.nextFloat() - 0.5F) * 0.005F;
/*  67 */       float scale = 0.2F + rand.nextFloat() * 0.2F;
/*  68 */       ParticleHandler.spawnParticle("distortionParticle", d0, d1, d2, mX, mY, mZ, scale);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149745_a(Random p_149745_1_) {
/*  74 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149699_a(World world, int x, int y, int z, EntityPlayer player) {
/*  79 */     ItemStack tool = player.func_71045_bC();
/*  80 */     if (tool != null && (player.func_71045_bC().func_77969_a(new ItemStack(ModItems.draconicDestructionStaff)) || player.func_71045_bC().func_77969_a(new ItemStack(ModItems.draconicPickaxe)))) {
/*  81 */       EntityItem item = new EntityItem(world, player.field_70165_t, player.field_70163_u, player.field_70161_v, new ItemStack(Item.func_150898_a(ModBlocks.xRayBlock)));
/*  82 */       world.func_147468_f(x, y, z);
/*  83 */       if (!world.field_72995_K) world.func_72838_d((Entity)item); 
/*     */     } 
/*  85 */     super.func_149699_a(world, x, y, z, player);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_149700_E() {
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149670_a(World world, int x, int y, int z, Entity entity) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\XRayBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */