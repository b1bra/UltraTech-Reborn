"""Reusable helpers for reading and flattening Minecraft NBT structures."""

from __future__ import annotations

import logging
from dataclasses import dataclass
from pathlib import Path
from typing import Any, Iterable

import nbtlib

LOGGER = logging.getLogger(__name__)

MOD_KEYWORDS = (
    "research", "progress", "energy", "machine", "quest", "rank",
    "currency", "achievement", "unlock", "forge", "loliland", "ultra",
)
VANILLA_NAMESPACES = {"minecraft"}


@dataclass(slots=True)
class NBTEntry:
    """A flattened NBT tag with its full path and a compact value preview."""

    path: str
    tag_type: str
    value_preview: str
    suspicious: bool = False


@dataclass(slots=True)
class SuspiciousNBT:
    """A likely modded/server-side NBT structure."""

    namespace: str
    tag: str
    path: str
    reason: str
    value_preview: str = ""


def read_nbt_file(path: Path, logger: logging.Logger | None = None) -> Any | None:
    """Read an NBT file without crashing the analysis on corrupt data."""

    log = logger or LOGGER
    try:
        return nbtlib.load(path)
    except Exception as exc:  # noqa: BLE001 - analyzer must tolerate bad saves.
        log.exception("Failed to read NBT file %s: %s", path, exc)
        return None


def unwrap_root(nbt_file: Any) -> Any:
    """Return the most useful root compound from an nbtlib File or raw tag."""

    return getattr(nbt_file, "root", nbt_file)


def tag_type(value: Any) -> str:
    """Return a readable NBT/Python type name."""

    return type(value).__name__


def short_value(value: Any, max_length: int = 160) -> str:
    """Create a bounded string representation suitable for Markdown reports."""

    if isinstance(value, dict):
        preview = f"{len(value)} keys: {', '.join(map(str, list(value.keys())[:8]))}"
    elif isinstance(value, (list, tuple)):
        preview = f"{len(value)} elements"
    else:
        preview = str(value)
    preview = preview.replace("\n", " ")
    return preview if len(preview) <= max_length else preview[: max_length - 3] + "..."


def iter_children(value: Any) -> Iterable[tuple[str, Any]]:
    """Yield child name/value pairs for compound and list-like NBT tags."""

    if isinstance(value, dict):
        yield from ((str(key), child) for key, child in value.items())
    elif isinstance(value, (list, tuple)):
        yield from ((f"[{index}]", child) for index, child in enumerate(value))


def flatten_nbt(value: Any, base_path: str, max_list_items: int = 200) -> list[NBTEntry]:
    """Recursively flatten NBT into entries while preserving full tag paths."""

    entries: list[NBTEntry] = []

    def visit(node: Any, path: str) -> None:
        suspicious = is_suspicious_path(path, node)
        entries.append(NBTEntry(path, tag_type(node), short_value(node), suspicious))
        children = list(iter_children(node))
        if isinstance(node, (list, tuple)) and len(children) > max_list_items:
            children = children[:max_list_items]
            entries.append(
                NBTEntry(f"{path}.[truncated]", "ListLimit", f"Only first {max_list_items} items shown")
            )
        for child_name, child in children:
            separator = "" if child_name.startswith("[") else "."
            visit(child, f"{path}{separator}{child_name}")

    visit(value, base_path)
    return entries


def is_suspicious_path(path: str, value: Any) -> bool:
    """Heuristic for finding modded or server-derived NBT data."""

    lowered = path.lower()
    if any(keyword in lowered for keyword in MOD_KEYWORDS):
        return True
    if ":" in lowered:
        namespace = lowered.split(":", 1)[0].split(".")[-1]
        return namespace not in VANILLA_NAMESPACES
    if isinstance(value, dict):
        return any(is_suspicious_path(f"{path}.{key}", child) for key, child in value.items())
    return False


def collect_suspicious(entries: Iterable[NBTEntry]) -> list[SuspiciousNBT]:
    """Convert suspicious flattened entries into report-friendly records."""

    records: list[SuspiciousNBT] = []
    for entry in entries:
        if not entry.suspicious:
            continue
        lowered = entry.path.lower()
        namespace = "unknown"
        if ":" in lowered:
            namespace = lowered.split(":", 1)[0].split(".")[-1]
        elif "forge" in lowered:
            namespace = "forge"
        elif "loliland" in lowered:
            namespace = "loliland"
        elif "ultra" in lowered:
            namespace = "ultra"
        matched = next((keyword for keyword in MOD_KEYWORDS if keyword in lowered), "modded namespace")
        records.append(SuspiciousNBT(namespace, entry.path.split(".")[-1], entry.path, matched, entry.value_preview))
    return records
