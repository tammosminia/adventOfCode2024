import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
class ExtensionsTest {
    @Test
    fun testSwap() {
        val l = listOf(1, 2, 3)
        assertEquals(listOf(2, 1, 3), l.swap(0, 1))
        assertEquals(listOf(3, 2, 1), l.swap(0, 2))
        assertEquals(listOf(1, 3, 2), l.swap(1, 2))
        assertEquals(listOf(1, 3, 2), l.swap(2, 1))
    }
}