from __future__ import annotations
from abc import ABC, abstractmethod
from pathlib import Path

class Recompiler(ABC):
    @abstractmethod
    def recompile(self, sources: Path, output_jar: Path) -> None: ...
