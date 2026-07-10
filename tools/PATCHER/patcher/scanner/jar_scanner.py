from __future__ import annotations

from pathlib import Path
from zipfile import ZipFile, BadZipFile

from patcher.diagnostics.models import Diagnostic, JarAnalysis, Severity


class JarScanner:
    """Full-container scanner that inspects classes, resources and metadata without mutating the source JAR."""

    def scan(self, jar_path: Path) -> JarAnalysis:
        analysis = JarAnalysis(jar_path=jar_path, mod_name=jar_path.stem)
        try:
            with ZipFile(jar_path) as jar:
                for info in jar.infolist():
                    name = info.filename
                    if name.endswith(".class"):
                        analysis.classes.append(name.removesuffix(".class").replace("/", "."))
                    else:
                        analysis.resources.append(name)
                    lowered = name.lower()
                    if lowered.endswith("mods.toml") or lowered.endswith("mcmod.info") or lowered == "meta-inf/manifest.mf":
                        analysis.manifests[name] = jar.read(name).decode("utf-8", errors="replace")
                    if "mixin" in lowered and lowered.endswith(".json"):
                        analysis.mixins.append(name)
                    if "coremod" in lowered:
                        analysis.coremods.append(name)
                    if "accesstransformer" in lowered or lowered.endswith("_at.cfg"):
                        analysis.access_transformers.append(name)
                    if lowered.endswith(".jar"):
                        analysis.embedded_jars.append(name)
                    if lowered.endswith((".js", ".groovy", ".cfg", ".json", ".toml")):
                        self._scan_text_resource(jar.read(name), name, analysis)
        except BadZipFile:
            analysis.diagnostics.append(Diagnostic(
                title="Invalid JAR archive", category="launcher_patch",
                description="The selected file is not a readable ZIP/JAR container.",
                cause="Minecraft launchers and mod loaders cannot load corrupted archives.",
                mods=[jar_path.name], auto_fixable=False,
                unresolved_reason="The archive structure must be restored from a valid source.", severity=Severity.ERROR))
        return analysis

    def _scan_text_resource(self, data: bytes, name: str, analysis: JarAnalysis) -> None:
        text = data.decode("utf-8", errors="ignore")[:250_000]
        for marker in ("Class.forName", "LaunchClassLoader", "org.objectweb.asm", "Mixin"):
            if marker in text:
                analysis.api_references.append(f"{name}: {marker}")
