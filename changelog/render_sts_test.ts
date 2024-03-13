import { assertEquals } from "$std/assert/assert_equals.ts"
import { Commit } from "./mod.ts"
import { fmtCommit } from "./render_sts.ts"

Deno.test(
  "fmtCommit() strips invalid characters",
  () => assertEquals(fmtCommit({ subject: "`foo` (#123)" } as unknown as Commit), "- [foo]"),
)
