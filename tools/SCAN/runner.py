"""Unified analysis runner used by the GUI."""

from __future__ import annotations


import shutil
import tempfile
import traceback
from dataclasses import dataclass
from pathlib import Path
from typing import Callable

from launcher_analyzer import analyze_launcher
from logger import ScannerSessionLogger, configure_logger
from modpack_analyzer import analyze_modpack


ProgressCallback = Callable[[int, str], None]


@dataclass(frozen=True)
class ScanRequest:
    launcher_path: str = ""
    modpack_path: str = ""
    output_path: str = ""
    generate_graph: bool = False
    test_mode: bool = False


def run_scan(request: ScanRequest, progress: ProgressCallback | None = None) -> None:
    """Run analysis; Test Mode uses a disposable output tree and persists nothing."""

    progress = progress or (lambda _percent, _message: None)
    if request.test_mode:
        with tempfile.TemporaryDirectory(prefix="scan_test_output_") as temp_dir:
            _run_scan_with_output(request, Path(temp_dir), progress)
        return

    output = Path(request.output_path).expanduser()
    if not str(output).strip():
        return
    _run_scan_with_output(request, output, progress)


def _run_scan_with_output(request: ScanRequest, output: Path, progress: ProgressCallback) -> None:
    """Run the unchanged analyzers against the supplied output root."""

    output.mkdir(parents=True, exist_ok=True)
    modpack_output = output / "modpack"
    launcher_output = output / "launcher"
    logs_output = output / "logs"
    parameters = {
        "launcher_path": request.launcher_path,
        "modpack_path": request.modpack_path,
        "output_path": str(output),
        "generate_graph": request.generate_graph,
        "test_mode": request.test_mode,
    }
    logger = configure_logger(logs_output, parameters)
    try:
        _run_scan_inner(request, output, modpack_output, launcher_output, logs_output, logger, progress)
    except Exception as exc:
        logger.log_exception_context("SCAN", "run_scan", exc)
        raise
    finally:
        logger.finish()


def _run_scan_inner(
    request: ScanRequest,
    output: Path,
    modpack_output: Path,
    launcher_output: Path,
    logs_output: Path,
    logger: ScannerSessionLogger,
    progress: ProgressCallback,
) -> None:
    launcher_path = Path(request.launcher_path).expanduser() if request.launcher_path.strip() else None
    modpack_path = Path(request.modpack_path).expanduser() if request.modpack_path.strip() else None
    total_parts = int(launcher_path is not None) + int(modpack_path is not None)
    if total_parts == 0:
        logger.warning("Launcher path and Modpack path are empty; no analysis was started")
        progress(100, "No analysis requested")
        return
    logger.banner("SCAN unified analysis")
    logger.info(f"Output root: {output}")
    progress(2, "Preparing analysis")

    if modpack_path is not None:
        analyze_modpack(
            modpack_path,
            modpack_output,
            request.generate_graph,
            logger,
            lambda percent, message: progress(_scale(percent, 3, 74 if launcher_path else 94), message),
        )

    if launcher_path is not None:
        if request.test_mode:
            logger.info("Test mode: launcher documentation will be generated in a temporary directory and discarded")
            with tempfile.TemporaryDirectory(prefix="scan_launcher_test_") as temp_dir:
                analyze_launcher(
                    launcher_path,
                    Path(temp_dir),
                    logger,
                    lambda percent, message: progress(_scale(percent, 76 if modpack_path else 3, 94), message),
                )
        else:
            analyze_launcher(
                launcher_path,
                launcher_output,
                logger,
                lambda percent, message: progress(_scale(percent, 76 if modpack_path else 3, 94), message),
            )

    if request.test_mode:
        _discard_test_mode_documentation(modpack_output, launcher_output, logger)

    if request.test_mode:
        logger.info("Test mode: all generated output was written to a temporary directory and will be discarded")

    if request.test_mode:
        logger.info("Test mode: all generated output was written to a temporary directory and will be discarded")

    progress(98, "Writing session summary")
    logger.success("Unified analysis complete")
    progress(100, "Complete")


def _scale(percent: int, start: int, end: int) -> int:
    return start + int((max(0, min(100, percent)) / 100) * (end - start))


def format_traceback(error: BaseException) -> str:
    """Return full traceback text for GUI console display."""

    return "".join(traceback.format_exception(type(error), error, error.__traceback__))


def _discard_test_mode_documentation(modpack_output: Path, launcher_output: Path, logger: ScannerSessionLogger) -> None:
    """Remove only the documentation artifacts disabled by Test Mode."""

    launcher_removed = False
    if launcher_output.exists():
        shutil.rmtree(launcher_output)
        launcher_removed = True
    assembly_doc = modpack_output / "modpack_technical_overview.md"
    if assembly_doc.exists():
        assembly_doc.unlink()
        logger.info(f"Test mode: discarded assembly documentation {assembly_doc}")
    if launcher_removed:
        logger.info(f"Test mode: discarded launcher documentation {launcher_output}")
