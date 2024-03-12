import $ from "$dax/mod.ts"
import { typedRegEx } from "https://deno.land/x/typed_regex@0.2.0/mod.ts"
import type { RegExCaptureResult } from "https://deno.land/x/typed_regex@0.2.0/type_parser.ts"

import { renderBBCode } from "./render_bbcode.ts"

export type Commit = RegExCaptureResult<typeof re>
export type Sections = Record<string, Commit[]>

export const getTags = () => $`git tag --sort=-v:refname`.lines()

type CommitRange = { from: string; to: string }
export const getCommits = ({ from, to }: CommitRange) =>
  $`git log ${from}..${to} --pretty=format:"%s"`.lines()

const re =
  "^(?<type>\\w+)(\\((?<scopes>.*)?\\))?(?<breaking>!)?:\\s*(?<subject>.*?)\\s*\\(#(?<pr>\\d+)\\)$"

const commitParser = typedRegEx(re)

export const parseCommits = (xs: string[]) =>
  xs
    .map(commitParser.captures)
    .filter((x): x is Commit => x !== undefined)
    .filter((x) => x.breaking || ["fix", "feat"].includes(x.type))

export const getSections = (commits: Commit[]): Sections => ({
  "Breaking Changes": commits.filter((x) => x.breaking),
  "New Features": commits.filter((x) => x.type === "feat"),
  Fixes: commits.filter((x) => x.type === "fix"),
})

if (import.meta.main) {
  const [latestTag] = await getTags()
  const commits = await getCommits({ from: latestTag, to: "main" }).then(parseCommits)

  const sections: Sections = getSections(commits)

  console.log(latestTag)
  console.log(sections)
  console.log(commits)
  console.log(
    renderBBCode({
      version: "v1.0.0",
      date: new Date().toISOString().split("T")[0],
      sections,
    }),
  )
}
