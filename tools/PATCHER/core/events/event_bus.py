"""Synchronous event bus and message bus for decoupled plugins."""
from __future__ import annotations
from dataclasses import dataclass, field
from enum import Enum
from typing import Any, Callable
class EventType(str, Enum): JAR_OPENED="JarOpened"; SCANNER_STARTED="ScannerStarted"; SCANNER_FINISHED="ScannerFinished"; PATCH_STARTED="PatchStarted"; PATCH_FINISHED="PatchFinished"; VERIFICATION_STARTED="VerificationStarted"; VERIFICATION_FINISHED="VerificationFinished"; AI_STARTED="AIStarted"; AI_COMPLETED="AICompleted"; SANDBOX_STARTED="SandboxStarted"; SANDBOX_FINISHED="SandboxFinished"; CRASH_DETECTED="CrashDetected"; LOG="Log"
@dataclass
class Event: type:EventType; payload:dict[str,Any]=field(default_factory=dict)
class EventBus:
    def __init__(self)->None: self._subs:dict[EventType,list[Callable[[Event],None]]]={}
    def subscribe(self,t:EventType,cb:Callable[[Event],None])->None: self._subs.setdefault(t,[]).append(cb)
    def publish(self,event:Event)->None:
        for cb in list(self._subs.get(event.type,[])): cb(event)
class MessageBus:
    def __init__(self)->None: self.messages:list[tuple[str,Any]]=[]
    def send(self,topic:str,payload:Any)->None: self.messages.append((topic,payload))
    def drain(self,topic:str)->list[Any]:
        vals=[p for t,p in self.messages if t==topic]; self.messages=[m for m in self.messages if m[0]!=topic]; return vals
