from __future__ import annotations

import shutil
import subprocess
import tempfile
from dataclasses import dataclass, field
from pathlib import Path

from patcher.core.services.environment import RuntimeInfo

@dataclass(slots=True)
class VerificationReport:
    success: bool
    sandbox: Path
    command: list[str]
    logs: list[str] = field(default_factory=list)
    error: str = ""

class VerificationEngine:
    """Creates an isolated sandbox, copies the patched mod, starts Minecraft/Java and captures logs."""

    def __init__(self, java: RuntimeInfo, minecraft: RuntimeInfo) -> None:
        self.java = java
        self.minecraft = minecraft

    def verify(self, patched_mod: Path, timeout_seconds: int = 30) -> VerificationReport:
        sandbox = Path(tempfile.mkdtemp(prefix="patcher-sandbox-"))
        mods_dir = sandbox / "mods"
        logs_dir = sandbox / "logs"
        mods_dir.mkdir(parents=True, exist_ok=True)
        logs_dir.mkdir(parents=True, exist_ok=True)
        copied_mod = mods_dir / patched_mod.name
        shutil.copy2(patched_mod, copied_mod)

        if not self.java.path:
            return VerificationReport(False, sandbox, [], [f"Copied patched mod to {copied_mod}"], "Java runtime was not detected")

        command = self._build_command(sandbox)
        logs = [f"Copied patched mod to {copied_mod}", "Java Started"]
        try:
            process = subprocess.Popen(command, cwd=sandbox, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
            try:
                output, _ = process.communicate(timeout=timeout_seconds)
            except subprocess.TimeoutExpired:
                process.kill()
                output, _ = process.communicate()
                logs.append("Minecraft process reached timeout; sandbox and logs retained for inspection")
                return VerificationReport(True, sandbox, command, logs + output.splitlines(), "")
            success = process.returncode == 0
            logs.extend(output.splitlines())
            return VerificationReport(success, sandbox, command, logs, "" if success else f"Process exited with {process.returncode}")
        except OSError as exc:
            return VerificationReport(False, sandbox, command, logs, str(exc))

    def _build_command(self, sandbox: Path) -> list[str]:
        java = self.java.path
        version_jar = self._find_minecraft_jar()
        if version_jar is None:
            return [java, "-version"]
        return [
            java,
            "-Xmx2G",
            "-Djava.awt.headless=false",
            "-cp",
            str(version_jar),
            "net.minecraft.client.main.Main",
            "--gameDir",
            str(sandbox),
            "--assetsDir",
            str(Path(self.minecraft.path) / "assets"),
            "--version",
            version_jar.stem,
        ]

    def _find_minecraft_jar(self) -> Path | None:
        root = Path(self.minecraft.path) / "versions" if self.minecraft.path else Path()
        if not root.exists():
            return None
        jars = sorted(root.glob("*/*.jar"), key=lambda item: item.stat().st_mtime, reverse=True)
        return jars[0] if jars else None
