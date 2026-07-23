/*    */ package com.gamerforea.draconicevolution;
/*    */ 
/*    */ import com.gamerforea.eventhelper.config.Config;
/*    */ import com.gamerforea.eventhelper.config.ConfigUtils;
/*    */ import com.gamerforea.eventhelper.config.NetworkOption;
/*    */ import com.gamerforea.eventhelper.config.NumberRange.Int;
/*    */ import com.gamerforea.eventhelper.config.Option;
/*    */ 
/*    */ @Config(name = "DraconicEvolution", autoLoad = true)
/*    */ public final class EventConfig
/*    */ {
/*    */   private static final String CATEGORY_OTHER = "other";
/*    */   @Option(category = "other", comment = "Максимальный радиус разрушения посохом")
/*    */   @Int(min = 0)
/*    */   @NetworkOption
/* 16 */   public static int staffMaxRange = 11; private static final String CATEGORY_BLOCK = "block";
/*    */   private static final String CATEGORY_BLOCK_SPAWNER = "block.spawner";
/*    */   @Option(category = "other", comment = "Максимальный радиус разрушения киркой")
/*    */   @Int(min = 0)
/*    */   @NetworkOption
/* 21 */   public static int pickaxeMaxRange = 11;
/*    */   
/*    */   @Option(category = "other", comment = "Включить улучшение Dig Speed")
/*    */   @NetworkOption
/*    */   public static boolean enableDigSpeedUpgrade = true;
/*    */   
/*    */   @Option(category = "other", comment = "Синхронизировать AoE-разрушение блоков с клиентом")
/*    */   @NetworkOption
/*    */   public static boolean updateGhostBlocks = true;
/*    */   
/*    */   @Option(category = "block.spawner", name = "harvestLevel", reloadable = false)
/*    */   @Int(min = 0)
/* 33 */   public static int spawnerHarvestLevel = 2;
/*    */   
/*    */   @Option(category = "block.spawner", name = "hardness", reloadable = false)
/* 36 */   public static float spawnerHardness = 10.0F;
/*    */   
/*    */   @Option(category = "block.spawner", name = "mobRotation", reloadable = false)
/*    */   public static boolean spawnerMobRotation = true;
/*    */   
/*    */   static {
/* 42 */     ConfigUtils.readConfig(EventConfig.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\gamerforea\draconicevolution\EventConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */