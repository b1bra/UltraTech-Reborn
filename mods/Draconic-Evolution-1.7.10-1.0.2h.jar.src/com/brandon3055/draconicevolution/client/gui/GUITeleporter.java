/*     */ package com.brandon3055.draconicevolution.client.gui;
/*     */ import com.brandon3055.brandonscore.client.utills.GuiHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.InventoryUtils;
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Teleporter;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.network.TeleporterPacket;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import com.brandon3055.brandonscore.common.tags.Tags;
/*     */ import com.brandon3055.brandonscore.common.tags.IItemMatcher;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import org.lwjglx.input.Keyboard;
/*     */ import org.lwjglx.input.Mouse;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GUITeleporter extends GuiScreen {
/*  36 */   private final int xSize = 182;
/*  37 */   private final int ySize = 141;
/*  38 */   private ResourceLocation guiTexture = new ResourceLocation("draconicevolution", "textures/gui/TeleporterMKII.png");
/*     */   private ItemStack teleporterItem;
/*  40 */   protected List<Teleporter.TeleportLocation> locations = new ArrayList<>(0);
/*     */   
/*  42 */   private int selected = 0;
/*  43 */   private int selectionOffset = 0;
/*  44 */   private int maxOffset = 0;
/*  45 */   private int fuel = 0;
/*     */   private boolean editingExisting = false;
/*     */   private boolean editingNew = false;
/*     */   private boolean showFuelLight = true;
/*  49 */   private int tick = 0;
/*     */   
/*     */   private GuiTextField textBeingEdited;
/*     */   private EntityPlayer player;
/*     */   
/*     */   public GUITeleporter(EntityPlayer player) {
/*  55 */     this.player = player;
/*  56 */     if (player.func_71045_bC() != null && player.func_71045_bC().func_77969_a(new ItemStack((Item)ModItems.teleporterMKII))) {
/*  57 */       this.teleporterItem = player.func_71045_bC();
/*  58 */       readDataFromItem(this.teleporterItem);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73863_a(int x, int y, float f) {
/*  64 */     func_146276_q_();
/*  65 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  66 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(this.guiTexture);
/*  67 */     getClass(); int posX = (this.field_146294_l - 182) / 2;
/*  68 */     getClass(); int posY = (this.field_146295_m - 141) / 2;
/*  69 */     getClass(); getClass(); func_73729_b(posX, posY, 0, 0, 182, 141);
/*     */     
/*  71 */     if (this.fuel <= 5) func_73729_b(posX + 169, posY + 86, 40, 150, 7, 7); 
/*  72 */     if ((this.fuel <= 5) ? this.showFuelLight : (this.fuel < 10)) {
/*  73 */       func_73729_b(posX + 169, posY + 86, 40, 143, 7, 7);
/*     */     }
/*  75 */     drawArrows(x - posX, y - posY);
/*     */     
/*  77 */     drawLocations(x - posX, y - posY);
/*     */     
/*  79 */     drawSelectionInfo();
/*     */     
/*  81 */     this.textBeingEdited.func_146194_f();
/*     */     
/*  83 */     String colour = EnumChatFormatting.GREEN + "";
/*  84 */     if (this.fuel < 10) colour = EnumChatFormatting.YELLOW + ""; 
/*  85 */     if (this.fuel == 0) colour = EnumChatFormatting.DARK_RED + ""; 
/*  86 */     this.field_146289_q.func_78276_b(colour + StatCollector.func_74838_a("info.teleporterInfFuel.txt") + " " + this.fuel, posX + 115, posY + 87, 0);
/*     */     
/*  88 */     super.func_73863_a(x, y, f);
/*     */     
/*  90 */     for (int i = 0; i < Math.min(12, this.locations.size()); i++) {
/*  91 */       if (GuiHelper.isInRect(17, 6 + i * 11, 80, 10, x - posX, y - posY)) {
/*  92 */         List<String> l = new ArrayList();
/*  93 */         l.add(StatCollector.func_74838_a("info.de.rightClickToTeleport.txt"));
/*  94 */         drawHoveringText(l, x, y, this.field_146289_q);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawLocations(int x, int y) {
/* 100 */     getClass(); int posX = (this.field_146294_l - 182) / 2;
/* 101 */     getClass(); int posY = (this.field_146295_m - 141) / 2;
/*     */     
/* 103 */     for (int i = 0; i < Math.min(12, this.locations.size()); i++) {
/* 104 */       if (GuiHelper.isInRect(17, 6 + i * 11, 80, 10, x, y)) {
/* 105 */         func_73729_b(posX + 19, posY + 5 + i * 11, 0, 188, 80, 10);
/*     */       }
/*     */       
/* 108 */       if (getLocationSafely(i + this.selectionOffset).getWriteProtected())
/* 109 */       { if (GuiHelper.isInRect(102, 7 + i * 11, 6, 6, x, y))
/* 110 */         { func_73729_b(posX + 102, posY + 7 + i * 11, 26, 149, 6, 6); }
/* 111 */         else { func_73729_b(posX + 102, posY + 7 + i * 11, 26, 143, 6, 6); }
/*     */          }
/* 113 */       else if (GuiHelper.isInRect(101, 7 + i * 11, 8, 7, x, y))
/* 114 */       { func_73729_b(posX + 101, posY + 7 + i * 11, 32, 150, 8, 7); }
/* 115 */       else { func_73729_b(posX + 101, posY + 7 + i * 11, 32, 143, 8, 7); }
/*     */     
/*     */     } 
/*     */     
/* 119 */     func_73729_b(posX + 19, posY + 5 + this.selected * 11, 0, 188, 80, 10);
/*     */     
/* 121 */     int yl = 0;
/* 122 */     for (int j = this.selectionOffset; j < this.locations.size() && j < this.selectionOffset + 12; j++) {
/* 123 */       String s = getLocationSafely(j).getName();
/* 124 */       if (this.field_146289_q.func_78256_a(s) > 80) {
/* 125 */         int safety = 0;
/* 126 */         while (this.field_146289_q.func_78256_a(s) > 70) {
/* 127 */           s = s.substring(0, s.length() - 1);
/* 128 */           safety++;
/* 129 */           if (safety > 200)
/*     */             break; 
/* 131 */         }  s = s + "...";
/*     */       } 
/* 133 */       this.field_146289_q.func_78276_b(s, posX + 21, posY + 7 + yl * 11, 0);
/* 134 */       yl++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawArrows(int x, int y) {
/* 139 */     getClass(); int posX = (this.field_146294_l - 182) / 2;
/* 140 */     getClass(); int posY = (this.field_146295_m - 141) / 2;
/*     */     
/* 142 */     if (this.selectionOffset > 0)
/* 143 */     { boolean highLighted = GuiHelper.isInRect(4, 4, 13, 15, x, y);
/* 144 */       if (highLighted) { func_73729_b(posX + 4, posY + 4, 0, 158, 13, 15); }
/* 145 */       else { func_73729_b(posX + 4, posY + 4, 0, 143, 13, 15); }  }
/* 146 */     else { func_73729_b(posX + 4, posY + 4, 0, 173, 13, 15); }
/* 147 */      if (this.selectionOffset < this.maxOffset)
/* 148 */     { boolean highLighted = GuiHelper.isInRect(4, 122, 13, 15, x, y);
/* 149 */       if (highLighted) { func_73729_b(posX + 4, posY + 122, 13, 158, 13, 15); }
/* 150 */       else { func_73729_b(posX + 4, posY + 122, 13, 143, 13, 15); }  }
/* 151 */     else { func_73729_b(posX + 4, posY + 122, 13, 173, 13, 15); }
/*     */     
/* 153 */     float percent = (this.locations.size() <= 12) ? 1.0F : (12.0F / this.locations.size());
/* 154 */     int drawSize = (int)(percent * 99.0F);
/* 155 */     int space = 99 - drawSize;
/* 156 */     float location = this.selectionOffset / (this.locations.size() - 12);
/* 157 */     int yOffset = (int)(location * space);
/* 158 */     func_73729_b(posX + 5, posY + 21 + yOffset, 182, 0, 11, drawSize);
/* 159 */     func_73729_b(posX + 5, posY + 21 + drawSize - 1 + yOffset, 182, 98, 11, 1);
/*     */   }
/*     */   
/*     */   private void drawSelectionInfo() {
/* 163 */     getClass(); int posX = (this.field_146294_l - 182) / 2;
/* 164 */     getClass(); int posY = (this.field_146295_m - 141) / 2;
/* 165 */     if (this.locations.isEmpty())
/* 166 */       return;  this.field_146289_q.func_78276_b(EnumChatFormatting.GOLD + "X: " + (int)getLocationSafely(this.selected + this.selectionOffset).getXCoord(), posX + 114, posY + 7, 0);
/* 167 */     this.field_146289_q.func_78276_b(EnumChatFormatting.GOLD + "Y: " + (int)getLocationSafely(this.selected + this.selectionOffset).getYCoord(), posX + 114, posY + 16, 0);
/* 168 */     this.field_146289_q.func_78276_b(EnumChatFormatting.GOLD + "Z: " + (int)getLocationSafely(this.selected + this.selectionOffset).getZCoord(), posX + 114, posY + 25, 0);
/* 169 */     this.field_146289_q.func_78276_b(EnumChatFormatting.GOLD + "" + getLocationSafely(this.selected + this.selectionOffset).getDimensionName(), posX + 114, posY + 34, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int x, int y, int button) {
/* 174 */     getClass(); int posX = (this.field_146294_l - 182) / 2;
/* 175 */     getClass(); int posY = (this.field_146295_m - 141) / 2;
/*     */     
/* 177 */     boolean offsetChanged = false;
/* 178 */     boolean selectionChanged = false;
/*     */     
/* 180 */     if (this.textBeingEdited.func_146176_q()) this.textBeingEdited.func_146192_a(x, y, button);
/*     */ 
/*     */     
/* 183 */     if (this.selectionOffset > 0 && GuiHelper.isInRect(3, 5, 13, 15, x - posX, y - posY)) {
/* 184 */       this.selectionOffset--;
/* 185 */       offsetChanged = true;
/* 186 */       if (this.selected < 11) {
/* 187 */         this.selected++;
/* 188 */         selectionChanged = true;
/*     */       } 
/*     */     } 
/* 191 */     if (this.selectionOffset < this.maxOffset && GuiHelper.isInRect(3, 123, 13, 15, x - posX, y - posY)) {
/* 192 */       this.selectionOffset++;
/* 193 */       offsetChanged = true;
/* 194 */       if (this.selected > 0) {
/* 195 */         this.selected--;
/* 196 */         selectionChanged = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 201 */     for (int i = 0; i < Math.min(12, this.locations.size()); i++) {
/* 202 */       if (GuiHelper.isInRect(17, 6 + i * 11, 80, 10, x - posX, y - posY)) {
/* 203 */         if ((!getLocationSafely(i + this.selectionOffset).getWriteProtected() || !this.editingExisting) && button == 0) {
/* 204 */           this.selected = i;
/* 205 */           selectionChanged = true;
/*     */         } 
/* 207 */         if ((!getLocationSafely(i + this.selectionOffset).getWriteProtected() || !this.editingExisting) && button == 1) {
/* 208 */           if (this.locations.isEmpty())
/*     */             return; 
/* 210 */           if (!this.player.field_71075_bZ.field_75098_d && this.fuel <= 0) {
/* 211 */             this.player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.teleporterOutOfFuel.txt", new Object[0]));
/*     */           }
/*     */           
/* 214 */           if (this.player.field_71075_bZ.field_75098_d || this.fuel > 0) {
/* 215 */             if (!this.player.field_71075_bZ.field_75098_d) this.fuel--; 
/* 216 */             DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(8, i + this.selectionOffset, false));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 221 */       if (GuiHelper.isInRect(99, 8 + i * 11, 8, 7, x - posX, y - posY)) {
/* 222 */         getLocationSafely(i + this.selectionOffset).setWriteProtected(!getLocationSafely(i + this.selectionOffset).getWriteProtected());
/* 223 */         DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(3, i + this.selectionOffset, getLocationSafely(i + this.selectionOffset).getWriteProtected()));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 228 */     if (selectionChanged)
/* 229 */       DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(4, this.selected, false)); 
/* 230 */     if (offsetChanged) {
/* 231 */       DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(5, this.selectionOffset, false));
/*     */     }
/* 233 */     updateButtons();
/* 234 */     super.func_73864_a(x, y, button);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146286_b(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
/* 239 */     super.func_146286_b(p_146286_1_, p_146286_2_, p_146286_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146274_d() {
/* 244 */     int i = Mouse.getEventDWheel();
/* 245 */     boolean offsetChanged = false;
/* 246 */     boolean selectionChanged = false;
/* 247 */     if (i < 0 && this.selectionOffset < this.maxOffset) {
/* 248 */       this.selectionOffset++;
/* 249 */       offsetChanged = true;
/* 250 */       if (this.selected > 0) {
/* 251 */         this.selected--;
/* 252 */         selectionChanged = true;
/*     */       } 
/* 254 */       updateButtons();
/*     */     } 
/* 256 */     if (i > 0 && this.selectionOffset > 0) {
/* 257 */       this.selectionOffset--;
/* 258 */       offsetChanged = true;
/* 259 */       if (this.selected < 11) {
/* 260 */         this.selected++;
/* 261 */         selectionChanged = true;
/*     */       } 
/* 263 */       updateButtons();
/*     */     } 
/*     */     
/* 266 */     if (selectionChanged)
/* 267 */       DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(4, this.selected, false)); 
/* 268 */     if (offsetChanged) {
/* 269 */       DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(5, this.selectionOffset, false));
/*     */     }
/* 271 */     super.func_146274_d();
/*     */   }
/*     */   
/*     */   private void updateButtons() {
/* 275 */     if (this.locations.size() > 12) { this.maxOffset = this.locations.size() - 12; }
/* 276 */     else { this.maxOffset = 0; }
/* 277 */      if (this.selectionOffset > this.maxOffset) this.selectionOffset = this.maxOffset; 
/* 278 */     if (this.selected > this.locations.size() || this.selected < 0)
/* 279 */       this.selected = Math.max(this.locations.size() - 1, 0); 
/* 280 */     if (this.selected + this.selectionOffset + 1 > this.locations.size()) {
/* 281 */       this.selected = 0;
/* 282 */       this.selectionOffset = 0;
/* 283 */       DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(4, this.selected, false));
/* 284 */       DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(5, this.selectionOffset, false));
/*     */     } 
/* 286 */     if (this.locations.isEmpty() || getLocationSafely(this.selected + this.selectionOffset).getWriteProtected()) {
/* 287 */       ((GuiButton)this.field_146292_n.get(0)).field_146124_l = false;
/* 288 */       ((GuiButton)this.field_146292_n.get(1)).field_146124_l = false;
/* 289 */       ((GuiButton)this.field_146292_n.get(2)).field_146124_l = false;
/*     */     } else {
/* 291 */       ((GuiButton)this.field_146292_n.get(0)).field_146124_l = true;
/* 292 */       ((GuiButton)this.field_146292_n.get(1)).field_146124_l = true;
/* 293 */       ((GuiButton)this.field_146292_n.get(2)).field_146124_l = true;
/*     */     } 
/* 295 */     if (this.editingNew) {
/* 296 */       ((GuiButton)this.field_146292_n.get(4)).field_146124_l = !this.textBeingEdited.func_146179_b().isEmpty();
/* 297 */       ((GuiButton)this.field_146292_n.get(4)).field_146126_j = StatCollector.func_74838_a("button.de.commit.txt");
/*     */     } 
/* 299 */     if (this.editingExisting) {
/* 300 */       ((GuiButton)this.field_146292_n.get(0)).field_146124_l = !this.textBeingEdited.func_146179_b().isEmpty();
/* 301 */       ((GuiButton)this.field_146292_n.get(0)).field_146126_j = StatCollector.func_74838_a("button.de.commit.txt");
/*     */     } 
/* 303 */     if (this.locations.size() >= 100) { ((GuiButton)this.field_146292_n.get(4)).field_146124_l = false; }
/* 304 */     else if (!this.editingNew) { ((GuiButton)this.field_146292_n.get(4)).field_146124_l = true; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/* 310 */     this.field_146292_n.clear();
/* 311 */     getClass(); int posX = (this.field_146294_l - 182) / 2;
/* 312 */     getClass(); int posY = (this.field_146295_m - 141) / 2;
/*     */ 
/*     */ 
/*     */     
/* 316 */     this.field_146292_n.add(new GuiButtonAHeight(0, posX + 112, posY + 45, 66, 12, StatCollector.func_74838_a("button.de.rename.txt")));
/* 317 */     this.field_146292_n.add(new GuiButtonAHeight(1, posX + 112, posY + 58, 66, 12, StatCollector.func_74838_a("button.de.setHere.txt")));
/* 318 */     this.field_146292_n.add(new GuiButtonAHeight(2, posX + 112, posY + 71, 66, 12, StatCollector.func_74838_a("button.de.remove.txt")));
/*     */     
/* 320 */     this.field_146292_n.add(new GuiButtonAHeight(3, posX + 112, posY + 99, 33, 12, StatCollector.func_74838_a("button.de.UP.txt")));
/*     */     
/* 322 */     this.field_146292_n.add(new GuiButtonAHeight(4, posX + 112, posY + 112, 66, 12, StatCollector.func_74838_a("button.de.addNew.txt")));
/* 323 */     this.field_146292_n.add(new GuiButtonAHeight(5, posX + 112, posY + 125, 66, 12, StatCollector.func_74838_a("button.de.addFuel.txt")));
/* 324 */     getClass(); this.field_146292_n.add(new GuiButtonAHeight(6, posX + 182 - 63, posY - 15, 60, 15, StatCollector.func_74838_a("button.de.cancel.txt")));
/*     */     
/* 326 */     this.field_146292_n.add(new GuiButtonAHeight(7, posX + 112 + 34, posY + 99, 33, 12, StatCollector.func_74838_a("button.de.DOWN.txt")));
/* 327 */     ((GuiButton)this.field_146292_n.get(6)).field_146125_m = false;
/*     */     
/* 329 */     getClass(); this.textBeingEdited = new GuiTextField(this.field_146289_q, posX + 3, posY - 14, 182 - 67, 12);
/* 330 */     this.textBeingEdited.func_146193_g(-1);
/* 331 */     this.textBeingEdited.func_146204_h(-1);
/* 332 */     this.textBeingEdited.func_146185_a(true);
/* 333 */     this.textBeingEdited.func_146203_f(40);
/* 334 */     this.textBeingEdited.func_146189_e(false);
/*     */     
/* 336 */     updateButtons();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton button) {
/* 341 */     if (button.field_146127_k == 0 || (button.field_146127_k == 6 && !this.editingNew)) {
/* 342 */       if (button.field_146127_k == 6) {
/* 343 */         this.editingExisting = false;
/* 344 */         ((GuiButton)this.field_146292_n.get(0)).field_146126_j = StatCollector.func_74838_a("button.de.rename.txt");
/* 345 */         ((GuiButton)this.field_146292_n.get(6)).field_146125_m = false;
/* 346 */         this.textBeingEdited.func_146189_e(false);
/* 347 */         ((GuiButton)this.field_146292_n.get(0)).field_146124_l = true;
/*     */         return;
/*     */       } 
/* 350 */       if (!this.editingExisting) {
/* 351 */         this.editingExisting = true;
/* 352 */         this.textBeingEdited.func_146189_e(true);
/* 353 */         this.textBeingEdited.func_146180_a(getLocationSafely(this.selected + this.selectionOffset).getName());
/* 354 */         this.textBeingEdited.func_146199_i(0);
/* 355 */         this.textBeingEdited.func_146195_b(true);
/* 356 */         ((GuiButton)this.field_146292_n.get(6)).field_146125_m = true;
/*     */       }
/* 358 */       else if (!this.textBeingEdited.func_146179_b().isEmpty()) {
/* 359 */         getLocationSafely(this.selected + this.selectionOffset).setName(this.textBeingEdited.func_146179_b());
/* 360 */         Teleporter.TeleportLocation location = new Teleporter.TeleportLocation();
/* 361 */         location.setName(this.textBeingEdited.func_146179_b());
/* 362 */         DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(location, 2, this.selected + this.selectionOffset));
/* 363 */         ((GuiButton)this.field_146292_n.get(0)).field_146126_j = StatCollector.func_74838_a("button.de.rename.txt");
/* 364 */         this.editingExisting = false;
/* 365 */         this.textBeingEdited.func_146189_e(false);
/* 366 */         ((GuiButton)this.field_146292_n.get(6)).field_146125_m = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 371 */     if (button.field_146127_k == 1) {
/* 372 */       Teleporter.TeleportLocation location = new Teleporter.TeleportLocation(this.player.field_70165_t, this.player.field_70163_u - 1.62D, this.player.field_70161_v, this.player.field_71093_bK, this.player.field_70125_A, this.player.field_70177_z, getLocationSafely(this.selected + this.selectionOffset).getName());
/* 373 */       DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(location, 7, this.selected + this.selectionOffset));
/* 374 */       this.locations.set(this.selected + this.selectionOffset, location);
/*     */     } 
/*     */     
/* 377 */     if (button.field_146127_k == 2) {
/* 378 */       DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(1, this.selected + this.selectionOffset, false));
/* 379 */       this.locations.remove(this.selected + this.selectionOffset);
/* 380 */       if (this.selectionOffset > 0) this.selectionOffset--; 
/* 381 */       if (this.selected >= this.locations.size()) this.selected--;
/*     */     
/*     */     } 
/* 384 */     if (button.field_146127_k == 3 || button.field_146127_k == 7) {
/* 385 */       if (button.field_146127_k == 3) {
/* 386 */         if (this.selected > 0) {
/* 387 */           Teleporter.TeleportLocation temp = getLocationSafely(this.selected + this.selectionOffset);
/* 388 */           this.locations.set(this.selected + this.selectionOffset, getLocationSafely(this.selected + this.selectionOffset - 1));
/* 389 */           this.locations.set(this.selected + this.selectionOffset - 1, temp);
/* 390 */           this.selected--;
/*     */         }
/*     */       
/* 393 */       } else if (this.selected < Math.min(11, this.locations.size() - 1)) {
/* 394 */         Teleporter.TeleportLocation temp = getLocationSafely(this.selected + this.selectionOffset);
/* 395 */         this.locations.set(this.selected + this.selectionOffset, getLocationSafely(this.selected + this.selectionOffset + 1));
/* 396 */         this.locations.set(this.selected + this.selectionOffset + 1, temp);
/* 397 */         this.selected++;
/*     */       } 
/*     */ 
/*     */       
/* 401 */       DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(10, this.selected + this.selectionOffset, (button.field_146127_k == 3)));
/*     */     } 
/*     */     
/* 404 */     if (button.field_146127_k == 4 || (button.field_146127_k == 6 && !this.editingExisting)) {
/* 405 */       if (button.field_146127_k == 6) {
/* 406 */         this.editingNew = false;
/* 407 */         ((GuiButton)this.field_146292_n.get(4)).field_146126_j = StatCollector.func_74838_a("button.de.addNew.txt");
/* 408 */         ((GuiButton)this.field_146292_n.get(6)).field_146125_m = false;
/* 409 */         ((GuiButton)this.field_146292_n.get(4)).field_146124_l = true;
/* 410 */         this.textBeingEdited.func_146189_e(false);
/*     */         return;
/*     */       } 
/* 413 */       if (!this.editingNew) {
/* 414 */         this.editingNew = true;
/* 415 */         this.textBeingEdited.func_146189_e(true);
/* 416 */         this.textBeingEdited.func_146180_a("" + (int)this.player.field_70165_t + " " + (int)this.player.field_70163_u + " " + (int)this.player.field_70161_v);
/* 417 */         this.textBeingEdited.func_146199_i(0);
/* 418 */         this.textBeingEdited.func_146195_b(true);
/* 419 */         ((GuiButton)this.field_146292_n.get(6)).field_146125_m = true;
/*     */       }
/* 421 */       else if (!this.textBeingEdited.func_146179_b().isEmpty()) {
/* 422 */         addCurrentLocationToList(this.textBeingEdited.func_146179_b());
/* 423 */         ((GuiButton)this.field_146292_n.get(4)).field_146126_j = StatCollector.func_74838_a("button.de.addNew.txt");
/* 424 */         this.editingNew = false;
/* 425 */         this.textBeingEdited.func_146189_e(false);
/* 426 */         ((GuiButton)this.field_146292_n.get(6)).field_146125_m = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 431 */     if (button.field_146127_k == 5)
/* 432 */       if (InventoryUtils.hasItem(this.player, (IItemMatcher)Tags.Items.ENDER_PEARL))
/* 433 */       { if (!Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54))
/* 434 */         { DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(6, 1, false));
/* 435 */           this.fuel += ConfigHandler.teleporterUsesPerPearl; }
/* 436 */         else if (hasPearls(16))
/* 437 */         { DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(6, 16, false));
/* 438 */           this.fuel += ConfigHandler.teleporterUsesPerPearl * 16; }
/* 439 */         else { this.player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.teleporterOutOfPearls.txt", new Object[0])); }  }
/* 440 */       else { this.player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.teleporterOutOfPearls.txt", new Object[0])); }
/*     */        
/* 442 */     updateButtons();
/*     */   }
/*     */   
/*     */   public boolean hasPearls(int number) {
/* 446 */     int found = 0;
/*     */     
/* 448 */     for (int i = 0; i < this.player.field_71071_by.func_70302_i_(); i++) {
/* 449 */       ItemStack stack = this.player.field_71071_by.func_70301_a(i);
/* 450 */       if (stack != null && Tags.Items.ENDER_PEARL.is(stack)) found += stack.field_77994_a;
/*     */       
/* 452 */       if (found >= number) return true; 
/*     */     } 
/* 454 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73869_a(char key, int keyN) {
/* 459 */     if (this.textBeingEdited.func_146201_a(key, keyN)) {
/* 460 */       if (this.editingNew) {
/* 461 */         ((GuiButton)this.field_146292_n.get(4)).field_146124_l = !this.textBeingEdited.func_146179_b().isEmpty();
/* 462 */         ((GuiButton)this.field_146292_n.get(4)).field_146126_j = StatCollector.func_74838_a("button.de.commit.txt");
/*     */       } 
/* 464 */       if (this.editingExisting) {
/* 465 */         ((GuiButton)this.field_146292_n.get(0)).field_146124_l = !this.textBeingEdited.func_146179_b().isEmpty();
/* 466 */         ((GuiButton)this.field_146292_n.get(0)).field_146126_j = StatCollector.func_74838_a("button.de.commit.txt");
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 471 */     if (keyN == 28 && this.editingNew) func_146284_a(this.field_146292_n.get(4)); 
/* 472 */     if (keyN == 28 && this.editingExisting) func_146284_a(this.field_146292_n.get(0));
/*     */     
/* 474 */     if ((key == 'e' && (!this.editingExisting || !this.editingNew)) || key == '\033') {
/* 475 */       this.field_146297_k.func_147108_a(null);
/* 476 */       this.field_146297_k.func_71381_h();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73876_c() {
/* 482 */     if (this.player.func_71045_bC() == null || !this.player.func_71045_bC().func_77969_a(new ItemStack((Item)ModItems.teleporterMKII)) || this.player.field_70128_L) {
/* 483 */       this.field_146297_k.func_147108_a(null);
/* 484 */       this.field_146297_k.func_71381_h();
/*     */     } 
/*     */     
/* 487 */     if (this.tick % 5 == 0 && !this.locations.isEmpty() && getLocationSafely(this.selected + this.selectionOffset).getDimensionName().equals("") && this.player.func_70694_bm() != null && this.player.func_70694_bm().func_77973_b() == ModItems.teleporterMKII) {
/* 488 */       readDataFromItem(this.player.func_70694_bm());
/*     */     }
/* 490 */     this.tick++;
/* 491 */     if (this.tick >= 10) {
/* 492 */       this.tick = 0;
/* 493 */       this.showFuelLight = !this.showFuelLight;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/* 499 */     return false;
/*     */   }
/*     */   
/*     */   private void readDataFromItem(ItemStack teleporter) {
/* 503 */     this.selected = ItemNBTHelper.getShort(teleporter, "Selection", (short)0);
/* 504 */     this.selectionOffset = ItemNBTHelper.getInteger(teleporter, "SelectionOffset", 0);
/* 505 */     this.fuel = ItemNBTHelper.getInteger(teleporter, "Fuel", 0);
/*     */     
/* 507 */     this.locations.clear();
/*     */     
/* 509 */     NBTTagCompound compound = teleporter.func_77978_p();
/* 510 */     if (compound == null || compound.func_150295_c("Locations", 0) == null)
/* 511 */       return;  NBTTagList list = (NBTTagList)compound.func_74781_a("Locations");
/* 512 */     if (list == null) list = new NBTTagList();
/*     */     
/* 514 */     for (int i = 0; i < list.func_74745_c(); i++) {
/* 515 */       NBTTagCompound tagLocation = list.func_150305_b(i);
/*     */       
/* 517 */       Teleporter.TeleportLocation location = new Teleporter.TeleportLocation();
/* 518 */       location.readFromNBT(tagLocation);
/* 519 */       location.setWriteProtected(tagLocation.func_74767_n("WP"));
/* 520 */       this.locations.add(location);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addCurrentLocationToList(String name) {
/* 525 */     Teleporter.TeleportLocation currentLocation = new Teleporter.TeleportLocation(this.player.field_70165_t, this.player.field_70163_u - 1.62D, this.player.field_70161_v, this.player.field_71093_bK, this.player.field_70125_A, this.player.field_70177_z, name);
/* 526 */     DraconicEvolution.network.sendToServer((IMessage)new TeleporterPacket(currentLocation, 0));
/* 527 */     this.locations.add(currentLocation);
/*     */   }
/*     */   
/*     */   private Teleporter.TeleportLocation getLocationSafely(int index) {
/* 531 */     if (index < this.locations.size() && index >= 0) return this.locations.get(index); 
/* 532 */     return new Teleporter.TeleportLocation(0.0D, 0.0D, 0.0D, 0, 0.0F, 0.0F, EnumChatFormatting.DARK_RED + "[Index Error]");
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\GUITeleporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */