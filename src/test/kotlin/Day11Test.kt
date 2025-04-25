import Day11.addMaps
import Day11.parse
import Day11.run1
import Day11.run2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day11Test {
    val sample = "125 17"
    val input = parse(sample)
    @Test
    fun test1() {
        assertEquals(55312, run1(input))
    }
    @Test
    fun test2() {
        assertEquals(2, run2(input, 0))
        assertEquals(3, run2(input, 1))
        assertEquals(4, run2(input, 2))
        assertEquals(5, run2(input, 3))
        assertEquals(9, run2(input, 4))
        assertEquals(13, run2(input, 5))
        assertEquals(55312, run2(input, 25))
    }
    @Test
    fun testAddMaps() {
        assertEquals(mapOf("a" to 2, "b" to 2, "c" to 2), addMaps(mapOf("a" to 1, "b" to 2), mapOf("a" to 1, "c" to 2), Int::plus))
    }
}