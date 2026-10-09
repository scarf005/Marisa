import { z } from "$zod/mod.ts"

export const schema = z.object({
  "${modId}:MiniHakkero": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:BewitchedHakkero": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:MagicArmor": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:MagicBroom": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:AmpWand": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:RampagingMagicTools": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:ExperimentalFamiliar": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:BreadOfAWashokuLover": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:SimpleLauncher": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:HandmadeGrimoire": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:ShroomBag": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:Cape": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:SproutingBranch": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:AmplifyWand": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:CatCart": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:BigShroomBag": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
}).strict().required()
