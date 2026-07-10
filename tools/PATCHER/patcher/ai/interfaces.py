from __future__ import annotations

from abc import ABC, abstractmethod
from patcher.diagnostics.models import Diagnostic, JarAnalysis

class AIAdvisor(ABC):
    @abstractmethod
    def explain(self, diagnostic: Diagnostic, analysis: JarAnalysis) -> str: ...

class PatchSuggestionProvider(ABC):
    @abstractmethod
    def suggest_patch(self, diagnostic: Diagnostic, analysis: JarAnalysis) -> str | None: ...
