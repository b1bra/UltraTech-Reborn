from PySide6.QtCore import Signal, QObject

class AppSignals(QObject):
    """Все сигналы приложения для межмодульного взаимодействия."""
    file_dropped = Signal(str)              # путь к загруженному .jar
    analysis_started = Signal()
    analysis_step_changed = Signal(str)     # текст текущего этапа
    progress_updated = Signal(int)          # 0..100
    analysis_finished = Signal()
    patching_finished = Signal()            # всё готово, можно активировать Save
    log_message = Signal(str, str)          # сообщение, уровень (INFO, WARNING, ERROR, SUCCESS)