package com.brandon3055.draconicevolution.common.utills;

import net.minecraft.enchantment.Enchantment;

public interface IInventoryTool extends IConfigurableItem {
  String getInventoryName();
  
  int getInventorySlots();
  
  boolean isEnchantValid(Enchantment paramEnchantment);
}


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\commo\\utills\IInventoryTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */