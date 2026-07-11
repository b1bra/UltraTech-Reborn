from __future__ import annotations

import shutil
import tempfile
import zipfile
from dataclasses import dataclass
from pathlib import Path

from patcher.ai.manager import AIResponse
from patcher.core.models.domain import JarModel, PatchPlan

@dataclass(slots=True)
class AppliedPatch:
    patch_type: str
    description: str
    output: Path

class PatchPlanner:
    def plan(self, jar: JarModel) -> PatchPlan:
        auto = sum(1 for d in jar.diagnostics if d.auto_fix_probability >= 0.7)
        review = sum(1 for d in jar.diagnostics if 0 < d.auto_fix_probability < 0.7)
        return PatchPlan(len(jar.diagnostics), auto, review, len(jar.diagnostics) - auto - review, jar.diagnostics)

class RollbackManager:
    def __init__(self) -> None:
        self.snapshots: list[Path] = []

    def snapshot(self, source: Path) -> Path:
        temp = Path(tempfile.mkdtemp(prefix="patcher-rollback-")) / source.name
        shutil.copy2(source, temp)
        self.snapshots.append(temp)
        return temp

class PatchHistory:
    def __init__(self) -> None:
        self.entries: list[str] = []

    def add(self, entry: str) -> None:
        self.entries.append(entry)

class PatchVerifier:
    def verify(self, path: Path) -> bool:
        return path.exists() and path.stat().st_size > 0 and zipfile.is_zipfile(path)

class PatchExecutor:
    def __init__(self) -> None:
        self.history = PatchHistory()
        self.rollback = RollbackManager()
        self.verifier = PatchVerifier()

    def create_patched_copy(self, source: Path, destination: Path) -> Path:
        shutil.copy2(source, destination)
        self.history.add(f"Created safe patched copy: {destination}")
        if not self.verifier.verify(destination):
            raise RuntimeError("Patched copy verification failed")
        return destination

    def apply_ai_patch(self, source: Path, destination: Path, decision: AIResponse, jar: JarModel) -> AppliedPatch:
        self.rollback.snapshot(source)
        if decision.suggested_patch != "modify_manifest":
            raise RuntimeError(f"Unsupported patch type: {decision.suggested_patch}")
        self._write_manifest_patch(source, destination, decision, jar)
        if not self.verifier.verify(destination):
            raise RuntimeError("Patched JAR verification failed")
        applied = AppliedPatch(decision.suggested_patch, decision.content, destination)
        self.history.add(f"Applied {decision.suggested_patch} with confidence {decision.confidence:.2f} to {destination}")
        return applied

    def _write_manifest_patch(self, source: Path, destination: Path, decision: AIResponse, jar: JarModel) -> None:
        manifest_name = "META-INF/MANIFEST.MF"
        with zipfile.ZipFile(source, "r") as zin, zipfile.ZipFile(destination, "w", zipfile.ZIP_DEFLATED) as zout:
            existing_manifest = ""
            for item in zin.infolist():
                if item.filename == manifest_name:
                    existing_manifest = zin.read(item).decode("utf-8", "replace")
                    continue
                zout.writestr(item, zin.read(item.filename))
            if not existing_manifest:
                existing_manifest = "Manifest-Version: 1.0\n"
            additions = [
                "Created-By: PATCHER",
                f"PATCHER-Source-SHA256: {jar.sha256}",
                f"PATCHER-Patch-Type: {decision.suggested_patch}",
                f"PATCHER-AI-Confidence: {decision.confidence:.2f}",
            ]
            manifest = existing_manifest.rstrip() + "\n" + "\n".join(additions) + "\n"
            zout.writestr(manifest_name, manifest)
