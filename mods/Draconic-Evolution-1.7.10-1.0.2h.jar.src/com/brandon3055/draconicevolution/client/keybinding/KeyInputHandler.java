/*     */ package com.brandon3055.draconicevolution.client.keybinding;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.baseclasses.ToolHandler;
/*     */ import com.brandon3055.draconicevolution.common.network.ButtonPacket;
/*     */ import com.brandon3055.draconicevolution.common.network.PlacedItemPacket;
/*     */ import com.brandon3055.draconicevolution.common.network.TeleporterPacket;
/*     */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.common.gameevent.InputEvent;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjglx.input.Mouse;
/*     */ 
/*     */ public class KeyInputHandler
/*     */ {
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void onKeyInput(InputEvent.KeyInputEvent event) {
/*  31 */     if (KeyBindings.placeItem.func_151468_f()) { handlePlaceItemKey(); }
/*  32 */     else if (KeyBindings.toolConfig.func_151468_f())
/*  33 */     { DraconicEvolution.network.sendToServer((IMessage)new ButtonPacket((byte)7, false)); }
/*  34 */     else if (KeyBindings.toolProfileChange.func_151468_f() && (Minecraft.func_71410_x()).field_71439_g != null && (Minecraft.func_71410_x()).field_71439_g.func_71011_bu() == null)
/*  35 */     { DraconicEvolution.network.sendToServer((IMessage)new ButtonPacket((byte)9, false));
/*     */       
/*  37 */       ItemStack stack = (Minecraft.func_71410_x()).field_71439_g.func_70694_bm();
/*  38 */       if (stack != null && stack.func_77973_b() instanceof IConfigurableItem && ((IConfigurableItem)stack.func_77973_b()).hasProfiles()) {
/*  39 */         int preset = ItemNBTHelper.getInteger(stack, "ConfigProfile", 0);
/*  40 */         if (++preset >= 5) preset = 0; 
/*  41 */         ItemNBTHelper.setInteger(stack, "ConfigProfile", preset);
/*     */       }  }
/*  43 */     else if (KeyBindings.toggleFlight.func_151468_f())
/*  44 */     { EntityClientPlayerMP entityClientPlayerMP = (Minecraft.func_71410_x()).field_71439_g;
/*  45 */       if (((EntityPlayer)entityClientPlayerMP).field_71075_bZ.field_75101_c) {
/*  46 */         if (((EntityPlayer)entityClientPlayerMP).field_71075_bZ.field_75100_b) {
/*  47 */           ((EntityPlayer)entityClientPlayerMP).field_71075_bZ.field_75100_b = false;
/*  48 */           entityClientPlayerMP.func_71016_p();
/*     */         } else {
/*  50 */           ((EntityPlayer)entityClientPlayerMP).field_71075_bZ.field_75100_b = true;
/*  51 */           if (((EntityPlayer)entityClientPlayerMP).field_70122_E) {
/*  52 */             entityClientPlayerMP.func_70107_b(((EntityPlayer)entityClientPlayerMP).field_70165_t, ((EntityPlayer)entityClientPlayerMP).field_70163_u + 0.05D, ((EntityPlayer)entityClientPlayerMP).field_70161_v);
/*  53 */             ((EntityPlayer)entityClientPlayerMP).field_70181_x = 0.0D;
/*     */           } 
/*  55 */           entityClientPlayerMP.func_71016_p();
/*     */         } 
/*     */       } }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   private void handlePlaceItemKey() {
/*  63 */     EntityClientPlayerMP player = (Minecraft.func_71410_x()).field_71439_g;
/*  64 */     WorldClient world = (Minecraft.func_71410_x()).field_71441_e;
/*  65 */     MovingObjectPosition mop = ToolHandler.raytraceFromEntity((World)world, (Entity)player, 4.5D);
/*  66 */     if (mop != null)
/*  67 */       DraconicEvolution.network.sendToServer((IMessage)new PlacedItemPacket((byte)mop.field_72310_e, mop.field_72311_b, mop.field_72312_c, mop.field_72309_d)); 
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void onMouseInput(InputEvent.MouseInputEvent event) {
/*  73 */     if (KeyBindings.placeItem.func_151468_f()) { handlePlaceItemKey(); }
/*  74 */     else if (KeyBindings.toolConfig.func_151468_f())
/*  75 */     { DraconicEvolution.network.sendToServer((IMessage)new ButtonPacket((byte)7, false)); }
/*  76 */     else if (KeyBindings.toolProfileChange.func_151468_f() && (Minecraft.func_71410_x()).field_71439_g != null)
/*  77 */     { DraconicEvolution.network.sendToServer((IMessage)new ButtonPacket((byte)9, false));
/*     */       
/*  79 */       ItemStack stack = (Minecraft.func_71410_x()).field_71439_g.func_70694_bm();
/*  80 */       if (stack != null && stack.func_77973_b() instanceof IConfigurableItem && ((IConfigurableItem)stack.func_77973_b()).hasProfiles()) {
/*  81 */         int preset = ItemNBTHelper.getInteger(stack, "ConfigProfile", 0);
/*  82 */         if (++preset >= 5) preset = 0; 
/*  83 */         ItemNBTHelper.setInteger(stack, "ConfigProfile", preset);
/*     */       }  }
/*     */ 
/*     */     
/*  87 */     EntityClientPlayerMP entityClientPlayerMP = (Minecraft.func_71410_x()).field_71439_g;
/*  88 */     int change = Mouse.getEventDWheel();
/*  89 */     if (change == 0 || !entityClientPlayerMP.func_70093_af())
/*     */       return; 
/*  91 */     if (change > 0) {
/*  92 */       ItemStack item = ((EntityPlayer)entityClientPlayerMP).field_71071_by.func_70301_a(previouseSlot(1, ((EntityPlayer)entityClientPlayerMP).field_71071_by.field_70461_c));
/*  93 */       if (item != null && item.func_77973_b().equals(ModItems.teleporterMKII)) {
/*  94 */         ((EntityPlayer)entityClientPlayerMP).field_71071_by.field_70461_c = previouseSlot(1, ((EntityPlayer)entityClientPlayerMP).field_71071_by.field_70461_c);
/*  95 */         DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(9, -1, false));
/*     */       } 
/*  97 */     } else if (change < 0) {
/*  98 */       ItemStack item = ((EntityPlayer)entityClientPlayerMP).field_71071_by.func_70301_a(previouseSlot(-1, ((EntityPlayer)entityClientPlayerMP).field_71071_by.field_70461_c));
/*  99 */       if (item != null && item.func_77973_b().equals(ModItems.teleporterMKII)) {
/* 100 */         ((EntityPlayer)entityClientPlayerMP).field_71071_by.field_70461_c = previouseSlot(-1, ((EntityPlayer)entityClientPlayerMP).field_71071_by.field_70461_c);
/* 101 */         DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(9, 1, false));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private int previouseSlot(int i, int c) {
/* 107 */     if (c > 0 && c < 8) return c + i; 
/* 108 */     if (c == 0 && i < 0) return 8; 
/* 109 */     if (c == 8 && i > 0) return 0; 
/* 110 */     return c + i;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\keybinding\KeyInputHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */