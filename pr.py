#!/usr/bin/env python3

import json
import re
import sys
from subprocess import run
from typing import Final, NewType, TypedDict

from flupy import flu


def get_commit_titles():
    return (
        run(
            "git log main..HEAD --pretty=format:%s".split(),
            capture_output=True,
            check=True,
        )
        .stdout.decode("utf-8")
        .splitlines()
    )


def get_title() -> str:
    match sys.argv:
        case [_, title]:
            return title
        case _:
            if len(titles := get_commit_titles()) == 1:
                return titles[0]
            result = input(
                "Enter commit title (empty to choose from existing commits): "
            )
            if (result) != "":
                return result

            for i, title in enumerate(titles):
                print(f"[{i}] {title}")
            return titles[int(input("Enter commit number: "))]


class Issue(TypedDict):
    number: int
    title: str


def open_issues() -> list[Issue]:
    args = ["gh", "issue", "list", "--state", "open", "--json", "number,title"]
    text = run(args, capture_output=True, check=True).stdout.decode("utf-8")
    return json.loads(text)


def choose_issues(issues: list[Issue]) -> int | None:
    issue_nums = flu(issues).map(lambda x: x["number"]).collect()
    for item in issues:
        number, title = item["number"], item["title"]
        print(f"[{number}] {title}")

    def invalid():
        print(
            "Invalid issue number, choose between "
            f"{', '.join(map(str, issue_nums))}"
        )

    while True:
        try:
            text = input("Enter issue number (empty to skip): ")
            if text == "":
                return None
            if (result := int(text)) in issue_nums:
                return result
            raise ValueError
        except ValueError:
            invalid()
        except KeyboardInterrupt:
            print("Aborted")
            sys.exit(0)


def get_issue_number() -> int | None:
    issues = open_issues()
    return choose_issues(issues)


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
issue: Final = get_issue_number()

BODY = f"""\
## Summary
- fixes #{issue or ""}

## Changelog

{commits}"""

print(f"will create PR with \ntitle: {title}\nbody:\n{BODY}")
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
