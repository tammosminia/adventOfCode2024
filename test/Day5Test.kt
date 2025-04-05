import Day5.correctOrder
import Day5.parse
import Day5.run1
import Day5.run2
import Day5.swap
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day5Test {
    val sample = """
      47|53
      97|13
      97|61
      97|47
      75|29
      61|13
      75|53
      29|13
      97|29
      53|29
      61|53
      97|53
      61|29
      47|13
      75|47
      97|75
      47|61
      75|61
      47|29
      75|13
      53|13

      75,47,61,53,29
      97,61,53,29,13
      75,29,13
      75,97,47,61,53
      61,13,29
      97,13,75,29,47
    """.trimIndent()
    val input = parse(sample)
    @Test
    fun test1() {
        assertEquals(143, run1(input))
    }
    @Test
    fun test2() {
        assertEquals(listOf(97,75,47,61,53), correctOrder(listOf(75,97,47,61,53), input.rules))
        assertEquals(listOf(61,29,13), correctOrder(listOf(61,13,29), input.rules))
        assertEquals(listOf(97,75,47,29,13), correctOrder(listOf(97,13,75,29,47), input.rules))
        assertEquals(123, run2(input))
    }
    @Test
    fun testSwap() {
        val l = listOf(1, 2, 3)
        assertEquals(listOf(2, 1, 3), l.swap(0, 1))
        assertEquals(listOf(3, 2, 1), l.swap(0, 2))
        assertEquals(listOf(1, 3, 2), l.swap(1, 2))
        assertEquals(listOf(1, 3, 2), l.swap(2, 1))
    }

}