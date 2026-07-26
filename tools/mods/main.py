import os
from pathlib import Path

def print_tree(directory, prefix="", is_last=True, max_depth=None, current_depth=0):
    """
    Рекурсивно выводит структуру папок и файлов в виде дерева
    
    Args:
        directory: путь к текущей директории
        prefix: префикс для отступов (вертикальные линии)
        is_last: является ли текущий элемент последним на своем уровне
        max_depth: максимальная глубина (None - без ограничений)
        current_depth: текущий уровень вложенности
    """
    if max_depth is not None and current_depth > max_depth:
        return
    
    # Получаем имя текущей директории
    dir_name = os.path.basename(directory)
    if not dir_name:  # для корневого пути
        dir_name = directory
    
    # Выводим текущую директорию
    if current_depth == 0:
        print(f"📁 {dir_name}")
    else:
        connector = "└── " if is_last else "├── "
        print(f"{prefix}{connector}📁 {dir_name}")
    
    try:
        # Получаем содержимое директории
        items = os.listdir(directory)
        
        # Отделяем папки от файлов
        dirs = []
        files = []
        
        for item in items:
            full_path = os.path.join(directory, item)
            if os.path.isdir(full_path):
                dirs.append(item)
            else:
                files.append(item)
        
        # Сортируем по алфавиту
        dirs.sort()
        files.sort()
        
        # Объединяем: сначала папки, потом файлы
        all_items = dirs + files
        total_items = len(all_items)
        
        # Вычисляем новый префикс для вложенных элементов
        if current_depth == 0:
            new_prefix = ""
        else:
            new_prefix = prefix + ("    " if is_last else "│   ")
        
        # Выводим все элементы
        for index, item in enumerate(all_items):
            full_path = os.path.join(directory, item)
            is_last_item = (index == total_items - 1)
            
            if os.path.isdir(full_path):
                # Для папок - рекурсивный вызов
                print_tree(full_path, new_prefix, is_last_item, max_depth, current_depth + 1)
            else:
                # Для файлов - просто выводим с иконкой
                connector = "└── " if is_last_item else "├── "
                extension = os.path.splitext(item)[1].lower()
                icon = get_file_icon(extension)
                
                if extension:
                    print(f"{new_prefix}{connector}{icon} {item}")
                else:
                    print(f"{new_prefix}{connector}{icon} {item} (нет расширения)")
                    
    except PermissionError:
        print(f"{prefix}{'└── ' if is_last else '├── '}🔒 Нет доступа")
    except Exception as e:
        print(f"{prefix}{'└── ' if is_last else '├── '}❌ Ошибка: {str(e)}")


def get_file_icon(extension):
    """Возвращает иконку в зависимости от расширения файла"""
    icons = {
        # Изображения
        '.jpg': '🖼️', '.jpeg': '🖼️', '.png': '🖼️', '.gif': '🖼️', 
        '.bmp': '🖼️', '.svg': '🖼️', '.ico': '🖼️', '.webp': '🖼️',
        
        # Документы
        '.txt': '📄', '.md': '📝', '.pdf': '📕', '.doc': '📘', 
        '.docx': '📘', '.xls': '📊', '.xlsx': '📊', '.ppt': '📽️',
        '.pptx': '📽️', '.csv': '📊',
        
        # Код
        '.py': '🐍', '.js': '📜', '.html': '🌐', '.css': '🎨',
        '.java': '☕', '.cpp': '⚙️', '.c': '⚙️', '.h': '⚙️',
        '.php': '🐘', '.rb': '💎', '.go': '🔵', '.rs': '🦀',
        '.ts': '📘', '.jsx': '⚛️', '.tsx': '⚛️', '.json': '📋',
        '.xml': '📋', '.yml': '📋', '.yaml': '📋', '.toml': '📋',
        '.ini': '📋', '.cfg': '📋',
        
        # Архивы
        '.zip': '📦', '.rar': '📦', '.7z': '📦', '.tar': '📦',
        '.gz': '📦', '.bz2': '📦',
        
        # Медиа
        '.mp3': '🎵', '.wav': '🎵', '.flac': '🎵', '.aac': '🎵',
        '.mp4': '🎬', '.avi': '🎬', '.mkv': '🎬', '.mov': '🎬',
        '.webm': '🎬',
        
        # Шрифты
        '.ttf': '🔤', '.otf': '🔤', '.woff': '🔤', '.woff2': '🔤',
        
        # Системные
        '.exe': '⚡', '.dll': '🔧', '.so': '🔧', '.dylib': '🔧',
        '.sh': '💻', '.bat': '💻', '.cmd': '💻', '.ps1': '💻',
        
        # Базы данных
        '.sql': '🗄️', '.db': '🗄️', '.sqlite': '🗄️',
    }
    
    return icons.get(extension, '📄')


