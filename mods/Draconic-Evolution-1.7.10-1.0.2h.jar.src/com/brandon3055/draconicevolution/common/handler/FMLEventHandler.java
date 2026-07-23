/*    */ package com.brandon3055.draconicevolution.common.handler;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.items.armor.CustomArmorHandler;
/*    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*    */ import cpw.mods.fml.common.gameevent.PlayerEvent;
/*    */ import cpw.mods.fml.common.gameevent.TickEvent;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public final class FMLEventHandler
/*    */ {
/*    */   @SubscribeEvent
/*    */   public void onPlayerTick(TickEvent.PlayerTickEvent event) {
/* 13 */     if (event.phase == TickEvent.Phase.START)
/* 14 */       CustomArmorHandler.onPlayerTick(event); 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
/* 19 */     EntityPlayer player = event.player;
/*    */     
/* 21 */     if (!player.field_70122_E) {
/* 22 */       CustomArmorHandler.ArmorSummery summery = (new CustomArmorHandler.ArmorSummery()).getSummery(player);
/*    */       
/* 24 */       if (summery != null && summery.flight[0]) {
/* 25 */         player.field_71075_bZ.field_75101_c = true;
/* 26 */         player.field_71075_bZ.field_75100_b = true;
/* 27 */         player.field_70143_R = 0.0F;
/* 28 */         player.func_71016_p();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\handler\FMLEventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */