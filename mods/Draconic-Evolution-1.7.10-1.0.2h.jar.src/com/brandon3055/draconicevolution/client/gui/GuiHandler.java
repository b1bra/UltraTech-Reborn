/*     */ package com.brandon3055.draconicevolution.client.gui;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.client.gui.componentguis.GUIToolConfig;
/*     */ import com.brandon3055.draconicevolution.common.container.ContainerAdvTool;
/*     */ import com.brandon3055.draconicevolution.common.container.ContainerDissEnchanter;
/*     */ import com.brandon3055.draconicevolution.common.container.ContainerEnergyInfuser;
/*     */ import com.brandon3055.draconicevolution.common.container.ContainerGrinder;
/*     */ import com.brandon3055.draconicevolution.common.container.ContainerUpgradeModifier;
/*     */ import com.brandon3055.draconicevolution.common.inventory.InventoryTool;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileDissEnchanter;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileEnergyInfuser;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileGrinder;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileParticleGenerator;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TilePlayerDetectorAdvanced;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileUpgradeModifier;
/*     */ import cpw.mods.fml.common.network.IGuiHandler;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class GuiHandler implements IGuiHandler {
/*     */   public static final int GUIID_GRINDER = 2;
/*     */   public static final int GUIID_TELEPORTER = 3;
/*     */   public static final int GUIID_PARTICLEGEN = 5;
/*     */   public static final int GUIID_PLAYERDETECTOR = 6;
/*     */   public static final int GUIID_ENERGY_INFUSER = 7;
/*     */   
/*  30 */   public GuiHandler() { NetworkRegistry.INSTANCE.registerGuiHandler(DraconicEvolution.instance, this); } public static final int GUIID_MANUAL = 9; public static final int GUIID_DISSENCHANTER = 10; public static final int GUIID_TOOL_CONFIG = 12; public static final int GUIID_FLOW_GATE = 13; public static final int GUIID_UPGRADE_MODIFIER = 15; public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) { TileEntity te2;
/*     */     TileEntity detector;
/*     */     TileEntity infuser;
/*     */     TileEntity dissenchanter;
/*     */     TileEntity containerTemp;
/*  35 */     switch (ID) {
/*     */       case 2:
/*  37 */         te2 = world.func_147438_o(x, y, z);
/*  38 */         if (te2 instanceof TileGrinder) {
/*  39 */           return new ContainerGrinder(player.field_71071_by, (TileGrinder)te2);
/*     */         }
/*     */         break;
/*     */       case 6:
/*  43 */         detector = world.func_147438_o(x, y, z);
/*  44 */         if (detector instanceof TilePlayerDetectorAdvanced) {
/*  45 */           return new ContainerPlayerDetector(player.field_71071_by, (TilePlayerDetectorAdvanced)detector);
/*     */         }
/*     */         break;
/*     */       case 7:
/*  49 */         infuser = world.func_147438_o(x, y, z);
/*  50 */         if (infuser instanceof TileEnergyInfuser) {
/*  51 */           return new ContainerEnergyInfuser(player.field_71071_by, (TileEnergyInfuser)infuser);
/*     */         }
/*     */         break;
/*     */       case 10:
/*  55 */         dissenchanter = world.func_147438_o(x, y, z);
/*  56 */         if (dissenchanter instanceof TileDissEnchanter) {
/*  57 */           return new ContainerDissEnchanter(player.field_71071_by, (TileDissEnchanter)dissenchanter);
/*     */         }
/*     */         break;
/*     */       case 12:
/*  61 */         return new ContainerAdvTool(player.field_71071_by, new InventoryTool(player, null));
/*     */       case 15:
/*  63 */         containerTemp = world.func_147438_o(x, y, z);
/*  64 */         if (containerTemp instanceof TileUpgradeModifier) {
/*  65 */           return new ContainerUpgradeModifier(player.field_71071_by, (TileUpgradeModifier)containerTemp);
/*     */         }
/*     */         break;
/*     */     } 
/*     */     
/*  70 */     return null; } public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) { TileEntity te2; TileEntity gen;
/*     */     TileEntity detector;
/*     */     TileEntity infuser;
/*     */     TileEntity dissenchanter;
/*     */     TileEntity containerTemp;
/*  75 */     switch (ID) {
/*     */       case 3:
/*  77 */         return new GUITeleporter(player);
/*     */       case 2:
/*  79 */         te2 = world.func_147438_o(x, y, z);
/*  80 */         if (te2 instanceof TileGrinder) {
/*  81 */           return new GUIGrinder(player.field_71071_by, (TileGrinder)te2);
/*     */         }
/*     */         break;
/*     */       case 5:
/*  85 */         gen = world.func_147438_o(x, y, z);
/*  86 */         return (gen instanceof TileParticleGenerator) ? new GUIParticleGenerator((TileParticleGenerator)gen, player) : null;
/*     */       case 6:
/*  88 */         detector = world.func_147438_o(x, y, z);
/*  89 */         if (detector instanceof TilePlayerDetectorAdvanced) {
/*  90 */           return new GUIPlayerDetector(player.field_71071_by, (TilePlayerDetectorAdvanced)detector);
/*     */         }
/*     */         break;
/*     */       case 7:
/*  94 */         infuser = world.func_147438_o(x, y, z);
/*  95 */         if (infuser instanceof TileEnergyInfuser) {
/*  96 */           return new GUIEnergyInfuser(player.field_71071_by, (TileEnergyInfuser)infuser);
/*     */         }
/*     */         break;
/*     */       case 9:
/* 100 */         return new GUIManual();
/*     */       case 10:
/* 102 */         dissenchanter = world.func_147438_o(x, y, z);
/* 103 */         if (dissenchanter instanceof TileDissEnchanter) {
/* 104 */           return new GUIDissEnchanter(player.field_71071_by, (TileDissEnchanter)dissenchanter);
/*     */         }
/*     */         break;
/*     */       case 12:
/* 108 */         return new GUIToolConfig(player, new ContainerAdvTool(player.field_71071_by, new InventoryTool(player, null)));
/*     */       case 13:
/* 110 */         return (world.func_147438_o(x, y, z) instanceof TileGate) ? new GUIFlowGate((TileGate)world.func_147438_o(x, y, z)) : null;
/*     */       case 15:
/* 112 */         containerTemp = world.func_147438_o(x, y, z);
/* 113 */         if (containerTemp instanceof TileUpgradeModifier) {
/* 114 */           return new GUIUpgradeModifier(player.field_71071_by, (TileUpgradeModifier)containerTemp, new ContainerUpgradeModifier(player.field_71071_by, (TileUpgradeModifier)containerTemp));
/*     */         }
/*     */         break;
/*     */     } 
/*     */     
/* 119 */     return null; }
/*     */ 
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\GuiHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */