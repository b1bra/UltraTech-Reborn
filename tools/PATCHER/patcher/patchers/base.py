from __future__ import annotations

from abc import ABC, abstractmethod
from pathlib import Path
from patcher.diagnostics.models import JarAnalysis, Diagnostic

class Patch(ABC):
    id: str
    @abstractmethod
    def can_apply(self, diagnostic: Diagnostic, analysis: JarAnalysis) -> bool: ...
    @abstractmethod
    def apply(self, source: Path, destination: Path, analysis: JarAnalysis) -> None: ...
