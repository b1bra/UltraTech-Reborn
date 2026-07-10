from __future__ import annotations

from dataclasses import dataclass


@dataclass(frozen=True)
class Layout:
    window_width: int = 1180
    window_height: int = 760
    title_bar_height: int = 52
    radius_large: int = 28
    radius_medium: int = 18
    radius_small: int = 12
    spacing: int = 18
    card_height: int = 126
    progress_height: int = 10
    animation_fast_ms: int = 180
    animation_normal_ms: int = 320
    animation_slow_ms: int = 520


LAYOUT = Layout()
SUPPORTED_JAR_EXTENSION = ".jar"
OPTIONAL_DOC_PATHS = ("docs/generated/launcher", "docs/generated/modpack", "docs/generated/logs")
