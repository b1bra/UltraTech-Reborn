/*     */ package com.brandon3055.draconicevolution.common.blocks.multiblock;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.api.DraconicEvolutionTags;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.blocks.BlockDE;
/*     */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.TileEnergyPylon;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.TileEnergyStorageCore;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.TileInvisibleMultiblock;
/*     */ import com.brandon3055.draconicevolution.common.utills.IHudDisplayBlock;
/*     */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import com.brandon3055.brandonscore.common.tags.Tags;
/*     */ import com.brandon3055.brandonscore.common.tags.BlockEntry;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InvisibleMultiblock
/*     */   extends BlockDE
/*     */   implements IHudDisplayBlock
/*     */ {
/*     */   public InvisibleMultiblock() {
/*  43 */     super(Material.field_151573_f);
/*  44 */     func_149711_c(10.0F);
/*  45 */     func_149752_b(2000.0F);
/*     */     
/*  47 */     func_149663_c("invisibleMultiblock");
/*  48 */     ModBlocks.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister iconRegister) {
/*  54 */     this.field_149761_L = iconRegister.func_94245_a("draconicevolution:draconium_block_0");
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_149645_b() {
/*  60 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTileEntity(int metadata) {
/*  75 */     return (metadata == 0 || metadata == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity createTileEntity(World world, int metadata) {
/*  80 */     if (metadata == 0 || metadata == 1) return (TileEntity)new TileInvisibleMultiblock(); 
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item func_149650_a(int meta, Random p_149650_2_, int var2) {
/*  86 */     if (meta == 0)
/*  87 */       return DraconicEvolutionTags.Blocks.DRACONIUM.getPrimary().map(BlockEntry::getItem).orElse(null); 
/*  88 */     if (meta == 1) {
/*  89 */       return Item.func_150898_a(BalanceConfigHandler.energyStorageStructureBlock);
/*     */     }
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149692_a(int metadata) {
/*  97 */     if (metadata == 0)
/*  98 */       return ((Integer)DraconicEvolutionTags.Blocks.DRACONIUM.getPrimary().map(BlockEntry::getPlacementMeta).orElse(Integer.valueOf(0))).intValue(); 
/*  99 */     if (metadata == 1) {
/* 100 */       return BalanceConfigHandler.energyStorageStructureBlockMetadata;
/*     */     }
/* 102 */     return super.func_149692_a(metadata);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
/* 107 */     int meta = world.func_72805_g(x, y, z);
/* 108 */     if (meta == 0 || meta == 1) {
/* 109 */       TileInvisibleMultiblock thisTile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TileInvisibleMultiblock) ? (TileInvisibleMultiblock)world.func_147438_o(x, y, z) : null;
/* 110 */       if (thisTile == null) {
/* 111 */         LogHelper.error("Missing Tile Entity (TileInvisibleMultiblock)");
/* 112 */         return false;
/*     */       } 
/* 114 */       TileEnergyStorageCore master = thisTile.getMaster();
/* 115 */       if (master == null) {
/* 116 */         func_149695_a(world, x, y, z, (Block)this);
/* 117 */         return false;
/*     */       } 
/* 119 */       if (!world.field_72995_K) {
/* 120 */         world.func_147471_g(master.field_145851_c, master.field_145848_d, master.field_145849_e);
/* 121 */         player.func_146105_b((IChatComponent)new ChatComponentText("Tier:" + (master.getTier() + 1)));
/* 122 */         String BN = String.valueOf(master.getEnergyStored());
/* 123 */         player.func_146105_b((IChatComponent)new ChatComponentText(StatCollector.func_74838_a("info.de.charge.txt") + ": " + Utills.formatNumber(master.getEnergyStored()) + " / " + Utills.formatNumber(master.getMaxEnergyStored()) + " [" + BN + " EU]"));
/*     */       } 
/* 125 */       return true;
/* 126 */     }  if (meta == 2) {
/* 127 */       TileEnergyPylon pylon = (world.func_147438_o(x, y + 1, z) != null && world.func_147438_o(x, y + 1, z) instanceof TileEnergyPylon) ? (TileEnergyPylon)world.func_147438_o(x, y + 1, z) : ((world.func_147438_o(x, y - 1, z) != null && world.func_147438_o(x, y - 1, z) instanceof TileEnergyPylon) ? (TileEnergyPylon)world.func_147438_o(x, y - 1, z) : null);
/* 128 */       if (pylon == null) return false; 
/* 129 */       pylon.setReciveEnergy(!pylon.reciveEnergy);
/* 130 */       world.func_147471_g(pylon.field_145851_c, pylon.field_145848_d, pylon.field_145849_e);
/* 131 */       pylon.onActivated();
/* 132 */       return true;
/*     */     } 
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int x, int y, int z, Block p_149695_5_) {
/* 139 */     int meta = world.func_72805_g(x, y, z);
/* 140 */     if (meta == 0 || meta == 1) {
/* 141 */       TileInvisibleMultiblock thisTile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TileInvisibleMultiblock) ? (TileInvisibleMultiblock)world.func_147438_o(x, y, z) : null;
/* 142 */       if (thisTile == null) {
/* 143 */         LogHelper.error("Missing Tile Entity (TileInvisibleMultiblock)");
/* 144 */         revert(world, x, y, z);
/*     */         return;
/*     */       } 
/* 147 */       TileEnergyStorageCore master = thisTile.getMaster();
/* 148 */       if (master == null) {
/* 149 */         LogHelper.error("Master = null reverting!");
/* 150 */         revert(world, x, y, z);
/*     */         return;
/*     */       } 
/* 153 */       if (master.isOnline()) master.isStructureStillValid((thisTile.getMaster().getTier() == 1)); 
/* 154 */       if (!master.isOnline()) revert(world, x, y, z); 
/* 155 */     } else if (meta == 2 && 
/* 156 */       world.func_147439_a(x, y + 1, z) != ModBlocks.energyPylon && world.func_147439_a(x, y - 1, z) != ModBlocks.energyPylon) {
/* 157 */       Tags.Blocks.GLASS.set(world, x, y, z);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void revert(World world, int x, int y, int z) {
/* 162 */     int meta = world.func_72805_g(x, y, z);
/* 163 */     if (meta == 0) {
/* 164 */       DraconicEvolutionTags.Blocks.DRACONIUM.set(world, x, y, z);
/* 165 */     } else if (meta == 1) {
/* 166 */       world.func_147465_d(x, y, z, BalanceConfigHandler.energyStorageStructureBlock, BalanceConfigHandler.energyStorageStructureBlockMetadata, 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149749_a(World world, int x, int y, int z, Block p_149749_5_, int meta) {
/* 172 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 173 */     TileInvisibleMultiblock thisTile = (tile instanceof TileInvisibleMultiblock) ? (TileInvisibleMultiblock)tile : null;
/* 174 */     if (thisTile != null && thisTile.getMaster() != null && thisTile.getMaster().isOnline()) {
/* 175 */       world.func_72921_c(x, y, z, 0, 2);
/*     */       
/* 177 */       thisTile.getMaster().isStructureStillValid((thisTile.getMaster().getTier() == 1));
/*     */     } 
/* 179 */     super.func_149749_a(world, x, y, z, p_149749_5_, meta);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149633_g(World world, int x, int y, int z) {
/* 184 */     int meta = world.func_72805_g(x, y, z);
/* 185 */     if (meta == 0 || meta == 1) {
/* 186 */       TileInvisibleMultiblock thisTile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TileInvisibleMultiblock) ? (TileInvisibleMultiblock)world.func_147438_o(x, y, z) : null;
/* 187 */       if (thisTile != null && thisTile.getMaster() != null) {
/* 188 */         return AxisAlignedBB.func_72330_a((thisTile.getMaster()).field_145851_c, (thisTile.getMaster()).field_145848_d, (thisTile.getMaster()).field_145849_e, (thisTile.getMaster()).field_145851_c + 0.5D, (thisTile.getMaster()).field_145848_d + 0.5D, (thisTile.getMaster()).field_145849_e + 0.5D);
/*     */       }
/* 190 */       return super.func_149633_g(world, x, y, z);
/* 191 */     }  if (meta == 2) {
/* 192 */       return AxisAlignedBB.func_72330_a(x + 0.49D, y + 0.49D, z + 0.49D, x + 0.51D, y + 0.51D, z + 0.51D);
/*     */     }
/* 194 */     return super.func_149633_g(world, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149668_a(World world, int x, int y, int z) {
/* 200 */     int meta = world.func_72805_g(x, y, z);
/* 201 */     if (meta == 2) {
/* 202 */       return AxisAlignedBB.func_72330_a(x, y, z, x, y, z);
/*     */     }
/* 204 */     return super.func_149668_a(world, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
/* 209 */     if (world.func_72805_g(x, y, z) == 0)
/* 210 */       return DraconicEvolutionTags.Blocks.DRACONIUM.makeStack().orElseGet(Tags.Blocks.GLASS::requireStack); 
/* 211 */     if (world.func_72805_g(x, y, z) == 1) {
/* 212 */       return new ItemStack(BalanceConfigHandler.energyStorageStructureBlock, 1, BalanceConfigHandler.energyStorageStructureBlockMetadata);
/*     */     }
/* 214 */     return Tags.Blocks.GLASS.requireStack();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getDisplayData(World world, int x, int y, int z) {
/* 220 */     List<String> list = new ArrayList<>();
/*     */     
/* 222 */     int meta = world.func_72805_g(x, y, z);
/* 223 */     if (meta == 0 || meta == 1) {
/* 224 */       TileInvisibleMultiblock thisTile = (world.func_147438_o(x, y, z) != null && world.func_147438_o(x, y, z) instanceof TileInvisibleMultiblock) ? (TileInvisibleMultiblock)world.func_147438_o(x, y, z) : null;
/* 225 */       if (thisTile == null) {
/* 226 */         LogHelper.error("Missing Tile Entity (TileInvisibleMultiblock getDisplayData)");
/* 227 */         return list;
/*     */       } 
/*     */       
/* 230 */       TileEnergyStorageCore master = thisTile.getMaster();
/*     */       
/* 232 */       if (master == null) {
/* 233 */         return list;
/*     */       }
/*     */       
/* 236 */       list.add(InfoHelper.HITC() + ModBlocks.energyStorageCore.func_149732_F());
/* 237 */       list.add("Tier: " + InfoHelper.ITC() + (master.getTier() + 1));
/* 238 */       String BN = String.valueOf(master.getEnergyStored());
/* 239 */       list.add(StatCollector.func_74838_a("info.de.charge.txt") + ": " + InfoHelper.ITC() + Utills.formatNumber(master.getEnergyStored()) + " / " + Utills.formatNumber(master.getMaxEnergyStored()) + " [" + Utills.addCommas(master.getEnergyStored()) + " EU]");
/*     */       
/* 241 */       return list;
/*     */     } 
/*     */     
/* 244 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_149739_a() {
/* 249 */     return super.func_149739_a();
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\multiblock\InvisibleMultiblock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */