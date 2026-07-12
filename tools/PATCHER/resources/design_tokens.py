"""Centralized design tokens for PATCHER; no UI magic numbers live outside this module."""
from dataclasses import dataclass
from enum import Enum

class ColorRole(str, Enum):
    BACKGROUND="background"; CARD="card"; HOVER="hover"; TEXT="text"; MUTED="muted"; ACCENT="accent"; ERROR="error"; SUCCESS="success"; BORDER="border"; TOAST="toast"

@dataclass(frozen=True)
class Palette:
    background:str="#0D1117"; card:str="#161B22"; hover:str="#1C2128"; text:str="#E6EDF3"; muted:str="#8B949E"; accent:str="#1F6FEB"; error:str="#F85149"; success:str="#3FB950"; border:str="#21262D"; toast:str="#263849"

@dataclass(frozen=True)
class Dimensions:
    radius:int=8; gap:int=8; padding:int=14; card_height:int=52; scrollbar:int=6; titlebar:int=42; min_width:int=1280; min_height:int=800; start_width:int=1400; start_height:int=900; dialog_width:int=500; dialog_height:int=400; progress_gap:int=2

@dataclass(frozen=True)
class AnimationTokens:
    fast_ms:int=200; normal_ms:int=300; easing_fast:str="OutCubic"; easing_normal:str="OutQuart"

@dataclass(frozen=True)
class Strings:
    app_name:str="PATCHER"; add_launcher:str="Добавить лаунчер"; choose_jar:str="Выбрать JAR"; drop_jar:str="Перетащите JAR мод сюда"; ok:str="OK"; continue_without_launcher:str="Продолжить без лаунчера"; choose_launcher_required:str="Необходимо выбрать лаунчер"; patch:str="Patch"; verify:str="Verify"; save_patched:str="Сохранить пропатченный мод"

PALETTE=Palette(); DIMENSIONS=Dimensions(); ANIMATIONS=AnimationTokens(); STRINGS=Strings()
