import os
import zipfile

# Корневая папка проекта (относительно места запуска скрипта)
ROOT = "PATCHER"

# Список всех папок (включая пустые resources/icons, resources/fonts)
dirs = [
    "managers",
    "ui",
    "utils",
    "qss",
    "resources/fonts",
    "resources/icons",
]

# Список всех файлов с путями (относительно ROOT)
files = [
    "main.py",
    # managers
    "managers/__init__.py",
    "managers/resource_manager.py",
    "managers/theme_manager.py",
    "managers/animation_manager.py",
    "managers/window_geometry_manager.py",
    # ui
    "ui/__init__.py",
    "ui/main_window.py",
    "ui/title_bar.py",
    "ui/left_panel.py",
    "ui/right_panel.py",
    "ui/mod_card.py",
    "ui/custom_progress_bar.py",
    "ui/drop_area.py",
    "ui/mod_info_widget.py",
    "ui/floating_menu.py",
    "ui/log_console.py",
    "ui/circular_button.py",
    "ui/save_button.py",
    "ui/settings_window.py",
    "ui/settings_sidebar.py",
    "ui/settings_content.py",
    "ui/custom_scrollbar.py",
    "ui/custom_resize_handler.py",
    # utils
    "utils/__init__.py",
    "utils/constants.py",
    "utils/signals.py",
    # qss
    "qss/base.qss",
]

# Создаём структуру во временной папке ROOT
for d in dirs:
    os.makedirs(os.path.join(ROOT, d), exist_ok=True)

for f in files:
    path = os.path.join(ROOT, f)
    os.makedirs(os.path.dirname(path), exist_ok=True)
    # Создаём пустой файл
    with open(path, "w", encoding="utf-8") as fh:
        pass  # просто пустой файл

# Имя архива
zip_name = "PATCHER_structure.zip"
with zipfile.ZipFile(zip_name, "w", zipfile.ZIP_DEFLATED) as zf:
    for root, _, filenames in os.walk(ROOT):
        for fn in filenames:
            full = os.path.join(root, fn)
            arcname = os.path.relpath(full, start=os.path.dirname(ROOT))
            zf.write(full, arcname)

# Очищаем временную папку (по желанию, можно оставить)
# import shutil
# shutil.rmtree(ROOT)

print(f"✅ Архив создан: {zip_name}")
print("Структура готова. Иконки и шрифты не включены — папки пусты.")