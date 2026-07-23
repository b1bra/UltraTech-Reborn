/*     */ package com.brandon3055.draconicevolution.common.handler;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import java.io.File;
/*     */ import net.loliland.mctags.api.Tags;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraftforge.common.config.Configuration;
/*     */ import net.minecraftforge.common.config.Property;
/*     */ 
/*     */ 
/*     */ public class BalanceConfigHandler
/*     */ {
/*     */   public static final int wyvernArmorMinShieldRecovery = 5;
/*     */   public static final int draconicArmorMinShieldRecovery = 5;
/*     */   public static final int wyvernToolsMinDigAOEUpgradePoints = 1;
/*     */   public static final int wyvernToolsMaxDigAOEUpgradePoints = 2;
/*     */   public static final int wyvernToolsMinDigSpeedUpgradePoints = 4;
/*     */   public static final int wyvernToolsMaxDigSpeedUpgradePoints = 16;
/*     */   public static final int draconicToolsMinDigAOEUpgradePoints = 2;
/*     */   public static final int draconicToolsMaxDigAOEUpgradePoints = 4;
/*     */   public static final int draconicToolsMinDigSpeedUpgradePoints = 5;
/*     */   public static final int draconicToolsMaxDigSpeedUpgradePoints = 32;
/*     */   public static final int draconicToolsMinDigDepthUpgradePoints = 1;
/*     */   public static final int draconicToolsMaxDigDepthUpgradePoints = 5;
/*     */   public static final int wyvernWeaponsMinAttackAOEUpgradePoints = 1;
/*     */   public static final int wyvernWeaponsMaxAttackAOEUpgradePoints = 3;
/*     */   public static final int wyvernWeaponsMinAttackDamageUpgradePoints = 0;
/*     */   public static final int wyvernWeaponsMaxAttackDamageUpgradePoints = 8;
/*     */   public static final int wyvernBowMinDrawSpeedUpgradePoints = 3;
/*     */   public static final int wyvernBowMaxDrawSpeedUpgradePoints = 5;
/*     */   public static final int wyvernBowMinArrowSpeedUpgradePoints = 1;
/*     */   public static final int wyvernBowMaxArrowSpeedUpgradePoints = 10;
/*     */   public static final int wyvernBowMinArrowDamageUpgradePoints = 2;
/*     */   public static final int wyvernBowMaxArrowDamageUpgradePoints = 10;
/*     */   public static final int draconicWeaponsMinAttackAOEUpgradePoints = 2;
/*     */   public static final int draconicWeaponsMaxAttackAOEUpgradePoints = 5;
/*     */   public static final int draconicWeaponsMinAttackDamageUpgradePoints = 0;
/*     */   public static final int draconicWeaponsMaxAttackDamageUpgradePoints = 16;
/*     */   public static final int draconicBowMinDrawSpeedUpgradePoints = 4;
/*     */   public static final int draconicBowMaxDrawSpeedUpgradePoints = 6;
/*     */   public static final int draconicBowMinArrowSpeedUpgradePoints = 3;
/*     */   public static final int draconicBowMaxArrowSpeedUpgradePoints = 10;
/*     */   public static final int draconicBowMinArrowDamageUpgradePoints = 3;
/*     */   public static final int draconicBowMaxArrowDamageUpgradePoints = 20;
/*     */   public static final int draconicStaffMinDigAOEUpgradePoints = 3;
/*     */   public static final int draconicStaffMaxDigAOEUpgradePoints = 5;
/*     */   public static final int draconicStaffMinDigDepthUpgradePoints = 7;
/*     */   public static final int draconicStaffMaxDigDepthUpgradePoints = 11;
/*     */   public static final int draconicStaffMinAttackAOEUpgradePoints = 3;
/*     */   public static final int draconicStaffMaxAttackAOEUpgradePoints = 13;
/*     */   public static final int draconicStaffMinAttackDamageUpgradePoints = 0;
/*     */   public static final int draconicStaffMaxAttackDamageUpgradePoints = 64;
/*  54 */   public static int wyvernArmorBaseStorage = 1000000;
/*  55 */   public static int wyvernArmorStoragePerUpgrade = 500000;
/*  56 */   public static int wyvernArmorMaxTransfer = 50000;
/*  57 */   public static int wyvernArmorEnergyPerProtectionPoint = 1000;
/*  58 */   public static int draconicArmorBaseStorage = 10000000;
/*  59 */   public static int draconicArmorStoragePerUpgrade = 5000000;
/*  60 */   public static int draconicArmorMaxTransfer = 500000;
/*  61 */   public static int draconicArmorEnergyPerProtectionPoint = 1000;
/*  62 */   public static int draconicArmorEnergyToRemoveEffects = 5000;
/*  63 */   public static int wyvernToolsBaseStorage = 1000000;
/*  64 */   public static int wyvernToolsStoragePerUpgrade = 500000;
/*  65 */   public static int wyvernToolsMaxTransfer = 50000;
/*  66 */   public static int wyvernToolsEnergyPerAction = 80;
/*  67 */   public static int draconicToolsBaseStorage = 10000000;
/*  68 */   public static int draconicToolsStoragePerUpgrade = 5000000;
/*  69 */   public static int draconicToolsMaxTransfer = 500000;
/*  70 */   public static int draconicToolsEnergyPerAction = 80;
/*  71 */   public static int wyvernCapacitorBaseStorage = 80000000;
/*  72 */   public static int wyvernCapacitorStoragePerUpgrade = 50000000;
/*  73 */   public static int wyvernCapacitorMaxReceive = 1000000;
/*  74 */   public static int wyvernCapacitorMaxExtract = 10000000;
/*  75 */   public static int draconicCapacitorBaseStorage = 250000000;
/*  76 */   public static int draconicCapacitorStoragePerUpgrade = 50000000;
/*  77 */   public static int draconicCapacitorMaxReceive = 10000000;
/*  78 */   public static int draconicCapacitorMaxExtract = 100000000;
/*  79 */   public static int wyvernWeaponsBaseStorage = 1000000;
/*  80 */   public static int wyvernWeaponsStoragePerUpgrade = 500000;
/*  81 */   public static int wyvernWeaponsMaxTransfer = 50000;
/*  82 */   public static int wyvernWeaponsEnergyPerAttack = 250;
/*  83 */   public static int wyvernBowEnergyPerShot = 80;
/*  84 */   public static int draconicWeaponsBaseStorage = 10000000;
/*  85 */   public static int draconicWeaponsStoragePerUpgrade = 5000000;
/*  86 */   public static int draconicWeaponsMaxTransfer = 500000;
/*  87 */   public static int draconicWeaponsEnergyPerAttack = 250;
/*  88 */   public static int draconicBowEnergyPerShot = 80;
/*  89 */   public static int draconicFireEnergyCostMultiptier = 30;
/*  90 */   public static int energyInfuserStorage = 10000000;
/*  91 */   public static int energyInfuserMaxTransfer = 10000000;
/*  92 */   public static long energyStorageTier1Storage = 45500000L;
/*  93 */   public static long energyStorageTier2Storage = 273000000L;
/*  94 */   public static long energyStorageTier3Storage = 1640000000L;
/*  95 */   public static long energyStorageTier4Storage = 9880000000L;
/*  96 */   public static long energyStorageTier5Storage = 59300000000L;
/*  97 */   public static long energyStorageTier6Storage = 356000000000L;
/*  98 */   public static long energyStorageTier7Storage = 2140000000000L;
/*  99 */   public static int grinderInternalEnergyBufferSize = 20000;
/* 100 */   public static int grinderExternalEnergyBufferSize = 100000;
/* 101 */   public static int grinderMaxReceive = 32000;
/* 102 */   public static int grinderEnergyPerKill = 1000;
/* 103 */   public static int wyvernArmorMaxCapacityUpgradePoints = 50;
/* 104 */   public static int wyvernArmorMaxUpgrades = 3;
/* 105 */   public static int wyvernArmorMaxUpgradePoints = 50;
/* 106 */   public static int draconicArmorMaxCapacityUpgradePoints = 50;
/* 107 */   public static int draconicArmorMaxUpgrades = 6;
/* 108 */   public static int draconicArmorMaxUpgradePoints = 50;
/* 109 */   public static int wyvernToolsMaxCapacityUpgradePoints = 50;
/* 110 */   public static int wyvernToolsMaxUpgrades = 3;
/* 111 */   public static int wyvernToolsMaxUpgradePoints = 50;
/* 112 */   public static int draconicToolsMaxCapacityUpgradePoints = 50;
/* 113 */   public static int draconicToolsMaxUpgrades = 6;
/* 114 */   public static int draconicToolsMaxUpgradePoints = 50;
/* 115 */   public static int wyvernWeaponsMaxCapacityUpgradePoints = 50;
/* 116 */   public static int wyvernWeaponsMaxUpgrades = 3;
/* 117 */   public static int wyvernWeaponsMaxUpgradePoints = 50;
/* 118 */   public static int draconicWeaponsMaxCapacityUpgradePoints = 50;
/* 119 */   public static int draconicWeaponsMaxUpgrades = 6;
/* 120 */   public static int draconicWeaponsMaxUpgradePoints = 50;
/* 121 */   public static int wyvernBowMaxCapacityUpgradePoints = 50;
/* 122 */   public static int wyvernBowMaxUpgrades = 3;
/* 123 */   public static int wyvernBowMaxUpgradePoints = 50;
/* 124 */   public static int draconicBowMaxCapacityUpgradePoints = 50;
/* 125 */   public static int draconicBowMaxUpgrades = 6;
/* 126 */   public static int draconicBowMaxUpgradePoints = 50;
/* 127 */   public static int draconicStaffMaxCapacityUpgradePoints = 50;
/* 128 */   public static int draconicStaffMaxUpgrades = 12;
/* 129 */   public static int draconicStaffMaxUpgradePoints = 50;
/* 130 */   public static int wyvernCapacitorMaxUpgradePoints = 50;
/* 131 */   public static int wyvernCapacitorMaxCapacityUpgradePoints = 50;
/* 132 */   public static int wyvernCapacitorMaxUpgrades = 3;
/* 133 */   public static int draconicCapacitorMaxUpgradePoints = 50;
/* 134 */   public static int draconicCapacitorMaxCapacityUpgradePoints = 50;
/* 135 */   public static int draconicCapacitorMaxUpgrades = 6;
/* 136 */   public static Block energyStorageStructureBlock = null;
/* 137 */   public static int energyStorageStructureBlockMetadata = 0;
/*     */   public static boolean grinderShouldUseLooting = false;
/*     */   private static Configuration config;
/*     */   
/*     */   public static void init(File modConfigurationDirectory) {
/* 142 */     if (config == null) {
/* 143 */       config = new Configuration(new File(modConfigurationDirectory, "DraconicEvolution.Balance.cfg"));
/* 144 */       config.load();
/* 145 */       config.setCategoryRequiresMcRestart("tweaks", true);
/* 146 */       config.setCategoryComment("tweaks.armor", "Values in this category may be replaced automatically to prevent problems");
/* 147 */       config.setCategoryComment("tweaks.tools", "Values in this category may be replaced automatically to prevent problems");
/* 148 */       config.setCategoryComment("tweaks.weapons", "Values in this category may be replaced automatically to prevent problems");
/* 149 */       syncConfig();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void syncConfig() {
/* 154 */     wyvernArmorBaseStorage = getInteger("energy.armor", "Wyvern Armor: Base energy storage (EU)", wyvernArmorBaseStorage);
/* 155 */     wyvernArmorStoragePerUpgrade = getInteger("energy.armor", "Wyvern Armor: Additional energy storage per upgrade installed (EU)", wyvernArmorStoragePerUpgrade);
/* 156 */     wyvernArmorMaxTransfer = getInteger("energy.armor", "Wyvern Armor: Maximum energy transfer rate (EU/t)", wyvernArmorMaxTransfer);
/* 157 */     wyvernArmorEnergyPerProtectionPoint = getInteger("energy.armor", "Wyvern Armor: Amount of energy required to restore protection point (EU)", wyvernArmorEnergyPerProtectionPoint);
/* 158 */     draconicArmorBaseStorage = getInteger("energy.armor", "Draconic Armor: Base energy storage (EU)", draconicArmorBaseStorage);
/* 159 */     draconicArmorStoragePerUpgrade = getInteger("energy.armor", "Draconic Armor: Additional energy storage per upgrade installed (EU)", draconicArmorStoragePerUpgrade);
/* 160 */     draconicArmorMaxTransfer = getInteger("energy.armor", "Draconic Armor: Maximum energy transfer rate (EU/t)", draconicArmorMaxTransfer);
/* 161 */     draconicArmorEnergyPerProtectionPoint = getInteger("energy.armor", "Draconic Armor: Amount of energy required to restore protection point (EU)", draconicArmorEnergyPerProtectionPoint);
/* 162 */     draconicArmorEnergyToRemoveEffects = getInteger("energy.armor", "Draconic Armor: Amount of energy required to remove negative effects (EU)", draconicArmorEnergyToRemoveEffects);
/* 163 */     wyvernToolsBaseStorage = getInteger("energy.tools", "Wyvern Tools: Base energy storage (EU)", wyvernToolsBaseStorage);
/* 164 */     wyvernToolsStoragePerUpgrade = getInteger("energy.tools", "Wyvern Tools: Additional energy storage per upgrade installed (EU)", wyvernToolsStoragePerUpgrade);
/* 165 */     wyvernToolsMaxTransfer = getInteger("energy.tools", "Wyvern Tools: Maximum energy transfer rate (EU/t)", wyvernToolsMaxTransfer);
/* 166 */     wyvernToolsEnergyPerAction = getInteger("energy.tools", "Wyvern Tools: Amount of energy required to perform action (EU)", wyvernToolsEnergyPerAction);
/* 167 */     draconicToolsBaseStorage = getInteger("energy.tools", "Draconic Tools: Base energy storage (EU)", draconicToolsBaseStorage);
/* 168 */     draconicToolsStoragePerUpgrade = getInteger("energy.tools", "Draconic Tools: Additional energy storage per upgrade installed (EU)", draconicToolsStoragePerUpgrade);
/* 169 */     draconicToolsMaxTransfer = getInteger("energy.tools", "Draconic Tools: Maximum energy transfer rate (EU/t)", draconicToolsMaxTransfer);
/* 170 */     draconicToolsEnergyPerAction = getInteger("energy.tools", "Draconic Tools: Amount of energy required to perform action (EU)", draconicToolsEnergyPerAction);
/* 171 */     wyvernCapacitorBaseStorage = getInteger("energy.tools", "Wyvern Flux Capacitor: Base energy storage (EU)", wyvernCapacitorBaseStorage);
/* 172 */     wyvernCapacitorStoragePerUpgrade = getInteger("energy.tools", "Wyvern Flux Capacitor: Additional energy storage per upgrade installed (EU)", wyvernCapacitorStoragePerUpgrade);
/* 173 */     wyvernCapacitorMaxReceive = getInteger("energy.tools", "Wyvern Flux Capacitor: Maximum energy reception rate (EU/t)", wyvernCapacitorMaxReceive);
/* 174 */     wyvernCapacitorMaxExtract = getInteger("energy.tools", "Wyvern Flux Capacitor: Maximum energy extraction rate (EU/t)", wyvernCapacitorMaxExtract);
/* 175 */     draconicCapacitorBaseStorage = getInteger("energy.tools", "Draconic Flux Capacitor: Base energy storage (EU)", draconicCapacitorBaseStorage);
/* 176 */     draconicCapacitorStoragePerUpgrade = getInteger("energy.tools", "Draconic Flux Capacitor: Additional energy storage per upgrade installed (EU)", draconicCapacitorStoragePerUpgrade);
/* 177 */     draconicCapacitorMaxReceive = getInteger("energy.tools", "Draconic Flux Capacitor: Maximum energy reception rate (EU/t)", draconicCapacitorMaxReceive);
/* 178 */     draconicCapacitorMaxExtract = getInteger("energy.tools", "Draconic Flux Capacitor: Maximum energy extraction rate (EU/t)", draconicCapacitorMaxExtract);
/* 179 */     wyvernWeaponsBaseStorage = getInteger("energy.weapons", "Wyvern Weapons: Base energy storage (EU)", wyvernWeaponsBaseStorage);
/* 180 */     wyvernWeaponsStoragePerUpgrade = getInteger("energy.weapons", "Wyvern Weapons: Additional energy storage per upgrade installed (EU)", wyvernWeaponsStoragePerUpgrade);
/* 181 */     wyvernWeaponsMaxTransfer = getInteger("energy.weapons", "Wyvern Weapons: Maximum energy transfer rate (EU/t)", wyvernWeaponsMaxTransfer);
/* 182 */     wyvernWeaponsEnergyPerAttack = getInteger("energy.weapons", "Wyvern Weapons: Amount of energy required to perform attack (EU)", wyvernWeaponsEnergyPerAttack);
/* 183 */     wyvernBowEnergyPerShot = getInteger("energy.weapons", "Wyvern Bow: Amount of energy required to shoot (EU)", wyvernBowEnergyPerShot);
/* 184 */     draconicWeaponsBaseStorage = getInteger("energy.weapons", "Draconic Weapons: Base energy storage (EU)", draconicWeaponsBaseStorage);
/* 185 */     draconicWeaponsStoragePerUpgrade = getInteger("energy.weapons", "Draconic Weapons: Additional energy storage per upgrade installed (EU)", draconicWeaponsStoragePerUpgrade);
/* 186 */     draconicWeaponsMaxTransfer = getInteger("energy.weapons", "Draconic Weapons: Maximum energy transfer rate (EU/t)", draconicWeaponsMaxTransfer);
/* 187 */     draconicWeaponsEnergyPerAttack = getInteger("energy.weapons", "Draconic Weapons: Amount of energy required to perform attack (EU)", draconicWeaponsEnergyPerAttack);
/* 188 */     draconicBowEnergyPerShot = getInteger("energy.weapons", "Draconic Bow: Amount of energy required to shoot (EU)", draconicBowEnergyPerShot);
/* 189 */     draconicFireEnergyCostMultiptier = getInteger("energy.weapons", "Arrow of Draconic Fire: Energy cost multiplier", draconicFireEnergyCostMultiptier);
/* 190 */     energyInfuserStorage = getInteger("energy.machines", "Energy Infuser: Energy buffer size (EU)", energyInfuserStorage);
/* 191 */     energyInfuserMaxTransfer = getInteger("energy.machines", "Energy Infuser: Maximum energy transfer rate (EU/t)", energyInfuserMaxTransfer);
/* 192 */     energyStorageTier1Storage = getLong("energy.machines", "Multiblock Energy Storage Tier 1: Energy buffer size (EU)", energyStorageTier1Storage);
/* 193 */     energyStorageTier2Storage = getLong("energy.machines", "Multiblock Energy Storage Tier 2: Energy buffer size (EU)", energyStorageTier2Storage);
/* 194 */     energyStorageTier3Storage = getLong("energy.machines", "Multiblock Energy Storage Tier 3: Energy buffer size (EU)", energyStorageTier3Storage);
/* 195 */     energyStorageTier4Storage = getLong("energy.machines", "Multiblock Energy Storage Tier 4: Energy buffer size (EU)", energyStorageTier4Storage);
/* 196 */     energyStorageTier5Storage = getLong("energy.machines", "Multiblock Energy Storage Tier 5: Energy buffer size (EU)", energyStorageTier5Storage);
/* 197 */     energyStorageTier6Storage = getLong("energy.machines", "Multiblock Energy Storage Tier 6: Energy buffer size (EU)", energyStorageTier6Storage);
/* 198 */     energyStorageTier7Storage = getLong("energy.machines", "Multiblock Energy Storage Tier 7: Energy buffer size (EU)", energyStorageTier7Storage);
/* 199 */     grinderInternalEnergyBufferSize = getInteger("energy.machines", "Mob Grinder: Internal energy buffer size (EU)", grinderInternalEnergyBufferSize);
/* 200 */     grinderExternalEnergyBufferSize = getInteger("energy.machines", "Mob Grinder: Main energy buffer size (EU)", grinderExternalEnergyBufferSize);
/* 201 */     grinderMaxReceive = getInteger("energy.machines", "Mob Grinder: Maximum energy reception rate (EU/t)", grinderMaxReceive);
/* 202 */     grinderEnergyPerKill = getInteger("energy.machines", "Mob Grinder: Amount of energy required to kill entity (EU)", grinderEnergyPerKill);
/* 203 */     wyvernArmorMaxCapacityUpgradePoints = (int)Math.floor((Integer.MAX_VALUE - wyvernArmorBaseStorage) / Math.max(wyvernArmorStoragePerUpgrade, 1)) * IUpgradableItem.EnumUpgrade.RF_CAPACITY.pointConversion;
/* 204 */     wyvernArmorMaxUpgrades = getInteger("tweaks.armor", "Wyvern Armor: Maximum amount of upgrades", wyvernArmorMaxUpgrades);
/* 205 */     wyvernArmorMaxUpgradePoints = getInteger("tweaks.armor", "Wyvern Armor: Maximum amount of upgrade points", wyvernArmorMaxUpgradePoints, wyvernArmorMaxUpgrades, 2147483647);
/* 206 */     wyvernArmorMaxCapacityUpgradePoints = Math.max(Math.min(wyvernArmorMaxUpgradePoints, wyvernArmorMaxCapacityUpgradePoints), 0);
/* 207 */     draconicArmorMaxCapacityUpgradePoints = (int)Math.floor((Integer.MAX_VALUE - draconicArmorBaseStorage) / Math.max(draconicArmorStoragePerUpgrade, 1)) * IUpgradableItem.EnumUpgrade.RF_CAPACITY.pointConversion;
/* 208 */     draconicArmorMaxUpgrades = getInteger("tweaks.armor", "Draconic Armor: Maximum amount of upgrades", draconicArmorMaxUpgrades);
/* 209 */     draconicArmorMaxUpgradePoints = getInteger("tweaks.armor", "Draconic Armor: Maximum amount of upgrade points", draconicArmorMaxUpgradePoints, draconicArmorMaxUpgrades, 2147483647);
/* 210 */     draconicArmorMaxCapacityUpgradePoints = Math.max(Math.min(draconicArmorMaxUpgradePoints, draconicArmorMaxCapacityUpgradePoints), 0);
/* 211 */     wyvernToolsMaxCapacityUpgradePoints = (int)Math.floor((Integer.MAX_VALUE - wyvernToolsBaseStorage) / Math.max(wyvernToolsStoragePerUpgrade, 1)) * IUpgradableItem.EnumUpgrade.RF_CAPACITY.pointConversion;
/* 212 */     wyvernToolsMaxUpgrades = getInteger("tweaks.tools", "Wyvern Tools: Maximum amount of upgrades", wyvernToolsMaxUpgrades, 0, IUpgradableItem.EnumUpgrade.DIG_AOE.pointConversion + 12 * IUpgradableItem.EnumUpgrade.DIG_SPEED.pointConversion + wyvernToolsMaxCapacityUpgradePoints);
/*     */     
/* 214 */     wyvernToolsMaxUpgradePoints = getInteger("tweaks.tools", "Wyvern Tools: Maximum amount of upgrade points", wyvernToolsMaxUpgradePoints, wyvernToolsMaxUpgrades, 2147483647);
/* 215 */     wyvernToolsMaxCapacityUpgradePoints = Math.max(Math.min(wyvernToolsMaxUpgradePoints, wyvernToolsMaxCapacityUpgradePoints), 0);
/* 216 */     draconicToolsMaxCapacityUpgradePoints = (int)Math.floor((Integer.MAX_VALUE - draconicToolsBaseStorage) / Math.max(draconicToolsStoragePerUpgrade, 1)) * IUpgradableItem.EnumUpgrade.RF_CAPACITY.pointConversion;
/* 217 */     draconicToolsMaxUpgrades = getInteger("tweaks.tools", "Draconic Tools: Maximum amount of upgrades", draconicToolsMaxUpgrades, 0, 2 * IUpgradableItem.EnumUpgrade.DIG_AOE.pointConversion + 27 * IUpgradableItem.EnumUpgrade.DIG_SPEED.pointConversion + 4 * IUpgradableItem.EnumUpgrade.DIG_DEPTH.pointConversion + draconicToolsMaxCapacityUpgradePoints);
/*     */ 
/*     */     
/* 220 */     draconicToolsMaxUpgradePoints = getInteger("tweaks.tools", "Draconic Tools: Maximum amount of upgrade points", draconicToolsMaxUpgradePoints, draconicToolsMaxUpgrades, 2147483647);
/* 221 */     draconicToolsMaxCapacityUpgradePoints = Math.max(Math.min(draconicToolsMaxUpgradePoints, draconicToolsMaxCapacityUpgradePoints), 0);
/* 222 */     wyvernWeaponsMaxCapacityUpgradePoints = (int)Math.floor((Integer.MAX_VALUE - wyvernWeaponsBaseStorage) / Math.max(wyvernWeaponsStoragePerUpgrade, 1)) * IUpgradableItem.EnumUpgrade.RF_CAPACITY.pointConversion;
/* 223 */     wyvernWeaponsMaxUpgrades = getInteger("tweaks.weapons", "Wyvern Weapons: Maximum amount of upgrades", wyvernWeaponsMaxUpgrades, 0, 2 * IUpgradableItem.EnumUpgrade.ATTACK_AOE.pointConversion + 8 * IUpgradableItem.EnumUpgrade.ATTACK_DAMAGE.pointConversion + wyvernWeaponsMaxCapacityUpgradePoints);
/*     */     
/* 225 */     wyvernWeaponsMaxUpgradePoints = getInteger("tweaks.weapons", "Wyvern Weapons: Maximum amount of upgrade points", wyvernWeaponsMaxUpgradePoints, wyvernWeaponsMaxUpgrades, 2147483647);
/* 226 */     wyvernWeaponsMaxCapacityUpgradePoints = Math.max(Math.min(wyvernWeaponsMaxUpgradePoints, wyvernWeaponsMaxCapacityUpgradePoints), 0);
/* 227 */     draconicWeaponsMaxCapacityUpgradePoints = (int)Math.floor((Integer.MAX_VALUE - draconicWeaponsBaseStorage) / Math.max(draconicWeaponsStoragePerUpgrade, 1)) * IUpgradableItem.EnumUpgrade.RF_CAPACITY.pointConversion;
/* 228 */     draconicWeaponsMaxUpgrades = getInteger("tweaks.weapons", "Draconic Weapons: Maximum amount of upgrades", draconicWeaponsMaxUpgrades, 0, 3 * IUpgradableItem.EnumUpgrade.ATTACK_AOE.pointConversion + 16 * IUpgradableItem.EnumUpgrade.ARROW_DAMAGE.pointConversion + draconicWeaponsMaxCapacityUpgradePoints);
/*     */     
/* 230 */     draconicWeaponsMaxUpgradePoints = getInteger("tweaks.weapons", "Draconic Weapons: Maximum amount of upgrade points", draconicWeaponsMaxUpgradePoints, draconicWeaponsMaxUpgrades, 2147483647);
/* 231 */     draconicWeaponsMaxCapacityUpgradePoints = Math.max(Math.min(draconicWeaponsMaxUpgradePoints, draconicWeaponsMaxCapacityUpgradePoints), 0);
/* 232 */     wyvernBowMaxCapacityUpgradePoints = (int)Math.floor((Integer.MAX_VALUE - wyvernWeaponsBaseStorage) / Math.max(wyvernWeaponsStoragePerUpgrade, 1)) * IUpgradableItem.EnumUpgrade.RF_CAPACITY.pointConversion;
/* 233 */     wyvernBowMaxUpgrades = getInteger("tweaks.weapons", "Wyvern Bow: Maximum amount of upgrades", wyvernBowMaxUpgrades, 0, 2 * IUpgradableItem.EnumUpgrade.DRAW_SPEED.pointConversion + 9 * IUpgradableItem.EnumUpgrade.ARROW_SPEED.pointConversion + 8 * IUpgradableItem.EnumUpgrade.ARROW_DAMAGE.pointConversion + wyvernBowMaxCapacityUpgradePoints);
/*     */ 
/*     */     
/* 236 */     wyvernBowMaxUpgradePoints = getInteger("tweaks.weapons", "Wyvern Bow: Maximum amount of upgrade points", wyvernBowMaxUpgradePoints, wyvernBowMaxUpgrades, 2147483647);
/* 237 */     wyvernBowMaxCapacityUpgradePoints = Math.max(Math.min(wyvernBowMaxUpgradePoints, wyvernBowMaxCapacityUpgradePoints), 0);
/* 238 */     draconicBowMaxCapacityUpgradePoints = (int)Math.floor((Integer.MAX_VALUE - draconicWeaponsBaseStorage) / Math.max(draconicWeaponsStoragePerUpgrade, 1)) * IUpgradableItem.EnumUpgrade.RF_CAPACITY.pointConversion;
/* 239 */     draconicBowMaxUpgrades = getInteger("tweaks.weapons", "Draconic Bow: Maximum amount of upgrades", draconicBowMaxUpgrades, 0, 2 * IUpgradableItem.EnumUpgrade.DRAW_SPEED.pointConversion + 7 * IUpgradableItem.EnumUpgrade.ARROW_SPEED.pointConversion + 17 * IUpgradableItem.EnumUpgrade.ARROW_DAMAGE.pointConversion + draconicBowMaxCapacityUpgradePoints);
/*     */ 
/*     */     
/* 242 */     draconicBowMaxUpgradePoints = getInteger("tweaks.weapons", "Draconic Bow: Maximum amount of upgrade points", draconicBowMaxUpgradePoints, draconicBowMaxUpgrades, 2147483647);
/* 243 */     draconicBowMaxCapacityUpgradePoints = Math.max(Math.min(draconicBowMaxUpgradePoints, draconicBowMaxCapacityUpgradePoints), 0);
/* 244 */     draconicStaffMaxCapacityUpgradePoints = (int)Math.floor((Integer.MAX_VALUE - draconicToolsBaseStorage * 2 - draconicWeaponsBaseStorage) / Math.max(draconicToolsStoragePerUpgrade + draconicWeaponsStoragePerUpgrade, 1)) * IUpgradableItem.EnumUpgrade.RF_CAPACITY.pointConversion;
/* 245 */     draconicStaffMaxUpgrades = draconicToolsMaxUpgrades + draconicWeaponsMaxUpgrades;
/* 246 */     draconicStaffMaxUpgradePoints = draconicToolsMaxUpgradePoints + draconicWeaponsMaxUpgradePoints;
/* 247 */     draconicStaffMaxCapacityUpgradePoints = Math.max(Math.min(draconicStaffMaxUpgradePoints, draconicStaffMaxCapacityUpgradePoints), 0);
/* 248 */     wyvernCapacitorMaxCapacityUpgradePoints = (int)Math.floor((Integer.MAX_VALUE - wyvernCapacitorBaseStorage) / Math.max(wyvernCapacitorStoragePerUpgrade, 1)) * IUpgradableItem.EnumUpgrade.RF_CAPACITY.pointConversion;
/* 249 */     wyvernCapacitorMaxUpgrades = getInteger("tweaks.tools", "Wyvern Flux Capacitor: Maximum amount of upgrades", wyvernCapacitorMaxUpgrades, 0, wyvernCapacitorMaxCapacityUpgradePoints);
/* 250 */     wyvernCapacitorMaxUpgradePoints = getInteger("tweaks.tools", "Wyvern Flux Capacitor: Maximum amount of upgrade points", wyvernCapacitorMaxUpgradePoints, wyvernCapacitorMaxUpgrades, 2147483647);
/* 251 */     wyvernCapacitorMaxCapacityUpgradePoints = Math.max(Math.min(wyvernCapacitorMaxUpgradePoints, wyvernCapacitorMaxCapacityUpgradePoints), 0);
/* 252 */     draconicCapacitorMaxCapacityUpgradePoints = (int)Math.floor((Integer.MAX_VALUE - draconicCapacitorBaseStorage) / Math.max(draconicCapacitorStoragePerUpgrade, 1)) * IUpgradableItem.EnumUpgrade.RF_CAPACITY.pointConversion;
/* 253 */     draconicCapacitorMaxUpgrades = getInteger("tweaks.tools", "Draconic Flux Capacitor: Maximum amount of upgrades", draconicCapacitorMaxUpgrades, 0, draconicCapacitorMaxCapacityUpgradePoints);
/* 254 */     draconicCapacitorMaxUpgradePoints = getInteger("tweaks.tools", "Draconic Flux Capacitor: Maximum amount of upgrade points", draconicCapacitorMaxUpgradePoints, draconicCapacitorMaxUpgrades, 2147483647);
/* 255 */     draconicCapacitorMaxCapacityUpgradePoints = Math.max(Math.min(draconicCapacitorMaxUpgradePoints, draconicCapacitorMaxCapacityUpgradePoints), 0);
/* 256 */     grinderShouldUseLooting = getBoolean("tweaks.machines", "Mob Grinder: Use Looting enchantment", grinderShouldUseLooting);
/* 257 */     if (config.hasChanged()) {
/* 258 */       config.save();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void finishLoading() {
/* 264 */     if (config == null) {
/*     */       return;
/*     */     }
/* 267 */     energyStorageStructureBlock = getBlock("tweaks.machines", "Multiblock Energy Storage: Main block of structure", Tags.Blocks.REDSTONE_BLOCK.requirePrimary().getBlock(), "WARNING! Changing of this value will replace blocks of all existing Energy Storage Multiblocks!");
/* 268 */     energyStorageStructureBlockMetadata = getInteger("tweaks.machines", "Multiblock Energy Storage: Metadata of main block of structure", energyStorageStructureBlockMetadata, "WARNING! Changing of this value will replace blocks of all existing Energy Storage Multiblocks!");
/* 269 */     if (config.hasChanged()) {
/* 270 */       config.save();
/*     */     }
/*     */   }
/*     */   
/*     */   private static Block getBlock(String category, String propertyName, Block defaultValue, String comment) {
/* 275 */     String defaultName = Block.field_149771_c.func_148750_c(defaultValue);
/* 276 */     Property property = config.get(category, propertyName, defaultName, comment);
/* 277 */     String value = property.getString();
/* 278 */     if (value == null || !value.contains(":")) {
/* 279 */       property.set(defaultName);
/* 280 */       return defaultValue;
/*     */     } 
/* 282 */     String modId = value.substring(0, value.indexOf(':'));
/* 283 */     String name = value.substring(value.indexOf(':') + 1);
/* 284 */     Block block = GameRegistry.findBlock(modId, name);
/* 285 */     if (block == null || block instanceof net.minecraft.block.ITileEntityProvider) {
/* 286 */       property.set(defaultName);
/* 287 */       return defaultValue;
/*     */     } 
/* 289 */     return block;
/*     */   }
/*     */   
/*     */   private static boolean getBoolean(String category, String propertyName, boolean defaultValue) {
/* 293 */     return config.get(category, propertyName, defaultValue).getBoolean(defaultValue);
/*     */   }
/*     */   
/*     */   private static int getInteger(String categoty, String propertyName, int defaultValue) {
/* 297 */     return config.get(categoty, propertyName, defaultValue).getInt(defaultValue);
/*     */   }
/*     */   
/*     */   private static int getInteger(String categoty, String propertyName, int defaultValue, String comment) {
/* 301 */     return config.get(categoty, propertyName, defaultValue, comment).getInt(defaultValue);
/*     */   }
/*     */   
/*     */   private static int getInteger(String category, String propertyName, int defaultValue, int minValue, int maxValue) {
/* 305 */     Property property = config.get(category, propertyName, defaultValue, "", minValue, maxValue);
/* 306 */     int value = property.getInt(defaultValue);
/* 307 */     if (value < minValue) {
/* 308 */       property.set(minValue);
/* 309 */       return minValue;
/*     */     } 
/* 311 */     if (value > maxValue) {
/* 312 */       property.set(maxValue);
/* 313 */       return maxValue;
/*     */     } 
/* 315 */     return value;
/*     */   }
/*     */   
/*     */   private static long getLong(String category, String propertyName, long defaultValue) {
/* 319 */     return (long)config.get(category, propertyName, defaultValue, "", 0.0D, 9.223372036854776E18D).getDouble(defaultValue);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\handler\BalanceConfigHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */