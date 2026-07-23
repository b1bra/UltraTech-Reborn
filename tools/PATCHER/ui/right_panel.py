from PySide6.QtCore import Qt
from PySide6.QtWidgets import QWidget, QVBoxLayout

from ui.mod_card import ModCard
from ui.save_button import SaveButton
from managers.theme_manager import ThemeManager
from managers.resource_manager import ResourceManager
from utils.constants import AppConstants
from utils.signals import AppSignals

class RightPanel(QWidget):
    def __init__(self, theme_manager: ThemeManager, resource_manager: ResourceManager,
                 signals: AppSignals, parent=None):
        super().__init__(parent)
        self.theme_manager = theme_manager
        self.resource_manager = resource_manager
        self.signals = signals

        self.setAutoFillBackground(False)
        layout = QVBoxLayout(self)
        layout.setContentsMargins(0, 0, 0, 0)
        layout.setSpacing(AppConstants.PADDING_MD)

        self.mod_card = ModCard(theme_manager, resource_manager, signals)
        layout.addWidget(self.mod_card, 1)  # растягивается на всю доступную высоту

        self.save_button = SaveButton(theme_manager, resource_manager, signals)
        layout.addWidget(self.save_button, 0)

        self.setStyleSheet("background: transparent;")