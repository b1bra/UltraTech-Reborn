from __future__ import annotations

from collections.abc import Callable
from dataclasses import dataclass, field
from typing import Generic, TypeVar

T = TypeVar("T")

@dataclass
class SignalBus(Generic[T]):
    _subscribers: list[Callable[[T], None]] = field(default_factory=list)

    def subscribe(self, callback: Callable[[T], None]) -> None:
        self._subscribers.append(callback)

    def publish(self, event: T) -> None:
        for callback in tuple(self._subscribers):
            callback(event)
