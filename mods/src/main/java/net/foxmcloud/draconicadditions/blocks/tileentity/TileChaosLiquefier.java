package net.foxmcloud.draconicadditions.blocks.tileentity;

import com.brandon3055.brandonscore.lib.IChangeListener;
import com.brandon3055.draconicevolution.lib.DESoundHandler;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class TileChaosLiquefier extends TileChaosHolderBase implements IEnergyReceiver, IChangeListener {

	private int chargeRate = 10000000;
	public int maxCharge = 200;

	public int charge = 0;
	public int chargeTo = maxCharge;
	public boolean active = false;
	public boolean powered = false;

	public TileChaosLiquefier() {
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
				if (charge >= 0 && charge < chargeTo - 1) {
					float beamPitch = (1.5F * charge / maxCharge) + 0.5F;
					world.playSoundEffect(xCoord + 0.5D, yCoord, zCoord + 0.5D, DESoundHandler.beam, 0.2F, beamPitch);
					// charge += 1;
				}
				else {
					world.playSoundEffect(xCoord + 0.5D, yCoord, zCoord + 0.5D, DESoundHandler.boom, 1.0F, 2.0F);
					// charge = 0;
				}
			}
		}
		else {
			active = charge > 0;
			ItemStack stack = getStackInSlot(0);
			if ((stack != null && stack.stackSize > 0) && isItemValidForSlot(0, stack) && chaos < getMaxChaos()) {
				int finalCharge = calcCharge(stack);
				if (finalCharge != chargeTo) {
					chargeTo = finalCharge;
				}
				if (energyStorage.getEnergyStored() >= chargeRate) {
					charge += 1;
					energyStorage.modifyEnergyStored(-chargeRate);
					if (charge >= chargeTo) {
						discharge();
					}
				}
				else if (charge > 0) {
					charge -= 1;
				}
			}
			else if (charge > 0) {
				charge -= 1;
			}
		}
	}

	public void discharge() {
		ItemStack stack = getStackInSlot(0);
		chaos += calcChaos(stack);
		if (chaos > getMaxChaos()) {
			chaos = getMaxChaos();
		}
		stack.stackSize--;
		if (stack.stackSize == 0) {
			stack = null;
		}
		charge = 0;
	}

	public int calcChaos(ItemStack stack) {
		if (isItemValidForSlot(0, stack)) {
			switch (stack.getItem().getMetadata(stack)) {
			case 0:
				return 1300;
			case 1:
				return 150;
			case 2:
				return 17;
			case 3:
				return 2;
			default:
				return 0;
			}
		}
		else return 0;
	}

	public int calcCharge(ItemStack stack) {
		if (isItemValidForSlot(0, stack)) {
			switch (stack.getItem().getMetadata(stack)) {
			case 0:
				return maxCharge;
			case 1:
				return maxCharge / 2;
			case 2:
				return maxCharge / 4;
			case 3:
				return maxCharge / 8;
			default:
				return maxCharge;
			}
		}
		else return 0;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (stack.getItem() == Item.getByNameOrId("draconicevolution:chaos_shard")) {
			return true;
		}
		else if (stack.getItem() == Item.getByNameOrId("draconicadditions:chaos_heart")) {
			return true;
		}
		else return false;
	}

	@Override
	public void onNeighborChange(int x, int y, int z) {
		powered = world.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}
}
