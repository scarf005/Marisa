import { resolve } from "@std/path"

export const root = import.meta.dirname!
export const jar = resolve(root, "build", "libs", "MarisaContinued.jar")
export const image = resolve("docs", "thumbnail", "image.jpg")

export const home = Deno.env.get("HOME")!
export const steam = resolve(home, ".steam", "steam", "steamapps", "common")
export const mod = resolve(steam, "SlayTheSpire", "MarisaContinued")
