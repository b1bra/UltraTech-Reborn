"""Verification engine with sandbox creation, log monitoring and crash analysis."""
from __future__ import annotations
from dataclasses import dataclass, field
from pathlib import Path
from typing import Any
import re, time
from tools.PATCHER.sandbox.manager import SandboxManager

@dataclass
class CrashAnalysis:
    detected: bool
    error_type: str
    location: str
    recommendation: str

@dataclass
class VerificationReport:
    jar: Path
    sandbox: Path
    success: bool
    stages: list[str] = field(default_factory=list)
    log_lines: list[str] = field(default_factory=list)
    crash: CrashAnalysis = field(default_factory=lambda: CrashAnalysis(False, "none", "", ""))

class VerificationEngine:
    def __init__(self, sandbox: SandboxManager | None = None) -> None:
        self.sandbox = sandbox or SandboxManager()
        self.error_patterns = {
            "class_not_found": re.compile(r"ClassNotFoundException|NoClassDefFoundError"),
            "no_such_method": re.compile(r"NoSuchMethodError"),
            "no_such_field": re.compile(r"NoSuchFieldError"),
            "verify_error": re.compile(r"VerifyError"),
            "mixin": re.compile(r"Mixin|InjectionError"),
        }

    def analyze_crash(self, lines: list[str]) -> CrashAnalysis:
        text = "\n".join(lines)
        for name, pattern in self.error_patterns.items():
            if pattern.search(text):
                location = next((line.strip() for line in lines if pattern.search(line)), "unknown")
                return CrashAnalysis(True, name, location, f"Use compatibility database and patcher for {name}")
        return CrashAnalysis(False, "none", "", "No crash markers detected")

    def verify(self, jar: Path, launcher_context: dict[str, Any] | None = None) -> VerificationReport:
        sandbox_report = self.sandbox.verify(jar)
        stages = list(sandbox_report.stages)
        stages.append("log_analysis")
        crash = self.analyze_crash(sandbox_report.logs)
        success = sandbox_report.success and not crash.detected and any("Main menu" in line for line in sandbox_report.logs)
        time.sleep(0.05)
        return VerificationReport(jar, sandbox_report.path, success, stages, sandbox_report.logs, crash)
