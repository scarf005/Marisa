import { assertEquals } from "https://deno.land/std@0.178.0/testing/asserts.ts"
import { dirEntries } from "./dirEntries.ts"

Deno.test(async function testDirEntries() {
  const path = Deno.cwd().endsWith("localization") ? "." : "src/main/resources/localization"

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
