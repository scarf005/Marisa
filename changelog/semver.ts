import type { SemVer } from "@std/semver"
import type { Commit } from "./mod.ts"

import { format, increment, parse } from "@std/semver"

const increaseBy = (commit: Commit): "major" | "minor" | "patch" =>
  commit.breaking ? "major" : commit.type === "feat" ? "minor" : "patch"

const levels = ["patch", "minor", "major"] as const

export const increaseVersion = (begin: SemVer, commits: Commit[]): SemVer =>
  increment(begin, levels[Math.max(...commits.map((x) => levels.indexOf(increaseBy(x))))])

export const getNextVersion = (latest: string, commits: Commit[]): string =>
  "v" + format(increaseVersion(parse(latest), commits))
