package util

import kotlin.test.Test
import kotlin.test.assertEquals

class IterableTest {
    @Test
    fun `partitions mixed types while preserving order and duplicates`() {
        val values = listOf<Any>("first", 1, "second", 2, "first")

        val (strings, others) = values.partitionByType<String, Any>()

        assertEquals(listOf("first", "second", "first"), strings)
        assertEquals(listOf(1, 2), others)
        assertEquals(listOf<Any>("first", 1, "second", 2, "first"), values)
    }

    @Test
    fun `partitions an empty iterable`() {
        assertEquals(emptyList<String>() to emptyList<Any>(), emptyList<Any>().partitionByType<String, Any>())
    }

    @Test
    fun `handles all matching elements`() {
        assertEquals(listOf("a", "b") to emptyList<Any>(), listOf<Any>("a", "b").partitionByType<String, Any>())
    }

    @Test
    fun `handles no matching elements`() {
        assertEquals(emptyList<String>() to listOf(1, 2), listOf<Any>(1, 2).partitionByType<String, Any>())
    }

    @Test
    fun `matches subclasses and leaves null in the remainder`() {
        val values = sequenceOf<Any?>(1, null, "text", 2.5).asIterable()

        val (numbers, others) = values.partitionByType<Number, Any?>()

        assertEquals(listOf<Number>(1, 2.5), numbers)
        assertEquals(listOf(null, "text"), others)
    }
}
