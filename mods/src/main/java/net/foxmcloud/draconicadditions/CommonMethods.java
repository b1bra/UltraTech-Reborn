package net.foxmcloud.draconicadditions;

import com.brandon3055.brandonscore.util.ItemNBTHelper;
import com.brandon3055.draconicevolution.handlers.CustomArmorHandler.ArmorSummery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class CommonMethods {

	public static final DamageSource chaosBurst = new DamageSource("chaosBurst").setDamageBypassesArmor();
	private static final short gracePeriod = 100;

	public static float subtractShielding(EntityPlayer player, float damageAmount, float entropyDamageStatic, float entropyDamageFactor) {
		ArmorSummery summary = new ArmorSummery().getSummery(player);
		if (summary == null || summary.protectionPoints <= 1) {
			return 0;
		}
		float newEntropy = Math.min(summary.entropy + entropyDamageStatic + (damageAmount * entropyDamageFactor / 20), 100F);
		float totalAbsorbed = 0;
		int remainingPoints = 0;
		for (int i = 0; i < summary.allocation.length; i++) {
			if (summary.allocation[i] == 0) continue;
			ItemStack armor = summary.armorStacks.get(i);
			float dmgShear = summary.allocation[i] / summary.protectionPoints;
			float dmg = dmgShear * damageAmount;
			float absorbed = Math.min(dmg, summary.allocation[i]);
			totalAbsorbed += absorbed;
			summary.allocation[i] -= absorbed;
			remainingPoints += summary.allocation[i];
			ItemNBTHelper.setFloat(armor, "ProtectionPoints", summary.allocation[i]);
			ItemNBTHelper.setFloat(armor, "ShieldEntropy", newEntropy);
		}
		summary.saveStacks(player);
		return totalAbsorbed;
	}

	public static float subtractShielding(EntityPlayer player, float damageAmount) {
		return subtractShielding(player, damageAmount, 1.0F, 1.0F);
	}

	//Must be called every tick, else check will fail.
	public static boolean cheatCheck(ItemStack stack, World world) {
		long containerTime = ItemNBTHelper.getLong(stack, "cheatCheck", 0);
		long serverTime = world.getTotalWorldTime();
		boolean isCheating = false;
		if (containerTime < serverTime - gracePeriod && containerTime > gracePeriod)  {
			isCheating = true;
		}
		ItemNBTHelper.setLong(stack, "cheatCheck", serverTime);
		return isCheating;
	}

	public static void explodeEntity(double x, double y, double z, World world) {
		world.playSoundEffect(x, y, z, "draconicevolution:beam", 0.25F, 0.5F);
		world.playSoundEffect(x, y, z, "draconicevolution:fusion_complete", 1.0F, 2.0F);
		if (world.isRemote) {
			for (int i = 0; i < 5; i++) {
				world.spawnParticle("largeexplode", x, y, z, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	public static void explodeEntity(int x, int y, int z, World world) {
		explodeEntity((double)x, (double)y, (double)z, world);
	}
}
