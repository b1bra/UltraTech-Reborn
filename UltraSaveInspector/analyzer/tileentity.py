"""Region and TileEntity analyzer."""

from __future__ import annotations

import logging
from dataclasses import dataclass, field
from pathlib import Path
from typing import Any

import gzip
import struct
import zlib
from io import BytesIO

import nbtlib

from .nbt_parser import NBTEntry, SuspiciousNBT, collect_suspicious, flatten_nbt, short_value

LOGGER = logging.getLogger(__name__)


@dataclass(slots=True)
class TileEntityAnalysis:
    id: str
    position: str
    region_file: Path
    entries: list[NBTEntry] = field(default_factory=list)
    suspicious: list[SuspiciousNBT] = field(default_factory=list)


def analyze_tile_entities(world_path: Path, logger: logging.Logger | None = None) -> list[TileEntityAnalysis]:
    """Scan MCA region chunks and extract block entity / tile entity compounds."""

    log = logger or LOGGER
    region_dir = world_path / "region"
    if not region_dir.exists():
        return []
    tiles: list[TileEntityAnalysis] = []
    for region_file in sorted(region_dir.glob("*.mca")):
        for chunk in _iter_region_chunks(region_file, log):
            tiles.extend(_extract_tiles_from_chunk(chunk, region_file))
    return tiles


def _iter_region_chunks(region_file: Path, log: logging.Logger) -> list[Any]:
    """Read chunks from an Anvil .mca file using the standard region header."""

    chunks: list[Any] = []
    try:
        raw = region_file.read_bytes()
        if len(raw) < 8192:
            return chunks
        for index in range(1024):
            offset = int.from_bytes(raw[index * 4 : index * 4 + 3], "big") * 4096
            sectors = raw[index * 4 + 3]
            if offset <= 0 or sectors <= 0 or offset + 5 > len(raw):
                continue
            length = struct.unpack(">I", raw[offset : offset + 4])[0]
            compression = raw[offset + 4]
            payload = raw[offset + 5 : offset + 4 + length]
            if compression == 1:
                decoded = gzip.decompress(payload)
            elif compression == 2:
                decoded = zlib.decompress(payload)
            else:
                log.warning("Unsupported chunk compression %s in %s", compression, region_file)
                continue
            chunks.append(nbtlib.File.parse(BytesIO(decoded)))
    except Exception as exc:  # noqa: BLE001 - corrupted regions should be logged, not fatal.
        log.exception("Failed to analyze region %s: %s", region_file, exc)
    return chunks


def _extract_tiles_from_chunk(chunk: Any, region_file: Path) -> list[TileEntityAnalysis]:
    root = getattr(chunk, "root", chunk)
    level = root.get("Level", root) if isinstance(root, dict) else root
    candidates = []
    if isinstance(level, dict):
        candidates.extend(level.get("TileEntities", []))
        candidates.extend(level.get("block_entities", []))
    analyses: list[TileEntityAnalysis] = []
    for tile in candidates:
        if not isinstance(tile, dict):
            continue
        tile_id = short_value(tile.get("id", "unknown"))
        position = _position(tile)
        base_path = f"tileentity.{tile_id}.{position}"
        entries = flatten_nbt(tile, base_path)
        analyses.append(TileEntityAnalysis(tile_id, position, region_file, entries, collect_suspicious(entries)))
    return analyses


def _position(tile: dict[str, Any]) -> str:
    x = tile.get("x", tile.get("X", "?"))
    y = tile.get("y", tile.get("Y", "?"))
    z = tile.get("z", tile.get("Z", "?"))
    return f"x={x}, y={y}, z={z}"
