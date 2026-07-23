from PySide6.QtCore import QObject, QPropertyAnimation, QEasingCurve, Property, QRect, QPoint
from PySide6.QtWidgets import QWidget
from utils.constants import AppConstants

class AnimationManager(QObject):
    """Фабрика анимаций с едиными настройками easing и длительности."""
    def __init__(self, parent=None):
        super().__init__(parent)

    @staticmethod
    def fade_in(widget: QWidget, duration: int = AppConstants.ANIM_FADE, start_value: float = 0.0, end_value: float = 1.0) -> QPropertyAnimation:
        """Плавное появление (изменение windowOpacity или opacity эффекта)."""
        effect = QGraphicsOpacityEffect(widget)
        widget.setGraphicsEffect(effect)
        anim = QPropertyAnimation(effect, b"opacity")
        anim.setDuration(duration)
        anim.setStartValue(start_value)
        anim.setEndValue(end_value)
        anim.setEasingCurve(QEasingCurve.OutCubic)
        return anim

    @staticmethod
    def fade_out(widget: QWidget, duration: int = AppConstants.ANIM_FADE) -> QPropertyAnimation:
        """Плавное исчезновение."""
        return AnimationManager.fade_in(widget, duration, 1.0, 0.0)

    @staticmethod
    def slide_up(widget: QWidget, start_y: int, end_y: int, duration: int = AppConstants.ANIM_SLIDE) -> QPropertyAnimation:
        """Вертикальное смещение с изменением geometry (y)."""
        anim = QPropertyAnimation(widget, b"geometry")
        anim.setDuration(duration)
        start_rect = widget.geometry()
        start_rect.moveTop(start_y)
        end_rect = widget.geometry()
        end_rect.moveTop(end_y)
        anim.setStartValue(start_rect)
        anim.setEndValue(end_rect)
        anim.setEasingCurve(QEasingCurve.OutCubic)
        return anim

    @staticmethod
    def resize_height(widget: QWidget, start_h: int, end_h: int, duration: int = AppConstants.ANIM_NORMAL) -> QPropertyAnimation:
        """Плавное изменение высоты."""
        anim = QPropertyAnimation(widget, b"maximumHeight")
        anim.setDuration(duration)
        anim.setStartValue(start_h)
        anim.setEndValue(end_h)
        anim.setEasingCurve(QEasingCurve.OutCubic)
        return anim

    @staticmethod
    def color_animation(widget: QWidget, property_name: bytes, start_color, end_color, duration: int = AppConstants.ANIM_NORMAL) -> QPropertyAnimation:
        """Анимация цвета для QSS-свойств (через dynamic property)."""
        # Использует QVariantAnimation и подмену стиля.
        # Проще реализовать через QVariantAnimation и обновление stylesheet,
        # но оставим заглушку: обычно цвета анимируются через custom painting или QPropertyAnimation с QColor.
        # В реальном коде будем использовать QPropertyAnimation на QColor-свойство виджета.
        anim = QPropertyAnimation(widget, property_name)
        anim.setDuration(duration)
        anim.setStartValue(start_color)
        anim.setEndValue(end_color)
        anim.setEasingCurve(QEasingCurve.OutCubic)
        return anim