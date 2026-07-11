from __future__ import annotations

from patcher.ui.styles.tokens import COLORS, SIZES

class ThemeManager:
    def stylesheet(self) -> str:
        return f"""
        QWidget#Root {{ background: {COLORS.background}; color: {COLORS.text}; font-family: 'Segoe UI', Arial; }}
        QWidget#Panel, QFrame#Card {{ background: {COLORS.surface}; border: 1px solid {COLORS.border}; border-radius: {SIZES.radius}px; padding: {SIZES.padding}px; }}
        QLabel#Muted {{ color: {COLORS.muted}; }}
        QPushButton {{ background: {COLORS.surface}; color: {COLORS.text}; border: 1px solid {COLORS.border}; border-radius: {SIZES.radius}px; padding: 8px 14px; }}
        QPushButton:hover {{ background: {COLORS.surface_hover}; }}
        QPushButton#Accent {{ background: {COLORS.accent}; color: #101216; }}
        QPushButton#Danger:hover {{ background: {COLORS.danger_hover}; }}
        QScrollBar:vertical {{ background: transparent; width: 8px; margin: 4px; }}
        QScrollBar::handle:vertical {{ background: rgba(154,163,178,90); border-radius: 4px; min-height: 32px; }}
        QScrollBar::add-line:vertical, QScrollBar::sub-line:vertical {{ height: 0; }}
        """
