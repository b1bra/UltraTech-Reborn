"""Diagnostics and health monitoring for PATCHER services."""
from __future__ import annotations
from dataclasses import dataclass, field
from pathlib import Path
from shutil import disk_usage
from typing import Any
import platform, sys, time

@dataclass
class HealthCheck:
    name: str
    ok: bool
    message: str
    details: dict[str, Any] = field(default_factory=dict)

class HealthMonitor:
    def __init__(self, runtime_dir: Path) -> None:
        self.runtime_dir = runtime_dir
        self.started = time.time()
        self.history: list[HealthCheck] = []

    def check_python(self) -> HealthCheck:
        ok = sys.version_info >= (3, 12)
        check = HealthCheck("python", ok, platform.python_version(), {"executable": sys.executable})
        self.history.append(check)
        return check

    def check_runtime_dir(self) -> HealthCheck:
        self.runtime_dir.mkdir(parents=True, exist_ok=True)
        usage = disk_usage(self.runtime_dir)
        ok = usage.free > 100 * 1024 * 1024
        check = HealthCheck("runtime_dir", ok, str(self.runtime_dir), {"free": usage.free, "total": usage.total})
        self.history.append(check)
        return check

    def check_services(self, registry: Any) -> HealthCheck:
        names = registry.names()
        required = {"Logger", "ConfigManager", "EventBus", "TaskScheduler", "ScannerEngine", "PatchEngine", "AIManager"}
        missing = sorted(required.difference(names))
        check = HealthCheck("services", not missing, "ready" if not missing else "missing services", {"missing": missing, "registered": names})
        self.history.append(check)
        return check

    def run_all(self, registry: Any) -> list[HealthCheck]:
        return [self.check_python(), self.check_runtime_dir(), self.check_services(registry)]

    def report_text(self) -> str:
        lines = [f"PATCHER uptime: {time.time() - self.started:.1f}s"]
        for item in self.history:
            lines.append(f"{item.name}: {'OK' if item.ok else 'FAIL'} — {item.message}")
        return "\n".join(lines)
