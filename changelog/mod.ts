import $ from "@david/dax"
import { typedRegEx } from "https://deno.land/x/typed_regex@0.2.0/mod.ts"
import type { RegExCaptureResult } from "https://deno.land/x/typed_regex@0.2.0/type_parser.ts"

import { renderBBCode } from "./render_bbcode.ts"
import { getNextVersion } from "./semver.ts"
import { renderMarkdown } from "./render_markdown.ts"
import { renderStS } from "./render_sts.ts"

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
  if (commits.length === 0) {
    console.log("No new commits")
    Deno.exit(0)
  }

  const option = {
    version: getNextVersion(latestTag, commits),
    date: new Date().toISOString().split("T")[0],
    sections: getSections(commits),
  }

  const files = {
    bbcode: renderBBCode,
    md: renderMarkdown,
    "sts.txt": renderStS,
  }

  const changelogPath = `${import.meta.dirname}/../docs/changelog`

  const writes = Object.entries(files)
    .map(([ext, render]) => [ext, render(option)])
    .map(([ext, text]) => {
      if (ext === "md") console.log(text)
      return Deno.writeTextFile(`${changelogPath}/changelog.${ext}`, text)
    })
    .concat(Deno.writeTextFile(`${changelogPath}/version.txt`, option.version.replace("v", "")))

  await Promise.all(writes)

  switch (Deno.args[0]) {
    case "nextversion": {
      await $`git tag ${option.version}`
      console.log(await getTags())
      break
    }
    default: {
      console.log("Usage: changelog.ts [nextversion]")
    }
  }
}
