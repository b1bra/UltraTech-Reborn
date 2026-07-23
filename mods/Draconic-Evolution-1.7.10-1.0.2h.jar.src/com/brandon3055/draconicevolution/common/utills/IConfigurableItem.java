/*    */ package com.brandon3055.draconicevolution.common.utills;
/*    */ 
/*    */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IConfigurableItem
/*    */ {
/*    */   List<ItemConfigField> getFields(ItemStack paramItemStack, int paramInt);
/*    */   
/*    */   boolean hasProfiles();
/*    */   
/*    */   public static class ProfileHelper
/*    */   {
/*    */     public static NBTTagCompound getProfileCompound(ItemStack stack) {
/* 21 */       int profile = ItemNBTHelper.getInteger(stack, "ConfigProfile", 0);
/* 22 */       NBTTagCompound stackCompound = ItemNBTHelper.getCompound(stack);
/* 23 */       if (!stackCompound.func_74764_b("ConfigProfiles") && stackCompound.func_150295_c("ConfigProfiles", 10).func_74745_c() < 5) {
/* 24 */         NBTTagList profileList = new NBTTagList();
/* 25 */         for (int i = 0; i < 5; ) { profileList.func_74742_a((NBTBase)new NBTTagCompound()); i++; }
/* 26 */          stackCompound.func_74782_a("ConfigProfiles", (NBTBase)profileList);
/*    */       } 
/*    */       
/* 29 */       return stackCompound.func_150295_c("ConfigProfiles", 10).func_150305_b(profile);
/*    */     }
/*    */     
/*    */     public static void setBoolean(ItemStack stack, String tag, boolean b) {
/* 33 */       getProfileCompound(stack).func_74757_a(tag, b);
/*    */     }
/*    */     
/*    */     public static void setInteger(ItemStack stack, String tag, int i) {
/* 37 */       getProfileCompound(stack).func_74768_a(tag, i);
/*    */     }
/*    */     
/*    */     public static void setFloat(ItemStack stack, String tag, float f) {
/* 41 */       getProfileCompound(stack).func_74776_a(tag, f);
/*    */     }
/*    */     
/*    */     public static void setDouble(ItemStack stack, String tag, double d) {
/* 45 */       getProfileCompound(stack).func_74780_a(tag, d);
/*    */     }
/*    */     
/*    */     public static void setString(ItemStack stack, String tag, String s) {
/* 49 */       getProfileCompound(stack).func_74778_a(tag, s);
/*    */     }
/*    */ 
/*    */     
/*    */     public static boolean getBoolean(ItemStack stack, String tag, boolean defaultExpected) {
/* 54 */       return getProfileCompound(stack).func_74764_b(tag) ? getProfileCompound(stack).func_74767_n(tag) : defaultExpected;
/*    */     }
/*    */     
/*    */     public static int getInteger(ItemStack stack, String tag, int defaultExpected) {
/* 58 */       return getProfileCompound(stack).func_74764_b(tag) ? getProfileCompound(stack).func_74762_e(tag) : defaultExpected;
/*    */     }
/*    */     
/*    */     public static float getFloat(ItemStack stack, String tag, float defaultExpected) {
/* 62 */       return getProfileCompound(stack).func_74764_b(tag) ? getProfileCompound(stack).func_74760_g(tag) : defaultExpected;
/*    */     }
/*    */     
/*    */     public static double getDouble(ItemStack stack, String tag, double defaultExpected) {
/* 66 */       return getProfileCompound(stack).func_74764_b(tag) ? getProfileCompound(stack).func_74769_h(tag) : defaultExpected;
/*    */     }
/*    */     
/*    */     public static String getString(ItemStack stack, String tag, String defaultExpected) {
/* 70 */       return getProfileCompound(stack).func_74764_b(tag) ? getProfileCompound(stack).func_74779_i(tag) : defaultExpected;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\commo\\utills\IConfigurableItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */