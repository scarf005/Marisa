import { assertEquals } from "@std/assert"
import { dirEntries } from "./dirEntries.ts"

Deno.test(async function testDirEntries() {
  const path = Deno.cwd().endsWith("localization") ? "." : "src/main/resources/marisa/localization/"

  const entries = (await dirEntries(`${path}/ENG`))
    .map((x) => x.name)
    .map((x) => x.replace(".json", ""))

  const expected = [
    "keywords",
    "events",
    "cards",
    "relics",
    "powers",
    "potions",
  ]
  assertEquals(entries, expected)
})
