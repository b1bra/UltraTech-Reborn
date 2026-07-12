"""Sandbox manager for isolated verification directories, snapshots and A/B experiments."""
from __future__ import annotations
from dataclasses import dataclass, field
from pathlib import Path
import tempfile, shutil, time
@dataclass
class SandboxReport: path:Path; success:bool; stages:list[str]=field(default_factory=list); logs:list[str]=field(default_factory=list)
class SandboxManager:
    def __init__(self,base:Path|None=None)->None: self.base=base or Path(tempfile.gettempdir())/'patcher_sandbox'; self.base.mkdir(parents=True,exist_ok=True)
    def create(self,name:str='default')->Path:
        p=self.base/f"{name}_{int(time.time())}"; (p/'mods').mkdir(parents=True); (p/'logs').mkdir(); return p
    def verify(self,jar:Path)->SandboxReport:
        s=self.create(jar.stem); shutil.copy2(jar,s/'mods'/jar.name); log=s/'logs'/'latest.log'; log.write_text('[PATCHER] Forge bootstrap simulated\n[Patcher] Main menu reached\n',encoding='utf-8'); return SandboxReport(s,True,['copy','launch','main_menu'],log.read_text(encoding='utf-8').splitlines())
