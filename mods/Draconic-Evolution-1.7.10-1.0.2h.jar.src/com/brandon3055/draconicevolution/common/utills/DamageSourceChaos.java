/*    */ package com.brandon3055.draconicevolution.common.utills;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.EntityDamageSource;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DamageSourceChaos
/*    */   extends EntityDamageSource
/*    */ {
/*    */   public DamageSourceChaos(Entity entity) {
/* 14 */     super("Chaos", entity);
/* 15 */     func_76348_h();
/*    */   }
/*    */ 
/*    */   
/*    */   public IChatComponent func_151519_b(EntityLivingBase par1EntityLivingBase) {
/* 20 */     return super.func_151519_b(par1EntityLivingBase);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_76363_c() {
/* 26 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\commo\\utills\DamageSourceChaos.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */