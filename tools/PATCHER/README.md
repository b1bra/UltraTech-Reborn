# PATCHER

PATCHER is a modular PySide6 application for analysing Minecraft JAR mods, diagnosing launch incompatibilities, and applying safe, extensible repairs without removing gameplay content.

Run with Python 3.12+:

```bash
pip install -r tools/PATCHER/requirements.txt
PYTHONPATH=tools/PATCHER python -m patcher.app
```

Alternatively, run the package from inside `tools/PATCHER`:

```bash
cd tools/PATCHER
python -m patcher.app
```

You can also launch the application file directly; it bootstraps the package path and prints startup errors instead of closing the console immediately:

```bash
python tools/PATCHER/patcher/app.py
```

On minimal Linux installations, install the Qt runtime libraries required by PySide6 (for example `libgl1` on Debian/Ubuntu) before launching the GUI.
