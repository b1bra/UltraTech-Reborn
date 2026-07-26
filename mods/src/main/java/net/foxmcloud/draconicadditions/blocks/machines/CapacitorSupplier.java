package net.foxmcloud.draconicadditions.blocks.machines;

import java.util.Random;

import com.brandon3055.brandonscore.blocks.BlockBCore;
import com.brandon3055.brandonscore.registry.Feature;
import com.brandon3055.brandonscore.registry.IRenderOverride;

import net.foxmcloud.draconicadditions.blocks.tileentity.TileCapacitorSupplier;
import net.foxmcloud.draconicadditions.client.render.tile.RenderTileCapacitorSupplier;
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
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CapacitorSupplier extends BlockBCore implements ITileEntityProvider, IRenderOverride {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public CapacitorSupplier() {
		super(Material.IRON);
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, ForgeDirection.NORTH).withProperty(ACTIVE, false));
	}

	@Override
	public boolean uberIsBlockFullCube() {
        return false;
    }

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, ACTIVE);
	}

	@Override
	public Block getActualState(Block state, IBlockAccess worldIn, int x, int y, int z) {
		TileCapacitorSupplier tileCapacitorDischarger = worldIn.getTileEntity(pos) instanceof TileCapacitorSupplier ? (TileCapacitorSupplier) worldIn.getTileEntity(pos) : null;
		return state.withProperty(ACTIVE, tileCapacitorDischarger != null && tileCapacitorDischarger.active.value);
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
		return new TileCapacitorSupplier();
	}

	@Override
	public int getLightValue(Block state, IBlockAccess world, int x, int y, int z) {
		return 0;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, Block state, EntityPlayer player, ForgeDirection side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileCapacitorSupplier tileCapacitorDischarger = world.getTileEntity(x, y, z) instanceof TileCapacitorSupplier ? (TileCapacitorSupplier) world.getTileEntity(x, y, z) : null;
			if (tileCapacitorDischarger != null) {
				ItemStack stack = player.getHeldItem();
				if ((stack != null && stack.stackSize > 0)) {
					stack = tileCapacitorDischarger.insertItem(stack);
					player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
				}
				else {
					stack = tileCapacitorDischarger.extractItem();
					player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
				}
			}
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerRenderer(Feature feature) {
		ClientRegistry.bindTileEntitySpecialRenderer(TileCapacitorSupplier.class, new RenderTileCapacitorSupplier());
	}

	@Override
	public boolean registerNormal(Feature feature) { return true; }

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(Block stateIn, World worldIn, int x, int y, int z, Random rand) {
		if (stateIn.getActualState(worldIn, pos).getValue(ACTIVE)) {
			double dx = x + 0.5D;
			double dy = y + 0.825D;
			double dz = z + 0.5D;
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, dx, dy, dz, 0.0D, 0.1D, 0.0D, new int[0]);
		}
	}
}
