package ru.loliland.lolisimulation;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import ru.loliland.lolisimulation.compat.CompatibilityLayer;
import ru.loliland.lolisimulation.diagnostics.Diagnostics;
import ru.loliland.lolisimulation.services.ServiceRegistry;

@Mod(modid = LoliSimulation.MODID, name = LoliSimulation.NAME, version = LoliSimulation.VERSION, acceptedMinecraftVersions = "[1.7.10]")
public final class LoliSimulation {
    public static final String MODID = "lolisimulation";
    public static final String NAME = "LoliSimulation";
    public static final String VERSION = "1.7.10-0.1.0";

    @Mod.Instance(MODID)
    public static LoliSimulation instance;

    private final ServiceRegistry services = new ServiceRegistry();
    private final Diagnostics diagnostics = new Diagnostics();
    private final CompatibilityLayer compatibility = new CompatibilityLayer(services, diagnostics);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        diagnostics.open(event.getModConfigurationDirectory());
        services.bootstrap(event.getModConfigurationDirectory(), diagnostics);
        compatibility.discover(Loader.instance().getIndexedModList());
        MinecraftForge.EVENT_BUS.register(compatibility);
        FMLCommonHandler.instance().bus().register(compatibility);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        services.startAll();
        compatibility.installRuntimeBridges();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        compatibility.reportFindings();
        diagnostics.close();
    }
}
