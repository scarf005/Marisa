#!/usr/bin/env -S deno run --allow-read --allow-write --allow-env

import { join, resolve } from "https://deno.land/std@0.177.0/path/mod.ts"
import {
  brightGreen,
  brightRed,
  brightYellow,
  magenta,
} from "https://deno.land/std@0.177.0/fmt/colors.ts"

import promiseObject from "https://deno.land/x/promise_object@v0.10.0/index.ts"
import * as TE from "https://deno.land/x/fun@v2.0.0-alpha.10/async_either.ts"
import { c, p } from "https://deno.land/x/copb@v1.0.1/mod.ts"
import { Command } from "https://deno.land/x/cliffy@v0.25.7/command/mod.ts"

import { outdent } from "npm:outdent"
import { match, P } from "npm:ts-pattern"

const jar = join("build", "libs", "MarisaContinued.jar")
const image = join("docs", "thumbnail", "image.jpg")
const home = Deno.env.get("HOME")!
const steam = join(home, ".steam", "steam", "steamapps", "common")
const mod = join(steam, "SlayTheSpire", "MarisaContinued")

type Fmt = Omit<LinkMeta, "isHardLinked">

const fmtDent = outdent({ trimTrailingNewline: false })
const fmtOk = ({ from, fromIno, target, targetIno }: Fmt) =>
  fmtDent`
      ${brightGreen(`${fromIno}`)} :: ${brightGreen(from)}
      ${brightGreen(`${targetIno}`)} :: ${brightGreen(target)}
    `

const fmtErr = ({ from, fromIno, target, targetIno }: Fmt) =>
  fmtDent`
      ${brightGreen(`${fromIno}`)} :: ${brightYellow(from)}
      ${brightRed(`${targetIno}`)} :: ${brightYellow(target)}
    `

const fmt = (log: Logger) => ({ isHardLinked, ...args }: LinkMeta) =>
  log((isHardLinked ? fmtOk : fmtErr)(args))

const lstat = TE.tryCatch(Deno.lstat, (e) => (e as Error).message)
const extractInode = TE.match(() => -1, (x: Deno.FileInfo) => x.ino!)

/** Get inode from given filePath. */
const inode = c(p(lstat)(extractInode))

type Logger = (...x: string[]) => void

type LinkMeta = {
  from: string
  target: string
  fromIno: number
  targetIno: number
  isHardLinked: boolean
}

const hardLinkMeta =
  (from: string) => async (to: string): Promise<LinkMeta> => {
    const target = join(to, from.split("/").pop()!)
    const { fromIno, targetIno } = await promiseObject({
      fromIno: inode(from)(),
      targetIno: inode(target)(),
    })
    const isHardLinked = fromIno === targetIno

    return { from, target, fromIno, targetIno, isHardLinked }
  }

type LinkAction = (l: Logger) => (f: string) => (t: string) => Promise<void>

const reportHardLinkError: LinkAction = (log) => () => () => {
  log(brightRed("paths are not hard linked."))
  Deno.exit(1)
}

const createHardLink: LinkAction = (log) => (from) => async (target) => {
  try {
    await Deno.remove(target)
    // deno-lint-ignore no-empty
  } catch {}
  await Deno.link(resolve(from), target)
  log(magenta(`linked ${resolve(from)} -> ${target}`))
}

const noop = () => {}

// deno-fmt-ignore
type ForceCreateLinkTo =
  (_: Logger) => (_: LinkAction) => (_: string) => (_: string) => Promise<void>

const forceCreateLinkTo: ForceCreateLinkTo =
  (log) => (action) => (from) => async (to) => {
    const meta = await hardLinkMeta(from)(to)
    fmt(log)(meta)

    await match(meta)
      .with(
        { isHardLinked: false, target: P.select() },
        (target) => action(log)(from)(target),
      )
      .otherwise(noop)
  }

const main = () =>
  new Command()
    .name("link.ts")
    .description("Hard link mod jar and image to steam's slay the spire folder")
    .option("-q, --quiet", "Do not output to console. Used in gradle task.")
    .option("-c, --check", "Exit with code 1 on failure.")
    .action(async ({ quiet, check }) => {
      const log = quiet ? noop : console.log
      const action = check ? reportHardLinkError : createHardLink
      const linkTo = forceCreateLinkTo(log)(action)

      await Promise.all([
        linkTo(jar)(join(mod, "content")),
        linkTo(image)(mod),
      ])
    })
    .parse(Deno.args)

if (import.meta.main) {
  main()
}
