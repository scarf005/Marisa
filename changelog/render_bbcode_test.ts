import { assertEquals } from "@std/assert"
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
