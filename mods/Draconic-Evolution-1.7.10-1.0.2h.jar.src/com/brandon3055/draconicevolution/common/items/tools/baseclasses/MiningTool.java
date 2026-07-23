/*     */ package com.brandon3055.draconicevolution.common.items.tools.baseclasses;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
/*     */ import com.gamerforea.draconicevolution.EventConfig;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.server.S18PacketEntityTeleport;
/*     */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ import net.minecraftforge.event.world.BlockEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MiningTool
/*     */   extends ToolBase
/*     */   implements IUpgradableItem
/*     */ {
/*     */   public MiningTool(Item.ToolMaterial material) {
/*  48 */     super(0.0F, material, (Set)null);
/*     */   }
/*     */   
/*     */   public Map<Block, Integer> getObliterationList(ItemStack stack) {
/*  52 */     Map<Block, Integer> blockMap = new HashMap<>();
/*     */     
/*  54 */     NBTTagCompound compound = ItemNBTHelper.getCompound(stack);
/*     */     
/*  56 */     if (compound.func_82582_d())
/*  57 */       return blockMap; 
/*  58 */     for (int i = 0; i < 9; i++) {
/*  59 */       NBTTagCompound tag = new NBTTagCompound();
/*  60 */       if (compound.func_74764_b("Item" + i)) {
/*  61 */         tag = compound.func_74775_l("Item" + i);
/*     */       }
/*  63 */       if (!tag.func_82582_d()) {
/*     */ 
/*     */         
/*  66 */         ItemStack stack1 = ItemStack.func_77949_a(tag);
/*     */         
/*  68 */         if (stack1 != null && stack1.func_77973_b() instanceof net.minecraft.item.ItemBlock)
/*  69 */           blockMap.put(Block.func_149634_a(stack1.func_77973_b()), Integer.valueOf(stack1.func_77960_j())); 
/*     */       } 
/*     */     } 
/*  72 */     return blockMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
/*  77 */     int radius = IConfigurableItem.ProfileHelper.getInteger(stack, "ToolDigAOE", 0);
/*     */     
/*  79 */     if (getCharge(stack) >= this.energyPerOperation && radius > 0) {
/*     */       
/*  81 */       if (player.field_70170_p.field_72995_K && !EventConfig.updateGhostBlocks) {
/*  82 */         return true;
/*     */       }
/*     */       
/*  85 */       int depth = IConfigurableItem.ProfileHelper.getInteger(stack, "ToolDigDepth", 1) - 1;
/*  86 */       return breakAOEBlocks(stack, x, y, z, radius, depth, player);
/*     */     } 
/*     */     
/*  89 */     return super.onBlockStartBreak(stack, x, y, z, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_150894_a(ItemStack stack, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase entity) {
/*  94 */     if (IConfigurableItem.ProfileHelper.getInteger(stack, "ToolDigAOE", 0) == 0) {
/*  95 */       if (entity instanceof EntityPlayer) {
/*  96 */         useEnergy(stack, this.energyPerOperation, (EntityPlayer)entity);
/*     */       } else {
/*  98 */         discharge(stack, this.energyPerOperation, false);
/*     */       } 
/*     */     }
/*     */     
/* 102 */     return super.func_150894_a(stack, p_150894_2_, p_150894_3_, p_150894_4_, p_150894_5_, p_150894_6_, entity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack stack, World world, EntityPlayer player) {
/* 107 */     ToolHandler.updateGhostBlocks(player, world);
/* 108 */     return super.func_77659_a(stack, world, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean breakAOEBlocks(ItemStack stack, int x, int y, int z, int breakRadius, int breakDepth, EntityPlayer player) {
/* 113 */     if (this instanceof com.brandon3055.draconicevolution.common.items.tools.DraconicDistructionStaff) {
/* 114 */       if (breakRadius > EventConfig.staffMaxRange)
/* 115 */         breakRadius = EventConfig.staffMaxRange; 
/* 116 */       if (breakDepth > EventConfig.staffMaxRange)
/* 117 */         breakDepth = EventConfig.staffMaxRange; 
/* 118 */     } else if (this instanceof com.brandon3055.draconicevolution.common.items.tools.DraconicPickaxe || this instanceof com.brandon3055.draconicevolution.common.items.tools.WyvernPickaxe) {
/* 119 */       if (breakRadius > EventConfig.pickaxeMaxRange)
/* 120 */         breakRadius = EventConfig.pickaxeMaxRange; 
/* 121 */       if (breakDepth > EventConfig.pickaxeMaxRange)
/* 122 */         breakDepth = EventConfig.pickaxeMaxRange; 
/*     */     } else {
/* 124 */       if (breakRadius > 20)
/* 125 */         breakRadius = 20; 
/* 126 */       if (breakDepth > 20) {
/* 127 */         breakDepth = 20;
/*     */       }
/*     */     } 
/*     */     
/* 131 */     Map<Block, Integer> blockMap = IConfigurableItem.ProfileHelper.getBoolean(stack, "ToolVoidJunk", false) ? getObliterationList(stack) : new HashMap<>();
/* 132 */     Block block = player.field_70170_p.func_147439_a(x, y, z);
/* 133 */     int meta = player.field_70170_p.func_72805_g(x, y, z);
/* 134 */     boolean effective = false;
/*     */     
/* 136 */     if (block != null)
/* 137 */       for (String s : getToolClasses(stack)) {
/* 138 */         if (block.isToolEffective(s, meta) || func_150893_a(stack, block) > 1.0F) {
/* 139 */           effective = true;
/*     */         }
/*     */       }  
/* 142 */     if (!effective) {
/* 143 */       return true;
/*     */     }
/* 145 */     float refStrength = ForgeHooks.blockStrength(block, player, player.field_70170_p, x, y, z);
/*     */     
/* 147 */     MovingObjectPosition mop = ToolHandler.raytraceFromEntity(player.field_70170_p, (Entity)player, 4.5D);
/* 148 */     if (mop == null) {
/* 149 */       ToolHandler.updateGhostBlocks(player, player.field_70170_p);
/* 150 */       return true;
/*     */     } 
/* 152 */     int sideHit = mop.field_72310_e;
/*     */     
/* 154 */     int xMax = breakRadius;
/* 155 */     int xMin = breakRadius;
/* 156 */     int yMax = breakRadius;
/* 157 */     int yMin = breakRadius;
/* 158 */     int zMax = breakRadius;
/* 159 */     int zMin = breakRadius;
/* 160 */     int yOffset = 0;
/*     */     
/* 162 */     switch (sideHit) {
/*     */       case 0:
/* 164 */         yMax = breakDepth;
/* 165 */         yMin = 0;
/* 166 */         zMax = breakRadius;
/*     */         break;
/*     */       case 1:
/* 169 */         yMin = breakDepth;
/* 170 */         yMax = 0;
/* 171 */         zMax = breakRadius;
/*     */         break;
/*     */       case 2:
/* 174 */         xMax = breakRadius;
/* 175 */         zMin = 0;
/* 176 */         zMax = breakDepth;
/* 177 */         yOffset = breakRadius - 1;
/*     */         break;
/*     */       case 3:
/* 180 */         xMax = breakRadius;
/* 181 */         zMax = 0;
/* 182 */         zMin = breakDepth;
/* 183 */         yOffset = breakRadius - 1;
/*     */         break;
/*     */       case 4:
/* 186 */         xMax = breakDepth;
/* 187 */         xMin = 0;
/* 188 */         zMax = breakRadius;
/* 189 */         yOffset = breakRadius - 1;
/*     */         break;
/*     */       case 5:
/* 192 */         xMin = breakDepth;
/* 193 */         xMax = 0;
/* 194 */         zMax = breakRadius;
/* 195 */         yOffset = breakRadius - 1;
/*     */         break;
/*     */     } 
/*     */     
/* 199 */     if (IConfigurableItem.ProfileHelper.getBoolean(stack, "BaseSafeAOE", false)) {
/* 200 */       for (int i = x - xMin; i <= x + xMax; i++) {
/* 201 */         for (int yPos = y + yOffset - yMin; yPos <= y + yOffset + yMax; yPos++) {
/* 202 */           for (int zPos = z - zMin; zPos <= z + zMax; zPos++) {
/* 203 */             if (player.field_70170_p.func_147438_o(i, yPos, zPos) != null) {
/* 204 */               if (player.field_70170_p.field_72995_K) {
/* 205 */                 player.func_146105_b((IChatComponent)new ChatComponentTranslation("msg.de.baseSafeAOW.txt", new Object[0]));
/*     */               } else {
/* 207 */                 ((EntityPlayerMP)player).field_71135_a.func_147359_a((Packet)new S23PacketBlockChange(x, y, z, player.field_70170_p));
/* 208 */               }  return true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 214 */     for (int xPos = x - xMin; xPos <= x + xMax; xPos++) {
/* 215 */       for (int yPos = y + yOffset - yMin; yPos <= y + yOffset + yMax; yPos++) {
/* 216 */         for (int zPos = z - zMin; zPos <= z + zMax; zPos++) {
/* 217 */           breakExtraBlock(stack, player.field_70170_p, xPos, yPos, zPos, breakRadius * (breakDepth / 2 + 1), player, refStrength, (Math.abs(x - xPos) <= 1 && Math.abs(y - yPos) <= 1 && Math.abs(z - zPos) <= 1), blockMap);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 223 */     List<EntityItem> items = player.field_70170_p.func_72872_a(EntityItem.class, AxisAlignedBB.func_72330_a((x - xMin), (y + yOffset - yMin), (z - zMin), (x + xMax + 1), (y + yOffset + yMax + 1), (z + zMax + 1)));
/* 224 */     for (EntityItem item : items) {
/* 225 */       if (!player.field_70170_p.field_72995_K) {
/* 226 */         item.func_70012_b(player.field_70165_t, player.field_70163_u, player.field_70161_v, 0.0F, 0.0F);
/* 227 */         ((EntityPlayerMP)player).field_71135_a.func_147359_a((Packet)new S18PacketEntityTeleport((Entity)item));
/* 228 */         item.field_145804_b = 0;
/* 229 */         if (ConfigHandler.rapidlyDespawnMinedItems) {
/* 230 */           item.lifespan = 100;
/*     */         }
/*     */       } 
/*     */     } 
/* 234 */     return true;
/*     */   }
/*     */   
/*     */   protected void breakExtraBlock(ItemStack stack, World world, int x, int y, int z, int totalSize, EntityPlayer player, float refStrength, boolean breakSound, Map<Block, Integer> blockMap) {
/* 238 */     if (world.func_147437_c(x, y, z)) {
/*     */       return;
/*     */     }
/* 241 */     Block block = world.func_147439_a(x, y, z);
/* 242 */     if (block.func_149688_o() instanceof net.minecraft.block.material.MaterialLiquid || (block.func_149712_f(world, x, y, x) == -1.0F && !player.field_71075_bZ.field_75098_d)) {
/*     */       return;
/*     */     }
/* 245 */     int meta = world.func_72805_g(x, y, z);
/*     */     
/* 247 */     boolean effective = false;
/*     */     
/* 249 */     for (String s : getToolClasses(stack)) {
/* 250 */       if (block.isToolEffective(s, meta) || func_150893_a(stack, block) > 1.0F) {
/* 251 */         effective = true;
/*     */       }
/*     */     } 
/* 254 */     if (!effective) {
/*     */       return;
/*     */     }
/* 257 */     float strength = ForgeHooks.blockStrength(block, player, world, x, y, z);
/*     */     
/* 259 */     if (!player.func_146099_a(block) || !ForgeHooks.canHarvestBlock(block, player, meta) || (refStrength / strength > 10.0F && !player.field_71075_bZ.field_75098_d)) {
/*     */       return;
/*     */     }
/* 262 */     if (!world.field_72995_K) {
/* 263 */       BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, world.func_72912_H()
/* 264 */           .func_76077_q(), (EntityPlayerMP)player, x, y, z);
/* 265 */       if (event.isCanceled()) {
/* 266 */         ((EntityPlayerMP)player).field_71135_a.func_147359_a((Packet)new S23PacketBlockChange(x, y, z, world));
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 271 */     int scaledPower = this.energyPerOperation + totalSize * this.energyPerOperation / 10;
/*     */     
/* 273 */     if (player.field_71075_bZ.field_75098_d || (blockMap.containsKey(block) && ((Integer)blockMap.get(block)).intValue() == meta)) {
/*     */       
/* 275 */       block.func_149681_a(world, x, y, z, meta, player);
/* 276 */       if (block.removedByPlayer(world, player, x, y, z, false)) {
/* 277 */         block.func_149664_b(world, x, y, z, meta);
/*     */       }
/* 279 */       if (!world.field_72995_K) {
/* 280 */         ((EntityPlayerMP)player).field_71135_a.func_147359_a((Packet)new S23PacketBlockChange(x, y, z, world));
/*     */       }
/* 282 */       if (blockMap.containsKey(block) && ((Integer)blockMap.get(block)).intValue() == meta)
/* 283 */         useEnergy(stack, scaledPower, player); 
/* 284 */       if (breakSound) {
/* 285 */         world.func_72926_e(2001, x, y, z, Block.func_149682_b(block) + (meta << 12));
/*     */       }
/*     */       return;
/*     */     } 
/* 289 */     useEnergy(stack, scaledPower, player);
/*     */     
/* 291 */     if (!world.field_72995_K) {
/*     */       
/* 293 */       block.func_149681_a(world, x, y, z, meta, player);
/*     */       
/* 295 */       if (block.removedByPlayer(world, player, x, y, z, true)) {
/* 296 */         block.func_149664_b(world, x, y, z, meta);
/* 297 */         block.func_149636_a(world, player, x, y, z, meta);
/* 298 */         player.func_71020_j(-0.025F);
/* 299 */         if (block.getExpDrop((IBlockAccess)world, meta, EnchantmentHelper.func_77517_e((EntityLivingBase)player)) > 0) {
/* 300 */           player.func_71023_q(block.getExpDrop((IBlockAccess)world, meta, EnchantmentHelper.func_77517_e((EntityLivingBase)player)));
/*     */         }
/*     */       } 
/* 303 */       EntityPlayerMP mpPlayer = (EntityPlayerMP)player;
/* 304 */       mpPlayer.field_71135_a.func_147359_a((Packet)new S23PacketBlockChange(x, y, z, world));
/*     */     } else {
/* 306 */       if (breakSound)
/* 307 */         world.func_72926_e(2001, x, y, z, Block.func_149682_b(block) + (meta << 12)); 
/* 308 */       if (block.removedByPlayer(world, player, x, y, z, true)) {
/* 309 */         block.func_149664_b(world, x, y, z, meta);
/*     */       }
/* 311 */       Minecraft.func_71410_x()
/* 312 */         .func_147114_u()
/* 313 */         .func_147297_a((Packet)new C07PacketPlayerDigging(2, x, y, z, (Minecraft.func_71410_x()).field_71476_x.field_72310_e));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IUpgradableItem.EnumUpgrade> getUpgrades(ItemStack itemstack) {
/* 319 */     return new ArrayList<IUpgradableItem.EnumUpgrade>()
/*     */       {
/*     */       
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex, ItemStack stack) {
/* 329 */     return getMaxUpgradePoints(upgradeIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getEfficiency(ItemStack stack) {
/* 335 */     if (!EventConfig.enableDigSpeedUpgrade) {
/* 336 */       return super.getEfficiency(stack);
/*     */     }
/*     */     
/* 339 */     int i = IUpgradableItem.EnumUpgrade.DIG_SPEED.getUpgradePoints(stack);
/* 340 */     if (i == 0) {
/* 341 */       return super.getEfficiency(stack);
/*     */     }
/* 343 */     return i * 3.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUpgradeStats(ItemStack stack) {
/* 348 */     List<String> strings = new ArrayList<>();
/* 349 */     int digaoe = 0;
/* 350 */     int depth = 0;
/* 351 */     int attackaoe = 0;
/* 352 */     for (ItemConfigField field : getFields(stack, 0)) {
/* 353 */       switch (field.name) {
/*     */         case "ToolDigAOE":
/* 355 */           digaoe = 1 + ((Integer)field.max).intValue() * 2;
/*     */         
/*     */         case "ToolDigDepth":
/* 358 */           depth = ((Integer)field.max).intValue();
/*     */         
/*     */         case "WeaponAttackAOE":
/* 361 */           attackaoe = 1 + ((Integer)field.max).intValue() * 2;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 366 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.RFCapacity.txt") + ": " + InfoHelper.HITC() + Utills.formatNumber(getMaxCharge(stack)));
/* 367 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.max.txt") + " " + StatCollector.func_74838_a("gui.de.DigAOE.txt") + ": " + InfoHelper.HITC() + digaoe + "x" + digaoe);
/* 368 */     if (depth > 0)
/* 369 */       strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.max.txt") + " " + StatCollector.func_74838_a("gui.de.DigDepth.txt") + ": " + InfoHelper.HITC() + depth); 
/* 370 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.max.txt") + " " + StatCollector.func_74838_a("gui.de.DigSpeed.txt") + ": " + InfoHelper.HITC() + getEfficiency(stack));
/* 371 */     if (attackaoe > 0) {
/* 372 */       strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.max.txt") + " " + StatCollector.func_74838_a("gui.de.AttackAOE.txt") + ": " + InfoHelper.HITC() + attackaoe + "x" + attackaoe);
/*     */     }
/* 374 */     return strings;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\baseclasses\MiningTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */