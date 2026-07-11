from __future__ import annotations

from dataclasses import dataclass

@dataclass(frozen=True, slots=True)
class Colors:
    background: str = "#1e1f22"
    surface: str = "#25262b"
    surface_hover: str = "#30323a"
    border: str = "#3b3d46"
    text: str = "#e6e8ef"
    muted: str = "#9aa3b2"
    accent: str = "#7aa2f7"
    danger_hover: str = "#8a5a5f"
    notification: str = "#516274"

@dataclass(frozen=True, slots=True)
class Sizes:
    radius: int = 14
    padding: int = 18
    gap: int = 14
    window_width: int = 1280
    window_height: int = 780
    title_height: int = 42
    animation_fast_ms: int = 140
    animation_ms: int = 240
    animation_slow_ms: int = 360

@dataclass(frozen=True, slots=True)
class Strings:
    app_name: str = "PATCHER"
    add_launcher: str = "Добавить лаунчер"
    choose_launcher_required: str = "Необходимо выбрать лаунчер"
    continue_without_launcher: str = "Продолжить без лаунчера"

COLORS = Colors()
SIZES = Sizes()
STRINGS = Strings()
