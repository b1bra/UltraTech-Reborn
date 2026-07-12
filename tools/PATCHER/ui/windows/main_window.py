"""Frameless main window, launcher first-run dialog, settings, model chat and file-change dialogs."""
from __future__ import annotations
from pathlib import Path
from PySide6.QtCore import Qt, QTimer
from PySide6.QtWidgets import QApplication, QDialog, QFileDialog, QFrame, QLabel, QMainWindow, QPushButton, QScrollArea, QVBoxLayout, QHBoxLayout, QWidget, QTextEdit, QListWidget
from tools.PATCHER.bootstrap import build_registry
from tools.PATCHER.core.models.entities import Provider
from tools.PATCHER.resources.design_tokens import DIMENSIONS, STRINGS, PALETTE
from tools.PATCHER.ui.widgets.common import TitleBar, Toast, JarDropArea, ModCard, AILogPanel, Card

class LauncherDialog(QDialog):
    def __init__(self,config,parent=None):
        super().__init__(parent); self.config=config; self.selected=""; self.setModal(True); self.setWindowTitle('Launcher'); self.resize(520,520)
        box=QVBoxLayout(self); self.cards=QVBoxLayout(); box.addLayout(self.cards); self.refresh(); row=QHBoxLayout(); ok=QPushButton(STRINGS.ok); skip=QPushButton(STRINGS.continue_without_launcher); row.addWidget(skip); row.addWidget(ok); box.addLayout(row); self.toast=Toast(self); ok.clicked.connect(self.accept_checked); skip.clicked.connect(self.accept)
    def refresh(self):
        while self.cards.count(): self.cards.takeAt(0).widget().deleteLater()
        for p in self.config.data.launchers:
            c=Card(Path(p).name,p); c.clicked.connect(lambda checked=False,x=p:self.choose_existing(x)); self.cards.addWidget(c)
        add=Card('+  '+STRINGS.add_launcher,'QFileDialog'); add.clicked.connect(self.add_launcher); self.cards.addWidget(add)
    def choose_existing(self,p): self.selected=p
    def add_launcher(self):
        p=QFileDialog.getExistingDirectory(self,STRINGS.add_launcher,str(Path.home()))
        if p: self.config.add_launcher(p); self.selected=p; self.refresh()
    def accept_checked(self):
        if not self.selected: self.toast.show_message(STRINGS.choose_launcher_required); return
        self.accept()

class ChangedFilesDialog(QDialog):
    def __init__(self,changes,parent=None):
        super().__init__(parent); self.resize(DIMENSIONS.dialog_width,DIMENSIONS.dialog_height); box=QVBoxLayout(self); box.addWidget(QLabel('Изменённые файлы')); listw=QListWidget(); box.addWidget(listw)
        for ch in changes: listw.addItem(f"{ch.path} — {ch.status} ({ch.old_size} → {ch.new_size})")

class SettingsDialog(QDialog):
    def __init__(self,registry,parent=None):
        super().__init__(parent); self.resize(900,650); box=QVBoxLayout(self); box.addWidget(QLabel('Настройки'))
        for title in ['Java Runtime','Minecraft','Launcher','Recaf / CFR / FernFlower / Vineflower / ASMifier / JD-GUI / Bytecode Viewer','API Configuration']:
            box.addWidget(Card(title,'Заполняется асинхронно через Task Scheduler'))

