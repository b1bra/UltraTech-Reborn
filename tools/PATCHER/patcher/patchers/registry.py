from __future__ import annotations

from dataclasses import dataclass, field
from patcher.patchers.base import Patch

@dataclass
class PatchRegistry:
    patches: list[Patch] = field(default_factory=list)
    def register(self, patch: Patch) -> None:
        self.patches.append(patch)
