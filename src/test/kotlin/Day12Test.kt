import Day12.parse
import Day12.run1
import Day12.run2
import Day12.splitRegions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12Test {
    val sample1 = """
        AAAA
        BBCD
        BBCC
        EEEC
    """.trimIndent()
    val input1 = parse(sample1)
    @Test
    fun test1() {
        assertEquals(5, splitRegions(input1).size)
        assertEquals(140, run1(input1))
    }
    @Test
    fun test2() {
        assertEquals(80, run2(input1))
    }
}