import Day21.parse
import Day21.run1
import Day21.run2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day21Test {
    val sample1 = """
        029A
        980A
        179A
        456A
        379A
    """.trimIndent()
    val input1 = parse(sample1)
    @Test
    fun test1() {
        assertEquals(126384, Day21A.run1(input1))
        assertEquals(126384, run1(input1))
    }
    @Test
    fun test2() {
        assertEquals(3, run2(input1))
    }
}