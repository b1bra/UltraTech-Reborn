from __future__ import annotations

import json
from dataclasses import asdict, dataclass, field
from pathlib import Path

@dataclass(slots=True)
class LauncherConfig:
    name: str
    path: str
    icon: str = "🎮"

@dataclass(slots=True)
class AppConfig:
    launchers: list[LauncherConfig] = field(default_factory=list)
    selected_launcher: str | None = None
    api_file: str = ""
    tools: dict[str, str] = field(default_factory=dict)
    feature_flags: dict[str, bool] = field(default_factory=dict)

class ConfigManager:
    def __init__(self, path: Path) -> None:
        self.path = path
        self.path.parent.mkdir(parents=True, exist_ok=True)
        self.config = self.load()

    def load(self) -> AppConfig:
        if not self.path.exists():
            return AppConfig()
        data = json.loads(self.path.read_text(encoding="utf-8"))
        data["launchers"] = [LauncherConfig(**item) for item in data.get("launchers", [])]
        return AppConfig(**data)

    def save(self) -> None:
        self.path.write_text(json.dumps(asdict(self.config), ensure_ascii=False, indent=2), encoding="utf-8")

    def add_launcher(self, path: Path) -> bool:
        normalized = str(path.resolve())
        if any(item.path == normalized for item in self.config.launchers):
            return False
        self.config.launchers.append(LauncherConfig(name=path.name or normalized, path=normalized))
        self.save()
        return True
