/*    */ package com.brandon3055.draconicevolution.common.tileentities.multiblocktiles;
/*    */ 
/*    */ import com.brandon3055.brandonscore.common.utills.Utills;
/*    */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*    */ import com.brandon3055.draconicevolution.client.render.particle.Particles;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TilePortalBlock
/*    */   extends TileEntity
/*    */ {
/* 17 */   public int masterX = 0;
/* 18 */   public int masterY = 0;
/* 19 */   public int masterZ = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_145845_h() {
/* 30 */     if (!this.field_145850_b.field_72995_K)
/* 31 */       return;  double distanceMod = Utills.getDistanceAtoB(this.field_145851_c + 0.5D, this.field_145848_d + 0.5D, this.field_145849_e + 0.5D, RenderManager.field_78725_b, RenderManager.field_78726_c, RenderManager.field_78723_d);
/* 32 */     if (this.field_145850_b.field_73012_v.nextInt(Math.max((int)(distanceMod * distanceMod / 5.0D), 1)) == 0) {
/* 33 */       if (this.field_145847_g == -1) {
/* 34 */         this.field_145847_g = this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*    */       }
/* 36 */       double rD1 = this.field_145850_b.field_73012_v.nextDouble();
/* 37 */       double rD2 = this.field_145850_b.field_73012_v.nextDouble();
/* 38 */       double rO1 = -0.1D + this.field_145850_b.field_73012_v.nextDouble() * 0.2D;
/* 39 */       double rO2 = -0.1D + this.field_145850_b.field_73012_v.nextDouble() * 0.2D;
/*    */ 
/*    */       
/* 42 */       if (this.field_145847_g == 1 && RenderManager.field_78723_d < this.field_145849_e + 0.5D) {
/* 43 */         DraconicEvolution.proxy.spawnParticle(new Particles.PortalParticle(this.field_145850_b, this.field_145851_c + rD1, this.field_145848_d + rD2, this.field_145849_e, this.field_145851_c + rD1 + rO1, this.field_145848_d + rD2 + rO2, this.field_145849_e + 0.75D), 256);
/* 44 */       } else if (this.field_145847_g == 1 && RenderManager.field_78723_d > this.field_145849_e + 0.5D) {
/* 45 */         DraconicEvolution.proxy.spawnParticle(new Particles.PortalParticle(this.field_145850_b, this.field_145851_c + rD1, this.field_145848_d + rD2, (this.field_145849_e + 1), this.field_145851_c + rD1 + rO1, this.field_145848_d + rD2 + rO2, this.field_145849_e + 0.25D), 256);
/* 46 */       } else if (this.field_145847_g == 2 && RenderManager.field_78725_b < this.field_145851_c + 0.5D) {
/* 47 */         DraconicEvolution.proxy.spawnParticle(new Particles.PortalParticle(this.field_145850_b, this.field_145851_c, this.field_145848_d + rD1, this.field_145849_e + rD2, this.field_145851_c + 0.75D, this.field_145848_d + rD1 + rO1, this.field_145849_e + rD2 + rO2), 256);
/* 48 */       } else if (this.field_145847_g == 2 && RenderManager.field_78725_b > this.field_145851_c + 0.5D) {
/* 49 */         DraconicEvolution.proxy.spawnParticle(new Particles.PortalParticle(this.field_145850_b, (this.field_145851_c + 1), this.field_145848_d + rD1, this.field_145849_e + rD2, this.field_145851_c + 0.25D, this.field_145848_d + rD1 + rO1, this.field_145849_e + rD2 + rO2), 256);
/* 50 */       } else if (this.field_145847_g == 3 && RenderManager.field_78726_c > this.field_145848_d + 0.5D) {
/* 51 */         DraconicEvolution.proxy.spawnParticle(new Particles.PortalParticle(this.field_145850_b, this.field_145851_c + rD1, (this.field_145848_d + 1), this.field_145849_e + rD2, this.field_145851_c + rD1 + rO1, this.field_145848_d + 0.25D, this.field_145849_e + rD2 + rO2), 256);
/* 52 */       } else if (this.field_145847_g == 3 && RenderManager.field_78726_c < this.field_145848_d + 0.5D) {
/* 53 */         DraconicEvolution.proxy.spawnParticle(new Particles.PortalParticle(this.field_145850_b, this.field_145851_c + rD1, this.field_145848_d, this.field_145849_e + rD2, this.field_145851_c + rD1 + rO1, this.field_145848_d + 0.75D, this.field_145849_e + rD2 + rO2), 256);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   public TileDislocatorReceptacle getMaster() {
/* 58 */     return (this.field_145850_b.func_147438_o(this.masterX, this.masterY, this.masterZ) instanceof TileDislocatorReceptacle) ? (TileDislocatorReceptacle)this.field_145850_b.func_147438_o(this.masterX, this.masterY, this.masterZ) : null;
/*    */   }
/*    */   
/*    */   public boolean isPortalStillValid() {
/* 62 */     TileDislocatorReceptacle master = getMaster();
/* 63 */     if (master == null || !master.isActive) return false; 
/* 64 */     master.validateActivePortal();
/* 65 */     return master.isActive;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145841_b(NBTTagCompound compound) {
/* 70 */     super.func_145841_b(compound);
/* 71 */     compound.func_74768_a("MasterX", this.masterX);
/* 72 */     compound.func_74768_a("MasterY", this.masterY);
/* 73 */     compound.func_74768_a("MasterZ", this.masterZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145839_a(NBTTagCompound compound) {
/* 78 */     super.func_145839_a(compound);
/* 79 */     this.masterX = compound.func_74762_e("MasterX");
/* 80 */     this.masterY = compound.func_74762_e("MasterY");
/* 81 */     this.masterZ = compound.func_74762_e("MasterZ");
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\multiblocktiles\TilePortalBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */