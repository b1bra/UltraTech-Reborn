/*     */ package com.brandon3055.draconicevolution.client.handler;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.DataUtills;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.items.armor.CustomArmorHandler;
/*     */ import com.brandon3055.draconicevolution.common.items.weapons.BowHandler;
/*     */ import com.brandon3055.draconicevolution.common.network.MountUpdatePacket;
/*     */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*     */ import cpw.mods.fml.common.eventhandler.EventPriority;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.common.gameevent.TickEvent;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraftforge.client.event.FOVUpdateEvent;
/*     */ import net.minecraftforge.client.event.RenderPlayerEvent;
/*     */ import net.minecraftforge.client.model.AdvancedModelLoader;
/*     */ import net.minecraftforge.client.model.IModelCustom;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientEventHandler
/*     */ {
/*  41 */   public static Map<EntityPlayer, DataUtills.XZPair<Float, Integer>> playerShieldStatus = new HashMap<>();
/*     */   
/*     */   public static int elapsedTicks;
/*  44 */   private static float previousFOB = 0.0F;
/*  45 */   public static float previousSensitivity = 0.0F;
/*     */   public static boolean bowZoom = false;
/*     */   public static boolean lastTickBowZoom = false;
/*  48 */   public static int tickSet = 0;
/*  49 */   private static int remountTicksRemaining = 0;
/*  50 */   private static int remountEntityID = 0;
/*  51 */   public static float energyCrystalAlphaValue = 0.0F;
/*  52 */   public static float energyCrystalAlphaTarget = 0.0F;
/*     */   public static boolean playerHoldingWrench = false;
/*     */   public static Minecraft mc;
/*  55 */   private static Random rand = new Random();
/*     */   private static IModelCustom shieldSphere;
/*     */   
/*     */   public ClientEventHandler() {
/*  59 */     shieldSphere = AdvancedModelLoader.loadModel(ResourceHandler.getResource("models/shieldSphere.obj"));
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void tickEnd(TickEvent.ClientTickEvent event) {
/*  64 */     if (event.phase != TickEvent.Phase.START || event.type != TickEvent.Type.CLIENT || event.side != Side.CLIENT) {
/*     */       return;
/*     */     }
/*     */     
/*  68 */     for (Iterator<Map.Entry<EntityPlayer, DataUtills.XZPair<Float, Integer>>> i = playerShieldStatus.entrySet().iterator(); i.hasNext(); ) {
/*  69 */       Map.Entry<EntityPlayer, DataUtills.XZPair<Float, Integer>> entry = i.next();
/*  70 */       if (elapsedTicks - ((Integer)((DataUtills.XZPair)entry.getValue()).getValue()).intValue() > 5) {
/*  71 */         i.remove();
/*     */       }
/*     */     } 
/*  74 */     if (mc == null) {
/*  75 */       mc = Minecraft.func_71410_x();
/*  76 */     } else if (mc.field_71441_e != null) {
/*  77 */       elapsedTicks++;
/*  78 */       HudHandler.clientTick();
/*     */       
/*  80 */       if (bowZoom && !lastTickBowZoom) {
/*  81 */         previousSensitivity = (Minecraft.func_71410_x()).field_71474_y.field_74341_c;
/*  82 */         (Minecraft.func_71410_x()).field_71474_y.field_74341_c = previousSensitivity / 3.0F;
/*  83 */       } else if (!bowZoom && lastTickBowZoom) {
/*  84 */         (Minecraft.func_71410_x()).field_71474_y.field_74341_c = previousSensitivity;
/*     */       } 
/*  86 */       lastTickBowZoom = bowZoom;
/*  87 */       if (elapsedTicks - tickSet > 10) {
/*  88 */         bowZoom = false;
/*     */       }
/*  90 */       if (energyCrystalAlphaValue < energyCrystalAlphaTarget)
/*  91 */         energyCrystalAlphaValue += 0.01F; 
/*  92 */       if (energyCrystalAlphaValue > energyCrystalAlphaTarget) {
/*  93 */         energyCrystalAlphaValue -= 0.01F;
/*     */       }
/*  95 */       if (Math.abs(energyCrystalAlphaTarget - energyCrystalAlphaValue) <= 0.02F) {
/*  96 */         energyCrystalAlphaTarget = rand.nextFloat();
/*     */       }
/*     */       
/*  99 */       playerHoldingWrench = (mc.field_71439_g.func_70694_bm() != null && mc.field_71439_g.func_70694_bm().func_77973_b() == ModItems.wrench);
/*     */       
/* 101 */       searchForPlayerMount();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.LOW)
/*     */   public void fovUpdate(FOVUpdateEvent event) {
/* 109 */     if (event.entity.func_70694_bm() != null && (event.entity.func_70694_bm()
/* 110 */       .func_77973_b() instanceof com.brandon3055.draconicevolution.common.items.weapons.WyvernBow || event.entity.func_70694_bm()
/* 111 */       .func_77973_b() instanceof com.brandon3055.draconicevolution.common.items.weapons.DraconicBow) && (Minecraft.func_71410_x()).field_71474_y.field_74313_G.func_151470_d()) {
/*     */       
/* 113 */       BowHandler.BowProperties properties = new BowHandler.BowProperties(event.entity.func_70694_bm(), (EntityPlayer)event.entity);
/*     */       
/* 115 */       event.newfov = (6.0F - properties.zoomModifier) / 6.0F * event.fov;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     CustomArmorHandler.ArmorSummery summery = (new CustomArmorHandler.ArmorSummery()).getSummery((EntityPlayer)event.entity);
/*     */     
/* 130 */     if (summery != null && summery.speedModifier > 0.0F) {
/* 131 */       IAttributeInstance iattributeinstance = event.entity.func_110148_a(SharedMonsterAttributes.field_111263_d);
/* 132 */       float f = (float)((iattributeinstance.func_111126_e() / event.entity.field_71075_bZ.func_75094_b() + 1.0D) / 2.0D);
/* 133 */       event.newfov /= f;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void searchForPlayerMount() {
/* 140 */     if (remountTicksRemaining > 0) {
/* 141 */       Entity e = (Minecraft.func_71410_x()).field_71441_e.func_73045_a(remountEntityID);
/* 142 */       if (e != null) {
/* 143 */         (Minecraft.func_71410_x()).field_71439_g.func_70078_a(e);
/* 144 */         LogHelper.info("Successfully placed player on mount after " + (500 - remountTicksRemaining) + " ticks");
/* 145 */         remountTicksRemaining = 0;
/*     */         return;
/*     */       } 
/* 148 */       remountTicksRemaining--;
/* 149 */       if (remountTicksRemaining == 0) {
/* 150 */         LogHelper.error("Unable to locate player mount after 500 ticks! Aborting");
/* 151 */         DraconicEvolution.network.sendToServer((IMessage)new MountUpdatePacket(-1));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void tryRepositionPlayerOnMount(int id) {
/* 157 */     if (remountTicksRemaining == 500)
/*     */       return; 
/* 159 */     remountTicksRemaining = 500;
/* 160 */     remountEntityID = id;
/* 161 */     LogHelper.info("Started checking for player mount");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderArmorEvent(RenderPlayerEvent.SetArmorModel event) {
/* 173 */     if (ConfigHandler.useOriginal3DArmorModel || ConfigHandler.useOldArmorModel || event.isCanceled())
/*     */       return; 
/* 175 */     if (event.stack != null && (event.stack.func_77973_b() instanceof com.brandon3055.draconicevolution.common.items.armor.DraconicArmor || event.stack.func_77973_b() instanceof com.brandon3055.draconicevolution.common.items.armor.WyvernArmor)) {
/* 176 */       ItemArmor itemarmor = (ItemArmor)event.stack.func_77973_b();
/* 177 */       ModelBiped modelbiped = itemarmor.getArmorModel((EntityLivingBase)event.entityPlayer, event.stack, event.slot);
/* 178 */       event.renderer.func_77042_a((ModelBase)modelbiped);
/* 179 */       modelbiped.field_78095_p = event.renderer.field_77109_a.field_78095_p;
/* 180 */       modelbiped.field_78093_q = event.renderer.field_77109_a.field_78093_q;
/* 181 */       modelbiped.field_78091_s = event.renderer.field_77109_a.field_78091_s;
/* 182 */       event.result = 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderPlayerEvent(RenderPlayerEvent.Post event) {
/* 188 */     if (playerShieldStatus.containsKey(event.entityPlayer)) {
/* 189 */       GL11.glPushMatrix();
/* 190 */       GL11.glDepthMask(false);
/* 191 */       GL11.glDisable(2884);
/* 192 */       GL11.glDisable(3008);
/* 193 */       GL11.glEnable(3042);
/* 194 */       GL11.glDisable(2896);
/* 195 */       ResourceHandler.bindResource("textures/models/shieldSphere.png");
/*     */       
/* 197 */       float p = ((Float)((DataUtills.XZPair)playerShieldStatus.get(event.entityPlayer)).getKey()).floatValue();
/*     */       
/* 199 */       EntityClientPlayerMP entityClientPlayerMP = (Minecraft.func_71410_x()).field_71439_g;
/*     */       
/* 201 */       int i = 5 - elapsedTicks - ((Integer)((DataUtills.XZPair)playerShieldStatus.get(event.entityPlayer)).getValue()).intValue();
/*     */       
/* 203 */       GL11.glColor4f(1.0F - p, 0.0F, p, i / 5.0F);
/*     */       
/* 205 */       if (entityClientPlayerMP != event.entityPlayer) {
/* 206 */         double translationXLT = event.entityPlayer.field_70169_q - ((EntityPlayer)entityClientPlayerMP).field_70169_q;
/* 207 */         double translationYLT = event.entityPlayer.field_70167_r - ((EntityPlayer)entityClientPlayerMP).field_70167_r;
/* 208 */         double translationZLT = event.entityPlayer.field_70166_s - ((EntityPlayer)entityClientPlayerMP).field_70166_s;
/*     */         
/* 210 */         double translationX = translationXLT + (event.entityPlayer.field_70165_t - ((EntityPlayer)entityClientPlayerMP).field_70165_t - translationXLT) * event.partialRenderTick;
/* 211 */         double translationY = translationYLT + (event.entityPlayer.field_70163_u - ((EntityPlayer)entityClientPlayerMP).field_70163_u - translationYLT) * event.partialRenderTick;
/* 212 */         double translationZ = translationZLT + (event.entityPlayer.field_70161_v - ((EntityPlayer)entityClientPlayerMP).field_70161_v - translationZLT) * event.partialRenderTick;
/*     */         
/* 214 */         GL11.glTranslated(translationX, translationY + 1.1D, translationZ);
/*     */       } else {
/* 216 */         GL11.glTranslated(0.0D, -0.5D, 0.0D);
/*     */       } 
/* 218 */       GL11.glScaled(1.0D, 1.5D, 1.0D);
/*     */       
/* 220 */       shieldSphere.renderAll();
/*     */       
/* 222 */       GL11.glEnable(2884);
/* 223 */       GL11.glEnable(3008);
/* 224 */       GL11.glDisable(3042);
/* 225 */       GL11.glEnable(2896);
/* 226 */       GL11.glDepthMask(true);
/* 227 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\handler\ClientEventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */