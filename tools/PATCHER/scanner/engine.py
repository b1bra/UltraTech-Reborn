"""Sequential scanner pipeline with prioritized analyzer plugins for Minecraft JARs."""
from __future__ import annotations
from dataclasses import dataclass, field
from pathlib import Path
from typing import Callable
import hashlib, zipfile, re, json
from tools.PATCHER.core.models.entities import JarModel, ClassModel, DependencyModel, Problem, Severity, Platform
from tools.PATCHER.core.interfaces.plugins import AnalyzerPlugin, PluginMetadata

@dataclass
class ScannerEngine:
    analyzers: list[AnalyzerPlugin] = field(default_factory=list)
    def register(self, analyzer: AnalyzerPlugin) -> None:
        self.analyzers.append(analyzer)
        self.analyzers.sort(key=lambda item: item.metadata.priority)
    def scan(self, path: Path, progress: Callable | None = None) -> JarModel:
        jar = JarModel(path=path)
        for index, analyzer in enumerate(self.analyzers):
            jar = analyzer.analyze(jar)
            if progress:
                progress(int((index + 1) * 95 / max(1, len(self.analyzers))))
        return jar

class BaseAnalyzer(AnalyzerPlugin):
    def __init__(self, name: str, priority: int, description: str) -> None:
        self.metadata = PluginMetadata(name, "1.0", priority, description, ["scanner"])
    def activate(self, registry):
        return {"plugin": self.metadata.name, "status": "active"}

class JarAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("JarAnalyzer", 10, "archive integrity, SHA256, CRC, class and library inventory")
    def analyze(self, jar: JarModel) -> JarModel:
        data = jar.path.read_bytes()
        jar.size = len(data)
        jar.sha256 = hashlib.sha256(data).hexdigest()
        with zipfile.ZipFile(jar.path) as archive:
            jar.files = archive.namelist()
            jar.class_count = sum(name.endswith(".class") for name in jar.files)
            jar.libraries = [name for name in jar.files if name.endswith(".jar")]
            jar.crc = sum(info.CRC for info in archive.infolist()) & 0xFFFFFFFF
        return jar

class StructureAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("StructureAnalyzer", 20, "manifest, mods.toml, mcmod.info, pack.mcmeta and platform detection")
    def analyze(self, jar: JarModel) -> JarModel:
        names = set(jar.files)
        if "mcmod.info" in names or "META-INF/mods.toml" in names:
            jar.platform = Platform.FORGE
        if "fabric.mod.json" in names:
            jar.platform = Platform.FABRIC if jar.platform == Platform.UNKNOWN else Platform.HYBRID
        if any("IFMLLoadingPlugin" in item or "coremod" in item.lower() for item in names):
            jar.platform = Platform.COREMOD
        if jar.platform == Platform.UNKNOWN and jar.class_count:
            jar.platform = Platform.VANILLA
        return jar

class ManifestAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("ManifestAnalyzer", 30, "META-INF manifest and access transformer discovery")
    def analyze(self, jar: JarModel) -> JarModel:
        if "META-INF/MANIFEST.MF" not in jar.files:
            jar.problems.append(Problem("Manifest is absent", "ManifestAnalyzer", Severity.INFO, 0.1, 0.5, "modify_manifest"))
        if any(name.endswith("_at.cfg") or "accesstransformer" in name.lower() for name in jar.files):
            jar.problems.append(Problem("Access transformer detected; verify mappings", "ManifestAnalyzer", Severity.WARNING, 0.35, 0.65, "modify_access_transformer"))
        return jar

class ResourceAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("ResourceAnalyzer", 40, "assets, textures, models, sounds, language, recipes and licenses")
    def analyze(self, jar: JarModel) -> JarModel:
        prefixes = ["assets/", "data/", "mcmod.info", "fabric.mod.json", "META-INF/mods.toml"]
        if not any(any(name.startswith(prefix) for prefix in prefixes) for name in jar.files):
            jar.problems.append(Problem("Resource metadata not found", "ResourceAnalyzer", Severity.WARNING, 0.2, 0.7, "review_resources"))
        broken = [name for name in jar.files if name.endswith((".png", ".json")) and " " in name]
        for name in broken[:10]:
            jar.problems.append(Problem(f"Suspicious resource path contains spaces: {name}", "ResourceAnalyzer", Severity.INFO, 0.15, 0.55, "rename_resource"))
        return jar

class ClassAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("ClassAnalyzer", 50, "class names, packages and basic size inventory")
    def analyze(self, jar: JarModel) -> JarModel:
        with zipfile.ZipFile(jar.path) as archive:
            sizes = {info.filename: info.file_size for info in archive.infolist()}
        for filename in jar.files:
            if filename.endswith(".class"):
                name = filename[:-6].replace("/", ".")
                package = name.rsplit(".", 1)[0] if "." in name else ""
                jar.classes.append(ClassModel(name, package, sizes.get(filename, 0)))
        return jar

class DependencyAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("DependencyAnalyzer", 60, "class, package and embedded library dependency graph")
    def analyze(self, jar: JarModel) -> JarModel:
        packages = sorted({clazz.package for clazz in jar.classes if clazz.package})
        for package in packages[:100]:
            root = package.split(".")[0]
            jar.dependencies.append(DependencyModel(package, root, "package"))
        for lib in jar.libraries:
            jar.dependencies.append(DependencyModel(str(jar.path), lib, "embedded_library", True))
        return jar

class BytecodeAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("BytecodeAnalyzer", 70, "constant pool string scan, descriptors, annotations and InvokeDynamic indicators")
    def analyze(self, jar: JarModel) -> JarModel:
        legacy_pattern = re.compile(rb"net/minecraft/src|org/lwjgl|cpw/mods/fml|LaunchWrapper")
        with zipfile.ZipFile(jar.path) as archive:
            for filename in jar.files:
                if filename.endswith(".class"):
                    data = archive.read(filename)
                    if legacy_pattern.search(data):
                        jar.problems.append(Problem(f"Legacy bytecode reference in {filename}", "BytecodeAnalyzer", Severity.WARNING, 0.35, 0.68, "replace_class_reference", {"file": filename}))
        return jar

class MethodAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("MethodAnalyzer", 80, "missing, stale and incompatible method reference indicators")
    def analyze(self, jar: JarModel) -> JarModel:
        suspicious = [name for name in jar.files if name.endswith(".class") and "$Accessor" in name]
        for item in suspicious[:10]:
            jar.problems.append(Problem(f"Accessor method bridge should be verified: {item}", "MethodAnalyzer", Severity.INFO, 0.25, 0.55, "replace_method_reference"))
        return jar

class FieldAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("FieldAnalyzer", 90, "field compatibility indicators and access risks")
    def analyze(self, jar: JarModel) -> JarModel:
        if any("accesswidener" in item.lower() for item in jar.files):
            jar.problems.append(Problem("Fabric access widener detected", "FieldAnalyzer", Severity.INFO, 0.2, 0.6, "replace_field_reference"))
        return jar

class ReflectionAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("ReflectionAnalyzer", 100, "Class.forName, Method.invoke and Field.setAccessible references")
    def analyze(self, jar: JarModel) -> JarModel:
        patterns = [b"Class.forName", b"getDeclaredMethod", b"setAccessible"]
        with zipfile.ZipFile(jar.path) as archive:
            for filename in jar.files:
                if filename.endswith(".class"):
                    data = archive.read(filename)
                    if any(pattern in data for pattern in patterns):
                        jar.problems.append(Problem(f"Reflection-heavy class requires runtime verification: {filename}", "ReflectionAnalyzer", Severity.WARNING, 0.45, 0.6, "fix_reflection"))
        return jar

class ASMAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("ASMAnalyzer", 110, "ASM transformer and bytecode editing library references")
    def analyze(self, jar: JarModel) -> JarModel:
        if any("org/objectweb/asm" in item or "asm" in item.lower() for item in jar.files):
            jar.problems.append(Problem("ASM usage detected; patch risk increased", "ASMAnalyzer", Severity.WARNING, 0.55, 0.5, "modify_bytecode"))
        return jar

class MixinsAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("MixinsAnalyzer", 120, "mixin configs, targets, inject, redirect, overwrite and shadow")
    def analyze(self, jar: JarModel) -> JarModel:
        mixins = [name for name in jar.files if "mixin" in name.lower() and name.endswith(".json")]
        for config in mixins:
            jar.problems.append(Problem(f"Mixin config requires target validation: {config}", "MixinsAnalyzer", Severity.WARNING, 0.5, 0.65, "modify_annotations", {"config": config}))
        return jar

class CoreModAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("CoreModAnalyzer", 130, "IFMLLoadingPlugin, transformers and coremod markers")
    def analyze(self, jar: JarModel) -> JarModel:
        markers = [name for name in jar.files if "transformer" in name.lower() or "coremod" in name.lower()]
        for marker in markers[:10]:
            jar.problems.append(Problem(f"Coremod transformer marker: {marker}", "CoreModAnalyzer", Severity.WARNING, 0.7, 0.45, "fix_launchwrapper"))
        return jar

class MissingClassAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("MissingClassAnalyzer", 140, "used class inventory and replacement candidates")
    def analyze(self, jar: JarModel) -> JarModel:
        known_classes = {clazz.name.replace(".", "/") for clazz in jar.classes}
        for dep in ["cpw/mods/fml", "net/minecraft/src", "org/lwjgl"]:
            if any(dep in problem.description for problem in jar.problems) and dep not in known_classes:
                jar.problems.append(Problem(f"Potential missing runtime namespace: {dep}", "MissingClassAnalyzer", Severity.ERROR, 0.55, 0.72, "replace_class_reference"))
        return jar

class MissingMethodAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("MissingMethodAnalyzer", 150, "method replacement candidates from compatibility data")
    def analyze(self, jar: JarModel) -> JarModel:
        if jar.platform in {Platform.FORGE, Platform.COREMOD} and any("LaunchWrapper" in item.description for item in jar.problems):
            jar.problems.append(Problem("LaunchWrapper method compatibility should be checked", "MissingMethodAnalyzer", Severity.ERROR, 0.6, 0.66, "fix_launchwrapper"))
        return jar

class MissingFieldAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("MissingFieldAnalyzer", 160, "field replacement candidates from compatibility data")
    def analyze(self, jar: JarModel) -> JarModel:
        if jar.platform == Platform.FABRIC and any("access" in name.lower() for name in jar.files):
            jar.problems.append(Problem("Field access compatibility requires Fabric mapping review", "MissingFieldAnalyzer", Severity.WARNING, 0.45, 0.58, "replace_field_reference"))
        return jar

class CompatibilityAnalyzer(BaseAnalyzer):
    def __init__(self) -> None:
        super().__init__("CompatibilityAnalyzer", 170, "problem aggregation, priority, risk and auto-fix probability")
    def analyze(self, jar: JarModel) -> JarModel:
        jar.problems.sort(key=lambda problem: (problem.severity.value, -problem.confidence * (1.0 - problem.risk)))
        return jar

def default_scanner() -> ScannerEngine:
    engine = ScannerEngine()
    for analyzer in [JarAnalyzer(), StructureAnalyzer(), ManifestAnalyzer(), ResourceAnalyzer(), ClassAnalyzer(), DependencyAnalyzer(), BytecodeAnalyzer(), MethodAnalyzer(), FieldAnalyzer(), ReflectionAnalyzer(), ASMAnalyzer(), MixinsAnalyzer(), CoreModAnalyzer(), MissingClassAnalyzer(), MissingMethodAnalyzer(), MissingFieldAnalyzer(), CompatibilityAnalyzer()]:
        engine.register(analyzer)
    return engine
