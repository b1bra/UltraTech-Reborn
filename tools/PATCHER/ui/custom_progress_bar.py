from PySide6.QtCore import Qt, QPropertyAnimation, QEasingCurve
from PySide6.QtWidgets import QWidget
from PySide6.QtGui import QPainter, QBrush, QColor, QPen

from managers.theme_manager import ThemeManager
from utils.constants import AppConstants

class CustomProgressBar(QWidget):
    """Полностью кастомный прогресс-бар с анимированной заливкой."""
    def __init__(self, theme_manager: ThemeManager, parent=None):
        super().__init__(parent)
        self.theme_manager = theme_manager
        self._value = 0
        self._display_value = 0  # анимированное значение
        self.setFixedHeight(AppConstants.PROGRESS_BAR_HEIGHT)
        self._animation = None

    def set_value(self, value: int):
        """Запускает анимацию к целевому значению (0-100)."""
        if self._animation and self._animation.state() == QPropertyAnimation.Running:
            self._animation.stop()
        self._animation = QPropertyAnimation(self, b"display_value")
        self._animation.setDuration(AppConstants.ANIM_PROGRESS)
        self._animation.setStartValue(self._display_value)
        self._animation.setEndValue(value)
        self._animation.setEasingCurve(QEasingCurve.OutCubic)
        self._animation.start()

    def get_display_value(self) -> int:
        return self._display_value

    def set_display_value(self, val: int):
        self._display_value = val
        self.update()

    display_value = property(get_display_value, set_display_value)

    def paintEvent(self, event):
        painter = QPainter(self)
        painter.setRenderHint(QPainter.Antialiasing)

        width = self.width()
        height = self.height()
        radius = height // 2  # максимально скруглённый

        # Фон (незаполненная часть) – розовый
        painter.setBrush(QBrush(self.theme_manager.theme.progress_unfilled))
        painter.setPen(Qt.NoPen)
        painter.drawRoundedRect(0, 0, width, height, radius, radius)

        # Заполненная часть – зелёный, с зазором слева и справа?
        # Согласно спецификации: заполненная зеленая, незаполненная розовая, зазор 2px между ними.
        # Мы можем нарисовать зеленый прямоугольник до анимированного процента, 
        # а сверху оставшуюся часть розовым, но проще: сначала розовый фон полностью, 
        # потом зеленый прямоугольник с зазором в 2px справа.
        # Реализуем: зеленый прямоугольник шириной (display_value% от полной ширины - 2px).
        fill_width = int((self._display_value / 100.0) * width)
        if fill_width > 0:
            painter.setBrush(QBrush(self.theme_manager.theme.progress_filled))
            if fill_width > 2:
                painter.drawRoundedRect(0, 0, fill_width - 2, height, radius, radius)
            else:
                # если прогресс очень маленький, просто не рисуем зеленое
                pass

        painter.end()