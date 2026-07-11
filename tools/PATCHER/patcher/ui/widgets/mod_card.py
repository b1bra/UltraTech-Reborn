from __future__ import annotations

from pathlib import Path
from PySide6.QtCore import Qt
from PySide6.QtGui import QPainter, QColor
from PySide6.QtWidgets import QFrame, QLabel, QVBoxLayout, QWidget
from patcher.core.models.domain import JarModel

class AnalysisProgressBar(QWidget):
    """Compact segmented progress bar with green loaded and red remaining parts."""

    def __init__(self) -> None:
        super().__init__()
        self._value = 0
        self.setFixedHeight(8)

    def set_value(self, value: int) -> None:
        self._value = max(0, min(100, value))
        self.update()

    def paintEvent(self, _event) -> None:
        painter = QPainter(self)
        painter.setRenderHint(QPainter.RenderHint.Antialiasing)
        width = self.width()
        loaded = int(width * self._value / 100)
        gap = 2 if 0 < loaded < width else 0
        painter.setBrush(QColor("#2f8f46"))
        painter.setPen(Qt.PenStyle.NoPen)
        painter.drawRoundedRect(0, 0, max(0, loaded - gap), self.height(), 3, 3)
        painter.setBrush(QColor("#8f3434"))
        painter.drawRoundedRect(min(width, loaded + gap), 0, max(0, width - loaded - gap), self.height(), 3, 3)

class ModCard(QFrame):
    """Low-height mod card used in the right mod list."""

    def __init__(self, path: Path) -> None:
        super().__init__(objectName="ModCard")
        self.path = path
        self.jar: JarModel | None = None
        self.setFixedHeight(94)
        layout = QVBoxLayout(self)
        layout.setContentsMargins(10, 8, 10, 8)
        layout.setSpacing(5)
        self.title = QLabel(path.name)
        self.title.setStyleSheet("font-weight:700;")
        self.details = QLabel("Waiting for analysis…")
        self.details.setObjectName("Muted")
        self.progress = AnalysisProgressBar()
        layout.addWidget(self.title)
        layout.addWidget(self.details)
        layout.addWidget(self.progress)

    def set_progress(self, value: int, stage: str = "") -> None:
        self.progress.set_value(value)
        if stage:
            self.details.setText(stage)

    def set_result(self, jar: JarModel, problems: int, auto_fixable: int) -> None:
        self.jar = jar
        self.progress.set_value(100)
        self.details.setText(f"{jar.loader.value} • classes {jar.metadata.get('class_count', 0)} • issues {problems}/{auto_fixable} auto")
