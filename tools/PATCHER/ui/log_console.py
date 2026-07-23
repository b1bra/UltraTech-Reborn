from PySide6.QtCore import Qt, QTimer
from PySide6.QtWidgets import QWidget, QVBoxLayout, QTextEdit
from PySide6.QtGui import QFont, QColor, QTextCursor

from managers.theme_manager import ThemeManager
from managers.resource_manager import ResourceManager
from utils.constants import AppConstants

class LogConsole(QWidget):
    """Консоль логов, выезжающая снизу. Поддерживает цветные сообщения и автоскролл."""

    def __init__(self, theme_manager: ThemeManager, resource_manager: ResourceManager, parent=None):
        super().__init__(parent)
        self.theme_manager = theme_manager
        self.theme = theme_manager.theme
        self.resource_manager = resource_manager

        self.setAttribute(Qt.WA_TranslucentBackground)
        self.setAutoFillBackground(False)

        layout = QVBoxLayout(self)
        layout.setContentsMargins(AppConstants.PADDING_MD, AppConstants.PADDING_MD,
                                  AppConstants.PADDING_MD, AppConstants.PADDING_MD)
        layout.setSpacing(0)

        self.text_edit = QTextEdit()
        self.text_edit.setReadOnly(True)
        self.text_edit.setVerticalScrollBarPolicy(Qt.ScrollBarAlwaysOff)
        self.text_edit.setHorizontalScrollBarPolicy(Qt.ScrollBarAlwaysOff)
        self.text_edit.setStyleSheet(f"""
            QTextEdit {{
                background-color: transparent;
                color: {self.theme.text_primary.name()};
                border: none;
                selection-background-color: {self.theme.button_hover.name()};
            }}
        """)
        font = self.resource_manager.get_font(AppConstants.FONT_MONO, AppConstants.FONT_SIZE_LOG)
        self.text_edit.setFont(font)
        layout.addWidget(self.text_edit)

        self._max_lines = AppConstants.MAX_LOG_LINES

    def add_message(self, message: str, level: str = "INFO"):
        """Добавляет сообщение с указанием уровня (INFO, WARNING, ERROR, SUCCESS)."""
        colors = {
            "INFO": self.theme.info,
            "WARNING": self.theme.warning,
            "ERROR": self.theme.danger,
            "SUCCESS": self.theme.success,
        }
        color = colors.get(level.upper(), self.theme.info)

        self.text_edit.moveCursor(QTextCursor.End)
        self.text_edit.setTextColor(color)
        self.text_edit.insertPlainText(f"[{level.upper()}] {message}\n")
        self.text_edit.moveCursor(QTextCursor.End)
        self.text_edit.ensureCursorVisible()

        # Ограничение количества строк
        while self.text_edit.document().blockCount() > self._max_lines:
            cursor = QTextCursor(self.text_edit.document().begin())
            cursor.select(QTextCursor.BlockUnderCursor)
            cursor.removeSelectedText()
            cursor.deleteChar()  # удалить оставшийся '\n'

    def paintEvent(self, event):
        painter = QPainter(self)
        painter.setRenderHint(QPainter.Antialiasing)
        rect = self.rect()
        # Рисуем фон только с закруглёнными верхними углами
        path = QPainterPath()
        path.addRoundedRect(rect, AppConstants.RADIUS_PANEL, AppConstants.RADIUS_PANEL)
        painter.fillPath(path, self.theme.console_bg)
        painter.end()

    def showEvent(self, event):
        super().showEvent(event)
        # Автоматическая прокрутка вниз при показе
        self.text_edit.moveCursor(QTextCursor.End)
        self.text_edit.ensureCursorVisible()