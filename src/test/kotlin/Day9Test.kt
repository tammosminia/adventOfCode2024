import Day9.moveFilesLeft
import Day9.moveFilesLeft2
import Day9.parse
import Day9.printDebug
import Day9.run1
import Day9.run2
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class Day9Test {
    val sample = "2333133121414131402"
    val input = parse(sample)
    @Test
    fun test1() {
        assertEquals("00...111...2...333.44.5555.6666.777.888899", printDebug(input))
        assertEquals("0099811188827773336446555566..............", printDebug(moveFilesLeft(input)))
        assertEquals(1928, run1(input))
    }
    @Test
    fun test2() {
        assertEquals("00992111777.44.333....5555.6666.....8888..", printDebug(moveFilesLeft2(input)))
        assertEquals(2858, run2(input))
    }
    @Test
    fun testSwap() {
        val l = listOf(1, 2, 3)
        assertEquals(listOf(2, 1, 3), l.swap(0, 1))
        assertEquals(listOf(3, 2, 1), l.swap(0, 2))
        assertEquals(listOf(1, 3, 2), l.swap(1, 2))
        assertEquals(listOf(1, 3, 2), l.swap(2, 1))
    }
    @Test
    fun testSwapMultiple() {
        val l = listOf(1, 2, 3, 4, 5)
        assertEquals(listOf(3, 4, 1, 2, 5), l.swap(0, 2, 2))
        assertEquals(listOf(4, 5, 3, 1, 2), l.swap(0, 3, 2))
        assertEquals(listOf(1, 4, 5, 2, 3), l.swap(1, 3, 2))
    }
    @Test
    fun testSwapExceptions() {
        val l = listOf(1, 2, 3)
        assertThrows<IllegalArgumentException> { l.swap(0, 0) }
        assertThrows<IllegalArgumentException> { l.swap(-1, 1) }
        assertThrows<IllegalArgumentException> { l.swap(0, 3) }
        assertThrows<IllegalArgumentException> { l.swap(0, 1, 0) }
        assertThrows<IllegalArgumentException> { l.swap(0, 1, 2) }
    }

}