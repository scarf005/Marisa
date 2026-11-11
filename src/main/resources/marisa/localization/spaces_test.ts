import { assertEquals } from "@std/assert"
import { walk } from "@std/fs"

Deno.test(async function cjkStringsHaveNoDoubleSpaces() {
  const path = Deno.cwd().endsWith("localization") ? "." : "src/main/resources/marisa/localization/"
  const files = await Promise.all(
    ["JPN", "KOR", "ZHS", "ZHT"].map((lang) =>
      Array.fromAsync(walk(`${path}/${lang}`, { exts: [".json"] }))
    ),
  )
  // Indentation is not part of a string; spaces inside a value must be single.
  const found = await Promise.all(
    files.flat().map(async ({ path }) =>
      (await Deno.readTextFile(path)).split("\n")
        .flatMap((line, i) => /\S {2,}/.test(line) ? [`${path}:${i + 1}`] : [])
    ),
  )
  assertEquals(found.flat(), [])
})
