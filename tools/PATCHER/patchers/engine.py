"""Patch planner and safe ZIP-based patch engine; source JAR is never modified."""
from __future__ import annotations
from dataclasses import dataclass, field
from pathlib import Path
import shutil, zipfile, time
from tools.PATCHER.core.models.entities import JarModel, PatchPlan, FileChange, Problem, Severity
@dataclass
class PatchEngine:
    patchers:list[str]=field(default_factory=lambda:['replace_class_reference','replace_method_reference','replace_field_reference','generate_stub','modify_manifest','fix_reflection','fix_lwjgl'])
    def plan(self,jar:JarModel)->PatchPlan:
        plan=PatchPlan(problems=list(jar.problems)); plan.auto_fixable=sum(p.confidence*(1-p.risk)>.45 for p in plan.problems); plan.needs_review=len(plan.problems)-plan.auto_fixable; return plan
    def apply(self,jar:JarModel,plan:PatchPlan,confirm:bool=True)->JarModel:
        if not confirm: return jar
        out=jar.path.with_name(jar.path.stem+'-patched.jar'); shutil.copy2(jar.path,out)
        with zipfile.ZipFile(out,'a',zipfile.ZIP_DEFLATED) as z:
            report=f"PATCHER report\nproblems={len(plan.problems)}\nauto_fixable={plan.auto_fixable}\ntime={time.time()}\n"
            z.writestr('META-INF/PATCHER-REPORT.txt',report)
        jar.patched_path=out; jar.changes.append(FileChange('META-INF/PATCHER-REPORT.txt','added',0,len(report))); return jar
