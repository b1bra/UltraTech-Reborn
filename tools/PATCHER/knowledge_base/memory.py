"""Persistent learning memory and simple similarity search."""
from __future__ import annotations
from dataclasses import dataclass, asdict
from pathlib import Path
import json, math

@dataclass
class MemoryRecord:
    text: str
    tags: list[str]
    score: float

class LearningMemory:
    def __init__(self, path: Path) -> None:
        self.path = path
        self.path.parent.mkdir(parents=True, exist_ok=True)
        self.records: list[MemoryRecord] = []
        self.load()
    def load(self) -> None:
        if self.path.exists():
            self.records = [MemoryRecord(**item) for item in json.loads(self.path.read_text(encoding="utf-8"))]
    def save(self) -> None:
        self.path.write_text(json.dumps([asdict(item) for item in self.records], ensure_ascii=False, indent=2), encoding="utf-8")
    def add(self, text: str, tags: list[str], score: float) -> None:
        self.records.append(MemoryRecord(text, tags, score)); self.save()
    def search(self, query: str, limit: int = 5) -> list[MemoryRecord]:
        q = set(query.lower().split())
        ranked = sorted(self.records, key=lambda record: len(q.intersection(record.text.lower().split())) + record.score / 10.0, reverse=True)
        return ranked[:limit]
