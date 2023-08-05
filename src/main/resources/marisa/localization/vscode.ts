import { langCodes, localization, projectRoot } from "./common.ts"
import { dirEntries } from "./dirEntries.ts"

const names = (await dirEntries("ENG")).map((x) => x.name)

function schemaSettingsGen(name: string) {
  const fileMatch = langCodes.map((lang) => `${localization}/${lang}/${name}`)
  return {
    fileMatch,
    url: `./${localization}/schemas/${name}`,
  }
}

if (import.meta.main) {
  const settingsFile = `${projectRoot}/.vscode/settings.json`
  const settings = JSON.parse(await Deno.readTextFile(settingsFile))
  const schemas = names.map(schemaSettingsGen)
  const newSettings = { ...settings, "json.schemas": schemas }

  await Deno.writeTextFile(settingsFile, JSON.stringify(newSettings, null, 2) + "\n")
}
