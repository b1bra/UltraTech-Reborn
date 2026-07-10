from __future__ import annotations

from PySide6.QtCore import Qt, Signal
from PySide6.QtWidgets import QDialog, QFrame, QHBoxLayout, QLabel, QPushButton, QScrollArea, QVBoxLayout, QWidget

from patcher.external.tools import ExternalTool, JavaRuntime, ToolLocator
from patcher.styles.theme import add_soft_shadow


class ToolCard(QFrame):
    selected = Signal(object)

    def __init__(self, tool: ExternalTool) -> None:
        super().__init__()
        self.tool = tool
        self.setObjectName("ToolCard")
        self.setFixedHeight(76)
        add_soft_shadow(self, 24, 6, 70)
        layout = QHBoxLayout(self)
        layout.setContentsMargins(18, 10, 18, 10)
        icon = QLabel(tool.icon)
        icon.setFixedWidth(40)
        icon.setAlignment(Qt.AlignmentFlag.AlignCenter)
        icon.setStyleSheet("font-size:24px;")
        text = QVBoxLayout()
        title = QLabel(tool.name)
        title.setStyleSheet("font-size:15px;font-weight:750;")
        path = QLabel(str(tool.executable or "Not found"))
        path.setObjectName("Muted")
        path.setWordWrap(False)
        text.addWidget(title)
        text.addWidget(path)
        layout.addWidget(icon)
        layout.addLayout(text, 1)

    def mousePressEvent(self, event):
        if event.button() == Qt.MouseButton.LeftButton:
            self.selected.emit(self.tool)
        super().mousePressEvent(event)

    def set_selected(self, selected: bool) -> None:
        self.setObjectName("SelectedCard" if selected else "ToolCard")
        self.style().unpolish(self)
        self.style().polish(self)


class JavaInfoCard(QFrame):
    def __init__(self, runtime: JavaRuntime) -> None:
        super().__init__()
        self.setObjectName("Card")
        add_soft_shadow(self, 28, 8, 80)
        layout = QVBoxLayout(self)
        layout.setContentsMargins(18, 14, 18, 14)
        title = QLabel("Java Runtime")
        title.setStyleSheet("font-size:17px;font-weight:800;")
        layout.addWidget(title)
        rows = (
            ("Java Version", runtime.version),
            ("Vendor", runtime.vendor),
            ("Architecture", runtime.architecture),
            ("Path", str(runtime.path or "Not found")),
        )
        for label, value in rows:
            row = QHBoxLayout()
            key = QLabel(label)
            key.setObjectName("Muted")
            val = QLabel(value)
            val.setAlignment(Qt.AlignmentFlag.AlignRight)
            val.setWordWrap(True)
            row.addWidget(key)
            row.addWidget(val, 1)
            layout.addLayout(row)


class ToolPicker(QDialog):
    def __init__(self, tools: list[ExternalTool], title: str = "Add an app", show_java: bool = False) -> None:
        super().__init__()
        self.selected_tool: ExternalTool | None = None
        self.cards: list[ToolCard] = []
        self.setWindowFlag(Qt.WindowType.FramelessWindowHint)
        self.setWindowModality(Qt.WindowModality.ApplicationModal)
        self.setAttribute(Qt.WidgetAttribute.WA_TranslucentBackground, False)
        self.setWindowTitle(title)
        self.resize(680, 520 if show_java else 420)
        self.setObjectName("Root")

        outer = QVBoxLayout(self)
        outer.setContentsMargins(22, 22, 22, 22)
        panel = QFrame(objectName="Panel")
        add_soft_shadow(panel, 42, 12, 120)
        layout = QVBoxLayout(panel)
        layout.setContentsMargins(18, 18, 18, 18)
        heading = QLabel(title)
        heading.setStyleSheet("font-size:22px;font-weight:850;")
        layout.addWidget(heading)

        if show_java:
            layout.addWidget(JavaInfoCard(ToolLocator().discover_java()))

        scroll = QScrollArea()
        scroll.setWidgetResizable(True)
        content = QWidget()
        list_layout = QVBoxLayout(content)
        list_layout.setContentsMargins(4, 8, 4, 8)
        list_layout.setSpacing(12)
        for tool in tools:
            card = ToolCard(tool)
            card.selected.connect(self._select)
            self.cards.append(card)
            list_layout.addWidget(card)
        list_layout.addStretch()
        scroll.setWidget(content)
        layout.addWidget(scroll, 1)

        actions = QHBoxLayout()
        actions.addStretch()
        ok = QPushButton("OK")
        ok.setObjectName("Accent")
        cancel = QPushButton("Cancel")
        ok.clicked.connect(self.accept)
        cancel.clicked.connect(self.reject)
        actions.addWidget(cancel)
        actions.addWidget(ok)
        layout.addLayout(actions)
        outer.addWidget(panel)

    def _select(self, tool: ExternalTool) -> None:
        self.selected_tool = tool
        for card in self.cards:
            card.set_selected(card.tool == tool)
