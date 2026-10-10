import { assertEquals } from "@std/assert"
import { walk } from "@std/fs"

Deno.test(async function everyColorMarkerNamesAColorTheGameKnows() {
  const path = Deno.cwd().endsWith("localization") ? "." : "src/main/resources/marisa/localization/"
  const files = await Array.fromAsync(
    walk(path, { exts: [".json"], skip: [/schemas/] }),
  )
  // FontHelper.identifyColor knows #r, #g, #b, #y and #p; other markers are shown as written.
  const unknown = await Promise.all(
    files.map(async ({ path }) =>
      (await Deno.readTextFile(path)).split("\n")
        .flatMap((line, i) => /#([^rgbyp]|$)/.test(line) ? [`${path}:${i + 1}`] : [])
    ),
  )
  assertEquals(unknown.flat(), [])
})
