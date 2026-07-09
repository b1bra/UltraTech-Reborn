"""Borderless GUI for the unified scanner."""

from __future__ import annotations

import queue
import subprocess
import threading
import tkinter as tk
from tkinter import filedialog

from runner import ScanRequest, format_traceback, run_scan


WIDTH = 1280
HEIGHT = 720
GREEN = "#74f0a0"
WHITE = "#d9e0dc"
BG = "#07120f"


class ScannerApp:
    """Custom drawn scanner window."""

    def __init__(self) -> None:
        self._hide_windows_console()
        self.root = tk.Tk()
        self.root.overrideredirect(True)
        self.root.geometry(f"{WIDTH}x{HEIGHT}+120+80")
        self.root.configure(bg=BG)
        self.root.resizable(False, False)
        self.canvas = tk.Canvas(self.root, width=WIDTH, height=HEIGHT, highlightthickness=0, bg=BG)
        self.canvas.pack(fill="both", expand=True)
        self.queue: queue.Queue[tuple[str, object]] = queue.Queue()
        self.progress = 0
        self.progress_phase = 0
        self.console_open = False
        self.external_console_enabled = False
        self.console_items: list[int] = []
        self.log_lines: list[str] = []
        self.entries: dict[str, tk.Entry] = {}
        self.entry_windows: list[int] = []
        self.running = False
        self.drag_start: tuple[int, int] | None = None
        self._build_static()
        self._build_form()
        self._bind_events()
        self._animate_background()
        self._poll_queue()

    def run(self) -> None:
        self.root.mainloop()

    def _build_static(self) -> None:
        self.canvas.create_rectangle(0, 0, WIDTH, HEIGHT, fill=BG, outline="")
        self._draw_background()
        self.logo = self._rounded_rect(40, 28, 240, 82, 28, fill="#4f5655", outline="")
        self.canvas.create_text(140, 55, text="SCANNER", fill=GREEN, font=("Segoe UI Light", 28), tags=("chrome",))
        self.close_button = self._rounded_rect(WIDTH - 54, 16, WIDTH - 12, 58, 18, fill="#979b9a", outline="")
        self.canvas.create_text(WIDTH - 33, 37, text="X", fill="#ff2020", font=("Segoe UI", 28, "bold"), tags=("close", "chrome"))
        self.console_button = self._rounded_rect(WIDTH - 72, HEIGHT - 72, WIDTH - 26, HEIGHT - 26, 14, fill="#8e9492", outline="")
        self.canvas.create_text(WIDTH - 49, HEIGHT - 49, text=">_", fill="#202827", font=("Consolas", 18, "bold"), tags=("console_btn", "chrome"))
        self.external_console_checkbox = self._rounded_rect(WIDTH - 126, HEIGHT - 65, WIDTH - 96, HEIGHT - 35, 13, fill="#a9afad", outline="", tags=("external_console", "chrome"))
        self.canvas.tag_bind("close", "<Button-1>", lambda _event: self.root.destroy())
        self.canvas.tag_bind("console_btn", "<Button-1>", lambda _event: self._toggle_console())
        self.canvas.tag_bind("external_console", "<Button-1>", lambda _event: self._toggle_external_console())

    def _draw_background(self) -> None:
        self.canvas.create_polygon(0, 0, 950, 0, 820, HEIGHT, 0, HEIGHT, fill="#091814", outline="")
        for x in range(0, WIDTH, 9):
            for y in range(0, HEIGHT, 9):
                if x < 190 or x > 700 or y > 530:
                    color = "#d7e2dc" if (x + y) % 27 == 0 else "#71817b"
                    self.canvas.create_oval(x, y, x + 2, y + 2, fill=color, outline="", stipple="gray50")
        self.canvas.create_text(225, 360, text="SC", fill="#003d2b", font=("Segoe UI Black", 300), anchor="center")
        self.canvas.create_text(890, 345, text="AN", fill="#00412e", font=("Segoe UI Black", 300), anchor="center")
        for points, color in (
            ((70, 90, 240, 270, 70, 450), "#03492f"),
            ((360, 75, 560, 240, 420, 500, 250, 250), "#07573a"),
            ((870, 80, 1180, 150, 1100, 620, 760, 610), "#075c3d"),
        ):
            self.canvas.create_polygon(*points, fill=color, outline="")

    def _build_form(self) -> None:
        specs = [
            ("launcher", "Launcher path", 172),
            ("modpack", "Modpack path", 286),
            ("output", "Output path", 400),
        ]
        for key, placeholder, y in specs:
            self._rounded_rect(228, y, 1088, y + 76, 38, fill="#35413d", outline="#ccd5d0", width=2, tags=("form",))
            entry = tk.Entry(
                self.root,
                borderwidth=0,
                highlightthickness=0,
                bg="#202927",
                fg=WHITE,
                insertbackground=WHITE,
                font=("Segoe UI", 21, "italic"),
            )
            entry.insert(0, placeholder)
            entry.placeholder = placeholder  # type: ignore[attr-defined]
            entry.bind("<FocusIn>", self._entry_focus_in)
            entry.bind("<FocusOut>", self._entry_focus_out)
            entry.bind("<Double-Button-1>", lambda _event, item=key: self._choose_path(item))
            window = self.canvas.create_window(275, y + 38, window=entry, anchor="w", width=760, height=42, tags=("form",))
            self.entries[key] = entry
            self.entry_windows.append(window)
        self.check_value = tk.BooleanVar(value=False)
        self.checkbox = self._rounded_rect(585, 525, 618, 558, 15, fill="#a9afad", outline="", tags=("form", "checkbox"))
        self.canvas.create_text(640, 541, text="Generate mod dependency graph", fill="#a6aaa8", font=("Segoe UI", 22, "italic"), anchor="w", tags=("form", "checkbox"))
        self.canvas.tag_bind("checkbox", "<Button-1>", lambda _event: self._toggle_checkbox())
        self.start_button = self._rounded_rect(42, HEIGHT - 84, 132, HEIGHT - 28, 18, fill="#5b6360", outline="#b4bdb8", tags=("form", "start"))
        self.canvas.create_text(87, HEIGHT - 56, text="Enter", fill=GREEN, font=("Segoe UI", 17, "bold"), tags=("form", "start"))
        self.canvas.tag_bind("start", "<Button-1>", lambda _event: self._start_scan())

    def _bind_events(self) -> None:
        self.canvas.bind("<ButtonPress-1>", self._start_drag)
        self.canvas.bind("<B1-Motion>", self._drag)

    def _start_drag(self, event: tk.Event) -> None:
        self.drag_start = (event.x_root - self.root.winfo_x(), event.y_root - self.root.winfo_y())

    def _drag(self, event: tk.Event) -> None:
        if self.drag_start:
            dx, dy = self.drag_start
            self.root.geometry(f"+{event.x_root - dx}+{event.y_root - dy}")

    def _entry_focus_in(self, event: tk.Event) -> None:
        entry = event.widget
        if entry.get() == getattr(entry, "placeholder", ""):
            entry.delete(0, "end")
            entry.config(fg=WHITE, font=("Segoe UI", 21, "italic"))

    def _entry_focus_out(self, event: tk.Event) -> None:
        entry = event.widget
        if not entry.get().strip():
            entry.insert(0, getattr(entry, "placeholder", ""))
            entry.config(fg="#a6aaa8")

    def _choose_path(self, key: str) -> None:
        selected = filedialog.askdirectory()
        if selected:
            entry = self.entries[key]
            entry.delete(0, "end")
            entry.insert(0, selected)

    def _toggle_checkbox(self) -> None:
        self.check_value.set(not self.check_value.get())
        fill = GREEN if self.check_value.get() else "#a9afad"
        self.canvas.itemconfig(self.checkbox, fill=fill)

    def _toggle_external_console(self) -> None:
        self.external_console_enabled = not self.external_console_enabled
        fill = GREEN if self.external_console_enabled else "#a9afad"
        self.canvas.itemconfig(self.external_console_checkbox, fill=fill)

    def _start_scan(self) -> None:
        if self.running:
            return
        request = ScanRequest(
            launcher_path=self._entry_value("launcher"),
            modpack_path=self._entry_value("modpack"),
            output_path=self._entry_value("output"),
            generate_graph=self.check_value.get(),
        )
        if not request.output_path:
            self._append_console("Output path is required.")
            self._toggle_console(force=True)
            return
        if self.external_console_enabled:
            self._open_external_log_console(request.output_path)
        self.running = True
        for item in self.canvas.find_withtag("form"):
            self.canvas.itemconfig(item, state="hidden")
        for window in self.entry_windows:
            self.canvas.itemconfig(window, state="hidden")
        self._show_progress()
        thread = threading.Thread(target=self._run_worker, args=(request,), daemon=True)
        thread.start()

    def _entry_value(self, key: str) -> str:
        value = self.entries[key].get().strip().strip('"')
        return "" if value == self.entries[key].placeholder else value  # type: ignore[attr-defined]

    def _run_worker(self, request: ScanRequest) -> None:
        try:
            run_scan(request, lambda percent, message: self.queue.put(("progress", (percent, message))))
            self.queue.put(("done", None))
        except Exception as error:
            self.queue.put(("error", format_traceback(error)))

    def _show_progress(self) -> None:
        self.progress_items = []
        self.progress_items.append(self._rounded_rect(350, 330, 930, 380, 25, fill="#3d4542", outline="#d0d8d0", width=2, tags=("progress",)))
        self.progress_text = self.canvas.create_text(640, 420, text="0%", fill=WHITE, font=("Segoe UI", 28, "bold"), tags=("progress",))
        self.progress_message = self.canvas.create_text(640, 465, text="Preparing...", fill="#a9b1ad", font=("Segoe UI", 14), tags=("progress",))
        self._animate_progress()

    def _animate_progress(self) -> None:
        self.canvas.delete("progress_fill")
        x1, y1, x2, y2 = 360, 340, 920, 370
        width = (x2 - x1) * (self.progress / 100)
        end = x1 + width
        if width > 4:
            points = [x1, y1, max(x1, end - 16), y1]
            for offset in range(0, 34, 6):
                wave_y = y1 + offset
                wave_x = end + (6 if (offset // 6 + self.progress_phase) % 2 == 0 else -5)
                points.extend([wave_x, wave_y])
            points.extend([max(x1, end - 16), y2, x1, y2])
            self.canvas.create_polygon(points, fill="#73f0a0", outline="", tags=("progress", "progress_fill"))
            self.canvas.create_polygon(points, fill="#9dffc0", outline="", tags=("progress", "progress_fill"))
        self.progress_phase = (self.progress_phase + 1) % 8
        if self.running:
            self.root.after(90, self._animate_progress)

    def _poll_queue(self) -> None:
        try:
            while True:
                event, payload = self.queue.get_nowait()
                if event == "progress":
                    percent, message = payload  # type: ignore[misc]
                    self.progress = int(percent)
                    self.canvas.itemconfig(self.progress_text, text=f"{self.progress}%")
                    self.canvas.itemconfig(self.progress_message, text=str(message))
                    self._append_console(f"{self.progress}% {message}")
                elif event == "done":
                    self.root.destroy()
                elif event == "error":
                    self.running = False
                    self._append_console(str(payload))
                    self._toggle_console(force=True)
        except queue.Empty:
            pass
        self.root.after(100, self._poll_queue)

    def _toggle_console(self, force: bool | None = None) -> None:
        state = (not self.console_open) if force is None else force
        if state == self.console_open:
            return
        self.console_open = state
        if state:
            self.console_items.append(self._rounded_rect(160, 96, 1120, 650, 18, fill="#020806", outline="#86f0aa", width=2, tags=("console",)))
            text = "\n".join(self.log_lines[-28:])
            self.console_text = self.canvas.create_text(185, 122, text=text, fill="#d9e0dc", font=("Consolas", 12), anchor="nw", width=900, tags=("console",))
        else:
            self.canvas.delete("console")
            self.console_items.clear()

    def _append_console(self, text: str) -> None:
        self.log_lines.extend(str(text).splitlines())
        if self.console_open:
            self.canvas.itemconfig(self.console_text, text="\n".join(self.log_lines[-28:]))

    def _open_external_log_console(self, output_path: str) -> None:
        logs_dir = output_path.rstrip("\\/") + "\\logs"
        command = (
            "$logs = '" + logs_dir.replace("'", "''") + "'; "
            "Write-Host 'SCAN live logs:' $logs; "
            "while (!(Test-Path (Join-Path $logs 'scanner.md'))) { Start-Sleep -Milliseconds 250 }; "
            "Get-Content -Path (Join-Path $logs 'scanner.md'), (Join-Path $logs 'errors.md') -Wait"
        )
        try:
            subprocess.Popen(
                ["cmd.exe", "/k", "powershell", "-NoLogo", "-NoExit", "-Command", command],
                creationflags=getattr(subprocess, "CREATE_NEW_CONSOLE", 0),
            )
        except Exception as error:
            self._append_console(f"Cannot open external cmd: {error}")
            self._toggle_console(force=True)

    def _hide_windows_console(self) -> None:
        try:
            import ctypes

            window = ctypes.windll.kernel32.GetConsoleWindow()
            if window:
                ctypes.windll.user32.ShowWindow(window, 0)
        except Exception:
            pass

    def _animate_background(self) -> None:
        self.canvas.move("glow", 2, 0)
        self.root.after(120, self._animate_background)

    def _rounded_rect(self, x1: int, y1: int, x2: int, y2: int, radius: int, **kwargs) -> int:
        points = [
            x1 + radius,
            y1,
            x2 - radius,
            y1,
            x2,
            y1,
            x2,
            y1 + radius,
            x2,
            y2 - radius,
            x2,
            y2,
            x2 - radius,
            y2,
            x1 + radius,
            y2,
            x1,
            y2,
            x1,
            y2 - radius,
            x1,
            y1 + radius,
            x1,
            y1,
        ]
        return self.canvas.create_polygon(points, smooth=True, **kwargs)


def main() -> None:
    ScannerApp().run()


if __name__ == "__main__":
    main()
