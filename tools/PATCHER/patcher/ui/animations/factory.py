from __future__ import annotations

from PySide6.QtCore import QEasingCurve, QPropertyAnimation, QObject

class AnimationFactory:
    @staticmethod
    def property(target: QObject, prop: bytes, duration: int, easing: QEasingCurve.Type = QEasingCurve.Type.OutCubic) -> QPropertyAnimation:
        anim = QPropertyAnimation(target, prop)
        anim.setDuration(duration)
        anim.setEasingCurve(easing)
        return anim
