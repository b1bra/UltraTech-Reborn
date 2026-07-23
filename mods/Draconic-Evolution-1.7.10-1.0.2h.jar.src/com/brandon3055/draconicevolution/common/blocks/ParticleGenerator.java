/*     */ package com.brandon3055.draconicevolution.common.blocks;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.client.handler.ParticleHandler;
/*     */ import com.brandon3055.draconicevolution.client.render.particle.ParticleCustom;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileParticleGenerator;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.TileEnergyStorageCore;
/*     */ import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.Random;
/*     */ import com.brandon3055.brandonscore.common.tags.Tags;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ParticleGenerator
/*     */   extends BlockDE
/*     */ {
/*     */   public static Block instance;
/*     */   
/*     */   public ParticleGenerator() {
/*  31 */     func_149663_c("particleGenerator");
/*  32 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/*  33 */     func_149672_a(field_149769_e);
/*  34 */     func_149713_g(0);
/*  35 */     ModBlocks.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister iconRegister) {
/*  41 */     this.field_149761_L = iconRegister.func_94245_a("draconicevolution:machine_side");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
/*  46 */     if (world.func_72805_g(x, y, z) == 1) return false;
/*     */     
/*  48 */     if (player.func_70694_bm() != null && Tags.Items.PAPER.is(player.func_70694_bm())) {
/*  49 */       TileEntity tile = world.func_147438_o(x, y, z);
/*  50 */       TileParticleGenerator gen = (tile instanceof TileParticleGenerator) ? (TileParticleGenerator)tile : null;
/*  51 */       ItemStack stack = player.func_70694_bm();
/*  52 */       if (gen != null && stack.func_77942_o() && stack.func_77978_p().func_74764_b("particles_enabled")) {
/*  53 */         gen.setBlockNBT(stack.func_77978_p());
/*  54 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     if (player.func_70093_af()) {
/*  59 */       if (activateEnergyStorageCore(world, x, y, z, player)) return true; 
/*  60 */       TileEntity tile = world.func_147438_o(x, y, z);
/*  61 */       TileParticleGenerator gen = (tile instanceof TileParticleGenerator) ? (TileParticleGenerator)tile : null;
/*  62 */       if (gen != null) {
/*  63 */         gen.toggleInverted();
/*     */       }
/*     */     } else {
/*  66 */       FMLNetworkHandler.openGui(player, DraconicEvolution.instance, 5, world, x, y, z);
/*  67 */     }  return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_149645_b() {
/*  73 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int x, int y, int z, Block block) {
/*  78 */     TileEntity tile = world.func_147438_o(x, y, z);
/*  79 */     TileParticleGenerator gen = (tile instanceof TileParticleGenerator) ? (TileParticleGenerator)tile : null;
/*  80 */     if (gen != null) {
/*  81 */       gen.signal = world.func_72864_z(x, y, z);
/*  82 */       world.func_147471_g(x, y, z);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149664_b(World world, int x, int y, int z, int meta) {
/*  94 */     if (world.field_72995_K) {
/*  95 */       Random rand = world.field_73012_v;
/*  96 */       float modifier = 0.1F;
/*  97 */       float SCALE = 1.0F;
/*  98 */       double spawnX = x + 0.5D;
/*  99 */       double spawnY = y + 0.5D;
/* 100 */       double spawnZ = z + 0.5D;
/*     */       
/* 102 */       for (int i = 0; i < 100; i++) {
/* 103 */         float MX = modifier - 2.0F * modifier * rand.nextFloat();
/* 104 */         float MY = modifier - 2.0F * modifier * rand.nextFloat();
/* 105 */         float MZ = modifier - 2.0F * modifier * rand.nextFloat();
/*     */         
/* 107 */         ParticleCustom particle = new ParticleCustom(world, spawnX, spawnY, spawnZ, MX, MY, MZ, SCALE, false, 1);
/* 108 */         particle.red = rand.nextInt(255);
/* 109 */         particle.green = rand.nextInt(255);
/* 110 */         particle.blue = rand.nextInt(255);
/* 111 */         particle.maxAge = rand.nextInt(10);
/* 112 */         particle.fadeTime = 20;
/* 113 */         particle.fadeLength = 20;
/* 114 */         particle.gravity = 0.0F;
/*     */         
/* 116 */         ParticleHandler.spawnCustomParticle((EntityFX)particle);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149646_a(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
/* 128 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTileEntity(int meta) {
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity createTileEntity(World world, int metadata) {
/* 143 */     return (TileEntity)new TileParticleGenerator();
/*     */   }
/*     */   
/*     */   private boolean activateEnergyStorageCore(World world, int x, int y, int z, EntityPlayer player) {
/* 147 */     for (int x1 = x - 11; x1 <= x + 11; x1++) {
/* 148 */       if (world.func_147439_a(x1, y, z) == ModBlocks.energyStorageCore) {
/* 149 */         TileEnergyStorageCore tile = (world.func_147438_o(x1, y, z) != null && world.func_147438_o(x1, y, z) instanceof TileEnergyStorageCore) ? (TileEnergyStorageCore)world.func_147438_o(x1, y, z) : null;
/* 150 */         if (tile != null && !tile.isOnline()) {
/* 151 */           if (player.field_71075_bZ.field_75098_d) {
/* 152 */             if (!tile.creativeActivate()) {
/* 153 */               if (world.field_72995_K)
/* 154 */                 player.func_146105_b((IChatComponent)new ChatComponentTranslation("msg.energyStorageCoreUTA.txt", new Object[0])); 
/* 155 */               return false;
/*     */             }
/*     */           
/* 158 */           } else if (!tile.tryActivate()) {
/* 159 */             if (world.field_72995_K)
/* 160 */               player.func_146105_b((IChatComponent)new ChatComponentTranslation("msg.energyStorageCoreUTA.txt", new Object[0])); 
/* 161 */             return false;
/*     */           } 
/*     */           
/* 164 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     for (int z1 = z - 11; z1 <= z + 11; z1++) {
/* 170 */       if (world.func_147439_a(x, y, z1) == ModBlocks.energyStorageCore) {
/* 171 */         TileEnergyStorageCore tile = (world.func_147438_o(x, y, z1) != null && world.func_147438_o(x, y, z1) instanceof TileEnergyStorageCore) ? (TileEnergyStorageCore)world.func_147438_o(x, y, z1) : null;
/* 172 */         if (tile != null && !tile.isOnline()) {
/* 173 */           if (player.field_71075_bZ.field_75098_d) {
/* 174 */             if (!tile.creativeActivate()) {
/* 175 */               if (world.field_72995_K)
/* 176 */                 player.func_146105_b((IChatComponent)new ChatComponentTranslation("msg.energyStorageCoreUTA.txt", new Object[0])); 
/* 177 */               return false;
/*     */             }
/*     */           
/* 180 */           } else if (!tile.tryActivate()) {
/* 181 */             if (world.field_72995_K)
/* 182 */               player.func_146105_b((IChatComponent)new ChatComponentTranslation("msg.energyStorageCoreUTA.txt", new Object[0])); 
/* 183 */             return false;
/*     */           } 
/*     */           
/* 186 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 190 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149749_a(World world, int x, int y, int z, Block p_149749_5_, int meta) {
/* 195 */     if (meta == 1) {
/* 196 */       TileEntity tile = world.func_147438_o(x, y, z);
/* 197 */       TileParticleGenerator gen = (tile instanceof TileParticleGenerator) ? (TileParticleGenerator)tile : null;
/* 198 */       if (gen != null && gen.getMaster() != null) {
/* 199 */         world.func_72921_c(x, y, z, 0, 2);
/*     */         
/* 201 */         gen.getMaster().isStructureStillValid(true);
/*     */       } 
/*     */     } 
/* 204 */     super.func_149749_a(world, x, y, z, p_149749_5_, meta);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\ParticleGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */