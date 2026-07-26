package net.foxmcloud.draconicadditions.items.armor;

import java.util.List;


import net.foxmcloud.draconicadditions.DAFeatures;
import net.foxmcloud.draconicadditions.DraconicAdditions;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class InfusedPotatoArmor extends ItemArmor {
	private static ArmorMaterial potatoMaterial = EnumHelper.addArmorMaterial("infusedPotatoArmor", DraconicAdditions.MODID_PREFIX + "infused_potato_armor", -1, new int[] {1, 1, 2, 1}, 0, "random.pop", 0.0F);

	public InfusedPotatoArmor(int renderIndexIn, int armorType) {
		super(potatoMaterial, renderIndexIn, armorType);
		this.setMaxDamage(-1);
	}

	public InfusedPotatoArmor(ArmorMaterial materialIn, int renderIndexIn, int armorType) {
		super(materialIn, renderIndexIn, armorType);
		this.setMaxDamage(-1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (ArmorStats.INFUSED_POTATO_RIGHT_CLICK) return transformItem(player, stack, false);
		else return stack;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (ArmorStats.INFUSED_POTATO_RIGHT_CLICK) {
			transformItem(player, stack, true);
			return false;
		}
		else return false;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if (ArmorStats.INFUSED_POTATO_SMACK) {
			transformItem(player, stack, true);
		}
		return false;
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player) {
		if (ArmorStats.INFUSED_POTATO_DROP) {
			transformItem(player, stack, true);
			return false;
		}
		else return true;
	}

	private ItemStack transformItem(EntityPlayer player, boolean replace) {
		ItemStack itemStack = player.getHeldItem();
		return transformItem(player, itemStack, replace);
	}

	private ItemStack transformItem(EntityPlayer player, ItemStack stack, boolean replace) {
		ItemArmor item = (ItemArmor)stack.getItem();
		int slot = item.armorType;
		PotatoArmor armor;
		if (slot == 0) armor = DAFeatures.potatoHelm;
		else if (slot == 1) armor = DAFeatures.potatoChest;
		else if (slot == 2) armor = DAFeatures.potatoLegs;
		else if (slot == 3) armor = DAFeatures.potatoBoots;
		else throw new Error("Something's wrong with the G-Diffuser!  This doesn't look like a valid Potato Armor...");
		ItemStack armorItem = new ItemStack(armor);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat("Energy", ArmorStats.POTATO_BASE_CAPACITY);
		armorItem.setTagCompound(nbt);
		player.inventory.deleteStack(stack);
		if (replace) {
			player.addItemStackToInventory(armorItem);
			player.addChatMessage(new ChatComponentTranslation("msg.da.infusedTransformation.smack"));
		}
		else player.addChatMessage(new ChatComponentTranslation("msg.da.infusedTransformation.normal"));
		return armorItem;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		tooltip.add(I18n.format("item.draconicadditions:infused_potato.lore"));
		tooltip.add(I18n.format("item.draconicadditions:infused_potato.lore2"));
	}
}
