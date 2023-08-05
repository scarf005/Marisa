import { assert, assertThrows } from "https://deno.land/std@0.178.0/testing/asserts.ts"
import { z } from "https://deno.land/x/zod@v3.20.5/mod.ts"

import { jsonToZod } from "./json_to_zod.ts"

const cardEng = {
  "GravityBeat": {
    "NAME": "Gravity Beat",
    "DESCRIPTION": "Deal !D! damage and draw a card for every !B! cards in your master deck.",
    "EXTENDED_DESCRIPTION": [
      " NL (",
      " time(s)).",
    ],
  },
}

const expected = z.object({
  "GravityBeat": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([
      z.string(),
      z.string(),
    ]),
  }),
})

const schema = jsonToZod(cardEng)

Deno.test(function validateSchemaTest() {
  const cardsJpn = {
    "GravityBeat": {
      "NAME": "グラビティビート",
      "DESCRIPTION": "敵全体に !D! ダメージ と 脱力 !M! を与える。",
    },
  }

  assertThrows(() => expected.parse(cardsJpn))
  assertThrows(() => schema.parse(cardsJpn))
})

Deno.test(function jsonToZodTest() {
  const extended = {
    "GravityBeat": {
      "NAME": "グラビティビート",
      "DESCRIPTION": "敵全体に !D! ダメージ と 脱力 !M! を与える。",
      "EXTENDED_DESCRIPTION": [
        " NL (",
        " time(s)).",
      ],
    },
  }

  assert(expected.parse(extended) !== undefined)
  assert(schema.parse(extended) !== undefined)
})
