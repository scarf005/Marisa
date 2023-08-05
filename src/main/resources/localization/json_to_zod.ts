import { z } from "https://deno.land/x/zod@v3.20.5/mod.ts"

type parseFn = (obj: unknown, seen: object[]) => string

/**
 * converts an json object into zod schema.
 * tailored for validating Slay the Spire localization files.
 *
 *  original @link https://github.com/rsinohara/json-to-zod
 *
 *  differences to original:
 *  - tuple by default
 *  - makes all field required
 *  - forbidden to have additional fields
 *  - return zod object code instead full program code
 */
export function jsonToZodCode(obj: unknown) {
  function parse(
    obj: unknown,
    seen: object[],
    { asTuple } = { asTuple: true },
  ): string {
    switch (typeof obj) {
      case "string":
        return "z.string()"
      case "number":
        return "z.number()"
      case "bigint":
        return "z.number().int()"
      case "boolean":
        return "z.boolean()"
      case "object":
        if (obj === null) {
          return "z.null()"
        }
        if (seen.find((seenObj) => Object.is(seenObj, obj))) {
          throw "Circular objects are not supported"
        }
        seen.push(obj)
        if (Array.isArray(obj)) {
          if (asTuple) {
            const options = obj
              .map((elem) => parse(elem, seen))
            if (options.length >= 0) {
              return `z.tuple([${options}])`
            } else {
              return `z.array(z.unknown())`
            }
          } else {
            const options = obj
              .map((obj) => parse(obj, seen))
              .reduce(
                (acc: string[], curr: string) => acc.includes(curr) ? acc : [...acc, curr],
                [],
              )
            if (options.length === 1) {
              return `z.array(${options[0]})`
            } else if (options.length > 1) {
              return `z.array(z.union([${options}]))`
            } else {
              return `z.array(z.unknown())`
            }
          }
        }
        return `z.object({${
          Object.entries(obj).map(
            ([k, v]) => `"${k}":${parse(v, seen, { asTuple: k !== "NAMES" })}`,
          )
        }}).strict()`
      case "undefined":
        return "z.undefined()"
      case "function":
        return "z.function()"
      case "symbol":
      default:
        return "z.unknown()"
    }
  }
  const result = parse(obj, [])
  return `${result}.required()`
}

/** unsafe function that converts an json object into zod schema */
export function jsonToZod(obj: unknown) {
  console.log(`return ${jsonToZodCode(obj)}`)
  const fn = Function("z", `return ${jsonToZodCode(obj)}`)

  // deno-lint-ignore no-explicit-any
  return fn(z) as z.ZodObject<any>
}
