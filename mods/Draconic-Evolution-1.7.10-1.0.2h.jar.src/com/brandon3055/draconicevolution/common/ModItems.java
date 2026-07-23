/*     */ package com.brandon3055.draconicevolution.common;
/*     */ import com.brandon3055.draconicevolution.common.items.ItemDE;
/*     */ import com.brandon3055.draconicevolution.common.items.ReactorStabiliserPart;
/*     */ import com.brandon3055.draconicevolution.common.items.WyvernCore;
/*     */ import com.brandon3055.draconicevolution.common.items.armor.DraconicArmor;
/*     */ import com.brandon3055.draconicevolution.common.items.armor.WyvernArmor;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.DraconicAxe;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.DraconicHoe;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.DraconicShovel;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.EnderArrow;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.SafetyMatch;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.TeleporterMKI;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.WyvernPickaxe;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.WyvernShovel;
/*     */ import com.brandon3055.draconicevolution.common.items.weapons.WyvernBow;
/*     */ import com.brandon3055.draconicevolution.common.items.weapons.WyvernSword;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.common.util.EnumHelper;
/*     */ 
/*     */ @ObjectHolder("DraconicEvolution")
/*     */ public class ModItems {
/*  24 */   public static ItemArmor.ArmorMaterial WYVERN_ARMOR = EnumHelper.addArmorMaterial("WYVERN_ARMOR", -1, new int[] { 3, 8, 6, 3 }, 30);
/*  25 */   public static ItemArmor.ArmorMaterial DRACONIC_ARMOR = EnumHelper.addArmorMaterial("DRACONIC_ARMOR", -1, new int[] { 3, 8, 6, 3 }, 30);
/*  26 */   public static Item.ToolMaterial WYVERN = EnumHelper.addToolMaterial("WYVERN", 10, -1, 12.0F, 20.0F, 35);
/*  27 */   public static Item.ToolMaterial AWAKENED = EnumHelper.addToolMaterial("AWAKENED", 10, -1, 16.0F, 40.0F, 40);
/*  28 */   public static Item.ToolMaterial CHAOTIC = EnumHelper.addToolMaterial("CHAOTIC", 10, -1, 400.0F, 60.0F, 45);
/*     */   
/*     */   public static Item draconicPickaxe;
/*     */   
/*     */   public static Item draconicShovel;
/*     */   
/*     */   public static Item draconicHoe;
/*     */   
/*     */   public static Item draconicAxe;
/*     */   
/*     */   public static Item draconicSword;
/*     */   
/*     */   public static Item draconicBow;
/*     */   public static ItemArmor draconicHelm;
/*     */   public static ItemArmor draconicChest;
/*     */   public static ItemArmor draconicLeggs;
/*     */   public static ItemArmor draconicBoots;
/*     */   public static Item draconicDestructionStaff;
/*     */   public static Item wyvernPickaxe;
/*     */   public static Item wyvernShovel;
/*     */   public static Item wyvernSword;
/*     */   public static Item wyvernBow;
/*     */   public static ItemArmor wyvernHelm;
/*     */   public static ItemArmor wyvernChest;
/*     */   public static ItemArmor wyvernLeggs;
/*     */   public static ItemArmor wyvernBoots;
/*     */   public static ItemDE wyvernCore;
/*     */   public static ItemDE draconiumBlend;
/*     */   public static ItemDE awakenedCore;
/*     */   public static ItemDE draconicCore;
/*     */   public static ItemDE mobSoul;
/*     */   public static ItemDE enderArrow;
/*     */   public static ItemDE safetyMatch;
/*     */   public static ItemDE infoTablet;
/*     */   public static ItemDE draconiumEnergyCore;
/*     */   public static ItemDE draconiumFluxCapacitor;
/*     */   public static ItemDE wrench;
/*     */   public static ItemDE reactorStabilizerParts;
/*     */   public static ItemDE chaoticCore;
/*     */   public static ItemDE magnet;
/*     */   public static ItemDE teleporterMKI;
/*     */   public static ItemDE teleporterMKII;
/*     */   public static ItemStack wyvernFluxCapacitor;
/*     */   public static ItemStack draconicFluxCapacitor;
/*     */   public static ItemStack wyvernEnergyCore;
/*     */   public static ItemStack draconicEnergyCore;
/*     */   public static ItemStack partStabFrame;
/*     */   public static ItemStack partStabRotorInner;
/*     */   public static ItemStack partStabRotorOuter;
/*     */   public static ItemStack partStabRotorAssembly;
/*     */   public static ItemStack partStabRing;
/*     */   
/*     */   public static void init() {
/*  81 */     draconicDestructionStaff = (Item)new DraconicDistructionStaff();
/*  82 */     draconicPickaxe = (Item)new DraconicPickaxe();
/*  83 */     draconicAxe = (Item)new DraconicAxe();
/*  84 */     draconicShovel = (Item)new DraconicShovel();
/*  85 */     draconicHoe = (Item)new DraconicHoe();
/*  86 */     draconicSword = (Item)new DraconicSword();
/*  87 */     draconicBow = (Item)new DraconicBow();
/*  88 */     draconicHelm = (ItemArmor)new DraconicArmor(DRACONIC_ARMOR, 0, "draconicHelm");
/*  89 */     draconicChest = (ItemArmor)new DraconicArmor(DRACONIC_ARMOR, 1, "draconicChest");
/*  90 */     draconicLeggs = (ItemArmor)new DraconicArmor(DRACONIC_ARMOR, 2, "draconicLeggs");
/*  91 */     draconicBoots = (ItemArmor)new DraconicArmor(DRACONIC_ARMOR, 3, "draconicBoots");
/*     */     
/*  93 */     wyvernPickaxe = (Item)new WyvernPickaxe();
/*  94 */     wyvernShovel = (Item)new WyvernShovel();
/*  95 */     wyvernSword = (Item)new WyvernSword();
/*  96 */     wyvernBow = (Item)new WyvernBow();
/*  97 */     wyvernHelm = (ItemArmor)new WyvernArmor(WYVERN_ARMOR, 0, "wyvernHelm");
/*  98 */     wyvernChest = (ItemArmor)new WyvernArmor(WYVERN_ARMOR, 1, "wyvernChest");
/*  99 */     wyvernLeggs = (ItemArmor)new WyvernArmor(WYVERN_ARMOR, 2, "wyvernLeggs");
/* 100 */     wyvernBoots = (ItemArmor)new WyvernArmor(WYVERN_ARMOR, 3, "wyvernBoots");
/*     */     
/* 102 */     draconicCore = (ItemDE)new DraconicCore();
/* 103 */     wyvernCore = (ItemDE)new WyvernCore();
/* 104 */     awakenedCore = (ItemDE)new AwakenedCore();
/* 105 */     chaoticCore = (ItemDE)new ChaoticCore();
/* 106 */     draconiumBlend = (ItemDE)new DraconiumBlend();
/* 107 */     teleporterMKI = (ItemDE)new TeleporterMKI();
/* 108 */     teleporterMKII = (ItemDE)new TeleporterMKII();
/* 109 */     mobSoul = (ItemDE)new MobSoul();
/* 110 */     enderArrow = (ItemDE)new EnderArrow();
/* 111 */     safetyMatch = (ItemDE)new SafetyMatch();
/* 112 */     infoTablet = (ItemDE)new InfoTablet();
/* 113 */     draconiumEnergyCore = (ItemDE)new DraconiumEnergyCore();
/* 114 */     draconiumFluxCapacitor = (ItemDE)new DraconiumFluxCapacitor();
/* 115 */     wrench = (ItemDE)new Wrench();
/* 116 */     reactorStabilizerParts = (ItemDE)new ReactorStabiliserPart();
/* 117 */     magnet = (ItemDE)new Magnet();
/*     */ 
/*     */     
/* 120 */     wyvernEnergyCore = new ItemStack((Item)draconiumEnergyCore, 1, 0);
/* 121 */     draconicEnergyCore = new ItemStack((Item)draconiumEnergyCore, 1, 1);
/* 122 */     wyvernFluxCapacitor = new ItemStack((Item)draconiumFluxCapacitor, 1, 0);
/* 123 */     draconicFluxCapacitor = new ItemStack((Item)draconiumFluxCapacitor, 1, 1);
/*     */     
/* 125 */     partStabFrame = new ItemStack((Item)reactorStabilizerParts, 1, 0);
/* 126 */     partStabRotorInner = new ItemStack((Item)reactorStabilizerParts, 1, 1);
/* 127 */     partStabRotorOuter = new ItemStack((Item)reactorStabilizerParts, 1, 2);
/* 128 */     partStabRotorAssembly = new ItemStack((Item)reactorStabilizerParts, 1, 3);
/* 129 */     partStabRing = new ItemStack((Item)reactorStabilizerParts, 1, 4);
/*     */   }
/*     */   
/*     */   public static void register(ItemDE item) {
/* 133 */     String name = item.getUnwrappedUnlocalizedName(item.func_77658_a());
/* 134 */     if (isEnabled((Item)item)) GameRegistry.registerItem((Item)item, name.substring(name.indexOf(':') + 1)); 
/*     */   }
/*     */   
/*     */   public static boolean isEnabled(Item item) {
/* 138 */     return !ConfigHandler.disabledNamesList.contains(item.func_77658_a());
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\ModItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */