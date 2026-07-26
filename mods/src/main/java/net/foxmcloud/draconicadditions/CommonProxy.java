package net.foxmcloud.draconicadditions;

import com.brandon3055.draconicevolution.DraconicEvolution;
import com.brandon3055.draconicevolution.entity.EntityDragonHeart;

import net.foxmcloud.draconicadditions.entity.EntityChaosHeart;
import net.foxmcloud.draconicadditions.entity.EntityPlug;
import net.foxmcloud.draconicadditions.handlers.DAEventHandler;
import net.foxmcloud.draconicadditions.integration.AE2Compat;
import net.foxmcloud.draconicadditions.lib.DARecipes;
import net.foxmcloud.draconicadditions.lib.FusionCostMultiplier;
import net.foxmcloud.draconicadditions.network.PacketChaosInjection;
import net.foxmcloud.draconicadditions.network.PacketOverloadBelt;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent event) {
		// Forge 1.7.10 has no RegistryEvent; register mod features directly during preInit.
		DAFeatures.registerFor1710();
		MinecraftForge.EVENT_BUS.register(new DAEventHandler());
		EntityRegistry.registerModEntity(new ResourceLocation(DraconicAdditions.MODID, "plug"), EntityPlug.class, "draconicadditions:plug", 1, DraconicAdditions.instance, 64, 5, false);
		EntityRegistry.registerModEntity(new ResourceLocation(DraconicAdditions.MODID, "chaosHeart"), EntityChaosHeart.class, "draconicadditions:chaosheartitem", 2, DraconicAdditions.instance, 64, 5, false);
	}

	public void init(FMLInitializationEvent event) {
		DraconicAdditions.network = NetworkRegistry.INSTANCE.newSimpleChannel(DraconicAdditions.networkChannelName);
		DraconicAdditions.network.registerMessage(PacketOverloadBelt.Handler.class, PacketOverloadBelt.class, 0, Side.SERVER);
		DraconicAdditions.network.registerMessage(PacketChaosInjection.Handler.class, PacketChaosInjection.class, 1, Side.SERVER);
		DARecipes.addRecipes();
		AE2Compat.init();
	}

	public void postInit(FMLPostInitializationEvent event) {
		FusionCostMultiplier.postInit();
	}
}
