/*     */ package com.brandon3055.draconicevolution.common.handler;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraftforge.common.config.Configuration;
/*     */ 
/*     */ public class ConfigHandler {
/*     */   public static Configuration config;
/*     */   public static int teleporterUsesPerPearl;
/*     */   public static int soulDropChance;
/*     */   public static int passiveSoulDropChance;
/*     */   public static boolean pigmenBloodRage;
/*     */   public static boolean bowBlockDamage;
/*     */   public static boolean showUnlocalizedNames;
/*     */   public static boolean disableLore;
/*     */   public static boolean invertDPDSB;
/*     */   public static int[] hudSettings;
/*     */   private static String[] disabledBlocksItems;
/*  24 */   public static List<String> disabledNamesList = new ArrayList<>();
/*     */   public static double maxPlayerSpeed;
/*     */   private static int[] speedDimBlackList;
/*  27 */   public static List<Integer> speedLimitDimList = new ArrayList<>();
/*     */   public static boolean speedLimitops;
/*     */   public static boolean rapidlyDespawnMinedItems;
/*     */   public static boolean useOldArmorModel;
/*     */   public static boolean useOriginal3DArmorModel;
/*     */   public static boolean useOldD2DToolTextures;
/*     */   public static boolean disableLog;
/*     */   public static boolean enableFlight;
/*     */   private static String[] itemDislocatorBlacklist;
/*  36 */   public static Map<String, Integer> itemDislocatorBlacklistMap = new HashMap<>();
/*     */ 
/*     */   
/*     */   public static String[] spawnerList;
/*     */ 
/*     */   
/*     */   public static boolean spawnerListType;
/*     */ 
/*     */   
/*     */   public static int reaperEnchantID;
/*     */ 
/*     */   
/*  48 */   private static String[] defaultSpawnerList = new String[] { "ExampleMob1", "ExampleMob2", "ExampleMob3 (these examples can be deleted)" };
/*     */   
/*     */   public static void init(File confFile) {
/*  51 */     if (config == null) {
/*  52 */       config = new Configuration(confFile);
/*  53 */       config.load();
/*  54 */       syncConfig();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void syncConfig() {
/*     */     try {
/*  64 */       teleporterUsesPerPearl = config.get("general", "Teleporter Uses PerPearl", 1, "Charm of Dislocation uses per Ender pearl").getInt(1);
/*  65 */       bowBlockDamage = config.get("general", "Bow Block Damage", true, "Dose Draconic bow explosion damage blocks").getBoolean(true);
/*  66 */       showUnlocalizedNames = config.get("general", "Show Unlocalized Names", false, "If set to true the unlocalized name of every block and item will be displayed in its tool tip").getBoolean(false);
/*  67 */       soulDropChance = config.get("general", "soulDropChance", 1000, "Mobs have a 1 in this number chance to drop a soul", 1, 2147483647).getInt(1000);
/*  68 */       passiveSoulDropChance = config.get("general", "passiveSoulDropChance", 800, "Passive (Animals) Mobs have a 1 in this number chance to drop a soul", 1, 2147483647).getInt(800);
/*  69 */       pigmenBloodRage = config.get("general", "Pigmen Blood Rage", true, "Is Pigmen blood rage active").getBoolean(true);
/*  70 */       disableLore = config.get("general", "Disable Item Lore", false, "Set to true to disable all item lore").getBoolean(false);
/*  71 */       invertDPDSB = config.get("general", "InvertDPDSB", false, "Invert Dislocator Pedestal display name shift behavior").getBoolean(false);
/*     */ 
/*     */       
/*  74 */       hudSettings = config.get("Gui Stuff", "HUD Settings", new int[] { 996, 825, 69, 907, 90, 100, 3, 0, 1, 1, 1, 1 }, "Used to store the position of the armor ant tool HUD's. This should not be modified", -2147483648, 2147483647, true, 12).getIntList();
/*  75 */       disabledBlocksItems = config.getStringList("Disabled Blocks & Items", "general", new String[0], "add the unlocalized name of a block or item to this list to disable it");
/*  76 */       maxPlayerSpeed = config.get("general", "Player speed cap", 10.0D, "Limits the max speed of players. Recommend between 0.5 - 1.0 for servers").getDouble(10.0D);
/*  77 */       speedDimBlackList = config.get("general", "Speed limit Dim black lack list", new int[] { 1 }, "A list of dimensions the speed limit will not effect (speed limit is not so really required in the end)").getIntList();
/*  78 */       speedLimitops = config.get("general", "Speed limit effects ops", false, "Dose the speed limit effect ops").getBoolean(false);
/*  79 */       rapidlyDespawnMinedItems = config.get("general", "Rapidly despawn aoe mined items", false, "If true items dropped by a tool in aoe mode will despawn after 5 seconds").getBoolean(false);
/*  80 */       useOldArmorModel = config.get("general", "Use old armor model", false, "If true the armor will use the original vanilla 2D model instead of the new 3D models").getBoolean(false);
/*  81 */       useOldD2DToolTextures = config.get("general", "Use old 2D tool textures", false, "If true the 3D tool models will be replaced with standard 2D textures").getBoolean(false);
/*  82 */       useOriginal3DArmorModel = config.get("general", "Use the original 3D armor models", false, "If true the original 3D armor models created by Skeletonpunk will be used instead of the current ones").getBoolean(false);
/*  83 */       itemDislocatorBlacklist = config.getStringList("Item Dislocator Blacklist", "general", new String[] { "appliedenergistics2:item.ItemCrystalSeed" }, "A list of items of items that should be ignored by the item dislocator. Use the items registry name e.g. minecraft:apple you can also add a meta value like so minecraft:wool|4");
/*  84 */       disableLog = config.get("general", "Disable Log", false, "If you are having issued with console spam that you cant fix setting this to true will disable all log output from Draconic Evolution (Not recommended)").getBoolean(false);
/*  85 */       enableFlight = config.get("general", "Enable Flight", true, "Set this to false to disable flight given by draconic armor.").getBoolean(true);
/*     */ 
/*     */ 
/*     */       
/*  89 */       spawnerListType = config.get("spawner", "listType", false, "Sets weather the spawner list is a white list or a black list (true = white list false = black list)").getBoolean(false);
/*  90 */       spawnerList = config.getStringList("Spawn List", "spawner", defaultSpawnerList, "List of names that will be ether accepted or rejected by the spawner depending on the list type");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  96 */       reaperEnchantID = config.get("magicId's", "Reaper Enchant id", 180).getInt(180);
/*     */       
/*  98 */       disabledNamesList.clear();
/*  99 */       disabledNamesList.addAll(Arrays.asList(disabledBlocksItems));
/*     */       
/* 101 */       speedLimitDimList.clear();
/* 102 */       for (int i : speedDimBlackList) {
/* 103 */         speedLimitDimList.add(Integer.valueOf(i));
/*     */       }
/*     */       
/* 106 */       itemDislocatorBlacklistMap.clear();
/* 107 */       for (String s : itemDislocatorBlacklist) {
/* 108 */         if (s.contains("|")) {
/* 109 */           itemDislocatorBlacklistMap.put(s.substring(0, s.indexOf('|')), Integer.valueOf(Integer.parseInt(s.substring(s.indexOf('|') + 1))));
/*     */         } else {
/* 111 */           itemDislocatorBlacklistMap.put(s, Integer.valueOf(-1));
/*     */         } 
/*     */       } 
/*     */       
/* 115 */       if (disableLog) {
/* 116 */         disableLog = false;
/* 117 */         LogHelper.warn("###########################################");
/* 118 */         LogHelper.warn("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
/* 119 */         LogHelper.warn("WARNING Draconic Evolution Log is Disabled!");
/* 120 */         LogHelper.warn("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
/* 121 */         LogHelper.warn("###########################################");
/* 122 */         disableLog = true;
/*     */       } 
/* 124 */     } catch (Exception e) {
/* 125 */       LogHelper.error("Unable to load Config");
/* 126 */       e.printStackTrace();
/*     */     } finally {
/* 128 */       if (config.hasChanged()) config.save(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\handler\ConfigHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */