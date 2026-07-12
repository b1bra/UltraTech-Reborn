"""Launcher and Java/Minecraft environment detection.

Detection is conservative: it never mutates user profiles, only inspects common
paths and the Java executable. Users can still add launcher paths manually from the
first-run dialog or settings window.
"""
from __future__ import annotations
from dataclasses import dataclass
from pathlib import Path
import os, platform, subprocess

@dataclass
class JavaRuntime:
    version: str
    vendor: str
    architecture: str
    path: str
    status: str

@dataclass
class LauncherProfile:
    name: str
    path: Path
    kind: str

class EnvironmentDetector:
    def common_minecraft_dirs(self) -> list[Path]:
        home = Path.home()
        candidates = [home / ".minecraft", home / "AppData/Roaming/.minecraft", home / "Library/Application Support/minecraft"]
        return [path for path in candidates if path.exists()]

    def detect_launchers(self) -> list[LauncherProfile]:
        profiles: list[LauncherProfile] = []
        for mc_dir in self.common_minecraft_dirs():
            profiles.append(LauncherProfile("Minecraft", mc_dir, "vanilla"))
        for name in ["PrismLauncher", "MultiMC", "CurseForge"]:
            candidate = Path.home() / name
            if candidate.exists():
                profiles.append(LauncherProfile(name, candidate, "third_party"))
        return profiles

    def detect_java(self) -> JavaRuntime:
        java_home = os.environ.get("JAVA_HOME", "")
        java_path = str(Path(java_home) / "bin" / ("java.exe" if platform.system() == "Windows" else "java")) if java_home else "java"
        try:
            proc = subprocess.run([java_path, "-version"], capture_output=True, text=True, timeout=5)
            text = (proc.stderr or proc.stdout).strip().splitlines()
            first = text[0] if text else "unknown"
            vendor = "OpenJDK" if "openjdk" in first.lower() else "Java"
            status = "available" if proc.returncode == 0 else "error"
        except Exception as exc:
            first = "unknown"
            vendor = "unknown"
            status = f"unavailable: {exc.__class__.__name__}"
        return JavaRuntime(first, vendor, platform.machine(), java_path, status)
