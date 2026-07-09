"""Playerdata analyzer."""

from __future__ import annotations

import logging
from dataclasses import dataclass, field
from pathlib import Path
from typing import Any

from .nbt_parser import NBTEntry, SuspiciousNBT, collect_suspicious, flatten_nbt, read_nbt_file, short_value, unwrap_root

LOGGER = logging.getLogger(__name__)


@dataclass(slots=True)
class PlayerAnalysis:
    uuid: str
    file: Path
    name: str = "unknown"
    position: str = "unknown"
    inventory: str = "unknown"
    armor: str = "unknown"
    experience: str = "unknown"
    effects: str = "unknown"
    statistics: str = "unknown"
    entries: list[NBTEntry] = field(default_factory=list)
    suspicious: list[SuspiciousNBT] = field(default_factory=list)
    errors: list[str] = field(default_factory=list)


def analyze_playerdata(world_path: Path, logger: logging.Logger | None = None) -> list[PlayerAnalysis]:
    log = logger or LOGGER
    player_dir = world_path / "playerdata"
    if not player_dir.exists():
        return []
    players: list[PlayerAnalysis] = []
    for file_path in sorted(player_dir.glob("*.dat")):
        analysis = PlayerAnalysis(uuid=file_path.stem, file=file_path)
        nbt_file = read_nbt_file(file_path, log)
        if nbt_file is None:
            analysis.errors.append("player NBT could not be read")
            players.append(analysis)
            continue
        root = unwrap_root(nbt_file)
        if isinstance(root, dict):
            analysis.name = short_value(root.get("bukkit", {}).get("lastKnownName", "unknown")) if isinstance(root.get("bukkit"), dict) else "unknown"
            analysis.position = short_value(root.get("Pos", "unknown"))
            analysis.inventory = short_value(root.get("Inventory", "unknown"))
            analysis.armor = short_value(root.get("ArmorItems", root.get("equipment", "unknown")))
            analysis.experience = short_value({key: root.get(key) for key in ("XpLevel", "XpP", "XpTotal") if key in root})
            analysis.effects = short_value(root.get("ActiveEffects", root.get("active_effects", "unknown")))
            analysis.statistics = short_value(root.get("Stats", root.get("stat", "unknown")))
        analysis.entries = flatten_nbt(root, f"player.{analysis.uuid}")
        analysis.suspicious = collect_suspicious(analysis.entries)
        players.append(analysis)
    return players
