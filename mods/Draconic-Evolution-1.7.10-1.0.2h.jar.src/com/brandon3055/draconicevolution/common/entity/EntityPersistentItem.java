/*     */ package com.brandon3055.draconicevolution.common.entity;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.item.ItemExpireEvent;
/*     */ 
/*     */ public class EntityPersistentItem
/*     */   extends EntityItem
/*     */ {
/*     */   public EntityPersistentItem(World par1World, double par2, double par4, double par6) {
/*  19 */     super(par1World, par2, par4, par6);
/*  20 */     this.field_70178_ae = true;
/*  21 */     this.lifespan = 72000;
/*     */   }
/*     */   
/*     */   public EntityPersistentItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack) {
/*  25 */     this(par1World, par2, par4, par6);
/*  26 */     func_92058_a(par8ItemStack);
/*  27 */     this.lifespan = 72000;
/*     */   }
/*     */   
/*     */   public EntityPersistentItem(World par1World) {
/*  31 */     super(par1World);
/*  32 */     this.field_70178_ae = true;
/*  33 */     this.lifespan = 72000;
/*     */   }
/*     */   
/*     */   public EntityPersistentItem(World world, Entity original, ItemStack stack) {
/*  37 */     this(world, original.field_70165_t, original.field_70163_u, original.field_70161_v);
/*  38 */     if (original instanceof EntityItem) { this.field_145804_b = ((EntityItem)original).field_145804_b; }
/*  39 */     else { this.field_145804_b = 20; }
/*  40 */      this.field_70159_w = original.field_70159_w;
/*  41 */     this.field_70181_x = original.field_70181_x;
/*  42 */     this.field_70179_y = original.field_70179_y;
/*  43 */     func_92058_a(stack);
/*  44 */     this.lifespan = 72000;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
/*  49 */     return par1DamageSource.func_76355_l().equals("outOfWorld");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70112_a(double p_70112_1_) {
/*  54 */     double d1 = this.field_70121_D.func_72320_b();
/*  55 */     d1 *= 256.0D;
/*  56 */     return (p_70112_1_ < d1 * d1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145770_h(double p_145770_1_, double p_145770_3_, double p_145770_5_) {
/*  61 */     return super.func_145770_h(p_145770_1_, p_145770_3_, p_145770_5_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  66 */     if (this.field_70292_b + 10 >= this.lifespan) this.field_70292_b = 0; 
/*  67 */     boolean flag2 = (this.field_70170_p.func_147439_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u - 1.0D), MathHelper.func_76128_c(this.field_70161_v)) == Blocks.field_150384_bq);
/*  68 */     ItemStack stack = func_70096_w().func_82710_f(10);
/*  69 */     if (stack != null && stack.func_77973_b() != null && 
/*  70 */       stack.func_77973_b().onEntityItemUpdate(this)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  75 */     if (func_92059_d() == null) {
/*  76 */       func_70106_y();
/*     */     } else {
/*  78 */       func_70030_z();
/*     */       
/*  80 */       if (this.field_145804_b > 0) {
/*  81 */         this.field_145804_b--;
/*     */       }
/*     */       
/*  84 */       this.field_70169_q = this.field_70165_t;
/*  85 */       this.field_70167_r = this.field_70163_u;
/*  86 */       this.field_70166_s = this.field_70161_v;
/*  87 */       this.field_70181_x -= 0.03999999910593033D;
/*  88 */       if (flag2) {
/*  89 */         this.field_70159_w = 0.0D;
/*  90 */         this.field_70181_x = 0.0D;
/*  91 */         this.field_70179_y = 0.0D;
/*     */       } 
/*  93 */       this.field_70145_X = func_145771_j(this.field_70165_t, (this.field_70121_D.field_72338_b + this.field_70121_D.field_72337_e) / 2.0D, this.field_70161_v);
/*  94 */       func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*  95 */       boolean flag = ((int)this.field_70169_q != (int)this.field_70165_t || (int)this.field_70167_r != (int)this.field_70163_u || (int)this.field_70166_s != (int)this.field_70161_v);
/*     */       
/*  97 */       if ((flag || this.field_70173_aa % 25 == 0) && 
/*  98 */         this.field_70170_p.func_147439_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v)).func_149688_o() == Material.field_151587_i) {
/*  99 */         this.field_70181_x = 0.20000000298023224D;
/* 100 */         this.field_70159_w = ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F);
/* 101 */         this.field_70179_y = ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F);
/* 102 */         func_85030_a("random.fizz", 0.4F, 2.0F + this.field_70146_Z.nextFloat() * 0.4F);
/*     */       } 
/*     */ 
/*     */       
/* 106 */       float f = 0.98F;
/*     */       
/* 108 */       if (this.field_70122_E) {
/* 109 */         f = (this.field_70170_p.func_147439_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70121_D.field_72338_b) - 1, MathHelper.func_76128_c(this.field_70161_v))).field_149765_K * 0.98F;
/*     */       }
/*     */       
/* 112 */       this.field_70159_w *= f;
/* 113 */       this.field_70181_x *= 0.9800000190734863D;
/* 114 */       this.field_70179_y *= f;
/* 115 */       if (flag2) {
/* 116 */         this.field_70159_w = 0.0D;
/* 117 */         this.field_70181_x = 0.0D;
/* 118 */         this.field_70179_y = 0.0D;
/*     */       } 
/*     */       
/* 121 */       if (this.field_70122_E) {
/* 122 */         this.field_70181_x *= -0.5D;
/*     */       }
/*     */       
/* 125 */       this.field_70292_b++;
/*     */       
/* 127 */       ItemStack item = func_70096_w().func_82710_f(10);
/*     */       
/* 129 */       if (!this.field_70170_p.field_72995_K && this.field_70292_b >= this.lifespan) {
/* 130 */         if (item != null) {
/* 131 */           ItemExpireEvent event = new ItemExpireEvent(this, (item.func_77973_b() == null) ? 6000 : item.func_77973_b().getEntityLifespan(item, this.field_70170_p));
/* 132 */           if (MinecraftForge.EVENT_BUS.post((Event)event)) {
/* 133 */             this.lifespan += event.extraLife;
/*     */           } else {
/* 135 */             func_70106_y();
/*     */           } 
/*     */         } else {
/* 138 */           func_70106_y();
/*     */         } 
/*     */       }
/*     */       
/* 142 */       if (item != null && item.field_77994_a <= 0)
/* 143 */         func_70106_y(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\entity\EntityPersistentItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */