# PATCHER

PATCHER is a modular PySide6 application for analysing Minecraft JAR mods, diagnosing launch incompatibilities, and applying safe, extensible repairs without removing gameplay content.

Run with Python 3.12+:

```bash
pip install -r tools/PATCHER/requirements.txt
PYTHONPATH=tools/PATCHER python -m patcher.app
```

Alternatively, run from inside `tools/PATCHER`:

```bash
cd tools/PATCHER
python -m patcher.app
```

On minimal Linux installations, install the Qt runtime libraries required by PySide6 (for example `libgl1` on Debian/Ubuntu) before launching the GUI.
