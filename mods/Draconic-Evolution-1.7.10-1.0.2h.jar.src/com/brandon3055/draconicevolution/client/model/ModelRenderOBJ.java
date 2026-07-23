/*     */ package com.brandon3055.draconicevolution.client.model;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.client.model.AdvancedModelLoader;
/*     */ import net.minecraftforge.client.model.IModelCustom;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class ModelRenderOBJ
/*     */   extends ModelRenderer
/*     */ {
/*     */   private IModelCustom model;
/*     */   private ResourceLocation texture;
/*     */   private int displayList;
/*     */   private boolean compiled = false;
/*  20 */   public float scale = 0.0F;
/*     */   
/*     */   public ModelRenderOBJ(ModelBase baseModel, ResourceLocation customModel, ResourceLocation texture) {
/*  23 */     super(baseModel);
/*  24 */     this.model = AdvancedModelLoader.loadModel(customModel);
/*  25 */     this.texture = texture;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_78785_a(float scale) {
/*  30 */     if (!this.field_78807_k && this.field_78806_j) {
/*  31 */       if (!this.compiled) {
/*  32 */         compileDisplayList(scale);
/*     */       }
/*     */       
/*  35 */       GL11.glTranslatef(this.field_82906_o, this.field_82908_p, this.field_82907_q);
/*     */       
/*  37 */       if (this.field_78795_f == 0.0F && this.field_78796_g == 0.0F && this.field_78808_h == 0.0F) {
/*  38 */         if (this.field_78800_c == 0.0F && this.field_78797_d == 0.0F && this.field_78798_e == 0.0F) {
/*  39 */           GL11.glCallList(this.displayList);
/*     */         } else {
/*  41 */           GL11.glTranslatef(this.field_78800_c * scale, this.field_78797_d * scale, this.field_78798_e * scale);
/*  42 */           GL11.glCallList(this.displayList);
/*  43 */           GL11.glTranslatef(-this.field_78800_c * scale, -this.field_78797_d * scale, -this.field_78798_e * scale);
/*     */         } 
/*     */       } else {
/*  46 */         GL11.glPushMatrix();
/*  47 */         GL11.glTranslatef(this.field_78800_c * scale, this.field_78797_d * scale, this.field_78798_e * scale);
/*  48 */         if (this.field_78808_h != 0.0F) {
/*  49 */           GL11.glRotatef(this.field_78808_h * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */         }
/*     */         
/*  52 */         if (this.field_78796_g != 0.0F) {
/*  53 */           GL11.glRotatef(this.field_78796_g * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/*  56 */         if (this.field_78795_f != 0.0F) {
/*  57 */           GL11.glRotatef(this.field_78795_f * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */         
/*  60 */         GL11.glCallList(this.displayList);
/*  61 */         GL11.glPopMatrix();
/*     */       } 
/*     */       
/*  64 */       GL11.glTranslatef(-this.field_82906_o, -this.field_82908_p, -this.field_82907_q);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void compileDisplayList(float scale) {
/*  70 */     if (this.scale == 0.0F) this.scale = scale; 
/*  71 */     scale = this.scale;
/*  72 */     this.displayList = GLAllocation.func_74526_a(1);
/*  73 */     GL11.glNewList(this.displayList, 4864);
/*     */     
/*  75 */     GL11.glPushMatrix();
/*  76 */     ResourceHandler.bindTexture(this.texture);
/*     */     
/*  78 */     GL11.glScalef(scale, scale, scale);
/*  79 */     GL11.glRotatef(180.0F, -1.0F, 0.0F, 1.0F);
/*  80 */     this.model.renderAll();
/*  81 */     GL11.glPopMatrix();
/*     */     
/*  83 */     GL11.glEndList();
/*  84 */     this.compiled = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_78791_b(float scale) {
/*  89 */     if (!this.field_78807_k && this.field_78806_j) {
/*  90 */       if (!this.compiled) {
/*  91 */         compileDisplayList(scale);
/*     */       }
/*     */       
/*  94 */       GL11.glPushMatrix();
/*  95 */       GL11.glTranslatef(this.field_78800_c * scale, this.field_78797_d * scale, this.field_78798_e * scale);
/*  96 */       if (this.field_78796_g != 0.0F) {
/*  97 */         GL11.glRotatef(this.field_78796_g * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */       }
/*     */       
/* 100 */       if (this.field_78795_f != 0.0F) {
/* 101 */         GL11.glRotatef(this.field_78795_f * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */       }
/*     */       
/* 104 */       if (this.field_78808_h != 0.0F) {
/* 105 */         GL11.glRotatef(this.field_78808_h * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */       }
/*     */       
/* 108 */       GL11.glCallList(this.displayList);
/* 109 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_78794_c(float scale) {
/* 116 */     if (!this.field_78807_k && this.field_78806_j)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 121 */       if (this.field_78795_f == 0.0F && this.field_78796_g == 0.0F && this.field_78808_h == 0.0F) {
/* 122 */         if (this.field_78800_c != 0.0F || this.field_78797_d != 0.0F || this.field_78798_e != 0.0F) {
/* 123 */           GL11.glTranslatef(this.field_78800_c * scale, this.field_78797_d * scale, this.field_78798_e * scale);
/*     */         }
/*     */       } else {
/* 126 */         GL11.glTranslatef(this.field_78800_c * scale, this.field_78797_d * scale, this.field_78798_e * scale);
/* 127 */         if (this.field_78808_h != 0.0F) {
/* 128 */           GL11.glRotatef(this.field_78808_h * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */         }
/*     */         
/* 131 */         if (this.field_78796_g != 0.0F) {
/* 132 */           GL11.glRotatef(this.field_78796_g * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 135 */         if (this.field_78795_f != 0.0F)
/* 136 */           GL11.glRotatef(this.field_78795_f * 57.295776F, 1.0F, 0.0F, 0.0F); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\model\ModelRenderOBJ.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */