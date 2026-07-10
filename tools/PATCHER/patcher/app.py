from __future__ import annotations

import sys
import traceback
from pathlib import Path


def _bootstrap_package_path() -> None:
    """Allow launching this file directly, not only via `python -m patcher.app`."""
    package_root = Path(__file__).resolve().parents[1]
    package_root_text = str(package_root)
    if package_root_text not in sys.path:
        sys.path.insert(0, package_root_text)


def _pause_after_error() -> None:
    if sys.stdin is not None and sys.stdin.isatty():
        try:
            input("\nPress Enter to close PATCHER...")
        except EOFError:
            pass


def _explain_startup_error(exc_type, exc_value, exc_traceback) -> None:
    print("PATCHER failed to start.", file=sys.stderr)
    print(file=sys.stderr)
    print("Install dependencies first:", file=sys.stderr)
    print("  pip install -r tools/PATCHER/requirements.txt", file=sys.stderr)
    print(file=sys.stderr)
    print("If you are on Linux and see libGL/Qt errors, install the Qt runtime libraries", file=sys.stderr)
    print("for your distribution, for example: sudo apt install libgl1 libegl1", file=sys.stderr)
    print(file=sys.stderr)
    print("Technical details:", file=sys.stderr)
    traceback.print_exception(exc_type, exc_value, exc_traceback)
    _pause_after_error()


_bootstrap_package_path()
sys.excepthook = _explain_startup_error

from PySide6.QtWidgets import QApplication

from patcher.external.tools import LauncherLocator
from patcher.logs.logger import configure_logging
from patcher.styles.theme import app_stylesheet
from patcher.ui.dialogs.tool_picker import ToolPicker
from patcher.ui.main_window import MainWindow


def main() -> int:
    configure_logging()
    app = QApplication(sys.argv)
    app.setStyleSheet(app_stylesheet())

    launchers = LauncherLocator().discover()
    picker = ToolPicker(launchers, "Select launcher context")
    if picker.exec() != 1:
        return 0

    window = MainWindow()
    window.show()
    return app.exec()


if __name__ == "__main__":
    raise SystemExit(main())
