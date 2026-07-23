/*    */ package com.brandon3055.draconicevolution.client.render.particle;
/*    */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*    */ import com.gamerforea.client.util.TessellatorDebuggerUtils;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.particle.EntityFX;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleCustom extends EntityFX {
/* 11 */   public int red = 0;
/* 12 */   public int green = 0;
/* 13 */   public int blue = 0;
/* 14 */   public int maxAge = 0;
/* 15 */   public int fadeTime = 0;
/* 16 */   public int fadeLength = 0;
/* 17 */   public float gravity = 0.0F;
/*    */ 
/*    */   
/*    */   public ParticleCustom(World world, double spawnX, double spawnY, double spawnZ, float spawnMotionX, float spawnMotionY, float spawnMotionZ, float scale, boolean canCollide, int index) {
/* 21 */     super(world, spawnX, spawnY, spawnZ, 0.0D, 0.0D, 0.0D);
/* 22 */     this.field_70159_w = spawnMotionX;
/* 23 */     this.field_70181_x = spawnMotionY;
/* 24 */     this.field_70179_y = spawnMotionZ;
/* 25 */     this.field_94054_b = index - 1;
/* 26 */     this.field_94055_c = 0;
/*    */     
/* 28 */     this.field_70544_f = scale;
/*    */     
/* 30 */     this.field_70145_X = !canCollide;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70071_h_() {
/* 36 */     this.field_70169_q = this.field_70165_t;
/* 37 */     this.field_70167_r = this.field_70163_u;
/* 38 */     this.field_70166_s = this.field_70161_v;
/*    */     
/* 40 */     if (this.field_70546_d++ >= this.maxAge) {
/* 41 */       this.field_82339_as = this.fadeTime / this.fadeLength;
/* 42 */       if (this.fadeTime <= 0) func_70106_y(); 
/* 43 */       this.fadeTime--;
/*    */     } 
/*    */     
/* 46 */     func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*    */     
/* 48 */     this.field_70181_x -= (this.gravity / 100.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_70539_a(Tessellator tesselator, float par2, float par3, float par4, float par5, float par6, float par7) {
/* 58 */     tesselator.func_78381_a();
/* 59 */     ResourceHandler.bindParticles();
/* 60 */     tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/ParticleCustom/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/* 61 */     tesselator.func_78380_c(200);
/*    */ 
/*    */     
/* 64 */     float minU = this.field_94054_b / 8.0F;
/* 65 */     float maxU = minU + 0.124F;
/* 66 */     float minV = this.field_94055_c / 8.0F;
/* 67 */     float maxV = minV + 0.124F;
/* 68 */     float drawScale = 0.1F * this.field_70544_f;
/*    */     
/* 70 */     if (this.field_70550_a != null) {
/* 71 */       minU = this.field_70550_a.func_94209_e();
/* 72 */       maxU = this.field_70550_a.func_94212_f();
/* 73 */       minV = this.field_70550_a.func_94206_g();
/* 74 */       maxV = this.field_70550_a.func_94210_h();
/*    */     } 
/*    */     
/* 77 */     float drawX = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * par2 - field_70556_an);
/* 78 */     float drawY = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * par2 - field_70554_ao);
/* 79 */     float drawZ = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * par2 - field_70555_ap);
/*    */     
/* 81 */     tesselator.func_78370_a(this.red, this.green, this.blue, (int)(this.field_82339_as * 255.0F));
/*    */     
/* 83 */     tesselator.func_78374_a((drawX - par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ - par5 * drawScale - par7 * drawScale), maxU, maxV);
/* 84 */     tesselator.func_78374_a((drawX - par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ - par5 * drawScale + par7 * drawScale), maxU, minV);
/* 85 */     tesselator.func_78374_a((drawX + par3 * drawScale + par6 * drawScale), (drawY + par4 * drawScale), (drawZ + par5 * drawScale + par7 * drawScale), minU, minV);
/* 86 */     tesselator.func_78374_a((drawX + par3 * drawScale - par6 * drawScale), (drawY - par4 * drawScale), (drawZ + par5 * drawScale - par7 * drawScale), minU, maxV);
/*    */     
/* 88 */     tesselator.func_78381_a();
/* 89 */     ResourceHandler.bindDefaultParticles();
/* 90 */     tesselator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/particle/ParticleCustom/func_70539_a(Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V");
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\particle\ParticleCustom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */