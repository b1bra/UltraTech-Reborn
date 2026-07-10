from __future__ import annotations

from abc import ABC, abstractmethod
from patcher.diagnostics.models import JarAnalysis, Diagnostic

class Analyzer(ABC):
    @abstractmethod
    def analyze(self, analysis: JarAnalysis) -> list[Diagnostic]: ...
