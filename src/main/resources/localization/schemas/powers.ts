import { z } from "https://deno.land/x/zod@v3.20.5/mod.ts"

export const schema = z.object({
  "DarkMatterPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "ExtraDraw": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string(), z.string()]),
  }).strict(),
  "EventHorizonPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string(), z.string(), z.string()]),
  }).strict(),
  "IllusionStarPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "MilliPulsaPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "SatellIllusPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "TempStrength": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "TempStrengthLoss": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "ExtraEnergyPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "ChargeUpPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string(), z.string(), z.string()]),
  }).strict(),
  "PolarisUniquePower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "OrrerysSunPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "Charged": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "BlazeAwayPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "MPPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "EnergyFlowPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "PulseMagicPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "SingularityPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "WitchOfGreedGold": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "WitchOfGreedPotion": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "OneTimeOffPlusPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "OneTimeOffPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "SuperNovaPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string(), z.string()]),
  }).strict(),
  "CasketOfStarPlusPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "CasketOfStarPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "GalacticHaloPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "ManaRampagePower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "PropBagPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "UltraShortWavePower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "DarkSparkPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "MagicChantPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "SatelIllusPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "GrandCrossPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "Diaspora": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "TalismanPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "Wraith": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "Nebula": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "LimboContact": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "InfernoClaw": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "WWWWWWWWWWWWW": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
}).strict().required()
