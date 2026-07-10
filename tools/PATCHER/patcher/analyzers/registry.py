from __future__ import annotations

from patcher.analyzers.base import Analyzer
from patcher.analyzers.compatibility import CompatibilityAnalyzer, MissingClassAnalyzer


class AnalyzerRegistry:
    """Small registration point for independent analyzers."""

    analyzer_types = (CompatibilityAnalyzer, MissingClassAnalyzer)

    def create_all(self, initial: list[Analyzer] | None = None) -> list[Analyzer]:
        analyzers = list(initial or [])
        analyzers.extend(analyzer_type() for analyzer_type in self.analyzer_types)
        return analyzers
