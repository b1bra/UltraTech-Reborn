from __future__ import annotations

from PySide6.QtWidgets import QDialog, QHBoxLayout, QLineEdit, QPushButton, QTextEdit, QVBoxLayout

class LogViewer(QDialog):
    def __init__(self, title: str, text: str) -> None:
        super().__init__(); self.setWindowTitle(title); self.resize(760, 520)
        layout = QVBoxLayout(self); tools = QHBoxLayout(); self.search = QLineEdit(); self.search.setPlaceholderText("Search / filter diagnostics...")
        copy = QPushButton("Copy"); tools.addWidget(self.search); tools.addWidget(copy)
        self.text = QTextEdit(); self.text.setReadOnly(True); self.text.setPlainText(text)
        self.search.textChanged.connect(self._filter); copy.clicked.connect(self.text.copy)
        layout.addLayout(tools); layout.addWidget(self.text); self._source = text

    def _filter(self, needle: str) -> None:
        if not needle: self.text.setPlainText(self._source); return
        self.text.setPlainText("\n".join(line for line in self._source.splitlines() if needle.lower() in line.lower()))
