"""Bytecode editing abstraction for ZIP/JAR entries.

The editor performs conservative binary-safe transformations for constant-pool UTF8
references and records every change. It does not attempt unsafe instruction
rewriting directly; higher-level patchers call these methods and then verification
validates the resulting archive.
"""
from __future__ import annotations
from dataclasses import dataclass, field
from pathlib import Path
import zipfile, shutil, tempfile
from tools.PATCHER.core.models.entities import FileChange

@dataclass
class BytecodeEditResult:
    output: Path
    changes: list[FileChange] = field(default_factory=list)

class BytecodeEditor:
    def replace_binary_reference(self, jar: Path, old: bytes, new: bytes, output: Path | None = None) -> BytecodeEditResult:
        target = output or jar.with_name(jar.stem + "-bytecode.jar")
        changes: list[FileChange] = []
        with tempfile.TemporaryDirectory() as tmp:
            tmp_path = Path(tmp)
            with zipfile.ZipFile(jar) as src:
                src.extractall(tmp_path)
            for file in tmp_path.rglob("*.class"):
                data = file.read_bytes()
                if old in data:
                    replaced = data.replace(old, new)
                    file.write_bytes(replaced)
                    changes.append(FileChange(str(file.relative_to(tmp_path)), "modified", len(data), len(replaced)))
            if target.exists():
                target.unlink()
            with zipfile.ZipFile(target, "w", zipfile.ZIP_DEFLATED) as dst:
                for file in tmp_path.rglob("*"):
                    if file.is_file():
                        dst.write(file, file.relative_to(tmp_path).as_posix())
        return BytecodeEditResult(target, changes)
