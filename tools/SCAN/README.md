# SCAN

Unified GUI scanner for the existing `tools/modscanner` and `tools/UltraSaveInspector` projects.

Run:

```text
pyw tools/SCAN/app.pyw
```

For debugging from a terminal:

```text
py tools/SCAN/app.py
```

Output layout:

```text
output/
  modpack/
  launcher/
  logs/
    errors.md
    scanner.md
    session.json
```

The GUI keeps the analysis optional by path:

```text
Output path only: no analysis is started until a launcher or modpack path is provided.
Modpack path only: modpack analysis only.
Launcher path only: launcher analysis only.
Both paths: both analyses.
```
