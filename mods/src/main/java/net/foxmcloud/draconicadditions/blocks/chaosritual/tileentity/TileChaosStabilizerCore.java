package net.foxmcloud.draconicadditions.blocks.chaosritual.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.brandon3055.brandonscore.block.TileInventoryBase;
import com.brandon3055.brandonscore.lib.IActivatableTile;
import com.brandon3055.draconicevolution.handlers.CustomArmorHandler.ArmorSummery;
import com.brandon3055.draconicevolution.handlers.DEEventHandler;
import com.google.common.collect.Lists;

import net.foxmcloud.draconicadditions.items.IChaosItem;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IUpdatePlayerListBox;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.common.util.FakePlayer;

public class TileChaosStabilizerCore extends TileInventoryBase implements IUpdatePlayerListBox, IActivatableTile {

	public double diameter = 1;
	public double intensity = 0;
	private static final float chaosDamage = 10;
	private static final double suckRadius = 6;
	private static final int delayInMultiblockCheck = 20;
	private double actualDiameter = diameter;
	private double actualIntensity = intensity;
	private int ritualTicks = 0;
	public boolean isRitualOngoing = false;
	public boolean isMultiblock = false;
	private Random rand = new Random();
	private DamageSource chaosBurst = new DamageSource("chaosBurst").setDamageBypassesArmor();

	public TileChaosStabilizerCore() {
		super();
		this.setInventorySize(1);
		setShouldRefreshOnBlockChange();
	}

