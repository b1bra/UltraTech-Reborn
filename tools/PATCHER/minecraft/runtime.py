"""Minecraft installation model and discovery helpers."""
from __future__ import annotations
from dataclasses import dataclass, field
from pathlib import Path
import json

@dataclass
class MinecraftVersion:
    id: str
    path: Path
    main_class: str = ""
    libraries: list[str] = field(default_factory=list)

class MinecraftDatabase:
    def discover_versions(self, minecraft_dir: Path) -> list[MinecraftVersion]:
        versions_dir = minecraft_dir / "versions"
        versions: list[MinecraftVersion] = []
        if not versions_dir.exists():
            return versions
        for version_json in versions_dir.glob("*/*.json"):
            try:
                data = json.loads(version_json.read_text(encoding="utf-8"))
            except Exception:
                data = {"id": version_json.parent.name}
            libs = [item.get("name", "") for item in data.get("libraries", []) if isinstance(item, dict)]
            versions.append(MinecraftVersion(data.get("id", version_json.parent.name), version_json.parent, data.get("mainClass", ""), libs))
        return versions
