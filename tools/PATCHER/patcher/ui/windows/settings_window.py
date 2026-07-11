from __future__ import annotations

from dataclasses import dataclass
from pathlib import Path
from PySide6.QtCore import QTimer
from PySide6.QtWidgets import QDialog, QFileDialog, QFrame, QLabel, QPushButton, QScrollArea, QVBoxLayout, QWidget
from patcher.ai.manager import AIManager
from patcher.core.config.manager import ConfigManager
from patcher.core.services.environment import RuntimeInfo
from patcher.ui.windows.ai_chat import ModelSelectorDialog

@dataclass(frozen=True, slots=True)
class SettingsCardSpec:
    key: str
    title: str
    description: str
    icon: str

SETTING_CARDS = (
    SettingsCardSpec('java', 'Java Runtime', 'Detected automatically at startup', '☕'),
    SettingsCardSpec('minecraft', 'Minecraft', 'Detected automatically at startup', '⛏'),
    SettingsCardSpec('launcher', 'Launcher', 'Selected launcher context', '🎮'),
    SettingsCardSpec('recaf', 'Recaf', 'External bytecode workbench', '🧩'),
    SettingsCardSpec('cfr', 'CFR', 'CFR decompiler jar or executable', '📄'),
    SettingsCardSpec('fernflower', 'FernFlower', 'FernFlower decompiler path', '🌿'),
    SettingsCardSpec('vineflower', 'Vineflower', 'Vineflower decompiler path', '🌱'),
    SettingsCardSpec('asmifier', 'ASMifier', 'ASM inspection tool', '🔬'),
    SettingsCardSpec('jdgui', 'JD-GUI', 'Source browsing tool', '📚'),
    SettingsCardSpec('bytecode_viewer', 'Bytecode Viewer', 'Bytecode viewer path', '👁'),
    SettingsCardSpec('decompiler', 'Decompiler', 'Default decompiler path', '⚙'),
    SettingsCardSpec('compiler', 'Compiler', 'Compiler executable or directory', '🛠'),
    SettingsCardSpec('api_file', 'API Configuration', 'External api.txt; one key per line, optional model name in parentheses', '🔑'),
)

class SettingsWindow(QDialog):
    def __init__(self, config: ConfigManager, java: RuntimeInfo, minecraft: RuntimeInfo) -> None:
        super().__init__(); self.config = config; self.java = java; self.minecraft = minecraft; self.setWindowTitle('Settings'); self.resize(980, 720)
        root = QVBoxLayout(self); title = QLabel('Settings'); title.setStyleSheet('font-size:26px;font-weight:800;'); root.addWidget(title)
        scroll = QScrollArea(); scroll.setWidgetResizable(True); self.container = QWidget(); self.list = QVBoxLayout(self.container); scroll.setWidget(self.container); root.addWidget(scroll)
        QTimer.singleShot(0, self.populate_async)
    def populate_async(self) -> None:
        for spec in SETTING_CARDS:
            self.list.addWidget(self._card(spec))
        self.list.addStretch()
    def _value_for(self, spec: SettingsCardSpec) -> tuple[str, str]:
        if spec.key == 'java': return self.java.path, self.java.status
        if spec.key == 'minecraft': return self.minecraft.path, self.minecraft.status
        if spec.key == 'launcher': return self.config.config.selected_launcher or '', 'Configured' if self.config.config.selected_launcher else 'Missing'
        if spec.key == 'api_file': return self.config.config.api_file, 'Configured' if self.config.config.api_file else 'Missing'
        value = self.config.config.tools.get(spec.key, '')
        return value, 'Configured' if value else 'Missing'
    def _card(self, spec: SettingsCardSpec) -> QFrame:
        frame = QFrame(objectName='Card'); layout = QVBoxLayout(frame)
        value, status = self._value_for(spec)
        label = QLabel(f'{spec.icon}  {spec.title}\n{spec.description}\nPath: {value or "not configured"}\nStatus: {status}')
        layout.addWidget(label)
        if spec.key == 'api_file':
            preview = QPushButton('Предпросмотр моделей'); preview.clicked.connect(self._preview_models); layout.addWidget(preview)
        def choose(_event) -> None:
            if spec.key in {'java', 'minecraft'}:
                return
            path, _ = QFileDialog.getOpenFileName(self, spec.title)
            if not path:
                path = QFileDialog.getExistingDirectory(self, spec.title)
            if not path:
                return
            if spec.key == 'api_file': self.config.config.api_file = path
            elif spec.key == 'launcher': self.config.config.selected_launcher = path
            else: self.config.config.tools[spec.key] = path
            self.config.save(); label.setText(f'{spec.icon}  {spec.title}\n{spec.description}\nPath: {path}\nStatus: Configured')
        frame.mouseDoubleClickEvent = choose
        return frame
    def _preview_models(self) -> None:
        models = AIManager().load_keys(Path(self.config.config.api_file)) if self.config.config.api_file else []
        ModelSelectorDialog(models, allow_chat=False).exec()
