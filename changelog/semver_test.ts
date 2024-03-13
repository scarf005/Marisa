import { assertObjectMatch } from "$std/assert/assert_object_match.ts"
import { assertEquals } from "$std/assert/assert_equals.ts"
import { getNextVersion, increaseVersion } from "./semver.ts"

const major = { type: "fix", breaking: "!", subject: "big stuff", pr: "123", scopes: undefined }
const minor = { type: "feat", breaking: undefined, subject: "feat", pr: "124", scopes: undefined }
const patch = { type: "fix", breaking: undefined, subject: "fix", pr: "125", scopes: undefined }

Deno.test("increaseVersion() only increases biggest change", async (t) => {
  await t.step("major", () => {
    assertObjectMatch(
      increaseVersion({ major: 1, minor: 0, patch: 0 }, [
        minor,
        patch,
        major,
      ]),
      { major: 2, minor: 0, patch: 0 },
    )
  })

  await t.step("minor", () => {
    assertObjectMatch(
      increaseVersion({ major: 1, minor: 0, patch: 0 }, [patch, minor]),
      { major: 1, minor: 1, patch: 0 },
    )
  })
})

Deno.test("nextVersion() returns correct version", () => {
  assertEquals(getNextVersion("v1.0.0", [minor, patch]), "v1.1.1")
})

Deno.test("nextVersion() handles multiple patch in single release", () => {
  assertEquals(getNextVersion("v1.0.0", [patch, patch, patch]), "v1.0.1")
})
