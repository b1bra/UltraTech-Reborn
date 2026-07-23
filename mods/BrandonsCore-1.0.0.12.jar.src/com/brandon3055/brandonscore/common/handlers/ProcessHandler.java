/*    */ package com.brandon3055.brandonscore.common.handlers;
/*    */ 
/*    */ import cpw.mods.fml.common.FMLCommonHandler;
/*    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*    */ import cpw.mods.fml.common.gameevent.TickEvent;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.event.world.WorldEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProcessHandler
/*    */ {
/* 23 */   private static List<IProcess> processes = new ArrayList<>();
/* 24 */   private static List<IProcess> newProcesses = new ArrayList<>();
/*    */   
/*    */   public static void init() {
/* 27 */     FMLCommonHandler.instance().bus().register(new ProcessHandler());
/* 28 */     MinecraftForge.EVENT_BUS.register(new ProcessHandler());
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onServerTick(TickEvent.ServerTickEvent event) {
/* 33 */     if (event.phase == TickEvent.Phase.START) {
/*    */       
/* 35 */       Iterator<IProcess> i = processes.iterator();
/*    */       
/* 37 */       while (i.hasNext()) {
/* 38 */         IProcess process = i.next();
/* 39 */         if (process.isDead()) { i.remove(); continue; }
/* 40 */          process.updateProcess();
/*    */       } 
/*    */       
/* 43 */       if (!newProcesses.isEmpty()) {
/* 44 */         processes.addAll(newProcesses);
/* 45 */         newProcesses.clear();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onWorldClose(WorldEvent.Unload event) {
/* 52 */     processes.clear();
/* 53 */     newProcesses.clear();
/*    */   }
/*    */   
/*    */   public static void addProcess(IProcess process) {
/* 57 */     newProcesses.add(process);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\common\handlers\ProcessHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */