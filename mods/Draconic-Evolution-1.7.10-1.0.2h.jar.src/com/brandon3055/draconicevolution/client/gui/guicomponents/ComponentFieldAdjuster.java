/*     */ package com.brandon3055.draconicevolution.client.gui.guicomponents;
/*     */ 
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentBase;
/*     */ import com.brandon3055.brandonscore.client.utills.GuiHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.DataUtills;
/*     */ import com.brandon3055.draconicevolution.client.gui.componentguis.GUIToolConfig;
/*     */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ 
/*     */ public class ComponentFieldAdjuster
/*     */   extends ComponentBase
/*     */ {
/*  20 */   private static final ResourceLocation widgets = new ResourceLocation("draconicevolution", "textures/gui/Widgets.png");
/*  21 */   protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
/*     */   
/*     */   public ItemConfigField field;
/*     */   public GUIToolConfig gui;
/*     */   
/*     */   public ComponentFieldAdjuster(int x, int y, ItemConfigField field, GUIToolConfig gui) {
/*  27 */     super(x, y);
/*  28 */     this.field = field;
/*  29 */     this.gui = gui;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  34 */     return 190;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  39 */     return 20;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/*  44 */     if (this.field == null)
/*  45 */       return;  minecraft.func_110434_K().func_110577_a(buttonTextures);
/*     */     
/*  47 */     if (isBoolean()) {
/*  48 */       renderButton(getWidth() / 2 - 30, 0, 60, 20, GuiHelper.isInRect(this.x + getWidth() / 2 - 30, this.y, 60, 20, mouseX - offsetX, mouseY - offsetY));
/*  49 */     } else if (isDecimal() || isNonDecimal()) {
/*  50 */       renderButton(getWidth() / 2 - 43, 3, 24, 14, GuiHelper.isInRect(this.x + getWidth() / 2 - 43, this.y + 3, 24, 14, mouseX - offsetX, mouseY - offsetY));
/*  51 */       renderButton(getWidth() / 2 - 69, 3, 24, 14, GuiHelper.isInRect(this.x + getWidth() / 2 - 69, this.y + 3, 24, 14, mouseX - offsetX, mouseY - offsetY));
/*  52 */       renderButton(getWidth() / 2 - 95, 3, 24, 14, GuiHelper.isInRect(this.x + getWidth() / 2 - 95, this.y + 3, 24, 14, mouseX - offsetX, mouseY - offsetY));
/*     */       
/*  54 */       renderButton(getWidth() / 2 + 19, 3, 24, 14, GuiHelper.isInRect(this.x + getWidth() / 2 + 19, this.y + 3, 24, 14, mouseX - offsetX, mouseY - offsetY));
/*  55 */       renderButton(getWidth() / 2 + 45, 3, 24, 14, GuiHelper.isInRect(this.x + getWidth() / 2 + 45, this.y + 3, 24, 14, mouseX - offsetX, mouseY - offsetY));
/*  56 */       renderButton(getWidth() / 2 + 71, 3, 24, 14, GuiHelper.isInRect(this.x + getWidth() / 2 + 71, this.y + 3, 24, 14, mouseX - offsetX, mouseY - offsetY));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderForground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/*  62 */     if (this.field == null)
/*  63 */       return;  String fieldName = this.field.getLocalizedName();
/*  64 */     String fieldValue = this.field.getFormattedValue();
/*  65 */     if (this.field.datatype == 5) {
/*  66 */       double d = ((Double)this.field.value).doubleValue();
/*  67 */       fieldValue = String.valueOf(Math.round(d * 100.0D) / 100.0D);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     int centre = this.fontRendererObj.func_78256_a(fieldName) / 2;
/*  75 */     this.fontRendererObj.func_78276_b(fieldName, this.x + getWidth() / 2 - centre, this.y - 12, 0);
/*  76 */     func_73732_a(this.fontRendererObj, fieldValue, this.x + getWidth() / 2, this.y + 6, 16777215);
/*  77 */     if (this.field.modifier == null || this.field.modifier.equals("AOE")) {
/*  78 */       func_73732_a(this.fontRendererObj, StatCollector.func_74838_a("gui.de.max.txt") + " " + this.field.getMaxFormattedValue(), this.x + getWidth() / 2, this.y + 20, 16777215);
/*     */     }
/*  80 */     if (isDecimal() || isNonDecimal()) {
/*  81 */       this.fontRendererObj.func_78276_b("---", 7, this.y + 6, 0);
/*  82 */       this.fontRendererObj.func_78276_b("--", 37, this.y + 6, 0);
/*  83 */       this.fontRendererObj.func_78276_b("-", 66, this.y + 6, 0);
/*  84 */       this.fontRendererObj.func_78276_b("+", 127, this.y + 6, 0);
/*  85 */       this.fontRendererObj.func_78276_b("++", 151, this.y + 6, 0);
/*  86 */       this.fontRendererObj.func_78276_b("+++", 174, this.y + 6, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderFinal(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/*  93 */     if (this.field == null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(int x, int y, int button) {
/*  99 */     if (this.field == null)
/*     */       return; 
/* 101 */     if (!isBoolean()) {
/* 102 */       if (GuiHelper.isInRect(this.x + getWidth() / 2 - 43, this.y + 3, 26, 14, x, y)) {
/* 103 */         incroment(-1);
/* 104 */       } else if (GuiHelper.isInRect(this.x + getWidth() / 2 - 69, this.y + 3, 26, 14, x, y)) {
/* 105 */         incroment(-10);
/* 106 */       } else if (GuiHelper.isInRect(this.x + getWidth() / 2 - 95, this.y + 3, 26, 14, x, y)) {
/* 107 */         incroment(-100);
/* 108 */       } else if (GuiHelper.isInRect(this.x + getWidth() / 2 + 19, this.y + 3, 26, 14, x, y)) {
/* 109 */         incroment(1);
/* 110 */       } else if (GuiHelper.isInRect(this.x + getWidth() / 2 + 45, this.y + 3, 26, 14, x, y)) {
/* 111 */         incroment(10);
/* 112 */       } else if (GuiHelper.isInRect(this.x + getWidth() / 2 + 71, this.y + 3, 26, 14, x, y)) {
/* 113 */         incroment(100);
/*     */       } 
/* 115 */     } else if (GuiHelper.isInRect(this.x + getWidth() / 2 - 30, this.y, 60, 20, x, y)) {
/* 116 */       incroment(1);
/*     */     }  } private void incroment(int multiplyer) { byte b; short s; int i;
/*     */     long l;
/*     */     float f;
/*     */     double d;
/* 121 */     switch (this.field.datatype) {
/*     */       case 0:
/* 123 */         b = ((Byte)this.field.value).byteValue();
/* 124 */         b = (byte)(b + ((Byte)this.field.incroment).byteValue() * (byte)multiplyer);
/* 125 */         if (b > ((Byte)this.field.max).byteValue()) b = ((Byte)this.field.max).byteValue(); 
/* 126 */         if (b < ((Byte)this.field.min).byteValue()) b = ((Byte)this.field.min).byteValue(); 
/* 127 */         this.field.value = Byte.valueOf(b);
/*     */         break;
/*     */       case 1:
/* 130 */         s = ((Short)this.field.value).shortValue();
/* 131 */         s = (short)(s + ((Short)this.field.incroment).shortValue() * (short)multiplyer);
/* 132 */         if (s > ((Short)this.field.max).shortValue()) s = ((Short)this.field.max).shortValue(); 
/* 133 */         if (s < ((Short)this.field.min).shortValue()) s = ((Short)this.field.min).shortValue(); 
/* 134 */         this.field.value = Short.valueOf(s);
/*     */         break;
/*     */       case 2:
/* 137 */         i = ((Integer)this.field.value).intValue();
/* 138 */         i += ((Integer)this.field.incroment).intValue() * multiplyer;
/* 139 */         if (i > ((Integer)this.field.max).intValue()) i = ((Integer)this.field.max).intValue(); 
/* 140 */         if (i < ((Integer)this.field.min).intValue()) i = ((Integer)this.field.min).intValue(); 
/* 141 */         this.field.value = Integer.valueOf(i);
/*     */         break;
/*     */       case 3:
/* 144 */         l = ((Long)this.field.value).longValue();
/* 145 */         l += ((Long)this.field.incroment).longValue() * multiplyer;
/* 146 */         if (l > ((Long)this.field.max).longValue()) l = ((Long)this.field.max).longValue(); 
/* 147 */         if (l < ((Long)this.field.min).longValue()) l = ((Long)this.field.min).longValue(); 
/* 148 */         this.field.value = Long.valueOf(l);
/*     */         break;
/*     */       case 4:
/* 151 */         f = ((Float)this.field.value).floatValue();
/* 152 */         f += ((Float)this.field.incroment).floatValue() * multiplyer;
/* 153 */         f *= 100.0F;
/* 154 */         f = Math.round(f);
/* 155 */         f /= 100.0F;
/* 156 */         if (f > ((Float)this.field.max).floatValue()) f = ((Float)this.field.max).floatValue(); 
/* 157 */         if (f < ((Float)this.field.min).floatValue()) f = ((Float)this.field.min).floatValue(); 
/* 158 */         this.field.value = Float.valueOf(f);
/*     */         break;
/*     */       case 5:
/* 161 */         d = ((Double)this.field.value).doubleValue();
/* 162 */         d += ((Double)this.field.incroment).doubleValue() * multiplyer;
/* 163 */         if (d > ((Double)this.field.max).doubleValue()) d = ((Double)this.field.max).doubleValue(); 
/* 164 */         if (d < ((Double)this.field.min).doubleValue()) d = ((Double)this.field.min).doubleValue(); 
/* 165 */         this.field.value = Double.valueOf(d);
/*     */         break;
/*     */       case 6:
/* 168 */         this.field.value = Boolean.valueOf(!((Boolean)this.field.value).booleanValue());
/*     */         break;
/*     */     } 
/* 171 */     Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
/* 172 */     ItemStack stack = this.gui.player.field_71071_by.func_70301_a(this.field.slot);
/* 173 */     if (stack != null && stack.func_77973_b() instanceof IConfigurableItem) {
/* 174 */       DataUtills.writeObjectToCompound(IConfigurableItem.ProfileHelper.getProfileCompound(stack), this.field.value, this.field.datatype, this.field.name);
/*     */     }
/* 176 */     this.field.sendChanges(); }
/*     */ 
/*     */   
/*     */   public void renderButton(int offsetX, int offsetY, int xSize, int ySize, boolean highlighted) {
/* 180 */     int k = highlighted ? 2 : 1;
/* 181 */     int x = this.x + offsetX;
/* 182 */     int y = this.y + offsetY;
/*     */     
/* 184 */     func_73729_b(x, y, 0, 46 + k * 20, xSize / 2, 20 - Math.max(0, 20 - ySize));
/* 185 */     func_73729_b(x + xSize / 2, y, 200 - xSize / 2, 46 + k * 20, xSize / 2, 20 - Math.max(0, 20 - ySize));
/*     */     
/* 187 */     if (ySize < 20) {
/* 188 */       func_73729_b(x, y + 3, 0, 46 + k * 20 + 20 - ySize + 3, xSize - 1, ySize - 3);
/* 189 */       func_73729_b(x + xSize / 2, y + 3, 200 - xSize / 2, 46 + k * 20 + 20 - ySize + 3, xSize / 2, ySize - 3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isBoolean() {
/* 194 */     return (this.field.datatype == 6);
/*     */   }
/*     */   
/*     */   private boolean isNonDecimal() {
/* 198 */     return (this.field.datatype == 2 || this.field.datatype == 1 || this.field.datatype == 0);
/*     */   }
/*     */   
/*     */   private boolean isDecimal() {
/* 202 */     return (this.field.datatype == 4 || this.field.datatype == 5);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\guicomponents\ComponentFieldAdjuster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */