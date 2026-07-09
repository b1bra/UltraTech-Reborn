"""Adapters around the existing ModScanner and UltraSaveInspector code."""

from __future__ import annotations

import importlib.util
import sys
from pathlib import Path
from types import ModuleType
from typing import Callable

from logger import ScannerSessionLogger
from markdown_utils import text_block


ROOT_DIR = Path(__file__).resolve().parents[2]
MODSCANNER_DIR = ROOT_DIR / "tools" / "modscanner"
SAVE_INSPECTOR_DIR = ROOT_DIR / "tools" / "UltraSaveInspector"


def _load_module(path: Path, name: str) -> ModuleType:
    spec = importlib.util.spec_from_file_location(name, path)
    if spec is None or spec.loader is None:
        raise RuntimeError(f"Cannot load module: {path}")
    module = importlib.util.module_from_spec(spec)
    sys.modules[name] = module
    spec.loader.exec_module(module)
    return module


def _prepare_import_paths() -> None:
    for path in (Path(__file__).resolve().parent, MODSCANNER_DIR, SAVE_INSPECTOR_DIR):
        text = str(path)
        if text not in sys.path:
            sys.path.insert(0, text)


def _patch_docs_path(module: ModuleType, output_dir: Path) -> None:
    def get_docs_path() -> str:
        output_dir.mkdir(parents=True, exist_ok=True)
        return str(output_dir)

    module.get_docs_path = get_docs_path


def discover_mods_folder(path: Path) -> Path:
    """Return the most likely folder containing mod JARs."""

    if path.name.lower() == "mods":
        return path
    candidate = path / "mods"
    if candidate.is_dir():
        return candidate
    return path


def discover_saves_folder(path: Path) -> Path | None:
    """Return a saves folder if the modpack contains one."""

    if path.name.lower() == "saves":
        return path
    candidate = path / "saves"
    if candidate.is_dir():
        return candidate
    if (path / "level.dat").exists():
        return path.parent
    return None


def analyze_modpack(
    modpack_path: Path,
    output_dir: Path,
    generate_graph: bool,
    logger: ScannerSessionLogger,
    progress: Callable[[int, str], None] | None = None,
) -> None:
    """Run all existing modpack analyzers into ``output_dir``."""

    _prepare_import_paths()
    output_dir.mkdir(parents=True, exist_ok=True)
    mods_folder = discover_mods_folder(modpack_path)
    if not mods_folder.is_dir():
        raise FileNotFoundError(f"Modpack path does not exist: {modpack_path}")

    progress = progress or (lambda _percent, _message: None)
    logger.banner("Modpack analysis starting")
    logger.info(f"Modpack path: {modpack_path}")
    logger.info(f"Mods folder: {mods_folder}")

    modscanner = _load_module(MODSCANNER_DIR / "modscannerv7.py", "scan_modscannerv7")
    _patch_docs_path(modscanner, output_dir)
    modscanner.logger = logger
    progress(8, "Loading mod scanners")
    scanners = modscanner.load_scanners()

    progress(14, "Finding JAR files")
    jars = modscanner.find_jars(str(mods_folder))
    if not jars:
        raise RuntimeError(f"No JAR files found in {mods_folder}")
    logger.info(f"JAR files found: {len(jars)}")

    progress(22, "Running surface scan")
    surface = modscanner.surface_scan(jars)
    progress(42, "Running class/resource/recipe scanners")
    scan_results = modscanner.run_scanners(scanners, jars)
    progress(58, "Merging mod data")
    mods = modscanner.merge_scans(surface, scan_results["class"], scan_results["resource"], scan_results["recipe"])

    progress(66, "Writing ModScanner documentation")
    modscanner.create_modlist(mods)
    modscanner.create_migration_report(mods)
    modscanner.create_dependencies_report(mods)
    modscanner.create_graph_json(mods)
    _write_unified_mod_docs(output_dir, mods, scan_results, surface, logger)

    progress(72, "Running network packet documentation")
    packet_logger = _load_module(MODSCANNER_DIR / "modpacketlogger_lite.py", "scan_modpacketlogger_lite")
    _patch_docs_path(packet_logger, output_dir)
    packet_logger.logger = logger
    packet_results = [packet_logger.analyze_jar(jar_path) for jar_path in jars]
    packet_logger.create_networklog(packet_results, str(mods_folder))

    if generate_graph:
        progress(80, "Generating dependency graph")
        _generate_dependency_graph(output_dir, logger)
    else:
        logger.info("Dependency graph generation skipped by option")

    saves_folder = discover_saves_folder(modpack_path)
    if saves_folder is not None and saves_folder.is_dir():
        progress(86, "Running UltraSaveInspector")
        analyze_saves(saves_folder, output_dir, logger)
    else:
        logger.warning("No saves folder found; UltraSaveInspector skipped")

    progress(94, "Finalizing modpack documentation")
    logger.success("Modpack analysis complete")


def analyze_saves(saves_path: Path, output_dir: Path, logger: ScannerSessionLogger) -> Path:
    """Run UltraSaveInspector without console prompts."""

    _prepare_import_paths()
    module = _load_module(SAVE_INSPECTOR_DIR / "main.py", "scan_ultrasaveinspector")
    module.OUTPUT_DIR = output_dir
    module.REPORT_PATH = output_dir / "networkNBTsaves.md"
    module.LOG_PATH = output_dir / "UltraSaveInspector.log"
    worlds = module.discover_worlds(saves_path)
    logger.info(f"UltraSaveInspector saves path: {saves_path}")
    logger.info(f"UltraSaveInspector worlds found: {len(worlds)}")
    results = []
    for index, world_path in enumerate(worlds, start=1):
        logger.info(f"Analyzing world {index}/{len(worlds)}: {world_path}")
        world = module.analyze_level_dat(world_path, logger)
        players = module.analyze_playerdata(world_path, logger)
        data_entries, data_suspicious = module.analyze_data_folder(world_path, logger)
        tiles = module.analyze_tile_entities(world_path, logger)
        results.append((world, players, data_entries, data_suspicious, tiles))
    module.REPORT_PATH.write_text(_normalize_markdown_lists(module.generate_report(results)), encoding="utf-8")
    _write_save_summary(output_dir, results, logger)
    logger.log_file(module.REPORT_PATH, "UltraSaveInspector report")
    return module.REPORT_PATH


def _write_unified_mod_docs(
    output_dir: Path,
    mods: list[dict],
    scan_results: dict[str, dict],
    surface: dict[str, dict],
    logger: ScannerSessionLogger,
) -> None:
    """Write expanded documentation from class/resource/recipe scanner data."""

    class_data = scan_results.get("class", {})
    resource_data = scan_results.get("resource", {})
    recipe_data = scan_results.get("recipe", {})
    _write_modpack_index(output_dir, mods, surface, logger)
    _write_class_report(output_dir, class_data, logger)
    _write_resource_report(output_dir, resource_data, logger)
    _write_recipe_report(output_dir, recipe_data, logger)
    _write_per_mod_deep_reports(output_dir, mods, class_data, resource_data, recipe_data, logger)


def _write_modpack_index(output_dir: Path, mods: list[dict], surface: dict[str, dict], logger: ScannerSessionLogger) -> None:
    path = output_dir / "modpack_technical_overview.md"
    total_size = round(sum(float(mod.get("size_mb", 0) or 0) for mod in mods), 2)
    hard = sum(1 for mod in mods if mod.get("difficulty") == "Hard")
    normal = sum(1 for mod in mods if mod.get("difficulty") == "Normal")
    easy = sum(1 for mod in mods if mod.get("difficulty") == "Easy")
    lines = [
        "# Modpack Technical Overview",
        "",
        "## Summary",
        "",
        text_block(
            [
                f"Total mods: {len(mods)}",
                f"Total size: {total_size} MB",
                f"Hard migration: {hard}",
                f"Normal migration: {normal}",
                f"Easy migration: {easy}",
                f"Surface records: {len(surface)}",
            ]
        ),
        "",
        "## Mod Inventory",
        "",
        text_block(
            [
                f"{mod.get('file')} | modid={mod.get('modid')} | version={mod.get('version')} | type={', '.join(mod.get('type', []))} | difficulty={mod.get('difficulty')} | score={mod.get('score')}"
                for mod in mods
            ]
        ),
        "",
        "## Dependency Edges",
        "",
        text_block(
            [
                f"{mod.get('modid') or mod.get('name')} -> {dependency}"
                for mod in mods
                for dependency in (mod.get("dependencies") or ["No dependencies detected"])
            ]
        ),
    ]
    path.write_text("\n".join(lines), encoding="utf-8")
    logger.log_file(path, "Unified modpack overview")


def _write_class_report(output_dir: Path, class_data: dict[str, dict], logger: ScannerSessionLogger) -> None:
    path = output_dir / "class_scanner_full.md"
    lines = ["# Class Scanner Full Analysis", ""]
    for file_name, data in sorted(class_data.items()):
        lines.extend(
            [
                f"## {file_name}",
                "",
                text_block(
                    [
                        f"Files in archive: {data.get('files', 0)}",
                        f"Class files: {data.get('class_files', 0)}",
                        f"Packages: {data.get('packages', 0)}",
                        f"Estimated methods: {data.get('estimated_methods', 0)}",
                        f"Difficulty: {data.get('difficulty', 'Unknown')}",
                        f"Score: {data.get('score', 0)}",
                        f"Client percent: {data.get('client_percent', 0)}",
                        f"Server percent: {data.get('server_percent', 0)}",
                        f"Creative tab hits: {data.get('creative', 0)}",
                    ]
                ),
                "Detected content:",
                "",
                text_block(_format_nested_dict(data.get("content", {}))),
                "Registration counters:",
                "",
                text_block(_format_nested_dict(data.get("registration", {}))),
                "GUI counters:",
                "",
                text_block(_format_nested_dict(data.get("gui", {}))),
                "Network counters:",
                "",
                text_block(_format_nested_dict(data.get("network", {}))),
                "Migration detections:",
                "",
                text_block(data.get("detections") or ["No detections"]),
                "Migration reasons:",
                "",
                text_block(data.get("reasons") or ["No reasons"]),
                "Important classes:",
                "",
                text_block(data.get("important_classes") or ["No classes detected"]),
                "Detected content names:",
                "",
                text_block(_format_nested_dict(data.get("content_lists", {}))),
                "",
            ]
        )
    path.write_text("\n".join(lines), encoding="utf-8")
    logger.log_file(path, "Class scanner full report")


def _write_resource_report(output_dir: Path, resource_data: dict[str, dict], logger: ScannerSessionLogger) -> None:
    path = output_dir / "resource_scanner_full.md"
    lines = ["# Resource Scanner Full Analysis", ""]
    for file_name, data in sorted(resource_data.items()):
        lines.extend(
            [
                f"## {file_name}",
                "",
                text_block(
                    [
                        f"Resource files: {data.get('resource_files', 0)}",
                        f"Textures: {data.get('textures', 0)}",
                        f"Block textures: {data.get('block_textures', 0)}",
                        f"Item textures: {data.get('item_textures', 0)}",
                        f"Models: {data.get('models', 0)}",
                        f"Block models: {data.get('block_models', 0)}",
                        f"Item models: {data.get('item_models', 0)}",
                        f"Blockstates: {data.get('blockstates', 0)}",
                        f"Languages: {data.get('languages', 0)}",
                        f"Sounds: {data.get('sounds', 0)}",
                        f"GUI resources: {data.get('gui_resources', 0)}",
                        f"Recipes: {data.get('recipes', 0)}",
                        f"Advancements: {data.get('advancements', 0)}",
                        f"JSON files: {data.get('json_files', 0)}",
                        f"Total resource size: {data.get('total_resource_size_mb', 0)} MB",
                        f"Resource complexity: {data.get('resource_complexity', 0)}",
                    ]
                ),
                "Important paths:",
                "",
                text_block(data.get("important_paths") or ["No important paths"]),
                "Large files:",
                "",
                text_block(_format_nested_dict(data.get("large_files", []))),
                "",
            ]
        )
    path.write_text("\n".join(lines), encoding="utf-8")
    logger.log_file(path, "Resource scanner full report")


def _write_recipe_report(output_dir: Path, recipe_data: dict[str, dict], logger: ScannerSessionLogger) -> None:
    path = output_dir / "recipe_scanner_full.md"
    lines = ["# Recipe Scanner Full Analysis", ""]
    for file_name, data in sorted(recipe_data.items()):
        lines.extend(
            [
                f"## {file_name}",
                "",
                text_block(
                    [
                        f"Total recipes: {data.get('total_recipes', 0)}",
                        f"JSON recipes: {data.get('json_recipes', 0)}",
                        f"Code recipes: {data.get('code_recipes', 0)}",
                        f"Crafting: {data.get('crafting', 0)}",
                        f"Ore recipes: {data.get('ore_recipes', 0)}",
                        f"Smelting: {data.get('smelting', 0)}",
                        f"Special: {data.get('special', 0)}",
                        f"JSON files: {data.get('json_files', 0)}",
                        f"Class files scanned: {data.get('class_files_scanned', 0)}",
                    ]
                ),
                "Recipe classes:",
                "",
                text_block(data.get("recipe_classes") or ["No recipe classes"]),
                "Recipe paths:",
                "",
                text_block(data.get("recipe_paths") or ["No recipe paths"]),
                "Ingredients:",
                "",
                text_block(_format_nested_dict(data.get("ingredients", {}))),
                "Outputs:",
                "",
                text_block(_format_nested_dict(data.get("outputs", {}))),
                "",
            ]
        )
    path.write_text("\n".join(lines), encoding="utf-8")
    logger.log_file(path, "Recipe scanner full report")


