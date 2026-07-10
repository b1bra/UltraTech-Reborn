from __future__ import annotations

from pathlib import Path
from PySide6.QtCore import QEasingCurve, QParallelAnimationGroup, Qt
from PySide6.QtWidgets import QFileDialog, QHBoxLayout, QLabel, QMainWindow, QPushButton, QScrollArea, QVBoxLayout, QWidget

from patcher.analyzers.builtin import MetadataAnalyzer
from patcher.constants import LAYOUT
from patcher.core.pipeline import PatchPipeline
from patcher.external.tools import ToolLocator
from patcher.scanner.jar_scanner import JarScanner
from patcher.ui.animations.factory import AnimationFactory
from patcher.ui.dialogs.log_viewer import LogViewer
from patcher.ui.dialogs.tool_picker import ToolPicker
from patcher.ui.widgets.cards import ModCard
from patcher.analyzers.registry import AnalyzerRegistry

class DropArea(QWidget):
    def __init__(self, on_file) -> None:
        super().__init__(); self.on_file = on_file; self.setAcceptDrops(True); self.setObjectName("Panel")
        layout = QVBoxLayout(self); layout.setContentsMargins(26, 24, 26, 24); title = QLabel("Drop Minecraft .jar mod here"); title.setStyleSheet("font-size:28px;font-weight:800;")
        subtitle = QLabel("or choose a JAR. PATCHER keeps the original archive untouched."); subtitle.setObjectName("Muted")
        choose = QPushButton("Choose JAR"); choose.setObjectName("Accent"); choose.clicked.connect(self._choose)
        layout.addStretch(); layout.addWidget(title, alignment=Qt.AlignmentFlag.AlignCenter); layout.addWidget(subtitle, alignment=Qt.AlignmentFlag.AlignCenter); layout.addWidget(choose, alignment=Qt.AlignmentFlag.AlignCenter); layout.addStretch()
    def _choose(self) -> None:
        path, _ = QFileDialog.getOpenFileName(self, "Select mod JAR", "", "Minecraft mods (*.jar)")
        if path: self.on_file(Path(path))
    def dragEnterEvent(self, event):
        if event.mimeData().hasUrls() and event.mimeData().urls()[0].toLocalFile().lower().endswith(".jar"): event.acceptProposedAction()
    def dropEvent(self, event): self.on_file(Path(event.mimeData().urls()[0].toLocalFile()))

class MainWindow(QMainWindow):
    def __init__(self) -> None:
        super().__init__(); self.setWindowFlags(Qt.WindowType.FramelessWindowHint); self.resize(LAYOUT.window_width, LAYOUT.window_height)
        self.pipeline = PatchPipeline(JarScanner(), AnalyzerRegistry().create_all([MetadataAnalyzer()])); self.logs: list[str] = []
        root = QWidget(objectName="Root"); self.setCentralWidget(root); outer = QVBoxLayout(root)
        title = QHBoxLayout(); settings = QPushButton("⚙"); settings.clicked.connect(self._settings); title.addWidget(settings); title.addStretch(); minb = QPushButton("—"); close = QPushButton("×"); close.setObjectName("Danger"); minb.clicked.connect(self.showMinimized); close.clicked.connect(self.close); title.addWidget(minb); title.addWidget(close); outer.addLayout(title)
        body = QHBoxLayout(); body.setSpacing(18); self.drop = DropArea(self.add_jar); body.addWidget(self.drop, 62)
        right = QVBoxLayout(); self.list_widget = QWidget(); self.cards = QVBoxLayout(self.list_widget); self.cards.addStretch()
        scroll = QScrollArea(); scroll.setWidgetResizable(True); scroll.setWidget(self.list_widget); scroll.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAsNeeded); right.addWidget(scroll)
        buttons = QHBoxLayout(); logs = QPushButton("Просмотр логов"); errors = QPushButton("Просмотр ошибок"); logs.clicked.connect(lambda: self._viewer("Logs", "\n".join(self.logs))); errors.clicked.connect(lambda: self._viewer("Errors", "\n".join(l for l in self.logs if "ERROR" in l or "WARNING" in l))); buttons.addWidget(logs); buttons.addWidget(errors); right.addLayout(buttons); body.addLayout(right, 35); outer.addLayout(body)
        self._drag_pos = None
    def mousePressEvent(self, event):
        if event.button() == Qt.MouseButton.LeftButton: self._drag_pos = event.globalPosition().toPoint() - self.frameGeometry().topLeft()
    def mouseMoveEvent(self, event):
        if self._drag_pos and event.buttons() & Qt.MouseButton.LeftButton: self.move(event.globalPosition().toPoint() - self._drag_pos)
    def add_jar(self, path: Path) -> None:
        if path.suffix.lower() != ".jar": return
        card = ModCard(path); card.saveRequested.connect(self._save_copy); self.cards.insertWidget(self.cards.count()-1, card)
        group = QParallelAnimationGroup(card); fade = AnimationFactory.property(card, b"windowOpacity", LAYOUT.animation_slow_ms, QEasingCurve.Type.InOutCubic); fade.setStartValue(0.0); fade.setEndValue(1.0); group.addAnimation(fade); group.start(); card._group = group
        for pct in (18, 43, 71): card.animate_progress(pct)
        analysis = self.pipeline.analyze(path); card.analysis = analysis; card.animate_progress(100); card.complete(not any(d.severity == "error" for d in analysis.diagnostics))
        for d in analysis.diagnostics: self.logs.append(f"{d.severity.upper()} | {d.title} | {d.category} | {d.description} | Cause: {d.cause} | Patch: {d.applied_patch or 'none'} | Unresolved: {d.unresolved_reason or 'n/a'}")
    def _save_copy(self, source: Path) -> None:
        dest, _ = QFileDialog.getSaveFileName(self, "Save patched JAR", str(source.with_name(source.stem + "-patched.jar")), "Minecraft mods (*.jar)")
        if dest: self.pipeline.save_patched_copy(source, Path(dest))
    def _viewer(self, title: str, text: str) -> None: LogViewer(title, text or "No entries yet.").exec()
    def _settings(self) -> None: ToolPicker(ToolLocator().discover(), "Settings", show_java=True).exec()
