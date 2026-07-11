from __future__ import annotations

from typing import Any, TypeVar

T = TypeVar("T")

class ServiceRegistry:
    def __init__(self) -> None:
        self._services: dict[str, Any] = {}

    def register(self, service_id: str, service: Any) -> None:
        self._services[service_id] = service

    def require(self, service_id: str, expected_type: type[T] | None = None) -> T:
        service = self._services[service_id]
        if expected_type is not None and not isinstance(service, expected_type):
            raise TypeError(f"Service {service_id} is not {expected_type.__name__}")
        return service

    def all(self) -> dict[str, Any]:
        return dict(self._services)
