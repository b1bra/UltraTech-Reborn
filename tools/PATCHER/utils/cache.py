"""Small JSON cache used by scanner, decompiler and knowledge-base services."""
from __future__ import annotations
from dataclasses import dataclass
from pathlib import Path
from typing import Any
import json, time, hashlib

@dataclass
class CacheEntry:
    key: str
    value: Any
    created_at: float
    ttl: float

class JsonCache:
    def __init__(self, path: Path) -> None:
        self.path = path
        self.path.parent.mkdir(parents=True, exist_ok=True)
        self.entries: dict[str, CacheEntry] = {}
        self.load()

    def make_key(self, *parts: str) -> str:
        digest = hashlib.sha256("::".join(parts).encode("utf-8")).hexdigest()
        return digest

    def load(self) -> None:
        if not self.path.exists():
            return
        raw = json.loads(self.path.read_text(encoding="utf-8"))
        self.entries = {key: CacheEntry(key, item["value"], item["created_at"], item["ttl"]) for key, item in raw.items()}

    def save(self) -> None:
        payload = {key: {"value": entry.value, "created_at": entry.created_at, "ttl": entry.ttl} for key, entry in self.entries.items()}
        self.path.write_text(json.dumps(payload, ensure_ascii=False, indent=2), encoding="utf-8")

    def get(self, key: str, default: Any = None) -> Any:
        entry = self.entries.get(key)
        if entry is None:
            return default
        if time.time() - entry.created_at > entry.ttl:
            self.entries.pop(key, None)
            self.save()
            return default
        return entry.value

    def set(self, key: str, value: Any, ttl: float = 3600.0) -> None:
        self.entries[key] = CacheEntry(key, value, time.time(), ttl)
        self.save()
