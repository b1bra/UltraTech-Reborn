"""Paths and color resources used by the application."""

from __future__ import annotations

from pathlib import Path

APP_DIR = Path(__file__).resolve().parent


def find_repository_root(start: Path) -> Path:
    """Find the repository root by walking up from the tracker directory."""
    for candidate in (start, *start.parents):
        if (candidate / ".git").exists() or (candidate / "docs" / "generated" / "modpack").exists():
            return candidate
    return start


REPO_ROOT = find_repository_root(APP_DIR)
GENERATED_MODLIST_PATH = REPO_ROOT / "docs" / "generated" / "modpack" / "modlist.md"
LOCAL_MODLIST_PATH = APP_DIR / "modlist.md"
MODLIST_PATH = GENERATED_MODLIST_PATH if GENERATED_MODLIST_PATH.exists() else LOCAL_MODLIST_PATH
PROGRESS_PATH = APP_DIR / "progress.json"

TYPE_COLORS: dict[str, str] = {
    "Library": "#9B6DFF",
    "Core": "#F2C14E",
    "CoreMod": "#E74C3C",
    "ASM": "#FF8C42",
    "API": "#4DB6E8",
    "World Generation": "#62D26F",
    "Technology": "#377DFF",
    "Magic": "#C77DFF",
    "Adventure": "#2DD4BF",
    "Decoration": "#F59ECA",
    "Storage": "#A97155",
    "Utility": "#A3E635",
    "Performance": "#22C55E",
    "Optimization": "#84CC16",
    "Client": "#FF6FB1",
    "HUD": "#38BDF8",
    "Rendering": "#60A5FA",
    "Audio": "#F472B6",
    "Compatibility": "#CBD5E1",
    "Integration": "#14B8A6",
    "Addon": "#F97316",
    "Food": "#FBBF24",
    "Agriculture": "#65A30D",
    "Dimensions": "#8B5CF6",
    "Mobs": "#EF4444",
    "Energy": "#FACC15",
    "Transport": "#06B6D4",
    "Network": "#0EA5E9",
    "Machines": "#64748B",
    "Development": "#D946EF",
    "Unknown": "#D1D5DB",
}

STATUS_PENDING = "#D8DCE4"
STATUS_DONE = "#D84E4E"
