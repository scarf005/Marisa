import { resolve } from "https://deno.land/std@0.183.0/path/mod.ts"
import { fromFileUrl } from "https://deno.land/std@0.183.0/path/mod.ts"
import { dirname } from "https://deno.land/std@0.183.0/path/win32.ts"

import { c, p } from "https://deno.land/x/copb@v1.0.1/mod.ts"

export const scripts = c(p(fromFileUrl)(dirname))(import.meta.url)
export const root = resolve(scripts, "..")
export const jar = resolve(root, "build", "libs", "MarisaContinued.jar")
export const image = resolve("docs", "thumbnail", "image.jpg")

export const home = Deno.env.get("HOME")!
export const steam = resolve(home, ".steam", "steam", "steamapps", "common")
export const mod = resolve(steam, "SlayTheSpire", "MarisaContinued")
