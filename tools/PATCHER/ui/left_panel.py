from PySide6.QtCore import Qt
from PySide6.QtWidgets import QWidget, QVBoxLayout, QStackedWidget

from ui.drop_area import DropArea
from ui.mod_info_widget import ModInfoWidget
from managers.theme_manager import ThemeManager
from managers.resource_manager import ResourceManager
from utils.constants import AppConstants
from utils.signals import AppSignals

class LeftPanel(QWidget):
    """Левая рабочая область (78%). Переключает DropArea / ModInfoWidget."""
    def __init__(self, theme_manager: ThemeManager, resource_manager: ResourceManager,
                 signals: AppSignals, parent=None):
        super().__init__(parent)
        self.theme_manager = theme_manager
        self.resource_manager = resource_manager
        self.signals = signals

        self.setAutoFillBackground(False)
        layout = QVBoxLayout(self)
        layout.setContentsMargins(0, 0, 0, 0)

        self.stack = QStackedWidget(self)
        layout.addWidget(self.stack)

        # Индекс 0: DropArea (начальное состояние)
        self.drop_area = DropArea(theme_manager, resource_manager, signals)
        # Индекс 1: ModInfoWidget (после загрузки файла)
        self.mod_info = ModInfoWidget(theme_manager, resource_manager, signals)

        self.stack.addWidget(self.drop_area)
        self.stack.addWidget(self.mod_info)

        # По умолчанию показываем DropArea
        self.stack.setCurrentIndex(0)

        # Переключение при загрузке файла
        self.signals.file_dropped.connect(self.show_mod_info)

        # Стиль фона панели
        self.setStyleSheet(f"""
            LeftPanel {{
                background-color: {self.theme_manager.theme.background_main.name()};
                border-radius: {AppConstants.RADIUS_PANEL}px;
            }}
        """)

    def show_mod_info(self, file_path: str):
        """Переключает стек на информацию о моде."""
        self.stack.setCurrentIndex(1)
        # Передаём путь в mod_info, чтобы он мог начать отображение
        self.mod_info.load_mod(file_path)