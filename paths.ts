import { resolve } from "@std/path"

const readGradle = async (): Promise<Record<string, string>> => {
  const gradle = await Deno.readTextFile("gradle.properties")

  return Object.fromEntries(gradle.split("\n").map((line) => line.trim().split("=")))
}

const gradleProperties = await readGradle()

export const root = import.meta.dirname!
export const jar = resolve(root, "build", "libs", "MarisaContinued.jar")
export const image = resolve("docs", "thumbnail", "image.jpg")

export const home = Deno.env.get("HOME")!
export const steam = resolve(gradleProperties["userSteamDir"], "common")
export const mod = resolve(steam, "SlayTheSpire", "MarisaContinued")
