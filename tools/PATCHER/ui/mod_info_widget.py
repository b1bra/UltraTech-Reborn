from PySide6.QtCore import Qt, QTimer
from PySide6.QtWidgets import QWidget, QVBoxLayout, QLabel

from managers.theme_manager import ThemeManager
from managers.resource_manager import ResourceManager
from utils.constants import AppConstants
from utils.signals import AppSignals

class ModInfoWidget(QWidget):
    """Отображает краткую информацию о моде после загрузки, этапы анализа."""
    def __init__(self, theme_manager: ThemeManager, resource_manager: ResourceManager,
                 signals: AppSignals, parent=None):
        super().__init__(parent)
        self.theme_manager = theme_manager
        self.resource_manager = resource_manager
        self.signals = signals

        layout = QVBoxLayout(self)
        layout.setAlignment(Qt.AlignCenter)
        layout.setSpacing(AppConstants.PADDING_SM)

        # Название мода (будет обновляться)
        self.name_label = QLabel()
        self.name_label.setAlignment(Qt.AlignCenter)
        self.name_label.setStyleSheet(f"""
            color: {theme_manager.theme.text_primary.name()};
            font-family: "{AppConstants.FONT_MAIN}";
            font-size: {AppConstants.FONT_SIZE_TITLE}px;
        """)
        layout.addWidget(self.name_label)

        # Текущее действие / этап
        self.status_label = QLabel()
        self.status_label.setAlignment(Qt.AlignCenter)
        self.status_label.setStyleSheet(f"""
            color: {theme_manager.theme.text_secondary.name()};
            font-family: "{AppConstants.FONT_MAIN}";
            font-size: {AppConstants.FONT_SIZE_NORMAL}px;
        """)
        layout.addWidget(self.status_label)

        # Дополнительные параметры (можно добавить позже)
        self.details_label = QLabel()
        self.details_label.setAlignment(Qt.AlignCenter)
        self.details_label.setStyleSheet(f"""
            color: {theme_manager.theme.text_disabled.name()};
            font-family: "{AppConstants.FONT_MAIN}";
            font-size: {AppConstants.FONT_SIZE_SMALL}px;
        """)
        layout.addWidget(self.details_label)

        # Подключаем сигналы
        self.signals.analysis_step_changed.connect(self.set_status)
        self.signals.file_dropped.connect(self.set_mod_name)
        # Сброс при новом файле
        self.signals.file_dropped.connect(lambda: self.set_status("Starting analysis..."))

    def load_mod(self, file_path: str):
        # Можно извлечь имя из пути
        name = file_path.split('/')[-1].replace('.jar', '')
        self.name_label.setText(name)

    def set_mod_name(self, file_path: str):
        self.load_mod(file_path)

    def set_status(self, text: str):
        self.status_label.setText(text)