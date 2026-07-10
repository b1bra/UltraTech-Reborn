from __future__ import annotations

from patcher.analyzers.base import Analyzer
from patcher.diagnostics.models import Diagnostic, JarAnalysis, Severity

class MetadataAnalyzer(Analyzer):
    def analyze(self, analysis: JarAnalysis) -> list[Diagnostic]:
        diagnostics: list[Diagnostic] = []
        if not analysis.manifests:
            diagnostics.append(Diagnostic("Missing mod metadata", "missing_class", "No mcmod.info, mods.toml, or manifest was found.", "Some loaders require metadata to resolve dependencies and entry points.", mods=[analysis.mod_name], auto_fixable=True, applied_patch="metadata_stub", severity=Severity.WARNING))
        if analysis.mixins:
            diagnostics.append(Diagnostic("Mixin configuration detected", "mixin", "Mixin configs require compatibility checks against target Minecraft and loader versions.", "A changed target class or injection point can stop startup.", mods=[analysis.mod_name], auto_fixable=False, unresolved_reason="Requires bytecode target validation.", severity=Severity.INFO))
        return diagnostics
