"""Modern PySide6 GUI for the unified scanner."""

from __future__ import annotations

import math
import queue
import random
import subprocess
import sys
import threading
from pathlib import Path

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



class TitleIconButton(AnimatedButton):
    """Title-bar button with vector-drawn icons so glyph fonts cannot disappear."""

    def __init__(self, icon: str, danger: bool = False) -> None:
        super().__init__("", danger=danger)
        self.icon = icon

    def paintEvent(self, event):
        super().paintEvent(event)
        painter = QPainter(self)
        painter.setRenderHint(QPainter.Antialiasing)
        color = QColor(255, 92, 104) if self._danger else QColor(232, 234, 240)
        color.setAlpha(245 if self.underMouse() else 190)
        pen = QPen(color, 2.2, Qt.SolidLine, Qt.RoundCap, Qt.RoundJoin)
        painter.setPen(pen)
        center = self.rect().center()
        if self.icon == "minimize":
            painter.drawLine(center.x() - 8, center.y() + 2, center.x() + 8, center.y() + 2)
        else:
            painter.drawLine(center.x() - 7, center.y() - 7, center.x() + 7, center.y() + 7)
            painter.drawLine(center.x() + 7, center.y() - 7, center.x() - 7, center.y() + 7)
=======



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

        super().__init__(); self._value = 0.0; self.phase = 0.0; self.label = "Preparing..."; self.setMinimumHeight(250)
        self.timer = QTimer(self, interval=33, timeout=self._tick); self.timer.start()
    def setProgress(self, value: int, label: str) -> None:
        self.label = label; self.anim = QPropertyAnimation(self, b"value", self, duration=220, easingCurve=QEasingCurve.OutCubic); self.anim.setStartValue(self._value); self.anim.setEndValue(float(value)); self.anim.start(); self.update()
=======
        super().__init__(); self._value = 0.0; self.phase = 0.0; self.label = "Preparing..."; self.setMinimumHeight(210)
        self.timer = QTimer(self, interval=33, timeout=self._tick); self.timer.start()
    def setProgress(self, value: int, label: str) -> None:
        self.label = label; self.anim = QPropertyAnimation(self, b"value", self, duration=220, easingCurve=QEasingCurve.OutCubic); self.anim.setStartValue(self._value); self.anim.setEndValue(float(value)); self.anim.start()

    def getValue(self): return self._value
    def setValue(self, v): self._value = v; self.update()
    value = Property(float, getValue, setValue)
    def _tick(self): self.phase += .045; self.update()

    def _wave_edge(self, left: float, right: float, baseline: float, offset: float) -> QPainterPath:
        wave = QPainterPath(QPointF(left, baseline)); x = left; amp = 7 + math.sin(self.phase + offset) * 2
        while x < right:
            wave.cubicTo(x + 35, baseline + math.sin(self.phase + x * .018 + offset) * amp, x + 70, baseline - math.sin(self.phase + x * .018 + offset) * amp, x + 105, baseline)
            x += 105
        return wave
    def paintEvent(self, _):
        p = QPainter(self); p.setRenderHint(QPainter.Antialiasing); bar = self.rect().adjusted(16,16,-16,-58)
        clip = QPainterPath(); clip.addRoundedRect(bar, 28, 28); p.fillPath(clip, QColor(255,255,255,16)); p.setPen(QPen(QColor(255,255,255,40),1)); p.drawPath(clip)
        split = bar.top() + bar.height() * (self._value/100.0); gap = 6
        done_edge = self._wave_edge(bar.left(), bar.right(), max(bar.top(), split - gap), 0.0)
        done = QPainterPath(QPointF(bar.left(), bar.top())); done.lineTo(bar.right(), bar.top()); done.lineTo(bar.right(), split - gap); done.connectPath(done_edge.toReversed()); done.closeSubpath()
        todo_edge = self._wave_edge(bar.left(), bar.right(), min(bar.bottom(), split + gap), 1.8)
        todo = QPainterPath(todo_edge); todo.lineTo(bar.right(), bar.bottom()); todo.lineTo(bar.left(), bar.bottom()); todo.closeSubpath()
        done_grad = QLinearGradient(bar.topLeft(), bar.bottomLeft()); done_grad.setColorAt(0,QColor(125,154,255,120)); done_grad.setColorAt(1,QColor(80,220,190,58))
        todo_grad = QLinearGradient(bar.topLeft(), bar.bottomLeft()); todo_grad.setColorAt(0,QColor(255,255,255,34)); todo_grad.setColorAt(1,QColor(255,255,255,12))
        p.fillPath(clip.intersected(done), done_grad); p.fillPath(clip.intersected(todo), todo_grad)
        p.setPen(QColor("#F0F2F8")); p.setFont(QFont("Segoe UI", 26, QFont.Bold)); p.drawText(bar, Qt.AlignCenter, f"{int(self._value)}%")
        text_rect = self.rect().adjusted(16, self.height()-48, -16, -8); p.setPen(QColor("#B9BDC9")); p.setFont(QFont("Segoe UI", 11)); p.drawText(text_rect, Qt.AlignHCenter|Qt.AlignVCenter, self.label)
=======
    def paintEvent(self, _):
        p = QPainter(self); p.setRenderHint(QPainter.Antialiasing); r = self.rect().adjusted(16,16,-16,-16)
        path = QPainterPath(); path.addRoundedRect(r, 28, 28); p.fillPath(path, QColor(255,255,255,16)); p.setPen(QPen(QColor(255,255,255,40),1)); p.drawPath(path)
        split = r.top() + r.height() * (self._value/100.0)
        for top, bottom, c1, c2, offset in [(r.top(), split-5, QColor(125,154,255,105), QColor(80,220,190,55),0), (split+5, r.bottom(), QColor(255,255,255,30), QColor(255,255,255,12),1.7)]:
            if bottom <= top: continue
            wave = QPainterPath(QPointF(r.left(), top)); wave.lineTo(r.left(), bottom)
            x = r.left(); amp = 8 + math.sin(self.phase+offset)*2
            wave.moveTo(r.left(), bottom)
            while x < r.right():
                wave.cubicTo(x+35, bottom+math.sin(self.phase+x*.018+offset)*amp, x+70, bottom-math.sin(self.phase+x*.018+offset)*amp, x+105, bottom)
                x += 105
            wave.lineTo(r.right(), top); wave.lineTo(r.left(), top); wave.closeSubpath()
            grad = QLinearGradient(r.topLeft(), r.bottomLeft()); grad.setColorAt(0,c1); grad.setColorAt(1,c2); p.fillPath(path.intersected(wave), grad)
        p.setPen(QColor("#F0F2F8")); p.setFont(QFont("Segoe UI", 26, QFont.Bold)); p.drawText(r, Qt.AlignCenter, f"{int(self._value)}%")
        p.setPen(QColor("#B9BDC9")); p.setFont(QFont("Segoe UI", 11)); p.drawText(r.adjusted(0,88,0,0), Qt.AlignHCenter|Qt.AlignTop, self.label)



class Toast(QLabel):
    def __init__(self, parent):
        super().__init__(parent); self.setAlignment(Qt.AlignCenter); self.setStyleSheet("background:rgba(125,154,255,54); border:1px solid rgba(165,185,255,125); border-radius:16px; padding:12px 18px;"); self.hide(); self.effect = QGraphicsOpacityEffect(self); self.setGraphicsEffect(self.effect)
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

        title=QHBoxLayout(); title.addWidget(QLabel("SCAN", styleSheet="font-size:28px; font-weight:700; letter-spacing:2px; color:#F2F4FA;")); title.addStretch(); self.min_btn=TitleIconButton("minimize"); self.close_btn=TitleIconButton("close", danger=True); self.min_btn.setFixedSize(46,38); self.close_btn.setFixedSize(46,38); title.addWidget(self.min_btn); title.addWidget(self.close_btn); layout.addLayout(title)
        card=QFrame(); card.setObjectName("card"); card.setStyleSheet("#card{background:rgba(23,23,27,150); border:1px solid rgba(255,255,255,30); border-radius:28px;}"); shadow=QGraphicsDropShadowEffect(card, blurRadius=38, xOffset=0, yOffset=18, color=QColor(0,0,0,130)); card.setGraphicsEffect(shadow); form=QVBoxLayout(card); form.setContentsMargins(34,30,34,30); form.setSpacing(18)
        self.entries={};
        for key, ph in [("launcher","Launcher path"),("modpack","Modpack path"),("output","Output path (normal mode only)")]:
=======
        title=QHBoxLayout(); title.addWidget(QLabel("SCAN", styleSheet="font-size:28px; font-weight:700; letter-spacing:2px; color:#F2F4FA;")); title.addStretch(); self.min_btn=AnimatedButton("—"); self.close_btn=AnimatedButton("✕", danger=True); self.min_btn.setFixedSize(46,38); self.close_btn.setFixedSize(46,38); title.addWidget(self.min_btn); title.addWidget(self.close_btn); layout.addLayout(title)
        card=QFrame(); card.setObjectName("card"); card.setStyleSheet("#card{background:rgba(23,23,27,150); border:1px solid rgba(255,255,255,30); border-radius:28px;}"); shadow=QGraphicsDropShadowEffect(card, blurRadius=38, xOffset=0, yOffset=18, color=QColor(0,0,0,130)); card.setGraphicsEffect(shadow); form=QVBoxLayout(card); form.setContentsMargins(34,30,34,30); form.setSpacing(18)
        self.entries={};
        for key, ph in [("launcher","Launcher path"),("modpack","Modpack path"),("output","Output path")]:

            row=QHBoxLayout(); e=QLineEdit(placeholderText=ph); e.setMinimumHeight(60); b=AnimatedButton("Browse"); b.clicked.connect(lambda _, k=key: self._choose_path(k)); row.addWidget(e,1); row.addWidget(b); form.addLayout(row); self.entries[key]=e
        self.graph=QCheckBox("Generate mod dependency graph"); self.external=QCheckBox("Open external live log console"); form.addWidget(self.graph); form.addWidget(self.external)
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
        req=ScanRequest(self.entries["launcher"].text().strip().strip('"'), self.entries["modpack"].text().strip().strip('"'), self.entries["output"].text().strip().strip('"'), self.graph.isChecked(), test_mode)

        if not req.output_path and not test_mode: self.toast.show_message("Output path is required."); self._append_log("Output path is required."); return
        if test_mode: self.toast.show_message("Запущен тестовый режим. Файлы сохраняться не будут; документация лаунчера и сборки не будет создана.")
        if self.external.isChecked() and req.output_path and not test_mode: self._open_external_log_console(req.output_path)
=======
        if not req.output_path: self._append_log("Output path is required."); self._show_logs(); return
        if test_mode: self.toast.show_message("Запущен тестовый режим. Документация лаунчера и сборки сохраняться не будет.")
        if self.external.isChecked(): self._open_external_log_console(req.output_path)

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
