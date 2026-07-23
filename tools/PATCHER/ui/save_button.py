from PySide6.QtCore import Qt, QPropertyAnimation, QEasingCurve
from PySide6.QtWidgets import QPushButton
from PySide6.QtGui import QColor

from managers.theme_manager import ThemeManager
from managers.resource_manager import ResourceManager
from utils.constants import AppConstants
from utils.signals import AppSignals

class SaveButton(QPushButton):
    """Большая кнопка SAVE с двумя состояниями: disabled и active."""
    def __init__(self, theme_manager: ThemeManager, resource_manager: ResourceManager,
                 signals: AppSignals, parent=None):
        super().__init__("SAVE", parent)
        self.theme_manager = theme_manager
        self.resource_manager = resource_manager
        self.signals = signals

        self.setFixedHeight(AppConstants.SAVE_BUTTON_HEIGHT)
        self.setEnabled(False)
        self.setCursor(Qt.PointingHandCursor)

        self._base_style = f"""
            SaveButton {{
                background-color: {theme_manager.theme.save_button_disabled.name()};
                color: {theme_manager.theme.button_text_disabled.name()};
                border: none;
                border-radius: {AppConstants.RADIUS_BUTTON}px;
                font-family: "{AppConstants.FONT_MAIN}";
                font-size: {AppConstants.FONT_SIZE_NORMAL}px;
                font-weight: bold;
            }}
        """
        self.setStyleSheet(self._base_style)

        # Становится активной после patching_finished
        self.signals.patching_finished.connect(self.enable_button)

    def enable_button(self):
        """Активирует кнопку с плавным изменением цвета."""
        self.setEnabled(True)
        # Анимация цвета фона через style sheet невозможна напрямую,
        # поэтому просто меняем стиль мгновенно, но можно добавить эффект прозрачности.
        self.setStyleSheet(f"""
            SaveButton {{
                background-color: {self.theme_manager.theme.save_button_active.name()};
                color: {self.theme_manager.theme.text_primary.name()};
                border: none;
                border-radius: {AppConstants.RADIUS_BUTTON}px;
                font-family: "{AppConstants.FONT_MAIN}";
                font-size: {AppConstants.FONT_SIZE_NORMAL}px;
                font-weight: bold;
            }}
            SaveButton:hover {{
                background-color: {self.theme_manager.theme.save_button_hover.name()};
            }}
            SaveButton:pressed {{
                background-color: {self.theme_manager.theme.save_button_pressed.name()};
            }}
        """)

    def set_disabled_state(self):
        self.setEnabled(False)
        self.setStyleSheet(self._base_style)