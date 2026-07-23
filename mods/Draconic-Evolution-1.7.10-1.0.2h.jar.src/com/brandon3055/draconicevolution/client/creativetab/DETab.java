/*    */ package com.brandon3055.draconicevolution.client.creativetab;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.api.IDraconicElectricItem;
/*    */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*    */ import com.brandon3055.draconicevolution.common.ModItems;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import com.brandon3055.brandonscore.common.tags.Tags;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class DETab extends CreativeTabs {
/*    */   private String label;
/*    */   private int tab;
/*    */   static ItemStack iconStackStaff;
/*    */   
/*    */   public static void initialize() {
/* 20 */     if (ModItems.isEnabled(ModItems.draconicDestructionStaff))
/* 21 */     { iconStackStaff = ((IDraconicElectricItem)ModItems.draconicDestructionStaff).makeChargedStack(); }
/* 22 */     else { iconStackStaff = Tags.Items.STICK.requireStack(); }
/*    */   
/*    */   }
/*    */   public DETab(int id, String modid, String label, int tab) {
/* 26 */     super(id, modid);
/* 27 */     this.label = label;
/* 28 */     this.tab = tab;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public ItemStack func_151244_d() {
/* 35 */     if (this.tab == 0) return iconStackStaff; 
/* 36 */     if (ModBlocks.isEnabled((Block)ModBlocks.energyInfuser)) return new ItemStack((Block)ModBlocks.energyInfuser); 
/* 37 */     return Tags.Items.ENDER_EYE.requireStack();
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public Item func_78016_d() {
/* 43 */     return func_151244_d().func_77973_b();
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_78013_b() {
/* 48 */     return this.label;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\creativetab\DETab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */