/*    */ package com.gamerforea.draconicevolution.minetweaker;
/*    */ 
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import cpw.mods.fml.common.Optional.Method;
/*    */ import minetweaker.MineTweakerAPI;
/*    */ 
/*    */ public final class MineTweakerIntegration
/*    */ {
/*    */   private static final String MINE_TWEAKER_MODID = "MineTweaker3";
/*    */   
/*    */   public static void init() {
/* 12 */     if (Loader.isModLoaded("MineTweaker3"))
/* 13 */       init0(); 
/*    */   }
/*    */   
/*    */   @Method(modid = "MineTweaker3")
/*    */   private static void init0() {
/* 18 */     MineTweakerAPI.registerClass(MobSouls.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\gamerforea\draconicevolution\minetweaker\MineTweakerIntegration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */