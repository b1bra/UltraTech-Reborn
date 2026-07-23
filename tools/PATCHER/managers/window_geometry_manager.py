from PySide6.QtCore import QSettings, QByteArray, QRect, QSize, QPoint
from PySide6.QtWidgets import QWidget

class WindowGeometryManager:
    """Сохраняет и восстанавливает геометрию окна через QSettings."""
    def __init__(self, org="PATCHER", app="PatcherApp"):
        self.settings = QSettings(org, app)

    def save_state(self, window: QWidget):
        self.settings.setValue("geometry", window.saveGeometry())
        self.settings.setValue("windowState", window.saveState())

    def restore_state(self, window: QWidget) -> bool:
        geometry = self.settings.value("geometry")
        state = self.settings.value("windowState")
        restored = False
        if geometry:
            window.restoreGeometry(geometry)
            restored = True
        if state:
            window.restoreState(state)
        return restored