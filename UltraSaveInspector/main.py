"""UltraSaveInspector entry point."""

from __future__ import annotations

import logging
from pathlib import Path
from typing import Iterable

from analyzer.nbt_parser import NBTEntry, SuspiciousNBT, collect_suspicious, flatten_nbt, read_nbt_file, unwrap_root
from analyzer.player import PlayerAnalysis, analyze_playerdata
from analyzer.tileentity import TileEntityAnalysis, analyze_tile_entities
from analyzer.world import WorldInfo, analyze_level_dat, discover_worlds

APP_DIR = Path(__file__).resolve().parent
OUTPUT_DIR = APP_DIR / "output"
REPORT_PATH = OUTPUT_DIR / "networkNBTsaves.md"
LOG_PATH = OUTPUT_DIR / "UltraSaveInspector.log"


def configure_logging() -> None:
    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)
    logging.basicConfig(
        level=logging.INFO,
        format="%(asctime)s [%(levelname)s] %(name)s: %(message)s",
        handlers=[logging.FileHandler(LOG_PATH, encoding="utf-8"), logging.StreamHandler()],
    )


def request_saves_path() -> Path:
    while True:
        raw = input("Введите путь к папке saves:\n").strip().strip('"')
        path = Path(raw).expanduser()
        if path.exists() and path.is_dir():
            return path
        print(f"Ошибка: папка не существует: {path}")
        print("Укажите путь повторно.")


def analyze_data_folder(world_path: Path, logger: logging.Logger) -> tuple[list[NBTEntry], list[SuspiciousNBT]]:
    entries: list[NBTEntry] = []
    data_dir = world_path / "data"
    if not data_dir.exists():
        return entries, []
    files = sorted([*data_dir.glob("*.dat"), *data_dir.glob("*.dat_old")])
    for index, file_path in enumerate(files, start=1):
        print(f"  data/: {index}/{len(files)} {file_path.name}")
        nbt_file = read_nbt_file(file_path, logger)
        if nbt_file is None:
            continue
        root = unwrap_root(nbt_file)
        entries.extend(flatten_nbt(root, f"world.data.{file_path.stem}"))
    return entries, collect_suspicious(entries)


def generate_report(
    worlds: Iterable[tuple[WorldInfo, list[PlayerAnalysis], list[NBTEntry], list[SuspiciousNBT], list[TileEntityAnalysis]]]
) -> str:
    lines: list[str] = ["# Minecraft NBT Save Analysis", ""]
    for world, players, data_entries, data_suspicious, tiles in worlds:
        lines.extend([
            f"## World: {world.name}", "", "Общая информация:", "",
            f"- Path: `{world.path}`", f"- Версия: {world.version}", f"- Seed: {world.seed}",
            f"- Дата: {world.created}", f"- Difficulty: {world.difficulty}", f"- Game mode: {world.game_mode}", "",
            "### level.dat Full Tag Paths", "", "```text",
        ])
        lines.extend(_format_entries(world.level_entries))
        lines.extend(["```", "", "## Player Data", ""])
        for player in players:
            lines.extend([f"### Игрок: {player.name}", f"UUID: `{player.uuid}`", "", "Обнаруженные данные:", "", "```text"])
            lines.extend([
                f"Position: {player.position}", f"Inventory: {player.inventory}", f"Armor: {player.armor}",
                f"Experience: {player.experience}", f"Effects: {player.effects}", f"Statistics: {player.statistics}", "",
            ])
            lines.extend(_format_entries(player.entries))
            lines.extend(["```", ""])
        lines.extend(["## World data/", "", "```text"])
        lines.extend(_format_entries(data_entries))
        lines.extend(["```", "", "## TileEntities", "", "Список найденных TileEntity:", ""])
        for tile in tiles:
            lines.extend([f"- ID: `{tile.id}`", f"  - Position: {tile.position}", f"  - Region: `{tile.region_file.name}`", "  - NBT:", "    ```text"])
            lines.extend(["    " + line for line in _format_entries(tile.entries[:250])])
            lines.extend(["    ```"])
        suspicious = [*world.suspicious, *data_suspicious]
        for player in players:
            suspicious.extend(player.suspicious)
        for tile in tiles:
            suspicious.extend(tile.suspicious)
        lines.extend(["", "## Mod Data", "", "Подозрительные структуры:", ""])
        lines.extend(_format_suspicious(suspicious))
        lines.extend(["", "## Unknown NBT", "", "Пути неизвестных структур, которые могут принадлежать модам, перечислены в разделе Mod Data.", ""])
    return "\n".join(lines)


def _format_entries(entries: Iterable[NBTEntry]) -> list[str]:
    return [f"{entry.path}\n  Тип: {entry.tag_type}\n  Значение: {entry.value_preview}" for entry in entries]


def _format_suspicious(records: Iterable[SuspiciousNBT]) -> list[str]:
    lines = []
    seen = set()
    for record in records:
        key = (record.namespace, record.path)
        if key in seen:
            continue
        seen.add(key)
        lines.extend([f"- namespace: `{record.namespace}`", f"  - tag: `{record.tag}`", f"  - path: `{record.path}`", f"  - reason: {record.reason}", f"  - value: {record.value_preview}"])
    return lines or ["Не найдено."]


def main() -> None:
    configure_logging()
    logger = logging.getLogger("UltraSaveInspector")
    saves_path = request_saves_path()
    worlds = discover_worlds(saves_path)
    print(f"Найдено миров: {len(worlds)}")
    results = []
    for index, world_path in enumerate(worlds, start=1):
        print(f"Анализ мира {index}/{len(worlds)}: {world_path.name}")
        world = analyze_level_dat(world_path, logger)
        players = analyze_playerdata(world_path, logger)
        data_entries, data_suspicious = analyze_data_folder(world_path, logger)
        tiles = analyze_tile_entities(world_path, logger)
        results.append((world, players, data_entries, data_suspicious, tiles))
    REPORT_PATH.write_text(generate_report(results), encoding="utf-8")
    print(f"Отчет создан: {REPORT_PATH}")
    print(f"Лог ошибок: {LOG_PATH}")


if __name__ == "__main__":
    main()
