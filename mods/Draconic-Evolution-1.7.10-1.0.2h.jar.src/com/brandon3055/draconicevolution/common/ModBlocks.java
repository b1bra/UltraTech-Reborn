/*    */ package com.brandon3055.draconicevolution.common;
/*    */ import com.brandon3055.draconicevolution.common.blocks.BlockDE;
/*    */ import com.brandon3055.draconicevolution.common.blocks.CustomSpawner;
/*    */ import com.brandon3055.draconicevolution.common.blocks.InfusedObsidian;
/*    */ import com.brandon3055.draconicevolution.common.blocks.ParticleGenerator;
/*    */ import com.brandon3055.draconicevolution.common.blocks.PlacedItem;
/*    */ import com.brandon3055.draconicevolution.common.blocks.Potentiometer;
/*    */ import com.brandon3055.draconicevolution.common.blocks.TeleporterStand;
/*    */ import com.brandon3055.draconicevolution.common.blocks.XRayBlock;
/*    */ import com.brandon3055.draconicevolution.common.blocks.machine.DissEnchanter;
/*    */ import com.brandon3055.draconicevolution.common.blocks.machine.FlowGate;
/*    */ import com.brandon3055.draconicevolution.common.blocks.machine.Grinder;
/*    */ import com.brandon3055.draconicevolution.common.blocks.machine.PlayerDetector;
/*    */ import com.brandon3055.draconicevolution.common.blocks.machine.UpgradeModifier;
/*    */ import com.brandon3055.draconicevolution.common.blocks.multiblock.DislocatorReceptacle;
/*    */ import com.brandon3055.draconicevolution.common.blocks.multiblock.EnergyPylon;
/*    */ import com.brandon3055.draconicevolution.common.blocks.multiblock.EnergyStorageCore;
/*    */ import cpw.mods.fml.common.registry.GameRegistry;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ 
/*    */ @ObjectHolder("DraconicEvolution")
/*    */ public class ModBlocks {
/*    */   public static BlockDE xRayBlock;
/*    */   public static BlockDE grinder;
/*    */   public static BlockDE potentiometer;
/*    */   public static BlockDE particleGenerator;
/*    */   public static BlockDE playerDetector;
/*    */   public static BlockDE playerDetectorAdvanced;
/*    */   public static BlockDE energyInfuser;
/*    */   public static BlockDE customSpawner;
/*    */   public static BlockDE energyStorageCore;
/*    */   public static BlockDE invisibleMultiblock;
/*    */   
/*    */   public static void init() {
/* 36 */     xRayBlock = (BlockDE)new XRayBlock();
/* 37 */     grinder = (BlockDE)new Grinder();
/* 38 */     potentiometer = (BlockDE)new Potentiometer();
/* 39 */     particleGenerator = (BlockDE)new ParticleGenerator();
/* 40 */     playerDetector = (BlockDE)new PlayerDetector();
/* 41 */     playerDetectorAdvanced = (BlockDE)new PlayerDetectorAdvanced();
/* 42 */     energyInfuser = (BlockDE)new EnergyInfuser();
/* 43 */     customSpawner = (BlockDE)new CustomSpawner();
/* 44 */     energyStorageCore = (BlockDE)new EnergyStorageCore();
/* 45 */     invisibleMultiblock = (BlockDE)new InvisibleMultiblock();
/* 46 */     energyPylon = (BlockDE)new EnergyPylon();
/* 47 */     placedItem = (BlockDE)new PlacedItem();
/* 48 */     safetyFlame = (Block)new SafetyFlame();
/* 49 */     dissEnchanter = (BlockDE)new DissEnchanter();
/* 50 */     teleporterStand = (BlockDE)new TeleporterStand();
/* 51 */     infusedObsidian = (BlockDE)new InfusedObsidian();
/* 52 */     dislocatorReceptacle = (BlockDE)new DislocatorReceptacle();
/* 53 */     portal = (BlockDE)new Portal();
/* 54 */     flowGate = (BlockDE)new FlowGate();
/* 55 */     upgradeModifier = (BlockDE)new UpgradeModifier();
/*    */   }
/*    */   public static BlockDE energyPylon; public static BlockDE placedItem; public static BlockDE dissEnchanter; public static BlockDE teleporterStand; public static BlockDE infusedObsidian; public static BlockDE dislocatorReceptacle; public static BlockDE portal; public static BlockDE flowGate; public static BlockDE upgradeModifier; public static Block safetyFlame;
/*    */   public static void register(BlockDE block) {
/* 59 */     String name = block.getUnwrappedUnlocalizedName(block.func_149739_a());
/* 60 */     if (isEnabled((Block)block)) GameRegistry.registerBlock((Block)block, name.substring(name.indexOf(':') + 1)); 
/*    */   }
/*    */   
/*    */   public static void register(BlockDE block, Class<? extends ItemBlock> item) {
/* 64 */     String name = block.getUnwrappedUnlocalizedName(block.func_149739_a());
/* 65 */     if (isEnabled((Block)block)) GameRegistry.registerBlock((Block)block, item, name.substring(name.indexOf(':') + 1)); 
/*    */   }
/*    */   
/*    */   public static void registerOther(Block block) {
/* 69 */     String name = block.func_149739_a().substring(block.func_149739_a().indexOf('.') + 1);
/* 70 */     if (isEnabled(block)) GameRegistry.registerBlock(block, name.substring(name.indexOf(':') + 1)); 
/*    */   }
/*    */   
/*    */   public static boolean isEnabled(Block block) {
/* 74 */     return !ConfigHandler.disabledNamesList.contains(block.func_149739_a());
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\ModBlocks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */