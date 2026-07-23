/*     */ package com.brandon3055.draconicevolution.common.handler;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*     */ import com.brandon3055.draconicevolution.common.utills.ShapedOreEnergyRecipe;
/*     */ import cpw.mods.fml.common.Loader;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import com.brandon3055.brandonscore.common.tags.Tags;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraftforge.oredict.ShapedOreRecipe;
/*     */ import net.minecraftforge.oredict.ShapelessOreRecipe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CraftingHandler
/*     */ {
/*     */   public static void init() {
/*  27 */     ItemStack obsidian = Tags.Blocks.OBSIDIAN.makeStack().get();
/*  28 */     ItemStack netherStar = Tags.Items.NETHER_STAR.makeStack().get();
/*  29 */     ItemStack blazeRod = Tags.Items.BLAZE_ROD.makeStack().get();
/*  30 */     ItemStack blazePowder = Tags.Items.BLAZE_POWDER.makeStack().get();
/*  31 */     ItemStack witherSkull = Tags.Items.WITHER_SKELETON_SKULL.makeStack().get();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  36 */     ItemStack mobSoul = new ItemStack((Item)ModItems.mobSoul);
/*  37 */     ItemNBTHelper.setString(mobSoul, "Name", "Any");
/*     */ 
/*     */     
/*  40 */     addOre((Item)ModItems.draconicCore, new Object[] { "CSC", "SMS", "CSC", Character.valueOf('C'), "ingotGold", Character.valueOf('S'), "ingotDraconium", Character.valueOf('M'), "gemDiamond" });
/*  41 */     addOre((Item)ModItems.wyvernCore, new Object[] { "CSC", "SMS", "CSC", Character.valueOf('C'), "ingotDraconium", Character.valueOf('S'), ModItems.draconicCore, Character.valueOf('M'), netherStar });
/*  42 */     addOre((Item)ModItems.awakenedCore, new Object[] { "CSC", "SCS", "CSC", Character.valueOf('C'), "ingotDraconiumAwakened", Character.valueOf('S'), ModItems.wyvernCore });
/*  43 */     addOre(ModItems.wyvernEnergyCore, new Object[] { "CSC", "SMS", "CSC", Character.valueOf('C'), "ingotDraconium", Character.valueOf('S'), Tags.Blocks.REDSTONE_BLOCK.requireStack(), Character.valueOf('M'), ModItems.draconicCore });
/*  44 */     addOre(ModItems.draconicEnergyCore, new Object[] { "CSC", "SMS", "CSC", Character.valueOf('C'), "ingotDraconiumAwakened", Character.valueOf('S'), ModItems.wyvernEnergyCore, Character.valueOf('M'), ModItems.wyvernCore });
/*     */ 
/*     */     
/*  47 */     addOre(ModItems.partStabFrame, new Object[] { "III", "CD ", "III", Character.valueOf('I'), "ingotIron", Character.valueOf('C'), ModItems.wyvernCore, Character.valueOf('D'), "ingotDraconiumAwakened" });
/*  48 */     addOre(ModItems.partStabRotorInner, new Object[] { "   ", "III", "CWW", Character.valueOf('I'), "ingotDraconiumAwakened", Character.valueOf('W'), "ingotDraconium", Character.valueOf('C'), ModItems.draconicCore });
/*  49 */     addOre(ModItems.partStabRotorOuter, new Object[] { "   ", "III", "CWW", Character.valueOf('I'), "gemDiamond", Character.valueOf('W'), "ingotDraconium", Character.valueOf('C'), ModItems.draconicCore });
/*  50 */     addOre(ModItems.partStabRotorAssembly, new Object[] { " IO", "CWW", " IO", Character.valueOf('I'), ModItems.partStabRotorInner, Character.valueOf('O'), ModItems.partStabRotorOuter, Character.valueOf('C'), ModItems.wyvernCore, Character.valueOf('W'), "ingotDraconium" });
/*  51 */     addOre(ModItems.partStabRing, new Object[] { "GDG", "DCD", "GDG", Character.valueOf('G'), "ingotGold", Character.valueOf('D'), "gemDiamond", Character.valueOf('C'), ModItems.wyvernCore });
/*     */ 
/*     */     
/*  54 */     addOre(ModItems.wyvernFluxCapacitor, new Object[] { "CSC", "SMS", "CSC", Character.valueOf('C'), "ingotDraconium", Character.valueOf('S'), ModItems.wyvernEnergyCore, Character.valueOf('M'), ModItems.wyvernCore });
/*     */     
/*  56 */     addOre(ModItems.wyvernPickaxe, new Object[] { " W ", "ITI", " E ", Character.valueOf('W'), ModItems.wyvernCore, Character.valueOf('I'), "ingotDraconium", Character.valueOf('T'), Items.field_151046_w, Character.valueOf('E'), ModItems.wyvernEnergyCore });
/*  57 */     addOre(ModItems.wyvernShovel, new Object[] { " W ", "ITI", " E ", Character.valueOf('W'), ModItems.wyvernCore, Character.valueOf('I'), "ingotDraconium", Character.valueOf('T'), Items.field_151047_v, Character.valueOf('E'), ModItems.wyvernEnergyCore });
/*  58 */     addOre(ModItems.wyvernSword, new Object[] { " W ", "ITI", " E ", Character.valueOf('W'), ModItems.wyvernCore, Character.valueOf('I'), "ingotDraconium", Character.valueOf('T'), Items.field_151048_u, Character.valueOf('E'), ModItems.wyvernEnergyCore });
/*  59 */     addOre(ModItems.wyvernBow, new Object[] { " W ", "ITI", " E ", Character.valueOf('W'), ModItems.wyvernCore, Character.valueOf('I'), "ingotDraconium", Character.valueOf('T'), Items.field_151031_f, Character.valueOf('E'), ModItems.wyvernEnergyCore });
/*  60 */     addOre(new ItemStack((Item)ModItems.magnet, 1, 0), new Object[] { "RII", "  C", "RII", Character.valueOf('R'), Tags.Blocks.REDSTONE_BLOCK.requireStack(), Character.valueOf('I'), "ingotIron", Character.valueOf('C'), ModItems.teleporterMKI });
/*     */ 
/*     */     
/*  63 */     addOre((Item)ModItems.wyvernHelm, new Object[] { "IWI", "IAI", "IEI", Character.valueOf('W'), ModItems.wyvernCore, Character.valueOf('I'), "ingotDraconium", Character.valueOf('A'), Items.field_151161_ac, Character.valueOf('E'), ModItems.wyvernEnergyCore });
/*  64 */     addOre((Item)ModItems.wyvernChest, new Object[] { "IWI", "IAI", "IEI", Character.valueOf('W'), ModItems.wyvernCore, Character.valueOf('I'), "ingotDraconium", Character.valueOf('A'), Items.field_151163_ad, Character.valueOf('E'), ModItems.wyvernEnergyCore });
/*  65 */     addOre((Item)ModItems.wyvernLeggs, new Object[] { "IWI", "IAI", "IEI", Character.valueOf('W'), ModItems.wyvernCore, Character.valueOf('I'), "ingotDraconium", Character.valueOf('A'), Items.field_151173_ae, Character.valueOf('E'), ModItems.wyvernEnergyCore });
/*  66 */     addOre((Item)ModItems.wyvernBoots, new Object[] { "IWI", "IAI", "IEI", Character.valueOf('W'), ModItems.wyvernCore, Character.valueOf('I'), "ingotDraconium", Character.valueOf('A'), Items.field_151175_af, Character.valueOf('E'), ModItems.wyvernEnergyCore });
/*     */ 
/*     */     
/*  69 */     addEnergy(ModItems.draconicFluxCapacitor, new Object[] { "CMC", "SPS", "CSC", Character.valueOf('C'), "ingotDraconiumAwakened", Character.valueOf('S'), ModItems.draconicEnergyCore, Character.valueOf('M'), ModItems.awakenedCore, Character.valueOf('P'), ModItems.wyvernFluxCapacitor });
/*     */     
/*  71 */     addEnergy(ModItems.draconicPickaxe, new Object[] { " C ", "ITI", " E ", Character.valueOf('C'), ModItems.awakenedCore, Character.valueOf('I'), "ingotDraconiumAwakened", Character.valueOf('T'), ModItems.wyvernPickaxe, Character.valueOf('E'), ModItems.draconicEnergyCore });
/*  72 */     addEnergy(ModItems.draconicShovel, new Object[] { " C ", "ITI", " E ", Character.valueOf('C'), ModItems.awakenedCore, Character.valueOf('I'), "ingotDraconiumAwakened", Character.valueOf('T'), ModItems.wyvernShovel, Character.valueOf('E'), ModItems.draconicEnergyCore });
/*  73 */     addOre(ModItems.draconicAxe, new Object[] { " C ", "ITI", " E ", Character.valueOf('C'), ModItems.awakenedCore, Character.valueOf('I'), "ingotDraconiumAwakened", Character.valueOf('T'), Items.field_151056_x, Character.valueOf('E'), ModItems.draconicEnergyCore });
/*  74 */     addOre(ModItems.draconicHoe, new Object[] { " C ", "ITI", " E ", Character.valueOf('C'), ModItems.awakenedCore, Character.valueOf('I'), "ingotDraconiumAwakened", Character.valueOf('T'), Items.field_151012_L, Character.valueOf('E'), ModItems.draconicEnergyCore });
/*  75 */     addEnergy(ModItems.draconicSword, new Object[] { " C ", "ITI", " E ", Character.valueOf('C'), ModItems.awakenedCore, Character.valueOf('I'), "ingotDraconiumAwakened", Character.valueOf('T'), ModItems.wyvernSword, Character.valueOf('E'), ModItems.draconicEnergyCore });
/*  76 */     addOre(ModItems.draconicBow, new Object[] { " C ", "ITI", " E ", Character.valueOf('C'), ModItems.awakenedCore, Character.valueOf('I'), "ingotDraconiumAwakened", Character.valueOf('T'), ModItems.wyvernBow, Character.valueOf('E'), ModItems.draconicEnergyCore });
/*  77 */     addEnergy(ModItems.draconicDestructionStaff, new Object[] { "IAI", "PIS", "IWI", Character.valueOf('I'), "ingotDraconiumAwakened", Character.valueOf('A'), ModItems.awakenedCore, Character.valueOf('P'), ModItems.draconicPickaxe, Character.valueOf('S'), ModItems.draconicShovel, Character.valueOf('W'), ModItems.draconicSword });
/*     */ 
/*     */     
/*  80 */     addEnergy((Item)ModItems.draconicHelm, new Object[] { "IWI", "IAI", "IEI", Character.valueOf('W'), ModItems.awakenedCore, Character.valueOf('I'), "ingotDraconiumAwakened", Character.valueOf('A'), ModItems.wyvernHelm, Character.valueOf('E'), ModItems.draconicEnergyCore });
/*  81 */     addEnergy((Item)ModItems.draconicChest, new Object[] { "IWI", "IAI", "IEI", Character.valueOf('W'), ModItems.awakenedCore, Character.valueOf('I'), "ingotDraconiumAwakened", Character.valueOf('A'), ModItems.wyvernChest, Character.valueOf('E'), ModItems.draconicEnergyCore });
/*  82 */     addEnergy((Item)ModItems.draconicLeggs, new Object[] { "IWI", "IAI", "IEI", Character.valueOf('W'), ModItems.awakenedCore, Character.valueOf('I'), "ingotDraconiumAwakened", Character.valueOf('A'), ModItems.wyvernLeggs, Character.valueOf('E'), ModItems.draconicEnergyCore });
/*  83 */     addEnergy((Item)ModItems.draconicBoots, new Object[] { "IWI", "IAI", "IEI", Character.valueOf('W'), ModItems.awakenedCore, Character.valueOf('I'), "ingotDraconiumAwakened", Character.valueOf('A'), ModItems.wyvernBoots, Character.valueOf('E'), ModItems.draconicEnergyCore });
/*     */ 
/*     */     
/*  86 */     add((Block)ModBlocks.particleGenerator, new Object[] { "RBR", "BCB", "RBR", Character.valueOf('R'), Tags.Blocks.REDSTONE_BLOCK.requireStack(), Character.valueOf('B'), blazeRod, Character.valueOf('C'), ModItems.draconicCore });
/*  87 */     addOre(new ItemStack((Block)ModBlocks.infusedObsidian, 4), new Object[] { "BOB", "ODO", "BOB", Character.valueOf('B'), blazePowder, Character.valueOf('O'), obsidian, Character.valueOf('D'), "dustDraconium" });
/*     */ 
/*     */     
/*  90 */     addOre((Block)ModBlocks.potentiometer, new Object[] { "ITI", "QCQ", "IRI", Character.valueOf('I'), "ingotIron", Character.valueOf('T'), Blocks.field_150429_aA, Character.valueOf('Q'), "gemQuartz", Character.valueOf('C'), Items.field_151132_bS, Character.valueOf('R'), Tags.Blocks.REDSTONE_BLOCK.requireStack() });
/*  91 */     addOre((Block)ModBlocks.teleporterStand, new Object[] { " P ", " S ", "HBH", Character.valueOf('P'), Blocks.field_150456_au, Character.valueOf('S'), "stone", Character.valueOf('H'), Tags.Blocks.STONE_SLAB.requireStack(), Character.valueOf('B'), blazePowder });
/*  92 */     addOre((Block)ModBlocks.dislocatorReceptacle, new Object[] { "ICI", " O ", "ISI", Character.valueOf('I'), "ingotIron", Character.valueOf('C'), ModItems.draconicCore, Character.valueOf('O'), ModBlocks.infusedObsidian, Character.valueOf('S'), ModBlocks.teleporterStand });
/*  93 */     addOre((Block)ModBlocks.upgradeModifier, new Object[] { "   ", "DCD", "III", Character.valueOf('I'), "ingotIron", Character.valueOf('D'), "ingotDraconium", Character.valueOf('C'), ModItems.draconicCore });
/*     */ 
/*     */     
/*  96 */     addOre((Block)ModBlocks.energyStorageCore, new Object[] { "CCC", "SMS", "CCC", Character.valueOf('C'), "ingotDraconium", Character.valueOf('S'), ModItems.wyvernEnergyCore, Character.valueOf('M'), ModItems.wyvernCore });
/*  97 */     addOre((Block)ModBlocks.playerDetectorAdvanced, new Object[] { "ISI", "EDE", "ICI", Character.valueOf('I'), "ingotDraconium", Character.valueOf('E'), Tags.Items.ENDER_EYE.requireStack(), Character.valueOf('S'), witherSkull, Character.valueOf('C'), Tags.Items.ENDER_PEARL.requireStack(), Character.valueOf('D'), ModBlocks.playerDetector });
/*  98 */     addOre((Block)ModBlocks.energyInfuser, new Object[] { "IPI", "CEC", "ICI", Character.valueOf('I'), "ingotDraconium", Character.valueOf('P'), ModBlocks.particleGenerator, Character.valueOf('C'), ModItems.draconicCore, Character.valueOf('E'), Blocks.field_150381_bn });
/*  99 */     addOre(getStack((Block)ModBlocks.energyPylon, 2, 0), new Object[] { "IEI", "MCM", "IDI", Character.valueOf('I'), "ingotDraconium", Character.valueOf('E'), Tags.Items.ENDER_EYE.requireStack(), Character.valueOf('C'), ModItems.draconicCore, Character.valueOf('D'), "gemDiamond", Character.valueOf('M'), "gemEmerald" });
/* 100 */     addOre(getStack((Block)ModBlocks.grinder, 1, 3), new Object[] { "IXI", "DCD", "IFI", Character.valueOf('I'), "ingotIron", Character.valueOf('X'), "ingotDraconium", Character.valueOf('D'), Items.field_151048_u, Character.valueOf('C'), ModItems.draconicCore, Character.valueOf('F'), Blocks.field_150460_al });
/* 101 */     addOre((Block)ModBlocks.playerDetector, new Object[] { "ITI", "CEC", "IDI", Character.valueOf('I'), "ingotIron", Character.valueOf('E'), Tags.Items.ENDER_EYE.requireStack(), Character.valueOf('T'), Blocks.field_150429_aA, Character.valueOf('C'), Items.field_151132_bS, Character.valueOf('D'), ModItems.draconicCore });
/* 102 */     addOre((Block)ModBlocks.dissEnchanter, new Object[] { "PIP", "ETE", "CBC", Character.valueOf('P'), Tags.Items.ENDER_EYE.requireStack(), Character.valueOf('I'), Items.field_151134_bR, Character.valueOf('E'), "gemEmerald", Character.valueOf('T'), Blocks.field_150381_bn, Character.valueOf('C'), ModItems.draconicCore, Character.valueOf('B'), Items.field_151122_aG });
/*     */     
/* 104 */     if (Loader.isModLoaded("ThermalDynamics")) {
/* 105 */       addOre(getStack((Block)ModBlocks.flowGate, 1, 0), new Object[] { "ICI", "RSR", "ICI", Character.valueOf('I'), "ingotDraconium", Character.valueOf('C'), getStack((Item)ModItems.draconicCore, 1, 0), Character.valueOf('R'), Items.field_151132_bS, Character.valueOf('S'), new ItemStack(GameRegistry.findBlock("ThermalDynamics", "ThermalDynamics_16"), 1, 2) });
/*     */     } else {
/* 107 */       addOre(getStack((Block)ModBlocks.flowGate, 1, 0), new Object[] { "ICI", "RSR", "ICI", Character.valueOf('I'), "ingotDraconium", Character.valueOf('C'), getStack((Item)ModItems.draconicCore, 1, 0), Character.valueOf('R'), Items.field_151132_bS, Character.valueOf('S'), Items.field_151133_ar });
/*     */     } 
/*     */ 
/*     */     
/* 111 */     addOre((Item)ModItems.teleporterMKII, new Object[] { "IEI", "ETE", "IWI", Character.valueOf('I'), "ingotDraconium", Character.valueOf('E'), Tags.Items.ENDER_PEARL.requireStack(), Character.valueOf('T'), ModItems.teleporterMKI, Character.valueOf('W'), ModItems.wyvernCore });
/* 112 */     addOre((Item)ModItems.teleporterMKI, new Object[] { "CSC", "SMS", "CSC", Character.valueOf('C'), blazePowder, Character.valueOf('S'), "dustDraconium", Character.valueOf('M'), Tags.Items.ENDER_EYE.requireStack() });
/* 113 */     addOre(getStack((Item)ModItems.safetyMatch, 1, 1000), new Object[] { " O ", " S ", "   ", Character.valueOf('O'), "dyeOrange", Character.valueOf('S'), "stickWood" });
/* 114 */     add((Item)ModItems.safetyMatch, new Object[] { "MMM", "MMM", "MMM", Character.valueOf('M'), getStack((Item)ModItems.safetyMatch, 1, 1000) });
/* 115 */     addOre((Item)ModItems.wrench, new Object[] { " ID", " RI", "C  ", Character.valueOf('I'), "ingotDraconium", Character.valueOf('D'), "gemDiamond", Character.valueOf('R'), blazeRod, Character.valueOf('C'), ModItems.draconicCore });
/*     */ 
/*     */     
/* 118 */     addOre((Item)ModItems.infoTablet, new Object[] { "SSS", "SDS", "SSS", Character.valueOf('S'), "stone", Character.valueOf('D'), "dustDraconium" });
/* 119 */     addShaplessOre(getStack((Item)ModItems.enderArrow, 1, 0), new Object[] { Items.field_151032_g, Tags.Items.ENDER_PEARL.requireStack() });
/* 120 */     addShaplessOre(Tags.Blocks.DIRT.requireStack(), new Object[] { Tags.Blocks.SAND.requireStack(), Tags.Items.ROTTEN_FLESH.requireStack(), "treeSapling", "treeSapling", "treeSapling" });
/* 121 */     addShaplessOre(Tags.Blocks.DIRT.requireStack(), new Object[] { Tags.Blocks.SAND.requireStack(), Tags.Items.ROTTEN_FLESH.requireStack(), "treeLeaves", "treeLeaves", "treeLeaves" });
/*     */ 
/*     */ 
/*     */     
/* 125 */     addOre(getStack((Block)ModBlocks.xRayBlock, 4, 0), new Object[] { "SGS", "GDG", "SGS", Character.valueOf('S'), netherStar, Character.valueOf('G'), "blockGlassColorless", Character.valueOf('D'), "gemDiamond" });
/*     */   }
/*     */ 
/*     */   
/*     */   private static ItemStack getChest() {
/* 130 */     if (Loader.isModLoaded("IronChest")) {
/* 131 */       LogHelper.info("Adding Iron Chests Integration");
/* 132 */       return new ItemStack(GameRegistry.findBlock("IronChest", "BlockIronChest"), 1, 6);
/*     */     } 
/*     */     
/* 135 */     LogHelper.info("Iron Chests was not detected! using fallback chest recipe");
/* 136 */     return new ItemStack((Block)Blocks.field_150486_ae);
/*     */   }
/*     */   
/*     */   private static void addOre(Block result, Object... recipe) {
/* 140 */     addOre(new ItemStack(result), recipe);
/*     */   }
/*     */   
/*     */   private static void addOre(Item result, Object... recipe) {
/* 144 */     addOre(new ItemStack(result), recipe);
/*     */   }
/*     */   
/*     */   private static void addOre(ItemStack result, Object... recipe) {
/* 148 */     if (result == null)
/*     */       return; 
/* 150 */     for (Object o : recipe) {
/* 151 */       if (o == null)
/*     */         return; 
/* 153 */       String s = (o instanceof Item) ? ((Item)o).func_77658_a() : ((o instanceof Block) ? ((Block)o).func_149739_a() : null);
/* 154 */       if (s != null && ConfigHandler.disabledNamesList.contains(s)) {
/*     */         return;
/*     */       }
/*     */     } 
/* 158 */     GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(result, recipe));
/*     */   }
/*     */   
/*     */   private static void addShaplessOre(ItemStack result, Object... recipe) {
/* 162 */     if (result == null)
/*     */       return; 
/* 164 */     for (Object o : recipe) {
/* 165 */       if (o == null)
/*     */         return; 
/* 167 */       String s = (o instanceof Item) ? ((Item)o).func_77658_a() : ((o instanceof Block) ? ((Block)o).func_149739_a() : null);
/* 168 */       if (s != null && ConfigHandler.disabledNamesList.contains(s)) {
/*     */         return;
/*     */       }
/*     */     } 
/* 172 */     GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(result, recipe));
/*     */   }
/*     */   
/*     */   private static void add(Block result, Object... recipe) {
/* 176 */     add(new ItemStack(result), recipe);
/*     */   }
/*     */   
/*     */   private static void add(Item result, Object... recipe) {
/* 180 */     add(new ItemStack(result), recipe);
/*     */   }
/*     */   
/*     */   private static void add(ItemStack result, Object... recipe) {
/* 184 */     if (result == null)
/*     */       return; 
/* 186 */     for (Object o : recipe) {
/* 187 */       if (o == null)
/*     */         return; 
/* 189 */       String s = (o instanceof Item) ? ((Item)o).func_77658_a() : ((o instanceof Block) ? ((Block)o).func_149739_a() : null);
/* 190 */       if (s != null && ConfigHandler.disabledNamesList.contains(s)) {
/*     */         return;
/*     */       }
/*     */     } 
/* 194 */     GameRegistry.addRecipe(result, recipe);
/*     */   }
/*     */   
/*     */   private static void addEnergy(Block result, Object... recipe) {
/* 198 */     addEnergy(new ItemStack(result), recipe);
/*     */   }
/*     */   
/*     */   private static void addEnergy(Item result, Object... recipe) {
/* 202 */     addEnergy(new ItemStack(result), recipe);
/*     */   }
/*     */   
/*     */   private static void addEnergy(ItemStack result, Object... recipe) {
/* 206 */     if (result == null)
/*     */       return; 
/* 208 */     for (Object o : recipe) {
/* 209 */       if (o == null)
/*     */         return; 
/* 211 */       String s = (o instanceof Item) ? ((Item)o).func_77658_a() : ((o instanceof Block) ? ((Block)o).func_149739_a() : null);
/* 212 */       if (s != null && ConfigHandler.disabledNamesList.contains(s)) {
/*     */         return;
/*     */       }
/*     */     } 
/* 216 */     GameRegistry.addRecipe((IRecipe)new ShapedOreEnergyRecipe(result, recipe));
/*     */   }
/*     */   
/*     */   private static ItemStack getStack(Block block, int count, int meta) {
/* 220 */     return ModBlocks.isEnabled(block) ? new ItemStack(block, count, meta) : null;
/*     */   }
/*     */   
/*     */   private static ItemStack getStack(Item item, int count, int meta) {
/* 224 */     return ModItems.isEnabled(item) ? new ItemStack(item, count, meta) : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\handler\CraftingHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */