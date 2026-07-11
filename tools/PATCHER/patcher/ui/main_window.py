from __future__ import annotations

from pathlib import Path
from PySide6.QtCore import QEasingCurve, QPropertyAnimation, QTimer, Qt
from PySide6.QtWidgets import QFileDialog, QHBoxLayout, QLabel, QMainWindow, QPushButton, QScrollArea, QTextEdit, QVBoxLayout, QWidget
from patcher.ai.manager import AIManager
from patcher.core.config.manager import ConfigManager
from patcher.core.scheduler.tasks import TaskScheduler
from patcher.core.services.environment import RuntimeInfo
from patcher.patchers.engine import PatchExecutor, PatchPlanner
from patcher.scanner.engine import ScannerEngine
from patcher.compatibility.engine import CompatibilityEngine
from patcher.verification.engine import VerificationEngine
from patcher.ui.styles.tokens import SIZES, STRINGS
from patcher.ui.widgets.mod_card import ModCard
from patcher.ui.windows.ai_chat import AIChatWindow, ModelSelectorDialog
from patcher.ui.windows.settings_window import SettingsWindow
from patcher.ui.windows.title_bar import TitleBar

class DropArea(QWidget):
    def __init__(self, on_file: callable) -> None:
        super().__init__(objectName="Panel")
        self.on_file = on_file
        self.setAcceptDrops(True)
        layout = QVBoxLayout(self)
        title = QLabel("Drop Minecraft .jar mod here")
        title.setStyleSheet("font-size:28px;font-weight:800;")
        subtitle = QLabel("or choose a JAR. PATCHER keeps the original archive untouched.")
        subtitle.setObjectName("Muted")
        choose = QPushButton("Choose JAR")
        choose.setObjectName("Accent")
        choose.clicked.connect(self._choose)
        layout.addStretch()
        layout.addWidget(title, alignment=Qt.AlignmentFlag.AlignCenter)
        layout.addWidget(subtitle, alignment=Qt.AlignmentFlag.AlignCenter)
        layout.addWidget(choose, alignment=Qt.AlignmentFlag.AlignCenter)
        layout.addStretch()

    def _choose(self) -> None:
        path, _ = QFileDialog.getOpenFileName(self, "Select mod JAR", "", "Minecraft mods (*.jar)")
        if path:
            self.on_file(Path(path))

    def dragEnterEvent(self, event) -> None:
        if event.mimeData().hasUrls() and event.mimeData().urls()[0].toLocalFile().lower().endswith(".jar"):
            event.acceptProposedAction()

    def dropEvent(self, event) -> None:
        self.on_file(Path(event.mimeData().urls()[0].toLocalFile()))

