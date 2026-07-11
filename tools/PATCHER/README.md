# PATCHER

PATCHER is a Python 3.12+ modular PySide6 platform for analysing, planning safe repairs, and verifying Minecraft mods. The application is organized around a Core API: `ServiceRegistry`, `EventBus`, `MessageBus`, background `TaskScheduler`, plugin metadata, typed domain models, and shared managers for logging, configuration, resources, themes, and animations.

Run:

```bash
pip install -r tools/PATCHER/requirements.txt
PYTHONPATH=tools/PATCHER python -m patcher.app
```

## Architecture

The `patcher/core` package is the only integration layer shared by subsystems. Analyzers, patchers, AI providers, decompilers, sandbox runners, knowledge bases, diagnostics, and UI services are expected to register as plugins and communicate through Core API services/events instead of direct cross-subsystem imports.

Implemented foundations include:

- typed dataclass models for projects, JARs, classes, methods, fields, dependencies, diagnostics, and patch plans;
- plugin lifecycle contracts (`initialize`, `shutdown`, `metadata`, `dependencies`, `execute`, `health`);
- service registry, event bus, message bus, task scheduler, logger, config manager, resource manager, theme manager, and animation manager;
- asynchronous JAR analysis that keeps the UI responsive;
- first-launch launcher selection persisted via `ConfigManager`;
- frameless dark PySide6 main window, custom title bar, drag-and-drop JAR area, mod cards, AI journal placeholder, and asynchronous settings window;
- scanner and patch-engine foundations that never modify the source JAR and create safe `*-patched.jar` copies.

The scaffold is intentionally plugin-oriented so new analyzers, patchers, AI providers, decompilers, Minecraft versions, launcher adapters, compatibility databases, and exporters can be added without changing existing callers.
