from __future__ import annotations

import shutil
from pathlib import Path
from patcher.analyzers.base import Analyzer
from patcher.diagnostics.models import JarAnalysis
from patcher.scanner.jar_scanner import JarScanner

class PatchPipeline:
    def __init__(self, scanner: JarScanner, analyzers: list[Analyzer]) -> None:
        self.scanner = scanner
        self.analyzers = analyzers

    def analyze(self, jar_path: Path) -> JarAnalysis:
        analysis = self.scanner.scan(jar_path)
        for analyzer in self.analyzers:
            analysis.diagnostics.extend(analyzer.analyze(analysis))
        return analysis

    def save_patched_copy(self, source: Path, destination: Path) -> None:
        if destination.suffix.lower() != ".jar":
            raise ValueError("Patched output must be a .jar file")
        shutil.copy2(source, destination)
