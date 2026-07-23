/*    */ package com.brandon3055.draconicevolution.common.items.tools;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*    */ import com.brandon3055.draconicevolution.common.ModItems;
/*    */ import com.brandon3055.draconicevolution.common.items.ItemDE;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.StatCollector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnderArrow
/*    */   extends ItemDE
/*    */ {
/*    */   public EnderArrow() {
/* 21 */     func_77655_b("enderArrow");
/* 22 */     func_77637_a(DraconicEvolution.tabToolsWeapons);
/* 23 */     ModItems.register(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_94581_a(IIconRegister iconRegister) {
/* 28 */     this.field_77791_bV = iconRegister.func_94245_a("draconicevolution:ender_arrow");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_77624_a(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
/* 34 */     list.add(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.ITALIC + StatCollector.func_74838_a("info.arrowInfo.txt"));
/* 35 */     list.add(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.ITALIC + StatCollector.func_74838_a("info.arrowInfo0.txt"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\EnderArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */