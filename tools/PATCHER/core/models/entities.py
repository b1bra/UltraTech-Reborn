"""Dataclass domain models shared by scanner, patcher, verification and UI."""
from __future__ import annotations
from dataclasses import dataclass, field
from enum import Enum
from pathlib import Path
from typing import Any

class Platform(str, Enum): FORGE="Forge"; FABRIC="Fabric"; VANILLA="Vanilla"; HYBRID="Hybrid"; COREMOD="CoreMod"; UNKNOWN="Unknown"
class Severity(str, Enum): INFO="info"; WARNING="warning"; ERROR="error"; CRITICAL="critical"
class PatchStatus(str, Enum): PLANNED="planned"; APPLIED="applied"; VERIFIED="verified"; ROLLED_BACK="rolled_back"; FAILED="failed"
class Provider(str, Enum): OPENAI="OpenAI"; DEEPSEEK="DeepSeek"

@dataclass(slots=True)
class FileChange: path:str; status:str; old_size:int=0; new_size:int=0
@dataclass(slots=True)
class MethodModel: name:str; descriptor:str; access:str=""; annotations:list[str]=field(default_factory=list)
@dataclass(slots=True)
class FieldModel: name:str; descriptor:str; access:str=""; annotations:list[str]=field(default_factory=list)
@dataclass(slots=True)
class ClassModel: name:str; package:str; size:int; parent:str=""; interfaces:list[str]=field(default_factory=list); annotations:list[str]=field(default_factory=list); modifiers:list[str]=field(default_factory=list); methods:list[MethodModel]=field(default_factory=list); fields:list[FieldModel]=field(default_factory=list)
@dataclass(slots=True)
class DependencyModel: source:str; target:str; kind:str; optional:bool=False
@dataclass(slots=True)
class Problem: description:str; analyzer:str; severity:Severity; risk:float; confidence:float; suggested_patch:str; metadata:dict[str,Any]=field(default_factory=dict)
@dataclass(slots=True)
class PatchPlan: problems:list[Problem]=field(default_factory=list); auto_fixable:int=0; needs_review:int=0; impossible:int=0
@dataclass(slots=True)
class JarModel: path:Path; sha256:str=""; crc:int=0; size:int=0; files:list[str]=field(default_factory=list); class_count:int=0; libraries:list[str]=field(default_factory=list); platform:Platform=Platform.UNKNOWN; classes:list[ClassModel]=field(default_factory=list); dependencies:list[DependencyModel]=field(default_factory=list); problems:list[Problem]=field(default_factory=list); changes:list[FileChange]=field(default_factory=list); patched_path:Path|None=None
@dataclass(slots=True)
class ProjectModel: jars:list[JarModel]=field(default_factory=list); launcher_path:Path|None=None; minecraft_path:Path|None=None; java:dict[str,str]=field(default_factory=dict)
@dataclass(slots=True)
class AIKey: key:str; name:str; provider:Provider
@dataclass(slots=True)
class AIResponse: request_id:str; provider:Provider; text:str; confidence:float; risk:float; cancelled:bool=False
