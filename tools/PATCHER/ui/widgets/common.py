"""Reusable dark PySide6 widgets: cards, title bar, toast, drop area and mod cards."""
from __future__ import annotations
from dataclasses import dataclass
from pathlib import Path
from PySide6.QtCore import Qt, Signal, QPoint, QPropertyAnimation, QEasingCurve
from PySide6.QtGui import QDragEnterEvent, QDropEvent
from PySide6.QtWidgets import QWidget, QFrame, QLabel, QPushButton, QVBoxLayout, QHBoxLayout, QFileDialog, QProgressBar, QTextEdit
from tools.PATCHER.resources.design_tokens import PALETTE, DIMENSIONS, STRINGS

class Card(QFrame):
    clicked=Signal()
    def __init__(self,title:str="",subtitle:str="",parent:QWidget|None=None)->None:
        super().__init__(parent); self.setObjectName('card'); self.setCursor(Qt.CursorShape.PointingHandCursor)
        layout=QVBoxLayout(self); layout.setContentsMargins(DIMENSIONS.padding,DIMENSIONS.padding,DIMENSIONS.padding,DIMENSIONS.padding); layout.setSpacing(DIMENSIONS.gap)
        self.title=QLabel(title); self.subtitle=QLabel(subtitle); self.subtitle.setStyleSheet(f"color:{PALETTE.muted}"); layout.addWidget(self.title); layout.addWidget(self.subtitle)
    def mousePressEvent(self,event): self.clicked.emit(); super().mousePressEvent(event)
    def enterEvent(self,event): self.setStyleSheet(f"background:{PALETTE.hover};border-radius:{DIMENSIONS.radius}px"); super().enterEvent(event)
    def leaveEvent(self,event): self.setStyleSheet(""); super().leaveEvent(event)

class TitleBar(QFrame):
    def __init__(self,window:QWidget)->None:
        super().__init__(window); self.window=window; self._pos:QPoint|None=None; self.setFixedHeight(DIMENSIONS.titlebar)
        row=QHBoxLayout(self); row.setContentsMargins(12,0,8,0); self.label=QLabel(STRINGS.app_name); row.addWidget(self.label); row.addStretch()
        self.min_btn=QPushButton('Свернуть'); self.close_btn=QPushButton('Закрыть'); self.close_btn.setObjectName('close'); row.addWidget(self.min_btn); row.addWidget(self.close_btn)
        self.min_btn.clicked.connect(window.showMinimized); self.close_btn.clicked.connect(window.close); self.close_btn.setStyleSheet(f"QPushButton:hover{{background:rgba(248,81,73,80);color:{PALETTE.text}}}")
    def mousePressEvent(self,e): self._pos=e.globalPosition().toPoint()-self.window.frameGeometry().topLeft()
    def mouseMoveEvent(self,e):
        if self._pos and e.buttons() & Qt.MouseButton.LeftButton: self.window.move(e.globalPosition().toPoint()-self._pos)
    def mouseReleaseEvent(self,e): self._pos=None

class Toast(QLabel):
    def __init__(self,parent:QWidget)->None:
        super().__init__(parent); self.setStyleSheet(f"background:{PALETTE.toast};border-radius:{DIMENSIONS.radius}px;padding:12px;color:{PALETTE.text}"); self.hide()
    def show_message(self,text:str)->None:
        self.setText(text); self.adjustSize(); self.move((self.parent().width()-self.width())//2,70); self.show()
        anim=QPropertyAnimation(self,b"windowOpacity",self); anim.setStartValue(0); anim.setEndValue(1); anim.setDuration(200); anim.setEasingCurve(QEasingCurve.Type.OutCubic); anim.start()

class JarDropArea(Card):
    jarSelected=Signal(str)
    def __init__(self)->None:
        super().__init__(STRINGS.drop_jar,STRINGS.choose_jar); self.setAcceptDrops(True); self.button=QPushButton(STRINGS.choose_jar); self.layout().addWidget(self.button); self.button.clicked.connect(self.choose)
    def choose(self)->None:
        p,_=QFileDialog.getOpenFileName(self,STRINGS.choose_jar,str(Path.home()),'Minecraft Mod (*.jar)')
        if p: self.jarSelected.emit(p)
    def dragEnterEvent(self,e:QDragEnterEvent)->None:
        if e.mimeData().hasUrls() and e.mimeData().urls()[0].toLocalFile().endswith('.jar'): e.acceptProposedAction()
    def dropEvent(self,e:QDropEvent)->None: self.jarSelected.emit(e.mimeData().urls()[0].toLocalFile())

class SegmentedProgress(QWidget):
    def __init__(self)->None: super().__init__(); self.value=0; self.setFixedHeight(8)
    def setValue(self,value:int)->None: self.value=max(0,min(100,value)); self.update()
    def paintEvent(self,e):
        from PySide6.QtGui import QPainter, QColor
        p=QPainter(self); w=self.width(); loaded=int(w*self.value/100); p.fillRect(0,0,w,self.height(),QColor(PALETTE.border)); p.fillRect(0,0,max(0,loaded-DIMENSIONS.progress_gap),self.height(),QColor(PALETTE.success)); p.fillRect(loaded+DIMENSIONS.progress_gap,0,max(0,w-loaded-DIMENSIONS.progress_gap),self.height(),QColor(PALETTE.error))

class ModCard(Card):
    patchRequested=Signal(str); verifyRequested=Signal(str); filesRequested=Signal(str)
    def __init__(self,path:str)->None:
        super().__init__(Path(path).name,path); self.path=path; self.setFixedHeight(96); row=QHBoxLayout(); self.menu=QPushButton('☰'); self.patch=QPushButton(STRINGS.patch); self.verify=QPushButton(STRINGS.verify); row.addWidget(self.patch); row.addWidget(self.verify); row.addWidget(self.menu); self.layout().addLayout(row); self.progress=SegmentedProgress(); self.layout().addWidget(self.progress)
        self.menu.clicked.connect(lambda:self.filesRequested.emit(self.path)); self.patch.clicked.connect(lambda:self.patchRequested.emit(self.path)); self.verify.clicked.connect(lambda:self.verifyRequested.emit(self.path))

class AILogPanel(QFrame):
    def __init__(self)->None:
        super().__init__(); self.setObjectName('card'); self.small=80; self.large=260; self.setMaximumHeight(self.small); box=QVBoxLayout(self); row=QHBoxLayout(); row.addWidget(QLabel('AI-журнал')); close=QPushButton('×'); row.addStretch(); row.addWidget(close); box.addLayout(row); self.text=QTextEdit(); self.text.setReadOnly(True); box.addWidget(self.text); close.clicked.connect(lambda:self.setMaximumHeight(self.small))
    def mousePressEvent(self,e):
        target=self.large if self.maximumHeight()==self.small else self.small; a=QPropertyAnimation(self,b"maximumHeight",self); a.setStartValue(self.maximumHeight()); a.setEndValue(target); a.setDuration(300); a.setEasingCurve(QEasingCurve.Type.OutQuart); a.start()
