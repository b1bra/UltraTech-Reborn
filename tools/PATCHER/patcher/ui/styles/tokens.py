from __future__ import annotations

from dataclasses import dataclass

@dataclass(frozen=True, slots=True)
class Colors:
    background: str = "#1e1e1e"
    surface: str = "#2b2b2b"
    surface_hover: str = "#3a3a3a"
    border: str = "#3a3a3a"
    text: str = "#f0f0f0"
    muted: str = "#b8b8b8"
    progress_loaded: str = "#2f8f46"
    progress_remaining: str = "#8f3434"

@dataclass(frozen=True, slots=True)
class Sizes:
    radius: int = 12
    padding: int = 14
    gap: int = 12
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
    ai_chat: str = "Чат с ИИ"
    save_mod: str = "Сохранить мод"

COLORS = Colors()
SIZES = Sizes()
STRINGS = Strings()
