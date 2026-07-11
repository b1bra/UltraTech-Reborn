from __future__ import annotations

from pathlib import Path

class ResourceManager:
    def __init__(self, root: Path) -> None:
        self.root = root

    def path(self, *parts: str) -> Path:
        return self.root.joinpath(*parts)

    def text(self, *parts: str) -> str:
        return self.path(*parts).read_text(encoding="utf-8")
