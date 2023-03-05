import { z } from "https://deno.land/x/zod@v3.20.5/mod.ts"

export const schema = z.object({
  "Strike_MRS": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "Defend_MRS": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "AsteroidBelt": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "BigCrunch": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "BlazingStar": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "DarkSpark": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "DeepEcoloBomb": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "DoubleSpark": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "EarthLightRay": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "EscapeVelocity": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "FinalSpark": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "GrandCross": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "GravityBeat": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "IllusionStar": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "MachineGunSpark": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "MagicAbsorber": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "MasterSpark": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "MeteoricShower": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "MilkyWay": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "NonDirectionalLaser": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "Occultation": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "OortCloud": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "PolarisUnique": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "SatelliteIllusion": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "ShootTheMoon": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "StarDustReverie": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "UltraShortWave": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "MillisecondPulsars": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "Spark": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() }).strict(),
  "UpSweep": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "GuidingStar": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "OrrerysSun": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "ChargingUp": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "LuminesStrike": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "OpenUniverse": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "BlazeAway": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string(), z.string()]),
  }).strict(),
  "MaximisePower": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "StarlightTyphoon": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "StarlightTyphoon_D": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "SuperPerseids": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "MysteriousBeam": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "ShootingEcho": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "EnergyFlow": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "PowerUp": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "WitchLeyline": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "JA": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "AbsoluteMagnitude": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "DragonMeteor": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "EventHorizon": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "PulseMagic": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "DC": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() }).strict(),
  "Singularity": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "SporeBomb": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "FluorensentBeam": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "WitchOfGreed": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "TreasureHunter": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "Robbery": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "6A": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() }).strict(),
  "CircumpolarStar": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "RefractionSpark": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "MagicChant": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "UnstableBomb": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "SuperNova": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "OneTimeOff": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "DarkMatter": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "GasGiant": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "CasketOfStar": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "ChargeUpSpray": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "EnergyRecoil": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "GalacticHalo": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "ManaConvection": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "ManaRampage": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "StarBarrage": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "AFriendsGift": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "BinaryStars": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "CollectingQuirk": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "FungusSplash": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "PropBag": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "BlackFlareStar": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "WhiteDwarf": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "FairyDestructionRay": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "Orbital": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "SillyJoke": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "ExplosiveMarionette": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "OpticalCamouflage": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "FiveColoredTalisman": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "AlicesGift": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "Exhaustion_MRS": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "UltimateShortwave": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "NebulaRing": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "SprinkleStarSeal": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "Wraith": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "Acceleration": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
}).strict().required()
