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
/*     */ public class ModelReactorStabilizerCore
/*     */   extends ModelBase
/*     */ {
/*     */   public ModelRenderer hub1;
/*     */   public ModelRenderer hub2;
/*     */   public ModelRenderer rotor1R;
/*     */   public ModelRenderer rotor2R;
/*     */   public ModelRenderer rotor1R_1;
/*     */   public ModelRenderer rotor1R_2;
/*     */   public ModelRenderer rotor1R_3;
/*     */   public ModelRenderer rotor1R_4;
/*     */   public ModelRenderer rotor1L;
/*     */   public ModelRenderer rotor1L_1;
/*     */   public ModelRenderer rotor1L_2;
/*     */   public ModelRenderer rotor1L_3;
/*     */   public ModelRenderer rotor1L_4;
/*     */   public ModelRenderer rotor2R_1;
/*     */   public ModelRenderer rotor2R_2;
/*     */   public ModelRenderer rotor2R_3;
/*     */   public ModelRenderer rotor2R_4;
/*     */   public ModelRenderer rotor2R_5;
/*     */   public ModelRenderer rotor2R_6;
/*     */   public ModelRenderer rotor2L;
/*     */   public ModelRenderer rotor2L_1;
/*     */   public ModelRenderer rotor2L_2;
/*     */   public ModelRenderer rotor2L_3;
/*     */   public ModelRenderer rotor2L_4;
/*     */   public ModelRenderer rotor2L_5;
/*     */   public ModelRenderer rotor2L_6;
/*     */   public ModelRenderer basePlate;
/*     */   public ModelRenderer frame1;
/*     */   public ModelRenderer frame2;
/*     */   public ModelRenderer frame3;
/*     */   public ModelRenderer frame4;
/*     */   public ModelRenderer frame5;
/*     */   public ModelRenderer frame6;
/*     */   public ModelRenderer frame7;
/*     */   public ModelRenderer frame8;
/*     */   public ModelRenderer frame9;
/*     */   public ModelRenderer frame10;
/*     */   public ModelRenderer frame11;
/*     */   public ModelRenderer frame12;
/*     */   public ModelRenderer backSpoke1;
/*     */   public ModelRenderer backSpoke2;
/*     */   public ModelRenderer coreElement2;
/*     */   public ModelRenderer coreElement1;
/*     */   
/*     */   public ModelReactorStabilizerCore() {
/*  59 */     this.field_78090_t = 64;
/*  60 */     this.field_78089_u = 32;
/*  61 */     this.coreElement2 = new ModelRenderer(this, 32, 20);
/*  62 */     this.coreElement2.func_78793_a(0.0F, 0.0F, 0.0F);
/*  63 */     this.coreElement2.func_78790_a(-0.5F, -0.5F, -2.0F, 1, 1, 4, 0.0F);
/*  64 */     this.rotor2L_1 = new ModelRenderer(this, 0, 4);
/*  65 */     this.rotor2L_1.func_78793_a(0.0F, 0.0F, 0.0F);
/*  66 */     this.rotor2L_1.func_78790_a(4.5F, -0.5F, -7.0F, 1, 1, 13, 0.0F);
/*  67 */     setRotateAngle(this.rotor2L_1, 0.0F, 0.0F, -3.3213615F);
/*  68 */     this.rotor2L_5 = new ModelRenderer(this, 0, 4);
/*  69 */     this.rotor2L_5.func_78793_a(0.0F, 0.0F, 0.0F);
/*  70 */     this.rotor2L_5.func_78790_a(4.5F, -0.5F, -7.0F, 1, 1, 13, 0.0F);
/*  71 */     setRotateAngle(this.rotor2L_5, 0.0F, 0.0F, 2.6022859F);
/*  72 */     this.rotor1L_3 = new ModelRenderer(this, 28, 2);
/*  73 */     this.rotor1L_3.func_78793_a(0.0F, 0.0F, 0.0F);
/*  74 */     this.rotor1L_3.func_78790_a(3.0F, -0.5F, -6.0F, 1, 1, 11, 0.0F);
/*  75 */     setRotateAngle(this.rotor1L_3, 0.0F, 0.0F, 3.6372662F);
/*  76 */     this.rotor2L_6 = new ModelRenderer(this, 0, 4);
/*  77 */     this.rotor2L_6.func_78793_a(0.0F, 0.0F, 0.0F);
/*  78 */     this.rotor2L_6.func_78790_a(4.5F, -0.5F, -7.0F, 1, 1, 13, 0.0F);
/*  79 */     setRotateAngle(this.rotor2L_6, 0.0F, 0.0F, 3.6808994F);
/*  80 */     this.frame12 = new ModelRenderer(this, 0, 4);
/*  81 */     this.frame12.func_78793_a(0.0F, 0.0F, 0.0F);
/*  82 */     this.frame12.func_78790_a(7.0F, -6.0F, 7.0F, 1, 12, 1, 0.0F);
/*  83 */     this.frame10 = new ModelRenderer(this, 0, 4);
/*  84 */     this.frame10.func_78793_a(0.0F, 0.0F, 0.0F);
/*  85 */     this.frame10.func_78790_a(-8.0F, -6.0F, -8.0F, 1, 12, 1, 0.0F);
/*  86 */     this.rotor2L_3 = new ModelRenderer(this, 0, 4);
/*  87 */     this.rotor2L_3.func_78793_a(0.0F, 0.0F, 0.0F);
/*  88 */     this.rotor2L_3.func_78790_a(4.5F, -0.5F, -7.0F, 1, 1, 13, 0.0F);
/*  89 */     setRotateAngle(this.rotor2L_3, 0.0F, 0.0F, -3.5011306F);
/*  90 */     this.rotor2R_6 = new ModelRenderer(this, 0, 4);
/*  91 */     this.rotor2R_6.func_78793_a(0.0F, 0.0F, 0.0F);
/*  92 */     this.rotor2R_6.func_78790_a(4.5F, -0.5F, -7.0F, 1, 1, 13, 0.0F);
/*  93 */     setRotateAngle(this.rotor2R_6, 0.0F, 0.0F, 0.53930676F);
/*  94 */     this.coreElement1 = new ModelRenderer(this, 32, 14);
/*  95 */     this.coreElement1.func_78793_a(0.0F, 0.0F, 0.0F);
/*  96 */     this.coreElement1.func_78790_a(-1.0F, -1.0F, 2.0F, 2, 2, 4, 0.0F);
/*  97 */     this.rotor1L_4 = new ModelRenderer(this, 28, 2);
/*  98 */     this.rotor1L_4.func_78793_a(0.0F, 0.0F, 0.0F);
/*  99 */     this.rotor1L_4.func_78790_a(3.0F, -0.5F, -6.0F, 1, 1, 11, 0.0F);
/* 100 */     setRotateAngle(this.rotor1L_4, 0.0F, 0.0F, 2.645919F);
/* 101 */     this.basePlate = new ModelRenderer(this, 0, 18);
/* 102 */     this.basePlate.func_78793_a(0.0F, 0.0F, 0.0F);
/* 103 */     this.basePlate.func_78790_a(-4.0F, -4.0F, 6.0F, 8, 8, 2, 0.0F);
/* 104 */     this.rotor1L_2 = new ModelRenderer(this, 28, 2);
/* 105 */     this.rotor1L_2.func_78793_a(0.0F, 0.0F, 0.0F);
/* 106 */     this.rotor1L_2.func_78790_a(3.0F, -0.5F, -6.0F, 1, 1, 11, 0.0F);
/* 107 */     setRotateAngle(this.rotor1L_2, 0.0F, 0.0F, 2.893756F);
/* 108 */     this.frame1 = new ModelRenderer(this, 12, 14);
/* 109 */     this.frame1.func_78793_a(0.0F, 0.0F, 0.0F);
/* 110 */     this.frame1.func_78790_a(6.0F, 6.0F, -8.0F, 2, 2, 16, 0.0F);
/* 111 */     this.rotor1L_1 = new ModelRenderer(this, 28, 2);
/* 112 */     this.rotor1L_1.func_78793_a(0.0F, 0.0F, 0.0F);
/* 113 */     this.rotor1L_1.func_78790_a(3.0F, -0.5F, -6.0F, 1, 1, 11, 0.0F);
/* 114 */     setRotateAngle(this.rotor1L_1, 0.0F, 0.0F, -2.893756F);
/* 115 */     this.frame5 = new ModelRenderer(this, 14, 0);
/* 116 */     this.frame5.func_78793_a(0.0F, 0.0F, 0.0F);
/* 117 */     this.frame5.func_78790_a(-6.0F, -8.0F, -8.0F, 12, 1, 1, 0.0F);
/* 118 */     this.frame11 = new ModelRenderer(this, 0, 4);
/* 119 */     this.frame11.func_78793_a(0.0F, 0.0F, 0.0F);
/* 120 */     this.frame11.func_78790_a(7.0F, -6.0F, -8.0F, 1, 12, 1, 0.0F);
/* 121 */     this.rotor1R_3 = new ModelRenderer(this, 28, 2);
/* 122 */     this.rotor1R_3.func_78793_a(0.0F, 0.0F, 1.0F);
/* 123 */     this.rotor1R_3.func_78790_a(3.0F, -0.5F, -7.0F, 1, 1, 11, 0.0F);
/* 124 */     setRotateAngle(this.rotor1R_3, 0.0F, 0.0F, -0.4956735F);
/* 125 */     this.rotor2R_3 = new ModelRenderer(this, 0, 4);
/* 126 */     this.rotor2R_3.func_78793_a(0.0F, 0.0F, 0.0F);
/* 127 */     this.rotor2R_3.func_78790_a(4.5F, -0.5F, -7.0F, 1, 1, 13, 0.0F);
/* 128 */     setRotateAngle(this.rotor2R_3, 0.0F, 0.0F, 0.35953784F);
/* 129 */     this.frame3 = new ModelRenderer(this, 12, 14);
/* 130 */     this.frame3.func_78793_a(0.0F, 0.0F, 0.0F);
/* 131 */     this.frame3.func_78790_a(6.0F, -8.0F, -8.0F, 2, 2, 16, 0.0F);
/* 132 */     this.rotor2R_5 = new ModelRenderer(this, 0, 4);
/* 133 */     this.rotor2R_5.func_78793_a(0.0F, 0.0F, 0.0F);
/* 134 */     this.rotor2R_5.func_78790_a(4.5F, -0.5F, -7.0F, 1, 1, 13, 0.0F);
/* 135 */     setRotateAngle(this.rotor2R_5, 0.0F, 0.0F, -0.53930676F);
/* 136 */     this.rotor2R_2 = new ModelRenderer(this, 0, 4);
/* 137 */     this.rotor2R_2.func_78793_a(0.0F, 0.0F, 0.0F);
/* 138 */     this.rotor2R_2.func_78790_a(4.5F, -0.5F, -7.0F, 1, 1, 13, 0.0F);
/* 139 */     setRotateAngle(this.rotor2R_2, 0.0F, 0.0F, 0.17976892F);
/* 140 */     this.backSpoke2 = new ModelRenderer(this, 52, 0);
/* 141 */     this.backSpoke2.func_78793_a(0.0F, 0.0F, 0.0F);
/* 142 */     this.backSpoke2.func_78790_a(-0.5F, -9.5F, 6.95F, 1, 19, 1, 0.0F);
/* 143 */     setRotateAngle(this.backSpoke2, 0.0F, 0.0F, 0.7853982F);
/* 144 */     this.frame6 = new ModelRenderer(this, 14, 0);
/* 145 */     this.frame6.func_78793_a(0.0F, 0.0F, 0.0F);
/* 146 */     this.frame6.func_78790_a(-6.0F, 7.0F, -8.0F, 12, 1, 1, 0.0F);
/* 147 */     this.rotor1R_2 = new ModelRenderer(this, 28, 2);
/* 148 */     this.rotor1R_2.func_78793_a(0.0F, 0.0F, 0.0F);
/* 149 */     this.rotor1R_2.func_78790_a(3.0F, -0.5F, -6.0F, 1, 1, 11, 0.0F);
/* 150 */     setRotateAngle(this.rotor1R_2, 0.0F, 0.0F, 0.24783675F);
/* 151 */     this.hub1 = new ModelRenderer(this, 0, 0);
/* 152 */     this.hub1.func_78793_a(0.0F, 0.0F, -0.02F);
/* 153 */     this.hub1.func_78790_a(-3.0F, -0.5F, 4.0F, 6, 1, 1, 0.0F);
/* 154 */     this.rotor2R_1 = new ModelRenderer(this, 0, 4);
/* 155 */     this.rotor2R_1.func_78793_a(0.0F, 0.0F, 0.0F);
/* 156 */     this.rotor2R_1.func_78790_a(4.5F, -0.5F, -7.0F, 1, 1, 13, 0.0F);
/* 157 */     setRotateAngle(this.rotor2R_1, 0.0F, 0.0F, -0.17976892F);
/* 158 */     this.hub2 = new ModelRenderer(this, 0, 2);
/* 159 */     this.hub2.func_78793_a(0.0F, 0.0F, -0.01F);
/* 160 */     this.hub2.func_78790_a(-4.5F, -0.5F, 5.0F, 9, 1, 1, 0.0F);
/* 161 */     this.backSpoke1 = new ModelRenderer(this, 52, 0);
/* 162 */     this.backSpoke1.func_78793_a(0.0F, 0.0F, 0.0F);
/* 163 */     this.backSpoke1.func_78790_a(-0.5F, -9.5F, 6.95F, 1, 19, 1, 0.0F);
/* 164 */     setRotateAngle(this.backSpoke1, 0.0F, 0.0F, -0.7853982F);
/* 165 */     this.rotor1R_4 = new ModelRenderer(this, 28, 2);
/* 166 */     this.rotor1R_4.func_78793_a(0.0F, 0.0F, 1.0F);
/* 167 */     this.rotor1R_4.func_78790_a(3.0F, -0.5F, -7.0F, 1, 1, 11, 0.0F);
/* 168 */     setRotateAngle(this.rotor1R_4, 0.0F, 0.0F, 0.4956735F);
/* 169 */     this.rotor1R = new ModelRenderer(this, 28, 2);
/* 170 */     this.rotor1R.func_78793_a(0.0F, 0.0F, 0.0F);
/* 171 */     this.rotor1R.func_78790_a(3.0F, -0.5F, -6.0F, 1, 1, 11, 0.0F);
/* 172 */     this.rotor2L_4 = new ModelRenderer(this, 0, 4);
/* 173 */     this.rotor2L_4.func_78793_a(0.0F, 0.0F, 0.0F);
/* 174 */     this.rotor2L_4.func_78790_a(4.5F, -0.5F, -7.0F, 1, 1, 13, 0.0F);
/* 175 */     setRotateAngle(this.rotor2L_4, 0.0F, 0.0F, -2.782055F);
/* 176 */     this.frame4 = new ModelRenderer(this, 12, 14);
/* 177 */     this.frame4.func_78793_a(0.0F, 0.0F, 0.0F);
/* 178 */     this.frame4.func_78790_a(-8.0F, -8.0F, -8.0F, 2, 2, 16, 0.0F);
/* 179 */     this.rotor2R = new ModelRenderer(this, 0, 4);
/* 180 */     this.rotor2R.func_78793_a(0.0F, 0.0F, 0.0F);
/* 181 */     this.rotor2R.func_78790_a(4.5F, -0.5F, -7.0F, 1, 1, 13, 0.0F);
/* 182 */     this.rotor2R_4 = new ModelRenderer(this, 0, 4);
/* 183 */     this.rotor2R_4.func_78793_a(0.0F, 0.0F, 0.0F);
/* 184 */     this.rotor2R_4.func_78790_a(4.5F, -0.5F, -7.0F, 1, 1, 13, 0.0F);
/* 185 */     setRotateAngle(this.rotor2R_4, 0.0F, 0.0F, -0.35953784F);
/* 186 */     this.rotor2L_2 = new ModelRenderer(this, 0, 4);
/* 187 */     this.rotor2L_2.func_78793_a(0.0F, 0.0F, 0.0F);
/* 188 */     this.rotor2L_2.func_78790_a(4.5F, -0.5F, -7.0F, 1, 1, 13, 0.0F);
/* 189 */     setRotateAngle(this.rotor2L_2, 0.0F, 0.0F, -2.9618237F);
/* 190 */     this.frame9 = new ModelRenderer(this, 0, 4);
/* 191 */     this.frame9.func_78793_a(0.0F, 0.0F, 0.0F);
/* 192 */     this.frame9.func_78790_a(-8.0F, -6.0F, 7.0F, 1, 12, 1, 0.0F);
/* 193 */     this.rotor2L = new ModelRenderer(this, 0, 4);
/* 194 */     this.rotor2L.func_78793_a(0.0F, 0.0F, 0.0F);
/* 195 */     this.rotor2L.func_78790_a(4.5F, -0.5F, -7.0F, 1, 1, 13, 0.0F);
/* 196 */     setRotateAngle(this.rotor2L, 0.0F, 0.0F, -3.1415927F);
/* 197 */     this.rotor1L = new ModelRenderer(this, 28, 2);
/* 198 */     this.rotor1L.func_78793_a(0.0F, 0.0F, 0.0F);
/* 199 */     this.rotor1L.func_78790_a(3.0F, -0.5F, -6.0F, 1, 1, 11, 0.0F);
/* 200 */     setRotateAngle(this.rotor1L, 0.0F, 0.0F, -3.1415927F);
/* 201 */     this.frame8 = new ModelRenderer(this, 14, 0);
/* 202 */     this.frame8.func_78793_a(0.0F, 0.0F, 0.0F);
/* 203 */     this.frame8.func_78790_a(-6.0F, 7.0F, 7.0F, 12, 1, 1, 0.0F);
/* 204 */     this.frame2 = new ModelRenderer(this, 12, 14);
/* 205 */     this.frame2.func_78793_a(0.0F, 0.0F, 0.0F);
/* 206 */     this.frame2.func_78790_a(-8.0F, 6.0F, -8.0F, 2, 2, 16, 0.0F);
/* 207 */     this.frame7 = new ModelRenderer(this, 14, 0);
/* 208 */     this.frame7.func_78793_a(0.0F, 0.0F, 0.0F);
/* 209 */     this.frame7.func_78790_a(-6.0F, -8.0F, 7.0F, 12, 1, 1, 0.0F);
/* 210 */     this.rotor1R_1 = new ModelRenderer(this, 28, 2);
/* 211 */     this.rotor1R_1.func_78793_a(0.0F, 0.0F, 0.0F);
/* 212 */     this.rotor1R_1.func_78790_a(3.0F, -0.5F, -6.0F, 1, 1, 11, 0.0F);
/* 213 */     setRotateAngle(this.rotor1R_1, 0.0F, 0.0F, -0.24783675F);
/* 214 */     this.basePlate.func_78792_a(this.coreElement2);
/* 215 */     this.rotor2R.func_78792_a(this.rotor2L_1);
/* 216 */     this.rotor2R.func_78792_a(this.rotor2L_5);
/* 217 */     this.rotor1R.func_78792_a(this.rotor1L_3);
/* 218 */     this.rotor2R.func_78792_a(this.rotor2L_6);
/* 219 */     this.basePlate.func_78792_a(this.frame12);
/* 220 */     this.basePlate.func_78792_a(this.frame10);
/* 221 */     this.rotor2R.func_78792_a(this.rotor2L_3);
/* 222 */     this.rotor2R.func_78792_a(this.rotor2R_6);
/* 223 */     this.basePlate.func_78792_a(this.coreElement1);
/* 224 */     this.rotor1R.func_78792_a(this.rotor1L_4);
/* 225 */     this.rotor1R.func_78792_a(this.rotor1L_2);
/* 226 */     this.basePlate.func_78792_a(this.frame1);
/* 227 */     this.rotor1R.func_78792_a(this.rotor1L_1);
/* 228 */     this.basePlate.func_78792_a(this.frame5);
/* 229 */     this.basePlate.func_78792_a(this.frame11);
/* 230 */     this.rotor1R.func_78792_a(this.rotor1R_3);
/* 231 */     this.rotor2R.func_78792_a(this.rotor2R_3);
/* 232 */     this.basePlate.func_78792_a(this.frame3);
/* 233 */     this.rotor2R.func_78792_a(this.rotor2R_5);
/* 234 */     this.rotor2R.func_78792_a(this.rotor2R_2);
/* 235 */     this.basePlate.func_78792_a(this.backSpoke2);
/* 236 */     this.basePlate.func_78792_a(this.frame6);
/* 237 */     this.rotor1R.func_78792_a(this.rotor1R_2);
/* 238 */     this.rotor2R.func_78792_a(this.rotor2R_1);
/* 239 */     this.basePlate.func_78792_a(this.backSpoke1);
/* 240 */     this.rotor1R.func_78792_a(this.rotor1R_4);
/* 241 */     this.rotor2R.func_78792_a(this.rotor2L_4);
/* 242 */     this.basePlate.func_78792_a(this.frame4);
/* 243 */     this.rotor2R.func_78792_a(this.rotor2R_4);
/* 244 */     this.rotor2R.func_78792_a(this.rotor2L_2);
/* 245 */     this.basePlate.func_78792_a(this.frame9);
/* 246 */     this.rotor2R.func_78792_a(this.rotor2L);
/* 247 */     this.rotor1R.func_78792_a(this.rotor1L);
/* 248 */     this.basePlate.func_78792_a(this.frame8);
/* 249 */     this.basePlate.func_78792_a(this.frame2);
/* 250 */     this.basePlate.func_78792_a(this.frame7);
/* 251 */     this.rotor1R.func_78792_a(this.rotor1R_1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_78088_a(Entity entity, float rotation, float brightness, float f2, float f3, float f4, float f5) {
/* 256 */     GL11.glPushMatrix();
/*     */     
/* 258 */     this.basePlate.func_78785_a(f5);
/*     */     
/* 260 */     GL11.glRotatef(rotation, 0.0F, 0.0F, 1.0F);
/* 261 */     this.hub1.func_78785_a(f5);
/* 262 */     GL11.glRotatef(rotation * 2.0F, 0.0F, 0.0F, -1.0F);
/* 263 */     this.hub2.func_78785_a(f5);
/*     */     
/* 265 */     float lastBrightnessX = OpenGlHelper.lastBrightnessX;
/* 266 */     float lastBrightnessY = OpenGlHelper.lastBrightnessY;
/*     */     
/* 268 */     float b = brightness * 200.0F;
/* 269 */     float colour = Math.min(2.0F, brightness * 2.0F + 0.1F);
/* 270 */     if (brightness > 0.0F) GL11.glDisable(2896);
/*     */     
/* 272 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, Math.min(200.0F, lastBrightnessX + b), Math.min(200.0F, lastBrightnessY + b));
/* 273 */     GL11.glColor4f(colour, colour, colour, 1.0F);
/* 274 */     this.rotor2R.func_78785_a(f5);
/* 275 */     GL11.glRotatef(rotation * 2.0F, 0.0F, 0.0F, 1.0F);
/* 276 */     this.rotor1R.func_78785_a(f5);
/* 277 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 278 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, lastBrightnessX, lastBrightnessY);
/*     */     
/* 280 */     if (brightness > 0.0F) GL11.glEnable(2896);
/*     */     
/* 282 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
/* 289 */     modelRenderer.field_78795_f = x;
/* 290 */     modelRenderer.field_78796_g = y;
/* 291 */     modelRenderer.field_78808_h = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\model\ModelReactorStabilizerCore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */