"""Background task scheduler ensuring heavy operations never block the Qt UI thread."""
from __future__ import annotations
from concurrent.futures import ThreadPoolExecutor, Future
from dataclasses import dataclass
from typing import Callable, Any
from uuid import uuid4
@dataclass(frozen=True)
class ScheduledTask: id:str; name:str; future:Future
class TaskScheduler:
    def __init__(self,max_workers:int=4)->None: self.pool=ThreadPoolExecutor(max_workers=max_workers,thread_name_prefix='patcher'); self.tasks:dict[str,ScheduledTask]={}
    def submit(self,name:str,fn:Callable[...,Any],*args:Any,**kwargs:Any)->ScheduledTask:
        tid=str(uuid4()); fut=self.pool.submit(fn,*args,**kwargs); task=ScheduledTask(tid,name,fut); self.tasks[tid]=task; return task
    def result(self,task_id:str,timeout:float|None=None)->Any: return self.tasks[task_id].future.result(timeout)
    def shutdown(self)->None: self.pool.shutdown(wait=False,cancel_futures=True)
