/*    */ package com.brandon3055.draconicevolution.common.items;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*    */ import com.brandon3055.draconicevolution.common.ModItems;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReactorStabiliserPart
/*    */   extends ItemDE
/*    */ {
/* 19 */   public static final Map<Integer, String> parts = new HashMap<Integer, String>()
/*    */     {
/*    */     
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReactorStabiliserPart() {
/* 28 */     func_77655_b("reactorCraftingPart");
/* 29 */     func_77637_a(DraconicEvolution.tabBlocksItems);
/* 30 */     func_77627_a(true);
/* 31 */     ModItems.register(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_150895_a(Item item, CreativeTabs p_150895_2_, List<ItemStack> list) {
/* 36 */     for (Integer i : parts.keySet()) list.add(new ItemStack(item, 1, i.intValue()));
/*    */   
/*    */   }
/*    */   
/*    */   public String func_77667_c(ItemStack itemStack) {
/* 41 */     if (parts.containsKey(Integer.valueOf(itemStack.func_77960_j())))
/* 42 */       return super.func_77667_c(itemStack) + "." + (String)parts.get(Integer.valueOf(itemStack.func_77960_j())); 
/* 43 */     return super.func_77667_c(itemStack);
/*    */   }
/*    */   
/*    */   public void func_94581_a(IIconRegister iconRegister) {}
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\ReactorStabiliserPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */