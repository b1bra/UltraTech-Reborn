"""Theme, animation and resource managers centralized for GUI and services."""
from __future__ import annotations
from pathlib import Path
from typing import Any
try:
    from PySide6.QtCore import QEasingCurve, QPropertyAnimation, QObject
except ModuleNotFoundError:
    QEasingCurve = None
    QPropertyAnimation = None
    QObject = object
from tools.PATCHER.resources.design_tokens import PALETTE, DIMENSIONS, ANIMATIONS

class ThemeManager:
    def stylesheet(self) -> str:
        p = PALETTE
        d = DIMENSIONS
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
    def animate(self, obj: Any, prop: bytes, start: Any, stop: Any, duration: int | None = None, curve: Any = None) -> Any:
        if QPropertyAnimation is None:
            setattr(obj, prop.decode() if isinstance(prop, bytes) else str(prop), stop)
            return {"animated": False, "start": start, "stop": stop, "duration": duration or ANIMATIONS.fast_ms}
        easing = curve or QEasingCurve.Type.OutCubic
        animation = QPropertyAnimation(obj, prop)
        animation.setStartValue(start)
        animation.setEndValue(stop)
        animation.setDuration(duration or ANIMATIONS.fast_ms)
        animation.setEasingCurve(easing)
        animation.start()
        return animation

class ResourceManager:
    def __init__(self, root: Path) -> None:
        self.root = root
        self.root.mkdir(parents=True, exist_ok=True)
    def path(self, *parts: str) -> Path:
        return self.root.joinpath(*parts)
    def text(self, *parts: str, default: str = "") -> str:
        target = self.path(*parts)
        return target.read_text(encoding="utf-8") if target.exists() else default
    def write_text(self, content: str, *parts: str) -> Path:
        target = self.path(*parts)
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_text(content, encoding="utf-8")
        return target
