"""Qt style sheets for the frameless dark interface."""

from __future__ import annotations

APP_QSS = """
* {
    font-family: "Segoe UI", "Inter", "Arial", sans-serif;
    color: rgba(245, 247, 250, 230);
}
QMainWindow, QWidget#centralRoot {
    background: transparent;
}
QLabel#titleLabel {
    font-size: 13px;
    font-weight: 600;
    color: rgba(255, 255, 255, 190);
}
QLabel#subtitleLabel {
    font-size: 11px;
    color: rgba(255, 255, 255, 105);
}
QPushButton#windowButton {
    border: none;
    border-radius: 10px;
    min-width: 34px;
    min-height: 28px;
    font-size: 18px;
    background: rgba(255, 255, 255, 18);
}
QPushButton#windowButton:hover {
    background: rgba(255, 255, 255, 38);
}
QPushButton#closeButton {
    border: none;
    border-radius: 10px;
    min-width: 34px;
    min-height: 28px;
    font-size: 18px;
    background: rgba(170, 42, 42, 120);
}
QPushButton#closeButton:hover {
    background: rgba(235, 64, 64, 220);
}
QScrollArea {
    border: none;
    background: transparent;
}
QScrollArea > QWidget > QWidget {
    background: transparent;
}
QScrollBar:vertical {
    background: rgba(255, 255, 255, 8);
    width: 10px;
    margin: 4px 2px 4px 2px;
    border-radius: 5px;
}
QScrollBar::handle:vertical {
    min-height: 48px;
    background: rgba(255, 255, 255, 58);
    border-radius: 5px;
}
QScrollBar::handle:vertical:hover {
    background: rgba(255, 255, 255, 88);
}
QScrollBar::add-line:vertical, QScrollBar::sub-line:vertical,
QScrollBar::add-page:vertical, QScrollBar::sub-page:vertical {
    height: 0;
    background: none;
}
codex/-minecraft-6f4mzt
QDialog#confirmDialog {
    background: #17191e;
    border: none;
    border-radius: 16px;
}
QLabel#confirmText {
    color: rgba(255, 255, 255, 220);
    font-size: 12px;
}
QPushButton#confirmButton {
    background: rgba(255, 255, 255, 28);
    border: none;
    border-radius: 8px;
    padding: 7px 22px;
}
QPushButton#confirmButton:hover {
=======
QMessageBox {
    background: #17191e;
}
QMessageBox QLabel {
    color: rgba(255, 255, 255, 220);
}
QMessageBox QPushButton {
    background: rgba(255, 255, 255, 28);
    border: 1px solid rgba(255, 255, 255, 36);
    border-radius: 8px;
    padding: 7px 20px;
}
QMessageBox QPushButton:hover {
development
    background: rgba(255, 255, 255, 52);
}
"""
