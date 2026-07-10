from __future__ import annotations

import json
import struct
from dataclasses import dataclass
from pathlib import Path
from zipfile import BadZipFile, ZipFile

from patcher.analyzers.base import Analyzer
from patcher.diagnostics.models import Diagnostic, JarAnalysis, Severity

JAVA_PREFIXES = ("java.", "javax.", "sun.", "com.sun.", "org.w3c.", "org.xml.")
KNOWN_PREFIXES = JAVA_PREFIXES + ("net.minecraft.", "cpw.mods.", "net.minecraftforge.", "org.objectweb.asm.")


@dataclass(slots=True)
class CompatibilityEntry:
    old_class: str
    new_class: str
    old_method: str | None
    new_method: str | None
    compatibility_level: str
    fix_type: str
    source: str


class CompatibilityDatabase:
    def __init__(self, root: Path | None = None) -> None:
        self.root = root or Path(__file__).resolve().parents[1] / "compatibility_database"

    def entries(self) -> list[CompatibilityEntry]:
        loaded: list[CompatibilityEntry] = []
        for path in sorted(self.root.glob("*.json")):
            try:
                data = json.loads(path.read_text(encoding="utf-8"))
            except (OSError, json.JSONDecodeError):
                continue
            for item in data.get("entries", []):
                loaded.append(CompatibilityEntry(
                    old_class=item.get("old_class", ""),
                    new_class=item.get("new_class", ""),
                    old_method=item.get("old_method"),
                    new_method=item.get("new_method"),
                    compatibility_level=item.get("compatibility_level", "unknown"),
                    fix_type=item.get("fix_type", "manual"),
                    source=path.stem,
                ))
        return [entry for entry in loaded if entry.old_class and entry.new_class]


class ClassConstantScanner:
    def scan(self, jar_path: Path) -> tuple[dict[str, set[str]], dict[str, set[str]], dict[str, set[str]]]:
        dependencies: dict[str, set[str]] = {}
        methods: dict[str, set[str]] = {}
        strings: dict[str, set[str]] = {}
        with ZipFile(jar_path) as jar:
            for info in jar.infolist():
                if not info.filename.endswith(".class"):
                    continue
                owner = info.filename.removesuffix(".class").replace("/", ".")
                try:
                    deps, method_refs, string_refs = self._parse_constants(jar.read(info))
                except (IndexError, struct.error, ValueError):
                    continue
                dependencies[owner] = deps
                methods[owner] = method_refs
                strings[owner] = string_refs
        return dependencies, methods, strings

    def _parse_constants(self, data: bytes) -> tuple[set[str], set[str], set[str]]:
        if data[:4] != b"\xca\xfe\xba\xbe":
            raise ValueError("Not a class file")
        count = struct.unpack_from(">H", data, 8)[0]
        index = 10
        constants: list[object | None] = [None] * count
        raw_class_indexes: list[int] = []
        name_and_type: dict[int, tuple[int, int]] = {}
        member_refs: list[tuple[int, int]] = []
        utf8_values: set[str] = set()
        i = 1
        while i < count:
            tag = data[index]
            index += 1
            if tag == 1:
                length = struct.unpack_from(">H", data, index)[0]
                index += 2
                value = data[index:index + length].decode("utf-8", errors="replace")
                index += length
                constants[i] = value
                utf8_values.add(value)
            elif tag == 7:
                name_index = struct.unpack_from(">H", data, index)[0]
                index += 2
                raw_class_indexes.append(name_index)
                constants[i] = name_index
            elif tag in {9, 10, 11}:
                class_index, nat_index = struct.unpack_from(">HH", data, index)
                index += 4
                member_refs.append((class_index, nat_index))
            elif tag == 12:
                name_index, descriptor_index = struct.unpack_from(">HH", data, index)
                index += 4
                name_and_type[i] = (name_index, descriptor_index)
            elif tag in {3, 4}:
                index += 4
            elif tag in {5, 6}:
                index += 8
                i += 1
            elif tag in {8, 16, 19, 20}:
                index += 2
            elif tag == 15:
                index += 3
            elif tag == 18:
                index += 4
            else:
                raise ValueError(f"Unsupported constant pool tag {tag}")
            i += 1
        classes = {self._normalize_class_name(str(constants[name_index])) for name_index in raw_class_indexes if constants[name_index]}
        methods: set[str] = set()
        for class_index, nat_index in member_refs:
            class_name_index = constants[class_index]
            if not isinstance(class_name_index, int) or nat_index not in name_and_type:
                continue
            class_name = self._normalize_class_name(str(constants[class_name_index]))
            method_name_index, _descriptor_index = name_and_type[nat_index]
            method_name = constants[method_name_index]
            if isinstance(method_name, str):
                methods.add(f"{class_name}#{method_name}")
        string_classes = {value.replace("/", ".") for value in utf8_values if "." in value and " " not in value}
        return classes, methods, string_classes

    def _normalize_class_name(self, value: str) -> str:
        return value.strip("[L;").replace("/", ".")


