/*    */ package com.brandon3055.draconicevolution.common.tileentities;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ public class TilePotentiometer extends TileEntity {
/* 10 */   public int power = 0;
/*    */   
/*    */   public void increasePower() {
/* 13 */     if (this.power < 15) {
/* 14 */       this.power++;
/* 15 */       this.field_145850_b.func_72980_b(this.field_145851_c, this.field_145848_d, this.field_145849_e, "random.click", 1.0F, 0.5F + this.power / 15.0F, false);
/*    */     } 
/* 17 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 18 */     updateBlocks();
/*    */   }
/*    */   
/*    */   public void decreasePower() {
/* 22 */     if (this.power > 0) {
/* 23 */       this.power--;
/* 24 */       this.field_145850_b.func_72980_b(this.field_145851_c, this.field_145848_d, this.field_145849_e, "random.click", 1.0F, 0.5F + this.power / 15.0F, false);
/*    */     } 
/* 26 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 27 */     updateBlocks();
/*    */   }
/*    */   
/*    */   public void updateBlocks() {
/* 31 */     int meta = this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 32 */     this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*    */     
/* 34 */     if (meta == 1) {
/* 35 */       this.field_145850_b.func_147459_d(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/* 36 */     } else if (meta == 2) {
/* 37 */       this.field_145850_b.func_147459_d(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/* 38 */     } else if (meta == 3) {
/* 39 */       this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/* 40 */     } else if (meta == 4) {
/* 41 */       this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/* 42 */     } else if (meta == 5) {
/* 43 */       this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/* 44 */     } else if (meta == 6) {
/* 45 */       this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*    */     } else {
/* 47 */       this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Packet func_145844_m() {
/* 53 */     NBTTagCompound tagCompound = new NBTTagCompound();
/* 54 */     func_145841_b(tagCompound);
/* 55 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, tagCompound);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/* 60 */     func_145839_a(pkt.func_148857_g());
/* 61 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145839_a(NBTTagCompound compound) {
/* 66 */     super.func_145839_a(compound);
/* 67 */     this.power = compound.func_74762_e("Power");
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145841_b(NBTTagCompound compound) {
/* 72 */     compound.func_74768_a("Power", this.power);
/* 73 */     super.func_145841_b(compound);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\TilePotentiometer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */