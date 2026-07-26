package net.foxmcloud.draconicadditions.blocks.tileentity;

import com.brandon3055.brandonscore.lib.IChangeListener;
import com.brandon3055.draconicevolution.lib.DESoundHandler;

import cofh.api.energy.IEnergyReceiver;
import net.foxmcloud.draconicadditions.items.IChaosContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class TileChaosInfuser extends TileChaosHolderBase implements IEnergyReceiver, IChangeListener {

	private int chargeRate = 1000000;
	public int maxCharge = 200;

	public boolean active = false;
	public boolean powered = false;

	public TileChaosInfuser() {
		setInventorySize(1);
		setEnergySyncMode().syncViaContainer();
		setCapacityAndTransfer(2000000000, 20000000, 20000000);
		setShouldRefreshOnBlockChange();
	}

	@Override
	public void updateEntity() {
		super.update();
		if (world.isRemote) {
			if (active) {
				float beamPitch = (float)(0.5F + (Math.random() * 0.1F));
				world.playSoundEffect(xCoord + 0.5D, yCoord, zCoord + 0.5D, DESoundHandler.beam, 0.2F, beamPitch);
			}
		}
		else {
			ItemStack stack = getStackInSlot(0);
			if ((stack != null && stack.stackSize > 0) && isItemValidForSlot(0, stack) && chaos > 0) {
				IChaosContainer chaosItem = (IChaosContainer)stack.getItem();
				if (chaosItem.getMaxChaos(stack) > 0 && chaosItem.getChaos(stack) < chaosItem.getMaxChaos(stack) && energyStorage.getEnergyStored() >= chargeRate) {
					active = true;
					energyStorage.modifyEnergyStored(-chargeRate);
					chaos += chaosItem.addChaos(stack, 1) - 1;
				}
				else active = false;
			}
			else active = false;
		}
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (stack.getItem() instanceof IChaosContainer) {
			return true;
		}
		else return false;
	}

	@Override
	public void onNeighborChange(int x, int y, int z) {
		powered = world.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}
}
