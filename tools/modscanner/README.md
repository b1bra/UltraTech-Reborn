### Mod Scanner

#### Purpose

This toolset analyzes Minecraft mod files and collects information required for project documentation.

###### Features

- Scan mod folders.
- Read mod metadata from jar files.
- Detect mod name, version, mod id and authors.
- Generate modlist documentation.
- Run ModPacketLogger Lite to inspect mod JARs for client network packet markers and Loliland usage.

###### ModPacketLogger Lite

`modpacketlogger_lite.py` is a lightweight static analyzer for mod JAR files only. It asks for a mods folder path on startup, or accepts the path as the first command-line argument.

The analyzer logs progress to the console and checks each mod with several static methods:

- metadata readers for `mcmod.info`, `mods.toml`, `fabric.mod.json`, and `quilt.mod.json`;
- JAR entry inspection;
- text resource scanning;
- printable string extraction from `.class` files;
- pattern matching for network packet/client-network APIs;
- Loliland server and website markers;
- `Loli` prefix markers that may indicate a custom Loliland team mod.

Run it with:

```text
python tools/modscanner/modpacketlogger_lite.py /path/to/mods
```

If the path is omitted, the script prompts for it interactively:

```text
python tools/modscanner/modpacketlogger_lite.py
```

The generated report is written to:

```text
docs/generated/networklog.md
```

Each mod is written as a second-level heading, and that mod's analysis is enclosed in a `text` code block.

###### Usage

The main scanner will analyze a selected mods folder and update the project mod list. Use ModPacketLogger Lite when only network/Loliland analysis for mods is needed.

###### Status

ModPacketLogger Lite is implemented. The broader scanner remains planned for full project analysis.
