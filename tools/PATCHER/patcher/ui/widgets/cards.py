from __future__ import annotations

from pathlib import Path
from PySide6.QtCore import QEasingCurve, Qt, Signal
from PySide6.QtWidgets import QFrame, QHBoxLayout, QLabel, QPushButton, QVBoxLayout

from patcher.constants import LAYOUT
from patcher.diagnostics.models import JarAnalysis
from patcher.ui.animations.factory import AnimationFactory
from patcher.ui.widgets.progress import SegmentedProgress
from patcher.styles.theme import add_soft_shadow

class ModCard(QFrame):
    saveRequested = Signal(Path)
    minimized = Signal()

    def __init__(self, jar_path: Path) -> None:
        super().__init__(); self.jar_path = jar_path; self.analysis: JarAnalysis | None = None
        self.setObjectName("ModCard"); self.setFixedHeight(92); add_soft_shadow(self, 22, 6, 70)
        layout = QVBoxLayout(self); layout.setContentsMargins(14, 10, 14, 10); layout.setSpacing(8); top = QHBoxLayout(); self.title = QLabel(jar_path.stem); self.title.setStyleSheet("font-size:15px;font-weight:700;")
        save = QPushButton("↓"); save.setToolTip("Save patched JAR"); mini = QPushButton("_"); mini.setToolTip("Minimize info")
        save.clicked.connect(lambda: self.saveRequested.emit(self.jar_path)); mini.clicked.connect(self.minimized.emit)
        top.addWidget(self.title); top.addStretch(); top.addWidget(save); top.addWidget(mini)
        row = QHBoxLayout(); self.progress = SegmentedProgress(); self.percent = QLabel("0%"); self.percent.setFixedWidth(44); self.percent.setAlignment(Qt.AlignmentFlag.AlignRight)
        row.addWidget(self.progress); row.addWidget(self.percent); layout.addLayout(top); layout.addLayout(row)

    def animate_progress(self, value: int) -> None:
        anim = AnimationFactory.property(self.progress, b"value", LAYOUT.animation_normal_ms, QEasingCurve.Type.OutQuart)
        anim.setStartValue(self.progress.value); anim.setEndValue(value); anim.start(); self._anim = anim
        self.percent.setText(f"{value}%")

    def complete(self, success: bool) -> None:
        self.progress.mark_done(True)
        if success:
            self.setObjectName("ModCard"); self.title.setText(f"<s>{self.jar_path.stem}</s>")
        else:
            self.setStyleSheet("QFrame#ModCard{background:#332129;border:1px solid #5a3844;border-radius:22px;}")
