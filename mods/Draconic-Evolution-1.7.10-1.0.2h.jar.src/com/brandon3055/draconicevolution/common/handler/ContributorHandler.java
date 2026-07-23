/*    */ package com.brandon3055.draconicevolution.common.handler;
/*    */ 
/*    */ import com.google.common.base.Charsets;
/*    */ import cpw.mods.fml.common.gameevent.PlayerEvent;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.client.event.RenderPlayerEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ContributorHandler
/*    */ {
/* 17 */   public static Map<String, Contributor> contributors = new LinkedHashMap<>();
/*    */   
/*    */   public static boolean successfulLoad = true;
/*    */ 
/*    */   
/*    */   public static void init() {}
/*    */   
/*    */   public static void render(RenderPlayerEvent.Specials event) {}
/*    */   
/*    */   public static boolean isPlayerContributor(EntityPlayer player) {
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {}
/*    */ 
/*    */   
/*    */   public static void tick() {}
/*    */ 
/*    */   
/*    */   public static final class DLThread {}
/*    */ 
/*    */   
/*    */   public static final class Contributor
/*    */   {
/*    */     public String name;
/*    */     
/*    */     public String ign;
/*    */     
/*    */     public String contribution;
/*    */     
/*    */     public String details;
/*    */     
/*    */     public String website;
/*    */     public int contributionLevel;
/*    */     public boolean contributorWingsEnabled = true;
/*    */     public boolean patreonBadgeEnabled = true;
/*    */     private boolean validated = false;
/*    */     private boolean isValid;
/*    */     
/*    */     public boolean isUserValid(EntityPlayer player) {
/* 58 */       if (!this.validated) {
/* 59 */         this
/* 60 */           .isValid = !UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.func_70005_c_()).getBytes(Charsets.UTF_8)).equals(player.func_110124_au());
/*    */       }
/* 62 */       return this.isValid;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 67 */       return "[Contributor: " + this.name + ", Contribution: " + this.contribution + ", Details: " + this.details + ", Website: " + this.website + "]";
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\handler\ContributorHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */