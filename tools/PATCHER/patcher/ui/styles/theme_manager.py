from __future__ import annotations

from patcher.ui.styles.tokens import COLORS, SIZES

class ThemeManager:
    def stylesheet(self) -> str:
        return f"""
        QWidget, QDialog, QMainWindow {{ background: {COLORS.background}; color: {COLORS.text}; font-family: 'Segoe UI', Arial; }}
        QWidget#Root {{ background: {COLORS.background}; color: {COLORS.text}; }}
        QWidget#Panel, QFrame#Card, QFrame#ModCard {{ background: {COLORS.surface}; border: 1px solid {COLORS.border}; border-radius: {SIZES.radius}px; }}
        QLabel {{ color: {COLORS.text}; background: transparent; }}
        QLabel#Muted {{ color: {COLORS.muted}; }}
        QTextEdit, QLineEdit {{ background: {COLORS.surface}; color: {COLORS.text}; border: 1px solid {COLORS.border}; border-radius: {SIZES.radius}px; padding: 8px; selection-background-color: {COLORS.surface_hover}; }}
        QPushButton {{ background: {COLORS.surface}; color: {COLORS.text}; border: 1px solid {COLORS.border}; border-radius: {SIZES.radius}px; padding: 8px 14px; }}
        QPushButton:hover {{ background: {COLORS.surface_hover}; }}
        QPushButton#Accent {{ background: {COLORS.surface_hover}; color: {COLORS.text}; font-weight: 700; }}
        QPushButton#Danger:hover {{ background: #3a3a3a; color: #f0f0f0; }}
        QScrollArea {{ background: {COLORS.background}; border: none; }}
        QScrollArea > QWidget > QWidget {{ background: {COLORS.background}; }}
        QScrollBar:vertical {{ background: {COLORS.background}; width: 8px; margin: 4px; }}
        QScrollBar::handle:vertical {{ background: {COLORS.surface_hover}; border-radius: 4px; min-height: 32px; }}
        QScrollBar::add-line:vertical, QScrollBar::sub-line:vertical {{ height: 0; background: {COLORS.background}; }}
        """
