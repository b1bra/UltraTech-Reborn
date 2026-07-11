from __future__ import annotations

from patcher.core.interfaces.plugins import CorePlugin
from patcher.core.models.domain import ServiceHealth

class PluginManager:
    def __init__(self) -> None:
        self._plugins: dict[str, CorePlugin] = {}

    def register(self, plugin: CorePlugin) -> None:
        self._plugins[plugin.metadata.plugin_id] = plugin

    def initialize_all(self, api: object) -> None:
        for plugin in self._plugins.values():
            plugin.initialize(api)

    def execute_all(self, payload: object) -> list[object]:
        return [plugin.execute(payload) for plugin in self._plugins.values()]

    def health(self) -> dict[str, ServiceHealth]:
        return {pid: plugin.health() for pid, plugin in self._plugins.items()}
