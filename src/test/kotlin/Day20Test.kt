import Day20.parse
import Day20.run1
import Day20.run2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day20Test {
    val sample1 = """
        ###############
        #...#...#.....#
        #.#.#.#.#.###.#
        #S#...#.#.#...#
        #######.#.#.###
        #######.#.#...#
        #######.#.###.#
        ###..E#...#...#
        ###.#######.###
        #...###...#...#
        #.#####.#.###.#
        #.#...#.#.#...#
        #.#.#.#.#.#.###
        #...#...#...###
        ###############
    """.trimIndent()
    val input1 = parse(sample1)
    @Test
    fun test1() {
        assertEquals(1, run1(input1, 64))
        assertEquals(44, run1(input1, 2))
    }
    @Test
    fun test2() {
        assertEquals(3, run2(input1, 76))
        val total = listOf(32,31,29,39,25,23,20,19,12,14,12,22,4,3).sum()
        assertEquals(total, run2(input1, 50))

    }
}