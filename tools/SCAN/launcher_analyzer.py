"""Static technical documentation generator for Minecraft launchers."""

from __future__ import annotations

import hashlib
import json
import os
import re
import zipfile
from collections import Counter, defaultdict
from dataclasses import dataclass, field
from pathlib import Path
from typing import Callable

from logger import ScannerSessionLogger
from markdown_utils import text_block


ARCHIVE_SUFFIXES = {".jar", ".zip", ".war"}
TEXT_SUFFIXES = {
    ".json",
    ".xml",
    ".yml",
    ".yaml",
    ".properties",
    ".cfg",
    ".conf",
    ".ini",
    ".txt",
    ".md",
    ".toml",
    ".bat",
    ".cmd",
    ".sh",
    ".ps1",
    ".gradle",
    ".kts",
    ".mf",
}
EXECUTABLE_SUFFIXES = {".exe", ".bat", ".cmd", ".sh", ".ps1", ".jar"}
URL_PATTERN = re.compile(r"https?://[A-Za-z0-9._~:/?#\[\]@!$&'()*+,;=%-]+")
JAVA_PATTERN = re.compile(r"\b(javaw?|jre|jdk|JAVA_HOME|ProcessBuilder|Runtime\.getRuntime|--?classpath|--?cp)\b", re.I)
MC_PATTERN = re.compile(r"\b(net\.minecraft|minecraft|launchwrapper|mainClass|tweakClass|gameDir|assetsDir|version\.json)\b", re.I)
JVM_ARG_PATTERN = re.compile(r"(-Xmx\S+|-Xms\S+|-D[A-Za-z0-9_.-]+=\S+|--[A-Za-z0-9_.-]+)")
LIB_PATTERN = re.compile(r"([A-Za-z0-9_.-]+:[A-Za-z0-9_.-]+:[A-Za-z0-9_.+-]+|[A-Za-z0-9_.-]+\.(?:jar|dll|so|dylib))")


@dataclass
class LauncherScan:
    root: Path
    files: list[str] = field(default_factory=list)
    directories: list[str] = field(default_factory=list)
    executables: list[str] = field(default_factory=list)
    archives: list[str] = field(default_factory=list)
    configs: list[str] = field(default_factory=list)
    libraries: list[str] = field(default_factory=list)
    urls: list[str] = field(default_factory=list)
    api_markers: list[str] = field(default_factory=list)
    java_markers: list[str] = field(default_factory=list)
    minecraft_markers: list[str] = field(default_factory=list)
    jvm_args: list[str] = field(default_factory=list)
    profiles: list[str] = field(default_factory=list)
    caches: list[str] = field(default_factory=list)
    update_markers: list[str] = field(default_factory=list)
    integrity_markers: list[str] = field(default_factory=list)
    install_markers: list[str] = field(default_factory=list)
    ui_markers: list[str] = field(default_factory=list)
    archive_entries: list[str] = field(default_factory=list)
    manifest_data: list[str] = field(default_factory=list)
    json_keys: list[str] = field(default_factory=list)
    extension_counts: Counter[str] = field(default_factory=Counter)
    errors: list[str] = field(default_factory=list)
    total_size: int = 0


def analyze_launcher(
    launcher_path: Path,
    output_dir: Path,
    logger: ScannerSessionLogger,
    progress: Callable[[int, str], None] | None = None,
) -> Path:
    """Analyze a launcher directory or file and write Markdown documentation."""

    progress = progress or (lambda _percent, _message: None)
    if not launcher_path.exists():
        raise FileNotFoundError(f"Launcher path does not exist: {launcher_path}")
    output_dir.mkdir(parents=True, exist_ok=True)
    logger.banner("Launcher analysis starting")
    logger.info(f"Launcher path: {launcher_path}")
    scan = LauncherScan(root=launcher_path)
    paths = _collect_paths(launcher_path)
    logger.info(f"Launcher filesystem objects found: {len(paths)}")

    for index, path in enumerate(paths, start=1):
        percent = 5 + int((index / max(len(paths), 1)) * 70)
        progress(percent, f"Launcher scan {index}/{len(paths)}")
        _scan_path(path, launcher_path, scan, logger)

    _deduplicate(scan)
    progress(82, "Writing launcher documentation")
    report = output_dir / "launcher.md"
    report.write_text(_render_report(scan), encoding="utf-8")
    logger.log_file(report, "Launcher report")

    inventory = output_dir / "launcher_inventory.json"
    inventory.write_text(json.dumps(_scan_to_json(scan), indent=4, ensure_ascii=False), encoding="utf-8")
    logger.log_file(inventory, "Launcher inventory")
    logger.success("Launcher analysis complete")
    return report


def _collect_paths(path: Path) -> list[Path]:
    if path.is_file():
        return [path]
    result: list[Path] = []
    for root, dirs, files in os.walk(path):
        dirs[:] = [item for item in dirs if item not in {".git", "__pycache__", "node_modules"}]
        root_path = Path(root)
        result.append(root_path)
        for file_name in files:
            result.append(root_path / file_name)
    return result


def _scan_path(path: Path, base: Path, scan: LauncherScan, logger: ScannerSessionLogger) -> None:
    rel = _relative(path, base)
    if path.is_dir():
        scan.directories.append(rel)
        lower = rel.lower()
        if any(token in lower for token in ("cache", ".minecraft", "assets", "libraries")):
            scan.caches.append(rel)
        if any(token in lower for token in ("profile", "profiles", "accounts")):
            scan.profiles.append(rel)
        return

    suffix = path.suffix.lower()
    scan.files.append(rel)
    scan.extension_counts[suffix or "<none>"] += 1
    try:
        scan.total_size += path.stat().st_size
    except OSError:
        pass
    if suffix in EXECUTABLE_SUFFIXES:
        scan.executables.append(rel)
    if suffix in ARCHIVE_SUFFIXES:
        scan.archives.append(rel)
        _scan_archive(path, rel, scan, logger)
    if suffix in TEXT_SUFFIXES:
        scan.configs.append(rel)
        _scan_text_file(path, rel, scan, logger)
    if path.name.lower() in {"launcher_profiles.json", "profiles.json", "version.json", "manifest.json"}:
        scan.profiles.append(rel)


def _scan_text_file(path: Path, rel: str, scan: LauncherScan, logger: ScannerSessionLogger) -> None:
    try:
        text = path.read_text(encoding="utf-8", errors="ignore")
    except Exception as exc:
        scan.errors.append(f"{rel}: {exc}")
        logger.warning(f"Cannot read text file {path}: {exc}")
        return
    _collect_text_markers(text, rel, scan)
    if path.suffix.lower() == ".json":
        _collect_json_keys(text, rel, scan)


def _scan_archive(path: Path, rel: str, scan: LauncherScan, logger: ScannerSessionLogger) -> None:
    try:
        with zipfile.ZipFile(path, "r") as archive:
            for info in archive.infolist()[:5000]:
                entry = f"{rel}!/{info.filename}"
                scan.archive_entries.append(entry)
                lower = info.filename.lower()
                if lower.endswith("manifest.mf"):
                    manifest = archive.read(info.filename).decode("utf-8", errors="ignore")
                    scan.manifest_data.extend(f"{entry}: {line}" for line in manifest.splitlines() if line.strip())
                    _collect_text_markers(manifest, entry, scan)
                elif lower.endswith((".json", ".properties", ".xml", ".txt", ".mf")) and info.file_size < 512_000:
                    text = archive.read(info.filename).decode("utf-8", errors="ignore")
                    _collect_text_markers(text, entry, scan)
                    if lower.endswith(".json"):
                        _collect_json_keys(text, entry, scan)
                if lower.endswith((".jar", ".dll", ".so", ".dylib")):
                    scan.libraries.append(entry)
    except Exception as exc:
        scan.errors.append(f"{rel}: {exc}")
        logger.warning(f"Cannot inspect archive {path}: {exc}")


def _collect_text_markers(text: str, source: str, scan: LauncherScan) -> None:
    for url in URL_PATTERN.findall(text):
        scan.urls.append(f"{source}: {url.rstrip('.,)\"')}")
    for arg in JVM_ARG_PATTERN.findall(text):
        scan.jvm_args.append(f"{source}: {arg}")
    for lib in LIB_PATTERN.findall(text):
        scan.libraries.append(f"{source}: {lib}")
    for match in JAVA_PATTERN.findall(text):
        scan.java_markers.append(f"{source}: {match}")
    for match in MC_PATTERN.findall(text):
        scan.minecraft_markers.append(f"{source}: {match}")

    lowered = text.lower()
    _append_marker(scan.update_markers, source, lowered, ("update", "patch", "download", "version_manifest"))
    _append_marker(scan.integrity_markers, source, lowered, ("sha1", "sha256", "md5", "checksum", "verify", "integrity"))
    _append_marker(scan.install_markers, source, lowered, ("install", "modpack", "extract", "unpack", "libraries", "assets"))
    _append_marker(scan.ui_markers, source, lowered, ("javafx", "swing", "awt", "imgui", "screen", "gui", "theme", "skin"))
    _append_marker(scan.api_markers, source, lowered, ("api/", "graphql", "rest", "oauth", "auth", "sessionserver", "textures.minecraft.net"))


def _append_marker(target: list[str], source: str, text: str, markers: tuple[str, ...]) -> None:
    found = [marker for marker in markers if marker in text]
    if found:
        target.append(f"{source}: {', '.join(found)}")


def _collect_json_keys(text: str, source: str, scan: LauncherScan) -> None:
    try:
        data = json.loads(text)
    except Exception:
        return
    for key in _walk_json_keys(data):
        scan.json_keys.append(f"{source}: {key}")


def _walk_json_keys(value: object, prefix: str = "") -> list[str]:
    result: list[str] = []
    if isinstance(value, dict):
        for key, child in value.items():
            current = f"{prefix}.{key}" if prefix else str(key)
            result.append(current)
            result.extend(_walk_json_keys(child, current))
    elif isinstance(value, list):
        for child in value[:10]:
            result.extend(_walk_json_keys(child, f"{prefix}[]"))
    return result


def _deduplicate(scan: LauncherScan) -> None:
    for name in (
        "files",
        "directories",
        "executables",
        "archives",
        "configs",
        "libraries",
        "urls",
        "api_markers",
        "java_markers",
        "minecraft_markers",
        "jvm_args",
        "profiles",
        "caches",
        "update_markers",
        "integrity_markers",
        "install_markers",
        "ui_markers",
        "archive_entries",
        "manifest_data",
        "json_keys",
        "errors",
    ):
        values = getattr(scan, name)
        setattr(scan, name, sorted(set(values)))


def _render_report(scan: LauncherScan) -> str:
    lines = [
        "# Launcher Technical Analysis",
        "",
        "## Summary",
        "",
        text_block(
            [
                f"Path: {scan.root}",
                f"Total files: {len(scan.files)}",
                f"Total directories: {len(scan.directories)}",
                f"Total size: {round(scan.total_size / (1024 * 1024), 2)} MB",
                f"Executables: {len(scan.executables)}",
                f"Archives: {len(scan.archives)}",
                f"Configurations: {len(scan.configs)}",
                f"URLs: {len(scan.urls)}",
                f"Libraries/dependencies: {len(scan.libraries)}",
            ]
        ),
    ]
    sections = [
        ("Architecture and File Inventory", scan.files[:2000]),
        ("Executable Entry Points", scan.executables),
        ("Archives and Bundled Components", scan.archives),
        ("Archive Entries", scan.archive_entries[:3000]),
        ("Manifest Data", scan.manifest_data[:1000]),
        ("Configuration Files", scan.configs),
        ("Profiles and Accounts", scan.profiles),
        ("Cache and Storage", scan.caches),
        ("Minecraft Launch Markers", scan.minecraft_markers),
        ("Java Detection and Runtime Usage", scan.java_markers),
        ("JVM Argument Formation", scan.jvm_args),
        ("Launcher and Modpack Dependencies", scan.libraries),
        ("Network URLs and Servers", scan.urls),
        ("API Markers", scan.api_markers),
        ("Download and Installation Markers", scan.install_markers),
        ("Update System Markers", scan.update_markers),
        ("Integrity Verification Markers", scan.integrity_markers),
        ("Minecraft UI Modification Markers", scan.ui_markers),
        ("JSON Configuration Keys", scan.json_keys[:3000]),
        ("File Type Statistics", [f"{key}: {value}" for key, value in scan.extension_counts.most_common()]),
        ("Scan Errors", scan.errors or ["No errors"]),
    ]
    for title, values in sections:
        lines.extend(["", f"## {title}", "", text_block(values or ["No data detected"])])
    return "\n".join(lines)


def _scan_to_json(scan: LauncherScan) -> dict[str, object]:
    return {
        "root": str(scan.root),
        "files": scan.files,
        "directories": scan.directories,
        "executables": scan.executables,
        "archives": scan.archives,
        "configs": scan.configs,
        "libraries": scan.libraries,
        "urls": scan.urls,
        "api_markers": scan.api_markers,
        "java_markers": scan.java_markers,
        "minecraft_markers": scan.minecraft_markers,
        "jvm_args": scan.jvm_args,
        "profiles": scan.profiles,
        "caches": scan.caches,
        "update_markers": scan.update_markers,
        "integrity_markers": scan.integrity_markers,
        "install_markers": scan.install_markers,
        "ui_markers": scan.ui_markers,
        "archive_entries": scan.archive_entries,
        "manifest_data": scan.manifest_data,
        "json_keys": scan.json_keys,
        "extension_counts": dict(scan.extension_counts),
        "errors": scan.errors,
        "total_size": scan.total_size,
    }


def _relative(path: Path, base: Path) -> str:
    try:
        return str(path.relative_to(base))
    except ValueError:
        return str(path)

