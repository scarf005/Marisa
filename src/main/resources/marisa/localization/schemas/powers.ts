import { z } from "$zod/mod.ts"

export const schema = z.object({
  "${modId}:DarkMatterPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:ExtraDraw": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string(), z.string()]),
  }).strict(),
  "${modId}:EventHorizonPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string(), z.string(), z.string()]),
  }).strict(),
  "${modId}:IllusionStarPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:MilliPulsaPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:SatellIllusPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:TempStrength": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:TempStrengthLoss": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:ExtraEnergyPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:ChargeUpPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string(), z.string(), z.string()]),
  }).strict(),
  "${modId}:PolarisUniquePower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:OrrerysSunPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:Charged": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:BlazeAwayPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:MPPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:EnergyFlowPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:PulseMagicPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:SingularityPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:WitchOfGreedGold": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:WitchOfGreedPotion": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:OneTimeOffPlusPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:OneTimeOffPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:SuperNovaPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string(), z.string()]),
  }).strict(),
  "${modId}:CasketOfStarPlusPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:CasketOfStarPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:GalacticHaloPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:ManaRampagePower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:PropBagPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:UltraShortWavePower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:DarkSparkPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:MagicChantPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:SatelIllusPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:GrandCrossPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:Diaspora": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:TalismanPower": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:Wraith": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:Nebula": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:LimboContact": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
  "${modId}:InfernoClaw": z.object({
    "NAME": z.string(),
    "DESCRIPTIONS": z.tuple([z.string()]),
  }).strict(),
}).strict().required()
