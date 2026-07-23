from PySide6.QtCore import Qt, QSize
from PySide6.QtWidgets import QPushButton, QGraphicsDropShadowEffect
from PySide6.QtGui import QPainter, QBrush, QColor, QPen, QFont, QPixmap

from managers.theme_manager import ThemeManager
from utils.constants import AppConstants

class CircularButton(QPushButton):
    def __init__(self, icon_or_text, theme_manager: ThemeManager, parent=None):
        super().__init__(parent)
        self.theme_manager = theme_manager
        self.theme = theme_manager.theme
        self._icon = None
        self._text = None

        if isinstance(icon_or_text, QPixmap):
            self._icon = icon_or_text
        else:
            self._text = icon_or_text

        self.setFixedSize(AppConstants.CIRCULAR_BUTTON_DIAMETER, AppConstants.CIRCULAR_BUTTON_DIAMETER)
        self.setCursor(Qt.PointingHandCursor)
        self.setStyleSheet("background: transparent; border: none;")

        # Тень
        shadow = QGraphicsDropShadowEffect()
        shadow.setBlurRadius(10)
        shadow.setOffset(0, 2)
        shadow.setColor(QColor(0, 0, 0, 80))
        self.setGraphicsEffect(shadow)

    def paintEvent(self, event):
        painter = QPainter(self)
        painter.setRenderHint(QPainter.Antialiasing)

        size = min(self.width(), self.height())
        radius = size // 2

        # Фон
        brush = QBrush(self.theme.background_dark)
        if self.underMouse():
            brush = QBrush(self.theme.button_hover)
        if self.isDown():
            brush = QBrush(self.theme.button_pressed)
        painter.setBrush(brush)
        painter.setPen(Qt.NoPen)
        painter.drawEllipse(0, 0, size, size)

        # Иконка / текст
        if self._icon and not self._icon.isNull():
            icon_size = int(size * 0.6)
            pixmap = self._icon.scaled(icon_size, icon_size, Qt.KeepAspectRatio, Qt.SmoothTransformation)
            x = (size - pixmap.width()) // 2
            y = (size - pixmap.height()) // 2
            painter.drawPixmap(x, y, pixmap)
        elif self._text:
            painter.setPen(QColor(self.theme.text_primary))
            font = painter.font()
            font.setFamily("Tenali Ramakrishna")
            font.setPixelSize(int(size * 0.5))
            painter.setFont(font)
            painter.drawText(self.rect(), Qt.AlignCenter, self._text)

        painter.end()