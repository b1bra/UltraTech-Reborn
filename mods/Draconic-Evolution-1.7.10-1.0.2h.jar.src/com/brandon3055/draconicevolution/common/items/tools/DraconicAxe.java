/*     */ package com.brandon3055.draconicevolution.common.items.tools;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.client.render.IRenderTweak;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.items.ItemDE;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.baseclasses.MiningTool;
/*     */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.IInventoryTool;
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.IItemRenderer;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ public class DraconicAxe
/*     */   extends MiningTool implements IInventoryTool, IRenderTweak {
/*     */   public DraconicAxe() {
/*  28 */     super(ModItems.WYVERN);
/*  29 */     setHarvestLevel("axe", 10);
/*  30 */     func_77655_b("draconicAxe");
/*  31 */     this.energyPerOperation = BalanceConfigHandler.draconicToolsEnergyPerAction;
/*  32 */     ModItems.register((ItemDE)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemConfigField> getFields(ItemStack stack, int slot) {
/*  37 */     List<ItemConfigField> list = super.getFields(stack, slot);
/*     */     
/*  39 */     list.add((new ItemConfigField(2, slot, "ToolDigAOE")).setMinMaxAndIncromente(Integer.valueOf(0), Integer.valueOf(IUpgradableItem.EnumUpgrade.DIG_AOE.getUpgradePoints(stack)), Integer.valueOf(1)).readFromItem(stack, Integer.valueOf(0)).setModifier("AOE"));
/*  40 */     list.add((new ItemConfigField(2, slot, "ToolDigDepth")).setMinMaxAndIncromente(Integer.valueOf(1), Integer.valueOf(IUpgradableItem.EnumUpgrade.DIG_DEPTH.getUpgradePoints(stack)), Integer.valueOf(1)).readFromItem(stack, Integer.valueOf(1)));
/*  41 */     list.add((new ItemConfigField(6, slot, "AxeTreeMode")).readFromItem(stack, Boolean.valueOf(false)));
/*  42 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInventoryName() {
/*  48 */     return StatCollector.func_74838_a("info.de.toolInventoryEnch.txt");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInventorySlots() {
/*  53 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnchantValid(Enchantment enchant) {
/*  58 */     return (enchant.field_77351_y == EnumEnchantmentType.digger);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
/*  63 */     if (IConfigurableItem.ProfileHelper.getBoolean(stack, "AxeTreeMode", false) && isTree(player.field_70170_p, x, y, z)) {
/*  64 */       trimLeavs(x, y, z, player, player.field_70170_p, stack);
/*  65 */       for (int i = 0; i < 9; i++)
/*  66 */         player.field_70170_p.func_72926_e(2001, x, y, z, Block.func_149682_b(player.field_70170_p.func_147439_a(x, y, z)) + (player.field_70170_p.func_72805_g(x, y, z) << 12)); 
/*  67 */       chopTree(x, y, z, player, player.field_70170_p, stack);
/*  68 */       return false;
/*     */     } 
/*     */     
/*  71 */     return super.onBlockStartBreak(stack, x, y, z, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUpgradeCap(ItemStack itemstack) {
/*  76 */     return BalanceConfigHandler.draconicToolsMaxUpgrades;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTier(ItemStack itemstack) {
/*  81 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUpgradeStats(ItemStack stack) {
/*  86 */     return super.getUpgradeStats(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/*  91 */     int points = IUpgradableItem.EnumUpgrade.RF_CAPACITY.getUpgradePoints(stack);
/*  92 */     return (BalanceConfigHandler.draconicToolsBaseStorage + points * BalanceConfigHandler.draconicToolsStoragePerUpgrade);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/*  97 */     return BalanceConfigHandler.draconicToolsMaxTransfer;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex) {
/* 102 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.RF_CAPACITY.index) {
/* 103 */       return BalanceConfigHandler.draconicToolsMaxCapacityUpgradePoints;
/*     */     }
/* 105 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_AOE.index) {
/* 106 */       return 4;
/*     */     }
/* 108 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_DEPTH.index) {
/* 109 */       return 7;
/*     */     }
/* 111 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_SPEED.index) {
/* 112 */       return 32;
/*     */     }
/* 114 */     return BalanceConfigHandler.draconicToolsMaxUpgradePoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseUpgradePoints(int upgradeIndex) {
/* 119 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_AOE.index) {
/* 120 */       return 2;
/*     */     }
/* 122 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_DEPTH.index) {
/* 123 */       return 1;
/*     */     }
/* 125 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_SPEED.index) {
/* 126 */       return 5;
/*     */     }
/* 128 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isTree(World world, int X, int Y, int Z) {
/* 159 */     Block wood = world.func_147439_a(X, Y, Z);
/* 160 */     if (wood == null || !wood.isWood((IBlockAccess)world, X, Y, Z)) {
/* 161 */       return false;
/*     */     }
/* 163 */     int top = Y;
/* 164 */     for (int y = Y; y <= Y + 50; y++) {
/* 165 */       if (!world.func_147439_a(X, y, Z).isWood((IBlockAccess)world, X, y, Z) && !world.func_147439_a(X, y, Z).isLeaves((IBlockAccess)world, X, y, Z)) {
/* 166 */         top += y;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 171 */     int leaves = 0;
/* 172 */     for (int xPos = X - 1; xPos <= X + 1; xPos++) {
/* 173 */       for (int yPos = Y; yPos <= top; yPos++) {
/* 174 */         for (int zPos = Z - 1; zPos <= Z + 1; zPos++) {
/* 175 */           if (world.func_147439_a(xPos, yPos, zPos).isLeaves((IBlockAccess)world, xPos, yPos, zPos)) leaves++; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 179 */     return (leaves >= 3);
/*     */   }
/*     */ 
/*     */   
/*     */   void chopTree(int X, int Y, int Z, EntityPlayer player, World world, ItemStack stack) {
/* 184 */     for (int xPos = X - 1; xPos <= X + 1; xPos++) {
/* 185 */       for (int yPos = Y; yPos <= Y + 1; yPos++) {
/* 186 */         for (int zPos = Z - 1; zPos <= Z + 1; zPos++) {
/* 187 */           Block block = world.func_147439_a(xPos, yPos, zPos);
/* 188 */           int meta = world.func_72805_g(xPos, yPos, zPos);
/* 189 */           if (block.isWood((IBlockAccess)world, xPos, yPos, zPos)) {
/* 190 */             world.func_147468_f(xPos, yPos, zPos);
/* 191 */             if (!player.field_71075_bZ.field_75098_d) {
/* 192 */               if (block.removedByPlayer(world, player, xPos, yPos, zPos, false)) {
/* 193 */                 block.func_149664_b(world, xPos, yPos, zPos, meta);
/*     */               }
/* 195 */               block.func_149636_a(world, player, xPos, yPos, zPos, meta);
/* 196 */               block.func_149681_a(world, xPos, yPos, zPos, meta, player);
/* 197 */               func_150894_a(stack, world, block, xPos, yPos, zPos, (EntityLivingBase)player);
/*     */             } 
/* 199 */             chopTree(xPos, yPos, zPos, player, world, stack);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void trimLeavs(int X, int Y, int Z, EntityPlayer player, World world, ItemStack stack) {
/* 209 */     scedualUpdates(X, Y, Z, player, world, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   void scedualUpdates(int X, int Y, int Z, EntityPlayer player, World world, ItemStack stack) {
/* 214 */     for (int xPos = X - 15; xPos <= X + 15; xPos++) {
/* 215 */       for (int yPos = Y; yPos <= Y + 50; yPos++) {
/* 216 */         for (int zPos = Z - 15; zPos <= Z + 15; zPos++) {
/* 217 */           Block block = world.func_147439_a(xPos, yPos, zPos);
/* 218 */           if (block.isLeaves((IBlockAccess)world, xPos, yPos, zPos)) {
/* 219 */             world.func_147464_a(xPos, yPos, zPos, block, 2 + world.field_73012_v.nextInt(10));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tweakRender(IItemRenderer.ItemRenderType type) {
/* 228 */     GL11.glTranslated(0.34D, 0.69D, 0.1D);
/* 229 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 230 */     GL11.glRotatef(140.0F, 0.0F, -1.0F, 0.0F);
/* 231 */     GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/* 232 */     GL11.glScaled(0.7D, 0.7D, 0.7D);
/*     */     
/* 234 */     if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/* 235 */       GL11.glScalef(11.0F, 11.0F, 11.0F);
/* 236 */       GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 237 */       GL11.glTranslated(-1.3D, 0.0D, -0.45D);
/* 238 */     } else if (type == IItemRenderer.ItemRenderType.ENTITY) {
/* 239 */       GL11.glRotatef(90.5F, 0.0F, 1.0F, 0.0F);
/* 240 */       GL11.glTranslated(0.0D, 0.0D, -0.9D);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\DraconicAxe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */