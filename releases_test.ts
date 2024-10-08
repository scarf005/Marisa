import { join } from "@std/path"
import { basePath, changelogPath, jarPath, stsPath, version } from "./releases.ts"
import { verifyHardLink } from "./link/mod.ts"
import { assertEquals, assertStringIncludes } from "@std/assert"
import { readZip } from "https://deno.land/x/jszip@0.11.0/mod.ts"

Deno.test("hardlinks are verified", async () => {
  await verifyHardLink({ quiet: true, check: true })
})

Deno.test("modjson.json has correct version", async () => {
  const zip = await readZip(jarPath)
  const modinfo = JSON.parse(await zip.file("ModTheSpire.json").async("text"))

  assertEquals(modinfo["version"], version)
})

Deno.test("changelog.md has correct version", async () => {
  const paths = [
    join(stsPath, "MarisaContinued", "config.json"),
    join(basePath, "changelog.bbcode"),
    join(basePath, "changelog.sts.txt"),
    changelogPath,
  ]
  const configs = await Promise.all(paths.map((x) => Deno.readTextFile(x)))

  configs.forEach((config) => assertStringIncludes(config, version))
})
