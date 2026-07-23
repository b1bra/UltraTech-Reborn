package ru.loliland.lolisimulation.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import ru.loliland.lolisimulation.api.OfflineService;

public final class SimpleStoreService implements OfflineService {
    private final String id;
    private final File file;
    private final Properties data = new Properties();

    public SimpleStoreService(String id, File file) { this.id = id; this.file = file; }
    public String id() { return id; }

    public void start() {
        file.getParentFile().mkdirs();
        if (file.isFile()) try { FileInputStream in = new FileInputStream(file); data.load(in); in.close(); } catch (IOException ignored) { }
    }

    public synchronized String get(String key, String fallback) { return data.getProperty(key, fallback); }
    public synchronized void put(String key, String value) { data.setProperty(key, value); flush(); }
    public void stop() { flush(); }

    private void flush() { try { FileOutputStream out = new FileOutputStream(file); data.store(out, id); out.close(); } catch (IOException ignored) { } }
}
