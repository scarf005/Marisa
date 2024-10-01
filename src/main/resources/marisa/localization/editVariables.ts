import { brightGreen, brightRed, brightYellow } from "@std/fmt/colors"
import { Command } from "$cliffy/command/mod.ts"
import type { Entry } from "npm:type-fest"

import cardsEng from "./ENG/cards.json" with { type: "json" }
import cardsFra from "./FRA/cards.json" with { type: "json" }
import cardsJpn from "./JPN/cards.json" with { type: "json" }
import cardsKor from "./KOR/cards.json" with { type: "json" }
import cardsZhs from "./ZHS/cards.json" with { type: "json" }
import cardsZht from "./ZHT/cards.json" with { type: "json" }

const cardsJson = [
  { path: "./ENG/cards.json", cards: cardsEng },
  { path: "./FRA/cards.json", cards: cardsFra },
  { path: "./JPN/cards.json", cards: cardsJpn },
  { path: "./KOR/cards.json", cards: cardsKor },
  { path: "./ZHS/cards.json", cards: cardsZhs },
  { path: "./ZHT/cards.json", cards: cardsZht },
]

type Card = {
  NAME: string
  DESCRIPTION: string
  UPGRADE_DESCRIPTION?: string
  EXTENDED_DESCRIPTION?: string[]
}
type Cards = Record<string, Card>

const replaceVar = (from: string, to: string) => (text: string) => {
  const regex = new RegExp(`!${from}!`, "g")
  const replaced = text.replaceAll(regex, `!${to}!`)

  if (replaced !== text) {
    console.log(
      `${brightRed(from)} -> ${brightGreen(to)} :: ${brightYellow(replaced)}`,
    )
  }
  return replaced
}

const cardReplaceVar = (
  cards: Cards,
  id: string,
  fn: (text: string) => string,
) => {
  const entries = Object.entries(cards).map(([key, value]) => {
    if (key !== id) return [key, value]

    value.NAME = fn(value.NAME)
    value.DESCRIPTION = fn(value.DESCRIPTION)
    if ("UPGRADE_DESCRIPTION" in value && value.UPGRADE_DESCRIPTION) {
      value.UPGRADE_DESCRIPTION = fn(value.UPGRADE_DESCRIPTION)
    }
    if ("EXTENDED_DESCRIPTION" in value && value.EXTENDED_DESCRIPTION) {
      value.EXTENDED_DESCRIPTION = value.EXTENDED_DESCRIPTION.map(fn)
    }
    return [key, value]
  })

  return Object.fromEntries(entries) as Entry<Cards>
}

const isBaseVar = (to: string) => ["D", "B", "M"].includes(to)

await new Command()
  .name("edit dynamic variables")
  .description("replace dynamic variables in localization files")
  .arguments("<id>")
  .option("-f, --from <from>", "var to be replaced", { required: true })
  .option("-t, --to <to>", "var to replace with", { required: true })
  .option("--no-namespace", "do not append namespace 'Marisa' to var")
  .action(async ({ from, to, namespace }, id) => {
    if (!Object.keys(cardsEng).includes(id)) {
      console.error(`Card id ${id} not found`)
      Deno.exit(1)
    }

    const replaceFn = replaceVar(
      from,
      !isBaseVar(to) && namespace ? `Marisa:${to}` : to,
    )

    const cards = cardsJson
      .map(({ path, cards }) => {
        const newCards = cardReplaceVar(cards, id, replaceFn)
        const text = JSON.stringify(newCards, null, 2)
        return Deno.writeTextFile(path, text)
      })

    await Promise.all(cards)
  })
  .parse(Deno.args)
