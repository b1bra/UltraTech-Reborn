package net.foxmcloud.draconicadditions.blocks.tileentity;

import com.brandon3055.brandonscore.block.TileEnergyInventoryBase;

public class TileChaosHolderBase extends TileEnergyInventoryBase {

	private int maxChaos = 2000;

	public int chaos = 0;

	public int getMaxChaos() {
		return maxChaos;
	}

	public void setMaxChaos(int amount) {
		maxChaos = amount;
	}
}
