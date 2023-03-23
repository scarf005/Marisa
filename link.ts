#!/usr/bin/env -S deno run --allow-read --allow-write --allow-env

import { join, resolve } from "https://deno.land/std@0.177.0/path/mod.ts"
import promiseObject from "https://deno.land/x/promise_object@v0.10.0/index.ts"
import { outdent } from "npm:outdent"
import {
  brightGreen,
  brightRed,
  brightYellow,
  magenta,
} from "https://deno.land/std@0.177.0/fmt/colors.ts"

const jar = join("build", "libs", "MarisaContinued.jar")
const image = join("docs", "thumbnail", "image.jpg")
const home = Deno.env.get("HOME")!
// deno-fmt-ignore
const mod = join(home, ".steam", "steam", "steamapps", "common", "SlayTheSpire", "MarisaContinued")

type Fmt = {
  from: string
  fromIno: number
  target: string
  targetIno: number
}
const fmtOk = ({ from, fromIno, target, targetIno }: Fmt) =>
  outdent`
      ${brightGreen(`${fromIno}`)} :: ${brightGreen(from)}
      ${brightGreen(`${targetIno}`)} :: ${brightGreen(target)}
    `

const fmtErr = ({ from, fromIno, target, targetIno }: Fmt) =>
  outdent`
      ${brightGreen(`${fromIno}`)} :: ${brightYellow(from)}
      ${brightRed(`${targetIno}`)} :: ${brightYellow(target)}
    `

const inode = async (filePath: string) => {
  try {
    return (await Deno.lstat(filePath)).ino!
  } catch {
    return -1
  }
}

const forceCreateLinkTo = async (from: string, to: string): Promise<void> => {
  const target = join(to, from.split("/").pop()!)
  // deno-fmt-ignore
  const { fromIno, targetIno } =
    await promiseObject({ fromIno: inode(from), targetIno: inode(target) })

  const fmt = fromIno === targetIno ? fmtOk : fmtErr
  console.log(fmt({ from, fromIno, target, targetIno }) + "\n")

  if (fromIno == targetIno) return

  try {
    await Deno.remove(target)
    // deno-lint-ignore no-empty
  } catch {}
  await Deno.link(resolve(from), target)
  console.log(magenta(`linked ${resolve(from)} -> ${target}`))
}

await Promise.all([
  forceCreateLinkTo(jar, join(mod, "content")),
  forceCreateLinkTo(image, mod),
])
