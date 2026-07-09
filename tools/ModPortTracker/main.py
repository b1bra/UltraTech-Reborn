"""Application entry point."""

from __future__ import annotations

import sys

from PySide6.QtWidgets import QApplication

from model import ProgressStore
from parser import parse_modlist
from resources import MODLIST_PATH, PROGRESS_PATH
from style import APP_QSS
from ui import MainWindow


def main() -> int:
    app = QApplication(sys.argv)
    app.setApplicationName("Minecraft Mod Port Tracker")
    app.setStyleSheet(APP_QSS)

    mods = parse_modlist(MODLIST_PATH)
    store = ProgressStore(PROGRESS_PATH)
    store.apply(mods)

    window = MainWindow(mods, store)
    window.show()
    return app.exec()


if __name__ == "__main__":
    raise SystemExit(main())
