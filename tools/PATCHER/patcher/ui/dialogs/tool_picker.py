from __future__ import annotations

from PySide6.QtCore import Qt
from PySide6.QtWidgets import QDialog, QGridLayout, QLabel, QPushButton, QVBoxLayout, QWidget
from patcher.external.tools import ExternalTool

class ToolPicker(QDialog):
    def __init__(self, tools: list[ExternalTool], title: str = "Add an app") -> None:
        super().__init__(); self.setWindowFlag(Qt.WindowType.FramelessWindowHint); self.setWindowTitle(title); self.resize(460, 320)
        layout = QVBoxLayout(self); grid = QGridLayout();
        for i, tool in enumerate(tools):
            card = QWidget(); card.setStyleSheet("QWidget{background:#20242e;border-radius:18px;} QWidget:hover{background:#303747;}")
            v = QVBoxLayout(card); v.addWidget(QLabel(tool.icon), alignment=Qt.AlignmentFlag.AlignCenter); v.addWidget(QLabel(tool.name), alignment=Qt.AlignmentFlag.AlignCenter)
            v.addWidget(QLabel(str(tool.executable or "Not found")), alignment=Qt.AlignmentFlag.AlignCenter); grid.addWidget(card, i//2, i%2)
        ok = QPushButton("OK"); cancel = QPushButton("Cancel"); ok.clicked.connect(self.accept); cancel.clicked.connect(self.reject)
        layout.addLayout(grid); layout.addWidget(ok); layout.addWidget(cancel)
