package net.foxmcloud.draconicadditions.items.armor;

import java.util.List;


import com.brandon3055.draconicevolution.DEConfig;
import com.brandon3055.draconicevolution.api.itemconfig.ToolConfigHelper;
import com.brandon3055.draconicevolution.api.itemupgrade.UpgradeHelper;
import com.brandon3055.draconicevolution.lib.RecipeManager;

import net.foxmcloud.draconicadditions.DAConfig;
import net.foxmcloud.draconicadditions.DAFeatures;
import net.foxmcloud.draconicadditions.DraconicAdditions;
import net.foxmcloud.draconicadditions.client.model.ModelPotatoArmor;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HermalArmor extends ChaoticArmor {

	private static ArmorMaterial hermalMaterial = EnumHelper.addArmorMaterial("hermalArmor", DraconicAdditions.MODID_PREFIX + "hermal_armor", -1, new int[] {8, 14, 20, 8}, 0, "random.pop", 0.0F);

	public HermalArmor(int renderIndexIn, int armorType) {
		super(hermalMaterial, renderIndexIn, armorType);
	}

	public HermalArmor(ArmorMaterial materialIn, int renderIndexIn, int armorType) {
		super(materialIn, renderIndexIn, armorType);
	}

	@Override
	public void getSubItems(CreativeTabs tab, List<ItemStack> subItems) {
		if (RecipeManager.isEnabled(DAFeatures.hermal)) {
			if (isInCreativeTab(tab)) {
				ItemStack stack = new ItemStack(this);
				setChaosStable(stack, true);
				modifyEnergy(stack, getCapacity(stack));
				subItems.add(stack);
				if (getMaxUpgradeLevel(stack, "") > 0) {
					ItemStack uberStack = new ItemStack(this);
					setChaosStable(uberStack, true);
					for (String upgrade : getValidUpgrades(uberStack)) {
						UpgradeHelper.setUpgradeLevel(uberStack, upgrade, getMaxUpgradeLevel(uberStack, upgrade));
					}
					modifyEnergy(uberStack, getCapacity(uberStack));
					subItems.add(uberStack);
				}
			}
		}
	}

	@Override
	public int getMaxUpgradeLevel(ItemStack stack, String upgrade) {
		return ArmorStats.HERMAL_UPGRADE_LEVEL;
	}

	@SideOnly(Side.CLIENT)
	public ModelBiped model;

	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot, ModelBiped _default) {
		if (ToolConfigHelper.getBooleanField("hideArmor", itemStack)) {
			if (model_invisible == null) {
				model_invisible = new ModelBiped() {
					@Override
					public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {}
				};
			}

			return model_invisible;
		}

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
		float points = ArmorStats.HERMAL_BASE_SHIELD_CAPACITY * getProtectionShare();
		return points;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if ((stack != null && stack.stackSize > 0)) {
			if (!isChaosStable(stack)) setChaosStable(stack, true);
			HermalArmor armor = (HermalArmor)stack.getItem();
			armor.modifyEnergy(stack, DAConfig.HERMAL_RF);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		tooltip.add(I18n.format("item.draconicadditions:hermal.lore"));
		tooltip.add(I18n.format("item.draconicadditions:hermal.lore2"));
		super.addInformation(stack, playerIn, tooltip, advanced);
	}

	@Override
	public float getRecoveryRate(ItemStack stack) {
		return (float) ArmorStats.HERMAL_SHIELD_RECOVERY;
	}

	@Override
	public int getEnergyPerProtectionPoint() {
		return ArmorStats.HERMAL_SHIELD_RECHARGE_COST;
	}

	@Override
	protected int getCapacity(ItemStack stack) {
		return ArmorStats.HERMAL_BASE_CAPACITY;
	}

	@Override
	protected int getMaxReceive(ItemStack stack) {
		return ArmorStats.HERMAL_MAX_RECIEVE;
	}
}
