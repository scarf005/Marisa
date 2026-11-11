import { assertEquals } from "@std/assert"
import { getSections, parseCommits } from "./mod.ts"
import { outdent } from "./vendor/outdent.ts"
import { renderBBCode } from "./render_bbcode.ts"
import { exampleSections } from "./changelog_test.ts"

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

Deno.test("renderBBCode() renders commits pushed without a PR", () =>
  assertEquals(
    renderBBCode({
      version: "v1.0.1",
      date: "2026-11-11",
      sections: getSections(parseCommits(["fix: upgrade `Dark Spark` only once"])),
    }),
    "[h1]v1.0.1 (2026-11-11)[/h1]\n\n[h2]Fixes[/h2]\n[list]\n    [*] upgrade [i]Dark Spark[/i] only once\n[/list]",
  ))
