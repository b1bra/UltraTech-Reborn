package net.foxmcloud.draconicadditions.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.brandon3055.brandonscore.registry.Feature;
import com.brandon3055.draconicevolution.blocks.ChaosCrystal;
import com.brandon3055.draconicevolution.blocks.tileentity.TileChaosCrystal;
import com.brandon3055.draconicevolution.client.render.item.RenderItemReactorComponent;
import com.brandon3055.draconicevolution.client.render.tile.RenderTileChaosCrystal;

import codechicken.lib.model.ModelRegistryHelper;
import net.foxmcloud.draconicadditions.client.render.item.RenderItemChaosCrystal;
import net.foxmcloud.draconicadditions.items.IChaosItem;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import java.util.List;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ChaosCrystalStable extends ChaosCrystal implements IChaosItem {

	public ChaosCrystalStable() {
		super();
		this.setHardness(100.0F);
	}

    @Override
    public void getSubBlocks(CreativeTabs tab, List<ItemStack> list) {
    	ItemStack stack = new ItemStack(this);
    	setChaosStable(stack, true);
    	list.add(stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
    	if (getChaosInfoStable(stack) != null) tooltip.add(getChaosInfoStable(stack));
        super.addInformation(stack, player, tooltip, advanced);
    }

	@Override
	public float getBlockHardness(Block blockState, World world, int x, int y, int z) {
		return 100F;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block state) {}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, Block state, EntityLivingBase placer, ItemStack stack) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!world.isRemote && tile instanceof TileChaosCrystal) {
			TileChaosCrystal tileCrystal = (TileChaosCrystal)tile;
			if (!isChaosStable(stack)) {
				tileCrystal.detonate();
			}
			else {
				tileCrystal.setLockPos();
				tileCrystal.guardianDefeated.value = true;
			}
		}
	}

    @SideOnly(Side.CLIENT)
    @Override
    public void registerRenderer(Feature feature) {
        super.registerRenderer(feature);
        ModelRegistryHelper.registerItemRenderer(Item.getItemFromBlock(this), new RenderItemChaosCrystal());
    }
}
