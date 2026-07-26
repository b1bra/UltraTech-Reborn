package net.foxmcloud.draconicadditions.blocks.machines;

import java.util.Random;

import com.brandon3055.brandonscore.blocks.BlockBCore;

import net.foxmcloud.draconicadditions.DraconicAdditions;
import net.foxmcloud.draconicadditions.GUIHandler;
import net.foxmcloud.draconicadditions.blocks.tileentity.TileChaosLiquefier;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ChaosLiquefier extends BlockBCore implements ITileEntityProvider {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public ChaosLiquefier() {
		super(Material.IRON);
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, ForgeDirection.NORTH).withProperty(ACTIVE, false));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, ACTIVE);
	}

	@Override
	public Block getActualState(Block state, IBlockAccess worldIn, int x, int y, int z) {
		TileChaosLiquefier tileChaoticGenerator = worldIn.getTileEntity(pos) instanceof TileChaosLiquefier ? (TileChaosLiquefier) worldIn.getTileEntity(pos) : null;
		return state.withProperty(ACTIVE, tileChaoticGenerator != null && tileChaoticGenerator.active.value);
	}

	@Override
	public Block getStateFromMeta(int meta) {
		ForgeDirection enumfacing = ForgeDirection.VALID_DIRECTIONS[MathHelper.abs(meta % ForgeDirection.VALID_DIRECTIONS.length)];

		if (enumfacing.getAxis() == ForgeDirection.Y) {
			enumfacing = ForgeDirection.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(Block state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public Block withRotation(Block state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public Block withMirror(Block state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	public Block getStateForPlacement(World world, int x, int y, int z, ForgeDirection facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public void onBlockPlacedBy(World worldIn, int x, int y, int z, Block state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileChaosLiquefier();
	}

	@Override
	public int getLightValue(Block state, IBlockAccess world, int x, int y, int z) {
		return 0;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, Block state, EntityPlayer player, ForgeDirection side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, DraconicAdditions.instance, GUIHandler.GUIID_CHAOS_LIQUEFIER, world, x, y, z);
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("incomplete-switch")
	public void randomDisplayTick(Block stateIn, World worldIn, int x, int y, int z, Random rand) {
		if (stateIn.getActualState(worldIn, pos).getValue(ACTIVE)) {
			ForgeDirection enumfacing = stateIn.getValue(FACING);
			double d0 = x + 0.5D;
			double d1 = y + 0.4 + rand.nextDouble() * 0.2;
			double d2 = z + 0.5D;
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.4D - 0.2D;

			switch (enumfacing) {
			case WEST:
				worldIn.spawnParticle(EnumParticleTypes.SPELL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.SPELL_INSTANT, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			case EAST:
				worldIn.spawnParticle(EnumParticleTypes.SPELL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.SPELL_INSTANT, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			case NORTH:
				worldIn.spawnParticle(EnumParticleTypes.SPELL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.SPELL_INSTANT, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			case SOUTH:
				worldIn.spawnParticle(EnumParticleTypes.SPELL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.SPELL_INSTANT, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}
}
