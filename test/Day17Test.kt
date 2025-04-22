import Day17.oneInstruction
import Day17.parse
import Day17.run1
import Day17.run2
import Day17.State
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day17Test {
    val sample1 = """
        Register A: 729
        Register B: 0
        Register C: 0

        Program: 0,1,5,4,3,0
    """.trimIndent()
    val sample2 = """
        Register A: 10
        Register B: 0
        Register C: 0

        Program: 5,0,5,1,5,4
    """.trimIndent()
    val sample3 = """
        Register A: 2024
        Register B: 0
        Register C: 0

        Program: 0,1,5,4,3,0
    """.trimIndent()
    val sample4 = """
        Register A: 2024
        Register B: 0
        Register C: 0

        Program: 0,3,5,4,3,0
    """.trimIndent()
    val input1 = parse(sample1)
    val input2 = parse(sample2)
    val input3 = parse(sample3)
    val input4 = parse(sample4)
    val initState = State(0, 0, 0, 0, emptyList())
    @Test
    fun test1() {
        assertEquals(1, oneInstruction(initState.copy(c = 9), listOf(2, 6)).b)
        assertEquals("0,1,2", run1(input2))
        assertEquals("4,2,5,6,7,7,7,7,3,1,0", run1(input3))
        assertEquals("4,6,3,5,6,3,5,2,1,0", run1(input1))
    }
    @Test
    fun test2() {
        assertEquals(117440, run2(input4))
    }
}