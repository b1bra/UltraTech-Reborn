/*     */ package com.brandon3055.draconicevolution.client.render.tile;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.client.model.ModelTeleporterStand;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileTeleporterStand;
/*     */ import com.gamerforea.client.util.TessellatorDebuggerUtils;
/*     */ import java.awt.geom.Point2D;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderTileTeleporterStand
/*     */   extends TileEntitySpecialRenderer
/*     */ {
/*  35 */   ModelTeleporterStand model = new ModelTeleporterStand();
/*     */   
/*  37 */   private final ResourceLocation texture = new ResourceLocation("draconicevolution", "textures/models/TeleporterStand.png");
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_147500_a(TileEntity tileentity, double x, double y, double z, float f) {
/*  42 */     ItemStack item = null;
/*  43 */     int rotation = 0;
/*  44 */     if (tileentity instanceof TileTeleporterStand) {
/*  45 */       item = ((TileTeleporterStand)tileentity).func_70301_a(0);
/*  46 */       rotation = ((TileTeleporterStand)tileentity).rotation;
/*     */     } 
/*     */     
/*  49 */     GL11.glPushMatrix();
/*  50 */     GL11.glEnable(32826);
/*  51 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/*  52 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  53 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(this.texture);
/*     */     
/*  55 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/*  56 */     GL11.glScalef(1.0F, -1.0F, -1.0F);
/*  57 */     GL11.glRotated(rotation, 0.0D, 1.0D, 0.0D);
/*     */     
/*  59 */     this.model.render();
/*  60 */     if (item != null) drawNameString(item, rotation, tileentity, f);
/*     */     
/*  62 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  63 */     GL11.glTranslatef(0.0F, 0.0F, -0.6F);
/*  64 */     GL11.glEnable(32826);
/*  65 */     if (item != null) renderItem(tileentity, item, f); 
/*  66 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderItem(TileEntity tile, ItemStack item, float f) {
/*  71 */     if (item.func_77973_b() == null)
/*  72 */       return;  GL11.glPushMatrix();
/*     */     
/*  74 */     EntityItem itemEntity = new EntityItem(tile.func_145831_w(), 0.0D, 0.0D, 0.0D, item);
/*  75 */     itemEntity.field_70290_d = 0.0F;
/*     */     
/*  77 */     if (item.func_77973_b() instanceof com.brandon3055.draconicevolution.common.items.tools.TeleporterMKII) {
/*  78 */       GL11.glTranslatef(0.0F, 0.18F, 0.864F);
/*  79 */       GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
/*  80 */       GL11.glRotated(30.0D, 1.0D, 0.0D, 0.0D);
/*  81 */       GL11.glScalef(1.0F, 1.0F, 1.0F);
/*     */     } else {
/*  83 */       GL11.glTranslatef(0.0F, 0.22F, 0.84F);
/*  84 */       GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
/*  85 */       GL11.glRotated(30.0D, 1.0D, 0.0D, 0.0D);
/*  86 */       GL11.glScalef(1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */     
/*  89 */     RenderItem.field_82407_g = true;
/*  90 */     RenderManager.field_78727_a.func_147940_a((Entity)itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
/*  91 */     RenderItem.field_82407_g = false;
/*     */     
/*  93 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void drawNameString(ItemStack item, float rotation, TileEntity tileentity, float f) {
/*  97 */     EntityClientPlayerMP entityClientPlayerMP = (Minecraft.func_71410_x()).field_71439_g;
/*  98 */     MovingObjectPosition mop = entityClientPlayerMP.func_70614_a(10.0D, f);
/*  99 */     boolean isCursorOver = (mop != null && mop.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && mop.field_72311_b == tileentity.field_145851_c && mop.field_72312_c == tileentity.field_145848_d && mop.field_72309_d == tileentity.field_145849_e);
/* 100 */     boolean isSneaking = entityClientPlayerMP.func_70093_af();
/*     */     
/* 102 */     if (!isCursorOver && isSneaking != ConfigHandler.invertDPDSB) {
/*     */       return;
/*     */     }
/* 105 */     String s = item.func_82837_s() ? item.func_82833_r() : "";
/* 106 */     if (item.func_77973_b() instanceof com.brandon3055.draconicevolution.common.items.tools.TeleporterMKII) {
/* 107 */       short selected = ItemNBTHelper.getShort(item, "Selection", (short)0);
/* 108 */       int selrctionOffset = ItemNBTHelper.getInteger(item, "SelectionOffset", 0);
/* 109 */       NBTTagCompound compound = item.func_77978_p();
/* 110 */       if (compound == null) compound = new NBTTagCompound(); 
/* 111 */       NBTTagList list = (NBTTagList)compound.func_74781_a("Locations");
/* 112 */       if (list == null) list = new NBTTagList(); 
/* 113 */       s = list.func_150305_b(selected + selrctionOffset).func_74779_i("Name");
/*     */     } 
/* 115 */     if (s.isEmpty()) {
/*     */       return;
/*     */     }
/* 118 */     FontRenderer fontRenderer = RenderManager.field_78727_a.func_78716_a();
/* 119 */     Tessellator tess = Tessellator.field_78398_a;
/*     */     
/* 121 */     GL11.glPushMatrix();
/* 122 */     GL11.glScalef(0.02F, 0.02F, 0.02F);
/* 123 */     GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
/* 124 */     GL11.glTranslated(0.0D, -40.0D, 0.0D);
/*     */ 
/*     */     
/* 127 */     Point2D.Double p1 = new Point2D.Double(tileentity.field_145851_c + 0.5D, tileentity.field_145849_e + 0.5D);
/* 128 */     Point2D.Double p2 = new Point2D.Double(((EntityPlayerSP)entityClientPlayerMP).field_70165_t, ((EntityPlayerSP)entityClientPlayerMP).field_70161_v);
/*     */     
/* 130 */     double xDiff = ((EntityPlayerSP)entityClientPlayerMP).field_70165_t - tileentity.field_145851_c + 0.5D;
/* 131 */     double yDiff = ((EntityPlayerSP)entityClientPlayerMP).field_70163_u - tileentity.field_145848_d + 0.5D;
/* 132 */     double zDiff = ((EntityPlayerSP)entityClientPlayerMP).field_70161_v - tileentity.field_145849_e + 0.5D;
/* 133 */     double yawAngle = Math.toDegrees(Math.atan2(zDiff, xDiff));
/* 134 */     double pitchAngle = Math.toDegrees(Math.atan2(yDiff, Utills.getDistanceAtoB(((EntityPlayerSP)entityClientPlayerMP).field_70165_t, ((EntityPlayerSP)entityClientPlayerMP).field_70163_u, ((EntityPlayerSP)entityClientPlayerMP).field_70161_v, tileentity.field_145851_c + 0.5D, tileentity.field_145848_d + 0.5D, tileentity.field_145849_e + 0.5D)));
/*     */     
/* 136 */     GL11.glRotated(yawAngle + 90.0D - rotation, 0.0D, 1.0D, 0.0D);
/* 137 */     GL11.glRotated(-pitchAngle, 1.0D, 0.0D, 0.0D);
/*     */     
/* 139 */     int xmin = -1 - fontRenderer.func_78256_a(s) / 2;
/* 140 */     int xmax = 1 + fontRenderer.func_78256_a(s) / 2;
/* 141 */     int ymin = -1;
/* 142 */     int ymax = fontRenderer.field_78288_b;
/*     */     
/* 144 */     GL11.glEnable(3042);
/* 145 */     GL11.glBlendFunc(770, 771);
/* 146 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.5F);
/*     */     
/* 148 */     tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/render/tile/RenderTileTeleporterStand/drawNameString(Lnet/minecraft/item/ItemStack;FLnet/minecraft/tileentity/TileEntity;F)V");
/* 149 */     tess.func_78374_a(xmin, ymax, 0.0D, (xmin / 64), 1.0D);
/* 150 */     tess.func_78374_a(xmax, ymax, 0.0D, (xmax / 64), 1.0D);
/* 151 */     tess.func_78374_a(xmax, ymin, 0.0D, (xmax / 64), 0.75D);
/* 152 */     tess.func_78374_a(xmin, ymin, 0.0D, (xmin / 64), 0.75D);
/* 153 */     tess.func_78381_a();
/*     */     
/* 155 */     GL11.glDisable(3042);
/* 156 */     GL11.glTranslated(0.0D, 0.0D, -0.1D);
/* 157 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 158 */     GL11.glDisable(2896);
/*     */     
/* 160 */     fontRenderer.func_78276_b(s, -fontRenderer.func_78256_a(s) / 2, 0, 16777215);
/*     */     
/* 162 */     GL11.glEnable(2896);
/* 163 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\tile\RenderTileTeleporterStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */