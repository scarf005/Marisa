import { brightGreen, brightRed, brightYellow } from "https://deno.land/std@0.183.0/fmt/colors.ts"

import { c, p } from "https://deno.land/x/copb@v1.0.1/mod.ts"

import { outdent } from "npm:outdent"

import { LinkMeta } from "./hardlink.ts"

const fmtDent = outdent({ trimTrailingNewline: false })

type Fmt = Omit<LinkMeta, "isHardLinked">

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

export const fmt = (log: Logger) => ({ isHardLinked, ...args }: LinkMeta) =>
  c(p(isHardLinked ? fmtOk : fmtErr)(log))(args)

export type Logger = (...x: string[]) => void
