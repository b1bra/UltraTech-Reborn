"""Console entrypoint for PATCHER. Run with `python tools/PATCHER/app.py`."""
from pathlib import Path
import sys
_REPO_ROOT = Path(__file__).resolve().parents[2]
if str(_REPO_ROOT) not in sys.path:
    sys.path.insert(0, str(_REPO_ROOT))
from tools.PATCHER.launcher_app import launch
if __name__ == "__main__":
    raise SystemExit(launch())
