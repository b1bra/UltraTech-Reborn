from __future__ import annotations

from dataclasses import dataclass
from patcher.core.event_bus import EventBus, MessageBus
from patcher.core.registry.services import ServiceRegistry

@dataclass(slots=True)
class CoreAPI:
    services: ServiceRegistry
    events: EventBus
    messages: MessageBus
