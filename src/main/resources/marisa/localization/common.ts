import { walk } from "@std/fs"

export const langCodes = ["ENG", "FRA", "JPN", "KOR", "ZHS", "ZHT", "SPA"] as const
export type LangCode = typeof langCodes[number]
export const projectRoot = await new Deno.Command("git", {
  args: ["rev-parse", "--show-toplevel"],
})
  .output()
  .then((x) => new TextDecoder().decode(x.stdout).trim())

export const localization = "src/main/resources/marisa/localization"

export const localizationPath = `${projectRoot}/${localization}`

export const jsons = async (name: LangCode) =>
  (await Array.fromAsync(walk(name)))
    .filter((x) => x.isFile && x.name.endsWith(".json"))
