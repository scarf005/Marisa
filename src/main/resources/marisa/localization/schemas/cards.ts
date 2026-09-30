import { z } from "$zod/mod.ts"

export const schema = z.object({
  "${modId}:Strike_MRS": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:Defend_MRS": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:AsteroidBelt": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:BigCrunch": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:BlazingStar": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:DarkSpark": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:DeepEcoloBomb": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:DoubleSpark": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:EarthLightRay": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:EscapeVelocity": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:FinalSpark": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:GrandCross": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:GravityBeat": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:IllusionStar": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:MachineGunSpark": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:MagicAbsorber": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:MasterSpark": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:MeteoricShower": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:MilkyWay": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:NonDirectionalLaser": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:Occultation": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:OortCloud": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:PolarisUnique": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:SatelliteIllusion": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:ShootTheMoon": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:StarDustReverie": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:UltraShortWave": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:MillisecondPulsars": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:Spark": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "${modId}:UpSweep": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:GuidingStar": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:OrrerysSun": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:ChargingUp": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:LuminesStrike": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:OpenUniverse": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:BlazeAway": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string(), z.string()]),
  }).strict(),
  "${modId}:MaximisePower": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:StarlightTyphoon": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:StarlightTyphoon_D": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:SuperPerseids": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:MysteriousBeam": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:ShootingEcho": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:EnergyFlow": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:PowerUp": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:WitchLeyline": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:JA": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:AbsoluteMagnitude": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:DragonMeteor": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:EventHorizon": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:PulseMagic": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:DC": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "${modId}:Singularity": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:SporeBomb": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:FluorensentBeam": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:WitchOfGreed": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:TreasureHunter": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:Robbery": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:6A": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "${modId}:CircumpolarStar": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:RefractionSpark": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:MagicChant": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:UnstableBomb": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:SuperNova": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:OneTimeOff": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:DarkMatter": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:GasGiant": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:CasketOfStar": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:ChargeUpSpray": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:EnergyRecoil": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:GalacticHalo": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:ManaConvection": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:ManaRampage": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:StarBarrage": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:AFriendsGift": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:BinaryStars": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:CollectingQuirk": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:FungusSplash": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:PropBag": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:BlackFlareStar": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:WhiteDwarf": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
    "UPGRADE_DESCRIPTION": z.string(),
    "EXTENDED_DESCRIPTION": z.tuple([z.string(), z.string()]),
  }).strict(),
  "${modId}:FairyDestructionRay": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:Orbital": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:ExplosiveMarionette": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:OpticalCamouflage": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:FiveColoredTalisman": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:AlicesGift": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:Exhaustion_MRS": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:UltimateShortwave": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:NebulaRing": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:SprinkleStarSeal": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
  "${modId}:Wraith": z.object({ "NAME": z.string(), "DESCRIPTION": z.string() })
    .strict(),
  "${modId}:Acceleration": z.object({
    "NAME": z.string(),
    "DESCRIPTION": z.string(),
  }).strict(),
}).strict().required()
