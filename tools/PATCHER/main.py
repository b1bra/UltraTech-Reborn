import sys
from PySide6.QtWidgets import QApplication
from PySide6.QtCore import Qt

from managers.theme_manager import ThemeManager
from managers.resource_manager import ResourceManager
from managers.animation_manager import AnimationManager
from ui.main_window import MainWindow
from utils.signals import AppSignals

def main():
    app = QApplication(sys.argv)
    app.setStyle("Fusion")  # чистая база для кастомизации

    # Инициализация менеджеров
    theme_manager = ThemeManager()
    resource_manager = ResourceManager()
    signals = AppSignals()

    # Создание и отображение главного окна (передаём signals)
    window = MainWindow(theme_manager, resource_manager, signals)
    window.show()

    sys.exit(app.exec())

if __name__ == "__main__":
    main()