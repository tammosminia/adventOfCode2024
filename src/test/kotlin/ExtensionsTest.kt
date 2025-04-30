import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.test.assertContentEquals

class ExtensionsTest {
    @Test
    fun testSwap() {
        val l = listOf(1, 2, 3)
        assertEquals(listOf(2, 1, 3), l.swap(0, 1))
        assertEquals(listOf(3, 2, 1), l.swap(0, 2))
        assertEquals(listOf(1, 3, 2), l.swap(1, 2))
        assertEquals(listOf(1, 3, 2), l.swap(2, 1))
    }

    @Test
    fun testSequenceEqualsList() {
        assertTrue(sequenceOf<Int>().equalsList(listOf()))
        assertTrue(sequenceOf(1, 2, 3).equalsList(listOf(1, 2, 3)))
        assertFalse(sequenceOf(1, 2, 3, 4).equalsList(listOf(1, 2, 3)))
        assertFalse(sequenceOf(1, 2, 3).equalsList(listOf(1, 2, 3, 4)))
        assertFalse(sequenceOf(1, 2, 3).equalsList(listOf(1, 2, 4)))
    }

    @Test
    fun testAllPerturbations() {
        assertContentEquals(sequenceOf(listOf()), listOf<Int>().allPerturbations())
        assertContentEquals(sequenceOf(listOf(1)), listOf(1).allPerturbations())
        assertContentEquals(sequenceOf(listOf(1, 2), listOf(2, 1)), listOf(1, 2).allPerturbations())
        assertContentEquals(sequenceOf(listOf(1, 2, 3), listOf(1, 3, 2), listOf(2, 1, 3), listOf(2, 3, 1), listOf(3, 1, 2), listOf(3, 2, 1)), listOf(1, 2, 3).allPerturbations())
        assertEquals(sequenceOf(listOf(1, 1, 2), listOf(1, 2, 1), listOf(2, 1, 1), listOf(2, 1, 1), listOf(1, 1, 2), listOf(1, 2, 1)).toSet(), listOf(1, 1, 2).allPerturbations().toSet())
    }
}