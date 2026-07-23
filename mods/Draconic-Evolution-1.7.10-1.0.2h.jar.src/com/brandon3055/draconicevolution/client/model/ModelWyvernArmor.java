/*     */ package com.brandon3055.draconicevolution.client.model;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ 
/*     */ public class ModelWyvernArmor
/*     */   extends ModelBiped {
/*     */   public ModelRenderOBJ head;
/*     */   public ModelRenderOBJ body;
/*     */   public ModelRenderOBJ rightArm;
/*     */   public ModelRenderOBJ leftArm;
/*     */   public ModelRenderOBJ belt;
/*     */   public ModelRenderOBJ rightLeg;
/*     */   public ModelRenderOBJ leftLeg;
/*     */   public ModelRenderOBJ rightBoot;
/*     */   public ModelRenderOBJ leftBoot;
/*     */   
/*     */   public ModelWyvernArmor(float f, boolean isHelmet, boolean isChestPiece, boolean isLeggings, boolean isdBoots) {
/*  22 */     super(f, 0.0F, 128, 128);
/*     */     
/*  24 */     this.field_78116_c = new ModelRenderer((ModelBase)this, 0, 0);
/*  25 */     this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
/*  26 */     this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
/*     */     
/*  28 */     this.field_78115_e = new ModelRenderer((ModelBase)this, 16, 16);
/*  29 */     this.field_78115_e.func_78793_a(0.0F, 0.0F, 0.0F);
/*  30 */     this.field_78115_e.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
/*     */     
/*  32 */     this.field_78113_g = new ModelRenderer((ModelBase)this, 40, 16);
/*  33 */     this.field_78113_g.func_78793_a(5.0F, 2.0F, 0.0F);
/*  34 */     this.field_78113_g.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
/*     */     
/*  36 */     this.field_78112_f = new ModelRenderer((ModelBase)this, 40, 16);
/*  37 */     this.field_78112_f.func_78793_a(-5.0F, 2.0F, 0.0F);
/*  38 */     this.field_78112_f.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
/*     */     
/*  40 */     this.field_78124_i = new ModelRenderer((ModelBase)this, 0, 16);
/*  41 */     this.field_78124_i.func_78793_a(2.0F, 12.0F, 0.0F);
/*  42 */     this.field_78124_i.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
/*     */     
/*  44 */     this.field_78123_h = new ModelRenderer((ModelBase)this, 0, 16);
/*  45 */     this.field_78123_h.func_78793_a(-2.0F, 12.0F, 0.0F);
/*  46 */     this.field_78123_h.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
/*     */ 
/*     */     
/*  49 */     this.head = new ModelRenderOBJ((ModelBase)this, ResourceHandler.getResource("models/armor/WyvernHelmet.obj"), ResourceHandler.getResource("textures/models/armor/WyvernHelmet.png"));
/*  50 */     this.body = new ModelRenderOBJ((ModelBase)this, ResourceHandler.getResource("models/armor/WyvernBody.obj"), ResourceHandler.getResource("textures/models/armor/WyvernBody.png"));
/*  51 */     this.rightArm = new ModelRenderOBJ((ModelBase)this, ResourceHandler.getResource("models/armor/WyvernRightArm.obj"), ResourceHandler.getResource("textures/models/armor/WyvernRightArm.png"));
/*  52 */     this.leftArm = new ModelRenderOBJ((ModelBase)this, ResourceHandler.getResource("models/armor/WyvernLeftArm.obj"), ResourceHandler.getResource("textures/models/armor/WyvernLeftArm.png"));
/*  53 */     this.belt = new ModelRenderOBJ((ModelBase)this, ResourceHandler.getResource("models/armor/WyvernBelt.obj"), ResourceHandler.getResource("textures/models/armor/WyvernBelt.png"));
/*  54 */     this.rightLeg = new ModelRenderOBJ((ModelBase)this, ResourceHandler.getResource("models/armor/WyvernRightLeg.obj"), ResourceHandler.getResource("textures/models/armor/WyvernRightLeg.png"));
/*  55 */     this.leftLeg = new ModelRenderOBJ((ModelBase)this, ResourceHandler.getResource("models/armor/WyvernLeftLeg.obj"), ResourceHandler.getResource("textures/models/armor/WyvernLeftLeg.png"));
/*  56 */     this.rightBoot = new ModelRenderOBJ((ModelBase)this, ResourceHandler.getResource("models/armor/WyvernRightBoot.obj"), ResourceHandler.getResource("textures/models/armor/WyvernRightBoot.png"));
/*  57 */     this.leftBoot = new ModelRenderOBJ((ModelBase)this, ResourceHandler.getResource("models/armor/WyvernLeftBoot.obj"), ResourceHandler.getResource("textures/models/armor/WyvernLeftBoot.png"));
/*     */     
/*  59 */     this.field_78116_c.field_78804_l.clear();
/*  60 */     this.field_78114_d.field_78804_l.clear();
/*  61 */     this.field_78115_e.field_78804_l.clear();
/*  62 */     this.field_78112_f.field_78804_l.clear();
/*  63 */     this.field_78113_g.field_78804_l.clear();
/*  64 */     this.field_78124_i.field_78804_l.clear();
/*  65 */     this.field_78123_h.field_78804_l.clear();
/*     */     
/*  67 */     this.body.field_82908_p = 0.755F;
/*  68 */     this.rightArm.field_82908_p = 0.755F;
/*  69 */     this.leftArm.field_82908_p = 0.755F;
/*     */     
/*  71 */     this.head.field_82908_p = -0.07F;
/*  72 */     this.body.field_82908_p = 0.755F;
/*  73 */     this.body.field_82907_q = -0.03F;
/*  74 */     this.rightArm.field_82908_p = 0.72F;
/*  75 */     this.rightArm.field_82906_o = -0.18F;
/*  76 */     this.rightArm.field_82907_q = 0.0F;
/*  77 */     this.leftArm.field_82908_p = 0.72F;
/*  78 */     this.leftArm.field_82906_o = 0.18F;
/*  79 */     this.leftArm.field_82907_q = 0.0F;
/*  80 */     this.belt.field_82908_p = 0.756F;
/*  81 */     this.belt.field_82907_q = -0.04F;
/*  82 */     this.rightLeg.field_82908_p = 0.6F;
/*  83 */     this.rightLeg.field_82906_o = -0.06F;
/*  84 */     this.leftLeg.field_82908_p = 0.6F;
/*  85 */     this.leftLeg.field_82906_o = 0.06F;
/*  86 */     this.rightBoot.field_82908_p = 0.76F;
/*  87 */     this.rightBoot.field_82906_o = -0.03F;
/*  88 */     this.leftBoot.field_82908_p = 0.76F;
/*  89 */     this.leftBoot.field_82906_o = 0.03F;
/*     */     
/*  91 */     this.leftLeg.scale = 0.06666667F;
/*  92 */     this.rightLeg.scale = 0.06666667F;
/*  93 */     this.leftBoot.scale = 0.06666667F;
/*  94 */     this.rightBoot.scale = 0.06666667F;
/*     */     
/*  96 */     if (isHelmet) {
/*  97 */       this.field_78116_c.func_78792_a(this.head);
/*     */     }
/*  99 */     if (isChestPiece) {
/* 100 */       this.field_78115_e.func_78792_a(this.body);
/* 101 */       this.field_78113_g.func_78792_a(this.leftArm);
/* 102 */       this.field_78112_f.func_78792_a(this.rightArm);
/*     */     } 
/* 104 */     if (isLeggings) {
/* 105 */       this.field_78124_i.func_78792_a(this.leftLeg);
/* 106 */       this.field_78123_h.func_78792_a(this.rightLeg);
/* 107 */       this.field_78115_e.func_78792_a(this.belt);
/*     */     } 
/* 109 */     if (isdBoots) {
/* 110 */       this.field_78124_i.func_78792_a(this.leftBoot);
/* 111 */       this.field_78123_h.func_78792_a(this.rightBoot);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
/* 118 */     if (entity == null)
/* 119 */     { this.field_78117_n = false;
/* 120 */       this.field_78093_q = false;
/* 121 */       this.field_78091_s = false;
/* 122 */       this.field_78118_o = false;
/*     */       
/* 124 */       this.field_78112_f.field_78795_f = 0.0F;
/* 125 */       this.field_78112_f.field_78796_g = 0.0F;
/* 126 */       this.field_78112_f.field_78808_h = 0.0F;
/* 127 */       this.field_78113_g.field_78795_f = 0.0F;
/* 128 */       this.field_78113_g.field_78796_g = 0.0F;
/* 129 */       this.field_78113_g.field_78808_h = 0.0F;
/*     */       
/* 131 */       this.field_78115_e.field_78795_f = 0.0F;
/* 132 */       this.field_78115_e.field_78796_g = 0.0F;
/* 133 */       this.field_78115_e.field_78808_h = 0.0F;
/*     */       
/* 135 */       this.field_78116_c.field_78795_f = 0.0F;
/* 136 */       this.field_78116_c.field_78796_g = 0.0F;
/* 137 */       this.field_78116_c.field_78808_h = 0.0F;
/*     */       
/* 139 */       this.field_78124_i.field_78795_f = 0.0F;
/* 140 */       this.field_78124_i.field_78796_g = 0.0F;
/* 141 */       this.field_78124_i.field_78808_h = 0.0F;
/*     */       
/* 143 */       this.field_78123_h.field_78795_f = 0.0F;
/* 144 */       this.field_78123_h.field_78796_g = 0.0F;
/* 145 */       this.field_78123_h.field_78808_h = 0.0F;
/*     */       
/* 147 */       func_78087_a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, (Entity)null); }
/* 148 */     else { super.func_78087_a(f, f1, f2, f3, f4, f5, entity); }
/*     */     
/* 150 */     this.field_78116_c.func_78785_a(0.07692308F);
/* 151 */     this.field_78112_f.func_78785_a(0.06666667F);
/* 152 */     this.field_78113_g.func_78785_a(0.06666667F);
/* 153 */     this.field_78115_e.func_78785_a(0.06666667F);
/* 154 */     this.field_78123_h.func_78785_a(0.0625F);
/* 155 */     this.field_78124_i.func_78785_a(0.0625F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float scale, Entity p_78087_7_) {
/* 160 */     this.field_78112_f.field_78808_h = 0.0F;
/* 161 */     this.field_78113_g.field_78808_h = 0.0F;
/* 162 */     this.field_78112_f.field_78798_e = 0.0F;
/* 163 */     this.field_78113_g.field_78798_e = 0.0F;
/* 164 */     this.field_78123_h.field_78796_g = 0.0F;
/* 165 */     this.field_78124_i.field_78796_g = 0.0F;
/* 166 */     this.field_78112_f.field_78796_g = 0.0F;
/* 167 */     this.field_78113_g.field_78796_g = 0.0F;
/* 168 */     this.field_78115_e.field_78795_f = 0.0F;
/* 169 */     this.field_78123_h.field_78798_e = 0.1F;
/* 170 */     this.field_78124_i.field_78798_e = 0.1F;
/* 171 */     this.field_78123_h.field_78797_d = 12.0F;
/* 172 */     this.field_78124_i.field_78797_d = 12.0F;
/* 173 */     this.field_78116_c.field_78797_d = 0.0F;
/* 174 */     this.field_78114_d.field_78797_d = 0.0F;
/* 175 */     this.leftLeg.field_78798_e = 0.0F;
/* 176 */     this.rightLeg.field_78798_e = 0.0F;
/* 177 */     this.field_78112_f.field_78808_h = 0.0F;
/* 178 */     this.field_78113_g.field_78808_h = 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\model\ModelWyvernArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */