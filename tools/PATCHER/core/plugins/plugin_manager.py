"""Runtime plugin manager for PATCHER.

Plugins are regular Python objects with metadata and default operational methods.
The manager keeps them decoupled from concrete services: it activates plugins with
the ServiceRegistry, groups them by capability, sorts by priority and exposes
health information for diagnostics. This is intentionally deterministic so that the
scanner, patcher and verification pipelines can be reproduced in bug reports.
"""
from __future__ import annotations
from dataclasses import dataclass, field
from importlib import import_module
from typing import Any, Iterable
from tools.PATCHER.core.interfaces.plugins import Plugin, AnalyzerPlugin, PatcherPlugin, VerificationPlugin

@dataclass
class PluginState:
    name: str
    version: str
    priority: int
    active: bool
    capabilities: list[str]
    message: str = "registered"

@dataclass
class PluginManager:
    registry: Any | None = None
    plugins: list[Plugin] = field(default_factory=list)
    states: dict[str, PluginState] = field(default_factory=dict)

    def register(self, plugin: Plugin) -> PluginState:
        self.plugins.append(plugin)
        self.plugins.sort(key=lambda item: item.metadata.priority)
        state = PluginState(plugin.metadata.name, plugin.metadata.version, plugin.metadata.priority, False, plugin.metadata.capabilities)
        self.states[plugin.metadata.name] = state
        return state

    def register_many(self, plugins: Iterable[Plugin]) -> list[PluginState]:
        return [self.register(plugin) for plugin in plugins]

    def activate_all(self) -> list[PluginState]:
        activated: list[PluginState] = []
        for plugin in self.plugins:
            result = plugin.activate(self.registry)
            state = self.states[plugin.metadata.name]
            state.active = True
            state.message = result.get("status", "active")
            activated.append(state)
        return activated

    def load_from_path(self, dotted_path: str, class_name: str) -> PluginState:
        module = import_module(dotted_path)
        plugin_class = getattr(module, class_name)
        plugin = plugin_class()
        return self.register(plugin)

    def analyzers(self) -> list[AnalyzerPlugin]:
        return [plugin for plugin in self.plugins if isinstance(plugin, AnalyzerPlugin)]

    def patchers(self) -> list[PatcherPlugin]:
        return [plugin for plugin in self.plugins if isinstance(plugin, PatcherPlugin)]

    def verifiers(self) -> list[VerificationPlugin]:
        return [plugin for plugin in self.plugins if isinstance(plugin, VerificationPlugin)]

    def health(self) -> dict[str, Any]:
        return {
            "total": len(self.plugins),
            "active": sum(1 for state in self.states.values() if state.active),
            "plugins": [state.__dict__.copy() for state in self.states.values()],
        }
