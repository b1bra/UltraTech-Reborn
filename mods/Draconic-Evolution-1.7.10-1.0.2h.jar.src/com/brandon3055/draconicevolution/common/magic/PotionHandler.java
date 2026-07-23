/*    */ package com.brandon3055.draconicevolution.common.magic;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PotionHandler
/*    */ {
/*    */   public static void init() {}
/*    */   
/*    */   public static class PotionBase
/*    */     extends Potion
/*    */   {
/*    */     public PotionBase(int id, boolean isBad, int colour) {
/* 20 */       super(id, isBad, colour);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean shouldRenderInvText(PotionEffect effect) {
/* 25 */       return false;
/*    */     }
/*    */     
/*    */     public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\magic\PotionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */