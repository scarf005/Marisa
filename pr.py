import re
import sys
from subprocess import run
from typing import Final, NewType


def get_title() -> str:
    match sys.argv:
        case [_, title]:
            return title
        case _:
            return input("Enter commit title: ")


commit_regex = re.compile(r"(?P<type>.+): (?P<subject>.*)")
CommitTitle = NewType("CommitTitle", str)


def parse_conventional_title(title: str) -> CommitTitle:
    if not commit_regex.match(title):
        raise ValueError(f"Invalid commit title {title}")

    return CommitTitle(title)


title: Final = parse_conventional_title(get_title())
commits: Final = run(
    [
        "git",
        "log",
        "--reverse",
        "--pretty=format:#### %s (%h)%n%b",
        "main..HEAD",
    ],
    capture_output=True,
    check=True,
).stdout.decode("utf-8")

BODY = f"""\
## Summary

{commits}"""

print("will create PR with following body:\n", BODY)
try:
    input("Press any key to continue...")
except KeyboardInterrupt:
    print("Aborted")
    sys.exit(0)

run(["git", "push"], check=True)
run(
    ["gh", "pr", "create", "--title", title, "--body", BODY],
    check=True,
)
