#!/usr/bin/env python3

"""
github: publish to github releases using github cli interface.
steam: publish to steam workshop using mod-uploader.jar
"""

import json
import sys
import zipfile
from concurrent.futures import ProcessPoolExecutor
from enum import StrEnum
from pathlib import Path
from subprocess import run
from typing import Callable, TypeVar

from typer import Argument, Typer

BASE = Path("docs/changelog")

CHANGELOG = BASE / "changelog.md"
VERSION = (BASE / "version.txt").read_text()

CHANGELOG_TEXT = CHANGELOG.read_text()

TITLE = CHANGELOG_TEXT.splitlines()[0].strip("# ")
BODY = "\n".join(CHANGELOG_TEXT.splitlines()[2:])

JAR = zipfile.Path("build/libs/MarisaContinued.jar")
MODINFO = JAR / "ModTheSpire.json"

T = TypeVar("T")


def apply(x: Callable[[], T]) -> T:
    print(f"Running {x.__name__}")
    return x()


def wait():
    try:
        input("Press any key to continue...")
    except KeyboardInterrupt:
        sys.exit(0)


def verify_jar_version():
    """Verify modjson.json"""
    text = MODINFO.read_text()
    modjson = json.loads(text)
    expected = VERSION
    actual = modjson["version"]
    assert expected == actual, f"{expected = } {actual = }"


def run_command(command: list[str], *, cwd=Path()):
    print(" ".join(command))
    wait()
    run(command, cwd=cwd, check=True)


def github():
    COMMAND = [
        *"gh release create".split(),
        # fmt: off
        "--verify-tag", f"v{VERSION}",
        "--title", f"'{TITLE}'",
        "--notes", f"'{BODY}'",
        # fmt: on
        # artifacts
        f"{str(JAR).removesuffix('/')}#MarisaContinued-{VERSION}.jar",
    ]
    run_command(COMMAND)


def steam():
    STS = Path("/home/scarf/Documents/SlayTheSpire/")
    COMMAND = "java -jar mod-uploader.jar upload -w MarisaContinued/".split(
        " "
    )
    run_command(COMMAND, cwd=STS)


class Command(StrEnum):
    STEAM = steam
    GITHUB = github


app = Typer()


@app.command(context_settings=dict(help_option_names=["-h", "--help"]))
def main(commands: list[Command] = Argument(..., help=__doc__)):
    verify_jar_version()

    with ProcessPoolExecutor() as executor:
        executor.map(apply, set(commands))  # type: ignore


if __name__ == "__main__":
    app()
