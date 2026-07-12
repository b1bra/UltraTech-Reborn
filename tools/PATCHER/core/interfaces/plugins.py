"""Plugin contracts for analyzers, patchers, decompilers and verification modules."""
from __future__ import annotations
from abc import ABC, abstractmethod
from dataclasses import dataclass
from typing import Protocol
from tools.PATCHER.core.models.entities import JarModel, PatchPlan
@dataclass(frozen=True)
class PluginMetadata: name:str; version:str; priority:int; description:str
class Plugin(ABC):
    metadata:PluginMetadata
    @abstractmethod
    def activate(self, registry): ...
class AnalyzerPlugin(Plugin):
    @abstractmethod
    def analyze(self, jar:JarModel)->JarModel: ...
class PatcherPlugin(Plugin):
    @abstractmethod
    def evaluate(self, jar:JarModel, plan:PatchPlan)->float: ...
    @abstractmethod
    def apply(self, jar:JarModel, plan:PatchPlan)->JarModel: ...
