package com.brandon3055.draconicevolution.common.utills;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface ICustomItemData {
  public static final String tagName = "TileCompound";
  
  void writeDataToItem(NBTTagCompound paramNBTTagCompound, ItemStack paramItemStack);
  
  void readDataFromItem(NBTTagCompound paramNBTTagCompound, ItemStack paramItemStack);
}


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\commo\\utills\ICustomItemData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */