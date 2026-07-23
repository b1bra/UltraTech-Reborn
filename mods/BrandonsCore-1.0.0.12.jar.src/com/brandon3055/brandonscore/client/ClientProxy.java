/*    */ package com.brandon3055.brandonscore.client;
/*    */ 
/*    */ import com.brandon3055.brandonscore.common.CommonProxy;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientProxy
/*    */   extends CommonProxy
/*    */ {
/*    */   public boolean isDedicatedServer() {
/* 16 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public MinecraftServer getMCServer() {
/* 21 */     return super.getMCServer();
/*    */   }
/*    */ 
/*    */   
/*    */   public World getClientWorld() {
/* 26 */     return (World)(Minecraft.func_71410_x()).field_71441_e;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSpaceDown() {
/* 31 */     return (Minecraft.func_71410_x()).field_71474_y.field_74314_A.func_151470_d();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isShiftDown() {
/* 36 */     return (Minecraft.func_71410_x()).field_71474_y.field_74311_E.func_151470_d();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCtrlDown() {
/* 41 */     return (Minecraft.func_71410_x()).field_71474_y.field_151444_V.func_151470_d();
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityPlayer getClientPlayer() {
/* 46 */     return (EntityPlayer)(Minecraft.func_71410_x()).field_71439_g;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\client\ClientProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */