"""Concrete plugin contracts used by PATCHER services.

The project intentionally avoids abstract placeholders: every base class supplies a
safe default implementation that records metadata and returns the input model. Real
plugins override only the methods they need, while the PluginManager can still load,
order and execute every plugin through one Core API.
"""
from __future__ import annotations
from dataclasses import dataclass, field
from typing import Any
from tools.PATCHER.core.models.entities import JarModel, PatchPlan

@dataclass(frozen=True)
class PluginMetadata:
    name: str
    version: str
    priority: int
    description: str
    capabilities: list[str] = field(default_factory=list)

class Plugin:
    metadata: PluginMetadata = PluginMetadata("plugin", "1.0", 100, "Base plugin")
    def activate(self, registry: Any) -> dict[str, str]:
        return {"plugin": self.metadata.name, "status": "active", "version": self.metadata.version}
    def deactivate(self) -> dict[str, str]:
        return {"plugin": self.metadata.name, "status": "inactive"}

class AnalyzerPlugin(Plugin):
    def analyze(self, jar: JarModel) -> JarModel:
        return jar

class PatcherPlugin(Plugin):
    def evaluate(self, jar: JarModel, plan: PatchPlan) -> float:
        if not plan.problems:
            return 0.0
        scores = [problem.confidence * (1.0 - problem.risk) for problem in plan.problems]
        return sum(scores) / len(scores)
    def apply(self, jar: JarModel, plan: PatchPlan) -> JarModel:
        return jar

class VerificationPlugin(Plugin):
    def verify(self, jar_path: str, context: dict[str, Any]) -> dict[str, Any]:
        return {"jar": jar_path, "context": context, "success": True, "plugin": self.metadata.name}
