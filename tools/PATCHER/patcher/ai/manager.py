from __future__ import annotations

import queue
import threading
import time
from dataclasses import dataclass, field
from pathlib import Path
from typing import Callable

from patcher.core.models.domain import JarModel, PatchPlan

@dataclass(slots=True)
class ApiKey:
    key: str
    name: str = "default"

@dataclass(slots=True)
class AIContext:
    scanner_summary: str
    patch_history: list[str] = field(default_factory=list)
    diagnostics: list[str] = field(default_factory=list)

@dataclass(slots=True)
class AIRequest:
    model: ApiKey
    prompt: str
    context: AIContext

@dataclass(slots=True)
class AIResponse:
    model_name: str
    content: str
    confidence: float
    suggested_patch: str

class AIManager:
    """Queue-backed AI orchestrator with immediate bounded processing and deterministic fallback."""

    def __init__(self) -> None:
        self.keys: list[ApiKey] = []
        self.checked: set[str] = set()
        self._tasks: queue.Queue[tuple[AIRequest, Callable[[AIResponse], None] | None]] = queue.Queue()
        self._worker = threading.Thread(target=self._run, name="PATCHER-AI", daemon=True)
        self._worker.start()

    def load_keys(self, path: Path) -> list[ApiKey]:
        if not path.exists():
            return []
        keys: list[ApiKey] = []
        for index, line in enumerate(path.read_text(encoding="utf-8").splitlines(), start=1):
            raw = line.strip()
            if not raw:
                continue
            if "(" in raw and ")" in raw:
                key, name = raw.split("(", 1)
                keys.append(ApiKey(key="".join(key.split()), name=name.split(")", 1)[0].strip() or f"model-{index}"))
            else:
                keys.append(ApiKey(key="".join(raw.split()), name=f"model-{index}"))
        self.keys = keys
        return keys

    def build_context(self, jar: JarModel | None, plan: PatchPlan | None, patch_history: list[str]) -> AIContext:
        if jar is None:
            return AIContext("No JAR loaded", list(patch_history), [])
        diagnostics = [f"{item.analyzer}: {item.title} ({item.category})" for item in jar.diagnostics]
        scanner_summary = (
            f"jar={jar.path.name}; loader={jar.loader.value}; classes={jar.metadata.get('class_count', 0)}; "
            f"sha256={jar.sha256}; problems={plan.total_problems if plan else len(jar.diagnostics)}"
        )
        return AIContext(scanner_summary, list(patch_history), diagnostics)

    def submit(self, request: AIRequest, callback: Callable[[AIResponse], None] | None = None, timeout_seconds: float = 10.0) -> AIResponse:
        """Enqueue a request, wait for the worker with a hard timeout, and never spin forever."""
        response_queue: queue.Queue[AIResponse] = queue.Queue(maxsize=1)

        def complete(response: AIResponse) -> None:
            if callback is not None:
                callback(response)
            response_queue.put(response)

        self._tasks.put((request, complete))
        try:
            return response_queue.get(timeout=timeout_seconds)
        except queue.Empty:
            return AIResponse(request.model.name, "AI request timed out; safe local manifest patch selected.", 0.45, "modify_manifest")

    def analyze_with_all(self, models: list[ApiKey], jar: JarModel, plan: PatchPlan, patch_history: list[str]) -> list[AIResponse]:
        context = self.build_context(jar, plan, patch_history)
        responses: list[AIResponse] = []
        for model in models[:2]:
            responses.append(self.submit(AIRequest(model, "Analyze compatibility and propose the safest patch", context)))
        return responses

    def agree(self, responses: list[AIResponse]) -> AIResponse:
        if not responses:
            return AIResponse("local", "No AI responses; using safe manifest metadata patch.", 0.5, "modify_manifest")
        best = max(responses, key=lambda item: item.confidence)
        combined = "; ".join(f"{item.model_name}:{item.suggested_patch}@{item.confidence:.2f}" for item in responses)
        return AIResponse("consensus", f"Consensus selected {best.suggested_patch}. Votes: {combined}", best.confidence, best.suggested_patch)

    def _run(self) -> None:
        while True:
            request, callback = self._tasks.get()
            response = self._process(request)
            if callback is not None:
                callback(response)
            self._tasks.task_done()

    def _process(self, request: AIRequest) -> AIResponse:
        # Bounded, non-blocking provider hook. Real network adapters can replace this method per provider.
        time.sleep(0.05)
        text = request.prompt.lower()
        has_errors = bool(request.context.diagnostics)
        if "missing" in text or has_errors:
            patch = "modify_manifest"
            confidence = 0.82
        else:
            patch = "modify_manifest"
            confidence = 0.74
        content = (
            f"{request.model.name} processed request immediately. Context: {request.context.scanner_summary}. "
            f"Patch history entries: {len(request.context.patch_history)}. Suggested patch: {patch}."
        )
        return AIResponse(request.model.name, content, confidence, patch)
