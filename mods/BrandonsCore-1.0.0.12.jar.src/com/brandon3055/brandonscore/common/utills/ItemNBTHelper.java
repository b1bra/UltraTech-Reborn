/*     */ package com.brandon3055.brandonscore.common.utills;
/*     */ 
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ItemNBTHelper
/*     */ {
/*     */   public static NBTTagCompound getCompound(ItemStack stack) {
/*  12 */     if (stack.func_77978_p() == null) stack.func_77982_d(new NBTTagCompound()); 
/*  13 */     return stack.func_77978_p();
/*     */   }
/*     */   
/*     */   public static ItemStack setByte(ItemStack stack, String tag, byte b) {
/*  17 */     NBTTagCompound compound = getCompound(stack);
/*  18 */     compound.func_74774_a(tag, b);
/*  19 */     stack.func_77982_d(compound);
/*  20 */     return stack;
/*     */   }
/*     */   
/*     */   public static ItemStack setBoolean(ItemStack stack, String tag, boolean b) {
/*  24 */     NBTTagCompound compound = getCompound(stack);
/*  25 */     compound.func_74757_a(tag, b);
/*  26 */     stack.func_77982_d(compound);
/*  27 */     return stack;
/*     */   }
/*     */   
/*     */   public static ItemStack setShort(ItemStack stack, String tag, short s) {
/*  31 */     NBTTagCompound compound = getCompound(stack);
/*  32 */     compound.func_74777_a(tag, s);
/*  33 */     stack.func_77982_d(compound);
/*  34 */     return stack;
/*     */   }
/*     */   
/*     */   public static ItemStack setInteger(ItemStack stack, String tag, int i) {
/*  38 */     NBTTagCompound compound = getCompound(stack);
/*  39 */     compound.func_74768_a(tag, i);
/*  40 */     stack.func_77982_d(compound);
/*  41 */     return stack;
/*     */   }
/*     */   
/*     */   public static ItemStack setLong(ItemStack stack, String tag, long i) {
/*  45 */     NBTTagCompound compound = getCompound(stack);
/*  46 */     compound.func_74772_a(tag, i);
/*  47 */     stack.func_77982_d(compound);
/*  48 */     return stack;
/*     */   }
/*     */   
/*     */   public static ItemStack setFloat(ItemStack stack, String tag, float f) {
/*  52 */     NBTTagCompound compound = getCompound(stack);
/*  53 */     compound.func_74776_a(tag, f);
/*  54 */     stack.func_77982_d(compound);
/*  55 */     return stack;
/*     */   }
/*     */   
/*     */   public static ItemStack setDouble(ItemStack stack, String tag, double d) {
/*  59 */     NBTTagCompound compound = getCompound(stack);
/*  60 */     compound.func_74780_a(tag, d);
/*  61 */     stack.func_77982_d(compound);
/*  62 */     return stack;
/*     */   }
/*     */   
/*     */   public static ItemStack setString(ItemStack stack, String tag, String s) {
/*  66 */     NBTTagCompound compound = getCompound(stack);
/*  67 */     compound.func_74778_a(tag, s);
/*  68 */     stack.func_77982_d(compound);
/*  69 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean verifyExistance(ItemStack stack, String tag) {
/*  75 */     NBTTagCompound compound = stack.func_77978_p();
/*  76 */     if (compound == null) {
/*  77 */       return false;
/*     */     }
/*  79 */     return stack.func_77978_p().func_74764_b(tag);
/*     */   }
/*     */   
/*     */   public static byte getByte(ItemStack stack, String tag, byte defaultExpected) {
/*  83 */     return verifyExistance(stack, tag) ? stack.func_77978_p().func_74771_c(tag) : defaultExpected;
/*     */   }
/*     */   
/*     */   public static boolean getBoolean(ItemStack stack, String tag, boolean defaultExpected) {
/*  87 */     return verifyExistance(stack, tag) ? stack.func_77978_p().func_74767_n(tag) : defaultExpected;
/*     */   }
/*     */   
/*     */   public static short getShort(ItemStack stack, String tag, short defaultExpected) {
/*  91 */     return verifyExistance(stack, tag) ? stack.func_77978_p().func_74765_d(tag) : defaultExpected;
/*     */   }
/*     */   
/*     */   public static int getInteger(ItemStack stack, String tag, int defaultExpected) {
/*  95 */     return verifyExistance(stack, tag) ? stack.func_77978_p().func_74762_e(tag) : defaultExpected;
/*     */   }
/*     */   
/*     */   public static long getLong(ItemStack stack, String tag, long defaultExpected) {
/*  99 */     return verifyExistance(stack, tag) ? stack.func_77978_p().func_74763_f(tag) : defaultExpected;
/*     */   }
/*     */   
/*     */   public static float getFloat(ItemStack stack, String tag, float defaultExpected) {
/* 103 */     return verifyExistance(stack, tag) ? stack.func_77978_p().func_74760_g(tag) : defaultExpected;
/*     */   }
/*     */   
/*     */   public static double getDouble(ItemStack stack, String tag, double defaultExpected) {
/* 107 */     return verifyExistance(stack, tag) ? stack.func_77978_p().func_74769_h(tag) : defaultExpected;
/*     */   }
/*     */   
/*     */   public static String getString(ItemStack stack, String tag, String defaultExpected) {
/* 111 */     return verifyExistance(stack, tag) ? stack.func_77978_p().func_74779_i(tag) : defaultExpected;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\commo\\utills\ItemNBTHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */