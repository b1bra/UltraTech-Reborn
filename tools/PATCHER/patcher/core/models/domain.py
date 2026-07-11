from __future__ import annotations

from dataclasses import dataclass, field
from enum import Enum
from pathlib import Path
from typing import Any

class HealthStatus(str, Enum):
    HEALTHY = "healthy"
    WARNING = "warning"
    FAILED = "failed"

class DiagnosticSeverity(str, Enum):
    INFO = "info"
    WARNING = "warning"
    ERROR = "error"

class ModLoader(str, Enum):
    VANILLA = "vanilla"
    FORGE = "forge"
    FABRIC = "fabric"
    HYBRID = "hybrid"
    COREMOD = "coremod"
    UNKNOWN = "unknown"

class PatchRisk(str, Enum):
    LOW = "low"
    MEDIUM = "medium"
    HIGH = "high"
    REQUIRES_CONFIRMATION = "requires_confirmation"

@dataclass(slots=True)
class ServiceHealth:
    service_id: str
    status: HealthStatus = HealthStatus.HEALTHY
    message: str = "Ready"

@dataclass(slots=True)
class PluginMetadata:
    plugin_id: str
    name: str
    version: str
    author: str = "UltraTech"
    api_version: str = "1.0"
    dependencies: tuple[str, ...] = ()

@dataclass(slots=True)
class Diagnostic:
    severity: DiagnosticSeverity
    title: str
    category: str
    description: str
    analyzer: str
    risk: PatchRisk = PatchRisk.LOW
    auto_fix_probability: float = 0.0
    suggested_patch: str | None = None

@dataclass(slots=True)
class ClassModel:
    name: str
    package: str = ""
    parent: str | None = None
    interfaces: list[str] = field(default_factory=list)
    annotations: list[str] = field(default_factory=list)
    modifiers: list[str] = field(default_factory=list)
    size: int = 0

@dataclass(slots=True)
class MethodModel:
    owner: str
    name: str
    descriptor: str
    modifiers: list[str] = field(default_factory=list)

@dataclass(slots=True)
class FieldModel:
    owner: str
    name: str
    descriptor: str
    modifiers: list[str] = field(default_factory=list)

@dataclass(slots=True)
class DependencyModel:
    source: str
    target: str
    kind: str

@dataclass(slots=True)
class JarModel:
    path: Path
    size: int = 0
    crc: str = ""
    sha256: str = ""
    loader: ModLoader = ModLoader.UNKNOWN
    manifest: dict[str, str] = field(default_factory=dict)
    resources: list[str] = field(default_factory=list)
    classes: list[ClassModel] = field(default_factory=list)
    methods: list[MethodModel] = field(default_factory=list)
    fields: list[FieldModel] = field(default_factory=list)
    dependencies: list[DependencyModel] = field(default_factory=list)
    metadata: dict[str, Any] = field(default_factory=dict)
    diagnostics: list[Diagnostic] = field(default_factory=list)

@dataclass(slots=True)
class ProjectModel:
    jars: list[JarModel] = field(default_factory=list)
    diagnostics: list[Diagnostic] = field(default_factory=list)

@dataclass(slots=True)
class PatchPlan:
    total_problems: int
    auto_fixable: int
    requires_review: int
    unresolved: int
    diagnostics: list[Diagnostic]
