from __future__ import annotations

from concurrent.futures import Future, ThreadPoolExecutor
from typing import Callable, TypeVar

T = TypeVar("T")

class TaskScheduler:
    def __init__(self, max_workers: int = 4) -> None:
        self._executor = ThreadPoolExecutor(max_workers=max_workers, thread_name_prefix="PATCHER")

    def submit(self, fn: Callable[..., T], *args: object, **kwargs: object) -> Future[T]:
        return self._executor.submit(fn, *args, **kwargs)

    def shutdown(self) -> None:
        self._executor.shutdown(wait=False, cancel_futures=True)
