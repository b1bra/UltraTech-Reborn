package net.foxmcloud.draconicadditions.client.creativetab;

import com.brandon3055.draconicevolution.client.creativetab.DETab;

import net.foxmcloud.draconicadditions.DAFeatures;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DATab extends DETab {
	private String label;
	private int tab;

	static ItemStack itemStackChaotic = null;

	public DATab(String modid, String label, int tab) {
		super(modid, label, tab);
		this.label = label;
		this.tab = tab;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack() {
		if ((itemStackChaotic == null || itemStackChaotic.stackSize <= 0)) {
			if (DAFeatures.chaoticHelm != null) {
				itemStackChaotic = new ItemStack(DAFeatures.chaoticHelm);
				DAFeatures.chaoticHelm.modifyEnergy(itemStackChaotic, DAFeatures.chaoticHelm.getMaxEnergyStored(itemStackChaotic));
			}
			else if (DAFeatures.chaoticEnergyCore != null) {
				itemStackChaotic = new ItemStack(DAFeatures.chaoticEnergyCore);
			}
			else itemStackChaotic = new ItemStack(Items.ENDER_EYE);
		}
		return itemStackChaotic;
	}
}
