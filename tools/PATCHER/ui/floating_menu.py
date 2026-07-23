from PySide6.QtCore import Qt, QPropertyAnimation, QEasingCurve, QRect, Signal
from PySide6.QtWidgets import QWidget, QVBoxLayout, QPushButton, QGraphicsOpacityEffect
from PySide6.QtGui import QPainter, QBrush, QColor, QPen, QPixmap, QFont

from managers.theme_manager import ThemeManager
from managers.resource_manager import ResourceManager
from utils.constants import AppConstants

class FloatingMenu(QWidget):
    """Вертикальное меню, выезжающее из круглой кнопки. Содержит 5 пунктов."""
    menu_action_triggered = Signal(str)  # action_name: Settings, ChatGPT, DeepSeek, Claude, Recaf

    def __init__(self, theme_manager: ThemeManager, resource_manager: ResourceManager, parent=None):
        super().__init__(parent)
        self.theme_manager = theme_manager
        self.theme = theme_manager.theme
        self.resource_manager = resource_manager

        self.setWindowFlags(Qt.Popup | Qt.FramelessWindowHint)
        self.setAttribute(Qt.WA_TranslucentBackground)
        self.setAutoFillBackground(False)

        self.setFixedWidth(180)  # фиксированная ширина

        layout = QVBoxLayout(self)
        layout.setContentsMargins(AppConstants.PADDING_SM, AppConstants.PADDING_SM,
                                  AppConstants.PADDING_SM, AppConstants.PADDING_SM)
        layout.setSpacing(AppConstants.PADDING_SM)

        actions = [
            ("Settings", "settings"),
            ("ChatGPT", "chatgpt"),
            ("DeepSeek", "deepseek"),
            ("Claude", "claude"),
            ("Recaf", "recaf"),
        ]
        self.buttons = []
        for text, icon_name in actions:
            btn = QPushButton(text)
            btn.setFixedHeight(AppConstants.FLOATING_MENU_BUTTON_HEIGHT)
            btn.setCursor(Qt.PointingHandCursor)
            icon = self.resource_manager.get_icon(icon_name, AppConstants.ICON_SIZE_MENU)
            if not icon.isNull():
                btn.setIcon(icon)
                btn.setIconSize(icon.size())
            btn.setStyleSheet(f"""
                QPushButton {{
                    background-color: transparent;
                    color: {self.theme.text_primary.name()};
                    border: none;
                    border-radius: {AppConstants.RADIUS_BUTTON}px;
                    text-align: left;
                    padding-left: 10px;
                    font-family: "Tenali Ramakrishna";
                    font-size: {AppConstants.FONT_SIZE_SMALL}px;
                }}
                QPushButton:hover {{
                    background-color: {self.theme.button_hover.name()};
                }}
                QPushButton:pressed {{
                    background-color: {self.theme.button_pressed.name()};
                }}
            """)
            btn.clicked.connect(lambda checked=False, n=icon_name: self.menu_action_triggered.emit(n))
            layout.addWidget(btn)
            self.buttons.append(btn)

        # Общая высота меню зависит от количества кнопок, поэтому вычисляем
        total_height = (len(actions) * AppConstants.FLOATING_MENU_BUTTON_HEIGHT +
                        (len(actions) - 1) * AppConstants.PADDING_SM +
                        2 * AppConstants.PADDING_SM)
        self.setFixedHeight(total_height)

    def paintEvent(self, event):
        painter = QPainter(self)
        painter.setRenderHint(QPainter.Antialiasing)
        # Фон меню с закруглением
        rect = self.rect()
        painter.setBrush(QBrush(self.theme.background_dark))
        painter.setPen(Qt.NoPen)
        painter.drawRoundedRect(rect, AppConstants.RADIUS_CARD, AppConstants.RADIUS_CARD)
        painter.end()

    def showEvent(self, event):
        super().showEvent(event)
        # Анимация прозрачности при появлении
        if not hasattr(self, '_opacity_effect'):
            self._opacity_effect = QGraphicsOpacityEffect(self)
            self.setGraphicsEffect(self._opacity_effect)
        self._opacity_effect.setOpacity(0.0)
        self._fade_in_anim = QPropertyAnimation(self._opacity_effect, b"opacity")
        self._fade_in_anim.setDuration(AppConstants.ANIM_MENU_OPEN)
        self._fade_in_anim.setStartValue(0.0)
        self._fade_in_anim.setEndValue(1.0)
        self._fade_in_anim.setEasingCurve(QEasingCurve.OutCubic)
        self._fade_in_anim.start()

    def hideEvent(self, event):
        # Просто скрываем, анимация не требуется при повторном нажатии
        super().hideEvent(event)