import { walk } from "https://deno.land/std@0.178.0/fs/walk.ts"
import { asyncIterableToArray } from "https://deno.land/x/replicache@v10.0.0-beta.0/async-iterable-to-array.ts"
export const langCodes = ["ENG", "FRA", "JPN", "KOR", "ZHS", "ZHT"] as const
export type LangCode = typeof langCodes[number]
export const projectRoot = await new Deno.Command("git", {
  args: ["rev-parse", "--show-toplevel"],
})
  .output()
  .then((x) => new TextDecoder().decode(x.stdout).trim())

export const localization = "src/main/resources/localization"

export const localizationPath = `${projectRoot}/${localization}`

export async function jsons(name: LangCode) {
  return (await asyncIterableToArray(walk(name)))
    .filter((x) => x.isFile && x.name.endsWith(".json"))
}
