import { z } from "$zod/mod.ts"

export const schema = z.object({
  "ShroomBrew": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string(), z.string()]),
  }).strict(),
  "BottledSpark": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "StarNLove": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
}).strict().required()
