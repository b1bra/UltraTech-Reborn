from __future__ import annotations

from pathlib import Path
from PySide6.QtCore import QTimer, Qt
from PySide6.QtWidgets import QFileDialog, QHBoxLayout, QLabel, QMainWindow, QPushButton, QScrollArea, QVBoxLayout, QWidget
from patcher.core.config.manager import ConfigManager
from patcher.core.scheduler.tasks import TaskScheduler
from patcher.patchers.engine import PatchExecutor, PatchPlanner
from patcher.scanner.engine import ScannerEngine
from patcher.ui.styles.tokens import SIZES
from patcher.ui.windows.settings_window import SettingsWindow
from patcher.ui.windows.title_bar import TitleBar

class DropArea(QWidget):
    def __init__(self, on_file: callable) -> None:
        super().__init__(objectName='Panel'); self.on_file = on_file; self.setAcceptDrops(True)
        layout = QVBoxLayout(self); title = QLabel('Drop Minecraft .jar mod here'); title.setStyleSheet('font-size:28px;font-weight:800;')
        subtitle = QLabel('or choose a JAR. PATCHER keeps the original archive untouched.'); subtitle.setObjectName('Muted')
        choose = QPushButton('Choose JAR'); choose.setObjectName('Accent'); choose.clicked.connect(self._choose)
        layout.addStretch(); layout.addWidget(title, alignment=Qt.AlignmentFlag.AlignCenter); layout.addWidget(subtitle, alignment=Qt.AlignmentFlag.AlignCenter); layout.addWidget(choose, alignment=Qt.AlignmentFlag.AlignCenter); layout.addStretch()
    def _choose(self) -> None:
        path, _ = QFileDialog.getOpenFileName(self, 'Select mod JAR', '', 'Minecraft mods (*.jar)')
        if path: self.on_file(Path(path))
    def dragEnterEvent(self, event) -> None:
        if event.mimeData().hasUrls() and event.mimeData().urls()[0].toLocalFile().lower().endswith('.jar'): event.acceptProposedAction()
    def dropEvent(self, event) -> None:
        self.on_file(Path(event.mimeData().urls()[0].toLocalFile()))

class MainWindow(QMainWindow):
    def __init__(self, config: ConfigManager, scheduler: TaskScheduler) -> None:
        super().__init__(); self.config = config; self.scheduler = scheduler; self.scanner = ScannerEngine(); self.planner = PatchPlanner(); self.executor = PatchExecutor(); self._drag_pos = None
        self.setWindowFlags(Qt.WindowType.FramelessWindowHint); self.resize(SIZES.window_width, SIZES.window_height)
        root = QWidget(objectName='Root'); self.setCentralWidget(root); outer = QVBoxLayout(root); outer.setContentsMargins(10,10,10,10)
        self.title_bar = TitleBar(self); self.title_bar.settings.clicked.connect(self._settings); outer.addWidget(self.title_bar)
        body = QHBoxLayout(); self.drop = DropArea(self.add_jar); body.addWidget(self.drop, 65)
        right_root = QWidget(objectName='Panel'); right = QVBoxLayout(right_root); right.addWidget(QLabel('Mods'))
        self.mod_list = QWidget(); self.cards = QVBoxLayout(self.mod_list); self.cards.addStretch(); scroll = QScrollArea(); scroll.setWidgetResizable(True); scroll.setWidget(self.mod_list); right.addWidget(scroll)
        body.addWidget(right_root, 35); outer.addLayout(body)
        self.ai_log = QLabel('AI journal — click to expand'); self.ai_log.setObjectName('Panel'); outer.addWidget(self.ai_log)
    def mousePressEvent(self, event) -> None:
        if event.button() == Qt.MouseButton.LeftButton: self._drag_pos = event.globalPosition().toPoint() - self.frameGeometry().topLeft()
    def mouseMoveEvent(self, event) -> None:
        if self._drag_pos and event.buttons() & Qt.MouseButton.LeftButton: self.move(event.globalPosition().toPoint() - self._drag_pos)
    def add_jar(self, path: Path) -> None:
        label = QLabel(f'Analyzing {path.name}…'); label.setObjectName('Panel'); self.cards.insertWidget(self.cards.count()-1, label)
        future = self.scheduler.submit(self.scanner.analyze, path, None)
        def poll() -> None:
            if not future.done(): QTimer.singleShot(50, poll); return
            jar = future.result(); plan = self.planner.plan(jar)
            label.setText(f'{path.name}\nLoader: {jar.loader.value}\nClasses: {jar.metadata.get("class_count", 0)}\nSHA256: {jar.sha256[:16]}…\nProblems: {plan.total_problems}, auto-fixable: {plan.auto_fixable}')
        poll()
    def _settings(self) -> None:
        SettingsWindow(self.config).exec()
