from __future__ import annotations
from pathlib import Path
from patcher.constants import OPTIONAL_DOC_PATHS

class ContextSources:
    def __init__(self, root: Path) -> None: self.root = root
    def available(self) -> list[Path]:
        return [self.root / p for p in OPTIONAL_DOC_PATHS if (self.root / p).exists()]
