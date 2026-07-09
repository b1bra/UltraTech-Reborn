"""Compatibility logger for legacy scanner modules.

The old ModScanner modules import a top-level module named ``logger``. When the
new GUI starts from ``tools/SCAN``, this file is imported first and forwards the
legacy calls into the session logger configured by the runner.
"""

from __future__ import annotations

import json
import platform
import sys
import time
import traceback
from datetime import datetime
from pathlib import Path
from typing import Any


class ScannerSessionLogger:
    """Markdown logger with the API expected by the existing scanners."""

    VERSION = "2.0"

    def __init__(self, logs_dir: str | Path | None = None) -> None:
        self.start_time = time.time()
        self.warning_count = 0
        self.error_count = 0
        self.stage_timers: dict[str, float] = {}
        self.parameters: dict[str, Any] = {}
        self.events: list[dict[str, Any]] = []
        self.logs_dir: Path | None = None
        self.scanner_log: Path | None = None
        self.error_log: Path | None = None
        self.session_file: Path | None = None
        if logs_dir is not None:
            self.configure(logs_dir)

    def configure(self, logs_dir: str | Path, parameters: dict[str, Any] | None = None) -> None:
        self.start_time = time.time()
        self.warning_count = 0
        self.error_count = 0
        self.stage_timers = {}
        self.parameters = dict(parameters or {})
        self.events = []
        self.logs_dir = Path(logs_dir)
        self.logs_dir.mkdir(parents=True, exist_ok=True)
        self.scanner_log = self.logs_dir / "scanner.md"
        self.error_log = self.logs_dir / "errors.md"
        self.session_file = self.logs_dir / "session.json"
        self.scanner_log.write_text("", encoding="utf-8")
        self.error_log.write_text("# Errors\n\n", encoding="utf-8")
        self.write_header()

    def _time(self) -> str:
        return datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    def _append(self, path: Path, text: str) -> None:
        with path.open("a", encoding="utf-8") as handle:
            handle.write(text)
            if not text.endswith("\n"):
                handle.write("\n")

    def write_header(self) -> None:
        self._append(self.scanner_log, "# Scanner Session\n")
        self._append(self.scanner_log, "## Environment\n")
        self._append(
            self.scanner_log,
            "```text\n"
            f"Session started: {self._time()}\n"
            f"Logger version: {self.VERSION}\n"
            f"Python: {platform.python_version()}\n"
            f"Executable: {sys.executable}\n"
            f"Platform: {platform.platform()}\n"
            f"Working directory: {Path.cwd()}\n"
            "```\n",
        )
        if self.parameters:
            self.section("Selected parameters")
            self.log_dict("Parameters", self.parameters)

    def log(self, level: str, message: str) -> None:
        if level == "WARNING":
            self.warning_count += 1
        if level in ("ERROR", "CRITICAL"):
            self.error_count += 1
        line = f"[{self._time()}] [{level:<8}] {message}"
        self.events.append({"time": self._time(), "level": level, "message": message})
        print(line)
        if self.scanner_log is not None:
            self._append(self.scanner_log, line)

    def info(self, message: str) -> None:
        self.log("INFO", message)

    def debug(self, message: str) -> None:
        self.log("DEBUG", message)

    def success(self, message: str) -> None:
        self.log("SUCCESS", message)

    def warning(self, message: str) -> None:
        self.log("WARNING", message)

    def error(self, message: str) -> None:
        self.log("ERROR", message)

    def critical(self, message: str) -> None:
        self.log("CRITICAL", message)

    def exception(self, error: BaseException) -> None:
        self.error(str(error))
        self._write_error("Exception", str(error), traceback.format_exc())

    def _write_error(self, title: str, message: str, trace: str) -> None:
        if self.error_log is None:
            return
        self._append(self.error_log, f"## {title}\n")
        self._append(
            self.error_log,
            "```text\n"
            f"Time: {self._time()}\n"
            f"Error: {message}\n\n"
            f"{trace}"
            "```\n",
        )

    def start_timer(self, name: str) -> None:
        self.stage_timers[name] = time.time()
        self.debug(f"Timer started: {name}")

    def stop_timer(self, name: str) -> float:
        started = self.stage_timers.pop(name, None)
        if started is None:
            self.warning(f"Timer not found: {name}")
            return 0.0
        elapsed = time.time() - started
        self.debug(f"Timer finished: {name} ({elapsed:.3f}s)")
        return elapsed

    def section(self, name: str) -> None:
        self.info("")
        self.info(f"## {name}")

    def banner(self, title: str) -> None:
        self.info("")
        self.info(f"# {title}")

    def log_file(self, path: str | Path, description: str = "File") -> None:
        path = Path(path)
        if path.exists():
            self.debug(f"{description}: {path} ({path.stat().st_size} bytes)")
        else:
            self.warning(f"{description} missing: {path}")

    def log_list(self, title: str, values: list[Any]) -> None:
        self.debug(f"{title}: {len(values)} entries")
        for value in values:
            self.debug(f"  - {value}")

    def log_dict(self, title: str, data: dict[str, Any]) -> None:
        self.debug(f"{title}: {len(data)} entries")
        for key, value in data.items():
            self.debug(f"  {key}: {value}")

    def log_system_info(self) -> None:
        self.section("System information")
        self.info(f"Python: {platform.python_version()}")
        self.info(f"OS: {platform.platform()}")
        self.info(f"Executable: {sys.executable}")

    def log_mod_start(self, mod_name: str) -> None:
        self.section(f"Scanning mod: {mod_name}")
        self.start_timer(f"mod_{mod_name}")

    def log_mod_finish(self, mod_name: str) -> None:
        elapsed = self.stop_timer(f"mod_{mod_name}")
        self.success(f"Finished mod: {mod_name} ({elapsed:.3f}s)")

    def log_dependency(self, mod: str, dependency: str) -> None:
        self.debug(f"Dependency detected: {mod} -> {dependency}")

    def log_class(self, class_name: str) -> None:
        self.debug(f"Class detected: {class_name}")

    def log_resource(self, resource_type: str, count: int) -> None:
        self.debug(f"Resources: {resource_type} = {count}")

    def log_recipe(self, recipe_type: str, count: int) -> None:
        self.debug(f"Recipes: {recipe_type} = {count}")

    def log_exception_context(self, module: str, stage: str, error: BaseException) -> None:
        self.error(f"{module} failed during {stage}: {error}")
        self._write_error(f"{module}: {stage}", str(error), traceback.format_exc())

    def add_warning(self, message: str) -> None:
        self.warning(message)

    def add_error(self, message: str) -> None:
        self.error(message)

    def add_critical(self, message: str) -> None:
        self.critical(message)

    def check_file(self, path: str | Path) -> bool:
        exists = Path(path).is_file()
        self.debug(f"File {'exists' if exists else 'missing'}: {path}")
        return exists

    def check_folder(self, path: str | Path) -> bool:
        exists = Path(path).is_dir()
        self.debug(f"Folder {'exists' if exists else 'missing'}: {path}")
        return exists

    def flush(self) -> None:
        return None

    def export_json(self, path: str | Path, data: Any) -> None:
        Path(path).write_text(json.dumps(data, indent=4, ensure_ascii=False), encoding="utf-8")
        self.debug(f"JSON exported: {path}")

    def read_json(self, path: str | Path) -> Any:
        return json.loads(Path(path).read_text(encoding="utf-8"))

    def separator(self) -> None:
        self.info("")

    def get_statistics(self) -> dict[str, Any]:
        return {
            "warnings": self.warning_count,
            "errors": self.error_count,
            "runtime": round(time.time() - self.start_time, 3),
        }

    def module(self, name: str, level: str, message: str) -> None:
        self.log(level, f"[{name}] {message}")

    def module_info(self, name: str, message: str) -> None:
        self.module(name, "INFO", message)

    def module_debug(self, name: str, message: str) -> None:
        self.module(name, "DEBUG", message)

    def module_success(self, name: str, message: str) -> None:
        self.module(name, "SUCCESS", message)

    def module_warning(self, name: str, message: str) -> None:
        self.module(name, "WARNING", message)

    def module_error(self, name: str, message: str) -> None:
        self.module(name, "ERROR", message)

    def create_session_data(self) -> dict[str, Any]:
        return {
            "logger_version": self.VERSION,
            "session_start": datetime.fromtimestamp(self.start_time).isoformat(),
            "session_end": datetime.now().isoformat(),
            "duration_seconds": round(time.time() - self.start_time, 3),
            "python_version": platform.python_version(),
            "python_executable": sys.executable,
            "platform": platform.platform(),
            "parameters": self.parameters,
            "warnings": self.warning_count,
            "errors": self.error_count,
            "events": self.events[-500:],
        }

    def save_session(self) -> None:
        if self.session_file is None:
            return
        self.session_file.write_text(json.dumps(self.create_session_data(), indent=4, ensure_ascii=False), encoding="utf-8")
        self.debug("Session data saved")

    def finish(self) -> None:
        self.section("Scanner session finished")
        self.info(f"Runtime: {time.time() - self.start_time:.3f} seconds")
        self.info(f"Warnings: {self.warning_count}")
        self.info(f"Errors: {self.error_count}")
        self.save_session()

    def shutdown(self) -> None:
        self.finish()


logger = ScannerSessionLogger()


def configure_logger(logs_dir: str | Path, parameters: dict[str, Any] | None = None) -> ScannerSessionLogger:
    logger.configure(logs_dir, parameters)
    return logger


def get_logger() -> ScannerSessionLogger:
    return logger


def log_info(message: str) -> None:
    logger.info(message)


def log_debug(message: str) -> None:
    logger.debug(message)


def log_success(message: str) -> None:
    logger.success(message)


def log_warning(message: str) -> None:
    logger.warning(message)


def log_error(message: str) -> None:
    logger.error(message)


def log_critical(message: str) -> None:
    logger.critical(message)
