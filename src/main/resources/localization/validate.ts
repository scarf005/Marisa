import { walk, WalkEntry } from "https://deno.land/std@0.178.0/fs/walk.ts"
import { asyncIterableToArray } from "https://deno.land/x/replicache@v10.0.0-beta.0/async-iterable-to-array.ts"
import { jsons, LangCode, langCodes } from "./common.ts"
import { parse } from "https://deno.land/std@0.178.0/path/mod.ts"
import * as log from "https://deno.land/std@0.178.0/log/mod.ts"
import {
  Command,
  EnumType,
} from "https://deno.land/x/cliffy@v0.25.7/command/mod.ts"
import { ZodIssue } from "https://deno.land/x/zod@v3.20.5/mod.ts"

const schemasPromise = (await asyncIterableToArray(walk("schemas")))
  .filter((x) => x.isFile && x.name.endsWith(".ts"))
  .map(async (x) => ({ ...x, schema: (await import(`./${x.path}`)).schema }))

const schemas = await Promise.all(schemasPromise)

type JsonEntry = WalkEntry & { json: unknown }

async function loadJson(entry: WalkEntry): Promise<JsonEntry> {
  const text = await Deno.readTextFile(entry.path)
  try {
    const json = JSON.parse(text)
    return { ...entry, json }
  } catch (e) {
    log.error(`when parsing ${entry.path}: ${e.message}`)
    throw e
  }
}

async function loadJsons(langCode: LangCode): Promise<JsonEntry[]> {
  const files = await jsons(langCode)

  return await Promise.all(files.map(loadJson))
}

export function validateJsons(jsons: JsonEntry[]) {
  jsons
    .filter(({ name }) => !name.endsWith("keywords.json"))
    .forEach(({ path, json }) => {
      log.info(path)
      const name = parse(path).name
      const schema = schemas.find((x) => parse(x.name).name === name)
      if (!schema) {
        console.log("no schema found for", name)
        return
      }
      try {
        schema.schema.parse(json)
      } catch (e) {
        const issues = e.issues as ZodIssue[]

        console.log(issues.map(({ code: _code, ...args }) => ({ ...args })))
      }
    })
}
// console.log(langCodes)
const langType = new EnumType(langCodes)

if (import.meta.main) {
  const { args } = await new Command()
    .name("validate localization")
    .description(
      "Validate localization files (run all when empty):\n\t" +
        `${langCodes.map((x) => `${x} `).join("")}`,
    )
    .type("langType", langType)
    .arguments("[lang...:langType]")
    .parse(Deno.args)

  const langs = args.length === 0 ? langCodes : args
  console.log({ langs })

  const values = await (Promise.all(langs.map(loadJsons)))

  values.forEach(validateJsons)
}