class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__(); self.registry=build_registry(Path.cwd()/'tools/PATCHER/.runtime'); self.config=self.registry.get('ConfigManager'); self.scanner=self.registry.get('ScannerEngine'); self.patcher=self.registry.get('PatchEngine'); self.scheduler=self.registry.get('TaskScheduler'); self.theme=self.registry.get('ThemeManager'); self.jars={}
        self.setWindowFlag(Qt.WindowType.FramelessWindowHint); self.setMinimumSize(DIMENSIONS.min_width,DIMENSIONS.min_height); self.resize(DIMENSIONS.start_width,DIMENSIONS.start_height); self.setStyleSheet(self.theme.stylesheet())
        root=QWidget(); self.setCentralWidget(root); box=QVBoxLayout(root); box.setContentsMargins(0,0,0,0); box.addWidget(TitleBar(self)); body=QHBoxLayout(); box.addLayout(body,1)
        left=QVBoxLayout(); right=QVBoxLayout(); body.addLayout(left,65); body.addLayout(right,35)
        self.drop=JarDropArea(); self.drop.jarSelected.connect(self.load_jar); left.addWidget(self.drop,1); self.info=QTextEdit(); self.info.setReadOnly(True); self.info.hide(); left.addWidget(self.info,1); self.ai_log=AILogPanel(); left.addWidget(self.ai_log)
        bottom=QHBoxLayout(); self.model_btn=QPushButton('AI Models'); self.settings_btn=QPushButton('Settings'); bottom.addWidget(self.model_btn); bottom.addWidget(self.settings_btn); left.addLayout(bottom)
        self.scroll=QScrollArea(); self.scroll.setWidgetResizable(True); self.mod_container=QWidget(); self.mod_list=QVBoxLayout(self.mod_container); self.mod_list.addStretch(); self.scroll.setWidget(self.mod_container); right.addWidget(self.scroll); self.save_btn=QPushButton(STRINGS.save_patched); right.addWidget(self.save_btn)
        self.model_btn.clicked.connect(self.open_models); self.settings_btn.clicked.connect(lambda:SettingsDialog(self.registry,self).exec()); self.save_btn.clicked.connect(self.save_patched)
        if not self.config.data.launchers: LauncherDialog(self.config,self).exec()
    def load_jar(self,path):
        self.drop.hide(); self.info.show(); self.ai_log.text.append('Запуск анализа JAR…')
        task=self.scheduler.submit('scan',self.scanner.scan,Path(path))
        timer=QTimer(self); timer.setInterval(100)
        def finish_when_ready():
            if not task.future.done():
                return
            timer.stop(); jar=task.future.result(); self.jars[path]=jar
            self.info.setText(f"Файл: {path}\nSHA256: {jar.sha256}\nCRC: {jar.crc}\nКлассы: {jar.class_count}\nПлатформа: {jar.platform.value}\nПроблемы: {len(jar.problems)}")
            card=ModCard(path); card.progress.setValue(96); card.patchRequested.connect(self.patch_jar); card.verifyRequested.connect(self.verify_jar); card.filesRequested.connect(self.show_files); self.mod_list.insertWidget(0,card)
            self.ai_log.text.append('Анализ завершён; план патчей готовится по запросу.')
        timer.timeout.connect(finish_when_ready); timer.start()
    def patch_jar(self,path):
        jar=self.jars[path]; plan=self.patcher.plan(jar); ai=self.registry.get('AIManager'); a=ai.enqueue_request(Provider.OPENAI,'patch plan',{'problems':len(plan.problems)}); b=ai.enqueue_request(Provider.DEEPSEEK,'patch plan',{'problems':len(plan.problems)}); jar=self.patcher.apply(jar,plan,True); self.jars[path]=jar; self.ai_log.text.append(f'AI сравнение: {ai.get_response(a).text} / {ai.get_response(b).text}\nСоздан: {jar.patched_path}')
    def verify_jar(self,path):
        jar=self.jars[path]; target=jar.patched_path or jar.path; report=self.registry.get('VerificationEngine').verify(target); self.ai_log.text.append('\n'.join(report.log_lines)); self.ai_log.text.append(f'Verification success: {report.success}')
    def show_files(self,path): ChangedFilesDialog(self.jars[path].changes,self).exec()
    def save_patched(self):
        patched=[jar.patched_path for jar in self.jars.values() if jar.patched_path]
        if not patched: self.ai_log.text.append('Нет пропатченного JAR для сохранения'); return
        target,_=QFileDialog.getSaveFileName(self,STRINGS.save_patched,str(patched[-1].name),'Minecraft Mod (*.jar)')
        if target:
            import shutil; shutil.copy2(patched[-1],target); self.ai_log.text.append(f'Сохранено: {target}')
    def open_models(self):
        d=QDialog(self); d.resize(700,500); box=QVBoxLayout(d); box.addWidget(QLabel('Выбор модели')); listw=QListWidget(); listw.addItems(['OpenAI:gpt-4.1','DeepSeek:deepseek-chat']); chat=QTextEdit(); box.addWidget(listw); box.addWidget(chat); listw.itemClicked.connect(lambda i: chat.append(f'Чат открыт для {i.text()}\nStreaming: готов к генерации.')); d.exec()

def main():
    app=QApplication([]); w=MainWindow(); w.show(); app.exec()
if __name__=='__main__': main()
