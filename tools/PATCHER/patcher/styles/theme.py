from __future__ import annotations

from dataclasses import dataclass

from PySide6.QtCore import QPropertyAnimation
from PySide6.QtGui import QColor
from PySide6.QtWidgets import QGraphicsDropShadowEffect, QWidget


@dataclass(frozen=True)
class Theme:
    bg: str = "#1e1e1e"
    overlay: str = "#1e1e1e"
    panel: str = "#2b2b2b"
    panel_alt: str = "#2b2b2b"
    card: str = "#2b2b2b"
    card_hover: str = "#3a3a3a"
    card_selected: str = "#3a3a3a"
    card_done: str = "#2b2b2b"
    card_error: str = "#3a3a3a"
    text: str = "#f0f0f0"
    muted: str = "#b8b8b8"
    border: str = "#3a3a3a"
    accent: str = "#3a3a3a"
    accent_2: str = "#2f8f46"
    danger: str = "#3a3a3a"
    progress_empty: str = "#8f3434"
    progress_done: str = "#2f8f46"
    shadow: str = "#000000"

def add_soft_shadow(widget: QWidget, blur: int = 34, y_offset: int = 10, alpha: int = 105) -> None:
    shadow = QGraphicsDropShadowEffect(widget)
    shadow.setBlurRadius(blur)
    shadow.setOffset(0, y_offset)
    color = QColor(0, 0, 0)
    color.setAlpha(alpha)
    shadow.setColor(color)
    widget.setGraphicsEffect(shadow)


def fade_in(widget: QWidget, duration_ms: int = 180) -> QPropertyAnimation:
    animation = QPropertyAnimation(widget, b"windowOpacity", widget)
    animation.setDuration(duration_ms)
    animation.setStartValue(0.0)
    animation.setEndValue(1.0)
    animation.start()
    return animation


def app_stylesheet(theme: Theme = Theme()) -> str:
    return f"""
    * {{ font-family: 'Inter', 'Segoe UI', Arial; color: {theme.text}; selection-background-color: #3a3a3a; }}
    QMainWindow, QDialog, QWidget#Root {{ background: {theme.bg}; }}
    QWidget {{ background: transparent; }}
    QWidget#Panel, QFrame#Panel {{ background: {theme.panel}; border: 1px solid {theme.border}; border-radius: 28px; padding: 8px; }}
    QWidget#Card, QFrame#Card, QFrame#ModCard, QFrame#ToolCard {{ background: {theme.card}; border: 1px solid {theme.border}; border-radius: 22px; }}
    QWidget#Card:hover, QFrame#Card:hover, QFrame#ModCard:hover, QFrame#ToolCard:hover {{ background: {theme.card_hover}; border-color: #3a3a3a; }}
    QFrame#SelectedCard {{ background: {theme.card_selected}; border: 1px solid #3a3a3a; border-radius: 22px; }}
    QLabel#Muted {{ color: {theme.muted}; }}
    QPushButton {{ background: {theme.panel_alt}; border: 1px solid {theme.border}; border-radius: 16px; padding: 9px 16px; min-height: 20px; }}
    QPushButton:hover {{ background: #3a3a3a; border-color: #3a3a3a; }}
    QPushButton:pressed {{ background: #3a3a3a; }}
    QPushButton#Accent {{ background: {theme.accent}; border: none; font-weight: 700; }}
    QPushButton#Accent:hover {{ background: #3a3a3a; }}
    QPushButton#Danger:hover {{ background: {theme.danger}; color: #f0f0f0; }}
    QLineEdit {{ background: {theme.panel_alt}; border: 1px solid {theme.border}; border-radius: 16px; padding: 10px; }}
    QTextEdit {{ background: {theme.panel}; border: 1px solid {theme.border}; border-radius: 20px; padding: 12px; }}
    QScrollArea {{ border: none; background: transparent; }}
    QScrollArea > QWidget > QWidget {{ background: transparent; }}
    QScrollBar:vertical {{ background: #1e1e1e; width: 10px; margin: 8px 2px 8px 2px; border-radius: 5px; }}
    QScrollBar::handle:vertical {{ background: #3a3a3a; min-height: 34px; border-radius: 5px; }}
    QScrollBar::handle:vertical:hover {{ background: #3a3a3a; }}
    QScrollBar::add-line:vertical, QScrollBar::sub-line:vertical {{ height: 0px; }}
    QScrollBar::add-page:vertical, QScrollBar::sub-page:vertical {{ background: transparent; }}
    QScrollBar:horizontal {{ background: #1e1e1e; height: 10px; margin: 2px 8px 2px 8px; border-radius: 5px; }}
    QScrollBar::handle:horizontal {{ background: #3a3a3a; min-width: 34px; border-radius: 5px; }}
    QScrollBar::add-line:horizontal, QScrollBar::sub-line:horizontal {{ width: 0px; }}
    """
