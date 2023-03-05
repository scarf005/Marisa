import {
  createFromBuffer,
  GlobalConfiguration,
} from "https://deno.land/x/dprint@0.2.0/mod.ts"
import { join } from "https://deno.land/std@0.178.0/path/mod.ts"
import * as log from "https://deno.land/std@0.178.0/log/mod.ts"
import { projectRoot } from "./common.ts"

const globalConfig: GlobalConfiguration = {
  indentWidth: 2,
  lineWidth: 80,
}

const wasm = "typescript-0.83.0.wasm"
const cachePath = join(projectRoot, ".cache", "dprint")

async function loadWasm(path: string, name: string) {
  const wasmPath = join(path, name)
  async function loadWasmFile() {
    return createFromBuffer(await Deno.readFile(wasmPath))
  }
  async function downloadWasm() {
    const { body } = await fetch(`https://plugins.dprint.dev/${wasm}`)

    if (!body) {
      log.error(`dprint :: failed to download plugin`)
      throw new Error("dprint :: failed to download plugin")
    }
    return body
  }
  async function saveWasm(body: ReadableStream<Uint8Array>) {
    await Deno.mkdir(path, { recursive: true })
    await Deno.writeFile(wasmPath, body)
  }

  try {
    return await loadWasmFile()
  } catch {
    log.warning(`dprint :: plugin not found in ${cachePath}`)
    log.warning(`dprint :: downloading ${name}`)
    const body = await downloadWasm()

    log.warning(`dprint :: plugin downloaded, caching at ${path}`)
    await saveWasm(body)

    log.warning(`dprint :: loading plugin`)
    return await loadWasmFile()
  }
}

const before = performance.now()
export const tsFormatter = await loadWasm(cachePath, wasm)
const after = performance.now()

tsFormatter.setConfig(globalConfig, { semiColons: "asi" })
log.info(`dprint :: plugin loaded in ${(after - before).toPrecision(2)}ms`)
