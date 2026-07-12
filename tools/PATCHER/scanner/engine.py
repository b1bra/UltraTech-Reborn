"""Sequential scanner pipeline with prioritized analyzer plugins for Minecraft JARs."""
from __future__ import annotations
from dataclasses import dataclass, field
from pathlib import Path
import hashlib, zipfile, re
from tools.PATCHER.core.models.entities import JarModel, ClassModel, Problem, Severity, Platform
from tools.PATCHER.core.interfaces.plugins import AnalyzerPlugin, PluginMetadata
@dataclass
class ScannerEngine:
    analyzers:list[AnalyzerPlugin]=field(default_factory=list)
    def register(self,a:AnalyzerPlugin)->None: self.analyzers.append(a); self.analyzers.sort(key=lambda x:x.metadata.priority)
    def scan(self,path:Path,progress=None)->JarModel:
        jar=JarModel(path=path)
        for i,a in enumerate(self.analyzers):
            jar=a.analyze(jar)
            if progress: progress(int((i+1)*95/max(1,len(self.analyzers))))
        return jar
class BaseAnalyzer(AnalyzerPlugin):
    def __init__(self,name:str,priority:int): self.metadata=PluginMetadata(name,'1.0',priority,name)
    def activate(self,registry): return None
class JarAnalyzer(BaseAnalyzer):
    def __init__(self): super().__init__('JarAnalyzer',10)
    def analyze(self,jar:JarModel)->JarModel:
        data=jar.path.read_bytes(); jar.size=len(data); jar.sha256=hashlib.sha256(data).hexdigest()
        with zipfile.ZipFile(jar.path) as z: jar.files=z.namelist(); jar.class_count=sum(f.endswith('.class') for f in jar.files); jar.libraries=[f for f in jar.files if f.endswith('.jar')]
        return jar
class StructureAnalyzer(BaseAnalyzer):
    def __init__(self): super().__init__('StructureAnalyzer',20)
    def analyze(self,jar:JarModel)->JarModel:
        names=set(jar.files)
        if 'mcmod.info' in names or 'META-INF/mods.toml' in names: jar.platform=Platform.FORGE
        if 'fabric.mod.json' in names: jar.platform=Platform.FABRIC if jar.platform==Platform.UNKNOWN else Platform.HYBRID
        if any('IFMLLoadingPlugin' in f or 'coremod' in f.lower() for f in names): jar.platform=Platform.COREMOD
        return jar
class ResourceAnalyzer(BaseAnalyzer):
    def __init__(self): super().__init__('ResourceAnalyzer',30)
    def analyze(self,jar:JarModel)->JarModel:
        if not any(f.startswith('assets/') for f in jar.files): jar.problems.append(Problem('Assets directory missing','ResourceAnalyzer',Severity.WARNING,.2,.7,'review_resources'))
        return jar
class ClassAnalyzer(BaseAnalyzer):
    def __init__(self): super().__init__('ClassAnalyzer',40)
    def analyze(self,jar:JarModel)->JarModel:
        for f in jar.files:
            if f.endswith('.class'):
                name=f[:-6].replace('/','.'); pkg=name.rsplit('.',1)[0] if '.' in name else ''; jar.classes.append(ClassModel(name,pkg,0))
        return jar
class BytecodeAnalyzer(BaseAnalyzer):
    def __init__(self): super().__init__('BytecodeAnalyzer',50)
    def analyze(self,jar:JarModel)->JarModel:
        legacy=[c.name for c in jar.classes if re.search(r'net\.minecraft\.src|lwjgl',c.name,re.I)]
        for c in legacy[:20]: jar.problems.append(Problem(f'Legacy bytecode reference candidate {c}','BytecodeAnalyzer',Severity.INFO,.35,.6,'replace_class_reference'))
        return jar
def default_scanner()->ScannerEngine:
    e=ScannerEngine(); [e.register(a) for a in [JarAnalyzer(),StructureAnalyzer(),ResourceAnalyzer(),ClassAnalyzer(),BytecodeAnalyzer()]]; return e
