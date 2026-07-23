from PySide6.QtCore import Qt, Signal, QPoint
from PySide6.QtWidgets import QWidget, QHBoxLayout, QLabel, QPushButton
from PySide6.QtGui import QFont, QPainter, QColor

from managers.theme_manager import ThemeManager
from managers.resource_manager import ResourceManager
from utils.constants import AppConstants

class TitleBar(QWidget):
    minimize_requested = Signal()
    close_requested = Signal()

    def __init__(self, theme_manager: ThemeManager, resource_manager: ResourceManager):
        super().__init__()
        self.theme_manager = theme_manager
        self.theme = theme_manager.theme
        self.resource_manager = resource_manager

        self.setFixedHeight(AppConstants.TITLE_BAR_HEIGHT)
        self.setAutoFillBackground(False)

        layout = QHBoxLayout(self)
        layout.setContentsMargins(AppConstants.PADDING_LG, 0, AppConstants.PADDING_SM, 0)
        layout.setSpacing(0)

        self.title_label = QLabel("PATCHER")
        title_font = self.resource_manager.get_font(AppConstants.FONT_LOGO, 18)  # меньше
        self.title_label.setFont(title_font)
        self.title_label.setStyleSheet(f"color: {self.theme.text_primary.name()};")
        layout.addWidget(self.title_label)
        layout.addStretch()

        self.btn_minimize = self._create_window_button("—")
        self.btn_minimize.clicked.connect(self.minimize_requested.emit)
        layout.addWidget(self.btn_minimize)
        layout.addSpacing(4)

        self.btn_close = self._create_window_button("✕")
        self.btn_close.clicked.connect(self.close_requested.emit)
        layout.addWidget(self.btn_close)

        self._drag_pos = None

    def _create_window_button(self, text: str) -> QPushButton:
        btn = QPushButton(text)
        btn.setFixedSize(AppConstants.WINDOW_BTN_SIZE, AppConstants.WINDOW_BTN_SIZE)
        btn.setStyleSheet(f"""
            QPushButton {{
                background-color: transparent;
                color: {self.theme.text_primary.name()};
                border: none;
                font-family: "Tenali Ramakrishna";
                font-size: 18px;
            }}
            QPushButton:hover {{
                background-color: {self.theme.button_hover.name()};
            }}
            QPushButton:pressed {{
                background-color: {self.theme.button_pressed.name()};
            }}
        """)
        return btn

    def paintEvent(self, event):
        painter = QPainter(self)
        painter.setRenderHint(QPainter.Antialiasing)
        painter.fillRect(self.rect(), self.theme.title_bar)
        painter.end()

    def mousePressEvent(self, event):
        if event.button() == Qt.LeftButton:
            self._drag_pos = event.globalPosition().toPoint() - self.window().frameGeometry().topLeft()
            event.accept()

    def mouseMoveEvent(self, event):
        if event.buttons() == Qt.LeftButton and self._drag_pos is not None:
            self.window().move(event.globalPosition().toPoint() - self._drag_pos)
            event.accept()

    def mouseReleaseEvent(self, event):
        self._drag_pos = None