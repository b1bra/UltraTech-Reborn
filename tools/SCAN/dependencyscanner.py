"""Extended hidden dependency scanner for SCAN.

This module augments the legacy ModScanner dependency reader.  It inspects JAR
metadata, class constant pools, manifests, service descriptors, Tweaker/CoreMod
entries, access transformers, annotations, inheritance/interface descriptors and
resource paths to find dependencies that are referenced at runtime but not always
listed in ``mcmod.info``.
"""

from __future__ import annotations

import json
import re
import zipfile
from dataclasses import dataclass, field
from pathlib import Path
from typing import Any, Iterable

DEPENDENCY_CATEGORIES = ("Declared", "Hidden", "Optional", "Soft", "Runtime", "Reflection", "ASM", "CoreMod")

KNOWN_APIS: dict[str, tuple[str, ...]] = {
    "Forge": ("net/minecraftforge/", "cpw/mods/fml/", "fml.common", "ForgeModLoader"),
    "IC2 API": ("ic2/api/", "ic2.core", "industrialcraft"),
    "AE2 API": ("appeng/api/", "appeng.api", "appliedenergistics2"),
    "Forestry": ("forestry/api/", "forestry.core", "forestry"),
    "BuildCraft": ("buildcraft/api/", "buildcraft.core", "BuildCraft"),
    "Thermal Foundation": ("cofh/api/", "cofh.thermalfoundation", "thermalfoundation"),
    "CoFHCore": ("cofh/core/", "cofhcore", "CoFHCore"),
    "CodeChickenLib": ("codechicken/lib/", "CodeChickenLib"),
    "EnderIO": ("crazypants/enderio/", "enderio"),
    "Thaumcraft": ("thaumcraft/api/", "thaumcraft"),
    "Baubles": ("baubles/api/", "baubles"),
    "Botania": ("vazkii/botania/api/", "botania"),
    "TConstruct": ("slimeknights/tconstruct/", "tconstruct", "mantle"),
    "ComputerCraft": ("dan200/computercraft/api/", "computercraft"),
    "OpenComputers": ("li/cil/oc/api/", "opencomputers"),
    "WAILA": ("mcp/mobius/waila/api/", "waila"),
    "JEI": ("mezz/jei/api/", "jei"),
    "CraftTweaker": ("crafttweaker/", "MineTweakerAPI", "minetweaker"),
    "LaunchWrapper": ("net/minecraft/launchwrapper/", "ITweaker", "LaunchClassLoader"),
    "ASM": ("org/objectweb/asm/", "IClassTransformer", "ClassVisitor", "MethodVisitor"),
}

REFLECTION_MARKERS = ("Class.forName", "getDeclaredMethod", "getDeclaredField", "Method.invoke", "ReflectionHelper", "ObfuscationReflectionHelper")
COREMOD_MARKERS = ("IFMLLoadingPlugin", "FMLCorePlugin", "FMLCorePluginContainsFMLMod", "IClassTransformer")
TWEAKER_MARKERS = ("TweakClass", "ITweaker", "Tweaker")
AT_MARKERS = ("FMLAT", "AccessTransformer", "_at.cfg", "META-INF/accesstransformer.cfg")

@dataclass
class DependencyRecord:
    """Normalized dependency fact with provenance and confidence."""

    name: str
    categories: set[str] = field(default_factory=set)
    confidence: int = 0
    sources: set[str] = field(default_factory=set)
    evidence: set[str] = field(default_factory=set)

    def merge(self, other: "DependencyRecord") -> None:
        self.categories.update(other.categories)
        self.sources.update(other.sources)
        self.evidence.update(other.evidence)
        self.confidence = max(self.confidence, other.confidence)

    def to_json(self) -> dict[str, Any]:
        return {
            "name": self.name,
            "categories": sorted(self.categories),
            "confidence": int(max(0, min(100, self.confidence))),
            "sources": sorted(self.sources),
            "evidence": sorted(self.evidence)[:20],
        }


def scan_modpack(jars: Iterable[str | Path], declared_by_file: dict[str, Iterable[str]] | None = None, logger: Any | None = None) -> dict[str, Any]:
    """Scan all JARs and return hidden dependency data plus graph edges."""

    declared_by_file = declared_by_file or {}
    mods: list[dict[str, Any]] = []
    edges: list[dict[str, Any]] = []
    for jar in jars:
        jar_path = Path(jar)
        records = scan_jar(jar_path, declared_by_file.get(jar_path.name, []), logger)
        deps = [record.to_json() for record in sorted(records.values(), key=lambda item: item.name.lower())]
        mods.append({"file": jar_path.name, "dependencies": deps})
        for dep in deps:
            edges.append({"from": jar_path.name, "to": dep["name"], "categories": dep["categories"], "confidence": dep["confidence"], "sources": dep["sources"]})
    return {"version": 1, "mods": mods, "edges": _dedupe_edges(edges)}


def scan_jar(jar_path: Path, declared_dependencies: Iterable[str] = (), logger: Any | None = None) -> dict[str, DependencyRecord]:
    """Inspect one JAR without replacing the legacy declared dependency scan."""

    records: dict[str, DependencyRecord] = {}
    for dep in declared_dependencies:
        _add(records, dep, "Declared", 100, "legacy dependency metadata", "mcmod.info dependency")
    try:
        with zipfile.ZipFile(jar_path, "r") as jar:
            names = jar.namelist()
            _scan_metadata(jar, names, records)
            for name in names:
                lower = name.lower()
                if lower.endswith(".class"):
                    data = jar.read(name)
                    text = _class_text(data)
                    _scan_text_references(text, name, records)
                elif lower.endswith((".cfg", ".json", ".info", ".properties", ".txt", ".xml")) or "meta-inf/services" in lower:
                    _scan_text_references(jar.read(name).decode("utf-8", "ignore"), name, records)
                _scan_resource_name(name, records)
    except Exception as exc:  # keep SCAN robust; hidden scan must never break existing scan
        if logger:
            logger.log_exception_context("DependencyScanner", f"scan_jar {jar_path.name}", exc)
    return records


def merge_dependency_records(records: Iterable[dict[str, Any]]) -> list[dict[str, Any]]:
    """Remove duplicate dependency entries while preserving all metadata."""

    merged: dict[str, DependencyRecord] = {}
    for item in records:
        rec = DependencyRecord(str(item.get("name", "")).strip())
        if not rec.name:
            continue
        rec.categories.update(item.get("categories") or [item.get("category", "Hidden")])
        rec.confidence = int(item.get("confidence", 0) or 0)
        rec.sources.update(map(str, item.get("sources") or []))
        rec.evidence.update(map(str, item.get("evidence") or []))
        _merge_record(merged, rec)
    return [r.to_json() for r in merged.values()]


def write_outputs(output_dir: Path, result: dict[str, Any], mods: list[dict[str, Any]], logger: Any | None = None) -> Path:
    """Persist scanner JSON and append generated documentation sections."""

    output_dir.mkdir(parents=True, exist_ok=True)
    json_path = output_dir / "dependency_scanner.json"
    json_path.write_text(json.dumps(result, indent=2, ensure_ascii=False), encoding="utf-8")
    _update_markdown(output_dir / "dependencies.md", result)
    _update_markdown(output_dir / "modlist.md", result)
    if logger:
        logger.log_file(json_path, "Extended dependency scanner output")
    return json_path


def _scan_metadata(jar: zipfile.ZipFile, names: list[str], records: dict[str, DependencyRecord]) -> None:
    for name in names:
        lower = name.lower()
        if lower.endswith("mcmod.info"):
            text = jar.read(name).decode("utf-8", "ignore")
            for dep in re.findall(r'(?:required-after|after|before|required-before|modid)\s*:?\s*\"?([A-Za-z0-9_\-.]+)', text):
                _add(records, dep, "Declared" if "required" in text else "Soft", 94, name, "metadata dependency")
        if any(marker.lower() in lower for marker in AT_MARKERS):
            _add(records, "AccessTransformer", "CoreMod", 88, name, "access transformer file")


def _scan_text_references(text: str, source: str, records: dict[str, DependencyRecord]) -> None:
    for dep, needles in KNOWN_APIS.items():
        hits = [needle for needle in needles if needle in text]
        if hits:
            category = "Hidden"
            confidence = 78
            if any(marker in text for marker in REFLECTION_MARKERS):
                category, confidence = "Reflection", 62
            if dep in ("ASM", "LaunchWrapper") or any(marker in text for marker in COREMOD_MARKERS):
                category, confidence = ("ASM" if dep == "ASM" else "CoreMod"), 92
            _add(records, dep, category, confidence, source, ", ".join(hits[:3]))
    for modid in re.findall(r'(?:Class\.forName|Loader\.isModLoaded|findModContainer)\s*\(\s*\"([A-Za-z0-9_.\-]+)\"', text):
        _add(records, modid, "Reflection", 60, source, "runtime/reflection string")
    if any(marker in text for marker in TWEAKER_MARKERS):
        _add(records, "LaunchWrapper", "Runtime", 90, source, "tweaker marker")


def _scan_resource_name(name: str, records: dict[str, DependencyRecord]) -> None:
    lower = name.lower()
    if lower.startswith("assets/") or lower.startswith("data/"):
        parts = lower.split("/")
        if len(parts) > 1 and parts[1] not in {"minecraft", "forge"}:
            _add(records, parts[1], "Optional", 45, name, "resource namespace")
    if lower.endswith("_at.cfg"):
        _add(records, "AccessTransformer", "CoreMod", 90, name, "access transformer resource")


def _class_text(data: bytes) -> str:
    return " ".join(match.decode("utf-8", "ignore") for match in re.findall(rb"[A-Za-z0-9_.$/\-]{4,}", data))


def _add(records: dict[str, DependencyRecord], name: str, category: str, confidence: int, source: str, evidence: str) -> None:
    clean = _clean_name(name)
    if not clean or clean.lower() in {"minecraft", "java", "scala"}:
        return
    rec = DependencyRecord(clean, {category}, confidence, {source}, {evidence})
    _merge_record(records, rec)


def _merge_record(records: dict[str, DependencyRecord], rec: DependencyRecord) -> None:
    key = rec.name.lower()
    if key in records:
        records[key].merge(rec)
    else:
        records[key] = rec


def _clean_name(name: str) -> str:
    text = str(name).strip().strip('"\'')
    text = re.sub(r"^required-(?:after|before):|^(?:after|before):", "", text, flags=re.I)
    return text[:96]


def _dedupe_edges(edges: list[dict[str, Any]]) -> list[dict[str, Any]]:
    merged: dict[tuple[str, str], dict[str, Any]] = {}
    for edge in edges:
        key = (edge["from"], edge["to"].lower())
        if key not in merged:
            merged[key] = dict(edge)
        else:
            merged[key]["confidence"] = max(merged[key].get("confidence", 0), edge.get("confidence", 0))
            merged[key]["categories"] = sorted(set(merged[key].get("categories", [])) | set(edge.get("categories", [])))
            merged[key]["sources"] = sorted(set(merged[key].get("sources", [])) | set(edge.get("sources", [])))
    return list(merged.values())


def _update_markdown(path: Path, result: dict[str, Any]) -> None:
    marker = "<!-- SCAN EXTENDED DEPENDENCIES -->"
    original = path.read_text(encoding="utf-8") if path.exists() else f"# {path.stem.title()}\n\n"
    base = original.split(marker)[0].rstrip()
    lines = ["", marker, "", "## Extended Dependency Analysis", "", "### Hidden dependencies", ""]
    sections = {name: [] for name in ["Hidden", "Runtime", "Reflection", "CoreMod", "ASM"]}
    for mod in result.get("mods", []):
        for dep in mod.get("dependencies", []):
            for cat in dep.get("categories", []):
                if cat in sections:
                    sections[cat].append((mod.get("file"), dep))
    for title, items in sections.items():
        lines.extend([f"### {title} dependencies" if title != "ASM" else "### ASM", ""])
        if not items:
            lines.extend(["- None detected", ""])
            continue
        for file_name, dep in items:
            lines.append(f"- **{file_name}** -> **{dep['name']}** | confidence: {dep['confidence']} | source: {', '.join(dep.get('sources', [])[:3])}")
        lines.append("")
    lines.extend(["### LaunchWrapper", "", "- Listed under Runtime/CoreMod when Tweaker or LaunchWrapper markers are detected.", "", "### Dependency confidence", "", "- Confidence is 0-100 and is based on metadata declarations, bytecode/API references, reflection strings, coremod markers, and resource namespaces.", "", "### Dependency source", "", "- Sources list the class/resource/metadata path that produced each dependency record.", ""])
    path.write_text(base + "\n" + "\n".join(lines), encoding="utf-8")
