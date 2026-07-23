/*    */ package com.brandon3055.draconicevolution.common.magic;
/*    */ 
/*    */ import net.minecraft.enchantment.Enchantment;
/*    */ import net.minecraft.enchantment.EnumEnchantmentType;
/*    */ 
/*    */ 
/*    */ public class EnchantmentReaper
/*    */   extends Enchantment
/*    */ {
/*    */   public EnchantmentReaper(int id) {
/* 11 */     super(id, 2, EnumEnchantmentType.weapon);
/* 12 */     func_77322_b("draconicevolution.reaperEnchant");
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_77325_b() {
/* 17 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_77321_a(int level) {
/* 22 */     return 1 + 10 * (level - 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_77317_b(int level) {
/* 27 */     return super.func_77321_a(level) + 50;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\magic\EnchantmentReaper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */