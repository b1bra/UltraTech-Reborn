"""Composition root registering all PATCHER services without coupling plugin implementations."""
from __future__ import annotations
from pathlib import Path
from tools.PATCHER.core.registry.service_registry import ServiceRegistry
from tools.PATCHER.core.events.event_bus import EventBus, MessageBus
from tools.PATCHER.core.logger.logger import PatcherLogger
from tools.PATCHER.core.config.config_manager import ConfigManager
from tools.PATCHER.core.scheduler.task_scheduler import TaskScheduler
from tools.PATCHER.core.resources.managers import ThemeManager, AnimationManager, ResourceManager
from tools.PATCHER.scanner.engine import default_scanner
from tools.PATCHER.patchers.engine import PatchEngine
from tools.PATCHER.ai.manager import AIManager
from tools.PATCHER.sandbox.manager import SandboxManager
from tools.PATCHER.decompiler.engine import DecompilerEngine
from tools.PATCHER.compatibility.database import CompatibilityDatabase

def build_registry(base:Path|None=None)->ServiceRegistry:
    base=base or Path.home()/'.patcher'; reg=ServiceRegistry()
    for name,svc in {'Logger':PatcherLogger(base/'logs'),'ConfigManager':ConfigManager(base/'config.json'),'EventBus':EventBus(),'MessageBus':MessageBus(),'TaskScheduler':TaskScheduler(),'ThemeManager':ThemeManager(),'AnimationManager':AnimationManager(),'ResourceManager':ResourceManager(base),'ScannerEngine':default_scanner(),'PatchEngine':PatchEngine(),'AIManager':AIManager(),'SandboxManager':SandboxManager(),'DecompilerEngine':DecompilerEngine(),'KnowledgeBase':CompatibilityDatabase(base/'compatibility')}.items(): reg.register(name,svc)
    return reg
