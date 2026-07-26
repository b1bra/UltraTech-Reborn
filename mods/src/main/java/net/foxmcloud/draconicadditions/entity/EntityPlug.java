package net.foxmcloud.draconicadditions.entity;

import com.brandon3055.brandonscore.util.ItemNBTHelper;

import net.foxmcloud.draconicadditions.items.tools.PortableWiredCharger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;

public class EntityPlug extends Entity {

	private EntityPlayer player;

	public EntityPlug(World worldIn) {
		super(worldIn);
		this.init(null);
	}

	public EntityPlug(World worldIn, EntityPlayer player, double x, double y, double z, ForgeDirection facing) {
		super(worldIn);
		this.init(player);
		float yaw = 0;
		switch(facing) {
		case EAST:
			this.setPosition(x + 0.5, y - 0.5, z);
			yaw = 90;
			break;
		case WEST:
			this.setPosition(x - 0.5, y - 0.5, z);
			yaw = 270;
			break;
		case SOUTH:
			this.setPosition(x, y - 0.5, z + 0.5);
			break;
		case NORTH:
			this.setPosition(x, y - 0.5, z - 0.5);
			yaw = 180;
			break;
		case UP:
			this.setPosition(x, y, z);
			break;
		case DOWN:
			this.setPosition(x, y - 1, z);
			break;
		}
		float pitch = facing == ForgeDirection.UP ? 90 : facing == ForgeDirection.DOWN ? -90 : 0;
		this.setRotation(yaw, pitch);
	}

	public EntityPlug(World worldIn, EntityPlayer player, int x, int y, int z, ForgeDirection facing) {
		this(worldIn, player, (double) x, (double) y, (double) z, facing);
	}
	

	@Override
	protected void entityInit() {}

	public void onEntityUpdate() {
		this.world.profiler.startSection("entityBaseTick");
		if (this.player != null) {
			ItemStack stack = this.player.getHeldItem();
			if ((stack == null || stack.stackSize <= 0) || !(stack.getItem() instanceof PortableWiredCharger)) {
				this.setDead();
			}
			else if (!this.firstUpdate && !ItemNBTHelper.getBoolean(stack, "pluggedIn", false)) {
				this.setDead();
			}
		}
		else this.setDead();
		this.firstUpdate = false;
		this.world.profiler.endSection();
	}

	protected void init(EntityPlayer player) {
		this.setSize(1F, 1F);
		this.ignoreFrustumCheck = true;
		this.player = player;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {}

	public EntityPlayer getPlayer() {
		return player;
	}

}
