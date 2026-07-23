/*    */ package com.brandon3055.draconicevolution.common.items;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*    */ import com.brandon3055.draconicevolution.common.ModItems;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class AwakenedCore
/*    */   extends ItemDE {
/*    */   public AwakenedCore() {
/* 10 */     func_77655_b("awakenedCore");
/* 11 */     func_77637_a(DraconicEvolution.tabBlocksItems);
/* 12 */     ModItems.register(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasEffect(ItemStack par1ItemStack, int pass) {
/* 17 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\AwakenedCore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */