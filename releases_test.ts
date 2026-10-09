import { join } from "@std/path"
import { basePath, changelogPath, jarPath, stsPath, version } from "./releases.ts"
import { verifyHardLink } from "./link/mod.ts"
import { assertEquals, assertStringIncludes } from "@std/assert"
import JSZip from "jszip"
import { modId } from "./paths.ts"

const readZip = async (path: string) => JSZip.loadAsync(await Deno.readFile(path))

Deno.test("hardlinks are verified", async () => {
  await verifyHardLink({ quiet: true, check: true })
})

Deno.test("modjson.json has correct version", async () => {
  const zip = await readZip(jarPath)
  const modinfo = JSON.parse(await zip.file("ModTheSpire.json")!.async("text"))

  assertEquals(modinfo["version"], version)
  assertEquals(modinfo["modid"], modId)
})

Deno.test("packaged localization expands the mod namespace", async () => {
  const zip = await readZip(jarPath)
  const sourceRoot = "src/main/resources"
  const localizationRoot = `${modId}/localization`
  const kinds = ["cards", "potions", "powers", "relics", "events", "keywords"]
  let checkedFiles = 0

  for await (const language of Deno.readDir(`${sourceRoot}/${localizationRoot}`)) {
    if (!language.isDirectory || language.name === "schemas") continue

    for await (const file of Deno.readDir(`${sourceRoot}/${localizationRoot}/${language.name}`)) {
      const kind = file.name.replace(/\.json$/, "")
      if (!file.isFile || !kinds.includes(kind)) continue

      const path = `${localizationRoot}/${language.name}/${file.name}`
      const source = await Deno.readTextFile(`${sourceRoot}/${path}`)
      const packaged = await zip.file(path)!.async("text")
      const strings = JSON.parse(packaged)
      assertEquals(packaged.includes("${modId}"), false, path)
      assertEquals(strings, JSON.parse(source.replaceAll("${modId}", modId)), path)
      if (!["events", "keywords"].includes(kind)) {
        assertEquals(Object.keys(strings).every((key) => key.startsWith(`${modId}:`)), true, path)
      }
      checkedFiles++
    }
  }
  assertEquals(checkedFiles > 0, true)
})

Deno.test("changelog.md has correct version", async () => {
  const paths = [
    join(stsPath, modId, "config.json"),
    join(basePath, "changelog.bbcode"),
    join(basePath, "changelog.sts.txt"),
    changelogPath,
  ]
  const configs = await Promise.all(paths.map((x) => Deno.readTextFile(x)))

  configs.forEach((config) => assertStringIncludes(config, version))
})