class CompatibilityAnalyzer(Analyzer):
    def __init__(self) -> None:
        self.database = CompatibilityDatabase()
        self.scanner = ClassConstantScanner()

    def analyze(self, analysis: JarAnalysis) -> list[Diagnostic]:
        diagnostics: list[Diagnostic] = []
        try:
            dependencies, methods, _strings = self.scanner.scan(analysis.jar_path)
        except BadZipFile:
            return diagnostics
        analysis.dependency_map = {owner: sorted(deps) for owner, deps in dependencies.items()}
        local_classes = set(analysis.classes)
        all_deps = {dep for deps in dependencies.values() for dep in deps}
        missing = sorted(dep for dep in all_deps if dep not in local_classes and not dep.startswith(KNOWN_PREFIXES))
        analysis.missing_classes = missing[:250]
        for entry in self.database.entries():
            users = [owner for owner, deps in dependencies.items() if entry.old_class in deps]
            method_users = [owner for owner, refs in methods.items() if any(ref.startswith(f"{entry.old_class}#") and (entry.old_method is None or ref.endswith(f"#{entry.old_method}")) for ref in refs)]
            if users or method_users:
                diagnostics.append(Diagnostic(
                    title=f"Compatibility replacement available: {entry.old_class}",
                    category="compatibility",
                    description=f"{entry.old_class} can be migrated to {entry.new_class} before Minecraft starts.",
                    cause=f"Static bytecode references found in {len(set(users + method_users))} class(es).",
                    classes=sorted(set(users + method_users))[:12],
                    mods=[analysis.mod_name],
                    auto_fixable=entry.fix_type == "bytecode_replace",
                    applied_patch=f"replace {entry.old_class} -> {entry.new_class}" if entry.fix_type == "bytecode_replace" else None,
                    unresolved_reason=None if entry.fix_type == "bytecode_replace" else "Compatibility database marks this as manual.",
                    severity=Severity.WARNING,
                    metadata={"old_class": entry.old_class, "new_class": entry.new_class, "fix_type": entry.fix_type, "source": entry.source},
                ))
        if missing:
            diagnostics.append(Diagnostic(
                title="Potential missing external classes",
                category="missing_class",
                description=f"Static analysis found {len(missing)} referenced classes that are not inside the selected JAR.",
                cause="The mod may depend on another mod/library or an incompatible launcher/runtime API.",
                classes=missing[:20],
                mods=[analysis.mod_name],
                auto_fixable=False,
                unresolved_reason="Requires dependency resolution or compatibility mapping.",
                severity=Severity.WARNING,
            ))
        return diagnostics


class MissingClassAnalyzer(Analyzer):
    def __init__(self) -> None:
        self.scanner = ClassConstantScanner()

    def analyze(self, analysis: JarAnalysis) -> list[Diagnostic]:
        diagnostics: list[Diagnostic] = []
        try:
            _dependencies, methods, strings = self.scanner.scan(analysis.jar_path)
        except BadZipFile:
            return diagnostics
        markers = ("ClassNotFoundException", "NoClassDefFoundError")
        for owner, values in strings.items():
            matched = [value for value in values if any(marker in value for marker in markers)]
            if not matched:
                continue
            method_names = sorted(ref.split("#", 1)[1] for ref in methods.get(owner, set()) if ref.startswith(f"{owner}#"))
            diagnostics.append(Diagnostic(
                title="Runtime missing-class handling detected",
                category="missing_class",
                description="The class contains ClassNotFoundException/NoClassDefFoundError references; PATCHER inspected it before launch.",
                cause="The mod may already expect optional or relocated classes.",
                classes=[owner],
                mods=[analysis.mod_name],
                auto_fixable=False,
                unresolved_reason="Review optional dependency handling and compatibility database mappings.",
                severity=Severity.INFO,
                metadata={"markers": matched[:8], "methods": method_names[:12]},
            ))
        return diagnostics
