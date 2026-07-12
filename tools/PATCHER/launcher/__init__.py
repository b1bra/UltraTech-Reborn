"""Launcher package exports Java, launcher and Minecraft environment discovery."""
from tools.PATCHER.launcher.detection import EnvironmentDetector, JavaRuntime, LauncherProfile
__all__ = ["EnvironmentDetector", "JavaRuntime", "LauncherProfile"]
