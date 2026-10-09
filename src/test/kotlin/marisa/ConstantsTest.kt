package marisa

import kotlin.test.Test
import kotlin.test.assertEquals

class ConstantsTest {
    @Test
    fun `prefixes resource paths with the mod namespace`() {
        assertEquals("marisa/img/cards/test.png", "img/cards/test.png".modPath())
    }

    @Test
    fun `preserves an empty relative path`() {
        assertEquals("marisa/", "".modPath())
    }
}
