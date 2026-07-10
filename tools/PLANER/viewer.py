"""PySide6 custom viewer for UltraTech PLANER JSON files."""
from __future__ import annotations
import json, sys
from pathlib import Path
from PySide6.QtCore import QPoint, QPointF, QRectF, Qt, QPropertyAnimation, QEasingCurve
from PySide6.QtGui import QAction, QColor, QPainter, QPainterPath, QPen
from PySide6.QtWidgets import QApplication, QFileDialog, QGraphicsDropShadowEffect, QHBoxLayout, QLabel, QMainWindow, QMenu, QPushButton, QTabBar, QVBoxLayout, QWidget

ROOT=Path(__file__).resolve().parents[2]
PLAN_DIR=ROOT/"docs"/"generated"/"modpack"/"PLANER"
STYLE="""*{font-family:'Segoe UI';color:#EEF1F7} QPushButton{background:#2B303A;border:1px solid #485164;border-radius:10px;padding:8px 12px} QMenu{background:#232832;border:1px solid #596173;border-radius:12px;padding:8px} QMenu::item{padding:8px 22px;border-radius:8px} QMenu::item:selected{background:#3B82F6} QTabBar::tab{background:#252A34;border-radius:12px;padding:9px 28px;margin:2px} QTabBar::tab:selected{background:#3A4252}"""

class Canvas(QWidget):
    """Animated pannable/zoomable canvas that paints the custom graph format."""
    def __init__(self): super().__init__(); self.doc={"nodes":[],"edges":[]}; self.zoom=1.0; self.offset=QPointF(40,40); self.pan=None; self.moving=None; self.selected=None; self.setMouseTracking(True)
    def load(self,path:Path): self.doc=json.loads(path.read_text(encoding='utf-8')); self.update()
    def paintEvent(self,_):
        p=QPainter(self); p.setRenderHint(QPainter.Antialiasing); p.fillRect(self.rect(),QColor('#D7D9DE'))
        p.setPen(QPen(QColor('#C4C8D0'),1)); step=max(20,int(32*self.zoom)); ox=int(self.offset.x())%step; oy=int(self.offset.y())%step
        for x in range(ox,self.width(),step): p.drawLine(x,0,x,self.height())
        for y in range(oy,self.height(),step): p.drawLine(0,y,self.width(),y)
        p.translate(self.offset); p.scale(self.zoom,self.zoom)
        nodes={n['id']:n for n in self.doc.get('nodes',[])}
        p.setPen(QPen(QColor('#6C768A'),2))
        for e in self.doc.get('edges',[]):
            a=nodes.get(e.get('from')); b=nodes.get(e.get('to'))
            if a and b:
                ap=a['position']; bp=b['position']; p.drawLine(QPointF(ap['x']+110,ap['y']+38),QPointF(bp['x']+110,bp['y']+38))
        for n in self.doc.get('nodes',[]): self._draw_node(p,n)
    def _draw_node(self,p,n):
        pos=n['position']; size=n.get('size',{'w':220,'h':76}); r=QRectF(pos['x'],pos['y'],size['w'],size['h']); path=QPainterPath(); path.addRoundedRect(r,18,18)
        p.fillPath(path,QColor(n.get('colors',{}).get('fill','#20242C'))); p.setPen(QPen(QColor('#7D9AFF' if n['id']==self.selected else n.get('colors',{}).get('accent','#AAAAAA')),3)); p.drawPath(path); p.drawText(r,Qt.AlignCenter,n.get('label',n['id']))
    def wheelEvent(self,e):
        target=max(.2,min(3.5,self.zoom*(1.15 if e.angleDelta().y()>0 else .87))); self.zoom=target; self.update()
    def mousePressEvent(self,e):
        if e.button()==Qt.MiddleButton: self.pan=e.position()
        elif e.button()==Qt.RightButton:
            node=self._hit(e.position()); menu=QMenu(self); act=menu.addAction('Move')
            if node and menu.exec(e.globalPosition().toPoint())==act: self.moving=node['id']; self.selected=node['id']
        elif e.button()==Qt.LeftButton:
            hit=self._hit(e.position()); self.selected=hit['id'] if hit else None; self.update()
    def mouseMoveEvent(self,e):
        if self.pan is not None: self.offset+=e.position()-self.pan; self.pan=e.position(); self.update()
        if self.moving:
            n=next(x for x in self.doc['nodes'] if x['id']==self.moving); world=(e.position()-self.offset)/self.zoom; n['position']={'x':world.x()-110,'y':world.y()-38}; self.update()
    def mouseReleaseEvent(self,e):
        if e.button()==Qt.MiddleButton: self.pan=None
    def _hit(self,pos):
        world=(pos-self.offset)/self.zoom
        for n in reversed(self.doc.get('nodes',[])):
            p=n['position']; s=n.get('size',{'w':220,'h':76})
            if QRectF(p['x'],p['y'],s['w'],s['h']).contains(world): return n
        return None

class Viewer(QMainWindow):
    """Frameless dark window with browser-like tabs and Photoshop-style file menu."""
    def __init__(self): super().__init__(); self.setWindowFlags(Qt.FramelessWindowHint|Qt.Window); self.resize(1280,760); self.setStyleSheet(STYLE); root=QWidget(); root.setStyleSheet('background:#151922;border-radius:18px'); self.setCentralWidget(root); lay=QVBoxLayout(root); bar=QHBoxLayout(); self.file=QPushButton('[ File ]'); self.tabs=QTabBar(movable=True,tabsClosable=True); self.tabs.tabCloseRequested.connect(self._close_tab); bar.addWidget(self.file); bar.addWidget(self.tabs); bar.addStretch(); [bar.addWidget(QPushButton(x)) for x in ('—','□','×')]; lay.addLayout(bar); self.canvas=Canvas(); lay.addWidget(self.canvas,1); done=QPushButton('✔ Done',self); done.clicked.connect(lambda: setattr(self.canvas,'moving',None)); done.move(1160,700); done.show(); self.file.clicked.connect(self._file_menu); self._open_default()
    def _file_menu(self):
        m=QMenu(self); a=m.addAction('Open Scheme');
        if m.exec(self.file.mapToGlobal(QPoint(0,self.file.height())))==a: self._open()
    def _open(self):
        path,_=QFileDialog.getOpenFileName(self,'Open Scheme',str(PLAN_DIR),'JSON (*.json)')
        if path: self._load(Path(path))
    def _open_default(self):
        p=PLAN_DIR/'viewer_plan.json'
        if p.exists(): self._load(p)
    def _load(self,p): self.tabs.addTab(p.stem); self.canvas.load(p)
    def _close_tab(self,i): self.tabs.removeTab(i)

if __name__=='__main__': app=QApplication(sys.argv); v=Viewer(); v.show(); sys.exit(app.exec())
