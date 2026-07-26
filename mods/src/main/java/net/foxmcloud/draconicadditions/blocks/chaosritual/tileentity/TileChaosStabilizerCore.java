package net.foxmcloud.draconicadditions.blocks.chaosritual.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.brandon3055.brandonscore.blocks.TileInventoryBase;
import com.brandon3055.brandonscore.lib.IActivatableTile;
import com.brandon3055.brandonscore.lib.Vec3D;
import com.brandon3055.brandonscore.lib.Vec3I;
import com.brandon3055.brandonscore.lib.datamanager.ManagedBool;
import com.brandon3055.brandonscore.lib.datamanager.ManagedDouble;
import com.brandon3055.brandonscore.lib.datamanager.ManagedInt;
import com.brandon3055.draconicevolution.handlers.CustomArmorHandler.ArmorSummery;
import com.brandon3055.draconicevolution.handlers.DEEventHandler;
import com.brandon3055.draconicevolution.lib.DESoundHandler;
import com.google.common.collect.Lists;

import net.foxmcloud.draconicadditions.items.IChaosItem;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IUpdatePlayerListBox;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.FakePlayer;

public class TileChaosStabilizerCore extends TileInventoryBase implements IUpdatePlayerListBox, IActivatableTile {

	public final ManagedDouble diameter = register("diameter", new ManagedDouble(1)).syncViaTile().finish();
	public final ManagedDouble intensity = register("intensity", new ManagedDouble(0)).syncViaTile().finish();
	private static final float chaosDamage = 10;
	private static final double suckRadius = 6;
	private static final int delayInMultiblockCheck = 20;
	private double actualDiameter = diameter.value;
	private double actualIntensity = intensity.value;
	private final ManagedInt ritualTicks = register("ritualTicks", new ManagedInt(0)).syncViaTile().finish();
	public final ManagedBool isRitualOngoing = register("isRitualOngoing", new ManagedBool(false)).syncViaTile().finish();
	public final ManagedBool isMultiblock = register("isMultiblock", new ManagedBool(false)).syncViaTile().finish();
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
		if (!isRitualOngoing.value) {
			if (isMultiblock.value) {
				intensity.value = 0.25F;
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
							playSound(SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, 1.0F, 1.0F);
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
			else intensity.value = 0;
		}
		else {
			if (isMultiblock.value) {
				ritualTicks.value++;
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
					double limit = 1.0 - (distance / (suckRadius * diameter.value));
					if (limit > 0.0D) {
						limit *= limit;
						e.motionX += dx / distance * limit * 0.8D;
						e.motionY += dy / distance * limit * 0.8D;
						e.motionZ += dz / distance * limit * 0.8D;
					}
				}
				if (ritualTicks.value < 260 && DEEventHandler.serverTicks % 10 == 0) {
					playSound(DESoundHandler.sunDialEffect, 1.0F, 0.5F);
				}
				if (ritualTicks.value >= 120 && ritualTicks.value < 260) {
					if (DEEventHandler.serverTicks % 4 == 0 && rand.nextBoolean()) {
						int i = rand.nextInt(4);
						switch (i) {
						case 0: // West Pillar
							world.spawnEntity(new EntityLightningBolt(world, xCoord + 2, yCoord + 1, zCoord, false));
							break;
						case 1: // East Pillar
							world.spawnEntity(new EntityLightningBolt(world, xCoord - 2, yCoord + 1, zCoord, false));
							break;
						case 2: // South Pillar
							world.spawnEntity(new EntityLightningBolt(world, xCoord, yCoord + 1, zCoord + 2, false));
							break;
						case 3: // North Pillar
							world.spawnEntity(new EntityLightningBolt(world, xCoord, yCoord + 1, zCoord - 2, false));
							break;
						}
					}
				}
				if (ritualTicks.value == 200) {
					diameter.value = 3;
					intensity.value = 0.8D;
				}
				else if (ritualTicks.value == 260) {
					diameter.value = 5.1D;
					intensity.value = 1;
				}
				if (ritualTicks.value >= 340) {
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
		isMultiblock.value = true;
		List<IntPos> check = new ArrayList<IntPos>();
		check.add(new IntPos(xCoord + 2, yCoord, zCoord));
		check.add(new IntPos(xCoord - 2, yCoord, zCoord));
		check.add(new IntPos(xCoord, yCoord, zCoord + 2));
		check.add(new IntPos(xCoord, yCoord, zCoord - 2));
		for (IntPos checkPos : check) {
			if (world.getBlock(checkPos.x, checkPos.y, checkPos.z).equals(Block.getBlockFromName("draconicevolution:draconic_block"))) continue;
			isMultiblock.value = false;
			return false;
		}
		check.clear();
		check.addAll(Lists.newArrayList(getAllInBox(-1, -1, -1, 1, 1, 1)));
		for (IntPos checkPos : check) {
			if (checkPos.x == xCoord && checkPos.y == yCoord && checkPos.z == zCoord) continue;
			if (world.isAirBlock(checkPos.x, checkPos.y, checkPos.z)) continue;
			isMultiblock.value = false;
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
			isMultiblock.value = false;
			return false;
		}
		return true;
	}

	private IntPos getOffsetPos(Vec3I vec) {
		return new IntPos(xCoord - vec.x, yCoord - vec.y, zCoord - vec.z);
	}

	private Vec3I getOffsetVec(IntPos offsetPos) {
		return new Vec3I(xCoord - offsetPos.x, yCoord - offsetPos.y, zCoord - offsetPos.z);
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
		if (!world.isRemote) player.sendStatusMessage(new TextComponentTranslation(message), true);
	}

	private void playSound(SoundEvent sound, float volume, float pitch) {
		if (!world.isRemote) DESoundHandler.playSoundFromServer(world, new Vec3D(xCoord, yCoord, zCoord), sound, volume, pitch, false, 128);
	}

	public double getCoreDiameter() {
		if (diameter.value > actualDiameter) {
			actualDiameter += 0.002F;
		}
		else if (diameter.value < actualDiameter) {
			actualDiameter -= 0.002F;
		}

		return actualDiameter;
	}

	public double getCoreIntensity() {
		if (intensity.value > actualIntensity) {
			actualIntensity += 0.0005F;
		}
		else if (intensity.value < actualIntensity) {
			actualIntensity -= 0.0005F;
		}
		else if (isMultiblock.value && rand.nextInt(10) == 0) {
			intensity.value += rand.nextDouble() / 10;
		}
		return actualIntensity;
	}

	@Override
	public boolean onBlockActivated(net.minecraft.block.Block block, int metadata, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		if (isMultiblock.value || player.isCreative()) {
			ItemStack stack = player.getHeldItem();
			if ((stack != null && stack.stackSize > 0) && stack.getCount() > 0) {
				if ((getStackInSlot(0) == null || getStackInSlot(0).stackSize <= 0)) {
					IChaosItem item = getChaosItem(stack.getItem());
					if (item != null) {
						if (item.isChaosStable(stack)) {
							sendMessage(player, "msg.da.chaosStabilizer.alreadyStabilized");
						}
						else {
							if (player.isCreative()) {
								ItemStack newStack = stack.splitStack(1);
								playSound(SoundEvents.ENTITY_ENDERDRAGON_GROWL, 1.0F, 0.2F);
								item.setChaosStable(newStack, true);
								EntityItem chaosItem = new EntityItem(world, xCoord, yCoord + 1.01D, zCoord, newStack);
								world.spawnEntity(chaosItem);
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
		if (!isRitualOngoing.value) {
			playSound(SoundEvents.ENTITY_ENDERDRAGON_GROWL, 1.0F, 0.2F);
			isRitualOngoing.value = true;
			diameter.value = 2.5D;
			intensity.value = 0.6D;
			markDirty();
			updateBlock();
		}
	}

	private void endRitual(boolean complete) {
		if (isRitualOngoing.value) {
			isRitualOngoing.value = false;
			ritualTicks.value = 0;
			diameter.value = 1;
			intensity.value = 0.25D;
			ItemStack invStack = removeStackFromSlot(0);
			if (!world.isRemote) {
				if (complete) {
					isMultiblock.value = false;
					List<IntPos> check = new ArrayList<IntPos>();
					check.add(new IntPos(xCoord + 2, yCoord, zCoord));
					check.add(new IntPos(xCoord - 2, yCoord, zCoord));
					check.add(new IntPos(xCoord, yCoord, zCoord + 2));
					check.add(new IntPos(xCoord, yCoord, zCoord - 2));
					for (IntPos checkPos : check) {
						world.setBlockToAir(checkPos.x, checkPos.y, checkPos.z);
						world.spawnEntity(new EntityLightningBolt(world, checkPos.x, checkPos.y + 1, checkPos.z, true));
					}
					getChaosItem(invStack.getItem()).setChaosStable(invStack, true);
				}
				EntityItem chaosItem = new EntityItem(world, xCoord, yCoord + 1.01D, zCoord, invStack);
				world.spawnEntity(chaosItem);
				chaosItem.motionX = 0;
				chaosItem.motionY = 0;
				chaosItem.motionZ = 0;
			}
			markDirty();
			updateBlock();
		}
	}
}
