package net.foxmcloud.draconicadditions.blocks.machines;

import net.foxmcloud.draconicadditions.DraconicAdditions;
import net.foxmcloud.draconicadditions.GUIHandler;
import net.foxmcloud.draconicadditions.blocks.tileentity.TileChaoticArmorGenerator;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class ChaoticArmorGenerator extends ArmorGenerator {

	public ChaoticArmorGenerator() {
		super();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileChaoticArmorGenerator();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, Block state, EntityPlayer player, ForgeDirection side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, DraconicAdditions.instance, GUIHandler.GUIID_ARMOR_GENERATOR, world, x, y, z);
		}
		return true;
	}
}