def _write_per_mod_deep_reports(
    output_dir: Path,
    mods: list[dict],
    class_data: dict[str, dict],
    resource_data: dict[str, dict],
    recipe_data: dict[str, dict],
    logger: ScannerSessionLogger,
) -> None:
    folder = output_dir / "mods"
    folder.mkdir(parents=True, exist_ok=True)
    for mod in mods:
        file_name = str(mod.get("file", "unknown.jar"))
        stem = "".join(char if char.isalnum() or char in "._-" else "_" for char in Path(file_name).stem)
        path = folder / f"{stem}.md"
        lines = [
            f"# {mod.get('name', file_name)}",
            "",
            "## Surface",
            "",
            text_block(_format_nested_dict(mod)),
            "",
            "## Class Scanner",
            "",
            text_block(_format_nested_dict(class_data.get(file_name, {}))),
            "",
            "## Resource Scanner",
            "",
            text_block(_format_nested_dict(resource_data.get(file_name, {}))),
            "",
            "## Recipe Scanner",
            "",
            text_block(_format_nested_dict(recipe_data.get(file_name, {}))),
        ]
        path.write_text("\n".join(lines), encoding="utf-8")
    logger.info(f"Per-mod deep reports written: {len(mods)}")


def _write_save_summary(output_dir: Path, results: list[tuple], logger: ScannerSessionLogger) -> None:
    path = output_dir / "save_inspector_summary.md"
    lines = ["# UltraSaveInspector Summary", ""]
    for world, players, data_entries, data_suspicious, tiles in results:
        lines.extend(
            [
                f"## {world.name}",
                "",
                text_block(
                    [
                        f"Path: {world.path}",
                        f"Version: {world.version}",
                        f"Seed: {world.seed}",
                        f"Created: {world.created}",
                        f"Difficulty: {world.difficulty}",
                        f"Game mode: {world.game_mode}",
                        f"Level tags: {len(world.level_entries)}",
                        f"Players: {len(players)}",
                        f"World data tags: {len(data_entries)}",
                        f"Suspicious world data: {len(data_suspicious)}",
                        f"TileEntities: {len(tiles)}",
                    ]
                ),
                "Game rules:",
                "",
                text_block(_format_nested_dict(world.game_rules)),
                "Players:",
                "",
                text_block(
                    [
                        f"{player.uuid} | name={player.name} | position={player.position} | inventory={player.inventory} | effects={player.effects}"
                        for player in players
                    ]
                    or ["No players"]
                ),
                "TileEntity inventory:",
                "",
                text_block([f"{tile.id} | {tile.position} | {tile.region_file}" for tile in tiles] or ["No tile entities"]),
                "",
            ]
        )
    path.write_text("\n".join(lines), encoding="utf-8")
    logger.log_file(path, "UltraSaveInspector summary")


def _format_nested_dict(value: object, prefix: str = "") -> list[str]:
    lines: list[str] = []
    if isinstance(value, dict):
        for key, child in value.items():
            current = f"{prefix}.{key}" if prefix else str(key)
            if isinstance(child, (dict, list, tuple)):
                lines.extend(_format_nested_dict(child, current))
            else:
                lines.append(f"{current}: {child}")
    elif isinstance(value, (list, tuple)):
        if not value:
            lines.append(f"{prefix}: []" if prefix else "[]")
        for index, child in enumerate(value):
            current = f"{prefix}[{index}]" if prefix else f"[{index}]"
            if isinstance(child, (dict, list, tuple)):
                lines.extend(_format_nested_dict(child, current))
            else:
                lines.append(f"{current}: {child}")
    else:
        lines.append(f"{prefix}: {value}" if prefix else str(value))
    return lines or ["No data"]


def _normalize_markdown_lists(markdown: str) -> str:
    """Move legacy technical bullet lists into fenced text blocks."""

    output: list[str] = []
    buffer: list[str] = []

    def flush_buffer() -> None:
        if not buffer:
            return
        output.append("```text")
        output.extend(buffer)
        output.append("```")
        buffer.clear()

    for line in markdown.splitlines():
        stripped = line.lstrip()
        if stripped.startswith("- "):
            buffer.append(stripped[2:])
            continue
        if buffer and (line.startswith("  ") or line.startswith("    ")):
            buffer.append(line.strip())
            continue
        flush_buffer()
        output.append(line)
    flush_buffer()
    return "\n".join(output) + "\n"


def _generate_dependency_graph(output_dir: Path, logger: ScannerSessionLogger) -> None:
    graph_module = _load_module(MODSCANNER_DIR / "graph_generatorv6.py", "scan_graph_generatorv6")
    graph_module.logger = logger

    def find_paths() -> tuple[str, str, str, str]:
        return (
            str(output_dir / "mod_graph.json"),
            str(output_dir / "full_graph.png"),
            str(output_dir / "full_graph_labeled.png"),
            str(output_dir / "migration_overview.png"),
        )

    graph_module.find_paths = find_paths
    graph_module.main()
    for file_name in ("full_graph.png", "full_graph_labeled.png", "migration_overview.png"):
        path = output_dir / file_name
        if path.exists():
            logger.log_file(path, "Dependency graph")
