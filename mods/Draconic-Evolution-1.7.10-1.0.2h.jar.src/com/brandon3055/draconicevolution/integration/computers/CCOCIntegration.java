/*    */ package com.brandon3055.draconicevolution.integration.computers;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.integration.computers.oc.DEManagedPeripheral;
/*    */ import com.gamerforea.eventhelper.stripper.ModOptional;
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import cpw.mods.fml.common.Optional.Method;
/*    */ import li.cil.oc.api.Driver;
/*    */ import li.cil.oc.api.driver.Block;
/*    */ import li.cil.oc.api.network.ManagedEnvironment;
/*    */ import li.cil.oc.api.prefab.DriverTileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CCOCIntegration
/*    */ {
/*    */   public static void init() {
/* 18 */     if (Loader.isModLoaded("OpenComputers")) initOC(); 
/*    */   }
/*    */   
/*    */   @Method(modid = "OpenComputers")
/*    */   public static void initOC() {
/* 23 */     Driver.add((Block)new OCAdapter());
/*    */   }
/*    */   
/*    */   @ModOptional(modid = "OpenComputers")
/*    */   public static class OCAdapter
/*    */     extends DriverTileEntity
/*    */   {
/*    */     public Class<?> getTileEntityClass() {
/* 31 */       return IDEPeripheral.class;
/*    */     }
/*    */ 
/*    */     
/*    */     public ManagedEnvironment createEnvironment(World world, int x, int y, int z) {
/* 36 */       return (ManagedEnvironment)new DEManagedPeripheral((IDEPeripheral)world.func_147438_o(x, y, z));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\integration\computers\CCOCIntegration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */