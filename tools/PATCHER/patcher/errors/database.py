from __future__ import annotations

import json
from pathlib import Path
from typing import Any

class ErrorDatabase:
    def __init__(self, root: Path) -> None:
        self.root = root
        self.entries: dict[str, dict[str, Any]] = {}

    def load(self) -> None:
        self.entries.clear()
        for path in sorted(self.root.glob("*.json")):
            self.entries[path.stem] = json.loads(path.read_text(encoding="utf-8"))

    def get(self, key: str) -> dict[str, Any] | None:
        return self.entries.get(key)
