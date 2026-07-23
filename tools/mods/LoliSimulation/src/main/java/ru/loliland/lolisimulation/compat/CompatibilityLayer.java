package ru.loliland.lolisimulation.compat;

import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ru.loliland.lolisimulation.diagnostics.Diagnostics;
import ru.loliland.lolisimulation.services.ServiceRegistry;

public final class CompatibilityLayer {
    private final ServiceRegistry services;
    private final Diagnostics diagnostics;
    private final List<String> loliMods = new ArrayList<String>();
    private final List<String> networkMods = new ArrayList<String>();
    private boolean announcedWorld;

    public CompatibilityLayer(ServiceRegistry services, Diagnostics diagnostics) {
        this.services = services;
        this.diagnostics = diagnostics;
    }

    public void discover(Map<String, ModContainer> indexedMods) {
        diagnostics.info("Discovering loaded mods: " + indexedMods.size());
        for (Map.Entry<String, ModContainer> entry : indexedMods.entrySet()) {
            String id = entry.getKey();
            String name = entry.getValue().getName();
            String fingerprint = (id + " " + name).toLowerCase();
            if (fingerprint.contains("loli") || fingerprint.contains("ultratech") || fingerprint.contains("msw") || fingerprint.contains("airdrop")) {
                loliMods.add(id);
                diagnostics.info("Observed LoliLand-family mod: " + id + " / " + name);
            }
            if (fingerprint.contains("network") || fingerprint.contains("quest") || fingerprint.contains("computer")) networkMods.add(id);
        }
    }

    public void installRuntimeBridges() {
        diagnostics.info("Installing offline bridges: player-data, economy, permissions, statistics, packet ledger");
        diagnostics.warn("No proprietary LoliLand server API bytecode was available locally; bridges therefore expose durable local state and diagnostics rather than guessed remote semantics.");
    }

    @SubscribeEvent
    public void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        diagnostics.info("Offline login observed for " + event.player.getCommandSenderName() + "; services=" + services.all().size());
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (!announcedWorld && event.phase == TickEvent.Phase.END && !event.world.isRemote) {
            announcedWorld = true;
            diagnostics.info("Integrated-server world detected: dimension=" + event.world.provider.dimensionId);
        }
    }

    public void reportFindings() {
        diagnostics.info("LoliLand-family mods detected: " + loliMods);
        diagnostics.info("Network-sensitive mods detected heuristically: " + networkMods);
    }
}
