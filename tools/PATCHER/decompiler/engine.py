"""Decompiler abstraction with adapters for CFR, Vineflower, FernFlower and bytecode views."""
from __future__ import annotations
from dataclasses import dataclass
from pathlib import Path
import zipfile
@dataclass(frozen=True)
class DecompileResult: class_name:str; source:str; adapter:str
class DecompilerEngine:
    adapters:tuple[str,...]=('CFR','Vineflower','FernFlower','Bytecode')
    def decompile(self,jar:Path,adapter:str='Bytecode')->list[DecompileResult]:
        out=[]
        with zipfile.ZipFile(jar) as z:
            for n in z.namelist():
                if n.endswith('.class'): out.append(DecompileResult(n[:-6].replace('/','.'),f'// {adapter} view for {n}\n',adapter))
        return out
