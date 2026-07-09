import argparse
import json
import os
import re
import string
import zipfile
from collections import Counter
from datetime import datetime

try:
    from logger import logger
except Exception as error:
    print("Logger import failed:", error)

    class DummyLogger:
        def info(self, text): print("[INFO]", text)
        def debug(self, text): print("[DEBUG]", text)
        def success(self, text): print("[SUCCESS]", text)
        def warning(self, text): print("[WARNING]", text)
        def error(self, text): print("[ERROR]", text)
        def section(self, text): print("-" * 80 + "\n" + text + "\n" + "-" * 80)
        def banner(self, text): print("=" * 80 + "\n" + text + "\n" + "=" * 80)
        def start_timer(self, name): pass
        def stop_timer(self, name): return 0
        def log_file(self, path, description="File"): self.debug(f"{description}: {path}")
        def log_mod_start(self, mod): self.info(f"Scanning mod: {mod}")
        def log_mod_finish(self, mod): self.success(f"Finished mod: {mod}")
        def log_exception_context(self, module, stage, error): self.error(f"{module} failed during {stage}: {error}")
        def finish(self): pass

    logger = DummyLogger()

MODULE_NAME = "ModPacketLoggerLite"
VERSION = "1.0"
LOLI_SERVER_PATTERNS = [
    "loliland", "loli.land", "mc.loliland", "api.loliland", "auth.loliland",
    "play.loliland", "launcher.loliland", "static.loliland", "cdn.loliland"
]
LOLI_SITE_PATTERNS = [
    "loliland.ru", "www.loliland.ru", "https://loliland", "http://loliland",
    "forum.loliland", "wiki.loliland", "shop.loliland", "cabinet.loliland",
    "discord.gg/loliland", "vk.com/loliland"
]
NETWORK_PATTERNS = [
    "simpleimpl", "simplechannel", "networkregistry", "fmlnetworkevent",
    "clientcustompayload", "servercustompayload", "custompayload", "packetbuffer",
    "bytebuf", "cpacket", "spacket", "packet", "channel", "handshake",
    "netty", "messagecontext", "imessage", "imessagehandler", "registermessage",
    "sendtoserver", "sendtoall", "sendtoplayer", "sendtoallaround", "clientbound",
    "serverbound", "play_client", "play_server", "payload"
]
CLIENT_PATTERNS = [
    "client", "minecraft.getminecraft", "net.minecraft.client", "clientproxy",
    "side.client", "dist.client", "onlyin", "clientbound", "sendtoserver"
]
PRINTABLE = set(bytes(string.printable, "ascii"))


def get_project_root():
    return os.path.abspath(os.path.join(os.path.dirname(__file__), "../.."))


def get_docs_path():
    path = os.path.join(get_project_root(), "docs", "generated")
    os.makedirs(path, exist_ok=True)
    return path


def ask_mod_folder():
    print("\nEnter mods folder path:")
    path = input("> ").strip().replace('"', "")
    if not os.path.isdir(path):
        raise Exception("Mods folder does not exist: " + path)
    return path


def find_jars(folder):
    jars = []
    for root, dirs, files in os.walk(folder):
        dirs[:] = [item for item in dirs if item not in (".git", "__pycache__")]
        for file_name in files:
            if file_name.lower().endswith(".jar"):
                jars.append(os.path.join(root, file_name))
    jars.sort(key=lambda value: value.lower())
    logger.info(f"Found mod JAR files: {len(jars)}")
    return jars


def safe_decode(data):
    return data.decode("utf-8", errors="ignore")


def extract_ascii_strings(data, min_length=4):
    current = []
    values = []
    for byte in data:
        if byte in PRINTABLE and byte not in (10, 13, 9, 11, 12):
            current.append(chr(byte))
        else:
            if len(current) >= min_length:
                values.append("".join(current))
            current = []
    if len(current) >= min_length:
        values.append("".join(current))
    return values


def read_metadata(jar, jar_path):
    filename = os.path.splitext(os.path.basename(jar_path))[0]
    metadata = {"name": filename, "modid": filename, "version": "Unknown", "authors": "Unknown"}
    readers = {
        "mcmod.info": read_mcmod_info,
        "mods.toml": read_mods_toml,
        "fabric.mod.json": read_fabric_json,
        "quilt.mod.json": read_fabric_json,
    }
    for entry in jar.namelist():
        lower = entry.lower()
        for suffix, reader in readers.items():
            if lower.endswith(suffix):
                try:
                    reader(safe_decode(jar.read(entry)), metadata)
                    logger.debug(f"Metadata found in {entry}")
                    return metadata
                except Exception as error:
                    logger.warning(f"Cannot parse metadata {entry}: {error}")
    return metadata


def read_mcmod_info(text, metadata):
    data = json.loads(text)
    if isinstance(data, list) and data:
        data = data[0]
    if isinstance(data, dict):
        metadata["name"] = data.get("name") or metadata["name"]
        metadata["modid"] = data.get("modid") or metadata["modid"]
        metadata["version"] = data.get("version") or metadata["version"]
        author = data.get("authorList") or data.get("authors") or data.get("author")
        if isinstance(author, list):
            author = ", ".join(str(item) for item in author)
        metadata["authors"] = author or metadata["authors"]


def read_mods_toml(text, metadata):
    for key, target in (("displayName", "name"), ("modId", "modid"), ("version", "version"), ("authors", "authors")):
        match = re.search(rf'{key}\s*=\s*"([^"]+)"', text)
        if match:
            metadata[target] = match.group(1)


def read_fabric_json(text, metadata):
    data = json.loads(text)
    metadata["name"] = data.get("name") or metadata["name"]
    metadata["modid"] = data.get("id") or metadata["modid"]
    metadata["version"] = data.get("version") or metadata["version"]
    authors = data.get("authors")
    if isinstance(authors, list):
        metadata["authors"] = ", ".join(str(item.get("name", item)) if isinstance(item, dict) else str(item) for item in authors)


def collect_evidence(entry, text, patterns):
    lower = text.lower()
    found = []
    for pattern in patterns:
        if pattern in lower:
            found.append(pattern)
    return [f"{entry}: {pattern}" for pattern in sorted(set(found))]


def analyze_jar(jar_path):
    filename = os.path.basename(jar_path)
    logger.log_mod_start(filename)
    result = {
        "file": filename,
        "path": jar_path,
        "name": os.path.splitext(filename)[0],
        "modid": os.path.splitext(filename)[0],
        "version": "Unknown",
        "authors": "Unknown",
        "network_evidence": [],
        "client_network_evidence": [],
        "loliland_servers": [],
        "loliland_site": [],
        "loli_prefix": [],
        "network_packets": Counter(),
        "scanned_entries": 0,
    }
    try:
        with zipfile.ZipFile(jar_path, "r") as jar:
            result.update(read_metadata(jar, jar_path))
            for entry in jar.namelist():
                if entry.endswith("/"):
                    continue
                lower_entry = entry.lower()
                should_scan = lower_entry.endswith((".class", ".json", ".toml", ".info", ".properties", ".cfg", ".txt", ".xml", ".yml", ".yaml", ".mcmeta"))
                if not should_scan:
                    continue
                result["scanned_entries"] += 1
                data = jar.read(entry)
                text = "\n".join(extract_ascii_strings(data)) if lower_entry.endswith(".class") else safe_decode(data)
                combined = entry + "\n" + text
                net = collect_evidence(entry, combined, NETWORK_PATTERNS)
                if net:
                    result["network_evidence"].extend(net[:10])
                    for evidence in net:
                        result["network_packets"][evidence.split(": ", 1)[1]] += 1
                    if collect_evidence(entry, combined, CLIENT_PATTERNS):
                        result["client_network_evidence"].extend(net[:10])
                result["loliland_servers"].extend(collect_evidence(entry, combined, LOLI_SERVER_PATTERNS))
                result["loliland_site"].extend(collect_evidence(entry, combined, LOLI_SITE_PATTERNS))
                if re.search(r"(^|[^A-Za-z])Loli[A-Za-z0-9_.$-]*", combined):
                    result["loli_prefix"].append(entry)
    except Exception as error:
        logger.log_exception_context(MODULE_NAME, f"analyze_jar {filename}", error)
        result["error"] = str(error)
    for key in ("network_evidence", "client_network_evidence", "loliland_servers", "loliland_site", "loli_prefix"):
        result[key] = sorted(set(result[key]))[:80]
    logger.info(f"{filename}: network markers={len(result['network_evidence'])}, client markers={len(result['client_network_evidence'])}")
    logger.info(f"{filename}: Loliland servers={len(result['loliland_servers'])}, site={len(result['loliland_site'])}, Loli prefix={len(result['loli_prefix'])}")
    logger.log_mod_finish(filename)
    return result


def yes_no(value):
    return "yes" if value else "no"


def write_block(file_handle, lines):
    file_handle.write("```text\n")
    for line in lines:
        file_handle.write(str(line) + "\n")
    file_handle.write("```\n\n")


def create_networklog(results, mods_folder):
    path = os.path.join(get_docs_path(), "networklog.md")
    logger.section("Creating networklog.md")
    with open(path, "w", encoding="utf-8") as file_handle:
        file_handle.write("# ModPacketLogger Lite Network Log\n\n")
        file_handle.write(f"Generated: {datetime.now()}\n\n")
        file_handle.write(f"Mods folder: `{mods_folder}`\n\n")
        file_handle.write(f"Total mods: {len(results)}\n\n")
        for mod in results:
            file_handle.write(f"## {mod.get('name', 'Unknown')}\n\n")
            lines = [
                f"- File: {mod.get('file')}",
                f"- Mod ID: {mod.get('modid')}",
                f"- Version: {mod.get('version')}",
                f"- Authors: {mod.get('authors')}",
                f"- Scanned entries: {mod.get('scanned_entries')}",
                f"- Client network packet markers: {yes_no(mod.get('client_network_evidence'))}",
                f"- Network packet/API markers: {yes_no(mod.get('network_evidence'))}",
                f"- Uses Loliland server data: {yes_no(mod.get('loliland_servers'))}",
                f"- Uses Loliland site data: {yes_no(mod.get('loliland_site'))}",
                f"- Contains Loli prefix: {yes_no(mod.get('loli_prefix'))}",
            ]
            if mod.get("network_packets"):
                lines.append("- Packet marker summary: " + ", ".join(f"{key}={value}" for key, value in mod["network_packets"].most_common(12)))
            for title, key in (("Client packet evidence", "client_network_evidence"), ("Network evidence", "network_evidence"), ("Loliland server evidence", "loliland_servers"), ("Loliland site evidence", "loliland_site"), ("Loli prefix evidence", "loli_prefix")):
                values = mod.get(key) or []
                lines.append(f"- {title}:" if values else f"- {title}: none")
                lines.extend(f"  - {value}" for value in values[:20])
            if mod.get("error"):
                lines.append(f"- Error: {mod['error']}")
            write_block(file_handle, lines)
    logger.log_file(path, "Network log")
    logger.success(f"networklog.md created: {path}")
    return path


def parse_args():
    parser = argparse.ArgumentParser(description="Analyze Minecraft mod JARs for client network packet markers and Loliland usage.")
    parser.add_argument("mods_folder", nargs="?", help="Path to the folder with mod JAR files")
    return parser.parse_args()


def main():
    logger.banner(f"ModPacketLogger Lite v{VERSION} starting")
    args = parse_args()
    mods_folder = args.mods_folder or ask_mod_folder()
    mods_folder = os.path.abspath(mods_folder.replace('"', ""))
    if not os.path.isdir(mods_folder):
        raise Exception("Mods folder does not exist: " + mods_folder)
    logger.info(f"Mods folder selected: {mods_folder}")
    jars = find_jars(mods_folder)
    if not jars:
        raise Exception("No JAR files found")
    results = [analyze_jar(jar_path) for jar_path in jars]
    output = create_networklog(results, mods_folder)
    logger.success(f"Analysis complete. Report saved to {output}")
    logger.finish()


if __name__ == "__main__":
    main()
