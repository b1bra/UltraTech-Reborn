package net.foxmcloud.draconicadditions.items.armor;

import java.util.List;

import javax.annotation.Nullable;

import com.brandon3055.brandonscore.utils.ItemNBTHelper;
import com.brandon3055.draconicevolution.DEConfig;
import com.brandon3055.draconicevolution.DEFeatures;
import com.brandon3055.draconicevolution.api.itemconfig.ItemConfigFieldRegistry;
import com.brandon3055.draconicevolution.items.armor.WyvernArmor;

import net.foxmcloud.draconicadditions.DraconicAdditions;
import net.foxmcloud.draconicadditions.client.model.ModelPotatoArmor;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PotatoArmor extends WyvernArmor {

	private static ArmorMaterial potatoMaterial = EnumHelper.addArmorMaterial("potatoArmor", DraconicAdditions.MODID_PREFIX + "potato_armor", -1, new int[] {1, 1, 2, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);

	public PotatoArmor(int renderIndexIn, int armorType) {
		super(potatoMaterial, renderIndexIn, armorType);
	}

	public PotatoArmor(ArmorMaterial materialIn, int renderIndexIn, int armorType) {
		super(materialIn, renderIndexIn, armorType);
	}

	@Override
	public int getMaxUpgradeLevel(ItemStack stack, String upgrade) {
		return ArmorStats.POTATO_UPGRADE_LEVEL;
	}

	@Override
	public ItemConfigFieldRegistry getFields(ItemStack stack, ItemConfigFieldRegistry registry) {
		return registry;
	}

	@SideOnly(Side.CLIENT)
	public ModelBiped model;

	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot, ModelBiped _default) {
		if (DEConfig.disable3DModels) {
			return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
		}

		if (model == null) {
			if (armorType == 0) model = new ModelPotatoArmor(0.5F, true, false, false, false);
			else if (armorType == 1) model = new ModelPotatoArmor(1.5F, false, true, false, false);
			else if (armorType == 2) model = new ModelPotatoArmor(1.5F, false, false, true, false);
			else model = new ModelPotatoArmor(1F, false, false, false, true);
			this.model.bipedHead.showModel = (armorType == 0);
			this.model.bipedHeadwear.showModel = (armorType == 0);
			this.model.bipedBody.showModel = ((armorType == 1) || (armorType == 2));
			this.model.bipedLeftArm.showModel = (armorType == 1);
			this.model.bipedRightArm.showModel = (armorType == 1);
			this.model.bipedLeftLeg.showModel = (armorType == 2 || armorType == 3);
			this.model.bipedRightLeg.showModel = (armorType == 2 || armorType == 3);
		}

		if (entityLiving == null) {
			return model;
		}

		this.model.isSneak = entityLiving.isSneaking();
		this.model.isRiding = entityLiving.isRiding();
		this.model.isChild = entityLiving.isChild();

		this.model.bipedHeadwear.showModel = (armorType == 0);
		this.model.bipedBody.showModel = ((armorType == 1) || (armorType == 2));
		this.model.bipedLeftArm.showModel = (armorType == 1);
		this.model.bipedRightArm.showModel = (armorType == 1);
		this.model.bipedLeftLeg.showModel = (armorType == 2 || armorType == 3);
		this.model.bipedRightLeg.showModel = (armorType == 2 || armorType == 3);

		return model;
	}

	@Override
	public float getProtectionPoints(ItemStack stack) {
		float points = ArmorStats.POTATO_BASE_SHIELD_CAPACITY * getProtectionShare();
		return points;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if ((stack != null && stack.stackSize > 0) && ArmorStats.POTATO_BREAK_ON_EXHAUST) {
			PotatoArmor armor = (PotatoArmor) stack.getItem();
			if (armor.getEnergyStored(stack) < 1000 && ItemNBTHelper.getFloat(stack, "ProtectionPoints", 0) == 0) {
				ItemStack draconiumDust = new ItemStack(DEFeatures.draconiumDust);
				ItemStack diamond = new ItemStack(Items.DIAMOND);
				if (DEConfig.hardMode) {
					draconiumDust.setCount(36 + (int) Math.round(Math.random() * 28));
					diamond.setCount(9 + (int) Math.round(Math.random() * 9));
				}
				else {
					draconiumDust.setCount(4 + (int) Math.round(Math.random() * 3));
					diamond.setCount(1 + (int) Math.round(Math.random()));
				}
				if (ArmorStats.POTATO_DROP_ON_EXHAUST) {
					player.addItemStackToInventory(draconiumDust);
					player.addItemStackToInventory(diamond);
				}
				player.inventory.deleteStack(stack);
				player.sendStatusMessage(new TextComponentTranslation("msg.da.energizedBreak"), true);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18n.format("item.draconicadditions:potato.lore"));
		tooltip.add(I18n.format("item.draconicadditions:potato.lore2"));
	}

	@Override
	public float getRecoveryRate(ItemStack stack) {
		return (float) ArmorStats.POTATO_SHIELD_RECOVERY;
	}

	@Override
	public boolean hasHillStep(ItemStack stack, EntityPlayer player) {
		return false;
	}

	@Override
	public float getFireResistance(ItemStack stack) {
		return 0;
	}

	@Override
	public boolean[] hasFlight(ItemStack stack) {
		return new boolean[] {false, false, false};
	}

	@Override
	public float getFlightSpeedModifier(ItemStack stack, EntityPlayer player) {
		return 0;
	}

	@Override
	public float getFlightVModifier(ItemStack stack, EntityPlayer player) {
		return 0;
	}

	@Override
	public int getEnergyPerProtectionPoint() {
		return ArmorStats.POTATO_SHIELD_RECHARGE_COST;
	}

	@Override
	protected int getCapacity(ItemStack stack) {
		return ArmorStats.POTATO_BASE_CAPACITY;
	}

	@Override
	protected int getMaxReceive(ItemStack stack) {
		return ArmorStats.POTATO_MAX_RECIEVE;
	}
}
