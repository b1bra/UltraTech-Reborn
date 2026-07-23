/*    */ package com.brandon3055.draconicevolution.integration.computers.oc;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.integration.computers.IDEPeripheral;
/*    */ import li.cil.oc.api.driver.NamedBlock;
/*    */ import li.cil.oc.api.machine.Arguments;
/*    */ import li.cil.oc.api.machine.Context;
/*    */ import li.cil.oc.api.network.ManagedPeripheral;
/*    */ import li.cil.oc.api.prefab.ManagedEnvironment;
/*    */ 
/*    */ public class DEManagedPeripheral
/*    */   extends ManagedEnvironment
/*    */   implements ManagedPeripheral, NamedBlock
/*    */ {
/*    */   private IDEPeripheral peripheral;
/*    */   
/*    */   public DEManagedPeripheral(IDEPeripheral peripheral) {
/* 17 */     this.peripheral = peripheral;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] methods() {
/* 22 */     return this.peripheral.getMethodNames();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object[] invoke(String method, Context context, Arguments args) {
/* 27 */     return this.peripheral.callMethod(method, args.toArray());
/*    */   }
/*    */ 
/*    */   
/*    */   public String preferredName() {
/* 32 */     return this.peripheral.getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public int priority() {
/* 37 */     return 10;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\integration\computers\oc\DEManagedPeripheral.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */