"""Modern PySide6 GUI for the unified scanner."""

from __future__ import annotations

import math
import queue
import random
import subprocess
import sys
import threading
from pathlib import Path

from PySide6.QtCore import QEasingCurve, QPoint, QPointF, QRectF, QParallelAnimationGroup, QPropertyAnimation, Qt, QTimer, Property
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
    QVBoxLayout,
    QWidget,
)

from runner import ScanRequest, format_traceback, run_scan


WIDTH = 1280
HEIGHT = 720


STYLE = """
* { color: #E8EAF0; font-family: 'Segoe UI', 'Inter', Arial, sans-serif; }
QLineEdit {
    background: rgba(30, 30, 34, 185); border: 1px solid rgba(255,255,255,42);
    border-radius: 18px; padding: 0 20px; font-size: 18px; selection-background-color: #536DFE;
}
QLineEdit:focus { border: 1px solid rgba(125, 154, 255, 165); background: rgba(36,36,42,215); }
QCheckBox { spacing: 12px; color: #B8BBC6; font-size: 15px; }
QCheckBox::indicator { width: 22px; height: 22px; border-radius: 7px; border: 1px solid rgba(255,255,255,55); background: rgba(255,255,255,18); }
QCheckBox::indicator:checked { background: #7D9AFF; border: 1px solid #A9BAFF; }
QPlainTextEdit {
    background: rgba(14,14,16,230); border: 1px solid rgba(255,255,255,35); border-radius: 18px;
    padding: 14px; color: #D7DAE5; font-family: 'Cascadia Mono', Consolas, monospace; font-size: 12px;
}
QScrollBar:vertical { background: transparent; width: 10px; margin: 8px 2px 8px 2px; }
QScrollBar::handle:vertical { background: rgba(255,255,255,55); border-radius: 4px; min-height: 36px; }
QScrollBar::handle:vertical:hover { background: rgba(255,255,255,95); }
QScrollBar::add-line:vertical, QScrollBar::sub-line:vertical { height: 0px; }
"""


class AnimatedButton(QPushButton):
    def __init__(self, text: str, accent: bool = False, danger: bool = False) -> None:
        super().__init__(text)
        self._scale = 1.0
        self._accent = accent
        self._danger = danger
        self.setCursor(Qt.PointingHandCursor)
        self.setMinimumHeight(46)
        self._anim = QPropertyAnimation(self, b"scale", self, duration=180, easingCurve=QEasingCurve.OutCubic)
        self._apply_style(False)

    def get_scale(self) -> float: return self._scale
    def set_scale(self, value: float) -> None:
        self._scale = value; self.updateGeometry(); self.update()
    scale = Property(float, get_scale, set_scale)

    def enterEvent(self, event):
        self._apply_style(True); self._animate(1.035); super().enterEvent(event)
    def leaveEvent(self, event):
        self._apply_style(False); self._animate(1.0); super().leaveEvent(event)
    def mousePressEvent(self, event):
        self._animate(0.975); super().mousePressEvent(event)
    def mouseReleaseEvent(self, event):
        self._animate(1.035 if self.underMouse() else 1.0); super().mouseReleaseEvent(event)
    def _animate(self, target: float) -> None:
        self._anim.stop(); self._anim.setStartValue(self._scale); self._anim.setEndValue(target); self._anim.start()
    def _apply_style(self, hover: bool) -> None:
        if self._danger:
            bg = "rgba(255, 72, 84, 70)" if hover else "rgba(255, 72, 84, 42)"; border = "rgba(255, 98, 110, 140)"
        elif self._accent:
            bg = "rgba(125,154,255,130)" if hover else "rgba(125,154,255,92)"; border = "rgba(165,185,255,170)"
        else:
            bg = "rgba(255,255,255,35)" if hover else "rgba(255,255,255,22)"; border = "rgba(255,255,255,55)"
        self.setStyleSheet(f"QPushButton {{ background:{bg}; border:1px solid {border}; border-radius:18px; padding:0 22px; font-weight:600; }}")



class WindowButton(AnimatedButton):
    """Frameless-window button with a painted glyph that is always visible."""

    def __init__(self, kind: str, danger: bool = False) -> None:
        super().__init__("", danger=danger)
        self.kind = kind
        self.setAccessibleName("Close" if kind == "close" else "Minimize")

    def paintEvent(self, event):
        super().paintEvent(event)
        p = QPainter(self)
        p.setRenderHint(QPainter.Antialiasing)
        p.setPen(QPen(QColor("#F4F6FC"), 2.4, Qt.SolidLine, Qt.RoundCap, Qt.RoundJoin))
        cx = self.width() / 2
        cy = self.height() / 2
        if self.kind == "close":
            size = 7.0
            p.drawLine(QPointF(cx - size, cy - size), QPointF(cx + size, cy + size))
            p.drawLine(QPointF(cx + size, cy - size), QPointF(cx - size, cy + size))
        else:
            p.drawLine(QPointF(cx - 8.0, cy + 4.0), QPointF(cx + 8.0, cy + 4.0))

class Background(QWidget):
    def __init__(self) -> None:
        super().__init__(); self.phase = 0.0; self.noise = [(random.randrange(WIDTH), random.randrange(HEIGHT), random.randrange(18, 42)) for _ in range(240)]
        self.timer = QTimer(self, interval=80, timeout=self._tick); self.timer.start()
    def _tick(self): self.phase += .015; self.update()
    def paintEvent(self, _event):
        p = QPainter(self); p.setRenderHint(QPainter.Antialiasing)
        p.fillRect(self.rect(), QColor("#0E0E10"))
        rg = QRadialGradient(QPointF(self.width()*0.54, self.height()*0.32), self.width()*0.75)
        rg.setColorAt(0, QColor(36,36,42,230)); rg.setColorAt(.55, QColor(18,18,20,245)); rg.setColorAt(1, QColor(8,8,10,255)); p.fillRect(self.rect(), rg)
        p.setPen(QPen(QColor(255,255,255,10), 1))
        for x in range(-self.height(), self.width(), 34): p.drawLine(x, self.height(), x+self.height(), 0)
        for x,y,a in self.noise: p.fillRect(x,y,1,1,QColor(255,255,255,a))
        vignette = QRadialGradient(QPointF(self.width()/2, self.height()/2), self.width()*.72)
        vignette.setColorAt(.55, QColor(0,0,0,0)); vignette.setColorAt(1, QColor(0,0,0,150)); p.fillRect(self.rect(), vignette)


class WaveProgress(QWidget):
    def __init__(self) -> None:
        super().__init__(); self._value = 0.0; self.phase = 0.0; self.label = "Preparing..."; self.setMinimumHeight(170)
        self.timer = QTimer(self, interval=33, timeout=self._tick); self.timer.start()
    def setProgress(self, value: int, label: str) -> None:
        self.label = label; self.anim = QPropertyAnimation(self, b"value", self, duration=260, easingCurve=QEasingCurve.OutCubic); self.anim.setStartValue(self._value); self.anim.setEndValue(float(value)); self.anim.start(); self.update()
    def getValue(self): return self._value
    def setValue(self, v): self._value = v; self.update()
    value = Property(float, getValue, setValue)
    def _tick(self): self.phase += .045; self.update()
    def _frontier_wave(self, rect, x_pos: float) -> QPainterPath:
        """Return a narrow vertical wave at the loaded/remaining boundary."""
        amp = 7 + math.sin(self.phase) * 1.6
        wave = QPainterPath(QPointF(x_pos, rect.top()))
        y = rect.top()
        while y < rect.bottom():
            wave.cubicTo(
                x_pos + math.sin(self.phase + y * .035) * amp, y + 18,
                x_pos - math.sin(self.phase + y * .035) * amp, y + 36,
                x_pos, y + 54,
            )
            y += 54
        wave.lineTo(min(rect.right(), x_pos + 14), rect.bottom())
        wave.lineTo(min(rect.right(), x_pos + 14), rect.top())
        wave.closeSubpath()
        return wave
    def paintEvent(self, _):
        p = QPainter(self); p.setRenderHint(QPainter.Antialiasing)
        r = self.rect().adjusted(16,16,-16,-58)
        path = QPainterPath(); path.addRoundedRect(r, 24, 24)
        # Rendering order is fixed: background, filled region, animated frontier wave, border.
        p.fillPath(path, QColor(255,255,255,16))
        progress_x = r.left() + r.width() * (max(0.0, min(100.0, self._value)) / 100.0)
        p.save()
        p.setClipPath(path)
        if progress_x > r.left():
            fill_rect = QRectF(r.left(), r.top(), progress_x - r.left(), r.height())
            fill = QPainterPath(); fill.addRect(fill_rect)
            grad = QLinearGradient(fill_rect.topLeft(), fill_rect.topRight()); grad.setColorAt(0,QColor(90,120,255,145)); grad.setColorAt(1,QColor(80,220,220,112))
            p.fillPath(fill, grad)
        wave_x = min(max(progress_x, r.left() + 2), r.right() - 14)
        wave_grad = QLinearGradient(QPointF(wave_x - 10, r.top()), QPointF(wave_x + 18, r.top()))
        wave_grad.setColorAt(0, QColor(80,220,220,20)); wave_grad.setColorAt(.48, QColor(100,245,255,220)); wave_grad.setColorAt(1, QColor(255,255,255,25))
        p.fillPath(self._frontier_wave(r, wave_x), wave_grad)
        p.restore()
        p.setPen(QPen(QColor(255,255,255,40),1)); p.drawPath(path)
        p.setPen(QColor("#F0F2F8")); p.setFont(QFont("Segoe UI", 24, QFont.Bold)); p.drawText(r, Qt.AlignCenter, f"{int(self._value)}%")
        label_rect = self.rect().adjusted(16, r.bottom()+12, -16, -8)
        p.setPen(QColor("#B9BDC9")); p.setFont(QFont("Segoe UI", 11)); p.drawText(label_rect, Qt.AlignHCenter|Qt.AlignTop|Qt.TextWordWrap, self.label)


class Toast(QLabel):
    def __init__(self, parent):
        super().__init__(parent); self.setAlignment(Qt.AlignCenter); self.setStyleSheet("background:rgba(125,154,255,72); border:none; border-radius:12px; padding:8px 14px;"); self.hide(); self.effect = QGraphicsOpacityEffect(self); self.setGraphicsEffect(self.effect)
    def show_message(self, text: str):
        self.setText(text); self.adjustSize(); self.move((self.parent().width()-self.width())//2, 58); self.show();
        self.fade = QPropertyAnimation(self.effect, b"opacity", self, duration=220); self.fade.setStartValue(0); self.fade.setEndValue(1)
        self.lift = QPropertyAnimation(self, b"pos", self, duration=220, easingCurve=QEasingCurve.OutCubic); self.lift.setStartValue(self.pos() + QPoint(0, -8)); self.lift.setEndValue(self.pos())
        self.group = QParallelAnimationGroup(self); self.group.addAnimation(self.fade); self.group.addAnimation(self.lift); self.group.start(); QTimer.singleShot(4200, self.hide)


class ScannerApp(QMainWindow):
    def __init__(self) -> None:
        super().__init__(); self._hide_windows_console(); self.queue: queue.Queue[tuple[str, object]] = queue.Queue(); self.running=False; self.drag_pos=None; self.logs=[]
        self.setWindowFlags(Qt.FramelessWindowHint | Qt.Window); self.resize(WIDTH, HEIGHT); self.setStyleSheet(STYLE)
        root = Background(); self.setCentralWidget(root); layout=QVBoxLayout(root); layout.setContentsMargins(28,18,28,24); layout.setSpacing(18)
        title=QHBoxLayout(); title.addWidget(QLabel("SCAN", styleSheet="font-size:28px; font-weight:700; letter-spacing:2px; color:#F2F4FA;")); title.addStretch(); self.min_btn=WindowButton("minimize"); self.close_btn=WindowButton("close", danger=True); self.min_btn.setFixedSize(46,38); self.close_btn.setFixedSize(46,38); title.addWidget(self.min_btn); title.addWidget(self.close_btn); layout.addLayout(title)
        card=QFrame(); card.setObjectName("card"); card.setStyleSheet("#card{background:rgba(23,23,27,150); border:1px solid rgba(255,255,255,30); border-radius:28px;}"); shadow=QGraphicsDropShadowEffect(card, blurRadius=38, xOffset=0, yOffset=18, color=QColor(0,0,0,130)); card.setGraphicsEffect(shadow); form=QVBoxLayout(card); form.setContentsMargins(34,30,34,30); form.setSpacing(18)
        self.entries={};
        for key, ph in [("launcher","Launcher path"),("modpack","Modpack path"),("output","Output path")]:
            row=QHBoxLayout(); e=QLineEdit(placeholderText=ph); e.setMinimumHeight(60); b=AnimatedButton("Browse"); b.clicked.connect(lambda _, k=key: self._choose_path(k)); row.addWidget(e,1); row.addWidget(b); form.addLayout(row); self.entries[key]=e
        self.graph=QCheckBox("Generate mod dependency graph"); self.migration_plan=QCheckBox("Create Migration Plan"); self.viewer_plan=QCheckBox("Generate Viewer Plan"); self.external=QCheckBox("Open external live log console"); form.addWidget(self.graph); form.addWidget(self.migration_plan); form.addWidget(self.viewer_plan); form.addWidget(self.external)
        actions=QHBoxLayout(); self.start=AnimatedButton("Start Analysis", accent=True); self.test=AnimatedButton("Test Mode"); self.logs_btn=AnimatedButton("Logs"); actions.addWidget(self.start); actions.addWidget(self.test); actions.addStretch(); actions.addWidget(self.logs_btn); form.addLayout(actions); layout.addWidget(card)
        self.progress=WaveProgress(); self.progress.hide(); layout.addWidget(self.progress); self.toast=Toast(root)
        self.min_btn.clicked.connect(self.showMinimized); self.close_btn.clicked.connect(self.close); self.start.clicked.connect(lambda: self._start_scan(False)); self.test.clicked.connect(lambda: self._start_scan(True)); self.logs_btn.clicked.connect(self._show_logs)
        self.poll=QTimer(self, interval=80, timeout=self._poll_queue); self.poll.start()
    def mousePressEvent(self,e):
        if e.button()==Qt.LeftButton and e.position().y()<70: self.drag_pos=e.globalPosition().toPoint()-self.frameGeometry().topLeft()
    def mouseMoveEvent(self,e):
        if self.drag_pos and e.buttons() & Qt.LeftButton: self.move(e.globalPosition().toPoint()-self.drag_pos)
    def mouseReleaseEvent(self,e): self.drag_pos=None
    def _choose_path(self,key):
        path=QFileDialog.getExistingDirectory(self,"Select folder");
        if path: self.entries[key].setText(path)
    def _start_scan(self,test_mode: bool):
        if self.running: return
        req=ScanRequest(launcher_path=self.entries["launcher"].text().strip().strip('"'), modpack_path=self.entries["modpack"].text().strip().strip('"'), output_path=self.entries["output"].text().strip().strip('"'), generate_graph=self.graph.isChecked(), create_migration_plan=self.migration_plan.isChecked(), generate_viewer_plan=self.viewer_plan.isChecked(), test_mode=test_mode)
        if not req.output_path and not test_mode:
            self._append_log("Output path is required."); self.toast.show_message("Output path is required"); return
        if test_mode: self.toast.show_message("Запущен тестовый режим. Документация лаунчера и сборки сохраняться не будет.")
        if self.external.isChecked() and req.output_path: self._open_external_log_console(req.output_path)
        self.running=True; self.progress.show(); self.progress.setProgress(0,"Preparing..."); self.start.setEnabled(False); self.test.setEnabled(False)
        threading.Thread(target=self._run_worker,args=(req,),daemon=True).start()
    def _run_worker(self,req):
        try: run_scan(req, lambda p,m: self.queue.put(("progress",(p,m)))); self.queue.put(("done",None))
        except Exception as err: self.queue.put(("error",format_traceback(err)))
    def _poll_queue(self):
        try:
            while True:
                ev,payload=self.queue.get_nowait()
                if ev=="progress": p,m=payload; self.progress.setProgress(int(p),str(m)); self._append_log(f"{int(p)}% {m}")
                elif ev=="done": self.close()
                elif ev=="error": self.running=False; self.start.setEnabled(True); self.test.setEnabled(True); self._append_log(str(payload)); self._show_logs()
        except queue.Empty: pass
    def _show_logs(self):
        d=QDialog(self); d.setWindowTitle("SCAN Logs"); d.resize(820,520); d.setStyleSheet(STYLE+"QDialog{background:#0E0E10;}"); lay=QVBoxLayout(d); edit=QPlainTextEdit(readOnly=True); edit.setPlainText("\n".join(self.logs)); lay.addWidget(edit); d.exec()
    def _append_log(self,text): self.logs.extend(str(text).splitlines())
    def _open_external_log_console(self, output_path: str) -> None:
        logs_dir = str(Path(output_path) / "logs")
        command = "$logs = '" + logs_dir.replace("'", "''") + "'; Write-Host 'SCAN live logs:' $logs; while (!(Test-Path (Join-Path $logs 'scanner.md'))) { Start-Sleep -Milliseconds 250 }; Get-Content -Path (Join-Path $logs 'scanner.md'), (Join-Path $logs 'errors.md') -Wait"
        try: subprocess.Popen(["cmd.exe","/k","powershell","-NoLogo","-NoExit","-Command",command], creationflags=getattr(subprocess,"CREATE_NEW_CONSOLE",0))
        except Exception as error: self._append_log(f"Cannot open external cmd: {error}")
    def _hide_windows_console(self) -> None:
        try:
            import ctypes; window=ctypes.windll.kernel32.GetConsoleWindow();
            if window: ctypes.windll.user32.ShowWindow(window,0)
        except Exception: pass


def main() -> None:
    app = QApplication(sys.argv); app.setApplicationName("SCAN"); window=ScannerApp(); window.show(); sys.exit(app.exec())

if __name__ == "__main__": main()