from __future__ import annotations

from PySide6.QtCore import Qt
from PySide6.QtWidgets import QHBoxLayout, QLabel, QPushButton, QWidget
from patcher.ui.styles.tokens import STRINGS

class TitleBar(QWidget):
    def __init__(self, parent: QWidget) -> None:
        super().__init__(parent)
        layout = QHBoxLayout(self); layout.setContentsMargins(10, 4, 10, 4)
        layout.addWidget(QLabel(STRINGS.app_name)); layout.addStretch()
        self.settings = QPushButton('⚙')
        self.minimize = QPushButton('—')
        self.close_button = QPushButton('×'); self.close_button.setObjectName('Danger')
        self.minimize.clicked.connect(parent.showMinimized)
        self.close_button.clicked.connect(parent.close)
        layout.addWidget(self.settings); layout.addWidget(self.minimize); layout.addWidget(self.close_button)
        self.setCursor(Qt.CursorShape.ArrowCursor)
