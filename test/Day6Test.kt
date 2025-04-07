import Day6.parse
import Day6.run1
import Day6.run2
import Day6.willLoop
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day6Test {
    val sample = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent()
    val sample2 = """
        .#..
        ...#
        #...
        .^#.
    """.trimIndent()
    val input = parse(sample)
    @Test
    fun test1() {
        assertEquals(41, run1(input))
    }
    @Test
    fun test2() {
        assertEquals(6, run2(input))
        assert(willLoop(parse(sample2)))
    }

}