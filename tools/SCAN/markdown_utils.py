"""Markdown helpers used by the unified scanner."""

from __future__ import annotations

from collections.abc import Iterable


def text_block(lines: Iterable[object]) -> str:
    """Return a fenced text block for technical lists."""

    body = "\n".join(str(line) for line in lines)
    return f"```text\n{body}\n```\n"


def write_text_block(handle, lines: Iterable[object]) -> None:
    """Write a fenced text block to an open Markdown file."""

    handle.write(text_block(lines))
    handle.write("\n")

