from PySide6.QtCore import Qt, QTimer
from PySide6.QtWidgets import QWidget, QVBoxLayout, QLabel, QTextEdit, QGraphicsOpacityEffect
from PySide6.QtGui import QFont, QPainter, QBrush, QColor, QTextCursor, QPen

from ui.custom_progress_bar import CustomProgressBar
from managers.theme_manager import ThemeManager
from managers.resource_manager import ResourceManager
from utils.constants import AppConstants
from utils.signals import AppSignals

class ModCard(QWidget):
    def __init__(self, theme_manager: ThemeManager, resource_manager: ResourceManager,
                 signals: AppSignals, parent=None):
        super().__init__(parent)
        self.theme_manager = theme_manager
        self.resource_manager = resource_manager
        self.signals = signals

        self.setFixedHeight(280)
        self.setAutoFillBackground(False)

        layout = QVBoxLayout(self)
        layout.setContentsMargins(AppConstants.PADDING_LG, AppConstants.PADDING_LG,
                                  AppConstants.PADDING_LG, AppConstants.PADDING_LG)
        layout.setSpacing(AppConstants.PADDING_SM)

        self.mod_name_label = QLabel("No mod loaded")
        self.mod_name_label.setAlignment(Qt.AlignLeft | Qt.AlignVCenter)
        self.mod_name_label.setStyleSheet(f"""
            color: {theme_manager.theme.text_primary.name()};
            font-family: "{AppConstants.FONT_MAIN}";
            font-size: {AppConstants.FONT_SIZE_NORMAL}px;
            background: transparent;
        """)
        self.mod_name_label.setWordWrap(False)
        self.mod_name_label.setToolTip("")
        layout.addWidget(self.mod_name_label)

        self.log_text = QTextEdit()
        self.log_text.setReadOnly(True)
        self.log_text.setVerticalScrollBarPolicy(Qt.ScrollBarAlwaysOff)
        self.log_text.setStyleSheet(f"""
            QTextEdit {{
                background-color: {theme_manager.theme.background_card.name()};
                color: {theme_manager.theme.text_secondary.name()};
                border: none;
                border-radius: {AppConstants.RADIUS_CARD}px;
                padding: 6px;
                font-family: "{AppConstants.FONT_MONO}";
                font-size: {AppConstants.FONT_SIZE_LOG}px;
            }}
        """)
        layout.addWidget(self.log_text, 1)

        self.progress_bar = CustomProgressBar(theme_manager)
        layout.addWidget(self.progress_bar)

        self.action_label = QLabel("Ready")
        self.action_label.setAlignment(Qt.AlignCenter)
        self.action_label.setStyleSheet(f"""
            color: {theme_manager.theme.text_secondary.name()};
            font-family: "{AppConstants.FONT_MAIN}";
            font-size: {AppConstants.FONT_SIZE_SMALL}px;
        """)
        layout.addWidget(self.action_label)

        self.signals.file_dropped.connect(self.on_file_loaded)
        self.signals.analysis_step_changed.connect(self.action_label.setText)
        self.signals.progress_updated.connect(self.progress_bar.set_value)
        self.signals.log_message.connect(self.append_log)

    def on_file_loaded(self, file_path: str):
        name = file_path.split('/')[-1].replace('.jar', '')
        self.mod_name_label.setText(name)
        self.mod_name_label.setToolTip(name)
        self.progress_bar.set_value(0)
        self.action_label.setText("Starting...")
        self.log_text.clear()

    def append_log(self, message: str, level: str = "INFO"):
        color = {
            "INFO": self.theme_manager.theme.info,
            "WARNING": self.theme_manager.theme.warning,
            "ERROR": self.theme_manager.theme.danger,
            "SUCCESS": self.theme_manager.theme.success,
        }.get(level.upper(), self.theme_manager.theme.info)
        self.log_text.moveCursor(QTextCursor.End)
        self.log_text.setTextColor(color)
        self.log_text.insertPlainText(f"[{level}] {message}\n")
        self.log_text.moveCursor(QTextCursor.End)

    def paintEvent(self, event):
        painter = QPainter(self)
        painter.setRenderHint(QPainter.Antialiasing)
        rect = self.rect()
        painter.setBrush(QBrush(self.theme_manager.theme.background_card))
        painter.setPen(Qt.NoPen)
        painter.drawRoundedRect(rect, AppConstants.RADIUS_PANEL, AppConstants.RADIUS_PANEL)
        painter.end()