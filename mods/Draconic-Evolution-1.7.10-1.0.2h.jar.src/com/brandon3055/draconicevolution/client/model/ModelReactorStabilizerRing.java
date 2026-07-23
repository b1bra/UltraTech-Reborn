/*     */ package com.brandon3055.draconicevolution.client.model;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelReactorStabilizerRing
/*     */   extends ModelBase
/*     */ {
/*     */   public ModelRenderer ringElement1;
/*     */   public ModelRenderer ringElement2;
/*     */   public ModelRenderer ringElement3;
/*     */   public ModelRenderer ringElement4;
/*     */   public ModelRenderer ringElement5;
/*     */   public ModelRenderer ringElement6;
/*     */   public ModelRenderer ringElement7;
/*     */   public ModelRenderer ringElement8;
/*     */   public ModelRenderer ringElement9;
/*     */   public ModelRenderer ringElement10;
/*     */   public ModelRenderer ringElement11;
/*     */   public ModelRenderer ringElement12;
/*     */   public ModelRenderer hing1;
/*     */   public ModelRenderer hing2;
/*     */   public ModelRenderer hing3;
/*     */   public ModelRenderer hing4;
/*     */   public ModelRenderer emitter1;
/*     */   public ModelRenderer emitter2;
/*     */   public ModelRenderer emitter3;
/*     */   public ModelRenderer emitter4;
/*     */   
/*     */   public ModelReactorStabilizerRing() {
/*  36 */     this.field_78090_t = 32;
/*  37 */     this.field_78089_u = 16;
/*  38 */     this.ringElement9 = new ModelRenderer(this, 0, 0);
/*  39 */     this.ringElement9.func_78793_a(6.7F, 0.01F, -1.55F);
/*  40 */     this.ringElement9.func_78790_a(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
/*  41 */     setRotateAngle(this.ringElement9, 0.0F, 2.0943952F, 0.0F);
/*  42 */     this.ringElement10 = new ModelRenderer(this, 0, 0);
/*  43 */     this.ringElement10.func_78793_a(1.55F, 0.02F, -6.7F);
/*  44 */     this.ringElement10.func_78790_a(-4.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
/*  45 */     setRotateAngle(this.ringElement10, 0.0F, 2.6179938F, 0.0F);
/*  46 */     this.ringElement1 = new ModelRenderer(this, 0, 0);
/*  47 */     this.ringElement1.func_78793_a(-2.0F, 0.01F, 6.6F);
/*  48 */     this.ringElement1.func_78790_a(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
/*  49 */     this.ringElement7 = new ModelRenderer(this, 0, 0);
/*  50 */     this.ringElement7.func_78793_a(-1.55F, 0.02F, -6.7F);
/*  51 */     this.ringElement7.func_78790_a(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
/*  52 */     setRotateAngle(this.ringElement7, 0.0F, -2.6179938F, 0.0F);
/*  53 */     this.hing4 = new ModelRenderer(this, 15, 0);
/*  54 */     this.hing4.func_78793_a(1.75F, -0.5F, 6.95F);
/*  55 */     this.hing4.func_78790_a(0.0F, 0.0F, 0.0F, 1, 1, 7, 0.0F);
/*  56 */     setRotateAngle(this.hing4, 0.0F, -1.571669F, 0.0F);
/*  57 */     this.ringElement8 = new ModelRenderer(this, 0, 0);
/*  58 */     this.ringElement8.func_78793_a(-6.7F, 0.01F, -1.55F);
/*  59 */     this.ringElement8.func_78790_a(-4.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
/*  60 */     setRotateAngle(this.ringElement8, 0.0F, -2.0943952F, 0.0F);
/*  61 */     this.emitter4 = new ModelRenderer(this, 0, 2);
/*  62 */     this.emitter4.func_78793_a(2.25F, -0.3F, 7.1F);
/*  63 */     this.emitter4.func_78790_a(-0.5F, -0.8F, 0.0F, 3, 1, 9, 0.0F);
/*  64 */     setRotateAngle(this.emitter4, 0.0F, -1.5707964F, 0.0F);
/*  65 */     this.emitter2 = new ModelRenderer(this, 0, 2);
/*  66 */     this.emitter2.func_78793_a(-7.1F, -0.3F, 2.25F);
/*  67 */     this.emitter2.func_78790_a(-0.5F, -0.8F, 0.0F, 3, 1, 9, 0.0F);
/*  68 */     setRotateAngle(this.emitter2, 0.0F, 3.1415927F, 0.0F);
/*  69 */     this.emitter1 = new ModelRenderer(this, 0, 2);
/*  70 */     this.emitter1.func_78793_a(7.1F, -0.3F, -2.25F);
/*  71 */     this.emitter1.func_78790_a(-0.5F, -0.8F, 0.0F, 3, 1, 9, 0.0F);
/*  72 */     this.hing2 = new ModelRenderer(this, 15, 0);
/*  73 */     this.hing2.func_78793_a(6.95F, -0.5F, -1.75F);
/*  74 */     this.hing2.func_78790_a(0.0F, 0.0F, 0.0F, 1, 1, 7, 0.0F);
/*  75 */     this.ringElement2 = new ModelRenderer(this, 0, 0);
/*  76 */     this.ringElement2.func_78793_a(2.0F, 0.01F, -6.6F);
/*  77 */     this.ringElement2.func_78790_a(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
/*  78 */     setRotateAngle(this.ringElement2, 0.0F, 3.1415927F, 0.0F);
/*  79 */     this.ringElement5 = new ModelRenderer(this, 0, 0);
/*  80 */     this.ringElement5.func_78793_a(-6.7F, 0.01F, 1.55F);
/*  81 */     this.ringElement5.func_78790_a(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
/*  82 */     setRotateAngle(this.ringElement5, 0.0F, -1.0471976F, 0.0F);
/*  83 */     this.ringElement12 = new ModelRenderer(this, 0, 0);
/*  84 */     this.ringElement12.func_78793_a(1.55F, 0.02F, 6.7F);
/*  85 */     this.ringElement12.func_78790_a(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
/*  86 */     setRotateAngle(this.ringElement12, 0.0F, 0.5235988F, 0.0F);
/*  87 */     this.hing1 = new ModelRenderer(this, 15, 0);
/*  88 */     this.hing1.func_78793_a(-7.45F, -0.5F, -1.75F);
/*  89 */     this.hing1.func_78790_a(0.0F, 0.0F, 0.0F, 1, 1, 7, 0.0F);
/*  90 */     this.ringElement6 = new ModelRenderer(this, 0, 0);
/*  91 */     this.ringElement6.func_78793_a(-1.55F, 0.02F, 6.7F);
/*  92 */     this.ringElement6.func_78790_a(-4.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
/*  93 */     setRotateAngle(this.ringElement6, 0.0F, -0.5235988F, 0.0F);
/*  94 */     this.ringElement11 = new ModelRenderer(this, 0, 0);
/*  95 */     this.ringElement11.func_78793_a(6.7F, 0.01F, 1.55F);
/*  96 */     this.ringElement11.func_78790_a(-4.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
/*  97 */     setRotateAngle(this.ringElement11, 0.0F, 1.0471976F, 0.0F);
/*  98 */     this.emitter3 = new ModelRenderer(this, 0, 2);
/*  99 */     this.emitter3.func_78793_a(-2.25F, -0.3F, -7.1F);
/* 100 */     this.emitter3.func_78790_a(-0.5F, -0.8F, 0.0F, 3, 1, 9, 0.0F);
/* 101 */     setRotateAngle(this.emitter3, 0.0F, 1.5707964F, 0.0F);
/* 102 */     this.ringElement4 = new ModelRenderer(this, 0, 0);
/* 103 */     this.ringElement4.func_78793_a(-6.6F, 0.0F, -2.0F);
/* 104 */     this.ringElement4.func_78790_a(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
/* 105 */     setRotateAngle(this.ringElement4, 0.0F, -1.5707964F, 0.0F);
/* 106 */     this.hing3 = new ModelRenderer(this, 15, 0);
/* 107 */     this.hing3.func_78793_a(1.75F, -0.5F, -7.45F);
/* 108 */     this.hing3.func_78790_a(0.0F, 0.0F, 0.0F, 1, 1, 7, 0.0F);
/* 109 */     setRotateAngle(this.hing3, 0.0F, -1.571669F, 0.0F);
/* 110 */     this.ringElement3 = new ModelRenderer(this, 0, 0);
/* 111 */     this.ringElement3.func_78793_a(6.6F, 0.0F, 2.0F);
/* 112 */     this.ringElement3.func_78790_a(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
/* 113 */     setRotateAngle(this.ringElement3, 0.0F, 1.5707964F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_78088_a(Entity entity, float embitterRotation, float brightness, float f2, float f3, float f4, float scale) {
/* 119 */     this.ringElement1.func_78785_a(scale);
/* 120 */     this.ringElement2.func_78785_a(scale);
/* 121 */     this.ringElement3.func_78785_a(scale);
/* 122 */     this.ringElement4.func_78785_a(scale);
/* 123 */     this.ringElement5.func_78785_a(scale);
/* 124 */     this.ringElement6.func_78785_a(scale);
/* 125 */     this.ringElement7.func_78785_a(scale);
/* 126 */     this.ringElement8.func_78785_a(scale);
/* 127 */     this.ringElement9.func_78785_a(scale);
/* 128 */     this.ringElement10.func_78785_a(scale);
/* 129 */     this.ringElement11.func_78785_a(scale);
/* 130 */     this.ringElement12.func_78785_a(scale);
/*     */     
/* 132 */     GL11.glPushMatrix();
/* 133 */     GL11.glTranslated(this.hing1.field_82906_o, this.hing1.field_82908_p, this.hing1.field_82907_q);
/* 134 */     GL11.glTranslated((this.hing1.field_78800_c * scale), (this.hing1.field_78797_d * scale), (this.hing1.field_78798_e * scale));
/* 135 */     GL11.glScaled(0.5D, 0.5D, 0.5D);
/* 136 */     GL11.glTranslated(-this.hing1.field_82906_o, -this.hing1.field_82908_p, -this.hing1.field_82907_q);
/* 137 */     GL11.glTranslated((-this.hing1.field_78800_c * scale), (-this.hing1.field_78797_d * scale), (-this.hing1.field_78798_e * scale));
/* 138 */     this.hing1.func_78785_a(scale);
/* 139 */     GL11.glPopMatrix();
/* 140 */     GL11.glPushMatrix();
/* 141 */     GL11.glTranslated(this.hing2.field_82906_o, this.hing2.field_82908_p, this.hing2.field_82907_q);
/* 142 */     GL11.glTranslated((this.hing2.field_78800_c * scale), (this.hing2.field_78797_d * scale), (this.hing2.field_78798_e * scale));
/* 143 */     GL11.glScaled(0.5D, 0.5D, 0.5D);
/* 144 */     GL11.glTranslated(-this.hing2.field_82906_o, -this.hing2.field_82908_p, -this.hing2.field_82907_q);
/* 145 */     GL11.glTranslated((-this.hing2.field_78800_c * scale), (-this.hing2.field_78797_d * scale), (-this.hing2.field_78798_e * scale));
/* 146 */     this.hing2.func_78785_a(scale);
/* 147 */     GL11.glPopMatrix();
/* 148 */     GL11.glPushMatrix();
/* 149 */     GL11.glTranslated(this.hing3.field_82906_o, this.hing3.field_82908_p, this.hing3.field_82907_q);
/* 150 */     GL11.glTranslated((this.hing3.field_78800_c * scale), (this.hing3.field_78797_d * scale), (this.hing3.field_78798_e * scale));
/* 151 */     GL11.glScaled(0.5D, 0.5D, 0.5D);
/* 152 */     GL11.glTranslated(-this.hing3.field_82906_o, -this.hing3.field_82908_p, -this.hing3.field_82907_q);
/* 153 */     GL11.glTranslated((-this.hing3.field_78800_c * scale), (-this.hing3.field_78797_d * scale), (-this.hing3.field_78798_e * scale));
/* 154 */     this.hing3.func_78785_a(scale);
/* 155 */     GL11.glPopMatrix();
/* 156 */     GL11.glPushMatrix();
/* 157 */     GL11.glTranslated(this.hing4.field_82906_o, this.hing4.field_82908_p, this.hing4.field_82907_q);
/* 158 */     GL11.glTranslated((this.hing4.field_78800_c * scale), (this.hing4.field_78797_d * scale), (this.hing4.field_78798_e * scale));
/* 159 */     GL11.glScaled(0.5D, 0.5D, 0.5D);
/* 160 */     GL11.glTranslated(-this.hing4.field_82906_o, -this.hing4.field_82908_p, -this.hing4.field_82907_q);
/* 161 */     GL11.glTranslated((-this.hing4.field_78800_c * scale), (-this.hing4.field_78797_d * scale), (-this.hing4.field_78798_e * scale));
/* 162 */     this.hing4.func_78785_a(scale);
/* 163 */     GL11.glPopMatrix();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     float lastBrightnessX = OpenGlHelper.lastBrightnessX;
/* 169 */     float lastBrightnessY = OpenGlHelper.lastBrightnessY;
/*     */     
/* 171 */     float b = brightness * 200.0F;
/* 172 */     float colour = Math.min(2.0F, brightness * 2.0F + 0.1F);
/*     */     
/* 174 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, Math.min(200.0F, lastBrightnessX + b), Math.min(200.0F, lastBrightnessY + b));
/* 175 */     GL11.glColor4f(colour, colour, colour, 1.0F);
/* 176 */     if (brightness > 0.0F) GL11.glDisable(2896);
/*     */     
/* 178 */     GL11.glPushMatrix();
/* 179 */     GL11.glTranslated(this.emitter1.field_82906_o, this.emitter1.field_82908_p, this.emitter1.field_82907_q);
/* 180 */     GL11.glTranslated((this.emitter1.field_78800_c * scale), (this.emitter1.field_78797_d * scale), (this.emitter1.field_78798_e * scale));
/* 181 */     GL11.glRotatef(embitterRotation, 0.0F, 0.0F, 1.0F);
/* 182 */     GL11.glScaled(0.5D, 0.5D, 0.5D);
/* 183 */     GL11.glTranslated(-this.emitter1.field_82906_o, -this.emitter1.field_82908_p, -this.emitter1.field_82907_q);
/* 184 */     GL11.glTranslated((-this.emitter1.field_78800_c * scale), (-this.emitter1.field_78797_d * scale), (-this.emitter1.field_78798_e * scale));
/* 185 */     this.emitter1.func_78785_a(scale);
/* 186 */     GL11.glPopMatrix();
/* 187 */     GL11.glPushMatrix();
/* 188 */     GL11.glTranslated(this.emitter2.field_82906_o, this.emitter2.field_82908_p, this.emitter2.field_82907_q);
/* 189 */     GL11.glTranslated((this.emitter2.field_78800_c * scale), (this.emitter2.field_78797_d * scale), (this.emitter2.field_78798_e * scale));
/* 190 */     GL11.glRotatef(embitterRotation, 0.0F, 0.0F, -1.0F);
/* 191 */     GL11.glScaled(0.5D, 0.5D, 0.5D);
/* 192 */     GL11.glTranslated(-this.emitter2.field_82906_o, -this.emitter2.field_82908_p, -this.emitter2.field_82907_q);
/* 193 */     GL11.glTranslated((-this.emitter2.field_78800_c * scale), (-this.emitter2.field_78797_d * scale), (-this.emitter2.field_78798_e * scale));
/* 194 */     this.emitter2.func_78785_a(scale);
/* 195 */     GL11.glPopMatrix();
/* 196 */     GL11.glPushMatrix();
/* 197 */     GL11.glTranslated(this.emitter3.field_82906_o, this.emitter3.field_82908_p, this.emitter3.field_82907_q);
/* 198 */     GL11.glTranslated((this.emitter3.field_78800_c * scale), (this.emitter3.field_78797_d * scale), (this.emitter3.field_78798_e * scale));
/* 199 */     GL11.glRotatef(embitterRotation, 1.0F, 0.0F, 0.0F);
/* 200 */     GL11.glScaled(0.5D, 0.5D, 0.5D);
/* 201 */     GL11.glTranslated(-this.emitter3.field_82906_o, -this.emitter3.field_82908_p, -this.emitter3.field_82907_q);
/* 202 */     GL11.glTranslated((-this.emitter3.field_78800_c * scale), (-this.emitter3.field_78797_d * scale), (-this.emitter3.field_78798_e * scale));
/* 203 */     this.emitter3.func_78785_a(scale);
/* 204 */     GL11.glPopMatrix();
/* 205 */     GL11.glPushMatrix();
/* 206 */     GL11.glTranslated(this.emitter4.field_82906_o, this.emitter4.field_82908_p, this.emitter4.field_82907_q);
/* 207 */     GL11.glTranslated((this.emitter4.field_78800_c * scale), (this.emitter4.field_78797_d * scale), (this.emitter4.field_78798_e * scale));
/* 208 */     GL11.glRotatef(embitterRotation, -1.0F, 0.0F, 0.0F);
/* 209 */     GL11.glScaled(0.5D, 0.5D, 0.5D);
/* 210 */     GL11.glTranslated(-this.emitter4.field_82906_o, -this.emitter4.field_82908_p, -this.emitter4.field_82907_q);
/* 211 */     GL11.glTranslated((-this.emitter4.field_78800_c * scale), (-this.emitter4.field_78797_d * scale), (-this.emitter4.field_78798_e * scale));
/* 212 */     this.emitter4.func_78785_a(scale);
/* 213 */     GL11.glPopMatrix();
/*     */     
/* 215 */     if (brightness > 0.0F) GL11.glEnable(2896); 
/* 216 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 217 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, lastBrightnessX, lastBrightnessY);
/* 218 */     GL11.glEnable(2896);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
/* 225 */     modelRenderer.field_78795_f = x;
/* 226 */     modelRenderer.field_78796_g = y;
/* 227 */     modelRenderer.field_78808_h = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\model\ModelReactorStabilizerRing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */