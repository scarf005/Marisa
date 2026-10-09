package marisa.testing

import java.io.File
import kotlin.test.assertEquals

/**
 * Compares [actual] with `src/test/snapshots/[name].txt`.
 * Run with `UPDATE_SNAPSHOTS=1` to rewrite the file instead.
 */
fun assertSnapshot(name: String, actual: String) {
    val file = File(System.getProperty("snapshots"), "$name.txt")
    if (System.getenv("UPDATE_SNAPSHOTS") == "1" || !file.exists()) {
        file.writeText(actual)
        return
    }
    assertEquals(file.readText(), actual, "snapshot $name differs; rerun with UPDATE_SNAPSHOTS=1 if intended")
}
