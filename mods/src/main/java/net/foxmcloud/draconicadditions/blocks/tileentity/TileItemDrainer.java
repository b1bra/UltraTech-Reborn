package net.foxmcloud.draconicadditions.blocks.tileentity;

import com.brandon3055.brandonscore.lib.IChangeListener;
import com.brandon3055.brandonscore.util.ItemNBTHelper;
import com.brandon3055.draconicevolution.lib.DESoundHandler;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import scala.Int;

public class TileItemDrainer extends TileChaosHolderBase implements IEnergyProvider, IChangeListener {

	private int cooldownRatio = 100000;
	private boolean clientPlayedSound = true;

	public int cooldownTime = 1;
	public int cooldownTimeRemaining = 0;
	public int fakeCapacity = 0;
	public boolean active = false;
	public boolean powered = false;

	public TileItemDrainer() {
		setInventorySize(1);
		setEnergySyncMode().syncViaContainer();
		setCapacityAndTransfer(Int.MaxValue(), 0, 1000000);
		setShouldRefreshOnBlockChange();
		setMaxChaos(0);
	}

	@Override
	public void updateEntity() {
		super.update();
		if (world.isRemote) {
			if (active && !clientPlayedSound) {
				world.playSoundEffect(xCoord + 0.5D, yCoord, zCoord + 0.5D, DESoundHandler.boom, 1.0F, 2.0F);
				clientPlayedSound = true;
			}
			else if (!active && clientPlayedSound) {
				clientPlayedSound = false;
			}
			return;
		}
		active = cooldownTimeRemaining > 2;
		if (cooldownTimeRemaining > 0) {
			cooldownTimeRemaining -= 1;
		}
		if (cooldownTimeRemaining == 0 && cooldownTime > 0) {
			cooldownTime = 0;
		}
		if (cooldownTimeRemaining <= 0 && getEnergyStored() == 0 && !powered) {
			extractEnergy();
		}
		energyStorage.modifyEnergyStored(-sendEnergyToAll());
		if (energyStorage.getEnergyStored() == 0 && fakeCapacity > 0) {
			fakeCapacity = 0;
		}
	}

	public void extractEnergy() {
		if (cooldownTimeRemaining > 0 || getEnergyStored() > 0) return;
		ItemStack stack = getStackInSlot(0);
		if ((stack != null && stack.stackSize > 0) && stack.getItem() instanceof IEnergyContainerItem) {
			if (ItemNBTHelper.getInteger(stack, "Energy", 0) > 0) {
				int energyToExtract = ItemNBTHelper.getInteger(stack, "Energy", 0);
				cooldownTime = energyToExtract / cooldownRatio;
				cooldownTimeRemaining = cooldownTime;
				fakeCapacity = energyToExtract;
				energyStorage.modifyEnergyStored(energyToExtract);
				ItemNBTHelper.setInteger(stack, "Energy", 0);
				/*
				 * if (chaos > 0) { burnTime = (int)(extractSpeed * (1 +
				 * (chaos / 2.0D))); extractTimeRemaining = burnTime;
				 * chaos -= (int)Math.floor(Math.random() * (chaos / 8)); }
				 */
			}
		}
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0 && stack.getItem() instanceof IEnergyContainerItem) {
			return true;
		}
		else return false;
	}

	@Override
	public void onNeighborChange(int x, int y, int z) {
		powered = world.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}
}
