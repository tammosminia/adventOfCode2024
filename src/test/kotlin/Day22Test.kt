import Day22.parse
import Day22.run1
import Day22.run2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day22Test {
    val sample1 = """
        1
        10
        100
        2024
    """.trimIndent()
    val input1 = parse(sample1)
    @Test
    fun test1() {
        assertEquals(37, Day22.mix(42L, 15L))
        assertEquals(16113920, Day22.prune(100000000))
        assertEquals(15887950, Day22.nextSecret(123))
        assertEquals(16495136, Day22.nextSecret(15887950))
        assertEquals(37327623, run1(input1))
    }
    @Test
    fun test2() {
        assertEquals(3, run2(input1))
    }
}