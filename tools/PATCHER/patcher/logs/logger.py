from __future__ import annotations

import logging
from pathlib import Path


def configure_logging(level: int = logging.INFO, log_dir: Path | None = None) -> Path:
    """Configure application logging and return the active log file path.

    The UI imports this during startup, so logging setup must be lightweight and
    safe to call repeatedly. Existing handlers are left intact to avoid duplicate
    console/file output when tests or launchers configure logging first.
    """
    target_dir = log_dir or (Path.home() / ".ultratech-patcher" / "logs")
    target_dir.mkdir(parents=True, exist_ok=True)
    log_file = target_dir / "patcher.log"

    root = logging.getLogger()
    root.setLevel(level)

    formatter = logging.Formatter("%(asctime)s | %(levelname)s | %(name)s | %(message)s")

    if not any(isinstance(handler, logging.StreamHandler) for handler in root.handlers):
        stream_handler = logging.StreamHandler()
        stream_handler.setFormatter(formatter)
        root.addHandler(stream_handler)

    resolved_log_file = log_file.resolve()
    if not any(
        isinstance(handler, logging.FileHandler)
        and Path(handler.baseFilename).resolve() == resolved_log_file
        for handler in root.handlers
    ):
        file_handler = logging.FileHandler(log_file, encoding="utf-8")
        file_handler.setFormatter(formatter)
        root.addHandler(file_handler)

    logging.getLogger(__name__).debug("PATCHER logging configured at %s", log_file)
    return log_file
