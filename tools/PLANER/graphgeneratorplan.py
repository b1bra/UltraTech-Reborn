"""Custom UltraTech viewer JSON generator.

This is intentionally not GraphViz, Mermaid or NetworkX JSON.  The schema is a
project-specific canvas document for the PySide6 viewer.
"""
from __future__ import annotations
import json, math
from pathlib import Path
from typing import Any

COLORS={"Libraries":"#7D9AFF","API":"#55D6BE","Technology":"#FFB86C","Magic":"#C792EA","Content":"#8BE9FD","Integrations":"#FF79C6"}

def create_viewer_plan(output_dir: Path, migration_plan: dict[str, Any] | None = None, logger: Any | None = None) -> dict[str, Any]:
    """Convert migration planner output into custom visualization JSON."""
    planer_dir=output_dir/"PLANER"; planer_dir.mkdir(parents=True, exist_ok=True)
    if migration_plan is None:
        migration_plan=json.loads((planer_dir/"migration_plan.json").read_text(encoding="utf-8"))
    nodes=[]; groups=[]; idx=0
    for stage in migration_plan.get("stages",[]):
        group_id=f"stage-{stage['index']}"; groups.append({"id":group_id,"title":stage["name"],"collapsed":False,"color":COLORS.get(stage["name"],"#AAAAAA")})
        for local, mod in enumerate(stage.get("mods", [])):
            nodes.append({"id":mod,"label":mod,"group":group_id,"status":"blocked" if mod in migration_plan.get("blocked_mods",[]) else "ready","category":stage["name"],"position":{"x":stage["index"]*300,"y":local*120},"size":{"w":220,"h":76},"colors":{"fill":"#20242C","accent":COLORS.get(stage["name"],"#AAAAAA"),"text":"#F3F5FA"},"animation":{"hover_ms":160,"select_ms":180,"move_ms":120},"collapsed":False}); idx+=1
    doc={"format":"ultratech-viewer-plan-v1","nodes":nodes,"groups":groups,"edges":[{"from":e["from"],"to":e["to"],"status":e.get("type","dependency"),"confidence":e.get("confidence",0),"color":"#6C768A","animation":{"flow":True,"duration_ms":1200}} for e in migration_plan.get("edges",[])],"colors":{"background":"#D7D9DE","grid":"#C4C8D0","selection":"#7D9AFF","warning":"#FFB86C","danger":"#FF5570"},"status":{"cycles":migration_plan.get("cycles",[]),"missing":migration_plan.get("missing_dependencies",[])},"category":{"stages":[s["name"] for s in migration_plan.get("stages",[])]},"position":{"layout":"staged-compact","allow_manual_overlap":True},"animation":{"zoom_ms":180,"pan_inertia":True,"node_hover_ms":160,"tab_ms":140,"menu_ms":120},"collapsed":{"default":False,"groups":{}},"viewport":{"x":0,"y":0,"zoom":1.0,"min_zoom":0.2,"max_zoom":3.5,"scrollbars":"photoshop-canvas"}}
    out=planer_dir/"viewer_plan.json"; out.write_text(json.dumps(doc,indent=2,ensure_ascii=False),encoding="utf-8")
    if logger: logger.log_file(out,"Viewer graph plan")
    return doc
