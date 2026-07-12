"""JSON5-backed configuration manager for launchers, tools, API file, window state and history."""
from __future__ import annotations
from dataclasses import dataclass, field, asdict
from pathlib import Path
from typing import Any
import json
try: import json5
except Exception: json5=None

@dataclass
class AppConfig:
    launchers:list[str]=field(default_factory=list); api_file:str=""; tools:dict[str,str]=field(default_factory=dict); chat_history:list[dict[str,str]]=field(default_factory=list); last_models:list[str]=field(default_factory=list); feature_flags:dict[str,bool]=field(default_factory=lambda:{"ai":True,"sandbox":True})
class ConfigManager:
    def __init__(self,path:Path)->None: self.path=path; self.path.parent.mkdir(parents=True,exist_ok=True); self.data=AppConfig(); self.load()
    def load(self)->AppConfig:
        if self.path.exists():
            raw=self.path.read_text(encoding='utf-8'); obj=(json5.loads(raw) if json5 else json.loads(raw)); self.data=AppConfig(**{**asdict(AppConfig()),**obj})
        return self.data
    def save(self)->None: self.path.write_text(json.dumps(asdict(self.data),ensure_ascii=False,indent=2),encoding='utf-8')
    def add_launcher(self,path:str)->bool:
        p=str(Path(path));
        if p not in self.data.launchers: self.data.launchers.append(p); self.save(); return True
        return False
    def set(self,key:str,value:Any)->None: setattr(self.data,key,value); self.save()
    def get(self,key:str,default:Any=None)->Any: return getattr(self.data,key,default)
