package net.foxmcloud.draconicadditions.capabilities;

import net.foxmcloud.draconicadditions.DraconicAdditions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ChaosInBloodProvider extends ChaosInBlood implements IExtendedEntityProperties {

	public static final String NAME = DraconicAdditions.MODID + "_chaos_in_blood";

	public static void register(EntityPlayer player) {
		if (get(player) == null) {
			player.registerExtendedProperties(NAME, new ChaosInBloodProvider());
		}
	}

	public static IChaosInBlood get(EntityPlayer player) {
		if (player == null) return null;
		return (IChaosInBlood) player.getExtendedProperties(NAME);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("Chaos", getChaos());
		tag.setFloat("LastChaos", getLastChaos());
		compound.setTag(NAME, tag);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		if (compound.hasKey(NAME)) {
			NBTTagCompound tag = compound.getCompoundTag(NAME);
			setChaos(tag.getFloat("Chaos"));
			setLastChaos(tag.getFloat("LastChaos"));
		}
	}

	@Override
	public void init(Entity entity, World world) {}
}
