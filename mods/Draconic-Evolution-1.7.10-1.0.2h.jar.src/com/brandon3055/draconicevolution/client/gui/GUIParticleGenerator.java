/*     */ package com.brandon3055.draconicevolution.client.gui;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.network.ParticleGenPacket;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileParticleGenerator;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjglx.input.Keyboard;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ public class GUIParticleGenerator extends GuiScreen {
/*  17 */   private final int xSize = 212;
/*  18 */   private final int ySize = 198;
/*  19 */   private ResourceLocation guiTexture = new ResourceLocation("draconicevolution", "textures/gui/ParticleGenerator.png");
/*  20 */   private int page = 1;
/*  21 */   private int infoPage = 0;
/*     */   
/*     */   private boolean hasInitialized = false;
/*     */   
/*     */   private boolean particles_enabled = true;
/*     */   
/*  27 */   private int red = 0;
/*  28 */   private int green = 0;
/*  29 */   private int blue = 0;
/*  30 */   private int random_red = 0;
/*  31 */   private int random_green = 0;
/*  32 */   private int random_blue = 0;
/*  33 */   private float motion_x = 0.0F;
/*  34 */   private float motion_y = 0.0F;
/*  35 */   private float motion_z = 0.0F;
/*  36 */   private float random_motion_x = 0.0F;
/*  37 */   private float random_motion_y = 0.0F;
/*  38 */   private float random_motion_z = 0.0F;
/*  39 */   private float scale = 0.0F;
/*  40 */   private float random_scale = 0.0F;
/*  41 */   private int life = 0;
/*  42 */   private int random_life = 0;
/*  43 */   private float spawn_x = 0.0F;
/*  44 */   private float spawn_y = 0.0F;
/*  45 */   private float spawn_z = 0.0F;
/*  46 */   private float random_spawn_x = 0.0F;
/*  47 */   private float random_spawn_y = 0.0F;
/*  48 */   private float random_spawn_z = 0.0F;
/*  49 */   private int fade = 0;
/*  50 */   private int spawn_rate = 0;
/*     */   private boolean collide = false;
/*  52 */   private int selected_particle = 1;
/*  53 */   private int selected_max = 3;
/*  54 */   private float gravity = 0.0F;
/*     */   
/*     */   private boolean beam_enabled = false;
/*     */   
/*     */   private boolean render_core = false;
/*     */   
/*  60 */   private int beam_red = 0;
/*  61 */   private int beam_green = 0;
/*  62 */   private int beam_blue = 0;
/*  63 */   private float beam_scale = 0.0F;
/*  64 */   private float beam_pitch = 0.0F;
/*  65 */   private float beam_yaw = 0.0F;
/*  66 */   private float beam_length = 0.0F;
/*  67 */   private float beam_rotation = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TileParticleGenerator tile;
/*     */ 
/*     */ 
/*     */   
/*     */   String[] InfoText;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GUIParticleGenerator(TileParticleGenerator tile, EntityPlayer player) {
/*  82 */     this.InfoText = new String[] { "The Particle Generator is a decorative device that allows you to create your own custom particle effects.                                                                 It is fairly easy you use this device you simply adjust the fields (variables) in the interface to change how the generated particles look and behave.                                                                                            This block is a work in progress and new features and particles are likely to be added in future versions.                                                         The following is a list of all of the fields in the interface and what they do.", "The first thing to note is that most fields have a random modifier which will add a random number between 0 and whatever max (or min) value you give it to the field.                                                                                      -The first 3 fields (Red, Green & Blue) control the colour of the particle. Most people should be familiar with this colour system if not google RGB colours. Note: the max value for each colour can not go higher then 255 so the colour field limits the random modifier e.g. if the colour field is set to 255 and the random modifier is set to 20 the result will always be 255", "-The next 3 fields (Motion X, Y & Z) control the direction and speed of the particle                                                                                            -The \"Life\" field sets how long (in ticks) before the particle despawns.                                                       -The \"Size\" field sets the size of the particle.                                                                                           -The next 3 fields (Spawn X, Y & Z) Sets the spawn location of the particle (relative to the location of the particle generator)", "-The \"Delay\" field sets the delay (in ticks) between each particle spawn e.g. 1=20/s, 20=1/s, 100=1/5s                                                                    -The \"Fade\" field sets how long (in ticks) it takes the partile to fade out of existance. Note: This adds to the life of the particle                                                                                  -The \"Gravity\" field sets how the particle is affected by gravity.                                                              -\"Block Collision\" Toggles weather or not the particle will collide with blocks                                                     -\"Particle Selected\" Switches between the different particles available.", EnumChatFormatting.DARK_RED + "              Redstone Control" + EnumChatFormatting.BLACK + "\nBy default a redstone signal is required for the generator to run.\n\nHowever if you shift right click the generator with an empty hand it will switch to inverted mode.\nThe redstone mode is indicated by the 8 cubes at the corners of the block.", EnumChatFormatting.DARK_RED + "              Computer Control" + EnumChatFormatting.BLACK + "\nThe Generator can be controlled via a computer\nIt exposes a relatively straight forward API:\n\n  setGeneratorProperty(property, value)\n  getGeneratorState()\n  resetGeneratorState()\n\nGenerator state is obtained as a whole from getGeneratorState, whereas properties are modified one at a time using setGeneratorProperty. Property names are strings and mostly correspond to button labels in the GUI." };
/*     */     this.tile = tile;
/*     */     syncWithServer();
/*     */   }
/*     */   public void func_73863_a(int x, int y, float f) {
/*  87 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  88 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(this.guiTexture);
/*  89 */     getClass(); int posX = (this.field_146294_l - 212) / 2;
/*  90 */     getClass(); int posY = (this.field_146295_m - 198) / 2;
/*  91 */     getClass(); getClass(); func_73729_b(posX, posY, 0, 0, 212, 198);
/*  92 */     if (this.page < 3) { this.field_146289_q.func_78261_a("Particle Generator", posX + 60, posY + 5, 65535); }
/*  93 */     else if (this.page < 10) { this.field_146289_q.func_78261_a("Beam Generator", posX + 65, posY + 5, 65535); }
/*  94 */     else { this.field_146289_q.func_78261_a("Information", posX + 75, posY + 5, 65535); }
/*     */     
/*  96 */     if (this.page == 1) { page1Txt(); }
/*  97 */     else if (this.page == 2) { page2Txt(); }
/*  98 */     else if (this.page == 3) { page3Txt(); }
/*  99 */     else if (this.page == 10)
/* 100 */     { this.field_146289_q.func_78279_b(this.InfoText[this.infoPage], posX + 5, posY + 20, 200, 0);
/* 101 */       this.field_146289_q.func_78279_b("Page: " + (this.infoPage + 1), posX + 88, posY + 180, 200, 16711680); }
/*     */ 
/*     */     
/* 104 */     this.field_146289_q.func_78261_a("Hold:", posX + 215, posY + 11, 16777215);
/* 105 */     this.field_146289_q.func_78261_a("Shift +- 10", posX + 215, posY + 21, 16777215);
/* 106 */     this.field_146289_q.func_78261_a("Ctrl +- 50", posX + 215, posY + 31, 16777215);
/* 107 */     this.field_146289_q.func_78261_a("Shift+Ctrl +- 100", posX + 215, posY + 41, 16777215);
/*     */     
/* 109 */     super.func_73863_a(x, y, f);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int x, int y, int button) {
/* 115 */     super.func_73864_a(x, y, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/* 121 */     getClass(); int posX = (this.field_146294_l - 212) / 2;
/* 122 */     getClass(); int posY = (this.field_146295_m - 198) / 2;
/* 123 */     this.field_146292_n.clear();
/*     */     
/* 125 */     if (this.page < 10) {
/* 126 */       this.field_146292_n.add(new GuiButton(33, posX + 213, posY + 177, 47, 20, "===>"));
/* 127 */       this.field_146292_n.add(new GuiButton(32, posX - 47, posY + 177, 47, 20, "<==="));
/*     */     } 
/*     */     
/* 130 */     if (this.page == 1) { page1Buttons(); }
/* 131 */     else if (this.page == 2) { page2Buttons(); }
/* 132 */     else if (this.page == 3) { page3Buttons(); }
/* 133 */     else if (this.page == 10)
/* 134 */     { this.field_146292_n.add(new GuiButton(57, posX + 4, posY + 174, 80, 20, "Previous page"));
/* 135 */       this.field_146292_n.add(new GuiButton(56, posX + 128, posY + 174, 80, 20, "Next page")); }
/*     */ 
/*     */     
/* 138 */     if (this.page < 10) { this.field_146292_n.add(new GuiButton(54, posX - 21, posY + 3, 20, 20, "i")); }
/* 139 */     else { this.field_146292_n.add(new GuiButton(55, posX - 31, posY + 23, 30, 20, "Back")); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton button) {
/* 146 */     if (button.field_146127_k < 100) { particleActions(button); }
/* 147 */     else { beamActions(button); }
/*     */   
/*     */   }
/*     */   private void particleActions(GuiButton button) {
/* 151 */     int value = 1;
/*     */     
/* 153 */     short packetValue = 0;
/* 154 */     if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) value = 10; 
/* 155 */     if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) value = 50; 
/* 156 */     if ((Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54))) {
/* 157 */       value = 100;
/*     */     }
/* 159 */     float value_F = value / 1000.0F;
/*     */     
/* 161 */     switch (button.field_146127_k) {
/*     */       case 0:
/* 163 */         this.red = Math.min(this.red + value, 255);
/* 164 */         packetValue = (short)this.red;
/*     */         break;
/*     */       case 1:
/* 167 */         this.green = Math.min(this.green + value, 255);
/* 168 */         packetValue = (short)this.green;
/*     */         break;
/*     */       case 2:
/* 171 */         this.blue = Math.min(this.blue + value, 255);
/* 172 */         packetValue = (short)this.blue;
/*     */         break;
/*     */       case 3:
/* 175 */         this.motion_x = Math.min(this.motion_x + value_F, 5.0F);
/* 176 */         packetValue = (short)(int)(this.motion_x * 1000.0F);
/*     */         break;
/*     */       case 4:
/* 179 */         this.motion_y = Math.min(this.motion_y + value_F, 5.0F);
/* 180 */         packetValue = (short)(int)(this.motion_y * 1000.0F);
/*     */         break;
/*     */       case 5:
/* 183 */         this.motion_z = Math.min(this.motion_z + value_F, 5.0F);
/* 184 */         packetValue = (short)(int)(this.motion_z * 1000.0F);
/*     */         break;
/*     */       case 6:
/* 187 */         this.red = Math.max(this.red - value, 0);
/* 188 */         packetValue = (short)this.red;
/*     */         break;
/*     */       case 7:
/* 191 */         this.green = Math.max(this.green - value, 0);
/* 192 */         packetValue = (short)this.green;
/*     */         break;
/*     */       case 8:
/* 195 */         this.blue = Math.max(this.blue - value, 0);
/* 196 */         packetValue = (short)this.blue;
/*     */         break;
/*     */       case 9:
/* 199 */         this.motion_x = Math.max(this.motion_x - value_F, -5.0F);
/* 200 */         packetValue = (short)(int)(this.motion_x * 1000.0F);
/*     */         break;
/*     */       case 10:
/* 203 */         this.motion_y = Math.max(this.motion_y - value_F, -5.0F);
/* 204 */         packetValue = (short)(int)(this.motion_y * 1000.0F);
/*     */         break;
/*     */       case 11:
/* 207 */         this.motion_z = Math.max(this.motion_z - value_F, -5.0F);
/* 208 */         packetValue = (short)(int)(this.motion_z * 1000.0F);
/*     */         break;
/*     */       case 12:
/* 211 */         this.random_red = Math.min(this.random_red + value, 255);
/* 212 */         packetValue = (short)this.random_red;
/*     */         break;
/*     */       case 13:
/* 215 */         this.random_green = Math.min(this.random_green + value, 255);
/* 216 */         packetValue = (short)this.random_green;
/*     */         break;
/*     */       case 14:
/* 219 */         this.random_blue = Math.min(this.random_blue + value, 255);
/* 220 */         packetValue = (short)this.random_blue;
/*     */         break;
/*     */       case 15:
/* 223 */         this.random_motion_x = Math.min(this.random_motion_x + value_F, 5.0F);
/* 224 */         packetValue = (short)(int)(this.random_motion_x * 1000.0F);
/*     */         break;
/*     */       case 16:
/* 227 */         this.random_motion_y = Math.min(this.random_motion_y + value_F, 5.0F);
/* 228 */         packetValue = (short)(int)(this.random_motion_y * 1000.0F);
/*     */         break;
/*     */       case 17:
/* 231 */         this.random_motion_z = Math.min(this.random_motion_z + value_F, 5.0F);
/* 232 */         packetValue = (short)(int)(this.random_motion_z * 1000.0F);
/*     */         break;
/*     */       case 18:
/* 235 */         this.random_red = Math.max(this.random_red - value, 0);
/* 236 */         packetValue = (short)this.random_red;
/*     */         break;
/*     */       case 19:
/* 239 */         this.random_green = Math.max(this.random_green - value, 0);
/* 240 */         packetValue = (short)this.random_green;
/*     */         break;
/*     */       case 20:
/* 243 */         this.random_blue = Math.max(this.random_blue - value, 0);
/* 244 */         packetValue = (short)this.random_blue;
/*     */         break;
/*     */       case 21:
/* 247 */         this.random_motion_x = Math.max(this.random_motion_x - value_F, -5.0F);
/* 248 */         packetValue = (short)(int)(this.random_motion_x * 1000.0F);
/*     */         break;
/*     */       case 22:
/* 251 */         this.random_motion_y = Math.max(this.random_motion_y - value_F, -5.0F);
/* 252 */         packetValue = (short)(int)(this.random_motion_y * 1000.0F);
/*     */         break;
/*     */       case 23:
/* 255 */         this.random_motion_z = Math.max(this.random_motion_z - value_F, -5.0F);
/* 256 */         packetValue = (short)(int)(this.random_motion_z * 1000.0F);
/*     */         break;
/*     */       case 24:
/* 259 */         this.life = Math.min(this.life + value, 1000);
/* 260 */         packetValue = (short)this.life;
/*     */         break;
/*     */       case 25:
/* 263 */         this.life = Math.max(this.life - value, 0);
/* 264 */         packetValue = (short)this.life;
/*     */         break;
/*     */       case 26:
/* 267 */         this.random_life = Math.min(this.random_life + value, 1000);
/* 268 */         packetValue = (short)this.random_life;
/*     */         break;
/*     */       case 27:
/* 271 */         this.random_life = Math.max(this.random_life - value, 0);
/* 272 */         packetValue = (short)this.random_life;
/*     */         break;
/*     */       case 28:
/* 275 */         this.scale = Math.min(this.scale + value_F * 10.0F, 50.0F);
/* 276 */         packetValue = (short)(int)(this.scale * 100.0F);
/*     */         break;
/*     */       case 29:
/* 279 */         this.scale = Math.max(this.scale - value_F * 10.0F, 0.01F);
/* 280 */         packetValue = (short)(int)(this.scale * 100.0F);
/*     */         break;
/*     */       case 30:
/* 283 */         this.random_scale = Math.min(this.random_scale + value_F * 10.0F, 50.0F);
/* 284 */         packetValue = (short)(int)(this.random_scale * 100.0F);
/*     */         break;
/*     */       case 31:
/* 287 */         this.random_scale = Math.max(this.random_scale - value_F * 10.0F, 0.0F);
/* 288 */         packetValue = (short)(int)(this.random_scale * 100.0F);
/*     */         break;
/*     */       case 32:
/* 291 */         if (this.page > 1) this.page--; 
/* 292 */         packetValue = (short)this.page;
/* 293 */         func_73866_w_();
/*     */         break;
/*     */       case 33:
/* 296 */         if (this.page < 3) this.page++; 
/* 297 */         func_73866_w_();
/* 298 */         packetValue = (short)this.page;
/*     */         break;
/*     */       case 34:
/* 301 */         this.spawn_x = Math.min(this.spawn_x + value_F * 100.0F, 50.0F);
/* 302 */         packetValue = (short)(int)(this.spawn_x * 100.0F);
/*     */         break;
/*     */       case 35:
/* 305 */         this.spawn_x = Math.max(this.spawn_x - value_F * 100.0F, -50.0F);
/* 306 */         packetValue = (short)(int)(this.spawn_x * 100.0F);
/*     */         break;
/*     */       case 36:
/* 309 */         this.random_spawn_x = Math.min(this.random_spawn_x + value_F * 100.0F, 50.0F);
/* 310 */         packetValue = (short)(int)(this.random_spawn_x * 100.0F);
/*     */         break;
/*     */       case 37:
/* 313 */         this.random_spawn_x = Math.max(this.random_spawn_x - value_F * 100.0F, -50.0F);
/* 314 */         packetValue = (short)(int)(this.random_spawn_x * 100.0F);
/*     */         break;
/*     */       case 38:
/* 317 */         this.spawn_y = Math.min(this.spawn_y + value_F * 100.0F, 50.0F);
/* 318 */         packetValue = (short)(int)(this.spawn_y * 100.0F);
/*     */         break;
/*     */       case 39:
/* 321 */         this.spawn_y = Math.max(this.spawn_y - value_F * 100.0F, -50.0F);
/* 322 */         packetValue = (short)(int)(this.spawn_y * 100.0F);
/*     */         break;
/*     */       case 40:
/* 325 */         this.random_spawn_y = Math.min(this.random_spawn_y + value_F * 100.0F, 50.0F);
/* 326 */         packetValue = (short)(int)(this.random_spawn_y * 100.0F);
/*     */         break;
/*     */       case 41:
/* 329 */         this.random_spawn_y = Math.max(this.random_spawn_y - value_F * 100.0F, -50.0F);
/* 330 */         packetValue = (short)(int)(this.random_spawn_y * 100.0F);
/*     */         break;
/*     */       case 42:
/* 333 */         this.spawn_z = Math.min(this.spawn_z + value_F * 100.0F, 50.0F);
/* 334 */         packetValue = (short)(int)(this.spawn_z * 100.0F);
/*     */         break;
/*     */       case 43:
/* 337 */         this.spawn_z = Math.max(this.spawn_z - value_F * 100.0F, -50.0F);
/* 338 */         packetValue = (short)(int)(this.spawn_z * 100.0F);
/*     */         break;
/*     */       case 44:
/* 341 */         this.random_spawn_z = Math.min(this.random_spawn_z + value_F * 100.0F, 50.0F);
/* 342 */         packetValue = (short)(int)(this.random_spawn_z * 100.0F);
/*     */         break;
/*     */       case 45:
/* 345 */         this.random_spawn_z = Math.max(this.random_spawn_z - value_F * 100.0F, -50.0F);
/* 346 */         packetValue = (short)(int)(this.random_spawn_z * 100.0F);
/*     */         break;
/*     */       case 46:
/* 349 */         this.spawn_rate = Math.min(this.spawn_rate + value, 200);
/* 350 */         packetValue = (short)this.spawn_rate;
/*     */         break;
/*     */       case 47:
/* 353 */         this.spawn_rate = Math.max(this.spawn_rate - value, 1);
/* 354 */         packetValue = (short)this.spawn_rate;
/*     */         break;
/*     */       case 48:
/* 357 */         this.fade = Math.min(this.fade + value, 100);
/* 358 */         packetValue = (short)this.fade;
/*     */         break;
/*     */       case 49:
/* 361 */         this.fade = Math.max(this.fade - value, 0);
/* 362 */         packetValue = (short)this.fade;
/*     */         break;
/*     */       case 50:
/* 365 */         this.collide = !this.collide;
/* 366 */         packetValue = (short)(this.collide ? 1 : 0);
/* 367 */         func_73866_w_();
/*     */         break;
/*     */       case 51:
/* 370 */         this.selected_particle = (this.selected_particle < this.selected_max) ? (this.selected_particle + 1) : 1;
/* 371 */         packetValue = (short)this.selected_particle;
/* 372 */         func_73866_w_();
/*     */         break;
/*     */       case 52:
/* 375 */         this.gravity = Math.min(this.gravity + value_F, 5.0F);
/* 376 */         packetValue = (short)(int)(this.gravity * 1000.0F);
/*     */         break;
/*     */       case 53:
/* 379 */         this.gravity = Math.max(this.gravity - value_F, -5.0F);
/* 380 */         packetValue = (short)(int)(this.gravity * 1000.0F);
/*     */         break;
/*     */       case 54:
/* 383 */         this.page = 10;
/* 384 */         func_73866_w_();
/* 385 */         packetValue = (short)this.page;
/*     */         break;
/*     */       case 55:
/* 388 */         this.page = 1;
/* 389 */         func_73866_w_();
/* 390 */         packetValue = (short)this.page;
/*     */         break;
/*     */       case 56:
/* 393 */         if (this.infoPage < 5) { this.infoPage++; break; }
/* 394 */          func_73866_w_();
/*     */         break;
/*     */       case 57:
/* 397 */         if (this.infoPage > 0) { this.infoPage--; break; }
/* 398 */          func_73866_w_();
/*     */         break;
/*     */       case 58:
/* 401 */         this.particles_enabled = !this.particles_enabled;
/* 402 */         packetValue = this.particles_enabled ? 1 : 0;
/* 403 */         func_73866_w_();
/*     */         break;
/*     */     } 
/* 406 */     DraconicEvolution.network.sendToServer((IMessage)new ParticleGenPacket((byte)button.field_146127_k, packetValue, this.tile.field_145851_c, this.tile.field_145848_d, this.tile.field_145849_e));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73869_a(char key, int keyN) {
/* 411 */     if (key == 'e' || key == '\033') {
/* 412 */       this.field_146297_k.func_147108_a(null);
/* 413 */       this.field_146297_k.func_71381_h();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73876_c() {}
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/* 423 */     return false;
/*     */   }
/*     */   
/*     */   private void syncWithServer() {
/* 427 */     this.red = this.tile.red;
/* 428 */     this.green = this.tile.green;
/* 429 */     this.blue = this.tile.blue;
/* 430 */     this.random_red = this.tile.random_red;
/* 431 */     this.random_green = this.tile.random_green;
/* 432 */     this.random_blue = this.tile.random_blue;
/* 433 */     this.motion_x = this.tile.motion_x;
/* 434 */     this.motion_y = this.tile.motion_y;
/* 435 */     this.motion_z = this.tile.motion_z;
/* 436 */     this.random_motion_x = this.tile.random_motion_x;
/* 437 */     this.random_motion_y = this.tile.random_motion_y;
/* 438 */     this.random_motion_z = this.tile.random_motion_z;
/* 439 */     this.scale = this.tile.scale;
/* 440 */     this.random_scale = this.tile.random_scale;
/* 441 */     this.life = this.tile.life;
/* 442 */     this.random_life = this.tile.random_life;
/* 443 */     this.spawn_x = this.tile.spawn_x;
/* 444 */     this.spawn_y = this.tile.spawn_y;
/* 445 */     this.spawn_z = this.tile.spawn_z;
/* 446 */     this.random_spawn_x = this.tile.random_spawn_x;
/* 447 */     this.random_spawn_y = this.tile.random_spawn_y;
/* 448 */     this.random_spawn_z = this.tile.random_spawn_z;
/* 449 */     this.page = this.tile.page;
/* 450 */     this.spawn_rate = this.tile.spawn_rate;
/* 451 */     this.collide = this.tile.collide;
/* 452 */     this.fade = this.tile.fade;
/* 453 */     this.selected_particle = this.tile.selected_particle;
/* 454 */     this.gravity = this.tile.gravity;
/* 455 */     this.particles_enabled = this.tile.particles_enabled;
/*     */     
/* 457 */     this.render_core = this.tile.render_core;
/* 458 */     this.beam_enabled = this.tile.beam_enabled;
/* 459 */     this.beam_red = this.tile.beam_red;
/* 460 */     this.beam_green = this.tile.beam_green;
/* 461 */     this.beam_blue = this.tile.beam_blue;
/* 462 */     this.beam_scale = this.tile.beam_scale;
/* 463 */     this.beam_pitch = this.tile.beam_pitch;
/* 464 */     this.beam_yaw = this.tile.beam_yaw;
/* 465 */     this.beam_length = this.tile.beam_length;
/* 466 */     this.beam_rotation = this.tile.beam_rotation;
/*     */   }
/*     */ 
/*     */   
/*     */   private void page1Txt() {
/* 471 */     getClass(); int posX = (this.field_146294_l - 212) / 2;
/* 472 */     getClass(); int posY = (this.field_146295_m - 198) / 2;
/*     */     
/* 474 */     String motionX = String.valueOf(Math.round(this.motion_x * 1000.0F) / 1000.0F);
/* 475 */     String motionY = String.valueOf(Math.round(this.motion_y * 1000.0F) / 1000.0F);
/* 476 */     String motionZ = String.valueOf(Math.round(this.motion_z * 1000.0F) / 1000.0F);
/* 477 */     String random_motionX = String.valueOf(Math.round(this.random_motion_x * 1000.0F) / 1000.0F);
/* 478 */     String random_motionY = String.valueOf(Math.round(this.random_motion_y * 1000.0F) / 1000.0F);
/* 479 */     String random_motionZ = String.valueOf(Math.round(this.random_motion_z * 1000.0F) / 1000.0F);
/* 480 */     String scale1 = String.valueOf(Math.round(this.scale * 100.0F) / 100.0F);
/* 481 */     String random_scale1 = String.valueOf(Math.round(this.random_scale * 100.0F) / 100.0F);
/*     */     
/* 483 */     int col1 = posX + 30;
/* 484 */     int col2 = posX + 141;
/* 485 */     int ln1 = 20;
/* 486 */     int ln2 = 30;
/*     */     
/* 488 */     this.field_146289_q.func_85187_a("Red:", col1, posY + ln1, 0, false);
/* 489 */     this.field_146289_q.func_85187_a(String.valueOf(this.red), col1, posY + ln2, 0, false);
/* 490 */     this.field_146289_q.func_85187_a("Green:", col1, posY + ln1 + 22, 0, false);
/* 491 */     this.field_146289_q.func_85187_a(String.valueOf(this.green), col1, posY + ln2 + 22, 0, false);
/* 492 */     this.field_146289_q.func_85187_a("Blue:", col1, posY + ln1 + 44, 0, false);
/* 493 */     this.field_146289_q.func_85187_a(String.valueOf(this.blue), col1, posY + ln2 + 44, 0, false);
/* 494 */     this.field_146289_q.func_85187_a("Motion X:", col1, posY + ln1 + 66, 0, false);
/* 495 */     this.field_146289_q.func_85187_a(motionX, col1, posY + ln2 + 66, 0, false);
/* 496 */     this.field_146289_q.func_85187_a("Motion Y:", col1, posY + ln1 + 88, 0, false);
/* 497 */     this.field_146289_q.func_85187_a(motionY, col1, posY + ln2 + 88, 0, false);
/* 498 */     this.field_146289_q.func_85187_a("Motion Z:", col1, posY + ln1 + 110, 0, false);
/* 499 */     this.field_146289_q.func_85187_a(motionZ, col1, posY + ln2 + 110, 0, false);
/* 500 */     this.field_146289_q.func_85187_a("Life:", col1, posY + ln1 + 132, 0, false);
/* 501 */     this.field_146289_q.func_85187_a("" + this.life + " T", col1, posY + ln2 + 132, 0, false);
/* 502 */     this.field_146289_q.func_85187_a("Size:", col1, posY + ln1 + 154, 0, false);
/* 503 */     this.field_146289_q.func_85187_a("" + scale1, col1, posY + ln2 + 154, 0, false);
/*     */     
/* 505 */     for (int i = 0; i < 8; i++) {
/* 506 */       this.field_146289_q.func_78261_a("(+)", posX + 98, posY + 25 + i * 22, 16777215);
/*     */     }
/* 508 */     this.field_146289_q.func_85187_a("Random:0", col2, posY + ln1, 0, false);
/* 509 */     this.field_146289_q.func_85187_a("> " + this.random_red, col2, posY + ln2, 0, false);
/* 510 */     this.field_146289_q.func_85187_a("Random:0", col2, posY + ln1 + 22, 0, false);
/* 511 */     this.field_146289_q.func_85187_a("> " + this.random_green, col2, posY + ln2 + 22, 0, false);
/* 512 */     this.field_146289_q.func_85187_a("Random:0", col2, posY + ln1 + 44, 0, false);
/* 513 */     this.field_146289_q.func_85187_a("> " + this.random_blue, col2, posY + ln2 + 44, 0, false);
/* 514 */     this.field_146289_q.func_85187_a("Random:0", col2, posY + ln1 + 66, 0, false);
/* 515 */     this.field_146289_q.func_85187_a("> " + random_motionX, col2, posY + ln2 + 66, 0, false);
/* 516 */     this.field_146289_q.func_85187_a("Random:0", col2, posY + ln1 + 88, 0, false);
/* 517 */     this.field_146289_q.func_85187_a("> " + random_motionY, col2, posY + ln2 + 88, 0, false);
/* 518 */     this.field_146289_q.func_85187_a("Random:0", col2, posY + ln1 + 110, 0, false);
/* 519 */     this.field_146289_q.func_85187_a("> " + random_motionZ, col2, posY + ln2 + 110, 0, false);
/* 520 */     this.field_146289_q.func_85187_a("Random:0", col2, posY + ln1 + 132, 0, false);
/* 521 */     this.field_146289_q.func_85187_a("> " + this.random_life, col2, posY + ln2 + 132, 0, false);
/* 522 */     this.field_146289_q.func_85187_a("Random:0", col2, posY + ln1 + 154, 0, false);
/* 523 */     this.field_146289_q.func_85187_a("> " + random_scale1, col2, posY + ln2 + 154, 0, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void page1Buttons() {
/* 528 */     getClass(); int posX = (this.field_146294_l - 212) / 2;
/* 529 */     getClass(); int posY = (this.field_146295_m - 198) / 2;
/* 530 */     int DX1 = 5;
/* 531 */     int DX2 = DX1 + 71;
/* 532 */     int DX3 = DX2 + 40;
/* 533 */     int DX4 = DX3 + 71;
/* 534 */     int y1 = 19;
/*     */     
/* 536 */     this.field_146292_n.add(new GuiButton(0, posX + DX1, posY + y1, 20, 20, "+"));
/* 537 */     this.field_146292_n.add(new GuiButton(1, posX + DX1, posY + y1 + 22, 20, 20, "+"));
/* 538 */     this.field_146292_n.add(new GuiButton(2, posX + DX1, posY + y1 + 44, 20, 20, "+"));
/* 539 */     this.field_146292_n.add(new GuiButton(3, posX + DX1, posY + y1 + 66, 20, 20, "+"));
/* 540 */     this.field_146292_n.add(new GuiButton(4, posX + DX1, posY + y1 + 88, 20, 20, "+"));
/* 541 */     this.field_146292_n.add(new GuiButton(5, posX + DX1, posY + y1 + 110, 20, 20, "+"));
/* 542 */     this.field_146292_n.add(new GuiButton(24, posX + DX1, posY + y1 + 132, 20, 20, "+"));
/* 543 */     this.field_146292_n.add(new GuiButton(28, posX + DX1, posY + y1 + 154, 20, 20, "+"));
/*     */     
/* 545 */     this.field_146292_n.add(new GuiButton(6, posX + DX2, posY + y1, 20, 20, "-"));
/* 546 */     this.field_146292_n.add(new GuiButton(7, posX + DX2, posY + y1 + 22, 20, 20, "-"));
/* 547 */     this.field_146292_n.add(new GuiButton(8, posX + DX2, posY + y1 + 44, 20, 20, "-"));
/* 548 */     this.field_146292_n.add(new GuiButton(9, posX + DX2, posY + y1 + 66, 20, 20, "-"));
/* 549 */     this.field_146292_n.add(new GuiButton(10, posX + DX2, posY + y1 + 88, 20, 20, "-"));
/* 550 */     this.field_146292_n.add(new GuiButton(11, posX + DX2, posY + y1 + 110, 20, 20, "-"));
/* 551 */     this.field_146292_n.add(new GuiButton(25, posX + DX2, posY + y1 + 132, 20, 20, "-"));
/* 552 */     this.field_146292_n.add(new GuiButton(29, posX + DX2, posY + y1 + 154, 20, 20, "-"));
/*     */     
/* 554 */     this.field_146292_n.add(new GuiButton(12, posX + DX3, posY + y1, 20, 20, "+"));
/* 555 */     this.field_146292_n.add(new GuiButton(13, posX + DX3, posY + y1 + 22, 20, 20, "+"));
/* 556 */     this.field_146292_n.add(new GuiButton(14, posX + DX3, posY + y1 + 44, 20, 20, "+"));
/* 557 */     this.field_146292_n.add(new GuiButton(15, posX + DX3, posY + y1 + 66, 20, 20, "+"));
/* 558 */     this.field_146292_n.add(new GuiButton(16, posX + DX3, posY + y1 + 88, 20, 20, "+"));
/* 559 */     this.field_146292_n.add(new GuiButton(17, posX + DX3, posY + y1 + 110, 20, 20, "+"));
/* 560 */     this.field_146292_n.add(new GuiButton(26, posX + DX3, posY + y1 + 132, 20, 20, "+"));
/* 561 */     this.field_146292_n.add(new GuiButton(30, posX + DX3, posY + y1 + 154, 20, 20, "+"));
/*     */     
/* 563 */     this.field_146292_n.add(new GuiButton(18, posX + DX4, posY + y1, 20, 20, "-"));
/* 564 */     this.field_146292_n.add(new GuiButton(19, posX + DX4, posY + y1 + 22, 20, 20, "-"));
/* 565 */     this.field_146292_n.add(new GuiButton(20, posX + DX4, posY + y1 + 44, 20, 20, "-"));
/* 566 */     this.field_146292_n.add(new GuiButton(21, posX + DX4, posY + y1 + 66, 20, 20, "-"));
/* 567 */     this.field_146292_n.add(new GuiButton(22, posX + DX4, posY + y1 + 88, 20, 20, "-"));
/* 568 */     this.field_146292_n.add(new GuiButton(23, posX + DX4, posY + y1 + 110, 20, 20, "-"));
/* 569 */     this.field_146292_n.add(new GuiButton(27, posX + DX4, posY + y1 + 132, 20, 20, "-"));
/* 570 */     this.field_146292_n.add(new GuiButton(31, posX + DX4, posY + y1 + 154, 20, 20, "-"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void page2Txt() {
/* 576 */     getClass(); int posX = (this.field_146294_l - 212) / 2;
/* 577 */     getClass(); int posY = (this.field_146295_m - 198) / 2;
/*     */     
/* 579 */     int col1 = posX + 30;
/* 580 */     int col2 = posX + 141;
/* 581 */     int ln1 = 20;
/* 582 */     int ln2 = 30;
/*     */     
/* 584 */     String spawn_X = String.valueOf(Math.round(this.spawn_x * 10.0F) / 10.0F);
/* 585 */     String spawn_Y = String.valueOf(Math.round(this.spawn_y * 10.0F) / 10.0F);
/* 586 */     String spawn_Z = String.valueOf(Math.round(this.spawn_z * 10.0F) / 10.0F);
/* 587 */     String random_spawn_X = String.valueOf(Math.round(this.random_spawn_x * 10.0F) / 10.0F);
/* 588 */     String random_spawn_Y = String.valueOf(Math.round(this.random_spawn_y * 10.0F) / 10.0F);
/* 589 */     String random_spawn_Z = String.valueOf(Math.round(this.random_spawn_z * 10.0F) / 10.0F);
/* 590 */     String Gravity = String.valueOf(Math.round(this.gravity * 1000.0F) / 1000.0F);
/*     */     
/* 592 */     this.field_146289_q.func_85187_a("Spawn X:", col1, posY + ln1, 0, false);
/* 593 */     this.field_146289_q.func_85187_a(spawn_X, col1, posY + ln2, 0, false);
/* 594 */     this.field_146289_q.func_85187_a("Spawn Y:", col1, posY + ln1 + 22, 0, false);
/* 595 */     this.field_146289_q.func_85187_a(spawn_Y, col1, posY + ln2 + 22, 0, false);
/* 596 */     this.field_146289_q.func_85187_a("Spawn Z:", col1, posY + ln1 + 44, 0, false);
/* 597 */     this.field_146289_q.func_85187_a(spawn_Z, col1, posY + ln2 + 44, 0, false);
/*     */     
/* 599 */     for (int i = 0; i < 3; i++) {
/* 600 */       this.field_146289_q.func_78261_a("(+)", posX + 98, posY + 25 + i * 22, 16777215);
/*     */     }
/* 602 */     this.field_146289_q.func_85187_a("Random:0", col2, posY + ln1, 0, false);
/* 603 */     this.field_146289_q.func_85187_a("> " + random_spawn_X, col2, posY + ln2, 0, false);
/* 604 */     this.field_146289_q.func_85187_a("Random:0", col2, posY + ln1 + 22, 0, false);
/* 605 */     this.field_146289_q.func_85187_a("> " + random_spawn_Y, col2, posY + ln2 + 22, 0, false);
/* 606 */     this.field_146289_q.func_85187_a("Random:0", col2, posY + ln1 + 44, 0, false);
/* 607 */     this.field_146289_q.func_85187_a("> " + random_spawn_Z, col2, posY + ln2 + 44, 0, false);
/*     */     
/* 609 */     this.field_146289_q.func_85187_a("Delay:", col1, posY + ln1 + 66, 0, false);
/* 610 */     this.field_146289_q.func_85187_a("" + this.spawn_rate, col1, posY + ln2 + 66, 0, false);
/* 611 */     this.field_146289_q.func_85187_a("Fade:", col1, posY + ln1 + 88, 0, false);
/* 612 */     this.field_146289_q.func_85187_a("" + this.fade, col1, posY + ln2 + 88, 0, false);
/* 613 */     this.field_146289_q.func_85187_a("Gravity:", col1, posY + ln1 + 110, 0, false);
/* 614 */     this.field_146289_q.func_85187_a(Gravity, col1, posY + ln2 + 110, 0, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void page2Buttons() {
/* 620 */     getClass(); int posX = (this.field_146294_l - 212) / 2;
/* 621 */     getClass(); int posY = (this.field_146295_m - 198) / 2;
/* 622 */     int DX1 = 5;
/* 623 */     int DX2 = DX1 + 71;
/* 624 */     int DX3 = DX2 + 40;
/* 625 */     int DX4 = DX3 + 71;
/* 626 */     int y1 = 19;
/*     */     
/* 628 */     this.field_146292_n.add(new GuiButton(34, posX + DX1, posY + y1, 20, 20, "+"));
/* 629 */     this.field_146292_n.add(new GuiButton(38, posX + DX1, posY + y1 + 22, 20, 20, "+"));
/* 630 */     this.field_146292_n.add(new GuiButton(42, posX + DX1, posY + y1 + 44, 20, 20, "+"));
/* 631 */     this.field_146292_n.add(new GuiButton(46, posX + DX1, posY + y1 + 66, 20, 20, "+"));
/* 632 */     this.field_146292_n.add(new GuiButton(48, posX + DX1, posY + y1 + 88, 20, 20, "+"));
/* 633 */     this.field_146292_n.add(new GuiButton(52, posX + DX1, posY + y1 + 110, 20, 20, "+"));
/*     */     
/* 635 */     this.field_146292_n.add(new GuiButton(35, posX + DX2, posY + y1, 20, 20, "-"));
/* 636 */     this.field_146292_n.add(new GuiButton(39, posX + DX2, posY + y1 + 22, 20, 20, "-"));
/* 637 */     this.field_146292_n.add(new GuiButton(43, posX + DX2, posY + y1 + 44, 20, 20, "-"));
/* 638 */     this.field_146292_n.add(new GuiButton(47, posX + DX2, posY + y1 + 66, 20, 20, "-"));
/* 639 */     this.field_146292_n.add(new GuiButton(49, posX + DX2, posY + y1 + 88, 20, 20, "-"));
/* 640 */     this.field_146292_n.add(new GuiButton(53, posX + DX2, posY + y1 + 110, 20, 20, "-"));
/*     */     
/* 642 */     this.field_146292_n.add(new GuiButton(36, posX + DX3, posY + y1, 20, 20, "+"));
/* 643 */     this.field_146292_n.add(new GuiButton(40, posX + DX3, posY + y1 + 22, 20, 20, "+"));
/* 644 */     this.field_146292_n.add(new GuiButton(44, posX + DX3, posY + y1 + 44, 20, 20, "+"));
/*     */     
/* 646 */     this.field_146292_n.add(new GuiButton(37, posX + DX4, posY + y1, 20, 20, "-"));
/* 647 */     this.field_146292_n.add(new GuiButton(41, posX + DX4, posY + y1 + 22, 20, 20, "-"));
/* 648 */     this.field_146292_n.add(new GuiButton(45, posX + DX4, posY + y1 + 44, 20, 20, "-"));
/*     */     
/* 650 */     this.field_146292_n.add(new GuiButton(50, posX + DX3 - 11, posY + y1 + 66, 102, 20, "Block Collision: " + (this.collide ? "on" : "off")));
/* 651 */     this.field_146292_n.add(new GuiButton(51, posX + DX3 - 11, posY + y1 + 88, 102, 20, "Particle Selected: " + this.selected_particle));
/*     */     
/* 653 */     this.field_146292_n.add(new GuiButton(58, posX + DX3 - 11, posY + y1 + 110, 102, 20, "Enabled: " + (this.particles_enabled ? "on" : "off")));
/*     */   }
/*     */   
/*     */   private void page3Txt() {
/* 657 */     getClass(); int posX = (this.field_146294_l - 212) / 2;
/* 658 */     getClass(); int posY = (this.field_146295_m - 198) / 2;
/*     */     
/* 660 */     int col1 = posX + 30;
/* 661 */     int col2 = posX + 141;
/* 662 */     int ln1 = 20;
/* 663 */     int ln2 = 30;
/*     */     
/* 665 */     String pitch = String.valueOf(Math.round(this.beam_pitch * 100.0F) / 100.0F);
/* 666 */     String yaw = String.valueOf(Math.round(this.beam_yaw * 100.0F) / 100.0F);
/* 667 */     String length = String.valueOf(Math.round(this.beam_length * 100.0F) / 100.0F);
/* 668 */     String rotation = String.valueOf(Math.round(this.beam_rotation * 100.0F) / 100.0F);
/* 669 */     String scale = String.valueOf(Math.round(this.beam_scale * 100.0F) / 100.0F);
/*     */     
/* 671 */     this.field_146289_q.func_85187_a("Red:", col1, posY + ln1, 0, false);
/* 672 */     this.field_146289_q.func_85187_a(String.valueOf(this.beam_red), col1, posY + ln2, 0, false);
/* 673 */     this.field_146289_q.func_85187_a("Green:", col1, posY + ln1 + 22, 0, false);
/* 674 */     this.field_146289_q.func_85187_a(String.valueOf(this.beam_green), col1, posY + ln2 + 22, 0, false);
/* 675 */     this.field_146289_q.func_85187_a("Blue:", col1, posY + ln1 + 44, 0, false);
/* 676 */     this.field_146289_q.func_85187_a(String.valueOf(this.beam_blue), col1, posY + ln2 + 44, 0, false);
/*     */     
/* 678 */     this.field_146289_q.func_85187_a("Y Rot:", col1, posY + ln1 + 66, 0, false);
/* 679 */     this.field_146289_q.func_85187_a(pitch, col1, posY + ln2 + 66, 0, false);
/* 680 */     this.field_146289_q.func_85187_a("Z,X Rot:", col1, posY + ln1 + 88, 0, false);
/* 681 */     this.field_146289_q.func_85187_a(yaw, col1, posY + ln2 + 88, 0, false);
/* 682 */     this.field_146289_q.func_85187_a("Length:", col1, posY + ln1 + 110, 0, false);
/* 683 */     this.field_146289_q.func_85187_a(length, col1, posY + ln2 + 110, 0, false);
/* 684 */     this.field_146289_q.func_85187_a("Rotation:", col1, posY + ln1 + 132, 0, false);
/* 685 */     this.field_146289_q.func_85187_a(rotation, col1, posY + ln2 + 132, 0, false);
/* 686 */     this.field_146289_q.func_85187_a("Scale:", col1, posY + ln1 + 154, 0, false);
/* 687 */     this.field_146289_q.func_85187_a(scale, col1, posY + ln2 + 154, 0, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void page3Buttons() {
/* 693 */     getClass(); int posX = (this.field_146294_l - 212) / 2;
/* 694 */     getClass(); int posY = (this.field_146295_m - 198) / 2;
/* 695 */     int DX1 = 5;
/* 696 */     int DX2 = DX1 + 71;
/* 697 */     int DX3 = DX2 + 40;
/* 698 */     int DX4 = DX3 + 71;
/* 699 */     int y1 = 19;
/*     */     
/* 701 */     this.field_146292_n.add(new GuiButton(100, posX + DX1, posY + y1, 20, 20, "+"));
/* 702 */     this.field_146292_n.add(new GuiButton(101, posX + DX1, posY + y1 + 22, 20, 20, "+"));
/* 703 */     this.field_146292_n.add(new GuiButton(102, posX + DX1, posY + y1 + 44, 20, 20, "+"));
/* 704 */     this.field_146292_n.add(new GuiButton(103, posX + DX1, posY + y1 + 66, 20, 20, "+"));
/* 705 */     this.field_146292_n.add(new GuiButton(104, posX + DX1, posY + y1 + 88, 20, 20, "+"));
/* 706 */     this.field_146292_n.add(new GuiButton(105, posX + DX1, posY + y1 + 110, 20, 20, "+"));
/* 707 */     this.field_146292_n.add(new GuiButton(106, posX + DX1, posY + y1 + 132, 20, 20, "+"));
/* 708 */     this.field_146292_n.add(new GuiButton(107, posX + DX1, posY + y1 + 154, 20, 20, "+"));
/*     */     
/* 710 */     this.field_146292_n.add(new GuiButton(108, posX + DX2, posY + y1, 20, 20, "-"));
/* 711 */     this.field_146292_n.add(new GuiButton(109, posX + DX2, posY + y1 + 22, 20, 20, "-"));
/* 712 */     this.field_146292_n.add(new GuiButton(110, posX + DX2, posY + y1 + 44, 20, 20, "-"));
/* 713 */     this.field_146292_n.add(new GuiButton(111, posX + DX2, posY + y1 + 66, 20, 20, "-"));
/* 714 */     this.field_146292_n.add(new GuiButton(112, posX + DX2, posY + y1 + 88, 20, 20, "-"));
/* 715 */     this.field_146292_n.add(new GuiButton(113, posX + DX2, posY + y1 + 110, 20, 20, "-"));
/* 716 */     this.field_146292_n.add(new GuiButton(114, posX + DX2, posY + y1 + 132, 20, 20, "-"));
/* 717 */     this.field_146292_n.add(new GuiButton(115, posX + DX2, posY + y1 + 154, 20, 20, "-"));
/*     */     
/* 719 */     this.field_146292_n.add(new GuiButton(116, posX + DX3 - 11, posY + y1, 102, 20, "Enabled: " + (this.beam_enabled ? "on" : "off")));
/* 720 */     this.field_146292_n.add(new GuiButton(117, posX + DX3 - 11, posY + y1 + 22, 102, 20, "Render Core: " + (this.render_core ? "on" : "off")));
/*     */ 
/*     */     
/* 723 */     this.field_146292_n.add(new GuiButton(127, posX + DX3 - 11, posY + y1 + 154, 102, 20, "Take note of values"));
/*     */   }
/*     */   
/*     */   private void beamActions(GuiButton button) {
/* 727 */     int value = 1;
/*     */     
/* 729 */     short packetValue = 0;
/* 730 */     if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) value = 10; 
/* 731 */     if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) value = 100; 
/* 732 */     if ((Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54))) {
/* 733 */       value = 1000;
/*     */     }
/* 735 */     float value_F = value / 100.0F;
/*     */     
/* 737 */     switch (button.field_146127_k) {
/*     */       case 100:
/* 739 */         this.beam_red = Math.min(this.beam_red + value, 255);
/* 740 */         packetValue = (short)this.beam_red;
/*     */         break;
/*     */       case 101:
/* 743 */         this.beam_green = Math.min(this.beam_green + value, 255);
/* 744 */         packetValue = (short)this.beam_green;
/*     */         break;
/*     */       case 102:
/* 747 */         this.beam_blue = Math.min(this.beam_blue + value, 255);
/* 748 */         packetValue = (short)this.beam_blue;
/*     */         break;
/*     */       case 103:
/* 751 */         this.beam_pitch = Math.min(this.beam_pitch + value_F, 180.0F);
/* 752 */         packetValue = (short)(int)(this.beam_pitch * 100.0F);
/*     */         break;
/*     */       case 104:
/* 755 */         this.beam_yaw = Math.min(this.beam_yaw + value_F, 180.0F);
/* 756 */         packetValue = (short)(int)(this.beam_yaw * 100.0F);
/*     */         break;
/*     */       case 105:
/* 759 */         this.beam_length = Math.min(this.beam_length + value_F, 320.0F);
/* 760 */         packetValue = (short)(int)(this.beam_length * 100.0F);
/*     */         break;
/*     */       case 106:
/* 763 */         this.beam_rotation = Math.min(this.beam_rotation + value_F, 1.0F);
/* 764 */         packetValue = (short)(int)(this.beam_rotation * 100.0F);
/*     */         break;
/*     */       case 107:
/* 767 */         this.beam_scale = Math.min(this.beam_scale + value_F, 5.0F);
/* 768 */         packetValue = (short)(int)(this.beam_scale * 100.0F);
/*     */         break;
/*     */       case 108:
/* 771 */         this.beam_red = Math.max(this.beam_red - value, 0);
/* 772 */         packetValue = (short)this.beam_red;
/*     */         break;
/*     */       case 109:
/* 775 */         this.beam_green = Math.max(this.beam_green - value, 0);
/* 776 */         packetValue = (short)this.beam_green;
/*     */         break;
/*     */       case 110:
/* 779 */         this.beam_blue = Math.max(this.beam_blue - value, 0);
/* 780 */         packetValue = (short)this.beam_blue;
/*     */         break;
/*     */       case 111:
/* 783 */         this.beam_pitch = Math.max(this.beam_pitch - value_F, -180.0F);
/* 784 */         packetValue = (short)(int)(this.beam_pitch * 100.0F);
/*     */         break;
/*     */       case 112:
/* 787 */         this.beam_yaw = Math.max(this.beam_yaw - value_F, -180.0F);
/* 788 */         packetValue = (short)(int)(this.beam_yaw * 100.0F);
/*     */         break;
/*     */       case 113:
/* 791 */         this.beam_length = Math.max(this.beam_length - value_F, -0.0F);
/* 792 */         packetValue = (short)(int)(this.beam_length * 100.0F);
/*     */         break;
/*     */       case 114:
/* 795 */         this.beam_rotation = Math.max(this.beam_rotation - value_F, -1.0F);
/* 796 */         packetValue = (short)(int)(this.beam_rotation * 100.0F);
/*     */         break;
/*     */       case 115:
/* 799 */         this.beam_scale = Math.max(this.beam_scale - value_F, -0.0F);
/* 800 */         packetValue = (short)(int)(this.beam_scale * 100.0F);
/*     */         break;
/*     */       case 116:
/* 803 */         this.beam_enabled = !this.beam_enabled;
/* 804 */         packetValue = this.beam_enabled ? 1 : 0;
/* 805 */         func_73866_w_();
/*     */         break;
/*     */       case 117:
/* 808 */         this.render_core = !this.render_core;
/* 809 */         packetValue = this.render_core ? 1 : 0;
/* 810 */         func_73866_w_();
/*     */         break;
/*     */     } 
/*     */     
/* 814 */     DraconicEvolution.network.sendToServer((IMessage)new ParticleGenPacket((byte)button.field_146127_k, packetValue, this.tile.field_145851_c, this.tile.field_145848_d, this.tile.field_145849_e));
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\GUIParticleGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */