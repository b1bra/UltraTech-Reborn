from __future__ import annotations

from abc import ABC, abstractmethod
from typing import Any
from patcher.core.models.domain import PluginMetadata, ServiceHealth

class CorePlugin(ABC):
    @property
    @abstractmethod
    def metadata(self) -> PluginMetadata: ...

    @abstractmethod
    def initialize(self, api: Any) -> None: ...

    @abstractmethod
    def shutdown(self) -> None: ...

    @abstractmethod
    def execute(self, payload: Any) -> Any: ...

    @abstractmethod
    def health(self) -> ServiceHealth: ...
