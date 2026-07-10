from __future__ import annotations

from PySide6.QtCore import Property, QRectF, Qt
from PySide6.QtGui import QColor, QPainter
from PySide6.QtWidgets import QWidget

from patcher.constants import LAYOUT
from patcher.styles.theme import Theme

class SegmentedProgress(QWidget):
    def __init__(self) -> None:
        super().__init__()
        self._value = 0.0
        self._done = False
        self.setFixedHeight(LAYOUT.progress_height)

    def get_value(self) -> float: return self._value
    def set_value(self, value: float) -> None:
        self._value = max(0.0, min(100.0, value)); self.update()
    value = Property(float, get_value, set_value)

    def mark_done(self, done: bool) -> None:
        self._done = done; self.update()

    def paintEvent(self, event):  # noqa: N802
        theme = Theme(); painter = QPainter(self); painter.setRenderHint(QPainter.RenderHint.Antialiasing)
        rect = QRectF(self.rect()); gap = 4; filled = rect.width() * self._value / 100
        empty_color = QColor(theme.progress_done if self._done else theme.progress_empty)
        fill_color = QColor(theme.progress_done if self._done else theme.accent_2)
        painter.setPen(Qt.PenStyle.NoPen)
        if filled > 0:
            painter.setBrush(fill_color); painter.drawRoundedRect(QRectF(0, 0, max(0, filled - gap), rect.height()), rect.height()/2, rect.height()/2)
        if filled + gap < rect.width():
            painter.setBrush(empty_color); painter.drawRoundedRect(QRectF(filled + gap, 0, rect.width() - filled - gap, rect.height()), rect.height()/2, rect.height()/2)
