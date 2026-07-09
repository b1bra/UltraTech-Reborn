"""Modern PySide6 GUI for the unified SCAN runner.

The analysis pipeline stays in :mod:`runner`, :mod:`launcher_analyzer`, and
:mod:`modpack_analyzer`.  This module is only responsible for presentation,
user input, animations, and forwarding progress events from the worker thread.
"""

from __future__ import annotations

import math
import queue
import random
import subprocess
import sys
import threading
from pathlib import Path

try:
    from PySide6.QtCore import QEasingCurve, QPoint, QPointF, QParallelAnimationGroup, QPropertyAnimation, Qt, QTimer, Property
    from PySide6.QtGui import QColor, QFont, QLinearGradient, QPainter, QPainterPath, QPen, QRadialGradient
    from PySide6.QtWidgets import (
        QApplication,
        QCheckBox,
        QDialog,
        QFileDialog,
        QFrame,
        QGraphicsDropShadowEffect,
        QGraphicsOpacityEffect,
        QHBoxLayout,
        QLabel,
        QLineEdit,
        QMainWindow,
        QPushButton,
        QPlainTextEdit,
        QSizePolicy,
        QVBoxLayout,
        QWidget,
    )
except ModuleNotFoundError as error:
    if error.name != "PySide6":
        raise
    message = "PySide6 is required to start SCAN. Install it with: python -m pip install -r tools/SCAN/requirements.txt"
    try:
        import ctypes

        ctypes.windll.user32.MessageBoxW(None, message, "SCAN startup error", 0x10)
    except Exception:
        print(message, file=sys.stderr)
    raise SystemExit(1) from error

try:
    from runner import ScanRequest, format_traceback, run_scan
except ModuleNotFoundError:
    from .runner import ScanRequest, format_traceback, run_scan

WIDTH = 1280
HEIGHT = 720
PANEL = "rgba(23, 23, 27, 176)"
TEXT = "#F0F2F8"
MUTED = "#AEB3C2"
ACCENT = "#7D9AFF"

APP_STYLE = """
* { color: #F0F2F8; font-family: 'Segoe UI', 'Inter', Arial, sans-serif; }
QLineEdit {
    background: rgba(30,30,34,190);
    border: 1px solid rgba(255,255,255,44);
    border-radius: 18px;
    padding: 0 18px;
    font-size: 17px;
    selection-background-color: #536DFE;
}
QLineEdit:hover { border-color: rgba(255,255,255,70); background: rgba(36,36,42,205); }
QLineEdit:focus { border-color: rgba(125,154,255,175); background: rgba(38,38,46,220); }
QCheckBox { spacing: 12px; color: #B8BBC6; font-size: 14px; }
QCheckBox::indicator { width: 22px; height: 22px; border-radius: 7px; border: 1px solid rgba(255,255,255,58); background: rgba(255,255,255,18); }
QCheckBox::indicator:hover { border-color: rgba(255,255,255,96); }
QCheckBox::indicator:checked { background: #7D9AFF; border: 1px solid #A9BAFF; }
QPlainTextEdit {
    background: rgba(14,14,16,235);
    border: 1px solid rgba(255,255,255,35);
    border-radius: 18px;
    padding: 14px;
    color: #D7DAE5;
    font-family: 'Cascadia Mono', Consolas, monospace;
    font-size: 12px;
}
QScrollBar:vertical { background: transparent; width: 10px; margin: 8px 2px 8px 2px; }
QScrollBar::handle:vertical { background: rgba(255,255,255,55); border-radius: 4px; min-height: 36px; }
QScrollBar::handle:vertical:hover { background: rgba(255,255,255,95); }
QScrollBar::add-line:vertical, QScrollBar::sub-line:vertical { height: 0px; }
"""


