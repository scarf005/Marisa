#!/usr/bin/env python3

"""
github: publish to github releases using github cli interface.
steam: publish to steam workshop using mod-uploader.jar
"""

import json
import sys
import zipfile
from concurrent.futures import ProcessPoolExecutor
from dataclasses import dataclass
from enum import StrEnum, auto
from pathlib import Path
from subprocess import run
from typing import Callable, Self, TypeVar

from typer import Argument, Option, Typer

BASE = Path("docs/changelog")

CHANGELOG = BASE / "changelog.md"
VERSION = (BASE / "version.txt").read_text().strip()

CHANGELOG_TEXT = CHANGELOG.read_text()

TITLE = CHANGELOG_TEXT.splitlines()[0].strip("# ")
BODY = "\n".join(CHANGELOG_TEXT.splitlines()[2:])

JAR = zipfile.Path("build/libs/MarisaContinued.jar")
MODINFO = JAR / "ModTheSpire.json"
STS = Path().home() / ".steam/steam/steamapps/common/" / "SlayTheSpire"


@dataclass
class Context:
    verify_tag: bool


T = TypeVar("T")
Fn = Callable[[Context], T]


@dataclass
class Runner:
    ctx: Context

    def __call__(self, x: Fn[T]) -> T:
        print(f"Running {x.__name__}")
        return x(self.ctx)


def wait():
    try:
        input("Press any key to continue...")
    except KeyboardInterrupt:
        sys.exit(0)


def verify_hard_links():
    run_command(["deno", "task", "--quiet", "link", "--check", "--quiet"])


def verify_jar_version():
    """Verify modjson.json"""
    text = MODINFO.read_text()
    modjson = json.loads(text)
    expected = VERSION
    actual = modjson["version"]
    assert expected == actual, f"{expected = } {actual = }"


def verify_changelog_version():
    """Verify changelog.md"""
    CONFIG = (STS / "MarisaContinued" / "config.json").read_text()

    assert VERSION in CONFIG
    assert VERSION in (BASE / "changelog.bbcode").read_text()
    assert VERSION in (BASE / "changelog.sts.txt").read_text()
    assert VERSION in CHANGELOG_TEXT


def run_command(command: list[str], *, cwd=Path()):
    print(" ".join(command))
    run(command, cwd=cwd, check=True)


def github(ctx: Context):
    GH = "gh release create".split()
    VERIFY = ["--verify-tag"] if ctx.verify_tag else []
    ARTIFACT = f"{str(JAR).removesuffix('/')}#MarisaContinued-{VERSION}.jar"
    # fmt: off
    COMMAND = GH + VERIFY + [
        "--title", TITLE,
        "--notes", BODY,
        f"v{VERSION}",
        ARTIFACT,
    ]
    # fmt: on
    run_command(COMMAND)


def steam(_: Context):
    COMMAND = "java -jar mod-uploader.jar upload -w MarisaContinued/".split(
        " "
    )
    run_command(COMMAND, cwd=STS)


class Command(StrEnum):
    STEAM = auto()
    GITHUB = auto()
    BOTH = auto()

    @classmethod
    def to_func(cls, command: Self) -> list[Fn[None]]:
        match command:
            case cls.STEAM:
                return [steam]
            case cls.GITHUB:
                return [github]
            case cls.BOTH:
                return [steam, github]


app = Typer()


@app.command(context_settings=dict(help_option_names=["-h", "--help"]))
def main(
    command: Command = Argument(..., help=__doc__),
    verify_tag: bool = Option(True, help="Enforce tags to be in origin"),
):
    verify_hard_links()
    verify_jar_version()
    verify_changelog_version()

    fx = Command.to_func(command)
    ctx = Context(verify_tag=verify_tag)
    runner = Runner(ctx)
    # pylint: disable-next=not-an-iterable
    names = ", ".join(f.__name__ for f in fx)
    print(f"Will run the following commands: {names}")
    wait()
    with ProcessPoolExecutor() as executor:
        result = executor.map(runner, fx)

        for r in result:
            print(r)


if __name__ == "__main__":
    app()
