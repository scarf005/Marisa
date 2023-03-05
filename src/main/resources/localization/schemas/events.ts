import { z } from "https://deno.land/x/zod@v3.20.5/mod.ts"

export const schema = z.object({
  "Mushrooms_MRS": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string(), z.string()]),
    "OPTIONS": z.tuple([
      z.string(),
      z.string(),
      z.string(),
      z.string(),
      z.string(),
    ]),
  }).strict(),
  "OrinTheCat": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([
      z.string(),
      z.string(),
      z.string(),
      z.string(),
      z.string(),
    ]),
    "OPTIONS": z.tuple([
      z.string(),
      z.string(),
      z.string(),
      z.string(),
      z.string(),
    ]),
  }).strict(),
}).strict().required()
