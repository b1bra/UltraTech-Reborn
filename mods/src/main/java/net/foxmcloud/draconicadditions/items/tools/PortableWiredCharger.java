package net.foxmcloud.draconicadditions.items.tools;

import java.util.ArrayList;
import java.util.List;

import com.brandon3055.brandonscore.items.ItemEnergyBase;
import com.brandon3055.brandonscore.lib.EnergyHelper;
import com.brandon3055.brandonscore.utils.InfoHelper;
import com.brandon3055.brandonscore.util.ItemNBTHelper;
import com.brandon3055.draconicevolution.entity.EntityPersistentItem;
import com.brandon3055.draconicevolution.integration.BaublesHelper;
import com.brandon3055.draconicevolution.integration.ModHelper;

import net.foxmcloud.draconicadditions.DAFeatures;
import net.foxmcloud.draconicadditions.entity.EntityPlug;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PortableWiredCharger extends ItemEnergyBase {

	public static final int basicTransfer = 100000;
	public static final int wyvernTransfer = 800000;
	public static final int draconicTransfer = 6400000;
	public static final int chaoticTransfer = 51200000;
	public boolean active = false;
	public double maxDistance = 3;

	public PortableWiredCharger() {
		this.setHasSubtypes(true);
		this.addName(0, "basic").addName(1, "wyvern").addName(2, "draconic").addName(3, "chaotic");
		this.addName(4, "basic").addName(5, "wyvern").addName(6, "draconic").addName(7, "chaotic");
		this.setMaxStackSize(1);
	}

	@Override
	public void getSubItems(CreativeTabs tab, List<ItemStack> subItems) {
		if (isInCreativeTab(tab)) {
			subItems.add(new ItemStack(DAFeatures.pwc, 1, 0));
			subItems.add(new ItemStack(DAFeatures.pwc, 1, 1));
			subItems.add(new ItemStack(DAFeatures.pwc, 1, 2));
			subItems.add(new ItemStack(DAFeatures.pwc, 1, 3));
		}
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}

	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		return new EntityPersistentItem(world, location, itemstack);
	}

	@Override
	public int getCapacity(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case 0:
		case 4:
			return basicTransfer;
		case 1:
		case 5:
			return wyvernTransfer;
		case 2:
		case 6:
			return draconicTransfer;
		case 3:
		case 7:
			return chaoticTransfer;
		}
		return 0;
	}

	@Override
	public int getMaxReceive(ItemStack stack) {
		return getCapacity(stack);
	}

	@Override
	public int getMaxExtract(ItemStack stack) {
		return getCapacity(stack);
	}

	@Override
	public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (ItemNBTHelper.getBoolean(stack, "pluggedIn", false)) {
			unplug(stack, player);
			return stack;
		}
		MovingObjectPosition trace = rayTrace(world, player, false);
		if (trace != null && trace.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && isDistanceValid(trace.blockX, trace.blockY, trace.blockZ, ForgeDirection.getOrientation(trace.sideHit), player)) {
			int x = trace.blockX;
			int y = trace.blockY;
			int z = trace.blockZ;
			if (world.getBlock(x, y, z).hasTileEntity(world.getBlockMetadata(x, y, z))) {
				TileEntity te = world.getTileEntity(x, y, z);
				if (EnergyHelper.isEnergyTile(te, null)) {
					if (EnergyHelper.isEnergyTile(te, trace.sideHit)) {
						if (!world.isRemote) {
							world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "draconicadditions:unplug", 0.8F, 1.5F);
						}
						ItemNBTHelper.setBoolean(stack, "pluggedIn", true);
						ItemNBTHelper.setInteger(stack, "blockX", x);
						ItemNBTHelper.setInteger(stack, "blockY", y);
						ItemNBTHelper.setInteger(stack, "blockZ", z);
						ItemNBTHelper.setString(stack, "blockSide", ForgeDirection.getOrientation(trace.sideHit).name());
						world.spawnEntityInWorld(new EntityPlug(world, player, x, y, z, ForgeDirection.getOrientation(trace.sideHit)));
					}
					else if (!world.isRemote) player.addChatMessage(new ChatComponentTranslation("msg.da.portableWiredCharger.invalidSide"));
				}
				else if (!world.isRemote) player.addChatMessage(new ChatComponentTranslation("msg.da.portableWiredCharger.connectEnergySource"));
			}
			else if (!world.isRemote) player.addChatMessage(new ChatComponentTranslation("msg.da.portableWiredCharger.connectEnergySource"));
		}
		else if (!world.isRemote) player.addChatMessage(new ChatComponentTranslation("msg.da.portableWiredCharger.connectEnergySource"));
		return stack;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (!(entity instanceof EntityPlayer) || world.isRemote) {
			return;
		}
		updateActive(stack);
		EntityPlayer player = (EntityPlayer) entity;
		if (active) {
			if (player.getHeldItem() != stack) {
				unplug(stack, player);
			}
			else if (getTileEntity(stack, world) != null) {
				checkDistance(stack, world, player);
				extractEnergyFromSource(stack, world);
			}
			else unplug(stack, player);
		}
		if (getEnergyStored(stack) > 0) {
			if (ModHelper.isBaublesInstalled) chargeItems(stack, player, getBaubles(player));
			else chargeItems(stack, player, new ArrayList<>());
		}
		if (getEnergyStored(stack) > 0) {
			sendEnergyToSource(stack, world);
		}
	}

	public void chargeItems(ItemStack charger, EntityPlayer player, List<ItemStack> stacks) {
		stacks.addAll(player.inventory.armorInventory);
		stacks.addAll(player.inventory.mainInventory);
		stacks.addAll(player.inventory.offHandInventory);
		for (ItemStack stack : stacks) {
			if (getEnergyStored(charger) == 0) break;
			if ((stack == null || stack.stackSize <= 0)) continue;
			if (EnergyHelper.canReceiveEnergy(stack)) {
				int max = Math.min(getEnergyStored(charger), getMaxExtract(charger));
				int insertedEnergy = EnergyHelper.insertEnergy(stack, Math.min(getMaxExtract(charger), max), false);
				super.extractEnergy(charger, insertedEnergy, false);
			}
		}
	}

	public void extractEnergyFromSource(ItemStack stack, World world) {
		if (active) {
			TileEntity te = getTileEntity(stack, world);
			ForgeDirection extractSide = ForgeDirection.valueOf(ItemNBTHelper.getString(stack, "blockSide", "NONE"));
			if (te != null && EnergyHelper.canExtractEnergy(te, extractSide)) {
				int storedEnergy = ItemNBTHelper.getInteger(stack, "Energy", 0);
				int energyToExtract = Math.min(getCapacity(stack) - storedEnergy, EnergyHelper.getEnergyStored(te, extractSide));
				if (energyToExtract == 0) return;
				storedEnergy += EnergyHelper.extractEnergy(te, energyToExtract, extractSide, false);
				ItemNBTHelper.setInteger(stack, "Energy", storedEnergy);
			}
		}
	}

	public void sendEnergyToSource(ItemStack stack, World world) {
		if (active) {
			TileEntity te = getTileEntity(stack, world);
			ForgeDirection insertSide = ForgeDirection.valueOf(ItemNBTHelper.getString(stack, "blockSide", "NONE"));
			if (te != null && EnergyHelper.canReceiveEnergy(te, insertSide)) {
				int storedEnergy = ItemNBTHelper.getInteger(stack, "Energy", 0);
				if (storedEnergy > 0) {
					storedEnergy -= EnergyHelper.insertEnergy(te, storedEnergy, insertSide, false);
					ItemNBTHelper.setInteger(stack, "Energy", storedEnergy);
				}
			}
		}
	}

	public void checkDistance(ItemStack stack, World world, EntityPlayer player) {
		TileEntity te = getTileEntity(stack, world);
		if (te != null) {
			ForgeDirection side = ForgeDirection.valueOf(ItemNBTHelper.getString(stack, "blockSide", "NONE"));
			if (!isDistanceValid(te.xCoord, te.yCoord, te.zCoord, side, player)) {
				unplug(stack, player);
			}
		}
	}

	public boolean isDistanceValid(int x, int y, int z, ForgeDirection side, EntityPlayer player) {
		int offsetX = x + side.offsetX - (int)player.posX;
		int offsetY = y + side.offsetY - (int)player.posY;
		int offsetZ = z + side.offsetZ - (int)player.posZ;
		if (Math.abs(offsetX) > maxDistance ||
			Math.abs(offsetY) > maxDistance ||
			Math.abs(offsetZ) > maxDistance) {
			return false;
		}
		else return true;
	}

	public void unplug(ItemStack stack, EntityPlayer player) {
		if (!player.getEntityWorld().isRemote) player.getEntityWorld().playSoundEffect(player.posX, player.posY, player.posZ, "draconicadditions:unplug", 0.8F, 1.0F);
		ItemNBTHelper.setBoolean(stack, "pluggedIn", false);
		ItemNBTHelper.setInteger(stack, "blockX", 0);
		ItemNBTHelper.setInteger(stack, "blockY", 0);
		ItemNBTHelper.setInteger(stack, "blockZ", 0);
		ItemNBTHelper.setString(stack, "blockSide", "NONE");
		updateActive(stack);
	}

	public TileEntity getTileEntity(ItemStack stack, World world) {
		if (active) {
			int x = ItemNBTHelper.getInteger(stack, "blockX", 0);
			int y = ItemNBTHelper.getInteger(stack, "blockY", 0);
			int z = ItemNBTHelper.getInteger(stack, "blockZ", 0);
			TileEntity te = world.getTileEntity(x, y, z);
			return te;
		}
		return null;
	}

	public void updateActive(ItemStack stack) {
		active = ItemNBTHelper.getBoolean(stack, "pluggedIn", false);
		int newDamage = stack.getItemDamage() % 4 + (active ? 4 : 0);
		if (newDamage != stack.getItemDamage()) stack.setItemDamage(newDamage);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return ItemNBTHelper.getBoolean(stack, "pluggedIn", false);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player) {
		if (active) {
			unplug(stack, player);
		}
		return true;
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		boolean isPWC = oldStack.getItem() instanceof PortableWiredCharger && newStack.getItem() instanceof PortableWiredCharger;
		boolean isSameDamage = oldStack.getItem().getDamage(oldStack) % 4 == newStack.getItem().getDamage(newStack) % 4;
		return !isPWC || !isSameDamage;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		if (InfoHelper.holdShiftForDetails(tooltip)) {
			boolean pluggedIn = ItemNBTHelper.getBoolean(stack, "pluggedIn", false);
			tooltip.add("Plugged In: " + pluggedIn);
			if (pluggedIn) {
				tooltip.add("X: " + ItemNBTHelper.getInteger(stack, "blockX", 0));
				tooltip.add("Y: " + ItemNBTHelper.getInteger(stack, "blockY", 0));
				tooltip.add("Z: " + ItemNBTHelper.getInteger(stack, "blockZ", 0));
				tooltip.add("Side: " + ItemNBTHelper.getString(stack, "blockSide", "NONE"));
			}
		}
		InfoHelper.addEnergyInfo(stack, tooltip);
	}

	private static List<ItemStack> getBaubles(EntityPlayer entity) {
		return BaublesHelper.getBaubles(entity);
	}
}
