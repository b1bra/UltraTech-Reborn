# PATCHER запуск

- Обычный запуск: `python tools/PATCHER/app.py`.
- Запуск без консоли Windows: `tools/PATCHER/appw.pyw`.
- Если приложение запущено прямо из файла, entrypoint сам добавляет корень репозитория в `sys.path`, поэтому ошибка `ModuleNotFoundError: No module named 'tools'` не возникает.
- Если в headless/Linux-контейнере отсутствуют нативные Qt/OpenGL библиотеки (`libGL.so.1`), launcher не падает с traceback: он пишет диагностическое сообщение, проверяет Core API и завершает работу с кодом `0`. На desktop-окружении с PySide6 и Qt runtime открывается GUI.
