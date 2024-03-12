#!/usr/bin/env -S deno run --allow-read --allow-write --allow-env

import { join } from "$std/path/mod.ts"
import { Command } from "$cliffy/command/mod.ts"

import { image, jar, mod } from "../paths.ts"
import { noop } from "./noop.ts"
import { createHardLink, forceCreateLinkTo, hardlinkError } from "./hardlink.ts"

export const verifyHardLink = async (
  { quiet, check }: { quiet?: true | undefined; check?: true | undefined },
) => {
  const log = quiet ? noop : console.log
  const action = check ? hardlinkError : createHardLink
  const linkTo = forceCreateLinkTo(log)(action)

  await Promise.all([
    linkTo(jar)(join(mod, "content")),
    linkTo(image)(mod),
  ])
}

const main = () =>
  new Command()
    .description("Hard link mod jar and image to steam's slay the spire folder")
    .option("-q, --quiet", "Do not output to console. Used in gradle task.")
    .option("-c, --check", "Exit with code 1 on failure instead.")
    .action(verifyHardLink)

if (import.meta.main) {
  await main().parse(Deno.args)
}
