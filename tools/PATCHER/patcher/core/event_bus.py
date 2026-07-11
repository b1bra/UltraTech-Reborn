from __future__ import annotations

from collections import defaultdict
from dataclasses import dataclass, field
from enum import Enum
from typing import Any, Callable
from uuid import uuid4

class EventType(str, Enum):
    JAR_OPENED = "JarOpened"
    SCANNER_STARTED = "ScannerStarted"
    SCANNER_FINISHED = "ScannerFinished"
    PATCH_STARTED = "PatchStarted"
    PATCH_FINISHED = "PatchFinished"
    VERIFICATION_STARTED = "VerificationStarted"
    VERIFICATION_FINISHED = "VerificationFinished"
    AI_STARTED = "AIStarted"
    AI_COMPLETED = "AICompleted"
    SANDBOX_STARTED = "SandboxStarted"
    SANDBOX_FINISHED = "SandboxFinished"
    CRASH_DETECTED = "CrashDetected"

@dataclass(slots=True)
class Event:
    type: EventType
    payload: dict[str, Any] = field(default_factory=dict)
    event_id: str = field(default_factory=lambda: str(uuid4()))

Subscriber = Callable[[Event], None]

class EventBus:
    def __init__(self) -> None:
        self._subscribers: dict[EventType, list[Subscriber]] = defaultdict(list)

    def subscribe(self, event_type: EventType, subscriber: Subscriber) -> None:
        self._subscribers[event_type].append(subscriber)

    def publish(self, event: Event) -> None:
        for subscriber in list(self._subscribers.get(event.type, [])):
            subscriber(event)

class MessageBus:
    def __init__(self) -> None:
        self._messages: list[Event] = []

    def send(self, event_type: EventType, **payload: Any) -> Event:
        event = Event(event_type, payload)
        self._messages.append(event)
        return event
