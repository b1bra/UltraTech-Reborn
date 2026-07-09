"""World discovery and level.dat analysis."""

from __future__ import annotations

import logging
from dataclasses import dataclass, field
from datetime import datetime, timezone
from pathlib import Path
from typing import Any

from .nbt_parser import NBTEntry, SuspiciousNBT, collect_suspicious, flatten_nbt, read_nbt_file, short_value, unwrap_root

LOGGER = logging.getLogger(__name__)
WORLD_MARKERS = ("level.dat", "region", "playerdata", "data")


@dataclass(slots=True)
class WorldInfo:
    path: Path
    name: str
    version: str = "unknown"
    created: str = "unknown"
    seed: str = "unknown"
    difficulty: str = "unknown"
    game_mode: str = "unknown"
    game_rules: dict[str, Any] = field(default_factory=dict)
    level_entries: list[NBTEntry] = field(default_factory=list)
    suspicious: list[SuspiciousNBT] = field(default_factory=list)
    errors: list[str] = field(default_factory=list)


def discover_worlds(saves_path: Path) -> list[Path]:
    """Find candidate Minecraft saves by checking common world markers."""

    worlds: list[Path] = []
    for child in sorted(saves_path.iterdir()):
        if not child.is_dir():
            continue
        markers = [marker for marker in WORLD_MARKERS if (child / marker).exists()]
        if "level.dat" in markers and len(markers) >= 2:
            worlds.append(child)
        elif len(markers) >= 2:
            worlds.append(child)
    return worlds


def analyze_level_dat(world_path: Path, logger: logging.Logger | None = None) -> WorldInfo:
    """Extract general world information and full level.dat tag paths."""

    log = logger or LOGGER
    info = WorldInfo(path=world_path, name=world_path.name)
    level_path = world_path / "level.dat"
    if not level_path.exists():
        info.errors.append("level.dat not found")
        return info

    nbt_file = read_nbt_file(level_path, log)
    if nbt_file is None:
        info.errors.append("level.dat could not be read")
        return info

    root = unwrap_root(nbt_file)
    data = root.get("Data", root) if isinstance(root, dict) else root
    info.name = str(data.get("LevelName", world_path.name)) if isinstance(data, dict) else world_path.name
    if isinstance(data, dict):
        version = data.get("Version", {})
        info.version = str(version.get("Name", version)) if isinstance(version, dict) else str(version)
        info.seed = short_value(data.get("RandomSeed", "unknown"))
        info.difficulty = short_value(data.get("Difficulty", "unknown"))
        info.game_mode = short_value(data.get("GameType", "unknown"))
        info.game_rules = dict(data.get("GameRules", {})) if isinstance(data.get("GameRules", {}), dict) else {}
        created = data.get("LastPlayed") or data.get("Time")
        info.created = _format_minecraft_time(created)
    info.level_entries = flatten_nbt(root, "world.level")
    info.suspicious = collect_suspicious(info.level_entries)
    return info


def _format_minecraft_time(value: Any) -> str:
    if value is None:
        return "unknown"
    try:
        number = int(value)
        if number > 10_000_000_000:
            return datetime.fromtimestamp(number / 1000, tz=timezone.utc).isoformat()
        return f"game time {number} ticks"
    except Exception:  # noqa: BLE001
        return str(value)
