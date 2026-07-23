/*    */ package com.brandon3055.draconicevolution.client.render.particle;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*    */ import com.gamerforea.client.util.TessellatorDebuggerUtils;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.particle.EntityFX;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleDistortion extends EntityFX {
/*    */   double originalX;
/*    */   
/*    */   public ParticleDistortion(World par1World, double par2, double par4, double par6, float par8, float par9, float par10, float scale) {
/* 15 */     this(par1World, par2, par4, par6, 1.0F, par8, par9, par10, scale);
/*    */   }
/*    */   double originalZ;
/*    */   public ParticleDistortion(World world, double par2, double par4, double par6, float par8, float par9, float par10, float par11, float scale) {
/* 19 */     super(world, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/* 20 */     this.field_70159_w = par9;
/* 21 */     this.field_70181_x = par10;
/* 22 */     this.field_70179_y = par11;
/* 23 */     this.originalX = par9;
/* 24 */     this.originalZ = par11;
/* 25 */     if (par9 == 0.0F) {
/* 26 */       par9 = 1.0F;
/*    */     }
/*    */     
/* 29 */     this.field_94054_b = 0;
/* 30 */     this.field_94055_c = 0;
/*    */     
/* 32 */     this.field_70552_h = 0.7F;
/* 33 */     this.field_70553_i = 0.8F;
/* 34 */     this.field_70551_j = 1.0F;
/*    */ 
/*    */     
/* 37 */     this.field_70544_f = scale;
/*    */     
/* 39 */     this.field_70547_e = 40 + world.field_73012_v.nextInt(40);
/* 40 */     this.field_70145_X = true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70071_h_() {
/* 46 */     this.field_70169_q = this.field_70165_t;
/* 47 */     this.field_70167_r = this.field_70163_u;
/* 48 */     this.field_70166_s = this.field_70161_v;
/*    */     
/* 50 */     if (this.field_70546_d++ >= this.field_70547_e) {
/* 51 */       func_70106_y();
/*    */     }
/* 53 */     func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 54 */     this.field_70159_w *= (1.0F - this.field_70170_p.field_73012_v.nextFloat() / 10.0F);
/* 55 */     this.field_70181_x *= (1.0F - this.field_70170_p.field_73012_v.nextFloat() / 10.0F);
/* 56 */     this.field_70179_y *= (1.0F - this.field_70170_p.field_73012_v.nextFloat() / 10.0F);
/* 57 */     this.field_82339_as = (1.0F - this.field_70546_d / this.field_70547_e) * 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_70539_a(Tessellator tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
/* 65 */     tessellator.func_78381_a();
/* 66 */     ResourceHandler.bindParticles();
/* 67 */     tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/ParticleDistortion/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/* 68 */     tessellator.func_78380_c(200);
/*    */ 
/*    */     
/* 71 */     float minU = 0.0F;
/* 72 */     float maxU = 0.1245F;
/* 73 */     float minV = 0.0F;
/* 74 */     float maxV = 0.1245F;
/* 75 */     float drawScale = 0.1F * this.field_70544_f;
/*    */     
/* 77 */     if (this.field_70550_a != null) {
/* 78 */       minU = this.field_70550_a.func_94209_e();
/* 79 */       maxU = this.field_70550_a.func_94212_f();
/* 80 */       minV = this.field_70550_a.func_94206_g();
/* 81 */       maxV = this.field_70550_a.func_94210_h();
/*    */     } 
/*    */     
/* 84 */     float drawX = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * par2 - field_70556_an);
/* 85 */     float drawY = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * par2 - field_70554_ao);
/* 86 */     float drawZ = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * par2 - field_70555_ap);
/*    */     
/* 88 */     tessellator.func_78369_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as);
/* 89 */     tessellator.func_78370_a(0, 255, 255, (int)(this.field_82339_as * 255.0F));
/*    */     
/* 91 */     tessellator.func_78374_a((drawX - par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ - par5 * drawScale - par7 * drawScale), maxU, maxV);
/* 92 */     tessellator.func_78374_a((drawX - par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ - par5 * drawScale + par7 * drawScale), maxU, minV);
/* 93 */     tessellator.func_78374_a((drawX + par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ + par5 * drawScale + par7 * drawScale), minU, minV);
/* 94 */     tessellator.func_78374_a((drawX + par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ + par5 * drawScale - par7 * drawScale), minU, maxV);
/*    */     
/* 96 */     tessellator.func_78381_a();
/* 97 */     ResourceHandler.bindDefaultParticles();
/* 98 */     tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/ParticleDistortion/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\particle\ParticleDistortion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */