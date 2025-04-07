import Day7.parse
import Day7.run1
import Day7.run2
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day7Test {
    val sample = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
    """.trimIndent()
    val input = parse(sample)
    @Test
    fun test1() {
        assertEquals(3749, run1(input))
    }
    @Test
    fun test2() {
        assertEquals(11387, run2(input))
    }

}