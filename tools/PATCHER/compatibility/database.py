"""JSON compatibility database loader and matcher for classes, methods, fields and errors."""
from __future__ import annotations
from dataclasses import dataclass
from pathlib import Path
import json
@dataclass(frozen=True)
class CompatibilityEntry: old:str; new:str; kind:str; minecraft_version:str; level:str; probability:float
class CompatibilityDatabase:
    def __init__(self,root:Path)->None: self.root=root; self.entries:list[CompatibilityEntry]=[]; self.load()
    def load(self)->None:
        self.root.mkdir(parents=True,exist_ok=True)
        for p in self.root.glob('*.json'):
            obj=json.loads(p.read_text(encoding='utf-8'))
            for e in obj.get('entries',[]): self.entries.append(CompatibilityEntry(e.get('old',''),e.get('new',''),e.get('kind','class'),e.get('mc','any'),e.get('level','medium'),float(e.get('probability',.5))))
    def find(self,symbol:str)->list[CompatibilityEntry]: return [e for e in self.entries if e.old==symbol or symbol in e.old]