	@Override
	public void update() {
		if (DEEventHandler.serverTicks % 10 == 0 && !world.isRemote) {
			checkMultiblock();
			updateBlock();
		}

		if (!isRitualOngoing) {
			if (isMultiblock) {
				intensity = 0.25F;
				List<Entity> suckEntities = this.world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(suckRadius, suckRadius, suckRadius));
				for (Entity e : suckEntities) {
					if (e instanceof EntityItem) {
						IChaosItem item = getChaosItem(((EntityItem)e).getItem().getItem());
						if (item != null) {
							if (item.isChaosStable(((EntityItem)e).getItem())) {
								continue;
							}
						}
					}
					double dx = (xCoord + 0.5D - e.posX);
					double dy = (yCoord + 0.5D - e.posY);
					double dz = (zCoord + 0.5D - e.posZ);
					double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
					if (distance < 1.1 && e instanceof EntityItem) {
						IChaosItem item = getChaosItem(((EntityItem)e).getItem().getItem());
						if (item != null && (getStackInSlot(0) == null || getStackInSlot(0).stackSize <= 0)) {
							ItemStack stack = ((EntityItem)e).getItem();
							setInventorySlotContents(0, stack);
							world.removeEntity(e);
							startRitual();
							return;
						}
						else {
							e.motionX -= (e.motionX * (suckRadius) / 1.5);
							e.motionY -= (e.motionY * (suckRadius) / 1.5);
							e.motionZ -= (e.motionZ * (suckRadius) / 1.5);
							playSound("random.pop", 1.0F, 1.0F);
						}
					}
					else {
						double limit = 1.0 - (distance / suckRadius);
						if (limit > 0.0D) {
							limit *= limit;
							e.motionX += dx / distance * limit * 0.4;
							e.motionY += dy / distance * limit * 0.4;
							e.motionZ += dz / distance * limit * 0.4;
						}
					}
				}
			}
			else intensity = 0;
		}
		else {
			if (isMultiblock) {
				ritualTicks++;
				List<Entity> suckEntities = this.world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(suckRadius * 2, suckRadius * 2, suckRadius * 2));
				for (Entity e : suckEntities) {
					double dx = (xCoord + 0.5D - e.posX);
					double dy = (yCoord + 0.5D - e.posY);
					double dz = (zCoord + 0.5D - e.posZ);
					double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
					if (distance < 3.1) {
						if (e instanceof EntityItem) {
							IChaosItem item = getChaosItem(((EntityItem)e).getItem().getItem());
							if (item != null) {
								world.removeEntity(e);
							}
							continue;
						}
						else if (e instanceof EntityPlayer) {
							EntityPlayer ePlayer = (EntityPlayer) e;
							if (!(ePlayer instanceof FakePlayer)) {
								ePlayer.attackEntityFrom(chaosBurst, 5);
							}
						}
						else if (e instanceof EntityLiving) {
							((EntityLiving) e).attackEntityFrom(chaosBurst, 20);
						}
					}
					double limit = 1.0 - (distance / (suckRadius * diameter));
					if (limit > 0.0D) {
						limit *= limit;
						e.motionX += dx / distance * limit * 0.8D;
						e.motionY += dy / distance * limit * 0.8D;
						e.motionZ += dz / distance * limit * 0.8D;
					}
				}
				if (ritualTicks < 260 && DEEventHandler.serverTicks % 10 == 0) {
					playSound("draconicevolution:sun_dial_effect", 1.0F, 0.5F);
				}
				if (ritualTicks >= 120 && ritualTicks < 260) {
					if (DEEventHandler.serverTicks % 4 == 0 && rand.nextBoolean()) {
						int i = rand.nextInt(4);
						switch (i) {
						case 0: // West Pillar
							world.spawnEntityInWorld(new EntityLightningBolt(world, xCoord + 2, yCoord + 1, zCoord));
							break;
						case 1: // East Pillar
							world.spawnEntityInWorld(new EntityLightningBolt(world, xCoord - 2, yCoord + 1, zCoord));
							break;
						case 2: // South Pillar
							world.spawnEntityInWorld(new EntityLightningBolt(world, xCoord, yCoord + 1, zCoord + 2));
							break;
						case 3: // North Pillar
							world.spawnEntityInWorld(new EntityLightningBolt(world, xCoord, yCoord + 1, zCoord - 2));
							break;
						}
					}
				}
				if (ritualTicks == 200) {
					diameter = 3;
					intensity = 0.8D;
				}
				else if (ritualTicks == 260) {
					diameter = 5.1D;
					intensity = 1;
				}
				if (ritualTicks >= 340) {
					endRitual(checkMultiblock());
				}
			}
			else {
				endRitual(false);
			}
		}
	}

	private IChaosItem getChaosItem(Item item) {
		IChaosItem cItem = null;
		if (item instanceof IChaosItem) {
			cItem = (IChaosItem)item;
		}
		else if (item instanceof ItemBlock && ((ItemBlock)item).getBlock() instanceof IChaosItem) {
			cItem = (IChaosItem)((ItemBlock)item).getBlock();
		}
		return cItem;
	}

	private boolean checkMultiblock() {
		isMultiblock = true;
		List<IntPos> check = new ArrayList<IntPos>();
		check.add(new IntPos(xCoord + 2, yCoord, zCoord));
		check.add(new IntPos(xCoord - 2, yCoord, zCoord));
		check.add(new IntPos(xCoord, yCoord, zCoord + 2));
		check.add(new IntPos(xCoord, yCoord, zCoord - 2));
		for (IntPos checkPos : check) {
			if (world.getBlock(checkPos.x, checkPos.y, checkPos.z).equals(Block.getBlockFromName("draconicevolution:draconic_block"))) continue;
			isMultiblock = false;
			return false;
		}
		check.clear();
		check.addAll(Lists.newArrayList(getAllInBox(-1, -1, -1, 1, 1, 1)));
		for (IntPos checkPos : check) {
			if (checkPos.x == xCoord && checkPos.y == yCoord && checkPos.z == zCoord) continue;
			if (world.isAirBlock(checkPos.x, checkPos.y, checkPos.z)) continue;
			isMultiblock = false;
			return false;
		}
		check.clear();
		check.addAll(Lists.newArrayList(getAllInBox(-2, -2, -2, 2, -2, 2)));
		check.addAll(Lists.newArrayList(getAllInBox(0, -2, -3, 0, 0, -3)));
		check.addAll(Lists.newArrayList(getAllInBox(0, -2, 3, 0, 0, 3)));
		check.addAll(Lists.newArrayList(getAllInBox(-3, -2, 0, -3, 0, 0)));
		check.addAll(Lists.newArrayList(getAllInBox(3, -2, 0, 3, 0, 0)));
		for (IntPos checkPos : check) {
			if (world.getBlock(checkPos.x, checkPos.y, checkPos.z).equals(Block.getBlockFromName("draconicevolution:infused_obsidian"))) continue;
			isMultiblock = false;
			return false;
		}
		return true;
	}

		private static class IntPos {
		final int x;
		final int y;
		final int z;

		IntPos(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

	private List<IntPos> getAllInBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		List<IntPos> positions = new ArrayList<IntPos>();
		for (int x = Math.min(minX, maxX); x <= Math.max(minX, maxX); x++) {
			for (int y = Math.min(minY, maxY); y <= Math.max(minY, maxY); y++) {
				for (int z = Math.min(minZ, maxZ); z <= Math.max(minZ, maxZ); z++) {
					positions.add(new IntPos(xCoord + x, yCoord + y, zCoord + z));
				}
			}
		}
		return positions;
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setDouble("diameter", diameter);
		compound.setDouble("intensity", intensity);
		compound.setInteger("ritualTicks", ritualTicks);
		compound.setBoolean("isRitualOngoing", isRitualOngoing);
		compound.setBoolean("isMultiblock", isMultiblock);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		diameter = compound.getDouble("diameter");
		intensity = compound.getDouble("intensity");
		ritualTicks = compound.getInteger("ritualTicks");
		isRitualOngoing = compound.getBoolean("isRitualOngoing");
		isMultiblock = compound.getBoolean("isMultiblock");
	}

	@Override
	public boolean canRenderBreaking() {
		return true;
	}

	@Override
	public boolean shouldRenderInPass(int pass) {
		return true;
	}

	@Override
	public double getMaxRenderDistanceSquared() {
		return 40960.0D;
	}

	private void sendMessage(EntityPlayer player, String message) {
		if (!world.isRemote) player.addChatMessage(new ChatComponentTranslation(message));
	}

	private void playSound(String sound, float volume, float pitch) {
		if (!world.isRemote) world.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, sound, volume, pitch);
	}

	public double getCoreDiameter() {
		if (diameter > actualDiameter) {
			actualDiameter += 0.002F;
		}
		else if (diameter < actualDiameter) {
			actualDiameter -= 0.002F;
		}

		return actualDiameter;
	}

	public double getCoreIntensity() {
		if (intensity > actualIntensity) {
			actualIntensity += 0.0005F;
		}
		else if (intensity < actualIntensity) {
			actualIntensity -= 0.0005F;
		}
		else if (isMultiblock && rand.nextInt(10) == 0) {
			intensity += rand.nextDouble() / 10;
		}
		return actualIntensity;
	}

	@Override
	public boolean onBlockActivated(net.minecraft.block.Block block, int metadata, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		if (isMultiblock || player.isCreative()) {
				if ((stack != null && stack.stackSize > 0) && stack.stackSize > 0) {
				if ((getStackInSlot(0) == null || getStackInSlot(0).stackSize <= 0)) {
					IChaosItem item = getChaosItem(stack.getItem());
					if (item != null) {
						if (item.isChaosStable(stack)) {
							sendMessage(player, "msg.da.chaosStabilizer.alreadyStabilized");
						}
						else {
							if (player.isCreative()) {
								ItemStack newStack = stack.splitStack(1);
								playSound("mob.enderdragon.growl", 1.0F, 0.2F);
								item.setChaosStable(newStack, true);
								EntityItem chaosItem = new EntityItem(world, xCoord, yCoord + 1.01D, zCoord, newStack);
								world.spawnEntityInWorld(chaosItem);

							}
							else sendMessage(player, "msg.da.chaosStabilizer.canStabilize");
						}
					}
					else {
						sendMessage(player, "msg.da.chaosStabilizer.cannotStabilize");
					}
				}
				else {
					sendMessage(player, "msg.da.chaosStabilizer.full");
				}
			}
			else {
				if (!world.isRemote && !player.isCreative()) {
					ArmorSummery armor = new ArmorSummery().getSummery(player);
					if (armor.maxProtectionPoints > chaosDamage) {
						if (armor.protectionPoints >= armor.maxProtectionPoints / 2) {
							player.attackEntityFrom(new DamageSource("chaosBurst").setDamageBypassesArmor(), (armor.maxProtectionPoints / 2));
						}
						else {
							player.attackEntityFrom(chaosBurst, armor.protectionPoints + (chaosDamage / 4));
						}
					}
					else {
						player.attackEntityFrom(chaosBurst, chaosDamage);
					}
					sendMessage(player, "msg.da.chaosStabilizer.emptyHand");
				}
			}
		}
		else sendMessage(player, "msg.da.chaosStabilizer.notMultiblock");
		return true;
	}

	private void startRitual() {
		if (!isRitualOngoing) {
			playSound("mob.enderdragon.growl", 1.0F, 0.2F);
			isRitualOngoing = true;
			diameter = 2.5D;
			intensity = 0.6D;
			markDirty();
			updateBlock();
		}
	}

	private void endRitual(boolean complete) {
		if (isRitualOngoing) {
			isRitualOngoing = false;
			ritualTicks = 0;
			diameter = 1;
			intensity = 0.25D;
			ItemStack invStack = removeStackFromSlot(0);
			if (!world.isRemote) {
				if (complete) {
					isMultiblock = false;
					List<IntPos> check = new ArrayList<IntPos>();
					check.add(new IntPos(xCoord + 2, yCoord, zCoord));
					check.add(new IntPos(xCoord - 2, yCoord, zCoord));
					check.add(new IntPos(xCoord, yCoord, zCoord + 2));
					check.add(new IntPos(xCoord, yCoord, zCoord - 2));
					for (IntPos checkPos : check) {
						world.setBlockToAir(checkPos.x, checkPos.y, checkPos.z);
						world.spawnEntityInWorld(new EntityLightningBolt(world, checkPos.x, checkPos.y + 1, checkPos.z));
					}
					getChaosItem(invStack.getItem()).setChaosStable(invStack, true);
				}
				EntityItem chaosItem = new EntityItem(world, xCoord, yCoord + 1.01D, zCoord, invStack);
				world.spawnEntityInWorld(chaosItem);
				chaosItem.motionX = 0;
				chaosItem.motionY = 0;
				chaosItem.motionZ = 0;
			}
			markDirty();
			updateBlock();
		}
	}
}
