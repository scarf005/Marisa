import { assertEquals } from "@std/assert"
import { getSections, parseCommits } from "./mod.ts"
import { renderMarkdown } from "./render_markdown.ts"
import { renderSections } from "./render.ts"

Deno.test("renderMarkdown() renders version, date, sections and PR references", () => {
  const sections = getSections(parseCommits([
    "feat: add a card (#12)",
    "feat: add a relic (#13)",
    "fix: correct card text (#14)",
  ]))

  assertEquals(
    renderMarkdown({ version: "v1.2.3", date: "2026-09-30", sections }),
    "# v1.2.3 (2026-09-30)\n\n## New Features\n\n- add a card (#12)\n- add a relic (#13)\n\n## Fixes\n\n- correct card text (#14)",
  )
})

Deno.test("renderMarkdown() renders a breaking change section", () => {
  const sections = getSections(parseCommits(["refactor!: remove old API (#15)"]))

  assertEquals(
    renderMarkdown({ version: "v2.0.0", date: "2026-09-30", sections }),
    "# v2.0.0 (2026-09-30)\n\n## Breaking Changes\n\n- remove old API (#15)",
  )
})

Deno.test("renderSections() skips empty sections without calling the formatter", () => {
  const formatted: string[] = []
  const sections = getSections(parseCommits(["fix: correct text (#16)"]))

  const result = renderSections({
    sections,
    fmtSection: ([name]) => {
      formatted.push(name)
      return name
    },
  })

  assertEquals(result, "Fixes")
  assertEquals(formatted, ["Fixes"])
})

Deno.test("renderSections() returns empty text for an empty changelog", () => {
  assertEquals(
    renderSections({
      sections: getSections([]),
      fmtSection: () => {
        throw new Error("Empty sections must not be formatted")
      },
    }),
    "",
  )
})
