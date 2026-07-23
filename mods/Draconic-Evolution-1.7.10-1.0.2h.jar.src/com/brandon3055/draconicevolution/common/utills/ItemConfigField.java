/*     */ package com.brandon3055.draconicevolution.common.utills;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.DataUtills;
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.network.ItemConfigPacket;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.util.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemConfigField
/*     */ {
/*     */   public Object value;
/*     */   public int slot;
/*     */   public int datatype;
/*     */   public String name;
/*     */   public int fieldid;
/*     */   public Object max;
/*     */   public Object min;
/*     */   public Object incroment;
/*     */   public String modifier;
/*     */   
/*     */   public ItemConfigField(int datatype, int slot, String name) {
/*  29 */     this.slot = slot;
/*  30 */     this.datatype = datatype;
/*  31 */     this.name = name;
/*     */   }
/*     */   
/*     */   public ItemConfigField(int datatype, Object value, int slot, String name) {
/*  35 */     this.value = value;
/*  36 */     this.slot = slot;
/*  37 */     this.datatype = datatype;
/*  38 */     this.name = name;
/*     */   }
/*     */   
/*     */   public ItemConfigField setMinMaxAndIncromente(Object min, Object max, Object incroment) {
/*  42 */     this.max = max;
/*  43 */     this.min = min;
/*  44 */     this.incroment = incroment;
/*  45 */     return this;
/*     */   }
/*     */   
/*     */   public String getLocalizedName() {
/*  49 */     return StatCollector.func_74838_a("button.de." + this.name + ".name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemConfigField readFromItem(ItemStack stack, Object defaultExpected) {
/*  57 */     this.value = DataUtills.readObjectFromCompound(IConfigurableItem.ProfileHelper.getProfileCompound(stack), this.datatype, this.name, defaultExpected);
/*  58 */     return this;
/*     */   }
/*     */   
/*     */   public ItemConfigField setModifier(String modifier) {
/*  62 */     this.modifier = modifier;
/*  63 */     return this;
/*     */   }
/*     */   
/*     */   public String getFormattedValue() {
/*  67 */     if (this.datatype == 2 && !StringUtils.func_151246_b(this.modifier) && this.modifier.equals("AOE")) {
/*  68 */       int i = ((Integer)this.value).intValue();
/*  69 */       i *= 2;
/*  70 */       return (i + 1) + "x" + (i + 1);
/*  71 */     }  if (this.datatype == 6)
/*  72 */       return ((Boolean)this.value).booleanValue() ? StatCollector.func_74838_a("gui.de.on.txt") : StatCollector.func_74838_a("gui.de.off.txt"); 
/*  73 */     if (this.datatype == 4 && !StringUtils.func_151246_b(this.modifier) && this.modifier.equals("PERCENT"))
/*  74 */       return Math.round(((Float)this.value).floatValue() * 100.0D) + "%"; 
/*  75 */     if (this.datatype == 4 && !StringUtils.func_151246_b(this.modifier) && this.modifier.equals("PLUSPERCENT")) {
/*  76 */       return "+" + Math.round(((Float)this.value).floatValue() * 100.0D) + "%";
/*     */     }
/*  78 */     return String.valueOf(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMaxFormattedValue() {
/*  83 */     if (this.datatype == 2 && !StringUtils.func_151246_b(this.modifier) && this.modifier.equals("AOE")) {
/*  84 */       int i = ((Integer)this.max).intValue();
/*  85 */       i *= 2;
/*  86 */       return (i + 1) + "x" + (i + 1);
/*     */     } 
/*  88 */     return String.valueOf(this.max);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTooltipInfo() {
/*  93 */     return InfoHelper.ITC() + getLocalizedName() + ": " + InfoHelper.HITC() + getFormattedValue();
/*     */   }
/*     */   
/*     */   public void sendChanges() {
/*  97 */     DraconicEvolution.network.sendToServer((IMessage)new ItemConfigPacket(this)); } public int castToInt() {
/*     */     long l;
/*     */     float f;
/*     */     double d;
/* 101 */     switch (this.datatype) {
/*     */       case 0:
/* 103 */         return ((Byte)this.value).byteValue();
/*     */       case 1:
/* 105 */         return ((Short)this.value).shortValue();
/*     */       case 2:
/* 107 */         return ((Integer)this.value).intValue();
/*     */       case 3:
/* 109 */         l = ((Long)this.value).longValue();
/* 110 */         return (int)l;
/*     */       case 4:
/* 112 */         f = ((Float)this.value).floatValue();
/* 113 */         return (int)f;
/*     */       case 5:
/* 115 */         d = ((Double)this.value).doubleValue();
/* 116 */         return (int)d;
/*     */       case 6:
/* 118 */         return ((Boolean)this.value).booleanValue() ? 1 : 0;
/*     */     } 
/* 120 */     return 0;
/*     */   }
/*     */   public double castToDouble() {
/*     */     long l;
/* 124 */     switch (this.datatype) {
/*     */       case 0:
/* 126 */         return ((Byte)this.value).byteValue();
/*     */       case 1:
/* 128 */         return ((Short)this.value).shortValue();
/*     */       case 2:
/* 130 */         return ((Integer)this.value).intValue();
/*     */       case 3:
/* 132 */         l = ((Long)this.value).longValue();
/* 133 */         return l;
/*     */       case 4:
/* 135 */         return ((Float)this.value).floatValue();
/*     */       case 5:
/* 137 */         return ((Double)this.value).doubleValue();
/*     */       case 6:
/* 139 */         return ((Boolean)this.value).booleanValue() ? 1.0D : 0.0D;
/*     */     } 
/* 141 */     return 0.0D;
/*     */   }
/*     */   public double castMinToDouble() {
/*     */     long l;
/* 145 */     switch (this.datatype) {
/*     */       case 0:
/* 147 */         return ((Byte)this.min).byteValue();
/*     */       case 1:
/* 149 */         return ((Short)this.min).shortValue();
/*     */       case 2:
/* 151 */         return ((Integer)this.min).intValue();
/*     */       case 3:
/* 153 */         l = ((Long)this.min).longValue();
/* 154 */         return l;
/*     */       case 4:
/* 156 */         return ((Float)this.min).floatValue();
/*     */       case 5:
/* 158 */         return ((Double)this.min).doubleValue();
/*     */       case 6:
/* 160 */         return 0.0D;
/*     */     } 
/* 162 */     return 0.0D;
/*     */   }
/*     */   public double castMaxToDouble() {
/*     */     long l;
/* 166 */     switch (this.datatype) {
/*     */       case 0:
/* 168 */         return ((Byte)this.max).byteValue();
/*     */       case 1:
/* 170 */         return ((Short)this.max).shortValue();
/*     */       case 2:
/* 172 */         return ((Integer)this.max).intValue();
/*     */       case 3:
/* 174 */         l = ((Long)this.max).longValue();
/* 175 */         return l;
/*     */       case 4:
/* 177 */         return ((Float)this.max).floatValue();
/*     */       case 5:
/* 179 */         return ((Double)this.max).doubleValue();
/*     */       case 6:
/* 181 */         return 1.0D;
/*     */     } 
/* 183 */     return 0.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\commo\\utills\ItemConfigField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */