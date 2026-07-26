package net.foxmcloud.draconicadditions.items.tools;

import java.util.ArrayList;
import java.util.List;


import com.brandon3055.brandonscore.items.ItemEnergyBase;
import com.brandon3055.brandonscore.util.ItemNBTHelper;
import com.brandon3055.draconicevolution.api.IInvCharge;
import com.brandon3055.draconicevolution.api.itemupgrade.IUpgradableItem;
import com.brandon3055.draconicevolution.api.itemupgrade.UpgradeHelper;
import com.brandon3055.draconicevolution.items.ToolUpgrade;

import net.foxmcloud.draconicadditions.CommonMethods;
import net.foxmcloud.draconicadditions.DAFeatures;
import net.foxmcloud.draconicadditions.blocks.tileentity.TileChaosHolderBase;
import net.foxmcloud.draconicadditions.capabilities.ChaosInBloodProvider;
import net.foxmcloud.draconicadditions.capabilities.IChaosInBlood;
import net.foxmcloud.draconicadditions.items.IChaosContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.List;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ChaosContainer extends ItemEnergyBase implements IChaosContainer, IUpgradableItem, IInvCharge {

	public ChaosContainer() {
		this.setMaxStackSize(1);
	}

	@Override
	public void getSubItems(CreativeTabs tab, List<ItemStack> subItems) {
		if (isInCreativeTab(tab)) {
			subItems.add(new ItemStack(this));
			ItemStack stack = new ItemStack(this);
			setEnergy(stack, getCapacity(stack));
			addChaos(stack, getMaxChaos(stack));
			subItems.add(stack);
		}
	}

	public int getMaxChaos(ItemStack stack) {
		int upgrade = UpgradeHelper.getUpgradeLevel(stack, ToolUpgrade.SHIELD_CAPACITY);
		return ToolStats.CHAOS_CONTAINER_MAX_CHAOS * (upgrade + 1);
	}

	@Override
	public int getCapacity(ItemStack stack) {
		int upgrade = UpgradeHelper.getUpgradeLevel(stack, ToolUpgrade.RF_CAPACITY);
		return ToolStats.CHAOS_CONTAINER_MAX_RF * (upgrade + 1);
	}

	@Override
	public int getMaxReceive(ItemStack stack) {
		int upgrade = UpgradeHelper.getUpgradeLevel(stack, ToolUpgrade.RF_CAPACITY);
		return ToolStats.CHAOS_CONTAINER_MAX_TRANSFER * (upgrade + 1);
	}

	@Override
	public int getMaxExtract(ItemStack stack) {
		return getMaxChaos(stack) * ToolStats.CHAOS_CONTAINER_RF_PER_CHAOS * 2;
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return getChaos(stack) > 0;
	}

	@Override
	public List<String> getValidUpgrades(ItemStack stack) {
		return new ArrayList<String>() {
			{
				add(ToolUpgrade.RF_CAPACITY);
				add(ToolUpgrade.SHIELD_CAPACITY);
			}
		};
	}

	@Override
	public int getMaxUpgradeLevel(ItemStack stack, String upgrade) {
		return 3;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (!(entity instanceof EntityPlayer) || world.isRemote) return;
		EntityPlayer player = (EntityPlayer) entity;
		upkeep(player, stack, world);
	}

	public void upkeep(EntityPlayer player, ItemStack stack, World world) {
		if (hasEffect(stack) && !player.isCreative()) {
			if (CommonMethods.cheatCheck(stack, world)) {
				ItemNBTHelper.setInteger(stack, "Energy", 0);
			}
			int drainedRF = extractEnergy(stack, getChaos(stack) * ToolStats.CHAOS_CONTAINER_RF_PER_CHAOS, false);
			if (drainedRF != getChaos(stack) * ToolStats.CHAOS_CONTAINER_RF_PER_CHAOS) {
				CommonMethods.explodeEntity(player.posX, player.posY, player.posZ, world);
				player.attackEntityFrom(CommonMethods.chaosBurst, getChaos(stack));
				player.addChatMessage(new ChatComponentTranslation("msg.da.chaosContainer.explode"));
				stack.stackSize--;
			}
		}
		else CommonMethods.cheatCheck(stack, world);
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player) {
		if (getChaos(stack) > 0 && !player.isCreative()) {
			player.addChatMessage(new ChatComponentTranslation("msg.da.chaosContainer.cantdrop"));
			return false;
		}
		else {
			ItemNBTHelper.setLong(stack, "cheatCheck", 0);
			return true;
		}
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if (entity instanceof EntityLiving) {
			EntityLiving ent = (EntityLiving) entity;
			if (getChaos(stack) > 0 && !ent.isEntityInvulnerable(CommonMethods.chaosBurst)) {
				CommonMethods.explodeEntity(ent.posX, ent.posY, ent.posZ, player.world);
				if (!player.world.isRemote) {
					float damage = Math.min(getChaos(stack), ent.getHealth());
					entity.attackEntityFrom(CommonMethods.chaosBurst, damage);
					removeChaos(stack, (int) Math.floor(damage));
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (world.getTileEntity(x, y, z) instanceof TileChaosHolderBase) {
			TileChaosHolderBase tileEntity = (TileChaosHolderBase) world.getTileEntity(x, y, z);
			if (((ChaosContainer) stack.getItem()).getChaos(stack) > 0 && tileEntity.chaos != tileEntity.getMaxChaos()) {
				int chaosToRemove = Math.min(getMaxChaos(stack) - tileEntity.chaos, getChaos(stack));
				removeChaos(stack, chaosToRemove);
				tileEntity.chaos += chaosToRemove;
			}
			else {
				int chaosToAdd = Math.min(getMaxChaos(stack) - getChaos(stack), tileEntity.chaos);
				addChaos(stack, chaosToAdd);
				tileEntity.chaos -= chaosToAdd;
			}
			return true;
		}
		else {
			IChaosInBlood pCap = ChaosInBloodProvider.get(player);
			if (pCap != null && player.isEntityAlive() && pCap.getChaos() > 0) {
				onItemRightClick(stack, world, player);
				return true;
			}
		}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		IChaosInBlood pCap = ChaosInBloodProvider.get(player);
		if (pCap != null && player.isEntityAlive() && pCap.getChaos() > 0) {
			int chaosToAdd = (int)(Math.min(pCap.getChaos(), 2) * 4);
			addChaos(stack, chaosToAdd);
			pCap.removeChaos(chaosToAdd / 4.0F);
			if (pCap.getChaos() <= 0.25) {
				ItemStack chest = player.inventory.armorItemInSlot(2);
				if (chest.getItem() == DAFeatures.chaoticChest && ItemNBTHelper.getBoolean(chest, "injecting", false)) {
					ItemNBTHelper.setBoolean(chest, "injecting", false);
					player.addChatMessage(new ChatComponentTranslation("msg.da.chaosInjection.failsafe"));
				}
			}
			else {
				player.addChatMessage(new ChatComponentTranslation("msg.da.chaosContainer.charge"));
			}
			return stack;
		}
		return stack;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		tooltip.add(getChaosInfo(stack));
		if (getMaxEnergyStored(stack) > 0)
		tooltip.add(I18n.format("info.da.shieldcharge.txt") + ": " + getEnergyStored(stack) + " / " + getMaxEnergyStored(stack) + " RF");
	}

	@Override
	public boolean canCharge(ItemStack stack, EntityPlayer player) {
		return true;
	}
}
