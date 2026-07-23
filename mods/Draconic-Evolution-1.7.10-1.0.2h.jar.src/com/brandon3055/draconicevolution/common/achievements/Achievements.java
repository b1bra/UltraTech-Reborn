/*     */ package com.brandon3055.draconicevolution.common.achievements;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.common.gameevent.PlayerEvent;
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraftforge.common.AchievementPage;
/*     */ import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
/*     */ 
/*     */ 
/*     */ public class Achievements
/*     */ {
/*     */   private static AchievementPage achievementsPage;
/*  23 */   private static HashMap<String, Achievement> achievementsList = new HashMap<>();
/*  24 */   private static HashMap<String, AchievementCondition> achievementItems = new HashMap<>();
/*     */   
/*     */   public static void addAchievement(String name, Achievement achievement, ItemStack stack, String triggerCondition) {
/*  27 */     if (stack == null || stack.func_77973_b() == null)
/*  28 */       return;  achievementsList.put(name, achievement.func_75971_g());
/*  29 */     achievementItems.put(stack.func_77977_a(), new AchievementCondition(name, triggerCondition));
/*     */   }
/*     */   
/*     */   public static void addAchievement(String name, Achievement achievement, String triggerCondition) {
/*  33 */     addAchievement(name, achievement, achievement.field_75990_d, triggerCondition);
/*     */   }
/*     */   
/*     */   public static void addAchievement(String name, Achievement achievement) {
/*  37 */     addAchievement(name, achievement, achievement.field_75990_d, "null");
/*     */   }
/*     */   
/*     */   public static Achievement getAchievement(String name) {
/*  41 */     return achievementsList.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void triggerAchievement(EntityPlayer player, String name) {
/*  46 */     Achievement ach = getAchievement(name);
/*     */     
/*  48 */     if (ach != null) {
/*  49 */       player.func_71029_a((StatBase)ach);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void addModAchievements() {
/*  54 */     int x = 5;
/*  55 */     addAchievement("draconicevolution.core", new Achievement("draconicevolution.core", "draconicevolution.core", -6 + x, 0, (Item)ModItems.draconicCore, getAchievement("draconicevolution.ingot")), "craft");
/*  56 */     addAchievement("draconicevolution.core2", (new Achievement("draconicevolution.core2", "draconicevolution.core2", -4 + x, 0, (Item)ModItems.wyvernCore, getAchievement("draconicevolution.core"))).func_75987_b(), "craft");
/*     */     
/*  58 */     addAchievement("draconicevolution.dislocator", new Achievement("draconicevolution.dislocator", "draconicevolution.dislocator", -8 + x, 1, (Item)ModItems.teleporterMKI, getAchievement("draconicevolution.dust")), "craft");
/*  59 */     addAchievement("draconicevolution.particles", new Achievement("draconicevolution.particles", "draconicevolution.particles", -7 + x, 2, (Block)ModBlocks.particleGenerator, getAchievement("draconicevolution.core")), "craft");
/*  60 */     addAchievement("draconicevolution.dissenchanter", new Achievement("draconicevolution.dissenchanter", "draconicevolution.dissenchanter", -7 + x, -2, (Block)ModBlocks.dissEnchanter, getAchievement("draconicevolution.core")), "craft");
/*  61 */     addAchievement("draconicevolution.ecore", new Achievement("draconicevolution.ecore", "draconicevolution.ecore", -4 + x, -6, (Block)ModBlocks.energyStorageCore, getAchievement("draconicevolution.core2")), "craft");
/*  62 */     addAchievement("draconicevolution.wpick", new Achievement("draconicevolution.wpick", "draconicevolution.wpick", -2 + x, 1, ModItems.wyvernPickaxe, getAchievement("draconicevolution.core2")), "craft");
/*  63 */     addAchievement("draconicevolution.wshovel", new Achievement("draconicevolution.wshovel", "draconicevolution.wshovel", -2 + x, -1, ModItems.wyvernShovel, getAchievement("draconicevolution.core2")), "craft");
/*  64 */     addAchievement("draconicevolution.wsword", new Achievement("draconicevolution.wsword", "draconicevolution.wsword", -2 + x, 2, ModItems.wyvernSword, getAchievement("draconicevolution.core2")), "craft");
/*  65 */     addAchievement("draconicevolution.wbow", new Achievement("draconicevolution.wbow", "draconicevolution.wbow", -2 + x, -2, ModItems.wyvernBow, getAchievement("draconicevolution.core2")), "craft");
/*  66 */     addAchievement("draconicevolution.whelm", new Achievement("draconicevolution.whelm", "draconicevolution.whelm", -2 + x, -4, (Item)ModItems.wyvernHelm, getAchievement("draconicevolution.core2")), "craft");
/*  67 */     addAchievement("draconicevolution.wchest", new Achievement("draconicevolution.wchest", "draconicevolution.wchest", -2 + x, -3, (Item)ModItems.wyvernChest, getAchievement("draconicevolution.core2")), "craft");
/*  68 */     addAchievement("draconicevolution.wleggs", new Achievement("draconicevolution.wleggs", "draconicevolution.wleggs", -2 + x, 3, (Item)ModItems.wyvernLeggs, getAchievement("draconicevolution.core2")), "craft");
/*  69 */     addAchievement("draconicevolution.wboots", new Achievement("draconicevolution.wboots", "draconicevolution.wboots", -2 + x, 4, (Item)ModItems.wyvernBoots, getAchievement("draconicevolution.core2")), "craft");
/*  70 */     addAchievement("draconicevolution.dislocator2", new Achievement("draconicevolution.dislocator2", "draconicevolution.dislocator2", -2 + x, -5, (Item)ModItems.teleporterMKII, getAchievement("draconicevolution.core2")), "craft");
/*  71 */     addAchievement("draconicevolution.flux", new Achievement("draconicevolution.flux", "draconicevolution.flux", -2 + x, 5, ModItems.wyvernFluxCapacitor, getAchievement("draconicevolution.core2")), "craft");
/*     */     
/*  73 */     addAchievement("draconicevolution.core3", (new Achievement("draconicevolution.core3", "draconicevolution.core3", 4 + x, 0, (Item)ModItems.awakenedCore, getAchievement("draconicevolution.awakenedblock"))).func_75987_b(), "craft");
/*     */     
/*  75 */     addAchievement("draconicevolution.dhelm", new Achievement("draconicevolution.dhelm", "draconicevolution.dhelm", 2 + x, -3, (Item)ModItems.draconicHelm, getAchievement("draconicevolution.core3")), "craft");
/*  76 */     addAchievement("draconicevolution.dChest", new Achievement("draconicevolution.dChest", "draconicevolution.dChest", 2 + x, -2, (Item)ModItems.draconicChest, getAchievement("draconicevolution.core3")), "craft");
/*  77 */     addAchievement("draconicevolution.dleggs", new Achievement("draconicevolution.dleggs", "draconicevolution.dleggs", 2 + x, 2, (Item)ModItems.draconicLeggs, getAchievement("draconicevolution.core3")), "craft");
/*  78 */     addAchievement("draconicevolution.dboots", new Achievement("draconicevolution.dboots", "draconicevolution.dboots", 2 + x, 3, (Item)ModItems.draconicBoots, getAchievement("draconicevolution.core3")), "craft");
/*     */     
/*  80 */     addAchievement("draconicevolution.dpick", new Achievement("draconicevolution.dpick", "draconicevolution.dpick", 6 + x, 0, ModItems.draconicPickaxe, getAchievement("draconicevolution.core3")), "craft");
/*  81 */     addAchievement("draconicevolution.dshovel", new Achievement("draconicevolution.dshovel", "draconicevolution.dshovel", 6 + x, -1, ModItems.draconicShovel, getAchievement("draconicevolution.core3")), "craft");
/*  82 */     addAchievement("draconicevolution.daxe", new Achievement("draconicevolution.daxe", "draconicevolution.daxe", 6 + x, 1, ModItems.draconicAxe, getAchievement("draconicevolution.core3")), "craft");
/*  83 */     addAchievement("draconicevolution.dsword", new Achievement("draconicevolution.dsword", "draconicevolution.dsword", 6 + x, -2, ModItems.draconicSword, getAchievement("draconicevolution.core3")), "craft");
/*  84 */     addAchievement("draconicevolution.dbow", new Achievement("draconicevolution.dbow", "draconicevolution.dbow", 6 + x, 2, ModItems.draconicBow, getAchievement("draconicevolution.core3")), "craft");
/*  85 */     addAchievement("draconicevolution.flux2", new Achievement("draconicevolution.flux2", "draconicevolution.flux2", 6 + x, -3, ModItems.draconicFluxCapacitor, getAchievement("draconicevolution.core3")), "craft");
/*  86 */     addAchievement("draconicevolution.dhoe", new Achievement("draconicevolution.dhoe", "draconicevolution.dhoe", 6 + x, 3, ModItems.draconicHoe, getAchievement("draconicevolution.core3")), "craft");
/*     */     
/*  88 */     addAchievement("draconicevolution.dstaff", (new Achievement("draconicevolution.dstaff", "draconicevolution.dstaff", 8 + x, 0, ModItems.draconicDestructionStaff, getAchievement("draconicevolution.dpick"))).func_75987_b(), "craft");
/*     */     
/*  90 */     ItemStack mobSoul = new ItemStack((Item)ModItems.mobSoul);
/*  91 */     ItemNBTHelper.setString(mobSoul, "Name", "Any");
/*  92 */     addAchievement("draconicevolution.soul", (new Achievement("draconicevolution.soul", "draconicevolution.soul", x, -2, mobSoul, null)).func_75966_h(), "null");
/*  93 */     addAchievement("draconicevolution.manual", new Achievement("draconicevolution.manual", "draconicevolution.manual", -8 + x, -1, (Item)ModItems.infoTablet, getAchievement("draconicevolution.dust")), "craft");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerAchievementPane() {
/*  99 */     Achievement[] achievements = new Achievement[achievementsList.size()];
/*     */     
/* 101 */     achievements = (Achievement[])achievementsList.values().toArray((Object[])achievements);
/* 102 */     achievementsPage = new AchievementPage(StatCollector.func_74838_a("draconicevolution.achievementPage.name"), achievements);
/* 103 */     AchievementPage.registerAchievementPage(achievementsPage);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void entityPickupEvent(EntityItemPickupEvent event) {
/* 108 */     ItemStack stack = event.item.func_92059_d().func_77946_l();
/* 109 */     stack.field_77994_a = 1;
/* 110 */     if (achievementItems.containsKey(stack.func_77977_a()) && ((AchievementCondition)achievementItems.get(stack.func_77977_a())).isCorrectCondition("pickup")) {
/* 111 */       triggerAchievement(event.entityPlayer, ((AchievementCondition)achievementItems.get(stack.func_77977_a())).getName());
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void craftEvent(PlayerEvent.ItemCraftedEvent event) {
/* 117 */     ItemStack stack = event.crafting.func_77946_l();
/* 118 */     stack.field_77994_a = 1;
/* 119 */     if (achievementItems.containsKey(stack.func_77977_a()) && ((AchievementCondition)achievementItems.get(stack.func_77977_a())).isCorrectCondition("craft")) {
/* 120 */       triggerAchievement(event.player, ((AchievementCondition)achievementItems.get(stack.func_77977_a())).getName());
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void smeltEvent(PlayerEvent.ItemSmeltedEvent event) {
/* 126 */     ItemStack stack = event.smelting.func_77946_l();
/* 127 */     stack.field_77994_a = 1;
/* 128 */     if (achievementItems.containsKey(stack.func_77977_a()) && ((AchievementCondition)achievementItems.get(stack.func_77977_a())).isCorrectCondition("smelt"))
/* 129 */       triggerAchievement(event.player, ((AchievementCondition)achievementItems.get(stack.func_77977_a())).getName()); 
/*     */   }
/*     */   
/*     */   private static class AchievementCondition
/*     */   {
/*     */     private final String name;
/*     */     public final String condition;
/*     */     
/*     */     public AchievementCondition(String name, String condition) {
/* 138 */       this.name = name;
/* 139 */       this.condition = condition;
/*     */     }
/*     */     
/*     */     public boolean isCorrectCondition(String s) {
/* 143 */       return s.equals(this.condition);
/*     */     }
/*     */     
/*     */     public String getName() {
/* 147 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\achievements\Achievements.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */