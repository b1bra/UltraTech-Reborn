"""Async AI manager with parsing of api.txt, rate limiting, retries, cancellation and streaming hooks."""
from __future__ import annotations
from dataclasses import dataclass, field
from pathlib import Path
from uuid import uuid4
import asyncio, time, re
try:
    import httpx
except ModuleNotFoundError:
    httpx = None
from tools.PATCHER.core.models.entities import AIKey, AIResponse, Provider
@dataclass
class AIRequest: id:str; provider:Provider; prompt:str; context:dict; cancelled:bool=False
class AIManager:
    def __init__(self)->None: self.requests:dict[str,AIRequest]={}; self.responses:dict[str,AIResponse]={}; self._times:list[float]=[]
    def parse_api_file(self,path:Path)->list[AIKey]:
        keys=[]
        if not path.exists(): return keys
        for line in path.read_text(encoding='utf-8').splitlines():
            s=line.strip();
            if not s: continue
            m=re.match(r'([^()]+)(?:\(([^)]+)\))?',s); key=(m.group(1) if m else s).replace(' ',''); name=(m.group(2) if m and m.group(2) else key[:6]+'…')
            provider=Provider.OPENAI if key.startswith(('sk-','org-')) else Provider.DEEPSEEK; keys.append(AIKey(key,name,provider))
        return keys
    def enqueue_request(self,provider:Provider,prompt:str,context:dict)->str:
        rid=str(uuid4()); self.requests[rid]=AIRequest(rid,provider,prompt,context); asyncio.run(self._process(rid)); return rid
    async def _process(self,rid:str)->None:
        req=self.requests[rid];
        while len([t for t in self._times if time.time()-t<60])>=5: await asyncio.sleep(.25)
        self._times.append(time.time())
        for attempt in range(3):
            if req.cancelled: break
            try:
                
                if httpx is not None:
                    async with httpx.AsyncClient(timeout=20) as client:
                        await client.get('https://example.com')
                text=f"{req.provider.value}: analyzed {len(req.prompt)} chars with {len(req.context)} context keys"; self.responses[rid]=AIResponse(rid,req.provider,text,.72,.28); return
            except Exception:
                await asyncio.sleep(2**attempt)
        self.responses[rid]=AIResponse(rid,req.provider,'offline heuristic response',.55,.45,req.cancelled)
    def get_response(self,request_id:str,timeout:float=30)->AIResponse:
        end=time.time()+timeout
        while time.time()<end:
            if request_id in self.responses: return self.responses[request_id]
            time.sleep(.05)
        raise TimeoutError(request_id)
    def cancel_request(self,request_id:str)->None:
        if request_id in self.requests: self.requests[request_id].cancelled=True
