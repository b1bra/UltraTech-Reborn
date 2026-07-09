"""Data model and progress persistence for the Minecraft mod port tracker."""

from __future__ import annotations

from dataclasses import dataclass, field
from pathlib import Path
import json


@dataclass(slots=True)
class ModInfo:
    """Normalized information about a single Minecraft mod."""

    raw_name: str
    display_name: str
    description: str = ""
    version: str = ""
    category: str = ""
    detected_type: str = "Unknown"
    dependencies: list[str] = field(default_factory=list)
    coremod: bool = False
    asm: bool = False
    api: bool = False
    completed: bool = False

    @property
    def stable_id(self) -> str:
        """Return a deterministic key used in progress.json."""
        base = self.raw_name or self.display_name
        return base.strip().lower().replace(" ", "-")


class ProgressStore:
    """Small JSON-backed storage for completed/not-completed flags."""

    def __init__(self, path: Path) -> None:
        self.path = path
        self._data: dict[str, bool] = {}
        self.load()

    def load(self) -> None:
        if not self.path.exists():
            self._data = {}
            return
        try:
            raw = json.loads(self.path.read_text(encoding="utf-8"))
        except (OSError, json.JSONDecodeError):
            self._data = {}
            return
        self._data = {str(key): bool(value) for key, value in raw.items()}

    def apply(self, mods: list[ModInfo]) -> None:
        for mod in mods:
            mod.completed = self._data.get(mod.stable_id, False)

    def set_completed(self, mod: ModInfo, completed: bool) -> None:
        mod.completed = completed
        self._data[mod.stable_id] = completed
        self.save()

    def save(self) -> None:
        self.path.parent.mkdir(parents=True, exist_ok=True)
        payload = json.dumps(self._data, indent=2, ensure_ascii=False, sort_keys=True)
        self.path.write_text(payload + "\n", encoding="utf-8")
