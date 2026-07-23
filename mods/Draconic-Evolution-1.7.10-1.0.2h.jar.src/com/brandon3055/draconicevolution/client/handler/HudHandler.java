/*     */ package com.brandon3055.draconicevolution.client.handler;
/*     */ 
/*     */ import com.brandon3055.brandonscore.client.utills.GuiHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.items.armor.CustomArmorHandler;
/*     */ import com.brandon3055.draconicevolution.common.utills.IHudDisplayBlock;
/*     */ import com.brandon3055.draconicevolution.common.utills.IHudDisplayItem;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HudHandler
/*     */ {
/*  31 */   private static List<String> hudList = null;
/*  32 */   private static List<String> ltHudList = null;
/*  33 */   private static float toolTipFadeOut = 0.0F;
/*  34 */   private static float armorStatsFadeOut = 0.0F;
/*     */   private static boolean showShieldHud = false;
/*  36 */   private static int shieldPercentCharge = 0;
/*  37 */   private static float shieldPoints = 0.0F;
/*  38 */   private static float maxShieldPoints = 0.0F;
/*  39 */   private static float shieldEntropy = 0.0F;
/*  40 */   private static int rfCharge = 0;
/*  41 */   private static long rfTotal = 0L;
/*     */   
/*     */   int width;
/*     */   int height;
/*     */   
/*     */   @SubscribeEvent
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void drawHUD(RenderGameOverlayEvent.Post event) {
/*  49 */     Minecraft mc = Minecraft.func_71410_x();
/*  50 */     if (event.type != RenderGameOverlayEvent.ElementType.ALL || mc.field_71474_y.field_74330_P || mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat) {
/*     */       return;
/*     */     }
/*  53 */     ScaledResolution resolution = event.resolution;
/*  54 */     this.width = resolution.func_78326_a();
/*  55 */     this.height = resolution.func_78328_b();
/*  56 */     FontRenderer fontRenderer = mc.field_71466_p;
/*     */     
/*  58 */     if (ConfigHandler.hudSettings[10] == 1 && hudList != null && toolTipFadeOut > 0.0F) {
/*  59 */       int x = (int)(ConfigHandler.hudSettings[0] / 1000.0F * this.width);
/*  60 */       int y = (int)(ConfigHandler.hudSettings[1] / 1000.0F * this.height);
/*     */       
/*  62 */       GL11.glPushMatrix();
/*  63 */       GuiHelper.drawHoveringText(hudList, x, y, fontRenderer, (toolTipFadeOut > 1.0F) ? 1.0F : toolTipFadeOut, ConfigHandler.hudSettings[4] / 100.0D, this.width, this.height);
/*  64 */       GL11.glDisable(2896);
/*  65 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/*  68 */     if (ConfigHandler.hudSettings[11] == 1 && showShieldHud) {
/*  69 */       int x = (int)(ConfigHandler.hudSettings[2] / 1000.0F * this.width);
/*  70 */       int y = (int)(ConfigHandler.hudSettings[3] / 1000.0F * this.height);
/*     */       
/*  72 */       drawArmorHUD(x, y, (ConfigHandler.hudSettings[8] == 1), ConfigHandler.hudSettings[5] / 100.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static void clientTick() {
/*  79 */     if (ConfigHandler.hudSettings[6] > 0 && toolTipFadeOut > 1.0F - ConfigHandler.hudSettings[6] * 0.25F) {
/*  80 */       toolTipFadeOut -= 0.1F;
/*     */     }
/*  82 */     if (hudList != null && !hudList.equals(ltHudList)) toolTipFadeOut = 5.0F; 
/*  83 */     if (ConfigHandler.hudSettings[7] > 0 && armorStatsFadeOut > 1.0F - ConfigHandler.hudSettings[7] * 0.25F) {
/*  84 */       armorStatsFadeOut -= 0.1F;
/*  85 */       if (armorStatsFadeOut < 0.0F) armorStatsFadeOut = 0.0F;
/*     */     
/*     */     } 
/*  88 */     ltHudList = hudList;
/*     */     
/*  90 */     Minecraft mc = Minecraft.func_71410_x();
/*  91 */     if (mc == null || mc.field_71439_g == null)
/*     */       return; 
/*  93 */     hudList = null;
/*     */     
/*  95 */     if (mc.field_71462_r != null) {
/*  96 */       if (mc.field_71462_r instanceof com.brandon3055.draconicevolution.client.gui.GuiHudConfig) {
/*  97 */         hudList = new ArrayList<>();
/*  98 */         hudList.add(StatCollector.func_74838_a("info.de.hudDisplayConfigTxt1.txt"));
/*  99 */         hudList.add("");
/* 100 */         hudList.add("");
/* 101 */         hudList.add("");
/* 102 */         hudList.add(StatCollector.func_74838_a("info.de.hudDisplayConfigTxt3.txt"));
/* 103 */         toolTipFadeOut = 1.0F;
/* 104 */         armorStatsFadeOut = 1.0F;
/*     */       } 
/* 106 */     } else if (mc.field_71439_g.func_70694_bm() != null && mc.field_71439_g.func_70694_bm().func_77973_b() instanceof IHudDisplayItem) {
/* 107 */       hudList = ((IHudDisplayItem)mc.field_71439_g.func_70694_bm().func_77973_b()).getDisplayData(mc.field_71439_g.func_70694_bm());
/*     */     } 
/*     */     
/* 110 */     MovingObjectPosition mop = mc.field_71439_g.func_70614_a(5.0D, 0.0F);
/* 111 */     if (mop != null && mop.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && mc.field_71441_e.func_147439_a(mop.field_72311_b, mop.field_72312_c, mop.field_72309_d) instanceof IHudDisplayBlock) {
/* 112 */       hudList = ((IHudDisplayBlock)mc.field_71441_e.func_147439_a(mop.field_72311_b, mop.field_72312_c, mop.field_72309_d)).getDisplayData((World)mc.field_71441_e, mop.field_72311_b, mop.field_72312_c, mop.field_72309_d);
/*     */     }
/*     */     
/* 115 */     CustomArmorHandler.ArmorSummery summery = (new CustomArmorHandler.ArmorSummery()).getSummery((EntityPlayer)mc.field_71439_g);
/*     */     
/* 117 */     if (summery == null) {
/* 118 */       showShieldHud = false;
/*     */       return;
/*     */     } 
/* 121 */     showShieldHud = (armorStatsFadeOut > 0.0F);
/*     */     
/* 123 */     if (maxShieldPoints != summery.maxProtectionPoints || shieldPoints != summery.protectionPoints || shieldEntropy != summery.entropy || rfTotal != summery.totalEnergyStored) {
/* 124 */       armorStatsFadeOut = 5.0F;
/*     */     }
/* 126 */     maxShieldPoints = summery.maxProtectionPoints;
/* 127 */     shieldPoints = summery.protectionPoints;
/* 128 */     shieldPercentCharge = (int)((summery.protectionPoints / summery.maxProtectionPoints) * 100.0D);
/* 129 */     shieldEntropy = summery.entropy;
/* 130 */     rfCharge = (int)(summery.totalEnergyStored / Math.max(summery.maxTotalEnergyStorage, 1.0D) * 100.0D);
/* 131 */     rfTotal = summery.totalEnergyStored;
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawArmorHUD(int x, int y, boolean rotated, double scale) {
/* 136 */     GL11.glPushMatrix();
/* 137 */     GL11.glEnable(3008);
/* 138 */     GL11.glEnable(3042);
/* 139 */     OpenGlHelper.func_148821_a(770, 771, 1, 0);
/* 140 */     ResourceHandler.bindResource("textures/gui/HUD.png");
/*     */     
/* 142 */     GL11.glTranslated(x, y, 0.0D);
/* 143 */     GL11.glScaled(scale, scale, 1.0D);
/* 144 */     GL11.glTranslated(-x, -y, 0.0D);
/* 145 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, Math.min(armorStatsFadeOut, 1.0F));
/*     */     
/* 147 */     if (rotated)
/* 148 */     { GuiHelper.drawTexturedRect((x - 15), (y + 1), 14.0D, 16.0D, 2, 0, 13, 15, 0.0D, 0.0078125D);
/* 149 */       x += 104;
/* 150 */       GL11.glTranslated(x, y, 0.0D);
/* 151 */       GL11.glRotated(-90.0D, 0.0D, 0.0D, -1.0D);
/* 152 */       GL11.glTranslated(-x, -y, 0.0D); }
/* 153 */     else { GuiHelper.drawTexturedRect((x + 1), (y + 105), 15.0D, 17.0D, 2, 0, 13, 15, 0.0D, 0.0078125D); }
/*     */     
/* 155 */     GuiHelper.drawTexturedRect(x, y, 17.0D, 104.0D, 0, 15, 17, 104, 0.0D, 0.0078125D);
/* 156 */     GuiHelper.drawTexturedRect((x + 2), (y + 2 + 100 - shieldPercentCharge), 7.0D, shieldPercentCharge, 17, 100 - shieldPercentCharge, 7, shieldPercentCharge, 0.0D, 0.0078125D);
/* 157 */     GuiHelper.drawTexturedRect((x + 10), (y + 2 + 100 - (int)shieldEntropy), 2.0D, (int)shieldEntropy, 25, 100 - (int)shieldEntropy, 2, (int)shieldEntropy, 0.0D, 0.0078125D);
/* 158 */     GuiHelper.drawTexturedRect((x + 13), (y + 2 + 100 - rfCharge), 2.0D, rfCharge, 28, 100 - rfCharge, 2, rfCharge, 0.0D, 0.0078125D);
/*     */ 
/*     */     
/* 161 */     if (ConfigHandler.hudSettings[9] == 1) {
/* 162 */       FontRenderer fontRenderer = (Minecraft.func_71410_x()).field_71466_p;
/* 163 */       GL11.glTranslated(x, y, 0.0D);
/* 164 */       if (rotated) GL11.glRotated(90.0D, 0.0D, 0.0D, -1.0D); 
/* 165 */       GL11.glTranslated(-x, -y, 0.0D);
/* 166 */       String shield = Math.round(shieldPoints) + "/" + (int)maxShieldPoints;
/* 167 */       String entropy = "EN: " + (int)shieldEntropy + "%";
/* 168 */       String energy = "EU: " + Utills.formatNumber(rfTotal);
/* 169 */       float fade = Math.min(armorStatsFadeOut, 1.0F);
/* 170 */       if (!rotated) {
/* 171 */         fontRenderer.func_78261_a(shield, x + 18, y + 74, (int)(fade * 240.0F) + 16 << 24 | 0xFFFFFF);
/* 172 */         fontRenderer.func_78261_a(energy, x + 18, y + 84, (int)(fade * 240.0F) + 16 << 24 | 0xFFFFFF);
/* 173 */         fontRenderer.func_78261_a(entropy, x + 18, y + 94, (int)(fade * 240.0F) + 16 << 24 | 0xFFFFFF);
/*     */       } else {
/* 175 */         fontRenderer.func_78276_b(shield, x - 52 - fontRenderer.func_78256_a(shield) / 2, y + 2, (int)(fade * 240.0F) + 16 << 24 | 0xFF);
/* 176 */         fontRenderer.func_78261_a(entropy, x - fontRenderer.func_78256_a(entropy), y + 18, (int)(fade * 240.0F) + 16 << 24 | 0xFFFFFF);
/* 177 */         fontRenderer.func_78261_a(energy, x - 102, y + 18, (int)(fade * 240.0F) + 16 << 24 | 0xFFFFFF);
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     ResourceHandler.bindTexture(ResourceHandler.getResourceMinecraft("textures/gui/icons.png"));
/* 182 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 183 */     GL11.glDisable(3042);
/* 184 */     GL11.glDisable(3008);
/* 185 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\handler\HudHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */