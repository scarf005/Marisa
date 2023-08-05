import { WalkEntry } from "https://deno.land/std@0.178.0/fs/walk.ts"
import * as log from "https://deno.land/std@0.178.0/log/mod.ts"
import { z } from "https://deno.land/x/zod@v3.20.5/mod.ts"
import { zodToJsonSchema } from "npm:zod-to-json-schema@3.21.3"
import { jsons } from "./common.ts"
import { tsFormatter } from "./dprint.ts"
import { jsonToZodCode } from "./json_to_zod.ts"

const baseCwd = Deno.cwd().endsWith("localization") ? "." : "./src/main/resources/marisa/localization"
const basePath = `${baseCwd}/schemas`
console.log(basePath)
await Deno.mkdir(basePath, { recursive: true })

function writeJsonSchema(schemaCode: string, name: string) {
  // deno-lint-ignore no-explicit-any
  const zodObject = Function("z", `return ${schemaCode}`)(z) as any
  const schema = zodToJsonSchema(zodObject)

  // @ts-ignore: $ref errornously added by zod-to-json-schema
  delete schema["$ref"]

  const file = `${name}.json`
  log.info(`write  :: ${file}`)

  return Deno.writeTextFile(
    file,
    JSON.stringify(schema, null, 2) + "\n",
  )
}
function writeZodCode(schemaCode: string, name: string) {
  const file = `${name}.ts`
  const rawCode = `
    import { z } from "https://deno.land/x/zod@v3.20.5/mod.ts"

    export const schema = ${schemaCode}
  `
  const code = tsFormatter.formatText(file, rawCode)
  log.info(`write  :: ${file}`)
  return Deno.writeTextFile(file, code)
}

async function schemaGen({ path, name }: WalkEntry) {
  log.info(`read   :: ${path}`)
  const file = await Deno.readTextFile(path)
  const json = JSON.parse(file)
  const schemaCode = jsonToZodCode(json)
  const base = `${basePath}/${name.replace(".json", "")}`

  await Promise.all([
    writeJsonSchema(schemaCode, base),
    writeZodCode(schemaCode, base),
  ])
}

async function main() {
  const eng = await jsons("ENG")
  eng.forEach(schemaGen)
}

if (import.meta.main) {
  const before = performance.now()
  await main()
  const after = performance.now()
  log.info(`Took ${Number((after - before).toPrecision(2))}ms`)
}
