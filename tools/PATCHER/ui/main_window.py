from PySide6.QtCore import Qt, QRect, QSize, QUrl, QMargins, QPoint
from PySide6.QtWidgets import QMainWindow, QWidget, QVBoxLayout, QHBoxLayout, QGraphicsDropShadowEffect
from PySide6.QtGui import QPainter, QPainterPath, QBrush, QColor, QRegion
from PySide6.QtGui import QDesktopServices

from managers.theme_manager import ThemeManager
from managers.resource_manager import ResourceManager
from managers.animation_manager import AnimationManager
from managers.window_geometry_manager import WindowGeometryManager
from utils.constants import AppConstants
from utils.signals import AppSignals

from ui.title_bar import TitleBar
from ui.left_panel import LeftPanel
from ui.right_panel import RightPanel
from ui.floating_menu import FloatingMenu
from ui.log_console import LogConsole
from ui.circular_button import CircularButton

class MainWindow(QMainWindow):
    def __init__(self, theme_manager: ThemeManager, resource_manager: ResourceManager, signals: AppSignals):
        super().__init__()
        self.theme_manager = theme_manager
        self.theme = theme_manager.theme
        self.resource_manager = resource_manager
        self.signals = signals
        self.geometry_manager = WindowGeometryManager()

        self.setWindowFlags(Qt.FramelessWindowHint)
        self.setAttribute(Qt.WA_TranslucentBackground, True)
        self.setAutoFillBackground(False)

        self.resize(AppConstants.DEFAULT_WIDTH, AppConstants.DEFAULT_HEIGHT)
        self.setMinimumSize(AppConstants.MIN_WIDTH, AppConstants.MIN_HEIGHT)

        # Маска для закругления
        self._update_mask()

        # Центральный контейнер (будет обрезан маской)
        self.central_container = QWidget(self)
        self.central_container.setObjectName("centralContainer")
        self.setCentralWidget(self.central_container)

        self.main_layout = QVBoxLayout(self.central_container)
        self.main_layout.setContentsMargins(0, 0, 0, 0)
        self.main_layout.setSpacing(0)

        # Верхняя панель
        self.title_bar = TitleBar(self.theme_manager, self.resource_manager)
        self.main_layout.addWidget(self.title_bar)

        # Контент (левая и правая панели + overlay)
        content_widget = QWidget()
        content_layout = QHBoxLayout(content_widget)
        content_layout.setContentsMargins(
            AppConstants.OUTER_MARGIN,
            AppConstants.PADDING_SM,
            AppConstants.OUTER_MARGIN,
            AppConstants.OUTER_MARGIN
        )
        content_layout.setSpacing(AppConstants.GAP_BETWEEN_PANELS)

        self.left_panel = LeftPanel(self.theme_manager, self.resource_manager, self.signals)
        self.right_panel = RightPanel(self.theme_manager, self.resource_manager, self.signals)

        content_layout.addWidget(self.left_panel, 78)
        content_layout.addWidget(self.right_panel, 22)

        self.main_layout.addWidget(content_widget, 1)

        # Overlay внутри content_widget (абсолютное позиционирование)
        self.overlay = QWidget(content_widget)
        self.overlay.setAttribute(Qt.WA_TransparentForMouseEvents, False)
        self.overlay.setStyleSheet("background: transparent;")
        self.overlay.setGeometry(content_widget.rect())

        overlay_layout = QHBoxLayout(self.overlay)
        overlay_layout.setContentsMargins(
            AppConstants.OUTER_MARGIN,
            0,
            AppConstants.OUTER_MARGIN,
            AppConstants.OUTER_MARGIN + 40
        )
        overlay_layout.setSpacing(AppConstants.PADDING_MD)

        left_buttons_widget = QWidget()
        left_buttons_layout = QHBoxLayout(left_buttons_widget)
        left_buttons_layout.setContentsMargins(0, 0, 0, 0)
        left_buttons_layout.setSpacing(AppConstants.PADDING_MD)
        left_buttons_layout.addStretch()

        self.menu_button = CircularButton(self.resource_manager.get_icon("settings", 24), self.theme_manager)
        self.menu_button.setFixedSize(AppConstants.CIRCULAR_BUTTON_DIAMETER, AppConstants.CIRCULAR_BUTTON_DIAMETER)

        self.log_button = CircularButton("L", self.theme_manager)
        self.log_button.setFixedSize(AppConstants.CIRCULAR_BUTTON_DIAMETER, AppConstants.CIRCULAR_BUTTON_DIAMETER)

        left_buttons_layout.addWidget(self.menu_button)
        left_buttons_layout.addWidget(self.log_button)
        left_buttons_layout.addStretch()

        overlay_layout.addWidget(left_buttons_widget, 0, Qt.AlignBottom | Qt.AlignLeft)
        overlay_layout.addStretch()

        # Плавающее меню (прикреплено к центральному контейнеру, чтобы быть поверх всего)
        self.floating_menu = FloatingMenu(self.theme_manager, self.resource_manager)
        self.floating_menu.hide()
        self.floating_menu.setParent(self.central_container)
        self.floating_menu.raise_()

        # Консоль логов
        self.log_console = LogConsole(self.theme_manager, self.resource_manager)
        self.log_console.hide()
        self.log_console.setParent(self.central_container)
        self.log_console.raise_()

        # Сигналы
        self.menu_button.clicked.connect(self.toggle_menu)
        self.log_button.clicked.connect(self.toggle_log_console)
        self.title_bar.minimize_requested.connect(self.showMinimized)
        self.title_bar.close_requested.connect(self.close_with_animation)
        self.floating_menu.menu_action_triggered.connect(self.handle_menu_action)

        self.geometry_manager.restore_state(self)

    def _update_mask(self):
        path = QPainterPath()
        path.addRoundedRect(QRect(QPoint(0,0), self.size()), AppConstants.RADIUS_WINDOW, AppConstants.RADIUS_WINDOW)
        region = QRegion(path.toFillPolygon().toPolygon())
        self.setMask(region)

    def resizeEvent(self, event):
        super().resizeEvent(event)
        self._update_mask()
        if hasattr(self, 'overlay'):
            self.overlay.setGeometry(self.central_container.rect().marginsRemoved(
                QMargins(AppConstants.OUTER_MARGIN, AppConstants.PADDING_SM, AppConstants.OUTER_MARGIN, AppConstants.OUTER_MARGIN)
            ))
        if self.floating_menu.isVisible():
            self.position_floating_menu()
        if self.log_console.isVisible():
            self.position_log_console()

    def paintEvent(self, event):
        painter = QPainter(self)
        painter.setRenderHint(QPainter.Antialiasing)
        path = QPainterPath()
        path.addRoundedRect(self.rect(), AppConstants.RADIUS_WINDOW, AppConstants.RADIUS_WINDOW)
        painter.fillPath(path, QBrush(self.theme.background_main))
        painter.end()

    def position_floating_menu(self):
        btn_global_pos = self.menu_button.mapToGlobal(self.menu_button.rect().topLeft())
        local_pos = self.central_container.mapFromGlobal(btn_global_pos)
        menu_x = local_pos.x()
        menu_y = local_pos.y() - self.floating_menu.height() - AppConstants.PADDING_SM
        self.floating_menu.move(menu_x, menu_y)

    def position_log_console(self):
        console_width = self.width()
        console_height = AppConstants.LOG_CONSOLE_HEIGHT
        x = 0
        y = self.height() - console_height
        self.log_console.setGeometry(x, y, console_width, console_height)

    def toggle_menu(self):
        if self.floating_menu.isVisible():
            self.floating_menu.hide()
        else:
            self.position_floating_menu()
            self.floating_menu.show()
            anim = AnimationManager.fade_in(self.floating_menu, duration=AppConstants.ANIM_MENU_OPEN)
            anim.start()

    def toggle_log_console(self):
        if self.log_console.isVisible():
            self.log_console.hide()
        else:
            self.position_log_console()
            self.log_console.show()
            anim = AnimationManager.fade_in(self.log_console, duration=AppConstants.ANIM_SLIDE)
            anim.start()

    def handle_menu_action(self, action: str):
        urls = {
            "chatgpt": "https://chat.openai.com",
            "deepseek": "https://chat.deepseek.com",
            "claude": "https://claude.ai",
            "settings": "",  # откроется окно настроек позже
        }
        if action in urls and urls[action]:
            QDesktopServices.openUrl(QUrl(urls[action]))
        # Recaf пока игнорируем

    def close_with_animation(self):
        self.close()

    def closeEvent(self, event):
        self.geometry_manager.save_state(self)
        super().closeEvent(event)