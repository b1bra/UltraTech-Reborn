from __future__ import annotations

import hashlib, zipfile, zlib
from pathlib import Path
from patcher.core.models.domain import ClassModel, Diagnostic, DiagnosticSeverity, JarModel, ModLoader, PatchRisk

class ScannerEngine:
    STAGES = ("Archive", "Structure", "Manifest", "Metadata", "Mixins", "Compatibility")

    def analyze(self, path: Path, progress: callable | None = None) -> JarModel:
        data = path.read_bytes()
        jar = JarModel(path=path, size=len(data), crc=f"{zlib.crc32(data) & 0xffffffff:08x}", sha256=hashlib.sha256(data).hexdigest())
        for index, stage in enumerate(self.STAGES, start=1):
            if progress:
                progress(stage, int(index / len(self.STAGES) * 90))
        if not zipfile.is_zipfile(path):
            jar.diagnostics.append(Diagnostic(DiagnosticSeverity.ERROR, "Invalid archive", "archive", "JAR is not a valid ZIP archive.", "JarAnalyzer", PatchRisk.HIGH, 0.0))
            return jar
        with zipfile.ZipFile(path) as zf:
            names = zf.namelist()
            jar.resources = names
            classes = [n for n in names if n.endswith('.class')]
            jar.classes = [ClassModel(name=n[:-6].replace('/', '.'), package='.'.join(n[:-6].split('/')[:-1]), size=zf.getinfo(n).file_size) for n in classes]
            jar.metadata['class_count'] = len(classes)
            jar.metadata['embedded_libraries'] = [n for n in names if n.endswith('.jar')]
            if 'META-INF/MANIFEST.MF' in names:
                jar.manifest = self._manifest(zf.read('META-INF/MANIFEST.MF').decode('utf-8', 'replace'))
            markers = set(names)
            if 'mods.toml' in markers or 'META-INF/mods.toml' in markers or 'mcmod.info' in markers:
                jar.loader = ModLoader.FORGE
            elif 'fabric.mod.json' in markers:
                jar.loader = ModLoader.FABRIC
            elif any('IFMLLoadingPlugin' in c.name or 'CoreMod' in c.name for c in jar.classes):
                jar.loader = ModLoader.COREMOD
            else:
                jar.loader = ModLoader.VANILLA
            if not classes:
                jar.diagnostics.append(Diagnostic(DiagnosticSeverity.WARNING, "No classes found", "structure", "Archive contains no Java class files.", "ClassAnalyzer", PatchRisk.LOW, 0.0))
        if progress:
            progress("Finished", 100)
        return jar

    def _manifest(self, text: str) -> dict[str, str]:
        result: dict[str, str] = {}
        for line in text.splitlines():
            if ': ' in line:
                key, value = line.split(': ', 1)
                result[key] = value
        return result
