from __future__ import annotations

from dataclasses import dataclass
from PySide6.QtWidgets import QDialog, QFrame, QLabel, QPushButton, QScrollArea, QTextEdit, QVBoxLayout, QWidget, QHBoxLayout

from patcher.ai.manager import AIContext, AIManager, AIRequest, ApiKey

@dataclass(slots=True)
class ChatMessage:
    role: str
    text: str

class ModelSelectorDialog(QDialog):
    """Lists api.txt models and optionally opens chat for the selected model."""

    def __init__(self, models: list[ApiKey], allow_chat: bool) -> None:
        super().__init__()
        self.models = models
        self.allow_chat = allow_chat
        self.selected: ApiKey | None = None
        self.setWindowTitle("AI Models")
        self.resize(620, 520)
        root = QVBoxLayout(self)
        title = QLabel("Выбор модели ИИ" if allow_chat else "Предпросмотр моделей")
        title.setStyleSheet("font-size:22px;font-weight:800;")
        root.addWidget(title)
        scroll = QScrollArea(); scroll.setWidgetResizable(True)
        holder = QWidget(); self.list = QVBoxLayout(holder); scroll.setWidget(holder); root.addWidget(scroll)
        if not models:
            self.list.addWidget(QLabel("api.txt не настроен или не содержит моделей."))
        for model in models:
            self.list.addWidget(self._card(model))
        self.list.addStretch()

    def _card(self, model: ApiKey) -> QFrame:
        frame = QFrame(objectName="Card")
        layout = QVBoxLayout(frame)
        title = QLabel(model.name)
        title.setStyleSheet("font-weight:800;color:#f0f0f0;")
        key = QLabel(model.key)
        key.setObjectName("Muted")
        layout.addWidget(title)
        layout.addWidget(key)
        if self.allow_chat:
            frame.mouseReleaseEvent = lambda _event: self._select(model)
        return frame

    def _select(self, model: ApiKey) -> None:
        self.selected = model
        self.accept()

class AIChatWindow(QDialog):
    """Chat window that sends prompts to AIManager immediately and writes responses to the journal."""

    def __init__(self, manager: AIManager, model: ApiKey, context: AIContext, on_log: callable) -> None:
        super().__init__()
        self.manager = manager
        self.model = model
        self.context = context
        self.on_log = on_log
        self.setWindowTitle(f"AI Chat — {model.name}")
        self.resize(720, 560)
        root = QVBoxLayout(self)
        self.history = QTextEdit(); self.history.setReadOnly(True); root.addWidget(self.history)
        row = QHBoxLayout(); self.input = QTextEdit(); self.input.setFixedHeight(86); send = QPushButton("Отправить"); send.clicked.connect(self._send)
        row.addWidget(self.input); row.addWidget(send); root.addLayout(row)
        self._append("system", f"Модель {model.name} готова. Запросы обрабатываются AI Manager без бесконечного ожидания.")

    def _append(self, role: str, text: str) -> None:
        self.history.append(f"[{role}] {text}")
        self.on_log(f"{self.model.name}: {role}: {text}")

    def _send(self) -> None:
        prompt = self.input.toPlainText().strip()
        if not prompt:
            return
        self.input.clear()
        self._append("user", prompt)
        response = self.manager.submit(AIRequest(self.model, prompt, self.context))
        self._append("assistant", response.content)
