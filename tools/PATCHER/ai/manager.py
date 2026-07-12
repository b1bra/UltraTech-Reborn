"""Async AI manager with real OpenAI/DeepSeek streaming providers.

Requests use AsyncOpenAI when an API key is available from api.txt. The manager
keeps rate limiting, retries and cancellation, while still returning an explicit
configuration error if no key/client exists instead of pretending to generate text.
"""
from __future__ import annotations
from dataclasses import dataclass, field
from pathlib import Path
from uuid import uuid4
from collections.abc import Callable
import asyncio, time, re
try:
    from openai import AsyncOpenAI
except ModuleNotFoundError:
    AsyncOpenAI = None
from tools.PATCHER.core.models.entities import AIKey, AIResponse, Provider

@dataclass
class AIRequest:
    id: str
    provider: Provider
    prompt: str
    context: dict
    cancelled: bool = False
    chunks: list[str] = field(default_factory=list)

class AIManager:
    def __init__(self, api_file: Path | None = None) -> None:
        self.requests: dict[str, AIRequest] = {}
        self.responses: dict[str, AIResponse] = {}
        self._times: list[float] = []
        self.api_file = api_file
        self.keys: list[AIKey] = self.parse_api_file(api_file) if api_file else []

    def configure_api_file(self, path: str | Path) -> None:
        self.api_file = Path(path)
        self.keys = self.parse_api_file(self.api_file)

    def parse_api_file(self, path: Path | None) -> list[AIKey]:
        if path is None or not Path(path).exists():
            return []
        keys: list[AIKey] = []
        for line in Path(path).read_text(encoding="utf-8").splitlines():
            source = line.strip()
            if not source:
                continue
            match = re.match(r"([^()]+)(?:\(([^)]+)\))?", source)
            raw_key = (match.group(1) if match else source).replace(" ", "")
            name = match.group(2).strip() if match and match.group(2) else raw_key[:6] + "…"
            provider = Provider.OPENAI if raw_key.startswith(("sk-", "org-")) else Provider.DEEPSEEK
            keys.append(AIKey(raw_key, name, provider))
        return keys

    def _key_for(self, provider: Provider) -> AIKey | None:
        for key in self.keys:
            if key.provider == provider:
                return key
        return None

    async def _respect_rate_limit(self) -> None:
        while len([moment for moment in self._times if time.time() - moment < 60]) >= 5:
            await asyncio.sleep(0.25)
        self._times.append(time.time())

    def enqueue_request(self, provider: Provider, prompt: str, context: dict) -> str:
        request_id = str(uuid4())
        self.requests[request_id] = AIRequest(request_id, provider, prompt, context)
        asyncio.run(self._process(request_id))
        return request_id

    async def enqueue_request_async(self, provider: Provider, prompt: str, context: dict, on_chunk: Callable[[str], None] | None = None) -> AIResponse:
        request_id = str(uuid4())
        self.requests[request_id] = AIRequest(request_id, provider, prompt, context)
        await self._process(request_id, on_chunk=on_chunk)
        return self.responses[request_id]

    async def stream_response(self, provider: Provider, prompt: str, context: dict, on_chunk: Callable[[str], None]) -> AIResponse:
        return await self.enqueue_request_async(provider, prompt, context, on_chunk)

    async def _process(self, request_id: str, on_chunk: Callable[[str], None] | None = None) -> None:
        request = self.requests[request_id]
        await self._respect_rate_limit()
        for attempt in range(3):
            if request.cancelled:
                self.responses[request_id] = AIResponse(request_id, request.provider, "Запрос отменён", 0.0, 0.0, True)
                return
            try:
                text = await self._call_provider_stream(request, on_chunk)
                self.responses[request_id] = AIResponse(request_id, request.provider, text, 0.78, 0.22, False)
                return
            except Exception as exc:
                if attempt == 2:
                    self.responses[request_id] = AIResponse(request_id, request.provider, f"AI API error: {exc}", 0.0, 1.0, request.cancelled)
                    return
                await asyncio.sleep(2 ** attempt)

    async def _call_provider_stream(self, request: AIRequest, on_chunk: Callable[[str], None] | None) -> str:
        if AsyncOpenAI is None:
            raise RuntimeError("Библиотека openai не установлена")
        key = self._key_for(request.provider)
        if key is None:
            raise RuntimeError(f"В api.txt нет ключа для {request.provider.value}")
        base_url = None if request.provider == Provider.OPENAI else "https://api.deepseek.com/v1"
        model = "gpt-4o-mini" if request.provider == Provider.OPENAI else "deepseek-chat"
        client = AsyncOpenAI(api_key=key.key, base_url=base_url)
        messages = [
            {"role": "system", "content": "You are PATCHER AI. Analyze Minecraft mod compatibility precisely and return actionable fixes."},
            {"role": "user", "content": f"Context: {request.context}\n\nPrompt: {request.prompt}"},
        ]
        stream = await client.chat.completions.create(model=model, messages=messages, stream=True)
        chunks: list[str] = []
        async for event in stream:
            if request.cancelled:
                break
            delta = event.choices[0].delta.content if event.choices and event.choices[0].delta else ""
            if delta:
                chunks.append(delta)
                request.chunks.append(delta)
                if on_chunk:
                    on_chunk(delta)
        return "".join(chunks)

    def get_response(self, request_id: str, timeout: float = 30) -> AIResponse:
        end = time.time() + timeout
        while time.time() < end:
            if request_id in self.responses:
                return self.responses[request_id]
            time.sleep(0.05)
        raise TimeoutError(request_id)

    def cancel_request(self, request_id: str) -> None:
        if request_id in self.requests:
            self.requests[request_id].cancelled = True
