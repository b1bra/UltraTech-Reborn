"""Migration stage planner for generated SCAN data.

The planner is imported by SCAN.  It consumes the existing ModScanner graph plus
extended dependency scanner output and emits JSON only for downstream viewers.
"""
from __future__ import annotations
import json
from pathlib import Path
from typing import Any

STAGE_RULES = [
    ("Libraries", ("lib", "core", "baubles", "codechicken", "cofh", "brandon", "mantle")),
    ("API", ("api", "jei", "waila")),
    ("Technology", ("tech", "thermal", "ender", "ic2", "mekanism", "ae2", "applied")),
    ("Magic", ("magic", "thaum", "botania", "blood")),
    ("Content", ("content", "biome", "mob", "world", "dimension")),
    ("Integrations", ("compat", "integration", "addon", "tweaker")),
]

def create_migration_plan(output_dir: Path, logger: Any | None = None) -> dict[str, Any]:
    """Read generated scan artifacts and create migration stages/diagnostics."""
    graph_path = output_dir / "mod_graph.json"
    deps_path = output_dir / "dependency_scanner.json"
    mods = json.loads(graph_path.read_text(encoding="utf-8")) if graph_path.exists() else []
    hidden = json.loads(deps_path.read_text(encoding="utf-8")) if deps_path.exists() else {"edges": [], "mods": []}
    nodes = [_node_id(mod) for mod in mods]
    node_set = {n.lower() for n in nodes}
    edges = []
    for mod in mods:
        src = _node_id(mod)
        for dep in mod.get("dependencies") or []:
            edges.append({"from": src, "to": str(dep), "type": "declared", "confidence": 100})
    for edge in hidden.get("edges", []):
        edges.append({"from": edge.get("from", ""), "to": edge.get("to", ""), "type": ",".join(edge.get("categories", [])), "confidence": edge.get("confidence", 0)})
    edges = _dedupe_edges(edges)
    plan = {
        "format": "ultratech-migration-plan-v1",
        "stages": _build_stages(mods),
        "nodes": nodes,
        "edges": edges,
        "cycles": _find_cycles(nodes, edges),
        "dead_dependencies": [e for e in edges if e["to"].lower() not in node_set and e.get("confidence", 0) >= 80],
        "missing_dependencies": [e for e in edges if e["to"].lower() not in node_set and e.get("type") == "declared"],
        "isolated_mods": _isolated(nodes, edges),
        "blocked_mods": _blocked(nodes, edges, node_set),
        "most_critical_mods": _critical(nodes, edges),
        "longest_dependency_chain": _longest_chain(nodes, edges),
        "parallel_migration_groups": [],
    }
    plan["parallel_migration_groups"] = _parallel_groups(plan["stages"], edges)
    out_dir = output_dir / "PLANER"
    out_dir.mkdir(parents=True, exist_ok=True)
    out_path = out_dir / "migration_plan.json"
    out_path.write_text(json.dumps(plan, indent=2, ensure_ascii=False), encoding="utf-8")
    if logger: logger.log_file(out_path, "Migration planner output")
    return plan

def _node_id(mod: dict[str, Any]) -> str:
    return str(mod.get("modid") or mod.get("name") or mod.get("file") or "Unknown")

def _build_stages(mods: list[dict[str, Any]]) -> list[dict[str, Any]]:
    buckets = {name: [] for name, _ in STAGE_RULES}
    for mod in mods:
        node = _node_id(mod); hay = " ".join(map(str, [node, mod.get("name", ""), mod.get("type", ""), mod.get("difficulty", "")])).lower()
        for stage, needles in STAGE_RULES:
            if any(n in hay for n in needles): buckets[stage].append(node); break
        else: buckets["Content"].append(node)
    return [{"index": i, "name": name, "mods": sorted(set(items))} for i, (name, _) in enumerate(STAGE_RULES, 1) for items in [buckets[name]]]

def _dedupe_edges(edges):
    data = {}
    for e in edges:
        if not e.get("from") or not e.get("to"): continue
        k=(e["from"].lower(), e["to"].lower())
        data[k] = {**data.get(k, {}), **e, "confidence": max(data.get(k, {}).get("confidence", 0), e.get("confidence", 0))}
    return list(data.values())

def _adj(nodes, edges):
    ns={n.lower(): n for n in nodes}; a={n: [] for n in nodes}
    for e in edges:
        s=ns.get(str(e["from"]).lower()); t=ns.get(str(e["to"]).lower())
        if s and t: a[s].append(t)
    return a

def _find_cycles(nodes, edges):
    a=_adj(nodes, edges); cycles=[]; stack=[]; seen=set()
    def dfs(n):
        seen.add(n); stack.append(n)
        for m in a[n]:
            if m in stack: cycles.append(stack[stack.index(m):]+[m])
            elif m not in seen: dfs(m)
        stack.pop()
    for n in nodes:
        if n not in seen: dfs(n)
    return cycles[:50]

def _isolated(nodes, edges):
    touched={x.lower() for e in edges for x in (e["from"], e["to"])}
    return [n for n in nodes if n.lower() not in touched]

def _blocked(nodes, edges, node_set):
    blocked={e["from"] for e in edges if e["to"].lower() not in node_set and e.get("confidence", 0) >= 80}
    return sorted(blocked)

def _critical(nodes, edges):
    score={n:0 for n in nodes}
    for e in edges:
        for n in nodes:
            if e["to"].lower()==n.lower(): score[n]+=1
    return sorted(score, key=score.get, reverse=True)[:20]

def _longest_chain(nodes, edges):
    a=_adj(nodes, edges); best=[]
    def dfs(n,path):
        nonlocal best
        if len(path)>len(best): best=path[:]
        for m in a[n]:
            if m not in path: dfs(m,path+[m])
    for n in nodes: dfs(n,[n])
    return best

def _parallel_groups(stages, edges):
    deps={e["from"].lower(): e["to"].lower() for e in edges}
    return [{"stage": s["name"], "groups": [[m for m in s["mods"] if deps.get(m.lower()) != other.lower()] for other in s["mods"][:1]] or [s["mods"]]} for s in stages]
