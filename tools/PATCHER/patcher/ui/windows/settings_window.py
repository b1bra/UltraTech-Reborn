from __future__ import annotations

from dataclasses import dataclass
from PySide6.QtCore import Qt, QTimer
from PySide6.QtWidgets import QDialog, QFileDialog, QFrame, QLabel, QScrollArea, QVBoxLayout, QWidget
from patcher.core.config.manager import ConfigManager

@dataclass(frozen=True, slots=True)
class SettingsCardSpec:
    key: str
    title: str
    description: str
    icon: str

SETTING_CARDS = (
    SettingsCardSpec('java', 'Java Runtime', 'Version, vendor, architecture and executable path', '☕'),
    SettingsCardSpec('minecraft', 'Minecraft', 'Game directory and version metadata', '⛏'),
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
    SettingsCardSpec('api_file', 'API Configuration', 'External api.txt with keys in key (name) format', '🔑'),
    SettingsCardSpec('chatgpt', 'ChatGPT', 'AI provider model card', '🤖'),
    SettingsCardSpec('deepseek', 'DeepSeek', 'AI provider model card', '🧠'),
    SettingsCardSpec('claude', 'Claude', 'AI provider model card', '💬'),
    SettingsCardSpec('gemini', 'Gemini', 'AI provider model card', '✨'),
)

class SettingsWindow(QDialog):
    def __init__(self, config: ConfigManager) -> None:
        super().__init__(); self.config = config; self.setWindowTitle('Settings'); self.resize(980, 720)
        root = QVBoxLayout(self); title = QLabel('Settings'); title.setStyleSheet('font-size:26px;font-weight:800;'); root.addWidget(title)
        scroll = QScrollArea(); scroll.setWidgetResizable(True); self.container = QWidget(); self.list = QVBoxLayout(self.container); scroll.setWidget(self.container); root.addWidget(scroll)
        QTimer.singleShot(0, self.populate_async)
    def populate_async(self) -> None:
        for spec in SETTING_CARDS:
            self.list.addWidget(self._card(spec))
        self.list.addStretch()
    def _card(self, spec: SettingsCardSpec) -> QFrame:
        frame = QFrame(objectName='Card'); layout = QVBoxLayout(frame)
        value = self.config.config.api_file if spec.key == 'api_file' else self.config.config.tools.get(spec.key, '')
        label = QLabel(f'{spec.icon}  {spec.title}\n{spec.description}\nPath: {value or "not configured"}\nStatus: {"Configured" if value else "Missing"}')
        layout.addWidget(label)
        def choose(_event) -> None:
            path, _ = QFileDialog.getOpenFileName(self, spec.title)
            if not path:
                path = QFileDialog.getExistingDirectory(self, spec.title)
            if not path:
                return
            if spec.key == 'api_file': self.config.config.api_file = path
            else: self.config.config.tools[spec.key] = path
            self.config.save(); label.setText(f'{spec.icon}  {spec.title}\n{spec.description}\nPath: {path}\nStatus: Configured')
        frame.mouseDoubleClickEvent = choose
        return frame
