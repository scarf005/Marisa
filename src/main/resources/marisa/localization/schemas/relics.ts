import { z } from "https://deno.land/x/zod@v3.20.5/mod.ts"

export const schema = z.object({
  "MiniHakkero": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "BewitchedHakkero": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "MagicArmor": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "MagicBroom": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "AmpWand": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "RampagingMagicTools": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "ExperimentalFamiliar": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "BreadOfAWashokuLover": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "SimpleLauncher": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "HandmadeGrimoire": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "ShroomBag": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "Cape": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "SproutingBranch": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "AmplifyWand": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "CatCart": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "BigShroomBag": z.object({
    "NAME": z.string(),
    "FLAVOR": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
}).strict().required()
