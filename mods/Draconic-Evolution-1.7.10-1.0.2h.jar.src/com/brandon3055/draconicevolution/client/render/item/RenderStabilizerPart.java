/*    */ package com.brandon3055.draconicevolution.client.render.item;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*    */ import com.brandon3055.draconicevolution.client.model.ModelReactorStabilizerCore;
/*    */ import com.brandon3055.draconicevolution.client.model.ModelReactorStabilizerRing;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.client.IItemRenderer;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderStabilizerPart
/*    */   implements IItemRenderer
/*    */ {
/* 15 */   public static ModelReactorStabilizerCore modelBase = new ModelReactorStabilizerCore();
/* 16 */   public static ModelReactorStabilizerCore modelBaseRotors = new ModelReactorStabilizerCore();
/* 17 */   public static ModelReactorStabilizerRing modelRing = new ModelReactorStabilizerRing();
/*    */ 
/*    */   
/*    */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/* 21 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/* 31 */     GL11.glPushMatrix();
/* 32 */     if (type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
/* 33 */       GL11.glTranslated(0.5D, 0.5D, 0.5D);
/*    */     }
/* 35 */     switch (item.func_77960_j()) {
/*    */       case 0:
/* 37 */         ResourceHandler.bindResource("textures/models/reactorStabilizerCore.png");
/* 38 */         modelBase.basePlate.func_78785_a(0.0625F);
/*    */         break;
/*    */       case 1:
/* 41 */         ResourceHandler.bindResource("textures/models/reactorStabilizerCore.png");
/*    */ 
/*    */         
/* 44 */         modelBaseRotors.rotor1R.field_78805_m.clear();
/* 45 */         modelBaseRotors.rotor1R.func_78785_a(0.0625F);
/* 46 */         modelBaseRotors.rotor1R_1.func_78785_a(0.0625F);
/* 47 */         modelBaseRotors.rotor1R_2.func_78785_a(0.0625F);
/* 48 */         modelBaseRotors.rotor1R_3.func_78785_a(0.0625F);
/* 49 */         modelBaseRotors.rotor1R_4.func_78785_a(0.0625F);
/*    */         break;
/*    */       case 2:
/* 52 */         ResourceHandler.bindResource("textures/models/reactorStabilizerCore.png");
/*    */ 
/*    */         
/* 55 */         modelBaseRotors.rotor2R.field_78805_m.clear();
/* 56 */         modelBaseRotors.rotor2R.func_78785_a(0.0625F);
/* 57 */         modelBaseRotors.rotor2R_1.func_78785_a(0.0625F);
/* 58 */         modelBaseRotors.rotor2R_2.func_78785_a(0.0625F);
/* 59 */         modelBaseRotors.rotor2R_3.func_78785_a(0.0625F);
/* 60 */         modelBaseRotors.rotor2R_4.func_78785_a(0.0625F);
/*    */         break;
/*    */       case 3:
/* 63 */         ResourceHandler.bindResource("textures/models/reactorStabilizerCore.png");
/*    */ 
/*    */         
/* 66 */         GL11.glRotatef(30.0F, 0.0F, 0.0F, 1.0F);
/* 67 */         modelBase.rotor1R.func_78785_a(0.0625F);
/* 68 */         modelBase.hub1.func_78785_a(0.0625F);
/* 69 */         GL11.glRotatef(60.0F, 0.0F, 0.0F, -1.0F);
/* 70 */         modelBase.hub2.func_78785_a(0.0625F);
/* 71 */         modelBase.rotor2R.func_78785_a(0.0625F);
/*    */         break;
/*    */       case 4:
/* 74 */         ResourceHandler.bindResource("textures/models/reactorStabilizerRing.png");
/* 75 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/* 76 */         modelRing.func_78088_a(null, -30.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
/*    */         break;
/*    */     } 
/*    */     
/* 80 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\item\RenderStabilizerPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */