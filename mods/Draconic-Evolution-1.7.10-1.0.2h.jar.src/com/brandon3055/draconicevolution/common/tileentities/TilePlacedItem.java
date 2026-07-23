/*    */ package com.brandon3055.draconicevolution.common.tileentities;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ public class TilePlacedItem
/*    */   extends TileEntity {
/*    */   public ItemStack stack;
/* 16 */   public float rotation = 0.0F;
/*    */   
/*    */   private boolean hasUpdated = false;
/*    */   
/*    */   public void func_145845_h() {
/* 21 */     if (!this.hasUpdated && this.stack != null) {
/* 22 */       this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 23 */       this.hasUpdated = true;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Packet func_145844_m() {
/* 29 */     NBTTagCompound tagCompound = new NBTTagCompound();
/* 30 */     func_145841_b(tagCompound);
/* 31 */     return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, tagCompound);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
/* 36 */     func_145839_a(pkt.func_148857_g());
/*    */   }
/*    */   
/*    */   public void setStack(ItemStack stack) {
/* 40 */     this.stack = stack;
/* 41 */     this.field_145850_b.func_147464_a(this.field_145851_c, this.field_145848_d, this.field_145849_e, (Block)ModBlocks.placedItem, 20);
/*    */   }
/*    */   
/*    */   public ItemStack getStack() {
/* 45 */     return this.stack;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145841_b(NBTTagCompound compound) {
/* 50 */     super.func_145841_b(compound);
/* 51 */     NBTTagCompound[] tag = new NBTTagCompound[1];
/* 52 */     tag[0] = new NBTTagCompound();
/* 53 */     if (this.stack != null) tag[0] = this.stack.func_77955_b(tag[0]); 
/* 54 */     compound.func_74782_a("Item0", (NBTBase)tag[0]);
/* 55 */     compound.func_74776_a("Rotation", this.rotation);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145839_a(NBTTagCompound compound) {
/* 60 */     super.func_145839_a(compound);
/* 61 */     NBTTagCompound[] tag = new NBTTagCompound[1];
/* 62 */     tag[0] = compound.func_74775_l("Item0");
/* 63 */     this.stack = ItemStack.func_77949_a(tag[0]);
/* 64 */     this.rotation = compound.func_74760_g("Rotation");
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\TilePlacedItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */