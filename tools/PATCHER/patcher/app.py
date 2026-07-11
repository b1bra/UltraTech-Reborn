from __future__ import annotations

import sys, traceback
from pathlib import Path

def _bootstrap_package_path() -> None:
    package_root = Path(__file__).resolve().parents[1]
    if str(package_root) not in sys.path: sys.path.insert(0, str(package_root))

def _explain_startup_error(exc_type, exc_value, exc_traceback) -> None:
    print('PATCHER failed to start.', file=sys.stderr); traceback.print_exception(exc_type, exc_value, exc_traceback)

_bootstrap_package_path(); sys.excepthook = _explain_startup_error

from PySide6.QtWidgets import QApplication
from patcher.core.config.manager import ConfigManager
from patcher.core.event_bus import EventBus, MessageBus
from patcher.core.logger.logger import LoggerManager
from patcher.core.registry.services import ServiceRegistry
from patcher.core.resources.manager import ResourceManager
from patcher.core.scheduler.tasks import TaskScheduler
from patcher.ui.styles.theme_manager import ThemeManager
from patcher.ui.windows.launcher_dialog import LauncherDialog
from patcher.ui.main_window import MainWindow


def main() -> int:
    app = QApplication(sys.argv); app.setStyleSheet(ThemeManager().stylesheet())
    data_dir = Path.home() / '.ultratech-patcher'
    services = ServiceRegistry(); scheduler = TaskScheduler(); config = ConfigManager(data_dir / 'config.json')
    services.register('Logger', LoggerManager(data_dir / 'logs')); services.register('ThemeManager', ThemeManager()); services.register('ConfigManager', config); services.register('ResourceManager', ResourceManager(Path(__file__).resolve().parents[1] / 'resources')); services.register('TaskScheduler', scheduler); services.register('EventBus', EventBus()); services.register('MessageBus', MessageBus())
    if not config.config.selected_launcher:
        dialog = LauncherDialog(config)
        if dialog.exec() != 1: return 0
    window = MainWindow(config, scheduler); window.show()
    try: return app.exec()
    finally: scheduler.shutdown()

if __name__ == '__main__':
    raise SystemExit(main())
