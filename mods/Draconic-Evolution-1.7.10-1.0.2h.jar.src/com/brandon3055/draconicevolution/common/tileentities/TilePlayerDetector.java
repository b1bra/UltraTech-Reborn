/*    */ package com.brandon3055.draconicevolution.common.tileentities;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ public class TilePlayerDetector extends TileEntity {
/* 11 */   private int tick = 0;
/*    */   public boolean output = false;
/* 13 */   private int scanRate = 5;
/* 14 */   private int range = 1;
/*    */ 
/*    */   
/*    */   public void func_145845_h() {
/* 18 */     if (this.field_145850_b.field_72995_K)
/*    */       return; 
/* 20 */     if (this.tick >= this.scanRate)
/* 21 */     { this.tick = 0;
/* 22 */       EntityPlayer player = this.field_145850_b.func_72977_a(this.field_145851_c + 0.5D, this.field_145848_d + 0.5D, this.field_145849_e + 0.5D, this.range + 0.5D);
/* 23 */       if (player != null)
/* 24 */       { if (!this.output) setOutput(true);
/*    */          }
/* 26 */       else if (this.output) { setOutput(false); }
/*    */        }
/* 28 */     else { this.tick++; }
/*    */   
/*    */   }
/*    */   
/*    */   private void setOutput(boolean out) {
/* 33 */     this.output = out;
/* 34 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 35 */     updateBlocks();
/*    */   }
/*    */   
/*    */   public void setRange(int value) {
/* 39 */     this.range = value;
/*    */   }
/*    */   
/*    */   public int getRange() {
/* 43 */     return this.range;
/*    */   }
/*    */   
/*    */   public void updateBlocks() {
/* 47 */     this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/* 48 */     this.field_145850_b.func_147459_d(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/* 49 */     this.field_145850_b.func_147459_d(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/* 50 */     this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/* 51 */     this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/* 52 */     this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/* 53 */     this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*    */   }
/*    */ 
/*    */   
/*    */   public Packet func_145844_m() {
/* 58 */     NBTTagCompound tagCompound = new NBTTagCompound();
/* 59 */     func_145841_b(tagCompound);
/* 60 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, tagCompound);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/* 65 */     func_145839_a(pkt.func_148857_g());
/* 66 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145841_b(NBTTagCompound compound) {
/* 71 */     compound.func_74757_a("OutPut", this.output);
/* 72 */     compound.func_74768_a("Range", this.range);
/*    */     
/* 74 */     super.func_145841_b(compound);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145839_a(NBTTagCompound compound) {
/* 79 */     this.output = compound.func_74767_n("OutPut");
/* 80 */     this.range = compound.func_74762_e("Range");
/*    */     
/* 82 */     super.func_145839_a(compound);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\TilePlayerDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */