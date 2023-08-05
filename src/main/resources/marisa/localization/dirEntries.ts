import { aggregate, pipeAsync } from "npm:iter-ops"

export async function dirEntries(dir: string) {
  const entries = pipeAsync(Deno.readDir(dir), aggregate((x) => Promise.all(x)))
  return await entries.first ?? []
}
