package net.foxmcloud.draconicadditions.blocks.machines;

import java.util.Random;

import com.brandon3055.brandonscore.block.BlockBCore;

import net.foxmcloud.draconicadditions.DraconicAdditions;
import net.foxmcloud.draconicadditions.GUIHandler;
import net.foxmcloud.draconicadditions.blocks.tileentity.TileChaosInfuser;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ChaosInfuser extends BlockBCore implements ITileEntityProvider {

	public ChaosInfuser() {
		super(Material.IRON);
		this.setDefaultDirection(ForgeDirection.NORTH);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, x, y, z, placer, stack);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileChaosInfuser();
	}


	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, DraconicAdditions.instance, GUIHandler.GUIID_CHAOS_INFUSER, world, x, y, z);
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("incomplete-switch")
	public void randomDisplayTick(World worldIn, int x, int y, int z, Random rand) {
		TileEntity tile = worldIn.getTileEntity(x, y, z);
		boolean active = false;
		if (tile instanceof TileChaosInfuser) {
			active = ((TileChaosInfuser) tile).active;
		}
		if (active) {
			ForgeDirection enumfacing = ForgeDirection.getOrientation(worldIn.getBlockMetadata(x, y, z));
			double d0 = x + 0.5D;
			double d1 = y + 0.4 + rand.nextDouble() * 0.2;
			double d2 = z + 0.5D;
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.4D - 0.2D;

			switch (enumfacing) {
			case WEST:
				worldIn.spawnParticle("spell", d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
				worldIn.spawnParticle("instantSpell", d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
				break;
			case EAST:
				worldIn.spawnParticle("spell", d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
				worldIn.spawnParticle("instantSpell", d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
				break;
			case NORTH:
				worldIn.spawnParticle("spell", d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
				worldIn.spawnParticle("instantSpell", d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
				break;
			case SOUTH:
				worldIn.spawnParticle("spell", d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
				worldIn.spawnParticle("instantSpell", d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}
