"""PySide6 user interface for the Minecraft mod port tracker."""

from __future__ import annotations

from PySide6.QtCore import QEasingCurve, QPoint, QPropertyAnimation, QRectF, Qt, Property, Signal
from PySide6.QtGui import QColor, QFont, QPainter, QPen
from PySide6.QtWidgets import (
    QFrame, QHBoxLayout, QLabel, QMainWindow, QMessageBox, QPushButton,
    QScrollArea, QSizePolicy, QVBoxLayout, QWidget,
)

from model import ModInfo, ProgressStore
from resources import STATUS_DONE, STATUS_PENDING, TYPE_COLORS


class BackgroundWidget(QWidget):
    """Paints the premium dark procedural background without image files."""

    def paintEvent(self, event) -> None:  # noqa: N802 - Qt override
        painter = QPainter(self)
        painter.setRenderHint(QPainter.RenderHint.Antialiasing)
        rect = self.rect()
        center = rect.center()
        gradient = QColor(12, 13, 16)
        painter.fillRect(rect, gradient)

        radial = QColor(38, 42, 49, 72)
        painter.setBrush(radial)
        painter.setPen(Qt.PenStyle.NoPen)
        painter.drawEllipse(center, int(rect.width() * 0.62), int(rect.height() * 0.48))

        painter.setPen(QPen(QColor(255, 255, 255, 10), 1))
        step = 34
        for offset in range(-rect.height(), rect.width(), step):
            painter.drawLine(offset, rect.height(), offset + rect.height(), 0)

        # Deterministic low-alpha speckles imitate subtle film grain without runtime randomness.
        painter.setPen(QPen(QColor(255, 255, 255, 13), 1))
        for i in range(700):
            x = (i * 73) % max(rect.width(), 1)
            y = (i * 151) % max(rect.height(), 1)
            painter.drawPoint(x, y)

        painter.setBrush(Qt.BrushStyle.NoBrush)
        for i, alpha in enumerate((50, 38, 26, 16)):
            painter.setPen(QPen(QColor(0, 0, 0, alpha), 40))
            inset = i * 22
            painter.drawRoundedRect(rect.adjusted(inset, inset, -inset, -inset), 32, 32)
        super().paintEvent(event)


class DotButton(QPushButton):
    clicked_with_dot = Signal()

    def __init__(self, color: str, clickable: bool = False) -> None:
        super().__init__()
        self._diameter = 16.0
        self.color = QColor(color)
        self.setFixedSize(28, 28)
        self.setCursor(Qt.CursorShape.PointingHandCursor if clickable else Qt.CursorShape.ArrowCursor)
        self.setFlat(True)
        self.clicked.connect(self.clicked_with_dot.emit)

    def get_diameter(self) -> float:
        return self._diameter

    def set_diameter(self, value: float) -> None:
        self._diameter = value
        self.update()

    diameter = Property(float, get_diameter, set_diameter)

    def set_color(self, color: str) -> None:
        self.color = QColor(color)
        self.update()

    def enterEvent(self, event) -> None:  # noqa: N802
        self._animate(21.0)
        super().enterEvent(event)

    def leaveEvent(self, event) -> None:  # noqa: N802
        self._animate(16.0)
        super().leaveEvent(event)

    def paintEvent(self, event) -> None:  # noqa: N802
        painter = QPainter(self)
        painter.setRenderHint(QPainter.RenderHint.Antialiasing)
        painter.setPen(Qt.PenStyle.NoPen)
        painter.setBrush(self.color)
        d = self._diameter
        painter.drawEllipse(QRectF((self.width() - d) / 2, (self.height() - d) / 2, d, d))

    def _animate(self, end: float) -> None:
        self.anim = QPropertyAnimation(self, b"diameter", self)
        self.anim.setDuration(150)
        self.anim.setEasingCurve(QEasingCurve.Type.OutCubic)
        self.anim.setEndValue(end)
        self.anim.start()


class ModCard(QFrame):
    def __init__(self, mod: ModInfo, store: ProgressStore) -> None:
        super().__init__()
        self.mod = mod
        self.store = store
        self._shade = 0.0
        self.setObjectName("modCard")
        self.setMinimumHeight(58)
        self.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Minimum)

        layout = QHBoxLayout(self)
        layout.setContentsMargins(18, 13, 18, 13)
        layout.setSpacing(12)
        self.type_dot = DotButton(TYPE_COLORS.get(mod.detected_type, TYPE_COLORS["Unknown"]))
        self.type_dot.setToolTip(mod.detected_type)
        self.title = QLabel(mod.display_name)
        self.title.setWordWrap(True)
        self.title.setFont(QFont("Segoe UI", 11))
        self.title.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Minimum)
        self.status_dot = DotButton(STATUS_DONE if mod.completed else STATUS_PENDING, clickable=True)
        self.status_dot.clicked_with_dot.connect(self.toggle_completed)
        layout.addWidget(self.type_dot, 0, Qt.AlignmentFlag.AlignTop)
        layout.addWidget(self.title, 1)
        layout.addWidget(self.status_dot, 0, Qt.AlignmentFlag.AlignTop)
        self._apply_state(animated=False)

    def get_shade(self) -> float:
        return self._shade

    def set_shade(self, value: float) -> None:
        self._shade = value
        self.update()

    shade = Property(float, get_shade, set_shade)

    def enterEvent(self, event) -> None:  # noqa: N802
        self._animate_shade(1.0)
        super().enterEvent(event)

    def leaveEvent(self, event) -> None:  # noqa: N802
        self._animate_shade(0.0)
        super().leaveEvent(event)

    def paintEvent(self, event) -> None:  # noqa: N802
        painter = QPainter(self)
        painter.setRenderHint(QPainter.RenderHint.Antialiasing)
        base_alpha = 20 if self.mod.completed else 35
        hover_bonus = int(18 * self._shade)
        painter.setPen(QPen(QColor(255, 255, 255, 18), 1))
        painter.setBrush(QColor(255, 255, 255, base_alpha + hover_bonus))
        painter.drawRoundedRect(self.rect().adjusted(1, 1, -1, -1), 18, 18)
        super().paintEvent(event)

    def toggle_completed(self) -> None:
        if self.mod.completed:
            text = "Вы действительно хотите продолжить работу над данным модом?"
        else:
            text = "Вы действительно закончили перенос данного мода?"
        answer = QMessageBox.question(self, "Подтверждение", text, QMessageBox.StandardButton.Yes | QMessageBox.StandardButton.No)
        if answer == QMessageBox.StandardButton.Yes:
            self.store.set_completed(self.mod, not self.mod.completed)
            self._apply_state(animated=True)

    def _apply_state(self, animated: bool) -> None:
        self.status_dot.set_color(STATUS_DONE if self.mod.completed else STATUS_PENDING)
        font = self.title.font()
        font.setStrikeOut(self.mod.completed)
        self.title.setFont(font)
        self.title.setStyleSheet("color: rgba(255,255,255,118);" if self.mod.completed else "color: rgba(255,255,255,226);")
        if animated:
            self._animate_shade(0.0)
        self.update()

    def _animate_shade(self, end: float) -> None:
        self.anim = QPropertyAnimation(self, b"shade", self)
        self.anim.setDuration(220)
        self.anim.setEasingCurve(QEasingCurve.Type.OutCubic)
        self.anim.setEndValue(end)
        self.anim.start()


class TitleBar(QWidget):
    def __init__(self, window: QMainWindow, count: int) -> None:
        super().__init__()
        self.window = window
        self.drag_pos: QPoint | None = None
        layout = QHBoxLayout(self)
        layout.setContentsMargins(22, 14, 18, 10)
        title_box = QVBoxLayout()
        title = QLabel("Minecraft Mod Port Tracker")
        title.setObjectName("titleLabel")
        subtitle = QLabel(f"Отслеживание переноса модов · {count} шт.")
        subtitle.setObjectName("subtitleLabel")
        title_box.addWidget(title)
        title_box.addWidget(subtitle)
        minimize = QPushButton("–")
        minimize.setObjectName("windowButton")
        close = QPushButton("×")
        close.setObjectName("closeButton")
        minimize.clicked.connect(window.showMinimized)
        close.clicked.connect(window.close)
        layout.addLayout(title_box)
        layout.addStretch(1)
        layout.addWidget(minimize)
        layout.addWidget(close)

    def mousePressEvent(self, event) -> None:  # noqa: N802
        if event.button() == Qt.MouseButton.LeftButton:
            self.drag_pos = event.globalPosition().toPoint() - self.window.frameGeometry().topLeft()

    def mouseMoveEvent(self, event) -> None:  # noqa: N802
        if self.drag_pos and event.buttons() & Qt.MouseButton.LeftButton:
            self.window.move(event.globalPosition().toPoint() - self.drag_pos)

    def mouseReleaseEvent(self, event) -> None:  # noqa: N802
        self.drag_pos = None


class MainWindow(QMainWindow):
    def __init__(self, mods: list[ModInfo], store: ProgressStore) -> None:
        super().__init__()
        self.setWindowFlags(Qt.WindowType.FramelessWindowHint | Qt.WindowType.Window)
        self.setAttribute(Qt.WidgetAttribute.WA_TranslucentBackground)
        self.resize(1200, 900)
        self.setMinimumSize(1000, 750)

        root = BackgroundWidget()
        root.setObjectName("centralRoot")
        layout = QVBoxLayout(root)
        layout.setContentsMargins(0, 0, 0, 22)
        layout.setSpacing(8)
        layout.addWidget(TitleBar(self, len(mods)))

        scroll = QScrollArea()
        scroll.setWidgetResizable(True)
        content = QWidget()
        cards = QVBoxLayout(content)
        cards.setContentsMargins(36, 18, 36, 28)
        cards.setSpacing(12)
        for mod in mods:
            cards.addWidget(ModCard(mod, store))
        cards.addStretch(1)
        scroll.setWidget(content)
        layout.addWidget(scroll, 1)
        self.setCentralWidget(root)
