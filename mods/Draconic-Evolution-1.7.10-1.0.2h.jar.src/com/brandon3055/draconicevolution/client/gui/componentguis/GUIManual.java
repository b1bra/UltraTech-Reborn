/*     */ package com.brandon3055.draconicevolution.client.gui.componentguis;
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentBase;
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentButton;
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentCollection;
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentTexturedRect;
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.GUIBase;
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.GUIScrollingBase;
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.draconicevolution.client.gui.guicomponents.ComponentIndexButton;
/*     */ import com.brandon3055.draconicevolution.client.gui.guicomponents.ComponentManualPage;
/*     */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*     */ import com.brandon3055.draconicevolution.common.container.DummyContainer;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiYesNoCallback;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ public class GUIManual extends GUIScrollingBase implements GuiYesNoCallback {
/*  35 */   private static List<ManualPage> pageList = new ArrayList<>();
/*  36 */   private static ManualPage currentPage = null;
/*  37 */   private int previousScale = -1;
/*  38 */   private int worldUpdateIn = -1; private static String lang;
/*     */   public static final String GR_BACKGROUND = "BACKGROUND";
/*     */   
/*     */   public GUIManual() {
/*  42 */     super((Container)new DummyContainer(), 255, 325);
/*  43 */     if (currentPage != null) {
/*  44 */       this.collection.addComponent((ComponentBase)new ComponentManualPage(0, 0, this, currentPage)).setGroup("PAGE");
/*  45 */       this.collection.addComponent((ComponentBase)new ComponentButton(102, 314, 50, 12, 1, (GUIBase)this, StatCollector.func_74838_a("button.de.back.txt")))
/*  46 */         .setGroup("PAGE");
/*  47 */       this.collection.setOnlyGroupEnabled("BACKGROUND");
/*  48 */       this.collection.setGroupEnabled("PAGE", true);
/*     */     } 
/*     */   }
/*     */   public static final String GR_INTRO = "INTRO";
/*     */   public static final String GR_INDEX = "INDEX";
/*     */   public static final String GR_PAGE = "PAGE";
/*     */   
/*     */   public void func_73866_w_() {
/*  56 */     super.func_73866_w_();
/*  57 */     loadPages();
/*  58 */     if (this.previousScale == -1) {
/*  59 */       adjustGuiScale();
/*     */     }
/*  61 */     if (!Minecraft.func_71410_x().func_135016_M().func_135041_c().func_135034_a().equals(lang))
/*  62 */       loadPages(); 
/*     */   }
/*     */   
/*     */   private void adjustGuiScale() {
/*  66 */     this.previousScale = this.field_146297_k.field_71474_y.field_74335_Z;
/*     */     
/*  68 */     if (this.field_146295_m < this.field_147000_g) {
/*  69 */       int x = this.field_146294_l;
/*  70 */       int y = this.field_146295_m;
/*     */       
/*  72 */       if (this.field_146297_k.field_71474_y.field_74335_Z == 0) {
/*  73 */         this.field_146297_k.field_71474_y.field_74335_Z = 3;
/*  74 */         ScaledResolution scaledresolution = new ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
/*  75 */         x = scaledresolution.func_78326_a();
/*  76 */         y = scaledresolution.func_78328_b();
/*     */       } 
/*     */       
/*  79 */       if (y < this.field_147000_g && this.field_146297_k.field_71474_y.field_74335_Z == 3) {
/*  80 */         this.field_146297_k.field_71474_y.field_74335_Z = 2;
/*  81 */         ScaledResolution scaledresolution = new ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
/*  82 */         x = scaledresolution.func_78326_a();
/*  83 */         y = scaledresolution.func_78328_b();
/*     */       } 
/*     */       
/*  86 */       if (y < this.field_147000_g && this.field_146297_k.field_71474_y.field_74335_Z == 2) {
/*  87 */         this.field_146297_k.field_71474_y.field_74335_Z = 1;
/*  88 */         ScaledResolution scaledresolution = new ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
/*  89 */         x = scaledresolution.func_78326_a();
/*  90 */         y = scaledresolution.func_78328_b();
/*     */       } 
/*     */       
/*  93 */       this.field_146295_m = y;
/*  94 */       this.field_146294_l = x;
/*  95 */       this.field_147003_i = (this.field_146294_l - this.field_146999_f) / 2;
/*  96 */       this.field_147009_r = (this.field_146295_m - this.field_147000_g) / 2;
/*  97 */       this.worldUpdateIn = 5;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146281_b() {
/* 103 */     super.func_146281_b();
/* 104 */     this.field_146297_k.field_71474_y.field_74335_Z = this.previousScale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ComponentCollection assembleComponents() {
/* 114 */     this.collection = new ComponentCollection(0, 0, this.field_146999_f, this.field_147000_g, (GUIBase)this);
/*     */     
/* 116 */     this.collection.addComponent((ComponentBase)new ComponentTexturedRect(0, 0, 255, 255, ResourceHandler.getResource("textures/gui/manualTop.png")))
/* 117 */       .setGroup("BACKGROUND");
/* 118 */     this.collection.addComponent((ComponentBase)new ComponentTexturedRect(0, 255, 255, 69, ResourceHandler.getResource("textures/gui/manualBottom.png")))
/* 119 */       .setGroup("BACKGROUND");
/*     */     
/* 121 */     this.collection.addComponent((ComponentBase)new ComponentTexturedRect(7, 100, 0, 0, 255, 255, ResourceHandler.getResource("textures/gui/images/debanner.png"), true))
/* 122 */       .setGroup("INTRO")
/* 123 */       .setName("BANNER");
/*     */     
/* 125 */     this.collection.addComponent((ComponentBase)new ComponentButton(75, 260, 100, 20, 0, (GUIBase)this, StatCollector.func_74838_a("info.de.manual.indexButton.txt"), StatCollector.func_74838_a("info.de.manual.indexButtonTip.txt")))
/* 126 */       .setGroup("INTRO");
/*     */     
/* 128 */     for (int i = 0; i < pageList.size(); i++) {
/* 129 */       this.collection.addComponent((ComponentBase)new ComponentIndexButton(20, 20 + i * 20, this, pageList.get(i)))
/* 130 */         .setGroup("INDEX")
/* 131 */         .setName("INDEX_BUTTON_" + i);
/* 132 */       this.pageLength += 20;
/*     */     } 
/*     */     
/* 135 */     this.scrollLimit = this.pageLength - 285;
/*     */     
/* 137 */     this.collection.setOnlyGroupEnabled("BACKGROUND");
/* 138 */     this.collection.setGroupEnabled("INTRO", true);
/* 139 */     return this.collection;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleScrollInput(int direction) {
/* 144 */     if (this.collection.getComponent("INDEX_BUTTON_0") != null && this.collection.getComponent("INDEX_BUTTON_0")
/* 145 */       .isEnabled()) {
/*     */       
/* 147 */       if (currentPage != null)
/*     */         return; 
/* 149 */       this.scrollOffset += direction * (InfoHelper.isShiftKeyDown() ? 30 : 10);
/* 150 */       if (this.scrollOffset < 0)
/* 151 */         this.scrollOffset = 0; 
/* 152 */       if (this.scrollOffset > this.pageLength - this.field_147000_g + 40)
/* 153 */         this.scrollOffset = this.pageLength - this.field_147000_g + 40; 
/* 154 */       if (this.pageLength + 40 <= this.field_147000_g) {
/* 155 */         this.scrollOffset = 0;
/*     */       }
/* 157 */       this.barPosition = (int)(this.scrollOffset / this.scrollLimit * 247.0D);
/* 158 */     } else if (this.collection.getComponent("OPEN_PAGE") != null && this.collection.getComponent("OPEN_PAGE")
/* 159 */       .isEnabled()) {
/* 160 */       ComponentManualPage page = (ComponentManualPage)this.collection.getComponent("OPEN_PAGE");
/* 161 */       this.barPosition = (int)(page.scrollOffset / page.scrollLimit * 247.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float par3) {
/* 168 */     super.func_73863_a(mouseX, mouseY, par3);
/* 169 */     int posX = (this.field_146294_l - this.field_146999_f) / 2;
/* 170 */     int posY = (this.field_146295_m - this.field_147000_g) / 2;
/* 171 */     if (this.collection.getComponent("BANNER") != null && this.collection.getComponent("BANNER").isEnabled()) {
/* 172 */       this.field_146289_q.func_78279_b(StatCollector.func_74838_a("info.de.manual.introTxt.txt"), posX + 20, posY + 190, 150, 0);
/*     */     }
/* 174 */     if (this.collection.getComponent("BANNER") != null && this.collection.getComponent("BANNER").isEnabled())
/*     */       return; 
/* 176 */     ResourceHandler.bindResource("textures/gui/Widgets.png");
/* 177 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 178 */     func_73729_b(this.field_147003_i, this.field_147009_r, 118, 20, 17, 17);
/*     */     
/* 180 */     if (mouseX - posX >= 0 && mouseX - posX <= 17 && mouseY - posY >= 0 && mouseY - posY <= 17) {
/* 181 */       List<String> list = new ArrayList<>();
/* 182 */       list.add(EnumChatFormatting.GREEN + StatCollector.func_74838_a("info.de.manual.nav1.txt"));
/* 183 */       list.add("");
/* 184 */       list.add("-" + StatCollector.func_74838_a("info.de.manual.nav2.txt"));
/* 185 */       list.add("-" + StatCollector.func_74838_a("info.de.manual.nav3.txt"));
/* 186 */       drawHoveringText(list, posX + 8, posY + 32, this.field_146289_q);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73876_c() {
/* 192 */     super.func_73876_c();
/*     */     
/* 194 */     this
/* 195 */       .disableScrollBar = (this.collection.getComponent("BANNER") != null && this.collection.getComponent("BANNER").isEnabled());
/*     */     
/* 197 */     if (!this.scrollPressed && this.collection.getComponent("INDEX_BUTTON_0") != null && this.collection.getComponent("INDEX_BUTTON_0")
/* 198 */       .isEnabled()) {
/* 199 */       this.barPosition = (int)(this.scrollOffset / this.scrollLimit * 247.0D);
/* 200 */     } else if (!this.scrollPressed && this.collection.getComponent("OPEN_PAGE") != null && this.collection.getComponent("OPEN_PAGE")
/* 201 */       .isEnabled()) {
/* 202 */       ComponentManualPage page = (ComponentManualPage)this.collection.getComponent("OPEN_PAGE");
/* 203 */       this.barPosition = (int)(page.scrollOffset / page.scrollLimit * 247.0D);
/*     */     } 
/*     */     
/* 206 */     if (this.worldUpdateIn > -1)
/* 207 */       this.worldUpdateIn--; 
/* 208 */     if (this.worldUpdateIn == 0) {
/* 209 */       this.collection.setWorldAndResolution(this.field_146297_k, this.field_146294_l, this.field_146295_m);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int mouseX, int mouseY) {
/* 215 */     GL11.glPushMatrix();
/* 216 */     GL11.glEnable(3042);
/*     */     
/* 218 */     ResourceHandler.bindResource("textures/gui/manualBottom.png");
/* 219 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 220 */     func_73729_b(0, this.field_147000_g - 34, 0, 69, 256, 34);
/* 221 */     func_73729_b(0, 0, 0, 103, 256, 34);
/*     */     
/* 223 */     GL11.glDisable(3042);
/* 224 */     GL11.glPopMatrix();
/*     */     
/* 226 */     super.func_146979_b(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int mouseX, int mouseY) {
/* 231 */     super.func_146976_a(f, mouseX, mouseY);
/* 232 */     if (this.disableScrollBar) {
/*     */       return;
/*     */     }
/* 235 */     ResourceHandler.bindResource("textures/gui/manualBottom.png");
/* 236 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 237 */     func_73729_b(this.field_147003_i - 16, this.field_147009_r + 10, 0, 146, 15, 100);
/* 238 */     func_73729_b(this.field_147003_i - 16, this.field_147009_r + this.field_147000_g - 110, 0, 156, 15, 100);
/* 239 */     func_73729_b(this.field_147003_i - 16, this.field_147009_r + 110, 0, 156, 15, 90);
/* 240 */     func_73729_b(this.field_147003_i - 16, this.field_147009_r + 140, 0, 156, 15, 90);
/*     */     
/* 242 */     func_73729_b(this.field_147003_i - 15, this.field_147009_r + 20 + this.barPosition, 15, 218, 13, 38);
/*     */   }
/*     */ 
/*     */   
/*     */   public void buttonClicked(int id, int button) {
/* 247 */     super.buttonClicked(id, button);
/* 248 */     if (id == 0) {
/* 249 */       this.collection.setOnlyGroupEnabled("BACKGROUND");
/* 250 */       this.collection.setGroupEnabled("INDEX", true);
/* 251 */     } else if (id == 1) {
/* 252 */       currentPage = null;
/* 253 */       this.collection.setOnlyGroupEnabled("BACKGROUND");
/* 254 */       this.collection.setGroupEnabled("INDEX", true);
/* 255 */       this.collection.schedulRemoval("PAGE");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char par1, int par2) {
/* 261 */     super.func_73869_a(par1, par2);
/* 262 */     if (14 == par2) {
/* 263 */       if (currentPage != null) {
/* 264 */         currentPage = null;
/* 265 */         this.collection.setOnlyGroupEnabled("BACKGROUND");
/* 266 */         this.collection.setGroupEnabled("INDEX", true);
/* 267 */         this.collection.schedulRemoval("PAGE");
/*     */       } else {
/* 269 */         this.collection.setOnlyGroupEnabled("BACKGROUND");
/* 270 */         this.collection.setGroupEnabled("INTRO", true);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void loadPages() {
/* 276 */     lang = Minecraft.func_71410_x().func_135016_M().func_135041_c().func_135034_a();
/*     */     
/* 278 */     ResourceLocation rsLocation = new ResourceLocation("draconicevolution", "manual/manual-" + lang + ".json");
/* 279 */     IResource resource = null;
/*     */     
/*     */     try {
/* 282 */       resource = Minecraft.func_71410_x().func_110442_L().func_110536_a(rsLocation);
/* 283 */     } catch (IOException e) {
/* 284 */       LogHelper.warn("##################################################################################");
/* 285 */       LogHelper.warn("");
/* 286 */       LogHelper.warn("Info Tablet language localisation is not available for the selected language: " + lang);
/* 287 */       LogHelper.warn("The default language (en_US) will be loaded instead");
/* 288 */       LogHelper.warn("");
/* 289 */       LogHelper.warn("##################################################################################");
/*     */       
/* 291 */       rsLocation = new ResourceLocation("draconicevolution", "manual/manual-en_US.json");
/*     */       try {
/* 293 */         resource = Minecraft.func_71410_x().func_110442_L().func_110536_a(rsLocation);
/* 294 */       } catch (IOException e1) {
/* 295 */         LogHelper.error("Well that didn't work... ");
/* 296 */         e1.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 300 */     if (resource == null) {
/* 301 */       LogHelper.error("Something went wrong while loading the Info Tablet json file");
/*     */       
/*     */       return;
/*     */     } 
/* 305 */     try (JsonReader reader = new JsonReader(new InputStreamReader(resource.func_110527_b(), StandardCharsets.UTF_8))) {
/* 306 */       reader.beginArray();
/*     */ 
/*     */       
/* 309 */       while (reader.hasNext()) {
/*     */         
/* 311 */         String name, nameL = null;
/* 312 */         int meta = 0;
/* 313 */         List<String> images = new ArrayList<>();
/* 314 */         List<String> content = new ArrayList<>();
/*     */         
/* 316 */         reader.beginObject();
/*     */ 
/*     */         
/* 319 */         String s = reader.nextName();
/* 320 */         if (s.equals("name")) {
/* 321 */           name = reader.nextString();
/*     */         } else {
/* 323 */           throw new IOException("Error reading manual.json (invalid name in place of \"name\" [Found:\"" + s + "\"])");
/*     */         } 
/*     */         
/* 326 */         s = reader.nextName();
/* 327 */         if (s.equals("nameL")) {
/* 328 */           nameL = reader.nextString();
/* 329 */           s = reader.nextName();
/*     */         } 
/* 331 */         if (s.equals("meta")) {
/* 332 */           meta = reader.nextInt();
/* 333 */           s = reader.nextName();
/*     */         } 
/*     */         
/* 336 */         if (s.equals("images")) {
/* 337 */           reader.beginArray();
/* 338 */           while (reader.hasNext()) {
/* 339 */             images.add(reader.nextString());
/*     */           }
/* 341 */           reader.endArray();
/*     */         } else {
/* 343 */           throw new IOException("Error reading manual.json (invalid name in place of \"images\" [Found:\"" + s + "\"])");
/*     */         } 
/*     */         
/* 346 */         s = reader.nextName();
/* 347 */         if (s.equals("content")) {
/* 348 */           reader.beginArray();
/* 349 */           while (reader.hasNext()) {
/* 350 */             content.add(reader.nextString());
/*     */           }
/* 352 */           reader.endArray();
/*     */         } else {
/* 354 */           throw new IOException("Error reading manual.json (invalid name in place of \"content\" [Found:\"" + s + "\"])");
/*     */         } 
/* 356 */         reader.endObject();
/*     */         
/* 358 */         if (isValidPage(name)) {
/* 359 */           pageList.add(new ManualPage(name, images.<String>toArray(new String[0]), content.<String>toArray(new String[0]), nameL, meta));
/*     */         }
/*     */       } 
/* 362 */       reader.endArray();
/*     */     }
/* 364 */     catch (IOException e) {
/* 365 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int x, int y, int button) {
/* 371 */     super.func_73864_a(x, y, button);
/* 372 */     if (this.buttonPressed) {
/*     */       return;
/*     */     }
/* 375 */     for (ComponentBase c : this.collection.getComponents()) {
/* 376 */       if (c instanceof ComponentIndexButton && c.isEnabled() && ((ComponentIndexButton)c).isOnScreen() && c.isMouseOver(x - this.field_147003_i, y - this.field_147009_r)) {
/* 377 */         Minecraft.func_71410_x()
/* 378 */           .func_147118_V()
/* 379 */           .func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
/* 380 */         currentPage = ((ComponentIndexButton)c).getPage();
/* 381 */         this.collection.addComponent((ComponentBase)new ComponentManualPage(0, 0, this, currentPage))
/* 382 */           .setGroup("PAGE")
/* 383 */           .setName("OPEN_PAGE");
/* 384 */         this.collection.addComponent((ComponentBase)new ComponentButton(102, 314, 50, 12, 1, (GUIBase)this, StatCollector.func_74838_a("button.de.back.txt")))
/* 385 */           .setGroup("PAGE");
/* 386 */         this.collection.setOnlyGroupEnabled("BACKGROUND");
/* 387 */         this.collection.setGroupEnabled("PAGE", true);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void barMoved(double position) {
/* 395 */     if (this.collection.getComponent("INDEX_BUTTON_0") != null && this.collection.getComponent("INDEX_BUTTON_0")
/* 396 */       .isEnabled()) {
/* 397 */       this.scrollOffset = (int)(position * this.scrollLimit);
/* 398 */     } else if (this.collection.getComponent("OPEN_PAGE") != null && this.collection.getComponent("OPEN_PAGE")
/* 399 */       .isEnabled()) {
/* 400 */       ComponentManualPage page = (ComponentManualPage)this.collection.getComponent("OPEN_PAGE");
/* 401 */       if (page.scrollLimit < 0)
/*     */         return; 
/* 403 */       page.scrollOffset = (int)(position * page.scrollLimit);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isValidPage(String name) {
/* 408 */     if (name.contains("info."))
/* 409 */       return true; 
/* 410 */     return !ConfigHandler.disabledNamesList.contains(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73878_a(boolean confirmed, int id) {
/* 415 */     this.field_146297_k.func_147108_a((GuiScreen)this);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\componentguis\GUIManual.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */