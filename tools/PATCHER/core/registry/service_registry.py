"""Typed service registry used as the only integration point between plugins."""
from __future__ import annotations
from dataclasses import dataclass
from typing import Any, TypeVar, Generic
T=TypeVar('T')
@dataclass(frozen=True)
class ServiceDescriptor: name:str; version:str; service:Any
class ServiceRegistry:
    def __init__(self)->None: self._services:dict[str,ServiceDescriptor]={}
    def register(self,name:str,service:Any,version:str="1.0")->None: self._services[name]=ServiceDescriptor(name,version,service)
    def get(self,name:str,typ:type[T]|None=None)->T:
        svc=self._services[name].service
        if typ and not isinstance(svc,typ): raise TypeError(f"Service {name} is not {typ}")
        return svc
    def optional(self,name:str)->Any|None: return self._services.get(name).service if name in self._services else None
    def names(self)->list[str]: return sorted(self._services)
