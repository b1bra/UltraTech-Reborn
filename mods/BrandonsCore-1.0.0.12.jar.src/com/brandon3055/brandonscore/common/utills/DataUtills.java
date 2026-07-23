/*     */ package com.brandon3055.brandonscore.common.utills;
/*     */ 
/*     */ import cpw.mods.fml.common.network.ByteBufUtils;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.text.DecimalFormat;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DataUtills
/*     */ {
/*  16 */   public static DataUtills instance = new DataUtills();
/*     */   
/*     */   public void writeObjectToBytes(ByteBuf bytes, int dataType, Object object) {
/*  19 */     switch (dataType) {
/*     */       case 0:
/*  21 */         bytes.writeByte(((Byte)object).byteValue());
/*     */         break;
/*     */       case 1:
/*  24 */         bytes.writeShort(((Short)object).shortValue());
/*     */         break;
/*     */       case 2:
/*  27 */         bytes.writeInt(((Integer)object).intValue());
/*     */         break;
/*     */       case 3:
/*  30 */         bytes.writeLong(((Long)object).longValue());
/*     */         break;
/*     */       case 4:
/*  33 */         bytes.writeFloat(((Float)object).floatValue());
/*     */         break;
/*     */       case 5:
/*  36 */         bytes.writeDouble(((Double)object).doubleValue());
/*     */         break;
/*     */       case 7:
/*  39 */         bytes.writeChar(((Character)object).charValue());
/*     */         break;
/*     */       case 8:
/*  42 */         ByteBufUtils.writeUTF8String(bytes, (String)object);
/*     */         break;
/*     */       case 6:
/*  45 */         bytes.writeBoolean(((Boolean)object).booleanValue());
/*     */         break;
/*     */       case 9:
/*  48 */         bytes.writeInt(((IntPair)object).i1);
/*  49 */         bytes.writeInt(((IntPair)object).i2);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public Object readObjectFromBytes(ByteBuf bytes, int dataType) {
/*     */     IntPair tx;
/*  55 */     switch (dataType) {
/*     */       case 0:
/*  57 */         return Byte.valueOf(bytes.readByte());
/*     */       case 1:
/*  59 */         return Short.valueOf(bytes.readShort());
/*     */       case 2:
/*  61 */         return Integer.valueOf(bytes.readInt());
/*     */       case 3:
/*  63 */         return Long.valueOf(bytes.readLong());
/*     */       case 4:
/*  65 */         return Float.valueOf(bytes.readFloat());
/*     */       case 5:
/*  67 */         return Double.valueOf(bytes.readDouble());
/*     */       case 7:
/*  69 */         return Character.valueOf(bytes.readChar());
/*     */       case 8:
/*  71 */         return ByteBufUtils.readUTF8String(bytes);
/*     */       case 6:
/*  73 */         return Boolean.valueOf(bytes.readBoolean());
/*     */       case 9:
/*  75 */         tx = new IntPair(0, 0);
/*  76 */         tx.i1 = bytes.readInt();
/*  77 */         tx.i2 = bytes.readInt();
/*  78 */         return tx;
/*     */     } 
/*  80 */     return null;
/*     */   }
/*     */   
/*     */   public static void writeObjectToItem(ItemStack stack, Object value, int datatype, String name) {
/*  84 */     switch (datatype) {
/*     */       case 0:
/*  86 */         ItemNBTHelper.setByte(stack, name, ((Byte)value).byteValue());
/*     */         break;
/*     */       case 1:
/*  89 */         ItemNBTHelper.setShort(stack, name, ((Short)value).shortValue());
/*     */         break;
/*     */       case 2:
/*  92 */         ItemNBTHelper.setInteger(stack, name, ((Integer)value).intValue());
/*     */         break;
/*     */       case 3:
/*  95 */         ItemNBTHelper.setLong(stack, name, ((Long)value).longValue());
/*     */         break;
/*     */       case 4:
/*  98 */         ItemNBTHelper.setFloat(stack, name, ((Float)value).floatValue());
/*     */         break;
/*     */       case 5:
/* 101 */         ItemNBTHelper.setDouble(stack, name, ((Double)value).doubleValue());
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8:
/* 107 */         ItemNBTHelper.setString(stack, name, (String)value);
/*     */         break;
/*     */       case 6:
/* 110 */         ItemNBTHelper.setBoolean(stack, name, ((Boolean)value).booleanValue());
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeObjectToCompound(NBTTagCompound compound, Object value, int datatype, String name) {
/* 116 */     switch (datatype) {
/*     */       case 0:
/* 118 */         compound.func_74774_a(name, ((Byte)value).byteValue());
/*     */         break;
/*     */       case 1:
/* 121 */         compound.func_74777_a(name, ((Short)value).shortValue());
/*     */         break;
/*     */       case 2:
/* 124 */         compound.func_74768_a(name, ((Integer)value).intValue());
/*     */         break;
/*     */       case 3:
/* 127 */         compound.func_74772_a(name, ((Long)value).longValue());
/*     */         break;
/*     */       case 4:
/* 130 */         compound.func_74776_a(name, ((Float)value).floatValue());
/*     */         break;
/*     */       case 5:
/* 133 */         compound.func_74780_a(name, ((Double)value).doubleValue());
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8:
/* 139 */         compound.func_74778_a(name, (String)value);
/*     */         break;
/*     */       case 6:
/* 142 */         compound.func_74757_a(name, ((Boolean)value).booleanValue());
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object readObjectFromItem(ItemStack stack, int dataType, String name, Object defaultExpected) {
/* 148 */     switch (dataType) {
/*     */       case 0:
/* 150 */         return Byte.valueOf(ItemNBTHelper.getByte(stack, name, ((Byte)defaultExpected).byteValue()));
/*     */       case 1:
/* 152 */         return Short.valueOf(ItemNBTHelper.getShort(stack, name, ((Short)defaultExpected).shortValue()));
/*     */       case 2:
/* 154 */         return Integer.valueOf(ItemNBTHelper.getInteger(stack, name, ((Integer)defaultExpected).intValue()));
/*     */       case 3:
/* 156 */         return Long.valueOf(ItemNBTHelper.getLong(stack, name, ((Long)defaultExpected).longValue()));
/*     */       case 4:
/* 158 */         return Float.valueOf(ItemNBTHelper.getFloat(stack, name, ((Float)defaultExpected).floatValue()));
/*     */       case 5:
/* 160 */         return Double.valueOf(ItemNBTHelper.getDouble(stack, name, ((Double)defaultExpected).doubleValue()));
/*     */       
/*     */       case 8:
/* 163 */         return ItemNBTHelper.getString(stack, name, (String)defaultExpected);
/*     */       case 6:
/* 165 */         return Boolean.valueOf(ItemNBTHelper.getBoolean(stack, name, ((Boolean)defaultExpected).booleanValue()));
/*     */     } 
/* 167 */     return null;
/*     */   }
/*     */   
/*     */   public static Object readObjectFromItem(ItemStack stack, int dataType, String name) {
/* 171 */     switch (dataType) {
/*     */       case 0:
/* 173 */         return Byte.valueOf(ItemNBTHelper.getByte(stack, name, (byte)0));
/*     */       case 1:
/* 175 */         return Short.valueOf(ItemNBTHelper.getShort(stack, name, (short)0));
/*     */       case 2:
/* 177 */         return Integer.valueOf(ItemNBTHelper.getInteger(stack, name, 0));
/*     */       case 3:
/* 179 */         return Long.valueOf(ItemNBTHelper.getLong(stack, name, 0L));
/*     */       case 4:
/* 181 */         return Float.valueOf(ItemNBTHelper.getFloat(stack, name, 0.0F));
/*     */       case 5:
/* 183 */         return Double.valueOf(ItemNBTHelper.getDouble(stack, name, 0.0D));
/*     */       
/*     */       case 8:
/* 186 */         return ItemNBTHelper.getString(stack, name, "");
/*     */       case 6:
/* 188 */         return Boolean.valueOf(ItemNBTHelper.getBoolean(stack, name, false));
/*     */     } 
/* 190 */     return null;
/*     */   }
/*     */   
/*     */   public static Object readObjectFromCompound(NBTTagCompound compound, int dataType, String name, Object defaultExpected) {
/* 194 */     switch (dataType) {
/*     */       case 0:
/* 196 */         return Byte.valueOf(compound.func_74764_b(name) ? compound.func_74771_c(name) : ((Byte)defaultExpected).byteValue());
/*     */       case 1:
/* 198 */         return Short.valueOf(compound.func_74764_b(name) ? compound.func_74765_d(name) : ((Short)defaultExpected).shortValue());
/*     */       case 2:
/* 200 */         return Integer.valueOf(compound.func_74764_b(name) ? compound.func_74762_e(name) : ((Integer)defaultExpected).intValue());
/*     */       case 3:
/* 202 */         return Long.valueOf(compound.func_74764_b(name) ? compound.func_74763_f(name) : ((Long)defaultExpected).longValue());
/*     */       case 4:
/* 204 */         return Float.valueOf(compound.func_74764_b(name) ? compound.func_74760_g(name) : ((Float)defaultExpected).floatValue());
/*     */       case 5:
/* 206 */         return Double.valueOf(compound.func_74764_b(name) ? compound.func_74769_h(name) : ((Double)defaultExpected).doubleValue());
/*     */       
/*     */       case 8:
/* 209 */         return compound.func_74764_b(name) ? compound.func_74779_i(name) : defaultExpected;
/*     */       case 6:
/* 211 */         return Boolean.valueOf(compound.func_74764_b(name) ? compound.func_74767_n(name) : ((Boolean)defaultExpected).booleanValue());
/*     */     } 
/* 213 */     return null;
/*     */   }
/*     */   
/*     */   public static class IntPair {
/*     */     public int i1;
/*     */     public int i2;
/*     */     
/*     */     public IntPair(int i1, int i2) {
/* 221 */       this.i1 = i1;
/* 222 */       this.i2 = i2;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class XZPair<X, Z> {
/*     */     public X x;
/*     */     public Z z;
/*     */     
/*     */     public XZPair(X x, Z z) {
/* 231 */       this.x = x;
/* 232 */       this.z = z;
/*     */     }
/*     */     
/*     */     public X getKey() {
/* 236 */       return this.x;
/*     */     }
/*     */     
/*     */     public Z getValue() {
/* 240 */       return this.z;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class XYZTri<X, Y, Z> {
/*     */     public X x;
/*     */     public Y y;
/*     */     public Z z;
/*     */     
/*     */     public XYZTri(X x, Y y, Z z) {
/* 250 */       this.x = x;
/* 251 */       this.y = y;
/* 252 */       this.z = z;
/*     */     }
/*     */   }
/*     */   
/*     */   public static String formatFileSize(long size) {
/* 257 */     if (size <= 0L) return "0"; 
/* 258 */     String[] units = { "B", "kB", "MB", "GB", "TB" };
/* 259 */     int digitGroups = (int)(Math.log10(size) / Math.log10(1024.0D));
/* 260 */     return (new DecimalFormat("#,##0.#")).format(size / Math.pow(1024.0D, digitGroups)) + " " + units[digitGroups];
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\commo\\utills\DataUtills.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */