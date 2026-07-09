"""Robust Markdown parser and mod type classifier."""

from __future__ import annotations

from pathlib import Path
import re

from model import ModInfo

_FIELD_RE = re.compile(r"^\s*(?:[-*]\s*)?(?P<key>[A-Za-zА-Яа-я _-]+)\s*[:=]\s*(?P<value>.+?)\s*$")
_VERSION_RE = re.compile(r"(?:^|[-_\s])(?:v)?\d+(?:\.\d+){1,4}[a-z0-9.-]*(?:[-_\s]|$)", re.IGNORECASE)
_MC_RE = re.compile(r"(?:mc|minecraft)?\s*1\.\d+(?:\.\d+)?", re.IGNORECASE)
_HASH_RE = re.compile(r"[-_](?:master|main)[-_][0-9a-f]{7,40}$", re.IGNORECASE)
_SUFFIX_RE = re.compile(
    r"(?:[-_\s](?:forge|fabric|quilt|build|release|beta|alpha|snapshot|final|universal|dev|client|server|hotfix\d*|gtnh))+",
    re.IGNORECASE,
)
_CAMEL_RE = re.compile(r"(?<=[a-z0-9])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])")
_SPLIT_RE = re.compile(r"[-_+.]+")

FIELD_ALIASES = {
    "name": "name", "mod": "name", "title": "name", "название": "name", "мод": "name",
    "description": "description", "desc": "description", "описание": "description",
    "version": "version", "версия": "version",
    "category": "category", "категория": "category",
    "type": "type", "тип": "type",
    "dependencies": "dependencies", "depends": "dependencies", "зависимости": "dependencies",
    "coremod": "coremod", "core mod": "coremod",
    "asm": "asm", "api": "api",
}

TYPE_KEYWORDS: list[tuple[str, tuple[str, ...]]] = [
    ("CoreMod", ("coremod", "core mod", "ifmlLoadingPlugin")),
    ("ASM", ("asm", "bytecode", "transformer", "mixin")),
    ("API", (" api", "api ", "application programming", "openmods", "nei api")),
    ("Library", ("lib", "library", "bookshelf", "mantle", "bdlib", "codechickenlib", "wanionlib", "mmlib")),
    ("Performance", ("performance", "fps", "fast", "lag", "foamfix", "optifine", "betterfps")),
    ("Optimization", ("optimization", "optimisation", "cache", "patcher")),
    ("Rendering", ("render", "shader", "texture", "model", "lighting")),
    ("HUD", ("hud", "waila", "tooltip", "overlay", "journeymap", "minimap")),
    ("Client", ("client", "mouse", "inventory tweaks", "nei", "not enough items")),
    ("World Generation", ("worldgen", "world generation", "ore gen", "biome", "rtg", "dungeon")),
    ("Dimensions", ("dimension", "galaxy", "space", "twilight", "planet")),
    ("Magic", ("magic", "thaum", "botania", "blood magic", "witch")),
    ("Technology", ("technology", "industrial", "thermal", "tech", "computers", "minefactory")),
    ("Machines", ("machine", "factory", "reactor", "turbine", "generator", "processing")),
    ("Energy", ("energy", "rf", "eu", "power", "nuclear")),
    ("Storage", ("storage", "chest", "drawer", "barrel", "applied energistics", "ae2")),
    ("Transport", ("transport", "pipe", "duct", "logistics", "rail", "cart")),
    ("Network", ("network", "computer", "packet", "wireless")),
    ("Adventure", ("adventure", "quest", "rpg", "dungeon", "boss")),
    ("Mobs", ("mob", "creature", "entity", "monster", "animal")),
    ("Decoration", ("decoration", "decor", "furniture", "chisel", "blocks")),
    ("Food", ("food", "cooking", "hunger", "spice", "pam")),
    ("Agriculture", ("agriculture", "farm", "crop", "bee", "forestry", "gendustry")),
    ("Audio", ("audio", "sound", "music")),
    ("Compatibility", ("compat", "compatibility", "bridge")),
    ("Integration", ("integration", "integrates", "support for")),
    ("Addon", ("addon", "add-on", "extension", "expansion")),
    ("Development", ("development", "debug", "dev tool", "logger")),
    ("Utility", ("utility", "helper", "tweak", "fix", "tool", "utils")),
    ("Core", ("core",)),
]


def parse_modlist(path: Path) -> list[ModInfo]:
    """Parse free-form modlist.md content into ModInfo objects."""
    if not path.exists():
        path.write_text("# Mod list\n\n- NotEnoughItems-1.0.5.jar\n- CoFHCore-1.7.10-3.1.4.jar\n", encoding="utf-8")
    text = path.read_text(encoding="utf-8")
    blocks = _split_blocks(text)
    mods: list[ModInfo] = []
    for block in blocks:
        mod = _mod_from_block(block)
        if mod.raw_name:
            mods.append(mod)
    return mods


def _split_blocks(text: str) -> list[list[str]]:
    blocks: list[list[str]] = []
    current: list[str] = []
    for line in text.splitlines():
        stripped = line.strip()
        if not stripped:
            if current:
                blocks.append(current)
                current = []
            continue
        if stripped.startswith("#"):
            if current:
                blocks.append(current)
                current = []
            title = stripped.lstrip("#").strip()
            if title and not title.lower().startswith(("mod list", "mods", "список")):
                current.append(f"name: {title}")
            continue
        if re.match(r"^[-*+]\s+\S", stripped) and current and any(_FIELD_RE.match(x) for x in current):
            blocks.append(current)
            current = []
        current.append(stripped)
    if current:
        blocks.append(current)
    return blocks


def _mod_from_block(lines: list[str]) -> ModInfo:
    fields: dict[str, str] = {}
    loose: list[str] = []
    for line in lines:
        match = _FIELD_RE.match(line)
        if match:
            key = FIELD_ALIASES.get(match.group("key").strip().lower())
            if key:
                fields[key] = match.group("value").strip()
                continue
        loose.append(re.sub(r"^[-*+]\s+", "", line).strip())
    raw_name = fields.get("name") or (loose[0] if loose else "")
    description = fields.get("description") or " ".join(loose[1:])
    version = fields.get("version") or _extract_version(raw_name)
    dependencies = _split_list(fields.get("dependencies", ""))
    detected_type = fields.get("type") or _detect_type(raw_name, description, fields.get("category", ""), dependencies, fields)
    return ModInfo(
        raw_name=raw_name,
        display_name=clean_mod_name(raw_name),
        description=description,
        version=version,
        category=fields.get("category", ""),
        detected_type=detected_type,
        dependencies=dependencies,
        coremod=_truthy(fields.get("coremod", "")),
        asm=_truthy(fields.get("asm", "")),
        api=_truthy(fields.get("api", "")),
    )


def clean_mod_name(raw_name: str) -> str:
    name = Path(raw_name.strip()).name
    name = re.sub(r"\.(?:jar|zip|disabled)$", "", name, flags=re.IGNORECASE)
    name = _HASH_RE.sub("", name)
    name = _VERSION_RE.sub(" ", name)
    name = _MC_RE.sub("", name)
    name = _SUFFIX_RE.sub(" ", name)
    name = _SPLIT_RE.sub(" ", name)
    name = re.sub(r"\b\d+[a-z]?\b", " ", name, flags=re.IGNORECASE)
    name = " ".join(_CAMEL_RE.sub(" ", part) for part in name.split())
    return re.sub(r"\s+", " ", name).strip(" -_") or raw_name


def _extract_version(raw_name: str) -> str:
    raw_name = re.sub(r"\.(?:jar|zip|disabled)$", "", Path(raw_name.strip()).name, flags=re.IGNORECASE)
    match = _VERSION_RE.search(raw_name)
    return match.group(0).strip("-_ ") if match else ""


def _split_list(value: str) -> list[str]:
    return [item.strip() for item in re.split(r"[,;|]", value) if item.strip()]


def _truthy(value: str) -> bool:
    return value.strip().lower() in {"1", "true", "yes", "да", "y", "on"}


def _detect_type(name: str, description: str, category: str, dependencies: list[str], fields: dict[str, str]) -> str:
    if _truthy(fields.get("coremod", "")):
        return "CoreMod"
    if _truthy(fields.get("asm", "")):
        return "ASM"
    if _truthy(fields.get("api", "")):
        return "API"
    haystack = f" {name} {description} {category} {' '.join(dependencies)} ".lower()
    for mod_type, keywords in TYPE_KEYWORDS:
        if any(keyword in haystack for keyword in keywords):
            return mod_type
    return "Unknown"
