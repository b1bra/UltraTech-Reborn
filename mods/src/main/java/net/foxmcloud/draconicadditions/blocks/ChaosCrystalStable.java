package net.foxmcloud.draconicadditions.blocks;

import java.util.List;

import com.brandon3055.brandonscore.block.BlockBCore;

import net.foxmcloud.draconicadditions.items.IChaosItem;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import java.util.List;


/**
 * TODO 1.7.10: reconnect this class to the correct Draconic Evolution 1.7.10
 * ChaosCrystal/TileChaosCrystal classes once their package names are verified.
 */
public class ChaosCrystalStable extends BlockBCore implements IChaosItem {

	public ChaosCrystalStable() {
		super(Material.rock);
		this.setHardness(100.0F);
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, List list) {
		ItemStack stack = new ItemStack(this);
		setChaosStable(stack, true);
		list.add(stack);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
		if (getChaosInfoStable(stack) != null) tooltip.add(getChaosInfoStable(stack));
		super.addInformation(stack, player, tooltip, advanced);
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return 100F;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
		// TODO 1.7.10: detonate/lock the real DE chaos crystal tile when the 1.7.10 class is wired back in.

	}
}
