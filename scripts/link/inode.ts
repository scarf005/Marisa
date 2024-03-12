import { resolve } from "https://deno.land/std@0.219.0/path/mod.ts"

import * as TE from "https://deno.land/x/fun@v2.0.0-alpha.10/async_either.ts"
import { c, p } from "https://deno.land/x/copb@v1.0.1/mod.ts"
import promiseObject from "https://deno.land/x/promise_object@v0.10.0/index.ts"

const lstat = TE.tryCatch(Deno.lstat, (e) => (e as Error).message)
const extractInode = TE.match(() => -1, (x: Deno.FileInfo) => x.ino!)

/** Get inode from given filePath.
 * @returns inode number or -1 on error.
 */
export const inode = c(p(lstat)(extractInode))

const remove = TE.tryCatch(Deno.remove, (e) => (e as Error).message)
export const hardlink = (from: string) => async (target: string) => {
  const { f, t } = await promiseObject({ f: resolve(from), t: resolve(target) })

  await remove(t)()
  await Deno.link(f, t)
}
