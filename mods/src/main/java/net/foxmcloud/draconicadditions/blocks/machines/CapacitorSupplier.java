package net.foxmcloud.draconicadditions.blocks.machines;

import java.util.Random;

import com.brandon3055.brandonscore.block.BlockBCore;
import net.foxmcloud.draconicadditions.blocks.tileentity.TileCapacitorSupplier;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CapacitorSupplier extends BlockBCore implements ITileEntityProvider {

	public CapacitorSupplier() {
		super(Material.IRON);
		this.setDefaultDirection(ForgeDirection.NORTH);
	}

	@Override
	public boolean uberIsBlockFullCube() {
        return false;
    }

	@Override
	public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, x, y, z, placer, stack);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCapacitorSupplier();
	}


	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileCapacitorSupplier tileCapacitorDischarger = world.getTileEntity(x, y, z) instanceof TileCapacitorSupplier ? (TileCapacitorSupplier) world.getTileEntity(x, y, z) : null;
			if (tileCapacitorDischarger != null) {
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


	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, int x, int y, int z, Random rand) {
		TileEntity tile = worldIn.getTileEntity(x, y, z);
		boolean active = tile instanceof TileCapacitorSupplier && ((TileCapacitorSupplier) tile).active;
		if (active) {
			double dx = x + 0.5D;
			double dy = y + 0.825D;
			double dz = z + 0.5D;
			worldIn.spawnParticle("reddust", dx, dy, dz, 0.0D, 0.1D, 0.0D);
		}
	}
}
