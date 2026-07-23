import os
from pathlib import Path
from PySide6.QtCore import Qt, QObject
from PySide6.QtGui import QFontDatabase, QFont, QPixmap, QColor, QPainter, QBrush, QPen

class ResourceManager(QObject):
    """Централизованная загрузка шрифтов и иконок. Все ресурсы запрашиваются только отсюда."""
    def __init__(self, parent=None):
        super().__init__(parent)
        self._fonts = {}          # family_name -> QFont
        self._icons = {}          # name -> QPixmap
        self._font_db = QFontDatabase()

        # Корневая папка ресурсов (относительно этого файла)
        self._base_dir = Path(__file__).parent.parent / "resources"
        self._load_fonts()
        self._load_icons()

    # ---- Шрифты ----
    def _load_fonts(self):
        fonts_dir = self._base_dir / "fonts"
        if not fonts_dir.exists():
            print("[ResourceManager] Папка fonts не найдена")
            return

        # Сопоставление имени папки -> желаемое имя семейства в приложении
        target_families = {
            "Stick": "Stick",
            "Tenali Ramakrishna": "Tenali Ramakrishna",
            "Courier Prime": "Courier Prime",
        }

        for folder_name, family_name in target_families.items():
            dir_path = fonts_dir / folder_name
            if not dir_path.exists() or not dir_path.is_dir():
                print(f"[ResourceManager] Папка шрифта {folder_name} не найдена")
                continue

            # Берём первый попавшийся шрифтовой файл в этой папке
            font_file = None
            for f in dir_path.iterdir():
                if f.suffix.lower() in (".ttf", ".otf"):
                    font_file = f
                    break

            if font_file:
                font_id = self._font_db.addApplicationFont(str(font_file))
                if font_id == -1:
                    print(f"[ResourceManager] Не удалось загрузить шрифт {font_file}")
                else:
                    families = self._font_db.applicationFontFamilies(font_id)
                    if families:
                        loaded_family = families[0]
                        self._fonts[family_name] = QFont(loaded_family)
                        print(f"[ResourceManager] Шрифт {family_name} загружен: {font_file.name}")
            else:
                print(f"[ResourceManager] В папке {folder_name} нет шрифтовых файлов")

    def get_font(self, family: str, size: int = 14) -> QFont:
        """Возвращает QFont из загруженных, иначе fallback на системный."""
        if family in self._fonts:
            font = self._fonts[family]
            font.setPixelSize(size)
            return font
        else:
            fallback = QFont(family)
            fallback.setPixelSize(size)
            print(f"[ResourceManager] Шрифт {family} не найден, используется системный")
            return fallback

    # ---- Иконки ----
    def _load_icons(self):
        icons_dir = self._base_dir / "icons"
        if not icons_dir.exists():
            print("[ResourceManager] Папка icons не найдена")
            return

        for f in icons_dir.iterdir():
            if f.suffix.lower() == ".png":
                pixmap = QPixmap(str(f))
                if pixmap.isNull():
                    print(f"[ResourceManager] Не удалось загрузить иконку {f.name}")
                    continue
                name = f.stem  # java, minecraft, settings, ...
                self._icons[name] = pixmap

    def get_icon(self, name: str, size: int = 24) -> QPixmap:
        """Возвращает QPixmap нужного размера. Если иконка отсутствует, генерирует заглушку."""
        if name in self._icons:
            return self._icons[name].scaled(size, size, mode=Qt.SmoothTransformation)
        else:
            print(f"[ResourceManager] Иконка '{name}' не найдена, создана заглушка")
            return self._create_placeholder(size)

    def _create_placeholder(self, size: int) -> QPixmap:
        pixmap = QPixmap(size, size)
        pixmap.fill(QColor(0, 0, 0, 0))  # прозрачный фон
        painter = QPainter(pixmap)
        painter.setRenderHint(QPainter.Antialiasing)
        painter.setBrush(QBrush(QColor("#FF5FA2")))
        painter.setPen(Qt.NoPen)
        painter.drawRoundedRect(0, 0, size, size, size//4, size//4)
        painter.setPen(QColor("#FFFFFF"))
        painter.setFont(self.get_font("Tenali Ramakrishna", size//2))
        painter.drawText(pixmap.rect(), Qt.AlignCenter, "?")
        painter.end()
        return pixmap