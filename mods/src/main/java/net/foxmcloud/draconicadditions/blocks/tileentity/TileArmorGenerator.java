package net.foxmcloud.draconicadditions.blocks.tileentity;

import com.brandon3055.brandonscore.lib.IChangeListener;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;

public class TileArmorGenerator extends TileChaosHolderBase implements IEnergyProvider, IChangeListener {

	private int burnSpeed = 50;
	private int baseRFMult = 40;

	public int burnTime = 1;
	public int burnTimeRemaining = 0;
	public double burnSpeedMultiplier = 1.0D;
	public boolean active = false;
	public boolean powered = false;

	public TileArmorGenerator() {
		setInventorySize(1);
		setEnergySyncMode().syncViaContainer();
		setCapacityAndTransfer(10000000, 0, 50000);
		setShouldRefreshOnBlockChange();
		setMaxChaos(0);
	}

	@Override
	public void updateEntity() {
		super.update();
		if (world.isRemote) {
			return;
		}

		active = burnTimeRemaining > 0 && getEnergyStored() < getMaxEnergyStored();

		if (burnTimeRemaining > 0 && getEnergyStored() < getMaxEnergyStored()) {
			int energyGen = (int) (burnSpeed * burnSpeedMultiplier);
			if (burnTimeRemaining < energyGen) energyGen = burnTimeRemaining;
			burnTimeRemaining -= energyGen;
			energyStorage.modifyEnergyStored(energyGen);
		}

		if (burnTimeRemaining <= 0 && getEnergyStored() < getMaxEnergyStored() && !powered) {
			refuel();
		}

		energyStorage.modifyEnergyStored(-sendEnergyToAll());
	}

	public void refuel() {
		if (burnTimeRemaining > 0 || getEnergyStored() >= getMaxEnergyStored()) return;
		ItemStack stack = getStackInSlot(0);
		if ((stack != null && stack.stackSize > 0) && !(stack.getItem() instanceof IEnergyContainerItem)) {
			if (stack.getItem() instanceof ItemArmor) {
				ItemArmor item = (ItemArmor) stack.getItem();
				int itemBurnTime = item.damageReduceAmount * (item.getMaxDamage(stack) - item.getDamage(stack) + 1) * baseRFMult;
				burnSpeedMultiplier = Math.round(item.toughness > 0 ? 1 + item.toughness : 1);
				if (stack.isItemEnchanted()) {
					NBTTagList list = stack.getEnchantmentTagList();
					if (list != null) {
						double lvls = 1.0F;
						for (int i = 0; i < list.tagCount(); i++) {
							NBTTagCompound compound = list.getCompoundTagAt(i);
							lvls += compound.getShort("lvl") / 5.0D;
						}
						itemBurnTime *= lvls;
						burnSpeedMultiplier = burnSpeedMultiplier * lvls;
					}
				}
				if (itemBurnTime > 0) {
					if (stack.stackSize == 1) {
						stack = stack.getItem().getContainerItem(stack);
					}
					else {
						stack.stackSize--;
					}
					setInventorySlotContents(0, stack);
					burnSpeedMultiplier *= (1 + (chaos / 80D));
					burnTime = (int) (itemBurnTime * (1 + (chaos / 80D)));
					burnTimeRemaining = burnTime;
					if (chaos > 0) {
						chaos -= (int) Math.floor(Math.random() * (chaos / 100));
					}
				}
			}
			else throw new Error("There was a non-armor item in a Armor Generator!  This should never happen...");
		}
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return super.extractEnergy(from, maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return super.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return super.getMaxEnergyStored();
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0 && stack.getItem() instanceof ItemArmor) {
			return true;
		}
		else return false;
	}

	@Override
	public void onNeighborChange(int x, int y, int z) {
		powered = world.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}
}
