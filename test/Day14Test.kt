import Day14.parse
import Day14.run1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day14Test {
    val sample1 = """
        p=0,4 v=3,-3
        p=6,3 v=-1,-3
        p=10,3 v=-1,2
        p=2,0 v=2,-1
        p=0,0 v=1,3
        p=3,0 v=-2,-2
        p=7,6 v=-1,-3
        p=3,0 v=-1,-2
        p=9,3 v=2,3
        p=7,3 v=-1,2
        p=2,4 v=2,-3
        p=9,5 v=-3,-3
    """.trimIndent()
    val input1 = parse(sample1, 11, 7)
    @Test
    fun test1() {
        assertEquals(Coordinate.create(2, 5), input1.moveRobot(input1.robots[7]).pos)
        assertEquals(12, run1(input1))
    }
}