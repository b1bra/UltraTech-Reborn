/*     */ package com.brandon3055.brandonscore.common.utills;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import org.lwjglx.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InfoHelper
/*     */ {
/*     */   public static void addEnergyInfo(ItemStack stack, List<String> list) {
/*  18 */     IElectricItem item = (IElectricItem)stack.func_77973_b();
/*  19 */     String eS = Utills.formatNumber(ElectricItem.manager.getCharge(stack));
/*  20 */     String eM = Utills.formatNumber(item.getMaxCharge(stack));
/*  21 */     list.add(StatCollector.func_74838_a("info.de.charge.txt") + ": " + eS + " / " + eM + " EU");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addLore(ItemStack stack, List<String> list, boolean addLeadingLine) {
/*  26 */     String[] lore = getLore(stack);
/*  27 */     if (addLeadingLine) list.add(""); 
/*  28 */     if (lore == null) {
/*  29 */       list.add("" + EnumChatFormatting.ITALIC + "" + EnumChatFormatting.DARK_PURPLE + "Invalid lore localization! (something is broken)");
/*     */       return;
/*     */     } 
/*  32 */     for (String s : lore) list.add("" + EnumChatFormatting.ITALIC + "" + EnumChatFormatting.DARK_PURPLE + s);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addLore(ItemStack stack, List list) {
/*  39 */     addLore(stack, list, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addEnergyAndLore(ItemStack stack, List<String> list) {
/*  47 */     if (!isShiftKeyDown()) {
/*  48 */       list.add(StatCollector.func_74838_a("info.de.hold.txt") + " " + EnumChatFormatting.AQUA + "" + EnumChatFormatting.ITALIC + StatCollector.func_74838_a("info.de.shift.txt") + EnumChatFormatting.RESET + " " + EnumChatFormatting.GRAY + StatCollector.func_74838_a("info.de.forDetails.txt"));
/*     */     } else {
/*  50 */       addEnergyInfo(stack, list);
/*  51 */       addLore(stack, list);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] getLore(ItemStack stack) {
/*  59 */     String unlocalizeLore = stack.func_77973_b().func_77658_a() + ".lore";
/*  60 */     String rawLore = StatCollector.func_74838_a(unlocalizeLore);
/*     */     
/*  62 */     if (rawLore.contains(unlocalizeLore))
/*     */     {
/*  64 */       return null;
/*     */     }
/*     */     
/*  67 */     String lineCountS = rawLore.substring(0, 1);
/*  68 */     rawLore = rawLore.substring(1);
/*  69 */     int lineCount = 0;
/*     */     
/*     */     try {
/*  72 */       lineCount = Integer.parseInt(lineCountS);
/*  73 */     } catch (NumberFormatException e) {
/*  74 */       LogHelper.error("Invalid Lore Format! Lore myst start with the number of lines \"3Line 1\\nLine 2\\nLine 3\"");
/*     */     } 
/*     */     
/*  77 */     String[] loreLines = new String[lineCount];
/*     */     
/*  79 */     for (int i = 0; i < lineCount; i++) {
/*  80 */       if (rawLore.contains("\\n")) { loreLines[i] = rawLore.substring(0, rawLore.indexOf("\\n")); }
/*  81 */       else { loreLines[i] = rawLore; }
/*  82 */        if (rawLore.contains("\\n")) rawLore = rawLore.substring(rawLore.indexOf("\\n") + 2);
/*     */     
/*     */     } 
/*  85 */     return loreLines;
/*     */   }
/*     */   
/*     */   public static boolean isShiftKeyDown() {
/*  89 */     return (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
/*     */   }
/*     */   
/*     */   public static boolean isCtrlKeyDown() {
/*  93 */     return (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean holdShiftForDetails(List<String> list, boolean inverted) {
/*  98 */     if (isShiftKeyDown() == inverted)
/*  99 */       list.add(StatCollector.func_74838_a("info.de.hold.txt") + " " + EnumChatFormatting.AQUA + "" + EnumChatFormatting.ITALIC + StatCollector.func_74838_a("info.de.shift.txt") + EnumChatFormatting.RESET + " " + EnumChatFormatting.GRAY + StatCollector.func_74838_a("info.de.forDetails.txt")); 
/* 100 */     return isShiftKeyDown();
/*     */   }
/*     */   
/*     */   public static boolean holdShiftForDetails(List list) {
/* 104 */     return holdShiftForDetails(list, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ITC() {
/* 111 */     return "" + EnumChatFormatting.RESET + "" + EnumChatFormatting.DARK_AQUA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String HITC() {
/* 118 */     return "" + EnumChatFormatting.RESET + "" + EnumChatFormatting.ITALIC + "" + EnumChatFormatting.GOLD;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\commo\\utills\InfoHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */