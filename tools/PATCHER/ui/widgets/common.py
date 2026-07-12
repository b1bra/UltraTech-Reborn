"""Reusable dark PySide6 widgets for cards, toast, drop area, chat and mod rows."""
from __future__ import annotations
from pathlib import Path
from PySide6.QtCore import Qt, Signal, QPropertyAnimation, QEasingCurve
from PySide6.QtGui import QDragEnterEvent, QDropEvent, QPainter, QColor, QPixmap, QIcon
from PySide6.QtWidgets import QWidget, QFrame, QLabel, QPushButton, QVBoxLayout, QHBoxLayout, QFileDialog, QTextEdit, QLineEdit, QSizePolicy
from tools.PATCHER.resources.design_tokens import PALETTE, DIMENSIONS, STRINGS

PURPLE = "#A259FF"


def transparent_icon() -> QIcon:
    pixmap = QPixmap(16, 16)
    pixmap.fill(Qt.GlobalColor.transparent)
    return QIcon(pixmap)


def label(text: str, muted: bool = False, center: bool = False) -> QLabel:
    item = QLabel(text)
    item.setAttribute(Qt.WidgetAttribute.WA_TranslucentBackground, True)
    item.setStyleSheet(f"background:transparent;color:{PALETTE.muted if muted else PALETTE.text};")
    if center:
        item.setAlignment(Qt.AlignmentFlag.AlignCenter)
    return item


class Card(QFrame):
    clicked = Signal()
    doubleClicked = Signal()

    def __init__(self, title: str = "", subtitle: str = "", parent: QWidget | None = None, centered: bool = False) -> None:
        super().__init__(parent)
        self.setObjectName("card")
        self.setCursor(Qt.CursorShape.PointingHandCursor)
        self.setStyleSheet(f"QFrame#card{{background:{PALETTE.card};border:1px solid {PALETTE.border};border-radius:{DIMENSIONS.radius}px;}}")
        layout = QVBoxLayout(self)
        layout.setContentsMargins(DIMENSIONS.padding, DIMENSIONS.padding, DIMENSIONS.padding, DIMENSIONS.padding)
        layout.setSpacing(DIMENSIONS.gap)
        self.title = label(title, center=centered)
        self.subtitle = label(subtitle, muted=True, center=centered)
        layout.addStretch(1 if centered else 0)
        layout.addWidget(self.title)
        layout.addWidget(self.subtitle)
        layout.addStretch(1 if centered else 0)
        self._hover_animation: QPropertyAnimation | None = None

    def set_text(self, title: str, subtitle: str = "") -> None:
        self.title.setText(title)
        self.subtitle.setText(subtitle)

    def mousePressEvent(self, event) -> None:
        self.clicked.emit()
        super().mousePressEvent(event)

    def mouseDoubleClickEvent(self, event) -> None:
        self.doubleClicked.emit()
        super().mouseDoubleClickEvent(event)

    def enterEvent(self, event) -> None:
        self.setStyleSheet(f"QFrame#card{{background:{PALETTE.hover};border:1px solid {PALETTE.border};border-radius:{DIMENSIONS.radius}px;}}")
        super().enterEvent(event)

    def leaveEvent(self, event) -> None:
        self.setStyleSheet(f"QFrame#card{{background:{PALETTE.card};border:1px solid {PALETTE.border};border-radius:{DIMENSIONS.radius}px;}}")
        super().leaveEvent(event)


class Toast(QLabel):
    def __init__(self, parent: QWidget) -> None:
        super().__init__(parent)
        self.setStyleSheet(f"background:{PALETTE.toast};border-radius:{DIMENSIONS.radius}px;padding:12px;color:{PALETTE.text}")
        self.hide()

    def show_message(self, text: str) -> None:
        self.setText(text)
        self.adjustSize()
        self.move((self.parent().width() - self.width()) // 2, 70)
        self.show()
        anim = QPropertyAnimation(self, b"windowOpacity", self)
        anim.setStartValue(0.0)
        anim.setEndValue(1.0)
        anim.setDuration(200)
        anim.setEasingCurve(QEasingCurve.Type.OutCubic)
        anim.start()


class JarDropArea(QFrame):
    jarSelected = Signal(str)

    def __init__(self, start_dir_provider=None) -> None:
        super().__init__()
        self.start_dir_provider = start_dir_provider
        self.setAcceptDrops(True)
        self.setCursor(Qt.CursorShape.PointingHandCursor)
        self.setObjectName("dropArea")
        self.setStyleSheet(f"QFrame#dropArea{{background:{PALETTE.card};border:1px dashed {PALETTE.border};border-radius:{DIMENSIONS.radius}px;}}")
        layout = QVBoxLayout(self)
        layout.setContentsMargins(18, 18, 18, 18)
        layout.addStretch()
        title = label("Drop your JAR file here", center=True)
        title.setStyleSheet(f"background:transparent;color:{PURPLE};font-size:18px;font-weight:600;")
        subtitle = label("or click to browse", muted=True, center=True)
        layout.addWidget(title)
        layout.addWidget(subtitle)
        layout.addStretch()

    def choose(self) -> None:
        start = Path.home()
        if self.start_dir_provider:
            provided = self.start_dir_provider()
            if provided:
                start = Path(provided)
        path, _ = QFileDialog.getOpenFileName(self, STRINGS.choose_jar, str(start), "Minecraft Mod (*.jar)")
        if path:
            self.jarSelected.emit(path)

    def mousePressEvent(self, event) -> None:
        if event.button() == Qt.MouseButton.LeftButton:
            self.choose()
        super().mousePressEvent(event)

    def dragEnterEvent(self, event: QDragEnterEvent) -> None:
        if event.mimeData().hasUrls() and event.mimeData().urls()[0].toLocalFile().lower().endswith(".jar"):
            event.acceptProposedAction()

    def dropEvent(self, event: QDropEvent) -> None:
        self.jarSelected.emit(event.mimeData().urls()[0].toLocalFile())


class SegmentedProgress(QWidget):
    def __init__(self) -> None:
        super().__init__()
        self.value = 0
        self.setFixedHeight(8)

    def setValue(self, value: int) -> None:
        self.value = max(0, min(100, value))
        self.update()

    def paintEvent(self, event) -> None:
        painter = QPainter(self)
        width = self.width()
        loaded = int(width * self.value / 100)
        painter.fillRect(0, 0, width, self.height(), QColor(PALETTE.border))
        painter.fillRect(0, 0, max(0, loaded - DIMENSIONS.progress_gap), self.height(), QColor(PALETTE.success))
        painter.fillRect(loaded + DIMENSIONS.progress_gap, 0, max(0, width - loaded - DIMENSIONS.progress_gap), self.height(), QColor(PALETTE.error))


class ModCard(Card):
    patchRequested = Signal(str)
    verifyRequested = Signal(str)
    filesRequested = Signal(str)

    def __init__(self, path: str) -> None:
        super().__init__(Path(path).name, path)
        self.path = path
        self.setFixedHeight(96)
        row = QHBoxLayout()
        self.menu = QPushButton("☰")
        self.patch = QPushButton(STRINGS.patch)
        self.verify = QPushButton(STRINGS.verify)
        row.addWidget(self.patch)
        row.addWidget(self.verify)
        row.addWidget(self.menu)
        self.layout().addLayout(row)
        self.progress = SegmentedProgress()
        self.layout().addWidget(self.progress)
        self.menu.clicked.connect(lambda: self.filesRequested.emit(self.path))
        self.patch.clicked.connect(lambda: self.patchRequested.emit(self.path))
        self.verify.clicked.connect(lambda: self.verifyRequested.emit(self.path))


class AILogPanel(QFrame):
    def __init__(self) -> None:
        super().__init__()
        self.setObjectName("card")
        self.small = 80
        self.large = 260
        self.setMaximumHeight(self.small)
        box = QVBoxLayout(self)
        row = QHBoxLayout()
        row.addWidget(label("AI-журнал"))
        close = QPushButton("×")
        row.addStretch()
        row.addWidget(close)
        box.addLayout(row)
        self.text = QTextEdit()
        self.text.setReadOnly(True)
        box.addWidget(self.text)
        close.clicked.connect(lambda: self.setMaximumHeight(self.small))

    def mousePressEvent(self, event) -> None:
        target = self.large if self.maximumHeight() == self.small else self.small
        animation = QPropertyAnimation(self, b"maximumHeight", self)
        animation.setStartValue(self.maximumHeight())
        animation.setEndValue(target)
        animation.setDuration(300)
        animation.setEasingCurve(QEasingCurve.Type.OutQuart)
        animation.start()


class ChatInput(QFrame):
    sendRequested = Signal(str)

    def __init__(self) -> None:
        super().__init__()
        self.setObjectName("card")
        layout = QHBoxLayout(self)
        self.input = QLineEdit()
        self.input.setPlaceholderText("Введите сообщение")
        self.send = QPushButton("➤")
        self.send.setObjectName("accent")
        self.send.setStyleSheet(f"QPushButton#accent{{background:{PALETTE.accent};color:#FFFFFF;border-radius:{DIMENSIONS.radius}px;padding:8px 14px;}}")
        layout.addWidget(self.input, 1)
        layout.addWidget(self.send)
        self.send.clicked.connect(self.emit_message)
        self.input.returnPressed.connect(self.emit_message)

    def emit_message(self) -> None:
        text = self.input.text().strip()
        if text:
            self.input.clear()
            self.sendRequested.emit(text)
