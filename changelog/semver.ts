import type { SemVer } from "$std/semver/types.ts"
import type { Commit } from "./mod.ts"

import { format, increment, parse } from "$std/semver/mod.ts"

const increaseBy = (commit: Commit): "major" | "minor" | "patch" =>
  commit.breaking ? "major" : commit.type === "feat" ? "minor" : "patch"

export const increaseVersion = (begin: SemVer, commits: Commit[]): SemVer =>
  commits.reduce(({ ver, count }, commit) => {
    const by = increaseBy(commit)
    count[by]++
    return (count[by] > 1) ? { ver, count } : { ver: increment(ver, increaseBy(commit)), count }
  }, { ver: begin, count: { major: 0, minor: 0, patch: 0 } }).ver

export const getNextVersion = (latest: string, commits: Commit[]): string =>
  "v" + format(increaseVersion(parse(latest), commits))
