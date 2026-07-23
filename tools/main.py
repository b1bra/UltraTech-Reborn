import os
import zipfile

print("=" * 60)
print("Поиск слова в Minecraft Forge модах (1.7.10)")
print("=" * 60)

mods_folder = input("Путь к папке с модами: ").strip().strip('"')

while not os.path.isdir(mods_folder):
    print("Такой папки не существует.")
    mods_folder = input("Введите путь ещё раз: ").strip().strip('"')

search_word = input("Введите слово для поиска: ").lower()

print("\nПоиск...\n")


def get_mod_name(jar):
    """Получение имени мода из mcmod.info"""
    for file in jar.namelist():
        if file.endswith("mcmod.info"):
            try:
                text = jar.read(file).decode("utf-8", errors="ignore")

                for line in text.splitlines():
                    if '"name"' in line:
                        value = line.split(":", 1)[1]
                        return value.replace('"', "").replace(",", "").strip()

            except:
                pass

    return None


mods_found = 0

for filename in sorted(os.listdir(mods_folder)):
    if not filename.lower().endswith(".jar"):
        continue

    jar_path = os.path.join(mods_folder, filename)

    try:
        with zipfile.ZipFile(jar_path, "r") as jar:

            mod_name = get_mod_name(jar)
            if mod_name is None:
                mod_name = filename

            matches = []

            for member in jar.namelist():
                try:
                    data = jar.read(member)

                    if member.endswith(".class"):
                        text = data.decode("latin1", errors="ignore")
                    else:
                        text = data.decode("utf-8", errors="ignore")

                    if search_word in text.lower():
                        matches.append(member)

                except:
                    pass

            if matches:
                mods_found += 1

                print("=" * 80)
                print(f"Мод: {mod_name}")
                print(f"JAR: {filename}")
                print(f"Совпадений: {len(matches)}")
                print()

                for match in matches:
                    print("  •", match)

                print()

    except Exception as e:
        print(f"Ошибка при открытии {filename}: {e}")

print("=" * 60)
print(f"Найдено модов: {mods_found}")
print("Поиск завершён.")
input("\nНажмите Enter для выхода...")