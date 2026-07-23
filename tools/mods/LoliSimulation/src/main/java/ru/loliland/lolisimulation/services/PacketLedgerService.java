package ru.loliland.lolisimulation.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ru.loliland.lolisimulation.api.OfflineService;

public final class PacketLedgerService implements OfflineService {
    private final List<String> packets = new ArrayList<String>();
    public String id() { return "packet-ledger"; }
    public void start() { }
    public void stop() { packets.clear(); }
    public synchronized void record(String channel, Object payload) { packets.add(channel + ":" + String.valueOf(payload)); }
    public synchronized List<String> snapshot() { return Collections.unmodifiableList(new ArrayList<String>(packets)); }
}
