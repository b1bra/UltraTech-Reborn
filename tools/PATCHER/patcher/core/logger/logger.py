from __future__ import annotations

import logging
from pathlib import Path
from threading import Lock

class LoggerManager:
    def __init__(self, log_dir: Path) -> None:
        self.log_dir = log_dir
        self.log_dir.mkdir(parents=True, exist_ok=True)
        self._lock = Lock()
        self._loggers: dict[str, logging.Logger] = {}

    def get(self, module: str) -> logging.Logger:
        with self._lock:
            if module in self._loggers:
                return self._loggers[module]
            logger = logging.getLogger(f"PATCHER.{module}")
            logger.setLevel(logging.INFO)
            logger.propagate = False
            handler = logging.FileHandler(self.log_dir / f"{module}.log", encoding="utf-8")
            handler.setFormatter(logging.Formatter("[%(asctime)s] [%(name)s] [%(levelname)s] %(message)s", "%H:%M:%S"))
            logger.handlers.clear()
            logger.addHandler(handler)
            self._loggers[module] = logger
            return logger
