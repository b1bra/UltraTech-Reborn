/*     */ package com.brandon3055.draconicevolution.client.model;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelDraconicArmorOld
/*     */   extends ModelBiped
/*     */ {
/*     */   public ModelRenderer MainHelmPieceRight1;
/*     */   public ModelRenderer MainHelmPieceRight2;
/*     */   public ModelRenderer MainHelmPieceLeft1;
/*     */   public ModelRenderer MainHelmPieceLeft2;
/*     */   public ModelRenderer MainHelmPieceRight3;
/*     */   public ModelRenderer MainHelmPieceLeft3;
/*     */   public ModelRenderer MainHelmPieceBack;
/*     */   public ModelRenderer MainHelmPieceTop;
/*     */   public ModelRenderer MainHelmPieceFrontTop;
/*     */   public ModelRenderer MainHelmPieceFrontBottom;
/*     */   public ModelRenderer MainHelmPieceBottom;
/*     */   public ModelRenderer HelmPieceBack1;
/*     */   public ModelRenderer HornPieceRight1;
/*     */   public ModelRenderer HornPieceRight2;
/*     */   public ModelRenderer HornPieceLeft1;
/*     */   public ModelRenderer HornPieceLeft2;
/*     */   public ModelRenderer HornPieceRight3;
/*     */   public ModelRenderer HornPieceLeft3;
/*     */   public ModelRenderer HornPieceRight4;
/*     */   public ModelRenderer HornPieceLeft4;
/*     */   public ModelRenderer HelmPieceFront1;
/*     */   public ModelRenderer HelmPieceFront2;
/*     */   public ModelRenderer MainChestPieceBottom;
/*     */   public ModelRenderer ChestDecorationPiece2;
/*     */   public ModelRenderer ChestDecorationPiece4;
/*     */   public ModelRenderer ShoulderPadRight2;
/*     */   public ModelRenderer ShoulderPadRight3;
/*     */   public ModelRenderer ShoulderPadRight4;
/*     */   public ModelRenderer ShoulderPadRight1;
/*     */   public ModelRenderer MainArmGuardRight;
/*     */   public ModelRenderer ArmGuardPieceRight1;
/*     */   public ModelRenderer ArmGuardPieceRight2;
/*     */   public ModelRenderer ArmGuardPieceRight3;
/*     */   public ModelRenderer ArmGuardPieceRight4;
/*     */   public ModelRenderer ArmStrapRightTop;
/*     */   public ModelRenderer ArmStrapRightBottom;
/*     */   public ModelRenderer ShoulderPadLeft2;
/*     */   public ModelRenderer ShoulderPadLeft3;
/*     */   public ModelRenderer ShoulderPadLeft4;
/*     */   public ModelRenderer MainArmGuardLeft;
/*     */   public ModelRenderer ShoulderPadLeft1;
/*     */   public ModelRenderer ArmGuardPieceLeft1;
/*     */   public ModelRenderer ArmGuardPieceLeft2;
/*     */   public ModelRenderer ArmGuardPieceLeft3;
/*     */   public ModelRenderer ArmGuardPieceLeft4;
/*     */   public ModelRenderer ArmStrapLeftBottom;
/*     */   public ModelRenderer ArmStrapLeftTop;
/*     */   public ModelRenderer MainLegPieceRight;
/*     */   public ModelRenderer LegPieceRight1;
/*     */   public ModelRenderer LegPieceRight2;
/*     */   public ModelRenderer MainBootPieceRight;
/*     */   public ModelRenderer BootPieceRight1;
/*     */   public ModelRenderer BootPieceRight2;
/*     */   public ModelRenderer MainKneePadRight;
/*     */   public ModelRenderer KneePieceRight1;
/*     */   public ModelRenderer MainLegPieceLeft;
/*     */   public ModelRenderer LegPieceLeft2;
/*     */   public ModelRenderer LegPieceLeft1;
/*     */   public ModelRenderer MainBootPieceLeft;
/*     */   public ModelRenderer BootPieceLeft1;
/*     */   public ModelRenderer BootPieceLeft2;
/*     */   public ModelRenderer MainKneePadLeft;
/*     */   public ModelRenderer KneePieceLeft1;
/*     */   public ModelRenderer BootPieceLeft3;
/*     */   public ModelRenderer BootPieceLeft4;
/*     */   public ModelRenderer BootPieceRight3;
/*     */   public ModelRenderer BootPieceRight4;
/*     */   public ModelRenderer LeggsTop;
/*     */   public ModelRenderer DrMainChestPieceTop;
/*     */   public ModelRenderer DrMainChestPieceMid;
/*     */   public ModelRenderer DrChestDecorationPiece1;
/*     */   public ModelRenderer DrChestDecorationPiece3;
/*     */   public ModelRenderer BeltFront;
/*     */   public ModelRenderer BeltBack;
/*     */   public ModelRenderer BeltLeft;
/*     */   public ModelRenderer BeltRight;
/*     */   public ModelRenderer BeltBuckle;
/*     */   public ModelRenderer WyHelmPieceTop1;
/*     */   public ModelRenderer WyHelmPieceTop2;
/*     */   public ModelRenderer WyHelmPieceTop3;
/*     */   public ModelRenderer WyHelmPieceTop4;
/*     */   public ModelRenderer WyMainChestPieceMid;
/*     */   public ModelRenderer WyMainChestPieceTop;
/*     */   public ModelRenderer WyChestPieceTop1;
/*     */   public ModelRenderer WyChestPieceTop2;
/*     */   public ModelRenderer WyChestDecorationPiece3;
/*     */   public ModelRenderer WyChestPieceTop3;
/*     */   private boolean isDraconic;
/*     */   
/*     */   public ModelDraconicArmorOld(float f, boolean isHelmet, boolean isChestPiece, boolean isLeggings, boolean isdBoots, boolean isDraconic) {
/* 112 */     super(f, 0.0F, 128, 128);
/* 113 */     this.field_78090_t = 256;
/* 114 */     this.field_78089_u = 128;
/* 115 */     this.isDraconic = isDraconic;
/*     */ 
/*     */     
/* 118 */     this.field_78116_c = new ModelRenderer((ModelBase)this, 0, 0);
/* 119 */     this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
/* 120 */     this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
/*     */     
/* 122 */     this.MainHelmPieceLeft1 = new ModelRenderer((ModelBase)this, 191, 0);
/* 123 */     this.MainHelmPieceLeft1.func_78793_a(3.5F, -3.0F, -4.5F);
/* 124 */     this.MainHelmPieceLeft1.func_78790_a(0.0F, 0.0F, 0.0F, 1, 3, 9, 0.0F);
/*     */     
/* 126 */     this.HelmPieceFront2 = new ModelRenderer((ModelBase)this, 177, 43);
/* 127 */     this.HelmPieceFront2.func_78793_a(-0.5F, -5.5F, -4.5F);
/* 128 */     this.HelmPieceFront2.func_78790_a(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
/*     */     
/* 130 */     this.MainHelmPieceRight2 = new ModelRenderer((ModelBase)this, 170, 0);
/* 131 */     this.MainHelmPieceRight2.func_78793_a(-4.5F, -8.0F, -4.5F);
/* 132 */     this.MainHelmPieceRight2.func_78790_a(0.0F, 0.0F, 0.0F, 1, 2, 9, 0.0F);
/*     */     
/* 134 */     this.MainHelmPieceTop = new ModelRenderer((ModelBase)this, 177, 24);
/* 135 */     this.MainHelmPieceTop.field_78809_i = true;
/* 136 */     this.MainHelmPieceTop.func_78793_a(-4.0F, -8.3F, -4.5F);
/* 137 */     this.MainHelmPieceTop.func_78790_a(0.0F, 0.0F, 0.0F, 8, 1, 9, 0.0F);
/* 138 */     setRotateAngle(this.MainHelmPieceTop, 0.05235988F, -0.0F, 0.0F);
/*     */     
/* 140 */     this.MainHelmPieceFrontBottom = new ModelRenderer((ModelBase)this, 174, 35);
/* 141 */     this.MainHelmPieceFrontBottom.func_78793_a(-4.0F, -3.0F, -5.0F);
/* 142 */     this.MainHelmPieceFrontBottom.func_78790_a(0.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
/*     */     
/* 144 */     this.MainHelmPieceLeft2 = new ModelRenderer((ModelBase)this, 170, 0);
/* 145 */     this.MainHelmPieceLeft2.func_78793_a(3.5F, -8.0F, -4.5F);
/* 146 */     this.MainHelmPieceLeft2.func_78790_a(0.0F, 0.0F, 0.0F, 1, 2, 9, 0.0F);
/*     */     
/* 148 */     this.HelmPieceBack1 = new ModelRenderer((ModelBase)this, 193, 39);
/* 149 */     this.HelmPieceBack1.func_78793_a(-4.0F, -8.0F, 4.0F);
/* 150 */     this.HelmPieceBack1.func_78790_a(0.0F, 0.0F, 0.0F, 8, 8, 1, 0.0F);
/*     */     
/* 152 */     this.MainHelmPieceLeft3 = new ModelRenderer((ModelBase)this, 195, 13);
/* 153 */     this.MainHelmPieceLeft3.func_78793_a(3.5F, -6.0F, -2.5F);
/* 154 */     this.MainHelmPieceLeft3.func_78790_a(0.0F, 0.0F, 0.0F, 1, 3, 7, 0.0F);
/*     */     
/* 156 */     this.MainHelmPieceRight1 = new ModelRenderer((ModelBase)this, 191, 0);
/* 157 */     this.MainHelmPieceRight1.func_78793_a(-4.5F, -3.0F, -4.5F);
/* 158 */     this.MainHelmPieceRight1.func_78790_a(0.0F, 0.0F, 0.0F, 1, 3, 9, 0.0F);
/*     */     
/* 160 */     this.MainHelmPieceBack = new ModelRenderer((ModelBase)this, 178, 13);
/* 161 */     this.MainHelmPieceBack.func_78793_a(-3.5F, -8.0F, 3.5F);
/* 162 */     this.MainHelmPieceBack.func_78790_a(0.0F, 0.0F, 0.0F, 7, 8, 1, 0.0F);
/*     */     
/* 164 */     this.MainHelmPieceRight3 = new ModelRenderer((ModelBase)this, 195, 13);
/* 165 */     this.MainHelmPieceRight3.func_78793_a(-4.5F, -6.0F, -2.5F);
/* 166 */     this.MainHelmPieceRight3.func_78790_a(0.0F, 0.0F, 0.0F, 1, 3, 7, 0.0F);
/*     */     
/* 168 */     this.HelmPieceFront1 = new ModelRenderer((ModelBase)this, 175, 40);
/* 169 */     this.HelmPieceFront1.func_78793_a(-1.0F, -6.0F, -5.0F);
/* 170 */     this.HelmPieceFront1.func_78790_a(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
/*     */     
/* 172 */     this.MainHelmPieceBottom = new ModelRenderer((ModelBase)this, 177, 24);
/* 173 */     this.MainHelmPieceBottom.func_78793_a(-4.0F, -0.5F, -4.5F);
/* 174 */     this.MainHelmPieceBottom.func_78790_a(0.0F, 0.0F, 0.0F, 8, 1, 9, 0.0F);
/*     */     
/* 176 */     this.MainHelmPieceFrontTop = new ModelRenderer((ModelBase)this, 193, 35);
/* 177 */     this.MainHelmPieceFrontTop.func_78793_a(-4.0F, -8.0F, -5.0F);
/* 178 */     this.MainHelmPieceFrontTop.func_78790_a(0.0F, 0.0F, 0.0F, 8, 2, 1, 0.0F);
/*     */ 
/*     */     
/* 181 */     this.HornPieceLeft1 = new ModelRenderer((ModelBase)this, 182, 40);
/* 182 */     this.HornPieceLeft1.func_78793_a(4.0F, -7.5F, -1.0F);
/* 183 */     this.HornPieceLeft1.func_78790_a(0.0F, 0.0F, 0.0F, 1, 4, 4, 0.0F);
/*     */     
/* 185 */     this.HornPieceLeft2 = new ModelRenderer((ModelBase)this, 201, 49);
/* 186 */     this.HornPieceLeft2.func_78793_a(4.6F, -5.5F, 1.03F);
/* 187 */     this.HornPieceLeft2.func_78790_a(0.0F, -1.5F, -1.5F, 2, 3, 3, 0.0F);
/* 188 */     setRotateAngle(this.HornPieceLeft2, 0.0F, -0.0F, -0.13962634F);
/*     */     
/* 190 */     this.HornPieceLeft3 = new ModelRenderer((ModelBase)this, 190, 49);
/* 191 */     this.HornPieceLeft3.func_78793_a(6.1F, -5.6F, 1.0F);
/* 192 */     this.HornPieceLeft3.func_78790_a(0.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F);
/* 193 */     setRotateAngle(this.HornPieceLeft3, 0.0F, -0.0F, -0.5235988F);
/*     */     
/* 195 */     this.HornPieceRight1 = new ModelRenderer((ModelBase)this, 182, 40);
/* 196 */     this.HornPieceRight1.func_78793_a(-5.0F, -7.5F, -1.0F);
/* 197 */     this.HornPieceRight1.func_78790_a(0.0F, 0.0F, 0.0F, 1, 4, 4, 0.0F);
/*     */     
/* 199 */     this.HornPieceRight2 = new ModelRenderer((ModelBase)this, 201, 49);
/* 200 */     this.HornPieceRight2.func_78793_a(-4.6F, -5.5F, 1.03F);
/* 201 */     this.HornPieceRight2.func_78790_a(0.0F, -1.5F, -1.5F, 2, 3, 3, 0.0F);
/* 202 */     setRotateAngle(this.HornPieceRight2, 0.0F, -0.0F, -3.0019662F);
/*     */     
/* 204 */     this.HornPieceRight3 = new ModelRenderer((ModelBase)this, 190, 49);
/* 205 */     this.HornPieceRight3.func_78793_a(-6.1F, -5.6F, 1.0F);
/* 206 */     this.HornPieceRight3.func_78790_a(0.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F);
/* 207 */     setRotateAngle(this.HornPieceRight3, 0.0F, -0.0F, -2.6179938F);
/*     */     
/* 209 */     this.HornPieceLeft4 = new ModelRenderer((ModelBase)this, 181, 49);
/* 210 */     this.HornPieceLeft4.func_78793_a(8.2F, -6.7F, 1.0F);
/* 211 */     this.HornPieceLeft4.func_78790_a(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
/* 212 */     setRotateAngle(this.HornPieceLeft4, 0.0F, -0.0F, -0.87266463F);
/*     */     
/* 214 */     this.HornPieceRight4 = new ModelRenderer((ModelBase)this, 181, 49);
/* 215 */     this.HornPieceRight4.func_78793_a(-8.2F, -6.7F, 1.0F);
/* 216 */     this.HornPieceRight4.func_78790_a(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
/* 217 */     setRotateAngle(this.HornPieceRight4, 0.0F, -0.0F, -2.268928F);
/*     */     
/* 219 */     this.WyHelmPieceTop1 = new ModelRenderer((ModelBase)this, 181, 49);
/* 220 */     this.WyHelmPieceTop1.func_78793_a(-0.5F, -8.5F, -4.5F);
/* 221 */     this.WyHelmPieceTop1.func_78790_a(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
/* 222 */     setRotateAngle(this.WyHelmPieceTop1, 0.27925268F, -0.0F, 0.0F);
/*     */     
/* 224 */     this.WyHelmPieceTop2 = new ModelRenderer((ModelBase)this, 201, 56);
/* 225 */     this.WyHelmPieceTop2.func_78793_a(-1.0F, -10.0F, -2.0F);
/* 226 */     this.WyHelmPieceTop2.func_78790_a(0.0F, 0.0F, 0.0F, 2, 2, 3, 0.0F);
/* 227 */     setRotateAngle(this.WyHelmPieceTop2, 0.08726646F, -0.0F, 0.0F);
/*     */     
/* 229 */     this.WyHelmPieceTop3 = new ModelRenderer((ModelBase)this, 188, 54);
/* 230 */     this.WyHelmPieceTop3.func_78793_a(-1.5F, -11.0F, 1.0F);
/* 231 */     this.WyHelmPieceTop3.func_78790_a(0.0F, 0.0F, 0.0F, 3, 3, 3, 0.0F);
/* 232 */     setRotateAngle(this.WyHelmPieceTop3, -0.27925268F, -0.0F, 0.0F);
/*     */     
/* 234 */     this.WyHelmPieceTop4 = new ModelRenderer((ModelBase)this, 179, 54);
/* 235 */     this.WyHelmPieceTop4.func_78793_a(-0.5F, -10.0F, 1.5F);
/* 236 */     this.WyHelmPieceTop4.func_78790_a(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
/* 237 */     setRotateAngle(this.WyHelmPieceTop4, -0.20943952F, -0.0F, 0.0F);
/*     */ 
/*     */     
/* 240 */     this.field_78115_e = new ModelRenderer((ModelBase)this, 16, 16);
/* 241 */     this.field_78115_e.func_78793_a(0.0F, 0.0F, 0.0F);
/* 242 */     this.field_78115_e.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
/*     */     
/* 244 */     this.DrMainChestPieceTop = new ModelRenderer((ModelBase)this, 214, 0);
/* 245 */     this.DrMainChestPieceTop.func_78793_a(-5.5F, -0.5F, -5.0F);
/* 246 */     this.DrMainChestPieceTop.func_78790_a(0.0F, 0.0F, 0.0F, 11, 4, 10, 0.0F);
/* 247 */     setRotateAngle(this.DrMainChestPieceTop, 0.15707964F, -0.0F, 0.0F);
/*     */     
/* 249 */     this.DrMainChestPieceMid = new ModelRenderer((ModelBase)this, 224, 15);
/* 250 */     this.DrMainChestPieceMid.func_78793_a(-5.0F, 2.0F, -3.0F);
/* 251 */     this.DrMainChestPieceMid.func_78790_a(0.0F, 0.0F, 0.0F, 10, 5, 6, 0.0F);
/*     */     
/* 253 */     this.WyChestPieceTop3 = new ModelRenderer((ModelBase)this, 239, 0);
/* 254 */     this.WyChestPieceTop3.func_78793_a(-3.5F, 4.5F, -3.5F);
/* 255 */     this.WyChestPieceTop3.func_78790_a(1.0F, 0.0F, 0.0F, 4, 4, 1, 0.0F);
/* 256 */     setRotateAngle(this.WyChestPieceTop3, 0.087964594F, 0.086219266F, -0.78906333F);
/*     */     
/* 258 */     this.WyMainChestPieceTop = new ModelRenderer((ModelBase)this, 250, 0);
/* 259 */     this.WyMainChestPieceTop.func_78793_a(-5.7F, 0.0F, -2.6F);
/* 260 */     this.WyMainChestPieceTop.func_78790_a(1.0F, 0.0F, 0.0F, 2, 4, 1, 0.0F);
/* 261 */     setRotateAngle(this.WyMainChestPieceTop, 0.0F, 0.4712389F, 0.0F);
/*     */     
/* 263 */     this.MainChestPieceBottom = new ModelRenderer((ModelBase)this, 228, 27);
/* 264 */     this.MainChestPieceBottom.func_78793_a(-4.5F, 7.0F, -2.5F);
/* 265 */     this.MainChestPieceBottom.func_78790_a(0.0F, 0.0F, 0.0F, 9, 5, 5, 0.0F);
/*     */     
/* 267 */     this.DrChestDecorationPiece1 = new ModelRenderer((ModelBase)this, 244, 38);
/* 268 */     this.DrChestDecorationPiece1.func_78793_a(0.0F, -0.5F, -4.0F);
/* 269 */     this.DrChestDecorationPiece1.func_78790_a(0.0F, 0.0F, -0.5F, 5, 5, 1, 0.0F);
/* 270 */     setRotateAngle(this.DrChestDecorationPiece1, 0.1426609F, -0.13533507F, 0.7946695F);
/*     */     
/* 272 */     this.ChestDecorationPiece2 = new ModelRenderer((ModelBase)this, 229, 38);
/* 273 */     this.ChestDecorationPiece2.func_78793_a(-3.0F, 7.0F, -3.0F);
/* 274 */     this.ChestDecorationPiece2.func_78790_a(0.0F, 0.0F, 0.0F, 6, 3, 1, 0.0F);
/* 275 */     setRotateAngle(this.ChestDecorationPiece2, 0.12217305F, -0.0F, 0.0F);
/*     */     
/* 277 */     this.DrChestDecorationPiece3 = new ModelRenderer((ModelBase)this, 236, 45);
/* 278 */     this.DrChestDecorationPiece3.func_78793_a(-4.0F, 1.0F, 3.0F);
/* 279 */     this.DrChestDecorationPiece3.func_78790_a(0.0F, 0.0F, 0.0F, 8, 5, 2, 0.0F);
/* 280 */     setRotateAngle(this.DrChestDecorationPiece3, -0.39211732F, -0.0F, 0.0F);
/*     */     
/* 282 */     this.ChestDecorationPiece4 = new ModelRenderer((ModelBase)this, 229, 38);
/* 283 */     this.ChestDecorationPiece4.func_78793_a(-3.0F, 6.8F, 2.0F);
/* 284 */     this.ChestDecorationPiece4.func_78790_a(0.0F, 0.0F, 0.0F, 6, 3, 1, 0.0F);
/* 285 */     setRotateAngle(this.ChestDecorationPiece4, -0.12217305F, -0.0F, 0.0F);
/*     */     
/* 287 */     this.WyMainChestPieceMid = new ModelRenderer((ModelBase)this, 224, 13);
/* 288 */     this.WyMainChestPieceMid.func_78793_a(-5.0F, -0.1F, -3.0F);
/* 289 */     this.WyMainChestPieceMid.func_78790_a(0.0F, 0.0F, 0.0F, 10, 7, 6, 0.0F);
/*     */     
/* 291 */     this.WyChestPieceTop2 = new ModelRenderer((ModelBase)this, 250, 0);
/* 292 */     this.WyChestPieceTop2.func_78793_a(2.1F, 0.0F, -4.4F);
/* 293 */     this.WyChestPieceTop2.func_78790_a(1.0F, 0.0F, 0.0F, 2, 4, 1, 0.0F);
/* 294 */     setRotateAngle(this.WyChestPieceTop2, 0.0F, -0.4712389F, 0.0F);
/*     */     
/* 296 */     this.WyChestPieceTop1 = new ModelRenderer((ModelBase)this, 240, 6);
/* 297 */     this.WyChestPieceTop1.func_78793_a(-4.0F, 0.0F, -4.0F);
/* 298 */     this.WyChestPieceTop1.func_78790_a(1.0F, 0.0F, 0.0F, 6, 4, 2, 0.0F);
/*     */     
/* 300 */     this.WyChestDecorationPiece3 = new ModelRenderer((ModelBase)this, 236, 45);
/* 301 */     this.WyChestDecorationPiece3.func_78793_a(-3.5F, 0.0F, 2.5F);
/* 302 */     this.WyChestDecorationPiece3.func_78790_a(0.0F, 0.0F, 0.0F, 7, 5, 2, 0.0F);
/* 303 */     setRotateAngle(this.WyChestDecorationPiece3, -0.2617994F, -0.0F, 0.0F);
/*     */     
/* 305 */     this.BeltFront = new ModelRenderer((ModelBase)this, 121, 25);
/* 306 */     this.BeltFront.func_78793_a(0.5F, 10.01F, -2.0F);
/* 307 */     this.BeltFront.func_78790_a(-5.0F, 0.0F, -0.7F, 9, 2, 1, 0.0F);
/*     */     
/* 309 */     this.BeltBuckle = new ModelRenderer((ModelBase)this, 121, 29);
/* 310 */     this.BeltBuckle.func_78793_a(3.5F, 10.0F, -2.2F);
/* 311 */     this.BeltBuckle.func_78790_a(-5.0F, 0.0F, -0.7F, 3, 2, 1, 0.0F);
/*     */     
/* 313 */     this.BeltBack = new ModelRenderer((ModelBase)this, 121, 25);
/* 314 */     this.BeltBack.func_78793_a(0.5F, 10.01F, 2.0F);
/* 315 */     this.BeltBack.func_78790_a(-5.0F, 0.0F, -0.3F, 9, 2, 1, 0.0F);
/*     */     
/* 317 */     this.BeltRight = new ModelRenderer((ModelBase)this, 142, 25);
/* 318 */     this.BeltRight.func_78793_a(-4.7F, 10.01F, -2.2F);
/* 319 */     this.BeltRight.func_78790_a(0.0F, 0.0F, -0.3F, 1, 2, 5, 0.0F);
/*     */     
/* 321 */     this.BeltLeft = new ModelRenderer((ModelBase)this, 142, 25);
/* 322 */     this.BeltLeft.func_78793_a(3.7F, 10.01F, -2.2F);
/* 323 */     this.BeltLeft.func_78790_a(0.0F, 0.0F, -0.3F, 1, 2, 5, 0.0F);
/*     */ 
/*     */     
/* 326 */     this.field_78113_g = new ModelRenderer((ModelBase)this, 40, 16);
/* 327 */     this.field_78113_g.func_78793_a(5.0F, 2.0F, 0.0F);
/* 328 */     this.field_78113_g.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
/*     */     
/* 330 */     this.ShoulderPadLeft1 = new ModelRenderer((ModelBase)this, 236, 53);
/* 331 */     this.ShoulderPadLeft1.func_78793_a(-1.5F, -2.0F, -2.5F);
/* 332 */     this.ShoulderPadLeft1.func_78790_a(0.0F, 0.0F, 0.0F, 5, 4, 5, 0.0F);
/*     */     
/* 334 */     this.ShoulderPadLeft2 = new ModelRenderer((ModelBase)this, 232, 63);
/* 335 */     this.ShoulderPadLeft2.func_78793_a(-1.0F, -2.5F, -3.0F);
/* 336 */     this.ShoulderPadLeft2.func_78790_a(-0.3F, 0.0F, 0.0F, 6, 2, 6, 0.0F);
/* 337 */     setRotateAngle(this.ShoulderPadLeft2, 0.0F, -0.0F, 0.36651915F);
/*     */     
/* 339 */     this.ShoulderPadLeft3 = new ModelRenderer((ModelBase)this, 215, 53);
/* 340 */     this.ShoulderPadLeft3.field_78809_i = true;
/* 341 */     this.ShoulderPadLeft3.func_78793_a(-1.0F, -3.0F, -2.5F);
/* 342 */     this.ShoulderPadLeft3.func_78790_a(0.3F, -0.5F, 0.0F, 5, 2, 5, 0.0F);
/* 343 */     setRotateAngle(this.ShoulderPadLeft3, 0.0F, -0.0F, 0.20943952F);
/*     */     
/* 345 */     this.ShoulderPadLeft4 = new ModelRenderer((ModelBase)this, 219, 61);
/* 346 */     this.ShoulderPadLeft4.func_78793_a(-0.5F, -2.5F, -2.0F);
/* 347 */     this.ShoulderPadLeft4.func_78790_a(0.7F, -2.0F, 0.5F, 3, 2, 3, 0.0F);
/* 348 */     setRotateAngle(this.ShoulderPadLeft4, 0.0F, -0.0F, 0.12217305F);
/*     */     
/* 350 */     this.MainArmGuardLeft = new ModelRenderer((ModelBase)this, 240, 72);
/* 351 */     this.MainArmGuardLeft.func_78793_a(0.5F, 3.0F, -2.5F);
/* 352 */     this.MainArmGuardLeft.func_78790_a(0.0F, 0.0F, 0.0F, 3, 5, 5, 0.0F);
/*     */     
/* 354 */     this.ArmGuardPieceLeft1 = new ModelRenderer((ModelBase)this, 223, 67);
/* 355 */     this.ArmGuardPieceLeft1.func_78793_a(2.5F, 6.0F, -1.5F);
/* 356 */     this.ArmGuardPieceLeft1.func_78790_a(0.2F, -0.3F, 0.0F, 1, 2, 3, 0.0F);
/* 357 */     setRotateAngle(this.ArmGuardPieceLeft1, 0.0F, -0.0F, -0.17453292F);
/*     */     
/* 359 */     this.ArmGuardPieceLeft2 = new ModelRenderer((ModelBase)this, 223, 67);
/* 360 */     this.ArmGuardPieceLeft2.func_78793_a(2.5F, 5.0F, -1.5F);
/* 361 */     this.ArmGuardPieceLeft2.func_78790_a(0.2F, -0.3F, 0.0F, 1, 2, 3, 0.0F);
/* 362 */     setRotateAngle(this.ArmGuardPieceLeft2, 0.0F, -0.0F, -0.17453292F);
/*     */     
/* 364 */     this.ArmGuardPieceLeft3 = new ModelRenderer((ModelBase)this, 223, 67);
/* 365 */     this.ArmGuardPieceLeft3.func_78793_a(2.5F, 4.0F, -1.5F);
/* 366 */     this.ArmGuardPieceLeft3.func_78790_a(0.2F, -0.3F, 0.0F, 1, 2, 3, 0.0F);
/* 367 */     setRotateAngle(this.ArmGuardPieceLeft3, 0.0F, -0.0F, -0.17453292F);
/*     */     
/* 369 */     this.ArmGuardPieceLeft4 = new ModelRenderer((ModelBase)this, 231, 72);
/* 370 */     this.ArmGuardPieceLeft4.func_78793_a(2.5F, 3.0F, -1.5F);
/* 371 */     this.ArmGuardPieceLeft4.func_78790_a(0.2F, 0.6F, 0.0F, 1, 1, 3, 0.0F);
/* 372 */     setRotateAngle(this.ArmGuardPieceLeft4, 0.0F, -0.0F, -0.17453292F);
/*     */     
/* 374 */     this.ArmStrapLeftTop = new ModelRenderer((ModelBase)this, 225, 77);
/* 375 */     this.ArmStrapLeftTop.func_78793_a(-1.43F, 3.0F, -2.5F);
/* 376 */     this.ArmStrapLeftTop.func_78790_a(0.0F, 0.0F, 0.0F, 2, 1, 5, 0.0F);
/*     */     
/* 378 */     this.ArmStrapLeftBottom = new ModelRenderer((ModelBase)this, 225, 77);
/* 379 */     this.ArmStrapLeftBottom.func_78793_a(-1.5F, 7.0F, -2.5F);
/* 380 */     this.ArmStrapLeftBottom.func_78790_a(0.0F, 0.0F, 0.0F, 2, 1, 5, 0.0F);
/*     */ 
/*     */ 
/*     */     
/* 384 */     this.MainArmGuardRight = new ModelRenderer((ModelBase)this, 240, 72);
/* 385 */     this.MainArmGuardRight.func_78793_a(-3.5F, 3.0F, -2.5F);
/* 386 */     this.MainArmGuardRight.func_78790_a(0.0F, 0.0F, 0.0F, 3, 5, 5, 0.0F);
/*     */     
/* 388 */     this.ArmGuardPieceRight1 = new ModelRenderer((ModelBase)this, 231, 72);
/* 389 */     this.ArmGuardPieceRight1.func_78793_a(-3.9F, 2.8F, -1.5F);
/* 390 */     this.ArmGuardPieceRight1.func_78790_a(0.2F, 0.6F, 0.0F, 1, 1, 3, 0.0F);
/* 391 */     setRotateAngle(this.ArmGuardPieceRight1, 0.0F, -0.0F, 0.17453292F);
/*     */     
/* 393 */     this.ArmGuardPieceRight2 = new ModelRenderer((ModelBase)this, 223, 67);
/* 394 */     this.ArmGuardPieceRight2.func_78793_a(-4.6F, 4.1F, -1.5F);
/* 395 */     this.ArmGuardPieceRight2.func_78790_a(0.8F, -0.7F, 0.0F, 1, 2, 3, 0.0F);
/* 396 */     setRotateAngle(this.ArmGuardPieceRight2, 0.0F, -0.0F, 0.17453292F);
/*     */     
/* 398 */     this.ArmGuardPieceRight3 = new ModelRenderer((ModelBase)this, 223, 67);
/* 399 */     this.ArmGuardPieceRight3.func_78793_a(-4.03F, 5.3F, -1.5F);
/* 400 */     this.ArmGuardPieceRight3.func_78790_a(0.2F, -0.8F, 0.0F, 1, 2, 3, 0.0F);
/* 401 */     setRotateAngle(this.ArmGuardPieceRight3, 0.0F, -0.0F, 0.17453292F);
/*     */     
/* 403 */     this.ArmGuardPieceRight4 = new ModelRenderer((ModelBase)this, 223, 67);
/* 404 */     this.ArmGuardPieceRight4.func_78793_a(-4.0F, 6.8F, -1.5F);
/* 405 */     this.ArmGuardPieceRight4.func_78790_a(0.1F, -1.3F, 0.0F, 1, 2, 3, 0.0F);
/* 406 */     setRotateAngle(this.ArmGuardPieceRight4, 0.0F, -0.0F, 0.17453292F);
/*     */     
/* 408 */     this.ShoulderPadRight1 = new ModelRenderer((ModelBase)this, 236, 53);
/* 409 */     this.ShoulderPadRight1.func_78793_a(-3.5F, -2.0F, -2.5F);
/* 410 */     this.ShoulderPadRight1.func_78790_a(0.0F, 0.0F, 0.0F, 5, 4, 5, 0.0F);
/*     */     
/* 412 */     this.ShoulderPadRight2 = new ModelRenderer((ModelBase)this, 232, 63);
/* 413 */     this.ShoulderPadRight2.func_78793_a(-5.0F, -2.5F, -3.0F);
/* 414 */     this.ShoulderPadRight2.func_78790_a(-0.1F, 2.1F, 0.0F, 6, 2, 6, 0.0F);
/* 415 */     setRotateAngle(this.ShoulderPadRight2, 0.0F, -0.0F, -0.36651915F);
/*     */     
/* 417 */     this.ShoulderPadRight3 = new ModelRenderer((ModelBase)this, 215, 53);
/* 418 */     this.ShoulderPadRight3.func_78793_a(-5.0F, -3.0F, -2.5F);
/* 419 */     this.ShoulderPadRight3.func_78790_a(0.6F, 0.7F, 0.0F, 5, 2, 5, 0.0F);
/* 420 */     setRotateAngle(this.ShoulderPadRight3, 0.0F, -0.0F, -0.20943952F);
/*     */     
/* 422 */     this.ShoulderPadRight4 = new ModelRenderer((ModelBase)this, 219, 61);
/* 423 */     this.ShoulderPadRight4.func_78793_a(-4.5F, -2.5F, -2.0F);
/* 424 */     this.ShoulderPadRight4.func_78790_a(1.3F, -1.5F, 0.5F, 3, 2, 3, 0.0F);
/* 425 */     setRotateAngle(this.ShoulderPadRight4, 0.0F, -0.0F, -0.12217305F);
/*     */     
/* 427 */     this.field_78112_f = new ModelRenderer((ModelBase)this, 40, 16);
/* 428 */     this.field_78112_f.func_78793_a(-5.0F, 2.0F, 0.0F);
/* 429 */     this.field_78112_f.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
/*     */     
/* 431 */     this.ArmStrapRightTop = new ModelRenderer((ModelBase)this, 225, 77);
/* 432 */     this.ArmStrapRightTop.func_78793_a(-0.5F, 3.0F, -2.5F);
/* 433 */     this.ArmStrapRightTop.func_78790_a(0.0F, 0.0F, 0.0F, 2, 1, 5, 0.0F);
/*     */     
/* 435 */     this.ArmStrapRightBottom = new ModelRenderer((ModelBase)this, 225, 77);
/* 436 */     this.ArmStrapRightBottom.func_78793_a(-0.5F, 7.0F, -2.5F);
/* 437 */     this.ArmStrapRightBottom.func_78790_a(0.0F, 0.0F, 0.0F, 2, 1, 5, 0.0F);
/*     */ 
/*     */     
/* 440 */     this.LeggsTop = new ModelRenderer((ModelBase)this, 121, 15);
/* 441 */     this.LeggsTop.func_78793_a(0.5F, 8.0F, -2.4F);
/* 442 */     this.LeggsTop.func_78790_a(-5.0F, 0.0F, 0.0F, 9, 4, 5, 0.0F);
/*     */     
/* 444 */     this.field_78124_i = new ModelRenderer((ModelBase)this, 0, 16);
/* 445 */     this.field_78124_i.func_78793_a(2.0F, 12.0F, 0.0F);
/* 446 */     this.field_78124_i.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
/*     */     
/* 448 */     this.MainLegPieceLeft = new ModelRenderer((ModelBase)this, 149, 0);
/* 449 */     this.MainLegPieceLeft.func_78793_a(-2.0F, -1.0F, -2.5F);
/* 450 */     this.MainLegPieceLeft.func_78790_a(0.0F, 0.0F, 0.0F, 4, 10, 5, 0.0F);
/*     */     
/* 452 */     this.LegPieceLeft1 = new ModelRenderer((ModelBase)this, 138, 0);
/* 453 */     this.LegPieceLeft1.func_78793_a(1.5F, -1.0F, -2.0F);
/* 454 */     this.LegPieceLeft1.func_78790_a(0.0F, 0.0F, 0.0F, 1, 10, 4, 0.0F);
/*     */     
/* 456 */     this.LegPieceLeft2 = new ModelRenderer((ModelBase)this, 138, 0);
/* 457 */     this.LegPieceLeft2.func_78793_a(-2.5F, -1.0F, -2.0F);
/* 458 */     this.LegPieceLeft2.func_78790_a(0.0F, 0.0F, 0.0F, 1, 10, 4, 0.0F);
/*     */     
/* 460 */     this.MainKneePadLeft = new ModelRenderer((ModelBase)this, 149, 15);
/* 461 */     this.MainKneePadLeft.func_78793_a(0.0F, 2.5F, -3.0F);
/* 462 */     this.MainKneePadLeft.func_78790_a(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
/* 463 */     setRotateAngle(this.MainKneePadLeft, 0.0F, -0.0F, 0.7853982F);
/*     */     
/* 465 */     this.KneePieceLeft1 = new ModelRenderer((ModelBase)this, 156, 15);
/* 466 */     this.KneePieceLeft1.func_78793_a(-0.5F, 2.0F, -2.7F);
/* 467 */     this.KneePieceLeft1.func_78790_a(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
/*     */ 
/*     */     
/* 470 */     this.field_78123_h = new ModelRenderer((ModelBase)this, 0, 16);
/* 471 */     this.field_78123_h.func_78793_a(-2.0F, 12.0F, 0.0F);
/* 472 */     this.field_78123_h.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
/*     */     
/* 474 */     this.MainLegPieceRight = new ModelRenderer((ModelBase)this, 149, 0);
/* 475 */     this.MainLegPieceRight.func_78793_a(-2.0F, -1.0F, -2.5F);
/* 476 */     this.MainLegPieceRight.func_78790_a(0.0F, 0.0F, 0.0F, 4, 10, 5, 0.0F);
/*     */     
/* 478 */     this.LegPieceRight1 = new ModelRenderer((ModelBase)this, 138, 0);
/* 479 */     this.LegPieceRight1.func_78793_a(-2.5F, -1.0F, -2.0F);
/* 480 */     this.LegPieceRight1.func_78790_a(0.0F, 0.0F, 0.0F, 1, 10, 4, 0.0F);
/*     */     
/* 482 */     this.LegPieceRight2 = new ModelRenderer((ModelBase)this, 138, 0);
/* 483 */     this.LegPieceRight2.func_78793_a(1.5F, -1.0F, -2.0F);
/* 484 */     this.LegPieceRight2.func_78790_a(0.0F, 0.0F, 0.0F, 1, 10, 4, 0.0F);
/*     */     
/* 486 */     this.MainKneePadRight = new ModelRenderer((ModelBase)this, 149, 15);
/* 487 */     this.MainKneePadRight.func_78793_a(0.0F, 2.5F, -3.0F);
/* 488 */     this.MainKneePadRight.func_78790_a(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
/*     */     
/* 490 */     setRotateAngle(this.MainKneePadRight, 0.0F, -0.0F, 0.7853982F);
/* 491 */     this.KneePieceRight1 = new ModelRenderer((ModelBase)this, 156, 15);
/* 492 */     this.KneePieceRight1.func_78793_a(-0.5F, 2.0F, -2.7F);
/* 493 */     this.KneePieceRight1.func_78790_a(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
/*     */ 
/*     */ 
/*     */     
/* 497 */     this.MainBootPieceLeft = new ModelRenderer((ModelBase)this, 119, 0);
/* 498 */     this.MainBootPieceLeft.func_78793_a(-2.0F, 9.1F, -2.5F);
/* 499 */     this.MainBootPieceLeft.func_78790_a(0.0F, 0.0F, 0.0F, 4, 3, 5, 0.0F);
/*     */     
/* 501 */     this.BootPieceLeft1 = new ModelRenderer((ModelBase)this, 129, 9);
/* 502 */     this.BootPieceLeft1.func_78793_a(-1.5F, 9.0F, -3.5F);
/* 503 */     this.BootPieceLeft1.func_78790_a(0.0F, 0.0F, 0.0F, 3, 3, 1, 0.0F);
/*     */     
/* 505 */     this.BootPieceLeft2 = new ModelRenderer((ModelBase)this, 122, 9);
/* 506 */     this.BootPieceLeft2.func_78793_a(-1.0F, 9.0F, -2.5F);
/* 507 */     this.BootPieceLeft2.func_78790_a(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
/* 508 */     setRotateAngle(this.BootPieceLeft2, -0.40142572F, -0.0F, 0.0F);
/*     */     
/* 510 */     this.BootPieceLeft3 = new ModelRenderer((ModelBase)this, 138, 0);
/* 511 */     this.BootPieceLeft3.func_78793_a(1.5F, 9.05F, -2.0F);
/* 512 */     this.BootPieceLeft3.func_78790_a(0.0F, 0.0F, 0.0F, 1, 3, 4, 0.0F);
/*     */     
/* 514 */     this.BootPieceLeft4 = new ModelRenderer((ModelBase)this, 138, 0);
/* 515 */     this.BootPieceLeft4.func_78793_a(-2.5F, 9.05F, -2.0F);
/* 516 */     this.BootPieceLeft4.func_78790_a(0.0F, 0.0F, 0.0F, 1, 3, 4, 0.0F);
/*     */ 
/*     */     
/* 519 */     this.MainBootPieceRight = new ModelRenderer((ModelBase)this, 119, 0);
/* 520 */     this.MainBootPieceRight.func_78793_a(-2.0F, 9.1F, -2.5F);
/* 521 */     this.MainBootPieceRight.func_78790_a(0.0F, 0.0F, 0.0F, 4, 3, 5, 0.0F);
/*     */     
/* 523 */     this.BootPieceRight1 = new ModelRenderer((ModelBase)this, 129, 9);
/* 524 */     this.BootPieceRight1.func_78793_a(-1.5F, 9.0F, -3.5F);
/* 525 */     this.BootPieceRight1.func_78790_a(0.0F, 0.0F, 0.0F, 3, 3, 1, 0.0F);
/*     */     
/* 527 */     this.BootPieceRight2 = new ModelRenderer((ModelBase)this, 122, 9);
/* 528 */     this.BootPieceRight2.func_78793_a(-1.0F, 9.0F, -2.5F);
/* 529 */     this.BootPieceRight2.func_78790_a(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
/* 530 */     setRotateAngle(this.BootPieceRight2, -0.40142572F, -0.0F, 0.0F);
/*     */     
/* 532 */     this.BootPieceRight3 = new ModelRenderer((ModelBase)this, 138, 0);
/* 533 */     this.BootPieceRight3.func_78793_a(-2.5F, 9.05F, -2.0F);
/* 534 */     this.BootPieceRight3.func_78790_a(0.0F, 0.0F, 0.0F, 1, 3, 4, 0.0F);
/*     */     
/* 536 */     this.BootPieceRight4 = new ModelRenderer((ModelBase)this, 138, 0);
/* 537 */     this.BootPieceRight4.func_78793_a(1.5F, 9.05F, -2.0F);
/* 538 */     this.BootPieceRight4.func_78790_a(0.0F, 0.0F, 0.0F, 1, 3, 4, 0.0F);
/*     */ 
/*     */     
/* 541 */     this.field_78116_c.field_78804_l.clear();
/* 542 */     this.field_78114_d.field_78804_l.clear();
/* 543 */     if (isHelmet) {
/* 544 */       this.field_78116_c.func_78792_a(this.MainHelmPieceLeft1);
/* 545 */       this.field_78116_c.func_78792_a(this.HelmPieceFront2);
/* 546 */       this.field_78116_c.func_78792_a(this.MainHelmPieceRight2);
/* 547 */       this.field_78116_c.func_78792_a(this.MainHelmPieceTop);
/* 548 */       this.field_78116_c.func_78792_a(this.MainHelmPieceFrontBottom);
/* 549 */       this.field_78116_c.func_78792_a(this.HornPieceRight1);
/* 550 */       this.field_78116_c.func_78792_a(this.HornPieceLeft1);
/*     */       
/* 552 */       this.field_78116_c.func_78792_a(this.MainHelmPieceLeft2);
/* 553 */       this.field_78116_c.func_78792_a(this.HornPieceRight3);
/* 554 */       this.field_78116_c.func_78792_a(this.HornPieceLeft3);
/* 555 */       this.field_78116_c.func_78792_a(this.HelmPieceBack1);
/*     */       
/* 557 */       this.field_78116_c.func_78792_a(this.MainHelmPieceLeft3);
/* 558 */       this.field_78116_c.func_78792_a(this.MainHelmPieceRight1);
/* 559 */       this.field_78116_c.func_78792_a(this.HornPieceRight2);
/* 560 */       this.field_78116_c.func_78792_a(this.MainHelmPieceBack);
/* 561 */       this.field_78116_c.func_78792_a(this.MainHelmPieceRight3);
/* 562 */       this.field_78116_c.func_78792_a(this.HelmPieceFront1);
/* 563 */       this.field_78116_c.func_78792_a(this.MainHelmPieceBottom);
/* 564 */       this.field_78116_c.func_78792_a(this.MainHelmPieceFrontTop);
/* 565 */       this.field_78116_c.func_78792_a(this.HornPieceLeft2);
/*     */       
/* 567 */       if (isDraconic) {
/* 568 */         this.field_78116_c.func_78792_a(this.HornPieceLeft4);
/* 569 */         this.field_78116_c.func_78792_a(this.HornPieceRight4);
/*     */       } else {
/* 571 */         this.field_78116_c.func_78792_a(this.WyHelmPieceTop1);
/* 572 */         this.field_78116_c.func_78792_a(this.WyHelmPieceTop2);
/* 573 */         this.field_78116_c.func_78792_a(this.WyHelmPieceTop3);
/* 574 */         this.field_78116_c.func_78792_a(this.WyHelmPieceTop4);
/*     */       } 
/*     */     } 
/*     */     
/* 578 */     this.field_78115_e.field_78804_l.clear();
/* 579 */     this.field_78112_f.field_78804_l.clear();
/* 580 */     this.field_78113_g.field_78804_l.clear();
/* 581 */     if (isChestPiece) {
/* 582 */       this.field_78115_e.func_78792_a(this.MainChestPieceBottom);
/* 583 */       this.field_78115_e.func_78792_a(this.ChestDecorationPiece2);
/* 584 */       this.field_78115_e.func_78792_a(this.ChestDecorationPiece4);
/*     */       
/* 586 */       if (isDraconic) {
/* 587 */         this.field_78115_e.func_78792_a(this.DrMainChestPieceTop);
/* 588 */         this.field_78115_e.func_78792_a(this.DrMainChestPieceMid);
/* 589 */         this.field_78115_e.func_78792_a(this.DrChestDecorationPiece1);
/* 590 */         this.field_78115_e.func_78792_a(this.DrChestDecorationPiece3);
/*     */         
/* 592 */         this.field_78113_g.func_78792_a(this.ArmGuardPieceLeft1);
/* 593 */         this.field_78113_g.func_78792_a(this.ArmGuardPieceLeft2);
/* 594 */         this.field_78113_g.func_78792_a(this.ArmGuardPieceLeft3);
/* 595 */         this.field_78113_g.func_78792_a(this.ArmGuardPieceLeft4);
/* 596 */         this.field_78113_g.func_78792_a(this.MainArmGuardLeft);
/* 597 */         this.field_78113_g.func_78792_a(this.ArmStrapLeftTop);
/* 598 */         this.field_78113_g.func_78792_a(this.ArmStrapLeftBottom);
/*     */         
/* 600 */         this.field_78112_f.func_78792_a(this.ArmGuardPieceRight1);
/* 601 */         this.field_78112_f.func_78792_a(this.ArmGuardPieceRight2);
/* 602 */         this.field_78112_f.func_78792_a(this.ArmGuardPieceRight3);
/* 603 */         this.field_78112_f.func_78792_a(this.ArmGuardPieceRight4);
/* 604 */         this.field_78112_f.func_78792_a(this.MainArmGuardRight);
/* 605 */         this.field_78112_f.func_78792_a(this.ArmStrapRightTop);
/* 606 */         this.field_78112_f.func_78792_a(this.ArmStrapRightBottom);
/*     */       } else {
/* 608 */         this.field_78115_e.func_78792_a(this.WyMainChestPieceMid);
/* 609 */         this.field_78115_e.func_78792_a(this.WyChestPieceTop2);
/* 610 */         this.field_78115_e.func_78792_a(this.WyChestPieceTop1);
/* 611 */         this.field_78115_e.func_78792_a(this.WyChestPieceTop3);
/* 612 */         this.field_78115_e.func_78792_a(this.WyChestDecorationPiece3);
/* 613 */         this.field_78115_e.func_78792_a(this.WyMainChestPieceTop);
/*     */       } 
/*     */       
/* 616 */       this.field_78113_g.func_78792_a(this.ShoulderPadLeft1);
/* 617 */       this.field_78113_g.func_78792_a(this.ShoulderPadLeft2);
/* 618 */       this.field_78113_g.func_78792_a(this.ShoulderPadLeft3);
/* 619 */       this.field_78113_g.func_78792_a(this.ShoulderPadLeft4);
/*     */       
/* 621 */       this.field_78112_f.func_78792_a(this.ShoulderPadRight1);
/* 622 */       this.field_78112_f.func_78792_a(this.ShoulderPadRight2);
/* 623 */       this.field_78112_f.func_78792_a(this.ShoulderPadRight3);
/* 624 */       this.field_78112_f.func_78792_a(this.ShoulderPadRight4);
/*     */     } 
/*     */     
/* 627 */     this.field_78124_i.field_78804_l.clear();
/* 628 */     this.field_78123_h.field_78804_l.clear();
/* 629 */     if (isLeggings) {
/* 630 */       this.field_78115_e.func_78792_a(this.LeggsTop);
/*     */       
/* 632 */       this.field_78124_i.func_78792_a(this.LegPieceLeft1);
/* 633 */       this.field_78124_i.func_78792_a(this.MainKneePadLeft);
/* 634 */       this.field_78124_i.func_78792_a(this.MainLegPieceLeft);
/* 635 */       this.field_78124_i.func_78792_a(this.KneePieceLeft1);
/* 636 */       this.field_78124_i.func_78792_a(this.LegPieceLeft2);
/*     */       
/* 638 */       this.field_78123_h.func_78792_a(this.LegPieceRight2);
/* 639 */       this.field_78123_h.func_78792_a(this.LegPieceRight1);
/* 640 */       this.field_78123_h.func_78792_a(this.MainKneePadRight);
/* 641 */       this.field_78123_h.func_78792_a(this.KneePieceRight1);
/* 642 */       this.field_78123_h.func_78792_a(this.MainLegPieceRight);
/*     */       
/* 644 */       if (isDraconic) {
/* 645 */         this.field_78115_e.func_78792_a(this.BeltFront);
/* 646 */         this.field_78115_e.func_78792_a(this.BeltBack);
/* 647 */         this.field_78115_e.func_78792_a(this.BeltLeft);
/* 648 */         this.field_78115_e.func_78792_a(this.BeltRight);
/* 649 */         this.field_78115_e.func_78792_a(this.BeltBuckle);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 654 */     if (isdBoots) {
/* 655 */       this.field_78124_i.func_78792_a(this.MainBootPieceLeft);
/* 656 */       this.field_78124_i.func_78792_a(this.BootPieceLeft2);
/* 657 */       this.field_78124_i.func_78792_a(this.BootPieceLeft1);
/* 658 */       this.field_78124_i.func_78792_a(this.BootPieceLeft3);
/* 659 */       this.field_78124_i.func_78792_a(this.BootPieceLeft4);
/*     */       
/* 661 */       this.field_78123_h.func_78792_a(this.MainBootPieceRight);
/* 662 */       this.field_78123_h.func_78792_a(this.BootPieceRight1);
/* 663 */       this.field_78123_h.func_78792_a(this.BootPieceRight2);
/* 664 */       this.field_78123_h.func_78792_a(this.BootPieceRight3);
/* 665 */       this.field_78123_h.func_78792_a(this.BootPieceRight4);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
/* 672 */     func_78087_a(f, f1, f2, f3, f4, f5, entity);
/* 673 */     this.field_78123_h.func_78785_a(f5);
/* 674 */     this.field_78115_e.func_78785_a(f5 * 1.05F);
/* 675 */     this.field_78113_g.func_78785_a(f5);
/* 676 */     this.field_78112_f.func_78785_a(f5);
/* 677 */     this.field_78124_i.func_78785_a(f5);
/* 678 */     this.field_78116_c.func_78785_a(f5 * 1.05F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
/* 685 */     modelRenderer.field_78795_f = x;
/* 686 */     modelRenderer.field_78796_g = y;
/* 687 */     modelRenderer.field_78808_h = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\model\ModelDraconicArmorOld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */