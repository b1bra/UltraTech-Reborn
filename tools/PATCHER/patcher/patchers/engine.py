from __future__ import annotations

import shutil
from pathlib import Path
from patcher.core.models.domain import JarModel, PatchPlan

class PatchPlanner:
    def plan(self, jar: JarModel) -> PatchPlan:
        auto = sum(1 for d in jar.diagnostics if d.auto_fix_probability >= 0.7)
        review = sum(1 for d in jar.diagnostics if 0 < d.auto_fix_probability < 0.7)
        return PatchPlan(len(jar.diagnostics), auto, review, len(jar.diagnostics) - auto - review, jar.diagnostics)

class RollbackManager:
    def __init__(self) -> None:
        self.snapshots: list[Path] = []

class PatchHistory:
    def __init__(self) -> None:
        self.entries: list[str] = []

class PatchVerifier:
    def verify(self, path: Path) -> bool:
        return path.exists() and path.stat().st_size > 0

class PatchExecutor:
    def __init__(self) -> None:
        self.history = PatchHistory()
        self.rollback = RollbackManager()
        self.verifier = PatchVerifier()

    def create_patched_copy(self, source: Path, destination: Path) -> Path:
        shutil.copy2(source, destination)
        self.history.entries.append(f"Created safe patched copy: {destination}")
        if not self.verifier.verify(destination):
            raise RuntimeError("Patched copy verification failed")
        return destination
