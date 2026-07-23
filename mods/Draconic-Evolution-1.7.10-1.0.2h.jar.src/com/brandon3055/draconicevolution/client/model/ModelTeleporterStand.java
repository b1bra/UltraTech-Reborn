/*    */ package com.brandon3055.draconicevolution.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelTeleporterStand
/*    */   extends ModelBase
/*    */ {
/*    */   ModelRenderer plate;
/*    */   ModelRenderer holders;
/*    */   ModelRenderer support;
/*    */   ModelRenderer collum;
/*    */   ModelRenderer baseTop;
/*    */   ModelRenderer baseBottom;
/*    */   
/*    */   public ModelTeleporterStand() {
/* 19 */     this.field_78090_t = 64;
/* 20 */     this.field_78089_u = 64;
/*    */     
/* 22 */     this.plate = new ModelRenderer(this, 0, 0);
/* 23 */     this.plate.func_78789_a(0.0F, 1.0F, 0.0F, 7, 1, 7);
/* 24 */     this.plate.func_78793_a(-3.5F, -7.8F, -2.0F);
/* 25 */     this.plate.func_78787_b(64, 64);
/* 26 */     setRotationDegree(this.plate, -30, 0, 0);
/*    */     
/* 28 */     this.holders = new ModelRenderer(this, 0, 0);
/* 29 */     this.holders.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
/* 30 */     this.holders.func_78789_a(5.55F, 0.0F, 0.0F, 1, 1, 1);
/* 31 */     this.holders.func_78793_a(-3.3F, -4.7F, 2.86F);
/* 32 */     this.holders.func_78787_b(64, 64);
/* 33 */     setRotationDegree(this.holders, -30, 0, 0);
/*    */     
/* 35 */     this.support = new ModelRenderer(this, 0, 8);
/* 36 */     this.support.func_78789_a(0.0F, 0.0F, 0.1F, 4, 1, 1);
/* 37 */     this.support.func_78793_a(-2.0F, -5.0F, -2.0F);
/* 38 */     this.support.func_78787_b(64, 64);
/* 39 */     setRotation(this.support, 0.0F, 0.0F, 0.0F);
/*    */     
/* 41 */     this.collum = new ModelRenderer(this, 28, 0);
/* 42 */     this.collum.func_78789_a(0.0F, 0.0F, 0.0F, 4, 12, 4);
/* 43 */     this.collum.func_78793_a(-2.0F, -4.0F, -2.0F);
/* 44 */     this.collum.func_78787_b(64, 64);
/* 45 */     setRotation(this.collum, 0.0F, 0.0F, 0.0F);
/*    */     
/* 47 */     this.baseTop = new ModelRenderer(this, 0, 27);
/* 48 */     this.baseTop.func_78789_a(0.0F, 0.0F, 0.0F, 6, 1, 6);
/* 49 */     this.baseTop.func_78793_a(-3.0F, 6.0F, -3.0F);
/* 50 */     this.baseTop.func_78787_b(64, 64);
/* 51 */     setRotation(this.baseTop, 0.0F, 0.0F, 0.0F);
/*    */     
/* 53 */     this.baseBottom = new ModelRenderer(this, 0, 16);
/* 54 */     this.baseBottom.func_78789_a(0.0F, 0.0F, 0.0F, 10, 1, 10);
/* 55 */     this.baseBottom.func_78793_a(-5.0F, 7.0F, -5.0F);
/* 56 */     this.baseBottom.func_78787_b(64, 64);
/* 57 */     setRotationDegree(this.baseBottom, 0, 0, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render() {
/* 62 */     float scale = 0.0625F;
/*    */     
/* 64 */     this.plate.func_78785_a(0.0625F);
/* 65 */     this.holders.func_78785_a(0.0625F);
/* 66 */     this.support.func_78785_a(0.0625F);
/* 67 */     this.collum.func_78785_a(0.0625F);
/* 68 */     this.baseTop.func_78785_a(0.0625F);
/* 69 */     this.baseBottom.func_78785_a(0.0625F);
/*    */   }
/*    */   
/*    */   private void setRotation(ModelRenderer model, float x, float y, float z) {
/* 73 */     model.field_78795_f = x;
/* 74 */     model.field_78796_g = y;
/* 75 */     model.field_78808_h = z;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void setRotationDegree(ModelRenderer model, int x, int y, int z) {
/* 82 */     model.field_78795_f = x / 57.295776F;
/* 83 */     model.field_78796_g = y / 57.295776F;
/* 84 */     model.field_78808_h = z / 57.295776F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\model\ModelTeleporterStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */