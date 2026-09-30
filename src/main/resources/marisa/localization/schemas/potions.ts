import { z } from "$zod/mod.ts"

export const schema = z.object({
  "${modId}:ShroomBrew": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string(), z.string()]),
  }).strict(),
  "${modId}:BottledSpark": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:StarNLove": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
}).strict().required()
