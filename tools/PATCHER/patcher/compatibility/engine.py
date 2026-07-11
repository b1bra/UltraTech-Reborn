from __future__ import annotations

from patcher.core.models.domain import Diagnostic, DiagnosticSeverity, JarModel, PatchRisk

class CompatibilityEngine:
    """Converts scanner output into compatibility diagnostics consumed by PatchPlanner."""

    def analyze(self, jar: JarModel) -> JarModel:
        if not jar.manifest:
            jar.diagnostics.append(Diagnostic(
                DiagnosticSeverity.WARNING,
                "Missing manifest",
                "compatibility",
                "Archive has no META-INF/MANIFEST.MF; PATCHER can add safe metadata without changing classes.",
                "CompatibilityAnalyzer",
                PatchRisk.LOW,
                0.9,
                "modify_manifest",
            ))
        if jar.loader.value == "unknown":
            jar.diagnostics.append(Diagnostic(
                DiagnosticSeverity.WARNING,
                "Unknown mod loader",
                "compatibility",
                "Loader could not be determined; generated metadata patch is safest first step.",
                "CompatibilityAnalyzer",
                PatchRisk.MEDIUM,
                0.6,
                "modify_manifest",
            ))
        return jar
