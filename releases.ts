import { join } from "$std/path/join.ts"
import $ from "$dax/mod.ts"
import { steam as SteamPath } from "./scripts/paths.ts"

export const basePath = "docs/changelog"

export const changelogPath = join(basePath, "changelog.md")
export const version = (await Deno.readTextFile(join(basePath, "version.txt"))).trim()

const changelogText = await Deno.readTextFile(changelogPath)
const title = changelogText.split("\n")[0].replace("# ", "")
const body = changelogText.split("\n").slice(1).join("\n")

export const jarPath = "build/libs/MarisaContinued.jar"
export const stsPath = join(SteamPath, "SlayTheSpire")

const github = async () => {
  const artifact = `${jarPath}#MarisaContinued-${version}.jar`
  const command =
    $`gh release create --verify-tag --title ${title} --notes ${body} v${version} ${artifact}`
  await command.printCommand()
  await command.text()
}

if (import.meta.main) {
  switch (Deno.args[0]) {
    case "github": {
      await github()
      break
    }
    case "steam": {
      await $`java -jar mod-uploader.jar upload -w MarisaContinued/`.cwd(stsPath)
    }
  }
}
