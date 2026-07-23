/*     */ package com.brandon3055.draconicevolution.client.gui;
/*     */ 
/*     */ import com.brandon3055.brandonscore.client.utills.GuiHelper;
/*     */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.container.ContainerUpgradeModifier;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileUpgradeModifier;
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import com.gamerforea.client.util.TessellatorDebuggerUtils;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GUIUpgradeModifier extends GuiContainer {
/*     */   public EntityPlayer player;
/*     */   private TileUpgradeModifier tile;
/*     */   public boolean inUse = false;
/*  33 */   private IUpgradableItem upgradableItem = null;
/*  34 */   private ItemStack stack = null;
/*  35 */   private List<IUpgradableItem.EnumUpgrade> itemUpgrades = new ArrayList<>(); private ContainerUpgradeModifier containerEM; private int coreSlots; private int coreTier;
/*     */   private int usedSlots;
/*     */   private boolean[] coreInInventory;
/*     */   
/*  39 */   public GUIUpgradeModifier(InventoryPlayer invPlayer, TileUpgradeModifier tile, ContainerUpgradeModifier containerEM) { super((Container)containerEM);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     this.coreSlots = 0;
/* 103 */     this.coreTier = 0;
/* 104 */     this.usedSlots = 0;
/* 105 */     this.coreInInventory = new boolean[4]; this.containerEM = containerEM; this.field_146999_f = 176; this.field_147000_g = 190; this.tile = tile; this.player = invPlayer.field_70458_d; }
/*     */   protected void func_146976_a(float f, int x, int y) { GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); ResourceHandler.bindResource("textures/gui/UpgradeModifier.png"); func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g); func_73729_b(this.field_147003_i + 70, this.field_147009_r + 6, 60, 106, 100, 50); func_73729_b(this.field_147003_i + 70, this.field_147009_r + 56, 60, 106, 100, 50); GL11.glPushMatrix(); GL11.glTranslated((this.field_147003_i + 70), (this.field_147009_r + 6), 0.0D); GL11.glTranslatef(50.0F, 50.0F, 0.0F); GL11.glRotatef(this.tile.rotation + f * this.tile.rotationSpeed, 0.0F, 0.0F, 1.0F); GL11.glTranslatef(-50.0F, -50.0F, 0.0F); func_73729_b(0, 0, 70, 6, 100, 100); GL11.glPopMatrix(); if (!this.inUse) { func_73729_b(this.field_147003_i + 3, this.field_147009_r + 77, 60, 106, 56, 55); func_73729_b(this.field_147003_i + 3, this.field_147009_r + 132, 60, 106, 56, 55); } else { drawFlippedTexturedModalRect(this.field_147003_i + 59, this.field_147009_r + 77, 3, 77, 56, 110); func_73729_b(this.field_147003_i + 115, this.field_147009_r + 77, 3, 77, 56, 110); drawFlippedTexturedModalRect(this.field_147003_i + 171, this.field_147009_r + 77, 57, 77, 2, 110); }  if (!this.inUse) { drawSlots(); } else { renderUpgrades(x, y); }
/*     */      if (this.inUse)
/*     */       drawHoveringText(this.upgradableItem.getUpgradeStats(this.stack), this.field_147003_i + this.field_146999_f - 9, this.field_147009_r + 17, this.field_146289_q);  }
/* 109 */   protected void func_146979_b(int x, int y) { func_73732_a(this.field_146289_q, this.tile.func_145838_q().func_149732_F(), this.field_146999_f / 2, -9, 65535); } public void func_73876_c() { super.func_73876_c();
/* 110 */     if (this.tile.func_70301_a(0) != null && this.tile.func_70301_a(0).func_77973_b() instanceof IUpgradableItem)
/* 111 */     { this.stack = this.tile.func_70301_a(0);
/* 112 */       this.upgradableItem = (IUpgradableItem)this.stack.func_77973_b();
/* 113 */       this.itemUpgrades = this.upgradableItem.getUpgrades(this.stack);
/* 114 */       this.inUse = true;
/* 115 */       this.coreSlots = this.upgradableItem.getUpgradeCap(this.stack);
/* 116 */       this.coreTier = this.upgradableItem.getMaxTier(this.stack);
/* 117 */       this.usedSlots = 0;
/* 118 */       this.coreInInventory[0] = this.player.field_71071_by.func_146028_b((Item)ModItems.draconicCore);
/* 119 */       this.coreInInventory[1] = this.player.field_71071_by.func_146028_b((Item)ModItems.wyvernCore);
/* 120 */       this.coreInInventory[2] = this.player.field_71071_by.func_146028_b((Item)ModItems.awakenedCore);
/* 121 */       this.coreInInventory[3] = this.player.field_71071_by.func_146028_b((Item)ModItems.chaoticCore);
/*     */       
/* 123 */       for (IUpgradableItem.EnumUpgrade upgrade : this.upgradableItem.getUpgrades(this.stack)) {
/* 124 */         int[] arrayOfInt; int i; byte b; for (arrayOfInt = upgrade.getCoresApplied(this.stack), i = arrayOfInt.length, b = 0; b < i; ) { Integer integer = Integer.valueOf(arrayOfInt[b]); this.usedSlots += integer.intValue(); b++; }
/*     */       
/*     */       }  }
/* 127 */     else { this.inUse = false; }
/*     */      } public void func_73866_w_() {
/*     */     super.func_73866_w_();
/*     */   } protected void func_146284_a(GuiButton button) {} private void drawSlots() {
/* 131 */     ResourceHandler.bindResource("textures/gui/Widgets.png");
/*     */     
/* 133 */     int xPos = this.field_147003_i + (this.field_146999_f - 162) / 2;
/* 134 */     int yPos = this.field_147009_r + 110;
/*     */     
/* 136 */     for (int y = 0; y < 3; y++) {
/* 137 */       for (int i = 0; i < 9; i++) {
/* 138 */         func_73729_b(xPos + i * 18, yPos + y * 18, 138, 0, 18, 18);
/*     */       }
/*     */     } 
/*     */     
/* 142 */     for (int x = 0; x < 9; x++) {
/* 143 */       func_73729_b(xPos + x * 18, yPos + 56, 138, 0, 18, 18);
/*     */     }
/*     */     
/* 146 */     func_73729_b(this.field_147003_i + 111, this.field_147009_r + 47, 138, 0, 18, 18);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int x, int y, int button) {
/* 151 */     super.func_73864_a(x, y, button);
/*     */     
/* 153 */     for (IUpgradableItem.EnumUpgrade upgrade : this.itemUpgrades) {
/* 154 */       int xIndex = this.itemUpgrades.indexOf(upgrade);
/* 155 */       int spacing = (this.field_146999_f - 6) / this.itemUpgrades.size();
/* 156 */       int xPos = this.field_147003_i + xIndex * spacing + (spacing - 23) / 2 + 4;
/* 157 */       int yPos = this.field_147009_r + 90;
/*     */       
/* 159 */       int[] appliedCores = upgrade.getCoresApplied(this.tile.func_70301_a(0));
/*     */       
/* 161 */       for (int i = 0; i <= this.coreTier; i++) {
/*     */         
/* 163 */         if (this.coreInInventory[i] && this.coreSlots > this.usedSlots && GuiHelper.isInRect(xPos, yPos + 33 + i * 18, 8, 8, x, y) && upgrade.getUpgradePoints(this.stack) < this.upgradableItem.getMaxUpgradePoints(upgrade.index, this.stack)) {
/* 164 */           this.containerEM.sendObjectToServer(null, upgrade.index, (i * 2));
/* 165 */           Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(ResourceHandler.getResourceMinecraft("gui.button.press"), 1.0F));
/*     */         } 
/*     */ 
/*     */         
/* 169 */         if (appliedCores[i] > 0 && GuiHelper.isInRect(xPos + 16, yPos + 33 + i * 18, 8, 8, x, y)) {
/* 170 */           this.containerEM.sendObjectToServer(null, upgrade.index, (1 + i * 2));
/* 171 */           Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(ResourceHandler.getResourceMinecraft("gui.button.press"), 1.0F));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderUpgrades(int x, int y) {
/* 180 */     for (IUpgradableItem.EnumUpgrade upgrade : this.itemUpgrades) {
/* 181 */       int xIndex = this.itemUpgrades.indexOf(upgrade);
/* 182 */       int spacing = (this.field_146999_f - 6) / this.itemUpgrades.size();
/* 183 */       int xPos = this.field_147003_i + xIndex * spacing + (spacing - 23) / 2 + 4;
/* 184 */       int yPos = this.field_147009_r + 90;
/*     */       
/* 186 */       ResourceHandler.bindResource("textures/gui/UpgradeModifier.png");
/*     */       
/* 188 */       func_73729_b(xPos, yPos, 0, 190, 24, 24);
/* 189 */       func_73729_b(xPos + 3, yPos + 3, upgrade.index * 18, 220, 18, 18);
/* 190 */       func_73729_b(xPos + 2, yPos - 10, 126, 190, 20, 11);
/*     */       
/* 192 */       int[] appliedCores = upgrade.getCoresApplied(this.tile.func_70301_a(0));
/*     */       int i;
/* 194 */       for (i = 0; i <= this.coreTier; i++) {
/* 195 */         func_73729_b(xPos + 3, yPos + 24 + i * 18, 24 + i * 18, 190, 18, 18);
/* 196 */         func_73729_b(xPos + 3, yPos + 24 + i * 18, 24 + i * 18, 190, 18, 18);
/*     */         
/* 198 */         GL11.glEnable(3042);
/* 199 */         GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.9F);
/* 200 */         if (appliedCores[i] < 10) { func_73729_b(xPos + 8, yPos + 28 + i * 18, 3, 3, 7, 9); }
/* 201 */         else { func_73729_b(xPos + 5, yPos + 28 + i * 18, 3, 3, 13, 9); }
/* 202 */          GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 203 */         GL11.glDisable(3042);
/*     */ 
/*     */         
/* 206 */         if (this.coreSlots > this.usedSlots && upgrade.getUpgradePoints(this.stack) < this.upgradableItem.getMaxUpgradePoints(upgrade.index, this.stack)) {
/* 207 */           boolean hovering = GuiHelper.isInRect(xPos, yPos + 33 + i * 18, 8, 8, x, y);
/* 208 */           if (!this.coreInInventory[i]) { func_73729_b(xPos, yPos + 33 + i * 18, 24, 208, 8, 8); }
/* 209 */           else { func_73729_b(xPos, yPos + 33 + i * 18, 32 + (hovering ? 8 : 0), 208, 8, 8); }
/*     */         
/*     */         } 
/*     */         
/* 213 */         if (appliedCores[i] > 0) {
/* 214 */           boolean hovering = GuiHelper.isInRect(xPos + 16, yPos + 33 + i * 18, 8, 8, x, y);
/* 215 */           func_73729_b(xPos + 16, yPos + 33 + i * 18, 56 + (hovering ? 8 : 0), 208, 8, 8);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 220 */       for (i = 0; i <= this.coreTier; i++)
/* 221 */         this.field_146289_q.func_78276_b(String.valueOf(appliedCores[i]), xPos + 12 - this.field_146289_q.func_78256_a(String.valueOf(appliedCores[i])) / 2, yPos + 29 + i * 18, 16777215); 
/* 222 */       this.field_146289_q.func_78276_b(String.valueOf(upgrade.getUpgradePoints(this.stack)), xPos + 12 - this.field_146289_q.func_78256_a(String.valueOf(upgrade.getUpgradePoints(this.stack))) / 2, yPos - 8, 16777215);
/*     */     } 
/*     */     
/* 225 */     this.field_146289_q.func_78261_a(StatCollector.func_74838_a("gui.de.cores.txt"), this.field_147003_i + 4, this.field_147009_r + 4, 65280);
/* 226 */     this.field_146289_q.func_78276_b(StatCollector.func_74838_a("gui.de.cap.txt"), this.field_147003_i + 4, this.field_147009_r + 16, 0);
/* 227 */     this.field_146289_q.func_78276_b(">" + this.coreSlots, this.field_147003_i + 4, this.field_147009_r + 25, 0);
/* 228 */     this.field_146289_q.func_78276_b(StatCollector.func_74838_a("gui.de.installed.txt"), this.field_147003_i + 4, this.field_147009_r + 37, 0);
/* 229 */     this.field_146289_q.func_78276_b(">" + this.usedSlots, this.field_147003_i + 4, this.field_147009_r + 46, 0);
/* 230 */     this.field_146289_q.func_78276_b(StatCollector.func_74838_a("gui.de.free.txt"), this.field_147003_i + 4, this.field_147009_r + 58, 0);
/* 231 */     this.field_146289_q.func_78276_b(">" + (this.coreSlots - this.usedSlots), this.field_147003_i + 4, this.field_147009_r + 67, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int x, int y, float f) {
/* 237 */     super.func_73863_a(x, y, f);
/*     */     
/* 239 */     if (!this.inUse)
/*     */       return; 
/* 241 */     for (IUpgradableItem.EnumUpgrade upgrade : this.itemUpgrades) {
/* 242 */       int xIndex = this.itemUpgrades.indexOf(upgrade);
/* 243 */       int spacing = (this.field_146999_f - 6) / this.itemUpgrades.size();
/* 244 */       int xPos = this.field_147003_i + xIndex * spacing + (spacing - 23) / 2 + 4;
/* 245 */       int yPos = this.field_147009_r + 90;
/* 246 */       int[] appliedCores = upgrade.getCoresApplied(this.tile.func_70301_a(0));
/*     */       
/* 248 */       if (GuiHelper.isInRect(xPos, yPos, 24, 24, x, y)) {
/* 249 */         List<String> list = new ArrayList();
/* 250 */         list.add(upgrade.getLocalizedName());
/* 251 */         drawHoveringText(list, x, y, this.field_146289_q);
/*     */       } 
/*     */       
/* 254 */       if (GuiHelper.isInRect(xPos + 3, yPos - 9, 18, 8, x, y)) {
/* 255 */         List<String> list = new ArrayList();
/* 256 */         list.add(StatCollector.func_74838_a("gui.de.basePoints.txt") + ": " + this.upgradableItem.getBaseUpgradePoints(upgrade.index));
/* 257 */         list.add(StatCollector.func_74838_a("gui.de.maxPoints.txt") + ": " + this.upgradableItem.getMaxUpgradePoints(upgrade.index, this.stack));
/* 258 */         list.add(StatCollector.func_74838_a("gui.de.pointCost.txt") + ": " + upgrade.pointConversion);
/* 259 */         drawHoveringText(list, x, y, this.field_146289_q);
/*     */       } 
/*     */       
/* 262 */       for (int i = 0; i <= this.coreTier; i++) {
/* 263 */         if (GuiHelper.isInRect(xPos + 9, yPos + 25 + i * 18, 6, 15, x, y)) {
/* 264 */           List<String> list = new ArrayList();
/* 265 */           double value = Math.pow(2.0D, i) / upgrade.pointConversion;
/* 266 */           String string = StatCollector.func_74838_a("gui.de.value.txt") + ": " + value + " " + ((value == 1.0D) ? StatCollector.func_74838_a("gui.de.point.txt") : StatCollector.func_74838_a("gui.de.points.txt"));
/* 267 */           list.add(string.replace(".0", ""));
/* 268 */           drawHoveringText(list, x, y, this.field_146289_q);
/*     */         } 
/*     */ 
/*     */         
/* 272 */         if (this.coreSlots > this.usedSlots && GuiHelper.isInRect(xPos, yPos + 33 + i * 18, 8, 8, x, y)) {
/* 273 */           List<String> list = new ArrayList();
/* 274 */           if (this.coreInInventory[i]) { list.add(StatCollector.func_74838_a("gui.de.addCore.txt")); }
/* 275 */           else { list.add(StatCollector.func_74838_a("gui.de.noCoresInInventory" + i + ".txt")); }
/* 276 */            drawHoveringText(list, x, y, this.field_146289_q);
/*     */         } 
/*     */ 
/*     */         
/* 280 */         if (appliedCores[i] > 0 && GuiHelper.isInRect(xPos + 16, yPos + 33 + i * 18, 8, 8, x, y)) {
/* 281 */           List<String> list = new ArrayList();
/* 282 */           if (this.coreInInventory[i]) list.add(StatCollector.func_74838_a("gui.de.removeCore.txt")); 
/* 283 */           drawHoveringText(list, x, y, this.field_146289_q);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawFlippedTexturedModalRect(int xPos, int yPos, int texXPos, int texYPos, int xSize, int ySize) {
/* 291 */     float f = 0.00390625F;
/* 292 */     float f1 = 0.00390625F;
/* 293 */     Tessellator tessellator = Tessellator.field_78398_a;
/* 294 */     tessellator.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/gui/GUIUpgradeModifier/drawFlippedTexturedModalRect(IIIIII)V");
/* 295 */     tessellator.func_78374_a(xPos, (yPos + ySize), this.field_73735_i, ((texXPos + xSize) * f), ((texYPos + ySize) * f1));
/* 296 */     tessellator.func_78374_a((xPos + xSize), (yPos + ySize), this.field_73735_i, (texXPos * f), ((texYPos + ySize) * f1));
/* 297 */     tessellator.func_78374_a((xPos + xSize), yPos, this.field_73735_i, (texXPos * f), (texYPos * f1));
/* 298 */     tessellator.func_78374_a(xPos, yPos, this.field_73735_i, ((texXPos + xSize) * f), (texYPos * f1));
/* 299 */     tessellator.func_78381_a();
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\GUIUpgradeModifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */