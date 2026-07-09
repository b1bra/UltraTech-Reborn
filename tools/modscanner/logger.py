import json
import os
import platform
import sys
import time
import traceback
from datetime import datetime


class Logger:

    VERSION = "1.0"

    INFO = "INFO"
    DEBUG = "DEBUG"
    SUCCESS = "SUCCESS"
    WARNING = "WARNING"
    ERROR = "ERROR"
    CRITICAL = "CRITICAL"

    def __init__(self):

        self.start_time = time.time()

        self.warning_count = 0
        self.error_count = 0

        self.stage_timers = {}

        self.project_root = os.path.abspath(
            os.path.join(
                os.path.dirname(__file__),
                "../.."
            )
        )

        self.logs_folder = os.path.join(
            os.path.dirname(__file__),
            "logs"
        )

        os.makedirs(
            self.logs_folder,
            exist_ok=True
        )

        self.scanner_log = os.path.join(
            self.logs_folder,
            "scanner.log"
        )

        self.error_log = os.path.join(
            self.logs_folder,
            "errors.log"
        )

        self.session_file = os.path.join(
            self.logs_folder,
            "session.json"
        )

        # Новый запуск очищает старые логи

        open(
            self.scanner_log,
            "w",
            encoding="utf-8"
        ).close()

        open(
            self.error_log,
            "w",
            encoding="utf-8"
        ).close()

        self.write_header()
    def _time(self):

        return datetime.now().strftime(
            "%Y-%m-%d %H:%M:%S"
        )



    def _color(self, level):

        colors = {

            self.INFO: "\033[97m",

            self.DEBUG: "\033[90m",

            self.SUCCESS: "\033[92m",

            self.WARNING: "\033[93m",

            self.ERROR: "\033[91m",

            self.CRITICAL: "\033[95m"

        }

        return colors.get(
            level,
            "\033[97m"
        )



    def _reset(self):

        return "\033[0m"



    def _write(self, text):

        with open(

            self.scanner_log,

            "a",

            encoding="utf-8"

        ) as file:

            file.write(
                text + "\n"
            )



    def write_header(self):

        line = "=" * 80

        self._write(line)
        self._write("ModScanner Logger")
        self._write(line)
        self._write("")

        self._write(
            f"Session started : {self._time()}"
        )

        self._write(
            f"Logger version  : {self.VERSION}"
        )

        self._write("")

        self._write(
            f"Python          : {platform.python_version()}"
        )

        self._write(
            f"Implementation  : {platform.python_implementation()}"
        )

        self._write(
            f"Platform        : {platform.platform()}"
        )

        self._write(
            f"System          : {platform.system()}"
        )

        self._write(
            f"Release         : {platform.release()}"
        )

        self._write(
            f"Machine         : {platform.machine()}"
        )

        self._write(
            f"Architecture    : {platform.architecture()[0]}"
        )

        self._write(
            f"Processor       : {platform.processor()}"
        )

        self._write(
            f"Python executable : {sys.executable}"
        )

        self._write(
            f"Working directory : {os.getcwd()}"
        )

        self._write(
            f"Project root      : {self.project_root}"
        )

        self._write("")
        self._write(line)
        self._write("")
    def log(self, level, message):

        timestamp = self._time()

        line = (
            f"[{timestamp}] "
            f"[{level:<8}] "
            f"{message}"
        )

        try:

            print(
                f"{self._color(level)}"
                f"{line}"
                f"{self._reset()}"
            )

        except Exception:

            print(line)



        self._write(
            line
        )



        if level == self.WARNING:

            self.warning_count += 1



        elif level in (

            self.ERROR,

            self.CRITICAL

        ):

            self.error_count += 1






    def info(self, message):

        self.log(
            self.INFO,
            message
        )



    def debug(self, message):

        self.log(
            self.DEBUG,
            message
        )



    def success(self, message):

        self.log(
            self.SUCCESS,
            message
        )



    def warning(self, message):

        self.log(
            self.WARNING,
            message
        )



    def error(self, message):

        self.log(
            self.ERROR,
            message
        )



    def critical(self, message):

        self.log(
            self.CRITICAL,
            message
        )





    def exception(self, error):

        trace = traceback.format_exc()



        self.error(
            str(error)
        )



        with open(

            self.error_log,

            "a",

            encoding="utf-8"

        ) as file:


            file.write(
                "=" * 80 + "\n"
            )


            file.write(
                f"Time: {self._time()}\n\n"
            )


            file.write(
                f"Exception: {error}\n\n"
            )


            file.write(
                trace
            )


            file.write(
                "\n\n"
            )
    def start_timer(self, name):

        self.stage_timers[name] = time.time()


        self.debug(
            f"Timer started: {name}"
        )



    def stop_timer(self, name):

        if name not in self.stage_timers:

            self.warning(
                f"Timer not found: {name}"
            )

            return 0



        elapsed = (
            time.time()
            -
            self.stage_timers[name]
        )


        self.debug(
            f"Timer finished: {name} ({elapsed:.3f}s)"
        )


        del self.stage_timers[name]


        return elapsed





    def section(self, name):

        line = "-" * 80


        self.info(
            line
        )


        self.info(
            name
        )


        self.info(
            line
        )





    def log_file(self, path, description="File"):

        try:

            size = os.path.getsize(
                path
            )


            self.debug(
                f"{description}: {path} ({size} bytes)"
            )


        except Exception as error:


            self.warning(
                f"Cannot read file info: {path} ({error})"
            )





    def log_list(self, title, values):

        self.debug(
            f"{title}: {len(values)} entries"
        )


        for value in values:

            self.debug(
                f"  - {value}"
            )





    def log_dict(self, title, data):

        self.debug(
            f"{title}: {len(data)} entries"
        )


        for key, value in data.items():

            self.debug(
                f"  {key}: {value}"
            )
    def create_session_data(self):

        duration = (
            time.time()
            -
            self.start_time
        )


        return {

            "logger_version":
            self.VERSION,


            "session_start":
            datetime.fromtimestamp(
                self.start_time
            ).strftime(
                "%Y-%m-%d %H:%M:%S"
            ),


            "session_end":
            self._time(),


            "duration_seconds":
            round(
                duration,
                3
            ),


            "python_version":
            platform.python_version(),


            "python_executable":
            sys.executable,


            "platform":
            platform.platform(),


            "system":
            platform.system(),


            "machine":
            platform.machine(),


            "warnings":
            self.warning_count,


            "errors":
            self.error_count

        }





    def save_session(self):

        data = self.create_session_data()



        try:

            with open(

                self.session_file,

                "w",

                encoding="utf-8"

            ) as file:


                json.dump(

                    data,

                    file,

                    indent=4,

                    ensure_ascii=False

                )



            self.debug(
                "Session data saved"
            )



        except Exception as error:


            self.exception(
                error
            )





    def finish(self):

        self.section(
            "ModScanner session finished"
        )


        duration = (
            time.time()
            -
            self.start_time
        )


        self.info(
            f"Runtime: {duration:.3f} seconds"
        )


        self.info(
            f"Warnings: {self.warning_count}"
        )


        self.info(
            f"Errors: {self.error_count}"
        )


        self.save_session()
    def log_mod_start(self, mod_name):

        self.section(
            f"Scanning mod: {mod_name}"
        )


        self.start_timer(
            f"mod_{mod_name}"
        )



    def log_mod_finish(self, mod_name):

        elapsed = self.stop_timer(
            f"mod_{mod_name}"
        )


        self.success(
            f"Finished mod: {mod_name} ({elapsed:.3f}s)"
        )





    def log_scan_result(self, category, value):

        self.info(
            f"{category}: {value}"
        )





    def log_dependency(self, mod, dependency):

        self.debug(
            f"Dependency detected: {mod} -> {dependency}"
        )





    def log_class(self, class_name):

        self.debug(
            f"Class detected: {class_name}"
        )





    def log_resource(self, resource_type, count):

        self.debug(
            f"Resources: {resource_type} = {count}"
        )





    def log_recipe(self, recipe_type, count):

        self.debug(
            f"Recipes: {recipe_type} = {count}"
        )





    def log_exception_context(
            self,
            module,
            stage,
            error
    ):

        with open(

            self.error_log,

            "a",

            encoding="utf-8"

        ) as file:


            file.write(
                "=" * 80 + "\n"
            )


            file.write(
                f"Time: {self._time()}\n"
            )


            file.write(
                f"Module: {module}\n"
            )


            file.write(
                f"Stage: {stage}\n"
            )


            file.write(
                f"Error: {error}\n\n"
            )


            file.write(
                traceback.format_exc()
            )


            file.write(
                "\n\n"
            )


        self.error(
            f"{module} failed during {stage}: {error}"
        )
    def add_warning(self, message):

        self.warning(
            message
        )





    def add_error(self, message):

        self.error(
            message
        )





    def add_critical(self, message):

        self.critical(
            message
        )





    def check_file(self, path):

        if os.path.isfile(path):

            self.debug(
                f"File exists: {path}"
            )

            return True



        self.warning(
            f"File missing: {path}"
        )

        return False





    def check_folder(self, path):

        if os.path.isdir(path):

            self.debug(
                f"Folder exists: {path}"
            )

            return True



        self.warning(
            f"Folder missing: {path}"
        )

        return False





    def log_system_info(self):

        self.section(
            "System information"
        )


        self.info(
            f"Python: {platform.python_version()}"
        )


        self.info(
            f"OS: {platform.platform()}"
        )


        self.info(
            f"CPU: {platform.processor()}"
        )


        self.info(
            f"Architecture: {platform.architecture()[0]}"
        )


        self.info(
            f"Executable: {sys.executable}"
        )





    def flush(self):

        """
        Принудительная запись логов.
        Используется перед аварийным завершением.
        """

        try:

            with open(

                self.scanner_log,

                "a",

                encoding="utf-8"

            ) as file:

                file.flush()



            with open(

                self.error_log,

                "a",

                encoding="utf-8"

            ) as file:

                file.flush()



        except Exception:

            pass
    def export_json(self, path, data):

        try:

            with open(

                path,

                "w",

                encoding="utf-8"

            ) as file:


                json.dump(

                    data,

                    file,

                    indent=4,

                    ensure_ascii=False

                )


            self.debug(
                f"JSON exported: {path}"
            )


        except Exception as error:


            self.exception(
                error
            )





    def read_json(self, path):

        try:

            with open(

                path,

                "r",

                encoding="utf-8"

            ) as file:


                data = json.load(
                    file
                )


            self.debug(
                f"JSON loaded: {path}"
            )


            return data



        except Exception as error:


            self.exception(
                error
            )


            return None





    def separator(self):

        self._write(
            ""
        )

        self._write(
            "=" * 80
        )

        self._write(
            ""
        )





    def banner(self, title):

        line = "=" * 80


        self.info(
            line
        )


        self.info(
            title
        )


        self.info(
            line
        )





    def get_statistics(self):

        return {

            "warnings":
            self.warning_count,


            "errors":
            self.error_count,


            "runtime":
            round(

                time.time()
                -
                self.start_time,

                3

            )

        }
    def __enter__(self):

        self.info(
            "Logger context started"
        )

        return self





    def __exit__(
            self,
            exc_type,
            exc_value,
            traceback_object
    ):

        if exc_value:

            self.exception(
                exc_value
            )


        self.finish()





    def shutdown(self):

        self.info(
            "Logger shutting down"
        )


        self.flush()


        self.save_session()





# ==========================================
# Global logger instance
# ==========================================

logger = Logger()
# ==========================================
# Helper functions
# ==========================================

def get_logger():

    return logger





def log_info(message):

    logger.info(
        message
    )





def log_debug(message):

    logger.debug(
        message
    )





def log_success(message):

    logger.success(
        message
    )





def log_warning(message):

    logger.warning(
        message
    )





def log_error(message):

    logger.error(
        message
    )





def log_critical(message):

    logger.critical(
        message
    )





# ==========================================
# Standalone test
# ==========================================

if __name__ == "__main__":


    logger.banner(
        "Logger standalone test"
    )


    logger.info(
        "Information message"
    )


    logger.debug(
        "Debug message"
    )


    logger.success(
        "Success message"
    )


    logger.warning(
        "Warning message"
    )


    logger.error(
        "Error message"
    )


    logger.start_timer(
        "test_timer"
    )


    time.sleep(
        1
    )


    logger.stop_timer(
        "test_timer"
    )


    logger.finish()