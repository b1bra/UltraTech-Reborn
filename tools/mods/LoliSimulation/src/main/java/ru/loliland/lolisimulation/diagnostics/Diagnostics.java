package ru.loliland.lolisimulation.diagnostics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Diagnostics {
    private FileWriter writer;

    public void open(File configDirectory) {
        File dir = new File(configDirectory, "LoliSimulation/diagnostics");
        dir.mkdirs();
        try {
            writer = new FileWriter(new File(dir, "startup-" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()) + ".log"));
            info("Diagnostics opened at " + dir.getAbsolutePath());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to open LoliSimulation diagnostics", e);
        }
    }

    public void info(String message) { write("INFO", message); }
    public void warn(String message) { write("WARN", message); }

    private synchronized void write(String level, String message) {
        System.out.println("[LoliSimulation/" + level + "] " + message);
        if (writer != null) {
            try { writer.write("[" + level + "] " + message + "\n"); writer.flush(); } catch (IOException ignored) { }
        }
    }

    public void close() {
        if (writer != null) try { writer.close(); } catch (IOException ignored) { }
    }
}
