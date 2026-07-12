"""Theme, animation and resource managers centralized for the GUI and services."""
from __future__ import annotations
from pathlib import Path
from PySide6.QtCore import QEasingCurve, QPropertyAnimation, QObject
from tools.PATCHER.resources.design_tokens import PALETTE, DIMENSIONS, ANIMATIONS
class ThemeManager:
    def stylesheet(self)->str:
        p=PALETTE; d=DIMENSIONS
        return f"""
        *{{color:{p.text};font-family:'Inter','Segoe UI',sans-serif;font-size:13px;}}
        QWidget{{background:{p.background};}}
        QFrame#card,QDialog{{background:{p.card};border:1px solid {p.border};border-radius:{d.radius}px;}}
        QPushButton{{background:{p.card};border:1px solid {p.border};border-radius:{d.radius}px;padding:8px 12px;}}
        QPushButton:hover{{background:{p.hover};}}
        QPushButton#accent{{background:{p.accent};}}
        QScrollBar:vertical{{background:transparent;width:{d.scrollbar}px;}}
        QScrollBar::handle:vertical{{background:rgba(139,148,158,90);border-radius:3px;}}
        """
class AnimationManager:
    def animate(self,obj:QObject,prop:bytes,start,stop,duration:int|None=None,curve:QEasingCurve.Type=QEasingCurve.Type.OutCubic)->QPropertyAnimation:
        a=QPropertyAnimation(obj,prop); a.setStartValue(start); a.setEndValue(stop); a.setDuration(duration or ANIMATIONS.fast_ms); a.setEasingCurve(curve); a.start(); return a
class ResourceManager:
    def __init__(self,root:Path)->None: self.root=root
    def path(self,*parts:str)->Path: return self.root.joinpath(*parts)
