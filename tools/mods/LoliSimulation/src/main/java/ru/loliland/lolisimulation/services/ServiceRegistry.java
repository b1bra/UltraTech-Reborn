package ru.loliland.lolisimulation.services;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import ru.loliland.lolisimulation.api.OfflineService;
import ru.loliland.lolisimulation.diagnostics.Diagnostics;

public final class ServiceRegistry {
    private final Map<String, OfflineService> services = new LinkedHashMap<String, OfflineService>();
    private Diagnostics diagnostics;

    public void bootstrap(File configDirectory, Diagnostics diagnostics) {
        this.diagnostics = diagnostics;
        register(new SimpleStoreService("player-data", new File(configDirectory, "LoliSimulation/player-data.properties")));
        register(new SimpleStoreService("economy", new File(configDirectory, "LoliSimulation/economy.properties")));
        register(new SimpleStoreService("permissions", new File(configDirectory, "LoliSimulation/permissions.properties")));
        register(new SimpleStoreService("statistics", new File(configDirectory, "LoliSimulation/statistics.properties")));
        register(new PacketLedgerService());
    }

    public void register(OfflineService service) {
        services.put(service.id(), service);
        if (diagnostics != null) diagnostics.info("Registered service: " + service.id());
    }

    public OfflineService get(String id) { return services.get(id); }
    public Collection<OfflineService> all() { return services.values(); }
    public void startAll() { for (OfflineService s : services.values()) s.start(); }
}
