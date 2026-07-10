from __future__ import annotations

from dataclasses import dataclass


@dataclass(frozen=True)
class Theme:
    bg: str = "#111318"
    panel: str = "#171a21"
    panel_alt: str = "#1d212a"
    card: str = "#20242e"
    card_done: str = "#191c23"
    card_error: str = "#2a1d22"
    text: str = "#f3f5f9"
    muted: str = "#9aa4b2"
    border: str = "#2b303b"
    accent: str = "#7c5cff"
    accent_2: str = "#42d392"
    danger: str = "#ff5f6d"
    progress_empty: str = "#7d2f38"
    progress_done: str = "#6f7785"
    shadow: str = "rgba(0, 0, 0, 90)"


def app_stylesheet(theme: Theme = Theme()) -> str:
    return f"""
    * {{ font-family: 'Inter', 'Segoe UI', Arial; color: {theme.text}; }}
    QMainWindow, QWidget#Root {{ background: {theme.bg}; }}
    QWidget#Panel {{ background: {theme.panel}; border: 1px solid {theme.border}; border-radius: 28px; }}
    QLabel#Muted {{ color: {theme.muted}; }}
    QPushButton {{ background: {theme.panel_alt}; border: 1px solid {theme.border}; border-radius: 14px; padding: 9px 14px; }}
    QPushButton:hover {{ background: #272c38; border-color: #3a4150; }}
    QPushButton#Accent {{ background: {theme.accent}; border: none; }}
    QPushButton#Danger:hover {{ background: {theme.danger}; color: white; }}
    QLineEdit {{ background: {theme.panel_alt}; border: 1px solid {theme.border}; border-radius: 14px; padding: 10px; }}
    QTextEdit {{ background: {theme.panel}; border: 1px solid {theme.border}; border-radius: 18px; padding: 12px; }}
    QScrollArea {{ border: none; background: transparent; }}
    """
