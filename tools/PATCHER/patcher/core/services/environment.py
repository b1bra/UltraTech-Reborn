from __future__ import annotations

import os
import shutil
from dataclasses import dataclass
from pathlib import Path

@dataclass(slots=True)
class RuntimeInfo:
    name: str
    path: str
    status: str

class EnvironmentDetector:
    """Detects local Java and Minecraft locations without blocking the UI."""

    def detect_java(self) -> RuntimeInfo:
        java_home = os.environ.get("JAVA_HOME")
        if java_home:
            candidate = Path(java_home) / "bin" / ("java.exe" if os.name == "nt" else "java")
            if candidate.exists():
                return RuntimeInfo("Java Runtime", str(candidate), "Detected from JAVA_HOME")
        executable = shutil.which("java")
        if executable:
            return RuntimeInfo("Java Runtime", executable, "Detected from PATH")
        return RuntimeInfo("Java Runtime", "", "Not detected")

    def detect_minecraft(self) -> RuntimeInfo:
        candidates = [
            Path.home() / ".minecraft",
            Path.home() / "AppData" / "Roaming" / ".minecraft",
            Path.home() / "Library" / "Application Support" / "minecraft",
        ]
        for candidate in candidates:
            if candidate.exists():
                return RuntimeInfo("Minecraft", str(candidate), "Detected automatically")
        return RuntimeInfo("Minecraft", "", "Not detected")
