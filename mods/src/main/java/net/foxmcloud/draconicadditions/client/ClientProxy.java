package net.foxmcloud.draconicadditions.client;

import codechicken.lib.texture.TextureUtils;
import codechicken.lib.util.ResourceUtils;
import net.foxmcloud.draconicadditions.CommonProxy;
import net.foxmcloud.draconicadditions.DraconicAdditions;
import net.foxmcloud.draconicadditions.client.keybinding.KeyBindings;
import net.foxmcloud.draconicadditions.client.keybinding.KeyInputHandler;
import net.foxmcloud.draconicadditions.client.model.DAArmorModelHelper;
import net.foxmcloud.draconicadditions.client.render.DA1710RenderRegistration;
import net.foxmcloud.draconicadditions.client.render.entity.RenderChaosHeart;
import net.foxmcloud.draconicadditions.client.render.entity.RenderPlug;
import net.foxmcloud.draconicadditions.entity.EntityChaosHeart;
import net.foxmcloud.draconicadditions.entity.EntityPlug;
import net.foxmcloud.draconicadditions.utils.DATextures;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
		OBJLoader.INSTANCE.addDomain(DraconicAdditions.MODID);
		TextureUtils.addIconRegister(new DAArmorModelHelper());
		TextureUtils.addIconRegister(new DATextures());
		ResourceUtils.registerReloadListener(new DATextures());
		RenderingRegistry.registerEntityRenderingHandler(EntityPlug.class, RenderPlug::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityChaosHeart.class, RenderChaosHeart::new);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		KeyBindings.init();
		DA1710RenderRegistration.registerItemModels();
		DA1710RenderRegistration.registerBlockRenderers();
	}
}
