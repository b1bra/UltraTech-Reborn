/*     */ package com.brandon3055.draconicevolution.common.blocks;
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.CustomSpawnerBaseLogic;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileCustomSpawner;
/*     */ import com.brandon3055.draconicevolution.common.utills.IHudDisplayBlock;
/*     */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*     */ import com.gamerforea.draconicevolution.EventConfig;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.loliland.mctags.api.Tags;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class CustomSpawner extends BlockDE implements IHudDisplayBlock {
/*     */   public CustomSpawner() {
/*  41 */     func_149663_c("customSpawner");
/*  42 */     func_149647_a(DraconicEvolution.tabBlocksItems);
/*     */ 
/*     */ 
/*     */     
/*  46 */     func_149711_c(EventConfig.spawnerHardness);
/*     */ 
/*     */     
/*  49 */     func_149752_b(2000.0F);
/*     */ 
/*     */ 
/*     */     
/*  53 */     setHarvestLevel("pickaxe", EventConfig.spawnerHarvestLevel);
/*     */ 
/*     */     
/*  56 */     ModBlocks.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
/*  61 */     TileEntity tile = world.func_147438_o(x, y, z);
/*  62 */     TileCustomSpawner spawner = (tile instanceof TileCustomSpawner) ? (TileCustomSpawner)tile : null;
/*  63 */     if (spawner != null) {
/*  64 */       ItemStack item = player.func_70694_bm();
/*  65 */       if (item != null && item.func_77973_b().equals(ModItems.mobSoul)) {
/*  66 */         String name = ItemNBTHelper.getString(item, "Name", "Pig");
/*  67 */         if (ConfigHandler.spawnerListType != Arrays.<String>asList(ConfigHandler.spawnerList).contains(name)) {
/*  68 */           if (!world.field_72995_K)
/*  69 */             player.func_146105_b((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "[Error] soul disabled in config!")); 
/*  70 */           return false;
/*     */         } 
/*     */         
/*  73 */         if (name.equals((spawner.getBaseLogic()).entityName))
/*  74 */           return false; 
/*  75 */         (spawner.getBaseLogic()).entityName = name;
/*  76 */         spawner.isSetToSpawn = true;
/*  77 */         (spawner.getBaseLogic()).skeletonType = ItemNBTHelper.getInteger(item, "SkeletonType", 0);
/*  78 */         world.func_147471_g(x, y, z);
/*  79 */         item.func_77979_a(1);
/*  80 */         return true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  85 */       if (item != null && Tags.Items.NETHER_STAR.is(item) && (spawner.getBaseLogic()).requiresPlayer) {
/*     */ 
/*     */         
/*  88 */         (spawner.getBaseLogic()).requiresPlayer = false;
/*  89 */         world.func_147471_g(x, y, z);
/*  90 */         item.func_77979_a(1);
/*  91 */         return true;
/*     */       } 
/*     */       
/*  94 */       if (item != null && item.func_77973_b().equals(ModItems.wyvernCore) && (spawner.getBaseLogic()).spawnSpeed == 1) {
/*  95 */         spawner.getBaseLogic().setSpawnRate(2);
/*  96 */         world.func_147471_g(x, y, z);
/*  97 */         item.func_77979_a(1);
/*  98 */         return true;
/*     */       } 
/* 100 */       if (item != null && item.func_77973_b().equals(ModItems.awakenedCore) && (spawner.getBaseLogic()).spawnSpeed == 2) {
/* 101 */         spawner.getBaseLogic().setSpawnRate(3);
/* 102 */         world.func_147471_g(x, y, z);
/* 103 */         item.func_77979_a(1);
/* 104 */         return true;
/*     */       } 
/* 106 */       if (item != null && Tags.Items.EMPOWERED_GOLDEN_APPLE.is(item) && !(spawner.getBaseLogic()).ignoreSpawnRequirements) {
/* 107 */         (spawner.getBaseLogic()).ignoreSpawnRequirements = true;
/* 108 */         world.func_147471_g(x, y, z);
/* 109 */         item.func_77979_a(1);
/* 110 */         return true;
/*     */       } 
/* 112 */       if (world.field_72995_K && !player.func_70093_af()) {
/* 113 */         player.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "#################################"));
/* 114 */         player.func_145747_a((new ChatComponentTranslation("msg.spawnerInfo1.txt", new Object[0])).func_150258_a(": " + EnumChatFormatting.DARK_AQUA + (spawner.getBaseLogic()).entityName));
/* 115 */         player.func_145747_a((new ChatComponentTranslation("msg.spawnerInfo2.txt", new Object[0])).func_150258_a(": " + EnumChatFormatting.DARK_AQUA + (spawner.getBaseLogic()).requiresPlayer));
/* 116 */         player.func_145747_a((new ChatComponentTranslation("msg.spawnerInfo3.txt", new Object[0])).func_150258_a(": " + EnumChatFormatting.DARK_AQUA + (spawner.getBaseLogic()).ignoreSpawnRequirements));
/* 117 */         player.func_145747_a((new ChatComponentTranslation("msg.spawnerInfo4.txt", new Object[0])).func_150258_a(": " + EnumChatFormatting.DARK_AQUA + (spawner.getBaseLogic()).spawnSpeed));
/* 118 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.spawnerInfo5.txt", new Object[0]));
/* 119 */         player.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "#################################"));
/* 120 */       } else if (world.field_72995_K && player.func_70093_af()) {
/* 121 */         player.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "#################################"));
/* 122 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.spawnerInfo6.txt", new Object[0]));
/* 123 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.spawnerInfo7.txt", new Object[0]));
/* 124 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.spawnerInfo8.txt", new Object[0]));
/* 125 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.spawnerInfo9.txt", new Object[0]));
/* 126 */         player.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "#################################"));
/*     */       } 
/* 128 */       return true;
/*     */     } 
/* 130 */     LogHelper.error("Invalid or nonexistent TileEntity");
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTileEntity(int metadata) {
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity createTileEntity(World world, int metadata) {
/* 141 */     return (TileEntity)new TileCustomSpawner();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHarvestLevel(int metadata) {
/* 158 */     return EventConfig.spawnerHarvestLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/* 164 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item func_149650_a(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
/* 169 */     return Item.func_150898_a(ModBlocks.customSpawner);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149745_a(Random p_149745_1_) {
/* 174 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister iconRegister) {
/* 180 */     this.field_149761_L = iconRegister.func_94245_a("mob_spawner");
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int x, int y, int z, Block p_149695_5_) {
/* 185 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 186 */     TileCustomSpawner spawner = (tile instanceof TileCustomSpawner) ? (TileCustomSpawner)tile : null;
/* 187 */     if (spawner != null) {
/* 188 */       CustomSpawnerBaseLogic logic = spawner.getBaseLogic();
/* 189 */       logic.powered = world.func_72864_z(x, y, z);
/* 190 */       if (logic.powered != logic.ltPowered) {
/* 191 */         logic.ltPowered = logic.powered;
/* 192 */         world.func_147471_g(x, y, z);
/* 193 */         logic.setSpawnRate(logic.spawnSpeed);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
/* 200 */     return new ItemStack(ModBlocks.customSpawner);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149749_a(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
/* 205 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 206 */     TileCustomSpawner spawner = (tile instanceof TileCustomSpawner) ? (TileCustomSpawner)tile : null;
/* 207 */     if (spawner != null && !world.field_72995_K) {
/* 208 */       float multiplyer = 0.05F;
/*     */       
/* 210 */       if ((spawner.getBaseLogic()).ignoreSpawnRequirements) {
/* 211 */         EntityItem item = new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, Tags.Items.EMPOWERED_GOLDEN_APPLE.requireStack());
/* 212 */         item.field_70159_w = ((-0.5F + world.field_73012_v.nextFloat()) * multiplyer);
/* 213 */         item.field_70181_x = ((4.0F + world.field_73012_v.nextFloat()) * multiplyer);
/* 214 */         item.field_70179_y = ((-0.5F + world.field_73012_v.nextFloat()) * multiplyer);
/* 215 */         world.func_72838_d((Entity)item);
/*     */       } 
/* 217 */       if ((spawner.getBaseLogic()).spawnSpeed > 1) {
/* 218 */         EntityItem item = new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, new ItemStack((Item)ModItems.wyvernCore));
/* 219 */         item.field_70159_w = ((-0.5F + world.field_73012_v.nextFloat()) * multiplyer);
/* 220 */         item.field_70181_x = ((4.0F + world.field_73012_v.nextFloat()) * multiplyer);
/* 221 */         item.field_70179_y = ((-0.5F + world.field_73012_v.nextFloat()) * multiplyer);
/* 222 */         world.func_72838_d((Entity)item);
/*     */       } 
/* 224 */       if ((spawner.getBaseLogic()).spawnSpeed > 2) {
/* 225 */         EntityItem item = new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, new ItemStack((Item)ModItems.awakenedCore));
/* 226 */         item.field_70159_w = ((-0.5F + world.field_73012_v.nextFloat()) * multiplyer);
/* 227 */         item.field_70181_x = ((4.0F + world.field_73012_v.nextFloat()) * multiplyer);
/* 228 */         item.field_70179_y = ((-0.5F + world.field_73012_v.nextFloat()) * multiplyer);
/* 229 */         world.func_72838_d((Entity)item);
/*     */       } 
/* 231 */       if (!(spawner.getBaseLogic()).requiresPlayer) {
/*     */ 
/*     */ 
/*     */         
/* 235 */         EntityItem item = new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, Tags.Items.NETHER_STAR.makeStack().get());
/*     */ 
/*     */         
/* 238 */         item.field_70159_w = ((-0.5F + world.field_73012_v.nextFloat()) * multiplyer);
/* 239 */         item.field_70181_x = ((4.0F + world.field_73012_v.nextFloat()) * multiplyer);
/* 240 */         item.field_70179_y = ((-0.5F + world.field_73012_v.nextFloat()) * multiplyer);
/* 241 */         world.func_72838_d((Entity)item);
/*     */       } 
/*     */     } 
/* 244 */     super.func_149749_a(world, x, y, z, p_149749_5_, p_149749_6_);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getDisplayData(World world, int x, int y, int z) {
/* 249 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 250 */     TileCustomSpawner spawner = (tile instanceof TileCustomSpawner) ? (TileCustomSpawner)tile : null;
/* 251 */     List<String> list = new ArrayList<>();
/* 252 */     if (spawner != null) {
/* 253 */       list.add(InfoHelper.HITC() + func_149732_F());
/* 254 */       if (world.field_72995_K && !(Minecraft.func_71410_x()).field_71439_g.func_70093_af()) {
/* 255 */         list.add(StatCollector.func_74838_a("msg.spawnerInfo1.txt") + ": " + EnumChatFormatting.DARK_AQUA + (spawner.getBaseLogic()).entityName);
/* 256 */         list.add(StatCollector.func_74838_a("msg.spawnerInfo2.txt") + ": " + EnumChatFormatting.DARK_AQUA + (spawner.getBaseLogic()).requiresPlayer);
/* 257 */         list.add(StatCollector.func_74838_a("msg.spawnerInfo3.txt") + ": " + EnumChatFormatting.DARK_AQUA + (spawner.getBaseLogic()).ignoreSpawnRequirements);
/* 258 */         list.add(StatCollector.func_74838_a("msg.spawnerInfo4.txt") + ": " + EnumChatFormatting.DARK_AQUA + (spawner.getBaseLogic()).spawnSpeed);
/* 259 */         list.add(StatCollector.func_74838_a("msg.spawnerInfo5.txt"));
/* 260 */       } else if (world.field_72995_K && (Minecraft.func_71410_x()).field_71439_g.func_70093_af()) {
/* 261 */         list.add(StatCollector.func_74838_a("msg.spawnerInfo6.txt"));
/* 262 */         list.add(StatCollector.func_74838_a("msg.spawnerInfo7.txt"));
/* 263 */         list.add(StatCollector.func_74838_a("msg.spawnerInfo8.txt"));
/* 264 */         list.add(StatCollector.func_74838_a("msg.spawnerInfo9.txt"));
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\CustomSpawner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */