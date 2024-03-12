import { assertEquals } from "$std/assert/assert_equals.ts"
import { outdent } from "$dax/src/deps.ts"
import { getSections, parseCommits } from "./mod.ts"
import { renderBBCode } from "./render_bbcode.ts"

export const exampleCommits = parseCommits([
  "feat: asdf (#1234)",
  "feat!: test (#123)",
  "fix(L10n): `language` stuff (#415)",
])
const exampleSections = getSections(exampleCommits)

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

Deno.test("renderBBCode() outputs identical text", () =>
  assertEquals(
    renderBBCode({ version: "v1.0.0", date: "2024-03-12", sections: exampleSections }),
    outdent`
      [h1]v1.0.0 (2024-03-12)[/h1]

      [h2]Breaking Changes[/h2]
      [list]
          [*] test ([url=https://github.com/scarf005/marisa/pull/123]#123[/url])
      [/list]

      [h2]New Features[/h2]
      [list]
          [*] asdf ([url=https://github.com/scarf005/marisa/pull/1234]#1234[/url])
          [*] test ([url=https://github.com/scarf005/marisa/pull/123]#123[/url])
      [/list]

      [h2]Fixes[/h2]
      [list]
          [*] [i]language[/i] stuff ([url=https://github.com/scarf005/marisa/pull/415]#415[/url])
      [/list]
  `,
  ))
