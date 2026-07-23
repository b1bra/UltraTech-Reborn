/*    */ package com.brandon3055.draconicevolution.integration;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.items.armor.CustomArmorHandler;
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import cpw.mods.fml.common.registry.GameRegistry;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraftforge.event.entity.living.LivingAttackEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModHelper
/*    */ {
/*    */   public static boolean isTConInstalled;
/*    */   public static boolean isAvaritiaInstalled;
/*    */   public static boolean isRotaryCraftInstalled;
/*    */   private static Item cleaver;
/*    */   private static Item avaritiaSword;
/*    */   private static Item bedrockSword;
/*    */   
/*    */   public static void init() {
/* 23 */     isTConInstalled = Loader.isModLoaded("TConstruct");
/* 24 */     isAvaritiaInstalled = Loader.isModLoaded("Avaritia");
/* 25 */     isRotaryCraftInstalled = Loader.isModLoaded("RotaryCraft");
/*    */   }
/*    */   
/*    */   public static boolean isHoldingCleaver(EntityPlayer player) {
/* 29 */     if (!isTConInstalled) return false; 
/* 30 */     if (cleaver == null) cleaver = GameRegistry.findItem("TConstruct", "cleaver");
/*    */     
/* 32 */     return (cleaver != null && player.func_70694_bm() != null && player.func_70694_bm().func_77973_b().equals(cleaver));
/*    */   }
/*    */   
/*    */   public static boolean isHoldingAvaritiaSword(EntityPlayer player) {
/* 36 */     if (!isAvaritiaInstalled) return false; 
/* 37 */     if (avaritiaSword == null) avaritiaSword = GameRegistry.findItem("Avaritia", "Infinity_Sword");
/*    */     
/* 39 */     return (avaritiaSword != null && player.func_70694_bm() != null && player.func_70694_bm().func_77973_b().equals(avaritiaSword));
/*    */   }
/*    */   
/*    */   public static boolean isHoldingBedrockSword(EntityPlayer player) {
/* 43 */     if (!isRotaryCraftInstalled) return false; 
/* 44 */     if (bedrockSword == null) bedrockSword = GameRegistry.findItem("RotaryCraft", "rotarycraft_item_bedsword");
/*    */     
/* 46 */     return (bedrockSword != null && player.func_70694_bm() != null && player.func_70694_bm().func_77973_b().equals(bedrockSword));
/*    */   }
/*    */   
/*    */   public static float applyModDamageAdjustments(CustomArmorHandler.ArmorSummery summery, LivingAttackEvent event) {
/* 50 */     if (summery == null) return event.ammount; 
/* 51 */     EntityPlayer attacker = (event.source.func_76346_g() instanceof EntityPlayer) ? (EntityPlayer)event.source.func_76346_g() : null;
/*    */     
/* 53 */     if (attacker == null) {
/* 54 */       return event.ammount;
/*    */     }
/*    */     
/* 57 */     if (isHoldingAvaritiaSword(attacker)) {
/* 58 */       event.entityLiving.field_70172_ad = 0;
/* 59 */       return 300.0F;
/* 60 */     }  if (isHoldingBedrockSword(attacker)) {
/* 61 */       summery.entropy += 10.0F;
/*    */       
/* 63 */       if (summery.entropy > 100.0F) {
/* 64 */         summery.entropy = 100.0F;
/*    */       }
/*    */       
/* 67 */       return Math.max(event.ammount, Math.min(50.0F, summery.protectionPoints));
/* 68 */     }  if (event.source.func_76363_c() || event.source.func_76357_e()) {
/* 69 */       summery.entropy += 3.0F;
/*    */       
/* 71 */       if (summery.entropy > 100.0F) {
/* 72 */         summery.entropy = 100.0F;
/*    */       }
/*    */       
/* 75 */       return event.ammount * 2.0F;
/*    */     } 
/*    */     
/* 78 */     return event.ammount;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\integration\ModHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */