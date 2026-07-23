/*     */ package com.brandon3055.draconicevolution.common.items.tools;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.InventoryUtils;
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.api.IDraconicElectricItem;
/*     */ import com.brandon3055.draconicevolution.client.render.IRenderTweak;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.entity.EntityPersistentItem;
/*     */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.baseclasses.ToolBase;
/*     */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.IHudDisplayItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import com.brandon3055.brandonscore.common.tags.Tags;
/*     */ import com.brandon3055.brandonscore.common.tags.IItemMatcher;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.IItemRenderer;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.player.UseHoeEvent;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ public class DraconicHoe
/*     */   extends ItemHoe
/*     */   implements IDraconicElectricItem, IRenderTweak, IUpgradableItem, IConfigurableItem, IHudDisplayItem {
/*     */   public DraconicHoe() {
/*  49 */     super(ModItems.WYVERN);
/*  50 */     func_77655_b("draconicHoe");
/*  51 */     func_77637_a(DraconicEvolution.tabToolsWeapons);
/*  52 */     if (ModItems.isEnabled((Item)this)) {
/*  53 */       GameRegistry.registerItem((Item)this, "draconicHoe");
/*     */     }
/*     */   }
/*     */   
/*     */   public int func_77619_b() {
/*  58 */     return this.field_77843_a.func_77995_e();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemConfigField> getFields(ItemStack stack, int slot) {
/*  63 */     List<ItemConfigField> list = new ArrayList<>();
/*  64 */     list.add((new ItemConfigField(2, slot, "ToolDigAOE")).setMinMaxAndIncromente(Integer.valueOf(0), Integer.valueOf(IUpgradableItem.EnumUpgrade.DIG_AOE.getUpgradePoints(stack)), Integer.valueOf(1))
/*  65 */         .readFromItem(stack, Integer.valueOf(0))
/*  66 */         .setModifier("AOE"));
/*  67 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77616_k(ItemStack p_77616_1_) {
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item item, CreativeTabs tab, List<ItemStack> list) {
/*  79 */     list.add(new ItemStack(item));
/*  80 */     list.add(makeChargedStack());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77658_a() {
/*  86 */     return String.format("item.%s%s", new Object[] { "draconicevolution:", super.func_77658_a()
/*  87 */           .substring(super.func_77658_a()
/*  88 */             .indexOf('.') + 1) });
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack itemStack) {
/*  93 */     return func_77658_a();
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister iconRegister) {
/*  99 */     this.field_77791_bV = iconRegister.func_94245_a("draconicevolution:draconic_hoe");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
/* 104 */     boolean successfull = false;
/* 105 */     Block clicked = world.func_147439_a(x, y, z);
/*     */     
/* 107 */     if (!player.func_70093_af() && player.func_82247_a(x, y, z, side, stack) && (Tags.Blocks.DIRT.is(clicked) || Tags.Blocks.GRASS.is(clicked) || Tags.Blocks.FARMLAND.is(clicked)) && side == 1) {
/* 108 */       int size = IConfigurableItem.ProfileHelper.getInteger(stack, "ToolDigAOE", 0);
/*     */       
/* 110 */       for (int offsetX = -size; offsetX <= size; offsetX++) {
/* 111 */         for (int offsetZ = -size; offsetZ <= size; offsetZ++) {
/* 112 */           if (!player.field_71075_bZ.field_75098_d && getCharge(stack) < BalanceConfigHandler.draconicToolsEnergyPerAction) {
/* 113 */             return false;
/*     */           }
/* 115 */           int xx = x + offsetX;
/* 116 */           int zz = z + offsetZ;
/*     */ 
/*     */           
/* 119 */           if (!world.field_72995_K) {
/*     */ 
/*     */             
/* 122 */             int topY = y + 1;
/* 123 */             Block topBlock = world.func_147439_a(xx, topY, zz);
/*     */             
/* 125 */             if (topBlock.isReplaceable((IBlockAccess)world, xx, topY, zz)) {
/* 126 */               world.func_147468_f(xx, topY, zz);
/*     */             }
/* 128 */             int top2Y = y + 2;
/* 129 */             Block topBlock2 = world.func_147439_a(xx, top2Y, zz);
/*     */             
/* 131 */             if (topBlock2.isReplaceable((IBlockAccess)world, xx, top2Y, zz)) {
/* 132 */               world.func_147468_f(xx, top2Y, zz);
/*     */             }
/* 134 */             Block block = world.func_147439_a(xx, y, zz);
/*     */             
/* 136 */             if (block.isReplaceable((IBlockAccess)world, xx, y, zz) && !block.func_149688_o().equals(Material.field_151586_h)) {
/* 137 */               world.func_147468_f(xx, y, zz);
/*     */             }
/* 139 */             if (world.func_147439_a(xx, y, zz) == Blocks.field_150350_a && world.func_147439_a(xx, y - 1, zz)
/* 140 */               .func_149747_d((IBlockAccess)world, x, y, z, 1))
/*     */             {
/* 142 */               if (player.field_71075_bZ.field_75098_d || InventoryUtils.consumeItem(player, (IItemMatcher)Tags.Blocks.DIRT)) {
/* 143 */                 Tags.Blocks.DIRT.set(world, xx, y, zz);
/*     */               }
/*     */             }
/* 146 */             Block newTopBlock = world.func_147439_a(xx, topY, zz);
/* 147 */             if ((Tags.Blocks.DIRT.is(newTopBlock) || Tags.Blocks.GRASS.is(newTopBlock) || Tags.Blocks.FARMLAND.is(newTopBlock)) && world.func_147439_a(xx, top2Y, zz) == Blocks.field_150350_a) {
/*     */               
/* 149 */               if (!world.field_72995_K)
/* 150 */                 world.func_72838_d((Entity)new EntityItem(world, player.field_70165_t, player.field_70163_u, player.field_70161_v, Tags.Blocks.DIRT.requireStack())); 
/* 151 */               world.func_147449_b(xx, topY, zz, Blocks.field_150350_a);
/*     */             } 
/*     */           } 
/*     */           
/* 155 */           if (hoe(stack, player, world, xx, y, zz, side))
/* 156 */             successfull = true; 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 160 */       successfull = hoe(stack, player, world, x, y, z, side);
/*     */     } 
/* 162 */     if (successfull) {
/* 163 */       Block farmland = Tags.Blocks.FARMLAND.requirePrimary().getBlock();
/* 164 */       world.func_72908_a((x + 0.5F), (y + 0.5F), (z + 0.5F), farmland.field_149762_H.func_150498_e(), (farmland.field_149762_H.func_150497_c() + 1.0F) / 2.0F, farmland.field_149762_H.func_150494_d() * 0.8F);
/*     */     } 
/*     */     
/* 167 */     return successfull;
/*     */   }
/*     */   
/*     */   private boolean hoe(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
/* 171 */     if (!player.field_71075_bZ.field_75098_d && !useEnergy(stack, BalanceConfigHandler.draconicToolsEnergyPerAction, player)) {
/* 172 */       return false;
/*     */     }
/* 174 */     if (!player.func_82247_a(x, y, z, side, stack)) {
/* 175 */       return false;
/*     */     }
/* 177 */     UseHoeEvent event = new UseHoeEvent(player, stack, world, x, y, z);
/* 178 */     if (MinecraftForge.EVENT_BUS.post((Event)event)) {
/* 179 */       return false;
/*     */     }
/* 181 */     if (event.getResult() == Event.Result.ALLOW) {
/* 182 */       stack.func_77972_a(1, (EntityLivingBase)player);
/* 183 */       return true;
/*     */     } 
/*     */     
/* 186 */     Block block = world.func_147439_a(x, y, z);
/*     */     
/* 188 */     if (side != 0 && world.func_147439_a(x, y + 1, z)
/* 189 */       .isAir((IBlockAccess)world, x, y + 1, z) && (Tags.Blocks.GRASS.is(block) || Tags.Blocks.DIRT.is(block))) {
/* 190 */       if (world.field_72995_K) {
/* 191 */         return true;
/*     */       }
/* 193 */       Tags.Blocks.FARMLAND.set(world, x, y, z);
/* 194 */       stack.func_77972_a(1, (EntityLivingBase)player);
/* 195 */       return true;
/*     */     } 
/*     */     
/* 198 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List list, boolean extraInformation) {
/* 203 */     InfoHelper.addEnergyInfo(stack, list);
/* 204 */     ToolBase.holdCTRLForUpgrades(list, stack);
/* 205 */     InfoHelper.addLore(stack, list);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/* 210 */     int points = IUpgradableItem.EnumUpgrade.RF_CAPACITY.getUpgradePoints(stack);
/* 211 */     return (BalanceConfigHandler.draconicToolsBaseStorage + points * BalanceConfigHandler.draconicToolsStoragePerUpgrade);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/* 216 */     return BalanceConfigHandler.draconicToolsMaxTransfer;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean showDurabilityBar(ItemStack stack) {
/* 221 */     return (getCharge(stack) < getMaxCharge(stack));
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDurabilityForDisplay(ItemStack stack) {
/* 226 */     return 1.0D - getChargePercent(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCustomEntity(ItemStack stack) {
/* 231 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity createEntity(World world, Entity location, ItemStack itemstack) {
/* 236 */     return (Entity)new EntityPersistentItem(world, location, itemstack);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tweakRender(IItemRenderer.ItemRenderType type) {
/* 241 */     GL11.glTranslated(0.4D, 1.0D, 0.0D);
/* 242 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 243 */     GL11.glRotatef(140.0F, 0.0F, -1.0F, 0.0F);
/* 244 */     GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
/* 245 */     GL11.glScaled(0.6D, 0.6D, 0.6D);
/*     */     
/* 247 */     if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/* 248 */       GL11.glScalef(12.0F, 12.0F, 12.0F);
/* 249 */       GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 250 */       GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/* 251 */       GL11.glTranslated(-1.4D, 0.0D, -0.1D);
/* 252 */     } else if (type == IItemRenderer.ItemRenderType.ENTITY) {
/* 253 */       GL11.glRotatef(90.5F, 1.0F, 0.0F, 0.0F);
/* 254 */       GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);
/* 255 */       GL11.glTranslated(0.35D, -0.4D, -1.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IUpgradableItem.EnumUpgrade> getUpgrades(ItemStack itemstack) {
/* 261 */     return new ArrayList<IUpgradableItem.EnumUpgrade>()
/*     */       {
/*     */       
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUpgradeCap(ItemStack itemstack) {
/* 269 */     return BalanceConfigHandler.draconicToolsMaxUpgrades;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTier(ItemStack itemstack) {
/* 274 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex) {
/* 279 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.RF_CAPACITY.index)
/* 280 */       return BalanceConfigHandler.draconicToolsMaxCapacityUpgradePoints; 
/* 281 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_AOE.index)
/* 282 */       return 5; 
/* 283 */     return BalanceConfigHandler.draconicToolsMaxUpgradePoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex, ItemStack stack) {
/* 288 */     return getMaxUpgradePoints(upgradeIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseUpgradePoints(int upgradeIndex) {
/* 293 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.DIG_AOE.index)
/* 294 */       return 3; 
/* 295 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUpgradeStats(ItemStack itemstack) {
/* 300 */     List<String> strings = new ArrayList<>();
/*     */     
/* 302 */     int digaoe = 0;
/* 303 */     for (ItemConfigField field : getFields(itemstack, 0)) {
/* 304 */       if (field.name.equals("ToolDigAOE")) {
/* 305 */         digaoe = 1 + ((Integer)field.max).intValue() * 2;
/*     */       }
/*     */     } 
/* 308 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.RFCapacity.txt") + ": " + InfoHelper.HITC() + Utills.formatNumber(getMaxCharge(itemstack)));
/* 309 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.max.txt") + " " + StatCollector.func_74838_a("gui.de.DigAOE.txt") + ": " + InfoHelper.HITC() + digaoe + "x" + digaoe);
/*     */     
/* 311 */     return strings;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasProfiles() {
/* 316 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getDisplayData(ItemStack stack) {
/* 321 */     List<String> list = new ArrayList<>();
/*     */     
/* 323 */     for (ItemConfigField field : getFields(stack, 0)) {
/* 324 */       list.add(field.getTooltipInfo());
/*     */     }
/* 326 */     list.add(InfoHelper.ITC() + StatCollector.func_74838_a("info.de.charge.txt") + ": " + InfoHelper.HITC() + Utills.formatNumber(getCharge(stack)) + " / " + Utills.formatNumber(getMaxCharge(stack)));
/*     */     
/* 328 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\DraconicHoe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */