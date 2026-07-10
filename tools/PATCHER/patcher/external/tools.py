from __future__ import annotations

import shutil
from dataclasses import dataclass
from pathlib import Path

@dataclass(slots=True)
class ExternalTool:
    name: str
    executable: Path | None
    icon: str = "◇"
    supports_automation: bool = True

class ToolLocator:
    supported = {"Recaf": ("recaf", "recaf.exe"), "Java": ("java", "java.exe"), "FernFlower": ("fernflower",)}

    def discover(self) -> list[ExternalTool]:
        tools: list[ExternalTool] = []
        for name, candidates in self.supported.items():
            found = next((shutil.which(c) for c in candidates if shutil.which(c)), None)
            tools.append(ExternalTool(name=name, executable=Path(found) if found else None))
        return tools
