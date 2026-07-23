/*     */ package com.brandon3055.brandonscore.common.utills;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.registry.GameData;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.command.IEntitySelector;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class Utills {
/*     */   public static IEntitySelector selectLivingBase;
/*     */   
/*     */   static {
/*  17 */     selectLivingBase = (entity -> entity instanceof EntityLivingBase);
/*     */     
/*  19 */     selectPlayer = (entity -> entity instanceof net.minecraft.entity.player.EntityPlayer);
/*     */   } public static IEntitySelector selectPlayer;
/*     */   public static String formatNumber(double value) {
/*  22 */     if (value < 1000.0D)
/*  23 */       return String.valueOf(Math.round(value)); 
/*  24 */     if (value < 1000000.0D)
/*  25 */       return (Math.round(value) / 1000.0D) + "K"; 
/*  26 */     if (value < 1.0E9D)
/*  27 */       return (Math.round(value / 1000.0D) / 1000.0D) + "M"; 
/*  28 */     if (value < 1.0E12D) {
/*  29 */       return (Math.round(value / 1000000.0D) / 1000.0D) + "B";
/*     */     }
/*  31 */     return (Math.round(value / 1.0E9D) / 1000.0D) + "T";
/*     */   }
/*     */   
/*     */   public static String formatNumber(long value) {
/*  35 */     if (value < 1000L)
/*  36 */       return String.valueOf(value); 
/*  37 */     if (value < 1000000L)
/*  38 */       return (Math.round((float)value) / 1000.0D) + "K"; 
/*  39 */     if (value < 1000000000L)
/*  40 */       return (Math.round((float)(value / 1000L)) / 1000.0D) + "M"; 
/*  41 */     if (value < 1000000000000L) {
/*  42 */       return (Math.round((float)(value / 1000000L)) / 1000.0D) + "B";
/*     */     }
/*  44 */     return (Math.round((float)(value / 1000000000L)) / 1000.0D) + "T";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String addCommas(int value) {
/*  51 */     String rawNumber = String.valueOf(value);
/*  52 */     StringBuilder formattedNumber = new StringBuilder();
/*     */     while (true) {
/*  54 */       int end = rawNumber.length();
/*  55 */       int start = Math.max(0, end - 3);
/*  56 */       String part = rawNumber.substring(start, end);
/*  57 */       rawNumber = rawNumber.substring(0, start);
/*  58 */       formattedNumber.insert(0, part + ((formattedNumber.length() > 0) ? "," : ""));
/*  59 */       if (rawNumber.isEmpty()) {
/*  60 */         return formattedNumber.toString();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String addCommas(long value) {
/*  67 */     String rawNumber = String.valueOf(value);
/*  68 */     StringBuilder formattedNumber = new StringBuilder();
/*     */     while (true) {
/*  70 */       int end = rawNumber.length();
/*  71 */       int start = Math.max(0, end - 3);
/*  72 */       String part = rawNumber.substring(start, end);
/*  73 */       rawNumber = rawNumber.substring(0, start);
/*  74 */       formattedNumber.insert(0, part + ((formattedNumber.length() > 0) ? "," : ""));
/*  75 */       if (rawNumber.isEmpty()) {
/*  76 */         return formattedNumber.toString();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getDistanceAtoB(double x1, double y1, double z1, double x2, double y2, double z2) {
/*  91 */     double dx = x1 - x2;
/*  92 */     double dy = y1 - y2;
/*  93 */     double dz = z1 - z2;
/*  94 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getDistanceAtoB(double x1, double z1, double x2, double z2) {
/* 107 */     double dx = x1 - x2;
/* 108 */     double dz = z1 - z2;
/* 109 */     return Math.sqrt(dx * dx + dz * dz);
/*     */   }
/*     */   
/*     */   public static double getDistanceSq(double x1, double y1, double z1, double x2, double y2, double z2) {
/* 113 */     double dx = x1 - x2;
/* 114 */     double dy = y1 - y2;
/* 115 */     double dz = z1 - z2;
/* 116 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */   
/*     */   public static double getDistanceSq(double x1, double z1, double x2, double z2) {
/* 120 */     double dx = x1 - x2;
/* 121 */     double dz = z1 - z2;
/* 122 */     return dx * dx + dz * dz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isConnectedToDedicatedServer() {
/* 129 */     return (FMLCommonHandler.instance().getMinecraftServerInstance() == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack getStackFromName(String name, int meta) {
/* 136 */     if (name.contains("tile.")) {
/* 137 */       name = name.replace("draconicevolution", "DraconicEvolution").replace("tile.", "");
/* 138 */       if (GameData.getBlockRegistry().func_82594_a(name) != null)
/* 139 */         return new ItemStack((Block)GameData.getBlockRegistry().func_82594_a(name), 1, meta); 
/*     */     } 
/* 141 */     if (name.contains("item.")) {
/* 142 */       name = name.replace("draconicevolution", "DraconicEvolution").replace("item.", "");
/* 143 */       if (GameData.getItemRegistry().func_82594_a(name) != null)
/* 144 */         return new ItemStack((Item)GameData.getItemRegistry().func_82594_a(name), 1, meta); 
/*     */     } 
/* 146 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateNeabourBlocks(World world, int x, int y, int z) {
/* 153 */     world.func_147459_d(x, y, z, world.func_147439_a(x, y, z));
/* 154 */     world.func_147459_d(x - 1, y, z, world.func_147439_a(x, y, z));
/* 155 */     world.func_147459_d(x + 1, y, z, world.func_147439_a(x, y, z));
/* 156 */     world.func_147459_d(x, y - 1, z, world.func_147439_a(x, y, z));
/* 157 */     world.func_147459_d(x, y + 1, z, world.func_147439_a(x, y, z));
/* 158 */     world.func_147459_d(x, y, z - 1, world.func_147439_a(x, y, z));
/* 159 */     world.func_147459_d(x, y, z + 1, world.func_147439_a(x, y, z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int determineOrientation(int x, int y, int z, EntityLivingBase entity) {
/* 166 */     if (MathHelper.func_76135_e((float)entity.field_70165_t - x) < 2.0F && MathHelper.func_76135_e((float)entity.field_70161_v - z) < 2.0F) {
/* 167 */       double d0 = entity.field_70163_u + 1.82D - entity.field_70129_M;
/*     */       
/* 169 */       if (d0 - y > 2.0D) return 0;
/*     */       
/* 171 */       if (y - d0 > 0.0D) return 1;
/*     */     
/*     */     } 
/* 174 */     int l = MathHelper.func_76128_c((entity.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3;
/* 175 */     return (l == 0) ? 3 : ((l == 1) ? 4 : ((l == 2) ? 2 : ((l == 3) ? 5 : 0)));
/*     */   }
/*     */   
/*     */   public static double round(double number, double multiplier) {
/* 179 */     return Math.round(number * multiplier) / multiplier;
/*     */   }
/*     */   
/*     */   public static int getNearestMultiple(int number, int multiple) {
/* 183 */     int result = number;
/*     */     
/* 185 */     if (number < 0) result *= -1;
/*     */     
/* 187 */     if (result % multiple == 0) return number; 
/* 188 */     if (result % multiple < multiple / 2) { result -= result % multiple; }
/* 189 */     else { result = result + multiple - result % multiple; }
/*     */     
/* 191 */     if (number < 0) result *= -1;
/*     */     
/* 193 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int toInt(double d) {
/* 200 */     return (int)d;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\commo\\utills\Utills.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */