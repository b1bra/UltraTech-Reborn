/*    */ package com.brandon3055.draconicevolution.client.handler;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.client.render.particle.ParticleDistortion;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.particle.EntityFX;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class ParticleHandler {
/* 12 */   private static Minecraft mc = Minecraft.func_71410_x();
/* 13 */   private static World theWorld = (World)mc.field_71441_e;
/*    */   
/*    */   public static EntityFX spawnParticle(String particleName, double x, double y, double z, double motionX, double motionY, double motionZ, float scale) {
/* 16 */     if (mc != null && mc.field_71451_h != null && mc.field_71452_i != null) {
/* 17 */       ParticleDistortion particleDistortion; int var14 = mc.field_71474_y.field_74362_aa;
/* 18 */       if (var14 == 1 && theWorld.field_73012_v.nextInt(3) == 0) {
/* 19 */         var14 = 2;
/*    */       }
/* 21 */       double var15 = mc.field_71451_h.field_70165_t - x;
/* 22 */       double var17 = mc.field_71451_h.field_70163_u - y;
/* 23 */       double var19 = mc.field_71451_h.field_70161_v - z;
/* 24 */       EntityFX var21 = null;
/* 25 */       double var22 = 16.0D;
/* 26 */       if (var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22)
/* 27 */         return null; 
/* 28 */       if (var14 > 1) {
/* 29 */         return null;
/*    */       }
/* 31 */       if (particleName.equals("distortionParticle")) {
/* 32 */         particleDistortion = new ParticleDistortion(theWorld, x, y, z, (float)motionX, (float)motionY, (float)motionZ, scale);
/*    */       }
/*    */       
/* 35 */       mc.field_71452_i.func_78873_a((EntityFX)particleDistortion);
/* 36 */       return (EntityFX)particleDistortion;
/*    */     } 
/*    */     
/* 39 */     return null;
/*    */   }
/*    */   
/*    */   public static EntityFX spawnCustomParticle(EntityFX particle) {
/* 43 */     return spawnCustomParticle(particle, 64.0D);
/*    */   }
/*    */   
/*    */   public static EntityFX spawnCustomParticle(EntityFX particle, double vewRange) {
/* 47 */     if (mc != null && mc.field_71451_h != null && mc.field_71452_i != null) {
/* 48 */       int var14 = mc.field_71474_y.field_74362_aa;
/* 49 */       if (var14 == 1 && theWorld.field_73012_v.nextInt(3) == 0) {
/* 50 */         var14 = 2;
/*    */       }
/* 52 */       if (!isInRange(particle.field_70165_t, particle.field_70163_u, particle.field_70161_v, vewRange))
/* 53 */         return null; 
/* 54 */       if (var14 > 1) {
/* 55 */         return null;
/*    */       }
/* 57 */       mc.field_71452_i.func_78873_a(particle);
/* 58 */       return particle;
/*    */     } 
/*    */     
/* 61 */     return null;
/*    */   }
/*    */   
/*    */   public static boolean isInRange(double x, double y, double z, double vewRange) {
/* 65 */     if (mc == null || mc.field_71451_h == null || mc.field_71452_i == null) return false;
/*    */     
/* 67 */     double var15 = mc.field_71451_h.field_70165_t - x;
/* 68 */     double var17 = mc.field_71451_h.field_70163_u - y;
/* 69 */     double var19 = mc.field_71451_h.field_70161_v - z;
/* 70 */     return (var15 * var15 + var17 * var17 + var19 * var19 <= vewRange * vewRange);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\handler\ParticleHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */