"""PySide6 custom viewer for UltraTech PLANER JSON files."""
from __future__ import annotations

import json
import sys
from pathlib import Path

from PySide6.QtCore import QEasingCurve, QPoint, QPointF, QRectF, Qt, QPropertyAnimation, Property
from PySide6.QtGui import QColor, QPainter, QPainterPath, QPen
from PySide6.QtWidgets import QApplication, QFileDialog, QHBoxLayout, QMainWindow, QMenu, QPushButton, QTabBar, QVBoxLayout, QWidget

ROOT = Path(__file__).resolve().parents[2]
PLAN_DIR = ROOT / "docs" / "generated" / "modpack" / "PLANER"
STYLE = """
* { font-family:'Segoe UI'; color:#EEF1F7; }
QMenu { background:#232832; border:1px solid #596173; border-radius:12px; padding:8px; }
QMenu::item { padding:8px 22px; border-radius:8px; }
QMenu::item:selected { background:#3B82F6; }
QTabBar::tab { background:#252A34; border-radius:12px; padding:9px 24px 9px 18px; margin:2px; }
QTabBar::tab:selected { background:#3A4252; }
QTabBar::close-button { margin-right:6px; subcontrol-position:right; }
"""

class AnimatedButton(QPushButton):
    """Small rounded button using the same hover/press animation style as SCAN."""
    def __init__(self, text: str, danger: bool = False) -> None:
        super().__init__(text); self._scale = 1.0; self._danger = danger; self.setCursor(Qt.PointingHandCursor); self.setMinimumHeight(34)
        self._anim = QPropertyAnimation(self, b"scale", self, duration=150, easingCurve=QEasingCurve.OutCubic); self._apply_style(False)
    def get_scale(self): return self._scale
    def set_scale(self, value): self._scale = value; self.update()
    scale = Property(float, get_scale, set_scale)
    def enterEvent(self, event): self._apply_style(True); self._animate(1.035); super().enterEvent(event)
    def leaveEvent(self, event): self._apply_style(False); self._animate(1.0); super().leaveEvent(event)
    def mousePressEvent(self, event): self._animate(.96); super().mousePressEvent(event)
    def mouseReleaseEvent(self, event): self._animate(1.035 if self.underMouse() else 1.0); super().mouseReleaseEvent(event)
    def _animate(self, target): self._anim.stop(); self._anim.setStartValue(self._scale); self._anim.setEndValue(target); self._anim.start()
    def _apply_style(self, hover):
        bg = "rgba(255,72,84,72)" if self._danger and hover else "rgba(255,72,84,44)" if self._danger else "rgba(255,255,255,42)" if hover else "rgba(255,255,255,24)"
        border = "rgba(255,98,110,145)" if self._danger else "rgba(255,255,255,70)"
        self.setStyleSheet(f"QPushButton{{background:{bg};border:1px solid {border};border-radius:12px;padding:6px 12px;font-weight:600;}}")

class Canvas(QWidget):
    """Pannable/zoomable canvas that keeps node movement separate from window dragging."""
    def __init__(self):
        super().__init__(); self.doc={"nodes":[],"edges":[]}; self.zoom=1.0; self.offset=QPointF(40,40); self.pan=None; self.moving=None; self.selected=None; self.setMouseTracking(True)
    def load(self, path: Path): self.doc=json.loads(path.read_text(encoding="utf-8")); self.update()
    def finish_move(self): self.moving=None; self.update()
    def paintEvent(self, _):
        p=QPainter(self); p.setRenderHint(QPainter.Antialiasing); p.fillRect(self.rect(), QColor(self.doc.get("colors",{}).get("background", "#D7D9DE")))
        p.setPen(QPen(QColor(self.doc.get("colors",{}).get("grid", "#C4C8D0")), 1)); step=max(20, int(32*self.zoom)); ox=int(self.offset.x())%step; oy=int(self.offset.y())%step
        for x in range(ox, self.width(), step): p.drawLine(x, 0, x, self.height())
        for y in range(oy, self.height(), step): p.drawLine(0, y, self.width(), y)
        p.translate(self.offset); p.scale(self.zoom, self.zoom); nodes={n["id"]: n for n in self.doc.get("nodes", [])}
        for e in self.doc.get("edges", []):
            a=nodes.get(e.get("from")); b=nodes.get(e.get("to"))
            if a and b: self._draw_edge(p, a, b, e)
        for n in self.doc.get("nodes", []): self._draw_node(p, n)
    def _draw_edge(self, p, a, b, e):
        ap=a["position"]; bp=b["position"]; aw=a.get("size",{}).get("w",220); ah=a.get("size",{}).get("h",76); bw=b.get("size",{}).get("w",220); bh=b.get("size",{}).get("h",76)
        start=QPointF(ap["x"]+aw/2, ap["y"]+ah/2); end=QPointF(bp["x"]+bw/2, bp["y"]+bh/2); p.setPen(QPen(QColor(e.get("color", "#6C768A")), 2, Qt.SolidLine, Qt.RoundCap)); p.drawLine(start, end)
        dx=end.x()-start.x(); dy=end.y()-start.y(); length=max((dx*dx+dy*dy)**0.5, 1); ux,uy=dx/length,dy/length; tip=end-QPointF(ux*bw*.48, uy*bh*.48); left=QPointF(tip.x()-ux*12-uy*6, tip.y()-uy*12+ux*6); right=QPointF(tip.x()-ux*12+uy*6, tip.y()-uy*12-ux*6); p.drawLine(tip,left); p.drawLine(tip,right)
    def _draw_node(self, p, n):
        pos=n["position"]; size=n.get("size", {"w":220,"h":76}); r=QRectF(pos["x"], pos["y"], size["w"], size["h"]); path=QPainterPath(); path.addRoundedRect(r, 18, 18)
        p.fillPath(path, QColor(n.get("colors",{}).get("fill", "#20242C"))); p.setPen(QPen(QColor("#7D9AFF" if n["id"]==self.selected else n.get("colors",{}).get("accent", "#AAAAAA")), 3)); p.drawPath(path); p.drawText(r, Qt.AlignCenter, n.get("label", n["id"]))
    def wheelEvent(self, e): self.zoom=max(.2, min(3.5, self.zoom*(1.15 if e.angleDelta().y()>0 else .87))); self.update()
    def mousePressEvent(self, e):
        if e.button()==Qt.MiddleButton: self.pan=e.position(); return
        if e.button()==Qt.RightButton:
            node=self._hit(e.position()); menu=QMenu(self); act=menu.addAction("Move")
            if node and menu.exec(e.globalPosition().toPoint())==act: self.moving=node["id"]; self.selected=node["id"]; self.update()
            return
        if e.button()==Qt.LeftButton:
            hit=self._hit(e.position())
            if hit: self.selected=hit["id"]
            self.update()
    def mouseMoveEvent(self, e):
        if self.pan is not None: self.offset += e.position()-self.pan; self.pan=e.position(); self.update()
        if self.moving: self._move_node_to(e.position())
    def mouseReleaseEvent(self, e):
        if e.button()==Qt.MiddleButton: self.pan=None
    def _move_node_to(self, pos):
        for n in self.doc.get("nodes", []):
            if n["id"] == self.moving:
                world=(pos-self.offset)/self.zoom; size=n.get("size", {"w":220,"h":76}); n["position"]={"x":world.x()-size["w"]/2, "y":world.y()-size["h"]/2}; self.update(); break
    def _hit(self, pos):
        world=(pos-self.offset)/self.zoom
        for n in reversed(self.doc.get("nodes", [])):
            p=n["position"]; s=n.get("size", {"w":220,"h":76})
            if QRectF(p["x"], p["y"], s["w"], s["h"]).contains(world): return n
        return None

class Viewer(QMainWindow):
    """Frameless rectangular window with draggable title bar and working controls."""
    def __init__(self):
        super().__init__(); self.drag_pos=None; self.setWindowFlags(Qt.FramelessWindowHint|Qt.Window); self.resize(1280,760); self.setStyleSheet(STYLE)
        root=QWidget(); root.setStyleSheet("background:#151922;"); self.setCentralWidget(root); lay=QVBoxLayout(root); lay.setContentsMargins(10,8,10,10)
        self.title_bar=QWidget(objectName="titleBar"); bar=QHBoxLayout(self.title_bar); bar.setContentsMargins(0,0,0,0)
        self.file=AnimatedButton("[ File ]"); self.tabs=QTabBar(movable=True, tabsClosable=True); self.tabs.tabCloseRequested.connect(self._close_tab)
        self.min_btn=AnimatedButton("—"); self.max_btn=AnimatedButton("□"); self.close_btn=AnimatedButton("×", danger=True)
        self.min_btn.clicked.connect(self.showMinimized); self.max_btn.clicked.connect(self._toggle_max_restore); self.close_btn.clicked.connect(self.close)
        bar.addWidget(self.file); bar.addWidget(self.tabs); bar.addStretch(); bar.addWidget(self.min_btn); bar.addWidget(self.max_btn); bar.addWidget(self.close_btn); lay.addWidget(self.title_bar)
        self.title_bar.mousePressEvent = self._title_mouse_press
        self.title_bar.mouseMoveEvent = self._title_mouse_move
        self.title_bar.mouseReleaseEvent = self._title_mouse_release
        self.canvas=Canvas(); lay.addWidget(self.canvas, 1); self.done=AnimatedButton("✔ Done"); self.done.setParent(self); self.done.clicked.connect(self.canvas.finish_move); self.done.resize(96,38); self.file.clicked.connect(self._file_menu); self._open_default()
    def resizeEvent(self, e): self.done.move(self.width()-112, self.height()-52); super().resizeEvent(e)
    def _title_mouse_press(self, e):
        if e.button()==Qt.LeftButton and not self.tabs.geometry().contains(e.position().toPoint()):
            self.drag_pos=e.globalPosition().toPoint()-self.frameGeometry().topLeft()
    def _title_mouse_move(self, e):
        if self.drag_pos and e.buttons() & Qt.LeftButton and not self.isMaximized(): self.move(e.globalPosition().toPoint()-self.drag_pos)
    def _title_mouse_release(self, e): self.drag_pos=None
    def _toggle_max_restore(self): self.showNormal() if self.isMaximized() else self.showMaximized()
    def _file_menu(self):
        m=QMenu(self); a=m.addAction("Open Scheme")
        if m.exec(self.file.mapToGlobal(QPoint(0, self.file.height())))==a: self._open()
    def _open(self):
        path,_=QFileDialog.getOpenFileName(self, "Open Scheme", str(PLAN_DIR), "JSON (*.json)")
        if path: self._load(Path(path))
    def _open_default(self):
        for name in ("viewer_plan.json", "migration_plan.json"):
            p=PLAN_DIR/name
            if p.exists(): self._load(p); break
    def _load(self, p): self.tabs.addTab(p.stem); self.tabs.setCurrentIndex(self.tabs.count()-1); self.canvas.load(p)
    def _close_tab(self, i): self.tabs.removeTab(i)

if __name__ == "__main__": app=QApplication(sys.argv); v=Viewer(); v.show(); sys.exit(app.exec())
