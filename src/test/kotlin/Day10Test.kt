import Day10.parse
import Day10.run1
import Day10.run2
import Day10.trailheadScore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day10Test {
    val sample = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent()
    val input = parse(sample)
    @Test
    fun test1() {
        assertEquals(1, trailheadScore(input, Coordinate.create(2, 5)))
        assertEquals(3, trailheadScore(input, Coordinate.create(5, 5)))
        assertEquals(36, run1(input))
    }
    @Test
    fun test2() {
        assertEquals(81, run2(input))
    }

}