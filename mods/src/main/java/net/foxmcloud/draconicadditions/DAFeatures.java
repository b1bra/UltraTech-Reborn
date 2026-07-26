package net.foxmcloud.draconicadditions;


import com.brandon3055.brandonscore.blocks.ItemBlockBCore;
import com.brandon3055.draconicevolution.blocks.tileentity.TileChaosCrystal;
import com.brandon3055.draconicevolution.items.ItemPersistent;

import net.foxmcloud.draconicadditions.blocks.ChaosCrystalStable;
import net.foxmcloud.draconicadditions.blocks.chaosritual.ChaosStabilizerCore;
import net.foxmcloud.draconicadditions.blocks.chaosritual.tileentity.TileChaosStabilizerCore;
import net.foxmcloud.draconicadditions.blocks.machines.*;
import net.foxmcloud.draconicadditions.blocks.tileentity.*;
import net.foxmcloud.draconicadditions.items.*;
import net.foxmcloud.draconicadditions.items.armor.*;
import net.foxmcloud.draconicadditions.items.baubles.*;
import net.foxmcloud.draconicadditions.items.tools.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(DraconicAdditions.MODID)
public class DAFeatures {

	// Crafting Components
	public static Item inertPotatoHelm = new Item();
	public static Item inertPotatoChest = new Item();
	public static Item inertPotatoLegs = new Item();
	public static Item inertPotatoBoots = new Item();
    public static ItemPersistent chaosHeart = new ItemPersistent();
	public static ChaoticEnergyCore chaoticEnergyCore = new ChaoticEnergyCore();
    public static Hermal hermal = new Hermal();

	// Blocks
	public static ChaosStabilizerCore chaosStabilizerCore = new ChaosStabilizerCore();
	public static ArmorGenerator armorGenerator = new ArmorGenerator();
	public static ChaoticArmorGenerator chaoticArmorGenerator = new ChaoticArmorGenerator();
	public static ItemDrainer itemDrainer = new ItemDrainer();
	public static ChaosLiquefier chaosLiquefier = new ChaosLiquefier();
	public static CapacitorSupplier capacitorSupplier = new CapacitorSupplier();

	// TODO 1.7.10: chaos_infuser remains disabled until its block/tile port is completed manually.
	//public static ChaosInfuser chaosInfuser = new ChaosInfuser();

	// Tools
	public static ChaoticStaffOfPower chaoticStaffOfPower = new ChaoticStaffOfPower();
	public static ChaoticBow chaoticBow = new ChaoticBow();
	public static ChaosContainer chaosContainer = new ChaosContainer();
	public static PortableWiredCharger pwc = new PortableWiredCharger();
	public static ItemStack pwcBasic = new ItemStack(pwc, 1, 0);
	public static ItemStack pwcWyvern = new ItemStack(pwc, 1, 1);
	public static ItemStack pwcDraconic = new ItemStack(pwc, 1, 2);
	public static ItemStack pwcChaotic = new ItemStack(pwc, 1, 3);
	public static PortableWiredDischarger pwd = new PortableWiredDischarger();
	public static ItemStack pwdBasic = new ItemStack(pwd, 1, 0);
	public static ItemStack pwdWyvern = new ItemStack(pwd, 1, 1);
	public static ItemStack pwdDraconic = new ItemStack(pwd, 1, 2);
	public static ItemStack pwdChaotic = new ItemStack(pwd, 1, 3);

	// Potato Armor
	public static InfusedPotatoArmor infusedPotatoHelm = new InfusedPotatoArmor(0, 0);
	public static InfusedPotatoArmor infusedPotatoChest = new InfusedPotatoArmor(1, 1);
	public static InfusedPotatoArmor infusedPotatoLegs = new InfusedPotatoArmor(2, 2);
	public static InfusedPotatoArmor infusedPotatoBoots = new InfusedPotatoArmor(3, 3);
	public static PotatoArmor potatoHelm = new PotatoArmor(0, 0);
	public static PotatoArmor potatoChest = new PotatoArmor(1, 1);
	public static PotatoArmor potatoLegs = new PotatoArmor(2, 2);
	public static PotatoArmor potatoBoots = new PotatoArmor(3, 3);
	public static HermalArmor hermalHelm = new HermalArmor(0, 0);
	public static HermalArmor hermalChest = new HermalArmor(1, 1);
	public static HermalArmor hermalLegs = new HermalArmor(2, 2);
	public static HermalArmor hermalBoots = new HermalArmor(3, 3);

	// Chaotic Armor
	public static ChaoticArmor chaoticHelm = new ChaoticArmor(0, 0);
	public static ChaoticArmor chaoticChest = new ChaoticArmor(1, 1);
	public static ChaoticArmor chaoticLegs = new ChaoticArmor(2, 2);
	public static ChaoticArmor chaoticBoots = new ChaoticArmor(3, 3);

	// Shield Baubles
	public static ShieldNecklace basicShieldNecklace = new ShieldNecklace(0);
	public static ShieldNecklace wyvernShieldNecklace = new ShieldNecklace(1);
	public static ShieldNecklace draconicShieldNecklace = new ShieldNecklace(2);

	// Other Baubles
	public static OverloadBelt overloadBelt = new OverloadBelt();
	public static VampiricShirt vampiricShirt = new VampiricShirt();
	public static InertiaCancelRing inertiaCancelRing = new InertiaCancelRing();

	// Misc / Decor
	public static ChaosCrystalStable chaosCrystalStable = new ChaosCrystalStable();
	public static void registerFor1710() {
		registerItem(inertPotatoHelm, "inert_potato_helm");
		registerItem(inertPotatoChest, "inert_potato_chest");
		registerItem(inertPotatoLegs, "inert_potato_legs");
		registerItem(inertPotatoBoots, "inert_potato_boots");
		registerItem(chaosHeart, "chaos_heart");
		registerItem(chaoticEnergyCore, "chaotic_energy_core");
		registerItem(hermal, "hermal");
		registerBlock(chaosStabilizerCore, "chaos_stabilizer_core", TileChaosStabilizerCore.class);
		registerBlock(armorGenerator, "armor_generator", TileArmorGenerator.class);
		registerBlock(chaoticArmorGenerator, "chaotic_armor_generator", TileChaoticArmorGenerator.class);
		registerBlock(itemDrainer, "item_drainer", TileItemDrainer.class);
		registerBlock(chaosLiquefier, "chaos_liquefier", TileChaosLiquefier.class);
		registerBlock(capacitorSupplier, "capacitor_supplier", TileCapacitorSupplier.class);
		registerItem(chaoticStaffOfPower, "chaotic_staff_of_power");
		registerItem(chaoticBow, "chaotic_bow");
		registerItem(chaosContainer, "chaos_container");
		registerItem(pwc, "portable_wired_charger");
		registerItem(pwd, "portable_wired_discharger");
		registerItem(infusedPotatoHelm, "infused_potato_helm");
		registerItem(infusedPotatoChest, "infused_potato_chest");
		registerItem(infusedPotatoLegs, "infused_potato_legs");
		registerItem(infusedPotatoBoots, "infused_potato_boots");
		registerItem(potatoHelm, "potato_helm");
		registerItem(potatoChest, "potato_chest");
		registerItem(potatoLegs, "potato_legs");
		registerItem(potatoBoots, "potato_boots");
		registerItem(hermalHelm, "hermal_helm");
		registerItem(hermalChest, "hermal_chest");
		registerItem(hermalLegs, "hermal_legs");
		registerItem(hermalBoots, "hermal_boots");
		registerItem(chaoticHelm, "chaotic_helm");
		registerItem(chaoticChest, "chaotic_chest");
		registerItem(chaoticLegs, "chaotic_legs");
		registerItem(chaoticBoots, "chaotic_boots");
		registerItem(basicShieldNecklace, "basic_shield_necklace");
		registerItem(wyvernShieldNecklace, "wyvern_shield_necklace");
		registerItem(draconicShieldNecklace, "draconic_shield_necklace");
		registerItem(overloadBelt, "overload_belt");
		registerItem(vampiricShirt, "vampiric_shirt");
		registerItem(inertiaCancelRing, "inertia_cancel_ring");
		registerBlock(chaosCrystalStable, "chaos_crystal_stable", TileChaosCrystal.class);
	}

	private static void registerItem(Item item, String name) {
		item.setUnlocalizedName(DraconicAdditions.MODID + ":" + name);
		GameRegistry.registerItem(item, name);
	}

	private static void registerBlock(Block block, String name, Class tileEntityClass) {
		block.setBlockName(DraconicAdditions.MODID + ":" + name);
		GameRegistry.registerBlock(block, ItemBlockBCore.class, name);
		GameRegistry.registerTileEntity(tileEntityClass, DraconicAdditions.MODID + ":" + name);
	}

}
