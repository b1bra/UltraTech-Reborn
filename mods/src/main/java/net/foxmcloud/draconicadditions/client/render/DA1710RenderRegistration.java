package net.foxmcloud.draconicadditions.client.render;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.foxmcloud.draconicadditions.DAFeatures;
import net.foxmcloud.draconicadditions.client.render.block.RenderBlock1710Stub;
import net.foxmcloud.draconicadditions.client.render.item.RenderItemChaosCrystal;
import net.foxmcloud.draconicadditions.client.render.item.RenderItemChaosStabilizerCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class DA1710RenderRegistration {
	public static int machineRenderId;

	public static void registerItemModels() {
		// Forge 1.7.10 normally uses IItemRenderer; keep the ItemModelMesher hook requested
		// by the backport checklist isolated here for packs that provide the compatibility shim.
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		// TODO 1.7.10: wire mesher.register(...) once concrete item model resources are ported.
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(DAFeatures.chaosCrystalStable), new RenderItemChaosCrystal());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(DAFeatures.chaosStabilizerCore), new RenderItemChaosStabilizerCore());
	}

	public static void registerBlockRenderers() {
		machineRenderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderBlock1710Stub(machineRenderId));
	}
}
