/*    */ package com.brandon3055.brandonscore.common;
/*    */ 
/*    */ import cpw.mods.fml.common.FMLCommonHandler;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommonProxy
/*    */ {
/*    */   public boolean isDedicatedServer() {
/* 15 */     return true;
/*    */   }
/*    */   
/*    */   public MinecraftServer getMCServer() {
/* 19 */     return FMLCommonHandler.instance().getMinecraftServerInstance();
/*    */   }
/*    */   
/*    */   public World getClientWorld() {
/* 23 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isOp(String paramString) {
/* 27 */     MinecraftServer localMinecraftServer = FMLCommonHandler.instance().getMinecraftServerInstance();
/* 28 */     paramString = paramString.trim();
/* 29 */     for (String str : localMinecraftServer.func_71203_ab().func_152606_n()) {
/* 30 */       if (paramString.equalsIgnoreCase(str)) {
/* 31 */         return true;
/*    */       }
/*    */     } 
/* 34 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isSpaceDown() {
/* 38 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isCtrlDown() {
/* 42 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isShiftDown() {
/* 46 */     return false;
/*    */   }
/*    */   
/*    */   public EntityPlayer getClientPlayer() {
/* 50 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\common\CommonProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */