package net.foxmcloud.draconicadditions.integration;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;

public class AE2Compat {
	public static void init() {
		if (Loader.isModLoaded("appliedenergistics2")) {
			FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", "net.foxmcloud.draconicadditions.blocks.tileentity.TileArmorGenerator");
			FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", "net.foxmcloud.draconicadditions.blocks.chaosritual.tileentity.TileChaosStabilizerCore");
		}
	}
}
