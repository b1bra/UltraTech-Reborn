from __future__ import annotations

from pathlib import Path
from PySide6.QtCore import QEasingCurve, Qt
from PySide6.QtWidgets import QDialog, QFileDialog, QFrame, QHBoxLayout, QLabel, QPushButton, QVBoxLayout
from patcher.core.config.manager import ConfigManager, LauncherConfig
from patcher.ui.animations.manager import AnimationManager
from patcher.ui.styles.tokens import STRINGS

class LauncherDialog(QDialog):
    def __init__(self, config: ConfigManager) -> None:
        super().__init__(); self.config = config; self.selected: LauncherConfig | None = None
        self.setModal(True); self.setWindowFlags(Qt.WindowType.FramelessWindowHint | Qt.WindowType.Dialog); self.resize(560, 560)
        self.animations = AnimationManager(); root = QVBoxLayout(self); root.setContentsMargins(24,24,24,24)
        title = QLabel('Выберите лаунчер'); title.setStyleSheet('font-size:24px;font-weight:800;'); root.addWidget(title, alignment=Qt.AlignmentFlag.AlignCenter)
        self.cards = QVBoxLayout(); root.addLayout(self.cards); root.addStretch(); self.notice = QLabel(''); self.notice.hide(); root.addWidget(self.notice)
        buttons = QHBoxLayout(); ok = QPushButton('OK'); skip = QPushButton(STRINGS.continue_without_launcher); ok.clicked.connect(self._ok); skip.clicked.connect(self.accept); buttons.addWidget(ok); buttons.addWidget(skip); root.addLayout(buttons)
        self._reload()
    def _reload(self) -> None:
        while self.cards.count():
            item = self.cards.takeAt(0); item.widget() and item.widget().deleteLater()
        if not self.config.config.launchers:
            self.cards.addWidget(self._card(STRINGS.add_launcher, '+', self._add))
        else:
            for launcher in self.config.config.launchers:
                self.cards.addWidget(self._card(f'{launcher.icon} {launcher.name}\n{launcher.path}', '', lambda l=launcher: self._select(l)))
            self.cards.addWidget(self._card(STRINGS.add_launcher, '+', self._add))
    def _card(self, text: str, icon: str, callback: callable) -> QFrame:
        frame = QFrame(objectName='Card'); layout = QVBoxLayout(frame); label = QLabel(f'{icon} {text}'); label.setAlignment(Qt.AlignmentFlag.AlignCenter); layout.addWidget(label); frame.mouseReleaseEvent = lambda event: callback(); return frame
    def _add(self) -> None:
        path = QFileDialog.getExistingDirectory(self, STRINGS.add_launcher)
        if path and self.config.add_launcher(Path(path)):
            self._reload()
    def _select(self, launcher: LauncherConfig) -> None:
        self.selected = launcher; self.config.config.selected_launcher = launcher.path; self.config.save()
    def _ok(self) -> None:
        if self.selected or self.config.config.selected_launcher:
            self.accept(); return
        self.notice.setText(STRINGS.choose_launcher_required); self.notice.setStyleSheet('background:#516274;border-radius:10px;padding:10px;'); self.notice.show()
        animation = self.animations.property(self.notice, b'windowOpacity', easing=QEasingCurve.Type.InOutCubic); animation.setStartValue(0.0); animation.setEndValue(1.0); animation.start(); self._notice_animation = animation
