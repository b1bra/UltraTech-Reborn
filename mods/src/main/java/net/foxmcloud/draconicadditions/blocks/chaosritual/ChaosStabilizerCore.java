package net.foxmcloud.draconicadditions.blocks.chaosritual;

import java.util.Random;

import com.brandon3055.brandonscore.block.BlockBCore;

import net.foxmcloud.draconicadditions.blocks.chaosritual.tileentity.TileChaosStabilizerCore;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ChaosStabilizerCore extends BlockBCore implements ITileEntityProvider {

	public ChaosStabilizerCore() {
		super();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileChaosStabilizerCore();
	}

	@Override

	public float getBlockHardness(World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		return tile instanceof TileChaosStabilizerCore ? 200F : super.getBlockHardness(world, x, y, z);
	}

	@Override
	public float getExplosionResistance(Entity exploder, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		TileEntity tile = world.getTileEntity(x, y, z);
		return tile instanceof TileChaosStabilizerCore ? 6000000.0F : super.getExplosionResistance(exploder, world, x, y, z, explosionX, explosionY, explosionZ);
	}

	@Override
	public int getRenderType() {
		return -1;

	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		return tile instanceof TileChaosStabilizerCore ? AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1) : super.getCollisionBoundingBoxFromPool(world, x, y, z);

	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!(tile instanceof TileChaosStabilizerCore)) {
			super.onBlockExploded(world, x, y, z, explosion);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)

	public void randomDisplayTick(World worldIn, int x, int y, int z, Random rand) {
		if (worldIn.getTileEntity(x, y, z) instanceof TileChaosStabilizerCore) {
			TileChaosStabilizerCore tile = (TileChaosStabilizerCore) worldIn.getTileEntity(x, y, z);
			if (tile.isMultiblock) {
				for (int i = 0; i < 10; i++) {
					double px = x + 0.5D + ((0.5D - rand.nextDouble()) * 2);
					double py = y + 0.5D + ((0.5D - rand.nextDouble()) * 2);
					double pz = z + 0.5D + ((0.5D - rand.nextDouble()) * 2);
					worldIn.spawnParticle("portal", px, py, pz, px - x, py - y - 0.5D, pz - z);

				}
			}
		}
	}
}