class MainWindow(QMainWindow):
    def __init__(self, config: ConfigManager, scheduler: TaskScheduler, java: RuntimeInfo, minecraft: RuntimeInfo) -> None:
        super().__init__()
        self.config = config
        self.scheduler = scheduler
        self.java = java
        self.minecraft = minecraft
        self.scanner = ScannerEngine()
        self.compatibility = CompatibilityEngine()
        self.planner = PatchPlanner()
        self.executor = PatchExecutor()
        self.latest_source: Path | None = None
        self.latest_card: ModCard | None = None
        self.latest_jar = None
        self.latest_plan = None
        self.latest_patched: Path | None = None
        self.ai_manager = AIManager()
        self.verifier = VerificationEngine(java, minecraft)
        self._drag_pos = None
        self._ai_expanded = False
        self.ai_entries: list[str] = []

        self.setWindowFlags(Qt.WindowType.FramelessWindowHint)
        self.resize(SIZES.window_width, SIZES.window_height)
        root = QWidget(objectName="Root")
        self.setCentralWidget(root)
        outer = QVBoxLayout(root)
        outer.setContentsMargins(10, 10, 10, 10)

        self.title_bar = TitleBar(self)
        self.title_bar.settings.clicked.connect(self._settings)
        outer.addWidget(self.title_bar)

        body = QHBoxLayout()
        left = QVBoxLayout()
        self.drop = DropArea(self.add_jar)
        left.addWidget(self.drop, 1)
        self.ai_log = QTextEdit()
        self.ai_log.setObjectName("Panel")
        self.ai_log.setReadOnly(True)
        self.ai_log.setFixedHeight(58)
        self.ai_log.setText("AI journal — нажмите, чтобы раскрыть")
        self.ai_log.mouseReleaseEvent = lambda _event: self.toggle_ai_journal()
        left.addWidget(self.ai_log)
        bottom_left = QHBoxLayout()
        chat = QPushButton(STRINGS.ai_chat)
        chat.clicked.connect(self._open_model_selector_for_chat)
        bottom_left.addWidget(chat)
        bottom_left.addStretch()
        left.addLayout(bottom_left)
        body.addLayout(left, 65)

        right_root = QWidget(objectName="Panel")
        right = QVBoxLayout(right_root)
        right.addWidget(QLabel("Mods"))
        self.mod_list = QWidget()
        self.cards = QVBoxLayout(self.mod_list)
        self.cards.setSpacing(8)
        self.cards.addStretch()
        scroll = QScrollArea()
        scroll.setWidgetResizable(True)
        scroll.setWidget(self.mod_list)
        right.addWidget(scroll)
        patch_button = QPushButton("Patch Mod")
        patch_button.clicked.connect(self._patch_latest_mod)
        verify_button = QPushButton("Verify Patch")
        verify_button.clicked.connect(self._verify_latest_patch)
        save = QPushButton(STRINGS.save_mod)
        save.clicked.connect(self._save_latest_mod)
        right.addWidget(patch_button, alignment=Qt.AlignmentFlag.AlignRight)
        right.addWidget(verify_button, alignment=Qt.AlignmentFlag.AlignRight)
        right.addWidget(save, alignment=Qt.AlignmentFlag.AlignRight)
        body.addWidget(right_root, 35)
        outer.addLayout(body)
        self._log_ai(f"Runtime: {java.status} — {java.path or 'missing'}")
        self._log_ai(f"Minecraft: {minecraft.status} — {minecraft.path or 'missing'}")

    def mousePressEvent(self, event) -> None:
        if event.button() == Qt.MouseButton.LeftButton:
            self._drag_pos = event.globalPosition().toPoint() - self.frameGeometry().topLeft()

    def mouseMoveEvent(self, event) -> None:
        if self._drag_pos and event.buttons() & Qt.MouseButton.LeftButton:
            self.move(event.globalPosition().toPoint() - self._drag_pos)

    def toggle_ai_journal(self) -> None:
        self._ai_expanded = not self._ai_expanded
        animation = QPropertyAnimation(self.ai_log, b"maximumHeight", self)
        animation.setDuration(SIZES.animation_ms)
        animation.setEasingCurve(QEasingCurve.Type.OutCubic)
        animation.setStartValue(self.ai_log.height())
        animation.setEndValue(260 if self._ai_expanded else 58)
        animation.start()
        self._ai_animation = animation

    def add_jar(self, path: Path) -> None:
        if path.suffix.lower() != ".jar":
            return
        self.latest_source = path
        card = ModCard(path)
        self.latest_card = card
        self.cards.insertWidget(self.cards.count() - 1, card)
        self._log_ai(f"Scanner: queued analysis for {path.name}")
        future = self.scheduler.submit(self.scanner.analyze, path, lambda stage, pct: None)
        card.set_progress(8, "Queued")

        def poll() -> None:
            if not future.done():
                current = min(92, card.progress._value + 7)
                card.set_progress(current, "Analyzing…")
                QTimer.singleShot(140, poll)
                return
            jar = self.compatibility.analyze(future.result())
            plan = self.planner.plan(jar)
            self.latest_jar = jar
            self.latest_plan = plan
            card.set_result(jar, plan.total_problems, plan.auto_fixable)
            self._log_ai(f"Scanner: {path.name} analyzed; classes={jar.metadata.get('class_count', 0)}, loader={jar.loader.value}")
            for diagnostic in jar.diagnostics:
                self._log_ai(f"{diagnostic.analyzer}: {diagnostic.title} — {diagnostic.description}")
        poll()

    def _save_latest_mod(self) -> None:
        if self.latest_source is None:
            self._log_ai("PatchExecutor: no mod selected for saving")
            return
        destination, _ = QFileDialog.getSaveFileName(self, "Save patched JAR", str(self.latest_source.with_name(self.latest_source.stem + "-patched.jar")), "Minecraft mods (*.jar)")
        if not destination:
            return
        source = self.latest_patched or self.latest_source
        patched = self.executor.create_patched_copy(source, Path(destination))
        self._log_ai(f"PatchExecutor: saved patched copy to {patched}")

    def _load_ai_models(self):
        return self.ai_manager.load_keys(Path(self.config.config.api_file)) if self.config.config.api_file else []

    def _patch_latest_mod(self) -> None:
        if self.latest_source is None or self.latest_jar is None or self.latest_plan is None:
            self._log_ai("PatchPipeline: load and analyze a mod before patching")
            return
        models = self._load_ai_models()
        if len(models) < 2:
            self._log_ai("PatchPipeline: api.txt should contain two AI lines; using available/local consensus fallback")
        responses = self.ai_manager.analyze_with_all(models, self.latest_jar, self.latest_plan, self.executor.history.entries)
        for response in responses:
            self._log_ai(f"{response.model_name}: {response.content}")
        decision = self.ai_manager.agree(responses)
        self._log_ai(f"AI consensus: {decision.content}")
        destination = self.latest_source.with_name(self.latest_source.stem + "-patched.jar")
        applied = self.executor.apply_ai_patch(self.latest_source, destination, decision, self.latest_jar)
        self.latest_patched = applied.output
        self._log_ai(f"PatchExecutor: applied {applied.patch_type} -> {applied.output}")

    def _verify_latest_patch(self) -> None:
        if self.latest_patched is None:
            self._log_ai("Verification: no patched mod yet; run Patch Mod first")
            return
        future = self.scheduler.submit(self.verifier.verify, self.latest_patched, 30)
        self._log_ai("Verification: sandbox creation and Minecraft launch queued")
        def poll() -> None:
            if not future.done():
                QTimer.singleShot(250, poll)
                return
            report = future.result()
            self._log_ai(f"Verification: success={report.success}; sandbox={report.sandbox}; error={report.error or 'none'}")
            for line in report.logs[-20:]:
                self._log_ai(f"VerificationLog: {line}")
        poll()

    def _open_model_selector_for_chat(self) -> None:
        models = self._load_ai_models()
        selector = ModelSelectorDialog(models, allow_chat=True)
        if selector.exec() == 1 and selector.selected:
            context = self.ai_manager.build_context(self.latest_jar, self.latest_plan, self.executor.history.entries)
            chat = AIChatWindow(self.ai_manager, selector.selected, context, self._log_ai)
            chat.exec()

    def _log_ai(self, message: str) -> None:
        self.ai_entries.append(message)
        self.ai_log.setText("\n".join(self.ai_entries[-200:]))

    def _settings(self) -> None:
        SettingsWindow(self.config, self.java, self.minecraft).exec()
