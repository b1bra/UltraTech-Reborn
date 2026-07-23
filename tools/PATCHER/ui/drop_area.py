import os
from PySide6.QtCore import Qt, Signal, QMimeData
from PySide6.QtWidgets import QWidget, QLabel, QVBoxLayout, QFileDialog, QGraphicsDropShadowEffect
from PySide6.QtGui import QDragEnterEvent, QDropEvent, QPainter, QBrush, QColor

from managers.theme_manager import ThemeManager
from managers.resource_manager import ResourceManager
from utils.constants import AppConstants
from utils.signals import AppSignals

class DropArea(QWidget):
    def __init__(self, theme_manager: ThemeManager, resource_manager: ResourceManager,
                 signals: AppSignals, parent=None):
        super().__init__(parent)
        self.theme_manager = theme_manager
        self.resource_manager = resource_manager
        self.signals = signals
        self.setAcceptDrops(True)
        self._highlight = False

        layout = QVBoxLayout(self)
        layout.setAlignment(Qt.AlignCenter)
        layout.setSpacing(4)   # уменьшенный отступ между надписями

        # Заголовок с неоновым эффектом
        self.label_title = QLabel("Drop your file (.jar) here")
        self.label_title.setAlignment(Qt.AlignCenter)
        self.label_title.setStyleSheet(f"""
            color: {theme_manager.theme.text_accent.name()};
            font-family: "{AppConstants.FONT_MAIN}";
            font-size: {AppConstants.FONT_SIZE_DROP_TITLE}px;
            background: transparent;
        """)
        # Неоновый эффект
        glow = QGraphicsDropShadowEffect()
        glow.setBlurRadius(20)
        glow.setColor(QColor("#FF5FA2"))
        glow.setOffset(0, 0)
        self.label_title.setGraphicsEffect(glow)
        layout.addWidget(self.label_title)

        # Подсказка
        self.label_subtitle = QLabel("or click to browse")
        self.label_subtitle.setAlignment(Qt.AlignCenter)
        self.label_subtitle.setStyleSheet(f"""
            color: {theme_manager.theme.text_primary.name()};
            font-family: "{AppConstants.FONT_MAIN}";
            font-size: {AppConstants.FONT_SIZE_SUBTITLE}px;
            background: transparent;
        """)
        layout.addWidget(self.label_subtitle)

        self.setStyleSheet(f"""
            DropArea {{
                background-color: {theme_manager.theme.background_secondary.name()};
                border-radius: {AppConstants.RADIUS_PANEL}px;
                border: 2px dashed {theme_manager.theme.separator.name()};
            }}
            DropArea:hover {{
                background-color: {theme_manager.theme.button_hover.name()};
                border-color: {theme_manager.theme.text_accent.name()};
            }}
        """)

    def dragEnterEvent(self, event: QDragEnterEvent):
        if event.mimeData().hasUrls():
            urls = event.mimeData().urls()
            if any(url.toLocalFile().endswith('.jar') for url in urls):
                event.acceptProposedAction()
                self._highlight = True
                self.update()
                return
        event.ignore()

    def dragLeaveEvent(self, event):
        self._highlight = False
        self.update()

    def dropEvent(self, event: QDropEvent):
        self._highlight = False
        urls = event.mimeData().urls()
        for url in urls:
            path = url.toLocalFile()
            if path.endswith('.jar'):
                self.signals.file_dropped.emit(path)
                break
        event.acceptProposedAction()
        self.update()

    def mousePressEvent(self, event):
        if event.button() == Qt.LeftButton:
            file_path, _ = QFileDialog.getOpenFileName(
                self, "Select .jar file", "", "JAR files (*.jar)"
            )
            if file_path:
                self.signals.file_dropped.emit(file_path)
        super().mousePressEvent(event)