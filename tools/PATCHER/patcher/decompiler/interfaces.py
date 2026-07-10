from __future__ import annotations
from abc import ABC, abstractmethod
from pathlib import Path

class Decompiler(ABC):
    @abstractmethod
    def decompile(self, jar: Path, output_dir: Path) -> None: ...
