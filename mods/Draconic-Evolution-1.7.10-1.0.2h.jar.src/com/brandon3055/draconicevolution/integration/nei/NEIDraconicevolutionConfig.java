/*    */ package com.brandon3055.draconicevolution.integration.nei;
/*    */ 
/*    */ import codechicken.nei.api.API;
/*    */ import codechicken.nei.api.IConfigureNEI;
/*    */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*    */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NEIDraconicevolutionConfig
/*    */   implements IConfigureNEI
/*    */ {
/*    */   public void loadConfig() {
/* 17 */     API.hideItem(new ItemStack((Block)ModBlocks.placedItem));
/* 18 */     API.hideItem(new ItemStack((Block)ModBlocks.invisibleMultiblock));
/* 19 */     API.hideItem(new ItemStack(ModBlocks.safetyFlame));
/* 20 */     API.hideItem(new ItemStack((Block)ModBlocks.portal));
/* 21 */     API.registerNEIGuiHandler(new DENEIGuiHandler());
/*    */     
/* 23 */     LogHelper.info("Added NEI integration");
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 28 */     return "DraconicEvolution-NEIConfig";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 33 */     return "1.0.2h";
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\integration\nei\NEIDraconicevolutionConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */