from PySide6.QtCore import QObject, Signal
from PySide6.QtGui import QColor

class Theme:
    def __init__(self):
        self.background_main = QColor("#262B3E")      # темнее
        self.background_secondary = QColor("#3B415C")
        self.background_card = QColor("#1E2233")      # карточка ещё темнее
        self.background_dark = QColor("#1A1D26")
        self.title_bar = QColor("#0D0D0D")
        self.separator = QColor("#252939")

        self.text_primary = QColor("#F5F5F5")
        self.text_secondary = QColor("#CFCFCF")
        self.text_disabled = QColor("#A0A0A0")
        self.text_accent = QColor("#FF5FA2")

        self.success = QColor("#59D97A")
        self.danger = QColor("#FF5A72")
        self.warning = QColor("#FFB347")
        self.info = QColor("#F5F5F5")

        self.button_normal = QColor("#404760")
        self.button_hover = self.button_normal.lighter(108)
        self.button_pressed = self.button_normal.darker(110)
        self.button_disabled = QColor("#555B70")
        self.button_text_disabled = QColor("#AAAAAA")

        self.save_button_active = QColor("#3B415C")
        self.save_button_hover = self.save_button_active.lighter(110)
        self.save_button_pressed = self.save_button_active.darker(110)
        self.save_button_disabled = QColor("#4B516A")

        self.progress_filled = self.success
        self.progress_unfilled = QColor("#FF5FA2")
        self.progress_bg = self.background_dark

        self.console_bg = QColor("#111318")

    def to_stylesheet(self) -> str:
        return f"""
        * {{
            color: {self.text_primary.name()};
            font-family: "Tenali Ramakrishna";
        }}
        """

class ThemeManager(QObject):
    theme_changed = Signal()

    def __init__(self, parent=None):
        super().__init__(parent)
        self._theme = Theme()

    @property
    def theme(self) -> Theme:
        return self._theme

    def update_theme(self, new_theme: Theme):
        self._theme = new_theme
        self.theme_changed.emit()