/*    */ package com.gamerforea.draconicevolution.util;
/*    */ 
/*    */ import com.gamerforea.eventhelper.imc.client.ISpawnerEntity;
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.boss.BossStatus;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class RenderUtils
/*    */ {
/* 16 */   private static final boolean BOTANIA_LOADED = Loader.isModLoaded("Botania");
/*    */   
/*    */   public static void renderScaledEntity(Entity entity, float f) {
/* 19 */     RenderManager renderManager = RenderManager.field_78727_a;
/* 20 */     if (renderManager == null || renderManager.field_78724_e == null) {
/*    */       return;
/*    */     }
/* 23 */     Render render = renderManager.func_78713_a(entity);
/* 24 */     if (render == null || render.func_147905_a()) {
/*    */       return;
/*    */     }
/* 27 */     float healthScale = BossStatus.field_82828_a;
/* 28 */     int statusBarTime = BossStatus.field_82826_b;
/* 29 */     String bossName = BossStatus.field_82827_c;
/* 30 */     boolean hasColorModifier = BossStatus.field_82825_d;
/*    */     
/* 32 */     float scale = Math.max(entity.field_70130_N, entity.field_70131_O);
/*    */     
/* 34 */     if (scale > 2.0F) {
/* 35 */       float reverseScale = 2.0F / scale;
/* 36 */       GL11.glScalef(reverseScale, reverseScale, reverseScale);
/*    */     } 
/*    */     
/* 39 */     if (BOTANIA_LOADED) {
/* 40 */       renderWithoutBotaniaBar(render, entity, f);
/*    */     } else {
/* 42 */       doRender(render, entity, f);
/*    */     } 
/* 44 */     BossStatus.field_82828_a = healthScale;
/* 45 */     BossStatus.field_82826_b = statusBarTime;
/* 46 */     BossStatus.field_82827_c = bossName;
/* 47 */     BossStatus.field_82825_d = hasColorModifier;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void doRender(Render render, Entity entity, float f) {
/* 58 */     ISpawnerEntity spawnerEntity = (entity instanceof ISpawnerEntity) ? (ISpawnerEntity)entity : null;
/* 59 */     if (spawnerEntity != null) {
/* 60 */       spawnerEntity.preRenderSpawner();
/*    */     }
/* 62 */     for (int pass = 0; pass < 2; pass++) {
/* 63 */       if (entity.shouldRenderInPass(pass)) {
/* 64 */         render.func_76986_a(entity, 0.0D, 0.0D, 0.0D, 0.0F, f);
/*    */       }
/*    */     } 
/* 67 */     if (spawnerEntity != null)
/* 68 */       spawnerEntity.postRenderSpawner(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\gamerforea\draconicevolutio\\util\RenderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */