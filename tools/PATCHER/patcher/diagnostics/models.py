from __future__ import annotations

from dataclasses import dataclass, field
from enum import StrEnum
from pathlib import Path
from typing import Any


class Severity(StrEnum):
    INFO = "info"
    WARNING = "warning"
    ERROR = "error"


@dataclass(slots=True)
class Diagnostic:
    title: str
    category: str
    description: str
    cause: str
    classes: list[str] = field(default_factory=list)
    mods: list[str] = field(default_factory=list)
    auto_fixable: bool = False
    applied_patch: str | None = None
    unresolved_reason: str | None = None
    severity: Severity = Severity.INFO
    metadata: dict[str, Any] = field(default_factory=dict)


@dataclass(slots=True)
class JarAnalysis:
    jar_path: Path
    mod_name: str
    classes: list[str] = field(default_factory=list)
    resources: list[str] = field(default_factory=list)
    manifests: dict[str, str] = field(default_factory=dict)
    mixins: list[str] = field(default_factory=list)
    coremods: list[str] = field(default_factory=list)
    access_transformers: list[str] = field(default_factory=list)
    embedded_jars: list[str] = field(default_factory=list)
    api_references: list[str] = field(default_factory=list)
    diagnostics: list[Diagnostic] = field(default_factory=list)
