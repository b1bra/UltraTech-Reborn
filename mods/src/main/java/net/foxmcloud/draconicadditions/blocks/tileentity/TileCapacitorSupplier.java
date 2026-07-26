package net.foxmcloud.draconicadditions.blocks.tileentity;

import com.brandon3055.brandonscore.blocks.TileEnergyInventoryBase;
import com.brandon3055.brandonscore.lib.EnergyHelper;
import com.brandon3055.brandonscore.lib.IChangeListener;
import com.brandon3055.brandonscore.lib.datamanager.ManagedBool;
import com.brandon3055.brandonscore.lib.datamanager.ManagedInt;
import com.brandon3055.brandonscore.utils.ItemNBTHelper;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.foxmcloud.draconicadditions.DAFeatures;
import net.foxmcloud.draconicadditions.items.Hermal;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.math.BlockPos;
import scala.Int;

public class TileCapacitorSupplier extends TileEnergyInventoryBase implements IEnergyProvider, IEnergyReceiver, IChangeListener {

	private int energyToExtract = 0;

	public final ManagedBool active = register("active", new ManagedBool(false)).saveToTile().saveToItem().syncViaTile().trigerUpdate().finish();
	public final ManagedBool powered = register("powered", new ManagedBool(false)).saveToTile().saveToItem().syncViaTile().trigerUpdate().finish();
	public final ManagedInt capacityBackup = register("capacityBackup", new ManagedInt(0)).saveToTile().saveToItem().finish();
	public final ManagedInt energyBackup = register("energyBackup", new ManagedInt(0)).saveToTile().saveToItem().finish();
	public final ManagedInt rateBackup = register("rateBackup", new ManagedInt(0)).saveToTile().saveToItem().finish();
	public final ManagedBool isHermal = register("isHermal", new ManagedBool(false)).saveToTile().saveToItem().syncViaTile().trigerUpdate().finish();

	public TileCapacitorSupplier() {
		setInventorySize(1);
		setEnergySyncMode().syncViaContainer();
		setCapacityAndTransfer(0, 0, 0);
	}

	@Override
	public void updateEntity() {
		super.update();
		if (world.isRemote) {
			return;
		}
		if (getMaxEnergyStored() == 0 && capacityBackup.value > 0) {
			setCapacityAndTransfer(capacityBackup.value, rateBackup.value, rateBackup.value);
			energyStorage.setEnergyStored(energyBackup.value);
		}
		if ((getStackInSlot(0) == null || getStackInSlot(0).stackSize <= 0) && active.value) {
			active.value = false;
		}
		else if (!(getStackInSlot(0) == null || getStackInSlot(0).stackSize <= 0) && !active.value) {
			active.value = true;
		}
		if (isHermal.value) {
			energyStorage.setEnergyStored(capacityBackup.value);
		}
		sendEnergyToAll();
		backupValues();
	}

	@Override
    public int sendEnergyToAll() {
        if (getEnergyStored() <= 0) {
            return 0;
        }
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            energyStorage.modifyEnergyStored(-sendEnergyTo(direction));
        }
        return 0;
    }

	public ItemStack insertItem(ItemStack stack) {
		if ((stack != null && stack.stackSize > 0)) {
			ItemStack stackInClaws = getStackInSlot(0);
			if ((stackInClaws == null || stackInClaws.stackSize <= 0)) {
				if (EnergyHelper.canExtractEnergy(stack) || ItemNBTHelper.getInteger(stack, "Energy", -1) >= 0) {
					isHermal.value = stack.getItem() instanceof Hermal;
					IEnergyContainerItem item = (IEnergyContainerItem)stack.getItem();
					int currentEnergy = item.getEnergyStored(stack);
					int maxEnergy = item.getMaxEnergyStored(stack);
					int transferRate = Math.max(item.extractEnergy(stack, Int.MaxValue(), true), item.receiveEnergy(stack, Int.MaxValue(), true));
					setCapacityAndTransfer(maxEnergy, transferRate, transferRate);
					energyStorage.setEnergyStored(currentEnergy);
					ItemNBTHelper.setInteger(stack, "Energy", 0);
					setInventorySlotContents(0, stack.copy());
					stack = null;
					backupValues();
					markDirty();
					updateBlock();
				}
			}
		}
		return stack;
	}

	public ItemStack extractItem() {
		ItemStack stack = null;
		ItemStack stackInClaws = getStackInSlot(0);
		if ((stackInClaws != null && stackInClaws.stackSize > 0)) {
			if (isHermal.value) {
				ItemNBTHelper.setInteger(stackInClaws, "Energy", ((Hermal)stackInClaws.getItem()).getCapacity(stackInClaws));
			}
			else {
				ItemNBTHelper.setInteger(stackInClaws, "Energy", energyStorage.getEnergyStored());
			}
			energyStorage.setEnergyStored(0);
			setCapacityAndTransfer(0, 0, 0);
			stack = removeStackFromSlot(0);
			backupValues();
			markDirty();
			updateBlock();
		}
		return stack;
	}

	protected void backupValues() {
		energyBackup.value = getEnergyStored();
		capacityBackup.value = getMaxEnergyStored();
		rateBackup.value = energyStorage.getMaxExtract();
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
	public void onNeighborChange(BlockPos neighbor) {
		powered.value = world.isBlockPowered(pos);
	}
}
