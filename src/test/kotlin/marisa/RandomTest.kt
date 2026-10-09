package marisa

import com.megacrit.cardcrawl.random.Random as CardCrawlRandom
import kotlin.test.Test
import kotlin.test.assertEquals

class RandomTest {
    @Test
    fun `uses the game RNG inclusive upper bound`() {
        val values = listOf("first", "middle", "last")
        val actualRng = CardCrawlRandom(123L)
        val expectedRng = CardCrawlRandom(123L)
        val expected = List(100) { values[expectedRng.random(values.lastIndex)] }
        val actual = List(100) { values.random(actualRng) }

        assertEquals(expected, actual)
        assertEquals(values.toSet(), actual.toSet())
        assertEquals(expectedRng.counter, actualRng.counter)
    }

    @Test
    fun `selects the only element from a singleton list`() {
        val random = CardCrawlRandom(123L)

        assertEquals("only", listOf("only").random(random))
    }

    @Test
    fun `repeats selections for the same seed`() {
        val values = (0..10).toList()
        val first = CardCrawlRandom(42L)
        val second = CardCrawlRandom(42L)

        assertEquals(List(20) { values.random(first) }, List(20) { values.random(second) })
    }
}
