"""Safe application launcher.

This module exists so `python tools/PATCHER/app.py` works when launched directly
from a checkout and also reports a useful headless diagnostic if the host is
missing native Qt libraries such as libGL. On a desktop with PySide6 and Qt
runtime available it starts the full GUI.
"""
from __future__ import annotations
from pathlib import Path
import os, sys, traceback


def ensure_repo_on_path() -> None:
    repo_root = Path(__file__).resolve().parents[1]
    if str(repo_root.parent) not in sys.path:
        sys.path.insert(0, str(repo_root.parent))


def run_headless(reason: BaseException) -> int:
    from tools.PATCHER.bootstrap import build_registry
    registry = build_registry(Path.cwd() / "tools/PATCHER/.runtime")
    logger = registry.get("Logger")
    logger.error("Launcher", f"GUI startup failed: {reason}")
    print("PATCHER не смог открыть PySide6 GUI в текущем окружении.")
    print(f"Причина: {reason}")
    print("Core API, Scanner, Patch Engine и Verification Engine загружены успешно.")
    print("Установите системные библиотеки Qt/OpenGL или запускайте на desktop Windows/Linux с PySide6.")
    return 0


def launch() -> int:
    ensure_repo_on_path()
    os.environ.setdefault("QT_ENABLE_HIGHDPI_SCALING", "1")
    try:
        from tools.PATCHER.ui.windows.main_window import main
        main()
        return 0
    except ImportError as exc:
        return run_headless(exc)
    except OSError as exc:
        return run_headless(exc)


if __name__ == "__main__":
    raise SystemExit(launch())
