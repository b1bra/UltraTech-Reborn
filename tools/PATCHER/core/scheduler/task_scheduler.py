"""Background task scheduler used by UI and engines.

The scheduler wraps ThreadPoolExecutor, stores progress/messages, and supports
callbacks without coupling workers to PySide6. Qt code can submit CPU or I/O heavy
operations and poll the task model from timers, keeping the interface responsive.
"""
from __future__ import annotations
from concurrent.futures import ThreadPoolExecutor, Future
from dataclasses import dataclass, field
from typing import Callable, Any
from uuid import uuid4
from datetime import datetime

@dataclass
class ScheduledTask:
    id: str
    name: str
    future: Future
    created_at: str
    progress: int = 0
    messages: list[str] = field(default_factory=list)

class TaskScheduler:
    def __init__(self, max_workers: int = 4) -> None:
        self.pool = ThreadPoolExecutor(max_workers=max_workers, thread_name_prefix="patcher")
        self.tasks: dict[str, ScheduledTask] = {}

    def submit(self, name: str, fn: Callable, *args: Any, **kwargs: Any) -> ScheduledTask:
        task_id = str(uuid4())
        future = self.pool.submit(fn, *args, **kwargs)
        task = ScheduledTask(task_id, name, future, datetime.now().isoformat(timespec="seconds"))
        self.tasks[task_id] = task
        return task

    def update_progress(self, task_id: str, value: int, message: str = "") -> None:
        task = self.tasks[task_id]
        task.progress = max(0, min(100, value))
        if message:
            task.messages.append(message)

    def result(self, task_id: str, timeout: float | None = None) -> Any:
        return self.tasks[task_id].future.result(timeout)

    def snapshot(self) -> list[dict[str, Any]]:
        rows: list[dict[str, Any]] = []
        for task in self.tasks.values():
            rows.append({"id": task.id, "name": task.name, "done": task.future.done(), "progress": task.progress, "messages": list(task.messages)})
        return rows

    def shutdown(self) -> None:
        self.pool.shutdown(wait=False, cancel_futures=True)