class AnimatedButton(QPushButton):
    """Glass button with animated hover brightness and press scale state."""

    def __init__(self, text: str, *, accent: bool = False, danger: bool = False) -> None:
        super().__init__(text)
        self._accent = accent
        self._danger = danger
        self._glow = 0.0
        self._scale = 1.0
        self.setCursor(Qt.PointingHandCursor)
        self.setMinimumHeight(46)
        self.setSizePolicy(QSizePolicy.Minimum, QSizePolicy.Fixed)
        self._glow_anim = QPropertyAnimation(self, b"glow", self)
        self._glow_anim.setDuration(180)
        self._glow_anim.setEasingCurve(QEasingCurve.OutCubic)
        self._scale_anim = QPropertyAnimation(self, b"scale", self)
        self._scale_anim.setDuration(150)
        self._scale_anim.setEasingCurve(QEasingCurve.OutCubic)
        self._apply_style()

    def glow(self) -> float:
        return self._glow

    def setGlow(self, value: float) -> None:
        self._glow = value
        self._apply_style()

    def scale(self) -> float:
        return self._scale

    def setScale(self, value: float) -> None:
        self._scale = value
        self.update()

    glow = Property(float, glow, setGlow)
    scale = Property(float, scale, setScale)

    def enterEvent(self, event):
        self._animate(self._glow_anim, self._glow, 1.0)
        self._animate(self._scale_anim, self._scale, 1.035)
        super().enterEvent(event)

    def leaveEvent(self, event):
        self._animate(self._glow_anim, self._glow, 0.0)
        self._animate(self._scale_anim, self._scale, 1.0)
        super().leaveEvent(event)

    def mousePressEvent(self, event):
        self._animate(self._scale_anim, self._scale, 0.975)
        super().mousePressEvent(event)

    def mouseReleaseEvent(self, event):
        self._animate(self._scale_anim, self._scale, 1.035 if self.underMouse() else 1.0)
        super().mouseReleaseEvent(event)

    def _animate(self, animation: QPropertyAnimation, start: float, end: float) -> None:
        animation.stop()
        animation.setStartValue(start)
        animation.setEndValue(end)
        animation.start()

    def _apply_style(self) -> None:
        if self._danger:
            base = 44 + int(self._glow * 38)
            border = 118 + int(self._glow * 70)
            color = f"rgba(255,72,84,{base})"
            border_color = f"rgba(255,98,110,{border})"
        elif self._accent:
            base = 92 + int(self._glow * 52)
            border = 150 + int(self._glow * 65)
            color = f"rgba(125,154,255,{base})"
            border_color = f"rgba(165,185,255,{border})"
        else:
            base = 24 + int(self._glow * 30)
            border = 55 + int(self._glow * 65)
            color = f"rgba(255,255,255,{base})"
            border_color = f"rgba(255,255,255,{border})"
        self.setStyleSheet(
            "QPushButton {"
            f"background:{color}; border:1px solid {border_color}; border-radius:18px;"
            "padding:0 22px; font-weight:600; font-size:14px;"
            "} QPushButton:disabled { color: rgba(240,242,248,90); background: rgba(255,255,255,12); }"
        )


class TitleIconButton(AnimatedButton):
    """Title-bar button with vector-drawn minimize/close icons."""

    def __init__(self, icon: str, *, danger: bool = False) -> None:
        super().__init__("", danger=danger)
        self.icon = icon
        self.setFixedSize(46, 38)

    def paintEvent(self, event):
        super().paintEvent(event)
        painter = QPainter(self)
        painter.setRenderHint(QPainter.Antialiasing)
        color = QColor(255, 92, 104) if self._danger else QColor(232, 234, 240)
        color.setAlpha(245 if self.underMouse() else 190)
        painter.setPen(QPen(color, 2.2, Qt.SolidLine, Qt.RoundCap, Qt.RoundJoin))
        center = self.rect().center()
        if self.icon == "minimize":
            painter.drawLine(center.x() - 8, center.y() + 2, center.x() + 8, center.y() + 2)
        else:
            painter.drawLine(center.x() - 7, center.y() - 7, center.x() + 7, center.y() + 7)
            painter.drawLine(center.x() + 7, center.y() - 7, center.x() - 7, center.y() + 7)


