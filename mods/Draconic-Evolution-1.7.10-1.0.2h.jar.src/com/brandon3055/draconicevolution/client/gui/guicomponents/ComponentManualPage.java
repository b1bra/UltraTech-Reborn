/*     */ package com.brandon3055.draconicevolution.client.gui.guicomponents;
/*     */ 
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentScrollingBase;
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.GUIScrollingBase;
/*     */ import com.brandon3055.brandonscore.client.utills.GuiHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.client.gui.componentguis.ManualPage;
/*     */ import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
/*     */ import com.brandon3055.draconicevolution.client.utill.CustomResourceLocation;
/*     */ import com.gamerforea.client.util.TessellatorDebuggerUtils;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.item.crafting.ShapedRecipes;
/*     */ import net.minecraft.item.crafting.ShapelessRecipes;
/*     */ import net.minecraftforge.oredict.ShapedOreRecipe;
/*     */ import net.minecraftforge.oredict.ShapelessOreRecipe;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ public class ComponentManualPage extends ComponentScrollingBase {
/*     */   public ManualPage page;
/*  32 */   public List<ContentComponent> contentList = new ArrayList<>();
/*  33 */   private int pageLength = 0;
/*  34 */   ItemStack stack = null;
/*  35 */   private int slideTick = 0;
/*     */   
/*  37 */   public int scrollOffset = 0;
/*  38 */   public int scrollLimit = 0;
/*     */   
/*     */   public ComponentManualPage(int x, int y, GUIScrollingBase gui, ManualPage page) {
/*  41 */     super(x, y, gui);
/*  42 */     this.page = page;
/*  43 */     this.pageLength = 25;
/*  44 */     this.stack = Utills.getStackFromName(page.name, page.meta);
/*  45 */     ContentComponent title = new ContentComponent("title." + page.getLocalizedName(), this.pageLength, this);
/*  46 */     this.pageLength += title.getHeight();
/*  47 */     this.contentList.add(title);
/*  48 */     for (String s : page.content) {
/*  49 */       ContentComponent c = new ContentComponent(s, this.pageLength, this);
/*  50 */       this.pageLength += c.getHeight();
/*  51 */       this.contentList.add(c);
/*     */     } 
/*  53 */     this.pageLength += 25;
/*     */     
/*  55 */     this.scrollLimit = this.pageLength - 325;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleScrollInput(int direction) {
/*  61 */     this.scrollOffset += direction * (InfoHelper.isShiftKeyDown() ? 30 : 10);
/*  62 */     if (this.scrollOffset < 0) this.scrollOffset = 0; 
/*  63 */     if (this.scrollOffset > this.pageLength - getHeight())
/*  64 */       this.scrollOffset = this.pageLength - getHeight(); 
/*  65 */     if (this.pageLength <= getHeight()) this.scrollOffset = 0;
/*     */   
/*     */   }
/*     */   
/*     */   public int getWidth() {
/*  70 */     return this.gui.getXSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  75 */     return this.gui.getYSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderBackground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/*  81 */     for (ContentComponent c : this.contentList) {
/*  82 */       c.render(mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderFinal(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderForground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  97 */     super.updateScreen();
/*  98 */     if (!InfoHelper.isShiftKeyDown()) this.slideTick++;
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ContentComponent
/*     */   {
/*     */     private String content;
/*     */     
/*     */     private int type;
/*     */     public int yPos;
/*     */     private ComponentManualPage page;
/*     */     private String[] textLines;
/* 111 */     private List<IRecipe> recipes = new ArrayList<>();
/* 112 */     private List<ItemStack> smeltingRecipes = new ArrayList<>();
/*     */     private ItemStack result;
/*     */     private boolean isTitle = false;
/*     */     private boolean slide = false;
/* 116 */     private List<String> slides = new ArrayList<>();
/*     */     
/*     */     public ContentComponent(String content, int y, ComponentManualPage page) {
/* 119 */       this.content = content;
/* 120 */       this.yPos = y;
/* 121 */       this.page = page;
/* 122 */       init();
/*     */     }
/*     */     
/*     */     public void init() {
/* 126 */       this.isTitle = (this.content != null && this.content.contains("title."));
/*     */       
/* 128 */       this.type = this.content.contains(".png") ? 1 : (this.content.contains("[c]") ? 2 : 0);
/*     */       
/* 130 */       this.slide = (this.type == 1 && this.content.contains("[>]"));
/* 131 */       if (this.slide) {
/* 132 */         String s = this.content;
/* 133 */         while (s.contains("[>]")) {
/* 134 */           this.slides.add(s.substring(0, s.indexOf("[>]")));
/* 135 */           s = s.substring(s.indexOf("[>]") + 3);
/*     */         } 
/* 137 */         this.slides.add(s);
/*     */       } 
/*     */       
/* 140 */       if (this.type == 0) {
/* 141 */         List<String> l = this.page.fontRendererObj.func_78271_c(this.content, 220);
/* 142 */         List<String> sl = new ArrayList<>();
/* 143 */         for (String st : l) {
/* 144 */           String s = st;
/* 145 */           while (s.contains("\\n")) {
/* 146 */             String s2 = s.substring(0, s.indexOf("\\n"));
/* 147 */             s = s.substring(s.indexOf("\\n") + 2);
/* 148 */             sl.add(s2);
/*     */           } 
/* 150 */           sl.add(s);
/*     */         } 
/* 152 */         this.textLines = sl.<String>toArray(new String[0]);
/* 153 */       } else if (this.type == 2) {
/* 154 */         String s = this.content.substring(this.content.indexOf("[c]") + 3);
/* 155 */         String name = s.substring(0, s.lastIndexOf(':'));
/* 156 */         ItemStack stack = Utills.getStackFromName(name, Integer.parseInt(s.substring(s.lastIndexOf(':') + 1)));
/* 157 */         this.result = stack;
/*     */         
/* 159 */         if (stack != null) {
/*     */           
/* 161 */           for (IRecipe recipe : CraftingManager.func_77594_a().func_77592_b()) {
/* 162 */             if (recipe == null)
/*     */               continue; 
/* 164 */             ItemStack result = recipe.func_77571_b();
/* 165 */             if (result == null || !result.func_77969_a(stack))
/*     */               continue; 
/* 167 */             Object[] input = getRecipeInput(recipe);
/* 168 */             if (input == null)
/*     */               continue; 
/* 170 */             this.recipes.add(recipe);
/*     */           } 
/*     */           
/* 173 */           Iterator<Map.Entry> iterator = FurnaceRecipes.func_77602_a().func_77599_b().entrySet().iterator();
/*     */ 
/*     */           
/* 176 */           while (iterator.hasNext()) {
/* 177 */             Map.Entry entry = iterator.next();
/* 178 */             if (entry.getKey() instanceof ItemStack && ((ItemStack)entry.getValue()).func_77969_a(stack))
/* 179 */               this.smeltingRecipes.add((ItemStack)entry.getKey()); 
/*     */           } 
/*     */         } 
/*     */       } else {
/* 183 */         this.textLines = null;
/*     */       } 
/*     */     }
/*     */     public int getHeight() {
/* 187 */       if (this.type == 1) {
/* 188 */         String filePath = this.slide ? this.slides.get(0) : this.content;
/* 189 */         CustomResourceLocation texture = ResourceHandler.getManualImage(FilenameUtils.getName(filePath));
/*     */         
/* 191 */         if (texture != null) {
/* 192 */           return (int)(texture.getHeight() / texture.getWidth() * 220.0D) + 5;
/*     */         }
/* 194 */       } else if (this.type == 2) {
/* 195 */         return (this.recipes.size() + this.smeltingRecipes.size()) * 57;
/*     */       } 
/*     */       
/* 198 */       return ((this.textLines != null) ? (this.textLines.length * 10) : 10) + 5 + ((this.isTitle && (this.page.page.name.contains("tile.") || this.page.page.name.contains("item."))) ? 18 : 0);
/*     */     }
/*     */     
/*     */     public void render(int mouseX, int mouseY) {
/* 202 */       if (this.type == 1) {
/* 203 */         renderImage();
/* 204 */       } else if (this.type == 2) {
/* 205 */         renderCrafting(mouseX, mouseY);
/* 206 */       } else if (this.isTitle) {
/* 207 */         int y = this.yPos - this.page.scrollOffset;
/* 208 */         if (y > 310 || y < 5)
/* 209 */           return;  this.page.func_73732_a(this.page.fontRendererObj, this.content.substring(this.content.indexOf("title.") + 6), 128, y, 65535);
/*     */         
/* 211 */         if ((this.page.page.name.contains("tile.") || this.page.page.name.contains("item.")) && this.page.stack != null) {
/* 212 */           ResourceHandler.bindResource("textures/gui/Widgets.png");
/* 213 */           GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
/* 214 */           this.page.func_73729_b(119, y + 11, 138, 0, 18, 18);
/* 215 */           this.page.drawItemStack(this.page.stack, 120, 12 + y, "");
/*     */         } 
/* 217 */       } else if (this.textLines != null) {
/* 218 */         for (int i = 0; i < this.textLines.length; i++) {
/* 219 */           int y = this.yPos + i * 10 - this.page.scrollOffset;
/* 220 */           if (y <= 310 && y >= 5) {
/* 221 */             this.page.fontRendererObj.func_78276_b(this.textLines[i], 20, y, 0);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void renderImage() {
/*     */       CustomResourceLocation image;
/* 229 */       if (this.slide) {
/* 230 */         int index = this.page.slideTick / 20 % this.slides.size();
/* 231 */         image = (this.slides.size() > index) ? ResourceHandler.getManualImage(FilenameUtils.getName(this.slides.get(index))) : null;
/*     */       } else {
/* 233 */         image = ResourceHandler.getManualImage(FilenameUtils.getName(this.content));
/*     */       } 
/*     */       
/* 236 */       if (image != null) {
/*     */         
/* 238 */         image.bind();
/* 239 */         Tessellator tess = Tessellator.field_78398_a;
/*     */         
/* 241 */         double ySize = image.getHeight() / image.getWidth() * 220.0D;
/*     */         
/* 243 */         double topS = 0.0D;
/* 244 */         if (this.yPos - this.page.scrollOffset < 0) {
/* 245 */           topS = (this.yPos - 5 - this.page.scrollOffset);
/*     */         }
/* 247 */         double btmS = 0.0D;
/* 248 */         if ((this.yPos - this.page.scrollOffset) + ySize > 310.0D) {
/* 249 */           btmS = (this.yPos - 5 - this.page.scrollOffset - 310) + ySize;
/*     */         }
/*     */         
/* 252 */         double xmin = 17.5D;
/* 253 */         double xmax = 237.5D;
/* 254 */         double ymin = (this.yPos - this.page.scrollOffset) - topS;
/* 255 */         double ymax = this.yPos + ySize - this.page.scrollOffset - btmS;
/*     */         
/* 257 */         double vmin = Math.max(0.0D, topS / -ySize);
/* 258 */         double vmax = Math.min(1.0D, 1.0D - btmS / ySize);
/*     */         
/* 260 */         tess.func_78382_b(); TessellatorDebuggerUtils.onCall("com/brandon3055/draconicevolution/client/gui/guicomponents/ComponentManualPage$ContentComponent/renderImage()V");
/* 261 */         tess.func_78369_a(1.0F, 1.0F, 1.0F, 1.0F);
/*     */         
/* 263 */         tess.func_78374_a(xmin, ymin, 0.0D, 0.0D, vmin);
/* 264 */         tess.func_78374_a(xmin, ymax, 0.0D, 0.0D, vmax);
/* 265 */         tess.func_78374_a(xmax, ymax, 0.0D, 1.0D, vmax);
/* 266 */         tess.func_78374_a(xmax, ymin, 0.0D, 1.0D, vmin);
/*     */         
/* 268 */         tess.func_78381_a();
/*     */         
/* 270 */         if (this.slide && (int)ymax - 10 < 305 && (int)ymax - 10 > 0 && !InfoHelper.isShiftKeyDown())
/* 271 */           this.page.fontRendererObj.func_78276_b(this.page.ttl("info.de.shiftToPause.txt"), 20, (int)ymax - 8, 16711680); 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void renderCrafting(int mouseX, int mouseY) {
/* 276 */       if (this.result == null)
/* 277 */         return;  ResourceHandler.bindResource("textures/gui/Widgets.png");
/* 278 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 279 */       int posX = 70;
/* 280 */       int posY = this.yPos - this.page.scrollOffset;
/*     */       
/* 282 */       for (IRecipe recipe : this.recipes) {
/* 283 */         int index = this.recipes.indexOf(recipe);
/*     */         
/* 285 */         int yDown = index * 57;
/* 286 */         for (int i = 0; i < 9; i++) {
/* 287 */           int x = i % 3 * 18;
/* 288 */           int y = i / 3 * 18;
/*     */           
/* 290 */           if (y + yDown + posY >= 0 && y + yDown + posY <= 305) {
/* 291 */             this.page.func_73729_b(x + posX, y + yDown + posY, 138, 0, 18, 18);
/* 292 */             ItemStack stack = ((getRecipeInput(recipe)).length > i && getRecipeInput(recipe)[i] instanceof ItemStack) ? (ItemStack)getRecipeInput(recipe)[i] : (((getRecipeInput(recipe)).length > i && getRecipeInput(recipe)[i] instanceof ArrayList) ? ((ArrayList<ItemStack>)getRecipeInput(recipe)[i]).get(0) : null);
/*     */             
/* 294 */             if (stack != null) {
/* 295 */               if (stack.func_77960_j() == 32767) stack.func_77964_b(0); 
/* 296 */               this.page.drawItemStack(stack, 1 + x + posX, 1 + y + yDown + posY, "");
/*     */             } 
/*     */           } 
/*     */         } 
/* 300 */         if (14 + yDown + posY > 0 && 14 + yDown + posY < 300)
/* 301 */           this.page.func_73729_b(90 + posX, 14 + yDown + posY, 156, 0, 26, 26); 
/* 302 */         if (19 + yDown + posY > 0 && 19 + yDown + posY < 305) {
/* 303 */           this.page.func_73729_b(61 + posX, 19 + yDown + posY, 204, 0, 22, 15);
/* 304 */           if (recipe.func_77571_b() != null) {
/* 305 */             this.page.drawItemStack(recipe.func_77571_b(), 95 + posX, 19 + yDown + posY, "");
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 310 */       for (IRecipe recipe : this.recipes) {
/* 311 */         int index = this.recipes.indexOf(recipe);
/*     */         
/* 313 */         int yDown = index * 57;
/*     */         
/* 315 */         if (19 + yDown + posY > 0 && 19 + yDown + posY < 305 && 
/* 316 */           GuiHelper.isInRect(95 + posX, 19 + yDown + posY, 18, 18, mouseX, mouseY)) {
/* 317 */           GL11.glPushAttrib(1048575);
/* 318 */           if (recipe.func_77571_b() != null)
/* 319 */             this.page.renderToolTip(recipe.func_77571_b(), mouseX, mouseY); 
/* 320 */           ResourceHandler.bindResource("textures/gui/Widgets.png");
/* 321 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 322 */           GL11.glPopAttrib();
/*     */         } 
/*     */ 
/*     */         
/* 326 */         for (int i = 0; i < 9; i++) {
/* 327 */           int x = i % 3 * 18;
/* 328 */           int y = i / 3 * 18;
/*     */           
/* 330 */           if (y + yDown + posY >= 0 && y + yDown + posY <= 305) {
/* 331 */             ItemStack stack = ((getRecipeInput(recipe)).length > i && getRecipeInput(recipe)[i] instanceof ItemStack) ? (ItemStack)getRecipeInput(recipe)[i] : (((getRecipeInput(recipe)).length > i && getRecipeInput(recipe)[i] instanceof ArrayList) ? ((ArrayList<ItemStack>)getRecipeInput(recipe)[i]).get(0) : null);
/*     */             
/* 333 */             if (stack != null && 
/* 334 */               GuiHelper.isInRect(1 + x + posX, 1 + y + yDown + posY, 18, 18, mouseX, mouseY)) {
/* 335 */               GL11.glPushAttrib(1048575);
/* 336 */               this.page.renderToolTip(stack, mouseX, mouseY);
/* 337 */               ResourceHandler.bindResource("textures/gui/Widgets.png");
/* 338 */               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 339 */               GL11.glPopAttrib();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 345 */       for (ItemStack recipe : this.smeltingRecipes) {
/* 346 */         int index = this.smeltingRecipes.indexOf(recipe);
/* 347 */         int yDown = (this.recipes.size() + index) * 57;
/*     */         
/* 349 */         if (yDown + posY > 0 && yDown + posY < 305) {
/* 350 */           this.page.func_73729_b(posX + 18, yDown + posY, 138, 0, 18, 18);
/* 351 */           this.page.drawItemStack(recipe, posX + 19, yDown + posY + 1, "");
/*     */         } 
/* 353 */         if (21 + yDown + posY > 0 && 21 + yDown + posY < 305)
/* 354 */           this.page.func_73729_b(posX + 18, yDown + posY + 21, 238, 0, 18, 15); 
/* 355 */         if (36 + yDown + posY > 0 && 36 + yDown + posY < 305)
/* 356 */           this.page.func_73729_b(posX + 18, yDown + posY + 36, 238, 15, 18, 33); 
/* 357 */         if (14 + yDown + posY > 0 && 14 + yDown + posY < 300) {
/* 358 */           this.page.func_73729_b(90 + posX, 14 + yDown + posY, 156, 0, 26, 26);
/* 359 */           this.page.drawItemStack(this.result, 95 + posX, 19 + yDown + posY, "");
/*     */         } 
/* 361 */         if (19 + yDown + posY > 0 && 19 + yDown + posY < 305) {
/* 362 */           this.page.func_73729_b(50 + posX, 19 + yDown + posY, 204, 0, 22, 15);
/*     */         }
/* 364 */         if (yDown + posY > 0 && yDown + posY < 305 && 
/* 365 */           GuiHelper.isInRect(posX + 19, yDown + posY + 1, 18, 18, mouseX, mouseY)) {
/* 366 */           GL11.glPushAttrib(1048575);
/* 367 */           this.page.renderToolTip(recipe, mouseX, mouseY);
/* 368 */           ResourceHandler.bindResource("textures/gui/Widgets.png");
/* 369 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 370 */           GL11.glPopAttrib();
/*     */         } 
/*     */ 
/*     */         
/* 374 */         if (14 + yDown + posY > 0 && 14 + yDown + posY < 305 && 
/* 375 */           GuiHelper.isInRect(95 + posX, 19 + yDown + posY, 18, 18, mouseX, mouseY)) {
/* 376 */           GL11.glPushAttrib(1048575);
/* 377 */           this.page.renderToolTip(this.result, mouseX, mouseY);
/* 378 */           ResourceHandler.bindResource("textures/gui/Widgets.png");
/* 379 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 380 */           GL11.glPopAttrib();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Object[] getRecipeInput(IRecipe recipe) {
/* 388 */       if (recipe instanceof ShapelessOreRecipe) return ((ShapelessOreRecipe)recipe).getInput().toArray(); 
/* 389 */       if (recipe instanceof ShapedOreRecipe) return getShapedOreRecipe((ShapedOreRecipe)recipe); 
/* 390 */       if (recipe instanceof ShapedRecipes) return (Object[])((ShapedRecipes)recipe).field_77574_d; 
/* 391 */       if (recipe instanceof ShapelessRecipes)
/* 392 */         return ((ShapelessRecipes)recipe).field_77579_b.toArray((Object[])new ItemStack[0]); 
/* 393 */       return null;
/*     */     }
/*     */     
/*     */     private Object[] getShapedOreRecipe(ShapedOreRecipe recipe) {
/*     */       try {
/* 398 */         Field field = ShapedOreRecipe.class.getDeclaredField("width");
/* 399 */         if (field != null) {
/* 400 */           field.setAccessible(true);
/* 401 */           int width = field.getInt(recipe);
/* 402 */           Object[] input = recipe.getInput();
/* 403 */           Object[] grid = new Object[9];
/* 404 */           for (int i = 0, offset = 0, y = 0; y < 3; y++) {
/* 405 */             for (int x = 0; x < 3; x++, i++) {
/* 406 */               if (x < width && offset < input.length) {
/* 407 */                 grid[i] = input[offset];
/* 408 */                 offset++;
/*     */               } else {
/* 410 */                 grid[i] = null;
/*     */               } 
/*     */             } 
/*     */           } 
/* 414 */           return grid;
/*     */         } 
/* 416 */       } catch (Exception e) {
/* 417 */         e.printStackTrace();
/*     */       } 
/* 419 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\guicomponents\ComponentManualPage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */