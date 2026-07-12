"""Unified PATCHER logger writing module logs and errors.log in the requested format."""
from __future__ import annotations
from dataclasses import dataclass
from datetime import datetime
from enum import Enum
from pathlib import Path
from threading import RLock

class LogLevel(str, Enum): DEBUG="DEBUG"; INFO="INFO"; WARNING="WARNING"; ERROR="ERROR"
@dataclass(frozen=True)
class LogRecord: time:str; module:str; level:LogLevel; message:str
class PatcherLogger:
    def __init__(self, log_dir: Path) -> None:
        self.log_dir=log_dir; self.log_dir.mkdir(parents=True, exist_ok=True); self._lock=RLock(); self.records:list[LogRecord]=[]
    def log(self,module:str,level:LogLevel,message:str)->None:
        rec=LogRecord(datetime.now().strftime('%H:%M:%S'),module,level,message); line=f"[{rec.time}] [{module}] [{level.value}] {message}\n"
        with self._lock:
            self.records.append(rec); (self.log_dir/'patcher.log').open('a',encoding='utf-8').write(line)
            if level in {LogLevel.ERROR}: (self.log_dir/'errors.log').open('a',encoding='utf-8').write(line)
    def debug(self,m,msg): self.log(m,LogLevel.DEBUG,msg)
    def info(self,m,msg): self.log(m,LogLevel.INFO,msg)
    def warning(self,m,msg): self.log(m,LogLevel.WARNING,msg)
    def error(self,m,msg): self.log(m,LogLevel.ERROR,msg)
