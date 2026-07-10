from __future__ import annotations

import sys
from PySide6.QtWidgets import QApplication
from patcher.external.tools import ExternalTool
from patcher.logs.logger import configure_logging
from patcher.styles.theme import app_stylesheet
from patcher.ui.dialogs.tool_picker import ToolPicker
from patcher.ui.main_window import MainWindow


def main() -> int:
    configure_logging(); app = QApplication(sys.argv); app.setStyleSheet(app_stylesheet())
    window = MainWindow(); window.show()
    launchers = [ExternalTool("Prism Launcher", None, "◈"), ExternalTool("Minecraft Launcher", None, "▣"), ExternalTool("Offline / no launcher", None, "◇")]
    ToolPicker(launchers, "Select launcher context").exec()
    return app.exec()

if __name__ == "__main__": raise SystemExit(main())
