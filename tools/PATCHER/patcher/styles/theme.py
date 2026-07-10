from __future__ import annotations

from dataclasses import dataclass

from PySide6.QtCore import QPropertyAnimation
from PySide6.QtGui import QColor
from PySide6.QtWidgets import QGraphicsDropShadowEffect, QWidget


@dataclass(frozen=True)
class Theme:
    bg: str = "#0f1117"
    overlay: str = "#0a0c11"
    panel: str = "#171a22"
    panel_alt: str = "#1d222c"
    card: str = "#232936"
    card_hover: str = "#2b3342"
    card_selected: str = "#343d4f"
    card_done: str = "#1b2029"
    card_error: str = "#332129"
    text: str = "#f3f5f9"
    muted: str = "#a0a8b6"
    border: str = "#303746"
    accent: str = "#7c5cff"
    accent_2: str = "#42d392"
    danger: str = "#ff5f6d"
    progress_empty: str = "#3a2430"
    progress_done: str = "#747d8c"
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
    * {{ font-family: 'Inter', 'Segoe UI', Arial; color: {theme.text}; selection-background-color: {theme.accent}; }}
    QMainWindow, QDialog, QWidget#Root {{ background: {theme.bg}; }}
    QWidget {{ background: transparent; }}
    QWidget#Panel, QFrame#Panel {{ background: {theme.panel}; border: 1px solid {theme.border}; border-radius: 28px; padding: 8px; }}
    QWidget#Card, QFrame#Card, QFrame#ModCard, QFrame#ToolCard {{ background: {theme.card}; border: 1px solid {theme.border}; border-radius: 22px; }}
    QWidget#Card:hover, QFrame#Card:hover, QFrame#ModCard:hover, QFrame#ToolCard:hover {{ background: {theme.card_hover}; border-color: #465166; }}
    QFrame#SelectedCard {{ background: {theme.card_selected}; border: 1px solid #65708a; border-radius: 22px; }}
    QLabel#Muted {{ color: {theme.muted}; }}
    QPushButton {{ background: {theme.panel_alt}; border: 1px solid {theme.border}; border-radius: 16px; padding: 9px 16px; min-height: 20px; }}
    QPushButton:hover {{ background: #2a3140; border-color: #4a556c; }}
    QPushButton:pressed {{ background: #343d4f; }}
    QPushButton#Accent {{ background: {theme.accent}; border: none; font-weight: 700; }}
    QPushButton#Accent:hover {{ background: #8b70ff; }}
    QPushButton#Danger:hover {{ background: {theme.danger}; color: white; }}
    QLineEdit {{ background: {theme.panel_alt}; border: 1px solid {theme.border}; border-radius: 16px; padding: 10px; }}
    QTextEdit {{ background: {theme.panel}; border: 1px solid {theme.border}; border-radius: 20px; padding: 12px; }}
    QScrollArea {{ border: none; background: transparent; }}
    QScrollArea > QWidget > QWidget {{ background: transparent; }}
    QScrollBar:vertical {{ background: rgba(255,255,255,18); width: 10px; margin: 8px 2px 8px 2px; border-radius: 5px; }}
    QScrollBar::handle:vertical {{ background: rgba(190,198,214,105); min-height: 34px; border-radius: 5px; }}
    QScrollBar::handle:vertical:hover {{ background: rgba(210,218,235,150); }}
    QScrollBar::add-line:vertical, QScrollBar::sub-line:vertical {{ height: 0px; }}
    QScrollBar::add-page:vertical, QScrollBar::sub-page:vertical {{ background: transparent; }}
    QScrollBar:horizontal {{ background: rgba(255,255,255,18); height: 10px; margin: 2px 8px 2px 8px; border-radius: 5px; }}
    QScrollBar::handle:horizontal {{ background: rgba(190,198,214,105); min-width: 34px; border-radius: 5px; }}
    QScrollBar::add-line:horizontal, QScrollBar::sub-line:horizontal {{ width: 0px; }}
    """
