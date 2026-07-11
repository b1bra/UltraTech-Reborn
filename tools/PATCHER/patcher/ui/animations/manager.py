from __future__ import annotations

from PySide6.QtCore import QEasingCurve, QPropertyAnimation, QObject
from patcher.ui.styles.tokens import SIZES

class AnimationManager:
    def property(self, target: QObject, prop: bytes, duration: int = SIZES.animation_ms, easing: QEasingCurve.Type = QEasingCurve.Type.OutCubic) -> QPropertyAnimation:
        animation = QPropertyAnimation(target, prop, target)
        animation.setDuration(duration)
        animation.setEasingCurve(easing)
        return animation