class Background(QWidget):
    """Code-drawn volumetric dark background."""

    def __init__(self) -> None:
        super().__init__()
        self.phase = 0.0
        self.noise = [(random.randrange(WIDTH), random.randrange(HEIGHT), random.randrange(14, 38)) for _ in range(260)]
        self.timer = QTimer(self)
        self.timer.setInterval(80)
        self.timer.timeout.connect(self._tick)
        self.timer.start()

    def _tick(self) -> None:
        self.phase += 0.012
        self.update()

    def paintEvent(self, _event) -> None:
        painter = QPainter(self)
        painter.setRenderHint(QPainter.Antialiasing)
        painter.fillRect(self.rect(), QColor("#0E0E10"))
        glow = QRadialGradient(QPointF(self.width() * 0.55, self.height() * 0.30), self.width() * 0.78)
        glow.setColorAt(0.0, QColor(36, 36, 42, 230))
        glow.setColorAt(0.58, QColor(18, 18, 20, 245))
        glow.setColorAt(1.0, QColor(8, 8, 10, 255))
        painter.fillRect(self.rect(), glow)
        painter.setPen(QPen(QColor(255, 255, 255, 9), 1))
        drift = int(math.sin(self.phase) * 8)
        for x in range(-self.height(), self.width(), 34):
            painter.drawLine(x + drift, self.height(), x + self.height() + drift, 0)
        for x, y, alpha in self.noise:
            painter.fillRect(x, y, 1, 1, QColor(255, 255, 255, alpha))
        vignette = QRadialGradient(QPointF(self.width() / 2, self.height() / 2), self.width() * 0.72)
        vignette.setColorAt(0.55, QColor(0, 0, 0, 0))
        vignette.setColorAt(1.0, QColor(0, 0, 0, 155))
        painter.fillRect(self.rect(), vignette)


class WaveProgress(QWidget):
    """Animated two-surface wave progress display."""

    def __init__(self) -> None:
        super().__init__()
        self._value = 0.0
        self.phase = 0.0
        self.status = "Preparing..."
        self.setMinimumHeight(250)
        self.timer = QTimer(self)
        self.timer.setInterval(33)
        self.timer.timeout.connect(self._tick)
        self.timer.start()

    def value(self) -> float:
        return self._value

    def setValue(self, value: float) -> None:
        self._value = max(0.0, min(100.0, value))
        self.update()

    value = Property(float, value, setValue)

    def set_progress(self, value: int, status: str) -> None:
        self.status = status
        self.animation = QPropertyAnimation(self, b"value", self)
        self.animation.setDuration(220)
        self.animation.setEasingCurve(QEasingCurve.OutCubic)
        self.animation.setStartValue(self._value)
        self.animation.setEndValue(float(value))
        self.animation.start()
        self.update()

    def _tick(self) -> None:
        self.phase += 0.045
        self.update()

    def _edge(self, left: float, right: float, baseline: float, offset: float) -> QPainterPath:
        path = QPainterPath(QPointF(left, baseline))
        x = left
        amp = 7 + math.sin(self.phase + offset) * 2
        while x < right:
            path.cubicTo(
                QPointF(x + 35, baseline + math.sin(self.phase + x * 0.018 + offset) * amp),
                QPointF(x + 70, baseline - math.sin(self.phase + x * 0.018 + offset) * amp),
                QPointF(x + 105, baseline),
            )
            x += 105
        return path

    def paintEvent(self, _event) -> None:
        painter = QPainter(self)
        painter.setRenderHint(QPainter.Antialiasing)
        bar = self.rect().adjusted(18, 14, -18, -62)
        clip = QPainterPath()
        clip.addRoundedRect(bar, 28, 28)
        painter.fillPath(clip, QColor(255, 255, 255, 16))
        painter.setPen(QPen(QColor(255, 255, 255, 42), 1))
        painter.drawPath(clip)
        split = bar.top() + bar.height() * (self._value / 100.0)
        gap = 6
        done_edge = self._edge(bar.left(), bar.right(), max(bar.top(), split - gap), 0.0)
        done = QPainterPath(QPointF(bar.left(), bar.top()))
        done.lineTo(bar.right(), bar.top())
        done.lineTo(bar.right(), split - gap)
        done.connectPath(done_edge.toReversed())
        done.closeSubpath()
        todo_edge = self._edge(bar.left(), bar.right(), min(bar.bottom(), split + gap), 1.8)
        todo = QPainterPath(todo_edge)
        todo.lineTo(bar.right(), bar.bottom())
        todo.lineTo(bar.left(), bar.bottom())
        todo.closeSubpath()
        done_gradient = QLinearGradient(bar.topLeft(), bar.bottomLeft())
        done_gradient.setColorAt(0.0, QColor(125, 154, 255, 122))
        done_gradient.setColorAt(1.0, QColor(80, 220, 190, 58))
        todo_gradient = QLinearGradient(bar.topLeft(), bar.bottomLeft())
        todo_gradient.setColorAt(0.0, QColor(255, 255, 255, 34))
        todo_gradient.setColorAt(1.0, QColor(255, 255, 255, 12))
        painter.fillPath(clip.intersected(done), done_gradient)
        painter.fillPath(clip.intersected(todo), todo_gradient)
        painter.setPen(QColor(TEXT))
        painter.setFont(QFont("Segoe UI", 26, QFont.Bold))
        painter.drawText(bar, Qt.AlignCenter, f"{int(self._value)}%")
        painter.setPen(QColor(MUTED))
        painter.setFont(QFont("Segoe UI", 11))
        painter.drawText(self.rect().adjusted(18, self.height() - 50, -18, -8), Qt.AlignHCenter | Qt.AlignVCenter, self.status)


class Toast(QLabel):
    """Frameless animated notification."""

    def __init__(self, parent: QWidget) -> None:
        super().__init__(parent)
        self.setAlignment(Qt.AlignCenter)
        self.setStyleSheet("background:rgba(125,154,255,58); border:1px solid rgba(165,185,255,125); border-radius:16px; padding:12px 18px;")
        self.effect = QGraphicsOpacityEffect(self)
        self.setGraphicsEffect(self.effect)
        self.hide()

    def show_message(self, text: str) -> None:
        self.setText(text)
        self.adjustSize()
        self.move((self.parent().width() - self.width()) // 2, 58)
        self.show()
        fade = QPropertyAnimation(self.effect, b"opacity", self)
        fade.setDuration(220)
        fade.setStartValue(0.0)
        fade.setEndValue(1.0)
        lift = QPropertyAnimation(self, b"pos", self)
        lift.setDuration(220)
        lift.setEasingCurve(QEasingCurve.OutCubic)
        lift.setStartValue(self.pos() + QPoint(0, -8))
        lift.setEndValue(self.pos())
        self.group = QParallelAnimationGroup(self)
        self.group.addAnimation(fade)
        self.group.addAnimation(lift)
        self.group.start()
        QTimer.singleShot(4300, self.hide)


class ScannerApp(QMainWindow):
    """Frameless SCAN application window."""

    def __init__(self) -> None:
        super().__init__()
        self._hide_windows_console()
        self.events: queue.Queue[tuple[str, object]] = queue.Queue()
        self.logs: list[str] = []
        self.running = False
        self.drag_offset: QPoint | None = None
        self.setWindowFlags(Qt.FramelessWindowHint | Qt.Window)
        self.resize(WIDTH, HEIGHT)
        self.setStyleSheet(APP_STYLE)
        self._build_ui()
        self.poll_timer = QTimer(self)
        self.poll_timer.setInterval(80)
        self.poll_timer.timeout.connect(self._poll_events)
        self.poll_timer.start()

    def _build_ui(self) -> None:
        root = Background()
        self.setCentralWidget(root)
        layout = QVBoxLayout(root)
        layout.setContentsMargins(28, 18, 28, 24)
        layout.setSpacing(18)
        title = QHBoxLayout()
        title_label = QLabel("SCAN")
        title_label.setStyleSheet("font-size:28px; font-weight:700; letter-spacing:2px; color:#F2F4FA;")
        title.addWidget(title_label)
        title.addStretch()
        self.minimize_button = TitleIconButton("minimize")
        self.close_button = TitleIconButton("close", danger=True)
        title.addWidget(self.minimize_button)
        title.addWidget(self.close_button)
        layout.addLayout(title)
        self.card = QFrame()
        self.card.setObjectName("mainCard")
        self.card.setStyleSheet(f"#mainCard {{ background:{PANEL}; border:1px solid rgba(255,255,255,32); border-radius:28px; }}")
        shadow = QGraphicsDropShadowEffect(self.card)
        shadow.setBlurRadius(40)
        shadow.setXOffset(0)
        shadow.setYOffset(18)
        shadow.setColor(QColor(0, 0, 0, 135))
        self.card.setGraphicsEffect(shadow)
        form = QVBoxLayout(self.card)
        form.setContentsMargins(34, 30, 34, 30)
        form.setSpacing(18)
        self.entries: dict[str, QLineEdit] = {}
        for key, placeholder in (("launcher", "Launcher path"), ("modpack", "Modpack path"), ("output", "Output path (normal mode only)")):
            row = QHBoxLayout()
            entry = QLineEdit()
            entry.setPlaceholderText(placeholder)
            entry.setMinimumHeight(60)
            browse = AnimatedButton("Browse")
            browse.clicked.connect(lambda _checked=False, item=key: self._choose_path(item))
            row.addWidget(entry, 1)
            row.addWidget(browse)
            form.addLayout(row)
            self.entries[key] = entry
        self.graph_check = QCheckBox("Generate mod dependency graph")
        self.external_console_check = QCheckBox("Open external live log console")
        form.addWidget(self.graph_check)
        form.addWidget(self.external_console_check)
        actions = QHBoxLayout()
        self.start_button = AnimatedButton("Start Analysis", accent=True)
        self.test_button = AnimatedButton("Test Mode")
        self.logs_button = AnimatedButton("Logs")
        actions.addWidget(self.start_button)
        actions.addWidget(self.test_button)
        actions.addStretch()
        actions.addWidget(self.logs_button)
        form.addLayout(actions)
        layout.addWidget(self.card)
        self.progress = WaveProgress()
        self.progress.hide()
        layout.addWidget(self.progress)
        self.toast = Toast(root)
        self.minimize_button.clicked.connect(self.showMinimized)
        self.close_button.clicked.connect(self.close)
        self.start_button.clicked.connect(lambda: self._start_scan(False))
        self.test_button.clicked.connect(lambda: self._start_scan(True))
        self.logs_button.clicked.connect(self._show_logs)

    def mousePressEvent(self, event) -> None:
        if event.button() == Qt.LeftButton and event.position().y() < 72:
            self.drag_offset = event.globalPosition().toPoint() - self.frameGeometry().topLeft()

    def mouseMoveEvent(self, event) -> None:
        if self.drag_offset is not None and event.buttons() & Qt.LeftButton:
            self.move(event.globalPosition().toPoint() - self.drag_offset)

    def mouseReleaseEvent(self, _event) -> None:
        self.drag_offset = None

    def _choose_path(self, key: str) -> None:
        selected = QFileDialog.getExistingDirectory(self, "Select folder")
        if selected:
            self.entries[key].setText(selected)

    def _entry_text(self, key: str) -> str:
        return self.entries[key].text().strip().strip('"')

    def _start_scan(self, test_mode: bool) -> None:
        if self.running:
            return
        request = ScanRequest(
            launcher_path=self._entry_text("launcher"),
            modpack_path=self._entry_text("modpack"),
            output_path=self._entry_text("output"),
            generate_graph=self.graph_check.isChecked(),
            test_mode=test_mode,
        )
        if not request.output_path and not test_mode:
            self.toast.show_message("Output path is required.")
            self._append_log("Output path is required.")
            return
        if test_mode:
            self.toast.show_message("Запущен тестовый режим. Файлы сохраняться не будут; документация лаунчера и сборки не будет создана.")
        if self.external_console_check.isChecked() and request.output_path and not test_mode:
            self._open_external_log_console(request.output_path)
        self.running = True
        self.start_button.setEnabled(False)
        self.test_button.setEnabled(False)
        self.progress.show()
        self.progress.set_progress(0, "Preparing...")
        threading.Thread(target=self._run_worker, args=(request,), daemon=True).start()

    def _run_worker(self, request: ScanRequest) -> None:
        try:
            run_scan(request, lambda percent, message: self.events.put(("progress", (percent, message))))
            self.events.put(("done", None))
        except Exception as error:
            self.events.put(("error", format_traceback(error)))

    def _poll_events(self) -> None:
        try:
            while True:
                event, payload = self.events.get_nowait()
                if event == "progress":
                    percent, message = payload  # type: ignore[misc]
                    self.progress.set_progress(int(percent), str(message))
                    self._append_log(f"{int(percent)}% {message}")
                elif event == "done":
                    self.running = False
                    self.close()
                elif event == "error":
                    self.running = False
                    self.start_button.setEnabled(True)
                    self.test_button.setEnabled(True)
                    self._append_log(str(payload))
                    self._show_logs()
        except queue.Empty:
            pass

    def _show_logs(self) -> None:
        dialog = QDialog(self)
        dialog.setWindowFlags(Qt.FramelessWindowHint | Qt.Dialog)
        dialog.resize(820, 520)
        dialog.setStyleSheet(APP_STYLE + "QDialog{background:#0E0E10; border:1px solid rgba(255,255,255,36); border-radius:20px;}")
        layout = QVBoxLayout(dialog)
        editor = QPlainTextEdit()
        editor.setReadOnly(True)
        editor.setPlainText("\n".join(self.logs))
        layout.addWidget(editor)
        close_button = AnimatedButton("Close")
        close_button.clicked.connect(dialog.accept)
        layout.addWidget(close_button)
        dialog.exec()

    def _append_log(self, text: str) -> None:
        self.logs.extend(str(text).splitlines())

    def _open_external_log_console(self, output_path: str) -> None:
        logs_dir = str(Path(output_path) / "logs")
        command = (
            "$logs = '" + logs_dir.replace("'", "''") + "'; "
            "Write-Host 'SCAN live logs:' $logs; "
            "while (!(Test-Path (Join-Path $logs 'scanner.md'))) { Start-Sleep -Milliseconds 250 }; "
            "Get-Content -Path (Join-Path $logs 'scanner.md'), (Join-Path $logs 'errors.md') -Wait"
        )
        try:
            subprocess.Popen(
                ["cmd.exe", "/k", "powershell", "-NoLogo", "-NoExit", "-Command", command],
                creationflags=getattr(subprocess, "CREATE_NEW_CONSOLE", 0),
            )
        except Exception as error:
            self._append_log(f"Cannot open external cmd: {error}")

    def _hide_windows_console(self) -> None:
        try:
            import ctypes

            window = ctypes.windll.kernel32.GetConsoleWindow()
            if window:
                ctypes.windll.user32.ShowWindow(window, 0)
        except Exception:
            pass


def main() -> None:
    application = QApplication(sys.argv)
    application.setApplicationName("SCAN")
    window = ScannerApp()
    window.show()
    sys.exit(application.exec())


if __name__ == "__main__":
    main()
