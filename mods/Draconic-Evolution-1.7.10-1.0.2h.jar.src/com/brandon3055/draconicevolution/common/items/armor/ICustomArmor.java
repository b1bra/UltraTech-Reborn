package com.brandon3055.draconicevolution.common.items.armor;

import com.brandon3055.draconicevolution.api.IDraconicElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ICustomArmor extends IDraconicElectricItem {
  float getProtectionPoints(ItemStack paramItemStack);
  
  int getRecoveryPoints(ItemStack paramItemStack);
  
  float getSpeedModifier(ItemStack paramItemStack, EntityPlayer paramEntityPlayer);
  
  float getJumpModifier(ItemStack paramItemStack, EntityPlayer paramEntityPlayer);
  
  boolean hasHillStep(ItemStack paramItemStack, EntityPlayer paramEntityPlayer);
  
  float getFireResistance(ItemStack paramItemStack);
  
  boolean[] hasFlight(ItemStack paramItemStack);
  
  float getFlightSpeedModifier(ItemStack paramItemStack, EntityPlayer paramEntityPlayer);
  
  float getFlightVModifier(ItemStack paramItemStack, EntityPlayer paramEntityPlayer);
  
  int getEnergyPerProtectionPoint();
}


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\armor\ICustomArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */