import { assertEquals } from "$std/assert/assert_equals.ts"
import { getSections, parseCommits } from "./mod.ts"

export const exampleCommits = parseCommits([
  "feat: asdf (#1234)",
  "feat!: test (#123)",
  "fix(L10n): `language` stuff (#415)",
])
export const exampleSections = getSections(exampleCommits)

Deno.test("parseCommits() correctly parse commits", () => {
  assertEquals(exampleCommits, [
    {
      type: "feat",
      scopes: undefined,
      breaking: undefined,
      subject: "asdf",
      pr: "1234",
    },
    {
      type: "feat",
      scopes: undefined,
      breaking: "!",
      subject: "test",
      pr: "123",
    },
    {
      type: "fix",
      scopes: "L10n",
      breaking: undefined,
      subject: "`language` stuff",
      pr: "415",
    },
  ])
})
