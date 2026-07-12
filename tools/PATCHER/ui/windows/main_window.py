"""Standard-frame PATCHER windows: main UI, launcher selection, settings and AI chat."""
from __future__ import annotations
from pathlib import Path
import os, shutil
from PySide6.QtCore import Qt, QTimer, QFileInfo
from PySide6.QtWidgets import (
    QApplication, QDialog, QFileDialog, QLabel, QMainWindow, QPushButton, QScrollArea,
    QVBoxLayout, QHBoxLayout, QWidget, QTextEdit, QListWidget, QFrame, QFileIconProvider,
)
from PySide6.QtGui import QIcon
from tools.PATCHER.bootstrap import build_registry
from tools.PATCHER.core.models.entities import Provider
from tools.PATCHER.resources.design_tokens import DIMENSIONS, STRINGS, PALETTE
from tools.PATCHER.ui.widgets.common import Toast, JarDropArea, ModCard, AILogPanel, Card, transparent_icon, label, ChatInput

SCROLLBAR_STYLE = f"""
QScrollBar:vertical{{background:transparent;width:7px;margin:2px;}}
QScrollBar::handle:vertical{{background:rgba(139,148,158,90);border-radius:3px;min-height:28px;}}
QScrollBar::handle:vertical:hover{{background:rgba(139,148,158,150);}}
QScrollBar::add-line:vertical,QScrollBar::sub-line:vertical{{height:0px;background:transparent;}}
"""


def make_standard_window(window: QWidget) -> None:
    window.setWindowTitle("\u200b")
    window.setWindowIcon(transparent_icon())
    window.setStyleSheet(window.styleSheet() + SCROLLBAR_STYLE)


def infer_launcher_root(exe_path: str) -> str:
    path = Path(exe_path)
    appdata = os.environ.get("APPDATA")
    candidates: list[Path] = []
    name = path.stem.lower()
    if appdata:
        base = Path(appdata)
        known = {
            "minecraftlauncher": base / ".minecraft",
            "tlauncher": base / ".minecraft",
            "prismlauncher": base / "PrismLauncher",
            "multimc": base / "MultiMC",
            "curseforge": base / "CurseForge",
        }
        candidates.extend([target for key, target in known.items() if key in name])
        candidates.extend([base / ".minecraft", base / path.stem])
    candidates.extend([path.parent, path.parent.parent if path.parent.parent.exists() else path.parent])
    for candidate in candidates:
        if candidate.exists():
            return str(candidate)
    return ""


class LauncherCard(Card):
    def __init__(self, exe_path: str) -> None:
        exe = Path(exe_path)
        root = infer_launcher_root(exe_path)
        super().__init__(exe.stem, root)
        icon = QFileIconProvider().icon(QFileInfo(str(exe)))
        if not icon.isNull():
            self.title.setPixmap(icon.pixmap(24, 24))
            self.title.setText(f"  {exe.stem}")


class LauncherDialog(QDialog):
    def __init__(self, config, parent: QWidget | None = None) -> None:
        super().__init__(parent)
        self.config = config
        self.selected = ""
        self.setModal(True)
        self.resize(560, 560)
        make_standard_window(self)
        root = QVBoxLayout(self)
        root.setContentsMargins(10, 10, 10, 10)
        self.scroll = QScrollArea()
        self.scroll.setWidgetResizable(True)
        self.scroll.setStyleSheet(SCROLLBAR_STYLE)
        self.container = QWidget()
        self.cards = QVBoxLayout(self.container)
        self.cards.setSpacing(10)
        self.scroll.setWidget(self.container)
        root.addWidget(self.scroll, 1)
        row = QHBoxLayout()
        ok = QPushButton(STRINGS.ok)
        skip = QPushButton(STRINGS.continue_without_launcher)
        row.addWidget(skip)
        row.addWidget(ok)
        root.addLayout(row)
        self.toast = Toast(self)
        ok.clicked.connect(self.accept_checked)
        skip.clicked.connect(self.accept)
        self.refresh()

    def refresh(self) -> None:
        while self.cards.count():
            item = self.cards.takeAt(0)
            if item.widget():
                item.widget().deleteLater()
        for path in self.config.data.launchers:
            card = LauncherCard(path)
            card.clicked.connect(lambda checked=False, value=path: self.choose_existing(value))
            self.cards.addWidget(card)
        add = Card("+ Добавить лаунчер", "", centered=True)
        add.setMinimumHeight(120)
        add.clicked.connect(self.add_launcher)
        self.cards.addWidget(add)
        self.cards.addStretch()

    def choose_existing(self, path: str) -> None:
        self.selected = path

    def add_launcher(self) -> None:
        start = os.environ.get("PROGRAMFILES", str(Path.home()))
        path, _ = QFileDialog.getOpenFileName(self, STRINGS.add_launcher, start, "Launcher executable (*.exe);;All files (*)")
        if path:
            self.config.add_launcher(path)
            self.selected = path
            self.refresh()

    def accept_checked(self) -> None:
        if not self.selected:
            self.toast.show_message(STRINGS.choose_launcher_required)
            return
        self.accept()


class ChangedFilesDialog(QDialog):
    def __init__(self, changes, parent: QWidget | None = None) -> None:
        super().__init__(parent)
        self.resize(DIMENSIONS.dialog_width, DIMENSIONS.dialog_height)
        make_standard_window(self)
        box = QVBoxLayout(self)
        box.setContentsMargins(10, 10, 10, 10)
        title = label("Изменённые файлы", center=True)
        box.addWidget(title)
        list_widget = QListWidget()
        box.addWidget(list_widget)
        for change in changes:
            list_widget.addItem(f"{change.path} — {change.status} ({change.old_size} → {change.new_size})")


class SettingsDialog(QDialog):
    def __init__(self, registry, parent: QWidget | None = None) -> None:
        super().__init__(parent)
        self.registry = registry
        self.config = registry.get("ConfigManager")
        self.detector = registry.get("EnvironmentDetector")
        self.scheduler = registry.get("TaskScheduler")
        self.cards_by_name: dict[str, Card] = {}
        self.resize(900, 650)
        make_standard_window(self)
        box = QVBoxLayout(self)
        box.setContentsMargins(12, 12, 12, 12)
        title = label("Настройки", center=True)
        title.setStyleSheet(f"background:transparent;color:{PALETTE.text};font-size:18px;font-weight:600;")
        box.addWidget(title)
        self.cards_layout = QVBoxLayout()
        box.addLayout(self.cards_layout, 1)
        self.create_placeholder_cards()
        footer = label("Заполняется асинхронно через Task Scheduler", muted=True, center=True)
        box.addWidget(footer)
        QTimer.singleShot(0, self.populate_async)

    def launcher_title(self) -> tuple[str, str]:
        if self.config.data.launchers:
            path = Path(self.config.data.launchers[-1])
            title = path.stem if path.suffix else path.name
            return title, infer_launcher_root(str(path)) or str(path if path.is_dir() else path.parent)
        return "Launcher", ""

    def create_placeholder_cards(self) -> None:
        names = ["Java Runtime", "Launcher", "Recaf", "CFR", "FernFlower", "Vineflower", "ASMifier", "JD-GUI", "Bytecode Viewer", "Decompiler", "Compiler", "API Configuration"]
        for name in names:
            card = Card(name, "")
            card.doubleClicked.connect(lambda checked=False, value=name: self.choose_path(value))
            self.cards_by_name[name] = card
            self.cards_layout.addWidget(card)
        self.cards_layout.addStretch()

    def populate_async(self) -> None:
        task = self.scheduler.submit("settings_detect", self.collect_settings_rows)
        timer = QTimer(self)
        timer.setInterval(80)

        def apply_when_ready() -> None:
            if not task.future.done():
                return
            timer.stop()
            rows = task.future.result()
            for name, title, path in rows:
                card = self.cards_by_name.get(name)
                if card:
                    card.set_text(title, path)

        timer.timeout.connect(apply_when_ready)
        timer.start()

    def collect_settings_rows(self) -> list[tuple[str, str, str]]:
        java = self.detector.detect_java()
        launcher_name, launcher_path = self.launcher_title()
        return [
            ("Java Runtime", "Java Runtime", java.path if java.status.startswith("available") else ""),
            ("Launcher", launcher_name, launcher_path),
            ("Recaf", "Recaf", self.config.data.tools.get("Recaf", "")),
            ("CFR", "CFR", self.config.data.tools.get("CFR", "")),
            ("FernFlower", "FernFlower", self.config.data.tools.get("FernFlower", "")),
            ("Vineflower", "Vineflower", self.config.data.tools.get("Vineflower", "")),
            ("ASMifier", "ASMifier", self.config.data.tools.get("ASMifier", "")),
            ("JD-GUI", "JD-GUI", self.config.data.tools.get("JD-GUI", "")),
            ("Bytecode Viewer", "Bytecode Viewer", self.config.data.tools.get("Bytecode Viewer", "")),
            ("Decompiler", "Decompiler", self.config.data.tools.get("Decompiler", "")),
            ("Compiler", "Compiler", self.config.data.tools.get("Compiler", "")),
            ("API Configuration", "API Configuration", self.config.data.api_file),
        ]

    def choose_path(self, name: str) -> None:
        if name == "Launcher":
            folder = QFileDialog.getExistingDirectory(self, "Launcher", str(Path.home()))
            if folder:
                self.config.add_launcher(folder)
                title = Path(folder).name or "Launcher"
                self.cards_by_name[name].set_text(title, folder)
            return
        if name == "API Configuration":
            path, _ = QFileDialog.getOpenFileName(self, "api.txt", str(Path.home()), "API file (api.txt);;Text files (*.txt);;All files (*)")
            if path:
                self.config.set("api_file", path)
                self.registry.get("AIManager").configure_api_file(path)
                self.cards_by_name[name].set_text("API Configuration", path)
            return
        path, _ = QFileDialog.getOpenFileName(self, name, str(Path.home()), "Executable/JAR (*.exe *.jar);;All files (*)")
        if path:
            self.config.data.tools[name] = path
            self.config.save()
            self.cards_by_name[name].set_text(name, path)



class MainWindow(QMainWindow):
    def __init__(self) -> None:
        super().__init__()
        self.registry = build_registry(Path.cwd() / "tools/PATCHER/.runtime")
        self.config = self.registry.get("ConfigManager")
        self.scanner = self.registry.get("ScannerEngine")
        self.patcher = self.registry.get("PatchEngine")
        self.scheduler = self.registry.get("TaskScheduler")
        self.theme = self.registry.get("ThemeManager")
        self.jars = {}
        self.mod_cards: dict[str, ModCard] = {}
        self.setMinimumSize(DIMENSIONS.min_width, DIMENSIONS.min_height)
        self.resize(DIMENSIONS.start_width, DIMENSIONS.start_height)
        self.setStyleSheet(self.theme.stylesheet() + SCROLLBAR_STYLE)
        make_standard_window(self)
        root = QWidget()
        self.setCentralWidget(root)
        box = QVBoxLayout(root)
        box.setContentsMargins(8, 8, 8, 8)
        logo = label("PATCHER", center=True)
        logo.setStyleSheet(f"background:transparent;color:{PALETTE.text};font-size:16px;font-weight:700;letter-spacing:2px;")
        box.addWidget(logo)
        body = QHBoxLayout()
        body.setSpacing(10)
        box.addLayout(body, 1)
        left = QVBoxLayout()
        right = QVBoxLayout()
        body.addLayout(left, 65)
        body.addLayout(right, 35)
        self.drop = JarDropArea(self.launcher_start_dir)
        self.drop.jarSelected.connect(self.load_jar)
        left.addWidget(self.drop, 1)
        self.info = QTextEdit()
        self.info.setReadOnly(True)
        self.info.hide()
        left.addWidget(self.info, 1)
        self.ai_log = AILogPanel()
        left.addWidget(self.ai_log)
        bottom = QHBoxLayout()
        self.model_btn = QPushButton("AI Models")
        self.settings_btn = QPushButton("⚙")
        self.settings_btn.setToolTip("Settings")
        self.settings_btn.setFixedWidth(44)
        bottom.addWidget(self.model_btn)
        bottom.addWidget(self.settings_btn)
        left.addLayout(bottom)
        self.scroll = QScrollArea()
        self.scroll.setWidgetResizable(True)
        self.scroll.setStyleSheet(SCROLLBAR_STYLE)
        self.mod_container = QWidget()
        self.mod_list = QVBoxLayout(self.mod_container)
        self.mod_list.addStretch()
        self.scroll.setWidget(self.mod_container)
        right.addWidget(self.scroll)
        self.save_btn = QPushButton(STRINGS.save_patched)
        right.addWidget(self.save_btn)
        self.model_btn.clicked.connect(self.open_models)
        self.settings_btn.clicked.connect(lambda: SettingsDialog(self.registry, self).exec())
        self.save_btn.clicked.connect(self.save_patched)
        if not self.config.data.launchers:
            LauncherDialog(self.config, self).exec()

    def launcher_start_dir(self) -> str:
        if self.config.data.launchers:
            root = infer_launcher_root(self.config.data.launchers[-1])
            return root or str(Path(self.config.data.launchers[-1]).parent)
        return ""

    def load_jar(self, path: str) -> None:
        self.drop.hide()
        self.info.show()
        self.ai_log.text.append("Запуск анализа JAR…")
        card = ModCard(path)
        card.progress.setValue(3)
        card.start_activity("scanner")
        card.patchRequested.connect(self.patch_jar)
        card.verifyRequested.connect(self.verify_jar)
        card.filesRequested.connect(self.show_files)
        self.mod_cards[path] = card
        self.mod_list.insertWidget(0, card)
        task = self.scheduler.submit("scan", self.scanner.scan, Path(path))
        timer = QTimer(self)
        timer.setInterval(100)

        def finish_when_ready() -> None:
            if not task.future.done():
                current = min(95, card.progress.value + 2)
                card.progress.setValue(current)
                return
            timer.stop()
            jar = task.future.result()
            self.jars[path] = jar
            self.info.setText(f"Файл: {path}\nSHA256: {jar.sha256}\nCRC: {jar.crc}\nКлассы: {jar.class_count}\nПлатформа: {jar.platform.value}\nПроблемы: {len(jar.problems)}")
            card.progress.setValue(100)
            card.stop_activity()
            self.ai_log.text.append("Анализ завершён; план патчей готовится по запросу.")

        timer.timeout.connect(finish_when_ready)
        timer.start()

    def patch_jar(self, path: str) -> None:
        jar = self.jars[path]
        card = self.mod_cards.get(path)
        if card:
            card.start_activity("ai")
            card.progress.setValue(20)
        ai = self.registry.get("AIManager")
        if self.config.data.api_file:
            ai.configure_api_file(self.config.data.api_file)

        def run_patch():
            plan = self.patcher.plan(jar)
            first = ai.enqueue_request(Provider.OPENAI, "patch plan", {"problems": len(plan.problems)})
            second = ai.enqueue_request(Provider.DEEPSEEK, "patch plan", {"problems": len(plan.problems)})
            patched = self.patcher.apply(jar, plan, True)
            return patched, ai.get_response(first).text, ai.get_response(second).text

        task = self.scheduler.submit("patch_ai", run_patch)
        timer = QTimer(self)
        timer.setInterval(100)

        def finish_patch() -> None:
            if not task.future.done():
                if card:
                    card.progress.setValue(min(95, card.progress.value + 3))
                return
            timer.stop()
            patched, first_text, second_text = task.future.result()
            self.jars[path] = patched
            if card:
                card.progress.setValue(100)
                card.stop_activity()
            self.ai_log.text.append(f"AI сравнение: {first_text} / {second_text}\nСоздан: {patched.patched_path}")

        timer.timeout.connect(finish_patch)
        timer.start()

    def verify_jar(self, path: str) -> None:
        jar = self.jars[path]
        target = jar.patched_path or jar.path
        report = self.registry.get("VerificationEngine").verify(target)
        self.ai_log.text.append("\n".join(report.log_lines))
        self.ai_log.text.append(f"Verification success: {report.success}")

    def show_files(self, path: str) -> None:
        ChangedFilesDialog(self.jars[path].changes, self).exec()

    def save_patched(self) -> None:
        patched = [jar.patched_path for jar in self.jars.values() if jar.patched_path]
        if not patched:
            self.ai_log.text.append("Нет пропатченного JAR для сохранения")
            return
        target, _ = QFileDialog.getSaveFileName(self, STRINGS.save_patched, str(patched[-1].name), "Minecraft Mod (*.jar)")
        if target:
            shutil.copy2(patched[-1], target)
            self.ai_log.text.append(f"Сохранено: {target}")

    def open_models(self) -> None:
        dialog = QDialog(self)
        dialog.resize(620, 920)
        make_standard_window(dialog)
        box = QVBoxLayout(dialog)
        box.setContentsMargins(12, 12, 12, 12)
        box.addWidget(label("AI Models", center=True))
        models = QListWidget()
        models.setMaximumHeight(145)
        models.addItems(["OpenAI:gpt-4.1", "DeepSeek:deepseek-chat"])
        box.addWidget(models)
        chat_area = QTextEdit()
        chat_area.setReadOnly(True)
        box.addWidget(chat_area, 1)
        input_row = ChatInput()
        box.addWidget(input_row)

        def add_message(text: str, incoming: bool = False) -> None:
            color = PALETTE.card if incoming else PALETTE.accent
            align = "left" if incoming else "right"
            chat_area.append(f"<div align='{align}' style='margin:8px;'><span style='background:{color};border-radius:8px;padding:12px 16px;color:{PALETTE.text};display:inline-block;'>{text}</span></div>")

        def send(text: str) -> None:
            add_message(text, False)
            provider = Provider.OPENAI if not models.currentItem() or models.currentItem().text().startswith("OpenAI") else Provider.DEEPSEEK
            ai = self.registry.get("AIManager")
            if self.config.data.api_file:
                ai.configure_api_file(self.config.data.api_file)

            def run_chat():
                request = ai.enqueue_request(provider, text, {"source": "chat"})
                return ai.get_response(request).text

            task = self.scheduler.submit("ai_chat", run_chat)
            timer = QTimer(dialog)
            timer.setInterval(100)

            def finish_chat() -> None:
                if not task.future.done():
                    return
                timer.stop()
                add_message(task.future.result(), True)

            timer.timeout.connect(finish_chat)
            timer.start()

        input_row.sendRequested.connect(send)
        models.itemClicked.connect(lambda item: add_message(f"Чат открыт для {item.text()}", True))
        dialog.exec()


def main() -> None:
    app = QApplication([])
    window = MainWindow()
    window.show()
    app.exec()


if __name__ == "__main__":
    main()