def count_items(directory, max_depth=None, current_depth=0):
    """Подсчитывает количество папок и файлов с учетом максимальной глубины"""
    folders = 0
    files = 0
    
    if max_depth is not None and current_depth > max_depth:
        return 0, 0
    
    try:
        items = os.listdir(directory)
        
        for item in items:
            full_path = os.path.join(directory, item)
            
            if os.path.isdir(full_path):
                folders += 1
                sub_folders, sub_files = count_items(full_path, max_depth, current_depth + 1)
                folders += sub_folders
                files += sub_files
            elif os.path.isfile(full_path):
                files += 1
                
    except PermissionError:
        pass
    
    return folders, files


def main():
    print("=" * 70)
    print("📂 ПРОГРАММА ДЛЯ ОТОБРАЖЕНИЯ ИЕРАРХИЧЕСКОЙ СТРУКТУРЫ ПАПОК")
    print("=" * 70)
    
    while True:
        # Запрашиваем путь к папке
        print("\n📌 Введите путь к папке:")
        print("   (можно перетащить папку в консоль или вставить путь)")
        print("   (для выхода введите 'exit', 'quit' или 'выход')")
        folder_path = input("\n>>> ").strip()
        
        # Проверяем, хочет ли пользователь выйти
        if folder_path.lower() in ['exit', 'quit', 'выход']:
            print("\n👋 До свидания!")
            break
        
        # Убираем кавычки, если они есть (при перетаскивании файла)
        folder_path = folder_path.strip('"').strip("'")
        
        # Проверяем существование пути
        if not os.path.exists(folder_path):
            print(f"\n❌ Ошибка: Путь '{folder_path}' не существует!")
            continue
        
        if not os.path.isdir(folder_path):
            print(f"\n❌ Ошибка: '{folder_path}' не является папкой!")
            continue
        
        # Запрашиваем максимальную глубину
        print("\n📏 Введите максимальную глубину просмотра:")
        print("   (Enter - без ограничений, 1 - только содержимое корневой папки)")
        depth_input = input(">>> ").strip()
        
        max_depth = None
        if depth_input:
            try:
                max_depth = int(depth_input)
                if max_depth < 1:
                    print("⚠️  Глубина должна быть больше 0. Установлено без ограничений.")
                    max_depth = None
            except ValueError:
                print("⚠️  Некорректное значение. Установлено без ограничений.")
        
        # Получаем статистику
        total_folders, total_files = count_items(folder_path, max_depth)
        
        print("\n" + "=" * 70)
        print("📊 СТАТИСТИКА:")
        print(f"   📁 Всего папок: {total_folders}")
        print(f"   📄 Всего файлов: {total_files}")
        print(f"   📦 Всего элементов: {total_folders + total_files}")
        if max_depth is not None:
            print(f"   📏 Максимальная глубина: {max_depth}")
        print("=" * 70)
        
        print("\n📂 ИЕРАРХИЧЕСКАЯ СТРУКТУРА:\n")
        
        # Выводим структуру
        print_tree(folder_path, max_depth=max_depth)
        
        print("\n" + "=" * 70)
        
        # Спрашиваем, хочет ли пользователь продолжить
        print("\n🔄 Хотите посмотреть другую папку? (да/нет)")
        again = input(">>> ").strip().lower()
        if again not in ['да', 'yes', 'y', 'д', 'lf', 'нуы']:
            print("\n👋 До свидания!")
            break


if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print("\n\n👋 Программа прервана пользователем. До свидания!")