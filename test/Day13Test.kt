import Day13.parse
import Day13.run1
import Day13.run2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day13Test {
    val sample1 = """
        Button A: X+94, Y+34
        Button B: X+22, Y+67
        Prize: X=8400, Y=5400

        Button A: X+26, Y+66
        Button B: X+67, Y+21
        Prize: X=12748, Y=12176

        Button A: X+17, Y+86
        Button B: X+84, Y+37
        Prize: X=7870, Y=6450

        Button A: X+69, Y+23
        Button B: X+27, Y+71
        Prize: X=18641, Y=10279
    """.trimIndent()
    val input1 = parse(sample1)
    @Test
    fun test1() {
        assertEquals(480, run1(input1))
    }
    @Test
    fun test2() {
        assertEquals(80, run2(input1))
    }
}