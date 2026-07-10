from __future__ import annotations
from abc import ABC, abstractmethod
from pathlib import Path

class BytecodeBackend(ABC):
    @abstractmethod
    def inspect_class(self, jar: Path, class_name: str) -> dict[str, object]: ...
    @abstractmethod
    def rewrite_class(self, jar: Path, class_name: str, operations: list[dict[str, object]]) -> bytes: ...
