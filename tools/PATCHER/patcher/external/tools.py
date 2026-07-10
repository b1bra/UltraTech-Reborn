from __future__ import annotations

import os
import platform
import shutil
import subprocess
from dataclasses import dataclass
from pathlib import Path


@dataclass(slots=True)
class ExternalTool:
    name: str
    executable: Path | None
    icon: str = "◇"
    supports_automation: bool = True


@dataclass(slots=True)
class JavaRuntime:
    runtime: str = "Not found"
    version: str = "Unknown"
    vendor: str = "Unknown"
    architecture: str = platform.machine() or "Unknown"
    path: Path | None = None


@dataclass(frozen=True, slots=True)
class DiscoveryRule:
    name: str
    candidates: tuple[str, ...]
    icon: str = "◇"


@dataclass(frozen=True, slots=True)
class LauncherRule:
    marker: str
    weight: int = 1


class ToolLocator:
    supported_rules = (
        DiscoveryRule("Recaf", ("recaf", "recaf.exe", "recaf.jar"), "⬡"),
        DiscoveryRule("CFR", ("cfr", "cfr.bat", "cfr.jar"), "Ⓒ"),
        DiscoveryRule("FernFlower", ("fernflower", "fernflower.jar"), "✦"),
        DiscoveryRule("Vineflower", ("vineflower", "vineflower.jar"), "✧"),
        DiscoveryRule("Bytecode Viewer", ("bytecode-viewer", "bytecode-viewer.jar", "bcv.jar"), "▣"),
        DiscoveryRule("ASMifier", ("asmifier", "asmifier.jar"), "⌬"),
        DiscoveryRule("JD-GUI", ("jd-gui", "jd-gui.exe", "jd-gui.jar"), "◈"),
        DiscoveryRule("Java Decompiler", ("jad", "jad.exe", "java-decompiler.jar"), "◇"),
        DiscoveryRule("Java", ("java", "java.exe"), "☕"),
    )

    def discover(self) -> list[ExternalTool]:
        return [self._discover_rule(rule) for rule in self.supported_rules]

    def discover_java(self) -> JavaRuntime:
        java_path = shutil.which("java") or shutil.which("java.exe")
        if not java_path:
            return JavaRuntime()
        runtime = JavaRuntime(runtime="Java Runtime", path=Path(java_path))
        try:
            result = subprocess.run([java_path, "-XshowSettings:properties", "-version"], capture_output=True, text=True, timeout=5)
        except (OSError, subprocess.SubprocessError):
            return runtime
        text = f"{result.stdout}\n{result.stderr}"
        version = vendor = architecture = "Unknown"
        for line in text.splitlines():
            stripped = line.strip()
            if stripped.startswith("java.version ="):
                version = stripped.split("=", 1)[1].strip()
            elif stripped.startswith("java.vendor ="):
                vendor = stripped.split("=", 1)[1].strip()
            elif stripped.startswith("os.arch ="):
                architecture = stripped.split("=", 1)[1].strip()
        return JavaRuntime("Java Runtime", version, vendor, architecture, Path(java_path))

    def _discover_rule(self, rule: DiscoveryRule) -> ExternalTool:
        for candidate in rule.candidates:
            found = shutil.which(candidate)
            if found:
                return ExternalTool(rule.name, Path(found), rule.icon)
        for root in self._tool_search_roots():
            found = self._find_in_tree(root, rule.candidates)
            if found:
                return ExternalTool(rule.name, found, rule.icon)
        return ExternalTool(rule.name, None, rule.icon)

    def _tool_search_roots(self) -> list[Path]:
        roots = [Path.cwd(), Path.home() / "Downloads", Path.home() / "Desktop"]
        appdata = os.environ.get("APPDATA")
        if appdata:
            roots.append(Path(appdata))
        return [root for root in roots if root.exists()]

    def _find_in_tree(self, root: Path, candidates: tuple[str, ...], max_depth: int = 4) -> Path | None:
        candidate_names = {candidate.lower() for candidate in candidates}
        try:
            for path in root.rglob("*"):
                if len(path.relative_to(root).parts) > max_depth:
                    continue
                if path.name.lower() in candidate_names and path.is_file():
                    return path
        except (OSError, PermissionError):
            return None
        return None


class LauncherLocator:
    rules = (
        LauncherRule("launcher_profiles.json", 5),
        LauncherRule("versions", 4),
        LauncherRule("libraries", 4),
        LauncherRule("mods", 3),
        LauncherRule("assets", 3),
        LauncherRule("runtime", 2),
        LauncherRule("minecraft", 2),
        LauncherRule("launcher", 2),
        LauncherRule("manifest", 1),
    )

    def discover(self) -> list[ExternalTool]:
        roaming = self._roaming_dir()
        if roaming is None or not roaming.exists():
            return [ExternalTool("Offline / no launcher", None, "◇", False)]
        launchers: list[ExternalTool] = []
        seen: set[Path] = set()
        for directory in self._walk_candidate_dirs(roaming):
            if directory.name.lower() == ".minecraft":
                continue
            score = self._score(directory)
            if score < 4:
                continue
            resolved = directory.resolve()
            if resolved in seen:
                continue
            seen.add(resolved)
            launchers.append(ExternalTool(self._display_name(directory), directory, "◈", False))
        launchers.sort(key=lambda tool: tool.name.lower())
        return launchers or [ExternalTool("Offline / no launcher", None, "◇", False)]

    def _roaming_dir(self) -> Path | None:
        appdata = os.environ.get("APPDATA")
        if appdata:
            return Path(appdata)
        if platform.system() == "Windows":
            return Path.home() / "AppData" / "Roaming"
        return Path.home() / ".config"

    def _walk_candidate_dirs(self, root: Path, max_depth: int = 5):
        try:
            for current, dirs, _files in os.walk(root):
                path = Path(current)
                rel_parts = path.relative_to(root).parts
                if len(rel_parts) > max_depth:
                    dirs[:] = []
                    continue
                dirs[:] = [d for d in dirs if d.lower() not in {".minecraft", "cache", "logs", "screenshots"}]
                yield path
        except (OSError, PermissionError):
            return

    def _score(self, directory: Path) -> int:
        try:
            children = {child.name.lower() for child in directory.iterdir()}
        except (OSError, PermissionError):
            return 0
        score = 0
        for rule in self.rules:
            if rule.marker.lower() in children or any(rule.marker.lower() in child for child in children):
                score += rule.weight
        return score

    def _display_name(self, directory: Path) -> str:
        name = directory.name.strip(".") or directory.name
        return name.replace("_", " ").replace("-", " ").title()
