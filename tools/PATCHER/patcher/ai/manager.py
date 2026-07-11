from __future__ import annotations

from dataclasses import dataclass
from pathlib import Path

@dataclass(slots=True)
class ApiKey:
    key: str
    name: str = "default"

class AIManager:
    def __init__(self) -> None:
        self.keys: list[ApiKey] = []
        self.checked: set[str] = set()

    def load_keys(self, path: Path) -> list[ApiKey]:
        if not path.exists():
            return []
        keys: list[ApiKey] = []
        for line in path.read_text(encoding='utf-8').splitlines():
            raw = line.strip()
            if not raw:
                continue
            if '(' in raw and ')' in raw:
                key, name = raw.split('(', 1)
                keys.append(ApiKey(key=''.join(key.split()), name=name.split(')', 1)[0].strip() or 'default'))
            else:
                keys.append(ApiKey(key=''.join(raw.split())))
        self.keys = keys
        return keys
